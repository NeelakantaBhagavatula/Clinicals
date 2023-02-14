package sjsu.edu.clinicals.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sjsu.edu.clinicals.dto.ClinicalDataRequest;
import sjsu.edu.clinicals.entities.ClinicalData;
import sjsu.edu.clinicals.entities.Patient;
import sjsu.edu.clinicals.repos.ClinicalDataRepository;
import sjsu.edu.clinicals.repos.PatientRepository;
import sjsu.edu.clinicals.util.BMICalculator;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClinicalDataController {

	private ClinicalDataRepository clinicalDataRepository;
	private PatientRepository patientRepository;

	@Autowired
	ClinicalDataController(ClinicalDataRepository clinicalDataRepository, PatientRepository patientRepository) {
		this.clinicalDataRepository = clinicalDataRepository;
		this.patientRepository = patientRepository;
	}

	@RequestMapping(value = "/clinicals", method = RequestMethod.POST)
	public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest request) {
		Patient patient = patientRepository.findById(request.getPatientId()).get();
		ClinicalData clinicalData = new ClinicalData();
		clinicalData.setComponentName(request.getComponentName());
		clinicalData.setComponentValue(request.getComponentValue());
		clinicalData.setPatient(patient);
		ClinicalData savedClinicalData = clinicalDataRepository.save(clinicalData);

		return savedClinicalData;
	}

	@RequestMapping(value = "/clinicals/{patientId}/{componentName}", method = RequestMethod.GET)
	public List<ClinicalData> getClinicalData(@PathVariable("patientId") int patientId,
			@PathVariable("componentName") String componentName) {
		// there are heightWeight records in DB but not bmi, so if user is requesting
		// for bmi we need to convert it to hw before hitting DB, get the record and
		// calculate bmi from hw records
		if (componentName.equals("bmi")) {
			componentName = "hw";
		}

		List<ClinicalData> clinicalData = clinicalDataRepository
				.findByPatientIdAndComponentNameOrderByMeasuredDateTime(patientId, componentName);
		List<ClinicalData> clinicalDataCopy = new ArrayList<>(clinicalData);

		for (ClinicalData item : clinicalDataCopy) {
			BMICalculator.calculateBMI(clinicalData, item);
		}
		return clinicalData;
	}
}

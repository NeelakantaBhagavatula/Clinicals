package sjsu.edu.clinicals.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sjsu.edu.clinicals.entities.ClinicalData;

public interface ClinicalDataRepository extends JpaRepository<ClinicalData, Integer> {

	List<ClinicalData> findByPatientIdAndComponentNameOrderByMeasuredDateTime(int patientId, String componentName);

}

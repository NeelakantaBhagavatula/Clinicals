package sjsu.edu.clinicals.util;

import java.util.List;

import sjsu.edu.clinicals.entities.ClinicalData;

public class BMICalculator {

	public static void calculateBMI(List<ClinicalData> clinicalData, ClinicalData item) {
		if (item.getComponentName().equals("hw")) {
			String[] heightWeight = item.getComponentValue().split("/");

			if (heightWeight != null && heightWeight.length > 1) {
				float heightInMetres = Float.parseFloat(heightWeight[0]) * 0.4536F;
				float bmi = Float.parseFloat(heightWeight[1]) / (heightInMetres * heightInMetres);
				ClinicalData bmiClinicalData = new ClinicalData();
				bmiClinicalData.setComponentName("bmi");
				bmiClinicalData.setComponentValue(Float.toString(bmi));
				clinicalData.add(bmiClinicalData);
			}
		}
	}
}

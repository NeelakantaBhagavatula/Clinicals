package sjsu.edu.clinicals.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import sjsu.edu.clinicals.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}

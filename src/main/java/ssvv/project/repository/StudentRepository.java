package ssvv.project.repository;


import ssvv.project.domain.Student;
import ssvv.project.validation.Validator;

public class StudentRepository extends AbstractCRUDRepository<String, Student> {
    public StudentRepository(Validator<Student> validator) {
        super(validator);
    }
}


package ssvv.project.repository;


import ssvv.project.domain.Nota;
import ssvv.project.domain.Pair;
import ssvv.project.validation.Validator;

public class NotaRepository extends AbstractCRUDRepository<Pair<String, String>, Nota> {
    public NotaRepository(Validator<Nota> validator) {
        super(validator);
    }
}

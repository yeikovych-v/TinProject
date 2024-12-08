package v.yeikovych.tinprojectsp.service;

import org.springframework.data.jpa.repository.JpaRepository;
import v.yeikovych.tinprojectsp.dto.Dto;
import v.yeikovych.tinprojectsp.model.EntityClass;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class CrudService<E extends EntityClass<D>, D extends Dto<E>, R extends JpaRepository<E, UUID>> {

    protected final R repository;

    public CrudService(R repository) {
        this.repository = repository;
    }

    public List<D> dtoList() {
        return repository
                .findAll()
                .stream()
                .map(E::toDto)
                .collect(Collectors.toList());
    }


    public void save(E itn) {
        repository.save(itn);
    }

    public void save(D itn) {
        save(itn.toEntity());
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Optional<D> findDtoById(UUID id) {
        return repository.findById(id).map(E::toDto);
    }

    public Optional<E> findEntityById(UUID id) {
        return repository.findById(id);
    }

}

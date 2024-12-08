package v.yeikovych.tinprojectsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import v.yeikovych.tinprojectsp.model.itn.Itn;

import java.util.UUID;

public interface ItnRepository extends JpaRepository<Itn, UUID> {
}

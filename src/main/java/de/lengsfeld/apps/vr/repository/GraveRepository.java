package de.lengsfeld.apps.vr.repository;

import de.lengsfeld.apps.vr.entity.Grave;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraveRepository extends CrudRepository<Grave, Long> {

    Grave findGraveByFirstNameContains(String string);
}

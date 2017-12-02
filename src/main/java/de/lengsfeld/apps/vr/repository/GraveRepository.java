package de.lengsfeld.apps.vr.repository;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.entity.Grave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraveRepository extends JpaRepository<Grave, Long> {

    Grave findGraveByFirstName(String string);

    List<Grave> findGraveByCemetery(Cemetery cemetery);
}

package de.lengsfeld.apps.vr.repository;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.entity.Grave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@Repository
public interface GraveRepository extends JpaRepository<Grave, Long> {

    Grave findGraveByFirstName(String string);

    List<Grave> findGraveByCemetery(Cemetery cemetery);

    Grave save(Grave grave);
}

package de.lengsfeld.apps.vr.repository;

import de.lengsfeld.apps.vr.entity.Cemetery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Repository
public interface CemeteryRepository extends JpaRepository<Cemetery, Long> {

    Cemetery findByName(String string);
}

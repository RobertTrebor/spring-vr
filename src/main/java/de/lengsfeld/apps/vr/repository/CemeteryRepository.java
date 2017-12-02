package de.lengsfeld.apps.vr.repository;

import de.lengsfeld.apps.vr.entity.Cemetery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CemeteryRepository extends JpaRepository<Cemetery, Long> {

    Cemetery findByName(String string);
}

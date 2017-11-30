package de.lengsfeld.apps.vr.repository;

import de.lengsfeld.apps.vr.entity.Cemetery;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CemeteryRepository extends PagingAndSortingRepository<Cemetery, Long> {

    Cemetery findAllByGravesContains(String string);
}

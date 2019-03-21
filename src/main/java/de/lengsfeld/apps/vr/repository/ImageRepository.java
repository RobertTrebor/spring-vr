package de.lengsfeld.apps.vr.repository;

import de.lengsfeld.apps.vr.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

    //Image findFirstImageByGrave(Grave grave);
    //List<Image> findAllBy(Grave grave);

}

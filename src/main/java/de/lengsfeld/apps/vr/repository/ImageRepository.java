package de.lengsfeld.apps.vr.repository;

import de.lengsfeld.apps.vr.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

    Image findImageByFileName(String filename);

    List<GraveImage> findImagesByGrave(Grave grave);

    List<CemeteryImage> findImagesByCemetery(Cemetery cemetery);

    Image findImageById(String id);

}

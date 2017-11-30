package de.lengsfeld.apps.vr.service;

import de.lengsfeld.apps.vr.entity.Grave;

import java.util.List;

public interface GraveService {

    Grave findById(Long id);

    Grave findByName(String name);

    void saveGrave(Grave grave);

    void updateGrave(Grave grave);

    void deleteGraveById(Long id);

    void deleteAllGraves();

    List<Grave> findAllGraves();

    boolean isGraveExist(Grave grave);
}

package de.lengsfeld.apps.vr.service;

import de.lengsfeld.apps.vr.entity.Cemetery;

import java.util.List;

public interface CemeteryService {

    Cemetery findById(Long id);

    Cemetery findByName(String name);

    void saveCemetery(Cemetery cemetery);

    void updateCemetery(Cemetery cemetery);

    void deleteCemeteryById(Long id);

    void deleteAllCemeteries();

    List<Cemetery> findAllCemeteries();

    boolean isCemeteryExist(Cemetery cemetery);

}

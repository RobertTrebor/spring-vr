package de.lengsfeld.apps.vr.service;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("cemeteryService")
@Transactional
public class CemeteryServiceImpl implements CemeteryService {

    @Autowired
    private CemeteryRepository cemeteryRepository;

    @Override
    public Cemetery findById(Long id) {
        return cemeteryRepository.findOne(id);
    }

    @Override
    public Cemetery findByName(String name) {
        return cemeteryRepository.findByName(name);
    }

    @Override
    public void saveCemetery(Cemetery cemetery) {
        cemeteryRepository.save(cemetery);
    }

    @Override
    public void updateCemetery(Cemetery cemetery) {
        saveCemetery(cemetery);
    }

    @Override
    public void deleteCemeteryById(Long id) {
        cemeteryRepository.delete(id);
    }

    @Override
    public void deleteAllCemeteries() {
        cemeteryRepository.deleteAll();
    }

    @Override
    public List<Cemetery> findAllCemeteries() {
        Iterable<Cemetery> cemeteryIterable = cemeteryRepository.findAll();
        List<Cemetery> cemeteryList = new ArrayList<>();
        cemeteryIterable.forEach(cemeteryList::add);
        return cemeteryList;
    }

    @Override
    public boolean isCemeteryExist(Cemetery cemetery) {
        return findByName(cemetery.getName()) != null;
    }
}

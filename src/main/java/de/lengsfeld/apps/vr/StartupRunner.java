package de.lengsfeld.apps.vr;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.entity.Grave;
import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import de.lengsfeld.apps.vr.repository.GraveRepository;
import de.lengsfeld.apps.vr.service.CemeteryService;
import de.lengsfeld.apps.vr.service.GraveService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.List;

public class StartupRunner implements CommandLineRunner {

    protected final Log log = LogFactory.getLog(getClass());


    @Autowired
    private GraveRepository graveRepository;

    @Autowired
    private CemeteryRepository cemeteryRepository;

    @Autowired
    private CemeteryService cemeteryService;

    @Autowired
    private GraveService graveService;

    @Override
    public void run(String... args) {
        log.info("Number of graves: " + graveRepository.count());
        if(cemeteryRepository.count() == 0L) {
            Cemetery cemetery = new Cemetery("Dorotheenstädtischer-Friedrichswerderscher Friedh");
            cemeteryRepository.save(cemetery);
            Cemetery cemetery2 = new Cemetery("Second Friedhof");
            cemeteryRepository.save(cemetery2);

            Grave grave = new Grave("Robert", "Lengsfeld", cemetery);
            graveRepository.save(grave);
            Grave grave2 = new Grave("Sombody", "IsDead", cemetery);
            graveRepository.save(grave2);
            List<Grave> graveList = new ArrayList<>();
            graveList.add(grave);
            graveList.add(grave2);
            cemetery = cemeteryRepository.findById(1L).get();
            cemetery.setGraves(graveList);
            cemeteryRepository.save(cemetery);
        }
        //testthis();
    }

    public void testthis() {
        Cemetery cemetery = cemeteryService.findById(1L);
        List<Grave> graves = graveService.findAllGraves();
        log.info(cemetery.getName() + ", " + graves.size() + "graves");
    }

}

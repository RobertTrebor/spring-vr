package de.lengsfeld.apps.vr;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.entity.Grave;
import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import de.lengsfeld.apps.vr.repository.GraveRepository;
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


    @Override
    public void run(String... args) throws Exception {
        log.info("Number of graves: " + graveRepository.count());
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
    }

}

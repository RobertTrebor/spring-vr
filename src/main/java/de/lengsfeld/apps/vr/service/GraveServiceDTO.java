package de.lengsfeld.apps.vr.service;

import de.lengsfeld.apps.vr.dto.GraveDTO;
import de.lengsfeld.apps.vr.entity.Grave;
import de.lengsfeld.apps.vr.repository.GraveRepository;
import de.lengsfeld.apps.vr.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GraveServiceDTO {
/*

    @Value("${resource.graves}")
    private String resource;

    @Value("${resource.graves}/{id}")
    private String idResource;
*/

    @Autowired
    private GraveRepository graveRepository;

    @Autowired
    private ImageRepository imageRepository;


    public List<GraveDTO> findAll() {
        return graveRepository.findAll().stream()
                .map(entity -> new GraveDTO(entity.getId(), entity.getFirstName(), entity.getLastName()))
                .collect(Collectors.toList());
    }

    public GraveDTO create(GraveDTO grave){
        Grave newGrave = new Grave();
        newGrave.setFirstName(grave.getFirstName());
        newGrave.setLastName(grave.getLastName());
        Grave savedGrave = graveRepository.saveAndFlush(newGrave);
        return new GraveDTO(savedGrave.getId(), savedGrave.getFirstName(), savedGrave.getLastName());
    }

    public void delete(Long id){
        Optional<Grave> grave = graveRepository.findById(id);
        if(grave.isPresent()) {
            if(imageRepository.findImagesByGrave(grave.get()).isEmpty()) {
                graveRepository.delete(grave.get());
            }
        }
    }

}

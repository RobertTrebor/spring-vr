package de.lengsfeld.apps.vr.service;

import de.lengsfeld.apps.vr.dto.GraveDTO;
import de.lengsfeld.apps.vr.entity.Grave;
import de.lengsfeld.apps.vr.repository.GraveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
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
    private RestTemplate restTemplate;


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
        String idStr = String.valueOf(id);
        restTemplate.delete("http://localhost:8080/delete/" + id, id);
    }

}

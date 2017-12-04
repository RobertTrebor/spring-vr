package de.lengsfeld.apps.vr;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.entity.Grave;
import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class VrApplicationTests {

    final String URL_LOCALHOST = "http://localhost:8080/vr";
    final String CEMETERY_NAME_1 = "Dorotheenstädtischer-Friedrichswerderscher Friedh";
    final String GRAVE_LASTNAME_1 = "Lengsfeld";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CemeteryRepository cemeteryRepository;

    //@Value("${local.server.port}")
    //private int port = 8080;

    private MockMvc mockMvc;
    private RestTemplate restTemplate = new TestRestTemplate().getRestTemplate();

    //private TestRestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void contextLoads() {
        assertEquals(2, cemeteryRepository.count());
    }

    @Test
    public void testApiCemeteryOne() {
        String get = URL_LOCALHOST + "/api/cemetery/1";
        assertTrue(StringUtils.contains(get, URL_LOCALHOST + "/"));
        Cemetery cemetery = restTemplate
                .getForObject(get, Cemetery.class);
        assertNotNull(cemetery);
        assertEquals(CEMETERY_NAME_1, cemetery.getName());
    }

    @Test
    public void testApiCemeteryAll() {
        String get = URL_LOCALHOST + "/api/cemetery/";
        assertTrue(StringUtils.contains(get, URL_LOCALHOST + "/"));
        Cemetery[] response = restTemplate
                .getForObject(get, Cemetery[].class);
        List<Cemetery> cemeteries = Arrays.asList(response);
        assertNotNull(cemeteries);
        assertEquals(2, cemeteries.size());
        assertEquals(CEMETERY_NAME_1, cemeteries.get(0).getName());
    }


    @Test
    public void testCemeteryOne() {
        String get = URL_LOCALHOST + "/cemeteries/1";
        assertTrue(StringUtils.contains(get, URL_LOCALHOST + "/"));
        Cemetery cemetery = restTemplate
                .getForObject(get, Cemetery.class);
        assertNotNull(cemetery);
        assertEquals(CEMETERY_NAME_1, cemetery.getName());
    }

    @Test
    public void testCemeteryAll() {
        String get = URL_LOCALHOST + "/restcemeteries/";
        assertTrue(StringUtils.contains(get, URL_LOCALHOST + "/"));
        Cemetery[] response = restTemplate
                .getForObject(get, Cemetery[].class);
        List<Cemetery> cemeteries = Arrays.asList(response);
        assertNotNull(cemeteries);
        assertEquals(2, cemeteries.size());
        assertEquals(CEMETERY_NAME_1, cemeteries.get(0).getName());
    }


    @Test
    public void testApiGravesInCemetery() {
        String get = URL_LOCALHOST + "/api/cemetery/1/graves";
        assertTrue(StringUtils.contains(get, URL_LOCALHOST + "/"));
        Grave[] response = restTemplate.getForObject(get, Grave[].class);
        List<Grave> graves = Arrays.asList(response);
        assertNotNull(graves);
        assertEquals(2, graves.size());
        assertEquals(GRAVE_LASTNAME_1, graves.get(0).getLastName());
    }

    @Test
    public void testGravesAll() {
        String get = URL_LOCALHOST + "/restgraves/";
        assertTrue(StringUtils.contains(get, URL_LOCALHOST + "/"));
        Grave[] response = restTemplate.getForObject(get, Grave[].class);
        List<Grave> graves = Arrays.asList(response);
        assertNotNull(graves);
        assertEquals(2, graves.size());
        assertEquals(GRAVE_LASTNAME_1, graves.get(0).getLastName());
    }

}

package de.lengsfeld.apps.vr.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
public class LoginController {

    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping({"/user", "/me"})
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        if(principal != null) {
            map.put("name", principal.getName());
        }
        if (principal instanceof OAuth2Authentication) {
            OAuth2Authentication auth = (OAuth2Authentication) principal;
            Authentication userAuthentication = auth.getUserAuthentication();
            Map details = (LinkedHashMap) userAuthentication.getDetails();
            map.put("fullname", (String) details.get("name"));
            if(details.get("id") != null) {
                map.put("id", String.valueOf(details.get("id")));
            }
        }
        return map;
    }

}


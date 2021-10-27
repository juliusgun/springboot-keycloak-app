package com.juliusgun.sample.sso.client.demo;

import java.net.http.HttpRequest;
import java.security.Principal;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(path = "/")
    public String index() {
        return "external";
    }

    @GetMapping(path = "/customers")
    public String customers(Principal principal, Model model) {

        model.addAttribute("username", principal.getName());
        
        if (principal instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            AccessToken accessToken = kp.getKeycloakSecurityContext().getToken();
            System.out.println(accessToken.getRealmAccess().getRoles());

            model.addAttribute("email", accessToken.getEmail());
            model.addAttribute("firstname", accessToken.getGivenName());
            model.addAttribute("lastname", accessToken.getFamilyName());
            model.addAttribute("name", accessToken.getName());
        }

        return "customers";
    }

}

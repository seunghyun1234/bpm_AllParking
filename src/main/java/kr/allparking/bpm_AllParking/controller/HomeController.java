package kr.allparking.bpm_AllParking.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(Model model){

        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> cauthorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter= cauthorities.iterator();
        GrantedAuthority auth = iter.next();
        String role =auth.getAuthority();


        model.addAttribute("id",id);
        model.addAttribute("role",role);
        return "index";
    }
    @GetMapping("/me")
    public String me(){
        return "info/me";

    }
    @GetMapping("/team")
    public String team(){
        return "info/team";
    }
    @GetMapping("/location")
    public String location(){
        return "info/location";
    }
    @GetMapping("/charge")
    public String charge(){
        return "info/charge";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }


}

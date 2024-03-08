package com.example.REATapi.Controller;

import com.example.REATapi.Entities.User;
import com.example.REATapi.Payload.Payload;
import com.example.REATapi.Services.UserService;
import com.example.REATapi.dao.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Arrays;

@Controller
public class UserController {

//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/register")
    public String signup(){
        return "register";
    }

    @Autowired
    private RestTemplate restTemplate;

    String baseUrl = "https://bcb6-116-71-14-212.ngrok-free.app";

    String Mail = "/mail";

    @GetMapping(value = "/login")
    public String login()
    {
        //ApiCallLogin("wkinglastx@gmail.com");
        return "login";
    }

    @PostMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute User user, HttpSession session){

        User u = userService.saveUser(user);

        if(u != null)
        {
            session.setAttribute("msg","Regiter Successfully..");
        }else{
            session.setAttribute("msg","Regiteration Failed..");
        }

        return "redirect:/register";
    }

    public void ApiCallLogin(String email){

        StringBuilder stringBuilder = new StringBuilder(baseUrl);

        Payload data = new Payload();
        data.setTo(email);
        data.setSubject("You are Login Successfully..");
        data.setBody("<h1>Congratulations "+"Waseem"+" you are Login in our Student Managemant System..</h1>");
        data.setMethod("GOOGLE_SMTP");


        HttpEntity<Payload> httpEntity = new HttpEntity<>(data,getthttpHeaders());
        String url = stringBuilder.append(Mail).toString();
        restTemplate.exchange(url, HttpMethod.POST,httpEntity,Payload.class);

//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.postForEntity(uri, apiData, apiData.class);
    }

    private HttpHeaders getthttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


//    @ModelAttribute
//    public void commonUser(Principal p, Model m)
//    {
//        if (p != null)
//        {
//            String email = p.getName();
//            User user = userRepository.findByEmail(email);
//            m.addAttribute("user",user);
//            System.out.println("commonUser method call inside User Controller");
//        }
//    }

}

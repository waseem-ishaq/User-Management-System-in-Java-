package com.example.REATapi.Services;

import com.example.REATapi.Entities.Student;
import com.example.REATapi.Entities.User;
import com.example.REATapi.Payload.Payload;
import com.example.REATapi.dao.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder PasswordEncoder;

    @Autowired
    private RestTemplate restTemplate;


    String baseUrl = "https://bcb6-116-71-14-212.ngrok-free.app";


    String Mail = "/mail";

    @Override
    public User saveUser(User user) {

//        if(userRepository.findByEmail(user.getEmail()) == null) {

            String password = PasswordEncoder.encode(user.getPassword());
            user.setPassword(password);
            user.setRole("ROLE_USER");
            User newUser = userRepository.save(user);

            StringBuilder stringBuilder = new StringBuilder(baseUrl);

            Payload data = new Payload();
            data.setTo(user.getEmail());
            data.setSubject("You are Register Successfully..");
            data.setBody("<h1>Congratulations " + user.getName() + " you are Register in our Student Managemant System..</h1>");
            data.setMethod("GOOGLE_SMTP");


            HttpEntity<Payload> httpEntity = new HttpEntity<>(data, getthttpHeaders());
            String url = stringBuilder.append(Mail).toString();
            //restTemplate.exchange(url, HttpMethod.POST,httpEntity,Payload.class);

            return newUser;
//        }
//        else
//        {
//            return null;
//        }
    }

    private HttpHeaders getthttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public void removeSessionMessage() {
       HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        session.removeAttribute("msg");
    }
}

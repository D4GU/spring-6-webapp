package guru.springframework.spring6webapp.services;

import org.springframework.stereotype.Service;

@Service("setterGreetingBean")
public class GreetingServiceSetterInjected implements GreetingService{
    @Override
    public String sayGreeting() {
        return "hey I'm setting a Greeting";
    }
}

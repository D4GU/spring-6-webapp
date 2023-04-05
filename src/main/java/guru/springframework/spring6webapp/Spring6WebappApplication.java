package guru.springframework.spring6webapp;

import guru.springframework.spring6webapp.controllers.MyController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Spring6WebappApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Spring6WebappApplication.class, args);

		MyController controller = ctx.getBean(MyController.class);

		System.out.println("In main method!!!");
		System.out.println(controller.sayHello());
	}

}

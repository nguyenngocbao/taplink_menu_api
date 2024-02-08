package taplink.network.menu.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TapLinkMenuApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TapLinkMenuApiApplication.class, args);
	}

}

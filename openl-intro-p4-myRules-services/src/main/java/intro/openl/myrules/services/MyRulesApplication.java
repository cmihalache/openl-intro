package intro.openl.myrules.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import intro.openl.myrules.model.Platform;

@SpringBootApplication
@ImportResource({ "classpath:META-INF/spring/*.xml" })
public class MyRulesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRulesApplication.class, args);
	}

	@Bean
	public Platform platform() {
		return new MyRulesPlatform();
	}

	@Bean
	public OwnerRepository ownerRepository() {
		return new OwnerRepository();
	}

}

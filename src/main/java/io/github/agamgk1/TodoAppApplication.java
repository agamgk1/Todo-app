package io.github.agamgk1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import javax.validation.Validator;

//RepositoryRestConfigurer - konfiguracja do validacji itp
//Bean klasa zarzadzalna przez springa
@EnableAsync
@SpringBootApplication
public class TodoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}
//kazda klasa ktora jest konfiguracja mozna doklejac inne zaleznosci (cos z poza kontekstu springa) w tym celu uzywa sie adnotacje bean
	@Bean
	Validator validator() {
		return new LocalValidatorFactoryBean();
	}


}

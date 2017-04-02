package com.pk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

@SpringBootApplication
@RestController
@ApiIgnore
public class ApiQuickstartApplication {


	@RequestMapping("/")
	RedirectView index() {
		return new RedirectView("swagger-ui.html");
	}


	public static void main(String[] args) {
		SpringApplication.run(ApiQuickstartApplication.class, args);
	}
}

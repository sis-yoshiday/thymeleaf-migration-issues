package com.example;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class ThymeleafMigrationIssuesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafMigrationIssuesApplication.class, args);
	}

	@Controller
	public static class MyController {

		@GetMapping("/th_remove")
		public String thRemove(Model model) {
			model.addAttribute("items", Collections.emptyList());
			return "th_remove";
		}

		@GetMapping("/th_inline_js")
		public String thInlineJavascript(Model model) {
			model.addAttribute("message", "real message");
			return "th_inline_js";
		}
	}
}

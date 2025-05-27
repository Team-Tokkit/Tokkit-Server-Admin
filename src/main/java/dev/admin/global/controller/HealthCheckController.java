package dev.admin.global.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-api")
public class HealthCheckController {

	@GetMapping("/health")
	public String healthCheck() {
		return "OK";
	}
}

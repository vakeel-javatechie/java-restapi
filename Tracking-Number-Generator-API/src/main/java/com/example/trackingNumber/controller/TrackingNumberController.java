package com.example.trackingNumber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.trackingNumber.service.TrackingNumberService;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
public class TrackingNumberController {

	@Autowired
	private TrackingNumberService service;

	@GetMapping("/next-tracking-number")
	public Map<String, Object> getTrackingNumber(@RequestParam String origin_country_id,
			@RequestParam String destination_country_id, @RequestParam double weight,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime created_at,
			@RequestParam String customer_id, @RequestParam String customer_name, @RequestParam String customer_slug) {
		return service.generateTrackingNumber(origin_country_id, destination_country_id, weight, created_at,
				customer_id, customer_name, customer_slug);
	}
}

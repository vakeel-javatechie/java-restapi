package com.example.trackingNumber.service;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TrackingNumberService {

	private static final AtomicLong COUNTER = new AtomicLong();
	private static final Map<String, Boolean> GENERATED_TRACKING_NUMBERS = new ConcurrentHashMap<>();

	public Map<String, Object> generateTrackingNumber(String origin, String destination, double weight,
			OffsetDateTime createdAt, String customerId, String customerName, String customerSlug) {

		String base = origin + destination + Long.toHexString(Double.doubleToLongBits(weight))
				+ customerSlug.toUpperCase();
		String uniquePart = Long.toString(COUNTER.incrementAndGet(), 36).toUpperCase();

		String trackingNumber;
		do {
			trackingNumber = (base + uniquePart + UUID.randomUUID().toString().substring(0, 4))
					.replaceAll("[^A-Z0-9]", "").substring(0, Math.min(16, base.length() + 4));
		} while (GENERATED_TRACKING_NUMBERS.putIfAbsent(trackingNumber, true) != null);

		return Map.of("tracking_number", trackingNumber, "created_at",
				createdAt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
	}
}

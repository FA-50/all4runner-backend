package com.uos.all4runner.common;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	protected UUID id;

	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Instant createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", nullable = false, updatable = false)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Instant updatedAt;
}

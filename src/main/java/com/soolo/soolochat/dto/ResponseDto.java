package com.soolo.soolochat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
	private int statusCode;
	private String message;
	private Object data;

	public ResponseDto(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public static ResponseDto setSuccess(int statusCode, String message, Object data) {
		return new ResponseDto(statusCode, message, data);
	}

	public static ResponseDto setSuccess(int statusCode, String message) {
		return new ResponseDto(statusCode, message);
	}
}
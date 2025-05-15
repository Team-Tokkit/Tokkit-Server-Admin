package dev.admin.global.apiPayload.exception.handler;

import dev.admin.global.apiPayload.code.status.ErrorStatus;

public class TempHandler extends RuntimeException {
	public TempHandler(ErrorStatus errorStatus) {
		super(errorStatus.getMessage());
	}
}

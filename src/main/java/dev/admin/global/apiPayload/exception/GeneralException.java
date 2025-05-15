package dev.admin.global.apiPayload.exception;


import dev.admin.global.apiPayload.code.ErrorReasonDTO;
import dev.admin.global.apiPayload.code.status.ErrorStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private final ErrorStatus errorStatus;

    public ErrorReasonDTO getErrorReason() {
        return this.errorStatus.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.errorStatus.getReasonHttpStatus();
    }
}
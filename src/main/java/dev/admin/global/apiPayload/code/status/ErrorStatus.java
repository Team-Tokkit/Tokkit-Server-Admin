package dev.admin.global.apiPayload.code.status;

import dev.admin.global.apiPayload.code.BaseErrorCode;
import dev.admin.global.apiPayload.code.ErrorReasonDTO;
import org.springframework.http.HttpStatus;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

	// For test
	TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트"),

	// 가장 일반적인 응답
	_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
	_BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
	_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
	_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

	// Notice
	NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTICE404", "공지사항을 찾을 수 없습니다."),

	// Logging
	API_REQUEST_LOG_NOT_FOUND(HttpStatus.NOT_FOUND, "API_REQUEST_LOG404", "API 요청 로그를 찾을 수 없습니다."),
	// Page
	INVALID_PAGE_NUMBER(HttpStatus.BAD_REQUEST, "PAGE400", "요청한 페이지 번호가 유효하지 않습니다."),

	// Date
	INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "DATE400", "날짜 형식이 유효하지 않습니다."),

	// User
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "사용자를 찾을 수 없습니다."),
	MERCHANT_NOT_FOUND(HttpStatus.NOT_FOUND, "MERCHANT404" , "가맹점주를 찾을 수 없습니다." );

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;


	@Override
	public ErrorReasonDTO getReason() {
		return ErrorReasonDTO.builder()
				.message(message)
				.code(code)
				.isSuccess(false)
				.build();
	}


	@Override
	public ErrorReasonDTO getReasonHttpStatus() {
		return ErrorReasonDTO.builder()
				.message(message)
				.code(code)
				.isSuccess(false)
				.httpStatus(httpStatus)
				.build();
	}
	}

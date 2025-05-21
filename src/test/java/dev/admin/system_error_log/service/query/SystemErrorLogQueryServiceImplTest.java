//package dev.admin.system_error_log.service.query;
//
//import dev.admin.system_error_log.dto.request.SystemErrorLogSearchDto;
//import dev.admin.system_error_log.dto.response.SystemErrorLogResponseDto;
//import dev.admin.system_error_log.entity.SystemErrorLog;
//import dev.admin.system_error_log.enums.Severity;
//import dev.admin.system_error_log.repository.SystemErrorLogRepository;
//import dev.admin.global.apiPayload.code.status.ErrorStatus;
//import dev.admin.global.apiPayload.exception.GeneralException;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class SystemErrorLogQueryServiceImplTest {
//
//    @Mock
//    private SystemErrorLogRepository systemErrorLogRepository;
//
//    @InjectMocks
//    private SystemErrorLogQueryServiceImpl queryService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    private SystemErrorLog createMockLog(Long id) {
//        return SystemErrorLog.builder()
//                .id(id)
//                .userId(100L)
//                .endpoint("/api/test")
//                .errorMessage("NullPointerException 발생")
//                .stackTrace("java.lang.NullPointerException\n\tat dev.admin.SomeClass.someMethod(SomeClass.java:42)")
//                .timestamp(LocalDateTime.of(2025, 5, 20, 15, 0))
//                .serverName("admin-server-1")
//                .traceId("trace-abc123")
//                .severity(Severity.ERROR)
//                .build();
//    }
//
//    @Nested
//    class SearchSystemErrorLogs {
//
//        @Test
//        @DisplayName("성공 - 시스템 에러 로그 목록 조회")
//        void searchLogsSuccess() {
//            // given
//            SystemErrorLogSearchDto dto = new SystemErrorLogSearchDto("NullPointer", Severity.ERROR);
//            PageRequest pageable = PageRequest.of(0, 10); // DTO에 페이지 정보 없음
//            List<SystemErrorLog> content = List.of(createMockLog(1L));
//            Page<SystemErrorLog> mockPage = new PageImpl<>(content, pageable, 1);
//
//            when(systemErrorLogRepository.search(any(), any())).thenReturn(mockPage);
//
//            // when
//            var result = queryService.searchSystemErrorLogs(dto, pageable);  // Impl 내 정의 따라 다를 수 있음
//
//            // then
//            assertThat(result).isNotNull();
//            assertThat(result.getContent()).hasSize(1);
//            verify(systemErrorLogRepository).searchSystemErrorLogs(any(), any());
//        }
//    }
//
//    @Nested
//    class GetSystemErrorLogDetail {
//
//        @Test
//        @DisplayName("성공 - 시스템 에러 로그 상세 조회")
//        void getDetailSuccess() {
//            // given
//            SystemErrorLog mockLog = createMockLog(1L);
//            when(systemErrorLogRepository.findById(1L)).thenReturn(Optional.of(mockLog));
//
//            // when
//            SystemErrorLogResponseDto result = queryService.getSystemErrorLogDetail(1L);
//
//            // then
//            assertThat(result.id()).isEqualTo(1L);
//            assertThat(result.endpoint()).isEqualTo("/api/test");
//            assertThat(result.errorMessage()).contains("NullPointerException");
//            assertThat(result.severity()).isEqualTo(Severity.ERROR);
//        }
//
//        @Test
//        @DisplayName("실패 - 존재하지 않는 ID로 상세 조회")
//        void getDetailFail() {
//            // given
//            when(systemErrorLogRepository.findById(999L)).thenReturn(Optional.empty());
//
//            // expect
//            assertThatThrownBy(() -> queryService.getSystemErrorLogDetail(999L))
//                    .isInstanceOf(GeneralException.class)
//                    .hasMessage(ErrorStatus.SYSTEM_ERROR_LOG_NOT_FOUND.getMessage());
//        }
//    }
//}

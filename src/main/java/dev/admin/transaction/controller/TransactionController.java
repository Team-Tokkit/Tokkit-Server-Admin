package dev.admin.transaction.controller;

import dev.admin.global.apiPayload.ApiResponse;
import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.transaction.dto.request.TransactionSearchDto;
import dev.admin.transaction.dto.response.TransactionResponseDto;
import dev.admin.transaction.dto.response.TransactionSimpleResponseDto;
import dev.admin.transaction.service.query.TransactionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionQueryService queryService;

    @GetMapping("/{id}")
    public ApiResponse<TransactionResponseDto> getById(@PathVariable Long id) {
        return ApiResponse.onSuccess(queryService.getById(id));
    }

    @GetMapping
    public ApiResponse<Page<TransactionSimpleResponseDto>> search(
            TransactionSearchDto condition,
            Pageable pageable) {

        return ApiResponse.onSuccess(queryService.search(condition, pageable));
    }
}

package dev.admin.region.controller;

import dev.admin.global.apiPayload.ApiResponse;
import dev.admin.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-api/regions")
public class RegionController {

    private final RegionRepository regionRepository;

    // 시도 목록 조회
    @GetMapping("/sido")
    public ApiResponse<List<String>> getSidoList() {
        List<String> sidoNames = regionRepository.findDistinctBySidoNameNotNull().stream()
                .map(region -> region.getSidoName())
                .distinct()
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(sidoNames);
    }

    // 특정 시도의 시군구 목록 조회
    @GetMapping("/sigungu")
    public ApiResponse<List<String>> getSigunguList(@RequestParam String sidoName) {
        List<String> sigunguNames = regionRepository.findBySidoName(sidoName).stream()
                .map(region -> region.getSigunguName())
                .distinct()
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(sigunguNames);
    }
}

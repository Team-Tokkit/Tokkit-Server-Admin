package dev.admin.voucher.dto.request;

import dev.admin.store.enums.StoreCategory;

public record VoucherSearchRequest(
        StoreCategory storeCategory,
        String searchKeyword,
        String sortByValidDate
) {}

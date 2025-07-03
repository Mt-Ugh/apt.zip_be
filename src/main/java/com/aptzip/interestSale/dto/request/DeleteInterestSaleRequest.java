package com.aptzip.interestSale.dto.request;

import java.util.List;

public record DeleteInterestSaleRequest(
        List<String> saleUuid) {
}

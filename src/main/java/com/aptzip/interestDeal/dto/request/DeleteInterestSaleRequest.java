package com.aptzip.interestDeal.dto.request;

import java.util.List;

public record DeleteInterestSaleRequest(
        List<String> saleUuid) {
}

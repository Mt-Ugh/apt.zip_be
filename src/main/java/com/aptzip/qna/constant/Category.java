package com.aptzip.qna.constant;

public enum Category {
    SALES("매매"),
    RENT("전세/월세"),
    FINANCE("금융"),
    INVESTMENT("투자"),
    REGIONS("지역"),
    MANAGEMENT("관리"),
    ETC("기타");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}


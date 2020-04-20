package com.tnkfactory.rwd.offerer;

public enum MainListItem {

    HEADER_01("Offerwall", true),
    BASIC("Basic"),
    POPUP_BASIC("Popup Basic"),
    EMBED_BASIC("Embed Basic"),
    TEMPLATE("Offerwall Template"),

    HEADER_02("Interstitial AD", true),
    INTERSTITIAL_AD("PPI Interstital Ad")
    ;


    private String value;
    private Boolean isHeader = false;

    MainListItem(String value) {
        this.value = value;
    }

    MainListItem(String value, boolean isHeader) {
        this.value = value;
        this.isHeader = isHeader;
    }

    public String getValue() {
        return value;
    }

    public boolean isHeader() {
        return isHeader;
    }
}

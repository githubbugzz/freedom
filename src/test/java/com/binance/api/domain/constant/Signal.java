package com.binance.api.domain.constant;

public enum Signal {
    LONG("做多"),SHORT("做空"),NOTRADING("没有交易");
    private final String value;

    Signal(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

}

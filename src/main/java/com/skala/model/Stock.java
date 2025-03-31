package com.skala.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    protected String stockName;
    protected int stockPrice;

    @Override
    public String toString() {
        return stockName + ":" + stockPrice;
    }
}
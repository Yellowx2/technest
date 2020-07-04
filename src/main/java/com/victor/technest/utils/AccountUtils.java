package com.victor.technest.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class AccountUtils {

    public static HashMap<String, BigDecimal> createExchangeMap() {
        var map = new HashMap<String, BigDecimal>();
        map.put("EUR-USD", BigDecimal.valueOf(1.12));
        map.put("USD-EUR", BigDecimal.valueOf(0.89));
        return map;
    }
}

package com.yc.busi;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 等额本金（每期还款金额固定，利息越来越少） | 先多后少
 */
public class EPEP {

    public static BigDecimal epep(BigDecimal principal, int totalTerm, BigDecimal rate) {
        BigDecimal interest = BigDecimal.ZERO;
        for (int i = 0; i < totalTerm; i++) {
            BigDecimal subtract = principal.subtract(principal.multiply(BigDecimal.valueOf(i)).divide(BigDecimal.valueOf(totalTerm), 2, RoundingMode.UP));
            interest = interest.add(subtract.multiply(BigDecimal.valueOf(0.095618)));
        }
        return interest;
    }

    public static void main(String[] args) {
        System.out.println(epep(BigDecimal.valueOf(38500), 6, BigDecimal.valueOf(0.095618)));
        /* P*R*(1+R)^N/(1+R)^N */
        BigDecimal multiply = BigDecimal.valueOf(38500).multiply(BigDecimal.valueOf(0.095618)).multiply(BigDecimal.valueOf(Math.pow(1.095618, 6)));
        BigDecimal divide = multiply.divide(BigDecimal.valueOf(Math.pow(1.095618, 6)).subtract(BigDecimal.ONE), 2, RoundingMode.UP);
        System.out.println(divide);
        BigDecimal de = BigDecimal.valueOf(38500).multiply(BigDecimal.valueOf(0.095618));

        BigDecimal p = BigDecimal.ZERO;
        for (int i = 1; i <= 6; i++) {
            p = p.add(de.multiply(BigDecimal.valueOf(Math.pow(1.095618, i))));
            System.out.println(de.multiply(BigDecimal.valueOf(Math.pow(1.095618, i))));
        }
        System.out.println(p);
        System.out.println(divide.multiply(BigDecimal.valueOf(6)).subtract(BigDecimal.valueOf(38500)));
    }
}

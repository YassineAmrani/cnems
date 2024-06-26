package com.cnems.dto;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.Target;

public interface SumExpensesByAccount {

    @Value("#{target.account_id}")
    Long getAccountId();

    @Value("#{target.sum_amount}")
    float getSumAmount();
}

package com.cnems.dto;

import org.springframework.beans.factory.annotation.Value;

public interface SumExpenseByCategory {
    @Value("#{target.category_id}")
    Long getCategoryId();

    @Value("#{target.sum_amount}")
    float getSumAmount();
}

package com.cnems.mocks;

import com.cnems.dto.SumExpenseByCategory;
import com.cnems.dto.SumExpensesByAccount;
import com.cnems.dto.UserSummaryDTO;

import java.util.List;

public class SummaryMock {

    public static SumExpenseByCategory getSumExpensesByCategoryMock() {return new SumExpenseByCategory() {
        @Override
        public Long getCategoryId() {
            return 0L;
        }

        @Override
        public float getSumAmount() {
            return 0;
        }
    };
    }

    public static SumExpensesByAccount getSumExpensesByAccountMock() {return new SumExpensesByAccount() {
        @Override
        public Long getAccountId() {
            return 0L;
        }

        @Override
        public float getSumAmount() {
            return 0;
        }
    };
    }

    public static UserSummaryDTO getUserSummaryDTO() {return new UserSummaryDTO(0f, 0f, List.of(getSumExpensesByAccountMock()), List.of(getSumExpensesByCategoryMock()));}
}

package com.cnems.mocks;

import com.cnems.entities.Account;

import java.util.Date;

public class AccountMock {

    public static Account getAccountMock() {return new Account(0L, "Test", 0f,0L,new Date());}
}

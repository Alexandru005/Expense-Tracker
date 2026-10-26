package com.alexstefan.expense_tracker.constants;

public final class ApiConstants {
    public static final String API_BASE = "/api";
    public static final String USERS_BASE = API_BASE + "/users";
    public static final String TRANSACTIONS_BASE = API_BASE + "/transactions";
    public static final String AUTH_BASE = API_BASE + "/auth";


    public static final String PATH_ID = "/{id}";
    public static final String PATH_EMAIL = "/{email}";
    public static final String PATH_LOGIN = AUTH_BASE + "/login";
    public static final String PATH_REGISTER = AUTH_BASE + "/register";


    public static final String USER_BY_ID = USERS_BASE + PATH_ID;
    public static final String USER_BY_EMAIL = USERS_BASE + PATH_EMAIL;
    public static final String TRANSACTION_BY_ID = TRANSACTIONS_BASE + PATH_ID;
    public static final String TRANSACTION_LIST =  "/list";
    public static final String SAVINGS_LIST = "/savings/list";
    public static final String EXPENSE_CHART = "/chart/expenses";
    public static final String YEARLY_SAVINGS_CHART = "/chart/savings/yearly";
}

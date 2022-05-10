package com.example.orderservice.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Constant {
    public static final Gson gson = new GsonBuilder().create();
    public static final String SUCCESS_CODE = "0";
    public static final String ERROR_CODE = "-1";
}

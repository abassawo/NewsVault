package com.abasscodes.newsy.data.rest;

import com.abasscodes.newsy.rules.MockServerRule;

public class TestRestClient {
    public static NytApi getRestApi(MockServerRule mockServer) {
        return RetrofitClient.getTestApi(mockServer.getServerUrl());
    }
}

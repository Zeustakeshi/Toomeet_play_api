package com.toomeet.toomeet_play_api.domain.context;

import jakarta.servlet.http.HttpServletRequest;

public class RequestContext {
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    public static HttpServletRequest getRequest() {
        return requestHolder.get();
    }

    public static void setRequest(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static void clean() {
        requestHolder.remove();
    }
}

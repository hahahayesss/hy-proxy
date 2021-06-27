package com.r00t.hyproxy;

import com.r00t.hyproxy.anno.ProxyService;

@ProxyService(handler = DemoHandler.class)
public interface DemoRequests {

    Object getList(String url, String token);

    Object getPage(String url, String token, int page);

    Object getOne(String url, String token, String id);
}

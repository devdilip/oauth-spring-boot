package com.authentication.oauth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.authentication.oauth.common.constants.RouteConstant.APP_BASE_ROUTE;
import static com.authentication.oauth.common.constants.RouteConstant.APP_VERSION;

@RestController
@Slf4j
@RequestMapping(APP_VERSION + APP_BASE_ROUTE)
public class AppController {

}

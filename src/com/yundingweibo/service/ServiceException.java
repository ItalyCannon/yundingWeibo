package com.yundingweibo.service;

/**
 * @author 杜奕明
 * @date 2019/2/18 21:56
 */
public class ServiceException extends RuntimeException {
    ServiceException(String message) {
        super(message);
    }
}

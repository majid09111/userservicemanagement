package com.urbanclap.usermanagement.utils;

public interface IResponse<T> {

    T exec() throws Exception;

}

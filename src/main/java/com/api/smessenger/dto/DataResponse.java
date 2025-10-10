package com.api.smessenger.dto;

public class DataResponse<T> extends BaseResponse {
  private T data;

  public DataResponse(String msg, T data) {
    super(msg);
    this.data = data;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

}

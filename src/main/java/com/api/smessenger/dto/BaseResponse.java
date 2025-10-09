package com.api.smessenger.dto;

public class BaseResponse {
  private String msg;

  public BaseResponse() {
  }

  public BaseResponse(String msg) {
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}

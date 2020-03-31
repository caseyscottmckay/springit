package com.springit.springit_backend.exception;

public class PostNotFoundException extends RuntimeException {

  public PostNotFoundException(String messge){
    super(messge);
  }
}

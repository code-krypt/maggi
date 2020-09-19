package com.drykode.maggi.container.engines.drools.models;

public class SessionOutputCollector {
  private String result;

  public void add(String result) {
    this.result = result;
  }

  public String getResult() {
    return result;
  }
}

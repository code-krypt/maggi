package com.drykode.maggi.container.drools.sample;

import com.drykode.maggi.container.engines.drools.models.SessionInput;
import com.drykode.maggi.container.engines.drools.models.SessionOutputCollector;

public class DroolsSampleCodeGenerator {

  public static void main(String[] args) {

    SessionInput sessionInput = SessionInput.builder().input("abc").build();
    SessionOutputCollector soc = new SessionOutputCollector();

    droolsCode(sessionInput, soc);
  }

  private static void droolsCode(SessionInput si, SessionOutputCollector soc) {
    String result = si.getInput();
    soc.add(result);
  }
}

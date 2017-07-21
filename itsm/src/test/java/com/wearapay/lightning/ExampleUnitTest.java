package com.wearapay.lightning;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
  @Test public void addition_isCorrect() throws Exception {
    //assertEquals(4, 2 + 2);
    String s1 = "20170719";
    String s2 = "2017071211";
    int i = s1.compareTo(s2);
    System.out.println(i);
  }
}
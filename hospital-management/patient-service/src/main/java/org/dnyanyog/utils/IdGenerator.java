package org.dnyanyog.utils;

import java.security.SecureRandom;

public class IdGenerator {
  private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final SecureRandom random = new SecureRandom();

  public static String generatePatientId() {
    StringBuilder builder = new StringBuilder("PAT");
    for (int i = 0; i < 8; i++) {
      int character = random.nextInt(ALPHA_NUMERIC_STRING.length());
      builder.append(ALPHA_NUMERIC_STRING.charAt(character));
    }
    return builder.toString();
  }
}

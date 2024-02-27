package model;

import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class User {
  private String username;
  private String password;
  public User() {}
  public User(String username) { this.username = username; }
  public String getUsername() { return this.username; }
  public void setUsername(String username) { this.username = username; }
  public String getPassword() { return this.password; }
  public void setPassword(String password) { this.password = password; }

  public static String generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salttmp = new byte[16];
    random.nextBytes(salttmp);
    return Base64.getEncoder().encodeToString(salttmp);
  }

  public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(Base64.getDecoder().decode(salt));
      byte[] hashedPassword = md.digest(password.getBytes());

      String hash = Base64.getEncoder().encodeToString(hashedPassword);
      return salt + hash;
    } catch (NoSuchAlgorithmException e) {
      throw e;
    }
  }

  public boolean verifyPassword(String password) throws NoSuchAlgorithmException {
    String salt = this.password.substring(0,24);
    try {
      String verify = hashPassword(password, salt);
      return verify.equals(this.password);
    } catch (NoSuchAlgorithmException e) {
      throw e;
    }
  }
}

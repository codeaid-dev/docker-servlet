package model;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable {
  private String username;
  private String password;
  private static final String ALG = "SHA-256";
	/* ソルト値 */
	private static final String SALT = "uMoJL3h90SenH7:r";
  public User() {}
  public User(String username) { this.username = username; }
  public String getUsername() { return this.username; }
  public void setUsername(String username) { this.username = username; }
  public String getPassword() { return this.password; }
  public void setPassword(String password, boolean flag) {
    if (flag) {
      this.password = toHash(password);
    } else {
      this.password = password;
    }
  }
  public boolean verifyPassword(String password, boolean flag) {
    if (flag) {
      password = toHash(password);
    }
    return password.equals(this.password);
  }
  public String toHash(String data) {
    String target = data + SALT;
    String hash = null;
    try {
      MessageDigest md = MessageDigest.getInstance(ALG);
      md.update(target.getBytes());
      byte[] digest = md.digest();
      hash = new String(digest, "UTF-8");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return hash;
  }
  public static String escape(String str) {
    if (str != null) {
      str = str.replaceAll("&","&amp;");
      str = str.replaceAll("<","&lt;");
      str = str.replaceAll(">","&gt;");
      str = str.replaceAll("'","&#39;");
      str = str.replaceAll("\"","&quot;");
      return str;
    }
    return null;
  }
}

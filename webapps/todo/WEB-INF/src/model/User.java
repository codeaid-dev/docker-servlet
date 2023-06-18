package model;
import java.io.Serializable;
import java.util.HashMap;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable {
  private String username;
  private String password;
  private String editId;
  private HashMap<String, String> tasks = new HashMap<String, String>();
  private static final String ALG = "SHA-256";
	/* ソルト値 */
	private static final String SALT = "uMoJL3h90SenH7:r";
  public User() {}
  public User(String username) { this.username = username; }
  public String getEditId() { return this.editId; }
  public void setEditId(String id) { this.editId = id; }
  public String getTask(String id) { return this.tasks.get(id); }
  public String getTask() { return this.tasks.get(this.editId); }
  public HashMap<String, String> getTasks() { return this.tasks; }
  public void setTask(String id, String task) {
    this.editId = id;
    tasks.put(id, task);
  }
  public void setTask(String task) { tasks.put(this.editId, task); }
  public void removeTask(String id) { this.tasks.remove(id); }
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
}

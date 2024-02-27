package model;
import java.io.Serializable;

public class Quiz implements Serializable {
  private String id;
  private String question;
  private String answer;
  private boolean update_done = false;
  private String info = "";
  public Quiz() {}
  public Quiz(String question,String answer) {
    this.question = question;
    this.answer = answer;
  }
  public Quiz(String id, String question,String answer) {
    this.id = id;
    this.question = question;
    this.answer = answer;
  }
  public String getID() { return this.id; }
  public void setID(String id) { this.id = id; }
  public String getQuestion() { return this.question; }
  public void setQuestion(String question) { this.question = question; }
  public String getAnswer() { return this.answer; }
  public void setAnswer(String answer) { this.answer = answer; }
  public boolean getUpdateDone() { return this.update_done; }
  public void setUpdateDone(boolean flag) { this.update_done = flag; }
  public String getInfo() { return this.info; }
  public void setInfo(String info) { this.info = info; }
  public void resetInfo() { this.info = ""; }

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

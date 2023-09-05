package model;
import java.io.Serializable;

public class Post implements Serializable {
  private int id;
  private String created_datetime;
  private String updated_datetime;
  private String created_date;
  private String updated_date;
  private String title;
  private String article;
  public Post() {}
  public int getId() { return this.id; }
  public void setId(int id) { this.id = id; }
  public String getCreatedDatetime() { return this.created_datetime; }
  public void setCreatedDatetime(String created_datetime) { this.created_datetime = created_datetime; }
  public String getCreatedDate() { return this.created_date; }
  public void setCreatedDate(String created_date) { this.created_date = created_date; }
  public String getUpdatedDatetime() { return this.updated_datetime; }
  public void setUpdatedDatetime(String updated_datetime) { this.updated_datetime = updated_datetime; }
  public String getUpdatedDate() { return this.updated_date; }
  public void setUpdatedDate(String updated_date) { this.updated_date = updated_date; }
  public String getTitle() { return this.title; }
  public void setTitle(String title) { this.title = title; }
  public String getArticle() { return this.article; }
  public void setArticle(String article) { this.article = article; }

}

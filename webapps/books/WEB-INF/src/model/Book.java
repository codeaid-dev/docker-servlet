package model;
import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {
  private String isbn;
  private String name;
  private String price;
  private String page;
  private String date;
  private String delete;
  private boolean update_done = false;
  private ArrayList<String> info = new ArrayList<String>();
  public Book() {}
  public Book(String isbn,String name,String price,String page,String date) {
    this.isbn = isbn;
    this.name = name;
    this.price = price;
    this.page = page;
    this.date = date;
  }
  public String getISBN() { return this.isbn; }
  public void setISBN(String isbn) { this.isbn = isbn; }
  public String getName() { return this.name; }
  public void setName(String name) { this.name = name; }
  public String getPrice() { return this.price; }
  public void setPrice(String price) { this.price = price; }
  public String getPage() { return this.page; }
  public void setPage(String page) { this.page = page; }
  public String getDate() { return this.date; }
  public void setDate(String date) { this.date = date; }
  public String getDelete() { return this.delete; }
  public void setDelete(String delete) { this.delete = delete; }
  public boolean getUpdateDone() { return this.update_done; }
  public void setUpdateDone(boolean flag) { this.update_done = flag; }
  public ArrayList<String> getInfo() { return this.info; }
  public void setInfo(String inf) { this.info.add(inf); }
  public void resetInfo() { this.info.clear(); }

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

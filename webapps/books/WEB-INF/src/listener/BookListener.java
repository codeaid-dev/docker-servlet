package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import model.DBAccess;

@WebListener
public class BookListener implements ServletContextListener {
  public void contextInitialized(ServletContextEvent sce) {
    DBAccess db = new DBAccess(sce.getServletContext());
    db.create();
    sce.getServletContext().log("DB Table create done.");
  }
  public void contextDestroyed(ServletContextEvent sce) {}
}

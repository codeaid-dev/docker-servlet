import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import model.DBAccess;

@WebListener
public class InitialEvent implements ServletContextListener {
  public void contextInitialized(ServletContextEvent sce) {
    try {
      DBAccess.create();
    } catch (Exception e) {
      sce.getServletContext().log(e.getMessage());
    }
  }
  public void contextDestroyed(ServletContextEvent sce) {}
}

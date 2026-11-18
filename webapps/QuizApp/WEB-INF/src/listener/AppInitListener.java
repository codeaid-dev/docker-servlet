package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import util.DBUtil;

@WebListener
public class AppInitListener
        implements ServletContextListener {
  public void contextInitialized(
          ServletContextEvent event) {
    try {
      DBUtil.init(event.getServletContext());
    } catch (Exception e) {
      event.getServletContext().log("データベースの初期化に失敗しました",e);
      throw new IllegalStateException("QuizAppの初期化に失敗しました",e);
    }
  }
}

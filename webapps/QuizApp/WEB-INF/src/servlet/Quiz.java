package servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import dao.QuizDAO;
import dao.WrongAnswerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.QuizQuestion;

@WebServlet("/quiz")
public class Quiz extends HttpServlet {
  private static final String SESSION_QUESTIONS = "quizQuestions";
  private static final String SESSION_INDEX = "quizIndex";
  private static final String SESSION_SCORE = "quizScore";
  private static final String SESSION_PHASE = "quizPhase";
  private static final String PHASE_QUESTION = "QUESTION";
  private static final String PHASE_FEEDBACK = "FEEDBACK";
  private static final String PHASE_RESULT = "RESULT";

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    // 新しいクイズを開始する場合
    if ("1".equals(request.getParameter("new"))) {
      if (session != null) {
        session.invalidate();
      }
      request.getRequestDispatcher(
          "/WEB-INF/jsp/quiz-start.jsp")
          .forward(request, response);
      return;
    }
    if (session == null ||
          session.getAttribute(SESSION_PHASE) == null) {
      request.getRequestDispatcher(
          "/WEB-INF/jsp/quiz-start.jsp")
          .forward(request, response);
      return;
    }
    String phase = (String) session.getAttribute(SESSION_PHASE);
    if (PHASE_QUESTION.equals(phase)) {
      showQuestion(session, request, response);
    } else if (PHASE_FEEDBACK.equals(phase)) {
      showFeedback(session, request, response);
    } else if (PHASE_RESULT.equals(phase)) {
      showResult(session, request, response);
    } else {
      session.invalidate();
      response.sendRedirect(
          request.getContextPath() + "/quiz"
      );
    }
  }
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String action = request.getParameter("action");
    if ("start".equals(action)) {
      startQuiz(request, response);
    } else if ("answer".equals(action)) {
      submitAnswer(request, response);
    } else if ("next".equals(action)) {
      nextQuestion(request, response);
    } else {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
  }
  // クイズ開始
  private void startQuiz(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String countText = request.getParameter("count");
    int count;
    try {
      count = Integer.parseInt(countText);
      if (count < 1 || count > 100) {
        throw new NumberFormatException();
      }
    } catch (NumberFormatException e) {
      request.setAttribute(
          "errorMessage",
          "出題数は1～100の整数で指定してください。"
      );
      request.getRequestDispatcher(
          "/WEB-INF/jsp/quiz-start.jsp")
          .forward(request, response);
      return;
    }
    try {
      List<QuizQuestion> questions =
          new QuizDAO().findAll();
      if (questions.isEmpty()) {
        request.setAttribute(
            "errorMessage",
            "出題できる問題が登録されていません。"
        );
        request.getRequestDispatcher(
            "/WEB-INF/jsp/quiz-start.jsp")
            .forward(request, response);
        return;
      }
      Collections.shuffle(questions);
      if (count > questions.size()) {
        count = questions.size();
      }
      List<QuizQuestion> quizQuestions =
          questions.subList(0, count);
      HttpSession session = request.getSession();
      session.setAttribute(
          SESSION_QUESTIONS, quizQuestions
      );
      session.setAttribute(SESSION_INDEX, 0);
      session.setAttribute(SESSION_SCORE, 0);
      session.setAttribute(SESSION_PHASE, PHASE_QUESTION);
      response.sendRedirect(
          request.getContextPath() + "/quiz"
      );
    } catch (Exception e) {
      throw new ServletException(
          "クイズの開始に失敗しました。", e
      );
    }
  }
  // 問題表示
  @SuppressWarnings("unchecked")
  private void showQuestion(HttpSession session, HttpServletRequest request,
                            HttpServletResponse response)
          throws ServletException, IOException {
    List<QuizQuestion> questions =
        (List<QuizQuestion>) session.getAttribute(
            SESSION_QUESTIONS
        );
    int index = (Integer) session.getAttribute(SESSION_INDEX);
    if (questions == null || index >= questions.size()) {
      session.setAttribute(SESSION_PHASE, PHASE_RESULT);
      response.sendRedirect(
          request.getContextPath() + "/quiz"
      );
      return;
    }
    QuizQuestion question = questions.get(index);
    request.setAttribute("question", question);
    request.setAttribute("questionNumber", index + 1);
    request.setAttribute("totalQuestions", questions.size());
    request.getRequestDispatcher(
        "/WEB-INF/jsp/quiz-question.jsp")
        .forward(request, response);
  }
  // 回答・採点
  @SuppressWarnings("unchecked")
  private void submitAnswer(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session == null ||
            !PHASE_QUESTION.equals(
                session.getAttribute(SESSION_PHASE))) {
      response.sendRedirect(
          request.getContextPath() + "/quiz"
      );
      return;
    }
    List<QuizQuestion> questions =
        (List<QuizQuestion>) session.getAttribute(
            SESSION_QUESTIONS
        );
    int index = (Integer) session.getAttribute(SESSION_INDEX);
    if (questions == null || index >= questions.size()) {
      response.sendRedirect(
          request.getContextPath() + "/quiz"
      );
      return;
    }
    QuizQuestion question = questions.get(index);
    int questionId;
    int selectedAnswer;
    try {
      questionId = Integer.parseInt(
          request.getParameter("questionId")
      );
      selectedAnswer = Integer.parseInt(
          request.getParameter("answer")
      );
    } catch (NumberFormatException e) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    if (questionId != question.getId() ||
            selectedAnswer < 1 ||
            selectedAnswer > 4) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    boolean correct =
        selectedAnswer == question.getCorrectAnswer();
    int score = (Integer) session.getAttribute(SESSION_SCORE);
    if (correct) {
      score++;
      session.setAttribute(SESSION_SCORE, score);
    } else {
      try {
        new WrongAnswerDAO().recordWrong(question.getId());
      } catch (Exception e) {
        throw new ServletException(
            "間違い問題の記録に失敗しました。", e
        );
      }
    }
    session.setAttribute("lastCorrect", correct);
    session.setAttribute("lastSelected", selectedAnswer);
    session.setAttribute(
        "lastCorrectAnswer", question.getCorrectAnswer()
    );
    session.setAttribute(
        "lastExplanation", question.getExplanation()
    );
    session.setAttribute("lastQuestion", question);
    session.setAttribute(SESSION_PHASE, PHASE_FEEDBACK);
    response.sendRedirect(
        request.getContextPath() + "/quiz"
    );
  }
  // 採点結果表示
  private void showFeedback(HttpSession session, HttpServletRequest request,
                            HttpServletResponse response)
          throws ServletException, IOException {
    request.setAttribute(
        "correct", session.getAttribute("lastCorrect")
    );
    request.setAttribute(
        "selectedAnswer", session.getAttribute("lastSelected")
    );
    request.setAttribute(
        "correctAnswer", session.getAttribute("lastCorrectAnswer")
    );
    request.setAttribute(
        "explanation", session.getAttribute("lastExplanation")
    );
    request.setAttribute(
        "question", session.getAttribute("lastQuestion")
    );
    request.getRequestDispatcher(
        "/WEB-INF/jsp/quiz-feedback.jsp")
        .forward(request, response);
  }
  // 次の問題へ
  @SuppressWarnings("unchecked")
  private void nextQuestion(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
      HttpSession session = request.getSession(false);
      if (session == null ||
              !PHASE_FEEDBACK.equals(
                  session.getAttribute(SESSION_PHASE))) {
          response.sendRedirect(
              request.getContextPath() + "/quiz"
          );
          return;
      }
      List<QuizQuestion> questions =
          (List<QuizQuestion>) session.getAttribute(
              SESSION_QUESTIONS
          );
      int index = (Integer) session.getAttribute(SESSION_INDEX);
      index++;
      session.setAttribute(SESSION_INDEX, index);
      if (index >= questions.size()) {
          session.setAttribute(SESSION_PHASE, PHASE_RESULT);
      } else {
          session.setAttribute(SESSION_PHASE, PHASE_QUESTION);
      }
      response.sendRedirect(
          request.getContextPath() + "/quiz"
      );
  }
  // 最終結果表示
  @SuppressWarnings("unchecked")
  private void showResult(HttpSession session, HttpServletRequest request,
                          HttpServletResponse response)
          throws ServletException, IOException {
    List<QuizQuestion> questions =
        (List<QuizQuestion>) session.getAttribute(
            SESSION_QUESTIONS
        );
    int score = (Integer) session.getAttribute(SESSION_SCORE);
    int total = questions.size();
    double percentage = total == 0 ? 0 : score * 100.0 / total;
    request.setAttribute("score", score);
    request.setAttribute("total", total);
    request.setAttribute("percentage", percentage);
    request.getRequestDispatcher(
        "/WEB-INF/jsp/quiz-result.jsp")
        .forward(request, response);
  }
}

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>クイズ出題</title>
  <link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <jsp:include page="/WEB-INF/jsp/header.jsp"/>
  <h1>4択クイズ</h1>
  <p>
    第 <c:out value="${questionNumber}" /> 問 /
    全 <c:out value="${totalQuestions}" /> 問
  </p>
  <h2>
    <c:out value="${question.question}" />
  </h2>
  <form action="${pageContext.request.contextPath}/quiz"
        method="post">
    <input type="hidden" name="action" value="answer">
    <input type="hidden"
            name="questionId"
            value="<c:out value='${question.id}'/>">
    <p>
      <label>
        <input type="radio" name="answer" value="1" required>
        <c:out value="${question.choice1}" />
      </label>
    </p>
    <p>
      <label>
        <input type="radio" name="answer" value="2">
        <c:out value="${question.choice2}" />
      </label>
    </p>
    <p>
      <label>
        <input type="radio" name="answer" value="3">
        <c:out value="${question.choice3}" />
      </label>
    </p>
    <p>
      <label>
        <input type="radio" name="answer" value="4">
        <c:out value="${question.choice4}" />
      </label>
    </p>
    <button type="submit">回答する</button>
  </form>
  <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>

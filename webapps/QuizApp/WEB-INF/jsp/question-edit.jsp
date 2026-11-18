<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>問題編集</title>
</head>
<body>
<h1>問題編集</h1>
<c:if test="${not empty errorMessage}">
    <p style="color:red">
        <c:out value="${errorMessage}"/>
    </p>
</c:if>
<form action="${pageContext.request.contextPath}/questions/edit"
      method="post">
  <input type="hidden"
          name="id"
          value="${question.id}">
  <p>カテゴリー:<br>
    <select name="categoryId" required>
      <c:forEach var="category" items="${categories}">
        <option value="${category.id}"
          <c:if test="${formCategoryId eq category.id}">
              selected
          </c:if>>
          <c:out value="${category.name}"/>
        </option>
      </c:forEach>
    </select>
  </p>
  <p>問題文:<br>
    <textarea name="question"
              maxlength="1000"
              rows="4"
              cols="30"
              required><c:out value="${formQuestion}"/></textarea>
  </p>
  <p><label>選択肢1:<br>
    <input type="text"
           name="choice1"
           maxlength="300"
           value="<c:out value='${formChoice1}'/>"
           required>
  </label></p>
  <p><label>選択肢2:<br>
    <input type="text"
           name="choice2"
           maxlength="300"
           value="<c:out value='${formChoice2}'/>"
           required>
  </label></p>
  <p><label>選択肢3:<br>
    <input type="text"
           name="choice3"
           maxlength="300"
           value="<c:out value='${formChoice3}'/>"
           required>
  </label></p>
  <p><label>選択肢4:<br>
    <input type="text"
           name="choice4"
           maxlength="300"
           value="<c:out value='${formChoice4}'/>"
           required>
  </label></p>
  <p>正解番号:
    <select name="correctAnswer" required>
      <c:forEach var="i" begin="1" end="4">
        <option value="${i}"
          <c:if test="${formCorrectAnswer eq i}">
              selected
          </c:if>>
          選択肢${i}
        </option>
      </c:forEach>
    </select>
  </p>
  <p>解説:<br>
    <textarea name="explanation"
              maxlength="2000"
              rows="4"><c:out value="${formExplanation}"/></textarea>
  </p>
  <p>
    <button type="submit">更新</button>
  </p>
</form>
<p>
  <a href="${pageContext.request.contextPath}/questions">
      問題一覧に戻る
  </a>
</p>
</body>
</html>

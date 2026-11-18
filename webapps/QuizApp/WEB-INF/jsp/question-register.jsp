<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>問題登録</title>
</head>
<body>
<h1>問題登録</h1>
<c:if test="${not empty errorMessage}">
  <p style="color:red">
    <c:out value="${errorMessage}"/>
  </p>
</c:if>
<form action="${pageContext.request.contextPath}/questions/register"
      method="post">
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
  <p><label>選択肢1:
    <input type="text"
            name="choice1"
            maxlength="300"
            value="<c:out value='${formChoice1}'/>"
            required>
  </label></p>
  <p><label>選択肢2:
    <input type="text"
            name="choice2"
            maxlength="300"
            value="<c:out value='${formChoice2}'/>"
            required>
  </label></p>
  <p><label>選択肢3:
    <input type="text"
            name="choice3"
            maxlength="300"
            value="<c:out value='${formChoice3}'/>"
            required>
  </label></p>
  <p><label>選択肢4:
    <input type="text"
            name="choice4"
            maxlength="300"
            value="<c:out value='${formChoice4}'/>"
            required>
  </label></p>
  <p>正解番号:
    <select name="correctAnswer" required>
      <option value="1"
        <c:if test="${formCorrectAnswer eq '1'}">
            selected
        </c:if>>
        選択肢1
      </option>
      <option value="2"
        <c:if test="${formCorrectAnswer eq '2'}">
            selected
        </c:if>>
        選択肢2
      </option>
      <option value="3"
        <c:if test="${formCorrectAnswer eq '3'}">
            selected
        </c:if>>
        選択肢3
      </option>
      <option value="4"
        <c:if test="${formCorrectAnswer eq '4'}">
            selected
        </c:if>>
        選択肢4
      </option>
    </select>
  </p>
  <p>解説:<br>
    <textarea name="explanation"
              maxlength="2000"
              rows="4"
              cols="30"><c:out value="${formExplanation}"/></textarea>
  </p>
  <p>
    <button type="submit">登録</button>
  </p>
</form>
<p>
  <a href="${pageContext.request.contextPath}/questions">
      問題一覧に戻る
  </a>
</p>
</body>
</html>

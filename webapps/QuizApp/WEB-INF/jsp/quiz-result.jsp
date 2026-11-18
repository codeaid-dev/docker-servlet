<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>クイズ結果</title>
</head>
<body>
<h1>クイズ終了</h1>
<h2>最終結果</h2>
<p>
  出題数：
  <c:out value="${total}" /> 問
</p>
<p>
  正解数：
  <c:out value="${score}" /> 問
</p>
<p>
  正答率：
  <c:out value="${percentage}">
  </c:out> %
</p>
<p>
  <a href="${pageContext.request.contextPath}/quiz?new=1">
      もう一度クイズをする
  </a>
</p>
<p>
  <a href="${pageContext.request.contextPath}/questions">
      問題一覧に戻る
  </a>
</p>
</body>
</html>

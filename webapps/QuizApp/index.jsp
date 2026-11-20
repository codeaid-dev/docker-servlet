<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>4択クイズ学習システム</title>
  <link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <jsp:include page="/WEB-INF/jsp/header.jsp"/>
  <main>
    <div class="menu">
      <section class="menu-item">
        <h2>問題一覧・管理</h2>
        <p>
            登録されている問題の確認や、
            新しい問題の登録・編集・削除を行います。
        </p>
        <a href="${pageContext.request.contextPath}/questions">
            問題一覧
        </a>
      </section>
      <section class="menu-item">
        <h2>問題登録</h2>
        <p>
            新しい問題の登録を行います。
        </p>
        <a href="${pageContext.request.contextPath}/questions/register">
            問題登録
        </a>
      </section>
      <section class="menu-item">
        <h2>4択クイズ</h2>
        <p>
            登録されている問題に挑戦して、
            正解数を確認しましょう。
        </p>
        <a href="${pageContext.request.contextPath}/quiz">
            クイズ開始
        </a>
      </section>
      <!-- <section class="menu-item">
        <h2>間違い問題の復習</h2>
        <p>
            過去に間違えた問題を確認して、
            もう一度解いてみましょう。
        </p>
        <a href="${pageContext.request.contextPath}/wrong-answers">
            復習する
        </a>
      </section> -->
    </div>
  </main>
  <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>

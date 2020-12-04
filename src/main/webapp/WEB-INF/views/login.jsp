<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

    <title>Login</title>
    <link href="../../css/style.css" type="text/css" rel="stylesheet">

</head>
<body>
<header>
    <section class="header_section">
        <a href="list">
            <section class="logo_section">
                BOOK STORE
            </section>
        </a>
        <section class="info_section">
            <div class="search_div">
                <form action="searchResult" method="get">
                    <input type="text" class="search_bar" name="result">
                    <input class="btn-primary" value="검색" type="submit">
                </form>
            </div>
        </section>
    </section>
</header>
<div class="wrap">
    <div class="form-wrap">
        <div class="button-wrap">
            <div id="btn"></div>
            <button type="button" class="togglebtn" onclick="login()">LOG IN</button>
        </div>
        <form id="login" action="login" method="post" class="input-group">
            <input type="text" name="userId" class="input-field" placeholder="User name" required>
            <input type="password" name="password" class="input-field" placeholder="Enter Password" required>

            <button type="submit" class="submit">Login</button>
            <button type="button" class="submit" onclick="location.href = 'register'">회원가입</button>
            <c:if test="${loginCheck == 1}">
                <script type="text/javascript">
                    alert("비밀번호를 확인하세요.")
                </script>
            </c:if>
            <c:if test="${loginCheck == 2}">
                <script type="text/javascript">
                    alert("아이디를 확인하세요.")
                </script>
            </c:if>
        </form>
    </div>
</div>
</body>
</html>
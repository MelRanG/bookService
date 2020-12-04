<%@ page import="java.net.URLEncoder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <script type="text/javascript">
        function formChange(obj) {
            obj.submit();
        }
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="../../css/style.css" type="text/css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Acme&family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <title>도서 목록</title>
</head>
<body>
<!---------------------------------------------------------------------------------------------------------------->
<!--header-->
<!---------------------------------------------------------------------------------------------------------------->
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
            <div class="session_div">
                <c:choose>
                    <c:when test="${userId == null}">
                        <button type="button" class="btn-primary" onclick="location.href = 'login'">로그인</button>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="btn-primary" onclick="location.href = 'logout'">로그아웃</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>
    </section>
</header>
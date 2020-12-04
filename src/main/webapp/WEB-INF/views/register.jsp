<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>회원가입 화면</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="../../css/style.css" type="text/css" rel="stylesheet">
    <style>
        #wrap{
            margin-top: 100px;
            width:530px;
            margin-left:auto;
            margin-right:auto;
            text-align:center;
        }
        table{
            margin-left: 90px;
            border:3px solid skyblue;
        }
        td{
            border:1px solid skyblue;
            background-color:skyblue;
        }
    </style>
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
<!-- 왼쪽, 오른쪽 바깥여백을 auto로 주면 중앙정렬된다.  -->
<div id="wrap">
    <form method="post" action="register" name="userInfo">
        <table>
            <tr>
                <td>아이디</td>
                <td>
                    <input id="userId" type="text" name="userId" maxlength="20">
                    <div id="idCheck"></div>
                </td>
            </tr>
            <tr>
                <td>비밀번호</td>
                <td>
                    <input id = "pw" type="password" name="password" maxlength="15">
                    <div id = "pwCheckText"></div>
                </td>
            </tr>
            <tr>
                <td>비밀번호 확인</td>
                <td>
                    <input id = "pwCheck" type="password"  maxlength="15">
                </td>
            </tr>
            <tr>
                <td>이름</td>
                <td>
                    <input type="text" name="name" maxlength="40">
                </td>
            </tr>
            <tr>
                <td>성별</td>
                <td>
                    <input type="radio" name="sex" value="남" checked>남
                    <input type="radio" name="sex" value="여" checked>여
                </td>
            </tr>
            <tr>
                <td>생일</td>
                <td>
                    <input type="text" name="birth" maxlength="4" placeholder="년(4자)" size="6" >
                    <select name="birth">
                        <option value="">월</option>
                        <option value="01" >1</option>
                        <option value="02" >2</option>
                        <option value="03" >3</option>
                        <option value="04" >4</option>
                        <option value="05" >5</option>
                        <option value="06" >6</option>
                        <option value="07" >7</option>
                        <option value="08" >8</option>
                        <option value="09" >9</option>
                        <option value="10" >10</option>
                        <option value="11" >11</option>
                        <option value="12" >12</option>
                    </select>
                    <input type="text" name="birth_dd" maxlength="2" placeholder="일" size="4" >
                </td>
            </tr>
            <tr>
                <td>선호 장르</td>
                <td>
                    <select name="bookGenre">
                        <option value="인문">인문</option>
                        <option value="소설">소설</option>
                        <option value="자기계발">자기계발</option>
                        <option value="건강">건강</option>
                        <option value="여행">여행</option>
                        <option value="과학">과학</option>
                        <option value="교육">교육</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>최근에 읽은 도서장르</td>
                <td>
                    <select name="readRecently">
                        <option value="인문">인문</option>
                        <option value="소설">소설</option>
                        <option value="자기계발">자기계발</option>
                        <option value="건강">건강</option>
                        <option value="여행">여행</option>
                        <option value="과학">과학</option>
                        <option value="교육">교육</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>직업</td>
                <td>
                    <select name="job">
                        <option value="학생">학생</option>
                        <option value="관리직">관리직</option>
                        <option value="전문직">전문직</option>
                        <option value="사무직">사무직</option>
                        <option value="서비스직">서비스직</option>
                        <option value="판매직">판매직</option>
                        <option value="단순노무직">단순노무직</option>
                    </select>
                </td>
            </tr>
        </table>
        <br>
        <input id="register" type="submit" value="가입"/>  <a href="list"><input type="button" value="취소"></a>
    </form>
</div>
</body>
<script src ="../../js/register.js" type="text/javascript"></script>
</html>



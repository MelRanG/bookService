<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!---------------------------------------------------------------------------------------------------------------->
<!--section-->
<!---------------------------------------------------------------------------------------------------------------->
<section class="main_section">
    <section class="top_section">
        <c:if test="${userId != null}">
            <div class="info_div">${userId}님 환영합니다.</div>
        </c:if>
    </section>

    <section class="bot_section">
        <ul class="book_ul">
        <c:forEach items="${result}" var="result" varStatus="vs">
                <li>
                    <div class="plattformName">${result.plattform}</div>
                    <div class="img_div"><img class="book_img" src="${result.img}"></div>
                    <div class="bookName"><a href="${result.href}">${result.bookName}</a></div>
                    <c:set var = "category" value="${result.category}"/>
                </li>
        </c:forEach>
    </ul>
        <form action="getCategory" method="get">
            <input type="hidden" name="category" value="${category}">
            <input type="hidden" name="resultParam" value="${param.result}">

            <input type="submit" value="추천도서">
        </form>
        <c:forEach items="${categoryList}" var="result" varStatus="vs">
            <li>
                <div class="plattformName">${result.plattform}</div>
                <div class="img_div"><img class="book_img" src="${result.img}"></div>
                <div class="bookName"><a href="${result.href}">${result.bookName}</a></div>
                <c:set var = "category" value="${result.category}"/>
            </li>
        </c:forEach>
    </section>
</section>
</body>

<!--footer-->
<%@ include file="/WEB-INF/views/footer.jsp" %>
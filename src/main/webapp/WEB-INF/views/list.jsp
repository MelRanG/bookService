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
        <div class="category_div">
            <button onclick="location.href='mbti'" class="btn-info">MBTI추천</button>
            <form method="get" action="list">
            <select name="book_category" onchange="formChange(this.form)">
                <option value="인문/역사/예술" <c:if test="${book_category eq '인문/역사/예술'}">selected='selected'</c:if> >인문/역사/예술</option>
                <option value="경영/경제" <c:if test="${book_category eq '경영/경제'}">selected='selected'</c:if> >경영/경제</option>
                <option value="자기계발" <c:if test="${book_category eq '자기계발'}">selected='selected'</c:if> >자기계발</option>
                <option value="에세이/시" <c:if test="${book_category eq '에세이/시'}">selected='selected'</c:if> >에세이/시</option>
                <option value="소설" <c:if test="${book_category eq '소설'}">selected='selected'</c:if> >소설</option>
                <option value="여행/건강/취미" <c:if test="${book_category eq '여행/건강/취미'}">selected='selected'</c:if> >여행/건강/취미</option>
                <option value="종교" <c:if test="${book_category eq '종교'}">selected='selected'</c:if> >종교</option>
                <option value="국어/외국어" <c:if test="${book_category eq '국어/외국어'}">selected='selected'</c:if> >국어/외국어</option>
                <option value="과학" <c:if test="${book_category eq '과학'}">selected='selected'</c:if> >과학</option>
                <option value="교육" <c:if test="${book_category eq '교육'}">selected='selected'</c:if> >교육</option>
                <option value="IT" <c:if test="${book_category eq 'IT'}">selected='selected'</c:if> >IT</option>
                <option value="어린이/청소년" <c:if test="${book_category eq '어린이/청소년'}">selected='selected'</c:if> >어린이/청소년</option>
                <option value="만화" <c:if test="${book_category eq '만화'}">selected='selected'</c:if> >만화</option>
                <option value="잡지" <c:if test="${book_category eq '잡지'}">selected='selected'</c:if> >잡지</option>
            </select>
            </form>
        </div>
    </section>
    <section class="bot_section">
        <ul class="book_ul">
            <c:if test="${book_category eq null}">
                <c:url value="list" var="url">
                    <c:param name="book_category" value="인문/역사/예술"/>
                </c:url>

                <c:redirect url="${url}"/>
            </c:if>
            <c:forEach items="${list}" var="ridi">
                <c:if test="${book_category eq ridi.category}">
                <li>
                    <div class="plattformName">${ridi.plattform}</div>
                    <div class="img_div"><img class="book_img" src="${ridi.img}"></div>
                    <div class="bookName"><a href="${ridi.href}">${ridi.bookName}</a></div>
                </li>
                </c:if>
            </c:forEach>
        </ul>
    </section>

    <section class="page_section">
        <c:choose>
            <c:when test="${startPage == 1}">
            <a class="page-link disabled" href="#">이전</a>
            </c:when>
            <c:otherwise>
                <a class="page-link" href="list?book_category=${book_category}&page=${startPage - 1}">이전</a>
            </c:otherwise>
        </c:choose>
        <c:forEach begin="${startPage}" end="${endPage}" var="index">
            <a href="list?book_category=${book_category}&page=${index - 1}">${index}</a>
        </c:forEach>
        <c:choose>
            <c:when test="${endPage == totalPages}">
            <a class="page-link disabled" href="#">다음</a>
            </c:when>
            <c:otherwise>
                <a class="page-link" href="list?book_category=${book_category}&page=${endPage + 1}">다음</a>
            </c:otherwise>
        </c:choose>
    </section>
</section>
</section>

<!---------------------------------------------------------------------------------------------------------------->
<!--footer-->
<!---------------------------------------------------------------------------------------------------------------->

<%@ include file="/WEB-INF/views/footer.jsp" %>
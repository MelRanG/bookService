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
            <form method="get" action="mbti">
                <select name ="mbtiKey" onchange="formChange(this.form)">
                    <option value="INTJ" <c:if test="${mbtiKey eq 'INTJ'}">selected='selected'</c:if> >INTJ</option>
                    <option value="INTP" <c:if test="${mbtiKey eq 'INTP'}">selected='selected'</c:if> >INTP</option>
                    <option value="ENTJ" <c:if test="${mbtiKey eq 'ENTJ'}">selected='selected'</c:if> >ENTJ</option>
                    <option value="ENTP" <c:if test="${mbtiKey eq 'ENTP'}">selected='selected'</c:if> >ENTP</option>
                    <option value="INFJ" <c:if test="${mbtiKey eq 'INFJ'}">selected='selected'</c:if> >INFJ</option>
                    <option value="INFP" <c:if test="${mbtiKey eq 'INFP'}">selected='selected'</c:if> >INFP</option>
                    <option value="ENFJ" <c:if test="${mbtiKey eq 'ENFJ'}">selected='selected'</c:if> >ENFJ</option>
                    <option value="ENFP" <c:if test="${mbtiKey eq 'ENFP'}">selected='selected'</c:if> >ENFP</option>
                    <option value="ISTJ" <c:if test="${mbtiKey eq 'ISTJ'}">selected='selected'</c:if> >ISTJ</option>
                    <option value="ISFJ" <c:if test="${mbtiKey eq 'ISFJ'}">selected='selected'</c:if> >ISFJ</option>
                    <option value="ESTJ" <c:if test="${mbtiKey eq 'ESTJ'}">selected='selected'</c:if> >ESTJ</option>
                    <option value="ESFJ" <c:if test="${mbtiKey eq 'ESFJ'}">selected='selected'</c:if> >ESFJ</option>
                    <option value="ISTP" <c:if test="${mbtiKey eq 'ISTP'}">selected='selected'</c:if> >ISTP</option>
                    <option value="ISFP" <c:if test="${mbtiKey eq 'ISFP'}">selected='selected'</c:if> >ISFP</option>
                    <option value="ESTP" <c:if test="${mbtiKey eq 'ESTP'}">selected='selected'</c:if> >ESTP</option>
                    <option value="ESFP" <c:if test="${mbtiKey eq 'ESFP'}">selected='selected'</c:if> >ESFP</option>
                </select>
            </form>
        </div>
    </section>
    <section class="bot_section">
        <ul class="book_ul">
            <c:if test="${mbtiKey eq null}">
                <c:url value="mbti" var="url">
                    <c:param name="mbtiKey" value="INTJ"/>
                </c:url>
                <c:redirect url="${url}"/>
            </c:if>
            <c:forEach items="${mbtiList}" var="key">
                <c:if test="${mbtiKey eq key.mbtiKey}">
                    <li>
                        <div class="plattformName">${key.plattform}</div>
                        <div class="img_div"><img class="book_img" src="${key.img}"></div>
                        <div class="bookName"><a href="${key.href}">${key.bookName}</a></div>
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
                <a class="page-link" href="mbti?mbtiKey=${mbtiKey}&page=${startPage - 1}">이전</a>
            </c:otherwise>
        </c:choose>
        <c:forEach begin="${startPage}" end="${endPage}" var="index">
            <a href="mbti?mbtiKey=${mbtiKey}&page=${index - 1}">${index}</a>
        </c:forEach>
        <c:choose>
            <c:when test="${endPage == totalPages}">
                <a class="page-link disabled" href="#">다음</a>
            </c:when>
            <c:otherwise>
                <a class="page-link" href="mbti?mbtiKey=${mbtiKey}&page=${endPage + 1}">다음</a>
            </c:otherwise>
        </c:choose>
    </section>
</section>
</section>

<!---------------------------------------------------------------------------------------------------------------->
<!--footer-->
<!---------------------------------------------------------------------------------------------------------------->

<%@ include file="/WEB-INF/views/footer.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: se
  Date: 2020-07-02
  Time: 오전 6:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session.invalidate();
    response.sendRedirect("list");
%>

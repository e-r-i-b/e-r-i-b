<%--
  Created by IntelliJ IDEA.
  User: Zhuravleva
  Date: 19.01.2009
  Time: 18:41:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<!-- ����� ���� -->
<c:if test="${leftMenu != ''}">
    <%@ include file="leftMenu.jsp"%>
</c:if>
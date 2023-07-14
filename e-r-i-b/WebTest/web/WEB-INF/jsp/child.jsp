<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 16.04.2009
  Time: 11:19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="${tree.id}" style="display: none; margin-left:2em;">
    <c:forEach items="${tree.children}" var="child">
        <table cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td>
                    &nbsp;<input type="checkbox" name="selectedAccountsIds" value="${child.id}" style="border:none;">
                    <c:choose>
                        <c:when test="${not empty child.children}">
                            <a href="javascript:hideOrShow('${child.id}')">
                                ${child.name}
                            </a>
                            <c:set var="tree" value="${child}" scope="request"/>
                            <jsp:include page="child.jsp"/>                            
                        </c:when>
                        <c:otherwise>${child.name}</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
    </c:forEach>
</div>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<%--
    компонент для зеленой полосы в правом личном меню.
     необходимо указывать либо action либо url
    title - заголовок блока
    action - ссылка
    absurl - урл
    idRound - id оборачивающего дива
--%>
<tiles:importAttribute/>

<div class="roundHr ${!empty action or !empty absurl?'pointer':''}"
        <c:if test="${!empty action}">
            <c:set var="url" value="${phiz:calculateActionURL(pageContext,action)}"/>
            onclick = "execIfRedirectResolved(function(){window.location = '${url}'});"
        </c:if>
        <c:if test="${!empty absurl}">
             onclick = "execIfRedirectResolved(function(){${absurl}}); return false;"
        </c:if>
        <c:if test="${!empty idRound}">
        id="${idRound}"
        </c:if>
        >
    <div class="roundHrLeft">
        <div class="roundHrRight">${title}</div>
    </div>
</div>
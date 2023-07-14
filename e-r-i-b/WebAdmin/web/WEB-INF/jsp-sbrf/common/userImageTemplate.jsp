<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<%--
Пользовательская картинка.

	imagePath             	- путь к картинке.
	selector            	- если путь одинаковый, используется селектор для идентификации картинок.
	imgStyle                - css класс картинки.
	imgId                   - id элемента.
	style                   - css стиль.
	temp                    - временная картинка.
--%>

<c:forEach items="${phiz:buildUserImageUri(pageContext, imagePath, selector, temp == 'true')}" var="url">
    <c:choose>
        <c:when test="${empty url1}">
            <c:set var="url1" value="${url}"/>
        </c:when>
        <c:otherwise>
            <c:set var="url2" value="${url}"/>
        </c:otherwise>
    </c:choose>
</c:forEach>

<img src="${url1}" id="userImage" onerror="if (this.src.lastIndexOf('${url2}') < 0) this.src='${url2}'"
    <c:if test="${not empty imgStyle}">class="${imgStyle}"</c:if>
    <c:if test="${not empty imgId}">id="${imgId}"</c:if>
    <c:if test="${not empty style}">style="${style}"</c:if>
>
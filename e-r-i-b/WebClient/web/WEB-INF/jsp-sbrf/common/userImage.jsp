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
	yandexContact           - отображать иконку яндекс-контакта.
--%>

<c:if test="${yandexContact == 'true'}">
    <c:set var="imagePath" value=""/>
    <c:set var="selector" value="yandex/${selector}"/>
</c:if>

<c:set var="userImageUrl" value="${phiz:buildUserImageUri(pageContext, imagePath, selector, temp == 'true')}"/>

<img src="${userImageUrl[0]}" onerror="if (this.src.lastIndexOf('${userImageUrl[1]}') < 0) this.src='${userImageUrl[1]}'"
    <c:if test="${not empty imgStyle}">class="${imgStyle}"</c:if>
    <c:if test="${not empty imgId}">id="${imgId}"</c:if>
    <c:if test="${not empty style}">style="${style}"</c:if>
/>
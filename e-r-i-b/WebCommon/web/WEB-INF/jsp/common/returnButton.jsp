<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<%--
Как работает кнопка.
- При нажатии на кнопку вызывается validationFunction (если установлена)
- Если валидация прошла успешно показывается confirmText (если установлен)
- Если пользователь подтвердил выполнение команды на сервер отправляется запрос

	bundle    - bundle в котором ищутся текст для textKey и helpKey
	helpKey   - ключ для описания команды
	textKey   - ключ для заголовка команды
	image	  - название файла с картинкой, картинка должна находится в папке картинок текущей конфигурации
	width	  - ширина кнопки, если не указана определяется размером кнорки
--%>                                                        

<c:set var="commandText"><bean:message key="${commandTextKey}" bundle="${bundle}"/></c:set>
<c:set var="commandHelp"><bean:message key="${commandHelpKey}" bundle="${bundle}"/></c:set>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="oldUrl" value="${phiz:getOldUrl()}"/>

<span class="buttDiv ${typeBtn}">
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="btnLeftCorner">&nbsp;</td>
			<td class="btnBase"
				<c:set var="url" value="${phiz:calculateActionURL(pageContext,oldUrl)}"/>
		      onclick="window.location='${url}'"

				align="center" style="white-space:noWrap" valign="middle"
                title="${commandHelp}"
	            <c:if test="${not empty width}">style="width:${width};"</c:if>
            >
				<c:choose>
					<c:when test="${not empty image}">
						<img src="${imagePath}/${image}" alt="${commandHelp}" width="12px" height="12px">&nbsp;
					</c:when>
					<c:otherwise>
						<img src="${globalImagePath}/1x1.gif" alt="${commandHelp}" width="3px" height="12px">&nbsp;
					</c:otherwise>
				</c:choose>
		        <span>${commandText}</span>
			<td class="btnRightCorner">&nbsp;</td>
		</tr>
	</table>
</span>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
Компонент для отображения настроек безопасности в профиле клиента
style - стиль блока (напр., redContainer, стиль - red)
titleId - идентификатор заголовка
aditionalData - Дополнительные данные идущие после блока с подсказкой
URL - ссылка на другую страницу (внешнюю)
action - ссылка на action
onClick - событие при клике на заголовок
text - подсказка под названием блока
title - заголовок блока
--%>
<tiles:importAttribute/>
<c:if test="${action !=''}">
    <c:set var="URL">${phiz:calculateActionURL(pageContext, action)}</c:set>
</c:if>

<c:if test="${titleId !=''}">
    <c:set var="titleId">id="${titleId}"</c:set>
</c:if>
<c:choose>
    <c:when test="${not empty URL}">
        <a class="${style}Container picInfoBlock" href="${URL}">
            <h1 class="noBg" ${titleId}>${title}</h1>

            <div class="hint">${text}</div>
        </a>
        ${aditionalData}
    </c:when>
    <c:when test="${onClick !=''}">
        <div class="forProductBorder showLight">
            <div class="productCover activeProduct">
                <div onclick="${onClick}" class="${style}Container picInfoBlock"  ${titleId}>
                    <h1>
                            ${title}
                    </h1>

                    <div class="hint">${text}</div>
                </div>
                <div class="innerOperationsCategiries">
                    ${aditionalData}
                </div>
                <div class="clear"></div>
            </div>
        </div>
        <div class="productDivider"></div>
    </c:when>
    <c:otherwise>
        <div class="${style}Container picInfoBlock noLink">
            <h1 class="decoration-none noBg" ${titleId}>
            ${title}
            </h1>
            <div class="hint">${text}</div>
        </div>
        ${aditionalData}
    </c:otherwise>
</c:choose>
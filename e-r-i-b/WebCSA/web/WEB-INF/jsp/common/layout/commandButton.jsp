<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>

<%--
id                  -- идентификатор (название действия экшена)
type                -- тип кнопки:
        Carroty -- рыжая
        Green   -- зеленая
        Grey    -- серая
        Hidden  -- невидимая
onclick             -- js-функция на клик по кнопке
clearFormAction     -- нужно ли очищать экшен формы (аналог setEmptyAction)
validationFunction  -- js-функция на клик по кнопке
isDefault           -- является ли кнопка дефолтной
useAjax             -- использовать аякс для отпаравки запроса
afterAjax           -- использовать аякс для отпаравки запроса
tabindex            -- таб-индекс
title               -- название
--%>

<div id="commandButton_${id}"
     class="commandButton<c:if test='${isDefault == "true"}'> default</c:if> ${type}"
     onclick="getButton('${id}').click();"
     onkeypress="clickIfEnterKeyPress(this, event);"
     tabindex="${tabindex}">

    <div class="button${type}">
        <div class="left-corner"></div>
        <div class="text">
            <span><c:out value="${title}"/></span>
        </div>
        <div class="right-corner"></div>
    </div>
    <div class="clear"></div>
</div>

<script type="text/javascript">
    <c:choose>
        <c:when test="${empty onclick}">
            <c:choose>
                <c:when test="${useAjax == 'true'}">
                    if(window.createAjaxButton != undefined)
                        createAjaxButton('${id}', ${isDefault}, ${validationFunction}, ${afterAjax});
                </c:when>
                <c:otherwise>
                    if(window.createSubmitButton != undefined)
                        createSubmitButton('${id}', ${isDefault}, ${validationFunction}, ${clearFormAction});
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            if(window.createClientButton != undefined)
                createClientButton('${id}', ${isDefault}, function(){${onclick}});
        </c:otherwise>
    </c:choose>
</script>


<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
Общий компонент для сообщений и ошибок
regionSelector - имя селектора
isDisplayed - стиль
color - цвет фона
needWarning - необходимость отображения "Внимание!"
data - данные внутри этого региона
userStyle - стиль для сообщений.
--%>

<tiles:importAttribute/>
<c:set var="style">
    <c:if test="${not empty userStyle}">
        class="${userStyle}"
    </c:if>
</c:set>
<div id="${regionSelector}" style="${isDisplayed ? 'display: block' : 'display: none'}" ${style}>
    <tiles:insert definition="roundBorderLight" flush="false">
        <tiles:put name="color" value="${color}"/>
        <tiles:put name="data">
            <div class="infoMessage">
                <c:if test="${needWarning}">
                    <div class="title"><span>Обратите внимание</span></div>
                </c:if>
                <div class="messageContainer">
                    ${data}
                </div>
            </div>
        </tiles:put>
    </tiles:insert>
</div>
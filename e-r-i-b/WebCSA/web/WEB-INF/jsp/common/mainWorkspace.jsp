<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%--
    компонент для отображения основных блоков. Заменяющий избыточный шаблон roundBorder.
    data        - данные
    title       - заголовок блока
    contentTitle - заголовок блока в контенте (при необходимости)
    addStyle    -
    control     - верхний правый блок управления с кнопками
    outerData - данные за пределом формы
    rightDataArea - данные в правой колонке
--%>

<tiles:importAttribute/>

<c:if test="${not empty rightDataArea}">
    <div class="rightDataArea">
        ${rightDataArea}
    </div>
</c:if>

<div class="workspace-box ${addStyle} ${color} <c:if test="${not empty rightDataArea}">float</c:if>">
    <c:if test="${title != ''}">
        <div class="RTC mainTitle">
            <div class="Title <c:if test='${!empty controlLeft or !empty control}'>float</c:if>">
                <span>${title}</span>
            </div>
            <c:if test="${!empty control}">
                <div class="CBT">${control}</div>
            </c:if>
            <c:if test="${!empty controlLeft}">
                <div class="${color}CTL">${controlLeft}</div>
            </c:if>
        </div>
        <div class="clear"></div>
    </c:if>
    <div class="RC r-content">
        ${contentTitle}
        ${data}
        <div class="clear"></div>
    </div>
</div>

<div class="clear"></div>
<c:if test="${not empty outerData}">
    <div class="outerData">
            ${outerData}
    </div>
</c:if>
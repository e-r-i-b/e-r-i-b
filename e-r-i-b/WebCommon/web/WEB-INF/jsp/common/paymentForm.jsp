<%--
  Created by IntelliJ IDEA.
  User: Egorova
  Date: 24.09.2008
  Time: 17:49:06
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<%--
Форма для стандартного платежа. Его  обрамление. Внутренняя таблица с полями прописывается в *.html.xslt платежа

	id          -   имя формы
	name        -   название отображаемое в UI
	description -   описание платежа
	data        -   данные платежа - внутренняя таблица с полями
    buttons     -   кнопка платежа
    additionalStyle - дополнительный стиль для внешней рамки окна.
    stamp       -   штамп (Возможные значения recived: принят,
                                              executed: исполнен,
                                              refused: отказан). Если пустое, штамп не рисуем.
    tableStyle  -   дополнительный стиль для таблицы
--%>
<!-- Заголовок платежки -->
<div class="paymentForms ${additionalStyle}">
    <div class="pmntTitleForm">
        <div class="pageTitle">${name}</div>
        <input type="hidden" id="formName" name="formName" value="${phiz:replaceQuotes(name)}">
        <%--Необходим для получения заголовка формы, при сохранении страницы в избранное--%>
        <c:if test="${not empty description}">
            <div class="pmntTitleText">
                ${description}
            </div>
        </c:if>
    </div>

    <div class="pmntData" onkeypress="onEnterKey(event);">
        <!-- Поля платежки -->
        ${data}
    </div>

    <c:choose>
        <c:when test="${not empty stamp}">
            <c:set var="OSB" value="${phiz:getOSB(form.department)}"/>
            <c:set var="CorrAcc" value="${phiz:getCorrByBIC(OSB.BIC)}"/>
            <div style="position:relative;">
                <div style="position:absolute;top:-100px;right:0;float:right;text-align:left;border:2px solid #5d417b;padding:5px;">
                    <span><c:out value="${OSB.name}" default="${bankName}"/></span><br>
                    <span>БИК:<c:out value="${OSB.BIC}" default="${bankBIC}"/></span><br>
                    <span>Корр.Счет: <c:out value="${phiz:getCorrByBIC(OSB.BIC)}" default="${phiz:getCorrByBIC(bankBIC)}"/></span><br>
                    <div style="text-align:center;">

                        <c:if test="${stamp == 'recived'}">
                            <img src="${imagePath}/stampReceived_noBorder.gif" width="128px" height="25px">
                        </c:if>
                        <c:if test="${stamp == 'executed'}">
                            <img src="${imagePath}/stampExecuted_noBorder.gif" width="137px" height="18px">
                        </c:if>
                        <c:if test="${stamp == 'refused'}">
                            <img src="${imagePath}/stampRefused_noBorder.gif" width="107px" height="17px">
                        </c:if>
                    </div>
                    <span style="color:#5d417b;font:bold 10px Arial;white-space: nowrap;"> ${docDate}</span>
                    <c:if test="${not empty message}">
                        <script type="text/javascript">
                            if (document.getElementById("titleHelp"))
                                document.getElementById("titleHelp").innerHTML = ${message}
                        </script>
                    </c:if>
                </div>
            </div>
        </c:when>
        <c:otherwise>&nbsp;</c:otherwise>
    </c:choose>

    <c:if test="${!confirmCSA}">
        <div class="pmntFormMainButton floatRight">
            <div class="otherButtonsArea">
                ${buttons}${btnList}
            </div>
        </div>
        <div class="clear"></div>
    </c:if>
</div>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
    Компонент для отображения блока курсов валют и/или металлов

    title - заголовок блока
    currencyRateItems - набор курсов валют состоит из компонентов "currencyRateTemplateItem"
    currencyRateBottomLink - линк на форму обмена валюты (если "currencyRateBottomLink" пустой, то линк не отображается)
    currencyRateBottomLinkTitle - текст линка на форму обмена валюты, отображаемый пользователю (если "currencyRateBottomLink" пустой, то линк не отображается)
    metallRateItems - набор курсов маталлов состоит из компонентов "currencyRateTemplateItem"
    metallRateBottomLink - линк на форму покупки/продажи металла (если "metallRateItems" пустой, то линк не отображается)
    metallRateBottomLinkTitle - текст линка на форму покупки/продажи металла, отображаемый пользователю (если "metallRateItems" пустой, то линк не отображается)
--%>
<tiles:importAttribute/>
<tiles:insert definition="roundBorderLight" flush="false">
    <%--<tiles:put name="title" value="${title}"/>--%>
    <tiles:put name="color" value="grayBorder css3"/>
    <tiles:put name="data">
        <div class="grayTitle">
            <span>${title}</span>
        </div>
        <div class="clear"></div>
        <%--Курс валют--%>
        <div class="currencyRateContainer">
            <div class="currencyRate">
                <div class="rateTitle">
                    <div class="rateText">&nbsp;</div>
                    <div class="rateText text-gray">Покупка</div>
                    <div class="rateText text-gray">Продажа</div>
                </div>
                ${currencyRateItems}
            </div>
            <c:if test="${not empty currencyRateBottomLink}">
                <div class="currencyRateBottomLink">
                    <a class="blueGrayLink courseLink" onclick="return redirectResolved();" href="${currencyRateBottomLink}">
                        ${currencyRateBottomLinkTitle}
                    </a>
                </div>
            </c:if>
        </div>
        <%--Курс металлов--%>
        <div class="currencyRateContainer">
            <div class="currencyRate">
                <div class="rateTitle">
                    <div class="rateText">&nbsp;</div>
                    <div class="rateText text-gray">Покупка</div>
                    <div class="rateText text-gray">Продажа</div>
                </div>
                ${metallRateItems}
            </div>
            <c:if test="${not empty metallRateBottomLink}">
                <div class="currencyRateBottomLink currencyRateBottom">
                    <a class="blueGrayLink courseLink" onclick="return redirectResolved();" href="${metallRateBottomLink}">
                        ${metallRateBottomLinkTitle}
                    </a>
                </div>
            </c:if>
        </div>
        <div class="currencyRateDivider"></div>
        <%--Информационное сообщение--%>
        <div class="currencyRateContainer">
            <div class="currencyRateFooter text-gray">
                В момент проведения операции значение курса может отличаться. В этом случае мы обязательно уведомим Вас.
            </div>
        </div>
    </tiles:put>
</tiles:insert>
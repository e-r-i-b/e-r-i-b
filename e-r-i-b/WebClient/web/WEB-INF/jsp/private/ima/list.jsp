<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<html:form action="/private/ima/list" onsubmit="return setEmptyAction(event)">

    <tiles:insert definition="imaInfo">
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>

        <tiles:put name="mainmenu" value="IMAInfo"/>
        <tiles:put name="enabledLink" value="false"/>
        <tiles:put name="menu" type="string"/>

        <tiles:put name="data" type="string">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <c:set var="creationType">${phiz:getPersonInfo().creationType}</c:set>
            <%--Должно быть право на "Заявка на открытие ОМС" и одно из: "Открытие ОМС с переводом со вклада", "Открытие ОМС с переводом с карты"--%>
            <c:if test="${phiz:impliesService('IMAOpeningClaim') &&
                         (phiz:impliesService('IMAOpeningFromCardClaim') || phiz:impliesService('IMAOpeningFromAccountClaim'))}">
                <div class="mainWorkspace productListLink">
                    <tiles:insert definition="paymentCardsDiv" service="IMAOpeningClaim" flush="false">
                        <tiles:put name="serviceId"   value="IMAOpeningClaim"/>
                        <tiles:put name="action"      value="/private/ima/products/list"/>
                        <tiles:put name="globalImage" value="iconPmntList_IMAOpeningClaim.jpg"/>
                        <tiles:put name="description" value="Открыть новый ОМС."/>
                    </tiles:insert>
                    <div class="clear"></div>
                </div>
            </c:if>

            <c:choose>
                <c:when test="${not empty form.imAccounts}">

                    <tiles:insert definition="mainWorkspace" flush="false">
                        <tiles:put name="title" value="Металлические счета"/>
                        <tiles:put name="data" type="string">

                            <c:set var="elementsCount" value="${fn:length(form.imAccounts)-1}"/>
                            <logic:iterate id="listElement" name="form" property="imAccounts" indexId="i">
                                <c:set var="imAccountLink" value="${listElement}" scope="request"/>
                                <c:set var="imAccountAbstract" value="${form.imAccountAbstract[listElement]}" scope="request"/>
                                <c:set var="bottomOn" value="true" scope="request"/>
                                <c:set var="imaInfoLink" value="true" scope="request"/>
                                <%@include file="imaTemplate.jsp" %>
                                <c:if test="${elementsCount != i}">
                                    <div class="productDivider"></div>
                                </c:if>
                            </logic:iterate>

                        </tiles:put>
                    </tiles:insert>

                    <%--<tiles:insert definition="hidableRoundBorder" flush="false">
                        <tiles:put name="title" value="Закрытые металлические счета"/>
                        <tiles:put name="color" value="whiteTop"/>
                        <tiles:put name="data">

                        </tiles:put>
                    </tiles:insert>--%>
                    
                </c:when>
                <c:otherwise>
                    <c:if test="${not form.allIMAccountDown}">
                        <div id="infotext">
                            <c:choose>
                                <c:when test="${creationType == 'UDBO'}">
                                    <tiles:insert page="no-ima.jsp" flush="false"/>
                                </c:when>
                                <c:otherwise>
                                    <tiles:insert page="/WEB-INF/jsp-sbrf/needUDBO.jsp" flush="false">
                                        <tiles:put name="product" value="обезличенным металлическим счетам" />
                                    </tiles:insert>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>

            <c:if test="${!empty form.rates && creationType == 'UDBO'}">
                <c:set var="rates" value="${form.rates}"/>
                <%-- Последовательность кодов металов --%>
                <c:set var="ratesCodesOrder">A98,A99,A76,A33</c:set>
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title" value="Курсы покупки/продажи драгоценных металлов"/>
                    <tiles:put name="data" type="string">
                        <p>
                            Вложение денег в драгоценные металлы является надежным средством сохранности Ваших сбережений.
                            Сбербанк предлагает хранить сбережения в золоте, серебре, платине или палладии.
                        </p>


                        <div class="noPadding negativeMargin simpleTable">
                            <table width="100%" class="tblInf shadow">
                                <tr class="tblInfHeader">
                                    <th class="titleTable">&nbsp;</th>
                                    <th class="titleTable">AUR</th>
                                    <th class="titleTable">ARG</th>
                                    <th class="titleTable">PTR</th>
                                    <th class="titleTable">PDR</th>
                                </tr>
                                <tr class="ListLine0">
                                    <td>Покупка</td>
                                    <c:forEach items="${ratesCodesOrder}" var="code">
                                        <td class="bold">
                                            <c:set var="rate" value="${rates[code]}"/>
                                            <c:choose>
                                                <c:when test="${!empty rate}">
                                                    ${phiz:getFormattedCurrencyRate(rate.buy,2)} ${phiz:getCurrencyName(rate.toCurrency)}
                                                </c:when>
                                                <c:otherwise>&mdash;</c:otherwise>
                                            </c:choose>
                                        </td>
                                    </c:forEach>
                                </tr>
                                <tr class="ListLine1">
                                    <td>Продажа</td>
                                     <c:forEach items="${ratesCodesOrder}" var="code">
                                        <td class="bold">
                                            <c:set var="rate" value="${rates[code]}"/>
                                            <c:choose>
                                                <c:when test="${!empty rate}">
                                                    ${phiz:getFormattedCurrencyRate(rate.sale,2)} ${phiz:getCurrencyName(rate.toCurrency)}
                                                </c:when>
                                                <c:otherwise>&mdash;</c:otherwise>
                                            </c:choose>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </table>
                        </div>
                        <span class="italicInfo">* Значения могут отличаться в подразделениях Банка </span>

                        <p class="align-right">
                            <c:set var="imaPayUrl"
                                   value="${phiz:calculateActionURL(pageContext,'/private/payments/payment.do?form=IMAPayment')}"/>
                            <a class="orangeText" href="${imaPayUrl}"><span>Купить/продать металл</span></a>
                        </p>
                    </tiles:put>
                </tiles:insert>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
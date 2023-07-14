<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<%--
Внешние переменные используемые в данном файле
imAccountLink - ссылка на текущий ОМС
imaInfoLink - если true, картинка и другие элементы будут кликабильными и будут вести на страницу с детальной
              информацией
showInMainCheckBox - признак необходимости вывода чекбокса "показать на главной"
isAbstractPage - если true, находимся на странице информации, где есть выписка, не нужно отображать блок операций
showInThisWidgetCheckBox - признак необходимости вывода чекбокса "показывать в этом виджете"
--%>

<tiles:importAttribute/>
<c:set var="imAccount" value="${imAccountLink.imAccount}"/>
<c:set var="imaInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/ima/info.do?id=')}"/>
<c:set var="person" value="${phiz:getPersonInfo()}"/>
<c:set var="isLock" value="${imAccount.state == 'closed'}"/>
<c:set var="isNegative" value="${imAccount.balance.decimal<0}"/>
<c:set var="availableIMAPayment" value="${phiz:impliesOperation('CreateFormPaymentOperation','IMAPayment')}"/>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>

<div class="imaProduct">
    <tiles:insert definition="productTemplate" flush="false">
        <tiles:put name="productViewBacklight" value="false"/>
        <c:if test="${detailsPage}">
            <tiles:put name="operationsBlockPosition" value="rightPosition"/>
        </c:if>
        <c:choose>
            <c:when test="${imAccount.state == 'closed' || imAccount.state == 'lost_passbook'}">
                <tiles:put name="img" value="${imagePath}/ima_type/imaccountBlocked.jpg"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="img" value="${imagePath}/ima_type/imaccount.gif"/>
            </c:otherwise>
        </c:choose>

        <tiles:put name="alt">
            <bean:write name="imAccountLink" property="name"/>
        </tiles:put>

        <c:if test="${imaInfoLink}">
            <tiles:put name="src" value="${imaInfoUrl}${imAccountLink.id}"/>
        </c:if>

        <tiles:put name="productNumbers">
            <c:choose>
                <c:when test="${imaInfoLink}">
                    <a href="${imaInfoUrl}${imAccountLink.id}" class="productNumber decoration-none">
                            ${phiz:getFormattedAccountNumber(imAccountLink.number)}
                    </a>
                </c:when>
                <c:otherwise>
                    <div class="productNumber">${phiz:getFormattedAccountNumber(imAccountLink.number)}</div>
                </c:otherwise>
            </c:choose>
        </tiles:put>

        <tiles:put name="title">
            <c:choose>
                <c:when test="${not empty imAccountLink.name}">
                    <%--В строке ниже форматирование не менять. Необходимо для корректного отображения всплывающей подсказки--%>
                    <bean:write name="imAccountLink" property="name"/><c:if test="${not empty imAccount.currency}">&nbsp;(${phiz:normalizeCurrencyCode(imAccount.currency.code)})</c:if>
                </c:when>
                <c:otherwise>
                    <c:if test="${not empty imAccount.currency}">
                        <bean:write name="imAccount" property="currency.name"/>
                        &nbsp;(${phiz:normalizeCurrencyCode(imAccount.currency.code)})
                    </c:if>
                </c:otherwise>
            </c:choose>
        </tiles:put>
        <c:choose>
            <c:when test="${detailsPage}">
                <tiles:put name="titleClass" value="mainProductTitle mainProductDetailTitle"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="titleClass" value="mainProductTitle  mainProductTitleLight"/>
            </c:otherwise>
        </c:choose>

        <tiles:put name="leftData">
            <div class="clear"></div>
            <c:if test="${(not empty imAccount.state and imAccount.state ne 'opened')}">
                <span class="detailStatus float"><bean:write name="imAccount" property="state.description"/></span>
                <c:if test="${imAccount.state == 'arrested'}">
                    <div class="float">
                        <tiles:insert definition="hintTemplate" flush="false">
                            <tiles:put name="color" value="whiteHint"/>
                            <tiles:put name="data">
                                Операции со счетом ограничены. За дополнительной информацией обратитесь в Контактный центр по телефону +7 (495) 500-55-50
                            </tiles:put>
                        </tiles:insert>
                    </div>
                </c:if>
                <br/><br/>
            </c:if>
            <c:if test="${not empty imAccount.openDate}">
                <table class="productDetail">
                    <tr>
                        <td>
                            <div class="availableReight">Открыт: &nbsp;</div>
                        </td>
                        <td>
                            <span class="bold availableReight">${phiz:formatDateWithStringMonth(imAccount.openDate)}</span>
                        </td>
                    </tr>
                </table>
            </c:if>

            <c:if test="${not empty imAccount.balance}">
                <div class="additionalData">
                    <c:set var="tarifPlanCodeType" value="${phiz:getActivePersonTarifPlanCode()}"/>
                    <c:set var="balance" value="${phiz:getIMBalanceInNationCurrency(imAccount, tarifPlanCodeType)}"/>
                    По курсу покупки Банка: &nbsp;
                    <b class="nowrapWhiteSpace"><c:out value="${phiz:formatAmount(balance)}"/></b>
                </div>
            </c:if>
        </tiles:put>

        <tiles:put name="centerData">
            <c:set var="amountinfo" value="${phiz:formatAmount(imAccount.balance)}"/>
            <c:if test="${isNegative}">
                <c:set var="amountinfo">
                    &minus;${fn:substring(amountinfo, 1, -1)}
                </c:set>
            </c:if>
            <c:if test="${not empty imAccount.balance}">
                <c:choose>
                    <c:when test="${isLock || isNegative}">
                        <c:if test="${detailsPage}">
                            <span class="detailAmount negativeAmount">${amountinfo}</span>
                        </c:if>
                        <c:if test="${not detailsPage}">
                            <span class="overallAmount negativeAmount">${amountinfo}</span>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${detailsPage}">
                            <span class="detailAmount">${amountinfo}</span>
                        </c:if>
                        <c:if test="${not detailsPage}">
                            <span class="overallAmount">${amountinfo}</span>
                        </c:if>
                    </c:otherwise>
                </c:choose>
                <br />
            </c:if>
        </tiles:put>

        <tiles:put name="bottomData">
            <c:if test="${empty hideable}">
                <tiles:insert definition="simpleTableTemplate" flush="false">
                    <tiles:put name="id" value="${style}"/>
                    <tiles:put name="hideable" value="true"/>
                    <tiles:put name="id" value="${imAccountLink.id}"/>
                    <tiles:put name="productType" value="imaccount"/>
                    <tiles:put name="show" value="${phiz:isShowOperations(imAccountLink)}"/>
                    <tiles:put name="ajaxDataURL">${phiz:calculateActionURL(pageContext, '/private/async/extract.do')}?type=ima&id=${imAccountLink.id}</tiles:put>
                </tiles:insert>
            </c:if>
        </tiles:put>

        <tiles:put name="rightData">
            <tiles:insert definition="listOfOperation" flush="false">
                <tiles:put name="productOperation" value="true"/>
                <c:if test="${detailsPage}">
                    <tiles:put name="nameOfOperation">Операции с металлом</tiles:put>
                </c:if>

                <c:choose>
                    <c:when test="${isAbstractPage}">
                        <tiles:putList name="items">
                            <c:choose>
                                <c:when test="${availableIMAPayment && !isLock}">
                                    <c:set var="imaUrl" value="${phiz:calculateActionURL(pageContext, '/private/payments/payment.do')}?form=IMAPayment&fromResource=im-account:${imAccountLink.id}&toResource=im-account:${imAccountLink.id}"/>
                                    <tiles:add><a href="${imaUrl}">Покупка/продажа металла</a></tiles:add>
                                </c:when>
                                <c:otherwise>
                                    <tiles:put name="isLock" value="true"/>
                                </c:otherwise>
                            </c:choose>
                        </tiles:putList>
                    </c:when>
                    <c:otherwise>
                        <tiles:putList name="items">
                            <c:choose>
                                <c:when test="${phiz:impliesOperation('GetIMAccountShortAbstractOperation', 'IMAccountInfoService')}">
                                    <c:set var="url">${phiz:calculateActionURL(pageContext, '/private/ima/info.do')}?id=${imAccountLink.id}</c:set>
                                    <tiles:add><a href="${url}">Выписка</a></tiles:add>
                                    <%--Покупка/продажа металла--%>
                                    <c:if test="${availableIMAPayment && !isLock}">
                                        <c:set var="imaUrl" value="${phiz:calculateActionURL(pageContext, '/private/payments/payment.do')}?form=IMAPayment&fromResource=im-account:${imAccountLink.id}&toResource=im-account:${imAccountLink.id}"/>
                                        <tiles:add><a href="${imaUrl}">Покупка/продажа металла</a></tiles:add>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <tiles:put name="isLock" value="true"/>
                                </c:otherwise>
                            </c:choose>
                        </tiles:putList>
                    </c:otherwise>
                </c:choose>
            </tiles:insert>
        </tiles:put>

        <c:if test="${showInMainCheckBox}">
            <tiles:put name="id" value="${imAccountLink.id}"/>
            <tiles:put name="showInMainCheckBox" value="true"/>
            <tiles:put name="inMain" value="${imAccountLink.showInMain}"/>
            <tiles:put name="productType" value="imaccount"/>
        </c:if>
        <c:if test="${isLock}">
            <tiles:put name="status" value="error"/>
        </c:if>

        <c:if test="${showInThisWidgetCheckBox}">
            <tiles:put name="id" value="${imAccountLink.id}"/>
            <tiles:put name="showInThisWidgetCheckBox" value="true"/>
            <tiles:put name="productType" value="account"/>
        </c:if>
    </tiles:insert>
</div>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<tiles:insert definition="productTemplate" flush="false">
    <tiles:put name="img" value="${skinUrl}/images/icon_mobilebank.png"/>
    <tiles:put name="alt" value="Мобильный банк"/>
    <tiles:put name="title" >
        ${title}
    </tiles:put>
    <tiles:put name="operationsBlockPosition" value="rightPosition"/>
    <tiles:put name="titleClass" value="mainProductTitle mainProductDetailTitle"/>

    <tiles:put name="leftData">
        <div class="cardName">
            <html:link href="${mainCardPageLink}">
                <c:out value="${cardName}"/>
            </html:link>
            <html:link href="${mainCardPageLink}" styleClass="accountNumber">${cardNumber}</html:link>

        </div>
        <br/>
        <span class="accountNumber">
            <c:if test="${!empty registrationStatus}">
                <span class="accountNumber">Состояние услуги:</span> <span class="status inlineBlock ${registrationColor}">${registrationStatus}</span>
            </c:if>
            <c:if test="${!empty registrationTariff}">
                <br/>
                <span class="accountNumber">Тариф: <span class="tariff">${registrationTariff}</span></span>
            </c:if>
        </span>
    </tiles:put>
    <tiles:put name="rightData">
        ${rightData}
    </tiles:put>
    <tiles:put name="centerData">
        <c:if test="${! empty amount}">
            <span class="detailAmount nowrap">${amount}</span>
        </c:if>
    </tiles:put>
    <c:if test="${isLock}">
        <tiles:put name="status" value="error"/>
    </c:if>
</tiles:insert>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

    <c:set var="form"                       value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmableRegistration"    value="${form.confirmableRegistration}"/>
    <c:set var="registration"            value="${form.registration}"/>
    <c:set var="mainCard"                   value="${registration.mainCard}"/>
    <c:set var="mainPhone" value="${registration.mainPhoneNumber}"/>
    <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
    <c:set var="hasCapStrategy" value="${phiz:isContainStrategy(confirmStrategy,'cap')}"/>
    <c:set var="phoneCode" value="${phiz:getMobileBankPhoneCode(mainPhone)}"/>
    <c:set var="windowCode" value="${mainCard.cardlink.id}|${phoneCode}"/>
    <c:set var="cardNumber" value="${mainCard.cardNumber}"/>

    <h2>Подтверждение операции</h2><br/>

    <div class="messageContainer">
        Для того чтобы подтвердить операцию, нажмите кнопку "Подтвердить по SMS"<c:if test="${hasCapStrategy}"> или "Подтвердить по карте"</c:if>.
    </div><hr/>

    <div class="buttonsArea">
        <c:if test="${not empty confirmableRegistration}">
            <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(confirmableRegistration)}" scope="request"/>
        </c:if>

        <c:if test="${not empty confirmRequest}">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"         value="commonBundle"/>
                <tiles:put name="viewType"       value="buttonGrey"/>
                <tiles:put name="onclick"        value="win.close('openChangeQuickServiceWindow${cardNumber}');"/>
            </tiles:insert>

            <tiles:insert definition="confirmButtons" flush="false">
                <tiles:put name="ajaxUrl" value="/private/async/mobilebank/quickService.do?cardCode=${mainCard.cardlink.id}"/>
                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                <tiles:put name="winConfirmName" value="winConfirmWindow${cardNumber}"/>
                <tiles:put name="onclick" value="win.close('openChangeQuickServiceWindow${cardNumber}');" type="string"/>
            </tiles:insert>
        </c:if>
    </div>
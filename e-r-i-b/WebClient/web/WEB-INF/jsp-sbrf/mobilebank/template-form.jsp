<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%--<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>--%>
<%--<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>--%>
<%--<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="smsCommand" value="${form.smsCommands[0]}"/>

<div id="paymentForm">
    <div>
        <b> Подключение </b>
    </div>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"> Банковская карта </tiles:put>
        <tiles:put name="data">
            <span> <c:out value="${phiz:getCardUserName(form.cardLink)}"/> </span>
            &nbsp;
            <span> ${phiz:getCutCardNumber(form.cardLink.number)} </span>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"> Номер телефона </tiles:put>
        <tiles:put name="data"> ${phiz:getCutPhoneNumber(form.phoneNumber)}</tiles:put>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"> Операция </tiles:put>
        <tiles:put name="data"> <b> ${smsCommand.name} </b> </tiles:put>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"> Формат запроса </tiles:put>
        <tiles:put name="data"> <b> ${smsCommand.format} </b> </tiles:put>
    </tiles:insert>
</div>

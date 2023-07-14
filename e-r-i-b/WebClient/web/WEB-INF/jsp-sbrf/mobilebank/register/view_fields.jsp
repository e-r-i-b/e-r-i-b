<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--@elvariable id="form" type="com.rssl.phizic.web.client.ext.sbrf.mobilebank.register.ConfirmRegistrationForm"--%>

<%-- 1. тариф FULL/ECONOM --%>
<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title">Пакет обслуживания:</tiles:put>
    <tiles:put name="data">
        <c:choose>
            <c:when test="${form.tariff == 'FULL'}">
                <b>Полный пакет</b>
            </c:when>
            <c:when test="${form.tariff == 'ECONOM'}">
                <b>Экономный пакет</b>
            </c:when>
        </c:choose>
    </tiles:put>
</tiles:insert>

<%-- 2. маскированный номер телефона --%>
<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title">Номер телефона:</tiles:put>
    <tiles:put name="data">
        <b>${form.maskedPhone}</b>
    </tiles:put>
    <tiles:put name="description">
        Услуга &laquo;Мобильный банк&raquo; будет подключена на этот номер телефона.
    </tiles:put>
</tiles:insert>

<%-- 3. маскированный номер карты --%>
<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title">Номер карты:</tiles:put>
    <tiles:put name="data">
        <b><c:out value="${form.maskedCard}"/></b>
    </tiles:put>
    <tiles:put name="description">
        Для данной карты будет подключена услуга &laquo;Мобильный банк&raquo;.
    </tiles:put>
</tiles:insert>

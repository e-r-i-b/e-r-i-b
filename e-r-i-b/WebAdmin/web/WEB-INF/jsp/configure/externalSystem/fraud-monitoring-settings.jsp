<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<table>
    <%--статус--%>
    <tiles:insert definition="propertyField" flush="false">
        <tiles:put name="fieldName" value="com.rssl.rsa.activity.state"/>
        <tiles:put name="fieldDescription">
            <bean:message bundle="configureBundle" key="RSA.field.key.com.rssl.rsa.activity.state"/>
        </tiles:put>
        <tiles:put name="fieldHint">
            <bean:message bundle="configureBundle" key="RSA.field.hint.com.rssl.rsa.activity.state"/>
        </tiles:put>
        <tiles:put name="fieldType" value="select"/>
        <tiles:put name="selectItems" value="ACTIVE_INTERACTION@Включен|ACTIVE_ONLY_SEND@Только отправка|NOT_ACTIVE@Выключен"/>
        <tiles:put name="imagePath" value="${imagePath}"/>
        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
    </tiles:insert>
    <%--урл к ФМ--%>
    <tiles:insert definition="propertyField" flush="false">
        <tiles:put name="fieldName" value="com.rssl.rsa.url"/>
        <tiles:put name="fieldDescription">
            <bean:message bundle="configureBundle" key="RSA.field.key.com.rssl.rsa.url"/>
        </tiles:put>
        <tiles:put name="fieldHint">
            <bean:message bundle="configureBundle" key="RSA.field.hint.com.rssl.rsa.url"/>
        </tiles:put>
        <tiles:put name="textSize" value="60"/>
        <tiles:put name="imagePath" value="${imagePath}"/>
        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
    </tiles:insert>
    <%--тайм аут--%>
    <tiles:insert definition="propertyField" flush="false">
        <tiles:put name="fieldName" value="com.rssl.rsa.connection.timeout"/>
        <tiles:put name="fieldDescription">
            <bean:message bundle="configureBundle" key="RSA.field.key.com.rssl.rsa.connection.timeout"/>
        </tiles:put>
        <tiles:put name="fieldHint">
            <bean:message bundle="configureBundle" key="RSA.field.hint.com.rssl.rsa.connection.timeout"/>
        </tiles:put>
        <tiles:put name="textSize" value="10"/>
        <tiles:put name="imagePath" value="${imagePath}"/>
        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
    </tiles:insert>
    <%--логин--%>
    <tiles:insert definition="propertyField" flush="false">
        <tiles:put name="fieldName" value="com.rssl.rsa.login"/>
        <tiles:put name="fieldDescription">
            <bean:message bundle="configureBundle" key="RSA.field.key.com.rssl.rsa.login"/>
        </tiles:put>
        <tiles:put name="fieldHint">
            <bean:message bundle="configureBundle" key="RSA.field.hint.com.rssl.rsa.login"/>
        </tiles:put>
        <tiles:put name="textSize" value="10"/>
        <tiles:put name="imagePath" value="${imagePath}"/>
        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
    </tiles:insert>
    <%--пароль--%>
    <tiles:insert definition="propertyField" flush="false">
        <tiles:put name="fieldName" value="com.rssl.rsa.password"/>
        <tiles:put name="fieldDescription">
            <bean:message bundle="configureBundle" key="RSA.field.key.com.rssl.rsa.password"/>
        </tiles:put>
        <tiles:put name="fieldHint">
            <bean:message bundle="configureBundle" key="RSA.field.hint.com.rssl.rsa.password"/>
        </tiles:put>
        <tiles:put name="textSize" value="10"/>
        <tiles:put name="imagePath" value="${imagePath}"/>
        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
    </tiles:insert>
    <%--время выгрузки--%>
    <tiles:insert definition="propertyField" flush="false">
        <tiles:put name="fieldName" value="com.rssl.rsa.job.unloading.time"/>
        <tiles:put name="fieldDescription">
            <bean:message bundle="configureBundle" key="RSA.field.key.com.rssl.rsa.job.unloading.time"/>
        </tiles:put>
        <tiles:put name="fieldHint">
            <bean:message bundle="configureBundle" key="RSA.field.hint.com.rssl.rsa.job.unloading.time"/>
        </tiles:put>
        <tiles:put name="textSize" value="30"/>
        <tiles:put name="imagePath" value="${imagePath}"/>
        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
    </tiles:insert>
    <%--скрывать сообщение офицеру безопасности--%>
    <tiles:insert definition="propertyField" flush="false">
        <tiles:put name="fieldName" value="com.rssl.rsa.verdict.comment.activity"/>
        <tiles:put name="fieldDescription">
            <bean:message bundle="configureBundle" key="RSA.field.key.com.rssl.rsa.verdict.comment.activity"/>
        </tiles:put>
        <tiles:put name="fieldHint">
            <bean:message bundle="configureBundle" key="RSA.field.hint.com.rssl.rsa.verdict.comment.activity"/>
        </tiles:put>
        <tiles:put name="fieldType" value="select"/>
        <tiles:put name="selectItems" value="false@Нет|true@Да"/>
        <tiles:put name="imagePath" value="${imagePath}"/>
        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
    </tiles:insert>
</table>

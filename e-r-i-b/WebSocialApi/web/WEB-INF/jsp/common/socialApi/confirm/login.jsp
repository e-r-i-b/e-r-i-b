<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/confirm/login">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmRequest" value="${form.confirmRequest}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <loginCompleted>false</loginCompleted>
            <tiles:insert definition="confirmStage" flush="false">
                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                <tiles:put name="autoStart" value="${form.confirmAutoStart}"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="messagesBundle" value="securityBundle"/>
    </tiles:insert>
</html:form>
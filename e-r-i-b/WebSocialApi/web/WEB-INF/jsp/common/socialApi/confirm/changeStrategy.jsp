<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<html:form action="/confirm/login">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmRequest" value="${form.confirmRequest}"/>
    <c:set var="anotherStrategy" value="${form.anotherStrategyAvailable}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <tiles:insert definition="confirmInfo" flush="false">
                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="messagesBundle" value="securityBundle"/>
    </tiles:insert>
</html:form>

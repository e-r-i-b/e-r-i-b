<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/cards/editEmailDelivery">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    <tiles:insert definition="payment" flush="false">
        <tiles:put name="data" type="string">
            ${form.html}

            <tiles:insert definition="confirmStage" flush="false">
                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                <tiles:put name="autoStart" value="${false}"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

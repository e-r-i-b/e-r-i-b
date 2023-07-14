<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<%--Ответ, в котором содержится подтверждение--%>
<html:form action="/private/basket/subscriptions/editSubscription">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.document)}"/>
    <tiles:insert definition="payment">
        <tiles:put name="data" type="string">
            <tiles:insert definition="confirmStage" flush="false">
                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
            </tiles:insert>
            <id>${form.document.id}</id>
        </tiles:put>
    </tiles:insert>
</html:form>
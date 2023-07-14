<%@page contentType="text/html;charset=windows-1251" language="java"%>

<%@taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean" %>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://rssl.com/tags"                        prefix="phiz" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c"    %>

<html:form action="/private/basket/subscriptions/claim">
    <c:set var="form"           value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.document)}"/>

    <tiles:insert definition="payment">

        <tiles:put name="data" type="string">
            <c:if test="${not empty confirmRequest}">
                <tiles:insert definition="confirmStage" flush="false">
                    <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>

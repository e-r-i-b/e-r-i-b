<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/tags/mobile" prefix="mobile" %>

<html:form action="/private/autotransfers/payment/info/print" onsubmit="return setEmptyAction(event)">
    <c:set var="form"     scope="request" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="payment"  scope="request" value="${form.payment}"/>

    <tiles:insert definition="atm">
        <tiles:put name="data" type="string">
            <c:choose>
                <c:when test="${'InternalCardsTransferLongOffer' == payment.type.simpleName}">
                    <jsp:include page="print-internal-autotransfer-check.jsp"/>
                </c:when>
                <c:when test="${'ExternalCardsTransferToOurBankLongOffer' == payment.type.simpleName}">
                    <jsp:include page="print-external-autotransfer-check.jsp"/>
                </c:when>
                <c:when test="${'ExternalCardsTransferToOtherBankLongOffer' == payment.type.simpleName}">
                    <jsp:include page="print-external-autotransfer-check.jsp"/>
                </c:when>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</html:form>
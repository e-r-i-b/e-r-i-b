<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<html:form action="/private/payments" onsubmit="return setEmptyAction(event)">
    <tiles:insert definition="paymentMain">
        <tiles:put name="showRates" value="true"/>
        <tiles:put name="mainmenu" value="Payments"/>
        <tiles:put name="enabledLink" value="false"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
        <tiles:put name="data" type="string">
            <div id="payments" class="positionStatic">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="data">
                        <c:set var="pageType" value="index" scope="request"/>
                        <c:set var="serviceURL" value="/private/payments/servicesPayments.do" scope="request"/>
                        <c:set var="providerURL" value="/private/payments/servicesPayments/edit.do" scope="request"/>

                        <c:if test="${phiz:impliesOperation('ListInvoicesOperation', 'PaymentBasketManagment')}">
                            <jsp:include page="/WEB-INF/jsp-sbrf/payments/invoices.jsp"/>
                        </c:if>
                        <jsp:include page="/WEB-INF/jsp-sbrf/payments/translations.jsp"/>
                        <jsp:include page="/WEB-INF/jsp-sbrf/payments/listServicesProviderSearch.jsp"/>
                        <jsp:include page="/WEB-INF/jsp-sbrf/payments/payments.jsp" />
                    </tiles:put>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>

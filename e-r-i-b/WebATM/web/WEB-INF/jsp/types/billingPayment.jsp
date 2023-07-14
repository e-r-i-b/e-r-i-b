<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:importAttribute ignore="true"/>

<%-- Услуго-поставщик
    serviceProvider - биллинговый услуго-поставщик
--%>

<%--@elvariable id="serviceProvider" type="com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider"--%>
<billing>${serviceProvider.billing.id}</billing>
<service>
    <id>${serviceProvider.id}</id>
    <name><c:out value="${serviceProvider.nameService}"/></name>
    <tiles:insert definition="imageType" flush="false">
        <tiles:put name="name" value="img"/>
        <tiles:put name="id" value="${serviceProvider.imageId}"/>
    </tiles:insert>
    <guid><c:out value="${serviceProvider.multiBlockRecordId}"/></guid>
</service>
<provider>
    <id>${serviceProvider.id}</id>
    <name><c:out value="${serviceProvider.name}"/></name>
    <tiles:insert definition="imageType" flush="false">
        <tiles:put name="name" value="img"/>
        <tiles:put name="id" value="${serviceProvider.imageId}"/>
    </tiles:insert>

    <%-- Графическая подсказка --%>
    <c:set var="imageHelpId" value="${serviceProvider.imageHelpId}"/>
    <c:if  test="${not empty imageHelpId}">
        <tiles:insert definition="imageType" flush="false">
            <tiles:put name="name" value="helpImg"/>
            <tiles:put name="id"   value="${imageHelpId}"/>
        </tiles:insert>
    </c:if>
    <c:if test="${not empty serviceProvider.subType}">
        <subType>${serviceProvider.subType}</subType>
    </c:if>
    <guid><c:out value="${serviceProvider.multiBlockRecordId}"/></guid>
</provider>
<c:if test="${not empty serviceProvider}">
    <c:set var="isIQW" value="${phiz:isIQWProvider(serviceProvider)}"/>
    <c:set var="autoPaymentSupported" value="${serviceProvider.autoPaymentSupportedInATM and
        ( (isIQW and phiz:impliesServiceRigid('CreateAutoPaymentPayment'))
        or (not isIQW and phiz:impliesServiceRigid('ClientCreateAutoPayment') ))}"/>
</c:if>
<autoPaymentSupported>${autoPaymentSupported}</autoPaymentSupported>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:importAttribute ignore="true"/>

<%-- Услуго-поставщик
    serviceProvider - биллинговый услуго-поставщик
--%>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<%--@elvariable id="serviceProvider" type="com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider"--%>
<billing>${serviceProvider.billing.id}</billing>
<service>
    <id>${serviceProvider.id}</id>
    <name><c:out value="${serviceProvider.nameService}"/></name>
    <tiles:insert definition="imageType" flush="false">
        <tiles:put name="name" value="img"/>
        <tiles:put name="id" value="${serviceProvider.imageId}"/>
    </tiles:insert>
</service>
<provider>
    <id>${serviceProvider.id}</id>
    <name><c:out value="${serviceProvider.name}"/></name>
    <tiles:insert definition="imageType" flush="false">
        <tiles:put name="name" value="img"/>
        <tiles:put name="id" value="${serviceProvider.imageId}"/>
    </tiles:insert>
</provider>
<c:set var="autoPaymentSupported" value="false"/>
<c:if test="${not empty serviceProvider}">
    <c:set var="isIQW" value="${phiz:isIQWProvider(serviceProvider)}"/>
    <c:set var="autoPaymentSupported" value="${serviceProvider.autoPaymentSupportedInApi and
        ( (isIQW and phiz:impliesServiceRigid('CreateAutoPaymentPayment'))
        or (form.mobileApiVersion >= 5.10 and not isIQW and phiz:impliesServiceRigid('ClientCreateAutoPayment') ))}"/>
</c:if>
<autoPaymentSupported>${autoPaymentSupported}</autoPaymentSupported>
<isBarSupported>${serviceProvider.barSupported}</isBarSupported>
<providerSubType>${serviceProvider.subType}</providerSubType>
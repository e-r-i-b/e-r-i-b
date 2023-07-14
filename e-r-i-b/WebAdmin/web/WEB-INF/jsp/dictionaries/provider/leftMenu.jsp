<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
<c:set var="id" value="${empty frm.id or frm.id==0 ? '$$newId' : frm.id}"/>
<c:set var="fieldId" value="${param['fieldId']}"/>
<c:set var="validatorId" value="${param['validatorId']}"/>
<c:if test="${phiz:impliesService('ServiceProvidersDictionaryManagement') || phiz:impliesService('ViewServiceProvidersDictionaryAdminService') || phiz:impliesService('ServiceProvidersDictionaryEmployeeService')}">
    <tiles:insert definition="leftMenuInset" flush="false">
        <tiles:put name="enabled" value="${submenu != 'Provider/Overview'}"/>
        <tiles:put name="action"  value="/dictionaries/provider/overview.do?id=${id}"/>
        <tiles:put name="text"    value="Общие сведения"/>
        <tiles:put name="title"   value="Общие сведения"/>
    </tiles:insert>
    <tiles:insert definition="leftMenuInset" flush="false">
        <tiles:put name="enabled" value="${submenu != 'Provider/SummRestrictions'}"/>
        <tiles:put name="action" value="/dictionaries/provider/summRestrictions.do?id=${id}"/>
        <tiles:put name="text"><bean:message key="label.provider.summRestr" bundle="providerBundle"/></tiles:put>
        <tiles:put name="title"><bean:message key="label.provider.summRestr" bundle="providerBundle"/></tiles:put>
    </tiles:insert>
    <tiles:insert definition="leftMenuInset" flush="false">
        <tiles:put name="enabled" value="${submenu != 'Provider/Regions'}"/>
        <tiles:put name="action"  value="/dictionaries/provider/regions.do?id=${id}"/>
        <tiles:put name="text"    value="Регионы"/>
        <tiles:put name="title"   value="Регионы"/>
    </tiles:insert>

    <c:set var="serviceProvider" value="${phiz:getServiceProviderConsiderMultiBlock(frm.id)}"/>

    <c:if test="${serviceProvider.type == 'BILLING'}">
        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="enabled" value="${submenu != 'Provider/PaymentServices'}"/>
            <tiles:put name="action"  value="/dictionaries/provider/payment/services.do?id=${id}"/>
            <tiles:put name="text">
                <bean:message key="label.payment.services" bundle="providerBundle"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message key="label.payment.services" bundle="providerBundle"/>
            </tiles:put>
        </tiles:insert>

        <c:set var="commissionAvailable" value="${phiz:isPaymentCommissionAvailableByProvider(serviceProvider)}"/>
        <c:if test="${not empty frm.id and frm.id>0 and !commissionAvailable}">
            <tiles:insert definition="leftMenuInset" flush="false">
                <tiles:put name="enabled" value="${submenu != 'Provider/Comission'}"/>
                <tiles:put name="action"  value="/dictionaries/provider/comission.do?id=${id}"/>
                <tiles:put name="text"    value="Комиссия"/>
                <tiles:put name="title"   value="Комиссия"/>
            </tiles:insert>
        </c:if>

        <c:if test="${serviceProvider.accountType == 'CARD' || serviceProvider.accountType == 'ALL'}">
            <tiles:insert definition="leftMenuInset" flush="false">
                <tiles:put name="enabled" value="${submenu != 'Provider/AutoPaySetting'}"/>
                <tiles:put name="action"  value="/dictionaries/provider/autopayment.do?id=${id}"/>
                <tiles:put name="text">
                    <bean:message key="lable.autopayment.setting" bundle="providerBundle"/>
                </tiles:put>
                <tiles:put name="title">
                    <bean:message key="lable.autopayment.setting" bundle="providerBundle"/>
                </tiles:put>
            </tiles:insert>
        </c:if>
    </c:if>
    <c:if test="${serviceProvider != null and serviceProvider.type != 'TAXATION'}">
        <tiles:insert definition="leftMenuInsetGroup">
            <tiles:put name="text" value="Реквизиты платежа"/>
            <tiles:put name="name" value="provider_fields"/>
            <tiles:put name="enabled" value="${submenu != 'Provider/Fields' and submenu != 'Provider/Fields/Validators'}"/>
            <tiles:put name="data">
                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${submenu != 'Provider/Fields'}"/>
                    <tiles:put name="action" value="/dictionaries/provider/fields/list.do?id=${id}"/>
                    <tiles:put name="text" value="Список полей"/>
                    <tiles:put name="title" value="Список полей"/>
                    <tiles:put name="parentName" value="provider_fields"/>
                </tiles:insert>
                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${submenu != 'Provider/Fields/Validators'}"/>
                    <tiles:put name="action" value="/dictionaries/provider/fields/validators/list.do?id=${id}"/>
                    <tiles:put name="text" value="Список валидаторов"/>
                    <tiles:put name="title" value="Список валидаторов"/>
                    <tiles:put name="parentName" value="provider_fields"/>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
    </c:if>

</c:if>

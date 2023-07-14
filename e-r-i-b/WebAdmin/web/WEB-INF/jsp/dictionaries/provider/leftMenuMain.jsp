<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset" service="FacilitatorsDictionaryManagement">
    <tiles:put name="enabled" value="${submenu != 'Facilitators'}"/>
    <tiles:put name="action"  value="/private/dictionaries/facilitator/list.do"/>
    <tiles:put name="text"    value="Справочник фасилитаторов"/>
    <tiles:put name="title"   value="Справочник фасилитаторов"/>
</tiles:insert>
<c:if test="${phiz:impliesService('ServiceProvidersDictionaryManagement') || phiz:impliesService('ViewServiceProvidersDictionaryAdminService') || phiz:impliesServiceRigid('ServiceProvidersDictionaryEmployeeService')}">
    <tiles:insert definition="leftMenuInset">
        <tiles:put name="enabled" value="${submenu != 'Providers'}"/>
        <tiles:put name="action"  value="/private/dictionaries/provider/list.do"/>
        <tiles:put name="text"    value="Справочник поставщиков услуг"/>
        <tiles:put name="title"   value="Справочник поставщиков услуг"/>
    </tiles:insert>
</c:if>
<tiles:insert definition="leftMenuInset" service="RegionDictionaryManagement">
   <tiles:put name="enabled" value="${submenu != 'Regions'}"/>
   <tiles:put name="action"  value="/private/dictionary/regions/list.do"/>
   <tiles:put name="text"    value="Справочник регионов"/>
   <tiles:put name="title"   value="Справочник регионов"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="PaymentServicesManagement">
    <tiles:put name="enabled" value="${submenu != 'PaymentServices'}"/>
    <tiles:put name="action"  value="/private/dictionaries/paymentService/list.do"/>
    <tiles:put name="text"    value="Справочник услуг"/>
    <tiles:put name="title"   value="Справочник услуг"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="OldPaymentServicesManagement">
    <tiles:put name="enabled" value="${submenu != 'PaymentServicesOld'}"/>
    <tiles:put name="action"  value="/dictionaries/oldPaymentService/list.do"/>
    <tiles:put name="text"    value="Справочник услуг для api"/>
    <tiles:put name="title"   value="Справочник услуг для api"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="UnloadJBTManagement" operation="UnloadJBTOperation">
    <tiles:put name="enabled" value="${submenu != 'JBT'}"/>
    <tiles:put name="action" value="/dictionaries/jbt/unloading.do"/>
    <tiles:put name="text"   value="Выгрузка ЖБТ"/>
    <tiles:put name="title"  value="Выгрузка ЖБТ"/>
</tiles:insert>
<c:if test="${phiz:impliesService('KBKAdminManagement') || phiz:impliesService('KBKEmployeeManagement')}">
    <tiles:insert definition="leftMenuInset">
        <tiles:put name="enabled" value="${submenu != 'KBK'}"/>
        <tiles:put name="action" value="/private/dictionary/kbk/list.do"/>
        <tiles:put name="text"   value="Справочник КБК"/>
        <tiles:put name="title"  value="Справочник КБК"/>
    </tiles:insert>
</c:if>
<tiles:insert definition="leftMenuInset" operation="ReplicateServiceProvidersOperation" service="*">
    <tiles:put name="enabled" value="${submenu != 'BackgroundProviderReplication'}"/>
    <tiles:put name="action" value="/dictionaries/provider/replication/task/list.do"/>
    <tiles:put name="text"   value="Фоновая загрузка поставщиков"/>
    <tiles:put name="title"  value="Справочник КБК"/>
</tiles:insert>

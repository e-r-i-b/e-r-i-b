<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset" service="AdvertisingBlockManagment">
	<tiles:put name="enabled" value="${submenu != 'AdvertisingBlock'}"/>
	<tiles:put name="action"  value="/advertising/block/list.do"/>
	<tiles:put name="text"    value="Рекламный баннер"/>
	<tiles:put name="title"   value="Рекламный баннер"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="QuickPaymentPanelService">
	<tiles:put name="enabled" value="${submenu != 'QuickPaymentPanel'}"/>
	<tiles:put name="action"  value="/quick/pay/list.do"/>
	<tiles:put name="text"    value="Панель быстрой оплаты"/>
	<tiles:put name="title"   value="Панель быстрой оплаты"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="InformationMessageService">
	<tiles:put name="enabled" value="${submenu != 'InformMessages'}"/>
	<tiles:put name="action"  value="/inform/message/list.do"/>
	<tiles:put name="text"    value="Опубликованные сообщения"/>
	<tiles:put name="title"   value="Опубликованные сообщения"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="InformationMessageService">
	<tiles:put name="enabled" value="${submenu != 'TemplateInformMessages'}"/>
	<tiles:put name="action"  value="/inform/template/list.do"/>
	<tiles:put name="text"    value="Список шаблонов сообщений"/>
	<tiles:put name="title"   value="Список шаблонов сообщений"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="OfferNotificationManagment">
    <tiles:put name="enabled" value="${submenu != 'OfferNotification'}"/>
    <tiles:put name="action"  value="/offers/notification/list.do"/>
    <tiles:put name="text"    value="Уведомления о предложениях"/>
    <tiles:put name="title"   value="Уведомления о предложениях"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="EditEmailDeliverySettingsOperation">
    <tiles:put name="enabled" value="${submenu != 'EditEmailDeliverySettings'}"/>
    <tiles:put name="action"  value="/cards/delivery/edit"/>
    <tiles:put name="text"><bean:message bundle="mailDeliverySettingsBundle" key="left.menu.title"/></tiles:put>
    <tiles:put name="title"><bean:message bundle="mailDeliverySettingsBundle" key="left.menu.title"/></tiles:put>
</tiles:insert>

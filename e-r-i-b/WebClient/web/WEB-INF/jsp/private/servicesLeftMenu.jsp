<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<tiles:insert definition="leftMenuInset" service="ChangePersonPassword">
	<tiles:put name="enabled" value="${submenu != 'changePassword'}"/>
	<tiles:put name="action"  value="/private/service"/>
	<tiles:put name="text"    value="������� ������"/>
	<tiles:put name="title"   value="������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ChangeUserSubscriptionList">
	<tiles:put name="enabled" value="${submenu != 'Events'}"/>
	<tiles:put name="action"  value="/private/notification/events"/>
	<tiles:put name="text"    value="��������� ����������"/>
	<tiles:put name="title"   value="��������� ����������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="CertificationOperation">
	<tiles:put name="enabled" value="${submenu != 'CertificateList'}"/>
	<tiles:put name="action"  value="/private/certification/certificate/list"/>
	<tiles:put name="text"    value="C����������"/>
	<tiles:put name="title"   value="C����������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="CertificationOperation">
	<tiles:put name="enabled" value="${submenu != 'DemandList'}"/>
	<tiles:put name="action"  value="/private/certification/list"/>
	<tiles:put name="text"    value="������ �� ����������"/>
	<tiles:put name="title"   value="������ �� ����������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ActivateNewPasswordCard">
	<tiles:put name="enabled" value="${submenu != 'activateCard'}"/>
	<tiles:put name="action"  value="/private/activatecard"/>
	<tiles:put name="text"    value="������������ ����� ������"/>
	<tiles:put name="title"   value="������������ ����� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup" service="SmsBanking">
	<tiles:put name="text"    value="SMS-�������"/>
	<tiles:put name="name"    value="SmsBanking"/>
	<tiles:put name="data">
		<tiles:insert definition="leftMenuInset" flush="false">
			<tiles:put name="enabled" value="${submenu != 'SmsTemplates'}"/>
			<tiles:put name="action" value="/private/smstemplates"/>
			<tiles:put name="text" value="SMS-�������"/>
			<tiles:put name="title" value="SMS-�������"/>
			<tiles:put name="parentName" value="SmsBanking"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" flush="false">
			<tiles:put name="enabled" value="${submenu != 'SmsPseudonyms'}"/>
			<tiles:put name="action" value="/private/smsbanking/pseudonyms"/>
			<tiles:put name="text" value="SMS-����������"/>
			<tiles:put name="title" value="SMS-����������"/>
			<tiles:put name="parentName" value="SmsBanking"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="PaymentList" flush="false">
			<tiles:put name="enabled" value="${submenu != 'PaymentList/status=smsbanking'}"/>
			<tiles:put name="action" value="/private/payments/common.do?status=smsbanking"/>
			<tiles:put name="text" value="������ ��������"/>
			<tiles:put name="title" value="������ ��������"/>
			<tiles:put name="parentName" value="SmsBanking"/>
		</tiles:insert>
	</tiles:put>

</tiles:insert>
<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'About'}"/>
	<tiles:put name="action"  value="/private/about"/>
	<tiles:put name="text"    value="� ���������"/>
	<tiles:put name="title"   value="� ���������"/>
</tiles:insert>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset" service="CertDemandControl">
		<tiles:put name="enabled" value="${submenu != 'DemandList'}"/>
		<tiles:put name="action"  value="/certification/list.do"/>
		<tiles:put name="text"    value="Список запросов"/>
		<tiles:put name="title"   value="Список запросов"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="CertDemandControl">
		<tiles:put name="enabled" value="${submenu != 'CertificateList'}"/>
		<tiles:put name="action"  value="/certification/certificate/list.do"/>
		<tiles:put name="text"    value="Список сертификатов"/>
		<tiles:put name="title"   value="Список сертификатов"/>
</tiles:insert>


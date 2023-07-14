<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'List'}"/>
	<tiles:put name="action"  value="/passwordcards/list.do"/>
	<tiles:put name="text"    value="������ ����"/>
	<tiles:put name="title"   value="������ ���� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="RequestPasswordCardsOperation">
	<tiles:put name="enabled" value="${submenu != 'CreateRequest'}"/>
	<tiles:put name="action"  value="/passwordcards/createRequest.do"/>
	<tiles:put name="text"    value="��������� ���� ������"/>
	<tiles:put name="title"   value="�������� ������ �� ������ ���� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="CreateCardsPrintingRequestOperation">
	<tiles:put name="enabled" value="${submenu != 'createCardsPrintingRequest'}"/>
	<tiles:put name="action"  value="/passwordcards/createCardsPrintingRequest.do"/>
	<tiles:put name="text"    value="������ �� ������ ���� ������"/>
	<tiles:put name="title"   value="�������� ������ �� ������ ���� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="UploadCardsAnswerOperation">
	<tiles:put name="enabled" value="${submenu != 'UploadResponse'}"/>
	<tiles:put name="action"  value="/passwordcards/uploadResponse.do"/>
	<tiles:put name="text"    value="�������� ������������ ������"/>
	<tiles:put name="title"   value="�������� ������ �� ������ ���� ������"/>
</tiles:insert>


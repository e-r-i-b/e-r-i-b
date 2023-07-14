<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="services">
	<tiles:put name="needSave" type="string" value="false"/>
	<!-- ��������� -->
	<tiles:put name="pageTitle" type="string">������</tiles:put>

	<!--����-->
	<tiles:put name="menu" type="string">
	</tiles:put>

	<!--������ ��������� ��������-->
	<tiles:put name="data" type="string">
		<table cellspacing="0" cellpadding="0" width="100%">
			<phiz:service serviceId="ChangePersonPassword" name="��������� ��������">
				<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<phiz:menuLink action="/private/service" id="m1" serviceId="ChangePersonPassword">
					������� ������
				</phiz:menuLink>
				<br/>
				<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<phiz:menuLink action="/private/notification/events" id="m2"
				               serviceId="ChangeUserSubscriptionList">
					������ ��������� ����������
				</phiz:menuLink>
				<br/>
				<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<phiz:menuLink action="/private/activatecard" id="m3">������������ ����� ������</phiz:menuLink>
			</phiz:service>
		</table>
	</tiles:put>
</tiles:insert>

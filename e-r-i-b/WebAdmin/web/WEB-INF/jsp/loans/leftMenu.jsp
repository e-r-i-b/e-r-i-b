<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 10.11.2006
  Time: 16:12:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'LoanClaims'}"/>
	<tiles:put name="action"  value="/loans/claims/list.do"/>
	<tiles:put name="text"    value="������"/>
	<tiles:put name="title"   value="������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'LoanClaimsArchive'}"/>
	<tiles:put name="action"  value="/loans/claims/archiveList.do"/>
	<tiles:put name="text"    value="����� ������"/>
	<tiles:put name="title"   value="����� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="LoanProducts">
	<tiles:put name="enabled" value="${submenu != 'LoanProducts'}"/>
	<tiles:put name="action"  value="/loans/products/list.do"/>
	<tiles:put name="text"    value="��������� ��������"/>
	<tiles:put name="title"   value="��������� ��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="LoanKinds">
	<tiles:put name="enabled" value="${submenu != 'LoanKinds'}"/>
	<tiles:put name="action"  value="/loans/kinds/list.do"/>
	<tiles:put name="text"    value="���� ��������"/>
	<tiles:put name="title"   value="���� ��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="LoanOffices">
	<tiles:put name="enabled" value="${submenu != 'LoanOffices'}"/>
	<tiles:put name="action"  value="/private/dictionary/offices/loans.do"/>
	<tiles:put name="text"    value="��������� �����"/>
	<tiles:put name="title"   value="��������� �����"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="LoanStateMessages">
	<tiles:put name="enabled" value="${submenu != 'StateMess'}"/>
	<tiles:put name="action"  value="/loans/statemess/list.do"/>
	<tiles:put name="text"    value="��������� � ����������� ������������ ������ � �����"/>
	<tiles:put name="title"   value="��������� � ����������� ������������ ������ � �����"/>
</tiles:insert>
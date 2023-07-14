<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'ChangePresence'}"/>
	<tiles:put name="action"  value="/bankcells/presence.do"/>
	<tiles:put name="text"    value="��������� �����"/>
	<tiles:put name="title"   value="�������������� ������� ������ � ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'CellTypes' && submenu != 'CellTypeTermsOfLease'}"/>
	<tiles:put name="action"  value="/bankcells/cells.do"/>
	<tiles:put name="text"    value="������� ������"/>
	<tiles:put name="title"   value="���������� �������� ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'TermsOfLease'}"/>
	<tiles:put name="action"  value="/bankcells/terms.do"/>
	<tiles:put name="text"    value="����� ������"/>
	<tiles:put name="title"   value="���������� ������ ������ ������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'OfficeCellTypes'}"/>
	<tiles:put name="action"  value="/bankcells/officeCells.do"/>
	<tiles:put name="text"    value="����� � ������"/>
	<tiles:put name="title"   value="����� � ������"/>
</tiles:insert>

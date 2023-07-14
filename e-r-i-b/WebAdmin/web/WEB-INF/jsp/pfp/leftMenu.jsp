<%--
  Created by IntelliJ IDEA.
  User: Pakhomova
  Date: 15.07.2008
  Time: 17:57:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"  %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset" service="ListPFPPassingJournalService">
	<tiles:put name="enabled" value="${submenu != 'journalPFP'}"/>
	<tiles:put name="action"  value="/journal/pfp/passingPfpJournal.do"/>
	<tiles:put name="text"    value="Æóðíàë ÏÔÏ"/>
	<tiles:put name="title"   value="Æóðíàë ÏÔÏ"/>
</tiles:insert>
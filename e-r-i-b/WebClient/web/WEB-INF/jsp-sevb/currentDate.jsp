<%--
  User: IIvanova
  Date: 12.04.2006
  Time: 18:43:07
--%>
<%@ page import="java.util.*"%>
<%@ page import="com.rssl.phizic.utils.*"%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<p class="infoTitle backTransparent">
  <br/>
  <br/>
	<c:set var="currentDate" value="${phiz:currentDate()}"/>
  Сегодня: <span class="menuInsertText backTransparent"><bean:write name="currentDate" property="time" format="dd.MM.yyyy"/></span>
  <table class="linkTab backTransparent" width="80%"><tr><td>&nbsp;</td></tr></table>
</p>
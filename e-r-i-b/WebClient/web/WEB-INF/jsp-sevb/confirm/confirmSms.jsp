<%--
  Created by IntelliJ IDEA.
  User: Zhuravleva
  Date: 26.01.2009
  Time: 17:02:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="confirmSms">
	<tiles:put type="page" name="data" value="/WEB-INF/jsp-sevb/confirm/confirmSmsData.jsp"/>
</tiles:insert></html>
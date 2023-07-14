
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>

<%--
creditProfile - дата запроса последнего отчета
--%>
<c:set var="globalPath" value="${globalUrl}/commonSkin/images"/>
<c:set var="onClickAction">createCommandButton('button.unloadPDF','').click('', false)</c:set>
<a href="#" class="b-get-cred-hist-download" onclick="${onClickAction}" >
    <img src="${globalPath}/PDF.png" border="0" class="float"/>
    <bean:message key="label.credit.history.report.download" bundle="creditHistoryBundle"/> <c:out value="${phiz:formatDateToMonthInWords(creditProfile.lastReport)}"/>
</a>

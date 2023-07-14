<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>

<%--
  resourceAbstract выписка по кредиту
--%>
<div class="mini-abstract">
    <sl:collection id="schedule" model="no-pagination" name="resourceAbstract" property="schedules" collectionSize="3">
        <sl:collectionItem styleClass="mini-abstract-name">
            Погашение кредита
        </sl:collectionItem>
        <sl:collectionItem styleClass="mini-abstract-date">
            ${phiz:formatDateDependsOnSysDate(schedule.date, false, false)}
        </sl:collectionItem>
        <sl:collectionItem styleClass="mini-abstract-amount">
            ${phiz:formatAmount(schedule.totalPaymentAmount)}
        </sl:collectionItem>
    <sl:collectionItem>&nbsp;</sl:collectionItem>
    </sl:collection>
</div>
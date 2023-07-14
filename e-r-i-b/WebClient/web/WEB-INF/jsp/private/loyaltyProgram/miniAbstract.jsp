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
     loyaltyProgramOperations - список операций по программе лояльности
--%>

<div class="mini-abstract">
    <sl:collection id="schedule" model="no-pagination" name="loyaltyProgramOperations"  collectionSize="3">
        <sl:collectionItem styleClass="align-left mini-abstract-date">
           ${phiz:formatDateDependsOnSysDate(schedule.operationDate, true, false)}
        </sl:collectionItem>
        <sl:collectionItem width="150px">
            <div class="miniPartnerBonus">
                <span class="word-wrap">${schedule.operationInfo}</span>
            </div>
        </sl:collectionItem>
        <sl:collectionItem>
            <c:choose>
                <c:when test="${schedule.operationKind == 'debit'}">
                    <span class="plus-amount">
                        +${phiz:getStringInNumberFormat(schedule.operationalBalance)}
                    </span>
                </c:when>
                <c:otherwise>
                    &minus;${phiz:getStringInNumberFormat(schedule.operationalBalance)}
                </c:otherwise>
            </c:choose>
        </sl:collectionItem>
    </sl:collection>
</div>
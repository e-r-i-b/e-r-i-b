<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:importAttribute/>

<%--
  pfrClaims список заявок на получение выписки из ПФР
--%>
<div class="mini-abstract">
    <sl:collection id="claim" name="pfrClaims" model="no-pagination" collectionSize="3" >

        <%--@elvariable id="claim" type="com.rssl.phizic.business.documents.PFRStatementClaim"--%>
        <sl:collectionItem title="">
            <c:choose>
                <c:when test="${claim.state.code == 'DRAFT'}">
                    <c:set var="claimActionUrl" value="/private/payments/payment"/>
                </c:when>
                <c:when test="${claim.state.code == 'SAVED'}">
                    <c:set var="claimActionUrl" value="/private/payments/confirm"/>
                </c:when>
                <c:otherwise>
                    <c:set var="claimActionUrl" value="/private/payments/view"/>
                </c:otherwise>
            </c:choose>
            <phiz:link action="${claimActionUrl}" serviceId="PFRService">
                <phiz:param name="id" value="${claim.id}"/>
                запрос на получение выписки
            </phiz:link>
        </sl:collectionItem>
        <sl:collectionItem title="">
            <span class="text-gray">${phiz:formatDateDependsOnSysDate(claim.dateCreated, true, false)}&nbsp;</span>
        </sl:collectionItem>
        <sl:collectionItem title="">
            <bean:message key="state.${claim.state.code}" bundle="pfrBundle"/>

            <c:if test="${claim.state.code=='EXECUTED'}">
                &nbsp;
                (<a href="" onclick="return openStatement(${claim.id});">посмотреть выписку</a>)
            </c:if>
        </sl:collectionItem>
    </sl:collection>
</div>


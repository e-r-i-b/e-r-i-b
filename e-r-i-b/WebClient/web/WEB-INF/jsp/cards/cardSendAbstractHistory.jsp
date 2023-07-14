<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%--
  Created by IntelliJ IDEA.
  User: bogdanov
  Date: 11.06.2015
  Time: 11:15:47
--%>
<tiles:importAttribute/>

<c:set var="bundle" value="${messagesBundle}"/>
<c:if test="${empty bundle || bundle==''}">
    <c:set var="bundle" value="commonBundle"/>
</c:if>

<html:form action="/private/async/cards/sendAbstract" onsubmit="return setEmptyAction(event)">
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="cardLink" value="${form.cardLink}"/>
    <c:set var="card" value="${cardLink.card}"/>

    <tiles:insert definition="simpleTableTemplate" flush="false">
        <tiles:put name="grid">
            <c:set var="url" value="/private/payments/view.do"/>
            <sl:collection id="history" property="history" model="simple-pagination"  styleClass="rowOver">
                <sl:collectionItem styleClass="reportTblCell" title="Период отчета" width="67%" action="${url}?id=${history[0]}">
                   ${history[2]}
                </sl:collectionItem>
                <sl:collectionItem title="Дата заказа" width="33%" action="${url}?id=${history[0]}">
                    ${history[1]}
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
        <tiles:put name="isEmpty" value="${history.size == 0}"/>
        <tiles:put name="emptyMessage">Вы не заказывали ни один отчет.</tiles:put>
    </tiles:insert>
 </html:form>

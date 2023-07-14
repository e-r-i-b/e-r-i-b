<%--
  User: bogdanov
  Date: 08.02.2012
  Time: 14:30:10
  График платежей по подписке на автоплатеж.
  --%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="items" value="${form.scheduleItems}"/>
<c:set var="contextName" value="${phiz:loginContextName()}"/>
<script type="text/javascript">
   function showHintBlock(id)
   {
      var autoPaymentHint = $('#autoPaymentStatusShower');
      var parentText = $('#statusDesc'+id);
      autoPaymentHint.css("margin-left", parentText.width()/2 - autoPaymentHint.width()/2);
   }
</script>
<c:if test="${not empty items}">
<sl:collection id="item" name="items" model="${modelPaymentGraphic}">
    <sl:collectionItem title="Дата" styleTitleClass="align-center" styleClass="tableFirstElement">
        <c:choose>
            <c:when test="${contextName eq 'PhizIC'}">
                <c:set var="payDate">${phiz:formatDateDependsOnSysDate(item.date, true, false)}</c:set>
            </c:when>
            <c:otherwise>
                 <c:set var="payDate"><fmt:formatDate value="${item.date.time}" pattern="dd.MM.yyyy HH:mm:ss"/></c:set>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${item.status == 'DONE' and not empty fromEmployee}">
                <a href="${paymentInfoUrl}<bean:write name='item' property="number" ignore="true"/>&subscriptionId=<bean:write name='form' property="id" ignore="true"/>"><c:out value="${payDate}"/></a>
            </c:when>
            <c:when test="${item.status == 'DONE'}">
                <a href="${paymentInfoUrl}<bean:write name='item' property="number" ignore="true"/>&subscriptionId=<bean:write name='form' property="id" ignore="true"/>"><c:out value="${payDate}"/></a>
            </c:when>
            <c:otherwise>
                <c:out value="${payDate}"/>
            </c:otherwise>
        </c:choose>
    </sl:collectionItem>
    <sl:collectionItem title="Сумма" styleClass="align-right" styleTitleClass="align-center">
        <c:if test="${not empty item.summ}">
            <bean:write name="item" property="summ" format="0.00"/>&nbsp;${phiz:getCurrencySign("RUB")}
        </c:if>
    </sl:collectionItem>
    <sl:collectionItem title="Комиссия" styleClass="align-right" styleTitleClass="align-center">
        <c:if test="${not empty item.commission}">
            <bean:write name="item" property="commission" format="0.00"/>&nbsp;${phiz:getCurrencySign("RUB")}
        </c:if>
    </sl:collectionItem>
    <sl:collectionItem title="Статус" styleClass="align-left" styleTitleClass="align-center">
        <c:if test="${not empty item.status}">
            <script type="text/javascript">
               var textHint${item.number} = "<div class=\"floatMessageHeader\"></div><div class=\"layerFonBlock\">${item.status.description}</div>";
            </script>
            <div id="statusDesc${item.number}" class="hintText"
                 onmouseover="showHint('statusDesc${item.number}', 'autoPaymentStatusShower', 'pointer', textHint${item.number});showHintBlock('${item.number}') "
                 onmouseout="hideLayer('autoPaymentStatusShower');">
                <bean:message key="payment.autoSub.payment.state.${item.status}" bundle="paymentsBundle"/>
            </div>
        </c:if>
        <c:if test="${not empty item.rejectionCause}">
            <div class="rejectionCause"><bean:write name="item" property="rejectionCause"/></div>
        </c:if>
    </sl:collectionItem>
</sl:collection>
</c:if>

<div id="autoPaymentStatusShower" class="layerFon hintWindow autoPaymentHint">
</div>
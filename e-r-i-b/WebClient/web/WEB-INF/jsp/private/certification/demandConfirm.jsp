<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 17.11.2006
  Time: 17:01:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html:form action="/private/certification/confirm" onsubmit="return setEmptyAction(event);">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="person" value="${form.person}"/>
<c:set var="demand" value="${form.demand}"/>
<c:set var="date" value="${demand.issueDate}"/>
<c:set var="status" value="${demand.status}"/>
<c:set var="confirmRequest" value="${phiz:currentConfirmRequest(demand)}"/>

<tiles:insert definition="certificationMain">
	<tiles:put name="submenu" type="string" value="DemandList"/>
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="confirmCertDemand.title" bundle="certificationBundle"/>
    </tiles:put>
	<tiles:put name="menu" type="string">
        <nobr>
		<span id="button_row">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.cancel"/>
				<tiles:put name="commandHelpKey" value="button.cancel.help"/>
				<tiles:put name="bundle"         value="certificationBundle"/>
				<tiles:put name="action"         value="/private/certification/list.do"/>
			</tiles:insert>
		</span>
		<nobr>
	 </tiles:put>

<tiles:put name="data" type="string">
	<tiles:insert definition="paymentForm" flush="false">
	<tiles:put name="id" value="certificationClaim"/>
	<tiles:put name="name" value="Заявка на сертификат"/>
	<tiles:put name="description" value="Подача в банк заявки на выпуск сертификата."/>
	<tiles:put name="buttons">
		 <tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.confirm"/>
			<tiles:put name="commandHelpKey" value="button.confirm.help"/>
			<tiles:put name="image" value="iconSm_confirm.gif"/>
			<tiles:put name="bundle" value="certificationBundle"/>
		 </tiles:insert>
	</tiles:put>
	<tiles:put name="data">
	   <tr>
		  <td class="Width120 LabelAll"><b><bean:message key="label.FIO" bundle="certificationBundle" /></b></td>
		  <td>
			  <bean:write name="person" property="surName"/>&nbsp;
				<bean:write name="person" property="firstName"/>&nbsp;
				<bean:write name="person" property="patrName"/>
		  </td>
	   </tr>
	   <tr>
		  <td class="Width120 LabelAll" nowrap="true"><b><bean:message key="label.id" bundle="certificationBundle" /></b></td>
		  <td>
				<bean:write name="form" property="demand.certDemandCryptoId"/>
			</td>
	   </tr>
	   <tr>
		  <td class="Width120 LabelAll" nowrap="true"><b><bean:message key="label.date" bundle="certificationBundle" /></b></td>
		  <td>
				<bean:write name="form" property="demand.issueDate.time" format="dd.MM.yyyy HH:mm:ss"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll" nowrap="true">
				<b><bean:message key="label.status" bundle="certificationBundle" />&nbsp;</b>
			</td>
			<td>
				 <c:choose>
				   <c:when test="${status=='E'}">
					   <nobr>Введен</nobr>
				   </c:when>
				   <c:when test="${status=='N'}">
					   <nobr>Новый</nobr>
				   </c:when>
				   <c:when test="${status=='S'}">
					   <nobr>Отправлен</nobr>
				   </c:when>
				   <c:when test="${status=='P'}">
					   <nobr>Обрабатывается</nobr>
				   </c:when>
				   <c:when test="${status=='G'}">
					   <nobr>Сертификат выдан</nobr>
				   </c:when>
				   <c:when test="${status=='R'}">
					   <nobr>Отказан</nobr>
				   </c:when>
				   <c:when test="${status=='I'}">
					   <nobr>Сертификат установлен</nobr>
				   </c:when>
				   <c:when test="${status=='D'}">
					   <nobr>Сертификат удален</nobr>
				   </c:when>
			   </c:choose>
			</td>
		</tr>
		</tiles:put>
		<tiles:put name="confirmInfo">
		<c:choose>
		<c:when test="${fn:contains(confirmRequest.strategyType, 'crypto') && fn:contains(confirmRequest.errorMessage, 'Не установлен активный сертификат') }">
			<tiles:insert  definition="confirm_none" flush="false">
				<tiles:put name="confirmRequest" beanName="confirmRequest"/>
				<tiles:put name="message" value="Проверьте правильность заполнения полей документа и нажмите кнопку \"Подтвердить\", чтобы передать документ в банк на исполнение."/>
			</tiles:insert>
		</c:when>
		<c:otherwise>
			<c:if test="${!confirmRequest.error}">
				<c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
				<tiles:insert  definition="${confirmTemplate}" flush="false">
					<tiles:put name="confirmRequest" beanName="confirmRequest"/>
					<tiles:put name="message" value="Проверьте правильность заполнения полей документа и нажмите кнопку \"Подтвердить\", чтобы передать документ в банк на исполнение."/>
				</tiles:insert>
			</c:if>
		</c:otherwise>
		</c:choose>
		</tiles:put>
		<tiles:put name="alignTable" value="center"/>
</tiles:insert>
<table>
	<tr id="error_row">
		<td colspan="2" id="error_cell"class="error" style="text-align:center;"></td>
	</tr>
</table>

 </tiles:put>
 </tiles:insert>
</html:form>
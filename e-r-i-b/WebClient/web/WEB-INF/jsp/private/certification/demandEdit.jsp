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

<html:form action="/private/certification/edit" onsubmit="return setEmptyAction(event);">                                                                              
		<c:set var="form" value="${EditCertDemandCertificationForm}"/>
		<c:set var="person" value="${form.person}"/>
	    <c:set var="date" value="${form.date}"/>
	    <c:set var="install" value="${form.install}"/>
		<c:choose>
			<c:when test="${!empty form.demand}">
				<c:set var="status" value="${form.demand.status}"/>
			</c:when>
			<c:otherwise>
				<c:set var="status" value="N"/>
			</c:otherwise>
		</c:choose>

<body <c:if test="${install}"> onload="installCert()";</c:if>>
<tiles:insert definition="certificationMain">
	<tiles:put name="submenu" type="string" value="DemandList"/>
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="editCertDemand.title" bundle="certificationBundle"/>
    </tiles:put>
	<tiles:put name="menu" type="string">
        <nobr>
		<!--<span id="button_row">-->
			<!---->
		<!--</span>-->
			<tiles:insert definition="clientButton" flush="false">
				 <tiles:put name="commandTextKey"     value="button.cancel"/>
				 <tiles:put name="commandHelpKey" value="button.cancel.help"/>
				 <tiles:put name="bundle"         value="certificationBundle"/>
				 <tiles:put name="action"         value="/private/certification/list.do"/>
			</tiles:insert>
		<nobr>
	 </tiles:put>
      <tiles:put name="data" type="string">
	<%@ include file="../../crypto/agavaStart.jsp" %>
<script type="text/javascript">
	var action = null;
	function cryptoStarted()
	{
		if(action == "request")
		{
			createRequestStart();
			return;
		}
		if(action == "install")
		{
			installCertStart();
			return;
		}
		if(action == "print")
		{
			printCertStart('${form.demand.certDemandCryptoId}');
			return;
		}
		if(action == "preview")
		{
			previewCertStart('${form.demand.certDemandCryptoId}');
			return;
		}

		alert("Не найдено действие");
	}

	function createRequest()
	{
		action = "request";
		startCrypto();
	}

	function installCert()
	{
		action = "install";
		startCrypto();
	}
	function printCert()
	{
		action = "print";
		startCrypto();
	}
	function previewCert()
	{
		action = "preview";
		startCrypto();
	}
	function printCertStart(id)
	{
		try
		{
			crypto.PrintCertDemand(id);
		}
		catch(ex)
		{
			showError(ex.message);
		}
	}
	function previewCertStart(id)
	{
		try
		{
			crypto.PreviewCertDemand(id);
		}
		catch(ex)
		{
			showError(ex.message);
		}
	}
	function createRequestStart()
	{
		var person = "${person.surName} ${person.firstName} ${person.patrName}";
		try
		{
			var result = crypto.CreateCertRequest(person);
			var req = document.getElementById("certRequest");
			var file = document.getElementById("certRequestFile");
			req.value = result['request'];
			file.value = result['fileName'];
			new CommandButton('button.save').click();
		}
		catch(ex)
		{
			var message = ex.message;
		    showError(message);
		}
	}

	function installCertStart()
	{
		try
		{
			var anwsw = document.getElementById("certAnswer").value;
			var file = document.getElementById("certAnswerFile").value;
			crypto.InstallCertAnswer(anwsw, file);		
		}
		catch(ex)
		{
			var message = ex.message;
		    showError(message);
		}
	}
</script>
<table cellspacing="2" cellpadding="0" width="100%" border=0 onkeypress="onEnterKey(event);">
	<html:hidden property="personId"/>
	<html:hidden property="dateString"/>
	<html:hidden property="certRequest" styleId="certRequest"/>
	<html:hidden property="certRequestFile" styleId="certRequestFile"/>
	<html:hidden property="certAnswer" styleId="certAnswer"/>
	<html:hidden property="certAnswerFile" styleId="certAnswerFile"/>
	<c:if test="${!empty param.new}">
		<script>
			alert("Заявка успешно отправлена!");
		</script>
	</c:if>
	<tiles:insert definition="paymentForm" flush="false">
		<tiles:put name="id" value="EditCertDemandCertification"/>
		<tiles:put name="name" value="Заявка на сертификат"/>
		<tiles:put name="description" value="Подача в банк заявки на выпуск сертификата."/>
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
			  <td class="Width120 LabelAll" nowrap="true"><b><bean:message key="label.date" bundle="certificationBundle" /></b></td>
			  <td>
					<c:choose>
						<c:when test="${empty form.demand}">
							<bean:write name="form" property="date" format="dd.MM.yyyy HH:mm:ss"/>
						</c:when>
						<c:otherwise>
							<bean:write name="form" property="demand.issueDate.time" format="dd.MM.yyyy HH:mm:ss"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" nowrap="true">
					<b><bean:message key="label.status" bundle="certificationBundle" />&nbsp;</b>
				</td>
				<td>
					 <c:choose>
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

			<c:if test="${status=='R'}">
				<tr>
					<td class="Width120 LabelAll" nowrap="true"><b><bean:message key="label.refuseReason" bundle="certificationBundle" /></b></td>
					<td><bean:write name="form" property="demand.refuseReason"/>&nbsp;
					</td>
				</tr>
			</c:if>
		</tiles:put>
	    <tiles:put name="stamp" value="${!(status=='N')?'received':''}"/>
		<tiles:put name="buttons">
			 <c:choose>
				<c:when test="${empty form.id }">
					<tiles:insert definition="clientButton" flush="false">
						<tiles:put name="commandTextKey" value="button.save"/>
						<tiles:put name="commandHelpKey" value="button.save.help"/>
						<tiles:put name="bundle" value="certificationBundle"/>
						<tiles:put name="onclick">createRequest();</tiles:put>
						<tiles:put name="image" value="iconSm_save.gif"/>
					</tiles:insert>
				</c:when>
				<c:otherwise>
					<c:if test="${status=='G' || status=='I'}">
						<tiles:insert definition="commandButton" flush="false">
						<tiles:put name="commandKey"     value="button.install"/>
						<tiles:put name="commandHelpKey" value="button.install.help"/>
						<tiles:put name="bundle"         value="certificationBundle"/>
						<tiles:put name="image"          value="iconSm_install.gif"/>
						</tiles:insert>
					</c:if>                      
					<c:if test="${status=='S' || status=='P'}">
						<tiles:insert definition="clientButton" flush="false">
							<tiles:put name="commandTextKey" value="button.print"/>
							<tiles:put name="commandHelpKey" value="button.print.help"/>
							<tiles:put name="bundle" value="certificationBundle"/>
							<tiles:put name="image" value="iconSm_print.gif"/>
							<tiles:put name="onclick">printCert();</tiles:put>
						</tiles:insert>
						<tiles:insert definition="clientButton" flush="false">
							<tiles:put name="commandTextKey" value="button.preview"/>
							<tiles:put name="commandHelpKey" value="button.preview.help"/>
							<tiles:put name="bundle" value="certificationBundle"/>
							<tiles:put name="image" value="iconSm_view.gif"/>
							<tiles:put name="onclick">previewCert();</tiles:put>
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
</body>
</html:form>
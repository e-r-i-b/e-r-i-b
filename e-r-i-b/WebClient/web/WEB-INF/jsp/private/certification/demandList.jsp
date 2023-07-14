<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 17.11.2006
  Time: 13:08:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/certification/list" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="certificationMain">
	<tiles:put name="submenu" type="string" value="DemandList"/>
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="listCertDemand.title" bundle="certificationBundle"/>
    </tiles:put>
	    <c:set var="form" value="${ShowCertDemandCertificationForm}"/>
		<c:set var="proceded" value="${form.isProceded}"/>
	<tiles:put name="menu" type="string">
        <nobr>
			<c:if test="${!proceded}">
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey"     value="button.create"/>
					<tiles:put name="commandHelpKey"     value="button.create.help"/>
					<tiles:put name="bundle"  value="certificationBundle"/>
					<tiles:put name="action"  value="/private/certification/edit.do"/>
				</tiles:insert>
			</c:if>
		<nobr>
	 </tiles:put>
      <tiles:put name="data" type="string">

	  <tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="tableCertClaims"/>
		<tiles:put name="image" value="iconMid_certification.gif"/>
		<tiles:put name="text" value="Список заявок на сертификаты"/>
		<tiles:put name="buttons">
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
		</tiles:put>
        <tiles:put name="grid">
	    	<%@ include file="../../crypto/agavaStart.jsp" %>
				<script type="text/javascript">
					var action = null;
					function printCert()
					{
						var crypto = createRSCrypto();
                        checkIfOneItem("selectedIds");
						if (!checkOneSelection("selectedIds", "Выберите один сертификат"))
						    		return;
						try
						{
							crypto.PrintCertificate( getCertId() );
						}
						catch(e)
						{
							alert(e.message+"\nВозможно, сертификат отсутствует в контейнере.");
						}
					}
					function previewCert()
					{
						var crypto = createRSCrypto();
                        checkIfOneItem("selectedIds");
						if (!checkOneSelection("selectedIds", "Выберите одн сертификат"))
						    		return;
						try
						{
							crypto.PreviewCertificate( getCertId() );
						}
						catch(e)
						{
							alert(e.message+"\nВозможно, сертификат отсутствует в контейнере.");
						}
					}
					function getCertId()
					{
						var ownId = -1;
						var ids = document.getElementsByName("selectedIds");
						for (var i=0; i< ids.length; i++)  if (ids[i].checked)
						{
							ownId=i;
							break;
						}
						var hidName = ids.item(ownId).value;
						var id = document.getElementById(hidName).value;
						return id;
					}
				</script>

            <c:if test="${form.needChange == true}">
			    <script type="text/javascript">
				    alert("У вас не найден активный сертификат.\nПожалуйста, создайте заявку на новый сертификат или установите сертификат выданный банком.");
			    </script>
			</c:if>

            <sl:collection id="listElement" model="list" property="data" bundle="certificationBundle">
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="id"/>

                <c:set var="id" value="${listElement.id}" />
				<c:set var="status" value="${listElement.status}" />

                <sl:collectionItem title="label.id" name="listElement" property="certDemandCryptoId">
                    <sl:collectionItemParam
                            id="action"
                            value="/private/certification/confirm.do?id=${id}"
                            condition="${status=='E'}"
                            />
                    <sl:collectionItemParam
                            id="action"
                            value="/private/certification/view.do?id=${id}"
                            condition="${status!='E'}"
                            />
                </sl:collectionItem>
                <sl:collectionItem title="label.date">
                    <c:if test="${not empty listElement.issueDate}">
                        <bean:write name="listElement" property="issueDate.time" format="dd.MM.yyyy HH:mm:ss"/>
                    </c:if>
                </sl:collectionItem>
                <sl:collectionItem title="label.status">
                    <sl:collectionItemParam id="value" value="Введен"    condition="${status=='E'}"/>
                    <sl:collectionItemParam id="value" value="Отправлен" condition="${status=='S'}"/>
                    <sl:collectionItemParam id="value" value="Обрабатывается"    condition="${status=='P'}"/>
                    <sl:collectionItemParam id="value" value="Сертификат выдан"  condition="${status=='G'}"/>
                    <sl:collectionItemParam id="value" value="Отказан"   condition="${status=='R'}"/>
                    <sl:collectionItemParam id="value" value="Сертификат установлен" condition="${status=='I'}"/>
                    <sl:collectionItemParam id="value" value="Сертификат удален" condition="${status=='D'}"/>
                </sl:collectionItem>
            </sl:collection>
		</tiles:put>

		<tiles:put name="isEmpty" value="${empty form.data}"/>
		<tiles:put name="emptyMessage" value="Не найдено ни одной заявки"/>
	</tiles:insert>
 </tiles:put>
 </tiles:insert>
</html:form>
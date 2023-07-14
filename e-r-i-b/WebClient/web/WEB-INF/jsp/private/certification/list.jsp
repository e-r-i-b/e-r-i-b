<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 28.03.2007
  Time: 15:16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/private/certification/certificate/list" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="certificationMain">
	<tiles:put name="submenu" type="string" value="CertificateList"/>
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="listCertificate.title" bundle="certificationBundle"/>
    </tiles:put>
	<!-- фильтр   -->
	<tiles:put name="filter" type="string">
		<tiles:insert definition="filterDataSpan" flush="false">
			<tiles:put name="label" value="label.startDate"/>
			<tiles:put name="bundle" value="certificationBundle"/>
			<tiles:put name="name" value="startDate"/>
			<tiles:put name="template" value="DATE_TEMPLATE"/>
		</tiles:insert>
		<tiles:insert definition="filterDataSpan" flush="false">
			<tiles:put name="label" value="label.endDate"/>
			<tiles:put name="bundle" value="certificationBundle"/>
			<tiles:put name="name" value="endDate"/>
			<tiles:put name="template" value="DATE_TEMPLATE"/>
		</tiles:insert>
		<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.status"/>
			<tiles:put name="bundle" value="certificationBundle"/>
			<tiles:put name="data">
				<html:select property="filter(status)" styleClass="select">
					<html:option value="">Все</html:option>
					<html:option value="A">Активный</html:option>
					<html:option value="N">Неактивный</html:option>
					<html:option value="E">Истек</html:option>
					<html:option value="B">Заблокирован</html:option>
				</html:select>
			</tiles:put>
		</tiles:insert>
    </tiles:put>
	<tiles:put name="menu" type="string">
	<script type="text/javascript">
		function printCert()
		{
            checkIfOneItem("selectedIds");
			if (!checkOneSelection('selectedIds','Выберите один сертификат для печати'))
				return;
			var crypto = createRSCrypto();
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
            checkIfOneItem("selectedIds");
			if (!checkOneSelection('selectedIds','Выберите один сертификат для печати'))
				return;
			var crypto = createRSCrypto();
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
			for (var i=0; i< ids.length; i++)  if (ids[i].checked){ ownId=i;break;}
			var hidName = ids.item(ownId).value;
			var id = document.getElementById(hidName).value;
			return id;
		}
	</script>
	</tiles:put>
    <tiles:put name="data" type="string">
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="tableCert"/>
		<tiles:put name="image" value="iconMid_certification.gif"/>
		<tiles:put name="text" value="Сертификаты"/>
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
            <sl:collection id="listElement" model="list" property="data" bundle="certificationBundle">
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="id"/>

                <c:set var="certOwn" value="${listElement}" />
				<c:set var="status" value="${certOwn.status}" />
				<c:set var="login" value="${certOwn.owner}" />
				<c:set var="cert" value="${certOwn.certificate}" />

                <sl:collectionItem title="label.id" name="cert" property="id">

                </sl:collectionItem>
                <sl:collectionItem title="label.startDate">
                    &nbsp;
                    <c:if test="${not empty(certOwn.startDate)}">
						<bean:write name="certOwn" property="startDate.time" format="dd.MM.yyyy HH:mm:ss"/>
					</c:if>
                </sl:collectionItem>
                <sl:collectionItem title="label.endDate">
                    &nbsp;
                    <c:if test="${not empty certOwn}">
                        <bean:write name="certOwn" property="endDate.time" format="dd.MM.yyyy HH:mm:ss"/>
                    </c:if>
                </sl:collectionItem>
                <sl:collectionItem title="label.status">
                    <sl:collectionItemParam id="value" value="Истек" condition="${status=='E'}"/>
                    <sl:collectionItemParam id="value" value="Активный" condition="${status=='A'}"/>
                    <sl:collectionItemParam id="value" value="Неактивный" condition="${status=='N'}"/>
                    <sl:collectionItemParam id="value" value="Заблокирован" condition="${status=='B'}"/>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
		<tiles:put name="isEmpty" value="${empty ListCertificateForm.data}"/>
		<tiles:put name="emptyMessage" value="Не найдено ни одного сертификата, соответствующего заданному фильтру"/>
	</tiles:insert>
 </tiles:put>
 </tiles:insert>
</html:form>
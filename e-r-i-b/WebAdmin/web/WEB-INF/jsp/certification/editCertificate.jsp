<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 27.03.2007
  Time: 14:44:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<body>
<html:form action="/certification/certificate/edit" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="certList">
	<tiles:put name="submenu" type="string" value="CertificateList"/>
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="editCert.title" bundle="certificationBundle"/>
    </tiles:put>

		<c:set var="form" value="${EditCertificateForm}"/>
	    <c:set var="certOwn" value="${form.certOwn}"/>
		<c:set var="login" value="${certOwn.owner}"/>
		<c:set var="cert" value="${certOwn.certificate}"/>
		<c:set var="status" value="${certOwn.status}"/>


	<tiles:put name="menu" type="string">
        <nobr>
	        <tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.cancel"/>
				<tiles:put name="commandHelpKey" value="button.cancel.help"/>
				<tiles:put name="bundle"         value="certificationBundle"/>
				<tiles:put name="action"         value="/certification/certificate/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		<nobr>
	 </tiles:put>
    <tiles:put name="data" type="string">
	<tiles:insert definition="paymentForm" flush="false">
		<tiles:put name="id" value=""/>
		<tiles:put name="name" value="Просмотр информации о сертификате"/>
		<tiles:put name="description" value="Информация по сертификату."/>
		<tiles:put name="data">	    
				<tr>
					<td class="Width120 LabelAll" nowrap="true">
						<b><bean:message key="label.id" bundle="certificationBundle"/></b>
					</td>
					<td>
						<bean:write name="cert" property="id"/>&nbsp;
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll" nowrap="true">
						<b><bean:message key="label.FIO" bundle="certificationBundle"/></b>
					</td>
					<td>
						<bean:write name="cert" property="name"/>&nbsp;
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll" nowrap="true">
						<b><bean:message key="label.startDate" bundle="certificationBundle"/></b>
					</td>
					<td>
						<c:if test="${not empty(certOwn.startDate)}">
							<bean:write name="certOwn" property="startDate.time" format="dd.MM.yyyy HH:mm:ss"/>
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll" nowrap="true">
						<b><bean:message key="label.endDate" bundle="certificationBundle"/></b>
					</td>
					<td>
						<bean:write name="certOwn" property="endDate.time" format="dd.MM.yyyy HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll" nowrap="true">
						<b><bean:message key="label.status" bundle="certificationBundle"/></b>
					</td>
					<td>&nbsp;
			 				 <c:choose>
							   <c:when test="${status=='A'}">
								   <nobr>Активный</nobr>
							   </c:when>
							   <c:when test="${status=='N'}">
								   <nobr>Неактивный</nobr>
							   </c:when>
							   <c:when test="${status=='B'}">
								   <nobr>Заблокирован</nobr>
							   </c:when>
							   <c:when test="${status=='E'}">
								   <nobr>Истек</nobr>
							   </c:when>
						   </c:choose>
					</td>
				</tiles:put>
				<tiles:put name="buttons">
		            <c:if test="${status=='N'}">
						<tiles:insert definition="commandButton" flush="false">
							 <tiles:put name="commandKey"     value="button.activate"/>
							 <tiles:put name="commandHelpKey" value="button.activate.help"/>
							 <tiles:put name="bundle"         value="certificationBundle"/>
							 <tiles:put name="image"          value="add.gif"/>
						</tiles:insert>
					</c:if>
				</tiles:put>
				<tiles:put name="alignTable" value="center"/>
		</tiles:insert>
 </tiles:put>
 </tiles:insert>
</html:form>
</body>
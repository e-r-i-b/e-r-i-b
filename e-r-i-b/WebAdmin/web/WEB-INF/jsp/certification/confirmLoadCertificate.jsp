<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 21.03.2007
  Time: 18:10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/certification/confirmload" onsubmit="return setEmptyAction(event);" enctype="multipart/form-data">
<tiles:insert definition="certList">
	<tiles:put name="submenu" type="string" value="DemandList"/>
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="confirmCert.title" bundle="certificationBundle"/>
    </tiles:put>
	<tiles:put name="menu" type="string">
        <nobr>
			<tiles:insert definition="commandButton" flush="false">
				 <tiles:put name="commandKey"     value="button.register"/>
				 <tiles:put name="commandHelpKey" value="button.register.help"/>
				 <tiles:put name="bundle"         value="certificationBundle"/>
                 <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey" value="button.cancel"/>
				<tiles:put name="commandHelpKey" value="button.cancel.help"/>
				<tiles:put name="bundle"         value="certificationBundle"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		<nobr>
	 </tiles:put>

      <tiles:put name="data" type="string">
	<table cellpadding="0" cellspacing="0" class="MaxSize">
		<tr>
		<td height="100%">
		<c:set var="form" value="${ConfirmLoadCertificateForm}"/>
		<c:set var="certFile" value="${form.cert}"/>
		<c:set var="certAnswFile" value="${form.certAns}"/>
		<c:set var="cert" value="${form.certificate}"/>
		<c:set var="id" value="${form.id}"/>						
		    <table cellspacing="2" cellpadding="0" width="100%" border=0 onkeypress="onEnterKey(event);">
				<tr>
					<td class="Width120 Label" nowrap="true">
						<bean:message key="label.id" bundle="certificationBundle"/>
					</td>
					<td>
						<bean:write name="form" property="certificate.id"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 Label" nowrap="true">
						<bean:message key="label.FIO" bundle="certificationBundle"/>
					</td>
					<td>
						<bean:write name="form" property="certificate.name"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 Label" nowrap="true">
						<bean:message key="label.expiration" bundle="certificationBundle"/>
					</td>
					<td>
						<bean:write name="form" property="certificate.expiration" format="dd.MM.yyyy"/>
					</td>
				</tr>			    
			</table>
		</td>
		</tr>
	</table>
 </tiles:put>
 </tiles:insert>
</html:form>
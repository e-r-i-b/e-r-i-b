<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 21.03.2007
  Time: 15:03:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/certification/load" onsubmit="return setEmptyAction(event);" enctype="multipart/form-data">
<tiles:insert definition="certList">
	<tiles:put name="submenu" type="string" value="DemandList"/>
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="loadCert.title" bundle="certificationBundle"/>
    </tiles:put>
	<tiles:put name="menu" type="string">
        <nobr>
			<tiles:insert definition="commandButton" flush="false">
				 <tiles:put name="commandKey"     value="button.upload"/>
				 <tiles:put name="commandHelpKey" value="button.upload.help"/>
				 <tiles:put name="bundle"         value="certificationBundle"/>
				 <tiles:put name="image"          value=""/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.cancel"/>
				<tiles:put name="commandHelpKey" value="button.cancel.help"/>
				<tiles:put name="bundle"         value="certificationBundle"/>
				<tiles:put name="image"          value=""/>
				<tiles:put name="action"         value="/certification/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		<nobr>
	 </tiles:put>
	
      <tiles:put name="data" type="string">
	<table cellpadding="0" cellspacing="0" class="MaxSize">
		<tr>
		<td height="100%">
		<c:set var="form" value="${LoadCertificateForm}"/>    
	    	<table cellspacing="2" cellpadding="0" width="100%" border=0 onkeypress="onEnterKey(event);">
				<tr>
					<td class="Width120 Label" nowrap="true">
						<bean:message key="label.AGAPath" bundle="certificationBundle"/>
					</td>
					<td>
						<html:file property="certAnswerFilePath" style="width:500"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 Label" nowrap="true">
						<bean:message key="label.AGCPath" bundle="certificationBundle"/>
					</td>
					<td>
						<html:file property="certFilePath" style="width:500"/>
					</td>
				</tr>
			</table>
		</td>
		</tr>
	</table>
 </tiles:put>
 </tiles:insert>
</html:form>
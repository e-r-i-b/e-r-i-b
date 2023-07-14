<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 02.04.2007
  Time: 16:43:32
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
<html:form action="/certification/refuse" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="certList">
	<tiles:put name="submenu" type="string" value="DemandList"/>
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="refuseDemand.title" bundle="certificationBundle"/>
    </tiles:put>

		<c:set var="form" value="${RefuseCertDemandForm}"/>
	<tiles:put name="menu" type="string">
        <nobr>
			<tiles:insert definition="commandButton" flush="false">
				 <tiles:put name="commandKey"     value="button.refuse"/>
				 <tiles:put name="commandHelpKey" value="button.refuse.help"/>
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
			<table cellspacing="2" cellpadding="0" width="100%" border=0 onkeypress="onEnterKey(event);">
				<tr>
					<td class="Width120 Label" nowrap="true">
						<bean:message key="label.reason" bundle="certificationBundle"/>
					</td>
					<td>
						<html:text  property="field(refuseReason)" size="20" maxlength="255" styleClass="contactInput"/>
					</td>
				</tr>
			</table>
		</td>
		</tr>
		</table>
 </tiles:put>
 </tiles:insert>
</html:form>
</body>
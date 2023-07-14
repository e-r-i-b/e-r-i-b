<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<script type="text/javascript">
	function otherReason()
	{
		document.getElementById("field(otherReason)").value = "";
		if (document.getElementById("field(selectedRefuseReason)")!=null)
		{
			var reason = document.getElementById("field(selectedRefuseReason)").value;
			if (reason == "Другое") document.getElementById("field(otherReason)").disabled = false
				else document.getElementById("field(otherReason)").disabled = true;
		}
	}
</script>

<body>
<html:form action="/documents/refuse" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="claimsMain">
	<tiles:put name="pageTitle" type="string">
	    <bean:message key="refuse.document.title" bundle="paymentsBundle"/>
    </tiles:put>

   	<c:set var="form" value="${DocumentRefuseForm}"/>

    <tiles:put name="data" type="string">
        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="id" value="refuseDocument"/>
            <tiles:put name="name">
                <bean:message key="label.document.reason" bundle="paymentsBundle"/>
            </tiles:put>
            <tiles:put name="description">

            </tiles:put>
            <tiles:put name="data">

                <tr>
                    <td colspan="2">
					    <html:select property="field(selectedRefuseReason)" styleId="field(selectedRefuseReason)"
						             styleClass="contactInput" onclick="javascript:otherReason()">
							<html:options property="field(refuseReason)"/>
							<html:option value="Другое">Другое</html:option>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="Width120 Label" nowrap="true">
						<bean:message key="label.document.otherReason" bundle="paymentsBundle"/>
					</td>
					<td>
						<html:text  property="field(otherReason)" styleId="field(otherReason)"
						            disabled="true" size="20" maxlength="255" styleClass="contactInput"/>
					</td>
				</tr>
         
            </tiles:put>
            <tiles:put name="buttons">
                <nobr>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.refuse"/>
                        <tiles:put name="commandHelpKey" value="button.refuse.help"/>
                        <tiles:put name="bundle"         value="paymentsBundle"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="paymentsBundle"/>
                        <tiles:put name="action"         value="/audit/businessDocument.do?status=admin"/>
                    </tiles:insert>
                <nobr>
            </tiles:put>
            <tiles:put name="alignTable" value="center"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>
</body>

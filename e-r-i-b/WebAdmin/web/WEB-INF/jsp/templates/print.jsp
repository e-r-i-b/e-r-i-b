<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html>
	<body onload="javascript:print()">
    <c:catch var="errorJSP">
		<table style="width:100%;height:400px" cellspacing="0" cellpadding="0">
			<tr height="20px"><td>&nbsp;<td/></tr>
			<tr><td align="center" valign="top">
				<table cellspacing="0" cellpadding="0" border="1px" width="100%">
                    <tr align="left" style="font-weight:bold">
                        <td colspan="3">&nbsp;"SMS-шаблоны банка"</td>
                    </tr>
					<tr align="center" style="font-weight:bold">
						<td width="20%">Наименование</td>
						<td width="35%">Вид платежа</td>
						<td width="45%">Формат команды</td>
					</tr>
				<logic:iterate id="metadataBean" name="ShowSmsTemplateListForm" property="bankTemplates">
					<c:set var="form" value="${ShowSmsTemplateListForm}"/>
					<c:set var="smsCommands" value="${form.smsCommands}"/>
                    <c:set var="forms" value="${form.paymentForms}"/>
                    <tr align="center">
                        <td><c:out value="${metadataBean.templateName}"/></td>
                        <td><c:out value="${forms[metadataBean.formName].description}"/></td>
                        <td>${smsCommands[metadataBean.id]}</td>
                    </tr>
				</logic:iterate>
				</table>
			</td></tr>
		</table>
	</body>
    </c:catch>
        <c:if test="${not empty errorJSP}">
            ${phiz:writeLogMessage(errorJSP)}
            <script type="text/javascript">
                window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
            </script>
        </c:if>
</html>

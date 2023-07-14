<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loans/claims/claimArchive">
	<tiles:insert definition="loansMain">
		<tiles:put name="submenu" type="string" value="LoanClaimsArchive"/>
		<tiles:put name="pageTitle" type="string">
			<bean:message key="view.claim.title" bundle="loansBundle"/>
		</tiles:put>

		<c:set var="form" value="${ViewLoanClaimForm}"/>

		<%-- кнопки --%>
		<tiles:put name="menu" type="string">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.fromarchive"/>
				<tiles:put name="commandHelpKey" value="button.fromarchive.help"/>
				<tiles:put name="bundle"         value="loansBundle"/>
				<tiles:put name="image"          value=""/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
			
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.close"/>
				<tiles:put name="commandHelpKey" value="button.close.help"/>
				<tiles:put name="bundle" value="loansBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="action" value="/loans/claims/archiveList"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
	    </tiles:put>

		<%-- данные --%>
		<tiles:put name="data" type="string">
			<input type="hidden" name="id" value="${form.id}">
			<table cellpadding="0" cellspacing="0" class="MaxSize">
				<tr>
					<td height="100%">
						${form.html}
					</td>
				</tr>
			</table>
		</tiles:put>
	</tiles:insert>
</html:form>

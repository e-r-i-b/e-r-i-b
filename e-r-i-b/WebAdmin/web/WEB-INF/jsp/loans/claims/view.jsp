<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loans/claims/claim">
	<tiles:insert definition="loansMain">
		<tiles:put name="submenu" type="string" value="LoanClaims"/>
		<tiles:put name="pageTitle" type="string">
			<bean:message key="view.claim.title" bundle="loansBundle"/>
		</tiles:put>

		<c:set var="form" value="${ViewLoanClaimForm}"/>

		<script language="Javascript">
			function confirmPrint(event)
			{
				var h = 600;
				var w = 500;
				var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1" +
							 ", width=" + w + ", height=" + h +
							 ", left=" + (getClientWidth() - w) / 2 + ", top=" + (getClientHeight() - h) / 2;
	            var win = openWindow(null, "${phiz:calculateActionURL(pageContext, '/loans/claims/claim/print.do')}?id=${form.id}", "dialog", winpar);

				win.focus();
			}
		</script>

		<%-- кнопки --%>
		<tiles:put name="menu" type="string">
				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey"     value="button.refuse"/>
					<tiles:put name="commandHelpKey" value="button.refuse.help"/>
					<tiles:put name="bundle"         value="loansBundle"/>
					<tiles:put name="confirmText"    value="Вы действительно хотите отказать заявку?"/>
					<tiles:put name="image"          value=""/>
                    <tiles:put name="viewType" value="blueBorder"/>
				</tiles:insert>

				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey"     value="button.accept"/>
					<tiles:put name="commandHelpKey" value="button.accept.help"/>
					<tiles:put name="bundle"         value="loansBundle"/>
					<tiles:put name="image"          value=""/>
                    <tiles:put name="viewType" value="blueBorder"/>
				</tiles:insert>

				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey"     value="button.completion"/>
					<tiles:put name="commandHelpKey" value="button.completion.help"/>
					<tiles:put name="bundle"         value="loansBundle"/>
					<tiles:put name="confirmText"    value="Вы действительно хотите отправить на редактирование клиенту заявку?"/>
					<tiles:put name="image"          value=""/>
                    <tiles:put name="viewType" value="blueBorder"/>
				</tiles:insert>

			<c:if test="${form.comment}">
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey"     value="button.comment"/>
					<tiles:put name="commandHelpKey" value="button.comment.help"/>
					<tiles:put name="bundle"         value="loansBundle"/>
					<tiles:put name="image"          value=""/>
                    <tiles:put name="action"         value="/loans/claims/comment.do?claimIds=${form.id}"/>
                    <tiles:put name="viewType" value="blueBorder"/>
				</tiles:insert>
			</c:if>
			<c:if test="${form.archive}">
				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey"     value="button.archive"/>
					<tiles:put name="commandHelpKey" value="button.archive.help"/>
					<tiles:put name="bundle"         value="loansBundle"/>
					<tiles:put name="image"          value=""/>
                    <tiles:put name="viewType" value="blueBorder"/>
				</tiles:insert>
			</c:if>
			<c:if test="${form.printDocuments}">
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.print"/>
					<tiles:put name="commandHelpKey" value="button.print.help"/>
					<tiles:put name="bundle" value="loansBundle"/>
					<tiles:put name="image" value=""/>
					<tiles:put name="onclick">confirmPrint()</tiles:put>
                    <tiles:put name="viewType" value="blueBorder"/>
				</tiles:insert>
			</c:if>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.close"/>
				<tiles:put name="commandHelpKey" value="button.close.help"/>
				<tiles:put name="bundle"  value="loansBundle"/>
				<tiles:put name="image"   value=""/>
				<tiles:put name="action"  value="/loans/claims/list"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
	    </tiles:put>

		<%-- данные --%>
		<tiles:put name="data" type="string">
			<input type="hidden" name="id" value="${form.id}">
			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value=""/>
				<tiles:put name="name" value="Просмотр кредитной заявки"/>
				<tiles:put name="description" value=""/>
				<tiles:put name="data">
				<tr>
					<td height="100%">
						${form.html}
					</td>
				</tr>
			</tiles:put>
			<tiles:put name="alignTable" value="center"/>
		</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>

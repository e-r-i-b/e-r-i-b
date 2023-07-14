<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loans/claims/comment">
	<tiles:insert definition="loansMain">
		<tiles:put name="submenu" type="string" value="LoanClaims"/>
		<tiles:put name="pageTitle" type="string">
			<bean:message key="view.claim.comment" bundle="loansBundle"/>
		</tiles:put>

		<c:set var="form" value="${AddLoanClaimCommentForm}"/>

		<%-- кнопки --%>
		<tiles:put name="menu" type="string">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.save"/>
				<tiles:put name="commandHelpKey" value="button.comment.help"/>
				<tiles:put name="bundle"         value="loansBundle"/>
				<tiles:put name="confirmText"    value="Вы действительно хотите изменить комментарии в выделенных заявках?"/>
				<tiles:put name="image"          value=""/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.close"/>
				<tiles:put name="commandHelpKey" value="button.close.help"/>
				<tiles:put name="bundle"         value="loansBundle"/>
				<tiles:put name="image"          value=""/>
				<tiles:put name="action"         value="/loans/claims/list"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
	    </tiles:put>

		<%-- данные --%>
		<tiles:put name="data" type="string">
			<c:forEach var="claimId" items="${form.claimIds}">
				<input type="hidden" name="claimIds" value="${claimId}">
			</c:forEach>
			<table cellpadding="0" cellspacing="0" class="MaxSize">
				<tr>
					<td nowrap="true" class="Label">
						&nbsp;Комментарий&nbsp;
					</td>
					<td height="100%">
						<input size="50" maxlength="255" name="comment" value="${form.comment}"/>
					</td>
					<td width="100%">&nbsp;</td>
				</tr>
			</table>
		</tiles:put>
	</tiles:insert>
</html:form>

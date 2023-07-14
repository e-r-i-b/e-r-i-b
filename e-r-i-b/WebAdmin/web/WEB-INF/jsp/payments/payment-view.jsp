<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/payments/payment">
	<c:set var="form"  value="${ViewDocumentForm}"/>

	<tiles:insert definition="paymentsMain">

		<!-- заголовок -->
		<tiles:put name="pageTitle" type="string">
			<c:out value="${form.title}"/>
		</tiles:put>

		<!-- меню -->
		<tiles:put name="menu" type="string">

            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/payments/print.do')}"/>

			<c:if test="${form.form == 'GoodsAndServicesPayment'}">
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.print"/>
					<tiles:put name="commandHelpKey" value="button.print"/>
					<tiles:put name="bundle"         value="formsBundle"/>
					<tiles:put name="image"          value=""/>
					<tiles:put name="onclick" value="openWindow(null, '${url}?id=${form.id}')"/>
                    <tiles:put name="viewType" value="blueBorder"/>
				</tiles:insert>
			</c:if>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.list"/>
				<tiles:put name="commandHelpKey" value="button.list"/>
				<tiles:put name="bundle"         value="formsBundle"/>
				<tiles:put name="image"          value=""/>
				<tiles:put name="action"         value="/payments/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<!-- собственно данные -->
		<tiles:put name="data" type="string">
		<tiles:insert definition="paymentForm" flush="false">
		<tiles:put name="id" value="EditLetter"/>
		<tiles:put name="name" value="Редактирование письма"/>
		<tiles:put name="description" value="Используйте данную форму редактирования письма	банку."/>
		<tiles:put name="data">
				<tr>
					<td>Ф.И.О.</td>
					<td><c:out value="${form.owner.fullName}"/></td>
				</tr>
				<tr>
					<td>Идентификатор</td>
					<td><c:out value="${form.owner.agreementNumber}"/></td>
				</tr>
				<tr>
					<td>Подразделение бэк-офиса</td>
					<td>
						&nbsp;
						<c:if test="${not empty form.office}">
							<bean:write name="form" property="office.name"/>&nbsp;<bean:write name="form" property="office.address"/>
						</c:if>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2">
					${form.html}
					</td>
				</tr>
		</tiles:put>
	<tiles:put name="alignTable" value="center"/>
</tiles:insert>
		</tiles:put>

	</tiles:insert>

</html:form>


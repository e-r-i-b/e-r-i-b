<%--
  Created by IntelliJ IDEA.
  User: mihaylov
  Date: 21.07.2008
  Time: 13:52:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/loans/statemess/edit" >
	<c:set var="form" value="${EditLoanStateMessagesForm}"/>

	<tiles:insert definition="loansEdit">
        <tiles:put name="pageTitle" type="string">
	        <bean:message key="edit.stateMess.title" bundle="loansBundle"/>
        </tiles:put>
		<tiles:put name="submenu" type="string" value="StateMess"/>

		<!--меню-->
		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.list"/>
				<tiles:put name="commandHelpKey"     value="button.list.kind.help"/>
				<tiles:put name="bundle"  value="loansBundle"/>
				<tiles:put name="image"   value=""/>
				<tiles:put name="action"  value="/loans/statemess/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<!--данные-->
		<tiles:put name="data" type="string">
			<input type="hidden" name="key" value="${form.key}"/>
			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value="LoansStatemess"/>
				<tiles:put name="name" value="Результат рассмотрения заявки"/>
				<tiles:put name="description" value="Используйте данную форму редактирования сообщения о результатах рассмотрения заявки в банке."/>
				<tiles:put name="data">
				<tr>
					<td  class="Width120 LabelAll">Статус заявки:</td>
					<td><b>
						<c:choose>
							<c:when test="${form.key == 'claim.state.REFUSED.message'}">Отказана</c:when>
							<c:when test="${form.key == 'claim.state.APPROVED.message'}">Утверждена</c:when>
							<c:when test="${form.key == 'claim.state.DISPATCHED.message'}">Принята</c:when>
							<c:when test="${form.key == 'claim.state.CONSIDERATION.message'}">В рассмотрении</c:when>
							<c:when test="${form.key == 'claim.state.COMPLETION.message'}">Требуется доработка</c:when>
							<c:when test="${form.key == 'claim.state.EXECUTED.message'}">Кредит выдан</c:when>
							<c:otherwise>
								&nbsp;
							</c:otherwise>
						</c:choose>
						</b>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Текст сообщения:</td>
					<td><html:textarea property="field(value)" cols="64" rows="4"/></td>
				</tr>
			</tiles:put>
			<tiles:put name="buttons">
				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey"     value="button.save"/>
					<tiles:put name="commandHelpKey" value="button.save.kind.help"/>
					<tiles:put name="bundle"  value="loansBundle"/>
				</tiles:insert>
			</tiles:put>
			<tiles:put name="alignTable" value="center"/>
		</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
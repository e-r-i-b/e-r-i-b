<%--
  Created by IntelliJ IDEA.
  User: gladishev
  Date: 19.12.2007
  Time: 10:58:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/loans/kinds/edit"  onsubmit="return setEmptyAction(event);" enctype="multipart/form-data">
	<c:set var="form" value="${EditLoanKindForm}"/>

	<tiles:insert definition="loansEdit">
        <tiles:put name="pageTitle" type="string">
	        <bean:message key="edit.kind.title" bundle="loansBundle"/>
        </tiles:put>
		<tiles:put name="submenu" type="string" value="LoanKinds"/>

		<!--меню-->
		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.list"/>
				<tiles:put name="commandHelpKey"     value="button.list.kind.help"/>
				<tiles:put name="bundle"  value="loansBundle"/>
				<tiles:put name="image"   value=""/>
				<tiles:put name="action"  value="/loans/kinds/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
			<c:if test="${form.id != null}">
				<tiles:insert definition="commandButton" flush="false">
					 <tiles:put name="commandKey"     value="button.download.claim.desciption"/>
					 <tiles:put name="commandHelpKey" value="button.download.claim.desciption.help"/>
					 <tiles:put name="bundle"         value="loansBundle"/>
					 <tiles:put name="image"          value=""/>
                    <tiles:put name="viewType" value="blueBorder"/>
				</tiles:insert>
			</c:if>
		</tiles:put>

		<!--данные-->
		<input type="hidden" name="id" value="${form.id}"/>
		<tiles:put name="data" type="string">
		<tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="id" value="DepositOpeningClaim"/>
			<tiles:put name="name" value="Вид кредита"/>
			<tiles:put name="description" value="Используйте данную форму для редактирования вида кредита."/>
			<tiles:put name="data">
				<tr>
					<td class="Width120 LabelAll">Наименование<span class="asterisk">*</span></td>
                    <td><html:text property="field(name)" size="100" maxlength="256" styleClass="contactInput"/></td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Описание</td>
					<td><html:text property="field(description)" size="100" maxlength="256" styleClass="contactInput"/></td>
				</tr>
                <tr>
					<td class="Width120 LabelAll">Описание заявки<span class="asterisk">*</span></td>
					<td><html:file property="claimDescription" style="width:500"/>&nbsp;</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">XSLT шаблон детальной информации<span class="asterisk">*</span></td>
					<td><html:file property="detailsTemplate" style="width:500"/>&nbsp;</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Кредитный калькулятор</td>
                    <td><html:checkbox property="field(calculator)" value="true"/></td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">Целевой кредит</td>
                    <td><html:checkbox property="field(target)" value="true"/></td>
				</tr>
			</tiles:put>
			<tiles:put name="buttons">
				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey"     value="button.save"/>
					<tiles:put name="commandHelpKey" value="button.save.kind.help"/>
					<tiles:put name="bundle"  value="loansBundle"/>
					<tiles:put name="isDefault" value="true"/>
					<tiles:put name="postbackNavigation" value="true"/>
				</tiles:insert>
			</tiles:put>
			<tiles:put name="alignTable" value="center"/>
		</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
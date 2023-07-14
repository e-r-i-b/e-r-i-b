<%@ page import="javax.servlet.ServletRequest" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/receivers/edit" onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<c:set var="person" value="${form.activePerson}"/>
	<c:set var="isShowSaves" value="${not (person.status == 'T' && phiz:isAgreementSignMandatory()) }"/>

	<tiles:insert definition="personEdit">
		<tiles:put name="submenu" type="string" value="PaymentReceiversJ"/>

		<c:choose>
			<c:when test="${empty form.id}">
				<tiles:put name="pageTitle" type="string" value="Получатели (юр. лица). Новый получатель."/>
			</c:when>
			<c:otherwise>
				<tiles:put name="pageTitle" type="string" value="Получатели (юр. лица). Редактирование получателя"/>
			</c:otherwise>
		</c:choose>

		<tiles:put name="menu" type="string">
            <c:if test="${isShowSaves}">
                <tiles:insert definition="commandButton" flush="false" operation="EditPaymentReceiverOperationAdmin">
                    <tiles:put name="commandKey" value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.receiver.help"/>
                    <tiles:put name="commandTextKey" value="button.save.receiver"/>
                    <tiles:put name="bundle" value="personsBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                </tiles:insert>
            </c:if>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandHelpKey" value="button.receivers-list.close.help"/>
				<tiles:put name="commandTextKey" value="button.receivers-list"/>
				<tiles:put name="bundle" value="personsBundle"/>
				<tiles:put name="action"  value="persons/receivers/list.do?kind=J&person=${form.person}"/>
                <tiles:put name="viewType" value="buttonGrey"/>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">
			<script type="text/javascript">
				function setBankInfo ( bankInfo )
				{
					setElement('field(bankName)',bankInfo["name"]);
					setElement('field(bankCode)',bankInfo["BIC"]);
					setElement('field(correspondentAccount)',bankInfo["account"]);
				}
			</script>
			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value=""/>
				<tiles:put name="name" value="Анкета получателя (юр.лица)"/>
				<tiles:put name="description" value=""/>
				<tiles:put name="data">
				<tr style="height:20px">
					<td class="Width120 LabelAll">
						<bean:message key="label.receiver.alias" bundle="personsBundle"/><span class="asterisk">*</span>
					</td>
					<td>
						<html:text readonly="${not isShowSaves}" property="field(alias)" maxlength="16" size="25"/>
					</td>
				</tr>
				<tr style="height:20px">
					<td class="Width120 LabelAll">
						<bean:message key="label.receiver.name" bundle="personsBundle"/><span class="asterisk">*</span>
					</td>
					<td>
						<html:text readonly="${not isShowSaves}" property="field(name)" maxlength="160" size="80"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">
						<bean:message key="label.receiver.inn" bundle="personsBundle"/><span class="asterisk">*</span>
					</td>
					<td>
						<html:text readonly="${not isShowSaves}" property="field(INN)" size="24" maxlength="10"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">
						<bean:message key="label.receiver.account" bundle="personsBundle"/><span class="asterisk">*</span>
					</td>
					<td>
						<html:text readonly="${not isShowSaves}" property="field(account)" styleId="value(account)" size="30" maxlength="25"/>
					</td>
				</tr>
				<tr>
					<td class="pmntInfAreaTitle" colspan="2"><bean:message key="label.receiver.bank.title" bundle="personsBundle"/></td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">
						<nobr><bean:message key="label.receiver.bank.name" bundle="personsBundle"/><span class="asterisk">*</span></nobr>
					</td>
					<td>
						<nobr>
						<html:text readonly="true" property="field(bankName)" size="80"/>
						<c:if test="${isShowSaves}">
							<input type="button" class="buttWhite smButt" onclick="javascript:openNationalBanksDictionary(setBankInfo,getFieldValue('field(bankName)'),getFieldValue('field(bankCode)'));" value="..."/>
						</c:if>
						</nobr>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">
						<bean:message key="label.receiver.bank.bic" bundle="personsBundle"/><span class="asterisk">*</span>
					</td>
					<td>
						<html:text readonly="${not isShowSaves}" property="field(bankCode)" size="24" maxlength="9"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">
						<bean:message key="label.receiver.bank.corAccount" bundle="personsBundle"/><span class="asterisk">*</span>
					</td>
					<td>
						<html:text readonly="${not isShowSaves}" property="field(correspondentAccount)" size="24" maxlength="20"/>
					</td>
				</tr>
				<html:hidden property="field(personId)" value="${form.person}"/>
				<html:hidden property="kind" value="J"/>			
			</tiles:put>
			<tiles:put name="buttons">

			</tiles:put>
			<tiles:put name="alignTable" value="center"/>
		</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>


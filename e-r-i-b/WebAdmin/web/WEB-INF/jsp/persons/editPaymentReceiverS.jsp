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
		<tiles:put name="submenu" type="string" value="PaymentReceiversI"/>

		<c:choose>
			<c:when test="${empty form.id}">
				<tiles:put name="pageTitle" type="string" value="Получатели (физ. лица). Новый получатель."/>
			</c:when>
			<c:otherwise>
				<tiles:put name="pageTitle" type="string" value="Получатели (физ. лица). Редактирование получателя"/>
			</c:otherwise>
		</c:choose>

		<tiles:put name="menu" type="string">
		<c:if test="${isShowSaves}">
			<tiles:insert definition="commandButton" flush="false" operation="EditPaymentReceiverOperationAdmin">
				<tiles:put name="commandKey" value="button.save"/>
				<tiles:put name="commandHelpKey" value="button.save.receiver.help"/>
				<tiles:put name="commandTextKey" value="button.save.receiver"/>
				<tiles:put name="bundle" value="personsBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="isDefault" value="true"/>
				<tiles:put name="postbackNavigation" value="true"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</c:if>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandHelpKey" value="button.receivers-list.close.help"/>
				<tiles:put name="commandTextKey" value="button.receivers-list"/>
				<tiles:put name="bundle" value="personsBundle"/>
				<tiles:put name="action"  value="persons/receivers/list.do?kind=S&person=${form.person}"/>
				<tiles:put name="image"   value=""/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">
			<script type="text/javascript">
				function setOfficeInfo ( officeInfo )
				{
					setElement('field(region)',officeInfo["regionBank"]);
					setElement('field(branch)',officeInfo["branch"]);
					setElement('field(office)',officeInfo["office"]);
					setElement('field(officeKey)',officeInfo["officeKey"]);
					setElement('field(bankName)',officeInfo["bankName"]);
					setElement('field(correspondentAccount)',officeInfo["corAccount"]);
					setElement('field(bankCode)',officeInfo["bankCode"]);
				}
			</script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="data">
				<tr style="height:20px">
					<td class="Width120 Label">
						<bean:message key="label.receiver.alias" bundle="personsBundle"/><span class="asterisk">*</span>
					</td>
					<td>
						<html:text readonly="${not isShowSaves}" property="field(alias)" size="25" maxlength="16"/>
					</td>
				</tr>
				<tr style="height:20px">
					<td class="Width120 Label">
						<bean:message key="label.receiver.fio" bundle="personsBundle"/><span class="asterisk">*</span>
					</td>
					<td>
						<html:text readonly="${not isShowSaves}" property="field(name)" size="80" maxlength="128"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 Label">
						<bean:message key="label.receiver.account" bundle="personsBundle"/><span class="asterisk">*</span>
					</td>
					<td>
						<html:text readonly="${not isShowSaves}" property="field(account)" size="24"  maxlength="20"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 Label">
						<bean:message key="label.receiver.sbrf.tb" bundle="personsBundle"/><span class="asterisk">*</span>
					</td>
					<td>
						<html:text property="field(region)" readonly="true" size="3" maxlength="3"/>
						<c:if test="${isShowSaves}">
							<input type="button" class="buttWhite smButt" onclick="openOfficesDictionary(setOfficeInfo);" value="..."/>
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="Width120 Label">
						<bean:message key="label.receiver.sbrf.branch" bundle="personsBundle"/><span class="asterisk">*</span>
					</td>
					<td>
						<html:text property="field(branch)" size="10"  readonly="true" maxlength="4"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 Label">
						<bean:message key="label.receiver.sbrf.office" bundle="personsBundle"/>
					</td>
					<td>
						<html:text property="field(office)" size="10"  readonly="true" maxlength="7"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 Label">
						<bean:message key="label.receiver.info" bundle="personsBundle"/>
					</td>
					<td>
						<html:textarea readonly="${not isShowSaves}" property="field(description)" rows="4" cols="81"/>
					</td>
				</tr>
                </tiles:put>
            </tiles:insert>
			<html:hidden property="field(officeKey)"/>
			<html:hidden property="field(bankName)"/>
			<html:hidden property="field(correspondentAccount)"/>
			<html:hidden property="field(bankCode)"/>
			<html:hidden property="field(fullName)"/>
			<html:hidden property="field(personId)" value="${form.person}"/>
			<html:hidden property="kind" value="S"/>
	<c:if test="${not empty person}">
		<script type="text/javascript">
			setElement("field(fullName)","${person.fullName}");
		</script>
	</c:if>
		</tiles:put>

	</tiles:insert>

</html:form>


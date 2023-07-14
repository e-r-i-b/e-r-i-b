<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/schedules/edit">

	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

	<tiles:insert definition="schedulesEdit">

		<tiles:put name="pageTitle" type="string">
			<bean:message key="schedules.edit.title" bundle="notificationsBundle"/>
		</tiles:put>

		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.cancel"/>
				<tiles:put name="commandHelpKey" value="button.cancel.help"/>
				<tiles:put name="bundle" value="notificationsBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="action" value="/schedules/list.do?kind=${form.kind}"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">
			<html:hidden property="id"/>
			<html:hidden property="kind"/>
			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value="editSchedule"/>
				<tiles:put name="name" value="Редактирование расписания"/>
				<tiles:put name="description" value="Используйте данную форму для изменения списка расписаний."/>
				<tiles:put name="data">
					<tr>
						<td class="Width120 LabelAll">Рассылка</td>
						<td>
							<html:select property="distributionKey" disabled="${form.id>0}">
							<html:optionsCollection property="distributions"
						          value="key" label="description"/>
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="Width120 LabelAll">Выражение</td>
						<td>
							<html:text property="field(expression)"/>
						</td>
					</tr>
					<tr>
						<td class="Width120 LabelAll">Описание</td>
						<td>
							<html:text property="field(description)"/>
						</td>
					</tr>
				</tiles:put>
				<tiles:put name="buttons">
					<tiles:insert definition="commandButton" flush="false">
						<tiles:put name="commandKey" value="button.save"/>
						<tiles:put name="commandHelpKey" value="button.save.help"/>
						<tiles:put name="bundle" value="notificationsBundle"/>
						<tiles:put name="isDefault" value="true"/>
						<tiles:put name="postbackNavigation" value="true"/>
					</tiles:insert>
				</tiles:put>
				<tiles:put name="alignTable" value="center"/>
			</tiles:insert>
		</tiles:put>

	</tiles:insert>

</html:form>

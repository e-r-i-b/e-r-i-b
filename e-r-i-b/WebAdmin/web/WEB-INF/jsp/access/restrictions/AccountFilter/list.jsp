<%--
  User: Kosyakov
  Date: 15.11.2005
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/access/restrictions/AccountList" onsubmit="return setEmptyAction(event);">

	<tiles:insert definition="dictionary">

		<tiles:put name="pageTitle" type="string" value="Выбор счетов"/>

		<tiles:put name="menu" type="string">
			<phiz:menuButton id="b1"
			                 onclick="javascript:turnSelection (true,'selectedIds',event)"
			                 title="Разрешить операцию для всех счетов">
				Разрешить все
			</phiz:menuButton>
			<phiz:menuButton id="b2"
			                 onclick="javascript:turnSelection (false,'selectedIds',event)"
			                 title="Запретить операцию для всех счетов">
				Запретить все
			</phiz:menuButton>
			<phiz:menuButton id="b3"
			                 operationKey="button.save" bundle="personsBundle"
			                 title="Сохранить задание параметров">Сохранить
			</phiz:menuButton>
			<phiz:menuButton id="b4" onclick="window.close();" title="Закрыть окно"
			                >Отменить
			</phiz:menuButton>
		</tiles:put>

		<tiles:put name="data" type="string">
		<tiles:insert definition="tableTemplate" flush="false">
			<tiles:put name="id" value="accountsFilterList"/>
			<tiles:put name="text" value=""/>
			<tiles:put name="head">
	            <td>Доступ</td>
				<td>Номер счета</td>
			</tiles:put>
			<tiles:put name="data">
				<c:set var="form" value="${AccountListRestrictionForm}"/>
				<% int lineNumber = 0;%>
				<c:forEach var="accountLink" items="${form.accounts}">
				<% lineNumber++;%>
					<c:set var="account" value="${accountLink.account}"/>
					<tr class="listLine<%=lineNumber%2%>">
						<td id="accessCell<%=lineNumber%>" class="ListItem">
							<html:multibox property="selectedIds" style="border:none"
						          onclick="setAccessDescription(this)">${accountLink.id}</html:multibox>
						<script>getAccessDescription('<%=lineNumber%>');</script>
						</td>
						<td class="ListItem">${account.number} ${account.type}</td>
					</tr>
				</c:forEach>
				</tiles:put>
		</tiles:insert>

		</tiles:put>

	</tiles:insert>

</html:form>

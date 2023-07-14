<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<!--TODO grid--> 
<html:form action="/schedules/list">

	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="schedulesList">
        <!--ѕризнак form.kind добавлен в значение submenu дл€ того, чтобы была возможность -->
        <!--независимо помечать различные пункты меню, из которых можно попасть на эту страницу -->
        <tiles:put name="submenu" type="string" value="Schedules${form.kind}"/>

        <tiles:put name="pageTitle" type="string">
			<bean:message key="schedules.list.title" bundle="notificationsBundle"/>
		</tiles:put>

		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.add"/>
				<tiles:put name="commandHelpKey" value="button.add.help"/>
				<tiles:put name="bundle" value="notificationsBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="action" value="/schedules/edit.do?kind=${form.kind}"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>

			<script type="text/javascript">
				var addUrl = "${phiz:calculateActionURL(pageContext,'/schedules/edit')}";
				function doEdit()
				{
                    checkIfOneItem("selectedIds");
					if (!checkOneSelection("selectedIds", '¬ыберите одну строку!'))
						return;
					var id = getRadioValue(document.getElementsByName("selectedIds"));
					window.location = addUrl + "?kind=${form.kind}&id=" + id;
				}
			</script>
		</tiles:put>

		<tiles:put name="data" type="string">
            <html:hidden property="kind"/>
			<tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="scheduleList"/>
                <tiles:put name="text" value="—писок расписаний"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                        <tiles:put name="bundle" value="notificationsBundle"/>
                        <tiles:put name="onclick" value="doEdit();"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle" value="notificationsBundle"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', '¬ыберите строку');
                            }
                        </tiles:put>
                        <tiles:put name="confirmText" value="¬ы действительно хотите удалить выбранное расписание?"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="head">
                      <td width="10px">&nbsp;</td>
					  <td width="90%">ќписание</td>
					  <td width="10%" nowrap="true">¬ыражение</td>
                </tiles:put>
                <tiles:put name="data">
					<c:forEach var="distribution" items="${form.data}">
					<tr>
					    <td colspan="3" class="tblGroupTitle">${distribution.description}&nbsp;</td>
					</tr>
					<c:forEach var="schedule" items="${distribution.schedules}">
					<c:if test="${not empty schedule}">
					<tr>
						<td class="ListItem">
							<html:multibox property="selectedIds"
							               value="${schedule.id}"/>&nbsp;
						</td>
						<td class="ListItem">${schedule.description}&nbsp;</td>
						<td class="ListItem">${schedule.expression}&nbsp;</td>
					</tr>
					</c:if>
					</c:forEach>
					</c:forEach>
					</tiles:put>                    
                </tiles:insert>
		</tiles:put>

	</tiles:insert>

</html:form>

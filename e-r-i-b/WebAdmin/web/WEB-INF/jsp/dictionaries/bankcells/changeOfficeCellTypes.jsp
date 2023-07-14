<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/bankcells/officeCells">
	<tiles:insert definition="bankcellsMain">
		<tiles:put name="submenu" type="string" value="OfficeCellTypes"/>
		<tiles:put name="pageTitle" type="string" value="Офисы / Сейфовые ячейки"/>

		<tiles:put name="menu" type="string">
		</tiles:put>

		<%-- данные --%>
		<tiles:put name="data" type="string">
			<c:set var="form" value="${ChangeOfficeCellTypesForm}"/>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="ServicesTable"/>
                <tiles:put name="text" value="Сейфовые ячейки"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="bankcellsBundle"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="head">
                    <td height="20px" width="100%">
						<bean:message key="label.office.cells" bundle="bankcellsBundle"/>
					</td>
					<td height="20px" width="117px" nowrap="true">
						<bean:message key="label.presence" bundle="bankcellsBundle"/>
					</td>
                </tiles:put>
                <tiles:put name="data">
				<c:forEach var="office" items="${form.offices}">
					<tr class="listLine0">
						<td colspan="2" class="listItem" width="100%" valign="top">
							<a href="javascript:showOrHideItems('cellTypes${office.code.id}')"
							   title="Показать/скрыть сейфовые ячейки">
								<bean:write name="office" property="name"/>
							</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellspacing="0" cellpadding="0" width="100%" id="cellTypes${office.code.id}" style="display:block">
								<c:forEach var="cellType" items="${form.cellTypes}" varStatus="lineInfo">
									<tr class="listLine${lineInfo.count % 2}">
										<td class="listItem" width="100%" valign="top">
											&nbsp;
											<bean:write name="cellType" property="description"/>
											&nbsp;
										</td>
										<td class="listItem" width="117px" nowrap="true" align="center">
											&nbsp;
											<html:multibox property="cells(${office.code.id})" style="border:none">
												<bean:write name="cellType" property="id"/>
											</html:multibox>
											&nbsp;
										</td>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
				</c:forEach>
                </tiles:put>
             </tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>

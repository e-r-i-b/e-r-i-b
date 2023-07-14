<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/bankcells/presence">
	<tiles:insert definition="bankcellsMain">
		<tiles:put name="submenu"   type="string" value="ChangePresence"/>
		<tiles:put name="pageTitle" type="string" value="Свободные сейфы"/>

		<tiles:put name="menu" type="string">
		</tiles:put>

		<%-- данные --%>
		<tiles:put name="data" type="string">
			<c:set var="form" value="${ChangeCellTypesPresenceForm}"/>
			<tiles:insert definition="tableTemplate" flush="false">
				<tiles:put name="id" value="bankcellsPresence"/>
				<tiles:put name="text" value="Сейфовые ячейки"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="bankcellsBundle"/>
                    </tiles:insert>
                </tiles:put>
				<c:choose>
				<c:when test="${not empty form.cellTypesByOffice}">
					<tiles:put name="head">
	                    <td height="20px" width="100%">
							<bean:message key="label.office.cells" bundle="bankcellsBundle"/>
						</td>
						<td height="20px" width="117px" nowrap="true">
							<bean:message key="label.presence" bundle="bankcellsBundle"/>
						</td>
					</tiles:put>
					<tiles:put name="data">
						<c:forEach var="cellTypesByOffice" items="${form.cellTypesByOffice}">
							<c:set var="office" value="${cellTypesByOffice.key}"/>
							<tr class="listLine0">
								<td colspan="2" class="listItem" width="100%" valign="top">
									<a href="javascript:showOrHideItems('bankcells${office.code.id}')"
									   title="Показать/скрыть сейфовые ячейки">
										<bean:write name="office" property="name"/>
									</a>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="tblTdBorder">
									<table cellspacing="0" cellpadding="0" width="100%"
									       id="bankcells${office.code.id}" style="display:block">
										<c:forEach var="bankcell" items="${cellTypesByOffice.value}"
										           varStatus="lineInfo">
											<tr class="listLine${lineInfo.count % 2}">
												<td class="listItem" width="100%" valign="top">
													&nbsp;
													<bean:write name="bankcell"
													            property="cellType.description"/>
													&nbsp;
												</td>
												<td class="listItem" width="117px" nowrap="true" align="center">
													&nbsp;
													<html:multibox property="selectedIds" style="border:none">
														<bean:write name="bankcell" property="id"/>
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
				</c:when>
				<c:otherwise>
					<tiles:put name="isEmpty" value="true"/>
					<tiles:put name="emptyMessage" value="В офисах нет сейфовых ячеек!"/>  					
				</c:otherwise>
			</c:choose>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<c:set var="accessType" value="${policy.accessType}"/>
<c:set var="helpers"    value="${subform.helpers}"/>
<c:set var="namePrefix" value="${accessType}Access"/>
<c:set var="idPrefix"   value="${accessType}"/>

	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="${idPrefix}_ServicesTable"/>
		<tiles:put name="text" value=""/>
		<tiles:put name="settingsBeforeInf">
				<c:if test="${accessType != 'employee'}">
				<tr>
					<td class="Width120 LabelAll">
						<c:out value="${policy.description}"/>
					</td>
					<td>
						<html:checkbox styleId="${idPrefix}_enabled" property="${namePrefix}.enabled" onclick="onAccessChanged('${idPrefix}')"
										disabled="${not isShowSaves}"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">
						Подтверждение входа
					</td>
					<td>
						<c:set var="choice" value="${policy.authenticationChoice}"/>

						<c:if test="${not (empty choice)}">
							<html:select property="${namePrefix}.properties(${choice.property})" disabled="${not isShowSaves}">
								<c:forEach var="option" items="${choice.options}">
									<html:option value="${option.value}"><c:out value="${option.name}"/></html:option>
								</c:forEach>
							</html:select>
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">
						Подтверждение операций
					</td>
					<td>
						<c:set var="choice" value="${policy.confirmationChoice}"/>

						<c:if test="${not (empty choice)}">
							<html:select property="${namePrefix}.properties(${choice.property})" disabled="${not isShowSaves}">
								<c:forEach var="option" items="${choice.options}">
									<html:option value="${option.value}"><c:out value="${option.name}"/></html:option>
								</c:forEach>
							</html:select>
						</c:if>
					</td>
				</tr>
				</c:if>
				<tr>
					<td class="Width120 LabelAll">
						<bean:message key="label.scheme" bundle="personsBundle"/>
					</td>
					<td>
						<html:hidden property="${namePrefix}.category"/>
						<html:select property="${namePrefix}.accessSchemeId"
									 onchange="updateStyle(this);onSchemeChanged(this)">
							<html:option value="" style="color:gray">
								<bean:message key="label.noSchemes" bundle="personsBundle"/>
							</html:option>
							<c:forEach var="helper" items="${helpers}">
								<c:set var="helperCategory" value="${helper.category}"/>
								<c:if test="${fn:length(helpers) > 1}">
								<optgroup label="<bean:message key="label.scheme.category.${helperCategory}" bundle="schemesBundle"/>" style="color:black;">
								</c:if>
									<html:option value="personal${helperCategory}"
												 style="color:red;"
												 styleId="${idPrefix}_personal_${helperCategory}">
										Индивидуальные права
									</html:option>
									<c:set var="accessSchemes" value="${helper.schemes}"/>
									<c:forEach var="scheme" items="${accessSchemes}">
										<html:option value="${scheme.id}"
													 style="color:black;"
													 styleId="${idPrefix}_${scheme.id}_${helperCategory}">
											<c:out value="${scheme.name}"/>
										</html:option>
									</c:forEach>
								<c:if test="${fn:length(helpers) > 1}">
								</optgroup>
								</c:if>
							</c:forEach>
						</html:select>
						<script type="text/javascript">updateStyle(getElement('${namePrefix}.accessSchemeId'));</script>
					</td>
				</tr>			
		</tiles:put>
	</tiles:insert>


<c:forEach var="helper" items="${helpers}">

<c:set var="services" value="${phiz:sort(helper.services, subform.servicesComparator)}"/>
<c:set var="helperCategory" value="${helper.category}"/>
<c:set var="operationsByService" value="${subform.operationsByServiceMap}"/>

	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="${idPrefix}_${helperCategory}_ServicesTable"/>
		<tiles:put name="text" value=""/>
		<tiles:put name="head">
	        <td width="100%" height="20px">Услуга</td>
			<td width="85px" height="20px" nowrap="true" align="left">
                <input id="${helperCategory}"
                       <c:if test="${not isShowSaves}">disabled</c:if>
                       name="${helperCategory}_${accessType}_isSelectAll"
                       value="on"
                       onclick="switchSelection('${helperCategory}_${accessType}_isSelectAll','${helperCategory}^${accessType}Access.selectedServices'); clearServiceRadioButton(id)"
                       style="border: medium none ;" type="checkbox">
                Доступ
            </td>
		</tiles:put>
		<tiles:put name="data">

	<c:forEach var="service" items="${services}" varStatus="li">
<%-- vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv --%>
		<c:set var="lineNumber"      value="${li.count}"/>
		<c:set var="checkboxId"      value="${idPrefix}_srv${service.id}_${helperCategory}_chk"/>
		<c:set var="operationsRowId" value="${idPrefix}_srv_${service.id}_or"/>

		<tr class="listLine${lineNumber % 2}">
			<td class="listItem"  valign="top">
				<a href="javascript:hideOrShow('${operationsRowId}')" title="Показать/скрыть операции">
					<c:out value="${service.name}"/>
				</a>
			</td>
			<td class="listItem" >
				<c:choose>
					<c:when test="${isShowSaves}">
						<html:multibox styleId="${checkboxId}"
							   property="${namePrefix}.selectedServices"
							   onclick="onServiceClick(this, '${helperCategory}');">
							${service.id}
						</html:multibox>
					</c:when>
					<c:otherwise>
						<html:multibox styleId="${checkboxId}"
							   property="${namePrefix}.selectedServices"
							   disabled="true">
							${service.id}
						</html:multibox>
					</c:otherwise>
				</c:choose>
				<label id="${checkboxId}_lbl" for="${checkboxId}">?</label>
			</td>
		</tr>
		<tr id="${operationsRowId}">
			<td colspan="2">
				<table cellspacing="0" cellpadding="0" width="100%" class="needBorder" style="border:1px solid #c5c5c5;border-width:0px 1px 1px 0px;color:#5f5f5f;font-weight:bold;">
					<c:forEach var="operation" items="${operationsByService[service]}">
						<tr class="listLine${lineNumber % 2}">
							<td class="listItem" valign="top" width="100%" style="padding-left:10px;">
								<bean:write name="operation" property="name"/>
							</td>
							<td class="listItem" width="117px" nowrap="true">
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
<%-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ --%>
</c:forEach>
<script type="text/javascript">
	initServicesTable('${namePrefix}');
	<c:forEach var="helper" items="${helpers}">
	hideOrShowTableRows('${idPrefix}_${helper.category}_ServicesTable', true, "${idPrefix}_.+");
	</c:forEach>
	onAccessChanged('${idPrefix}');
	onSchemeChanged(getElement("${namePrefix}.accessSchemeId"));
	<c:if test="${not isShowSaves}">
		disableAll('${idPrefix}');
	</c:if>
</script>
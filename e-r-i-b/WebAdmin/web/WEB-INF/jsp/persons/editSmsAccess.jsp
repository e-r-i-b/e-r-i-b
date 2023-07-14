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

<table cellspacing="2" cellpadding="1" style="margin-bottom:2px" class="fieldBorder">
	<c:if test="${accessType != 'employee'}">
	<tr>
		<td class="Width120 Label">
			<c:out value="${policy.description}"/>
		</td>
		<td>
			<html:checkbox styleId="${idPrefix}_enabled" property="${namePrefix}.enabled" onclick="onAccessChanged('${idPrefix}')"
							disabled="${not isShowSaves}"/>
		</td>
	</tr>
	<%--<tr>
		<td class="Width120 Label">
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
	</tr>--%>
	<tr>
		<td class="Width120 Label">
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
	<%--<tr>
		<td class="Width120 Label">
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
	</tr>--%>
</table>
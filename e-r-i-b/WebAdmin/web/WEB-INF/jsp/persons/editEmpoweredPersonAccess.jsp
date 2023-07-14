<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<c:set var="accessType" value="${policy.accessType}"/>
<c:set var="namePrefix" value="${accessType}Access"/>
<c:set var="idPrefix"   value="${accessType}"/>

<table cellspacing="2" cellpadding="1" style="margin-bottom:2px" class="fieldBorder">
	<tr>
		<td class="Width120 LabelAll">
			<c:out value="${policy.description}"/>
		</td>
		<td>
			<html:checkbox disabled="${not isShowSaves}" styleId="${idPrefix}_enabled" property="${namePrefix}.enabled" onclick="onAccessChanged('${idPrefix}')"/>
		</td>
	</tr>
	<tr>
		<td class="Width120 LabelAll">
			Подтверждение входа
		</td>
		<td>
			<c:set var="choice" value="${policy.authenticationChoice}"/>

			<c:if test="${not (empty choice)}">
				<html:select property="${namePrefix}.properties(${choice.property})"disabled="${not isShowSaves}">
					<c:forEach var="option" items="${choice.options}">
						<c:if test="${option.value!='otp' && namePrefix!='simple'}">
							<html:option value="${option.value}"><c:out value="${option.name}"/></html:option>
						</c:if>
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
				<html:select property="${namePrefix}.properties(${choice.property})"disabled="${not isShowSaves}">
					<c:forEach var="option" items="${choice.options}">
						<c:if test="${option.value!='otp' && namePrefix!='simple'}">
							<html:option value="${option.value}"><c:out value="${option.name}"/></html:option>
						</c:if>
					</c:forEach>
				</html:select>
			</c:if>
		</td>
	</tr>
    <tr>
        <td colspan="2">
           <c:set var="services" value="${phiz:sort(subform.services, subform.servicesComparator)}"/>
           <c:set var="helperCategory" value="empow"/>
           <c:set var="operationsByService" value="${subform.operationsByServiceMap}"/>
                <table cellpadding="0" cellspacing="0" width="100%" id="${idPrefix}_${helperCategory}_ServicesTable">
                <tr>
                    <td width="100%" height="20px">Услуга</td>
                    <td width="85px" height="20px" nowrap="true">
                    <input name="${accessType}_isSelectAll" value="on"
                           onclick="switchSelection('${accessType}_isSelectAll','${accessType}Access.selectedServices')"
                           style="border: medium none ;"
                           type="checkbox"
                           <c:if test="${not isShowSaves}">
                           disabled
                           </c:if>
                           >Доступ</td>
                </tr>
                <tr>
                    <td>
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
                                    <html:multibox styleId="${checkboxId}"
                                                   property="${namePrefix}.selectedServices"
                                                   onclick="onServiceClick(this, '${helperCategory}');">
                                        ${service.id}
                                    </html:multibox>
                                    <label id="${checkboxId}_lbl" for="${checkboxId}">?</label>
                                </td>
                            </tr>
                            <tr id="${operationsRowId}">
                                <td colspan="2">
                                    <table cellspacing="0" cellpadding="0" width="100%">
                                        <c:forEach var="operation" items="${operationsByService[service]}">
                                            <tr class="listLine${lineNumber % 2}">
                                                <td class="listItem" valign="top" width="100%">
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
                    </td>
                </tr>
                </table>
        </td>
    </tr>
</table>


<%-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ --%>
<script type="text/javascript">
	initServicesTable('${namePrefix}');
	hideOrShowTableRows('${idPrefix}_${helperCategory}_ServicesTable', true, "${idPrefix}_.+");
	onAccessChanged('${idPrefix}');
	<c:if test="${not isShowSaves}">
		disableAll('${idPrefix}');
	</c:if>
</script>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/departments/commissions/edit" onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="bundle" value="employeesBundle"/>
<c:set var="display" value="отображать"/>
<c:set var="notDisplay" value="не отображать"/>
<tiles:insert definition="departmentsEdit">
	<tiles:put name="submenu" type="string" value="EditTBCommissions"/>
    <tiles:put name="pageTitle"   type="string" value="Настройка комиссий"/>
    <tiles:put name="data" type="string">
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="text" value="Список операций"/>
            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle" value="${bundle}"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="data">
                <tr class="tblInfHeader" style="padding:2">
                    <td align="left"><bean:message key="label.operations.from.currency" bundle="commissionSettingBundle"/></td>
                    <td align="left"><bean:message key="label.comission"     bundle="providerBundle"/></td>
                </tr>
                <script type="text/javascript">
                    function changeDisplayLabel(paymentType)
                    {
                        var checkbx = document.getElementById('chkbx_' + paymentType);
                        var label = document.getElementById('display_' + paymentType);
                        if (checkbx.checked)
                            label.innerHTML = '&nbsp;<c:out value="${display}"/>';
                        else
                            label.innerHTML = '&nbsp;<c:out value="${notDisplay}"/>';
                    }
                </script>
                <% int lineNumber = 0;%>
                <c:forEach items="${form.currencySettings}" var="record">
                    <% lineNumber++;%>
                    <c:set var="value" value="${record.key}"/>
                    <c:set var="key" value="${record.value}"/>
                    <tr class="ListLine<%=lineNumber%2%>" style="padding:2">
                        <td>
                            <c:out value="${key}"/>
                        </td>
                        <td>
                            <table>
                                <tr>
                                    <td align=center class="ListItem" width="20px" style="border: 0px;">
                                        <input type="checkbox" name="selectedIds" value="${value.first}" id="chkbx_${value.first}"
                                               <c:if test="${value.second}"> checked="true"</c:if>
                                               onclick="changeDisplayLabel('${value.first}');">
                                    </td>
                                    <td align=center width="90px" style="border: 0px;">
                                        <c:choose>
                                            <c:when test="${value.second}">
                                                <div id="display_${value.first}">
                                                    &nbsp;<c:out value="${display}"/>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div id="display_${value.first}">
                                                    &nbsp;<c:out value="${notDisplay}"/>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </c:forEach>
                <tr class="tblInfHeader" style="padding:2">
                    <td align="left"><bean:message key="label.operations.from.rub" bundle="commissionSettingBundle"/></td>
                    <td align="left"><bean:message key="label.comission"     bundle="providerBundle"/></td>
                </tr>
                <c:forEach items="${form.rurSettings}" var="record">
                    <% lineNumber++;%>
                    <c:set var="value" value="${record.key}"/>
                    <c:set var="key" value="${record.value}"/>
                    <tr class="ListLine<%=lineNumber%2%>" style="padding:2">
                        <td>
                            <c:out value="${key}"/>
                        </td>
                        <td>
                            <table>
                                <tr>
                                    <td align=center class="ListItem" width="20px" style="border: 0px;">
                                        <input type="checkbox" name="selectedIds" value="${value.first}" id="chkbx_${value.first}"
                                               <c:if test="${value.second}"> checked="true"</c:if>
                                               onclick="changeDisplayLabel('${value.first}');">
                                    </td>
                                    <td align=center width="90px" style="border: 0px;">
                                        <c:choose>
                                            <c:when test="${value.second}">
                                                <div id="display_${value.first}">
                                                    &nbsp;<c:out value="${display}"/>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div id="display_${value.first}">
                                                    &nbsp;<c:out value="${notDisplay}"/>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </c:forEach>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>

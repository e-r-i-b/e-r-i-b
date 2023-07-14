<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html:form action="/ermb/migration/conflict">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="isEmpty" value="${empty form.conflicts}"/>
    <tiles:importAttribute/>

    <tiles:insert definition="migrationMain">
        <c:choose>
            <c:when test="${param.vip}">
                <tiles:put name="submenu" type="string" value="VipClients"/>
                <c:set var="backUrl" value="/ermb/migration/vip.do"/>
                <tiles:put name="pageTitle" type="string">
                    <bean:message key="migration.conflict.vip.title" bundle="migrationBundle"/>
                </tiles:put>
            </c:when>
            <c:otherwise>
                <tiles:put name="submenu" type="string" value="Clients"/>
                <c:set var="backUrl" value="/ermb/migration/list.do"/>
                <tiles:put name="pageTitle" type="string">
                    <bean:message key="migration.conflict.client.title" bundle="migrationBundle"/>
                </tiles:put>
            </c:otherwise>
        </c:choose>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function checkIfAllRadioGroupsChecked()
                {
                    var result = true;
                    $("input:radio").each(function(){
                        var name = $(this).attr("name");
                        if($("input:radio[name="+name+"]:checked").length == 0)
                        {
                            result = false;
                            return result;
                        }
                    });

                    if (!result)
                        alert('Для всех телефонов необходимо указать клиента, которому телефон принадлежит, или удалить его из системы');
                    return result;
                }

                function updateFieldFromRadio(name, id)
                {
                    var field = getElement('result('+name+')');
                    field.value = id;
                }
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <html:hidden property="id"/>
                <html:hidden property="vip"/>
                <tiles:put name="text">
                    <bean:message key="client.table.title" bundle="migrationBundle"/>
                </tiles:put>
                <tiles:put name="grid">
                    <c:if test="${isEmpty}">
                        <div class="messageTab" align="center">
                            <bean:message key="empty.conflicts.message" bundle="migrationBundle"/>
                        </div>
                    </c:if>
                    <c:forEach var="conflict" items="${form.conflicts}">
                        <html:hidden property="result(${conflict.phone})"
                                     value="${conflict.status=='RESOLVED_TO_OWNER'?conflict.ownerId:'RESOLVED_TO_DELETE'}"/>
                        <div class="gridDiv">
                            <h2 style="padding-left:10px;">
                                <bean:message key="label.conflict.phone" bundle="migrationBundle"/>
                                <c:out value="${conflict.phone}"/>
                            </h2>
                            <div style="padding-left: 40px">
                                <div style="padding-left:10px;">
                                    <bean:message key="label.conflict.choice" bundle="migrationBundle"/>
                                </div>

                                <c:forEach var="client" items="${conflict.clients}">
                                    <table>
                                        <tr>
                                            <td>
                                                <input name="${conflict.phone}" type="radio" onchange="updateFieldFromRadio(${conflict.phone}, ${client.id})"
                                                       ${(conflict.status=='RESOLVED_TO_OWNER' and conflict.ownerId==client.id) ? 'checked' : ''}>
                                            </td>
                                            <td class="gridDiv" style="width:100%">
                                                <table>
                                                    <tr>
                                                        <td>
                                                            <bean:message key="label.fio" bundle="migrationBundle"/>:
                                                        </td>
                                                        <td>
                                                            <c:out value="${client.firstName}"/>
                                                            <c:out value="${client.middleName}"/>
                                                            <c:out value="${client.lastName}"/>
                                                            <c:if test="${client.vipOrMvs}">
                                                                <span class="gridDiv">VIP/МВС</span>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <bean:message key="label.document" bundle="migrationBundle"/>:
                                                        </td>
                                                        <td>
                                                            <c:out value="${client.document}"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <bean:message key="label.birthday" bundle="migrationBundle"/>:
                                                        </td>
                                                        <td>
                                                            <fmt:formatDate value="${client.birthday.time}" pattern="dd.MM.yyyy"/>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table></br>
                                </c:forEach>
                                <table>
                                    <tr>
                                        <td>
                                            <input name="${conflict.phone}" type="radio" onchange="updateFieldFromRadio(${conflict.phone}, 'RESOLVED_TO_DELETE')"
                                                    ${conflict.status=='RESOLVED_TO_DELETE' ? 'checked' : ''}/>
                                        </td>
                                        <td class="gridDiv" style="width:100%">
                                                Удалить телефон
                                                <c:out value="${conflict.phone}"/>
                                                из системы
                                        </td>
                                    </tr>
                                </table></br>
                            </div>
                        </div>
                    </c:forEach>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="commonBundle"/>
                        <tiles:put name="action"         value="${backUrl}"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="validationFunction" value="checkIfAllRadioGroupsChecked()"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

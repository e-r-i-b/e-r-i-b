<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"     prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"    prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"     prefix="bean"%>
<%@ taglib uri="http://rssl.com/tags"                           prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"              prefix="c" %>

<c:set var="imagePath" value="${skinUrl}/images"/>
<html:form action="/dictionaries/productRequirements/list" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="dictionary">
        <tiles:put name="pageTitle" type="string"><bean:message key="page.title" bundle="productRequirementsBundle"/></tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"         value="pagesBundle"/>
                <tiles:put name="onclick"        value="javascript:window.close()"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="productRequirementsList"/>
                <tiles:put name="text"><bean:message key="table.title" bundle="productRequirementsBundle"/></tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.select"/>
                        <tiles:put name="commandHelpKey" value="button.select.help"/>
                        <tiles:put name="bundle" value="pagesBundle"/>
                        <tiles:put name="onclick">sendRequirementsData()</tiles:put>
                    </tiles:insert>
                    <script type="text/javascript">
                        function sendRequirementsData()
                        {
                            var ids = document.getElementsByName("selectedIds");
                            var accTypesids = document.getElementsByName("selectedAccountTypes");
                            var selectedAcc = false;

                            var requirements = [];
                            var accTypes = [];
                            var state;
                            var stateName;
                            var elemName;
                            for (var i = 0; i < ids.length; i++)
                            {
                                if (ids.item(i).checked)
                                {
                                    state = document.getElementById("select_" + ids.item(i).value);
                                    stateName = state.options[state.selectedIndex].text;
                                    elemName = ensureElementByName("name_" + ids.item(i).value);
                                    requirements.push({'type': ids.item(i).value, 'typeName': trim(elemName.value),
                                        'state': trim(state.value), 'stateName': stateName});
                                    if (ids.item(i).value == 'ACCOUNT')
                                        selectedAcc = true;
                                }
                            }

                            var selectedProd = false;
                            for (i = 0; i < accTypesids.length; i++)
                            {
                                if (accTypesids.item(i).checked)
                                {
                                    selectedProd = true;
                                    state = document.getElementById("select_" + accTypesids.item(i).value);
                                    stateName = state.options[state.selectedIndex].text;
                                    elemName = ensureElementByName("name_" + accTypesids.item(i).value);
                                    if (!selectedAcc)
                                        accTypes.push({'type': accTypesids.item(i).value, 'typeName': trim(elemName.value),
                                            'state': trim(state.value), 'stateName': stateName});
                                    else
                                        accTypes.push({'type': accTypesids.item(i).value, 'typeName': trim(elemName.value),
                                            'state': '', 'stateName': ''});
                                }
                            }

                            if (!selectedAcc && selectedProd)
                            {
                                requirements.push({'type': 'ACCOUNT', 'typeName': '¬клады', 'state': '', 'stateName': ''});
                            }

                            window.opener.setRequirementsInfo(requirements, accTypes);
                            window.close();
                            return true;
                        }

                        function removeLock(id)
                        {
                            var select = $('#select_'+id);
                            if ($('#input_'+id).attr('checked'))
                                select.removeAttr('disabled');
                            else
                                select.attr('disabled', 'disabled');

                            if (id == 'ACCOUNT')
                            {
                                var elements = document.getElementById('accountRequirements').getElementsByTagName('input');
                                for (var i = elements.length-1; i >= 0; i--)
                                {
                                    var input = elements.item(i);
                                    if (input.type == 'checkbox')
                                        if ($('#input_'+id).attr('checked'))
                                        {
                                            input.disabled = 'disabled';
                                            input.checked = true;
                                            addLock(input.value);
                                        }
                                        else
                                        {
                                            input.removeAttribute('disabled');
                                            $('#select_'+input.value).removeAttr('disabled');
                                        }
                                }
                            }
                        }

                        function addLock(id)
                        {
                            var select = $('#select_'+id);
                            select.attr('disabled', 'disabled');
                        }

                        function showTypes(requirement)
                        {
                            var element = $('#'+requirement+'Requirements');
                            if ( element.css('display') == 'table-row')
                                element.css('display','none');
                            else
                                element.css('display','');
                        }

                        function changeState()
                        {
                            var newState = $("#select_ACCOUNT").val();
                            var elements = document.getElementById('accountRequirements').getElementsByTagName('input');
                            for (var i = elements.length-1; i >= 0; i--)
                            {
                                var input = elements.item(i);
                                if (input.type == 'checkbox')
                                {
                                    $('#select_'+input.value).val(newState);
                                }
                            }
                        }
                    </script>
                </tiles:put>

                <tiles:put name="data">
                    <c:set var="form" value="${ListProductRequirementsForm}"/>
                    <c:forEach items="${form.productRequirementTypes}" var="type">
                        <tr>
                            <td class="product">
                                <input id="input_${type}" type="checkbox" name="selectedIds" value="${type}" style="border:none;" onclick="removeLock('${type}')"/>

                                <c:set var="typeName"><bean:message key="type.${type}" bundle="productRequirementsBundle"/></c:set>
                                <input type="hidden" name="name_${type}" value="${typeName}"/>
                                <c:choose>
                                    <c:when test="${type == 'ACCOUNT'}">
                                        <span class="requirementWithTypes" onclick="showTypes('account')">${typeName}</span>
                                    </c:when>
                                    <c:otherwise>
                                        ${typeName}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="state">
                                <c:choose>
                                    <c:when test="${type == 'ACCOUNT'}">
                                        <html:select styleId="select_${type}" property="fields(state)" styleClass="select" disabled="true" onchange="changeState()">
                                            <html:option value="CONNECTED"><bean:message key="label.CONNECTED" bundle="productRequirementsBundle"/></html:option>
                                            <html:option value="NOTCONNECTED"><bean:message key="label.NOTCONNECTED" bundle="productRequirementsBundle"/></html:option>
                                        </html:select>
                                    </c:when>
                                    <c:otherwise>
                                        <html:select styleId="select_${type}" property="fields(state)" styleClass="select" disabled="true">
                                            <html:option value="CONNECTED"><bean:message key="label.CONNECTED" bundle="productRequirementsBundle"/></html:option>
                                            <html:option value="NOTCONNECTED"><bean:message key="label.NOTCONNECTED" bundle="productRequirementsBundle"/></html:option>
                                        </html:select>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:if test="${type == 'ACCOUNT'}">
                            <tr id="accountRequirements" style="display: none;">
                                <td colspan="2">
                                    <table style="padding-left: 20px;">
                                        <tbody>
                                            <c:forEach items="${form.accountTypes}" var="accountType">
                                                <tr>
                                                    <td>
                                                        <c:set var="id" value="${accountType.productId}"/>
                                                        <input id="input_${id}" type="checkbox" name="selectedAccountTypes" value="${id}" style="border:none;" onclick="removeLock('${id}')"/>
                                                        <input type="hidden" name="name_${id}" value="${accountType.name}"/>
                                                        ${accountType.name}
                                                    </td>
                                                    <td class="state" style="padding-right: 0">
                                                        <html:select styleId="select_${id}" property="fields(state)" styleClass="select" disabled="true">
                                                            <html:option value="CONNECTED"><bean:message key="label.CONNECTED" bundle="productRequirementsBundle"/></html:option>
                                                            <html:option value="NOTCONNECTED"><bean:message key="label.NOTCONNECTED" bundle="productRequirementsBundle"/></html:option>
                                                        </html:select>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

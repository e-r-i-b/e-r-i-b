<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<c:set var="isUseCasNsiDictionaries" value="${phiz:isUseCasNsiDictionaries()}"/>
<c:set var="actionPath" value="${isUseCasNsiDictionaries ? '/depositEntity/editVisibility' : '/deposits/editVisibility'}"/>

<html:form action="${actionPath}" onsubmit="return setEmptyAction(event);">

<tiles:insert definition="depositInfo">
<tiles:put name="submenu" type="string" value="depositVisibleInfo"/>

<tiles:put name="menu" type="string">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.cancel"/>
        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
        <tiles:put name="bundle" value="depositsBundle"/>
        <tiles:put name="image" value=""/>
        <tiles:put name="action" value="/deposits/list.do"/>
        <tiles:put name="viewType" value="blueBorder"/>
    </tiles:insert>
</tiles:put>

<c:set var="form" value="${EditDepositProductBankForm}"/>
<c:set var="entityVisibilityForm" value="${EditDepositEntityForm}"/>

<c:set var="depositType" value="${isUseCasNsiDictionaries ? entityVisibilityForm.id : form.id}"/>
<c:set var="depositName" value="${isUseCasNsiDictionaries ? entityVisibilityForm.depositName : form.depositName}"/>
<c:set var="available" value="${isUseCasNsiDictionaries ? entityVisibilityForm.available : form.available}"/>

<tiles:put name="data" type="string">
    <script type="text/javascript">

        function showDescSys(elementId)
        {
            if ($("input[name=" + elementId + "]").attr("checked") == true)
            {
                $("#" + elementId + "Label").html('доступен');
            }
            else
            {
                $("#" + elementId + "Label").html('не доступен');
            }
        }

        <%--Добавить ТБ--%>
        function setDepartmentInfo(result)
        {
            var tbListTable = $('#tbListTable');
            var tbList = tbListTable.find('table.standartTable tbody');
            var resId = result['tb'];

            if (tbList.find('input[value=' + resId + ']').length == 0)
            {
                tbListTable.find('div.tblNeedTableStyle').show();
                tbListTable.find('div.workspace-box').remove();

                var inputList = tbListTable.find('tr[class^="ListLine"] input');
                tbList.append(
                        '<tr class="ListLine' + inputList.length % 2 + '"><td class="listItem" align="center">' +
                                '<input type="checkbox" name="terbankIds" value=' + resId + '>' +
                                '</td><td class="listItem">' + result['name'] + '</td></tr>'
                );
            }
        }

        <%--Удалить выбранные ТБ--%>
        function removeDepartment()
        {
            if (!checkSelection("terbankIds", "Не выбран ТБ."))
                return;
            var tbListTable = $('#tbListTable');

            //удаляем строки с выбранными ТБ
            tbListTable.find('tr[class^="ListLine"] input:checked').parent().parent().remove();

            var inputList = tbListTable.find('tr[class^="ListLine"] input');
            //если не осталось ни одного ТБ, прячем таблицу и добавляем сообщение
            if (inputList.length == 0)
            {
                tbListTable.find('tr.tblInfHeader input')[0].checked = false;
                tbListTable.find('div.tblNeedTableStyle').hide();
                var emptyMsg = '<bean:message key="empty.tb.message" bundle="depositsBundle"/>';
                tbListTable.append(
                        '<div class="workspace-box roundBorder redBlock"><div class="r-content">' + emptyMsg +
                                '</div></div>'
                );
            }
        }

        <%--Прочекать инпуты перед отправкой в бин--%>
        function checkBeforeSave()
        {
            $('#tbListTable tr[class^="ListLine"] input').each(function () {this.checked = true;});
            return true;
        }

    </script>

    <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="id" value="editDepartments"/>
        <tiles:put name="name" value="Редактирование данных вклада"/>
        <tiles:put name="description" value="Используйте данную форму редактирования данных	вклада."/>

        <tiles:put name="data">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="cardProducts"/>
                <tiles:put name="text">Настройка доступности вклада для открытия</tiles:put>
                <tiles:put name="head">
                    <th width="20px" class="titleTable">
                        Номер
                    </th>
                    <th class="titleTable bold">
                        Наименование вклада
                    </th>
                    <th class="titleTable bold">
                        Доступность для открытия
                    </th>
                </tiles:put>
                <tiles:put name="data">
                    <tr>
                        <td>
                                ${depositType}
                        </td>
                        <td>
                            &quot;<c:out value="${depositName}"/>&quot;
                        </td>
                        <td>
                            <c:set var="mess" value="доступен"/>
                            <c:if test="${not available}">
                                <c:set var="mess" value="не доступен"/>
                            </c:if>

                            <c:set var="spanName" value="available"/>
                            <html:checkbox property="${spanName}" onclick="showDescSys('${spanName}');"/>
                            <label id="${spanName}Label" for="${spanName}">${mess}</label>
                        </td>
                    </tr>
                    <c:choose>
                        <c:when test="${isUseCasNsiDictionaries}">
                            <c:if test="${not empty entityVisibilityForm.depositEntitySubTypes}">
                                <tiles:insert page="/WEB-INF/jsp-sbrf/deposits/visibilityList.jsp" flush="false"/>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            ${form.visibilityHtml}
                        </c:otherwise>
                    </c:choose>
                </tiles:put>
            </tiles:insert>

            <fieldset>
                <legend>
                    Видимость вклада
                </legend>

                <table cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td>
                            <div style="float:right;">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.add"/>
                                    <tiles:put name="commandHelpKey" value="button.add.help"/>
                                    <tiles:put name="bundle" value="personsBundle"/>
                                    <tiles:put name="onclick" value="openDepartmentsDictionary(setDepartmentInfo, 'null', 1, true);"/>
                                </tiles:insert>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.remove"/>
                                    <tiles:put name="commandHelpKey" value="button.remove.help"/>
                                    <tiles:put name="bundle" value="personsBundle"/>
                                    <tiles:put name="onclick" value="removeDepartment();"/>
                                </tiles:insert>
                            </div>

                        </td>
                    </tr>

                    <tr>
                        <td id="tbListTable">
                            <tiles:insert definition="tableTemplate" flush="false">
                            <tiles:put name="head">
                            <th width="20px" class="titleTable">
                                <input name="isSelectedAllItems" type="checkbox" style="border:none"
                                       onclick="switchSelection('isSelectedAllItems','terbankIds')"/>
                            </th>
                            <th class="titleTable bold">
                                Территориальный банк
                            </th>
                            </tiles:put>

                            <tiles:put name="data">
                                <c:set var="departments" value="${isUseCasNsiDictionaries ? entityVisibilityForm.departments : form.departments}"/>
                                <c:forEach var="department" items="${departments}" varStatus="lineInfo">
                                    <tr class="ListLine${lineInfo.count%2}">
                                        <td class="listItem" align="center">
                                            <input type="checkbox" name="terbankIds" value="${department}">
                                        </td>
                                        <td class="listItem" nowrap="true">
                                                ${phiz:getTBNameByCode(department)}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tiles:put>
                            <tiles:put name="createEmpty" value="true"/>
                            <tiles:put name="emptyMessage">
                                <bean:message key="empty.tb.message" bundle="depositsBundle"/>
                            </tiles:put>
                            </tiles:insert>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </tiles:put>

        <tiles:put name="buttons">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="dictionariesBundle"/>
                <tiles:put name="image" value=""/>
                <tiles:put name="action" value="/deposits/list.do"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="depositsBundle"/>
                <tiles:put name="validationFunction" value="checkBeforeSave();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</tiles:put>
</tiles:insert>


</html:form>
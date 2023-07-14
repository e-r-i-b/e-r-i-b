<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/cards/limits/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="cardsLimitsList">
        <tiles:put name="submenu" type="string" value="CardsLimitsList"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="title.cardsLimits.dictionaries" bundle="cardsBundle"/>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="CardAmountStepOperationEdit">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add.help"/>
                <tiles:put name="bundle" value="cardsBundle"/>
                <tiles:put name="action" value="/cards/limits/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/cards/limits/edit')}"/>
                function doEdit()
                {
                    checkIfOneItem("selectedIds");
                    if (!checkSelection("selectedIds", 'Укажите одну запись'))
                        return;
                    var id = getRadioValue(document.getElementsByName("selectedIds"));
                    window.location = '${url}?id=' + id;
                }
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="cardsLimitsList"/>
                <tiles:put name="buttons">

                    <tiles:insert definition="clientButton" flush="false" operation="CardAmountStepOperationEdit">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                        <tiles:put name="bundle" value="cardsBundle"/>
                        <tiles:put name="onclick" value="doEdit()"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle" value="cardsBundle"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите лимит для удаления');
                            }
                        </tiles:put>
                        <tiles:put name="confirmText" value="Удалить выбранный лимит?"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="item" property="data" model="list">    
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectType" value="radio"/>
                        <sl:collectionParam id="selectProperty" value="id"/>
                        <sl:collectionParam id="onRowClick" value="selectRow(this);"/>
                        <sl:collectionItem title="Сумма лимита" value="${phiz:isoFormatAmountWithNoCents(item.value)}"/>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage" value="В справочнике не задано ни одного лимита"/>
            </tiles:insert>

        </tiles:put>

    </tiles:insert>


</html:form>
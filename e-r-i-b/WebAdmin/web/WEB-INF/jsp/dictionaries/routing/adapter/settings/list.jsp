<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core"            prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags"                         prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"   prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"   prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"  prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"  prefix="tiles" %>

<script type="text/javascript">
    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/dictionaries/routing/adapter/settings/edit')}"/>
    
    <%-- Идем редактировать свойство --%>
    function doEdit()
    {
        if (!checkSelection("selectedIds", 'Укажите одну запись') || !checkOneSelection("selectedIds", "Выберите одну запись"))
        {
            return;
        }
        
        window.location = '${url}?id=' + getRadioValue(document.getElementsByName("selectedIds")) + '&adapterId=${param['filter(adapterId)']}';
    }

    <%-- Удалить одно или более свойств адаптера --%>
    function doRemove()
    {
        checkIfOneItem("selectedIds");
        if (checkSelection('selectedIds', 'Укажите хотя бы одну запись'))
        {
            return confirm("Вы действительно хотите удалить " + getSelectedQnt('selectedIds') + " настройки адаптера?");
        }
        return false;
    }
</script>

<html:form action="/dictionaries/routing/adapter/settings/view" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="routingList" flush="false">
        <tiles:put name="pageTitle">
            <bean:message bundle="adapterBundle" key="page.settings.title"/>
        </tiles:put>
        <tiles:put name="mainmenu"  type="string" value="ExternalSystems"/>
        <tiles:put name="submenu"   type="string" value="Adapter"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"         value="adapterBundle"/>
                <tiles:put name="action"         value="dictionaries/routing/adapter/edit.do?id=${param['filter(adapterId)']}"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
            
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.settings.add"/>
                <tiles:put name="commandHelpKey" value="button.settings.add.help"/>
                <tiles:put name="bundle"         value="adapterBundle"/>
                <tiles:put name="action"         value="/dictionaries/routing/adapter/settings/edit.do?adapterId=${param['filter(adapterId)']}"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"     value="table.title.key"/>
                <tiles:put name="bundle"    value="adapterBundle"/>
                <tiles:put name="name"      value="key"/>
                <tiles:put name="maxlength" value="60"/>
            </tiles:insert>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"     value="table.title.category"/>
                <tiles:put name="bundle"    value="adapterBundle"/>
                <tiles:put name="name"      value="category"/>
                <tiles:put name="maxlength" value="60"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="AdapterSettingsListTable"/>

                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton"  flush="false">
                        <tiles:put name="commandTextKey"     value="button.edit"/>
                        <tiles:put name="commandHelpKey"     value="button.edit.help"/>
                        <tiles:put name="bundle"             value="adapterBundle"/>
                        <tiles:put name="onclick"            value="doEdit();"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"         value="button.remove"/>
                        <tiles:put name="commandHelpKey"     value="button.remove.help"/>
                        <tiles:put name="bundle"             value="adapterBundle"/>
                        <tiles:put name="validationFunction" value="doRemove();"/>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="grid">
                    <sl:collection id="element" model="list" property="data">
                        <sl:collectionParam id="selectName"       value="selectedIds"/>
                        <sl:collectionParam id="selectType"       value="checkbox"/>
                        <sl:collectionParam id="selectProperty"   value="id"/>

                        <sl:collectionItem property="key">
                            <sl:collectionItemParam id="title">
                                <bean:message bundle="adapterBundle" key="table.title.key"/>
                            </sl:collectionItemParam>
                        </sl:collectionItem>

                        <sl:collectionItem property="value">
                            <sl:collectionItemParam id="title">
                                <bean:message bundle="adapterBundle" key="table.title.value"/>
                            </sl:collectionItemParam>
                        </sl:collectionItem>

                        <sl:collectionItem property="category">
                            <sl:collectionItemParam id="title">
                                <bean:message bundle="adapterBundle" key="table.title.category"/>
                            </sl:collectionItemParam>
                        </sl:collectionItem>

                        <tiles:put name="emptyMessage">
                            <bean:message bundle="adapterBundle" key="empty.settings.message"/>
                        </tiles:put>
                    </sl:collection>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

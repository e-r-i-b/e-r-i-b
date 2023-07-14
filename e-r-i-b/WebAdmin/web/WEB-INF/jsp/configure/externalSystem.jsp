<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<html:form action="/adapters/configure" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="routingEdit">
        <tiles:put name="submenu" value="Configure"/>
        <tiles:put name="mainmenu" value="ExternalSystems"/>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function setAdapterInfo(adapter)
                {
                    setElement('field(adapterName)', adapter["name"]);
                    setElement('field(adapterId)', adapter["id"]);
                }

                <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/adapters/configure/externalSystem')}"/>
                function doEdit()
                {
                    checkIfOneItem("selectedIds");
                    if (!checkSelection("selectedIds", 'Пожалуйста, укажите одну запись') || (!checkOneSelection("selectedIds", 'Пожалуйста, укажите одну запись')))
                        return;
                    var systemName = getRadioValue(document.getElementsByName("selectedIds"));
                    window.location = '${url}?systemName=' + systemName;
                }
            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value=""/>
                <tiles:put name="name" value="Настройки параметров внешних систем"/>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Адаптер IQWAVE
                        </tiles:put>
                        <tiles:put name="data">
                            <html:hidden property="field(adapterId)"/>
                            <html:text property="field(adapterName)" readonly="true" size="30"/>
                            <input type="button" class="buttWhite smButt" onclick="openAdaptersDictionary(setAdapterInfo);" value="..."/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" operation="ConfigureExternalSystemsOperation">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
            <div class="clear"></div>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="NodesListTable"/>
                <tiles:put name="text">Список внешних систем</tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                        <tiles:put name="bundle"         value="configureBundle"/>
                        <tiles:put name="onclick"        value="doEdit();"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="externalSystemsList">
                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectType"     value="checkbox"/>
                        <sl:collectionParam id="selectProperty" value="first"/>

                        <sl:collectionItem property="second" title="Название"/>
                    </sl:collection>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
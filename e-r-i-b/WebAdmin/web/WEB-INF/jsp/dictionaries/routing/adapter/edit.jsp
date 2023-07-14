<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<html:form action="/dictionaries/routing/adapter/edit">
    <tiles:insert definition="routingEdit">
        <tiles:put name="mainmenu" value="ExternalSystems"/>
        <tiles:put name="submenu" value="Adapter" type="string"/>

        <tiles:put name="menu" type="string">
       </tiles:put>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
               <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/dictionaries/routing/adapter/settings/edit')}"/>
                var nodeTypes = new Array();

                function needSaveChange()
                {
                    if (isDataChanged())
                    {
                        alert("Необходимо сначала сохранить изменения в адаптере");
                        return;
                    }
                    window.location = '${url}?id=${EditAdapterForm.id}&nodeType=' + getFieldValue('field(nodeType)');
                }

                function changeNodeOrESBFlag()
                {
                    var checkedVal = $('[name=fields(adapterType)]:checked').val();
                    ensureElement("nodeIds").disabled = (checkedVal == 'ESB' || checkedVal == 'IGW');

                    if (checkedVal == 'ESB' || checkedVal == 'IGW')
                    {
                        hideOrShow("addressWebService", true);
                        hideOrShow("settingsButton", true);
                        hideOrShow("settingsSofiaButton", true);
                    }
                    else
                    {
                        var element = document.getElementById("nodeIds");
                        var ids = 0;
                        if (element != null)
                            ids = element.value;
                        var nodeType = nodeTypes[ids];
                        document.getElementById("field(nodeType)").value = nodeType;
                        if (nodeType == "SOFIA")
                        {
                            hideOrShow("addressWebService", false);
                            hideOrShow("settingsButton", true);
                            hideOrShow("settingsSofiaButton", false);

                        }
                        else
                        {
                            hideOrShow("addressWebService", true);
                            hideOrShow("settingsButton", false);
                            hideOrShow("settingsSofiaButton", true);
                        }

                        if (nodeType == "COD" || nodeType == "IQWAVE" || nodeType == "SOFIA")
                            hideOrShow("listenerUrl", false);
                        else
                            hideOrShow("listenerUrl", true);
                    }
                }

                $(document).ready(function()
                {
                   changeNodeOrESBFlag();
                });

            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <html:hidden property="id"/>
                <tiles:put name="id"  value="adapter"/>
                <tiles:put name="name"><bean:message bundle="adapterBundle" key="editform.name"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="adapterBundle" key="editform.title"/></tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="adapterBundle" key="label.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(name)" size="30"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <html:radio property="fields(adapterType)" value="NONE" styleId="isEsbFlag" onchange="changeNodeOrESBFlag()"/>
                            <bean:message key="label.interaction.NONE" bundle="adapterBundle"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <html:radio property="fields(adapterType)" value="ESB" styleId="isEsbFlag" onchange="changeNodeOrESBFlag()"/>
                            <bean:message key="label.interaction.ESB" bundle="adapterBundle"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <html:radio property="fields(adapterType)" value="IGW" styleId="isEsbFlag" onchange="changeNodeOrESBFlag()"/>
                            <bean:message key="label.interaction.IPS" bundle="adapterBundle"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="adapterBundle" key="label.node"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(nodeId)" styleClass="filterSelectMenu" styleId="nodeIds" onchange="changeNodeOrESBFlag()">
                                <c:set var="nodes" value="${EditAdapterForm.allNodesList}"/>
                                <c:forEach var="node" items="${nodes}">
                                    <html:option value="${node.id}">
                                        <c:out value="${node.name}"/>
                                        <script type="text/javascript">
                                            nodeTypes[${node.id}] = '${node.type}';
                                        </script>
                                    </html:option>
                                </c:forEach>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="adapterBundle" key="label.UUID"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(UUID)" size="30"/>
                        </tiles:put>
                        <tiles:put name="description">
                            <bean:message bundle="adapterBundle" key="warning.message"/>
                        </tiles:put>
                    </tiles:insert>

                    <div id="addressWebService">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="adapterBundle" key="label.address.web.service"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(addressWebService)" size="30"/>
                            </tiles:put>
                        </tiles:insert>
                    </div>

                    <div id="listenerUrl">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="adapterBundle" key="label.address.web.service.back"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(listenerUrl)" size="30"/>
                            </tiles:put>
                            <tiles:put name="description">
                                <bean:message bundle="adapterBundle" key="web.service.back.message"/>
                            </tiles:put>
                        </tiles:insert>
                    </div>
                    <html:hidden property="field(nodeType)" styleId="field(nodeType)"/>
                </tiles:put>
                <tiles:put name="buttons">
                    <span id="settingsButton" >
                        <tiles:insert definition="clientButton"  flush="false" operation="EditAdapterSettingsOperation">
                            <tiles:put name="commandTextKey" value="button.viewSettings"/>
                            <tiles:put name="commandHelpKey" value="button.viewSettings.help"/>
                            <tiles:put name="bundle"         value="adapterBundle"/>
                            <tiles:put name="onclick"        value="needSaveChange()" />
                        </tiles:insert>
                    </span>
                    <span id="settingsSofiaButton" >
                        <tiles:insert definition="clientButton" flush="false" operation="EditAdapterSettingsOperation">
                            <tiles:put name="commandTextKey" value="button.viewSettings.sofia"/>
                            <tiles:put name="commandHelpKey" value="button.viewSettings.sofia.help"/>
                            <tiles:put name="bundle"         value="adapterBundle"/>
                            <tiles:put name="onclick"        value="needSaveChange()"/>
                        </tiles:insert>
                    </span>
                    <tiles:insert definition="commandButton" flush="false" operation="EditAdapterOperation">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"         value="adapterBundle"/>
                        <tiles:put name="isDefault"        value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" operation="ListAdaptersOperation">
                       <tiles:put name="commandTextKey" value="button.cancel"/>
                       <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                       <tiles:put name="bundle"         value="adapterBundle"/>
                       <tiles:put name="action"         value="/dictionaries/routing/adapter/list.do"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>

</html:form>
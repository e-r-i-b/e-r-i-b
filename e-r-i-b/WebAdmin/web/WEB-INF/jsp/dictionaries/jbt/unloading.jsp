<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${UnloadJBTForm}"/>

<html:form action="/dictionaries/jbt/unloading" onsubmit="return setEmptyAction();">
    <tiles:insert definition="unloadingJBT">
        <tiles:put name="submenu" type="string" value="JBT"/>
        <tiles:put name="menu" type="string">
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id"  value="JBT"/>
                <tiles:put name="name"><bean:message bundle="jbtBundle" key="form.name"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="jbtBundle" key="form.description"/></tiles:put>
                <tiles:put name="data">

                <div>
                    <input type="radio" name="type" id="taxRadio" value="T" style="border:0" onclick="selectProviderType(ensureElement('taxRadio').value)"/> <bean:message key="label.type.tax" bundle="jbtBundle"/>
                <input type="radio" name="type" id="billingRadio" value="B" style="border:0" checked="true" onclick="selectProviderType(ensureElement('billingRadio').value)"/> <bean:message key="label.type.billing" bundle="jbtBundle"/>
                </div>
                <br/>

                <fieldset>
                    <legend><bean:message key="label.unload.period" bundle="jbtBundle"/></legend>

                    <p><bean:message key="label.unload.period.description" bundle="jbtBundle"/></p>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.unload.period.from" bundle="jbtBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <bean:message key="label.unload.period.date" bundle="jbtBundle"/>
                            <input type="text"
                                   name="field(unloadPeriodDateFrom)" class="dot-date-pick"
                                   value='<bean:write name="form" property="fields.unloadPeriodDateFrom" format="dd.MM.yyyy"/>'
                                   size="10" class="contactInput"/>
                            <bean:message key="label.unload.period.time" bundle="jbtBundle"/>
                            <input type="text"
                                   name="field(unloadPeriodTimeFrom)"
                                   value='<bean:write name="form" property="fields.unloadPeriodTimeFrom" format="HH:MM"/>'
                                   size="6" class="short-time-template contactInput"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.unload.period.to" bundle="jbtBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <bean:message key="label.unload.period.date" bundle="jbtBundle"/>
                            <input type="text"
                                   name="field(unloadPeriodDateTo)" class="dot-date-pick"
                                   value='<bean:write name="form" property="fields.unloadPeriodDateTo" format="dd.MM.yyyy"/>'
                                   size="10" class="contactInput" />

                            <bean:message key="label.unload.period.time" bundle="jbtBundle"/>
                            <input type="text"
                                   name="field(unloadPeriodTimeTo)"
                                   value='<bean:write name="form" property="fields.unloadPeriodTimeTo" format="HH:MM"/>'
                                   size="6" class="short-time-template"/>
                        </tiles:put>
                    </tiles:insert>
                </fieldset>

                <fieldset>
                    <html:hidden property="field(state)" styleId="state"/>
                    <html:hidden property="field(type)"/>

                    <script type="text/javascript">
                        doOnLoad(function(){
                                    if (${form.fields.relocateToDownload != null && form.fields.relocateToDownload == true})
                                    {
                                        <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/dictionaries/jbt/downloading')}"/>
                                        clientBeforeUnload.showTrigger=false;
                                        goTo('${downloadFileURL}');
                                        clientBeforeUnload.showTrigger=false;
                                    }
                        });
                        function showAllProviders()
                        {
                            var state = document.getElementById('state');
                            state.value = 'ALL';
                            disabledTables();
                        }

                        function showExceptProviders()
                        {
                            var state = document.getElementById('state');
                            state.value = 'EXCEPT';
                            disabledTable('except', false);
                            disabledTable('select', true);
                        }

                        function showSelectProviders()
                        {
                            var state = document.getElementById('state');
                            state.value = 'SELECT';
                            disabledTable('select', false);
                            disabledTable('except', true);
                        }

                        function showTable(prefix)
                        {
                            var selectElements = document.getElementsByName(prefix + "SelectedIds4Remove");
                            if (selectElements.length == 0)
                            {
                                return;
                            }
                            var table = document.getElementById('' + prefix + 'Providers');
                            var addButton = document.getElementById('' + prefix + 'AddButton');
                            var removeButton = document.getElementById('' + prefix + 'RemoveButton');
                            var checkbox = document.getElementById(prefix + "Checkbox");

                            table.style.display = "block";
                            checkbox.checked = false;
                            addButton.style.display  = "block";
                            removeButton.style.display = "block";
                        }

                        function disabledTable(prefix, value)
                        {
                            var selectElements = document.getElementsByName(prefix + "SelectedIds4Remove");
                            var addButton = document.getElementById(prefix + 'AddButton');
                            var removeButton = document.getElementById(prefix + 'RemoveButton');
                            var checkbox = document.getElementById(prefix + "Checkbox");

                            checkbox.disabled = value;
                            for (var i=0; i<selectElements.length; i++)
                            {
                                selectElements.item(i).disabled = value;
                            }
                            addButton.disabled  = value;
                            removeButton.disabled  = value;
                        }

                        function disabledTables()
                        {
                            disabledTable('except', true);
                            disabledTable('select', true);
                        }

                        function setProviderInfo(resource)
                        {
                            for (var i = 0; i < resource.length; i++)
                            {
                                setOneProviderInfo(resource[i])
                            }
                        }

                        function setOneProviderInfo(resource)
                        {
                            if (document.getElementById("tr" + resource['externalId']) != null)
                            {
                                alert("Данный поставщик услуг уже добавлен!");
                                return;
                            }

                            var state = document.getElementById('state').value.toLowerCase();
                            var table = document.getElementById('' + state + 'Providers');

                            var tbody = table.getElementsByTagName('TBODY')[3];
                            var tr = document.createElement("TR");
                            tbody.appendChild(tr);

                            tr.setAttribute("id",    "tr" + resource['externalId']);
                            tr.setAttribute("class", "ListLine" + (document.getElementsByName("selectedIds").length % 2));

                            var td1 = document.createElement("TD");
                            tr.appendChild(td1);
                            td1.setAttribute("align", "center");
                            td1.setAttribute("class", "listItem");
                            td1.innerHTML = "<input type='checkbox' name='" + state + "SelectedIds4Remove' value='"+ resource['externalId'] +"'" +  ">";

                            var td2 = document.createElement("TD");
                            tr.appendChild(td2);
                            td2.setAttribute("align", "center")
                            td2.setAttribute("width", "40%");
                            td2.setAttribute("class", "listItem");
                            td2.innerHTML = resource['code'];

                            var td3 = document.createElement("TD");
                            tr.appendChild(td3);
                            td3.setAttribute("align", "center");
                            td3.setAttribute("width", "60%");
                            td3.setAttribute("class", "listItem");
                            td3.innerHTML = resource['name'] + "<input type='checkbox' name='selectedIds' checked='checked' style='visibility:hidden;' value='"+ resource['externalId'] +"' >";
                            showTable('' + state);
                        }

                        function removeTableRow(prefix)
                        {
                            var currentState = document.getElementById('state').value.toLowerCase();
                            if (currentState != prefix)
                            {
                                return;
                            }
                            if (!checkSelection('' + prefix + 'SelectedIds4Remove', 'Выберите поставщика услуг!'))
                                return;

                            var state = document.getElementById('state').value.toLowerCase();
                            var table = document.getElementById('' + state + 'Providers');

                            var elements = document.getElementsByName('' + prefix + "SelectedIds4Remove");
                            for (var i = elements.length-1; i >= 0; i--)
                            {
                                if (elements[i].checked && table.hasChildNodes())
                                {
                                    var elem = document.getElementById("tr" + elements[i].value);
                                    table.getElementsByTagName('TBODY')[3].removeChild(elem);

                                    if (elements.length == 0)
                                    {
                                        var removeButton = document.getElementById('' + state + 'RemoveButton');
                                        removeButton.style.display  = "none";
                                        table.style.display = "none";
                                    }
                                }
                            }
                        }

                        function openProvidersDictionary(state)
                        {
                            var currentState = document.getElementById('state').value.toLowerCase();
                            if (state == currentState)
                            {
                                if (getElement("field(billingId)").value == "")
                                {
                                    alert("Выберите биллинговую систему!");
                                    return;
                                }
                                openProvidersDictionaryForEmployee(setOneProviderInfo, getElement("field(type)").value, getElement("field(billingId)").value);
                            }
                        }

                        function removeTableData(prefix)
                        {
                            var state = document.getElementById('state').value.toLowerCase();
                            var table = document.getElementById('' + state + 'Providers');
                            var elements = document.getElementsByName('' + prefix + "SelectedIds4Remove");

                            for (var i = elements.length-1; i >= 0; i--)
                            {
                                if (table.hasChildNodes())
                                {
                                    var elem = document.getElementById("tr" + elements[i].value);
                                    table.getElementsByTagName('TBODY')[3].removeChild(elem);
                                }

                                if (elements.length == 0)
                                {
                                    var removeButton = document.getElementById('' + state + 'RemoveButton');
                                    removeButton.style.display  = "none";
                                    table.style.display = "none";
                                }
                            }
                            ensureElement("all").checked = true;
                            showAllProviders();
                        }

                    </script>

                    <legend><bean:message key="label.providers" bundle="jbtBundle"/></legend>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="jbtBundle" key="label.billing"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(billingName)" readonly="true" size="40"/>
                            <html:hidden property="field(billingId)"/>
                            <input type="button" name="billingButton" class="buttWhite smButt" onclick="javascript:openBillingsDictionary(setBillingInfo);" value="..."/>
                        </tiles:put>
                    </tiles:insert>

                    <p><bean:message key="label.providers.description" bundle="jbtBundle"/></p>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <html:radio property="field(state)" styleId="all" value="ALL" onclick="showAllProviders();">
                                <bean:message bundle="jbtBundle" key="button.all"/>
                            </html:radio>
                        </tiles:put>
                    </tiles:insert>

                    <div>
                        <div class="float">
                            <html:radio property="field(state)" styleId="except" value="EXCEPT" onclick="showExceptProviders();">
                                <bean:message bundle="jbtBundle" key="button.except"/>
                            </html:radio>
                        </div>

                        <span id="exceptRemoveButton" style="display: none">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.remove"/>
                                <tiles:put name="commandHelpKey" value="button.remove"/>
                                <tiles:put name="bundle"         value="jbtBundle"/>
                                <tiles:put name="onclick"        value="removeTableRow('except')"/>
                                <tiles:put name="typeBtn"        value="enterDiv"/>
                                <tiles:put name="viewType"       value="buttonGrayNew"/>
                            </tiles:insert>
                        </span>
                        <span id="exceptAddButton">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.add.except"/>
                                <tiles:put name="commandHelpKey" value="button.add.except"/>
                                <tiles:put name="bundle"         value="jbtBundle"/>
                                <tiles:put name="onclick"        value="openProvidersDictionary('except')"/>
                                <tiles:put name="typeBtn"        value="enterDiv"/>
                                <tiles:put name="viewType"       value="buttonGrayNew"/>
                            </tiles:insert>
                        </span>
                    </div>

                    <div class="clear"></div>
                    <div id="exceptProviders" style="display:none">
                        <table width="100%" cellspacing="0" cellpadding="0" align="CENTER" class="standartTable">
                            <tr>
                                <th width="20px">
                                    <input type="checkbox" id="exceptCheckbox"
                                           onclick="switchSelection(this,'' + document.getElementById('state').value.toLowerCase() + 'SelectedIds4Remove');switchSelection(this,'selectedIds')">
                                </th>
                                <th>
                                    &nbsp;<bean:message bundle="jbtBundle" key="label.id"/>&nbsp;
                                </th>
                                <th>
                                    &nbsp;<bean:message bundle="jbtBundle" key="label.name"/>&nbsp;
                                </th>
                            </tr>
                        </table>
                    </div>

                    <div class="clear"></div>
                    <div>
                        <div class="float">
                            <html:radio property="field(state)" styleId="select" value="SELECT" onclick="showSelectProviders();">
                                <bean:message bundle="jbtBundle" key="button.select"/>
                            </html:radio>
                        </div>

                        <span id="selectRemoveButton" style="display: none">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.remove"/>
                                <tiles:put name="commandHelpKey" value="button.remove"/>
                                <tiles:put name="bundle"         value="jbtBundle"/>
                                <tiles:put name="onclick"        value="removeTableRow('select')"/>
                                <tiles:put name="typeBtn"        value="enterDiv"/>
                                <tiles:put name="viewType"       value="buttonGrayNew"/>
                            </tiles:insert>
                        </span>
                        <span id="selectAddButton">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.add.select"/>
                                <tiles:put name="commandHelpKey" value="button.add.select"/>
                                <tiles:put name="bundle"         value="jbtBundle"/>
                                <tiles:put name="onclick"        value="openProvidersDictionary('select')"/>
                                <tiles:put name="typeBtn"        value="enterDiv"/>
                                <tiles:put name="viewType"       value="buttonGrayNew"/>
                            </tiles:insert>
                        </span>
                    </div>
                    <div class="clear"></div>

                    <div id="selectProviders" style="display:none">
                        <table width="100%" cellspacing="0" cellpadding="0" class="standartTable">
                            <tr>
                                <th width="20px">
                                    <input type="checkbox" id="selectCheckbox"
                                           onclick="switchSelection(this,'' + document.getElementById('state').value.toLowerCase() + 'SelectedIds4Remove');switchSelection(this,'selectedIds')">
                                </th>
                                <th>
                                    &nbsp;<bean:message bundle="jbtBundle" key="label.id"/>&nbsp;
                                </th>
                                <th>
                                    &nbsp;<bean:message bundle="jbtBundle" key="label.name"/>&nbsp;
                                </th>
                            </tr>
                        </table>
                    </div>
                    <script type="text/javascript">
                        function setBillingInfo(billingInfo)
                        {
                            setElement('field(billingName)', billingInfo["name"]);
                            setElement('field(billingId)',   billingInfo["id"]);
                        }

                        function selectProviderType(type)
                        {
                            var isDisabled = (type == "B") ? false : true;
                            getElement("field(billingName)").disabled = isDisabled;
                            getElement("billingButton").disabled = isDisabled;
                            getElement("field(type)").value = type;
                            getElement("field(billingName)").value = "";
                            getElement("field(billingId)").value = "";
                            ensureElement("billingRadio").checked = !isDisabled;

                            removeTableData('except');
                            removeTableData('select');
                        }

                        selectProviderType("B");    //Первоначально устанавливается биллинговый поставщик услуг
                        showAllProviders();
                    </script>
                </fieldset>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.unload"/>
                        <tiles:put name="commandHelpKey" value="button.unload"/>
                        <tiles:put name="bundle" value="jbtBundle"/>
                        <tiles:put name="image" value=""/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
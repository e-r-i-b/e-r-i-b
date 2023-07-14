<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/provider/unloading" method="post">
    <tiles:insert definition="providersMain">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <tiles:put name="submenu" value="Providers"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="JBT"/>  <%--используем пиктограмму формы выгрузки ЖБТ--%>
                <tiles:put name="name">
                    <bean:message bundle="providerBundle" key="unloading.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="providerBundle" key="unloading.description"/>
                </tiles:put>

                <tiles:put name="data">
                    <script type="text/javascript">
                        doOnLoad(function(){
                                    if (${form.fields.relocateToDownload != null && form.fields.relocateToDownload == true})
                                    {
                                        <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/dictionaries/provider/downloading')}"/>
                                        clientBeforeUnload.showTrigger=false;
                                        goTo('${downloadFileURL}');
                                        clientBeforeUnload.showTrigger=false;
                                    }
                        });

                        function changeTableArea(prefix, hide)
                        {
                            var table = ensureElement(prefix + "Table");
                            if (table != null)
                            {
                                var selectedIds = document.getElementsByName(prefix);
                                hideOrShow(table, hide || selectedIds.length == 0);
                            }

                            var removeButton = ensureElement(prefix + "RemoveButton");
                            if (removeButton != null)
                                removeButton.style.display = (hide || selectedIds.length == 0) ? "none" : "block";
                        }

                        function removeTableRecord(prefix)
                        {
                            var deletedItemsName = prefix + 'SelectedIds4Remove';
                            if (!checkSelection(deletedItemsName, 'Выберите запись для удаления!'))
                                return;

                            var table = ensureElement(prefix + "Table");
                            var elements = document.getElementsByName(deletedItemsName);
                            for (var i = elements.length - 1; i >= 0 ; i--)
                            {
                                if (elements[i].checked && table.hasChildNodes())
                                {
                                    var elem = ensureElement("tr" + prefix + elements[i].value);
                                    table.getElementsByTagName('TBODY')[3].removeChild(elem);

                                    if (elements.length == 0)
                                    {
                                        table.style.display = "none";
                                        ensureElement(prefix + "RemoveButton").style.display = "none";
                                    }
                                }
                            }
                        }

                        function addTableRecord(resource, prefix, colCount)
                        {
                            if (ensureElement("tr" + prefix + resource['id']) != null)
                            {
                                alert("Данная запись уже добавлена!");
                                return;
                            }

                            var table = ensureElement(prefix + "Table");
                            var tbody = table.getElementsByTagName('TBODY')[3];

                            var tr = document.createElement("TR");
                            tbody.appendChild(tr);
                            tr.setAttribute("id",    "tr" + prefix + resource['id']);
                            tr.setAttribute("class", "ListLine" + (document.getElementsByName(prefix).length % 2));

                            var td1 = document.createElement("TD");
                            tr.appendChild(td1);
                            td1.setAttribute("align", "center");
                            td1.setAttribute("class", "listItem");
                            td1.innerHTML = "<input type='checkbox' name='" + prefix + "SelectedIds4Remove' value='"+ resource['id'] +"'>";

                            if (colCount == 3)
                            {
                                var td2 = document.createElement("TD");
                                tr.appendChild(td2);
                                td2.setAttribute("align", "center");
                                td2.setAttribute("width", "40%");
                                td2.setAttribute("class", "listItem");
                                td2.innerHTML = resource['code'];
                            }

                            var td3 = document.createElement("TD");
                            tr.appendChild(td3);
                            td3.setAttribute("align", "center");
                            td3.setAttribute("width", (colCount == 2) ? "100%" : "60%");
                            td3.setAttribute("class", "listItem");
                            td3.innerHTML = "<input type='checkbox' name='" + prefix + "' value='"+ resource['id'] +"' style='visibility:hidden;' checked='checked'/>" + resource['name'];


                            hideOrShow(ensureElement(prefix + "RemoveButton"), false);
                            hideOrShow(table, false);
                        }

                        function setDepartmentInfo(resource)
                        {
                            addTableRecord(resource, "departments", 2);
                        }

                        function setProviderInfo(resource)
                        {
                            addTableRecord(resource, "providers",   3);
                        }

                        function setPaymentServiceInfo(resource)
                        {
                            addTableRecord(resource, "services",    2);
                        }

                        function openDepartDictionary()
                        {
                            if (!getElement("deptRadio").checked)
                                openDepartmentsDictionary(setDepartmentInfo, 'null');
                        }

                        function openProvidDictionary()
                        {
                            if (!getElement("provRadio").checked)
                                openProvidersDictionaryForEmployee(setProviderInfo, 'B');
                        }

                        function openPaymServicesDictionary()
                        {
                            if (!getElement("servRadio").checked)
                                openPaymentServicesDictionary(setPaymentServiceInfo);
                        }
                        
                        addClearMasks(null,
                                function(event)
                                {
                                    clearInputTemplate('field(fromDate)', '__.__.____');
                                    clearInputTemplate('field(toDate)', '__.__.____');
                                });

                    </script>

                    <fieldset>
                        <legend>
                            <bean:message key="label.unload.period" bundle="providerBundle"/>
                        </legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.unload.period.from" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <input type="text"  name="field(fromDate)"
                                       value='<bean:write name="form" property="field(fromDate)" format="dd.MM.yyyy"/>'
                                       size="10" class="dot-date-pick contactInput"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.unload.period.to" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <input type="text"  name="field(toDate)"
                                       value='<bean:write name="form" property="field(toDate)" format="dd.MM.yyyy"/>'
                                       size="10" class="dot-date-pick contactInput"/>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                    <fieldset>
                        <legend>
                            <bean:message key="label.unloading" bundle="providerBundle"/>
                        </legend>

                        <div>
                            <html:checkbox property="useNotActiveProviders" name="form">
                                <bean:message bundle="providerBundle" key="label.unloading.use.not.active.providers"/>
                            </html:checkbox>
                        </div><br/>

                        <div class="chooseAction">
                            <input type="radio" name="deptRadio" id="deptRadioAll" onclick="changeTableArea('departments', true);" checked/>
                            <label for="deptRadioAll"><bean:message bundle="providerBundle" key="label.unloading.all.department"/></label>
                            <br/><br/>
                            <div class="float">
                                <input type="radio" name="deptRadio" id="deptRadioSelect" onclick="changeTableArea('departments', false);"/>
                                <label for="deptRadioSelect"><bean:message bundle="providerBundle" key="label.unloading.only.department"/> &nbsp;</label>
                            </div>
                            <div class="float marginTop5">
                                <span id="departmentsRemoveButton" style="display: none">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.remove"/>
                                        <tiles:put name="commandHelpKey" value="button.remove"/>
                                        <tiles:put name="bundle"         value="providerBundle"/>
                                        <tiles:put name="onclick"        value="removeTableRecord('departments');"/>
                                        <tiles:put name="typeBtn"        value="enterDiv"/>
                                        <tiles:put name="viewType"       value="buttonGrayNew"/>
                                    </tiles:insert>
                                </span>
                                <span id="departmentsAddButton">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.add"/>
                                        <tiles:put name="commandHelpKey" value="button.add.departments.help"/>
                                        <tiles:put name="bundle"         value="providerBundle"/>
                                        <tiles:put name="onclick"        value="openDepartDictionary();"/>
                                        <tiles:put name="typeBtn"        value="enterDiv"/>
                                        <tiles:put name="viewType"       value="buttonGrayNew"/>
                                    </tiles:insert>
                                </span>
                            </div>
                        </div>

                        <tiles:insert definition="gridTableTemplate" flush="false">
                            <tiles:put name="id"    value="departmentsTable"/>
                            <tiles:put name="style" value="display:none"/>
                            <tiles:put name="data">
                                <tr>
                                    <th width="20px">
                                        <input type="checkbox" id="AllDepartments"
                                            onclick="switchSelection(this,'departmentsSelectedIds4Remove');switchSelection(this,'departments')">
                                    </th>
                                    <th>
                                        &nbsp;<bean:message bundle="providerBundle" key="filter.name"/>&nbsp;
                                    </th>
                                </tr>
                            </tiles:put>
                        </tiles:insert>
                         <div class="clear"></div>  <br/>
                        <div class="chooseAction">
                            <input type="radio" name="provRadio" id="provRadioAll" onclick="changeTableArea('providers', true);" checked/>
                            <label for="provRadioAll"><bean:message bundle="providerBundle" key="label.unloading.all.providers"/></label>
                            <br/><br/>

                            <div class="float">
                                <input type="radio" name="provRadio" id="provRadioSelect" onclick="changeTableArea('providers', false);"/>
                                <label for="provRadioSelect"><bean:message bundle="providerBundle" key="label.unloading.only.providers"/>&nbsp;</label>
                            </div>
                            <div class="float marginTop5">
                                <span id="providersRemoveButton" style="display: none">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.remove"/>
                                        <tiles:put name="commandHelpKey" value="button.remove"/>
                                        <tiles:put name="bundle"         value="providerBundle"/>
                                        <tiles:put name="onclick"        value="removeTableRecord('providers');"/>
                                        <tiles:put name="typeBtn"        value="enterDiv"/>
                                        <tiles:put name="viewType"       value="buttonGrayNew"/>
                                    </tiles:insert>
                                </span>
                                <span id="providersAddButton">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.add"/>
                                        <tiles:put name="commandHelpKey" value="button.add.providers.help"/>
                                        <tiles:put name="bundle"         value="providerBundle"/>
                                        <tiles:put name="onclick"        value="openProvidDictionary();"/>
                                        <tiles:put name="typeBtn"        value="enterDiv"/>
                                        <tiles:put name="viewType"       value="buttonGrayNew"/>
                                    </tiles:insert>
                                </span>
                            </div>
                        </div>
                        <tiles:insert definition="gridTableTemplate" flush="false">
                            <tiles:put name="id"    value="providersTable"/>
                            <tiles:put name="style" value="display:none"/>
                            <tiles:put name="data">
                                <tr>
                                    <th width="20px">
                                        <input type="checkbox" id="AllProviders"
                                            onclick="switchSelection(this,'providersSelectedIds4Remove');switchSelection(this,'providers')">
                                    </th>
                                    <th>
                                        &nbsp;<bean:message bundle="providerBundle" key="label.code"/>&nbsp;
                                    </th>
                                    <th>
                                        &nbsp;<bean:message bundle="providerBundle" key="filter.name"/>&nbsp;
                                    </th>
                                </tr>
                            </tiles:put>
                        </tiles:insert>
                        <div class="clear"></div>  <br/>

                        <div class="chooseAction">
                            <input type="radio" name="servRadio" id="servRadioAll" onclick="changeTableArea('services', true);" checked/>
                            <label for="servRadioAll"><bean:message bundle="providerBundle" key="label.unloading.all.services"/></label>
                            <br/><br/>

                            <div class="float">
                                <input type="radio" name="servRadio" id="servRadioSelect" onclick="changeTableArea('services', false);"/>
                                <label for="servRadioSelect"><bean:message bundle="providerBundle" key="label.unloading.only.services"/> &nbsp;</label>
                            </div>
                            <div class="float marginTop5">
                                <span id="servicesRemoveButton" style="display: none">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.remove"/>
                                        <tiles:put name="commandHelpKey" value="button.remove"/>
                                        <tiles:put name="bundle"         value="providerBundle"/>
                                        <tiles:put name="onclick"        value="removeTableRecord('services');"/>
                                        <tiles:put name="typeBtn"        value="enterDiv"/>
                                        <tiles:put name="viewType"       value="buttonGrayNew"/>
                                    </tiles:insert>
                                </span>
                                <span id="servicesAddButton">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.add"/>
                                        <tiles:put name="commandHelpKey" value="button.add.services.help"/>
                                        <tiles:put name="bundle"         value="providerBundle"/>
                                        <tiles:put name="onclick"        value="openPaymServicesDictionary();"/>
                                        <tiles:put name="typeBtn"        value="enterDiv"/>
                                        <tiles:put name="viewType"       value="buttonGrayNew"/>
                                    </tiles:insert>
                                </span>
                            </div>
                        </div>
                        <tiles:insert definition="gridTableTemplate" flush="false">
                            <tiles:put name="id"    value="servicesTable"/>
                            <tiles:put name="style" value="display:none"/>
                            <tiles:put name="data">
                                <tr>
                                    <th width="20px">
                                        <input type="checkbox" id="AllServices"
                                            onclick="switchSelection(this,'servicesSelectedIds4Remove');switchSelection(this,'services')">
                                    </th>
                                    <th>
                                        &nbsp;<bean:message bundle="providerBundle" key="filter.name"/>&nbsp;
                                    </th>
                                </tr>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.unload"/>
                        <tiles:put name="commandHelpKey" value="button.unload"/>
                        <tiles:put name="bundle"         value="providerBundle"/>
                        <tiles:put name="image"          value=""/>
                        <tiles:put name="autoRefresh"    value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
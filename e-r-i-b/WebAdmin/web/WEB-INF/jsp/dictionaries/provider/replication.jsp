<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/provider/replication" enctype="multipart/form-data">
    <tiles:insert definition="providersMain">
        <tiles:put name="needSave" value="false" type="string"/>
        <tiles:put name="submenu" value="Providers"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">

                <script type="text/javascript">

                    function showBillings(selectedType)
                    {
                        if (selectedType == "selected")
                        {
                            disabledTable(false);
                        }
                        else
                        {
                            disabledTable(true);
                        }
                    }

                    function disabledTable(value)
                    {
                        var aButton = document.getElementById("aButton");
                        var rButton = document.getElementById("rButton");
                        var checkbox = document.getElementById("selectedAll");
                        var selectElements = document.getElementsByName("selectedIdsForRemove");

                        checkbox.disabled = value;
                        for (var i=0; i<selectElements.length; i++)
                        {
                            selectElements.item(i).disabled = value;
                        }
                        aButton.disabled  = value;
                        rButton.disabled  = value;
                    }

                    function showTable()
                    {
                        var selectElements = document.getElementsByName("selectedIdsForRemove");
                        if (selectElements.length == 0)
                        {
                            return;
                        }
                        var table = document.getElementById('billings');
                        var addButton = document.getElementById('aButton');
                        var removeButton = document.getElementById('rButton');
                        var checkbox = document.getElementById("selectedAll");

                        checkbox.checked = false;
                        table.style.display = "block";
                        checkbox.checked = false;
                        addButton.style.display  = "";
                        removeButton.style.display = "";
                    }

                    function openDictionary()
                    {
                        var selectedBox = document.getElementById("selected");
                        if (selectedBox.checked)
                            openBillingsDictionary(setBillingInfo);
                    }

                    function setBillingInfo(resource)
                    {
                        if (document.getElementById("tr" + resource['id']) != null)
                        {
                            alert("Данная биллинговая система уже добавлена!");
                            return;
                        }

                        var billings = document.getElementById("billings");
                        var tbody = billings.getElementsByTagName('TBODY')[1];

                        var tr = document.createElement("TR");
                        tbody.appendChild(tr);
                        tr.setAttribute("id",    "tr" + resource['id']);
                        tr.setAttribute("class", "ListLine" + (document.getElementsByName("selectedIds").length % 2));

                        var td1 = document.createElement("TD");
                        tr.appendChild(td1);
                        td1.setAttribute("align", "center");
                        td1.setAttribute("class", "listItem");
                        td1.innerHTML = "<input type='checkbox' name='selectedIdsForRemove' value='"+ resource['id'] +"'>";

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
                        td3.innerHTML = "<input type='checkbox' name='selectedIds' value='"+ resource['id'] +"' style='visibility:hidden;' checked='checked'/>" + resource['name'];

                        showTable();

                    }

                    function removeTag()
                    {
                        var selectedBox = document.getElementById("selected");
                        if (!selectedBox.checked)
                            return;

                        if (!checkSelection('selectedIdsForRemove', 'Выберите биллинговую систему!'))
                            return;

                        var billings = document.getElementById("billings");
                        var elements = document.getElementsByName("selectedIdsForRemove");
                        for (var i = elements.length - 1; i >= 0 ; i--)
                        {
                            if (elements[i].checked && billings.hasChildNodes())
                            {
                                var elem = document.getElementById("tr" + elements[i].value);
                                billings.getElementsByTagName('TBODY')[1].removeChild(elem);

                                if (elements.length == 0)
                                {
                                    var rButton = document.getElementById("rButton");
                                    rButton.style.display  = "none";
                                    billings.style.display = "none";
                                }
                            }
                        }
                    }

                    function changefileValue(){
                        var filePath = document.getElementById('file');
                        var fileName = document.getElementById('file-file-name');
                        fileName.value = filePath.value.split('/').pop().split('\\').pop();
                    }
                    
                </script>

                <tiles:put name="id" value=""/>
                <tiles:put name="name">
                    <bean:message bundle="providerBundle" key="replication.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="providerBundle" key="replication.description"/>
                </tiles:put>

                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="providerBundle" key="file.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <input type="hidden" id="file-file-name" name="field(fileName)">
                            <input type="file" id="file" name="file" size="70" class="contactInput" onchange="changefileValue();"/>
                        </tiles:put>
                    </tiles:insert>

                    <fieldset width="100%">
                        <legend>
                            <bean:message key="label.billing" bundle="providerBundle"/>
                        </legend>


                        <div class="chooseAction">
                            <input type="radio" name="type" id="all" checked="true" onclick="showBillings('all');"/>
                            <label for="all">Все</label>
                            <br/><br/>
                            <div class="float">
                                <input type="radio" name="type" id="selected" onclick="showBillings('selected');"/>
                                <label for="selected">Только выбранные</label>&nbsp;
                            </div>
                            <div class="float">
                                <span id="rButton" style="display: none">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.remove"/>
                                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                                        <tiles:put name="bundle"         value="providerBundle"/>
                                        <tiles:put name="onclick"        value="removeTag()"/>
                                        <tiles:put name="typeBtn"        value="enterDiv"/>
                                        <tiles:put name="viewType"       value="buttonGrayNew"/>
                                    </tiles:insert>
                                </span>
                                <span id="aButton">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.add"/>
                                        <tiles:put name="commandHelpKey" value="button.add.help"/>
                                        <tiles:put name="bundle"         value="providerBundle"/>
                                        <tiles:put name="onclick"        value="openDictionary()"/>
                                        <tiles:put name="typeBtn"        value="enterDiv"/>
                                        <tiles:put name="viewType"       value="buttonGrayNew"/>
                                    </tiles:insert>
                                </span>
                            </div>
                        </div>
                        <div class="clear"></div>

                        <table id="billings" cellspacing="0" cellpadding="0" style="display:none">
                            <tr>
                                <td>
                                    <table cellspacing="0" cellpadding="0" class="standartTable">
                                        <tr>
                                            <th width="20px">
                                                <input type="checkbox" id="selectedAll" onclick="switchSelection(this,'selectedIdsForRemove');switchSelection(this,'selectedIds')">
                                            </th>
                                            <th>Идентификатор</th>
                                            <th>Наименование</th>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </fieldset>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Загружать в фоне
                        </tiles:put>
                        <tiles:put name="data">
                            <html:checkbox property="background"/>
                        </tiles:put>
                    </tiles:insert>

                    <script type="text/javascript">
                        showBillings();
                    </script>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.replic"/>
                        <tiles:put name="commandHelpKey" value="button.replic.help"/>
                        <tiles:put name="bundle"         value="providerBundle"/>
                    </tiles:insert>

                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="providerBundle"/>
                        <tiles:put name="action"         value="/private/dictionaries/provider/list.do"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
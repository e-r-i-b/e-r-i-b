<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/bannedaccount/edit" onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="bannedAccountsEdit">
<tiles:put name="submenu" type="string" value="BannedAccount"/>

<tiles:put name="data" type="string">
    <tiles:insert definition="paymentForm" flush="false">
	<tiles:put name="name"><bean:message key="page.edit.name" bundle="bannedaccountsBundle"/></tiles:put>

    <tiles:put name="buttons">
        <tiles:insert definition="clientButton" flush="false">
              <tiles:put name="commandTextKey" value="button.cancel"/>
              <tiles:put name="commandHelpKey" value="button.cancel.help"/>
              <tiles:put name="bundle" value="bannedaccountsBundle"/>
              <tiles:put name="action"  value="/bannedaccount/list.do"/>
        </tiles:insert>
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey"     value="button.save"/>
            <tiles:put name="commandHelpKey" value="button.save.help"/>
            <tiles:put name="isDefault" value="true"/>
            <tiles:put name="bundle" value="bannedaccountsBundle"/>
            <tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="validationFunction">
                function()
                {
                    var accountValue = document.getElementsByName("field(account)")[0].value;
                    if(accountValue == '' || accountValue == null)
                    {
                        alert("Введите значение в поле Счет");
                        return false;
                    }
                    return true;
                }
            </tiles:put>
        </tiles:insert>
    </tiles:put>
	<tiles:put name="data">

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                Счет
            </tiles:put>
            <tiles:put name="data">
                <c:set var="account"><bean:write name="form" property="field(account)"/></c:set>
                <html:text property="field(account)" value="${account}" disabled="false" maxlength="20"/>
                Запретить для
                <html:select property="field(banType)" name="form">
                    <html:option value="ALL"> <bean:message key="label.ALL" bundle="bannedaccountsBundle"/> </html:option>
                    <html:option value="JUR"> <bean:message key="label.JUR" bundle="bannedaccountsBundle"/> </html:option>
                    <html:option value="PHIZ"><bean:message key="label.PHIZ" bundle="bannedaccountsBundle"/></html:option>
                </html:select>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                Добавить БИК
            </tiles:put>
            <tiles:put name="data">
                <div class="float">
                    <input type="text" name="field_BIC" maxlength="9">
                </div>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.add"/>
                    <tiles:put name="commandHelpKey" value="button.add.help"/>
                    <tiles:put name="bundle" value="bannedaccountsBundle"/>
                    <tiles:put name="onclick" value="addBic()"/>
                    <tiles:put name="viewType" value="buttonGrayNew"/>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="data">
                <!-- пишем значение строки с БИКами в скрытое поле-->
                <c:set var="BICListAsString"><bean:write name="form" property="field(BICList)"/></c:set>
                <html:text property="field(BICList)" value="${BICListAsString}" disabled="false" style="display:none"/>
                <table cellspacing="0" cellpadding="0" class="standartTable float" id="bicTable">
                    <tbody>
                    <tr class="tblInfHeader" style="padding: 0px;">
                        <th width="20px" class="titleTable" align="CENTER">
                            <input type="checkbox" id="AllBICs"
                                   onclick="switchSelection(this,'bicSelectedIds4Remove');switchSelection(this,'bic')">
                        </th>
                        <th class="titleTable">
                            &nbsp;БИК&nbsp;
                        </th>
                    </tr>
                    </tbody>
                </table>
                <div id="btnRem">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle" value="bannedaccountsBundle"/>
                        <tiles:put name="onclick" value="removeBic()"/>
                        <tiles:put name="viewType" value="buttonGrayNew"/>
                    </tiles:insert>
                </div>
            </tiles:put>
        </tiles:insert>

        <script type="text/javascript">
            function addBic()
            {
                var bicValue = document.getElementsByName("field_BIC")[0].value;
                if(!validateBicValue(bicValue))
                    return;
                addTableRecord(bicValue, "bic");
                var oldValue = document.getElementsByName("field(BICList)")[0].value;
                if(oldValue!='' && oldValue!=null)
                    document.getElementsByName("field(BICList)")[0].value = oldValue + ',' + bicValue;
                else
                    document.getElementsByName("field(BICList)")[0].value = bicValue;
                var table = ensureElement("bicTable");
                table.style.display = "";
                document.getElementById("btnRem").style.display = "block";
            }

            function validateBicValue(bicValue)
            {
                if(ensureElement("bic"+bicValue)!=null)
                {
                    alert("Такой БИК уже добавлен!");
                    return false;
                }
                var regexp = new RegExp("\\d{9}");
                if(regexp.exec(bicValue)==null)
                {
                    alert("Введите банковский идентификационный код. БИК может состоять только из 9 цифр");
                    return false;
                }
                return true;
            }

            function removeBic()
            {
                var deletedItemsName = 'bicSelectedIds4Remove';
                if (!checkSelection(deletedItemsName, 'Выберите запись для удаления!'))
                    return;

                var table = ensureElement('bicTable');
                var elements = document.getElementsByName(deletedItemsName);
                var newValue = '';
                for (var i = elements.length - 1; i >= 0 ; i--)
                {
                    if (elements[i].checked && table.hasChildNodes())
                    {
                        var elem = ensureElement("trbic" + elements[i].value);
                        table.getElementsByTagName('TBODY')[0].removeChild(elem);
                        if (elements.length == 0)
                        {
                            table.style.display = "none";
                            document.getElementById("btnRem").style.display = "none";
                        }
                    }
                    else
                    {
                        newValue = newValue + elements[i].value + ",";
                    }
                }
                //урезем последнюю запятую
                newValue = newValue.substring(0,newValue.length-1);
                document.getElementsByName("field(BICList)")[0].value = newValue;
            }

            function fillTable()
            {
                var BICs = document.getElementsByName("field(BICList)")[0].value;
                var table = ensureElement("bicTable");
                if(BICs!='' && BICs!=null)
                {
                    document.getElementById("btnRem").style.display = "block";
                    var arrBICs = BICs.split(",");
                    for (var i = 0; i < arrBICs.length; i++)
                    {
                        addTableRecord(arrBICs[i], "bic");
                    }
                }
                else
                {
                    table.style.display = "none";
                    document.getElementById("btnRem").style.display = "none";
                }
            }

            function addTableRecord(resource, prefix)
            {
                var table = ensureElement(prefix + "Table");
                var tbody = table.getElementsByTagName('TBODY')[0];

                var tr = document.createElement("TR");
                tbody.appendChild(tr);
                tr.setAttribute("id",    "tr" + prefix + resource);
                tr.setAttribute("class", "ListLine" + (document.getElementsByName(prefix).length % 2));

                var td1 = document.createElement("TD");
                tr.appendChild(td1);
                td1.setAttribute("align", "center");
                td1.setAttribute("class", "listItem");
                td1.innerHTML = "<input type='checkbox' name='" + prefix + "SelectedIds4Remove' value='"+ resource +"'>";

                var td3 = document.createElement("TD");
                tr.appendChild(td3);
                td3.setAttribute("align", "center");
                td3.setAttribute("class", "listItem");
                td3.innerHTML = "<input type='checkbox' id='" + prefix + resource +  "' value='"+ resource +"' style='display:none;' checked='checked'/>"+resource;
            }
            doOnLoad(function(){fillTable();});
        </script>
        </tiles:put>
    </tiles:insert>

</tiles:put>
</tiles:insert>

</html:form>

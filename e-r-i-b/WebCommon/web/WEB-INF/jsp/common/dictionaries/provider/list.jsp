<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"  %>

<c:set var="standalone" value="${empty param['action']}"/>
<c:set var="getManyProviders" value="${param['action'] == 'getProviders'}"/>
<c:set var="isAdmin" value="${phiz:impliesService('ServiceProvidersDictionaryManagement') || phiz:impliesService('ViewServiceProvidersDictionaryAdminService') || phiz:impliesService('ServiceProvidersDictionaryEmployeeService')}"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:choose>
	<c:when test="${not standalone}">
		<c:set var="layoutDefinition" value="dictionary"/>
	</c:when>
	<c:otherwise>
		<c:set var="layoutDefinition" value="providersMain"/>
	</c:otherwise>
</c:choose>
<html:form action="/private/dictionaries/provider/list" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="${layoutDefinition}">
        <c:if test="${standalone}">
            <tiles:put name="submenu" type="string" value="Providers"/>
            <tiles:put name="needSave" value="false"/>
        </c:if>
        <tiles:put name="pageTitle" type="string"><bean:message bundle="providerBundle" key="listpage.title"/></tiles:put>
        <tiles:put name="menu" type="string">
            <c:if test="${not standalone}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="mailBundle"/>
                    <tiles:put name="image" value=""/>
                    <tiles:put name="onclick" value="window.close()"/>
                </tiles:insert>
            </c:if>
        </tiles:put>

        <tiles:put name="filter" type="string">
            <c:if test="${not standalone}">
                <html:hidden property="filter(kind)"/>
            </c:if>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.region"/>
                <tiles:put name="bundle" value="providerBundle"/>
                <tiles:put name="data">
                    <nobr>
                        <html:text  property="filter(regionName)" readonly="true" style="width:200px"/>
                        <html:hidden property="filter(regionId)"/>
                        <input type="button" class="buttWhite smButt" onclick="openRegionsDictionary(setRegionInfo, 'filter');" value="..."/>
                        <script type="text/javascript">
                           function setRegionInfo(result)
                           {
                               setElementByContainName("regionId",   result['id']);
                               setElementByContainName("regionName", result['name']);
                           }
                       </script>
                    </nobr>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.paymentService"/>
                <tiles:put name="bundle" value="providerBundle"/>
                <tiles:put name="data">
                    <nobr>
                        <html:text  property="filter(paymentServiceName)" readonly="true" style="width:200px"/>
                        <html:hidden property="filter(paymentServiceId)"/>
                        <input type="button" class="buttWhite smButt" onclick="openPaymentServicesDictionary(setPaymentServiceInfo_);" value="..."/>
                        <script type="text/javascript">
                           function setPaymentServiceInfo_(result)
                           {
                               setElementByContainName("paymentServiceId",   result['id']);
                               setElementByContainName("paymentServiceName", result['name']);
                           }
                       </script>
                    </nobr>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="filter.name"/>
                <tiles:put name="bundle" value="providerBundle"/>
                <tiles:put name="name" value="name"/>
                <tiles:put name="maxlength" value="160"/>
                <tiles:put name="size" value="46"/>
            </tiles:insert>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="filter.account"/>
                <tiles:put name="bundle" value="providerBundle"/>
                <tiles:put name="name" value="account"/>
                <tiles:put name="maxlength" value="25"/>
                <tiles:put name="size" value="46"/>
            </tiles:insert>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="filter.INN"/>
                <tiles:put name="bundle" value="providerBundle"/>
                <tiles:put name="name" value="INN"/>
                <tiles:put name="maxlength" value="21"/>
                <tiles:put name="size" value="14"/>
            </tiles:insert>
            <c:if test="${standalone}">
                <tiles:insert definition="filterEntryField" flush="false">
                    <tiles:put name="label" value="filter.department"/>
                    <tiles:put name="bundle" value="providerBundle"/>
                    <tiles:put name="data">
                        <nobr>
                            <html:text  property="filter(departmentName)" readonly="true" style="width:200px"/>
                            <html:hidden property="filter(departmentId)"/>
                            <input type="button" class="buttWhite smButt"onclick="openDepartmentsDictionary(setDepartmentInfo);" value="..."/>
                        </nobr>
                    </tiles:put>
                </tiles:insert>
                <tiles:insert definition="filterEntryField" flush="false">
                    <tiles:put name="label" value="filter.billing"/>
                    <tiles:put name="bundle" value="providerBundle"/>
                    <tiles:put name="data">
                        <nobr>
                            <html:text  property="filter(billingName)" readonly="true" style="width:200px"/>
                            <html:hidden property="filter(billingId)"/>
                            <input type="button" class="buttWhite smButt" onclick="openBillingsDictionary(setBillingInfo);" value="..."/>
                        </nobr>
                    </tiles:put>
                </tiles:insert>
                <tiles:insert definition="filterEntryField" flush="false">
                    <tiles:put name="label" value="filter.state"/>
                    <tiles:put name="bundle" value="providerBundle"/>
                    <tiles:put name="data">
                        <html:select property="filter(state)" styleClass="select">
                            <html:option value="">Все</html:option>
                            <html:option value="ACTIVE">Активный</html:option>
                            <html:option value="NOT_ACTIVE">Обслуживание приостановлено</html:option>
                            <html:option value="ARCHIVE">Помещен в архив</html:option>
                            <html:option value="MIGRATION">Заблокирован для новых платежей</html:option>
                        </html:select>
                    </tiles:put>
                </tiles:insert>
            </c:if>
        </tiles:put>

        <c:if test="${standalone}">
            <tiles:put name="menu" type="string">
                <tiles:insert definition="clientButton" operation="EditServiceProvidersOperation" flush="false">
                    <tiles:put name="commandTextKey" value="button.add"/>
                    <tiles:put name="commandHelpKey" value="button.add.help"/>
                    <tiles:put name="bundle"         value="providerBundle"/>
                    <tiles:put name="action"         value="/dictionaries/provider/overview.do"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" operation="UnloadingServiceProvidersOperation" flush="false">
                    <tiles:put name="commandTextKey" value="button.unload"/>
                    <tiles:put name="commandHelpKey" value="button.unload"/>
                    <tiles:put name="bundle"         value="providerBundle"/>
                    <tiles:put name="action"         value="/dictionaries/provider/unloading.do"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" operation="ReplicateServiceProvidersOperation" service="*" flush="false">
                    <tiles:put name="commandTextKey" value="button.list.replic"/>
                    <tiles:put name="commandHelpKey" value="button.list.replic.help"/>
                    <tiles:put name="bundle"         value="providerBundle"/>
                    <tiles:put name="action"         value="/dictionaries/provider/replication.do"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </tiles:put>
        </c:if>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                    function sendProviderData(event)
                    {
                        var manySelection = <c:out value="${getManyProviders}"/>;

                        checkIfOneItem("selectedIds");
                        if(manySelection)
                        {
                            if (!checkSelection("selectedIds", 'Укажите записи'))
                                return;
                        }
                        else
                        {
                            if (!checkSelection("selectedIds", 'Укажите одну запись') || (!checkOneSelection("selectedIds", 'Укажите только одну запись')))
                                return;
                        }
                        var ids = document.getElementsByName("selectedIds");
                        var res = new Array();
                        for (var i = 0; i < ids.length; i++)
                        {
                            if (ids.item(i).checked)
                            {
                                var r = ids.item(i).parentNode.parentNode;
                                var a = new Array();
                                a['id']                     = ids.item(i).value;
                                a['name']                   = trim(getElementTextContent(r.cells[1]));
                                a['INN']                    = trim(getElementTextContent(r.cells[2]));
                                a['account']                = trim(getElementTextContent(r.cells[3]));
                                a['serviceId']              = trim(getElementTextContent(r.cells[4]));
                                a['codeService']            = trim(getElementTextContent(r.cells[5]));
                                a['status']                 = trim(getElementTextContent(r.cells[7]));
                                a['bankName']               = trim(getElementTextContent(r.cells[8]));
                                a['bankCode']               = trim(getElementTextContent(r.cells[9]));
                                a['correspondentAccount']   = trim(getElementTextContent(r.cells[10]));
                                a['externalId']             = trim(getElementTextContent(r.cells[11]));
                                a['code']                   = trim(getElementTextContent(r.cells[12]));
                                <c:if test="${not standalone}">
                                    a['imageUrl']           = trim(getElementTextContent(r.cells[14]));
                                    a['imageId']            = trim(getElementTextContent(r.cells[15]));
                                    a['alias']              = trim(getElementTextContent(r.cells[16]));
                                </c:if>
                                if (!manySelection)
                                {
                                    var message = window.opener.setProviderInfo(a);
                                    if(message != null)
                                    {
                                        alert(message);
                                        return;
                                    }
                                    window.close();
                                    return;
                                }
                                res.push(a);
                            }
                        }
                        var message = window.opener.setProviderInfo(res);
                        if(message != null)
                        {
                            alert(message);
                            return;
                        }
                        window.close();
                        return;
                    }
            </script>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="ProvidersListTable"/>
                <tiles:put name="image" value=""/>
                <tiles:put name="buttons">
                    <c:if test="${standalone}">
                       <script type="text/javascript">
                            function doEdit()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkOneSelection("selectedIds", "Выберите поставщика услуг!"))
                                    return;

                                var url = "${phiz:calculateActionURL(pageContext,'/dictionaries/provider/overview')}";
                                var id = getRadioValue(document.getElementsByName("selectedIds"));
                                window.location = url + "?id=" + id;
                            }

                            function doPrint()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkOneSelection("selectedIds", "Выберите поставщика услуг!"))
                                    return;

                                var id = getRadioValue(document.getElementsByName("selectedIds"));
                                doPrintProvider(id);
                            }

                            // получаем строку, отображаемую в случае неактивного поставщика
                            function getNotActiveState()
                            {
                                return '<bean:message key="label.provider.state.NOT_ACTIVE" bundle="providerBundle"/>';
                            }

                            function validateLock()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkSelection("selectedIds", "Для выполнения действия необходимо указать одну или несколько записей из списка"))
                                    return false;

                                var ids = document.getElementsByName("selectedIds");
                                var names = new Array();
                                for (var i = 0; i < ids.length; i++)
                                {
                                    if(ids.item(i).checked)
                                    {
                                        var r = ids.item(i).parentNode.parentNode;
                                        var isInternetShop = trim(getElementTextContent(r.cells[12]));
                                        var name = trim(getElementTextContent(r.cells[1]));
                                        if (isInternetShop.toLowerCase() == 'true')
                                        {
                                            names.push(name);
                                        }
                                    }
                                }
                                if (names.length != 0)
                                {
                                    var str ='';
                                    for (var i = 0; i < names.length - 1; i++)
                                    {
                                        str = str + names[i] + ", ";
                                    }
                                    str = str + names[names.length-1];
                                    return confirm("Данная операция приведет к отключению функционала, связанного с " + str);
                                }
                                return true;
                            }

                            function validateRemove()
                            {
                                if (!checkSelection('selectedIds', 'Укажите хотя бы одну запись'))
                                    return false;
                                return confirm("Вы действительно хотите удалить " + getSelectedQnt('selectedIds') + " записей поставщиков из справочника?");
                            }
                       </script>
                       <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/providers.js"></script>

                        <c:if test="${isAdmin}">
                            <tiles:insert definition="commandButton" flush="false" operation="ActivateOrLockManyServiceProviderOperation">
                                <tiles:put name="commandKey"        value="button.activate"/>
                                <tiles:put name="commandHelpKey"    value="button.activate.help"/>
                                <tiles:put name="bundle"            value="providerBundle"/>
                                <tiles:put name="validationFunction">
                                    function()
                                    {
                                        checkIfOneItem("selectedIds");
                                        return checkSelection("selectedIds", "Для выполнения действия необходимо указать одну или несколько записей из списка");
                                    }
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="commandButton" flush="false" operation="ActivateOrLockManyServiceProviderOperation">
                                <tiles:put name="commandKey"        value="button.lock"/>
                                <tiles:put name="commandHelpKey"    value="button.lock.help"/>
                                <tiles:put name="bundle"            value="providerBundle"/>
                                <tiles:put name="validationFunction">validateLock();</tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="commandButton" flush="false" operation="MigrationServiceProviderOperation">
                                <tiles:put name="commandKey"        value="button.migration"/>
                                <tiles:put name="commandHelpKey"    value="button.migration.help"/>
                                <tiles:put name="bundle"            value="providerBundle"/>
                                <tiles:put name="validationFunction">
                                    function()
                                    {
                                        checkIfOneItem("selectedIds");
                                        return checkOneSelection("selectedIds", "Для выполнения действия необходимо указать только одну запись из списка.");
                                    }
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey"    value="button.print"/>
                                <tiles:put name="commandHelpKey"    value="button.print.help"/>
                                <tiles:put name="bundle"            value="providerBundle"/>
                                <tiles:put name="onclick"           value="doPrint()"/>
                            </tiles:insert>

                            <tiles:insert definition="clientButton" flush="false" operation="EditServiceProvidersOperation">
                                <tiles:put name="commandTextKey"    value="button.edit"/>
                                <tiles:put name="commandHelpKey"    value="button.edit.help"/>
                                <tiles:put name="bundle"            value="providerBundle"/>
                                <tiles:put name="onclick"           value="doEdit()"/>
                            </tiles:insert>
                        </c:if>

                        <tiles:insert definition="commandButton" operation="RemoveServiceProvidersOperation" flush="false">
                            <tiles:put name="commandKey"            value="button.remove"/>
                            <tiles:put name="commandHelpKey"        value="button.remove.help"/>
                            <tiles:put name="bundle"                value="providerBundle"/>
                            <tiles:put name="validationFunction">validateRemove();</tiles:put>
                        </tiles:insert>
                    </c:if>

                    <c:if test="${not standalone}">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.send"/>
                            <tiles:put name="commandHelpKey" value="button.send"/>
                            <tiles:put name="bundle"         value="providerBundle"/>
                            <tiles:put name="onclick"        value="sendProviderData(event);"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" >
                        <c:set var="isBillingProvider" value="${listElement.type == 'BILLING'}"/>

                        <sl:collectionParam id="selectType" value="${not(standalone || getManyProviders)?'radio':'checkbox'}"/>

                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>
                        <sl:collectionParam id="onRowClick"     value="selectRow(this, 'selectedIds');" condition="${not(standalone || getManyProviders)}"/>
                        <sl:collectionParam id="onRowDblClick"  value="sendProviderData(event)"         condition="${not(standalone || getManyProviders)}"/>

                        <sl:collectionItem title="Наименование" property="name">
                            <sl:collectionItemParam id="action" value="/dictionaries/provider/overview?id=${listElement.id}" condition="${isAdmin && standalone}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="ИНН"          property="INN"/>
                        <sl:collectionItem title="Счет"         property="account"/>
                        <sl:collectionItem title="Услуга">
                            &nbsp;
                            <c:if test="${isBillingProvider}">
                                <c:out value="${listElement.nameService}"/>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem hidden="true">
                            &nbsp;
                            <c:if test="${isBillingProvider}">
                                <c:out value="${listElement.codeService}"/>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem styleClass="listItem align-center">
                            <sl:collectionItemParam id="title"><img src="${imagePath}/clip.gif" title="Прикреплена графическая подсказка"/></sl:collectionItemParam>
                            <sl:collectionItemParam id="value">
                                <c:if test="${not empty listElement.imageHelpId}">
                                    <img src="${imagePath}/clip.gif" title="Прикреплена графическая подсказка"/>
                                </c:if>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="Статус">
                            <c:set var="state" value="${listElement.state}"/>
                            <c:if test="${not empty state}">
                                <div id="active_${listElement.id}" >
                                    <bean:message key="label.provider.state.${state}" bundle="providerBundle"/>
                                </div>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem hidden="true"        property="bankName"/>
                        <sl:collectionItem hidden="true"        property="BIC"/>
                        <sl:collectionItem hidden="true"        property="corrAccount"/>
                        <sl:collectionItem hidden="true"        property="synchKey"/>
                        <sl:collectionItem hidden="true"        property="code"/>
                        <sl:collectionItem hidden="true" value="${phiz:isInstance(listElement, 'com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider')}"/>
                        <c:if test="${not standalone}">
                            <sl:collectionItem hidden="true">
                                <c:set var="imageId" value="${listElement.imageId}"/>
                                <c:choose>
                                    <c:when test="${not empty imageId}">
                                        <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                                        ${phiz:getAddressImage(imageData, pageContext)}
                                    </c:when>
                                    <c:otherwise>
                                        ${globalUrl}/images/logotips/IQWave/IQWave-other.jpg
                                    </c:otherwise>
                                </c:choose>
                            </sl:collectionItem>
                            <sl:collectionItem hidden="true" property="imageId"/>
                            <sl:collectionItem hidden="true" property="alias"/>
                        </c:if>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty state}"/>
                <tiles:put name="emptyMessage">
                    <bean:message bundle="providerBundle" key="empty.message"/>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

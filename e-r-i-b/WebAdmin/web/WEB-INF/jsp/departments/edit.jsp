<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/departments/edit" onsubmit="return setEmptyAction(event);">
<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
<c:set var="department" value="${frm.department}"/>
<c:set var="parentDepartment" value="${frm.parentDepartment}"/>
<c:set var="canEdit" value="${phiz:impliesOperation('EditDepartmentOperation', 'DepartmentsManagement')}"/>
<c:set var="creditCardOfficeEditEnable" value="${phiz:isCreditCardOfficeEditDisable(department)}"/>
<c:set var="isVSPOffice" value="${phiz:isVSPOffice(department)}"/>

<tiles:insert definition="departmentsEdit">
<tiles:put name="submenu" type="string" value="Edit"/>

<tiles:put name="menu" type="string">

    <tiles:insert definition="commandButton" flush="false" operation="EditDepartmentOperation">
        <tiles:put name="commandKey" value="button.save"/>
        <tiles:put name="commandHelpKey" value="button.save.department.help"/>
        <tiles:put name="bundle" value="departmentsBundle"/>
        <tiles:put name="isDefault" value="true"/>
        <tiles:put name="postbackNavigation" value="true"/>
        <tiles:put name="viewType" value="blueBorder"/>
    </tiles:insert>

    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.cancel"/>
        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
        <tiles:put name="bundle" value="departmentsBundle"/>
        <tiles:put name="action" value="/departments/list.do"/>
        <tiles:put name="viewType" value="blueBorder"/>
    </tiles:insert>

</tiles:put>

<tiles:put name="data" type="string">

<script type="text/javascript">

    function openSelectExternalSystemWindow()
    {
        var win = window.open('../dictionaries/routing/adapter/list.do?action=getExternalSystemInfo',
                'ExternalSystem', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=" + (getClientWidth() - 100) + "px,height=" + (getClientHeight() - 100) + "px");
        win.moveTo(50, 50);
    }

    function openSelectOfficeWindow()
    {
        var externalSystemUUID = getElementValue("field(externalSystemUUID)");
        if (externalSystemUUID.length == 0)
        {
            alert("Выберите внешнюю систему");
            return;
        }
        var params = "";
        params = addParam2List(params, "field(externalSystemUUID)", "externalSystemUUID");
        params = addParam2List(params, "field(branch)", "fltrBranch");
        params = addParam2List(params, "field(office)", "fltrOffice");
        if (params.length > 0) params = "&" + params;
        window.open('../private/dictionary/offices.do?action=getOfficesInfo'
                + params, 'Offices', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
    }
    function setOfficeInfo(officeInfo)
    {
        setField('officeId', officeInfo["officeId"]);
        setField('region', officeInfo["regionBank"]);
        setField('officeName', officeInfo["name"]);
        setField('branch', officeInfo["branch"]);
        setField('office', officeInfo["office"]);
        setField('address', officeInfo["location"]);
        setField('location', officeInfo["location"]);
        setField('telephone', officeInfo["phone"]);
    }

    <%-- изменить форму в соответствии с доступностью интерфейса получения офисов --%>
    function setExternalSystemOffice(flag)
    {
        setFieldReadOnlyState("region", flag);
        setFieldReadOnlyState("branch", flag);
        setFieldReadOnlyState("office", flag);
        setFieldReadOnlyState("officeName", flag);

        if (flag)
        {
            $("#officeSelectButton").show();
        }
        else
        {
            $("#officeSelectButton").hide();
        }
    }

    function setAdapterInfo(externalSystem)
    {
        var externalSystemUUID = getElementValue("field(externalSystemUUID)");

        setField('externalSystemName', externalSystem["name"]);
        setField('externalSystemUUID', externalSystem["UUID"]);
        setExternalSystemOffice(externalSystem["extOffices"] == "true");

        if (externalSystemUUID != externalSystem["UUID"] && !isOfficeEmpty())
        {
            alert("В связи с изменением внешней системы, необходимо изменить офис.");
            //обнуляем настройки офиса
            setField('officeId', "");
            setField('region', "");
            setField('officeName', "");
            setField('branch', "");
            setField('office', "");
            setField('address', "");
            setField('location', "");
            setField('telephone', "");
        }
    }

    function isOfficeEmpty()
    {
        var officeId = getElementValue("field(officeId)");
        var region = getElementValue("field(region)");
        var branch = getElementValue("field(branch)");
        var office = getElementValue("field(office)");
        var name = getElementValue("field(officeName)");

        return officeId == "" && region == "" && branch == "" && office == "" && name == "";
    }

    function doService()
    {
        var service = getField("service").checked;
        document.getElementById("externalSystemSelectButton").disabled = (!service || ${!canEdit});
        document.getElementById("officeSelectButton").disabled = !service;
        if (!service && ${phiz:isService(frm.department) && phiz:hasClients(frm.department)})
        {
            var result = confirm("В подразделении есть активные клиенты. Отключить?");
            if (!result)
            {
                getField("service").checked = true;
            }
        }
    }

    <c:if test="${empty parentDepartment}">
    function setBillingInfo(billingInfo)
    {
        setElement('field(billingName)', billingInfo["name"]);
        setElement('field(billingId)', billingInfo["id"]);
    }

    function clearBilling()
    {
        setElement('field(billingName)', '');
        setElement('field(billingId)', '');
    }
    </c:if>
</script>

<html:hidden property="mode"/>
<html:hidden property="field(officeId)"/>

<tiles:insert definition="paymentForm" flush="false">
<tiles:put name="id" value=""/>
<tiles:put name="name" value="Подразделения банка"/>
<tiles:put name="description" value="Используйте данную форму для редактирования данных подразделения банка."/>
<tiles:put name="data">
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Наименование
    </tiles:put>
    <tiles:put name="isNecessary" value="true"/>
    <tiles:put name="data">
        <html:text property="field(name)" size="60" disabled="${!canEdit}"/>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        <bean:message bundle="commonBundle" key="text.SBOL.connected"/>
    </tiles:put>
    <tiles:put name="isNecessary" value="true"/>
    <tiles:put name="needMargin" value="true"/>
    <tiles:put name="data">
        <html:checkbox accesskey="onLineCheckBox" property="field(service)" value="true" disabled="${!canEdit}" onclick="doService();"/>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Поддерживается "Базовый продукт"
    </tiles:put>
    <tiles:put name="isNecessary" value="true"/>
    <tiles:put name="needMargin" value="true"/>
    <tiles:put name="data">
        <html:checkbox name="frm" property="field(esbSupported)" value="true"/>
    </tiles:put>
</tiles:insert>
<c:if test="${(frm.parentId == null || frm.parentId == 0) && frm.parentDepartment == null}">
    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message key="label.sign.automation" bundle="departmentsBundle"/>
        </tiles:put>
        <tiles:put name="isNecessary" value="true"/>
        <tiles:put name="data">
            <html:radio property="fields(automation)" value="centralized" disabled="${!canEdit}"/>
            Централизованное
        </tiles:put>
    </tiles:insert>
    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="data">
            <html:radio property="fields(automation)" value="decentralized" disabled="${!canEdit}"/>
            Децентрализованное
        </tiles:put>
    </tiles:insert>
</c:if>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Головное подразделение
    </tiles:put>
    <tiles:put name="data">
        <html:checkbox property="field(mainDepartment)" disabled="${!canEdit}" value="true"/>
    </tiles:put>
</tiles:insert>
<c:if test="${phiz:impliesOperation('ListBillingsOperation', '*') && empty parentDepartment}">
    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            Биллинг
        </tiles:put>
        <tiles:put name="data">
            <html:text property="field(billingName)" readonly="true" size="60"/>
            <c:choose>
                <c:when test="${canEdit}">
                    <input id="billingSelectButton" type="button" class="buttWhite smButt" onclick="openBillingsDictionary(setBillingInfo);" value="..."/>
                    <input id="billingClearButton" type="button" class="buttWhite" onclick="clearBilling();" value="Очистить"/>
                </c:when>
                <c:otherwise>
                    <input id="billingSelectButton" type="button" class="buttWhite smButt" disabled="disabled" value="..."/>
                    <input id="billingClearButton" type="button" class="buttWhite" disabled="disabled" value="Очистить"/>
                </c:otherwise>
            </c:choose>
            <html:hidden property="field(billingId)"/>
        </tiles:put>
    </tiles:insert>
</c:if>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Внешняя система
    </tiles:put>
    <tiles:put name="isNecessary" value="true"/>
    <tiles:put name="data">
        <html:text property="field(externalSystemName)" readonly="true" size="60" disabled="${!canEdit}"/>
        <input id="externalSystemSelectButton" type="button" class="buttWhite smButt" onclick="openSelectExternalSystemWindow();" value="..."/>
        <html:hidden property="field(externalSystemUUID)"/>
        <html:hidden property="field(externalSystemOffice)"/>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Наименование
    </tiles:put>
    <tiles:put name="isNecessary" value="true"/>
    <tiles:put name="data">
        <html:text property="field(officeName)" readonly="true" size="60" disabled="${!canEdit}"/>
        <input id="officeSelectButton" type="button" class="buttWhite smButt" onclick="openSelectOfficeWindow();"
               <c:if test="${!canEdit}">disabled="disabled"</c:if>
               value="..."/>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        ТБ
    </tiles:put>
    <tiles:put name="isNecessary" value="true"/>
    <tiles:put name="data">
        <html:text property="field(region)" readonly="true" size="5" disabled="${!canEdit}"/>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Номер отделения
    </tiles:put>
    <tiles:put name="data">
        <html:text property="field(branch)" readonly="true" size="7" disabled="${!canEdit}"/>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Номер филиала
    </tiles:put>
    <tiles:put name="data">
        <html:text property="field(office)" readonly="true" size="7" disabled="${!canEdit}"/>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Город
    </tiles:put>
    <tiles:put name="data">
        <html:text property="field(city)" size="60" disabled="${!canEdit}"/>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Почтовый адрес
    </tiles:put>
    <tiles:put name="data">
        <html:text property="field(address)" size="60" disabled="${!canEdit}"/>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Местонахождение
    </tiles:put>
    <tiles:put name="data">
        <html:text property="field(location)" size="60" disabled="${!canEdit}"/>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Телефон
    </tiles:put>
    <tiles:put name="data">
        <html:text property="field(telephone)" size="15" maxlength="15" disabled="${!canEdit}"/>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Часовой пояс
    </tiles:put>
    <tiles:put name="data">
        <html:select name="frm" property="field(timeZone)" disabled="${!canEdit}">
            <c:forEach var="i" begin="0" end="10">
                <html:option value="${i}" bundle="departmentsBundle" key="department.timezone.${i}"/>
            </c:forEach>
        </html:select>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Операционное время в рабочие дни
    </tiles:put>
    <tiles:put name="isNecessary" value="true"/>
    <tiles:put name="needMargin" value="true"/>
    <tiles:put name="data">
        c <html:text property="field(weekOperationTimeBegin)" size="5" disabled="${!canEdit}"/> до
        <html:text property="field(weekOperationTimeEnd)" size="5" disabled="${!canEdit}"/>
    </tiles:put>
</tiles:insert>
<div style="display:none;">
    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            Операционное время в предвыходные и предпраздничные дни
        </tiles:put>
        <tiles:put name="isNecessary" value="true"/>
        <tiles:put name="needMargin" value="true"/>
        <tiles:put name="data">
            с <html:text property="field(fridayOperationTimeBegin)" size="5" disabled="${!canEdit}"/> до
            <html:text property="field(fridayOperationTimeEnd)" size="5" disabled="${!canEdit}"/>
        </tiles:put>
    </tiles:insert>
</div>
<div style="display: none;">
    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            Шкала времени
        </tiles:put>
        <tiles:put name="data">
            <html:text property="field(timeScale)" size="30" maxlength="30"/>
        </tiles:put>
    </tiles:insert>
</div>
<div style="display: none;">
    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            Уведомлять о расторжении договора за
        </tiles:put>
        <tiles:put name="needMargin" value="true"/>
        <tiles:put name="data">
            <html:text property="field(notifyContractCancelation)" size="4"/>&nbsp;дней
        </tiles:put>
    </tiles:insert>
</div>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Тариф за организацию расчетного обслуживания
    </tiles:put>
    <tiles:put name="isNecessary" value="true"/>
    <tiles:put name="needMargin" value="true"/>
    <tiles:put name="data">
        <html:text property="field(connectionCharge)" size="12" maxlength="11" styleClass="moneyField" disabled="${!canEdit}"/> рублей
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Ежемесячная плата
    </tiles:put>
    <tiles:put name="isNecessary" value="true"/>
    <tiles:put name="data">
        <html:text property="field(monthlyCharge)" size="12" maxlength="11" styleClass="moneyField" disabled="${!canEdit}"/> рублей
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="data">
        <html:checkbox property="field(creditCardOffice)" disabled="${creditCardOfficeEditEnable}"/>&nbsp;подразделение выдаёт кредитные карты
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="data">
        <html:checkbox property="field(openIMAOffice)" disabled="true"/>&nbsp;подразделение выдаёт ОМС
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="data">
        <html:checkbox property="field(possibleLoansOperation)" disabled="${!canEdit}"/>&nbsp;Возможны операции по кредитованию физ. лиц
    </tiles:put>
</tiles:insert>
<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="data">
        <html:checkbox property="field(notActive)" disabled="true"/>&nbsp;Неактивное
    </tiles:put>
</tiles:insert>

</tiles:put>
<tiles:put name="buttons">
    <tiles:insert definition="commandButton" flush="false" operation="EditDepartmentOperation">
        <tiles:put name="commandKey" value="button.save"/>
        <tiles:put name="commandHelpKey" value="button.save.department.help"/>
        <tiles:put name="bundle" value="departmentsBundle"/>
        <tiles:put name="isDefault" value="true"/>
        <tiles:put name="postbackNavigation" value="true"/>
    </tiles:insert>

    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.cancel"/>
        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
        <tiles:put name="bundle" value="departmentsBundle"/>
        <tiles:put name="action" value="/departments/list.do"/>
    </tiles:insert>
</tiles:put>
</tiles:insert>
<script type="text/javascript" language="javascript">
    <c:if test="${not empty department.adapterUUID || not empty frm.fields['externalSystemUUID']}">
    setExternalSystemOffice(${frm.fields['externalSystemOffice']});
    </c:if>
    doService();
</script>

</tiles:put>


</tiles:insert>

</html:form>
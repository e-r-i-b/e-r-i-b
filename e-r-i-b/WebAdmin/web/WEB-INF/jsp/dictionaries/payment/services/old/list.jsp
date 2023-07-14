<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/oldPaymentService/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="paymentServicesMain">
        <tiles:put name="submenu" type="string" value="PaymentServicesOld"/>

        <tiles:put name="pageTitle" type="string"><bean:message bundle="paymentServiceBundle" key="listpage.title"/></tiles:put>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="commandButton" flush="false" service="OldPaymentServicesManagement">
                <tiles:put name="commandKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add.help"/>
                <tiles:put name="bundle" value="paymentServiceBundle"/>
                <tiles:put name="validationFunction" value="doAdd();"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                var addUrl = "${phiz:calculateActionURL(pageContext,'/dictionaries/oldPaymentService/edit')}";
                function doEdit()
                {
                    checkIfOneItem("selectedIds");
                    if (!checkSelection("selectedIds", 'Укажите одну запись') || (!checkOneSelection("selectedIds", 'Укажите только одну запись')))
                        return;
                    var id = getRadioValue(document.getElementsByName("selectedIds"));
                    window.location = addUrl + "?id=" + encodeURIComponent(id);
                }

                function doAdd()
                {
                    if (!checkOneSelectionOrNothing('selectedIds', 'Выберите не более одной услуги'))
                        return false;
                    var id = getRadioValue(document.getElementsByName("selectedIds"));
                    if (!document.getElementById("child" + id))
                    {
                        return true;
                    }
                    else
                    {
                        alert("Достигнут максимально возможный уровень иерархии - 2.");
                    }
                    return false;
                }
                function doRemove()
                {
                    checkIfOneItem("selectedIds");
                    if (checkSelection('selectedIds', 'Укажите хотя бы одну запись'))
                    {
                        var id = getRadioValue(document.getElementsByName("selectedIds"));
                        if (!document.getElementById("parent" + id))
                        {
                            return confirm("Вы действительно хотите удалить " + getSelectedQnt('selectedIds') + " записей услуг из справочника?");
                        }
                        else
                        {
                            alert("Укажите запись без подчиненных элементов.");
                        }
                    }
                    return false;
                }
            </script>
            <c:set var="paymentServices" value="${form.data}"/>

           <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="PaymentServiceList"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" service="OldPaymentServicesManagement">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                        <tiles:put name="bundle" value="paymentServiceBundle"/>
                        <tiles:put name="onclick" value="doEdit();"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false" service="OldPaymentServicesManagement">
                        <tiles:put name="commandKey" value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle" value="paymentServiceBundle"/>
                        <tiles:put name="validationFunction">
                            doRemove()
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="data">
                    <%@ include file="tree.jsp"%>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty paymentServices}"/>
                <tiles:put name="emptyMessage" type="string"><bean:message bundle="paymentServiceBundle" key="empty.message"/></tiles:put>
            </tiles:insert>

        </tiles:put>
    </tiles:insert>

</html:form>

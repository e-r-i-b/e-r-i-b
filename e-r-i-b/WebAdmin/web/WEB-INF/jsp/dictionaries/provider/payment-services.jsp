<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/provider/payment/services" onsubmit="return setEmptyAction(event);" enctype="multipart/form-data">
    <tiles:insert definition="recipientDictionariesEdit">
        <tiles:put name="needSave" type="string" value="false"/>
        <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="isEditable" value="${frm.editable}"/>

        <tiles:put name="submenu" value="Provider/PaymentServices"/>

        <tiles:put name="menu" type="string">
            <c:if test="${isEditable}">
                <c:if test='${frm.fields.state eq "NOT_ACTIVE" || frm.fields.state eq "MIGRATION"}'>
                    <tiles:insert definition="commandButton" flush="false" operation="ActivateOrLockServiceProviderOperation">
                        <tiles:put name="commandKey" value="button.activate"/>
                        <tiles:put name="commandHelpKey" value="button.activate.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:if>
                <c:if test='${frm.fields.state eq "ACTIVE"}'>
                    <tiles:insert definition="commandButton" flush="false" operation="ActivateOrLockServiceProviderOperation">
                        <tiles:put name="commandKey" value="button.lock"/>
                        <tiles:put name="commandHelpKey" value="button.lock.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="validationFunction">checkProviderType('${frm.internetShop}');</tiles:put>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:if>
                <tiles:insert definition="clientButton" flush="false" operation="SetupBillingProviderServicesOperation">
                    <tiles:put name="commandTextKey" value="button.add"/>
                    <tiles:put name="commandHelpKey" value="button.add.help"/>
                    <tiles:put name="bundle"         value="providerBundle"/>
                    <tiles:put name="onclick"        value="openPaymentServicesDictionary(setPaymentServiceInfo)"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:if>

            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"         value="providerBundle"/>
                <tiles:put name="action"         value="/private/dictionaries/provider/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <html:hidden property="id" name="frm"/>
                <html:hidden property="newIds" styleId="newIds" name="frm"/>
                <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/providers.js"></script>
                <script type="text/javascript">

                    function setPaymentServiceInfo(resource)
                    {
                        document.getElementById("newIds").value = resource["id"];
                        var button = new CommandButton('button.save', '');
                        button.click();
                    }
                </script>

                <tiles:put name="description">
                    <bean:message bundle="providerBundle" key="label.payment.services"/>
                </tiles:put>

                <c:if test="${isEditable}">
                    <tiles:put name="buttons">
                        <tiles:insert definition="commandButton" flush="false" operation="SetupBillingProviderServicesOperation">
                            <tiles:put name="commandKey" value="button.remove"/>
                            <tiles:put name="commandHelpKey" value="button.remove.help"/>
                            <tiles:put name="bundle"         value="providerBundle"/>
                            <tiles:put name="validationFunction">
                                function()
                                {
                                    checkIfOneItem("selectedIds");
                                    return checkSelection('selectedIds', 'Выберите запись для удаления.');
                                }
                            </tiles:put>
                        </tiles:insert>
                    </tiles:put>
                </c:if>

                <tiles:put name="data">
                    <tiles:put name="grid">
                        <sl:collection id="item" model="list" property="billingProviderServices" bundle="providerBundle">
                            <sl:collectionParam id="selectType" value="checkbox"/>
                            <sl:collectionParam id="selectName" value="selectedIds"/>
                            <c:set var="service" value="${item.paymentService}"/>
                            <sl:collectionParam id="selectProperty" value="paymentService.id"/>

                            <sl:collectionItem title="Наименование услуг" value="${service.name}"/>
                            <sl:collectionItem title="Место в иерархии">
                                <c:forEach var="parent" items="${service.parentServices}">
                                    ${parent.name}; 
                                </c:forEach>
                            </sl:collectionItem>
                        </sl:collection>
                    </tiles:put>
                </tiles:put>
                <tiles:put name="emptyMessage" value="Не найдено ни одной услуги, соответствующей заданному фильтру!"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

</html:form>
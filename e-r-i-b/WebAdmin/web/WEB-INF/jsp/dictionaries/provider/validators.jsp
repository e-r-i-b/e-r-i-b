<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/dictionaries/provider/fields/validators/list" onsubmit="return setEmptyAction(event);">

    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="isEditable" value="${frm.editable}"/>
    <tiles:insert definition="recipientDictionariesEdit">
        <tiles:put name="submenu" value="Provider/Fields/Validators" type="string"/>
        <tiles:put name="needSave" value="false"/>
        <tiles:put name="pageTitle" type="string"><bean:message bundle="providerBundle" key="label.validators"/></tiles:put>
        <tiles:put name="menu" type="string">
            <c:if test="${isEditable}">
                <c:if test='${frm.providerState eq "NOT_ACTIVE" || frm.providerState eq "MIGRATION"}'>
                    <tiles:insert definition="commandButton" flush="false" operation="ActivateOrLockServiceProviderOperation">
                        <tiles:put name="commandKey" value="button.activate"/>
                        <tiles:put name="commandHelpKey" value="button.activate.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${frm.providerState eq 'ACTIVE'}">
                    <tiles:insert definition="commandButton" flush="false" operation="ActivateOrLockServiceProviderOperation">
                        <tiles:put name="commandKey" value="button.lock"/>
                        <tiles:put name="commandHelpKey" value="button.lock.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="validationFunction">checkProviderType('${frm.internetShop}');</tiles:put>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:if>
                <tiles:insert definition="clientButton" flush="false" operation="EditFieldValidatorOperation">
                    <tiles:put name="commandTextKey" value="button.add"/>
                    <tiles:put name="commandHelpKey" value="button.add.help"/>
                    <tiles:put name="bundle" value="providerBundle"/>
                    <tiles:put name="action" value="/dictionaries/provider/fields/validators/edit.do?id=${frm.id}"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:if>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="FiledValidatorsTable"/>
                <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/providers.js"></script>
                <tiles:put name="buttons">
                    <c:if test="${isEditable}">
                        <script type="text/javascript">
                            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/dictionaries/provider/fields/validators/edit')}"/>
                            <c:set var="urlCheck" value="${phiz:calculateActionURL(pageContext,'/dictionaries/provider/fields/validators/check')}"/>

                            function doEdit()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkSelection("selectedIds", 'Укажите одну запись') || (!checkOneSelection("selectedIds", 'Укажите только одну запись')))
                                    return;
                                var id = getRadioValue(document.getElementsByName("selectedIds"));
                                window.location = '${url}?validatorId=' + id;
                            }

                            function doCheck()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkSelection("selectedIds", 'Укажите одну запись') || (!checkOneSelection("selectedIds", 'Укажите только одну запись')))
                                    return;
                                var id = getRadioValue(document.getElementsByName("selectedIds"));
                                window.open('${urlCheck}?id=' + id, 'checkValidator', "resizable=0,menubar=0,toolbar=0,scrollbars=1,width=800px,height=400px");
                            }
                        </script>
                        <tiles:insert definition="clientButton" flush="false" operation="CheckValidatorOperation">
                            <tiles:put name="commandTextKey" value="button.check"/>
                            <tiles:put name="commandHelpKey" value="button.check.help"/>
                            <tiles:put name="bundle" value="providerBundle"/>
                            <tiles:put name="onclick" value="doCheck();"/>
                        </tiles:insert>

                        <tiles:insert definition="clientButton" flush="false" operation="EditFieldValidatorOperation">
                            <tiles:put name="commandTextKey" value="button.edit"/>
                            <tiles:put name="commandHelpKey" value="button.edit.help"/>
                            <tiles:put name="bundle" value="providerBundle"/>
                            <tiles:put name="onclick" value="doEdit();"/>
                        </tiles:insert>

                        <tiles:insert definition="commandButton" flush="false" operation="RemoveFieldValidatorOperation">
                            <tiles:put name="commandKey" value="button.remove"/>
                            <tiles:put name="commandHelpKey" value="button.remove.help"/>
                            <tiles:put name="bundle" value="providerBundle"/>
                            <tiles:put name="validationFunction">checkSelection("selectedIds", 'Укажите одну запись')</tiles:put>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" selectBean="bean">
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <c:set var="bean" value="${listElement[3]}"/>
                        <c:set var="validator" value="${listElement[2]}"/>

                        <sl:collectionItem title="Наименование">
                            ${listElement[0]}
                        </sl:collectionItem>
                        <sl:collectionItem title="Код поля">
                            ${listElement[1]}
                        </sl:collectionItem>
                        <sl:collectionItem title="Выражение валидатора">
                            ${validator.value}
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="emptyMessage"><bean:message bundle="providerBundle" key="empty.validators.message"/></tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/provider/comission" onsubmit="return setEmptyAction(event);" enctype="multipart/form-data">
    <tiles:insert definition="recipientDictionariesEdit">
        <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="isEditable" value="${frm.editable}"/>

        <tiles:put name="submenu" value="Provider/Comission"/>
        <tiles:put name="needSave" value="true"/>
        <tiles:put name="menu" type="string">

            <c:if test="${isEditable}">
                <c:if test='${frm.fields.state eq "NOT_ACTIVE" || frm.fields.state eq "MIGRATION"}'>
                    <tiles:insert definition="commandButton" flush="false"
                                  operation="ActivateOrLockServiceProviderOperation">
                        <tiles:put name="commandKey" value="button.activate"/>
                        <tiles:put name="commandHelpKey" value="button.activate.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:if>
                <c:if test='${frm.fields.state eq "ACTIVE"}'>
                    <tiles:insert definition="commandButton" flush="false"
                                  operation="ActivateOrLockServiceProviderOperation">
                        <tiles:put name="commandKey" value="button.lock"/>
                        <tiles:put name="commandHelpKey" value="button.lock.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="validationFunction">checkProviderType('${frm.internetShop}');</tiles:put>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:if>
            </c:if>
        </tiles:put>

        <tiles:put name="data" type="string">
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/providers.js"></script>

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message bundle="providerBundle" key="edit.name"/>
                </tiles:put>

                <tiles:put name="description">
                    <bean:message bundle="providerBundle" key="edit.description"/>
                </tiles:put>

                <tiles:put name="data">
                    <html:hidden property="id" name="frm"/>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.minComission" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(minCommission)" name="frm" size="20" styleClass="contactInput" maxlength="11" disabled="${!isEditable}"/>&nbsp;RUB
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.maxComission" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(maxCommission)" name="frm" size="20" styleClass="contactInput" maxlength="11" disabled="${!isEditable}"/>&nbsp;RUB
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.rate" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(rate)" name="frm" size="20" styleClass="contactInput" maxlength="7" disabled="${!isEditable}"/>&nbsp;%
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="providerBundle"/>
                        <tiles:put name="action"         value="/private/dictionaries/provider/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditServiceProvidersOperation">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"         value="providerBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
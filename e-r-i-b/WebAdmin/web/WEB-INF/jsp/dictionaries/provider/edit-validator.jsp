<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/provider/fields/validators/edit" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="recipientDictionariesEdit">
        <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="providerId" value="${frm.id}"/>
        <c:set var="isEditable" value="${frm.editable}"/>
        <tiles:put name="submenu" value="Provider/Fields/Validators" type="string"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name"><bean:message bundle="providerBundle" key="edit.field.name"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="providerBundle" key="edit.field.description"/></tiles:put>
                <tiles:put name="data">
                    <html:hidden property="id" styleId="id"/>
                    <html:hidden property="validatorId" styleId="validatorId"/>
                    <fieldset>
                        <legend><bean:message key="label.general.parameters" bundle="providerBundle"/></legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.provider" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(providerName)" size="40" name="frm" disabled="true" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.billing" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(billingName)" size="40" name="frm" disabled="true" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.payment.service" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(serviceName)" size="40" name="frm" disabled="true" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.name.select" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:select property="fieldId" name="frm" styleClass="contactInput" onchange="changeField();">
                                    <c:forEach items="${frm.allFields}" var="field">
                                        <html:option value="${field.id}">
                                            <c:out value="${field.name}"/>
                                        </html:option>
                                    </c:forEach>
                                </html:select>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.field.code" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(fieldCode)" size="40" name="frm" value="${code}" disabled="true" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                    <fieldset>
                        <legend><bean:message key="label.validator.parameters" bundle="providerBundle"/></legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.validator.type" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <c:set var="validator" value="${frm.fields}"/>
                                <input type="text" id="validatorType" size="80" size="40" disabled="disabled" value='${validator.validatorType}'/>
                                <html:hidden property="field(validatorType)"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.validator.expression" bundle="providerBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:hidden property="field(validatorRuleType)"/>
                                <html:text property="field(validatorExpression)" disabled="${!isEditable}" size="40" name="frm" styleClass="contactInput"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <fieldset style="border-style:dotted;text-align:center;">
                                    <bean:message key="label.validator.text" bundle="providerBundle"/>
                                </fieldset>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <bean:message key="label.validator.error.message" bundle="providerBundle"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:textarea property="field(validatorErrorMessage)" name="frm" disabled="${!isEditable}" styleClass="MaxSize"/>&nbsp;
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                        <tiles:put name="action" value="/dictionaries/provider/fields/validators/list.do?id=${providerId}"/>
                    </tiles:insert>
                    <c:if test="${isEditable}">
                        <tiles:insert definition="commandButton" flush="false" operation="EditFieldValidatorOperation">
                            <tiles:put name="commandKey" value="button.save"/>
                            <tiles:put name="commandHelpKey" value="button.save.help"/>
                            <tiles:put name="bundle" value="providerBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="postbackNavigation" value="true"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
    <script type="text/javascript">
        function fieldProp(externalId)
        {
            this.externalId = externalId;
        }
        var fields = new Array();
        <c:forEach var="field" items="${frm.allFields}">
            fields["${field.id}"] = new fieldProp("${phiz:escapeForJS(field.externalId, true)}");
        </c:forEach>
        function changeField()
        {
            setElement("field(fieldCode)", fields[getElementValue("fieldId")].externalId);
        }
        changeField();
    </script>
</html:form>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<html:form action="/dictionaries/oldPaymentService/edit" enctype="multipart/form-data">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="paymentServiceEdit">
        <tiles:put name="submenu" type="string" value="PaymentServicesOld"/>

        <tiles:put name="menu" type="string">
        </tiles:put>
        <tiles:put name="data" type="string">
            <html:hidden property="id"/>
            <html:hidden property="parentId"/>
            <c:set var="isEdit" value="${not empty param.id || not empty form.id}"/>
            <c:set var="isSystem" value="${EditPaymentServiceForm.fields.system}" />
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="editPaymentService"/>
                <tiles:put name="name" type="string"><bean:message bundle="paymentServiceBundle" key="editform.name"/></tiles:put>
                <tiles:put name="description" type="string"><bean:message bundle="paymentServiceBundle" key="editform.title"/></tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="paymentServiceBundle" key="label.code"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(synchKey)" size="59" readonly="${(isSystem) && (param.id != null)}"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="paymentServiceBundle" key="label.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(name)" size="59"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.description" bundle="paymentServiceBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:textarea property="field(description)" cols="40" rows="5" styleClass="contactInput"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Логотип
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.show.popular" bundle="paymentServiceBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:radio property="field(popular)" name="form" value="true" style="border:0"  disabled="${(isSystem) && (param.id != null)}"/> <bean:message key="label.yes" bundle="paymentServiceBundle"/>
                            <html:radio property="field(popular)" name="form" value="false" style="border:0" disabled="${(isSystem) && (param.id != null)}"/> <bean:message key="label.no" bundle="paymentServiceBundle"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="paymentServiceBundle" key="label.priority"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <c:set var="priority" value="${form.fields.priority}"/>
                            <tiles:insert definition="scrollTemplate2" flush="false">
                                <tiles:put name="id" value="income"/>
                                <tiles:put name="minValue" value="0"/>
                                <tiles:put name="maxValue" value="100"/>
                                <tiles:put name="currValue" value="${empty priority ? 0 : priority}"/>
                                <tiles:put name="fieldName" value="fields(priority)"/>
                                <tiles:put name="step" value="1"/>
                                <tiles:put name="round" value="0"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="paymentServiceBundle" key="label.parent"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(parent)" size="59" disabled="true"/>
                        </tiles:put>
                    </tiles:insert>

                    <p><bean:message bundle="paymentServiceBundle" key="label.serviceGroup"/></p>
                    <p>Ниже перечислены названия иконок на странице «<bean:message key="category.operations.SERVICE_PAYMENT" bundle="paymentServiceBundle"/>». Выберете иконку, при нажатии на которую будет отображаться данная услуга. Вы можете выбрать несколько иконок.</p>
                    <table>
                        <tr>
                            <td>
                                <html:checkbox property="field(category_TRANSFER)" name="form" styleId="borderAutoPay" value="true"/> <bean:message key="category.operations.TRANSFER" bundle="paymentServiceBundle"/>
                            </td>
                            <td>
                                <html:checkbox property="field(category_EDUCATION)" name="form" styleId="borderAutoPay" value="true"/> <bean:message key="category.operations.EDUCATION" bundle="paymentServiceBundle"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <html:checkbox property="field(category_DEPOSITS_AND_LOANS)" name="form" styleId="borderAutoPay" value="true"/> <bean:message key="category.operations.DEPOSITS_AND_LOANS" bundle="paymentServiceBundle"/>
                            </td>
                            <td>
                                <html:checkbox property="field(category_SERVICE_PAYMENT)" name="form" styleId="borderAutoPay" value="true"/> <bean:message key="category.operations.SERVICE_PAYMENT" bundle="paymentServiceBundle"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <html:checkbox property="field(category_COMMUNICATION)" name="form" styleId="borderAutoPay" value="true"/> <bean:message key="category.operations.COMMUNICATION" bundle="paymentServiceBundle"/>
                            </td>
                            <td>
                                <html:checkbox property="field(category_PFR)" name="form" styleId="borderAutoPay" value="true"/> <bean:message key="category.operations.PFR" bundle="paymentServiceBundle"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <html:checkbox property="field(category_TAX_PAYMENT)" name="form" styleId="borderAutoPay" value="true"/> <bean:message key="category.operations.TAX_PAYMENT" bundle="paymentServiceBundle"/>
                            </td>
                            <td>
                                <html:checkbox property="field(category_OTHER)" name="form" styleId="borderAutoPay" value="true"/> <bean:message key="category.operations.OTHER" bundle="paymentServiceBundle"/>
                            </td>
                        </tr>
                    </table>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="paymentServiceBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="paymentServiceBundle"/>
                        <tiles:put name="action"         value="/dictionaries/oldPaymentService/list"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
            <html:hidden property="field(popular)" disabled="${isEdit && !isSystem}"/>
        </tiles:put>

    </tiles:insert>

</html:form>
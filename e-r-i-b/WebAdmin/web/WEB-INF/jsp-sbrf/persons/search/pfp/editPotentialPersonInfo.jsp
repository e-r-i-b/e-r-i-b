<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/person/potentialPersonInfo" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="pfpPassing">
        <tiles:put name="submenu" type="string" value="personInfo"/>

        <c:set var="personInfo" value="${phiz:getPersonInfo()}"/>
        <c:set var="closeButtonActionUrl" value="/person/pfp/edit.do"/>

        <tiles:put name="data" type="string">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <script type="text/javascript">
                function setDepartmentInfo(resource)
                {
                    $("#departmentIdField").val(resource['id']);
                    $("#departmentNameField").val(resource['name']);
                }
            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="editPerson"/>
                <tiles:put name="name"><bean:message key="label.person.potential.info.form.title" bundle="pfpPassingBundle"/></tiles:put>
                <tiles:put name="description"><bean:message key="label.person.potential.info.form.description" bundle="pfpPassingBundle"/></tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="person.info.field.surName" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(surName)" size="58" readonly="true"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="person.info.field.firstName" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(firstName)" size="58" readonly="true"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="person.info.field.patrName" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(patrName)" size="58" readonly="true"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.documentType" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:select property="field(documentType)" styleId="field(documentType)" styleClass="select" disabled="true">
                                <c:set var="documentType" value="${form.fields.documentType}"/>
                                <html:option value="${documentType}" styleClass="text-grey"><bean:message key="document.type.${documentType}" bundle="personsBundle"/></html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="person.info.field.documentType" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(documentSeries)" size="10" readonly="true"/>
                            <html:text property="field(documentNumber)" size="10" readonly="true"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="person.search.field.birthDay" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(birthDay)" size="10" readonly="true"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.mobilePhone" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(mobilePhone)" size="20" maxlength="16"/>
                            <bean:message key="label.person.potential.info.form.fields.mobilePhone.description" bundle="pfpPassingBundle"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.email" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="fields(email)" maxlength="70" size="58"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="person.search.field.tb" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <div class="inlineBlock paddBottom10">
                                <html:text property="field(departmentName)" styleId="departmentNameField" size="58" readonly="true"/>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey"    value="label.person.potential.info.form.button.back"/>
                        <tiles:put name="commandHelpKey"    value="label.person.potential.info.form.button.back.help"/>
                        <tiles:put name="bundle"            value="pfpPassingBundle"/>
                        <tiles:put name="action"            value="/pfp/person/search"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"        value="button.save"/>
                        <tiles:put name="commandTextKey"    value="label.person.potential.info.form.button.next"/>
                        <tiles:put name="commandHelpKey"    value="label.person.potential.info.form.button.next.help"/>
                        <tiles:put name="bundle"            value="pfpPassingBundle"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

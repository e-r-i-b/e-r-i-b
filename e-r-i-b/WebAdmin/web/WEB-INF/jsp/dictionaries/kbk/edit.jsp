<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/dictionary/kbk/edit">
    <tiles:insert definition="editKBK">
        <tiles:put name="submenu" value="KBK" type="string"/>
        <tiles:put name="menu" type="string"/>

        <c:set var="accessToEdit" value="${phiz:impliesOperation('EditKBKOperation', 'KBKAdminManagement')}"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <html:hidden property="id"/>
                <tiles:put name="id"  value="KBK"/>
                <tiles:put name="name"><bean:message bundle="kbkBundle" key="label.edit.name"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="kbkBundle" key="label.edit.title"/></tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="kbkBundle" key="label.code"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(code)" size="50" maxlength="20" style="font-size:14px;font-family:serif;" disabled="${not accessToEdit}"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="kbkBundle" key="label.description"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea property="field(description)" cols="50" disabled="${not accessToEdit}"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="kbkBundle" key="label.shortName"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(shortName)" size="50" maxlength="25" style="font-size:14px;font-family:serif;" disabled="${not accessToEdit}"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="kbkBundle" key="label.appointment"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea property="field(appointment)" cols="50" disabled="${not accessToEdit}"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="kbkBundle" key="label.payment.type"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(paymentType)" disabled="${not accessToEdit}">
                                <html:option value="TAX"><bean:message bundle="kbkBundle" key="label.tax"/></html:option>
                                <html:option value="BUDGET"><bean:message bundle="kbkBundle" key="label.budget"/></html:option>
                                <html:option value="OFF_BUDGET"><bean:message bundle="kbkBundle" key="label.off.budget"/></html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <fieldset>
                        <legend><bean:message key="label.commission" bundle="kbkBundle"/></legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="kbkBundle" key="label.commission.min"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                             <tiles:put name="data">
                                <html:text property="field(minCommission)" size="30" styleClass="moneyField" disabled="${not accessToEdit}"/>
                                <bean:message bundle="kbkBundle" key="label.commission.currency"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="kbkBundle" key="label.commission.max"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(maxCommission)" size="30" styleClass="moneyField" disabled="${not accessToEdit}"/>
                                <bean:message bundle="kbkBundle" key="label.commission.currency"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="kbkBundle" key="label.rate"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(rate)" size="30" disabled="${not accessToEdit}"/>
                                <bean:message bundle="kbkBundle" key="label.rate.suffix"/>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" operation="EditKBKOperation">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"         value="kbkBundle"/>
                        <tiles:put name="isDefault"        value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" operation="ListKBKOperation">
                       <tiles:put name="commandTextKey" value="button.cancel"/>
                       <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                       <tiles:put name="bundle"         value="kbkBundle"/>
                       <tiles:put name="action"         value="/private/dictionary/kbk/list.do"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>

</html:form>
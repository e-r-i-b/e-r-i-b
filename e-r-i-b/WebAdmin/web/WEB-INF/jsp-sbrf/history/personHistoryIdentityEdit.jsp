<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>


<html:form action="/private/person/documents/history/edit" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="personEdit">

        <tiles:put name="submenu" type="string" value="PersonIdentityHistory"/>

        <tiles:put name="needSave" type="string" value="false"/>
        <tiles:put name="data" type="string">

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="name">
                  <bean:message key="label.person.identity.history.edit" bundle="identityBundle"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="label.person.identity.history.edit.description" bundle="identityBundle"/>
                </tiles:put>

                <tiles:put name="data">
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.firstName" bundle="personsBundle"/><span class="asterisk">*</span>
                        </div>
                        <div class="paymentValue">
                            <html:text property="field(firstName)" size="40" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.surName" bundle="personsBundle"/><span class="asterisk">*</span>
                        </div>
                        <div class="paymentValue">
                            <html:text property="field(surName)"  size="40" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.patrName" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <html:text property="field(patrName)"  size="40" styleClass="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.birthDay" bundle="personsBundle"/><span class="asterisk">*</span>
                        </div>
                        <div class="paymentValue">
                            <input type="text"
                                   name="field(birthDay)" class="dot-date-pick"
                                   value='<bean:write name="form" property="fields.birthDay" format="dd.MM.yyyy"/>'
                                   size="10" class="contactInput"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.documentType" bundle="personsBundle"/><span class="asterisk">*</span>
                        </div>
                        <div class="paymentValue">
                            <html:select property="field(documentType)" styleClass="select" style="width:500px;">
                                <c:forEach var="documentType" items="${form.documentTypes}">
                                    <html:option value="${documentType}"
                                                 styleClass="text-grey"><bean:message
                                            key="document.type.${documentType}"
                                            bundle="personsBundle"/></html:option>
                                </c:forEach>
                            </html:select>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.documentSeries" bundle="personsBundle"/><span class="asterisk">*</span>
                        </div>
                        <div class="paymentValue">
                            <html:text property="field(documentSeries)" size="20" styleClass="contactInput" styleId="field(documentSeries)"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.documentNumber" bundle="personsBundle"/><span class="asterisk">*</span>
                        </div>
                        <div class="paymentValue">
                            <html:text property="field(documentNumber)" size="20" styleClass="contactInput" styleId="field(documentNumber)"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                </tiles:put>
                <tiles:put name="buttons" type="string">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="identityBundle"/>
                        <tiles:put name="action" value="/private/person/documents/history.do?person=${form.person}"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandTextKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save"/>
                        <tiles:put name="bundle" value="identityBundle"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
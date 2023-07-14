<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html:form action="/persons/basketrequisites" onsubmit="return setEmptyAction()">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="person" value="${form.activePerson}"/>
    <c:set var="accessToEditDL" value="${phiz:impliesOperation('EditBasketRequisitesOperation', 'PersonManagement')}"/>
    <c:set var="accessToEditRC" value="${phiz:impliesOperation('EditBasketRequisitesOperation', 'PersonManagement')}"/>

    <tiles:insert definition="personEdit">
        <tiles:put name="submenu" type="string" value="BasketRequisites"/>
        <tiles:put name="needSave" value="false"/>
        <tiles:put name="additionalInfoBlock" type="string" value=""/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name" value="Реквизиты корзины"/>
                <tiles:put name="description" value="Используйте данную форму для изменения данных клиента"/>
                <tiles:put name="data">

                    <%@include file="clientInfoBlock.jsp"%>

                    <c:if test="${form.accessDL}">
                        <c:if test="${phiz:impliesOperation('ViewBasketRequisitesOperation', 'PersonsViewing') or accessToEditDL}">
                            <fieldset>
                                <legend><bean:message key="label.document.driverLicense" bundle="personsBundle"/></legend>
                                <div class="clear"></div>

                                <div class="form-row">
                                    <div class="paymentLabel">
                                        ${phiz:getFieldNameForReq('seriesDL')}:
                                    </div>
                                    <div class="paymentValue">
                                        <html:text property="field(series_dl)" size="10" maxlength="4" styleClass="contactInput" disabled="${not accessToEditDL}"/>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="form-row">
                                    <div class="paymentLabel">
                                        ${phiz:getFieldNameForReq('numberDL')}:
                                    </div>
                                    <div class="paymentValue">
                                        <html:text property="field(number_dl)" size="20" maxlength="6" styleClass="contactInput" disabled="${not accessToEditDL}"/>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="form-row">
                                    <div class="paymentLabel">
                                        ${phiz:getFieldNameForReq('issueOrgDL')}:
                                    </div>
                                    <div class="paymentValue">
                                        <html:text property="field(issueBy)" size="50" maxlength="150" styleClass="contactInput" disabled="${not accessToEditDL}"/>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="form-row">
                                    <div class="paymentLabel">
                                        ${phiz:getFieldNameForReq('issueDateDL')}:
                                    </div>
                                    <div class="paymentValue">
                                        <input type="text"
                                            name="field(issueDate)" class="dot-date-pick"
                                            value='<bean:write name="form" property="fields.issueDate" format="dd.MM.yyyy"/>'
                                            size="10" class="contactInput"
                                            <c:if test="${not accessToEditDL}">disabled="true"</c:if>
                                        />
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="form-row">
                                    <div class="paymentLabel">
                                        ${phiz:getFieldNameForReq('expireDateDL')}:
                                    </div>
                                    <div class="paymentValue">
                                        <input type="text"
                                            name="field(expireDate)" class="dot-date-pick"
                                            value='<bean:write name="form" property="fields.expireDate" format="dd.MM.yyyy"/>'
                                            size="10" class="contactInput"
                                            <c:if test="${not accessToEditDL}">disabled="true"</c:if>
                                        />
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <c:if test="${accessToEditDL}">
                                    <div class="form-row">
                                        <div class="paymentLabel"></div>
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="commandKey"         value="button.removeDocumentDL"/>
                                            <tiles:put name="commandTextKey"     value="button.removeDocument"/>
                                            <tiles:put name="commandHelpKey"     value="button.removeDocument.help"/>
                                            <tiles:put name="bundle"             value="personsBundle"/>
                                            <tiles:put name="viewType"           value="blueBorder"/>
                                        </tiles:insert>
                                    </div>
                                </c:if>
                            </fieldset>
                        </c:if>
                    </c:if>

                    <c:if test="${form.accessRC}">
                        <c:if test="${phiz:impliesOperation('ViewBasketRequisitesOperation', 'PersonsViewing') or accessToEditRC}">
                            <fieldset>
                                <legend><bean:message key="label.document.registrationCertificate" bundle="personsBundle"/></legend>
                                <div class="clear"></div>

                                <div class="form-row">
                                    <div class="paymentLabel">
                                            ${phiz:getFieldNameForReq('seriesRC')}:
                                    </div>
                                    <div class="paymentValue">
                                        <html:text property="field(series_rc)" size="10" maxlength="4" styleClass="contactInput" disabled="${not accessToEditRC}"/>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="form-row">
                                    <div class="paymentLabel">
                                            ${phiz:getFieldNameForReq('numberRC')}:
                                    </div>
                                    <div class="paymentValue">
                                        <html:text property="field(number_rc)" size="20" maxlength="6" styleClass="contactInput" disabled="${not accessToEditRC}"/>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <c:if test="${accessToEditRC}">
                                    <div class="form-row">
                                        <div class="paymentLabel"></div>
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="commandKey"         value="button.removeDocumentRC"/>
                                            <tiles:put name="commandTextKey"     value="button.removeDocument"/>
                                            <tiles:put name="commandHelpKey"     value="button.removeDocument.help"/>
                                            <tiles:put name="bundle"             value="personsBundle"/>
                                            <tiles:put name="viewType"           value="blueBorder"/>
                                        </tiles:insert>
                                    </div>
                                </c:if>
                            </fieldset>

                            <c:if test="${accessToEditRC}">
                                <tiles:put name="buttons">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.cancel"/>
                                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                        <tiles:put name="bundle"         value="commonBundle"/>
                                        <tiles:put name="action"         value="/persons/edit.do?person=${person.id}"/>
                                    </tiles:insert>
                                    <tiles:insert definition="commandButton" flush="false">
                                        <tiles:put name="commandKey"         value="button.save"/>
                                        <tiles:put name="commandHelpKey"     value="button.save.help"/>
                                        <tiles:put name="bundle"             value="commonBundle"/>
                                        <tiles:put name="isDefault"          value="true"/>
                                        <tiles:put name="postbackNavigation" value="true"/>
                                    </tiles:insert>
                                </tiles:put>
                            </c:if>
                        </c:if>
                    </c:if>

                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
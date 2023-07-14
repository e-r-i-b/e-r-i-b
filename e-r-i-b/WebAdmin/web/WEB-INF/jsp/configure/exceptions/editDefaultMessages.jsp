<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<html:form action="/configure/exceptions/defaultMessages"  onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="configEdit">
        <tiles:put name="submenu" type="string" value="ExceptionDefaultMessageEdit"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message key="edit.defaultMessages.title" bundle="exceptionEntryBundle"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="edit.defaultMessages.description" bundle="exceptionEntryBundle"/>
                </tiles:put>
                <tiles:put name="data" type="string">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="edit.defaultMessages.field.client" bundle="exceptionEntryBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <div class="float">
                                <html:textarea styleId="clientMessageText" property="field(clientMessage)" cols="58" rows="5"/>
                                <div class="clear"></div>
                                <tiles:insert definition="bbCodeButtons" flush="false">
                                    <tiles:put name="textAreaId" value="clientMessageText"/>
                                    <tiles:put name="showFio" value="true"/>
                                    <tiles:put name="showBold" value="true"/>
                                    <tiles:put name="showItalics" value="true"/>
                                    <tiles:put name="showUnderline" value="true"/>
                                    <tiles:put name="showColor" value="true"/>
                                    <tiles:put name="showHyperlink" value="true"/>
                                    <tiles:put name="showTextAlign" value="true"/>
                                </tiles:insert>
                            </div>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="edit.defaultMessages.field.admin" bundle="exceptionEntryBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <div class="float">
                                <html:textarea styleId="adminMessageText" property="field(adminMessage)" cols="58" rows="5"/>
                                <div class="clear"></div>
                                <tiles:insert definition="bbCodeButtons" flush="false">
                                    <tiles:put name="textAreaId" value="adminMessageText"/>
                                    <tiles:put name="showBold" value="true"/>
                                    <tiles:put name="showItalics" value="true"/>
                                    <tiles:put name="showUnderline" value="true"/>
                                    <tiles:put name="showColor" value="true"/>
                                    <tiles:put name="showHyperlink" value="true"/>
                                    <tiles:put name="showTextAlign" value="true"/>
                                </tiles:insert>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="exceptionEntryBundle"/>
                        <tiles:put name="action"         value="/configure/exceptions/defaultMessages"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"         value="exceptionEntryBundle"/>
                    </tiles:insert>
                    <div style="float:right">
                        <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/configure/exceptions/defaultMessages')}"/>
                        <tiles:insert definition="languageSelectForEdit" flush="false">
                            <tiles:put name="selectId" value="chooseLocale"/>
                            <tiles:put name="styleClass" value="float"/>
                            <tiles:put name="selectTitleOnclick" value="openEditLocaleWin(this);"/>
                            <tiles:put name="editLanguageURL" value="${editLanguageURL}"/>
                            <tiles:put name="entityId" value="noMetter"/>
                        </tiles:insert>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
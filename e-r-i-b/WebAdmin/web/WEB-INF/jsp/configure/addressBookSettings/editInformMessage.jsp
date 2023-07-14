<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/settings/editInformMessage" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="configEdit">
        <tiles:put name="submenu" type="string" value="EditInformMessageOperation"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message key="settings.addressBook.informMessage.name" bundle="configureBundle"/>
                </tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="settings.addressBook.informMessage.showMessage" bundle="configureBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <div class="float">
                                <html:checkbox property="field(showMessage)"/>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="settings.addressBook.informMessage.message" bundle="configureBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <div class="float">
                                <html:textarea styleId="messageText" property="field(message)" cols="58" rows="5"/>
                                <div class="clear"></div>
                                <tiles:insert definition="bbCodeButtons" flush="false">
                                    <tiles:put name="textAreaId" value="messageText"/>
                                    <tiles:put name="showFio" value="false"/>
                                    <tiles:put name="showBold" value="true"/>
                                    <tiles:put name="showItalics" value="true"/>
                                    <tiles:put name="showUnderline" value="true"/>
                                    <tiles:put name="showColor" value="true"/>
                                    <tiles:put name="showHyperlink" value="true"/>
                                    <tiles:put name="showTextAlign" value="false"/>
                                </tiles:insert>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" operation="EditInformMessageOperation" service="EditAddressBookSynchronizationSettings">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="onclick" value="window.location.reload();"/>
                    </tiles:insert>
                    <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/settings/locale/editInformMessage')}"/>
                    <tiles:insert definition="languageSelectForEdit" flush="false">
                        <tiles:put name="selectId" value="chooseLocale"/>
                        <tiles:put name="entityId" value=""/>
                        <tiles:put name="styleClass" value="float"/>
                        <tiles:put name="selectTitleOnclick" value="openEditLocaleWin(this);"/>
                        <tiles:put name="editLanguageURL" value="${editLanguageURL}"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
    <script type="text/javascript">
        $(document).ready(function(){
            initAreaMaxLengthRestriction('messageText', 2000);
        });
    </script>
</html:form>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<html:form action="/clientProfile/configure"  onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="showCheckbox" value="${form.replication}"/>
    <c:set var="editProfileCofigureAccess" value="${phiz:impliesService('ClientProfileConfigureService') or phiz:impliesService('ClientProfileConfigureServiceEmployee')}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="ÑlientProfileConfig"/>
        <tiles:put name="pageTitle"><bean:message bundle="configureBundle" key="settings.clientProfile.menuitem"/></tiles:put>
        <tiles:put name="data" type="string">
            <tiles:importAttribute/>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="personsConfigure"/>
                <tiles:put name="description" value=""/>
                <tiles:put name="data">
                    <fieldset>
                        <legend><bean:message bundle="configureBundle" key="settings.clientProfile.title.avatarSettings"/></legend>
                        <table>
                            <tr>
                                <td>
                                    <c:if test="${showCheckbox}">
                                        <input type="checkbox" id="replicationSelect" value="com.rssl.profile.avatarAvailableFiles" name="selectedProperties"/>
                                        <label for="replicationSelect"><bean:message key="label.checkbox.replication.property" bundle="commonBundle"/></label>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="vertical-align rightAlign"><bean:message bundle="configureBundle" key="settings.clientProfile.field.avatarAvailableFiles"/></td>
                                <td>
                                    <html:checkbox property="checkedJPG" styleId="checkedJPG" disabled="${showCheckbox or not editProfileCofigureAccess}"/><label for="checkedJPG">jpeg/jpg</label><br/>
                                    <html:checkbox property="checkedGIF" styleId="checkedGIF" disabled="${showCheckbox or not editProfileCofigureAccess}"/><label for="checkedGIF">gif</label><br/>
                                    <html:checkbox property="checkedPNG" styleId="checkedPNG" disabled="${showCheckbox or not editProfileCofigureAccess}"/><label for="checkedPNG">png</label><br>
                                    <bean:message bundle="configureBundle" key="settings.clientProfile.field.avatarAvailableFiles.selectednone"/>
                                </td>
                            </tr>
                            <tiles:insert definition="propertyField" flush="false">
                                <tiles:put name="fieldName" value="com.rssl.profile.maxLoadedImageLongSize"/>
                                <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.clientProfile.field.maxLoadedImageLongSize"/></tiles:put>
                                <tiles:put name="textSize" value="8"/>
                                <tiles:put name="textMaxLength" value="5"/>
                                <tiles:put name="showHint">none</tiles:put>
                                <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                                <tiles:put name="disabled" value="${not editProfileCofigureAccess}"/>
                            </tiles:insert>
                            <tiles:insert definition="propertyField" flush="false">
                                <tiles:put name="fieldName" value="com.rssl.profile.avatarFileMaxSize"/>
                                <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.clientProfile.field.avatarFileMaxSize"/></tiles:put>
                                <tiles:put name="textSize" value="8"/>
                                <tiles:put name="textMaxLength" value="5"/>
                                <tiles:put name="showHint">none</tiles:put>
                                <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                                <tiles:put name="disabled" value="${not editProfileCofigureAccess}"/>
                            </tiles:insert>
                            <tiles:insert definition="propertyField" flush="false">
                                <tiles:put name="fieldName" value="com.rssl.profile.maxAvatarLongSize"/>
                                <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.clientProfile.field.maxAvatarLongSize"/></tiles:put>
                                <tiles:put name="textSize" value="8"/>
                                <tiles:put name="textMaxLength" value="5"/>
                                <tiles:put name="showHint">none</tiles:put>
                                <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                                <tiles:put name="disabled" value="${not editProfileCofigureAccess}"/>
                            </tiles:insert>
                            <tiles:insert definition="propertyField" flush="false">
                                <tiles:put name="fieldName" value="com.rssl.profile.kadrAvatarFileMaxSize"/>
                                <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.clientProfile.field.kadrAvatarFileMaxSize"/></tiles:put>
                                <tiles:put name="textSize" value="8"/>
                                <tiles:put name="textMaxLength" value="5"/>
                                <tiles:put name="showHint">none</tiles:put>
                                <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                                <tiles:put name="disabled" value="${not editProfileCofigureAccess}"/>
                            </tiles:insert>
                        </table>
                    </fieldset>
                    <table>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.iccs.addressbook.amountP2P.toAdd"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.clientProfile.field.amountP2PToAdd"/></tiles:put>
                            <tiles:put name="textSize" value="8"/>
                            <tiles:put name="textMaxLength" value="5"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                            <tiles:put name="disabled" value="${not editProfileCofigureAccess}"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.iccs.addressbook.fromP2P"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.clientProfile.field.fillFromP2P"/></tiles:put>
                            <tiles:put name="fieldType" value="checkbox"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                            <tiles:put name="disabled" value="${not editProfileCofigureAccess}"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.iccs.addressbook.show.link.confirmContact.android"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.clientProfile.field.showLinkConfirmContactAndroid"/></tiles:put>
                            <tiles:put name="fieldType" value="checkbox"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                            <tiles:put name="disabled" value="${not editProfileCofigureAccess}"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.iccs.addressbook.allowOneConfirm"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.clientProfile.field.allowOneConfirm"/></tiles:put>
                            <tiles:put name="fieldType" value="checkbox"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                            <tiles:put name="disabled" value="${not editProfileCofigureAccess}"/>
                        </tiles:insert>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.iccs.addressbook.fromServicePayments"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.clientProfile.field.fillFromServicePayments"/></tiles:put>
                            <tiles:put name="fieldType" value="checkbox"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                            <tiles:put name="disabled" value="${not editProfileCofigureAccess}"/>
                        </tiles:insert>
                    </table>
                </tiles:put>
            </tiles:insert>
            <tiles:put name="formButtons">
                <c:if test="${editProfileCofigureAccess}">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="commonBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </c:if>
            </tiles:put>
        </tiles:put>
    </tiles:insert>
</html:form>

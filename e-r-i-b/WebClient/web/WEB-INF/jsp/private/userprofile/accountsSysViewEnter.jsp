<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>
<html:form action="/private/userprofile/accountsSystemView" onsubmit="return setEmptyAction(event);">
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:if test="${not empty form.confirmableObject}">
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
</c:if>
<c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
<tiles:insert definition="userProfile">
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Настройки"/>
            <tiles:put name="action" value="/private/userprofile/userSettings.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Настройка безопасности"/>
            <tiles:put name="action" value="/private/userprofile/accountSecurity.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Настройка видимости продуктов"/>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="data" type="string">
        <html:hidden property="field(unsavedData)" name="form"/>
        <div id="profile"  onkeypress="onEnterKey(event);">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Настройки"/>
                <tiles:put name="data">
                    <tiles:insert definition="userSettings" flush="false">
                        <tiles:put name="data">
                            <c:set var="selectedTab" value="securetySettings"/>
                            <%@ include file="/WEB-INF/jsp/private/userprofile/userSettingsHeader.jsp" %>
                            <div class="payments-tabs">
                                <c:set var="security" value="true"/>
                                <%@ include file="securityHeader.jsp"%>
                                <div class="greenContainer picInfoBlockSysView">
                                    <div>
                                        Для безопасного доступа к данному пункту меню введите SMS-пароль.
                                        Для этого нажмите на кнопку &laquo;<bean:message key="button.confirmSMS" bundle="securityBundle"/>&raquo;.
                                    </div>
                                </div>
                                <div class="clear"></div>
                                <tiles:put name="buttons">
                                    <div class="buttonsArea iebuttons">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.cancel"/>
                                            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                            <tiles:put name="bundle" value="commonBundle"/>
                                            <tiles:put name="action" value="/private/userprofile/accountSecurity.do"/>
                                            <tiles:put name="viewType" value="buttonGrey"/>
                                        </tiles:insert>
                                        <tiles:insert definition="window" flush="false">
                                            <tiles:put name="id" value="oneTimePasswordWindow"/>
                                        </tiles:insert>
                                        <tiles:insert definition="confirmButtons" flush="false">
                                            <tiles:put name="ajaxUrl" value="/private/async/userprofile/accountsSystemView"/>
                                            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                            <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                            <tiles:put name="preConfirmCommandKey" value="button.preConfirm"/>
                                        </tiles:insert>
                                    </div>
                                </tiles:put>
                            </div>
                       </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>
</html:form>
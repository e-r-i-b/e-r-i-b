<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl"%>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>

<html:form action="/private/userprofile/userNotification">
<c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>



<c:set var="confirmableObjectExist" value="${not empty form.confirmableObject}"/>
<c:if test="${confirmableObjectExist && form.confirmedType == 'loginNotification'}">
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    <c:set var="notificationConfirmStrategy" value="${form.notificationConfirmStrategy}"/>
</c:if>
<c:if test="${confirmableObjectExist && form.confirmedType == 'mailNotification'}">
    <c:set var="mailConfirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    <c:set var="mailNotificationConfirmStrategy" value="${form.notificationConfirmStrategy}"/>
</c:if>
<c:if test="${confirmableObjectExist && form.confirmedType == 'operationNotification'}">
    <c:set var="deliveryConfirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    <c:set var="deliveryNotificationConfirmStrategy" value="${form.notificationConfirmStrategy}"/>
</c:if>
<c:if test="${confirmableObjectExist && form.confirmedType == 'newsNotification'}">
    <c:set var="bankNewsConfirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    <c:set var="bankNewsNotificationConfirmStrategy" value="${form.notificationConfirmStrategy}"/>
</c:if>

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
            <tiles:put name="name" value="Настройка оповещений"/>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="data" type="string">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="bundle" value="userprofileBundle"/>
        <html:hidden property="field(unsavedData)" name="form"/>
        <div id="profile" onkeypress="onEnterKey(event);">
            <tiles:insert definition="mainWorkspace" flush="false">
            <tiles:put name="title" value="Настройки"/>
                <tiles:put name="data">
                    <tiles:insert definition="userSettings" flush="false">
                        <tiles:put name="data">
                            <c:set var="selectedTab" value="messagesSettings"/>
                            <%@ include file="/WEB-INF/jsp/private/userprofile/userSettingsHeader.jsp" %>
                            <div class="clear"></div>
                            <div class="securityOptionsArea">
                                <div class="securityOptions">
                                    <tiles:insert definition="userProfileSecurity" flush="false">
                                        <tiles:put name="title" value="Настройка уведомлений о входе на личную страницу"/>
                                        <tiles:put name="text">
                                            Здесь Вы можете выбрать способ доставки уведомлений о том, что совершен вход на Вашу личную страницу:
                                        </tiles:put>
                                        <tiles:put name="style" value="turquoise"/>
                                        <tiles:put name="data">
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td colspan="3" class="align-left">
                                                <html:hidden name="form" property="field(email)" style="display:none"/>
                                                <html:hidden name="form" property="field(phone)" style="display:none"/>
                                                <html:hidden name="form" property="field(push)" style="display:none"/>
                                                <html:hidden name="form" property="field(mobileDevices)" style="display:none"/>
                                                <c:if test="${not empty confirmRequest}">
                                                    <html:hidden name="form" property="field(loginNotification)" style="display:none"/>
                                                </c:if>

                                                <div>
                                                    <html:radio name="form" property="field(loginNotification)" value="sms" disabled="${confirmableObjectExist}"/>
                                                    <bean:message bundle="userprofileBundle" key="label.notification.sms" arg0="${form.fields.phone}"/>
                                                </div>
                                                <div>
                                                    <html:radio name="form" property="field(loginNotification)" value="email" disabled="${confirmableObjectExist}"/>
                                                    <bean:message bundle="userprofileBundle" key="label.notification.email" arg0="${form.fields.email}"/>
                                                </div>
                                                <c:if test="${phiz:impliesService('ClientProfilePush')}">
                                                    <div>
                                                        <html:radio name="form" property="field(loginNotification)" value="push" disabled="${confirmableObjectExist}"/>
                                                        <bean:message bundle="userprofileBundle" key="label.notification.push" arg0="${form.fields.push}"/>
                                                    </div>
                                                </c:if>
                                            </td>
                                        </tr>
                                        <div>&nbsp;</div>
                                        <div>&nbsp;</div>
                                        <div class="buttonsArea">
                                            <c:choose>
                                                <c:when test="${not empty confirmRequest}">
                                                    <tiles:insert definition="clientButton" flush="false">
                                                        <tiles:put name="commandTextKey" value="button.backToEdit"/>
                                                        <tiles:put name="commandHelpKey" value="button.backToEdit.help"/>
                                                        <tiles:put name="bundle" value="commonBundle"/>
                                                        <tiles:put name="action" value="/private/userprofile/userNotification.do"/>
                                                        <tiles:put name="viewType" value="buttonGrey"/>
                                                    </tiles:insert>
                                                    <tiles:insert definition="confirmButtons" flush="false">
                                                        <tiles:put name="ajaxUrl" value="/private/async/userprofile/userNotification"/>
                                                        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                                        <tiles:put name="confirmStrategy" beanName="notificationConfirmStrategy"/>
                                                        <tiles:put name="anotherStrategy" value="false"/>
                                                        <tiles:put name="preConfirmCommandKey" value="button.preConfirmNotification"/>
                                                    </tiles:insert>
                                                </c:when>
                                                <c:otherwise>
                                                    <tiles:insert definition="clientButton" flush="false">
                                                        <tiles:put name="commandTextKey" value="button.cancel"/>
                                                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                                        <tiles:put name="bundle" value="commonBundle"/>
                                                        <tiles:put name="action" value="/private/accounts.do"/>
                                                        <tiles:put name="viewType" value="buttonGrey"/>
                                                    </tiles:insert>
                                                    <tiles:insert definition="commandButton" flush="false">
                                                        <tiles:put name="commandKey" value="button.save"/>
                                                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                                                        <tiles:put name="bundle" value="commonBundle"/>
                                                    </tiles:insert>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                                <div class="securityOptions profileSettings">
                                    <tiles:insert definition="userProfileSecurity" flush="false">
                                        <tiles:put name="title" value="Настройка оповещений из службы помощи"/>
                                        <tiles:put name="text">
                                            Здесь Вы можете выбрать способ получения оповещения о доставке письма из службы помощи
                                        </tiles:put>
                                        <tiles:put name="style" value="turquoise"/>
                                        <tiles:put name="data">
                                            <tr>
                                                <td>&nbsp;</td>
                                                <td colspan="3" class="align-left">
                                                    <html:hidden name="form" property="field(email)" style="display:none"/>
                                                    <html:hidden name="form" property="field(phoneForMail)" style="display:none"/>
                                                    <html:hidden name="form" property="field(push)" style="display:none"/>
                                                    <html:hidden name="form" property="field(mobileDevices)" style="display:none"/>
                                                    <c:if test="${not empty mailConfirmRequest}">
                                                        <html:hidden name="form" property="field(mailNotification)" style="display:none"/>
                                                    </c:if>

                                                    <div>
                                                        <html:radio  name="form" property="field(mailNotification)" value="sms" disabled="${confirmableObjectExist}"/>
                                                        <bean:message bundle="userprofileBundle" key="label.notification.sms" arg0="${form.fields.phoneForMail}"/>
                                                    </div>
                                                    <div>
                                                        <html:radio name="form" property="field(mailNotification)" value="email" disabled="${confirmableObjectExist}"/>
                                                        <bean:message bundle="userprofileBundle" key="label.notification.email" arg0="${form.fields.email}"/>
                                                    </div>
                                                    <c:if test="${phiz:impliesService('ClientProfilePush')}">
                                                        <div>
                                                            <html:radio name="form" property="field(mailNotification)" value="push" disabled="${confirmableObjectExist}"/>
                                                            <bean:message bundle="userprofileBundle" key="label.notification.push" arg0="${form.fields.push}"/>
                                                        </div>
                                                    </c:if>
                                                    <div>
                                                        <html:radio name="form" property="field(mailNotification)" value="none" disabled="${confirmableObjectExist}"/>
                                                        <bean:message bundle="userprofileBundle" key="label.delivery.N"/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <div>&nbsp;</div>
                                            <div>&nbsp;</div>
                                            <div class="buttonsArea">
                                                <c:choose>
                                                    <c:when test="${not empty mailConfirmRequest}">
                                                        <tiles:insert definition="clientButton" flush="false">
                                                            <tiles:put name="commandTextKey" value="button.backToEdit"/>
                                                            <tiles:put name="commandHelpKey" value="button.backToEdit.help"/>
                                                            <tiles:put name="bundle" value="commonBundle"/>
                                                            <tiles:put name="action" value="/private/userprofile/userNotification.do"/>
                                                            <tiles:put name="viewType" value="buttonGrey"/>
                                                        </tiles:insert>
                                                        <tiles:insert definition="confirmButtons" flush="false">
                                                            <tiles:put name="ajaxUrl" value="/private/async/userprofile/userNotification"/>
                                                            <tiles:put name="confirmRequest" beanName="mailConfirmRequest"/>
                                                            <tiles:put name="confirmStrategy" beanName="mailNotificationConfirmStrategy"/>
                                                            <tiles:put name="preConfirmCommandKey" value="button.preConfirmMail"/>
                                                            <tiles:put name="anotherStrategy" value="false"/>
                                                        </tiles:insert>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tiles:insert definition="clientButton" flush="false">
                                                            <tiles:put name="commandTextKey" value="button.cancel"/>
                                                            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                                            <tiles:put name="bundle" value="commonBundle"/>
                                                            <tiles:put name="action" value="/private/accounts.do"/>
                                                            <tiles:put name="viewType" value="buttonGrey"/>
                                                        </tiles:insert>
                                                        <tiles:insert definition="commandButton" flush="false">
                                                            <tiles:put name="commandKey" value="button.saveMailNotificationSettings"/>
                                                            <tiles:put name="commandHelpKey" value="button.saveMailNotificationSettings.help"/>
                                                            <tiles:put name="bundle" value="commonBundle"/>
                                                        </tiles:insert>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>

                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                                <div class="securityOptions profileSettings">
                                    <tiles:insert definition="userProfileSecurity" flush="false">
                                        <tiles:put name="title" value="Настройка оповещений об исполнении операций"/>
                                        <tiles:put name="text">
                                            Здесь Вы можете выбрать способ доставки оповещений об исполнении, отклонении операций и необходимости их подтверждения в Контактном центре банка.
                                        </tiles:put>
                                        <tiles:put name="style" value="turquoise"/>
                                        <tiles:put name="data">
                                            <tr>
                                                <td>&nbsp;</td>
                                                <td colspan="3" class="align-left">
                                                    <html:hidden name="form" property="field(email)" style="display:none"/>
                                                    <html:hidden name="form" property="field(phoneForMail)" style="display:none"/>
                                                    <html:hidden name="form" property="field(push)" style="display:none"/>
                                                    <html:hidden name="form" property="field(mobileDevices)" style="display:none"/>
                                                    <c:if test="${not empty deliveryConfirmRequest}">
                                                        <html:hidden name="form" property="field(operationNotification)" style="display:none"/>
                                                    </c:if>

                                                    <div>
                                                        <html:radio  name="form" property="field(operationNotification)" value="sms" disabled="${confirmableObjectExist}"/>
                                                        <bean:message bundle="userprofileBundle" key="label.notification.sms" arg0="${form.fields.phoneForMail}"/>
                                                    </div>
                                                    <div>
                                                        <html:radio name="form" property="field(operationNotification)" value="email" disabled="${confirmableObjectExist}"/>
                                                        <bean:message bundle="userprofileBundle" key="label.notification.email" arg0="${form.fields.email}"/>
                                                    </div>
                                                    <c:if test="${phiz:impliesService('ClientProfilePush')}">
                                                        <div>
                                                            <html:radio name="form" property="field(operationNotification)" value="push" disabled="${confirmableObjectExist}"/>
                                                            <bean:message bundle="userprofileBundle" key="label.notification.push" arg0="${form.fields.push}"/>
                                                        </div>
                                                    </c:if>
                                                    <div>
                                                        <html:radio name="form" property="field(operationNotification)" value="none" disabled="${confirmableObjectExist}"/>
                                                        <bean:message bundle="userprofileBundle" key="label.delivery.N"/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <div>&nbsp;</div>
                                            <div>&nbsp;</div>
                                            <div class="buttonsArea">
                                                <c:choose>
                                                    <c:when test="${not empty deliveryConfirmRequest}">
                                                        <tiles:insert definition="clientButton" flush="false">
                                                            <tiles:put name="commandTextKey" value="button.backToEdit"/>
                                                            <tiles:put name="commandHelpKey" value="button.backToEdit.help"/>
                                                            <tiles:put name="bundle" value="commonBundle"/>
                                                            <tiles:put name="action" value="/private/userprofile/userNotification.do"/>
                                                            <tiles:put name="viewType" value="buttonGrey"/>
                                                        </tiles:insert>
                                                        <tiles:insert definition="confirmButtons" flush="false">
                                                            <tiles:put name="ajaxUrl" value="/private/async/userprofile/userNotification"/>
                                                            <tiles:put name="confirmRequest" beanName="deliveryConfirmRequest"/>
                                                            <tiles:put name="confirmStrategy" beanName="deliveryNotificationConfirmStrategy"/>
                                                            <tiles:put name="preConfirmCommandKey" value="button.preConfirmDeliveryNotification"/>
                                                            <tiles:put name="anotherStrategy" value="false"/>
                                                        </tiles:insert>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tiles:insert definition="clientButton" flush="false">
                                                            <tiles:put name="commandTextKey" value="button.cancel"/>
                                                            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                                            <tiles:put name="bundle" value="commonBundle"/>
                                                            <tiles:put name="action" value="/private/accounts.do"/>
                                                            <tiles:put name="viewType" value="buttonGrey"/>
                                                        </tiles:insert>
                                                        <tiles:insert definition="commandButton" flush="false">
                                                            <tiles:put name="commandKey" value="button.saveDeliveryNotificationSettings"/>
                                                            <tiles:put name="commandHelpKey" value="button.saveDeliveryNotificationSettings.help"/>
                                                            <tiles:put name="bundle" value="commonBundle"/>
                                                        </tiles:insert>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>

                                        </tiles:put>
                                    </tiles:insert>
                                </div>

                                <c:if test="${form.showResourcesSmsNotificationBlock}">
                                    <div class="securityOptions profileSettings">
                                        <tiles:insert definition="userProfileSecurity" flush="false">
                                            <tiles:put name="title" value="Настройка SMS-оповещений по продуктам"/>
                                            <tiles:put name="text">
                                                Здесь Вы можете выбрать продукты, по которым хотите получать SMS-оповещения
                                            </tiles:put>
                                            <tiles:put name="style" value="turquoise"/>
                                            <tiles:put name="data">
                                                <c:set var="cancelURL" value="/private/userprofile/userNotification.do"/>
                                                <%@ include file="/WEB-INF/jsp/private/userprofile/userProductNotificationData.jsp" %>
                                            </tiles:put>
                                        </tiles:insert>
                                    </div>

                                    <tiles:insert definition="window" flush="false">
                                        <tiles:put name="id" value="redirectRefused"/>
                                        <tiles:put name="data">
                                             <h2>Внимание!</h2>
                                                Изменения настроек не сохранены. Перейти на другую страницу?».
                                            <div class="buttonsArea">
                                                <tiles:insert definition="clientButton" flush="false">
                                                    <tiles:put name="commandTextKey" value="button.confirm"/>
                                                    <tiles:put name="commandHelpKey" value="button.confirm"/>
                                                    <tiles:put name="bundle" value="commonBundle"/>
                                                    <tiles:put name="viewType" value="buttonGrey"/>
                                                    <tiles:put name="onclick" value="redirect()"/>
                                                </tiles:insert>
                                                <tiles:insert definition="clientButton" flush="false">
                                                    <tiles:put name="commandTextKey" value="button.closeWindow"/>
                                                    <tiles:put name="commandHelpKey" value="button.closeWindow"/>
                                                    <tiles:put name="bundle" value="commonBundle"/>
                                                    <tiles:put name="viewType" value="buttonGrey"/>
                                                    <tiles:put name="onclick" value="win.close('redirectRefused');"/>
                                                </tiles:insert>
                                            </div>
                                        </tiles:put>
                                    </tiles:insert>
                                </c:if>
                            </div>


                            <div class="securityOptions profileSettings">
                                <tiles:insert definition="userProfileSecurity" flush="false">
                                    <tiles:put name="title" value="Настройка рассылки новостей банка"/>
                                    <tiles:put name="text">
                                        Здесь Вы можете настроить рассылку новостей банка
                                    </tiles:put>
                                    <tiles:put name="style" value="turquoise"/>
                                    <tiles:put name="data">
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td colspan="1" class="align-left">
                                                <html:hidden name="form" property="field(email)" style="display:none"/>
                                                <c:if test="${not empty bankNewsConfirmRequest}">
                                                    <html:hidden name="form" property="field(newsNotification)" style="display:none"/>
                                                </c:if>

                                                <div>
                                                    <html:radio name="form" property="field(newsNotification)" value="email" disabled="${confirmableObjectExist}"/>
                                                    <bean:message bundle="userprofileBundle" key="label.notification.email" arg0="${form.fields.email}"/>
                                                </div>
                                                <div>
                                                    <html:radio name="form" property="field(newsNotification)" value="none" disabled="${confirmableObjectExist}"/>
                                                    <bean:message bundle="userprofileBundle" key="label.delivery.N"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <div>&nbsp;</div>
                                        <div>&nbsp;</div>
                                        <div class="buttonsArea">
                                            <c:choose>
                                                <c:when test="${not empty bankNewsConfirmRequest}">
                                                    <tiles:insert definition="clientButton" flush="false">
                                                        <tiles:put name="commandTextKey" value="button.backToEdit"/>
                                                        <tiles:put name="commandHelpKey" value="button.backToEdit.help"/>
                                                        <tiles:put name="bundle" value="commonBundle"/>
                                                        <tiles:put name="action" value="/private/userprofile/userNotification.do"/>
                                                        <tiles:put name="viewType" value="buttonGrey"/>
                                                    </tiles:insert>
                                                    <tiles:insert definition="confirmButtons" flush="false">
                                                        <tiles:put name="ajaxUrl" value="/private/async/userprofile/userNotification"/>
                                                        <tiles:put name="confirmRequest" beanName="bankNewsConfirmRequest"/>
                                                        <tiles:put name="confirmStrategy" beanName="bankNewsNotificationConfirmStrategy"/>
                                                        <tiles:put name="preConfirmCommandKey" value="button.preConfirmBankNewsNotification"/>
                                                        <tiles:put name="anotherStrategy" value="false"/>
                                                    </tiles:insert>
                                                </c:when>
                                                <c:otherwise>
                                                    <tiles:insert definition="clientButton" flush="false">
                                                        <tiles:put name="commandTextKey" value="button.cancel"/>
                                                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                                        <tiles:put name="bundle" value="commonBundle"/>
                                                        <tiles:put name="action" value="/private/accounts.do"/>
                                                        <tiles:put name="viewType" value="buttonGrey"/>
                                                    </tiles:insert>
                                                    <tiles:insert definition="commandButton" flush="false">
                                                        <tiles:put name="commandKey" value="button.saveBankNewsNotificationSettings"/>
                                                        <tiles:put name="commandHelpKey" value="button.saveBankNewsNotificationSettings.help"/>
                                                        <tiles:put name="bundle" value="commonBundle"/>
                                                    </tiles:insert>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>

                                    </tiles:put>
                                </tiles:insert>
                            </div>
                            <div class="clear"></div>

                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
           </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>
<div></div></html:form>

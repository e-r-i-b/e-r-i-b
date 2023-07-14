<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>

<html:form action="/private/mobileApplications/view" onsubmit="return setEmptyAction(event)">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="newUserProfile">
        <tiles:put name="data" type="string">
            <tiles:insert definition="profileTemplate" flush="false">
                <tiles:put name="title">Мобильные приложения</tiles:put>
                <tiles:put name="activeItem">mobileApplications</tiles:put>
                <tiles:put name="data">
                    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
                    <c:set var="mobileApplications" value="${form.mobileApplications}"/>
                    <c:set var="mobilePlatformList" value="${form.mobilePlatformList}"/>
                    <c:set var="winID" value="qrCodeWindowID"/>
                    <c:set var="qrCodeLink" value="${phiz:calculateActionURL(pageContext,'/private/mobileApplications/qrcode.do')}"/>
                    <c:set var="qrCodeActionUrl" value="${qrCodeLink}?mobileApplication="/>

                        <h3>
                            <bean:message key="label.mobileApplication.header1" bundle="commonBundle" arg0="${phiz:calculateActionURL(pageContext, '/private/userprofile/accountSecurity')}?needOpenTab=mobileProductView"/>  <br/>
                            <bean:message key="label.mobileApplication.header2" bundle="commonBundle" arg0="${phiz:calculateActionURL(pageContext, '/private/userprofile/accountSecurity')}"/>
                        </h3>

                        <%--Список подключенных мобильных приложений--%>
                        <c:if test="${not empty mobileApplications}">
                            <div class="view-mobileApplications">
                                <table>
                                    <c:forEach var="mobileApp" items="${mobileApplications}" varStatus="line">
                                        <tr>
                                            <td class="align-left logo">
                                                <img src="${phiz:getDeviceIcon(mobileApp.deviceInfo)}" border="0"/>
                                            </td>
                                            <%--Подключение и статус PUSH-уведомлений--%>
                                            <td class="align-left">
                                                <span>
                                                    <c:forEach var="mobilePlatform" items="${mobilePlatformList}" varStatus="line">
                                                        <c:if test="${mobileApp.deviceInfo eq mobilePlatform.platformId}">
                                                            <span class="deviceInfo"><c:out value="${mobilePlatform.platformName}"/></span>
                                                        </c:if>
                                                    </c:forEach>
                                                    <c:if test="${phiz:impliesOperation('PushSettingsOperation', 'ClientProfilePush')}">
                                                        <c:if test="${!mobileApp.pushSupported}">
                                                            <div class="relativeBubble">
                                                                <div class="bubbleContainer">
                                                                    <span class="pushSupported mobileBubbleLeft">
                                                                        <span class="mobileBubbleRight">
                                                                            <span class="mobileBubbleInner">
                                                                                <span class="deviceStatus">
                                                                                    Push-уведомления отключены
                                                                                    <span class="yellowRoundTriangle"></span>
                                                                                    <span class="hint">
                                                                                        <span class="textHint">
                                                                                            Для получения Push-уведомлений включите данную опцию в приложении «Сбербанк Онлайн» на вашем мобильном устройстве и в разделе «<a href="${phiz:calculateActionURL(pageContext, '/private/userprofile/userNotification')}">Оповещения</a>» вашего профиля в «Сбербанк Онлайн».
                                                                                            <span class="triangle-with-shadow"></span>
                                                                                        </span>
                                                                                    </span>
                                                                                </span>
                                                                            </span>
                                                                        </span>
                                                                    </span>
                                                                </div>
                                                            </div>
                                                        </c:if>
                                                    </c:if>
                                                </span>
                                            </td>
                                            <%--Ссылка "отключить" мобильное устройство--%>
                                            <c:if test="${phiz:impliesOperation('MobileApplicationsLockOperation','ShowConnectedMobileDevicesService')}">
                                                <td class="cancelMobileApplication">
                                                    <tiles:insert definition="confirmationButton" flush="false">
                                                        <tiles:put name="winId" value="confirmRemoveMobileApplication${mobileApp.id}"/>
                                                        <tiles:put name="title" value="Подтверждение отключения приложения"/>
                                                        <tiles:put name="currentBundle" value="commonBundle"/>
                                                        <tiles:put name="confirmKey" value="button.mobileapplications.lock"/>
                                                        <tiles:put name="confirmCommandKey" value="button.mobileApplication.lock"/>
                                                        <tiles:put name="confirmCommandTextKey" value="button.mobileApplication.lock.confirm"/>
                                                        <tiles:put name="message" value="Вы действительно хотите отключить приложение?"/>
                                                        <tiles:put name="afterConfirmFunction">cancelMobileApplication('${mobileApp.id}')</tiles:put>
                                                    </tiles:insert>
                                                </td>
                                            </c:if>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </c:if>

                        <div class="view-mobileApplicationInfo">
                            <h3>
                                <bean:message key="label.mobileApplication.header3" bundle="commonBundle"/>
                            </h3>
                        </div>
                        <%--Подключенные мобильные приложения--%>
                        <%--Максимальное кол-во отображаемых типов мобильных приложений в ряде--%>
                        <c:set var="mobileAppTypeMaxColCount" value="3"/>

                        <c:set var="rowIndex" value="1"/>
                        <div class="view-mobileApplicationsType">
                            <c:forEach var="mobilePlatform" items="${mobilePlatformList}" varStatus="line" >
                                <c:if test="${mobilePlatform.showInApps}">
                                    <div>
                                        <c:choose>
                                            <c:when test="${mobilePlatform.downloadFromSBRF}">
                                                <c:set var='onclick' value="openMobileAppDistrib('${mobilePlatform.bankURL}');"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var='onclick' value="openQRCode('${mobilePlatform.platformId}');"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${empty mobilePlatform.platformIcon}">
                                                <div class="${mobilePlatform.platformName != 'Windows Phone' ? mobilePlatform.platformName : 'windowsPhone'} mobileApp" onclick="${onclick}">
                                                    <span class="mobileLogo">&nbsp;</span>
                                                    <span class="mobileText">Приложение для <br/> <span class="mobileType">${mobilePlatform.platformName}</span></span>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="imageData" value="${phiz:getImageById(mobilePlatform.platformIcon)}"/>
                                                <img src="${phiz:getAddressImage(imageData, pageContext)}"/>
                                                <%--Ссылка на стр. QR-кода для скачивания мобильного приложения для соответствующей платформы--%>
                                                <div class="imgLabel">
                                                    <c:choose>
                                                        <c:when test="${mobilePlatform.downloadFromSBRF}">
                                                            <a onclick="${onclick}" href="#">${mobilePlatform.platformName}</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a onclick="${onclick}" href="#">${mobilePlatform.platformName}</a>
                                                        </c:otherwise>
                                                    </c:choose>
                                               </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <tiles:insert definition="window" flush="false">
                                        <tiles:put name="id" value="${winID}" />
                                    </tiles:insert>
                                </c:if>
                            </c:forEach>
                        </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

<script type="text/javascript">

    $(document).ready(function(){
        $(".hint").hover(
            function(){
                $(this).find(".textHint").css("display", "block");
            },
            function(){
                $(this).find(".textHint").css("display", "none");
            }
        );
    });

    function openQRCode(platformName)
    {
        var QRCODE_FORM_ID = '${winID}';
        win.aditionalData(QRCODE_FORM_ID, {onOpen: function (id)
        {
            var element = document.getElementById(id);
            if (element.dataWasLoaded != undefined && element.dataWasLoaded[platformName] != null ) {
                element.innerHTML = element.dataWasLoaded[platformName];
                return true;
            }

            win.loadAjaxData(QRCODE_FORM_ID, '${qrCodeActionUrl}'+platformName,
                function ()
                {
                    if (element.dataWasLoaded == undefined) element.dataWasLoaded = [];
                    element.dataWasLoaded[platformName] = element.innerHTML;
                    win.open(QRCODE_FORM_ID, true);
                });

            return false;
        }
        });

        win.open('${winID}');
    }

    function cancelMobileApplication(id)
    {
        addField('hidden','cancelId',id);
        callOperation(null, 'button.lock');
        return true;
    }

    function openMobileAppDistrib(url)
        {
            if(url!=""){
                var winparams = "resizable=1,menubar=0,toolbar=0,scrollbars=1";
                var pwin = window.open(url, "mobileApplicationsDistrib", winparams);
                pwin.focus();
            }
        }

</script>
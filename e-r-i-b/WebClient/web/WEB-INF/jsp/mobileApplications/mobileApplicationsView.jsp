<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%--<tiles:importAttribute/>--%>
<html:form action="/private/mobileApplications/view" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="mobileApplications" value="${form.mobileApplications}"/>
    <c:set var="mobilePlatformList" value="${form.mobilePlatformList}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="winID" value="qrCodeWindowID"/>
    <c:set var="qrCodeLink" value="${phiz:calculateActionURL(pageContext,'/private/mobileApplications/qrcode.do')}"/>
    <c:set var="qrCodeActionUrl" value="${qrCodeLink}?mobileApplication="/>
    <c:set var="globalPath" value="${globalUrl}/commonSkin/images"/>

    <tiles:insert definition="list">
        <tiles:put name="pageTitle" type="string">Мобильные приложения</tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Мобильные приложения"/>
                <tiles:put name="data">
                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="image" value="${globalPath}/mobile_applications.jpg"/>
                        <tiles:put name="description">
                            <h3>
                                <bean:message key="label.mobileApplication.view" bundle="commonBundle"/>
                            </h3>
                        </tiles:put>
                    </tiles:insert>

                    <%--Подключенные мобильные приложения--%>
                    <%--Максимальное кол-во отображаемых типов мобильных приложений в ряде--%>
                    <c:set var="mobileAppTypeMaxColCount" value="4"/>

                    <c:set var="rowIndex" value="1"/>
                    <div class="view-mobileApplicationsType">
                        <table>
                            <c:forEach var="mobilePlatform" items="${mobilePlatformList}" varStatus="line" >
                                <c:if test="${mobilePlatform.showInApps}">
                                <c:if test="${line.count gt (rowIndex * mobileAppTypeMaxColCount)}">
                                    <tr>
                                </c:if>
                                    <td>
                                        <div>
                                            <c:choose>
                                                <c:when test="${empty mobilePlatform.platformIcon}">
                                                    <img src="${globalImagePath}/${mobilePlatform.platformName}.jpg" alt="изображение для ${mobilePlatform.platformName} отсутствует" border="0">
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="imageData" value="${phiz:getImageById(mobilePlatform.platformIcon)}"/>
                                                    <img src="${phiz:getAddressImage(imageData, pageContext)}"/>
                                                </c:otherwise>
                                            </c:choose>

                                        </div>
                                        <%--Ссылка на стр. QR-кода для скачивания мобильного приложения для соответствующей платформы--%>
                                        <div class="imgLabel">
                                            <c:choose>
                                                <c:when test="${mobilePlatform.downloadFromSBRF}">
                                                    <a onclick="openMobileAppDistrib('${mobilePlatform.bankURL}');" href="#">${mobilePlatform.platformName}</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a onclick="openQRCode('${mobilePlatform.platformId}');" href="#">${mobilePlatform.platformName}</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <tiles:insert definition="window" flush="false">
                                            <tiles:put name="id" value="${winID}" />
                                        </tiles:insert>
                                    </td>
                                <c:if test="${line.count gt (rowIndex * mobileAppTypeMaxColCount)}">
                                    </tr>
                                    <c:set var="rowIndex" value="${rowIndex + 1}"/>
                                </c:if>
                                </c:if>
                            </c:forEach>
                        </table>
                    </div>
                    <%--Список подключенных мобильных приложений--%>
                    <c:if test="${not empty mobileApplications}">
                        <div class="view-mobileApplications">
                            <table>
                                <tr>
                                    <th><bean:message bundle="commonBundle"key="title.mobileApplication.connect"/></th>
                                    <th class="align-center"><bean:message bundle="commonBundle"key="title.mobileApplication.connectDate"/></th>
                                    <th>&nbsp;</th>
                                </tr>
                                <c:forEach var="mobileApp" items="${mobileApplications}" varStatus="line">
                                    <tr>
                                        <%--Подключение и статус PUSH-уведомлений--%>
                                        <td class="align-left">
                                            <c:forEach var="mobilePlatform" items="${mobilePlatformList}" varStatus="line">
                                                <c:if test="${mobileApp.deviceInfo eq mobilePlatform.platformId}">
                                                    <span><c:out value="${mobilePlatform.platformName}"/></span>
                                                </c:if>
                                            </c:forEach>
                                            <br />
                                            <c:if test="${phiz:impliesOperation('PushSettingsOperation', 'ClientProfilePush')}">
                                                <c:choose>
                                                    <c:when test="${mobileApp.pushSupported}">
                                                        <span class="deviceStatus">PUSH-уведомления подключены</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="deviceStatus">PUSH-уведомления отключены</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </td>
                                        <%--Дата подключения--%>
                                        <td class="align-center">
                                            <c:out value="${phiz:formatDateDependsOnSysDate(mobileApp.creationDate, true, false)}"/>
                                        </td>
                                        <%--Ссылка "отключить" мобильное устройство--%>
                                        <c:if test="${phiz:impliesOperation('MobileApplicationsLockOperation','ShowConnectedMobileDevicesService')}">
                                            <td class="align-right">
                                                <tiles:insert definition="confirmationButton" flush="false">
                                                    <tiles:put name="winId" value="confirmRemoveMobileApplication${mobileApp.id}"/>
                                                    <tiles:put name="title" value="Подтверждение отключения приложения"/>
                                                    <tiles:put name="currentBundle" value="commonBundle"/>
                                                    <tiles:put name="confirmKey" value="button.mobileApplication.lock"/>
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
                            <bean:message key="label.mobileApplication.productVisibleInfo" bundle="commonBundle"/>
                            «<a class="blueGrayLink" href="${phiz:calculateActionURL(pageContext, '/private/userprofile/accountsSystemView')}?field(pageType)=mobile">Настройка видимости продуктов</a>».
                        </h3>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
<script type="text/javascript">
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
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--<tiles:importAttribute/>--%>
<html:form action="/private/socialApplications/view" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="socialApplications" value="${form.socialApplications}"/>
    <c:set var="socialPlatformList" value="${form.socialPlatformList}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="winID" value="qrCodeWindowID"/>
    <c:set var="qrCodeLink" value="${phiz:calculateActionURL(pageContext,'/private/socialApplications/qrcode.do')}"/>
    <c:set var="qrCodeActionUrl" value="${qrCodeLink}?socialApplication="/>
    <c:set var="globalPath" value="${globalUrl}/commonSkin/images"/>

    <tiles:insert definition="list">
        <tiles:put name="pageTitle" type="string">Социальные приложения</tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Социальные приложения"/>
                <tiles:put name="data">
                   <%--Список подключенных социальных приложений--%>
                    <c:if test="${not empty socialApplications}">
                        <div class="view-socialApplications">
                            <table>
                                <c:forEach var="socialApp" items="${socialApplications}" varStatus="line">
                                    <tr>
                                        <td class="align-left logo">
                                            <c:choose>
                                                <c:when test="${fn:toLowerCase(socialApp.deviceInfo) eq 'odnoklas'}">
                                                    <span class="socialBigLogoOdnoklas">&nbsp;</span>
                                                </c:when>
                                                <c:when test="${fn:toLowerCase(socialApp.deviceInfo) eq 'vkontakte'}">
                                                    <span class="socialBigLogoVkontakte">&nbsp;</span>
                                                </c:when>
                                                <c:when test="${fn:toLowerCase(socialApp.deviceInfo) eq 'facebook'}">
                                                    <span class="socialBigLogoFacebook">&nbsp;</span>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td class="align-left">
                                           <span>
                                               <c:forEach var="socialPlatform" items="${socialPlatformList}" varStatus="line">
                                                   <c:if test="${fn:toLowerCase(socialApp.deviceInfo) eq fn:toLowerCase(socialPlatform.platformId)}">
                                                       <span class="deviceInfo"><c:out value="${socialPlatform.platformName}"/></span>
                                                   </c:if>
                                               </c:forEach>
                                           </span>
                                       </td>
                                        <%--Ссылка "отключить" соц. приложение--%>
                                        <c:if test="${phiz:impliesOperation('SocialApplicationsLockOperation','ShowConnectedSocialAppService')}">
                                            <td class="cancelSocialApplication">
                                                <tiles:insert definition="confirmationButton" flush="false">
                                                    <tiles:put name="winId" value="confirmRemoveSocialApplication${socialApp.id}"/>
                                                    <tiles:put name="title" value="Подтверждение отключения приложения"/>
                                                    <tiles:put name="currentBundle" value="commonBundle"/>
                                                    <tiles:put name="confirmKey" value="button.socialapplications.lock"/>
                                                    <tiles:put name="confirmCommandKey" value="button.socialApplication.lock"/>
                                                    <tiles:put name="confirmCommandTextKey" value="button.socialApplication.lock.confirm"/>
                                                    <tiles:put name="message" value="Вы действительно хотите отключить приложение?"/>
                                                    <tiles:put name="afterConfirmFunction">cancelSocialApplication('${socialApp.id}')</tiles:put>
                                                </tiles:insert>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </c:if>

                    <div class="view-socialApplicationInfo">
                        <h3>
                            <bean:message key="label.socialApplication.header3" bundle="commonBundle"/>
                        </h3>
                    </div>
                    <%--Подключенные социального приложения--%>
                    <%--Максимальное кол-во отображаемых типов мобильных приложений в ряде--%>
                    <c:set var="socialAppTypeMaxColCount" value="3"/>

                    <c:set var="rowIndex" value="1"/>
                    <div class="view-socialApplicationsType">
                        <c:forEach var="socialPlatform" items="${socialPlatformList}" varStatus="line" >
                            <c:if test="${socialPlatform.showInApps}">
                                <div>
                                    <c:choose>
                                        <c:when test="${socialPlatform.downloadFromSBRF}">
                                            <c:set var='onclick' value="openSocialAppDistrib('${socialPlatform.bankURL}');"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var='onclick' value="openQRCode('${socialPlatform.platformId}');"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${empty socialPlatform.platformIcon}">
                                            <div class="${socialPlatform.platformId} socialApp" onclick="${onclick}">
                                                <span class="socialLogo">&nbsp;</span>
                                                <span class="socialText">Приложение <span class="socialType">${socialPlatform.platformName}</span></span>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="imageData" value="${phiz:getImageById(socialPlatform.platformIcon)}"/>
                                            <img src="${phiz:getAddressImage(imageData, pageContext)}"/>
                                            <%--Ссылка на стр. QR-кода для скачивания социального приложения для соответствующей платформы--%>
                                            <div class="imgLabel">
                                                <c:choose>
                                                    <c:when test="${socialPlatform.downloadFromSBRF}">
                                                        <a onclick="${onclick}" href="#">${socialPlatform.platformName}</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a onclick="${onclick}" href="#">${socialPlatform.platformName}</a>
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


                    <div class="view-socialApplicationInfo">
                        <h3>
                            <bean:message key="label.socialApplication.productVisibleInfo" bundle="commonBundle"/>
                            «<a class="blueGrayLink" href="${phiz:calculateActionURL(pageContext, '/private/userprofile/accountsSystemView')}?field(pageType)=social">Настройка видимости продуктов</a>».
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

    function cancelSocialApplication(id)
    {
        addField('hidden','cancelId',id);
        callOperation(null, 'button.lock');
        return true;
    }

    function openSocialAppDistrib(url)
        {
            if(url!=""){
                var winparams = "resizable=1,menubar=0,toolbar=0,scrollbars=1";
                var pwin = window.open(url, "socialApplicationsDistrib", winparams);
                pwin.focus();
            }
        }

</script>
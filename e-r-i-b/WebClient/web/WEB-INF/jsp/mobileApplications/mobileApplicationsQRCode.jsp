<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html:form action="/private/mobileApplications/qrcode" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:set var="platformName" value="${form.mobilePlatformName}"/>
    <c:set var="qrCodeImageName" value="${form.mobileApplicationQRImgName}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    
    <h2><bean:message bundle="commonBundle" key="label.SBOL.mobileApp"/></h2>
    <c:set var="scanQRCodeText"><bean:message bundle="commonBundle" key="text.QRCode.scan"/></c:set>
    <div class="view-mobileApplicationsQRCode">
        <div class="align-left">
            ${fn:replace(scanQRCodeText, '%platformName%', platformName)}
        </div>
        <div class="mobileApplicationsQRCodeImg">
            <img src="${globalImagePath}/${qrCodeImageName}" alt="изображение для QR-кода ${platformName} отсутствует" width="200px" height="200px" border="0">
        </div>

        <div class="mobileApplicationsQRInfo">
            QR-код для загрузки приложения для ${platformName} <br> 
            или пройдите по ссылке
        </div>

        <div>
            <a onclick="openMobileAppDistrib();" href="#">${form.mobileApplicationDistribURL}</a>
        </div>
        <div class="mobileApplicationsQRClose">
            <a onclick="win.close(this);" href="#">Закрыть</a>
        </div>
    </div>
    <script type="text/javascript">
        function openMobileAppDistrib(event)
        {
            <c:set var="url" value="${form.mobileApplicationDistribURL}"/>
            <c:if test="${not empty url}">
                var winparams = "resizable=1,menubar=0,toolbar=0,scrollbars=1";
                var pwin = openWindow(event, "${url}", "mobileApplicationsDistrib", winparams);
                pwin.focus();
            </c:if>
        }
    </script>
</html:form>
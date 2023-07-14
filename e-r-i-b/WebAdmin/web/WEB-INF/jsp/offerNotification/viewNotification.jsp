<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ page import="com.rssl.common.forms.TemporalDocumentException" %>
<%@ page import="com.rssl.phizic.web.skins.SkinUrlValidator" %>
<%@ page import="com.rssl.phizic.web.util.SkinHelper" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>


<html:form action="/offers/notification/view">
    <tiles:importAttribute/>
    <%
        String skinUrl =  request.getParameter("skinUrl");
        SkinUrlValidator urlValidator = new SkinUrlValidator();
        String newSkinUrl = "";
        try
        {
            if (urlValidator.validate(skinUrl))
                newSkinUrl+="http://";
        }
        catch (TemporalDocumentException e)
        {
            newSkinUrl+="http://";
        }
        newSkinUrl = SkinHelper.updateSkinPath(newSkinUrl + skinUrl);
    %>
    <c:set var="tempSkinUrl"><%=newSkinUrl%></c:set>
    <c:set var="imagePath" value="${tempSkinUrl}/images"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>

    <head>
        <title><bean:message key="page.header" bundle="offerNotificationBundle"/></title>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
        <link rel="icon" type="image/x-icon" href="${tempSkinUrl}/images/favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/systemAll.css"/>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/style.css"/>
        <link rel="stylesheet" type="text/css" href="${tempSkinUrl}/style.css"/>
        <script type="text/javascript">
            document.webRoot = '/PhizIC';
            var skinUrl = '${tempSkinUrl}';
            var globalUrl = '${globalUrl}';
        </script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
        <!--[if IE 6]>
        <link type="text/css" rel="stylesheet" href="${tempSkinUrl}/ie.css"/>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/ie.css"/>
        <![endif]-->
    </head>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <body>
        <div class="bunnerView">
            <div id="content">
                <div id="workspace">
                    <c:set var="notification" value="${form.personalOfferNotification}" scope="page"/>
                    <c:set var="currentId" value="${notification.id}" scope="page"/>
                        <div id="notification${notification.id}" class="bunnerArea">
                            <div class="bunnerLists">
                                <div class="grayRTC r-top-center"></div>
                                <div class="grayRC r-content">
                                    <div class="bunnerContent">
                                        <c:set var="imageOrder" value="First"/>
                                        <c:set var="top" value=""/>
                                        <c:forEach var="area" items="${notification.areas}">
                                            <c:if test="${area.areaName == 'title' && not empty notification.title}">
                                                <c:set var="imageOrder" value=""/>
                                                <c:set var="offerTitle" value="${phiz:processBBCode(notification.title)}"/>
                                                <c:choose>
                                                    <c:when test="${notification.productType == 'LOAN'}">
                                                        <div class="bunnerTitle" >${phiz:getLoanOfferText(offerTitle, null)}</div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="bunnerTitle" >${phiz:getLoanCardOfferText(offerTitle, null)}</div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                            <c:if test="${area.areaName == 'text' && not empty notification.text}">
                                                <c:set var="imageOrder" value=""/>
                                                <c:set var="offerText" value="${phiz:processBBCode(notification.text)}"/>
                                                <c:choose>
                                                    <c:when test="${notification.productType == 'LOAN'}">
                                                        <div class="bunnerText" >${phiz:getLoanOfferText(offerText, null)}</div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="bunnerText" >${phiz:getLoanCardOfferText(offerText, null)}</div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                            <c:if test="${area.areaName == 'image' && notification.imageId!= null}">
                                                <c:set var="imageData" value="${phiz:getImageById(notification.imageId)}"/>
                                                <c:set var="imageUrl" value="${imageData.linkURL}"/>
                                                <c:set var="imagePath" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                                <div class="bunnerImage${imageOrder}">
                                                    <c:set var="top" value="${imageOrder}"/>
                                                    <c:choose>
                                                        <c:when test="${imageUrl != null}">
                                                            <a href="#">
                                                                <img id="notificationImg${notification.id}" src="${imagePath}" />
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img id="notificationImg${notification.id}" src="${imagePath}"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </c:if>
                                            <c:if test="${area.areaName == 'buttons' && not empty notification.buttons}">
                                                <c:set var="imageOrder" value=""/>
                                                <div class="buttons-area">
                                                    <c:forEach  var="button" items="${notification.buttons}">
                                                        <c:if test="${button.show == 'true'}">
                                                            <c:set var="buttonImagePath" value=""/>
                                                            <c:if test="${not empty button.imageId}">
                                                                <c:set var="imageData" value="${phiz:getImageById(button.imageId)}"/>
                                                                <c:set var="buttonImagePath" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                                            </c:if>

                                                            <c:set var="buttonUrl" value="${button.url}"/>
                                                            <c:set var="buttonTitle" value="${phiz:processBBCode(button.title)}"/>

                                                            <c:if test="${empty buttonImagePath  && button.title != null}">
                                                                <div class="clientButton">
                                                                    <a href="#">
                                                                        <div class="buttonGreen">
                                                                            <div class="text">
                                                                                <span>${buttonTitle}</span>
                                                                            </div>
                                                                        </div>
                                                                    </a>
                                                                    <div class="clear"></div>
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${not empty buttonImagePath}">
                                                                <div class="clientButton" >
                                                                    <a href="#">
                                                                        <div class="buttonGreen">
                                                                            <div class="text" style="background: url('${buttonImagePath}') no-repeat;">
                                                                                <span>${buttonTitle}</span>
                                                                            </div>
                                                                        </div>
                                                                    </a>
                                                                    <div class="clear"></div>
                                                                </div>
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                        <div id="notificationContent${notification.id}" class="advertisingBlockContent opacity50" style="display:none;">
                                            <img src="${imgPath}/ajaxLoader.gif" alt="Loading..." title="Loading..." class="abstractLoader"/>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="navigation">
                                <div class="navigationGray"></div>
                                <div class="navigationGreen"></div>
                                <div class="navigationGray"></div>
                            </div>
                        </div>
                </div>
                <div class="clear"></div>
                <span class="italicInfo"><bean:message key="label.preview.warnings" bundle="offerNotificationBundle"/></span>
            </div>
        </div>
    </body>

</html:form>
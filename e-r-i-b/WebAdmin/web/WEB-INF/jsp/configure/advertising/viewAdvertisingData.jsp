<%@ page import="com.rssl.common.forms.TemporalDocumentException" %>
<%@ page import="com.rssl.phizic.web.skins.SkinUrlValidator" %>
<%@ page import="com.rssl.phizic.web.util.SkinHelper" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
    <title>Сбербанк Онлайн</title>
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
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/select.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/validators.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Templates.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/PaymentsFormHelp.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/commandButton.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/builder.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/iframerequest.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/cookies.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/layout.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/windows.js"></script>


    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.datePicker.js"></script>
    <!--[if IE 6]>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/iepngfix_tilebg.js"></script>
        <link type="text/css" rel="stylesheet" href="${tempSkinUrl}/ie.css"/>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/ie.css"/>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.bgiframe.min.js"></script>
    <![endif]-->
</head>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="bunner" value="${form.advertisingBlock}"/>
<body>
    <div class="bunnerView">
        <div id="content">
            <div id="workspace">
                <div id="bunnerAreaView" class="bunnerArea">
                    <div class="grayRTL r-top-left">
                        <div class="grayRTR r-top-right">
                            <div class="grayRTC r-top-center"></div>
                        </div>
                    </div>
                    <div class="grayRCL r-center-left">
                        <div class="grayRCR r-center-right">
                            <div class="grayRC r-content">
                                <c:set var="imageOrder" value="First"/>
                                <c:forEach var="bunnerArea" items="${bunner.areas}">
                                    <c:if test="${bunnerArea.areaName == 'title' && not empty bunner.title}">
                                        <c:set var="imageOrder" value=""/>
                                        <div class="bunnerTitle" >${phiz:processBBCode(bunner.title)}</div>
                                    </c:if>
                                    <c:if test="${bunnerArea.areaName == 'text' && not empty bunner.text}">
                                        <c:set var="imageOrder" value=""/>
                                        <div class="bunnerText" >${phiz:processBBCode(bunner.text)}</div>
                                    </c:if>
                                    <c:if test="${bunnerArea.areaName == 'image' && bunner.image != null}">
                                        <c:set var="imageData" value="${bunner.image}"/>
                                        <c:set var="imageUrl" value="${imageData.linkURL}"/>
                                        <c:set var="imagePath" value="${phiz:getAddressImageConsiderMultiBlock(imageData, pageContext)}"/>
                                        <c:set var ="externalURL" value="${fn:indexOf(imageUrl, 'PhizIC') < 0}" scope="page"/>
                                        <div class="bunnerImage${imageOrder}">
                                            <c:choose>
                                                <c:when test="${imageUrl != null}">
                                                    <a href="#"><img src="${imagePath}" /></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${imagePath}"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:if>
                                    <c:if test="${bunnerArea.areaName == 'buttons' && not empty bunner.buttons}">
                                        <c:set var="imageOrder" value=""/>
                                        <div class="buttons-area">
                                            <c:forEach  var="bunnerButton" items="${bunner.buttons}">
                                                <c:if test="${bunnerButton.show == 'true' }">
                                                    <c:set var ="externalURL" value="${fn:indexOf(bunnerButton.url, 'PhizIC') < 0}" scope="page"/>
                                                    <c:set var="buttonImagePath" value=""/>
                                                    <c:if test="${bunnerButton.image != null}">
                                                        <c:set var="imageBunnerButtonData" value="${bunnerButton.image}"/>
                                                        <c:set var="buttonImagePath" value="${phiz:getAddressImageConsiderMultiBlock(imageBunnerButtonData, pageContext)}"/>
                                                    </c:if>
                                                    <c:if test="${empty buttonImagePath && bunnerButton.title != null}">
                                                        <div class="clientButton">
                                                            <a href="#">
                                                                <div class="buttonGreen">
                                                                    <div class="left-corner"> </div>
                                                                    <div class="text">
                                                                        <span>${phiz:processBBCode(bunnerButton.title)}</span>
                                                                    </div>
                                                                    <div class="right-corner"> </div>
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
                                                                        <span>${phiz:processBBCode(bunnerButton.title)}</span>
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

                                <div class="clear"></div>
                                <div class="navigation">
                                    <div class="navigationGray"></div>
                                    <div class="navigationGreen"></div>
                                    <div class="navigationGray"></div>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                    <div class="grayRBL r-bottom-left">
                        <div class="grayRBR r-bottom-right">
                            <div class="grayRBC r-bottom-center"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="clear"></div>
            <span class="italicInfo">*На данной странице отображаются только сохраненные параметры баннера.</span>
        </div>
    </div>
</body>




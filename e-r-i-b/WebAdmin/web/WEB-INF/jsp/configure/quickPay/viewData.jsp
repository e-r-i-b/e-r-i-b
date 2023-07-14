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
    <title><bean:message bundle="commonBundle" key="title.SBOL.application"/></title>
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
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>

    <!--[if IE 6]>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/iepngfix_tilebg.js"></script>
        <link type="text/css" rel="stylesheet" href="${tempSkinUrl}/ie.css"/>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/ie.css"/>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.bgiframe.min.js"></script>
    <![endif]-->
</head>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="panel" value="${form.panel}"/>
<body>

<c:set var ="blocks" value="${panel.panelBlocks}"/>

<c:if test="${phiz:size(blocks) > 0}">
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.ifixpng.js"></script>
    <script type="text/javascript">
       
        var MIN_WIDTH = 600;
        function initMobileBanner()
        {
            var width = document.body.clientWidth;
            if (width < MIN_WIDTH)
            {
                $('#mobileBannerArrow').css('display','');
                $('#mobileBanner .greenBannerArea').css('cursor','pointer');
                $('#mobileBanner .greenBannerArea').bind('click', openMobileBanner);
                openMobileBanner();
            }
            
            var bunners = $('#mobileBanner .operators');
            var greenArea = $('#mobileBanner .greenBannerArea');
            if (bunners.height() < greenArea.height())
            {
                var h = $('#mobileBanner .operators .operator')[0].offsetHeight;
                var paddTop = (greenArea.height() - h) / 2;
                bunners.css("height", greenArea.height() - paddTop);
                bunners.css("padding-top", paddTop);
            }
        }

        function openMobileBanner()
        {
            var w = $('#mobileBanner .box').width();
            var l = parseInt($('#mobileBanner').css('left'));
            if (l != 0)
            {
                $('#mobileBanner').css('left','0px');
                $('#mobileBannerArrow').css('background-position','left');
            }
            else
            {
                $('#mobileBanner').css('left','-'+w+'px');
                $('#mobileBannerArrow').css('background-position','right');
            }
        }

        $(document).ready(function()
        {
            initMobileBanner();
            $('#mobileBanner .greenBannerArea').ifixpng();
        });
    </script>

    <c:set var="commonImgPath" value="${globalUrl}/commonSkin/images"/>

    <c:set var="providerBlocks">
        <c:forEach items="${blocks}" var="block">
            <c:if test="${block.show}">
                <c:set var="providerId"    value="${provider.providerId}"/>
                <c:set var="providerAlias" value="${block.providerName}"/>
                <c:set var="showProviderName" value="${block.showName}"/>
                <c:set var="imageData"       value="${block.image}"/>
                <c:set var="height"        value="${showProviderName ? '40px' : '55px'}"/>
                <c:choose>
                    <c:when test="${not empty imageData}">
                        <c:set var="imageURL" value="${phiz:getAddressImageConsiderMultiBlock(imageData, pageContext)}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="imageURL" value="${globalUrl}/images/logotips/IQWave/IQWave-other.jpg"/>
                    </c:otherwise>
                </c:choose>
                <div class="operator dot">                     
                    <c:if test="${not empty block.providerFieldName}">
                        <c:set var="providerAlias" value="${phiz:getCutPhoneNumberForQPP()}"/>
                    </c:if>
                    <a href="#">
                        <div class="operatorIcon" <c:if test="${empty providerAlias && showProviderName}">style="margin: -1px -7px 9px -7px;"</c:if>><img src="${imageURL}" alt='<c:out value="${providerAlias}"/>' height="${height}" width="55px"></div>
                        <c:if test="${showProviderName}">
                        <div class="operatorTitle greenTitle">
                            <span class="nowrapWhiteSpace">
                                <c:out value="${providerAlias}"/>
                            </span>
                        </div>
                        </c:if>
                    </a>
                </div>
            </c:if>
        </c:forEach>
    </c:set>
        <c:if test="${not empty providerBlocks}">
        <div id="mobileBanner">
            <div class="box">
                <div class="operators">
                   ${providerBlocks}
                </div>
                <div class="mobileBannerBR">
                    <div class="mobileBannerBC"></div>
                </div>
            </div>
            <div class="greenBannerArea">
               <div id="mobileBannerArrow" style="display: none"></div>
           </div>
           <div class="clear"></div>
        </div>
    </c:if>
</c:if>
</body>




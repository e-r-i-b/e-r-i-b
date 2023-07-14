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
<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="operationsHistoryLabel"><bean:message bundle="paymentsBundle" key="label.history.title"/></c:set>
<head>
    <title><bean:message bundle="commonBundle" key="title.SBOL.application"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
    <meta name="format-detection" content="telephone=no">
    <link rel="icon" type="image/x-icon" href="${tempSkinUrl}/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/systemAll.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/style.css"/>
    <link rel="stylesheet" type="text/css" href="${tempSkinUrl}/style.css"/>
    <script type="text/javascript">
        document.webRoot = '/PhizIC';
        var skinUrl   = '${tempSkinUrl}';
        var globalUrl = '${globalUrl}';
    </script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/select.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/customPlaceholder.js"></script>
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

    <!--[if IE 8]>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/html5.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/PIE.js"></script>
    <![endif]-->

    <!--[if IE 6]>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/iepngfix_tilebg.js"></script>
            <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/ie.css"/>
        <link type="text/css" rel="stylesheet" href="${tempSkinUrl}/ie.css"/>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.bgiframe.min.js"></script>
    <![endif]-->
</head>


<body>
<div class="fonContainer">

<form name="ShowAccountsForm" method="post" action="/PhizIC/private/accounts.do">
    <div class="pageBackground">
        <div class="pageContent">
            <div id="header">
                <div class="hdrContainer">
                    <div class="NewHeader">
                        <div class="Logo">
                            <a class="logoImage logoImageText" href="">
                                <img src="${imagePath}/logoHeader.png" alt=""/>
                            </a>
                        </div>
                        <div class="PhoneNambers">
                            <span>+7 (495) <span>500-55-50</span><span style="display:none;">_</span></span><br />
                            <span>8 (800) <span>555-55-50</span><span style="display:none;">_</span></span>
                        </div>
                    </div>

                    <div class="topLineContainer">
                        <div class="UserInfo">
                            <a class="lettersForUser" href="#" title="��������� � ������ ������">
                                <div class="mailBlock"></div>
                            </a>
                            <table cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                    <td>
                                        <ul id="previousEnter">
                                            <li id="previousEnterInfo">
                                                <span class="word-wrap" style="overflow: visible;"><span>������ �. �.</span></span>
                                                <div class="clear"></div>
                                                <div class="clientInfo" style="margin-left: -34.5px;">
                                                    <div class="clientInfoItemBorder"></div>
                                                    <div class="clientInfoItem"></div>
                                                    <ul>
                                                        <li>
                                                            ��������� ���������:<br/>
                                                            IP:<b> 127.0.0.1</b>
                                                            <b>������� � 13:46</b>
                                                        </li>
                                                        <li class="clientRegion">
                                                            ������������� �����, � ������� �� ��������������:<br>
                                                            <b>����.����� �1556/071</b>
                                                            <span id="regionNameHeader" style="display:none"></span>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </li>
                                        </ul>
                                    </td>
                                    <td>
                                        <a class="saveExit" href="#" title="���������� ����� �� �������">
                                            <span>�����</span>
                                            <div id="exit" alt="���������� ����� �� �������"></div>
                                        </a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div id="navigation">
                        <div id="menu">
                            <table class="mainMenu" cellspacing="0" cellpadding="0" border="0">
                                <tr>
                                    <td class="active firstPositionActive firstPosition">
                                        <span class="menuItems">
                                            <a href="">�������</a>
                                        </span>
                                    </td>
                                    <td>
                                        <span class="menuItems">
                                            <a href="">������� � ��������</a>
                                        </span>
                                    </td>
                                    <td>
                                        <span class="menuItems">
                                            <a href="">������ � �����</a>
                                        </span>
                                    </td>
                                    <td>
                                        <span class="menuItems">
                                            <a href="">�����</a>
                                        </span>
                                    </td>
                                    <td>
                                        <span class="menuItems">
                                            <a href="">�������</a>
                                        </span>
                                    </td>
                                    <td id="other">
                                        <span class="menuItems">
                                            <a href="" onclick="hideOrShowDDM(); return false;"><span>������</span></a>
                                        </span>
                                        <div id="dropDownMenu" style="display: none;">
                                            <table>
                                                <tr>
                                                    <td>
                                                        <span class="menuItems">
                                                            <a href="">����� ����</a>
                                                        </span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <span class="menuItems">
                                                            <a href="">�����������</a>
                                                        </span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <span class="menuItems">
                                                            <a href="">������������� �����</a>
                                                        </span>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                    <td style="width: 50px;" class=" lastPosition">
                                        <span class="menuItems">
                                            <a href="#">
                                                <div class="options"></div>
                                            </a>
                                        </span>
                                    </td>
                                </tr>
                            </table>
                        </div>

                        <script type="text/javascript">

                            var isClosed = true;
                            var previousClassName;

                            function hideOrShowDDM()
                            {
                                hideOrShow('dropDownMenu');
                                if (false)
                                {
                                    var li = document.getElementById('other');
                                    if (isClosed)
                                    {
                                        previousClassName = li.className;
                                        li.className = 'hover';
                                    }
                                    else
                                    {
                                        li.className = previousClassName;
                                    }
                                }

                                isClosed = !isClosed;
                            }

                        </script>
                        <div class="clear"></div>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>



            <div id="wrapper">
            <div id="content" class="">

            <div id="workspace" class="topSpace">

                <div id="loading" style="left:-3300px;">
                    <div id="loadingImg"><img src="${tempSkinUrl}/images/ajax-loader64.gif"/></div>
                    <span>����������, ���������,<br/> ��� ������ ��������������.</span>
                </div>


                <script type="text/javascript">

                    var errorHash = {};

                    var messageHash = {};

                    function addFieldErrorsMessages(beforeText, errors, divId, addFunction, beforeTextCondition)
                    {
                        var error = '';
                        var fieldError = '';
                        var isNeedBeforeText = false;

                        for (var field in errors)
                        {
                            if (payInput.fieldError(field, errors[field]))
                            {
                                fieldError += '<br/>\n' + payInput.getFieldLabel(field);
                                if (beforeTextCondition == null)
                                {
                                    isNeedBeforeText = true;
                                }
                                else
                                {
                                    isNeedBeforeText = beforeTextCondition(errors[field]);
                                }
                            }
                            else
                            {
                                if (error.length > 0)
                                    error += '<br/>\n';
                                error += errors[field];
                            }
                        }

                        if (fieldError != '' && isNeedBeforeText) addFunction(beforeText + fieldError, divId);

                        // ���� ���� �� ���� ������� ������ ������� ����� ������
                        if (error != '') addFunction(error, divId);
                    }

                    function getFieldError(errors, divId, deleteMsgIfExist)
                    {
                        // ����� �������������� ������������ ����� � ������� ��������� ������
                        var TEXT_BEFORE_FIELD_ERROR = "��������� ���� ����� ���� ��������� �����������. ����������, ������� ��������� � ����, ���������� ������� ������:";

                        addFieldErrorsMessages(TEXT_BEFORE_FIELD_ERROR, errors, divId, function(error, div)
                        {
                            addError(error, div, deleteMsgIfExist);
                        });
                    }

                    function getFieldMessage(messages, divId)
                    {
                        // �����, �������������� ������������ ����� ������� ���� ��������
                        var TEXT_BEFORE_FIELD_ERROR = "��������, ��������� ���� ����� ���� �����������. ����������, �������� "
                                + "�������� �� ���������� ������� ������ ���� � �������������� ������, ���� ��� �� ���������� "
                                + "����� ��������.";

                        var beforeTextCondition = function(message)
                        {
                            return !(message == '' || message == null);
                        };

                        addFieldErrorsMessages(TEXT_BEFORE_FIELD_ERROR, messages, divId, function(error, div)
                        {
                            addMessage(error, div);
                        }, beforeTextCondition);
                    }

                    // ���������� ����� �������� ���� ��������� ��������� ����� JS �� ����� �������� ����
                    doOnLoad(function ()
                    {
                        getFieldError(errorHash);
                        getFieldMessage(messageHash);
                    });

                </script>


                <div id="warnings" style="display: none">
                    <div class="workspace-box orange">
                        <div class="orangeRT r-top">
                            <div class="orangeRTL r-top-left">
                                <div class="orangeRTR r-top-right">
                                    <div class="orangeRTC r-top-center"></div>
                                </div>
                            </div>
                        </div>
                        <div class="orangeRCL r-center-left">
                            <div class="orangeRCR r-center-right">
                                <div class="orangeRC r-content">
                                    <div class="infoMessage">
                                        <div class="messageContainer"></div>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </div>
                        <div class="orangeRBL r-bottom-left">
                            <div class="orangeRBR r-bottom-right">
                                <div class="orangeRBC r-bottom-center"></div>
                            </div>
                        </div>
                    </div>
                </div>


                <div id="errors" style="display: none">
                    <div class="workspace-box red">
                        <div class="redRT r-top">
                            <div class="redRTL r-top-left">
                                <div class="redRTR r-top-right">
                                    <div class="redRTC r-top-center"></div>
                                </div>
                            </div>
                        </div>
                        <div class="redRCL r-center-left">
                            <div class="redRCR r-center-right">
                                <div class="redRC r-content">
                                    <div class="infoMessage">
                                        <span class='text-red bold'>��������!</span>
                                        <div class="messageContainer"></div>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </div>
                        <div class="redRBL r-bottom-left">
                            <div class="redRBR r-bottom-right">
                                <div class="redRBC r-bottom-center"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="workspace-box">
                    <div class="RT r-top">
                        <div class="RTL r-top-left">
                            <div class="RTR r-top-right">
                                <div class="RTC r-top-center">
                                    <div style="float: left;" class="Title">
                                        <span>
                                            <a href="">�����</a>
                                        </span>
                                        <div class="lightness"></div>
                                    </div>
                                    <div class="CBT">
                                        <a title="� ������" href="" class="blueGrayLink productOnMainHeaderLink">��� �����</a>
                                        <a title="���������" class="productSettingsImg" href="">
                                            <span title="���������" class="blueGrayLink">
                                                ���������
                                            </span>
                                        </a>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="RCL r-center-left">
                        <div class="RCR r-center-right">
                            <div class="RC r-content">
                                <!-- ���������� �� ����� -->
                                <script type="text/javascript">
                                    function showFullInfo(url)
                                    {
                                        window.location = url;
                                    }
                                </script>
                                <div class="forProductBorder ">
                                    <div class="productCover activeProduct">
                                        <div class="pruductImg ">
                                            <a><img border="0" onerror="onImgError(this)" alt="Visa Classic" src="${imagePath}/cards_type/icon_cards_vc64.gif"></a>
                                        </div>
                                        <div class="all-about-product">
                                           <div class="productTitle">
                                               <div class="productTitleText">
                                                   <table width="100%">
                                                       <tbody><tr>
                                                           <td class="alignTop">
                                                               <div class="productName">
                                                                   <div class="titleName">
                                                                       <div class="description">&nbsp;</div>
                                                                       <div class="clear"></div>
                                                                       <span title="Visa Classic" class="relative titleBlock" style="width: 118px;">
                                                                           <a onclick="return false;" href=""><span class="mainProductTitle">Visa Classic</span></a>
                                                                           <div class="lightness"></div>
                                                                       </span>&nbsp;
                                                                   </div>
                                                               </div>
                                                           </td>
                                                           <td class="productButtonsAndOperations">
                                                                <table class="floatRight">
                                                                    <tbody><tr>
                                                                        <td>
                                                                            <div class="productAmount">
                                                                                <div class="productCenter">
                                                                                    <span class="overallAmount nowrap">1 078 838 352,00 ���.</span>
                                                                                    <br>
                                                                                </div>
                                                                            </div>
                                                                        </td>
                                                                        <td class="alignMiddle">
                                                                            <div class="productRight">
                                                                                <span class="prodStatus">
                                                                                    <div onclick="cancelBubbling(event);" id="listOfOperation0_parent" class="listOfOperation productOperation" style="z-index: 0;">
                                                                                        <div style="display: none; z-index: 0;" class="moreListOfOperation" id="listOfOperation0">
                                                                                            <div class="workspace-box hoar">
                                                                                                <div class="hoarRT r-top ">
                                                                                                    <div class="hoarRTL r-top-left">
                                                                                                        <div class="hoarRTR r-top-right">
                                                                                                            <div class="hoarRTC r-top-center">
                                                                                                                <div class="clear"></div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                                <div class="hoarRCL r-center-left">
                                                                                                    <div class="hoarRCR r-center-right">
                                                                                                        <div class="hoarRC r-content">
                                                                                                            <div>
                                                                                                                <table cellspacing="0" cellpadding="0" class="productOperationList">
                                                                                                                    <tbody><tr class="opHoverFirst"><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">��������</a></td></tr>
                                                                                                                    <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">���������</a></td></tr>
                                                                                                                    <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="" class="operationSeparate">��������� ����� ������ ������� � �������</a></td></tr>
                                                                                                                    <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">��������� �����������</a></td></tr>
                                                                                                                    <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">��������� �������� ����</a></td></tr>
                                                                                                                    <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">���������� �����</a></td></tr>
                                                                                                                    <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">�������� ������</a></td></tr>
                                                                                                                    <tr class="opHoverLast"><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="" class="operationSeparate block">�������������</a></td></tr>
                                                                                                                </tbody></table>
                                                                                                            </div>
                                                                                                            <div class="clear"></div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                                <div class="hoarRBL r-bottom-left">
                                                                                                    <div class="hoarRBR r-bottom-right">
                                                                                                        <div class="hoarRBC r-bottom-center"></div>
                                                                                                    </div>
                                                                                                 </div>
                                                                                            </div>
                                                                                        </div>

                                                                                        <div class="buttonSelect productListOperation">
                                                                                            <div class="buttonSelectLeft"></div>
                                                                                            <div class="buttonSelectCenter">��������</div>
                                                                                            <div class="buttonSelectRight"></div>
                                                                                            <div class="clear"></div>
                                                                                        </div>
                                                                                    </div>
                                                                                </span>
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                </tbody></table>
                                                           </td>
                                                       </tr>
                                                   </tbody></table>
                                                   <div class="productNumberBlock">
                                                        <div class="accountNumber decoration-none">8741 17** **** 7726</div>
                                                   </div>
                                               </div>
                                            </div>
                                            <div class="productCenterContainer ">
                                                <div class="productCDL">
                                                    <div class="productLeft"></div>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                            <div class="clear"></div>
                                            <div class="productBottom">
                                                <div style="display: none" class="simpleTable" id="card_287">
                                                    <div class="grid">
                                                        <img class="abstractLoader" title="Loading..." alt="Loading..." src="${tempSkinUrl}/ajaxLoader.gif">
                                                    </div>
                                                </div>
                                                <a rel="������ ��������" class="hide-text text-gray hideable-element">�������� ��������</a>
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                                <div class="clear"></div>
                                <!-- ���������� �� ����� -->
                            <script type="text/javascript">
                                function showFullInfo(url)
                                {
                                    window.location = url;
                                }
                            </script>
                            <div class="forProductBorder ">
                                <div class="productCover activeProduct">
                                    <div class="pruductImg ">
                                        <a><img border="0" onerror="onImgError(this)" alt="Visa Classic" src="${imagePath}/cards_type/icon_cards_vc64.gif"></a>
                                    </div>
                                    <div class="all-about-product">
                                       <div class="productTitle">
                                           <div class="productTitleText  ">
                                               <table width="100%">
                                                   <tbody><tr>
                                                       <td class="alignTop">
                                                           <div class="productName">
                                                               <div class="titleName">
                                                                   <div class="description">&nbsp;</div>
                                                                   <div class="clear"></div>
                                                                   <span title="Visa Classic" class="relative titleBlock" style="width: 118px;">
                                                                       <a onclick="return false;" href=""><span class="mainProductTitle">Visa Classic</span></a>
                                                                       <div class="lightness"></div>
                                                                   </span>&nbsp;
                                                               </div>
                                                           </div>
                                                       </td>
                                                       <td class="productButtonsAndOperations">
                                                            <table class="floatRight">
                                                                <tbody><tr>
                                                                    <td>
                                                                        <div class="productAmount">
                                                                            <div class="productCenter">
                                                                                <span class="overallAmount nowrap">1 078 838 352,00 ���.</span>
                                                                                <br>
                                                                            </div>
                                                                        </div>
                                                                    </td>
                                                                    <td class="alignMiddle">
                                                                        <div class="productRight">
                                                                            <span class="prodStatus">
                                                                                <div onclick="cancelBubbling(event);" id="listOfOperation0_parent" class="listOfOperation productOperation" style="z-index: 0;">
                                                                                    <div style="display: none; z-index: 0;" class="moreListOfOperation" id="listOfOperation0">
                                                                                        <div class="workspace-box hoar">
                                                                                            <div class="hoarRT r-top ">
                                                                                                <div class="hoarRTL r-top-left">
                                                                                                    <div class="hoarRTR r-top-right">
                                                                                                        <div class="hoarRTC r-top-center">
                                                                                                            <div class="clear"></div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="hoarRCL r-center-left">
                                                                                                <div class="hoarRCR r-center-right">
                                                                                                    <div class="hoarRC r-content">
                                                                                                        <div>
                                                                                                            <table cellspacing="0" cellpadding="0" class="productOperationList">
                                                                                                                <tbody><tr class="opHoverFirst"><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">��������</a></td></tr>
                                                                                                                <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">���������</a></td></tr>
                                                                                                                <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="" class="operationSeparate">��������� ����� ������ ������� � �������</a></td></tr>
                                                                                                                <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">��������� �����������</a></td></tr>
                                                                                                                <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">��������� �������� ����</a></td></tr>
                                                                                                                <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">���������� �����</a></td></tr>
                                                                                                                <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">�������� ������</a></td></tr>
                                                                                                                <tr class="opHoverLast"><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="" class="operationSeparate block">�������������</a></td></tr>
                                                                                                            </tbody></table>
                                                                                                        </div>
                                                                                                        <div class="clear"></div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="hoarRBL r-bottom-left">
                                                                                                <div class="hoarRBR r-bottom-right">
                                                                                                    <div class="hoarRBC r-bottom-center"></div>
                                                                                                </div>
                                                                                             </div>
                                                                                        </div>
                                                                                    </div>

                                                                                    <div class="buttonSelect productListOperation">
                                                                                        <div class="buttonSelectLeft"></div>
                                                                                        <div class="buttonSelectCenter">��������</div>
                                                                                        <div class="buttonSelectRight"></div>
                                                                                        <div class="clear"></div>
                                                                                    </div>
                                                                                </div>
                                                                            </span>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            </tbody></table>
                                                       </td>
                                                   </tr>
                                               </tbody></table>
                                               <div class="productNumberBlock">
                                                    <div class="accountNumber decoration-none">8741 17** **** 7726</div>
                                               </div>
                                           </div>
                                        </div>
                                        <div class="productCenterContainer ">
                                            <div class="productCDL">
                                                <div class="productLeft"></div>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                        <div class="clear"></div>
                                        <div class="productBottom">
                                            <div style="display: none" class="simpleTable" id="card_287">
                                                <div class="grid">
                                                    <img class="abstractLoader" title="Loading..." alt="Loading..." src="${tempSkinUrl}/ajaxLoader.gif">
                                                </div>
                                            </div>
                                            <a rel="������ ��������" class="hide-text text-gray hideable-element">�������� ��������</a>
                                        </div>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                            <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                    <div class="RBL r-bottom-left">
                        <div class="RBR r-bottom-right">
                            <div class="RBC r-bottom-center"></div>
                        </div>
                    </div>
                </div>

                <div class="workspace-box">
                    <div class="RT r-top">
                        <div class="RTL r-top-left">
                            <div class="RTR r-top-right">
                                <div class="RTC r-top-center">
                                    <div style="float: left;" class="Title">
                                        <span>
                                            <a href="">������</a>
                                        </span>
                                        <div class="lightness"></div>
                                    </div>
                                    <div class="CBT">
                                        <a title="� ������" href="" class="blueGrayLink productOnMainHeaderLink">��� ������ � �����</a>
                                        <a title="���������" class="productSettingsImg" href="">
                                            <span title="���������" class="blueGrayLink">���������</span>
                                        </a>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="RCL r-center-left">
                        <div class="RCR r-center-right">
                            <div class="RC r-content">
                                <!-- ���������� �� ����� -->
                                <script type="text/javascript">
                                    function showFullInfo(url)
                                    {
                                        window.location = url;
                                    }
                                </script>
                                <div class="forProductBorder ">
                                    <div class="productCover Product">
                                        <div class="pruductImg ">
                                            <a onclick="return false;" href=""><img border="0" onerror="onImgError(this)" alt="�����" src="${imagePath}/deposits_type/account.jpg"></a>
                                        </div>
                                        <div class="all-about-product">
                                            <div class="productTitle">
                                                <div class="productTitleText">
                                                   <table width="100%">
                                                       <tbody><tr>
                                                           <td class="alignTop">
                                                               <div class="productName">
                                                                   <div class="titleName">
                                                                       <div class="description">&nbsp;</div>
                                                                       <div class="clear"></div>
                                                                       <span title="����� ��������������" class="relative titleBlock">
                                                                           <a onclick="return false;" href=""><span class="mainProductTitle">����� ��������������</span></a>
                                                                           <div class="lightness"></div>
                                                                       </span>&nbsp;
                                                                   </div>
                                                                   <table>
                                                                       <tr>
                                                                           <td></td>
                                                                       </tr>
                                                                   </table>
                                                               </div>
                                                           </td>
                                                           <td class="productButtonsAndOperations">
                                                                <table class="floatRight">
                                                                    <tbody><tr>
                                                                        <td>
                                                                            <div class="productAmount">
                                                                                <div class="productCenter">
                                                                                    <span class="overallAmount">8 077,04 ���.</span>
                                                                                    <br>
                                                                                </div>
                                                                            </div>
                                                                        </td>
                                                                        <td class="alignMiddle">
                                                                            <div class="productRight">
                                                                                <span class="prodStatus">
                                                                                    <div onclick="cancelBubbling(event);" id="listOfOperation1_parent" class="listOfOperation productOperation" style="z-index: 0;">
                                                                                        <div style="display: none; z-index: 0;" class="moreListOfOperation" id="listOfOperation1">
                                                                                            <div class="workspace-box hoar">
                                                                                                <div class="hoarRT r-top ">
                                                                                                    <div class="hoarRTL r-top-left">
                                                                                                        <div class="hoarRTR r-top-right">
                                                                                                            <div class="hoarRTC r-top-center">
                                                                                                                <div class="clear"></div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                                <div class="hoarRCL r-center-left">
                                                                                                    <div class="hoarRCR r-center-right">
                                                                                                        <div class="hoarRC r-content">
                                                                                                            <div>
                                                                                                                <table cellspacing="0" cellpadding="0" class="productOperationList">
                                                                                                                        <tbody><tr class="opHoverFirst"><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);">
                                                                                                                        <a href="">��������</a>
                                                                                                                    </td></tr>
                                                                                                                        <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);">
                                                                                                                        <a href="">��������� �����</a>
                                                                                                                    </td></tr>
                                                                                                                        <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);">
                                                                                                                        <a href="" class="operationSeparate">��������� ����� ������ ������� � �������</a>
                                                                                                                    </td></tr>
                                                                                                                        <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);">
                                                                                                                        <a href="">��������� �����������</a>
                                                                                                                    </td></tr>
                                                                                                                        <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);">
                                                                                                                        <a href="">��������� �������� ����</a>
                                                                                                                    </td></tr>
                                                                                                                        <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="" class="operationSeparate">�������� ������ (������� ���� ��������)</a></td></tr>
                                                                                                                        <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">������� �����</a></td></tr>
                                                                                                                        <tr class="opHoverLast"><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="" class="block">������� �� �����<br> ����������</a></td></tr>
                                                                                                                </tbody></table>
                                                                                                            </div>
                                                                                                            <div class="clear"></div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                                <div class="hoarRBL r-bottom-left">
                                                                                                    <div class="hoarRBR r-bottom-right">
                                                                                                        <div class="hoarRBC r-bottom-center"></div>
                                                                                                    </div>
                                                                                                 </div>
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="buttonSelect productListOperation">
                                                                                            <div class="buttonSelectLeft"></div>
                                                                                            <div class="buttonSelectCenter">��������</div>
                                                                                            <div class="buttonSelectRight"></div>
                                                                                            <div class="clear"></div>
                                                                                        </div>
                                                                                    </div>
                                                                                </span>
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                </tbody></table>
                                                           </td>
                                                       </tr>
                                                   </tbody></table>
                                                   <div class="productNumberBlock">
                                                    <div class="productNumber decoration-none">408 17 810 6 73840329734</div></div>
                                                </div>
                                            </div>
                                            <div class="productCenterContainer ">
                                                <div class="productCDL">
                                                    <div class="productLeft">
                                                        <table class="productDetail">
                                                                <tbody><tr>
                                                                    <td>
                                                                        <div class="availableReight">����� ��� ������: &nbsp;</div>
                                                                    </td>
                                                                    <td>
                                                                        <div class="maxSumWriteContainer"><b>9 572,41</b> ���.</div>
                                                                    </td>
                                                                </tr>
                                                        </tbody></table>
                                                    </div>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                            <div class="clear"></div>
                                            <div class="productBottom">
                                                <div style="display: none" class="simpleTable" id="account_484">
                                                    <div class="grid">
                                                        <img class="abstractLoader" title="Loading..." alt="Loading..." src="${tempSkinUrl}/ajaxLoader.gif">
                                                    </div>
                                                </div>
                                                <a rel="������ ��������" class="hide-text text-gray hideable-element">�������� ��������</a>
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>

                                <!-- ���������� �� ����� -->
                                <script type="text/javascript">
                                    function showFullInfo(url)
                                    {
                                        window.location = url;
                                    }
                                </script>
                                <div class="forProductBorder ">
                                    <div class="productCover Product">
                                        <div class="pruductImg ">
                                            <a onclick="return false;" href=""><img border="0" onerror="onImgError(this)" alt="�����" src="${imagePath}/deposits_type/account.jpg"></a>
                                        </div>
                                        <div class="all-about-product">
                                            <div class="productTitle">
                                                <div class="productTitleText">
                                                   <table width="100%">
                                                       <tbody><tr>
                                                           <td class="alignTop">
                                                               <div class="productName">
                                                                   <div class="titleName">
                                                                       <div class="description">&nbsp;</div>
                                                                       <div class="clear"></div>
                                                                       <span title="����� ��������������" class="relative titleBlock" style="width: 211px;">
                                                                           <a onclick="return false;" href=""><span class="mainProductTitle">����� ��������������</span></a>
                                                                           <div class="lightness"></div>
                                                                       </span>&nbsp;
                                                                   </div>
                                                                   <table>
                                                                       <tr>
                                                                           <td></td>
                                                                       </tr>
                                                                   </table>
                                                               </div>
                                                           </td>
                                                           <td class="productButtonsAndOperations">
                                                                <table class="floatRight">
                                                                    <tbody><tr>
                                                                        <td>
                                                                            <div class="productAmount">
                                                                                <div class="productCenter">
                                                                                    <span class="overallAmount">10 374,09 ���.</span>
                                                                                    <br>
                                                                                </div>
                                                                            </div>
                                                                        </td>
                                                                        <td class="alignMiddle">
                                                                            <div class="productRight">
                                                                                <span class="prodStatus">
                                                                                    <div onclick="cancelBubbling(event);" id="listOfOperation1_parent" class="listOfOperation productOperation" style="z-index: 0;">
                                                                                        <div style="display: none; z-index: 0;" class="moreListOfOperation" id="listOfOperation1">
                                                                                            <div class="workspace-box hoar">
                                                                                                <div class="hoarRT r-top ">
                                                                                                    <div class="hoarRTL r-top-left">
                                                                                                        <div class="hoarRTR r-top-right">
                                                                                                            <div class="hoarRTC r-top-center">
                                                                                                                <div class="clear"></div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                                <div class="hoarRCL r-center-left">
                                                                                                    <div class="hoarRCR r-center-right">
                                                                                                        <div class="hoarRC r-content">
                                                                                                            <div>
                                                                                                                <table cellspacing="0" cellpadding="0" class="productOperationList">
                                                                                                                        <tbody><tr class="opHoverFirst"><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);">
                                                                                                                        <a href="">��������</a>
                                                                                                                    </td></tr>
                                                                                                                        <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);">
                                                                                                                        <a href="">��������� �����</a>
                                                                                                                    </td></tr>
                                                                                                                        <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);">
                                                                                                                        <a href="" class="operationSeparate">��������� ����� ������ ������� � �������</a>
                                                                                                                    </td></tr>
                                                                                                                        <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);">
                                                                                                                        <a href="">��������� �����������</a>
                                                                                                                    </td></tr>
                                                                                                                        <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);">
                                                                                                                        <a href="">��������� �������� ����</a>
                                                                                                                    </td></tr>
                                                                                                                        <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="" class="operationSeparate">�������� ������ (������� ���� ��������)</a></td></tr>
                                                                                                                        <tr><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">������� �����</a></td></tr>
                                                                                                                        <tr class="opHoverLast"><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="" class="block">������� �� �����<br> ����������</a></td></tr>
                                                                                                                </tbody></table>
                                                                                                            </div>
                                                                                                            <div class="clear"></div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                                <div class="hoarRBL r-bottom-left">
                                                                                                    <div class="hoarRBR r-bottom-right">
                                                                                                        <div class="hoarRBC r-bottom-center"></div>
                                                                                                    </div>
                                                                                                 </div>
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="buttonSelect productListOperation">
                                                                                            <div class="buttonSelectLeft"></div>
                                                                                            <div class="buttonSelectCenter">��������</div>
                                                                                            <div class="buttonSelectRight"></div>
                                                                                            <div class="clear"></div>
                                                                                        </div>
                                                                                    </div>
                                                                                </span>
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                </tbody></table>
                                                           </td>
                                                       </tr>
                                                   </tbody></table>
                                                   <div class="productNumberBlock">
                                                    <div class="productNumber decoration-none">458 23 8658 3 93780329963</div></div>
                                                </div>
                                            </div>
                                            <div class="productCenterContainer ">
                                                <div class="productCDL">
                                                    <div class="productLeft">
                                                        <table class="productDetail">
                                                                <tbody><tr>
                                                                    <td>
                                                                        <div class="availableReight">����� ��� ������: &nbsp;</div>
                                                                    </td>
                                                                    <td>
                                                                        <div class="maxSumWriteContainer"><b>9 572,41</b> ���.</div>
                                                                    </td>
                                                                </tr>
                                                        </tbody></table>
                                                    </div>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                            <div class="clear"></div>
                                            <div class="productBottom">
                                                <div style="display: none" class="simpleTable" id="account_484">
                                                    <div class="grid">
                                                        <img class="abstractLoader" title="Loading..." alt="Loading..." src="${tempSkinUrl}/ajaxLoader.gif">
                                                    </div>
                                                </div>
                                                <a rel="������ ��������" class="hide-text text-gray hideable-element">�������� ��������</a>
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>


                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                    <div class="RBL r-bottom-left">
                        <div class="RBR r-bottom-right">
                            <div class="RBC r-bottom-center"></div>
                        </div>
                    </div>
                </div>
                <div class="workspace-box">
                    <div class="RT r-top">
                        <div class="RTL r-top-left">
                            <div class="RTR r-top-right">
                                <div class="RTC r-top-center">
                                    <div style="float: left;" class="Title">
                                        <span>
                                            <a href="">�������</a>
                                        </span>
                                        <div class="lightness"></div>
                                    </div>
                                    <div class="CBT">
                                        <a title="� ������" href="" class="blueGrayLink productOnMainHeaderLink">��� �������</a>
                                        <a title="���������" class="productSettingsImg" href="">
                                            <span title="���������" class="blueGrayLink">���������</span>
                                        </a>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="RCL r-center-left">
                        <div class="RCR r-center-right">
                            <div class="RC r-content">
                                <!-- ���������� �� ������� -->
                                <script type="text/javascript">
                                    function showFullInfo(url)
                                    {
                                        window.location = url;
                                    }
                                </script>
                                <div class="forProductBorder ">
                                    <div class="productCover activeProduct">
                                        <div class="pruductImg ">
                                            <a href=""><img border="0" onerror="onImgError(this)" alt="������" src="${imagePath}/credit_type/icon_creditDiffer.jpg"></a>
                                        </div>
                                        <div class="all-about-product">
                                            <div class="productTitle">
                                                <div class="productTitleText">
                                                   <table width="100%">
                                                       <tbody><tr>
                                                           <td class="alignTop">
                                                               <div class="productName">
                                                                   <div class="titleName">
                                                                       <div class="description">&nbsp;</div>
                                                                       <div class="clear"></div>
                                                                       <span title="������ �� �����" class="relative titleBlock">
                                                                           <a href=""><span class="mainProductTitle">������ �� �����</span></a>
                                                                           <div class="lightness"></div>
                                                                       </span>&nbsp;
                                                                   </div>
                                                               </div>
                                                           </td>
                                                           <td class="productButtonsAndOperations">
                                                                <table class="floatRight">
                                                                    <tbody><tr>
                                                                    <td>
                                                                        <div class="productAmount">
                                                                            <div class="productCenter">
                                                                                <span class="overallAmount">1 000,00 ���.</span>
                                                                                <br />
                                                                                <span class="amountStatus">��������������� ������</span>
                                                                            </div>
                                                                        </div>
                                                                    </td>
                                                                    <td class="alignMiddle">
                                                                        <div class="productRight">
                                                                            <span class="prodStatus">
                                                                                <div onclick="cancelBubbling(event);" id="listOfOperation3_parent" class="listOfOperation productOperation" style="z-index: 0;">
                                                                                    <div style="display: none; z-index: 0;" class="moreListOfOperation" id="listOfOperation3">
                                                                                        <div class="workspace-box hoar">
                                                                                            <div class="hoarRT r-top">
                                                                                                <div class="hoarRTL r-top-left">
                                                                                                    <div class="hoarRTR r-top-right">
                                                                                                        <div class="hoarRTC r-top-center">
                                                                                                            <div class="clear"></div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="hoarRCL r-center-left">
                                                                                                <div class="hoarRCR r-center-right">
                                                                                                    <div class="hoarRC r-content">
                                                                                                        <div>
                                                                                                            <table cellspacing="0" cellpadding="0" class="productOperationList">
                                                                                                                <tbody><tr class="opHoverFirst"><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">������ ������</a></td></tr>
                                                                                                                <tr class="opHoverLast"><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">������</a></td></tr>
                                                                                                            </tbody></table>
                                                                                                        </div>
                                                                                                        <div class="clear"></div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="hoarRBL r-bottom-left">
                                                                                                <div class="hoarRBR r-bottom-right">
                                                                                                    <div class="hoarRBC r-bottom-center"></div>
                                                                                                </div>
                                                                                             </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="buttonSelect productListOperation">
                                                                                        <div class="buttonSelectLeft"></div>
                                                                                        <div class="buttonSelectCenter">��������</div>
                                                                                        <div class="buttonSelectRight"></div>
                                                                                        <div class="clear"></div>
                                                                                    </div>
                                                                                </div>
                                                                            </span>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                </tbody></table>
                                                           </td>
                                                        </tr>
                                                    </tbody></table>
                                                    <div class="productNumberBlock">
                                                         <div class="loanClientType">��&nbsp;�������/���������</div>
                                                    </div>
                                               </div>
                                            </div>
                                            <div class="productCenterContainer ">
                                                <div class="productCDL">
                                                    <div class="productLeft">
                                                        <table class="productDetail">
                                                            <tbody>
                                                            <tr>
                                                                <td><div>������ �� �������:</div></td>
                                                                <td class="value"><span class="bold">13</span>%</td>
                                                            </tr>
                                                            <tr>
                                                                <td><div>������ ��:</div></td>
                                                                <td class="value"><div>18 ������ 2011</div></td>
                                                            </tr>
                                                        </tbody></table>
                                                    </div>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                            <div class="clear"></div>
                                            <div class="productBottom">
                                                <div style="display: none" class="simpleTable" id="loan_81">
                                                    <div class="grid">
                                                        <img class="abstractLoader" title="Loading..." alt="Loading..." src="${tempSkinUrl}/images/ajaxLoader.gif">
                                                    </div>
                                                </div>
                                                <a rel="������ ��������" class="hide-text text-gray hideable-element ">�������� ��������</a>
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                                <div class="clear"></div>
                                <!-- ���������� �� ������� -->
                                <script type="text/javascript">
                                    function showFullInfo(url)
                                    {
                                        window.location = url;
                                    }
                                </script>
                                <div class="forProductBorder">
                                    <div class="productCover activeProduct">
                                        <div class="pruductImg">
                                            <a href=""><img border="0" onerror="onImgError(this)" alt="������" src="${imagePath}/credit_type/icon_creditDiffer.jpg"></a>
                                        </div>
                                        <div class="all-about-product">
                                            <div class="productTitle">
                                                <div class="productTitleText">
                                                   <table width="100%">
                                                       <tbody><tr>
                                                           <td class="alignTop">
                                                               <div class="productName">
                                                                   <div class="titleName">
                                                                       <div class="description">&nbsp;</div>
                                                                       <div class="clear"></div>
                                                                       <span title="������ �� �����" class="relative titleBlock">
                                                                           <a href=""><span class="mainProductTitle">������ �� �����</span></a>
                                                                           <div class="lightness"></div>
                                                                       </span>&nbsp;
                                                                   </div>
                                                               </div>
                                                           </td>
                                                           <td class="productButtonsAndOperations">
                                                                <table class="floatRight">
                                                                    <tbody><tr>
                                                                    <td>
                                                                        <div class="productAmount">
                                                                            <div class="productCenter">
                                                                                <span class="overallAmount">2 337,30 ���.</span>
                                                                                <br />
                                                                                <span class="amountStatus">��������������� ������</span>
                                                                            </div>
                                                                        </div>
                                                                    </td>
                                                                    <td class="alignMiddle">
                                                                        <div class="productRight">
                                                                            <span class="prodStatus">
                                                                                <div onclick="cancelBubbling(event);" id="listOfOperation3_parent" class="listOfOperation productOperation" style="z-index: 0;">
                                                                                    <div style="display: none; z-index: 0;" class="moreListOfOperation" id="listOfOperation3">
                                                                                        <div class="workspace-box hoar">
                                                                                            <div class="hoarRT r-top">
                                                                                                <div class="hoarRTL r-top-left">
                                                                                                    <div class="hoarRTR r-top-right">
                                                                                                        <div class="hoarRTC r-top-center">
                                                                                                            <div class="clear"></div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="hoarRCL r-center-left">
                                                                                                <div class="hoarRCR r-center-right">
                                                                                                    <div class="hoarRC r-content">
                                                                                                        <div>
                                                                                                            <table cellspacing="0" cellpadding="0" class="productOperationList">
                                                                                                                <tbody><tr class="opHoverFirst"><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">������ ������</a></td></tr>
                                                                                                                <tr class="opHoverLast"><td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">������</a></td></tr>
                                                                                                            </tbody></table>
                                                                                                        </div>
                                                                                                        <div class="clear"></div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="hoarRBL r-bottom-left">
                                                                                                <div class="hoarRBR r-bottom-right">
                                                                                                    <div class="hoarRBC r-bottom-center"></div>
                                                                                                </div>
                                                                                             </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="buttonSelect productListOperation">
                                                                                        <div class="buttonSelectLeft"></div>
                                                                                        <div class="buttonSelectCenter">��������</div>
                                                                                        <div class="buttonSelectRight"></div>
                                                                                        <div class="clear"></div>
                                                                                    </div>
                                                                                </div>
                                                                            </span>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                </tbody></table>
                                                           </td>
                                                        </tr>
                                                    </tbody></table>
                                                    <div class="productNumberBlock">
                                                         <div class="loanClientType">��&nbsp;�������/���������</div>
                                                    </div>
                                               </div>
                                            </div>
                                            <div class="productCenterContainer ">
                                                <div class="productCDL">
                                                    <div class="productLeft">
                                                        <table class="productDetail">
                                                            <tbody>
                                                            <tr>
                                                                <td><div>������ �� �������:</div></td>
                                                                <td class="value"><span class="bold">13</span>%</td>
                                                            </tr>
                                                            <tr>
                                                                <td><div>������ ��:</div></td>
                                                                <td class="value"><div>23 ������ 2013</div></td>
                                                            </tr>
                                                        </tbody></table>
                                                    </div>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                            <div class="clear"></div>
                                            <div class="productBottom">
                                                <div style="display: none" class="simpleTable" id="loan_81">
                                                    <div class="grid">
                                                        <img class="abstractLoader" title="Loading..." alt="Loading..." src="${tempSkinUrl}/ajaxLoader.gif">
                                                    </div>
                                                </div>
                                                <a rel="������ ��������" class="hide-text text-gray hideable-element">�������� ��������</a>
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                    <div class="RBL r-bottom-left">
                        <div class="RBR r-bottom-right">
                            <div class="RBC r-bottom-center"></div>
                        </div>
                    </div>
                </div>
                <div class="workspace-box">
                    <div class="RT r-top">
                        <div class="RTL r-top-left">
                            <div class="RTR r-top-right">
                                <div class="RTC r-top-center">
                                    <div style="float: left;" class="Title">
                                        <span class="middleTitle"><a href="">����� ����</a></span>
                                        <div class="lightness"></div>
                                    </div>
                                    <div class="CBT">
                                        <a title="� ������" href="" class="blueGrayLink productOnMainHeaderLink">��� �����</a>
                                        <a title="���������" class="productSettingsImg" href="">
                                            <span title="���������" class="blueGrayLink">���������</span>
                                        </a>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="RCL r-center-left">
                        <div class="RCR r-center-right">
                            <div class="RC r-content">
                                <!-- ���������� �� ����� -->
                                <div class="depoAccountTemplate ">
                                    <script type="text/javascript">
                                        function showFullInfo(url)
                                        {
                                            window.location = url;
                                        }
                                    </script>
                                    <div class="forProductBorder ">
                                        <div class="productCover activeProduct">
                                            <div class="pruductImg ">
                                                <a  href=""><img border="0"  alt="���� ����" src="${imagePath}/icon_depositariy.jpg"></a>
                                            </div>
                                            <div class="all-about-product">
                                                <div class="productTitle">
                                                    <div class="productTitleText">
                                                        <table width="100%">
                                                            <tr>
                                                                <td class="alignTop">
                                                                    <div class="productName">
                                                                        <div class="titleName">
                                                                            <div class="description">&nbsp;</div>
                                                                            <div class="clear"></div>
                                                                            <span title="0365265789" class="relative titleBlock">
                                                                                <a href=""><span class="mainProductTitle">0365265789</span></a>
                                                                               <div class="lightness"></div>
                                                                           </span>&nbsp;
                                                                       </div>
                                                                   </div>
                                                                </td>
                                                                <td class="productButtonsAndOperations">
                                                                    <table class="floatRight">
                                                                        <tr>
                                                                            <td>
                                                                                <div class="productAmount">
                                                                                    <div class="productCenter">
                                                                                        <span class="overallAmount">2 120,13 ���.</span><br />
                                                                                        <span class="amountStatus">�������������</span>
                                                                                    </div>
                                                                                </div>
                                                                            </td>
                                                                            <td class="alignMiddle">
                                                                                <div class="productRight">
                                                                                    <span class="prodStatus">
                                                                                        <div id="listOfOperation9_parent" class="listOfOperation productOperation" style="z-index: 0;">
                                                                                            <div style="display: none; z-index: 0;" class="moreListOfOperation" id="listOfOperation9">
                                                                                                <div class="workspace-box hoar">
                                                                                                    <div class="hoarRT r-top ">
                                                                                                        <div class="hoarRTL r-top-left">
                                                                                                            <div class="hoarRTR r-top-right">
                                                                                                                <div class="hoarRTC r-top-center">
                                                                                                                    <div class="clear"></div>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div class="hoarRCL r-center-left">
                                                                                                        <div class="hoarRCR r-center-right">
                                                                                                            <div class="hoarRC r-content">
                                                                                                                <div>
                                                                                                                    <table cellspacing="0" cellpadding="0" class="productOperationList">
                                                                                                                        <tr class="opHoverFirst">
                                                                                                                            <td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"> <a href="">������ ���������</a></td>
                                                                                                                        </tr>
                                                                                                                        <tr>
                                                                                                                            <td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">�������������</a></td>
                                                                                                                        </tr>
                                                                                                                        <tr>
                                                                                                                            <td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">�������</a></td>
                                                                                                                        </tr>
                                                                                                                        <tr class="opHoverLast">
                                                                                                                            <td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">������ ����������</a></td>
                                                                                                                        </tr>
                                                                                                                    </table>
                                                                                                                </div>
                                                                                                                <div class="clear"></div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div class="hoarRBL r-bottom-left">
                                                                                                        <div class="hoarRBR r-bottom-right">
                                                                                                            <div class="hoarRBC r-bottom-center"></div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="buttonSelect productListOperation">
                                                                                                <div class="buttonSelectLeft"></div>
                                                                                                <div class="buttonSelectCenter">��������</div>
                                                                                                <div class="buttonSelectRight"></div>
                                                                                                <div class="clear"></div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </span>
                                                                                </div>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                           </tr>
                                                        </table>
                                                        <div class="productNumberBlock">
                                                            <div class="productNumber decoration-none">� ��������&nbsp;7758-2c-8pL</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="productCenterContainer ">
                                                    <div class="productCDL">
                                                        <div class="productLeft">
                                                            <table class="productDetail">
                                                                <tbody><tr>
                                                                    <td>
                                                                        <div class="availableReight">������: &nbsp;</div>
                                                                    </td>
                                                                    <td>
                                                                        <span class="bold">02 ��� 2012</span>
                                                                    </td>
                                                                </tr>
                                                            </tbody></table>
                                                        </div>
                                                    </div>
                                                    <div class="clear"></div>
                                                </div>
                                                <div class="clear"></div>
                                                <div class="productBottom"></div>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="clear"></div>
                                <!-- ���������� �� ����� -->
                                <div class="depoAccountTemplate ">
                                    <script type="text/javascript">
                                        function showFullInfo(url)
                                        {
                                            window.location = url;
                                        }
                                    </script>
                                    <div class="forProductBorder ">
                                        <div class="productCover activeProduct">
                                            <div class="pruductImg ">
                                                <a  href=""><img border="0"  alt="���� ����" src="${imagePath}/icon_depositariy.jpg"></a>
                                            </div>
                                            <div class="all-about-product">
                                                <div class="productTitle">
                                                    <div class="productTitleText">
                                                        <table width="100%">
                                                            <tr>
                                                                <td class="alignTop">
                                                                    <div class="productName">
                                                                        <div class="titleName">
                                                                            <div class="description">&nbsp;</div>
                                                                            <div class="clear"></div>
                                                                            <span title="0365265789" class="relative titleBlock">
                                                                                <a href=""><span class="mainProductTitle">2685265850</span></a>
                                                                               <div class="lightness"></div>
                                                                           </span>&nbsp;
                                                                       </div>
                                                                   </div>
                                                                </td>
                                                                <td class="productButtonsAndOperations">
                                                                    <table class="floatRight">
                                                                        <tr>
                                                                            <td>
                                                                                <div class="productAmount">
                                                                                    <div class="productCenter">
                                                                                        <span class="overallAmount">1 100,50 ���.</span><br />
                                                                                        <span class="amountStatus">�������������</span>
                                                                                    </div>
                                                                                </div>
                                                                            </td>
                                                                            <td class="alignMiddle">
                                                                                <div class="productRight">
                                                                                    <span class="prodStatus">
                                                                                        <div id="listOfOperation9_parent" class="listOfOperation productOperation" style="z-index: 0;">
                                                                                            <div style="display: none; z-index: 0;" class="moreListOfOperation" id="listOfOperation9">
                                                                                                <div class="workspace-box hoar">
                                                                                                    <div class="hoarRT r-top ">
                                                                                                        <div class="hoarRTL r-top-left">
                                                                                                            <div class="hoarRTR r-top-right">
                                                                                                                <div class="hoarRTC r-top-center">
                                                                                                                    <div class="clear"></div>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div class="hoarRCL r-center-left">
                                                                                                        <div class="hoarRCR r-center-right">
                                                                                                            <div class="hoarRC r-content">
                                                                                                                <div>
                                                                                                                    <table cellspacing="0" cellpadding="0" class="productOperationList">
                                                                                                                        <tr class="opHoverFirst">
                                                                                                                            <td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"> <a href="">������ ���������</a></td>
                                                                                                                        </tr>
                                                                                                                        <tr>
                                                                                                                            <td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">�������������</a></td>
                                                                                                                        </tr>
                                                                                                                        <tr>
                                                                                                                            <td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">�������</a></td>
                                                                                                                        </tr>
                                                                                                                        <tr class="opHoverLast">
                                                                                                                            <td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"><a href="">������ ����������</a></td>
                                                                                                                        </tr>
                                                                                                                    </table>
                                                                                                                </div>
                                                                                                                <div class="clear"></div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div class="hoarRBL r-bottom-left">
                                                                                                        <div class="hoarRBR r-bottom-right">
                                                                                                            <div class="hoarRBC r-bottom-center"></div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="buttonSelect productListOperation">
                                                                                                <div class="buttonSelectLeft"></div>
                                                                                                <div class="buttonSelectCenter">��������</div>
                                                                                                <div class="buttonSelectRight"></div>
                                                                                                <div class="clear"></div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </span>
                                                                                </div>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                           </tr>
                                                        </table>
                                                        <div class="productNumberBlock">
                                                            <div class="productNumber decoration-none">� ��������&nbsp;6487-3c-9pL</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="productCenterContainer ">
                                                    <div class="productCDL">
                                                        <div class="productLeft">
                                                            <table class="productDetail">
                                                                <tbody><tr>
                                                                    <td>
                                                                        <div class="availableReight">������: &nbsp;</div>
                                                                    </td>
                                                                    <td>
                                                                        <span class="bold">30 ��� 2013</span>
                                                                    </td>
                                                                </tr>
                                                            </tbody></table>
                                                        </div>
                                                    </div>
                                                    <div class="clear"></div>
                                                </div>
                                                <div class="clear"></div>
                                                <div class="productBottom"></div>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="RBL r-bottom-left">
                        <div class="RBR r-bottom-right">
                            <div class="RBC r-bottom-center"></div>
                        </div>
                        </div>
                </div>

                <div class="workspace-box">
                    <div class="RT r-top">
                        <div class="RTL r-top-left">
                            <div class="RTR r-top-right">
                                <div class="RTC r-top-center">
                                    <div class="Title" style="float: left;">
                                        <span class="middleTitle"><a href="">������������� �����</a></span>
                                    </div>
                                    <div class="CBT" style="width: 167px;">
                                        <a title="� ������" href="" class="blueGrayLink productOnMainHeaderLink">��� �����</a>
                                        <a title="���������" class="productSettingsImg" href="">
                                            <span title="���������" class="blueGrayLink">
                                                ���������
                                            </span>
                                        </a>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="RCL r-center-left">
                        <div class="RCR r-center-right">
                            <div class="RC r-content">
                                <div class="imaProduct">
                                    <script type="text/javascript">
                                        function showFullInfo(url)
                                        {
                                            window.location = url;
                                        }
                                    </script>
                                    <div class="forProductBorder ">
                                        <div class="productCover activeProduct">
                                            <div class="pruductImg ">
                                                <a href=""><img border="0"  alt="������� � �������" src="${imagePath}/ima_type/imaccount.gif"></a>
                                            </div>
                                            <div class="all-about-product">
                                               <div class="productTitle">
                                                   <div class="productTitleText  ">
                                                       <table width="100%">
                                                           <tr>
                                                               <td class="alignTop">
                                                                   <div class="productName">
                                                                       <div class="titleName">
                                                                           <div class="description">&nbsp;</div>
                                                                           <div class="clear"></div>
                                                                           <span title="������� � �������&nbsp;(ARG)" class="relative titleBlock">
                                                                               <a href=""><span class="mainProductTitle">������� � �������&nbsp;(ARG)</span></a>
                                                                               <div class="lightness"></div>
                                                                           </span>&nbsp;
                                                                       </div>
                                                                   </div>
                                                               </td>
                                                               <td class="productButtonsAndOperations">
                                                                    <table id="" class="floatRight">
                                                                        <tbody><tr>
                                                                            <td>
                                                                                <div class="productAmount">
                                                                                    <div class="productCenter">
                                                                                        <span class="overallAmount">6 310 781,0 �</span>
                                                                                        <br />
                                                                                    </div>
                                                                                </div>
                                                                            </td>
                                                                            <td class="alignMiddle">
                                                                                <div class="productRight">
                                                                                    <span class="prodStatus">
                                                                                        <div onclick="cancelBubbling(event);" id="listOfOperation11_parent" class="listOfOperation productOperation" style="z-index: 0;">
                                                                                            <div style="display: none; z-index: 0;" class="moreListOfOperation" id="listOfOperation11">
                                                                                                <div class="workspace-box hoar">
                                                                                                    <div class="hoarRT r-top ">
                                                                                                        <div class="hoarRTL r-top-left">
                                                                                                            <div class="hoarRTR r-top-right">
                                                                                                                <div class="hoarRTC r-top-center">
                                                                                                                    <div class="clear"></div>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div class="hoarRCL r-center-left">
                                                                                                        <div class="hoarRCR r-center-right">
                                                                                                            <div class="hoarRC r-content">
                                                                                                                <div>
                                                                                                                    <table cellspacing="0" cellpadding="0" class="productOperationList">
                                                                                                                        <tr class="opHoverFirst">
                                                                                                                            <td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);">
                                                                                                                                <a href="">�������</a>
                                                                                                                            </td>
                                                                                                                        </tr>
                                                                                                                        <tr class="opHoverLast">
                                                                                                                            <td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);">
                                                                                                                                <a href="">�������/������� �������</a>
                                                                                                                            </td>
                                                                                                                        </tr>
                                                                                                                    </table>
                                                                                                                </div>
                                                                                                                <div class="clear"></div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div class="hoarRBL r-bottom-left">
                                                                                                        <div class="hoarRBR r-bottom-right">
                                                                                                            <div class="hoarRBC r-bottom-center"></div>
                                                                                                        </div>
                                                                                                     </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="buttonSelect productListOperation">
                                                                                                <div class="buttonSelectLeft"></div>
                                                                                                <div class="buttonSelectCenter">��������</div>
                                                                                                <div class="buttonSelectRight"></div>
                                                                                                <div class="clear"></div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </span>
                                                                                </div>
                                                                            </td>
                                                                        </tr>
                                                                    </tbody></table>
                                                               </td>
                                                           </tr>
                                                       </table>
                                                       <div class="productNumberBlock">
                                                            <a class="productNumber decoration-none" href="">266 81 196 7 9642281833622</a>
                                                       </div>
                                                   </div>
                                               </div>
                                               <div class="productCenterContainer ">
                                                    <div class="productCDL">
                                                        <div class="productLeft">
                                                            <div class="clear"></div>
                                                            <table class="productDetail">
                                                                <tr>
                                                                    <td>
                                                                        <div class="availableReight">������: &nbsp;</div>
                                                                    </td>
                                                                    <td>
                                                                        <span class="bold">10 ������� 2012</span>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            <div class="additionalData">
                                                                �� ����� ������� �����: &nbsp;
                                                                <b class="nowrapWhiteSpace">68 590 616,53 ���.</b>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="clear"></div>
                                                </div>
                                               <div class="clear"></div>

                                               <div class="productBottom">
                                                    <div style="overflow: hidden; height: auto;" class="simpleTable" id="imaccount_463">
                                                        <div class="grid">
                                                            <div class="mini-abstract">
                                                                <table width="100%" cellspacing="0" cellpadding="0" align="CENTER" class="tblInf">
                                                                    <tr class="ListLine0">
                                                                        <td class="listItem"><span class="text-gray"> 03.12&nbsp;</span></td>
                                                                        <td class="listItem">������� � �������&nbsp;(ARG)</td>
                                                                        <td class="align-right">-6 269,1 �</td>
                                                                        <td class="align-right">9 147,81 ���.</td>
                                                                    </tr>
                                                                    <tr class="ListLine1">
                                                                        <td class="listItem"><span class="text-gray"> 03.12&nbsp;</span></td>
                                                                        <td class="listItem">������� � �������&nbsp;(ARG)</td>
                                                                        <td class="align-right"><span class="plus-amount">6 911,6 �</span></td>
                                                                        <td class="align-right">6 561,94 ���.</td>
                                                                    </tr>
                                                                    <tr class="ListLine0">
                                                                        <td class="listItem"><span class="text-gray"> 03.12&nbsp;</span></td>
                                                                        <td class="listItem">������� � �������&nbsp;(ARG)</td>
                                                                        <td class="align-right"><span class="plus-amount">4 559,9 �</span></td>
                                                                        <td class="align-right">8 883,45 ���.</td>
                                                                    </tr>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <a rel="������ ��������" class="hide-text text-gray hideable-element hide">������ ��������</a>
                                                </div>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                <div class="RBL r-bottom-left">
                    <div class="RBR r-bottom-right">
                        <div class="RBC r-bottom-center"></div>
                    </div>
                </div>
                </div>
                <div class="workspace-box">
                    <div class="RT r-top">
                        <div class="RTL r-top-left">
                            <div class="RTR r-top-right">
                                <div class="RTC r-top-center">
                                    <div class="Title">
                                        <span class="middleTitle">���������� ���������</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="RCL r-center-left">
                        <div class="RCR r-center-right">
                            <div class="RC r-content">
                                <div class="pfrProduct">
                                    <div class="forProductBorder ">
                                        <div class="productCover activeProduct">
                                            <div class="pruductImg ">
                                                <a href=""><img border="0" alt="" src="${imagePath}/products/icon_pfr.jpg"></a>
                                            </div>
                                            <div class="all-about-product">

                                                <div class="productTitle">
                                                   <div class="productTitleText ">
                                                       <table width="100%">
                                                           <tr>
                                                               <td class="alignTop">
                                                                   <div class="productName">
                                                                       <div class="titleName">
                                                                           <div class="description">&nbsp;</div>
                                                                           <div class="clear"></div>
                                                                           <span title="�������  � ���������  ��������������� �������� �����" class="relative titleBlock">
                                                                               <a href=""><span class="mainProductTitle">�������  � ���������  ��������������� �������� �����</span></a>
                                                                               <div class="lightness"> </div>
                                                                           </span>&nbsp;
                                                                       </div>
                                                                   </div>
                                                               </td>
                                                               <td class="productButtonsAndOperations">
                                                                    <table class="floatRight">
                                                                        <tr>
                                                                            <td>
                                                                                <div class="productAmount">
                                                                                    <div class="productCenter">&nbsp;</div>
                                                                                </div>
                                                                            </td>
                                                                            <td class="alignMiddle">
                                                                                <div class="productRight">
                                                                                    <span class="prodStatus">
                                                                                        <div id="listOfOperation17_parent" class="listOfOperation productOperation" style="z-index: 0;" onclick="cancelBubbling(event);">
                                                                                            <div id="listOfOperation17" class="moreListOfOperation" style="display: none; z-index: 0;">
                                                                                                <div class="workspace-box hoar">
                                                                                                    <div class="hoarRT r-top ">
                                                                                                        <div class="hoarRTL r-top-left">
                                                                                                            <div class="hoarRTR r-top-right">
                                                                                                                <div class="hoarRTC r-top-center">
                                                                                                                    <div class="clear"></div>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div class="hoarRCL r-center-left">
                                                                                                        <div class="hoarRCR r-center-right">
                                                                                                            <div class="hoarRC r-content">
                                                                                                                <div>
                                                                                                                    <table cellspacing="0" cellpadding="0" class="productOperationList">
                                                                                                                        <tr class="opHoverFirst opHoverLast FirstLastOperation">
                                                                                                                            <td onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);">
                                                                                                                                <a href="">�������� �������</a>
                                                                                                                            </td>
                                                                                                                        </tr>
                                                                                                                    </table>
                                                                                                                </div>
                                                                                                                <div class="clear"></div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div class="hoarRBL r-bottom-left">
                                                                                                        <div class="hoarRBR r-bottom-right">
                                                                                                            <div class="hoarRBC r-bottom-center"></div>
                                                                                                        </div>
                                                                                                     </div>
                                                                                                </div>
                                                                                            </div>

                                                                                            <div class="buttonSelect productListOperation">
                                                                                                <div class="buttonSelectLeft"></div>
                                                                                                <div class="buttonSelectCenter">��������</div>
                                                                                                <div class="buttonSelectRight"></div>
                                                                                                <div class="clear"></div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </span>
                                                                                </div>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                               </td>
                                                           </tr>
                                                       </table>
                                                       <div class="productNumberBlock"></div>
                                                   </div>
                                               </div>

                                                <div class="productCenterContainer ">
                                                    <div class="productCDL">
                                                        <div class="productLeft"></div>
                                                    </div>
                                                    <div class="clear"></div>
                                                </div>
                                                <div class="clear"></div>

                                                <div class="productBottom">
                                                    <div style="display: none" class="simpleTable" id="pfr_81">
                                                        <div class="grid">
                                                            <img class="abstractLoader" title="Loading..." alt="Loading..." src="${tempSkinUrl}/images/ajaxLoader.gif">
                                                        </div>
                                                    </div>
                                                    <a rel="������ ��������" class="hide-text text-gray hideable-element ">�������� ��������</a>
                                                </div>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                    <div class="RBL r-bottom-left">
                        <div class="RBR r-bottom-right">
                            <div class="RBC r-bottom-center"></div>
                        </div>
                    </div>
                </div>
                <div id="mainPageNewsContainer">
                    <div class="mainPageNews">
                        <div class="workspace-box">
                            <div class="RT r-top">
                                <div class="RTL r-top-left">
                                    <div class="RTR r-top-right">
                                        <div class="RTC r-top-center">
                                            <div class="Title" style="float: left;">
                                                <span class="middleTitle">�������</span>
                                            </div>
                                            <div class="CBT">
                                                <a class="blueGrayLink productOnMainHeaderLink" title="� ������" href="">������� ���</a>
                                                <a class="blueGrayLink" title="� ������" href="">��� �������</a>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="RCL r-center-left">
                                <div class="RCR r-center-right">
                                    <div class="RC r-content">
                                        <div id="news">
                                            <div class="news-list">
                                                <div class="bold important NextNews">22.03 <br />
                                                    <a class="sbrfLink newsLinkTitle word-wrap" href="#">
                                                        News
                                                    </a>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="RBL r-bottom-left">
                                <div class="RBR r-bottom-right">
                                    <div class="RBC r-bottom-center"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="right-section">
                <!-- ������ ����-->
                <!--������� �������� � ��������� ��������-->
                <div class="personalMenu">
                    <div class="workspace-box roundBorder grayGradient css3">

                        <div class="roundTitle"> ������ ���� </div>

                        <div id="favouriteLinks">
                            <div class="favouriteLinkTitleItem">
                                <ul class='underlined linksList'>
                                    <li>
                                        <a href=''>
                                            <div class="greenTitle"><span>${operationsHistoryLabel}</span></div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href=''>
                                            <div class="greenTitle"><span>��������� �������</span></div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href=''>
                                            <div class="greenTitle"><span>��������� ����������</span></div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href=''>
                                            <div class="greenTitle"><span>����� ����������������</span></div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href=''>
                                            <div class="greenTitle"><span>��� ��������-������</span></div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href=''>
                                            <div class="greenTitle"><span>������� �� ���������</span></div>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>

                        <div class="personalMenuWithTitleItem">
                            <div>
                                <ul class="underlined linksList">
                                    <li class="linksListTitle">
                                        <div class="greenTitle">
                                            <div style="margin-right: 15px;">��� ������� <img class="newGroup" src="${tempSkinUrl}/images/newGroup.png"></div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>

                        <div class="personalMenuWithTitleItem">
                            <div>
                                <ul class="underlined linksList">
                                    <li class="linksListTitle">
                                        <div class="greenTitle">
                                            <div style="margin-right: 15px;">���������</div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>

                        <div class="personalMenuWithTitleItem">
                            <div>
                                <ul class="underlined linksList">
                                    <li class="linksListTitle">
                                        <div class="greenTitle">
                                            <div>��� �������</div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>

                        <div class="personalMenuWithTitleItem">
                            <ul class="underlined linksList">
                                <li class="linksListTitle">
                                    <div class="greenTitle">
                                        <div>��������� ����</div>
                                    </div>
                                </li>
                            </ul>
                        </div>

                        <div class="personalMenuWithTitleItem">
                            <ul class="underlined linksList">
                                <li class="linksListTitle">
                                    <div class="greenTitle">
                                        <div>��� �����������</div>
                                    </div>
                                </li>
                            </ul>
                        </div>

                        <div class="clear"></div>

                    </div>
                </div>
                <!-- ����� �������� �������� � ��������� �������� -->
                <div class="workspace-box roundBorder greenGradient">
                    <div class="helpDiv ">
                        <div class="helpDivLeftBlock">
                            <div class="noManagerAssistant helpBlock">
                                <div class="helpLink helpPage"><a title="������ �� ������ � �������" class="underline" href=""><span>������</span></a></div>

                                <div class="helpLink faqPage">
                                    <a title="����� ���������� �������" href="">
                                        <span>����� ���������� �������</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="helpDivRightBlock">
                            <div class="girl-img">
                                <a href="">
                                    <img border="0" alt="" src="${tempSkinUrl}/images/girl_right.gif">
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
                <!-- ������ ����� ����� -->
                <div class="rightSectionLastBlock">
                    <div class="currencyRate">
                        <div class="workspace-box gray">
                            <div class="grayRT r-top">
                                <div class="grayRTL r-top-left">
                                    <div class="grayRTR r-top-right">
                                        <div class="grayRTC r-top-center">
                                            <div class="grayTitle">
                                                <span>�����</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="grayRCL r-center-left">
                                <div class="grayRCR r-center-right">
                                    <div class="grayRC r-content">
                                        <div class="currencyRateContainer">
                                            <div class="currencyRate">
                                                <div class="rateTitle">
                                                    <div class="rateText">&nbsp;</div>
                                                    <div class="rateText text-gray">�������</div>
                                                    <div class="rateText text-gray">�������</div>
                                                </div>
                                                <div class="rateValues">
                                                    <div class="currencyRateName">EUR</div>
                                                    <div class="rateText">
                                                        33.24
                                                        <img alt="" src="${imagePath}/course_up.png">
                                                    </div>
                                                    <div class="rateText">
                                                        36.10
                                                        <img alt="" src="${imagePath}/course_up.png">
                                                    </div>
                                                </div>
                                                <div class="rateValues">
                                                    <div class="currencyRateName">USD</div>
                                                    <div class="rateText">
                                                        9.63
                                                        <img alt="" src="${imagePath}/course_down.png">
                                                    </div>
                                                    <div class="rateText">
                                                        10.50
                                                        <img alt="" src="${imagePath}/course_down.png">
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="currencyRateBottomLink">
                                                <a class="blueGrayLink courseLink" href="">����� ������</a>
                                            </div>

                                            <div class="clear"></div>
                                        </div>

                                        <div class="currencyRateContainer">
                                            <div class="currencyRate">
                                                <div class="rateTitle">
                                                    <div class="rateText">&nbsp;</div>
                                                    <div class="rateText text-gray">�������</div>
                                                    <div class="rateText text-gray">�������</div>
                                                </div>
                                                <div class="rateValues">
                                                    <div class="currencyRateName">������</div>
                                                    <div class="rateText">449.36</div>
                                                    <div class="rateText">450.50</div>
                                                </div>
                                                <div class="rateValues">
                                                    <div class="currencyRateName">�������</div>
                                                    <div class="rateText">10.68</div>
                                                    <div class="rateText">11.90</div>
                                                </div>

                                                <div class="rateValues">
                                                    <div class="currencyRateName">�������</div>
                                                    <div class="rateText">565.91</div>
                                                    <div class="rateText">566.92</div>
                                                </div>
                                                <div class="rateValues">
                                                    <div class="currencyRateName">��������</div>
                                                    <div class="rateText">368.47</div>
                                                    <div class="rateText">369.88</div>
                                                </div>

                                            </div>

                                            <div class="currencyRateBottomLink currencyRateBottom">
                                                <a href="" class="blueGrayLink courseLink">
                                                    ������� � ������� �������
                                                </a>
                                            </div>
                                            <div class="currencyRateDivider"></div>
                                            <div class="currencyRateContainer">
                                                <div class="currencyRateFooter text-gray">
                                                    � ������ ���������� �������� �������� ����� ����� ����������. � ���� ������ �� ����������� �������� ���.
                                                </div>
                                            </div>
                                        </div>

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
                </div>
                <!-- ����� ����� ����� -->
            </div>
            <!-- end right-section-->
            <div class="clear"></div>
            </div>
            </div>
            <div class="cleaners"></div>
            <div id="footer" class="footerBackground">
                <div class="footerBorder">
                    <div class="ftrContainer">

                        <div class="footerRound">
                            <span>+7 (495) <b>500-55-50</b>, 8 (800) <b>555-55-50</b></span>
                        </div>
                        <div class="clear"></div>

                        <a href="" class="lightGreenRound">sberbank.ru</a>

                        <div class="aroundTheClock">�������������� ������</div>

                        <div class="feedback">
                            <c:if test="${phiz:impliesService('ClientMailManagment')}">
                                <div class="feedbackItems">
                                    <a href="" class="inlineBlock">
                                        <span class="letterToBank"></span>
                                        <span class="inlineBlock">������ � ����</span>
                                    </a>
                                </div>
                            </c:if>
                            <div class="feedbackItems">
                                <a href="" class="inlineBlock">
                                    <span class="onlineHelp"></span>
                                    <span class="inlineBlock">������ ������</span>
                                </a>
                            </div>
                        </div>

                        <div class="clear"></div>
                        <div class="copyrightBlock">
                            <div class="copyright">
                                <p class="bold">� 1997 � 2015 ��� ��������� ������</p>
                                <p>������, ������, 117997, ��. ��������, �. 19,<br/>
                                ����������� �������� �� ������������� ���������� �������� �� 8 ������� 2012<br/>
                                ��������������� ����� � 1481.<br/>
                                ����������� ��������� R-Style Softlab</p>
                            </div>
                            <div id="linkConteiner" class="floatRight">
                                <div class="floatRight">
                                    <p class="bold">�� � ���������� �����</p>
                                    <div id="SocialLinksImg">
                                        <a href="" class="facebook"></a>
                                        <a href="" class="vkontakte"></a>
                                        <a href="" class="twitter"></a>
                                        <a href="" class="odnoklassniki"></a>
                                        <a href="" class="youtube"></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>        
    </div>
</form>
</div>
</body>




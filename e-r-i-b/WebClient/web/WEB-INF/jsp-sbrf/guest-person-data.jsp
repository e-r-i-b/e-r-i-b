<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="imagesPath" value="${globalUrl}/commonSkin/images/guest"/>
<c:set var="skinUrl" value="${globalUrl}/commonSkin"/>
<c:set var="guestEntryType" value="${form.request}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <title>�������� ������</title>
        <link rel="icon" type="image/x-icon" href="${globalUrl}/commonSkin/images/favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/systemAll.css"/>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/style.css"/>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/skins/sbrf/style.css"/>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/guest.css"/>

        <script type="text/javascript" src="${globalUrl}/scripts/jquery-1.4.2.js"></script>
        <script type="text/javascript" src="${globalUrl}/scripts/DivFloat.js"></script>
        <script type="text/javascript" src="${globalUrl}/scripts/Utils.js"></script>
        <script type="text/javascript" src="${globalUrl}/scripts/customPlaceholder.js"></script>
        <script type="text/javascript" src="${globalUrl}/scripts/layout.js"></script>
        <script type="text/javascript" src="${globalUrl}/scripts/windows.js"></script>
        <script type="text/javascript" src="${globalUrl}/scripts/jquery.maskedinput.js"></script>
        <script type="text/javascript" src="${globalUrl}/scripts/jquery.datePicker.js"></script>
        <script type="text/javascript" src="${globalUrl}/scripts/Templates.js"></script>
    </head>
    <body class="gPage">
        <html:form action="/guestPersonData" show="true" onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();" styleId="guestPersonData">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <div id="workspaceCSA" class="fullHeight">

                <div class="mainContent">
                    <div class="header noButtonRegistration">
                        <a class="logoSB" href="http://www.sberbank.ru/">
                            <img src="${imagesPath}/logoSB.png" height="72" width="289"/>
                        </a>

                        <div class="headPhones">
                            <div class="federal">
                                <span class="phoneIco"></span>
                                8 (800) 555 55 50
                            </div>
                            <div class="regional">+7 (495) 500-55-50</div>
                        </div>
                    </div>

                    <div class="title">
                        <p>���������� �������</p>

                        <div class="g-num">
                            <c:set var="phoneNumber" value="${phiz:getGuestPhoneNumber()}"/>
                            ��������� �����
                            <div class="g-num-mask">${phiz:getCutPhoneNumber(phoneNumber)}</div>
                        </div>
                    </div>

                    <div class="wrapper">
                        <div class="ears-content">
                            <div class="formContent">
                                <div class="formContentMargin">
                                    <p class="contentTitle">���� ������������ ������</p>

                                    <c:set var="informMessages" value="${phiz:getInformMessages()}"/>
                                    <c:set var="errors" value=""/>
                                    <phiz:messages id="error" bundle="commonBundle" field="field" title="title" message="error">
                                        <c:set var="errors">${errors}<div class = "itemDiv">${phiz:processBBCodeAndEscapeHtml(error, false)} </div></c:set>
                                    </phiz:messages>

                                    <c:set var="errorsLength" value="${fn:length(errors)}"/>
                                    <tiles:insert definition="errorBlock" flush="false">
                                        <tiles:put name="regionSelector" value="errors"/>
                                        <tiles:put name="isDisplayed" value="${errorsLength gt 0 ? true : false}"/>
                                        <tiles:put name="data">
                                            <bean:write name="errors" filter="false"/>
                                        </tiles:put>
                                    </tiles:insert>

                                    <div class="g-claim">
                                        <div class="form-row">
                                            <div class="paymentLabel">
                                                <span class="paymentTextLabel">�������:</span>
                                                <span class="asterisk">*</span>
                                            </div>
                                            <div class="paymentValue">
                                                <html:text property="field(surName)" size="58" maxlength="120" styleClass="required mainInputWidth"/>
                                            </div>
                                            <div class="clear"></div>
                                        </div>

                                        <div class="form-row">
                                            <div class="paymentLabel">
                                                <span class="paymentTextLabel">���:</span>
                                                <span class="asterisk">*</span>
                                            </div>
                                            <div class="paymentValue">
                                                <html:text property="field(firstName)" size="58" maxlength="120" styleClass="required mainInputWidth"/>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                        <div class="form-row">
                                            <div class="paymentLabel">
                                                <span class="paymentTextLabel">��������:</span>
                                            </div>
                                            <div class="paymentValue">
                                                <html:text styleId="patrName" property="field(patrName)" size="58" maxlength="120" styleClass="required mainInputWidth"/>
                                                <div class="clear"></div>
                                                <div class="textTick">
                                                    <input id="fatherName" class="float" type="checkbox" onchange="clearPatrName()"/>
                                                    <label class="float" for="fatherName"> ��� ��������</label>
                                                </div>
                                            </div>
                                            <div class="clear"></div>
                                        </div>

                                        <div class="s-dataLevel">
                                            <div class="form-row">
                                                <div class="paymentLabel">
                                                    <span class="paymentTextLabel">���� ��������:</span>
                                                    <span class="asterisk">*</span>
                                                </div>
                                                <div class="paymentValue">
                                                    <input type="text" id="guest-birthday"
                                                           name="field(birthday)" class="dot-date-pick"
                                                           size="10" class="contactInput"
                                                           value="<bean:write name="form" property="field(birthday)" format="dd.MM.yyyy"/>"/>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                        </div>
                                    </div>

                                    <p class="contentTitle float c-title">������� ���������� ���������� ���������</p>

                                    <div class="rightHint">
                                        <a class="imgHint">
                                            <div class="hintBlock">
                                                <div class="outerShine">
                                                    <div class="tHint"></div>
                                                    �������� ������ ����� ������ ��������� ���������� ���������
                                                </div>
                                            </div>
                                        </a>
                                    </div>

                                    <div class="g-claim g-claim-last">
                                        <div class="form-row">
                                            <div class="paymentLabel">
                                                <span class="paymentTextLabel">����� � ����� ��������:</span>
                                                <span class="asterisk">*</span>
                                            </div>
                                            <div class="paymentValue">
                                                <html:text styleId="seriesAndNumber" property="field(document)" size="58" maxlength="16" styleClass="required mainInputWidth"/>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                    </div>

                                    <div class="buttonsArea ">
                                        <div class="commandButton ">
                                            <div class="buttonGreen">
                                                <a tabindex="20">
                                                    <div class="left-corner"></div>
                                                    <div class="text"><span onclick="next();">����������</span></div>
                                                    <div class="right-corner"></div>
                                                </a>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="base">
                            <div class="main_row">
                                <div class="content_row"></div>
                            </div>
                            <div class="ears ears-b"></div>
                        </div>
                    </div>
                </div>

                <div class="clear"></div>
                <div class="footer">
                    <div class="references">
                        <a href="http://www.sberbank.ru/moscow/ru/person/contributions/max_stavka/">���������� � ���������� ������� �� ��������� ����������� ������ � �����������
                            ������</a><br/>
                        <a href="http://www.sberbank.ru/moscow/ru/investor_relations/disclosure/shareholders/">���������� � �����, ��� ��������� ������� ��������� ����</a>

                        <div class="copyright">
                            &copy 1997&#8211;2015 �������� ������, ������, ������, 117997, ��. ��������, �.19, ���. +7(495) 500
                            5550 <br/>
                            8 800 555 5550. ����������� �������� �� ������������� ���������� �������� �� 8 ������� 2012
                            ����. <br/>
                            ��������������� ����� &#8212; 1481.
                            <a class="icon" href="http://www.sberbank.ru/moscow/ru/important/"><span>�������� �����.</span></a>
                        </div>
                    </div>
                </div>
                <div id="loading" style="left:-3300px;">
                    <div id="loadingImg"><img src="${skinUrl}/skins/commonSkin/images/ajax-loader64.gif"/></div>
                    <span>����������, ���������,<br/> ��� ������ ��������������.</span>
                </div>
                <script type="text/javascript">
                    var skinUrl = '${skinUrl}';
                    var globalUrl = '${globalUrl}';
                    document.webRoot = '/PhizIC';
                    $("#seriesAndNumber").createMask(PASSPORT_NUMBER_AND_SERIES_TEMPLATE);
                    function clearPatrName()
                    {
                        if ($("#fatherName").attr("checked"))
                            $("#patrName").val("");
                    }

                    function next()
                    {
                        var seriesAndNumberField = $("#seriesAndNumber");
                        seriesAndNumberField.val(seriesAndNumberField.val().replace(/ /g, ""));
                        var form = $("#guestPersonData");
                        form.append("<input type='hidden' name='operation' value='next'/>");
                        form.submit();
                    }
                </script>
            </div>
        </html:form>
    </body>
</html>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${GuestEntryForm}"/>
<c:set var="imagesPath" value="${skinUrl}/skins/commonSkin/images/guest"/>
<c:set var="guestEntryType" value="${form.request}"/>
<c:choose>
    <c:when test="${guestEntryType == 'debet_card'}">
        <c:set var="headGuestEntry" value="����� ��������� �����"/>
        <c:set var="selectImage" value="selectCard.png"/>
        <c:set var="typeClaim" value="debet_card"/>
        <c:set var="typeClaimDescription" value="��� ������ ��������� ����� ������� �����</br> ������ ���������� ��������"/>
    </c:when>
    <c:when test="${guestEntryType == 'credit'}">
        <c:set var="headGuestEntry" value="������ �� ������"/>
        <c:set var="selectImage" value="selectCredit.png"/>
        <c:set var="typeClaim" value="credit"/>
        <c:set var="typeClaimDescription" value="��� ���������� ������� ������� �����</br> ������ ���������� ��������"/>
    </c:when>
    <c:otherwise>
        <c:set var="headGuestEntry" value="����� ��������� �����"/>
        <c:set var="selectImage" value="selectOther.png"/>
        <c:set var="typeClaim" value="credit_card"/>
        <c:set var="typeClaimDescription" value="��� ������ ��������� ����� ������� �����</br> ������ ���������� ��������"/>
    </c:otherwise>
</c:choose>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>�������� ������</title>
    <link rel="icon" type="image/x-icon" href="${skinUrl}/skins/sbrf/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/skins/commonSkin/guest.css"/>
    <script type="text/javascript" src="${skinUrl}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/csa/guest.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/csa/csaUtils.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/windows.js"></script>
    <script type="text/javascript" src="${skinUrl}/scripts/csa/jquery.maskedinput.js"></script>
</head>
<body class="gPage">
<div id="workspaceCSA" class="fullHeight">
    <%--���������� "�� ����� ��������"--%>
    <div id="SMSAttemptsEndedWin" class="SMSAttemptsEnded window farAway">
        <div class="crossButton"><img src="${imagesPath}/buttonCross.png" class="crossButtonAction"></div>
        <div class="textArea">
            <div class="headerErrorMessage">�� ����� ��������</div>
            <div class="errorMessage"><p>SMS-������ ������ �����������. �� ��������� ��� ������� �����. ������� ������� ����������� ������ ��� ���������� �� ������� � ���������� ����� ���������: 8
                (800) 555-55-50, +7 (495) 500-55-50</p>

                <p>���� �� �� �������� ��������� � SMS-�������, ��������� � ������������ ���� ������ ���������� ��������.</p></div>
        </div>
        <div id="buttonRestart" class="buttonNext crossButtonAction">
            ������ ������
        </div>
    </div>
    <%--����� ���������� "�� ����� ��������"--%>

    <%--���������� "�������������� ������"--%>
    <div id="popupErrorWin" class="popupError window farAway">
        <div class="crossButton"><img src="${imagesPath}/buttonCross.png" class="crossButtonAction"></div>
        <div>
            <div class="textArea">
                <div class="headerErrorMessage">������</div>
                <div class="errorMessage">
                    <ul id="popupErrorDescription"></ul>
                </div>
            </div>
        </div>
    </div>
    <%--����� ���������� "�������������� ������"--%>

    <div class="mainContent">
        <div class="header">
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
            <p>${headGuestEntry}</p>
        </div>

        <%--����� �������������� ������� --%>
        <div class="wrapper" id="clientHasAccount">
            <div class="base">
                <div class="mainQuestion">
                    <div class="titleWithPhone">
                        <h4 class="secondTitle">�� ������������������ ������������ �������� ������?</h4>
                    </div>
                    <div class="q-content">
                        <div class="q-list">
                            <img width="126" height="73" src="${imagesPath}/logoSB-s2.png" alt="�������� ������"/>

                            <div class="q-listLink">
                                <a href="${phiz:calculateActionURL(pageContext, "index.do")}" class="q-link">��, � ������������ �������� ������</a>
                            </div>
                        </div>
                        <div class="q-list">
                            <img width="126" height="73" src="${imagesPath}/guest.png" alt="��� ������ � �������� ������"/>

                            <div class="q-listLink">
                                <a id="hasNotLoginInSBOL" class="q-link">� ���� ��� ������ � �������� ������</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <%--����� ��� �����������--%>
        <div id="forAllPeopleBlock">
            <div class="registrationBlock">
                <div id="titleWithPhone">
                    <div class="floatLeft left">
                        <p>�� ����� <span id="phone"></span> ��������� SMS-������</p>
                    </div>
                    <div class="right">
                        <div id="buttonChangePhone">�������� �����</div>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="restriction">
                    <div id="step1" class="step">
                        <div class="line">
                            <div class="leftPart relative">
                                <div class="error"></div>
                                <label class="placeholder">��� ��������� �������</label>
                                <input class="field" type="text" id="phoneNumber" name="field(phoneNumber)"/>
                            </div>
                            <div class="rightPart">
                                ${typeClaimDescription}
                            </div>
                            <div class="clear"></div>
                        </div>
                    </div>
                    <div id="step2" class="step">
                        <div class="line">
                            <div class="leftPart relative">
                                <div class="error"></div>
                                <label class="placeholder">������� ������</label>
                                <input class="field" type="text" id="confirmPassword" name="field(confirmPassword)"/>
                            </div>
                            <div class="rightPart">
                                ��������� SMS-������ �������� ����� ����� <span id="timer"></span> ���
                            </div>
                            <div class="clear"></div>
                        </div>
                    </div>
                    <!--�����-->
                    <div class="line" id="blockCaptcha">
                        <div class="leftPart relative">
                            <img id="idCaptchaImg" height="35" width="105" class="captchaImg"/>

                            <p id="updateCaptchaButton">�������� ���</p>

                            <div class="error"></div>
                            <input type="text" id="captcha" name="field(captcha)" class="field"/>
                        </div>
                        <div class="rightPart">

                        </div>
                        <div class="clear"></div>
                    </div>
                    <div id="buttonNext" class="buttonNext">
                        ����������
                    </div>
                </div>
            </div>

            <div class="wrapper">
                <div class="ears-content">
                    <div class="requestType">
                        <img src="${imagesPath}/${selectImage}" height="319" width="783"/>
                    </div>
                </div>

                <div class="base">
                    <div class="main_row">
                        <div class="content_row"></div>
                    </div>
                    <!--ears-s/ears-b-->
                    <div class="ears ears-s"></div>
                </div>
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
        $(document).ready(page.init('${phiz:calculateActionURL(pageContext, "guest/entry.do")}', '/${csa:loginContextName()}', ${csa:isActiveCaptha(pageContext.request, "captchaServlet")}, "${typeClaim}"));

    </script>
</div>
</body>
<%@ include file="/WEB-INF/jsp/common/analytics.jsp" %>
</html>

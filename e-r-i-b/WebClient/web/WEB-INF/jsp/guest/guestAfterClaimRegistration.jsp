<%--
  User: kichinova
  Date: 27.07.15
  Description: ������ ��� ��������, �������������� ����� �������� ������ ��������-������
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--topMessage - ����� ��� ������� roundBorderLight--%>
<%--contentHeader - ��������� ��������--%>
<%--content - �������--%>
<%--documentID - id ���������--%>

<tiles:importAttribute/>
<c:set var="imagesPath" value="${globalUrl}/commonSkin/images/guest"/>
<c:set var="headerOfRegistration" value="${showRegBtn ? '�����������������' : '������ ����� ������������������'}"/>
<c:if test="${showRegBtn}">
    <c:set var="redirect" value="${phiz:getLinkOnSelfRegistrationInCSA()}"/>
</c:if>


<div id="workspace" class="fullHeight" style="float: none; width: 100%;">

<%--���������� "������� �� ����������� ������ � ������"--%>
<div id="fullRulesWin" class="fullRules window farAway">
    <div class="crossButton"><img src="${imagesPath}/buttonCross.png" class="crossButtonAction"></div>
    <div class="b-rules" id="AuthRules">
        <h3 class="rules_title">������� ����������� ������ � ������</h3>

        <div class="rules_cols">
            <div class="rules_col floatLeft">
                <h4 class="rules_col-title">�����</h4>
                <ul class="rules_list">
                    <li class="rules_item"><span class="wrapperLi">����� �� 8 �� 30 ��������.</span></li>
                    <li class="rules_item"><span class="wrapperLi">����� ������ ��������� ��� ������� ���� ����� � ��� ������� ���� �����.
                        </span></li>
                    <li class="rules_item"><span class="wrapperLi">����� ������ ���� ������ �� ���������� ��������.</span></li>
                    <li class="rules_item"><span class="wrapperLi">�� ����� �������� �� 10 ����.</span></li>
                    <li class="rules_item"><span class="wrapperLi">�� ������ ��������� ����� 3-� ���������� �������� ������.</span></li>
                    <li class="rules_item"><span class="wrapperLi">����� ��������� �������� ���������� �� ������ � � � @ _ - .�</span></li>
                    <li class="rules_item"><span class="wrapperLi">�� ������������ � ��������.</span></li>
                </ul>
            </div>
            <div class="rules_col floatRight">
                <h4 class="rules_col-title">������</h4>
                <ul class="rules_list">
                    <li class="rules_item"><span class="wrapperLi">����� �� ����� 8 ��������.</span></li>
                    <li class="rules_item"><span class="wrapperLi">������ ������ ��������� ��� ������� ���� ����� � ��� ������� ����
                            �����.</span></li>
                    <li class="rules_item"><span class="wrapperLi">�� ������ ��������� ����� 3-� ���������� �������� ������, � �����
                            3-� ��������, ������������� ����� � ����� ���� ����������.
                        </span></li>
                    <li class="rules_item"><span class="wrapperLi">������ ���������� �� ������.</span></li>
                    <li class="rules_item"><span class="wrapperLi">����� ��������� �������� ���������� �� ������ � � ! @ # $ % ^ & * ( ) _ - + : ; , . �</span></li>
                    <li class="rules_item"><span class="wrapperLi">�� ������ ��������� ������ ������ �� ��������� 3 ������.
                        </span></li>
                </ul>
            </div>
        </div>
    </div>
    <!-- // b-rules -->
</div>
<%--����� ���������� "������� �� ����������� ������ � ������"--%>

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
    <!-- // b-rules -->
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

    <%--����� ��� �������, � �������� ���� ������ � ����--%>
    <div class="topPanelWhitMessage">
        ${topMessage}
    </div>
    <c:if test="${!showRegistration}">
        <div class="fullWidth registrationBanner">
            <div class="fon1">
                <div class="base">
                    <div class="bannerRegistration">
                        <p class="bannerHeader">${headerOfRegistration}</p>

                        <div class="bannerText bannerTextDesc">
                            <div class="floatLeft bannerLeftColumn">
                                <c:choose>
                                    <c:when test="${showRegBtn}">
                                        <p class="textAlignLeft headerConnectSBOL">������������ � �������� ������ ����� ������</p>
                                        <a href="${redirect}">
                                            <div class="registrationButton marginTop20">������������������</div>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="headerLoginAndPassword fontFamilyTrebuchet fontSize19 marginBottom20">���������� ����� � ������</p>
                                        <input type="hidden" value="${documentID}" id="documentID"/>
                                        <input type="hidden" value="${documentType}" id="documentType"/>

                                        <div class="pairFieldError relative">
                                            <div class="error"></div>
                                            <label class="placeholderRegistration">�����</label>
                                            <input class="inputTextField" type="text" id="loginField" name="field(login)"/>
                                        </div>
                                        <div class="pairFieldError relative">
                                            <div class="error"></div>
                                            <label class="placeholderRegistration">������� ������</label>
                                            <input class="inputTextField" type="password" id="passwordField" name="field(password)"/>

                                        </div>
                                        <div class="pairFieldError relative">
                                            <div class="error"></div>
                                            <label class="placeholderRegistration">��������� ������</label>
                                            <input class="inputTextField" type="password" id="confirmPasswordField" name="field(confirmPassword)"/>

                                        </div>
                                        <div id="blockCaptcha">
                                            <div class="pairFieldError relative">
                                                <img id="idCaptchaImg" class="captchaImgRegistration" height="35" width="105"/>

                                                <p id="updateCaptchaButtonRegistration">�������� ���</p>

                                                <div class="error"></div>
                                                <input class="customPlaceholder inputTextField" type="text" title="������� ��� � ��������" name="field(captchaCode)" id="captchaCodeField"
                                                       maxlength="40"/>
                                            </div>
                                        </div>
                                        <div class="registrationButton" id="buttonRegistrationNewClient">������������������</div>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                            <div class="floatRight items relative">
                                <c:choose>
                                    <c:when test="${showRegBtn}">
                                        <p class="additionText textAlignLeft">� ��� ��� ���� ��������� ����� ���������. ������� ����������� � �������� ������ �������� ���� ����������� � ����
                                            ����������� ������������</p>
                                        <ul class="itemUL">
                                            <li class="itemLI">
                                                <span class="itemHeader">����������� ������������ ������ � ����� ������</span>
                                            </li>
                                            <li class="itemLI">
                                                <span class="itemHeader">���������� ������ �� �������� ��������</span>
                                            </li>
                                            <li class="itemLI">
                                                <span class="itemHeader">���������� ���������� ����� ��������</span>
                                            </li>
                                        </ul>
                                    </c:when>
                                    <c:otherwise>
                                        <ul class="itemUL" id="IdItemUL">
                                            <li class="itemLI">
                                                <span class="itemHeader">���������</span>

                                                <p class="itemText">������� ���������� ������</p>
                                            </li>
                                            <li class="itemLI">
                                                <span class="itemHeader">���������</span>

                                                <p class="itemText">������� �������� ������ � �������� ������</p>
                                            </li>
                                            <li class="itemLI">
                                                <span class="itemHeader">���������</span>

                                                <p class="itemText">˸���� �������� ��������� ������</p>
                                            </li>
                                        </ul>
                                        <div class="b-reg-helper" id="Id-b-reg-helper">
                                            <h3 class="reg-helper_head">������� �������</h3>

                                            <div class="reg-helper_inner">
                                                <div class="reg-helper_item">
                                                    <h4 class="reg-helper_title">sberbank21</h4>

                                                    <p class="reg-helper_description">����� � ������<br/> ������ ��������� �����<br/> � �����.
                                                        ������� 8 ��������.</p>

                                                    <h4 class="reg-helper_title">A=a</h4>

                                                    <p class="reg-helper_description">����� � ������<br/> �� ������������� � ��������</p>

                                                </div>
                                                <div class="reg-helper_item floatRight">
                                                    <img class="reg-helper_pic" src="${imagesPath}/qwer.png" alt="" width="114" height="44"/>

                                                    <p class="reg-helper_description">������ ������������ �����<br/> 3-� �������� ������ � �����<br/> ����
                                                        ����������.</p>
                                                    <span id="buttonFullRules">������� ���������</span>
                                                </div>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="clear"></div>

                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <div class="wrapper">
        <div class="ears-content">
            <div class="formContent">
                <div class="formContentMargin">
                    <tiles:insert page="/WEB-INF/jsp-sbrf/common/layout/messages.jsp">
                        <tiles:put name="bundle" type="string" value="messagesBundle"/>
                    </tiles:insert>
                    <p class="contentHeader">${contentHeader}</p>
                    ${content}
                </div>
            </div>
        </div>
        <div class="base">
            <div class="main_row">
                <div class="content_row"></div>
            </div>
            <!--ears-s/ears-b-->
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
            &copy 1997 � 2015 �������� ������, ������, ������, 117997, ��. ��������, �.19, ���. +7(495) 500
            5550 <br/>
            8 800 555 5550. ����������� �������� �� ������������� ���������� �������� �� 8 ������� 2012
            ����. <br/>
            ��������������� ����� - 1481.
            <a class="icon" href="http://www.sberbank.ru/moscow/ru/important/"><span>�������� �����</span>.</a>
        </div>
    </div>
</div>
<div id="loading" style="left:-3300px;">
    <div id="loadingImg"><img src="${globalUrl}/commonSkin/images/ajax-loader64.gif"/></div>
    <span>����������, ���������,<br/> ��� ������ ��������������.</span>
</div>
</div>
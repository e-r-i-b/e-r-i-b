<%--
  User: basharin
  Date: 22.01.2014

  �������� ��������������� �����������. ����� �������.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="person" value="${phiz:getPersonInfo()}"/>
<c:set var="form" value="${SelfRegistrationForm}"/>
<c:set var="urlLogoff" value="${phiz:calculateActionURL(pageContext,'/logoff.do?toLogin=true')}"/>
<c:set var="urlLater" value="${phiz:calculateActionURL(pageContext,'/private/accounts.do')}"/>
<c:set var="isActiveCaptha" value="${phiz:isActiveCaptha(pageContext.request, 'selfRegistrationCaptchaServlet')}"/>
<c:set var="captchaUrl" value="${phiz:calculateActionURL(pageContext,'/registration/captcha')}"/>
<c:set var="captchaUrl" value="${fn:replace(captchaUrl, '.do', '.png')}"/>

<!DOCTYPE HTML>
<!--[if IE 6 ]>
<html lang="ru-RU" class="ie678 ie6"><![endif]-->
<!--[if IE 7 ]>
<html lang="ru-RU" class="ie678 ie7"><![endif]-->
<!--[if IE 8 ]>
<html lang="ru-RU" class="ie678 ie8"><![endif]-->
<!--[if IE 9 ]>
<html lang="ru-RU" class="ie9"><![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="ru-RU"><!--<![endif]-->
<head>
    <meta charset="windows-1251">
    <title><bean:message key="application.title" bundle="commonBundle"/></title>
    <link rel="icon" type="image/x-icon" href="${imagePath}/favicon.ico"/>
    <link rel="stylesheet" href="${skinUrl}/selfRegistration/common.css"/>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/selfRegistration/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/selfRegistration/main.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/windows.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/DivFloat.js"></script>
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>
</head>
<body onclick="return{token:'xxxxxxxx'}">
<div  id="workspace">
<tiles:insert definition="googleTagManager"/>
<input type="hidden" name="PAGE_TOKEN" value="${form.pageToken}"/>
<c:if test="${form.hintDelay != 0}">
    <input type="hidden" name="hintDelay" value="${form.hintDelay}"/>
</c:if>
<div id="loading" style="left:-3300px;">
    <div id="loadingImg"><img src="${imagePath}/ajax-loader64.gif" alt="��������"/></div>
    <span>����������, ���������,<br/> ��� ������ ��������������.</span>
</div>
<div class="g-wrapper" id="workspaceCSA">

<div class="g-header">
    <div class="g-header_inner">

        <div class="b-logo">
            <a class="logo_link" href="${urlLater}">�������� ������</a>
        </div>
        <!-- // b-logo -->

        <div class="b-phones">
            <span class="phones_item">+7 (495) <b>500-55-50</b></span>
            <span class="phones_item">8 (800) <b>555-55-50</b></span>
        </div>
        <!-- // b-phones -->

        <div class="b-login">
            <span class="login_user"><c:out value="${phiz:getFormattedPersonFIO(person.firstName, person.surName, person.patrName)}"/></span>
            <a class="login_out" href="${urlLogoff}">
                <span class="link">�����</span> <i class="login_out-pic"></i>
            </a>
        </div>
        <!-- // b-login -->

    </div>
    <!-- // g-header_inner -->
</div>
<!-- // g-header -->
<div class="g-main">
    <div class="g-main_inner">

        <div class="b-steps">
            <div class="steps_list">
                <div class="steps_item first current">
                    <span class="steps_text">�������� ������</span>
                    <i class="steps_border"></i>
                </div>
                <div class="steps_item last">
                    <span class="steps_text">������������� �� SMS</span>
                    <i class="steps_border"></i>
                </div>
                <div class="steps_mark">
                    <div class="steps_oval">
                        <i class="steps_round"></i>
                        <b class="steps_num">1</b>
                    </div>
                    <i class="steps_ico"></i>
                </div>
            </div>
        </div><!-- // b-steps -->
        <div class="g-content">

            <div class="b-slider">
                <div class="slider_outer">
                    <div class="slider_inner">
                        <div class="slider_page" id="SliderAuthData" style="visibility:visible">

                            <div class="b-page b-reg">
                                <div class="page_head">
                                    <div class="page_head-wrapper">

                                        <h2 class="b-title">
                                            <span class="title_text">���������� ����� <br/>�&nbsp;������</span>
                                        </h2><!-- // b-title -->

                                    </div>
                                </div>
                                <div class="page_inner">
                                    <div class="page_inner-wrapper">

                                        <form class="b-form b-auth-form" id="AuthData" onclick="return{step:0, actionPath:'async/registration.do', captcha: ${isActiveCaptha}, operation: 'save'}">
                                            <div class="form_inner">

                                                <div class="b-field" id="Login"
                                                     onclick="return{type: 'login', valid:{required: true, maxLen: 30, minLen: ${form.minLoginLength}, letter: true, number: true, key3: true, let3: true }, avail: {path: 'async/registration.do', data:{operation: 'checkLogin'}}}">
                                                    <div class="field_inner">
                                                        <input class="field_input" id="field(login)" name="field(login)" type="text"
                                                               placeholder="����� �����" autocomplete="off" value="${form.fields.login}"/>
                                                    </div>
                                                </div>
                                                <!-- // b-field -->

                                                <div class="b-field" id="Password"
                                                     onclick="return{type: 'password', valid:{required: true, maxLen: 30, minLen: 8, letter: true, number: true, key3: true, let3: true}, differ: 'Login'}">
                                                    <div class="field_inner">
                                                        <input class="field_input" id="field(password)" name="field(password)" type="password"
                                                               placeholder="������� ����� ������" autocomplete="off"/>
                                                    </div>
                                                </div>
                                                <!-- // b-field -->

                                                <div class="b-field" id="RepeatPassword"
                                                     onclick="return{type: 'password', valid:{required: true, maxLen: 30, minLen: 8}, compare:'Password'}">
                                                    <div class="field_inner">
                                                        <input class="field_input" id="field(confirmPassword)" name="field(confirmPassword)"
                                                               type="password" placeholder="��������� ������" autocomplete="off"/>
                                                    </div>
                                                </div>
                                                <!-- // b-field -->
                                                <c:if test="${form.needShowEmailAddress}">
                                                    <div class="b-field" id="Email" onclick="return{type:'email', valid:{required: true, maxLen: 30}}">
                                                        <div class="field_inner">
                                                            <input class="field_input" id="field(email)" name="field(email)" type="text"
                                                                   placeholder="E-mail" autocomplete="off"/>
                                                        </div>
                                                    </div>
                                                </c:if>
                                                <!-- // b-field -->

                                                <div class="b-captcha" id="AuthDataCaptcha"
                                                     onclick="return{valid:{required: true}, replace: /[^\w�-��-�]/gi, url: '${captchaUrl}?=<%=java.lang.Math.round(java.lang.Math.random() * 100000)%>'}"
                                                     style="display:none">
                                                    <div class="captcha_pic"><img class="captcha_image" src="" alt=""/></div>
                                                    <div class="captcha_refresh">
                                                        <div class="captcha_ico"></div>
                                                        <span class="lnk">�������� ���</span>
                                                    </div>
                                                    <div class="field_inner">
                                                        <input class="field_input" id="field(captchaCode)" name="field(captchaCode)"
                                                               type="text" placeholder="��� � ��������" autocomplete="off"/>
                                                    </div>
                                                </div>
                                                <!-- // b-captcha -->

                                                <div class="b-btn disabled" id="AuthDataSubmit">
                                                    <div class="btn_text">����������</div>
                                                    <i class="btn_crn"></i>
                                                    <input class="btn_hidden" type="submit"/>
                                                </div>
                                                <!-- // b-btn -->

                                                <c:choose>
                                                    <c:when test="${not form.hardRegistrationMode}">
                                                        <div class="b-back">
                                                            <a class="back_text" onclick="showOrHideAjaxPreloader();location.href = '${urlLater}'">��������� �����</a>
                                                        </div>
                                                        <!-- // b-back -->

                                                        <div class="b-trigger" data-popup="CancelReasonPopup">
                                                            <span class="dot">���������� �� �������� ������ �&nbsp;������</span>
                                                        </div>
                                                        <!-- // b-back -->
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="b-back">
                                                            <a class="back_text" href="${urlLogoff}">�������� � �����</a>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <i class="form_pic"></i>
                                        </form>
                                        <!-- // b-sms-form -->

                                    </div>
                                </div>
                                <div class="page_content">
                                    <div class="page_content-wrapper">

                                        <div class="b-helper" onclick="return{show: $('.b-reg-helper'), interval: 7000}">
                                            <div class="helper_inner">
                                                ������ �� ������ ���������� ���� ����������� ����� �&nbsp;������. ��� ������� ���� ������������, ��������� ����� �&nbsp;����������� �������� ���� �&nbsp;�������� ������.
                                            </div>
                                            <i class="helper_arr"></i>
                                        </div><!-- // b-helper -->

										<div class="b-reg-helper" style="display:none">
                                            <h3 class="reg-helper_head">������� �������</h3>

                                            <div class="reg-helper_inner">
                                                <div class="reg-helper_item">
                                                    <h4 class="reg-helper_title">sberbank21</h4>

                                                    <p class="reg-helper_description">����� �&nbsp;������ ������ ��������� ����� �&nbsp;�����.
                                                                                      ������� 8&nbsp;��������.</p>
                                                    <img class="reg-helper_pic" src="${imagePath}/selfRegistration/qwer.png" alt="" width="114" height="44"/>

                                                    <p class="reg_description">������ ������������ ����� 3-� �������� ������ �&nbsp;����� ����
                                                                               ����������.</p>
                                                </div>
                                                <div class="reg-helper_item">
                                                    <h4 class="reg-helper_title">A=a</h4>

                                                    <p class="reg-helper_description">����� � ������ ��&nbsp;������������� �&nbsp;��������</p>
                                                </div>
                                            </div>
                                            <div class="reg-helper_extend b-trigger" data-show="showRules">
                                                <span class="dash">������� ���������</span>
                                            </div>
                                        </div>
                                        <!-- // b-reg-helper -->

                                    </div>
                                    <div class="page_content-rules" style="display:none">

                                        <div class="b-rules" id="AuthRules">
                                        	<h3 class="rules_title">������� ����������� ������ �&nbsp;������</h3>

                                        	<div class="rules_cols">
                                        		<div class="rules_col">
                                        			<h4 class="rules_col-title">�����</h4>
                                        			<ul class="rules_list">
                                        				<li class="rules_item">����� ��&nbsp;8&nbsp;��&nbsp;30 ��������.</li>
                                        				<li class="rules_item">����� ������ ��������� ��� ������� ���� ����� �&nbsp;��� ������� ���� �����.
                                        				</li>
                                        				<li class="rules_item">����� ������ ���� ������ ��&nbsp;���������� ��������.</li>
                                        				<li class="rules_item">�� ����� �������� ��&nbsp;10&nbsp;����.</li>
                                        				<li class="rules_item">�� ������ ��������� ����� 3-� ���������� �������� ������.</li>
                                        				<li class="rules_item">����� ��������� �������� ���������� ��&nbsp;������ � �&nbsp;�&nbsp;@&nbsp;_&nbsp;-&nbsp;.�</li>
                                        				<li class="rules_item">�� ������������ �&nbsp;��������.</li>
                                        			</ul>
                                        		</div>
                                        		<div class="rules_col">
                                        			<h4 class="rules_col-title">������</h4>
                                        			<ul class="rules_list">
                                        				<li class="rules_item">����� ��&nbsp;����� 8 ��������.</li>
                                        				<li class="rules_item">������ ������ ��������� ��� ������� ���� ����� �&nbsp;��� ������� ����
                                        									   �����.</li>
                                        				<li class="rules_item">�� ������ ��������� ����� 3-� ���������� �������� ������, �&nbsp;�����
                                        									   3-� ��������, ������������� ����� �&nbsp;����� ���� ����������.
                                        				</li>
                                        				<li class="rules_item">������ ���������� ��&nbsp;������.</li>
                                        				<li class="rules_item">����� ��������� �������� ���������� ��&nbsp;������ �&nbsp;�&nbsp;!&nbsp;@&nbsp;#&nbsp;$&nbsp;%&nbsp;^&nbsp;&&nbsp;*&nbsp;(&nbsp;)&nbsp;_&nbsp;-&nbsp;+&nbsp;:&nbsp;;&nbsp;,&nbsp;.&nbsp;�</li>
                                        				<li class="rules_item">�� ������ ��������� ������ ������ ��&nbsp;��������� 3 ������.
                                        				</li>
                                        			</ul>
                                        		</div>
                                        	</div>

                                        	<div class="b-return b-trigger" data-show="showHelper"><i class="return_pic"></i></div>

                                        </div><!-- // b-rules -->


                                    </div>
                                    <div class="page_content-errors" style="display:none">

                                    </div>
                                </div>
                            </div>
                            <!-- // b-page -->

                        </div>
                        <div class="slider_page" id="SliderSmsConfirm" style="visibility:hidden">

                            <div class="b-page b-confirm">
                                <div class="page_head">
                                    <div class="page_head-wrapper">

                                        <h2 class="b-title">
                                            <span class="title_text">����������� ��������� <br/>SMS-�������
                                                <i class="b-timer"></i>
                                            </span>
                                        </h2><!-- // b-title -->

                                    </div>
                                </div>
                                <div class="page_inner">
                                    <div class="page_inner-wrapper">

                                        <form class="b-form b-confirm-form" id="SmsConfirm" onclick="return{step:1, actionPath:'async/registration.do', captcha: false, operation:'sms'}">
                                            <div class="form_inner">

                                                <div class="b-field" id="Sms"
                                                     onclick="return{replace: /[^\d]/gi, valid:{required: true, maxLen: 5, minLen: 5}}">
                                                    <div class="field_inner">
                                                        <input class="field_input" id="$$confirmSmsPassword" name="$$confirmSmsPassword" type="text"
                                                               placeholder="SMS-������" maxlength="5" autocomplete="off"/>
                                                    </div>

                                                    <div class="b-action b-action-sms" onclick="return{actionPath:'async/registration.do', data:{operation:'sendNewSms'}}">
                                                        <div class="action_refresh">
                                                            <i class="action_ico"></i>
                                                            <span class="link">������� ����� SMS-������</span>
                                                        </div>
                                                    </div>
                                                    <!-- // b-action-sms -->

                                                </div>
                                                <!-- // b-field -->

                                                <div class="b-btn disabled" id="SmsConfirmSubmit">
                                                    <div class="btn_text">�����������</div>
                                                    <i class="btn_crn"></i>
                                                    <input class="btn_hidden" type="submit"/>
                                                </div>
                                                <!-- // b-btn -->

                                            </div>
                                            <i class="form_pic"></i>
                                        </form>
                                        <!-- // b-sms-form -->

                                    </div>
                                </div>
                                <div class="page_content">
                                    <div class="page_content-wrapper">

                                        <div class="b-sms-helper">
                                            <h3 class="sms-helper_head">������ ��&nbsp;�������� SMS-������?</h3>

                                            <div class="sms-helper_inner">
                                                <div class="sms-helper_item">
                                                    <img class="sms-helper_pic" src="${imagePath}/selfRegistration/gray_phones.png" alt="" width="58" height="53"/>
                                                    <h4 class="sms-helper_title">����� �������� ������ ���� �������� �&nbsp;�����</h4>

                                                    <p class="sms-helper_description">��������� ����� &laquo;�������&raquo; ��&nbsp;����� 900, �&nbsp;��������
                                                                                      ��������� ��� ������ ���������� �&nbsp;������� �����������
                                                                                      �&nbsp;���������� �����</p>
                                                </div>
                                                <div class="sms-helper_item">
                                                    <img class="sms-helper_pic" src="${imagePath}/selfRegistration/card.png" alt="" width="62" height="53"/>
                                                    <h4 class="sms-helper_title">����� ������ ���� �������� ����������</h4>

                                                    <p class="sms-helper_description">��������� ��� ��&nbsp;������� ������� ����� �����
                                                                                      ���������� ������� ���������</p>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- // b-sms_helper -->

                                    </div>
                                    <div class="page_content-rules" style="display:none">

                                    </div>
                                    <div class="page_content-errors" style="display:none">

                                        <div class="b-message" id="IncorrectSMS">
                                            <h3 class="sys-error_title">��&nbsp;����� �������� SMS-������, �&nbsp;��� �������� {sms_try}</h3>

                                            <div class="moved">
                                                <p>���������, ��� ��&nbsp;��������� ������� ����������� SMS-������ ��&nbsp;���������. ������
                                                   ������ SMS:</p>

                                                <p>&laquo;�������� ������. ������ ��� ������������� ��������� �����������&nbsp;&mdash;
                                                          12345&raquo;</p>
                                            </div>
                                        </div>
                                        <!-- // b-message -->

                                    </div>
                                </div>
                            </div>
                            <!-- // b-page -->

                        </div>
                    </div>
                </div>
            </div><!-- // b-slider -->

        </div><!-- // g-content -->
    </div><!-- // g-main-inner -->

    <div class="b-svg" id="Svg"></div><!-- // b-svg -->

</div>
<!-- // g-main -->
<div class="g-footer">
    <div class="g-footer_inner">

        <div class="b-copy">
            <div class="copy_name">� 1997�2015 �������� ������</div>
            <div class="copy_description">������, ������, 117997, ��. ��������, �. 19,<br/>����������� �������� ��
                                          ������������� ���������� �������� �� 8 ������� 2012 ����<br/>���������������
                                          ����� � 1481
            </div>
        </div>
        <!-- // b-copy -->

        <div class="b-social">
            <div class="social_title">�� � ���������� �����</div>
            <div class="social_links">
                <a class="social_link tw" href="https://twitter.com/sberbank/" target="_blank"></a>
                <a class="social_link vk" href="http://vk.com/bankdruzey" target="_blank"></a>
                <a class="social_link fb" href="https://www.facebook.com/bankdruzey" target="_blank"></a>
                <a class="social_link ok" href="http://www.odnoklassniki.ru/bankdruzey" target="_blank"></a>
                <a class="social_link yt" href="http://www.youtube.com/sberbank" target="_blank"></a>
            </div>
        </div>
        <!-- // b-social -->

    </div>
    <!-- // g-footer-inner -->
</div>
<!-- // g-footer -->
</div>
<!-- // g-wrapper -->

<div class="g-overlay">

    <div class="b-popup" id="CancelReasonPopup">
        <div class="popup_inner">

            <form class="b-cancel-form" action="" id="CancelReason">
                <h2 class="cancel-form_title">� ��&nbsp;���� ��������� ����� ����� �&nbsp;������, <br/>������ ���:</h2>
                <input type="hidden" name="operation" value="cancelReason"/>
                <input type="hidden" name="PAGE_TOKEN" value="${form.pageToken}"/>
                <div class="cancel-form_line">
                    <label class="cancel-form_row">
                        <input class="cancel-form_radio" type="radio" name="reason" value="1"/>
                        ��� ������ ������������ �������� ��������������� �&nbsp;������� � ����.
                    </label>
                </div>
                <div class="cancel-form_line">
                    <label class="cancel-form_row">
                        <input class="cancel-form_radio" type="radio" name="reason" value="2"/>
                        �� ���� ������.
                    </label>
                </div>
                <div class="cancel-form_line">
                    <label class="cancel-form_row">
                        <input class="cancel-form_radio" type="radio" name="reason" value="3"/>
                        ��� �� �� �������� ������� ��&nbsp;��� �����.
                    </label>
                </div>
                <div class="cancel-form_line">
                    <label class="cancel-form_row">
                        <input class="cancel-form_radio" type="radio" name="reason" value="4"/>
                        �� �������, ���� ��&nbsp;���� �����.
                    </label>
                </div>
                <div class="cancel-form_line">
                    <label class="cancel-form_row">
                        <input class="cancel-form_radio" type="radio" name="reason" value="5"/>
                    </label>

                    <div class="cancel-form_area">
                        <textarea name="another" id="another" placeholder="������ �������"></textarea>
                    </div>
                </div>
                <script type="text/javascript">
                    $(document).ready(function()
                    {
                        initAreaMaxLengthRestriction("another", 250);
                    });
                </script>
                <div class="cancel-form_bottom">

                    <div class="b-back">
                        <button class="back_text" type="reset">��������</button>
                    </div><!-- // b-back -->

                    <div class="b-btn disabled" id="CancelReasonSubmit">
                        <div class="btn_text">���������</div>
                        <i class="btn_crn"></i>
                        <input class="btn_hidden" type="submit" disabled>
                    </div><!-- // b-btn -->

                </div>
            </form><!-- // b-cancel-form -->

        </div>
        <a class="popup_close"></a>
    </div><!-- // b-popup -->

    <div class="b-popup" id="SysError">
        <div class="popup_inner">

            <div class="b-message">
                <h1>������ ����������</h1>
                <p>�� ������� ������������ � �������. ���������� �����</p>
            </div><!-- // b-message -->

        </div>
        <a class="popup_close"></a>
    </div><!-- // b-popup -->

    <div class="b-popup" id="CustomMessage">
        <div class="popup_inner">

            <div class="b-message"></div><!-- // b-message -->

        </div>
        <a class="popup_close"></a>
    </div><!-- // b-popup -->

</div><!-- // g-overlay -->
</div>
</body>
</html>
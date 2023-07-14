<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="csa" uri="http://rssl.com/tags/csa" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<tiles:useAttribute name="formId"/>
<tiles:useAttribute name="form"/>
<tiles:insert definition="registration" flush="false">
    <tiles:put name="data" type="string">
        <tiles:insert definition="step-form" flush="false">
            <tiles:put name="id" type="string" value="${formId}"/>
            <tiles:put name="steps">
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="�������� �����"/>
                </tiles:insert>
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="������������� �� SMS"/>
                    <tiles:put name="current" value="true"/>
                </tiles:insert>
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="����� � ������"/>
                </tiles:insert>
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="������ � �������-�����"/>
                    <tiles:put name="abs" value="true"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="data" type="string">
                <div class="actions_item">
                    <i class="actions_cnt">3</i>
                    <c:set var="attempts" value="${form.operationInfo.confirmParams['Attempts']}"/>
                    <c:set var="timeout" value="${form.operationInfo.confirmParams['Timeout']}"/>
                    <h2 class="actions_title">������� SMS-������. <c:if test="${not empty attempts}">�������� �������: ${attempts}</c:if></h2>
                    <div class="actions_description">SMS-������ ��������� �� ��������� �������, ����������� � ����� <b>${csa:getCutCardNumber(form.operationInfo.cardNumber)}</b>.</div>
                    <!-- ������ � ��������, ���������� ��� <= 120s -->
                    <c:if test="${not empty timeout}">
                        <div id="SmsTimer" class="actions_timer" onclick="return{time: ${timeout}}">
                            <span class="current">����� �������� ������ ��������: <b class="time"></b></span>
                        </div>
                    </c:if>

                    <div class="b-sms">
                        <i class="sms_bg"></i>
                        <tiles:useAttribute name="action"/>
                        <html:form styleId="${formId}" action="${action}" styleClass="form sms_form">
                            <div class="sms_block">
                                <label for="SmsInput" class="label">SMS-������</label>
                                <!-- ���� ����� ������������� ���� � ������� � ������, ����� � onclick ������� �������� �������� error c ������� ������ -->
                                <div class="field" onclick="return{type:'sms', validMap: ['required']}">
                                    <input id="SmsInput" type="text" class="input" autocomplete="off" maxlength="7" name="field(confirmPassword)"/>
                                    <i class="field_ico"></i>
                                </div>
                            </div>

                            <tiles:insert definition="nextButtonReg" flush="false">
                                <tiles:put name="text" value="�����������"/>
                                <tiles:put name="commandKey" value="button.next"/>
                            </tiles:insert>
                            <div class="sms_helper">
                                <!-- �� ����� ����� ���������� ����� � �����. ID -->
                                <div class="dot" onclick="utils.showPopup('SmsDoesNotCome')">SMS-������ ��&nbsp;��������</div>
                            </div>

                        </html:form>
                    </div><!-- // b-sms -->

                </div>
            </tiles:put>
        </tiles:insert>

        <c:set var="recoverPasswordUrl" value="${csa:calculateActionURL(pageContext, '/index')}?form=recover-password-form"/>
        <c:set var="loginUrl" value="${csa:calculateActionURL(pageContext, '/index')}?form=login-form"/>
        <tiles:put name="overlay" type="string">
            <csa:popupCollection defaultUrl="${csa:calculateActionURL(pageContext, action)}">
                <csa:popupItem id="SmsDoesNotCome" page="/WEB-INF/jsp/auth/registration/popups/lost-sms.jsp"/>
                <csa:popupItem id="Registered" closable="false">
                    <html:form action="${action}" style="display:none">
                        <input id="RegisteredFormSubmit" type="submit" name="operation" value="button.next" style="position:absolute; opacity: 0">
                    </html:form>
                    <h1 class="m25">�� ��� ��������� ������������� ��������� ������</h1>
                    <p>���� ��&nbsp;������� ���� <i>����� �&nbsp;������</i>, ��������� ��&nbsp;<a href="${loginUrl}">�������� ����� �&nbsp;��������� ������</a>.</p>
                    <p class="m25">���� ��&nbsp;<i>������ ������</i>&nbsp;&mdash; �������������� <a href="${recoverPasswordUrl}">��������������� ������</a>.</p>
                    <div class="moved m4">
                        <i class="posit">����</i>
                        <p><a href="#" onclick="$('#RegisteredFormSubmit').click();return false;">�������� ����� ����� �&nbsp;������</a> ��&nbsp;��������� ����. ����������� ��������� ������ ����� �������� �&nbsp;�������������. ����������� ����� �����, ���������� ����� �������� ������� �&nbsp;���������� ����� ���������.</p>
                    </div>
                </csa:popupItem>
                <c:set var="passRecoveryHelpText"><bean:message bundle="commonBundle" key="text.password.recovery.help"/></c:set>
                <csa:popupItem id="alreadyRegRestrict" onclose="location.href='${recoverPasswordUrl}';">
                    ${fn:replace(passRecoveryHelpText, '%rPU%', recoverPasswordUrl)}
                </csa:popupItem>
            </csa:popupCollection>
        </tiles:put>
    </tiles:put>
</tiles:insert>

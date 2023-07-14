<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

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
                </tiles:insert>
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="����� � ������"/>
                    <tiles:put name="current" value="true"/>
                </tiles:insert>
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="������ � �������-�����"/>
                    <tiles:put name="abs" value="true"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="data" type="string">
                <div class="actions_item">
                    <i class="actions_cnt">4</i>
                    <h2 class="actions_title">���������� ����� �&nbsp;������</h2>
                    <div class="actions_description">��� ����� ������������� ��� ����� �&nbsp;��������� ������.</b></div>

                    <div class="b-login">
                        <div class="login_helper">
                            <span class="dot"  onclick="utils.showPopup('Rules')">������� ����������� ������ �&nbsp;������</span>
                        </div>
                        <tiles:useAttribute name="action"/>
                        <html:form styleId="${formId}" action="${action}" styleClass="form login_form">
                            <i class="login_bg"></i>
                            <div class="login_block">
                                <div class="login_row">
                                    <label for="field(login)" class="label">�����:</label>
                                    <div class="field" onclick="return{ validMap: ['required', 'login', 'decAndLetter','sameSymbols', 'nearbyKeys'], format:'lowercase', minlength: 8, maxlength: 30}">
                                        <c:set var="login" value="${form.fields['login']}"/>
                                        <input name="field(login)" type="text" class="input" autocomplete="off"
                                               value="<c:out value='${login}'/>"/>
                                        <i class="field_ico"></i>
                                    </div>
                                </div>
                                <div class="login_row">
                                    <label for="field(password)" class="label">������:</label>
                                    <div class="field" onclick="return{ validMap: ['required', 'password','decAndLetter','sameSymbols', 'nearbyKeys'], minlength: 8}">
                                        <input name="field(password)" type="password" class="input" autocomplete="off"/>
                                        <i class="field_ico"></i>
                                    </div>
                                </div>
                                <div class="login_row">
                                    <label for="field(confirmPassword)" class="label">������������� ������:</label>
                                    <div class="field" onclick="return{ validMap: ['required', 'password','decAndLetter','sameSymbols', 'nearbyKeys'], compare:'field(password)', minlength: 8}">
                                        <input name="field(confirmPassword)" type="password" class="input" autocomplete="off"/>
                                        <i class="field_ico"></i>
                                    </div>
                                </div>
                            </div>
                            <c:set var="isActiveCaptha" value="${csa:isActiveCaptha(pageContext.request, 'registrationCaptchaServlet')}"/>
                            <c:if test="${isActiveCaptha}">
                                <tiles:insert page="/WEB-INF/jsp/common/captcha.jsp" flush="false">
                                    <tiles:put name="id" value="Captcha"/>
                                    <tiles:put name="show" value="true"/>
                                    <tiles:put name="url" value="registration/captcha.png"/>
                                </tiles:insert>
                            </c:if>

                            <tiles:insert definition="nextButtonReg" flush="false">
                                <tiles:put name="text" value="����������"/>
                                <tiles:put name="commandKey" value="button.next"/>
                                <c:if test="${isActiveCaptha}">
                                    <tiles:put name="additionClass" value="afterCaptchaBlock"/>
                                </c:if>
                            </tiles:insert><!-- // b-btn -->
                        </html:form>
                    </div><!-- // b-login -->
                </div><!-- // b-login -->
            </tiles:put>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="overlay" type="string">
        <c:set var="loginUrl" value="${csa:calculateActionURL(pageContext, '/index')}?form=login-form"/>
        <csa:popupCollection defaultUrl="${csa:calculateActionURL(pageContext, action)}">
            <csa:popupItem id="Rules" page="/WEB-INF/jsp/auth/registration/popups/rules.jsp"/>
            <csa:popupItem id="Complete" onclose="location.href='${loginUrl}';">
                �� ������� ���������������� � ������� �������� ������.
            </csa:popupItem>
            <csa:popupItem id="CaptchaMessage">
                ��� ����������� ������ � �������� �������� ��� ���� � ������� ���, ������������ �� ��������.
            </csa:popupItem>
        </csa:popupCollection>
    </tiles:put>
</tiles:insert>
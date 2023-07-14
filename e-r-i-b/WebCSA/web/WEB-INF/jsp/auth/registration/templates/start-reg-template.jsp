<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="csa" uri="http://rssl.com/tags/csa" %>

<tiles:useAttribute name="formId"/>
<tiles:useAttribute name="form"/>

<tiles:insert definition="registration" flush="false">
    <tiles:put name="data" type="string">
        <tiles:insert definition="step-form" flush="false">
            <tiles:put name="id" type="string" value="${formId}"/>
            <tiles:put name="steps">
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="�������� �����"/>
                    <tiles:put name="current" value="true"/>
                </tiles:insert>
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="������������� �� SMS"/>
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
                    <i class="actions_cnt">1</i>
                    <h2 class="actions_title">��������� ����� �&nbsp;��������� �������</h2>
                    <div class="actions_cols">
                        <div class="actions_col">
                            <img src="${skinUrl}/skins/sbrf/images/csa/reg-col_1.png" alt="" class="actions_pic"/>
                            <div class="actions_description">�&nbsp;��� �&nbsp;����� ������ ���� <br/><b>�������� ����������� ����� <br/>���������.</b></div>
                        </div>
                        <div class="actions_col">
                            <img src="${skinUrl}/skins/sbrf/images/csa/reg-col_2.png" alt="" class="actions_pic"/>
                            <div class="actions_description">�������, ��&nbsp;������� ��������� <br/><b>��������� ����</b>, ������ ���� ��� �����&nbsp;&mdash;<br/>��&nbsp;���� ������ sms-������.</div>
                        </div>
                    </div>
                </div>
                <div class="actions_item">
                    <i class="actions_cnt">2</i>
                    <h2 class="actions_title">������� ����� �����</h2>
                    <div class="b-reg">
                        <i class="reg_bg"></i>
                        <tiles:useAttribute name="action"/>
                        <html:form styleId="${formId}" action="${action}" styleClass="form reg_form">
                            <div class="reg_block">
                                <div class="field" onclick="return{type:'card', validMap:['required', 'luhn']}">
                                    <input id="RegInput" type="text" name="field(cardNumber)" class="input" autocomplete="off" tabindex="1"/>
                                    <i class="field_ico"></i>
                                </div>
                            </div>
                            <div class="reg_description">����� ����� <br/>���������� ��&nbsp;�� <br/>������� �������.</div>
                            <!-- ���� � ������ -->
                            <c:set var="isActiveCaptha" value="${csa:isActiveCaptha(pageContext.request, 'captchaServlet')}"/>
                            <c:if test="${isActiveCaptha}">
                                <tiles:insert page="/WEB-INF/jsp/common/captcha.jsp" flush="false">
                                    <tiles:put name="id" value="Captcha"/>
                                    <tiles:put name="show" value="true"/>
                                    <tiles:put name="url" value="captcha.png"/>
                                </tiles:insert>
                            </c:if>
                            <tiles:insert definition="nextButtonReg" flush="false">
                                <tiles:put name="text" value="����������"/>
                                <tiles:put name="commandKey" value="button.begin"/>
                                <tiles:put name="tabindex" value="4"/>
                                <c:if test="${isActiveCaptha}">
                                    <tiles:put name="additionClass" value="afterCaptchaBlock"/>
                                </c:if>
                            </tiles:insert>
                        </html:form>
                    </div><!-- // b-reg -->

                </div>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="overlay" type="string">
        <csa:popupCollection>
        </csa:popupCollection>
    </tiles:put>
</tiles:insert>
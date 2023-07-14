<%--
  User: bogdanov
  Date: 11.09.2012
  Time: 9:48:13

  �������� � ����������, ��� ������ �������
  �������� ������ � CSABack
  ����� ���� ��� ������ ������� ���������� ��������.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute/>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<c:set var="titleText"><bean:message bundle="commonBundle" key="text.SBOL.changePassword"/></c:set>


    <tiles:insert definition="login">
        <tiles:put name="headerGroup" value="true"/>
        <tiles:put name="pageTitle" type="string" value="${titleText}"/>
        <tiles:put type="string" name="data">
            <html:form action="/login/checkOldPassword" styleClass="CheckOldPasswordForm" show="true" onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();">
                <tiles:insert page="../common/layout/messages.jsp" flush="false">
                    <tiles:importAttribute name="messagesBundle" ignore="true"/>
                    <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
                    <c:set var="bundleName" value="${messagesBundle}"/>
                </tiles:insert>

                <br/>
                <tiles:insert definition="warningBlock" flush="false">
                    <tiles:put name="regionSelector" value="warnings"/>
                    <tiles:put name="isDisplayed" value="true"/>
                    <tiles:put name="data">
                        ����� �������� ������ ������ �������. ����������, ������� ����� ������.
                    </tiles:put>
                </tiles:insert>
                <br/>
                <tiles:insert definition="roundBorder" flush="false">
                    <tiles:put name="color" value="greenTop"/>
                    <tiles:put name="title">����� ������</tiles:put>
                    <tiles:put name="data">

                        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/complexValueIndicator.js"></script>

                        <br>

                        <table class="shield">
                            <tr>
                                <td><html:img src="${imagePath}/red_shield.png"/></td>
                                <td class="iconsTitle">��������� ������ ����� � �������.</td>
                            </tr>
                        </table>

                        <table class="widthAuto alignAuto">
                            <tr>
                                <td class="text-align-right field">������� ����� ������:</td>
                                <td width="80%" class="newPassword">
                                    <html:password property="pswd" styleId="pswd" styleClass="login" maxlength="30" value="" style="width: 210px;"/>
                                    <a onclick="javascript:win.open('recomendation');" href="#"><bean:message bundle="userprofileBundle" key="label.password.help"/></a>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td style="padding: 5px 10px;">
                                    <tiles:insert definition="complexIndicator" flush="false">
                                        <tiles:put name="id" value="passwordField"/>
                                    </tiles:insert>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-align-right field">��������� ����� ������:</td>
                                <td style="padding: 5px 0 5px 10px;">
                                    <html:password property="pswd2" styleId="pswd2" styleClass="login" maxlength="30" value="" style="width: 210px;"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-align-right field"></td>
                                <td style="padding: 5px 0 5px 10px;">
                                    <html:checkbox styleId="showPassword" property="showPassword" value="true"/>���������� ������
                                </td>
                            </tr>
                        </table>
                        <tiles:insert definition="window" flush="false">
                            <tiles:put name="id" value="recomendation"/>
                            <tiles:put name="styleClass" value="otherRegions"/>
                            <tiles:put name="data" type="string">
                                <bean:message key="password.recomendationForGood" bundle="securityBundle"/>
                                <ul style="list-style:disc; margin-left:15px;">
                                    <bean:message key="password.recomendationForGood.list" bundle="securityBundle"/>
                                </ul>
                                <div class="buttonsArea">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey"    value="button.close"/>
                                        <tiles:put name="commandHelpKey"    value="button.close"/>
                                        <tiles:put name="bundle"            value="securityBundle"/>
                                        <tiles:put name="viewType"          value="simpleLink"/>
                                        <tiles:put name="onclick"           value="win.close('recomendation');"/>
                                    </tiles:insert>
                                </div>
                            </tiles:put>
                        </tiles:insert>

                        <div class="buttonsArea">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey"    value="button.cancel"/>
                                <tiles:put name="commandHelpKey"    value="button.cancel"/>
                                <tiles:put name="bundle"            value="securityBundle"/>
                                <tiles:put name="viewType"          value="simpleLink"/>
                                <tiles:put name="action"            value="/logoff.do?toLogin=true"/>
                            </tiles:insert>

                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.save"/>
                                <tiles:put name="commandHelpKey" value="button.save"/>
                                <tiles:put name="bundle" value="securityBundle"/>
                                <tiles:put name="isDefault" value="true"/>
                                <tiles:put name="enabled" value="false"/>
                                <tiles:put name="id" value="nextButton"/>
                            </tiles:insert>
                        </div>
                        <script type="text/javascript">
                            $(document).ready(function(){

                                var indicator = new Indicator("passwordField", stateComplexPasswordCsa, enableNextButton);
                                indicator.init("pswd");

                                $("#showPassword").change(function(){
                                    showHidePassword('pswd', this.checked);
                                    showHidePassword('pswd2', this.checked);
                                });
                            });

                            //������ ������ "���������" ���������� � ������ ����������� ������
                            function enableNextButton(state)
                            {
                                var nextButton = $('#nextButton')[0];
                                if(state == "green")
                                {
                                    nextButton.onclick = function(){findCommandButton('button.save').click('', false);};
                                    nextButton.find('.buttonGreen').removeClass("disabled");
                                    setDefaultCommandButon(findCommandButton('button.save'));
                                }
                                else
                                {
                                    nextButton.onclick = null;
                                    nextButton.find('.buttonGreen').addClass("disabled");
                                    setDefaultCommandButon(null);
                                }
                            }
                        </script>
                    </tiles:put>
                </tiles:insert>
            </html:form>
        </tiles:put>
    </tiles:insert>

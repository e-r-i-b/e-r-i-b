<%--
  User: bogdanov
  Date: 11.09.2012
  Time: 9:48:13

  страница с сообщением, что пароль устарел
  отсылаем пароль в CSABack
  после того как пароль изменен продолжаем загрузку.
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
<c:set var="changePassText"><bean:message bundle="commonBundle" key="text.SBOL.changePassword"/></c:set>

<html:form action="/login/checkOldPassword" show="true" onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();">
    <tiles:insert definition="login">
        <tiles:put name="headerGroup" value="true"/>
        <tiles:put name="pageTitle" type="string" value="${changePassText}"/>
        <tiles:put type="string" name="data">
            <br/>
            <tiles:insert definition="warningBlock" flush="false">
                <tiles:put name="regionSelector" value="warnings"/>
                <tiles:put name="isDisplayed" value="true"/>
                <tiles:put name="data">
                    Время действия Вашего пароля истекло. Пожалуйста, задайте новый пароль.
                </tiles:put>
            </tiles:insert>
            <br/>
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title">Смена пароля</tiles:put>
                <tiles:put name="data">

                    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/complexValueIndicator.js"></script>

                    <br>

                    <table class="shield">
                        <tr>
                            <td><html:img src="${imagePath}/red_shield.png"/></td>
                            <td class="iconsTitle">Изменение пароля входа в систему.</td>
                        </tr>
                    </table>

                    <table class="widthAuto">
                        <tr>
                            <td class="text-align-right field">Введите новый пароль:</td>
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
                                <script type="text/javascript">
                                    doOnLoad(function()
                                    {
                                        var indicator = new Indicator("passwordField", stateComplexPasswordCsa);
                                        indicator.init("pswd");
                                    });
                                </script>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-align-right field">Повторите новый пароль:</td>
                            <td style="padding: 5px 0 5px 10px;">
                                <html:password property="pswd2" styleId="pswd2" styleClass="login" maxlength="30" value="" style="width: 210px;"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-align-right field"></td>
                            <td style="padding: 5px 0 5px 10px;">
                                <html:checkbox styleId="showPassword" property="showPassword" value="true"/>отобразить пароли
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
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.save"/>
                            <tiles:put name="commandHelpKey" value="button.save"/>
                            <tiles:put name="bundle" value="securityBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                        </tiles:insert>
                    </div>

                    <script type="text/javascript" language="JavaScript">
                        if(window.$)
                        {
                            $("#showPassword").change(function(){
                                showHidePassword('pswd', this.checked);
                                showHidePassword('pswd2', this.checked);
                            });
                        }
                    </script>

                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

</html:form>

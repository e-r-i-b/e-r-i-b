<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
    <head>
        <title>Интернет-клиент для физических лиц. Система администрирования</title>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
        <link rel="icon" type="image/x-icon" href="${initParam.resourcesRealPath}/images/favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${initParam.resourcesRealPath}/style/login.css">
        <link rel="stylesheet" type="text/css" href="${initParam.resourcesRealPath}/style/style.css">
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/login.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/KeyboardUtils.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/gost.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/customPlaceholder.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/windows.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/DivFloat.js"></script>
        <!--[if IE 6]>
        <link rel="stylesheet" type="text/css" href="${initParam.resourcesRealPath}/style/ie.css"/>
        <![endif]-->

        <script type="text/javascript">
            var imagePath = "${initParam.resourcesRealPath}/images";
        </script>
    </head>
    <body>
        <html:messages id="message">
            <c:set var="errorMessage">${message}</c:set>
        </html:messages>

        <html:form styleId="changePasswordForm" action="/login" acceptCharset="windows-1251">
            <c:set var="form" value="${LoginForm}"/>
            <c:set var="imagePath" value="${initParam.resourcesRealPath}/images"/>
            <div id="pageContent" class="fonContainer loginBlock">
                <div id="header">
                    <div class="roundTL">
                        <div class="roundTR">
                            <div class="hdrContainer">
                                <div class="newHeader">
                                    <div class="Logo">
                                        <a class="logoImage logoImageText" onclick="return redirectResolved();" href="">
                                            <img alt="" src="${imagePath}/logoHeader.gif"/>
                                        </a>
                                    </div>

                                </div>
                                <div class="exitBlock">
                                    <a class="saveExit" title="Выход из системы" href="#" onclick="exit(); return false;">
                                        <span>Выход</span>
                                        <div class="exitImg"></div>
                                    </a>
                                </div>
                                <div class="topLineContainer">
                                    <div class="UserInfo">
                                        <div class="timeBlock">
                                            <div class="hourBlock" id="hours">
                                                <script type="text/javascript">
                                                    obj_hours=document.getElementById("hours");
                                                    function wr_hours()
                                                    {
                                                        time=new Date();

                                                        time_min=time.getMinutes();
                                                        time_hours=time.getHours();
                                                        time_wr=((time_hours<10)?"0":"")+time_hours;
                                                        time_wr+=":";
                                                        time_wr+=((time_min<10)?"0":"")+time_min;
                                                        obj_hours.innerHTML=time_wr;
                                                    }
                                                    wr_hours();
                                                    setInterval("wr_hours();",1000);
                                                </script>
                                            </div>
                                            <script type="text/javascript">
                                                var dateStr = time.getDate() + ' ' + monthToStringByNumber(time.getMonth()) + ' ' + (time.getFullYear());
                                                document.write(dateStr);
                                            </script>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="wrapper">
                    <div id="workspace">
                        <div class="adminLoginBlock">
                            <div class="mainInnerBlock">
                                <div class="whiteLoginBlock" id="LoginDiv">
                                    <div onkeypress="onEnterKeyPress(event, function(){changePassword();});">
                                        <h2>
                                            <span class="auth-title">Изменение пароля</span>
                                        </h2>

                                        <div class="passwordBlock loginFields">
                                            <label>Новый пароль</label>
                                            <div class="clear"></div>
                                            <input id="newPassword" type="password" autocomplete="off" name="field(newPassword)" maxlength="30" size="30"/>
                                        </div>

                                        <div class="passwordBlock loginFields">
                                            <label>Повторите новый пароль</label>
                                            <div class="clear"></div>
                                            <input id="repeatedPassword" type="password" autocomplete="off" name="field(repeatedPassword)" maxlength="30" size="30"/>
                                        </div>

                                        <div class="buttonsArea">
                                            <div class="commandButton">
                                                <div class="buttonGreen" onclick="changePassword();">
                                                    <div class="left-corner"></div>
                                                    <div class="text"><span>Сохранить</span></div>
                                                    <div class="right-corner"></div>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <c:if test="${not empty errorMessage}">
                            <div class="error">
                                ${errorMessage}
                            </div>
                        </c:if>
                        <div class="loginCopyright">R-Style Softlab, 2015. Все права защищены ©</div>
                    </div>
                </div>
                <div class="clear"></div>

                <c:set var="winId" value="changePassword"/>
                <div class="window farAway" id="${winId}Win">
                    <div class="workspace-box shadow">
                        <div class="shadowRT r-top">
                            <div class="shadowRTL r-top-left">
                                <div class="shadowRTR r-top-right">
                                    <div class="shadowRTC r-top-center"></div>
                                </div>
                            </div>
                        </div>
                        <div class="shadowRCL r-center-left">
                            <div class="shadowRCR r-center-right">
                                <div class="shadowRC r-content">
                                    <div class="confirmFloatMesage">
                                        <div id="${winId}">
                                            <div class="infoMessage">
                                                <h2>
                                                    Обратите внимание!
                                                </h2>
                                            </div>

                                            <div>
                                                Для продолжения работы Вы должны сменить пароль.
                                            </div>

                                            <div class="buttonsArea">
                                                <div class="commandButton">
                                                    <div class="buttonGreen">
                                                        <div class="left-corner"></div>
                                                        <div class="text" onclick="win.close('${winId}');">
                                                            <span>Ok</span>
                                                        </div>
                                                        <div class="right-corner"></div>
                                                        <div class="clear"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </div>
                        <div class="shadowRBL r-bottom-left">
                            <div class="shadowRBR r-bottom-right">
                                <div class="shadowRBC r-bottom-center"></div>
                            </div>
                        </div>
                    </div>
                    <div class="closeImg" title="закрыть" onclick="win.close('${winId}');"></div>
                </div>
            </div>
            <c:if test="${empty errorMessage}">
                <script type="text/javascript">
                    $(document).ready(function(){
                        win.open('${winId}');
                    });
                </script>
            </c:if>
        </html:form>
    </body>
</html:html>


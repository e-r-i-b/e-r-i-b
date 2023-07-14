<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
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
        <html:form styleId="loginForm" action="/login" acceptCharset="windows-1251">
        <c:set var="form" value="${LoginForm}"/>
        <c:set var="imagePath" value="${initParam.resourcesRealPath}/images"/>
        <div id="pageContent" class="fonContainer loginBlock">
            <div id="header">
                <div class="roundTL">
                    <div class="roundTR">
                        <div class="hdrContainer">
                            <div class="newHeader">
                                <div class="Logo">
                                    <a class="logoImage logoImageText" onclick="return redirectResolved();" href="${phiz:calculateActionURL(pageContext, '/login')}">
                                        <img alt="" src="${imagePath}/logoHeader.gif"/>
                                    </a>
                                </div>
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
                                <div class="login">
                                    <h2>
                                        <img src="${imagePath}/lock.jpg"/>
                                        <span class="auth-title"> Вход в систему</span>
                                    </h2>
                                    <div class="clear"></div>
                                    <html:hidden property="field(clientRandom)" styleId="clientRandom"/>
                                    <html:hidden property="field(serverRandom)" styleId="serverRandom"/>
                                    <div onkeypress="onEnterKeyPress(event, function(){login();})">
                                        <div class="loginFields">
                                            <label>Логин</label>
                                            <div class="clear"></div>
                                            <input type="text" autocomplete="off" name="field(login)" id="inputLogin" maxlength="30" value="${form.fields.login}" class="customPlaceholder" title=""/>
                                        </div>
                                        <div id="passBlock" class="loginFields">
                                            <label>Пароль</label>
                                            <div class="clear"></div>
                                            <input type="password" name="passwordTxt" id="passwordTxt" maxlength="30" onfocus="focusPassword();" onblur="blurPassword();"/>
                                            <input type="hidden" name="field(password)" id="password" maxlength="30"/>
                                        </div>
                                    </div>

                                    <div class="buttonsArea">
                                        <input type="hidden" name="accessType" value="employee"/>
                                        <div class="commandButton">
                                            <div class="buttonGreen" onclick="login();">
                                                <div class="left-corner"></div>
                                                <div class="text"><span>Войти</span></div>
                                                <div class="right-corner"></div>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                    </div>

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

        </div>
    </html:form>
    </body>
</html:html>


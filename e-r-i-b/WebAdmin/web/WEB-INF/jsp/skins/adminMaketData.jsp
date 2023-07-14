<%@ page import="com.rssl.common.forms.TemporalDocumentException" %>
<%@ page import="com.rssl.phizic.web.skins.SkinUrlValidator" %>
<%@ page import="com.rssl.phizic.web.util.SkinHelper" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<%
    String skinUrl =  request.getParameter("skinUrl");
    SkinUrlValidator urlValidator = new SkinUrlValidator();
	String newSkinUrl = "";
    try
    {
        if (urlValidator.validate(skinUrl))
            newSkinUrl+="http://";
    }
    catch (TemporalDocumentException e)
    {
        newSkinUrl+="http://";
    }
    newSkinUrl = SkinHelper.updateSkinPath(newSkinUrl + skinUrl);
%>
<c:set var="tempSkinUrl"><%=newSkinUrl%></c:set>
<c:set var="imagePath" value="${tempSkinUrl}/images"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>

<html>
    <head>
        <title>Интернет-клиент для физических лиц. Система администрирования</title>

        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
        <link rel="icon" type="image/x-icon" href="${tempSkinUrl}/images/favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/systemAll.css"/>


        <link rel="stylesheet" type="text/css" href="${tempSkinUrl}/style.css"/>
        <script type="text/javascript">
            document.webRoot = '/PhizIA';
            var skinUrl = '${tempSkinUrl}';
        </script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/DivFloat.js"></script>

        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/select.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/customPlaceholder.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/validators.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Templates.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/PaymentsFormHelp.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/commandButton.js"></script>

        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/builder.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/iframerequest.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/cookies.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/layout.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/windows.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/longOffer.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.datePicker.js"></script>
    </head>
    <body onLoad="showMessage();setWorkspaceWidth();validCountMMIns();"
          onresize="setWorkspaceWidth();validCountMMIns();">
        <div id="pageContent" class="fonContainer">
            <input type="hidden" name="operation"/>
            <script type="text/javascript">
                var error = null;
                var message = null;

                function showMessage()
                {
                    if (error != null) alert(error);
                    if (message != null) alert(message);

                }

                error = "";
                message = "";
                error = trim(error);
                message = trim(message);
                error = (error == "" ? null : error);
                message = (message == "" ? null : message);
            </script>
            <script type="text/javascript">
                document.imgPath = "${tempSkinUrl}/images/";
                //
                function setSepAtrb(beginInsSep, eolInsSep, widthSep, heightSep)
                {
                    if (widthSep >= 4)
                    {
                        beginInsSep.className = "buttDiv";
                        eolInsSep.className = "buttDiv";
                        eolInsSep.style.float = "right";
                        beginInsSep.innerHTML = '<img src="${globalImagePath}/1x1.gif" alt="" width="' + (widthSep - 2) + '" height="' + (heightSep) + '" border="0">';
                        eolInsSep.innerHTML = '<img src="${globalImagePath}/1x1.gif" alt="" width="' + (widthSep - 2) + '" height="' + (heightSep) + '" border="0">';
                    }
                }
            </script>
            <form name="ChangePasswordForm" method="post" style="margin: 0;">

                <div id="header">
                    <div class="roundTL">
                        <div class="roundTR">
                            <div class="clear"></div>
                            <div class="hdrContainer">
                                <div class="NewHeader">
                                    <div class="Logo">
                                        <a class="logoImage logoImageText" href="">
                                            <img alt="" src="${skinUrl}/images/logoHeader.gif">
                                        </a>
                                    </div>
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
                                <div class="topLineContainer">
                                    <div class="UserInfo">
                                        <a class="saveExit" title="Выход">
                                            <span>Выход&nbsp;</span>
                                            <div id="exit"></div>
                                        </a>
                                        <div class="employeeInfo">
                                            <div class="null">
                                                <span>Иванов И. И.</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="clear"></div>

                                <div class="headerMenu">
                                    <div class="buttDiv menuItems">
                                        <div class="leftMenuItem">
                                            <div class="rightMenuItem">
                                                <div class="centerMenuItem">Клиенты</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="buttDiv menuItems">
                                        <div class="leftMenuItem leftMenuItemActive activeItem">
                                            <div class="rightMenuItem rightMenuItemActive activeItem">
                                                <div class="centerMenuItem centerMenuItemActive activeItem">Сервис</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="buttDiv menuItems">
                                        <div class="leftMenuItem">
                                            <div class="rightMenuItem">
                                                <div class="centerMenuItem">Аудит</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="buttDiv menuItems">
                                        <div class="leftMenuItem">
                                            <div class="rightMenuItem">
                                                <div class="centerMenuItem">Автоплатежи</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="buttDiv menuItems">
                                        <div class="leftMenuItem">
                                            <div class="rightMenuItem">
                                                <div class="centerMenuItem">Справочники</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="buttDiv menuItems">
                                        <div class="leftMenuItem">
                                            <div class="rightMenuItem">
                                                <div class="centerMenuItem">Поставщики услуг</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="buttDiv menuItems">
                                        <div class="leftMenuItem">
                                            <div class="rightMenuItem">
                                                <div class="centerMenuItem">Настройки</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="buttDiv menuItems">
                                        <div class="leftMenuItem">
                                            <div class="rightMenuItem">
                                                <div class="centerMenuItem">Реклама и сообщения</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="buttDiv menuItems">
                                        <div class="leftMenuItem">
                                            <div class="rightMenuItem">
                                                <div class="centerMenuItem">Подразделения</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="buttDiv menuItems">
                                        <div class="leftMenuItem">
                                            <div class="rightMenuItem">
                                                <div class="centerMenuItem">События</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="buttDiv menuItems">
                                        <div class="leftMenuItem">
                                            <div class="rightMenuItem">
                                                <div class="centerMenuItem">Письма</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="buttDiv menuItems">
                                        <div class="leftMenuItem">
                                            <div class="rightMenuItem">
                                                <div class="centerMenuItem">Кредиты</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="buttDiv menuItems">
                                        <div class="leftMenuItem">
                                            <div class="rightMenuItem">
                                                <div class="centerMenuItem">Карты</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="wrapper">
                    <div id="workspace" class="content">
                        <div class="mainInnerBlock">
                            <div class="pageTitle"></div>
                            <div id="workspaceDiv" class="workspaceRegion" style="position:relative;">
                                <div>
                                    <div class="paymentForms">
                                        <div class="pmntTitleForm">
                                            <div class="pageTitle">Изменение пароля</div>
                                            <div class="pmntTitleText">
                                                Используйте данную форму для изменения пароля на вход в систему.
                                            </div>
                                            <div class="pmntData">
                                                <div class="form-row">
                                                    <div class="paymentLabel">Введите новый пароль:</div>
                                                    <div class="paymentValue">
                                                        <input type="password" value="" name="field(newPassword)"/>
                                                    </div>
                                                    <div class="clear"></div>
                                                </div>
                                                <div class="form-row">
                                                    <div class="paymentLabel">Повторите новый пароль:</div>
                                                    <div class="paymentValue">
                                                        <input type="password" value="" name="field(repeatedPassword)"/>
                                                    </div>
                                                    <div class="clear"></div>
                                                </div>
                                            </div>
                                            <div class="pmntFormMainButton floatRight">
                                                <div class="otherButtonsArea">
                                                    <div class="commandButton">
                                                        <div class="buttonGreen">
                                                            <div class="left-corner"></div>
                                                            <div class="text">
                                                                <span>Сохранить</span>
                                                            </div>
                                                            <div class="right-corner"></div>
                                                        </div>
                                                        <div class="clear"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="left-section">
                        <div class="workspace-box subMenu">
                            <div class="subMenuRT r-top ">
                                <div class="subMenuRTL r-top-left">
                                    <div class="subMenuRTR r-top-right">
                                        <div class="subMenuRTC r-top-center">
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="subMenuRCL r-center-left">
                                <div class="subMenuRCR r-center-right">
                                    <div class="subMenuRC r-content">
                                        <div class="subMenuTitle">
                                            <span>Сервис</span>
                                        </div>
                                        <div class="clear"></div>

                                        <div class="subMInactiveInset">
                                            <span class="subMTitle"><i>Объединённый журнал</i></span>
                                        </div>
                                        <div class="subMInactiveInset">
                                            <span class="subMTitle"><i>Журнал действий пользователей</i></span>
                                        </div>
                                        <div class="subMInactiveInset">
                                            <span class="subMTitle"><i>Журнал системных действий</i></span>
                                        </div>
                                        <div class="subMInactiveInset">
                                            <span class="subMTitle"><i>Журнал сообщений</i></span>
                                        </div>
                                        <div class="subMInactiveInset">
                                            <span class="subMTitle"><i>Журнал регистрации входов</i></span>
                                        </div>
                                        <div id="subMActiveInset" class="subMActiveInset">
                                            <span class="subMTitle"><i class="">Сменить пароль</i></span>
                                        </div>
                                        <div class="subMInactiveInset">
                                            <span class="subMTitle"><i>Формы платежей</i></span>
                                        </div>
                                        <div class="subMInactiveInset">
                                            <span class="subMTitle"><i>Проверка АСП</i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="subMenuRBL r-bottom-left">
                                <div class="subMenuRBR r-bottom-right">
                                    <div class="subMenuRBC r-bottom-center"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="clear"></div>
                <div id="footer">
                    <div class="roundBL">
                        <div class="roundBR">
                            <div class="ftrContainer">
                                <div class="copyright">
                                    <span>Разработано</span>
                                    <a href="" class="RS-logo"></a>
                                </div>
                                <div class="feedback">
                                    <div class="feedbackItems">
                                        <a id="helpLink" class="onlineHelp" href="">
                                            <span>Помощь</span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>
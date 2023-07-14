<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="imagesPath" value="${globalUrl}/commonSkin/images/guest"/>
<c:set var="skinUrl" value="${globalUrl}/commonSkin"/>
<c:set var="guestEntryType" value="${form.request}"/>

<tiles:importAttribute/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Сбербанк Онлайн</title>
    <link rel="icon" type="image/x-icon" href="${globalUrl}/commonSkin/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/systemAll.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/style.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/style.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/guest.css"/>

    <script type="text/javascript" src="${globalUrl}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/customPlaceholder.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/layout.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/windows.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.maskedinput.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.datePicker.js"></script>
</head>
<body class="gPage">
<html:form action="${action}" show="true" onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();" styleId="guestPersonData">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <div id="workspaceCSA" class="fullHeight">

        <div class="mainContent">
            <div class="header noButtonRegistration">
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

            <div class="title">
                <p>Оформление кредита</p>

                <div class="g-num">
                    Мобильный номер
                    <div class="g-num-mask">${phiz:getCutPhoneNumber(form.phone)}</div>
                </div>
            </div>

            <div class="wrapper">
                <div class="ears-content">
                    <div class="formContent">
                        <div class="formContentMargin">
                            ${data}
                        </div>
                    </div>
                </div>
                <div class="base">
                    <div class="main_row">
                        <div class="content_row"></div>
                    </div>
                    <div class="ears ears-b"></div>
                </div>
            </div>
        </div>

        <div class="clear"></div>
        <div class="footer">
            <div class="references">
                <a href="http://www.sberbank.ru/moscow/ru/person/contributions/max_stavka/">Информация о процентных ставках по договорам банковского вклада с физическими
                    лицами</a><br/>
                <a href="http://www.sberbank.ru/moscow/ru/investor_relations/disclosure/shareholders/">Информация о лицах, под контролем которых находится банк</a>

                <div class="copyright">
                    &copy 1997&#8211;2015 Сбербанк России, Россия, Москва, 117997, ул. Вавилова, д.19, тел. +7(495) 500
                    5550 <br/>
                    8 800 555 5550. Генеральная лицензия на осуществление банковских операций от 8 августа 2012
                    года. <br/>
                    Регистрационный номер &#8212; 1481.
                    <a class="icon" href="http://www.sberbank.ru/moscow/ru/important/"><span>Обратная связь.</span></a>
                </div>
            </div>
        </div>
        <div id="loading" style="left:-3300px;">
            <div id="loadingImg"><img src="${skinUrl}/skins/commonSkin/images/ajax-loader64.gif"/></div>
            <span>Пожалуйста, подождите,<br/> Ваш запрос обрабатывается.</span>
        </div>
        <script type="text/javascript">
            var skinUrl = '${skinUrl}';
            var globalUrl = '${globalUrl}';
            document.webRoot = '/PhizIC';

            function next()
            {
                var form = $("#guestPersonData");
                form.append("<input type='hidden' name='operation' value='next'/>");
                form.submit();
            }
        </script>
    </div>
</html:form>
</body>
</html>
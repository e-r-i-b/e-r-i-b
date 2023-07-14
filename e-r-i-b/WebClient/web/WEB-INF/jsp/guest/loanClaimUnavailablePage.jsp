<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>

<c:set var="imagesPath" value="${globalUrl}/commonSkin/images/guest"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <title>Сбербанк Онлайн</title>
        <link rel="icon" type="image/x-icon" href="${globalUrl}/commonSkin/images/favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/guest.css"/>
    </head>
    <body class="gPage">
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
                    <p>Заказ кредитной карты</p>
                </div>

                <div class="wrapper">
                   <div class="ears-content">
                       <div class="formContent">
                           <div class="formContentMargin">
                               <p class="contentTitle">К сожалению, заявка отклонена.</p>
                               <br/>
                               Уважаемый клиент! От Вашего имени ранее уже поступала заявка на кредитную карту. Оформление второй кредитной карты не допускается.
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
        </div>
    </body>
</html>
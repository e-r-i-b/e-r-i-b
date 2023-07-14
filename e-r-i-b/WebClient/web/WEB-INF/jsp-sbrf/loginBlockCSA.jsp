<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<c:set var="faqLink" value="${phiz:calculateActionURL(pageContext, '/faq.do')}"/>
<tiles:importAttribute name="needSave" ignore="true" scope="request"/>
<head>
    <title><bean:message key="application.title" bundle="commonBundle"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
    <link rel="icon" type="image/x-icon" href="${skinUrl}/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/style.css"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/style.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/login.css">
    <link rel="stylesheet" type="text/css" href="${skinUrl}/login.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/systemAll.css"/>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>
    <script type="text/javascript">
        document.webRoot='/${phiz:loginContextName()}';
        var skinUrl = '${skinUrl}';
        var globalUrl = '${globalUrl}';
    </script>

     <!--[if IE 6]>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/iepngfix_tilebg.js"></script>
	    <link type="text/css" rel="stylesheet" href="${globalUrl}/commonSkin/ie.css"/>
	    <link type="text/css" rel="stylesheet" href="${skinUrl}/ie.css"/>
    <![endif]-->

    <style type="text/css">
        .workspace-box{
            width: 960px;
            margin: 20px auto;
            padding: 0 6px;
        }

        .login_info {
            margin-bottom: 0px;
        }
    </style>
</head>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}"/>

<body>
    <tiles:insert definition="googleTagManager"/>
    <div class="main_container">
        <div class="pageBackground">
            <div class="pageContent">
                <html:form show="true">
                    <c:set var="headerGroup" value="true"/>
                    <c:set var="headerMenu"  value="true"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/common/layout/header.jsp"%>
                    <div class="clear"></div>
                    <div id="wrapper">
                        <div class="padding"></div>
                        <div id="content">
                            <div>
                                <tiles:insert page="/WEB-INF/jsp-sbrf/common/layout/messages.jsp">
                                    <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
                                </tiles:insert>
                            </div>
                            <div class="clear"></div>
                            <div>
                                <tiles:insert definition="roundBorder" flush="false">
                                    <tiles:put name="data">
                                        <div class="loginDataBlock">
                                            <div class="login_info">
                                                <br/>
                                                <p>
                                                    <bean:message bundle="commonBundle" key="text.SBOL.about"/>
                                                    Их можно получить:
                                                    <ul>
                                                        <li>с помощью банковской карты через устройства самообслуживания Сбербанка России (список устройств);</li>
                                                        <li>с помощью «Мобильного банка», отправив SMS-сообщение на номер 900: Parol xxxxx (где xxxxx - последние 5 цифр номера Вашей карты). Далее позвоните по телефонам Контактного центра Сбербанка России +7 (495) 500 5550, 8 800 555 5550 и получите идентификатор пользователя.</li>
                                                    </ul>
                                                    Для того чтобы получить более подробную информацию, щелкните по ссылке <a href="#" onclick="openFAQ('/PhizIC/faq.do#p10')">«Часто задаваемые вопросы»</a>.
                                                </p>
                                                <br/>
                                                <p><a href="https://esk.sberbank.ru/esClient/_ns/ProtectInfoPage.aspx" target="_blank"><bean:message bundle="commonBundle" key="text.SBOL.precautions"/></a>
                                                </p>

                                                <p>
                                                    <a href="http://sberbank.ru/ru/person/dist_services/warning/"
                                                       class="paperEnterLink" target="_blank">О рисках при дистанционном банковском
                                                        обслуживании</a>
                                                </p>
                                            </div>
                                            <div class="login_girl">
                                                <a href="#" onclick="openFAQ('${faqLink}')">
                                                    <img src="${imagePath}/login_girl.png" alt="Часто задаваемые вопросы"/>
                                                </a>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                    </tiles:put>
                                </tiles:insert>
                            </div>
                        </div>
                    </div>
                    <%@ include file="/WEB-INF/jsp-sbrf/common/layout/footer.jsp"%>
                </html:form>
            </div>
        </div>
    </div>
</body>
</html:html>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<c:set var="form" value="${ListDownloadLinksForm}"/>
<tiles:insert definition="mobileMain" flush="false">
    <tiles:put name="title">Сбербанк Онлайн</tiles:put>
    <tiles:put name="data">
        <div class="b-logo">
            <img class="logo_img" width="284" height="82" src="${skinUrl}/skins/sbrf/images/csa/big-logo_img.png" alt=""/>
        </div>
        <br/>
        <div class="m-text">
            <bean:message bundle="commonBundle" key="downloadLinks.mobileApp.notification"/>
        </div>
        <div class="clear"></div>
        <tiles:insert definition="downloadButton" flush="false">
            <tiles:put name="text">
                Скачать
            </tiles:put>
            <tiles:put name="URL" value="${form.link}"/>
            <tiles:put name="btnType" value="download"/>
        </tiles:insert>
        <%-- блок или --%>
        <div class="form_remind">
            или <br/>
            <a class="aBlack" href="${csa:calculateActionURL(pageContext, '/unallowedbrowsers.do')}?operation=loadFullVersion">Скачать позже</a>
        </div>
    </tiles:put>
    <tiles:put name="footer">
        <br/>
        Список всех мобильных <br/> приложений доступен на сайте <a href="http://sberbank.ru">www.sberbank.ru</a>
    </tiles:put>
</tiles:insert>
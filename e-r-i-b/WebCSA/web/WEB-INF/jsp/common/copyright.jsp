<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<div class="ftrContainer">
    <div class="copyright">
        © 1997 — 2015 ОАО «Сбербанк России» Россия, Москва, 117997, ул. Вавилова, д. 19.<br/>
        Генеральная лицензия на осуществление банковских операций от 8 августа 2012. Регистрационный номер - 1481.<br/>
        Разработано компанией R-Style Softlab<br/>
    </div>
    <c:if test="${not empty additionalFooterData}">
        ${additionalFooterData}
    </c:if>
    <c:if test="${csa:isUECPaymentSession()}">
        <div class="uecLogo">
            <a href="${csa:getUECWebSiteUrl()}" name="uec" target="_blank">
                <img width="26px" height="40px" src="${skinUrl}/skins/sbrf/images/csa/uec-logo.jpg" alt="uec" />
            </a>
        </div>
    </c:if>
    <div id="linkConteiner" class="floatRight">
        <div class="floatRight">
            <p>Будьте в курсе:</p>
            <div id="SocialLinksImg">
                <a href="https://www.facebook.com/bankdruzey" name="facebook" target="_blank">
                    <img width="16px" height="16px" src="${skinUrl}/skins/sbrf/images/csa/icon_facebookApplication.gif" alt="facebook/bankdruzey" />
                </a>
                <a href="http://vk.com/bankdruzey" name="vkontakte.com" target="_blank">
                    <img width="16px" height="16px" src="${skinUrl}/skins/sbrf/images/csa/icon_VKApplication.gif" alt="vkontakte/bankdruzey" />
                </a>
                <a href="https://twitter.com/sberbank/" name="twitter.com" target="_blank">
                    <img width="16px" height="16px" src="${skinUrl}/skins/sbrf/images/csa/icon_twitterApplication.gif" alt="twitter/sberbank" />
                </a>
                <a href="http://www.odnoklassniki.ru/bankdruzey" name="odnoklassniki.ru" target="_blank">
                    <img width="16px" height="16px" src="${skinUrl}/skins/sbrf/images/csa/icon_odnoklassnikiApplication.gif" alt="odnoklassniki/bankdruzey">
                </a>
                <a href="http://www.youtube.com/sberbank" name="youtube.com" target="_blank">
                    <img width="16px" height="16px" src="${skinUrl}/skins/sbrf/images/csa/icon_youtubeApplication.gif" alt="youtube/sberbank">
                </a>
                <div id=""></div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/common/analytics.jsp"  %>
<%@ include file="/WEB-INF/jsp/common/scriptPublicMetricPixel.jsp"  %>
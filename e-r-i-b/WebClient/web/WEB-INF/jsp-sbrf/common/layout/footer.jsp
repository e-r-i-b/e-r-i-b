<%--
  User: Zhuravleva
  Date: 09.06.2009
  Time: 19:53:47
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<c:set var="mode" value="${phiz:getUserVisitingMode()}"/>
<div class="cleaners"></div>
<div id="footer">
    <div class="footerBorder">
        <div class="footerColor">
            <div class="ftrContainer">
                <div class="footerRound">
                    <span>+7 (495) <b>500-55-50</b>, 8 (800) <b>555-55-50</b></span>
                </div>
                <div class="clear"></div>

                <a href="http://www.sberbank.ru/" target="_blank" class="lightGreenRound">sberbank.ru</a>

                <div class="aroundTheClock">Круглосуточная помощь</div>

                <c:if test="${mainMenuType != 'guestMainMenu'}">
                    <div class="feedback">
                        <c:if test="${phiz:impliesService('ClientMailManagment') && mode != 'PAYORDER_PAYMENT'}">
                            <div class="feedbackItems">
                                <a class="inlineBlock" href="${phiz:calculateActionURL(pageContext,"/private/mail/list.do")}">
                                    <span class="letterToBank"></span>
                                    <span class="inlineBlock">Письмо в банк</span>
                                </a>
                            </div>
                        </c:if>
                        <div class="feedbackItems">
                            <a class="inlineBlock" href="" onclick="openHelp('${helpLink}'); return false;">
                                <span class="onlineHelp"></span>
                                <span class="inlineBlock">Помощь онлайн</span>
                            </a>
                        </div>
                    </div>
                </c:if>


                <div class="clear"></div>
                <div class="copyrightBlock">
                    <div class="copyright">
                        <p class="bold">© 1997 — 2015 ОАО «Сбербанк России»</p>
                        <p>Россия, Москва, 117997, ул. Вавилова, д. 19,<br/>
                        Генеральная лицензия на осуществление банковских операций от 8 августа 2012<br/>
                        Регистрационный номер — 1481.<br/>
                        Разработано компанией R-Style Softlab</p>
                    </div>
                    <div id="linkConteiner" class="floatRight">
                        <div class="floatRight">
                            <p class="bold">Мы в социальных сетях</p>
                            <div id="SocialLinksImg">
                                <a href="https://www.facebook.com/bankdruzey" name="facebook" target="_blank" class="facebook"></a>
                                <a href="http://vk.com/bankdruzey" name="vkontakte.com" target="_blank" class="vkontakte"></a>
                                <a href="https://twitter.com/sberbank/" name="twitter.com" target="_blank" class="twitter"></a>
                                <a href="http://www.odnoklassniki.ru/bankdruzey" name="odnoklassniki.ru" target="_blank" class="odnoklassniki"></a>
                                <a href="http://www.youtube.com/sberbank" name="youtube.com" target="_blank" class="youtube"></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/common/analytics.jsp"  %>
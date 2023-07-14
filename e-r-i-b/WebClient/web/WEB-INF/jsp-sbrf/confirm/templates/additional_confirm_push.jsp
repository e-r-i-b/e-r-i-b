<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<%--
    preConfirmCommandKey - ключ для кнопки вызова окна подтверждения одноразовым паролем( + окончание от имени стратегии SMS,Cap или Card)
    hasSMS               - возможновть подтверждения СМС
    hasCard              - возможновть подтверждения картой
    hasCap               - возможновть подтверждения CAP
    hasCapButton         - есть ли кнопка подтверждения CAP
--%>

<tiles:importAttribute/>

<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

<c:if test="${empty preConfirmCommandKey}">
    <c:set var="preConfirmCommandKey" value="button.confirm"/>
</c:if>
<div class="clear"></div>
<div class="changeStrategy">
    <a class="blueGrayLinkDotted">Другой способ подтверждения</a>
    <div class="clear"></div>
    <div class="anotherStrategy" style="width: 350px; z-index: 20">
        <div class="anotherStrategyTL">
            <div class="anotherStrategyTR">
                <div class="anotherStrategyTC">
                    <div class="anotherStrategyItem"></div>
                </div>
            </div>
        </div>
        <div class="anotherStrategyCL">
            <div class="anotherStrategyCR">
                <div class="anotherStrategyCC">
                    <ul>
                        <c:if test="${hasSMS}">
                            <li onclick="clickConfirmButton('${preConfirmCommandKey}SMS','<bean:message key="button.confirmSMS" bundle="securityBundle"/>')">
                                <%--Подтвердить по SMS--%>
                                <span class="strategyTitle">SMS-пароль</span>
                                <span class="textStrategy">Одноразовый пароль приходит в SMS на вашем мобильном телефоне</span>
                            </li>
                        </c:if>

                        <c:if test="${hasCard}">
                            <li onclick="clickConfirmButton('${preConfirmCommandKey}Card','<bean:message key="button.confirmCard" bundle="securityBundle"/>')">
                                <%--Подтвердить чеком--%>
                                <span class="strategyTitle">Пароль с чека</span>
                                <span class="textStrategy">Список паролей печатается на чеке в любом банкомате или терминале Сбербанка</span>
                            </li>
                        </c:if>
                        <c:if test="${hasCapButton or hasCap}">
                            <li onclick="clickConfirmButton('${preConfirmCommandKey}Cap','<bean:message key="button.confirmCap" bundle="securityBundle"/>')">
                                <%--Подтвердить по карте--%>
                                <span class="strategyTitle">Пароль с карты</span>
                                <span class="textStrategy">Пароль, полученный с Вашей скретч-карты.</span>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>
        <div class="anotherStrategyBL">
            <div class="anotherStrategyBR">
                <div class="anotherStrategyBC">
                </div>
            </div>
        </div>
    </div>
</div>

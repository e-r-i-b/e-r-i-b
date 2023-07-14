<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<c:set var="hasSMS" value="${phiz:isContainStrategy(confirmStrategy, 'sms')}"/>
<c:set var="hasCard" value="${phiz:isContainStrategy(confirmStrategy,'card')}"/>
<c:set var="hasCap" value="${phiz:isContainStrategy(confirmStrategy, 'cap')}"/>
<c:set var="hasPush" value="${phiz:isContainStrategy(confirmStrategy, 'push')}"/>
<c:set var="currentStrategyType"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:choose>
    <c:when test="${confirmRequest.strategyType eq 'sms'}">
        <c:set var="currentStrategyType" value="confirmSMS"/>
    </c:when>
    <c:when test="${confirmRequest.strategyType eq 'push'}">
        <c:set var="currentStrategyType" value="confirmPush"/>
    </c:when>
    <c:when test="${confirmRequest.strategyType eq 'card'}">
        <c:set var="currentStrategyType" value="confirmCard"/>
    </c:when>
    <c:when test="${confirmRequest.strategyType eq 'cap'}">
        <c:set var="currentStrategyType" value="confirmCap"/>
    </c:when>
    <c:otherwise>
        <c:set var="currentStrategyType" value="confirmSMS"/>
    </c:otherwise>
</c:choose>

<div class="chooseConfirmStrategy confirmTemplateButtonRegion">
    <tiles:insert definition="commandButton" flush="false">
        <tiles:put name="commandKey" value="button.${currentStrategyType}"/>
        <tiles:put name="commandTextKey" value="button.${currentStrategyType}"/>
        <tiles:put name="commandHelpKey" value="button.${currentStrategyType}.help"/>
        <tiles:put name="bundle" value="securityBundle"/>
    </tiles:insert>
    <c:if test="${hasCard or hasPush}">

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
                                <c:if test="${hasSMS and confirmRequest.strategyType != 'sms'}">
                                    <script type="text/javascript">
                                        if(window.createCommandButton != undefined)
                                        {
                                           createCommandButton('button.confirmSMS', 'button.confirmSMS');
                                        }
                                    </script>
                                    <li onclick="findCommandButton('button.confirmSMS').click('', false)">
                                        <%--Подтвердить по SMS--%>
                                        <span class="strategyTitle">SMS-пароль</span>
                                        <span class="textStrategy">Одноразовый пароль приходит в SMS на вашем мобильном телефоне</span>
                                    </li>
                                </c:if>
                                <c:if test="${hasPush and confirmRequest.strategyType != 'push'}">
                                    <script type="text/javascript">
                                        if(window.createCommandButton != undefined)
                                        {
                                           createCommandButton('button.confirmPush', 'button.confirmPush');
                                        }
                                    </script>
                                    <li onclick="findCommandButton('button.confirmPush').click('', false)">
                                        <div class="newLabel <c:if test="${not(anotherStrategy or hasCard) && not(anotherStrategy or hasSMS)}">newLabelFirst</c:if>"><img src="${globalImagePath}/newLabel.gif" width="51" height="51"/></div>
                                        <span class="strategyTitle">Push-пароль из уведомления в мобильном приложении</span>
                                        <span class="textStrategy">Одноразовые пароли приходят в приложение Сбербанка на вашем мобильном телефоне.</span>
                                    </li>
                                </c:if>
                                <c:if test="${hasCard and confirmRequest.strategyType != 'card'}">
                                    <script type="text/javascript">
                                        if(window.createCommandButton != undefined)
                                        {
                                           createCommandButton('button.confirmCard', 'button.confirmCard');
                                        }
                                    </script>
                                    <li onclick="findCommandButton('button.confirmCard').click('', false)">
                                        <%--Подтвердить чеком--%>
                                        <span class="strategyTitle">Пароль с чека</span>
                                        <span class="textStrategy">Список паролей печатается на чеке в любом банкомате или терминале Сбербанка</span>
                                    </li>
                                </c:if>
                                <c:if test="${hasCap and confirmRequest.strategyType != 'cap'}">
                                    <script type="text/javascript">
                                        if(window.createCommandButton != undefined)
                                        {
                                           createCommandButton('button.confirmCap', 'button.confirmCap');
                                        }
                                    </script>
                                    <li onclick="findCommandButton('button.confirmCard').click('', false)">
                                        <%--Подтвердить чеком--%>
                                        <span class="strategyTitle">Пароль с карты</span>
                                        <span class="textStrategy">CAP-пароль с карты</span>
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
    </c:if>
</div>
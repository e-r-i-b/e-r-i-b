<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/credit/report" onsubmit="return setEmptyAction(event)">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="creditHistory">
        <tiles:put name="pageTitle">
            <bean:message bundle="creditHistoryBundle" key="label.credit.history.title"/>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title">
                    <bean:message bundle="creditHistoryBundle" key="label.credit.history.title"/>
                </tiles:put>
                <tiles:put name="data">
                    <div class="credit-history-box">
                        <tiles:insert definition="formHeader" flush="false">
                            <tiles:put name="description">
                                <p class="b-get-credit-history-text">
                                    <bean:message bundle="creditHistoryBundle" key="label.credit.history.description"/>
                                </p>
                            </tiles:put>
                        </tiles:insert>

                        <c:if test="${not form.bkiError}">
                            <c:choose>
                                <c:when test="${form.waitingNew}">
                                    <div class="we-are-preparing-cr-history">
                                        <img src="${globalUrl}/commonSkin/images/clock.png" alt="" />
                                        <div class="we-are-preparing-cr-history-text">
                                            Мы готовим Вашу кредитную историю<br />Пожалуйста, подождите
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="b-get-credit-history">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.get.credit.history"/>
                                            <tiles:put name="commandHelpKey" value="button.get.credit.history"/>
                                            <tiles:put name="bundle" value="creditHistoryBundle"/>
                                            <tiles:put name="action" value="/private/payments/creditReportPayment/edit.do?recipient=${form.providerId}"/>
                                            <tiles:put name="isDefault" value="true"/>
                                        </tiles:insert>
                                        <div class="b-get-credit-history-cost-wrap">
                                            <div class="b-get-credit-history-cost-title">
                                                СТОИМОСТЬ<br />УСЛУГИ
                                            </div>
                                            <span class="b-get-credit-history-cost-value">${form.cost}</span>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:if>

                        <div class="b-get-credit-history-bg"></div>
                        <div class="b-get-credit-history-example-content">
                            <div class="b-get-credit-history-zoom">
                                <div class="categoriesTitle b-get-credit-history-example-title">Пример кредитной истории</div>
                                <p class="b-get-credit-history-example-text">Кредитный отчет содержит детальную<br /> информацию о действующих и ранее выплаченных<br /> кредитах, а также о запросах, которые делали<br /> банки и другие организации для проверки Вашей<br /> кредитной истории.</p>

                                <div class="b-get-credit-history-rating">
                                    <h3 class="b-get-credit-history-rating-title">Ваш кредитный рейтинг</h3>
                                    <p>Кредитный рейтинг показывает насколько хороша Ваша кредитная история и требуется ли её улучшение.</p>
                                    <p>Если Вы вносите все платежи по кредитам вовремя и не имеете слишком много открытых кредитов, то Ваш кредитный рейтинг будет высоким. Узнайте его прямо сейчас!</p>
                                </div>
                            </div>

                            <div class="b-get-credit-history-data">
                                <div class="b-get-credit-history-data-content">
                                    <h3>Полные данные по всем Вашим кредитам и кредитным картам</h3>
                                    <p>Вся информация по всем Вашим кредитам как на ладони!</p>
                                    <p>По каждому кредиту Вы увидите детальную историю платежей с указанием допущенных просрочек.</p>
                                </div>
                            </div>
                        </div>
                        <div class="b-get-credit-history-data-bottom-bg"></div>

                        <div class="b-get-credit-history-who-was-interesed">
                            <h3>Кто интересовался Вашей кредитной историей</h3>
                            <p>Вы увидите, кто и когда проверял Вашу кредитную историю. Например, банки в большинстве случаев делают запросы при рассмотрение заявления на кредит, чтобы сообщить Вам своё решение.</p>
                        </div>

                        <c:if test="${not form.bkiError and not form.waitingNew}">
                            <div class="b-get-credit-history">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.get.credit.history"/>
                                    <tiles:put name="commandHelpKey" value="button.get.credit.history"/>
                                    <tiles:put name="bundle" value="creditHistoryBundle"/>
                                    <tiles:put name="action" value="/private/payments/creditReportPayment/edit.do?recipient=${form.providerId}"/>
                                    <tiles:put name="isDefault" value="true"/>
                                </tiles:insert>
                                <div class="b-get-credit-history-cost-wrap">
                                    <div class="b-get-credit-history-cost-title">
                                        СТОИМОСТЬ<br />УСЛУГИ
                                    </div>
                                    <span class="b-get-credit-history-cost-value">${form.cost}</span>
                                </div>
                            </div>
                        </c:if>
                    </div>
                    <div class="credit-history-right">
                        <div class="b-r-sidebar-okb">
                            <img src="${globalUrl}/commonSkin/images/okb-logo.gif" alt="" class="float"/>
                            <p>Отчёт предоставлен <a href="${phiz:getBkiOkbUrl()}" target="_blank">ЗАО «ОКБ»</a></p>
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

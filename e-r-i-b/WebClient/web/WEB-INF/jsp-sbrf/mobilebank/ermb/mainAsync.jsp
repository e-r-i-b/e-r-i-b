<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<c:set var="thisActionUrl" value="/private/async/mobilebank/ermb/main"/>

<html:form action="${thisActionUrl}">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmableObject"  value="${form.confirmableObject}"/>
    <c:if test="${not empty confirmableObject}">
        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(confirmableObject)}"/>
    </c:if>
    <c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>

    <tiles:insert  definition="${confirmTemplate}" flush="false">
        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
        <tiles:put name="anotherStrategy" value="false"/>
        <tiles:put name="confirmableObject" value="ermbSettings"/>
        <tiles:put name="message">
            <bean:message key="${confirmRequest.strategyType}.settings.security.message.enter" bundle="securityBundle"/>
        </tiles:put>

        <tiles:put name="data">
            <p class="bold">Вы изменили следующие настройки:</p>

            <c:if test="${not empty confirmableObject.tarif}">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">Тариф : </tiles:put>
                    <tiles:put name="data">
                        <span class="bold"><c:out value="${confirmableObject.tarif.name}"/></span>
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <c:if test="${confirmableObject.cardChanged}">
                <c:set var="card" value="${confirmableObject.mainCard}"/>
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">Основная карта для списания платы: </tiles:put>
                    <tiles:put name="data">
                            <span class="bold">
                                <c:choose>
                                    <c:when test="${not empty card}">
                                        <c:out value="${card.name} ${phiz:getCutCardNumber(card.number)}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="Не указана"/>
                                    </c:otherwise>
                                </c:choose>
                            </span>
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <c:if test="${not empty confirmableObject.mainPhone}">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">Основной телефон: </tiles:put>
                    <tiles:put name="data">
                            <span class="bold"><c:out value="${phiz:getCutPhoneNumber(confirmableObject.mainPhone)}"/></span>
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <c:if test="${not empty confirmableObject.phones}">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">Телефоны: </tiles:put>
                    <tiles:put name="data">
                        <logic:iterate id="listItem" name="confirmableObject" property="phones">
                            <table>
                                <tr>
                                    <td>
                                        <span class="bold"><c:out value="${phiz:getCutPhoneNumber(listItem)}"/></span>
                                    </td>
                                </tr>
                            </table>
                        </logic:iterate>
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <c:if test="${not empty confirmableObject.ntfStartTime}">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">Время начала оповещений: </tiles:put>
                    <tiles:put name="data">
                        <span class="bold"><bean:write name="confirmableObject" property="ntfStartTime" format="HH:mm"/></span>
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <c:if test="${not empty confirmableObject.ntfEndTime}">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">Время окончания оповещений: </tiles:put>
                    <tiles:put name="data">
                        <span class="bold"><bean:write name="confirmableObject" property="ntfEndTime" format="HH:mm"/></span>
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <c:if test="${not empty confirmableObject.timeZone}">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">Часовой пояс: </tiles:put>
                    <tiles:put name="data">
                        <span class="bold"><c:out value="${confirmableObject.timeZone.text}"/></span>
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <c:if test="${confirmableObject.daysChanged}">
                <c:set var="days" value="${confirmableObject.days}"/>
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">Дни недели: </tiles:put>
                    <tiles:put name="data">
                        <span class="bold">
                            <c:choose>
                                <c:when test="${not empty days}">
                                    <c:out value="${confirmableObject.days}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="Не указаны"/>
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </tiles:put>
                </tiles:insert>
            </c:if>


            <c:if test="${not empty confirmableObject.depositsTransfer}">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">Проценты по счетам: </tiles:put>
                    <tiles:put name="data">
                        <c:set var="depositsTransfer" value="${confirmableObject.depositsTransfer ? 'on' : 'off'}"/>
                        <span class="bold">
                            <bean:message key="title.depositsTransfer.${depositsTransfer}" bundle="ermbBundle"/>
                        </span>
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <c:if test="${not empty confirmableObject.fastServiceAvailable}">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">Быстрый платеж: </tiles:put>
                    <tiles:put name="data">
                        <c:set var="fastServiceAvailable" value="${confirmableObject.fastServiceAvailable ? 'on' : 'off'}"/>
                        <span class="bold">
                            <bean:message key="title.fastServiceAvailable.${fastServiceAvailable}" bundle="ermbBundle"/>
                        </span>
                    </tiles:put>
                </tiles:insert>
            </c:if>

        </tiles:put>
        <tiles:put name="preConfirmCommandKey" value="button.preConfirmSMS"/>
        <tiles:put name="confirmCommandKey" value="button.confirm"/>
        <tiles:put name="useAjax" value="true"/>
        <tiles:put name="ajaxUrl" value="${thisActionUrl}"/>
    </tiles:insert>
</html:form>
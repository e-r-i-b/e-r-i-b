<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/log/confirmationInfo" onsubmit="return setEmptyAction(event)">
<c:set var="isAudit" value="${param['isAudit']}"/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="dictionary">

<tiles:put name="pageTitle" type="string">
    <c:choose>
        <c:when test="${isAudit == 'true'}">
            <bean:message key="label.detail.confirm.payment"  bundle="logBundle" />
        </c:when>
        <c:otherwise>
           	<bean:message key="label.detail.confirm.entry" bundle="logBundle"/>
        </c:otherwise>
    </c:choose>
</tiles:put>

<tiles:put name="menu" type="string">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.log.cancel"/>
        <tiles:put name="commandHelpKey" value="button.log.cancel"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="image" value=""/>
        <tiles:put name="onclick" value="window.close();"/>
        <tiles:put name="viewType" value="blueBorder"/>
    </tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
    <tiles:insert definition="tableTemplate" flush="false">
        <tiles:put name="text">
            <c:choose>
                <c:when test="${isAudit == 'true'}">
                    <bean:message key="label.attempt.confirm.payment"  bundle="logBundle" />
                </c:when>
                <c:otherwise>
                    <bean:message key="label.attempt.logon" bundle="logBundle"/>
                </c:otherwise>
            </c:choose>
        </tiles:put>
        <tiles:put name="grid" >

            <sl:collection id="listElement" model="list" property="data" bundle="logBundle" >
                <sl:collectionItem title="№" value="${lineNumber}"/>
                <c:set var="lineNumber" value="${lineNumber + 1}"/>
                <sl:collectionItem title="label.datetime">
                    <fmt:formatDate value="${listElement.date.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                </sl:collectionItem>
                <sl:collectionItem title="label.action">
                    <c:choose>
                        <c:when test="${listElement.state == 'CONF_SUCCESS' or listElement.state == 'CONF_FAILED' or listElement.state == 'CONF_TIMEOUT'}">
                            Ввод одноразового кода подтверждения
                        </c:when>
                        <c:when test="${listElement.state == 'INIT_FAILED' or listElement.state == 'INIT_SUCCESS'}">
                            <c:choose>
                                <c:when test="${listElement.type == 'SMS'}">
                                    Отправка СМС с одноразовым кодом подтверждения
                                </c:when>
                                <c:when test="${listElement.type == 'PUSH'}">
                                    Отправка PUSH с одноразовым кодом подтверждения
                                </c:when>
                                <c:when test="${listElement.type == 'CARD'}">
                                    Инициализация подтверждения чековым паролем
                                </c:when>
                                <c:when test="${listElement.type == 'CAP'}">
                                    Инициализация подтверждения СAP-карте
                                </c:when>
                            </c:choose>
                        </c:when>
                        <%--
                        Поддержка старых типов
	SUCCESSFUL("успешно"),
	TIMOUT("истечение таймаута"),
	SYSTEM_ERROR("не удалось отправить SMS-пароль"),
	CARD_ERROR("нет доступных чековых паролей"),
	CAP_ERROR("не удалось инициировать подтверждение CAP паролем"),
	CLIENT_ERROR("неправильный ввод пароля"),
	NEW_PASSW("пароль не введен"),
                        --%>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${listElement.type == 'SMS'}">
                                    СМС-подтверждение
                                </c:when>
                                <c:when test="${listElement.type == 'PUSH'}">
                                    PUSH-подтверждение
                                </c:when>
                                <c:when test="${listElement.type == 'CARD'}">
                                    Подтверждение чековым паролем
                                </c:when>
                                <c:when test="${listElement.type == 'CAP'}">
                                    Подтверждения по СAP-карте
                                </c:when>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </sl:collectionItem>

                <sl:collectionItem title="label.description">
                    <c:choose>
                        <c:when test="${listElement.state == 'CONF_SUCCESS' or listElement.state == 'CONF_FAILED' or listElement.state == 'CONF_TIMEOUT'}">
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${listElement.type == 'SMS'}">
                                    Номер телефона: ${listElement.recipient}</br>
                                    Проверка IMSI: ${listElement.checkIMSI}</br>
                                    Сообщение: "${listElement.message}"
                                </c:when>
                                <c:when test="${listElement.type == 'PUSH'}">
                                    Платформа подключения: ${listElement.recipient}</br>
                                    Сообщение: "${listElement.message}"
                                </c:when>
                                <c:when test="${listElement.type == 'CARD'}">
                                    Чек № ${listElement.cardNumber}, пароль № ${listElement.passwordNumber}
                                </c:when>
                                <c:when test="${listElement.type == 'CAP'}">
                                    Номер карты: ${listElement.recipient}
                                </c:when>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </sl:collectionItem>

                <sl:collectionItem title="label.entries.state">
                    <c:choose>
                        <c:when test="${listElement.state == 'SUCCESSFUL'}">
                            <c:choose>
                                <c:when test="${isAudit == 'true'}">
                                    подтверждение платежа
                                </c:when>
                                <c:otherwise>
                                    выполнен вход
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            ${listElement.state.description}
                        </c:otherwise>
                    </c:choose>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
        <tiles:put name="emptyMessage"> <bean:message key="label.confirm.logon.empty"  bundle="logBundle" /></tiles:put>
    </tiles:insert>
</tiles:put>

</tiles:insert>
</html:form>
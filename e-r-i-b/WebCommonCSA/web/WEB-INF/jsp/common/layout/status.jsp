<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
    Файл с возможными состояниями Status
    STATUS_OK и STATUS_LOGIC_ERROR используются в login.jsp и iphone.jsp
    STATUS_END_SESSION используется в login.jsp
    STATUS_CRITICAL_ERROR устанавливается в jsp exceptions
    STATUS_PRODUCT_ERROR использиется в products/list
    STATUS_ACCESS_DENIED доступ запрещен
    STATUS_BANNED работа с приложением запрещена (например, ОС устройства не прошла проверку безопасности)
    STATUS_RESET_MGUID работа с приложением запрещена (например, неверно подтверждена регистрация приложения)
    STATUS_CARD_NUM_ERROR отсутствует номер карты при ргеистрации в mAPI
--%>
<c:set var="STATUS_OK" value="0"/><%-- ошибки отсутствуют --%>
<c:set var="STATUS_LOGIC_ERROR" value="1"/><%-- логическая ошибка --%>
<c:set var="STATUS_CRITICAL_ERROR" value="2"/><%-- критическая ошибка --%>
<c:set var="STATUS_END_SESSION" value="3"/><%-- сессия более недоступна --%>
<c:set var="STATUS_PRODUCT_ERROR" value="4"/><%-- ошибка пролучения продукта --%>
<c:set var="STATUS_ACCESS_DENIED" value="5"/><%-- доступ запрещен --%>
<c:set var="STATUS_BANNED" value="6"/><%-- работа с приложением запрещена --%>
<c:set var="STATUS_RESET_MGUID" value="7"/><%-- работа с приложением запрещена, необходимо сбросить mGUID --%>
<c:set var="STATUS_BLOCKING_RULE" value="8"/><%-- действует правило блокировки.Вход и регистрация запрещены --%>
<c:set var="STATUS_SIM_ERROR" value="9"/><%-- при отправке sms обнаружена замена сим-карты --%>
<c:set var="STATUS_CARD_NUM_ERROR" value="10"/><%-- отсутствует номер карты при ргеистрации в mAPI --%>

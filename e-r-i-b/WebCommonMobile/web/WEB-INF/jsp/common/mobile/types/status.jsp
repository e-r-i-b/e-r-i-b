<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
    ‘айл с возможными состо€ни€ми Status
    STATUS_OK и STATUS_LOGIC_ERROR используютс€ в login.jsp и iphone.jsp
    STATUS_END_SESSION используетс€ в login.jsp
    STATUS_CRITICAL_ERROR устанавливаетс€ в jsp exceptions
    STATUS_PRODUCT_ERROR использиетс€ в products/list
    STATUS_ACCESS_DENIED доступ запрещен
    STATUS_BANNED работа с приложением запрещена (например, ќ— устройства не прошла проверку безопасности)
    STATUS_RESET_MGUID работа с приложением запрещена (например, неверно подтверждена регистраци€ приложени€)
    STATUS_SIM_ERROR ѕри отправке SMS обнаружена замена SIM-карты (при регистрации приложени€)
    STATUS_CARD_NUM_ERROR отсутствует номер карты при ргеистрации в mAPI
--%>
<c:set var="STATUS_OK" value="0"/>
<%-- ошибки отсутствуют --%>
<c:set var="STATUS_LOGIC_ERROR" value="1"/>
<%-- логическа€ ошибка --%>
<c:set var="STATUS_CRITICAL_ERROR" value="2"/>
<%-- критическа€ ошибка --%>
<c:set var="STATUS_END_SESSION" value="3"/>
<%-- сесси€ более недоступна --%>
<c:set var="STATUS_PRODUCT_ERROR" value="4"/>
<%-- ошибка пролучени€ продукта --%>
<c:set var="STATUS_ACCESS_DENIED" value="5"/>
<%-- доступ запрещен --%>
<c:set var="STATUS_BANNED" value="6"/>
<%-- работа с приложением запрещена --%>
<c:set var="STATUS_RESET_MGUID" value="7"/>
<%-- работа с приложением запрещена, необходимо сбросить mGUID --%>
<c:set var="STATUS_SIM_ERROR" value="9"/>
<%-- при отправке sms обнаружена замена сим-карты --%>
<c:set var="STATUS_CARD_NUM_ERROR" value="10"/>
<%-- отсутствует номер карты при ргеистрации в mAPI --%>


<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<div class="mbkGuestMessage css3">
    <div class="mbkGuest_b-style">
        <%--TODO: после того как будет реализована верстка для этого сообщения, допилить его отображение. Исполнитель Гололобов Евгений--%>
        <c:set var="bankPhoneForSms">
            <bean:message key="loan.callFromBank.bankPhone" bundle="commonBundle"/>
        </c:set>
        <bean:message key="loan.callFromBank.messages.guestSession.sendSmsInfo" bundle="commonBundle"
                      arg0="${documentShortNumber}" arg1="${bankPhoneForSms}"/>
    </div>
</div>
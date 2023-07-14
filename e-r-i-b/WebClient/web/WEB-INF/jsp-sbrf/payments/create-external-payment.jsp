<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--@elvariable id="form" type="com.rssl.phizic.web.actions.payments.forms.EditServicePaymentForm"--%>

<%-- ѕервый шаг ввода данных платежа по внешней ссылке --%>
<html:form action="/external/payments/servicesPayments/edit" onsubmit="return setEmptyAction();">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:choose>
        <%-- ƒл€ оплаты ”Ё  (первый шаг редактировани€) используетс€ отдельна€ jsp --%>
        <c:when test="${form.UECPayOrder}">
            <tiles:insert definition="fnsMain">
                <tiles:put name="data" type="string">
                    <tiles:insert page="create-uec-payment-data.jsp" flush="false">
                        <%--распологать данный линию жизни по центру (в случае, если платеж показан в режиме без правого меню)--%>
                        <tiles:put name="byCenter" value="Center"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <tiles:insert page="edit-payment-data.jsp" flush="false"/>
        </c:otherwise>
    </c:choose>
</html:form>

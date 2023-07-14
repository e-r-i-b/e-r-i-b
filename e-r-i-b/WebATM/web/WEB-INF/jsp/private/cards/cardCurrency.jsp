<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/cards/currency">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.card}">
                <cardNumber>${phiz:getCutCardNumber(form.card.number)}</cardNumber>
                <tiles:insert definition="currencyType" flush="false">
                    <tiles:put name="currency" beanName="form" beanProperty="card.currency"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
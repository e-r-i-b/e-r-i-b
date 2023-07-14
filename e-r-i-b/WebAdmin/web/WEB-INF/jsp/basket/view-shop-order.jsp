<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/async/basket/shoporder/view">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="id" value="${frm.formName}"/>
        <tiles:put name="alignTable" value="center"/>
        <tiles:put name="name" value="Оплата заказа ${frm.providerName}"/>
        <tiles:put name="data">
            ${frm.html}
        </tiles:put>
    </tiles:insert>
</html:form>


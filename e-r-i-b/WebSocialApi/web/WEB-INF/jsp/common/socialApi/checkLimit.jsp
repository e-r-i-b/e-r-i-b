<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<fmt:setLocale value="ru-RU"/>

<html:form action="/checkLimit">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:set var="limit" value="${form.totalAmount}"/>
            <c:if test="${not empty limit}">
                <tiles:insert definition="mobileMoneyType" flush="false">
                    <tiles:put name="name" value="balance"/>
                    <tiles:put name="money" beanName="limit"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
        <tiles:put name="messagesBundle" value="securityBundle"/>
    </tiles:insert>
</html:form>

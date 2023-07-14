<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/creditcards/incomes/products_list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    &nbsp;   <%-- странно, но без этого, в IE6 из ajax приходит обрезанная страница (обрезаются вся невидимая пользователю информация до первой видимой части)--%>
    <c:set var="productNames" value=""/>
    <c:set var="isFirst" value="true"/>
    <c:forEach var="product" items="${form.data}">
        <c:if test="${!isFirst}">
            <c:set var="productNames" value="${productNames}, "/>
        </c:if>
        <c:set var="productNames" value="${productNames}${product.name}"/>
        <c:set var="isFirst" value="false"/>
    </c:forEach>
    ${productNames}

</html:form>
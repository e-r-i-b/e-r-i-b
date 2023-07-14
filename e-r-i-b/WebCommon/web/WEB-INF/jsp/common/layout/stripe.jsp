<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<tiles:importAttribute/>

<%--
компонент для отображения состояний платежа
name - текст
current - является ли элемент текущим(true - является)
future - если true - элемент после текущего, если false - перед текущим
width - ширина блока
show - если false не отображаем
--%>
<c:if test="${show}">
    <c:choose>
        <c:when test="${current}">
            <div class="currentStripe" <c:if test="${not empty width}">style="width:${width}"</c:if>>
                <div class="currentStripeRightCircle">
                    &nbsp;
                </div>
                <div class="currentStripeLeftCircle">
                    &nbsp;
                </div>
                <div class="currentStripeText"><div class="stepNamePos">${name}</div></div>
            </div>
        </c:when>
        <c:when test="${future}">
            <div class="futureStripe" <c:if test="${not empty width}">style="width:${width}"</c:if>>
                <div class="futureStripeRightCircle">
                    <div class="stepNamePos">${name}</div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="pastStripe" <c:if test="${not empty width}">style="width:${width}"</c:if>>
                <div class="pastStripeLeftCircle">
                    <div class="stepNamePos">${name}</div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%-- Шаблон поля ресурса списания для использования в платежах, формируемых на  jsp
    name - имя поля
    title - подпись к полю для клиента
    chargeOffResources - список линков списания
    value - текущее значение (для установки selected)
--%>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<availableFromResources>
    <name>${name}</name>
    <title>${title}</title>
    <type>resource</type>
    <required>true</required>
    <editable>true</editable>
    <visible>true</visible>
    <resourceType>
        <c:if test="${not empty chargeOffResources}">
            <availableValues>
                <c:forEach var="link" items="${chargeOffResources}">
                    <valueItem>
                        <value>${link.code}</value>
                        <selected>${not empty value and value == link.code}</selected>
                        <c:choose>
                            <c:when test="${link.resourceType == 'CARD'}">
                                <c:set var="displayedValue" value="${phiz:getCutCardNumber(link.number)} [${link.name}]"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="displayedValue" value="${link.number} [${link.name}]"/>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${not empty displayedValue}">
                            <displayedValue><c:out value="${displayedValue}"/></displayedValue>
                        </c:if>
                        <c:if test="${form.mobileApiVersion >= 5.20}">
                            <currency>${link.currency.code}</currency>
                        </c:if>
                    </valueItem>
                </c:forEach>
            </availableValues>
        </c:if>
    </resourceType>
    <changed>false</changed>
</availableFromResources>
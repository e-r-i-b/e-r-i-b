<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/async/userprofile/getContactInfo">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="contact" value="${form.contact}"/>
    {
    <c:if test="${not empty contact}">
        "id": "${contact.id}",
        "fullName" : "${contact.fullName}",
        "cardNumber" : "${phiz:getCutCardNumber(contact.cardNumber)}",
        "phone" : "${phiz:getCutPhoneForAddressBook(contact.phone)}",
        <c:choose>
            <c:when test="${not contact.incognito}">
                "sberbankClient" : "${contact.sberbankClient}",
                "avatarPath" : "${contact.avatarPath}"
            </c:when>
            <c:otherwise>
                "sberbankClient" : "false"
            </c:otherwise>
        </c:choose>
    </c:if>
    <c:choose>
        <c:when test="${form.externalSystemError}">
            <c:if test="${not empty contact}">,</c:if>
            "externalSystemError":  "true"
        </c:when>
        <c:when test="${form.limitExceeded}">
            <c:if test="${not empty contact}">,</c:if>
            "limitExceeded":  "true"
        </c:when>
        <c:otherwise>
            <c:set var="userInfo" value="${form.userInfo}"/>
            <c:if test="${not empty userInfo}">
                <c:if test="${not empty contact}">,</c:if>
                <c:set var="client" value="${userInfo.first}"/>
                "wayFIO": "${phiz:getFormattedPersonName(client.firstName, client.surName, client.patrName)}"
                <c:if test="${empty contact}">, "sberbankClient" : "${form.sberbankClient}"</c:if>
                <c:if test="${not empty userInfo.second}">
                    , "cardCurrency" : "${userInfo.second.currency.code}"
                </c:if>
            </c:if>
        </c:otherwise>
    </c:choose>
    <c:if test="${not empty form.receiverAvatarPath}">
        , "avatarPath" : "${form.receiverAvatarPath}"
    </c:if>
    }

</html:form>
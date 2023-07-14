<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/private/contacts/search">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <addressbook>
                <c:forEach var="entry" items="${form.contacts}">
                    <contact>
                        <id>${entry.id}</id>
                        <phone>${phiz:getCutPhoneForAddressBook(entry.phone)}</phone>
                        <name><c:out value="${entry.fullName}"/></name>
                        <incognito>${entry.incognito}</incognito>
                        <sberbankclient>${entry.sberbankClient}</sberbankclient>
                        <c:if test="${not empty entry.alias}">
                            <alias><c:out value="${entry.alias}"/></alias>
                        </c:if>
                        <c:if test="${not empty entry.avatarPath}">
                            <avatar>${entry.avatarPath}</avatar>
                        </c:if>
                        <c:if test="${not empty entry.cardNumber}">
                            <cardnumber>${phiz:getCutCardNumber(entry.cardNumber)}</cardnumber>
                        </c:if>
                        <category>${entry.category}</category>
                        <trusted>${entry.trusted}</trusted>
                        <frequencyp2p>${entry.frequencypP2P}</frequencyp2p>
                        <frequencypay>${entry.frequencyPay}</frequencypay>
                    </contact>
                </c:forEach>
            </addressbook>
        </tiles:put>
    </tiles:insert>
</html:form>
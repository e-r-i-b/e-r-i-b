<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/private/mail/edit">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="mailSavedState" value="${form.fields.mailState}"/>               <%-- статус письма --%>
    <c:set var="isNotAnswer"    value="${form.fields.isNotAnswer}"/>             <%-- не ответ (parentId == null) --%>
    <c:set var="view"           value="${mailSavedState == 'NEW'}"/>             <%-- письмо отправлено или пришло (просмотр письма) --%>
    <c:set var="reply"          value="${!view && !isNotAnswer}"/>               <%-- ответ -- новый ответ или черновик ответа (не отправленный ответ) --%>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.id and not empty form.fields.num}">
                <mail>
                    <id>${form.id}</id>
                    <state>${form.fields.mailState}</state>
                    <num>${form.fields.num}</num>
                    <type>${form.fields.type}</type>
                    <themeId>${form.fields.mail_theme}</themeId>
                    <c:set var="responseMethod" value="${form.fields.response_method}"/>
                    <responseMethod>${responseMethod}</responseMethod>
                    <c:choose>
                        <c:when test="${responseMethod == 'BY_PHONE'}">
                            <phone>${form.fields.phone}</phone>
                        </c:when>
                        <c:when test="${responseMethod == 'IN_WRITING'}">
                            <eMail>${form.fields.email}</eMail>
                        </c:when>
                    </c:choose>
                    <subject><c:out value="${form.fields.subject}"/></subject>
                    <body><c:out value="${form.fields.body}"/></body>
                    <c:set var="fileName" value="${form.fields.fileName}"/>
                    <c:if test="${not empty fileName}">
                        <fileName><c:out value="${fileName}"/></fileName>
                    </c:if>
                    <c:if test="${reply && not empty form.fields.correspondence}">
                        <sl:collection id="mail" property="fields.correspondence" model="xml-list" title="correspondence">
                            <sl:collectionItem title="mail">
                                <tiles:insert definition="mobileDateTimeType" flush="false">
                                    <tiles:put name="name" value="dateTime"/>
                                    <tiles:put name="calendar" beanName="mail" beanProperty="date"/>
                                </tiles:insert>
                                <direction>${mail.direction}</direction>
                                <body><c:out value="${mail.body}"/></body>
                            </sl:collectionItem>
                        </sl:collection>
                    </c:if>
                </mail>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:form action="/private/mail/inbox">
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <sl:collection id="listElement" property="data" model="xml-list" title="mailList">
                <sl:collectionItem title="mail">
                    <c:set var="mail" value="${listElement[0]}"/>
                    <c:set var="recipient" value="${listElement[1]}"/>
                    <id>${mail.id}</id>
                    <num>${mail.num}</num>
                    <subject><c:out value="${mail.subject}"/></subject>
                    <tiles:insert definition="mobileDateTimeType" flush="false">
                        <tiles:put name="name" value="dateTime"/>
                        <tiles:put name="calendar" beanName="mail" beanProperty="date"/>
                    </tiles:insert>
                    <recipientState>${recipient.state}</recipientState>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>
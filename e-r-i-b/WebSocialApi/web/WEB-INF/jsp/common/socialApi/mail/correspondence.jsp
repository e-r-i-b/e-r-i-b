<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/private/mail/correspondence">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.correspondence}">
                <sl:collection id="mail" property="correspondence" model="xml-list" title="correspondence">
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
        </tiles:put>
    </tiles:insert>
</html:form>
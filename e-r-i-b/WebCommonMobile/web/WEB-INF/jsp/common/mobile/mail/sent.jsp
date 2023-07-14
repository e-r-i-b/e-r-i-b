<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:form action="/private/mail/sent">
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <sl:collection id="mail" property="data" model="xml-list" title="mailList">
                <sl:collectionItem title="mail">
                    <id>${mail.id}</id>
                    <num>${mail.num}</num>
                    <subject><c:out value="${mail.subject}"/></subject>
                    <tiles:insert definition="mobileDateTimeType" flush="false">
                        <tiles:put name="name" value="dateTime"/>
                        <tiles:put name="calendar" beanName="mail" beanProperty="date"/>
                    </tiles:insert>
                    <state>${mail.state}</state>
                    <type>${mail.type}</type>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>
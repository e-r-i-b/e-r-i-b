<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:form action="/private/mail/themes">
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <sl:collection id="theme" property="mailThemes" model="xml-list" title="themeList">
                <sl:collectionItem title="theme">
                    <id>${theme.id}</id>
                    <description><c:out value="${theme.description}"/></description>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>
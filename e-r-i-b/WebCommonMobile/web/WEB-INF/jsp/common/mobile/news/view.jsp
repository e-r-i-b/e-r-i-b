<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/news/view">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="news" value="${form.news}"/>
    <c:set var="text" value="${phiz:processBBCode(news.text)}"/>
    <tiles:importAttribute/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${text != ''}">
                <text><![CDATA[ ${text} ]]></text>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<fmt:setLocale value="ru-RU"/>
<html:form action="/private/news/list">

    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.data}">
                <sl:collection id="entity" property="data" model="xml-list" title="newsList">
                    <sl:collectionItem title="news">

                        <id>${entity.id}</id>
                        <title>${fn:escapeXml(entity.title)}</title>
                        <date><fmt:formatDate value="${entity.newsDate.time}" pattern="dd.MM.yyyy"/></date>
                        <shortText><![CDATA[ ${phiz:processBBCode(entity.shortText)} ]]></shortText>
                        <important>${entity.important}</important>

                    </sl:collectionItem>
                </sl:collection>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
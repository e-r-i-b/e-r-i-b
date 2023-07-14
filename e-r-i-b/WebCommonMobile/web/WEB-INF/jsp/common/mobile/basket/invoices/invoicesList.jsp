<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/private/basket/invoices/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone">
        <tiles:put name="data" type="string">
            <c:if test="${not empty form.data}">
                <sl:collection id="item" property="data" model="xml-list" title="invoices">
                    <sl:collectionItem title="invoice">
                        <id>${item.id}</id>
                        <name>${fn:escapeXml(item.name)}</name>
                        <providerName>${item.providerName}</providerName>
                        <imageId>${item.imageId}</imageId>
                        <entityName>${item.entityName}</entityName>
                        <state>${item.state}</state>
                        <keyName>${item.keyName}</keyName>
                        <keyValue>${item.keyValue}</keyValue>
                        <sum>${item.sum}</sum>
                        <type>${item.type}</type>
                        <externalId>${item.externalId}</externalId>
                        <isNew>${item.isNew}</isNew>
                    </sl:collectionItem>
                </sl:collection>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
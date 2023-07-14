<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/private/payments/category">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <sl:collection id="element" property="list" model="xml-list" title="elementList">
                <sl:collectionItem title="element">
                    <id>${element[0]}</id>
                    <type>${form.type}</type>
                    <title><c:out value="${element[1]}"/></title>
                    <c:if test="${not empty element[2]}">
                        <description><c:out value="${element[2]}"/></description>
                    </c:if>
                    <tiles:insert definition="imageType" flush="false">
                        <tiles:put name="name" value="imgURL"/>
                        <tiles:put name="id" value="${element[3]}"/>
                        <tiles:put name="url" value="${element[4]}"/>
                    </tiles:insert>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>
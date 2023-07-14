<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<html:form action="/private/dictionary/servicesPayments">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <sl:collection id="element" property="paymentCategories" model="xml-list" title="elementList">
                <sl:collectionItem title="element">
                    <id><c:out value="${element.name}"/></id>
                    <type>${form.type}</type>
                    <title><c:out value="${element.name}"/></title>
                    <description><c:out value="${element.value}"/></description>
                    <c:if test="${! empty element.name}">
                        <tiles:insert definition="imageType" flush="false">
                            <tiles:put name="name" value="imgURL"/>
                            <tiles:put name="url">/<bean:message key="category.operations.${element.name}.image" bundle="paymentServicesBundle"/></tiles:put>
                        </tiles:insert>
                    </c:if>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<html:form action="/private/insurance/view" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="favoriteLinkType" value="INSURANCE_LINK"/>
    <c:set var="insuranceLink" value="${form.link}"/>
    <c:set var="insuranceApp" value="${form.insuranceApp}"/>
    <tiles:insert definition="insurance">
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Страхование"/>
                <tiles:put name="action" value="/private/insurance/list.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><c:out value="${insuranceLink.name}"/></tiles:put>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <html:hidden property="id"/>
            <c:if test="${not empty insuranceLink && not empty  insuranceApp}">
                <%@include file="detail.jsp" %>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
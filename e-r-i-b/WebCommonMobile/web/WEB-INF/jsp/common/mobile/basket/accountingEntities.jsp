<%@ taglib prefix="html"  uri="http://jakarta.apache.org/struts/tags-html"  %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%@ taglib prefix="bean"  uri="http://jakarta.apache.org/struts/tags-bean"  %>
<%@ taglib prefix="phiz"  uri="http://rssl.com/tags"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page contentType="text/xml;charset=windows-1251" language="java" %>

<html:form action="/private/basket/accountingEntity/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.accountingEntities}">
            <accountingEntities>
            <c:forEach items="${form.accountingEntities}" var="accountingEntity">
                <c:set var="accountingEntityId">${accountingEntity.id}</c:set>
                <accountingEntity>
                    <bean:define id="accountingEntityType" name="accountingEntity" property="type"/>
                    <id>${accountingEntityId}</id>
                    <name>${accountingEntity.name}</name>
                    <type>
                        <code>${accountingEntityType.code}</code>
                        <defaultName>${accountingEntityType.defaultName}</defaultName>
                    </type>

                    <c:if test="${not empty form.serviceCategories[accountingEntityId]}">
                    <serviceCategories>
                        <c:forEach var="serviceCategories" items="${form.serviceCategories[accountingEntityId]}">
                        <serviceCategory>
                            <id>${serviceCategories.id}</id>
                            <code>${serviceCategories.code}</code>
                            <buttonName>${serviceCategories.buttonName}</buttonName>
                            <accountName>${serviceCategories.accountName}</accountName>
                        </serviceCategory>
                        </c:forEach>
                    </serviceCategories>
                    </c:if>
                </accountingEntity>
            </c:forEach>
            </accountingEntities>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
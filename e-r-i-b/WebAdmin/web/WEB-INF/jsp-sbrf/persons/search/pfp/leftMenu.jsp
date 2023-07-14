<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset" service="EmployeePfpEditService">
	<tiles:put name="enabled" value="${submenu != 'searchClient' && submenu != 'clientNotFound'}"/>
	<tiles:put name="action"  value="/pfp/person/search.do"/>
	<tiles:put name="text">
        <bean:message bundle="pfpPassingBundle" key="leftMenu.label.person.search"/>
	</tiles:put>
	<tiles:put name="title">
        <bean:message bundle="pfpPassingBundle" key="leftMenu.label.person.search"/>
	</tiles:put>
</tiles:insert>
<c:set var="person" value="${phiz:getPersonInfo()}"/>
<c:if test="${person != null && phiz:canWorkWhithPerson(person) && submenu != 'clientNotFound'}">
    <tiles:insert definition="leftMenuInset" service="EmployeePfpEditService">
        <tiles:put name="enabled" value="${submenu != 'personInfo'}"/>
        <c:choose>
            <c:when test="${person.creationType eq 'POTENTIAL'}">
                <tiles:put name="action"  value="/pfp/person/potentialPersonInfo"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="action"  value="/pfp/person/showInfo"/>
            </c:otherwise>
        </c:choose>
        <tiles:put name="text">
            <bean:message bundle="pfpPassingBundle" key="leftMenu.label.person.info"/>
        </tiles:put>
        <tiles:put name="title">
            <bean:message bundle="pfpPassingBundle" key="leftMenu.label.person.info"/>
        </tiles:put>
    </tiles:insert>
    <c:if test="${not empty person.id}">
        <tiles:insert definition="leftMenuInset" service="EmployeePfpEditService">
            <tiles:put name="enabled" value="${submenu != 'PersonPFP'}"/>
            <tiles:put name="action"  value="/person/pfp/edit.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpPassingBundle" key="leftMenu.label.person.passing"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpPassingBundle" key="leftMenu.label.person.passing"/>
            </tiles:put>
        </tiles:insert>
    </c:if>
    <c:if test="${person.creationType ne 'POTENTIAL'}">
        <tiles:insert definition="leftMenuInset" service="EmployeePfpEditService">
            <tiles:put name="enabled" value="${submenu != 'PersonFinances'}"/>
            <tiles:put name="action"  value="/pfp/person/finance.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpPassingBundle" key="leftMenu.label.person.account.info"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpPassingBundle" key="leftMenu.label.person.account.info"/>
            </tiles:put>
        </tiles:insert>
    </c:if>
</c:if>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/rates/list" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="department" value="${phiz:getDepartmentForCurrentClient()}"/>
<c:set var="rates">
    <c:if test="${empty form.type or form.type == 'currency'}">
        <%-- курс USD к рублю --%>
        <tiles:insert definition="atmRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="USD"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс EUR к рублю --%>
        <tiles:insert definition="atmRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="EUR"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
    </c:if>
    <c:if test="${empty form.type or form.type == 'metal'}">
        <%-- курс золота к рублю --%>
        <tiles:insert definition="atmRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="A98"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс серебра к рублю --%>
        <tiles:insert definition="atmRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="A99"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс платины к рублю --%>
        <tiles:insert definition="atmRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="A76"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс палладия к рублю --%>
        <tiles:insert definition="atmRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="A33"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
    </c:if>
</c:set>

<tiles:insert definition="atm" flush="false">
    <tiles:put name="data">
        <c:if test="${fn:trim(rates) != ''}">
            <rates>
                ${rates}
            </rates>
        </c:if>
    </tiles:put>
</tiles:insert>
</html:form>
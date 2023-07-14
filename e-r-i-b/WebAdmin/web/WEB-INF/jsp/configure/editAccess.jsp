<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<c:set var="accessType" value="${policy.accessType}"/>
<c:set var="helpers"    value="${subform.helpers}"/>
<c:set var="namePrefix" value="${accessType}Access"/>
<c:set var="idPrefix"   value="${accessType}"/>

<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        <c:out value="${policy.description}"/>
    </tiles:put>
    <tiles:put name="data">
        <html:checkbox styleId="${idPrefix}_enabled" property="${namePrefix}.enabled" onclick="onAccessChanged('${idPrefix}')"/>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Подтверждение входа
    </tiles:put>
    <tiles:put name="data">
        <c:set var="choice" value="${policy.authenticationChoice}"/>
        <c:if test="${not (empty choice)}">
            <html:select property="${namePrefix}.properties(${choice.property})">
                <c:forEach var="option" items="${choice.options}">
                    <html:option value="${option.value}"><c:out value="${option.name}"/></html:option>
                </c:forEach>
            </html:select>
        </c:if>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        Подтверждение операций
    </tiles:put>
    <tiles:put name="data">
        <c:set var="choice" value="${policy.confirmationChoice}"/>

        <c:if test="${not (empty choice)}">
            <html:select property="${namePrefix}.properties(${choice.property})">
                <c:forEach var="option" items="${choice.options}">
                    <html:option value="${option.value}"><c:out value="${option.name}"/></html:option>
                </c:forEach>
            </html:select>
        </c:if>
    </tiles:put>
</tiles:insert>

<script type="text/javascript">
	onAccessChanged('${idPrefix}');
</script>

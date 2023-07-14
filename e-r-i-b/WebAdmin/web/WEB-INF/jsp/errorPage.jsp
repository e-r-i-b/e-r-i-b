<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="main">
	<tiles:put name="data" type="string">
        <tiles:insert definition="roundBorderLight" flush="false">
            <tiles:put name="color" value="red"/>
            <tiles:put name="data">
                <c:set var="errorMessage" value="${phiz:getErrorMessage(pageContext)}"/>
                <c:choose>
                    <c:when test="${not empty errorMessage}">
                        ${phiz:processBBCode(errorMessage)}
                    </c:when>
                    <c:when test="${not empty errorMessageKey}">
                        <bean:message key="${errorMessageKey}" bundle="commonBundle"/>
                    </c:when>
                    <c:otherwise>
                        <bean:message key="error.errorHeader" bundle="commonBundle"/>
                    </c:otherwise>
                </c:choose>
            </tiles:put>
        </tiles:insert>
	</tiles:put>
</tiles:insert>

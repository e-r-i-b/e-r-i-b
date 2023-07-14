<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ include file="/WEB-INF/jsp/common/socialApi/types/status.jsp"%>
<html:form action="/private/cards/abstract">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <c:if test="${form.backError}">
            <tiles:put name="status" value="${STATUS_CRITICAL_ERROR}"/>
            <tiles:put name="errorDescription" value="Информация из АБС временно недоступна."/>
        </c:if>
        <tiles:put name="data">
            <c:if test="${!empty form.cardAbstract.transactions}">
                <sl:collection id="transaction" property="cardAbstract.transactions" model="xml-list" title="operations">
                    <sl:collectionItem title="operation">
                        <%@ include file="transaction.jsp"%>
                    </sl:collectionItem>
                </sl:collection>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
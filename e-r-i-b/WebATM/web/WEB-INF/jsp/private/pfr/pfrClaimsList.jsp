<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/types/status.jsp" %>

<html:form action="/private/pfrClaims/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <c:if test="${form.error}">
                <tiles:put name="status">${STATUS_LOGIC_ERROR}</tiles:put>
                <tiles:put name="errorDescription">Информация по последним запросам в ПФР временно недоступна.</tiles:put>
            </c:if>
            <c:if test="${not empty form.claims}">
                <sl:collection id="claim" property="claims" model="xml-list" title="claims">
                    <sl:collectionItem title="claim">
                        <id>${claim.id}</id>
                        <state>${claim.state.code}</state>
                        <tiles:insert definition="atmDateType" flush="false">
                            <tiles:put name="name" value="date"/>
                            <tiles:put name="calendar" beanName="claim" beanProperty="dateCreated"/>
                            <tiles:put name="pattern" value="dd.MM.yyyy'T'HH:mm:ss.SSS0"/>
                        </tiles:insert>
                    </sl:collectionItem>
                </sl:collection>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
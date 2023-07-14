<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:html>
<head>
    <title>–едактирование копилки</title>
</head>

<body>
<h1>–едактирование копилки: сохранение(подтверждение)</h1>

<html:form action="/atm/editMoneyBox" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="formDocument" value="${form.document}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/moneyboxes/edit.do"/>
        <c:if test="${empty formDocument}">
            <tiles:put name="operation" value="save"/>
        </c:if>

        <tiles:put name="data">
            <c:choose>
                <c:when test="${empty formDocument}">
                    <c:set var="document" value="${form.response.initialData.editMoneyBoxClaim}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="document" value="${formDocument.editMoneyBoxClaimDocument}"/>
                </c:otherwise>
            </c:choose>
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <tiles:insert page="field.jsp" flush="false">
                        <c:choose>
                            <c:when test="${empty formDocument}">
                                <tiles:put name="field" beanName="document" beanProperty="documentNumber"/>
                            </c:when>
                            <c:otherwise>
                                <tiles:put name="field" beanName="formDocument" beanProperty="documentNumber"/>
                            </c:otherwise>
                        </c:choose>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="moneyBoxName"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="toResource"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="fromResource"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="moneyBoxSumType"/>
                    </tiles:insert>
                    <c:choose>
                        <c:when test="${empty formDocument}">
                            <%-- регул€рный --%>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="fixedSumma.eventType"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="fixedSumma.nextPayDate"/>
                            </tiles:insert>
                            <%-- по % --%>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="byPercent.percent"/>
                            </tiles:insert>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${document.fixedSumma!=null}">
                                    <%-- регул€рный --%>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="fixedSumma.eventType"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="fixedSumma.nextPayDate"/>
                                    </tiles:insert>
                                </c:when>
                                <c:otherwise>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="byPercent.percent"/>
                                    </tiles:insert>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="amount"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
</body>
</html:html>
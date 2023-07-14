<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jsp/common/socialApi/types/status.jsp"%>
<tiles:importAttribute/>

<html:form action="/private/payments/loan/claim">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="minDuration" value="1"/>
    <c:set var="maxDuration" value="999"/>
    <c:set var="kind"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <loanProductStage>
                <c:forEach items="${form.data}" var="product">
                    <option>
                        <c:if test="${kind ne product.loanKind.name}">
                            <c:set var="kind" value="${product.loanKind.name}"/>
                            <loanKindName><c:out value="${kind}"/></loanKindName>
                        </c:if>
                        <name><c:out value="${product.name}"/></name>
                        <duration>
                            <c:choose>
                                <c:when test="${empty(product.minDuration.duration)}">
                                    <min><c:out value="${minDuration}"/></min>
                                </c:when>
                                <c:otherwise>
                                    <min><c:out value="${product.minDuration.duration}"/></min>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${empty(product.maxDuration.duration)}">
                                    <max><c:out value="${maxDuration}"/></max>
                                </c:when>
                                <c:otherwise>
                                    <max><c:out value="${product.maxDuration.duration}"/></max>
                                </c:otherwise>
                            </c:choose>
                        </duration>
                        <c:if test="${product.needInitialInstalment}">
                            <initialInstalment>
                                <min><c:out value="${product.minInitialInstalment}"/></min>
                                <max><c:out value="${product.maxInitialInstalment}"/></max>
                            </initialInstalment>
                        </c:if>
                        <needSurety><c:out value="${product.needSurety}"/></needSurety>
                        <conditions>
                            <c:forEach items="${product.conditions}" var="condition">
                                <condition>
                                    <changeDate><c:out value="${product.changeDate.timeInMillis}"/></changeDate>
                                    <loanId><c:out value="${condition.id}"/></loanId>
                                    <amount>
                                        <c:if test="${not empty condition.minAmount}">
                                            <c:set var="sum" value="${condition.minAmount}"/>
                                            <tiles:insert definition="mobileMoneyType" flush="false">
                                                <tiles:put name="name" value="min" />
                                                <tiles:put name="money" beanName="sum"/>
                                                <tiles:put name="noCents" value="true"/>
                                            </tiles:insert>
                                        </c:if>
                                        <c:if test="${not empty condition.maxAmount or not empty condition.maxAmountPercent}">
                                            <max>
                                                <c:choose>
                                                    <c:when test="${not empty condition.maxAmount}">
                                                        <c:set var="sum" value="${condition.maxAmount}"/>
                                                        <tiles:insert definition="mobileMoneyType" flush="false">
                                                            <tiles:put name="name" value="maxAmount" />
                                                            <tiles:put name="money" beanName="sum"/>
                                                            <tiles:put name="noCents" value="true"/>
                                                        </tiles:insert>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <maxPercent><c:out value="${condition.maxAmountPercent}"/></maxPercent>
                                                    </c:otherwise>
                                                </c:choose>
                                                <include><c:out value="${condition.maxInterestRateInclude}"/></include>
                                            </max>
                                        </c:if>
                                    </amount>
                                    <c:if test="${not empty condition.minInterestRate or not empty condition.maxInterestRate}">
                                        <interestRate>
                                            <c:if test="${not empty condition.minInterestRate}">
                                                <min><c:out value="${condition.minInterestRate}"/></min>
                                            </c:if>
                                            <c:if test="${not empty condition.maxInterestRate}">
                                                <max>
                                                    <maxAmount><c:out value="${condition.maxInterestRate}"/></maxAmount>
                                                    <include><c:out value="${condition.maxAmountInclude}"/></include>
                                                </max>
                                            </c:if>
                                        </interestRate>
                                    </c:if>
                                </condition>
                            </c:forEach>
                        </conditions>
                        <c:if test="${not empty product.additionalTerms}">
                            <additionalTerms><c:out value="${product.additionalTerms}"/></additionalTerms>
                        </c:if>
                    </option>
                </c:forEach>
            </loanProductStage>
        </tiles:put>
    </tiles:insert>
</html:form>

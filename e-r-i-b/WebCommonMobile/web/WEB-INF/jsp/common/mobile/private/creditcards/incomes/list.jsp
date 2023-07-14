<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jsp/common/mobile/types/status.jsp"%>
<tiles:importAttribute/>
<html:form action="/private/payments/loan/cardClaim">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <incomeStage>
                <c:forEach var="incomeLevel" items="${form.data}">
                    <option>
                        <id><c:out value="${incomeLevel.id}"/></id>
                        <c:if test="${incomeLevel.minIncome != null}">
                            <c:set var="sum" value="${incomeLevel.minIncome}"/>
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="minIncome" />
                                <tiles:put name="money" beanName="sum"/>
                            </tiles:insert>
                        </c:if>
                        <c:if test="${incomeLevel.maxIncome != null}">
                            <c:set var="sum" value="${incomeLevel.maxIncome}"/>
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="maxIncome" />
                                <tiles:put name="money" beanName="sum"/>
                            </tiles:insert>
                            <maxIncomeInclude><c:out value="${incomeLevel.maxIncomeInclude}"/></maxIncomeInclude>
                        </c:if>
                    </option>
                </c:forEach>
            </incomeStage>
        </tiles:put>
        <c:if test="${!form.loanCardClaimAvailable}">
            <tiles:put name="status">${STATUS_CRITICAL_ERROR}</tiles:put>
        </c:if>
    </tiles:insert>
</html:form>

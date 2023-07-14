<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/loans/list" onsubmit="return setEmptyAction(event)">
<tiles:importAttribute/>
<tiles:insert definition="accountInfo">
<tiles:put name="mainmenu" value="Loans"/>
<tiles:put name="enabledLink" value="false"/>
<tiles:put name="menu" type="string"/>
<tiles:put name="data" type="string">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:if test="${phiz:impliesOperation('LoanProductListOperation', 'LoanProduct')}">
        <div class="mainWorkspace productListLink">
            <tiles:insert definition="paymentsPaymentCardsDiv" service="LoanProduct" operation="LoanProductListOperation"
                              flush="false">
                <tiles:put name="serviceId" value="LoanProduct"/>
                <tiles:put name="image" value="credit_64.png"/>
                <tiles:put name="action" value="/private/payments/loan_product"/>
            </tiles:insert>
            <div class="clear"></div>
        </div>
    </c:if>

        <c:choose>
            <c:when test="${not empty form.activeLoans || not empty form.blockedLoans}">
                <jsp:include page="annLoanMessageWindow.jsp" flush="false"/>
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title" value="Кредиты"/>
                    <tiles:put name="data">
                        <c:if test="${not empty form.activeLoans}">
                            <c:set var="elementsCount" value="${fn:length(form.activeLoans)-1}"/>
                            <logic:iterate id="listElement" name="ShowAccountsForm" property="activeLoans" indexId="i">
                                <c:set var="loanLink" value="${listElement}" scope="request"/>
                                <%@ include file="/WEB-INF/jsp-sbrf/loans/loanTemplate.jsp" %>
                                <c:if test="${elementsCount != i}">
                                    <div class="productDivider"></div>
                                </c:if>
                            </logic:iterate>
                        </c:if>
                    </tiles:put>
                </tiles:insert>

                <c:if test="${not empty form.blockedLoans}">
                    <tiles:insert definition="hidableRoundBorder" flush="false">
                        <a id="closedLoans"><tiles:put name="title" value="Закрытые кредиты"/></a>
                        <tiles:put name="color" value="whiteTop"/>
                        <tiles:put name="data">
                            <c:set var="elementsCount" value="${fn:length(form.blockedLoans)-1}"/>
                            <logic:iterate id="listElement" name="ShowAccountsForm" property="blockedLoans" indexId="i">
                                <c:set var="loanLink" value="${listElement}" scope="request"/>
                                <%@ include file="/WEB-INF/jsp-sbrf/loans/loanTemplate.jsp" %>
                                <c:if test="${elementsCount != i}">
                                    <div class="productDivider"></div>
                                </c:if>
                            </logic:iterate>
                        </tiles:put>
                    </tiles:insert>
                </c:if>
            </c:when>
            <c:otherwise>
                <c:if test="${not form.allLoanDown}">
                    <div id="infotext">
                        <c:set var="creationType">${phiz:getPersonInfo().creationType}</c:set>
                        <c:choose>
                            <c:when test="${creationType == 'UDBO' || creationType == 'SBOL'}">
                                <tiles:insert page="loansEmpty.jsp?emptyLoanOffer=${form.emptyLoanOffer}" flush="false"/>
                            </c:when>
                            <c:otherwise>
                                <tiles:insert page="/WEB-INF/jsp-sbrf/needUDBO.jsp" flush="false">
                                    <tiles:put name="product" value="кредитам" />
                                </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>

</tiles:put>
</tiles:insert>
</html:form>

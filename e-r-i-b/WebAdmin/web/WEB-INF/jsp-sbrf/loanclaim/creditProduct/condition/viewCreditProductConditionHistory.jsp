<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loanclaim/credit/condition/history/view" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="dictionary">
        <tiles:put name="pageTitle" value="Àðõèâ óñëîâèé ïî êðåäèòó"/>
        <tiles:put name="data" type="string">

            <table width="100%" cellspacing="0" cellpadding="0" align="CENTER" class="standartTable">
                <tbody>
                <tr>
                    <th>
                        <bean:message key="label.loan.claim.credit.amount" bundle="loanclaimBundle"/>
                    </th>
                    <th>
                        <bean:message key="label.loan.claim.credit.interest.rate" bundle="loanclaimBundle"/>
                    </th>
                </tr>
                <c:forEach var="currCondition" items="${form.hisrotyCurrCoditions}">
                    <tr>
                        <td>
                            <c:if test="${currCondition.minLimitAmount != null}">
                                <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                <c:out value="${phiz:formatAmountISO(currCondition.minLimitAmount)}"/>
                            </c:if>
                            <c:choose>
                                <c:when test="${currCondition.percentUse == false and currCondition.maxLimitAmount != null}">
                                    <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                    <c:out value="${phiz:formatAmountISO(currCondition.maxLimitAmount)}"/>
                                    <c:if test="${currCondition.maxLimitInclude == false}">*<c:set
                                            var="include" value="1"/></c:if>
                                </c:when>
                                <c:when test="${currCondition.percentUse and currCondition.maxLimitPercent != null}">
                                    <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                    <c:out value="${currCondition.maxLimitPercent}"/>
                                    <bean:message key="label.loan.claim.percent.from.cost" bundle="loanclaimBundle"/>
                                    <c:if test="${currCondition.maxLimitInclude == false}">*<c:set
                                            var="include" value="1"/></c:if>
                                </c:when>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${currCondition.minPercentRate != null and currCondition.maxPercentRate != null}">
                                    <c:out value="${currCondition.minPercentRate}-${currCondition.maxPercentRate}"/>
                                    <c:if test="${currCondition.maxLimitInclude == false}">*
                                        <c:set var="include" value="1"/>
                                    </c:if>
                                </c:when>
                                <c:when test="${currCondition.minPercentRate != null and currCondition.maxPercentRate == null}">
                                    <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                    <c:out value="${currCondition.maxLimitPercent}"/>
                                </c:when>
                                <c:when test="${currCondition.minPercentRate == null and currCondition.maxPercentRate != null}">
                                    <<bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                    <c:out value="${currCondition.maxPercentRate}"/>
                                    <c:if test="${condition.maxLimitInclude == false}">*
                                        <c:set var="include" value="1"/>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    -
                                </c:otherwise>
                            </c:choose>
                            (<bean:message key="label.loan.claim.act.from" bundle="loanclaimBundle"/>
                            <c:out value="${phiz:ñalendarToString(currCondition.startDate)}"/>
                            <c:set var="nextDate"  value="${phiz:nextCurrConditionDate(currCondition)}"/>
                            <c:if test="${not empty nextDate}">
                                <bean:message key="label.loan.claim.act.to" bundle="loanclaimBundle"/>
                                <c:out value="${nextDate}"/>
                            </c:if>
                            )
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div style="float:right;">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.close"/>
                    <tiles:put name="commandHelpKey" value="button.close"/>
                    <tiles:put name="bundle" value="loanclaimBundle"/>
                    <tiles:put name="onclick" value="window.close();"/>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>
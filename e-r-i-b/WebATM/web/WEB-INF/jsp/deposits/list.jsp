<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:importAttribute/>
<html:form action="/private/deposits/products/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <depositsList>
                <c:choose>
                    <c:when test="${phiz:isUseCasNsiDictionaries()}">
                        <c:if test="${not empty form.depositProductEntities}">
                            <c:forEach items="${form.depositProductEntities}" var="depositEntity">
                                <deposit>
                                    <title>${depositEntity.groupName}</title>
                                    <depositId>${depositEntity.depositType}</depositId>
                                    <depositType>${depositEntity.depositType}</depositType>
                                    <depositGroup>${depositEntity.groupCode}</depositGroup>
                                    <needInitialFee>${depositEntity.initialFee}</needInitialFee>
                                    <withMinimumBalance>${depositEntity.withMinimumBalance}</withMinimumBalance>
                                    <conditionsList>
                                        <c:forEach items="${depositEntity.depositDescriptions}" var="row" varStatus="rowNum">
                                            <condition>
                                                <minimumBalance>
                                                    <fmt:formatNumber value="${row.sumBegin}" pattern="0.00"/>
                                                </minimumBalance>
                                                <currency>
                                                    <code>${row.currency}</code>
                                                    <name>${phiz:getCurrencySign(row.currency)}</name>
                                                </currency>
                                                <c:set var="minPeriod" value="${phiz:getDepositMinPeriod(depositEntity.periodList)}"/>
                                                <c:if test="${not empty minPeriod}">
                                                    <period>
                                                        <c:set var="maxPeriod" value="${phiz:getDepositMaxPeriod(depositEntity.periodList)}"/>
                                                        <from>${minPeriod}</from>
                                                        <c:if test="${not (minPeriod eq maxPeriod)}">
                                                            <to>${maxPeriod}</to>
                                                        </c:if>
                                                    </period>
                                                </c:if>
                                                <interestRate>
                                                    <from>
                                                        <fmt:formatNumber value="${row.minRate}" pattern="0.00"/>
                                                    </from>
                                                    <c:if test="${row.minRate != row.maxRate}">
                                                        <to>
                                                            <fmt:formatNumber value="${row.maxRate}" pattern="0.00"/>
                                                        </to>
                                                    </c:if>
                                                </interestRate>
                                            </condition>
                                        </c:forEach>
                                    </conditionsList>
                                </deposit>
                            </c:forEach>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                ${form.listHtml}
                    </c:otherwise>
                </c:choose>
            </depositsList>
        </tiles:put>
    </tiles:insert>
</html:form>

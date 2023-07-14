<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/jsp/types/status.jsp"%>

<html:form action="/private/accounts/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="accountLink" value="${form.accountLink}" scope="request"/>
    <c:set var="account" value="${accountLink.account}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <detail>
            <description>${accountLink.description}</description>
            <c:if test="${!empty account.period}">
                <period>${account.period.years}-${account.period.months}-${account.period.days}</period>
            </c:if>
            <tiles:insert definition="atmDateTimeType" flush="false">
                <tiles:put name="name" value="open"/>
                <tiles:put name="calendar" beanName="account" beanProperty="openDate"/>
            </tiles:insert>
            <%--Вклад срочный--%>
            <c:set var="isNotDemand" value="${(not empty account.demand) && (not account.demand)}"/>
            <%--Если вклад срочный и есть дата закрытия--%>
            <c:if test="${isNotDemand && (not empty account.closeDate)}">
                <tiles:insert definition="atmDateTimeType" flush="false">
                    <tiles:put name="name" value="close"/>
                    <tiles:put name="calendar" beanName="account" beanProperty="closeDate"/>
                </tiles:insert>
            </c:if>
            <c:if test="${!empty account.interestRate}">
                <interestRate><bean:write name="account" property="interestRate" format="0.00"/></interestRate>
            </c:if>
            <c:if test="${not empty account.maxSumWrite}">
                <tiles:insert definition="atmMoneyType" flush="false">
                    <tiles:put name="name" value="maxSumWrite" />
                    <tiles:put name="money" beanName="account" beanProperty="maxSumWrite"/>                    
                </tiles:insert>
            </c:if>
            <c:choose>
                <c:when test="${account.passbook}">
                    <passbook>true</passbook>
                </c:when>
                <c:otherwise>
                    <passbook>false</passbook>
                </c:otherwise>
            </c:choose>

            <c:if test="${not empty account.creditCrossAgencyAllowed}">
                <crossAgency>${account.creditCrossAgencyAllowed}</crossAgency>
            </c:if>
            <c:if test="${not empty account.debitCrossAgencyAllowed}">
                <debitCrossAgencyAllowed>${account.debitCrossAgencyAllowed}</debitCrossAgencyAllowed>
            </c:if>
            <c:if test="${not empty account.prolongationAllowed}">
                <prolongation>${account.prolongationAllowed}</prolongation>
            </c:if>

            <c:choose>
                <c:when test="${not empty account.interestTransferCard}">
                    <percentCard>${account.interestTransferCard}</percentCard>
                    <percentTransferSourceTypeField>card</percentTransferSourceTypeField>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${account.interestTransferAccount eq account.number}">
                            <percentTransferSourceTypeField>account</percentTransferSourceTypeField>
                        </c:when>
                        <c:otherwise>
                            <percentAcc>${account.interestTransferAccount}</percentAcc>
                            <percentTransferSourceTypeField>account</percentTransferSourceTypeField>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>

            <c:if test="${not empty account.minimumBalance}">
                <tiles:insert definition="atmMoneyType" flush="false">
                    <tiles:put name="name" value="irreducibleAmt" />
                    <tiles:put name="money" beanName="account" beanProperty="minimumBalance"/>
                </tiles:insert>
            </c:if>
            <c:if test="${not empty account.maxBalance}">
                 <maxBalance>${phiz:formatDecimalToAmountWithCustomSeparatorGrouping(account.maxBalance, 2, ".", false)}</maxBalance>
            </c:if>
            <c:if test="${not empty account.creditAllowed}">
                <creditAllowed>${account.creditAllowed}</creditAllowed>
            </c:if>
            <c:if test="${not empty account.debitAllowed}">
                <debitAllowed>${account.debitAllowed}</debitAllowed>
            </c:if>

            <productValue>
                <accounts>
                    <status>
                        <c:choose>
                            <c:when test="${empty accountLink}">
                                <code>${STATUS_PRODUCT_ERROR}</code>
                                <errors>
                                    <error>
                                        <text>Информация по вкладу из АБС временно недоступна. Повторите операцию позже.</text>
                                    </error>
                                </errors>
                            </c:when>
                            <c:otherwise>
                                <code>${STATUS_OK}</code>
                            </c:otherwise>
                        </c:choose>
                    </status>
                    <c:if test="${not empty accountLink}">
                        <account>
                            <id>${accountLink.id}</id>
                            <code>${accountLink.code}</code>
                            <name><bean:write name="accountLink" property="name"/></name>
                            <c:if test="${not empty accountLink.number}">
                                <number>${accountLink.number}</number>
                            </c:if>
                            <c:if test="${not empty account.balance}">
                                <tiles:insert definition="atmMoneyType" flush="false">
                                    <tiles:put name="name" value="balance" />
                                    <tiles:put name="money" beanName="account" beanProperty="balance"/>
                                </tiles:insert>
                            </c:if>
                            <state>${account.accountState}</state>

                             <c:if test="${not empty form.moneyBoxes}">
                                  <jsp:include page="/WEB-INF/jsp/products/moneyBoxDetailInfo.jsp"/>
                            </c:if>

                        </account>
                    </c:if>
                </accounts>
            </productValue>
            </detail>
        </tiles:put>
    </tiles:insert>
</html:form>

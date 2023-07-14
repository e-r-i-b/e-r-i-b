<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<%@ include file="/WEB-INF/jsp/types/status.jsp"%>

<html:form action="/private/loans/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="loanLink" value="${form.loanLink}"/>
    <c:set var="loan" value="${loanLink.loan}"/>
    <c:set var="loanOffice" value="${loan.office}"/>
    <c:set var="scheduleAbstract" value="${form.scheduleAbstract}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty loanLink}">
                <detail>
                <c:if test="${not empty loan.description}">
                    <description>${loan.description}</description>
                </c:if>
                <c:if test="${not empty loan.rate}">
                    <rate>${loan.rate}</rate>
                </c:if>
                <c:if test="${not empty loan.isAnnuity}">
                    <c:choose>
                    <c:when test="${loan.isAnnuity}">
                        <repaymentMethod>аннуитетный</repaymentMethod>
                    </c:when>
                    <c:otherwise>
                       <repaymentMethod>дифференцированный</repaymentMethod>
                    </c:otherwise>
                    </c:choose>
                </c:if>
                <c:if test="${not empty loan.termStart}">
                    <tiles:insert definition="atmDateTimeType" flush="false">
                        <tiles:put name="name" value="termStart"/>
                        <tiles:put name="calendar" beanName="loan" beanProperty="termStart"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${not empty loan.termDuration}">
                    <termDuration>${loan.termDuration.years}-${loan.termDuration.months}-${loan.termDuration.days}</termDuration>
                </c:if>
                <c:if test="${not empty loan.termEnd}">
                    <tiles:insert definition="atmDateTimeType" flush="false">
                        <tiles:put name="name" value="termEnd"/>
                        <tiles:put name="calendar" beanName="loan" beanProperty="termEnd"/>
                    </tiles:insert>
                </c:if>
                <c:set var="client" value="${loan.borrower}"/>
                <c:if test="${not empty client}">
                    <borrowerFullName><c:out value="${phiz:getFormattedPersonName(client.firstName, client.surName, client.patrName)}"/></borrowerFullName>
                </c:if>
                <c:if test="${not empty loan.agreementNumber}">
                    <agreementNumber>${loan.agreementNumber}</agreementNumber>
                </c:if>
                <c:if test="${not empty loan.accountNumber}">
                    <accountNumber>${loan.accountNumber}</accountNumber>
                </c:if>
                <c:if test="${not empty loanLink.personRole}">
                    <personRole>${loanLink.personRole.description}</personRole>
                </c:if>

                <c:if test="${scheduleAbstract != null}">
                    <c:if test="${not empty scheduleAbstract.remainAmount}">
                        <tiles:insert definition="atmMoneyType" flush="false">
                            <tiles:put name="name" value="remainAmount" />
                            <tiles:put name="money" beanName="scheduleAbstract" beanProperty="remainAmount"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty scheduleAbstract.doneAmount}">
                        <tiles:insert definition="atmMoneyType" flush="false">
                            <tiles:put name="name" value="doneAmount"/>
                            <tiles:put name="money" beanName="scheduleAbstract" beanProperty="doneAmount"/>
                        </tiles:insert>
                    </c:if>
                </c:if>
                <c:if test="${not empty loanOffice.address}">
                    <address>${loanOffice.address}</address>
                </c:if>
                <productValue>
                    <loans>
                        <status>
                            <c:choose>
                                <c:when test="${empty loanLink}">
                                    <code>${STATUS_PRODUCT_ERROR}</code>
                                    <errors>
                                        <error>
                                            <text>Информация по кредиту из АБС временно недоступна. Повторите операцию позже.</text>
                                        </error>
                                    </errors>
                                </c:when>
                                <c:otherwise>
                                    <code>${STATUS_OK}</code>
                                </c:otherwise>
                            </c:choose>
                        </status>
                        <c:if test="${not empty loanLink}">
                            <loan>
                                <id>${loanLink.id}</id>
                                <code>${loanLink.code}</code>
                                <name><c:out value="${loanLink.name}"/></name>
                                <c:if test="${not empty loan.loanAmount}">
                                <tiles:insert definition="atmMoneyType" flush="false">
                                    <tiles:put name="name" value="amount" />
                                    <tiles:put name="money" beanName="loan" beanProperty="loanAmount"/>
                                </tiles:insert>
                                </c:if>
                                <c:if test="${not empty loan.nextPaymentAmount}">
                                    <tiles:insert definition="atmMoneyType" flush="false">
                                        <tiles:put name="name" value="nextPayAmount" />
                                        <tiles:put name="money" beanName="loan" beanProperty="nextPaymentAmount"/>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${loan.state != 'overdue'}">
                                    <tiles:insert definition="atmDateTimeType" flush="false">
                                        <tiles:put name="name" value="nextPayDate"/>
                                        <tiles:put name="calendar" beanName="loan" beanProperty="nextPaymentDate"/>
                                    </tiles:insert>
                                </c:if>
                                <state>${loan.state}</state>
                            </loan>
                        </c:if>
                    </loans>
                </productValue>
                </detail>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>

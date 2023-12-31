<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<%@ include file="/WEB-INF/jsp/common/mobile/types/status.jsp"%>

<html:form action="/private/products/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <c:if test="${form.allCardDown || form.allAccountDown || form.allLoanDown || form.allIMAccountDown}">
            <tiles:put name="status">${STATUS_PRODUCT_ERROR}</tiles:put>
            <tiles:put name="errorDescription">��� �������� ����������. ����� ���������� �� ��������.</tiles:put>
        </c:if>
        <tiles:put name="data">
            <%-- ����� --%>
            <c:if test="${not empty form.cards || form.allCardDown}">
                <cards>
                    <status>
                        <c:choose>
                            <c:when test="${form.allCardDown}">
                                <code>${STATUS_PRODUCT_ERROR}</code>
                                <errors>
                                    <error>
                                        <text>���������� �� ������ �� ��� �������� ����������. ��������� �������� �����.</text>
                                    </error>
                                </errors>
                            </c:when>
                            <c:otherwise>
                                <code>${STATUS_OK}</code>
                            </c:otherwise>
                        </c:choose>
                    </status>
                    <logic:iterate id="listElement" name="form" property="cards" indexId="i">
                        <card>
                            <c:set var="cardLink" value="${listElement}"/>
                            <c:set var="card" value="${cardLink.card}"/>

                            <id>${cardLink.id}</id>
                            <name><c:out value="${phiz:getCardUserName(cardLink)}"/></name>
                            <c:choose>
                                <c:when test="${not empty cardLink.ermbSmsAlias}">
                                    <smsName>${cardLink.ermbSmsAlias}</smsName>
                                </c:when>
                                <c:when test="${not empty cardLink.autoSmsAlias}">
                                    <smsName>${cardLink.autoSmsAlias}</smsName>
                                </c:when>
                            </c:choose>
                            <c:if test="${not empty cardLink.description}">
                                <description>${cardLink.description}</description>
                            </c:if>
                            <c:if test="${not empty cardLink.number}">
                                <number>${phiz:getCutCardNumber(cardLink.number)}</number>
                            </c:if>
                            <isMain>${cardLink.main}</isMain>
                            <type>${card.cardType}</type>
                            <c:if test="${not empty card.availableLimit}">
                                <tiles:insert definition="mobileMoneyType" flush="false">
                                    <tiles:put name="name" value="availableLimit" />
                                    <tiles:put name="money" beanName="card" beanProperty="availableLimit"/>
                                </tiles:insert>
                            </c:if>
                            <state>${card.cardState}</state>
                        </card>
                    </logic:iterate>
                </cards>
            </c:if>

            <%-- ������ --%>
            <c:if test="${not empty form.accounts || form.allAccountDown }">
                <accounts>
                    <status>
                        <c:choose>
                            <c:when test="${form.allAccountDown}">
                                <code>${STATUS_PRODUCT_ERROR}</code>
                                <errors>
                                    <error>
                                        <text>���������� �� ������� �� ��� �������� ����������. ��������� �������� �����.</text>
                                    </error>
                                </errors>
                            </c:when>
                            <c:otherwise>
                                <code>${STATUS_OK}</code>
                            </c:otherwise>
                        </c:choose>
                    </status>
                    <logic:iterate id="listElement" name="ShowAccountsForm" property="accounts" indexId="i">
                        <c:set var="accountLink" value="${listElement}" scope="request"/>
                        <c:set var="account" value="${accountLink.account}"/>
                        <account>
                            <id>${accountLink.id}</id>
                            <name><c:out value="${accountLink.name}"/></name>
                            <c:choose>
                                <c:when test="${not empty accountLink.ermbSmsAlias}">
                                    <smsName>${accountLink.ermbSmsAlias}</smsName>
                                </c:when>
                                <c:when test="${not empty accountLink.autoSmsAlias}">
                                    <smsName>${accountLink.autoSmsAlias}</smsName>
                                </c:when>
                            </c:choose>
                            <c:if test="${not empty accountLink.number}">
                                <number>${accountLink.number}</number>
                            </c:if>
                            <c:if test="${not empty account.balance}">
                                <tiles:insert definition="mobileMoneyType" flush="false">
                                    <tiles:put name="name" value="balance" />
                                    <tiles:put name="money" beanName="account" beanProperty="balance"/>
                                </tiles:insert>
                            </c:if>
                            <state>${accountLink.account.accountState}</state>
                        </account>
                    </logic:iterate>
                </accounts>
            </c:if>

            <%-- ������� --%>
            <c:if test="${not empty form.loans || form.allLoanDown}">
                <loans>
                    <status>
                        <c:choose>
                            <c:when test="${form.allLoanDown}">
                                <code>${STATUS_PRODUCT_ERROR}</code>
                                <errors>
                                    <error>
                                        <text>���������� �� �������� �� ��� �������� ����������. ��������� �������� �����.</text>
                                    </error>
                                </errors>
                            </c:when>
                            <c:otherwise>
                                <code>${STATUS_OK}</code>
                            </c:otherwise>
                        </c:choose>
                    </status>
                    <logic:iterate id="listElement" name="ShowAccountsForm" property="loans" indexId="i">
                        <c:set var="loanLink" value="${listElement}" scope="request"/>
                        <c:set var="loan" value="${loanLink.loan}"/>
                        <loan>
                            <id>${loanLink.id}</id>
                            <name><c:out value="${loanLink.name}"/></name>
                            <c:choose>
                                <c:when test="${not empty loanLink.ermbSmsAlias}">
                                    <smsName>${loanLink.ermbSmsAlias}</smsName>
                                </c:when>
                                <c:when test="${not empty loanLink.autoSmsAlias}">
                                    <smsName>${loanLink.autoSmsAlias}</smsName>
                                </c:when>
                            </c:choose>
                            <c:if test="${not empty loan.loanAmount}">
                                <tiles:insert definition="mobileMoneyType" flush="false">
                                    <tiles:put name="name" value="amount" />
                                    <tiles:put name="money" beanName="loan" beanProperty="loanAmount"/>
                                </tiles:insert>
                            </c:if>
                            <c:if test="${not empty loan.nextPaymentAmount}">
                                <tiles:insert definition="mobileMoneyType" flush="false">
                                    <tiles:put name="name" value="nextPayAmount" />
                                    <tiles:put name="money" beanName="loan" beanProperty="nextPaymentAmount"/>
                                </tiles:insert>
                            </c:if>
                            <c:if test="${loan.state != 'overdue'}">
                                <tiles:insert definition="mobileDateTimeType" flush="false">
                                    <tiles:put name="name" value="nextPayDate"/>
                                    <tiles:put name="calendar" beanName="loan" beanProperty="nextPaymentDate"/>
                                </tiles:insert>
                            </c:if>
                            <state>${loan.state}</state>
                        </loan>
                    </logic:iterate>
                </loans>
            </c:if>

            <%--���--%>
            <c:if test="${not empty form.imAccounts || form.allIMAccountDown}">
                <imaccounts>
                    <status>
                        <c:choose>
                            <c:when test="${form.allIMAccountDown}">
                                <code>${STATUS_PRODUCT_ERROR}</code>
                                <errors>
                                    <error>
                                        <text>���������� �� ������������ ������������� ������ �� ��� �������� ����������. ��������� �������� �����.</text>
                                    </error>
                                </errors>
                            </c:when>
                            <c:otherwise>
                                <code>${STATUS_OK}</code>
                            </c:otherwise>
                        </c:choose>
                    </status>
                    <logic:iterate id="listElement" name="ShowAccountsForm" property="imAccounts" indexId="i">
                        <c:set var="imAccountLink" value="${listElement}"/>
                        <c:set var="imAccount" value="${imAccountLink.imAccount}"/>
                        <ima>
                            <id>${imAccountLink.id}</id>
                            <name><c:out value="${imAccountLink.name}"/></name>
                            <number>${imAccount.number}</number>
                            <tiles:insert definition="mobileDateTimeType" flush="false">
                                <tiles:put name="name" value="openDate"/>
                                <tiles:put name="calendar" beanName="imAccount" beanProperty="openDate"/>
                            </tiles:insert>
                            <c:if test="${not empty imAccount.closingDate}">
                                <tiles:insert definition="mobileDateTimeType" flush="false">
                                    <tiles:put name="name" value="closeDate"/>
                                    <tiles:put name="calendar" beanName="imAccount" beanProperty="closingDate"/>
                                </tiles:insert>
                            </c:if>
                            <c:if test="${not empty imAccount.balance}">
                                <tiles:insert definition="mobileMoneyType" flush="false">
                                    <tiles:put name="name" value="balance"/>
                                    <tiles:put name="money" beanName="imAccount" beanProperty="balance"/>
                                </tiles:insert>
                            </c:if>
                            <currency><c:out value="${imAccount.currency.name} (${phiz:normalizeMetalCode(imAccount.currency.code)})"/></currency>
                            <agreementNumber>${imAccount.agreementNumber}</agreementNumber>
                            <state>${imAccount.state}</state>
                        </ima>
                    </logic:iterate>
                </imaccounts>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>

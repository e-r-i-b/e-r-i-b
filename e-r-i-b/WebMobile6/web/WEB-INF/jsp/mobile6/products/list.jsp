<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/jsp/common/mobile/types/status.jsp"%>

<fmt:setLocale value="en-US"/>

<html:form action="/private/products/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <c:if test="${form.allCardDown || form.allAccountDown || form.allLoanDown || form.allIMAccountDown}">
            <tiles:put name="status">${STATUS_PRODUCT_ERROR}</tiles:put>
            <tiles:put name="errorDescription">АБС временно недоступна. Часть информации не получена.</tiles:put>
        </c:if>
        <tiles:put name="data">
            <%-- Карты --%>
            <c:if test="${not empty form.cards || form.allCardDown}">
                <cards>
                    <status>
                        <c:choose>
                            <c:when test="${form.allCardDown}">
                                <code>${STATUS_PRODUCT_ERROR}</code>
                                <errors>
                                    <error>
                                        <text>Информация по картам из АБС временно недоступна. Повторите операцию позже.</text>
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

            <c:set var="targets" value="${form.targets}"/>
            <c:if test="${not empty form.targets}">
                <targets>
                    <c:forEach var="target" items="${targets}">
                        <c:set var="targetAccountLink" value="${target.accountLink}"/>

                        <target>
                            <type>${target.dictionaryTarget}</type>
                            <name><![CDATA[${target.name}]]></name>

                            <c:set var="comment" value="${target.nameComment}"/>
                            <c:if test="${not empty comment}">
                                <comment><![CDATA[${target.nameComment}]]></comment>
                            </c:if>

                            <tiles:insert definition="mobileDateTimeType" flush="false">
                                <tiles:put name="name"     value="date"/>
                                <tiles:put name="calendar" beanName="target" beanProperty="plannedDate"/>
                                <tiles:put name="pattern"  value="dd.MM.yyyy"/>
                            </tiles:insert>

                            <c:set var="amount" value="${target.amount}"/>
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name"         value="amount"/>
                                <tiles:put name="decimal"      value="${amount.decimal}"/>
                                <tiles:put name="currencyCode" value="${amount.currency.code}"/>
                            </tiles:insert>

                            <c:choose>
                                <c:when test="${not empty targetAccountLink}">
                                    <c:set var="account" value="${targetAccountLink.account}"/>
                                    <c:set var="balance" value="${account.balance}"/>

                                    <c:choose>
                                        <c:when test="${not targetAccountLink.showInMobile}">
                                            <c:set var="status"      value="accountDisabled"/>
                                            <c:set var="description" value="Отображение данного счета для мобильного приложения отключено в настройках видимости продуктов."/>
                                        </c:when>

                                        <c:when test="${account.accountState == 'CLOSED'}">
                                            <c:set var="status"      value="accountClosed"/>
                                            <c:set var="description" value="Вклад для достижения цели закрыт. Вы можете открыть новый вклад или удалить цель."/>
                                        </c:when>

                                        <c:when test="${empty balance}">
                                            <c:set var="status"      value="accountUnavailable"/>
                                            <c:set var="description" value="Информация по вкладам временно недоступна."/>
                                        </c:when>

                                        <c:otherwise>
                                            <c:set var="status"      value="accountEnabled"/>
                                            <c:set var="description" value="Информация о вкладе недоступна. Возможны две причины: задержка получения данных или вклад Вами был закрыт."/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="claim"       value="${phiz:getAccountOpeningClaimById(target.claimId)}"/>
                                    <c:set var="claimState"  value="${claim.state.code}"/>

                                    <c:choose>
                                        <c:when test="${claimState == 'DELAYED_DISPATCH'}">
                                            <c:set var="status"      value="claimDelayed"/>

                                            <c:set var="formattedDate"><fmt:formatDate value="${claim.admissionDate.time}" pattern="dd.MM.yyyy в HH:mm"/></c:set>
                                            <c:set var="description" value="Заявка на открытие вклада отправлена в банк на обработку. Плановая дата исполнения ${formattedDate}"/>
                                        </c:when>

                                        <c:when test="${claimState == 'SAVED'}">
                                            <c:set var="status"      value="claimNotConfirmed"/>
                                            <c:set var="description" value="Для открытия вклада Вам необходимо подтвердить заявку."/>
                                        </c:when>

                                        <c:when test="${claimState == 'EXECUTED'}">
                                            <c:set var="status"      value="claimExecuted"/>
                                            <c:set var="description" value="Информация о вкладе недоступна. Возможны две причины: задержка получения данных или вклад Вами был закрыт."/>
                                        </c:when>

                                        <c:when test="${claimState == 'REFUSED' || claimState == 'DELETED'}">
                                            <c:set var="status"      value="claimRefused"/>
                                            <c:set var="description" value="Для открытия вклада создайте новую заявку. Оформленная ранее заявка отказана."/>
                                        </c:when>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>

                            <status>${status}</status>

                            <c:if test="${status eq 'accountEnabled'}">
                                <account>
                                    <id>${targetAccountLink.id}</id>
                                    <rate><fmt:formatNumber value="${account.interestRate}" pattern="0.00"/></rate>

                                    <tiles:insert definition="mobileMoneyType" flush="false">
                                        <tiles:put name="name"         value="value"/>
                                        <tiles:put name="decimal"      value="${balance.decimal}"/>
                                        <tiles:put name="currencyCode" value="${balance.currency.code}"/>
                                    </tiles:insert>
                                </account>
                            </c:if>
                            <statusDescription>${description}</statusDescription>
                        </target>
                    </c:forEach>
                </targets>
            </c:if>

            <%-- Вклады --%>
            <c:if test="${not empty form.accounts || form.allAccountDown }">
                <accounts>
                    <status>
                        <c:choose>
                            <c:when test="${form.allAccountDown}">
                                <code>${STATUS_PRODUCT_ERROR}</code>
                                <errors>
                                    <error>
                                        <text>Информация по вкладам из АБС временно недоступна. Повторите операцию позже.</text>
                                    </error>
                                </errors>
                            </c:when>
                            <c:otherwise>
                                <code>${STATUS_OK}</code>
                            </c:otherwise>
                        </c:choose>
                    </status>
                    <logic:iterate id="listElement" name="ShowAccountsForm" property="accounts" indexId="i">
                        <c:set var="accountLink" value="${listElement}"/>
                        <c:set var="account" value="${accountLink.account}"/>
                        <account>
                            <id>${accountLink.id}</id>
                            <name><c:out value="${accountLink.name}"/></name>
                            <c:if test="${not empty account.interestRate}">
                                <rate><fmt:formatNumber value="${account.interestRate}" pattern="0.00"/></rate>
                            </c:if>
                            <%--Вклад срочный--%>
                            <c:set var="isNotDemand" value="${(not empty account.demand) && (not account.demand)}"/>
                            <%--Если вклад срочный и есть дата закрытия--%>
                            <c:if test="${isNotDemand and not empty account.closeDate}">
                                <tiles:insert definition="mobileDateType" flush="false">
                                    <tiles:put name="name" value="closeDate"/>
                                    <tiles:put name="calendar" beanName="account" beanProperty="closeDate"/>
                                </tiles:insert>
                            </c:if>
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

            <%-- Кредиты --%>
            <c:if test="${not empty form.loans || form.allLoanDown}">
                <loans>
                    <status>
                        <c:choose>
                            <c:when test="${form.allLoanDown}">
                                <code>${STATUS_PRODUCT_ERROR}</code>
                                <errors>
                                    <error>
                                        <text>Информация по кредитам из АБС временно недоступна. Повторите операцию позже.</text>
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
                            <c:if test="${not empty loan.rate}">
                                <rate><fmt:formatNumber value="${loan.rate}" pattern="0.00"/></rate>
                            </c:if>
                            <c:if test="${not empty loan.balanceAmount}">
                                <tiles:insert definition="mobileMoneyType" flush="false">
                                    <tiles:put name="name" value="balanceAmount"/>
                                    <tiles:put name="money" beanName="loan" beanProperty="balanceAmount"/>
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

            <%--ОМС--%>
            <c:if test="${not empty form.imAccounts || form.allIMAccountDown}">
                <imaccounts>
                    <status>
                        <c:choose>
                            <c:when test="${form.allIMAccountDown}">
                                <code>${STATUS_PRODUCT_ERROR}</code>
                                <errors>
                                    <error>
                                        <text>Информация по обезличенным металлическим счетам из АБС временно недоступна. Повторите операцию позже.</text>
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
                            <number>${imAccountLink.number}</number>
                            <c:if test="${not empty imAccount.openDate}">
                                <tiles:insert definition="mobileDateTimeType" flush="false">
                                    <tiles:put name="name" value="openDate"/>
                                    <tiles:put name="calendar" beanName="imAccount" beanProperty="openDate"/>
                                </tiles:insert>
                            </c:if>
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
                            <c:if test="${not empty imAccount.currency}">
                                <currency><c:out value="${imAccount.currency.name} (${phiz:normalizeMetalCode(imAccount.currency.code)})"/></currency>
                            </c:if>
                            <c:if test="${not empty imAccount.agreementNumber}">
                                <agreementNumber>${imAccount.agreementNumber}</agreementNumber>
                            </c:if>
                            <state>${imAccount.state}</state>
                        </ima>
                    </logic:iterate>
                </imaccounts>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>

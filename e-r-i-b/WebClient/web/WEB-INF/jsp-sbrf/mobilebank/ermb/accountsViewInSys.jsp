<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<div style="margin-top:15px;">
    <div class="interface-items">
        <%--Карты--%>
        <c:if test="${not empty form.cards}">
            <div class="profile-view-products viewProductsWidth">
                <fieldset>
                    <table width="100%">
                        <tr>
                            <th colspan="3"><bean:message bundle="ermbBundle" key="title.cards"/></th>
                        </tr>
                        <logic:iterate id="listItem" name="form" property="cards">
                            <tr>
                                <td class="align-left firstCell" width="600px">
                                    <div class="products-text-style">
                                        <span class="word-wrap"><bean:write name="listItem" property="name"/></span>
                                        <span class="card-number">${phiz:getCutCardNumber(listItem.number)}</span>&nbsp;
                                        <c:set var="spanClass" value="text-green"/>
                                        <c:set var="quantifier" value="card:${listItem.id}"/>
                                        <c:if test="${listItem.card.availableLimit.decimal < 0}">
                                            <c:set var="spanClass" value="text-red"/>
                                        </c:if>
                                        <span class="${spanClass}"><nobr>${phiz:formatAmount(listItem.card.availableLimit)}</nobr></span>
                                    </div>
                                </td>
                                <td class="align-left">
                                    <c:set var="alias" value="${listItem.ermbSmsAlias}"/>
                                    <c:if test="${empty alias}">
                                        <c:set var="alias" value="${listItem.autoSmsAlias}"/>
                                    </c:if>
                                    <img src="${imagePath}/icon_sms.gif">
                                    <c:out value="${alias}"/>
                                </td>
                            </tr>
                        </logic:iterate>
                    </table>
                </fieldset>
            </div>
        </c:if>

        <%--Вклады--%>
        <c:if test="${not empty form.accounts}">
            <div class="profile-view-products viewProductsWidth">
                <fieldset>
                    <table width="100%">
                        <tr>
                            <th colspan="2"><bean:message bundle="ermbBundle" key="title.accounts"/></th>
                        </tr>
                        <logic:iterate id="listItem" name="form" property="accounts">
                            <tr>
                                <td class="align-left firstCell" width="600px">
                                    <div class="products-text-style">
                                        <bean:write name="listItem" property="name"/>&nbsp;
                                        <span class="card-number">${phiz:getFormattedAccountNumber(listItem.account.number)}</span>&nbsp;
                                        <c:set var="spanClass" value="text-green"/>
                                        <c:set var="quantifier" value="account:${listItem.id}"/>
                                        <c:if test="${listItem.account.balance.decimal < 0}">
                                            <c:set var="spanClass" value="text-red"/>
                                        </c:if>
                                        <span class="${spanClass}"><nobr>${phiz:formatAmount(listItem.account.balance)}</nobr></span>
                                    </div>
                                </td>
                                <td class="align-left">
                                    <c:set var="alias" value="${listItem.ermbSmsAlias}"/>
                                    <c:if test="${empty alias}">
                                        <c:set var="alias" value="${listItem.autoSmsAlias}"/>
                                    </c:if>
                                    <img src="${imagePath}/icon_sms.gif">
                                    <c:out value="${alias}"/>
                                </td>
                            </tr>
                        </logic:iterate>
                    </table>
                </fieldset>
            </div>
        </c:if>

        <%--Кредиты--%>
        <c:if test="${not empty form.loans}">
            <div class="profile-view-products viewProductsWidth">
                <fieldset>
                    <table width="100%">
                        <tr>
                            <th colspan="2"><bean:message bundle="ermbBundle" key="title.loans"/></th>
                        </tr>
                        <logic:iterate id="listItem" name="form" property="loans">
                            <tr>
                                <td class="align-left firstCell" width="600px">
                                    <div class="products-text-style">
                                     <span class="word-wrap"><bean:write name="listItem"
                                                                         property="name"/></span>
                                        <span class="card-number">${listItem.number}</span>&nbsp;
                                        <span class="text-green"><nobr>${phiz:formatAmount(listItem.loan.loanAmount)}</nobr></span>
                                    </div>
                                </td>
                                <td class="align-left">
                                    <c:set var="alias" value="${listItem.ermbSmsAlias}"/>
                                    <c:if test="${empty alias}">
                                        <c:set var="alias" value="${listItem.autoSmsAlias}"/>
                                    </c:if>
                                    <img src="${imagePath}/icon_sms.gif">
                                    <c:out value="${alias}"/>
                                </td>
                            </tr>
                        </logic:iterate>
                    </table>
                </fieldset>
            </div>
        </c:if>
    </div>
</div>

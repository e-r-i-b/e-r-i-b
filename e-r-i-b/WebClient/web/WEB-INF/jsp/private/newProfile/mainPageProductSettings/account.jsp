<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<div id="sortableAccounts">
    <div class="sortableHeader">
        <bean:message bundle="userprofileBundle" key="title.accounts"/>
    </div>
    <c:set var="accountsCount" value="${phiz:size(form.accounts)}"/>
    <div id="sortableAccountsShow"  class="connectedSortable">
        <c:set var="countShowAccounts" value="0"/>
        <logic:iterate id="listItem" name="form" property="accounts">
            <c:set var="accountLink" value="${listItem}" scope="request"/>
            <c:if test="${accountLink.showInMain}">
                <c:set var="countShowAccounts" value="${countShowAccounts + 1}"/>
                <c:set var="account" value="${accountLink.account}" scope="request"/>
                <c:set var="accountState" value="${account.accountState}" scope="request"/>

                <div class="sortableProductLinks">
                    <html:hidden property="sortedAccountIds" value="${accountLink.id}"/>
                    <div class="sortIcon opacitySort"></div>
                    <div class="tinyProductImg opacitySort">
                        <c:choose>
                            <c:when test="${accountState == 'OPENED' || accountState == 'LOST_PASSBOOK'}">
                                <img src="${imagePath}/deposits_type/account32.jpg"/>
                            </c:when>
                            <c:otherwise>
                                <img src="${imagePath}/deposits_type/account32Blocked.jpg"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="left opacitySort">
                        <div class="titleBlock">
                            <c:set var="linkName"><c:out value="${accountLink.name}"/></c:set>
                            <span class="linkTitle">${phiz:changeWhiteSpaces(linkName)}</span>
                            <div class="lightness"></div>
                        </div>
                        <c:set var="formattedNumber" value="${phiz:getFormattedAccountNumber(account.number)}"/>
                        <span class="productNumber">${phiz:changeWhiteSpaces(formattedNumber)}</span>
                        <html:multibox name="form"   property="selectedAccountIds"    value="${listItem.id}"       styleId="Account${listItem.id}"  styleClass="hideCheckbox" />
                    </div>
                    <div class="right opacitySort">
                        <span class="sortableProductCurrency">${phiz:getHtmlFormatAmount(account.balance)}</span>
                    </div>
                </div>
            </c:if>
        </logic:iterate>
        <div class="sortableMenuLinksShowDesc" <c:if test="${countShowAccounts > 0}">style="display:none"</c:if>>
            <span>Перетащите сюда, чтобы показать</span>
        </div>
    </div>
    <div class="sortableMenuLinksHide">
            <div class="sortableMenuLinksHideTitle" <c:if test="${countShowAccounts == accountsCount}">style="display:none"</c:if>>СКРЫТЫ НА ГЛАВНОЙ</div>
            <div id="sortableAccountsHide"  class="connectedSortable">
                <logic:iterate id="listItem" name="form" property="accounts">
                    <c:set var="accountLink" value="${listItem}" scope="request"/>
                    <c:if test="${!accountLink.showInMain}">
                        <c:set var="account" value="${accountLink.account}" scope="request"/>
                        <c:set var="accountState" value="${account.accountState}" scope="request"/>
                        <div class="sortableProductLinks">
                            <html:hidden property="sortedAccountIds" value="${accountLink.id}"/>
                            <div class="sortIcon opacitySort"></div>
                            <div class="tinyProductImg opacitySort">
                                <c:choose>
                                    <c:when test="${accountState == 'OPENED' || accountState == 'LOST_PASSBOOK'}">
                                        <img src="${imagePath}/deposits_type/account32.jpg"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${imagePath}/deposits_type/account32Blocked.jpg"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="left opacitySort">
                                <div class="titleBlock">
                                    <c:set var="linkName"><c:out value="${accountLink.name}"/></c:set>
                                    <span class="linkTitle">${phiz:changeWhiteSpaces(linkName)}</span>
                                    <div class="lightness"></div>
                                </div>
                                <c:set var="formattedNumber" value="${phiz:getFormattedAccountNumber(account.number)}"/>
                                <span class="productNumber">${phiz:changeWhiteSpaces(formattedNumber)}</span>
                                <html:multibox name="form"   property="selectedAccountIds"    value="${listItem.id}"       styleId="Account${listItem.id}"  styleClass="hideCheckbox"/>
                            </div>
                            <div class="right opacitySort">
                                <span class="sortableProductCurrency">${phiz:getHtmlFormatAmount(account.balance)}</span>
                            </div>
                        </div>
                    </c:if>
                </logic:iterate>
            </div>
            <div class="sortableMenuLinksHideDesc" <c:if test="${countShowAccounts != accountsCount}">style="display:none"</c:if>>
                Перетащите сюда, чтобы скрыть
            </div>
        </div>
</div>
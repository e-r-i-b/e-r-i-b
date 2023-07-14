<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<!-- Информация по OMC -->
<div id="sortableIMAccounts">
    <div class="sortableHeader">
        <bean:message bundle="userprofileBundle" key="title.ima"/>
    </div>
    <div id="sortableIMAccountsShow" class="connectedSortable">
        <c:set var="IMAccountsCount" value="${phiz:size(form.imAccounts)}"/>
        <c:set var="countShowIMAccounts" value="0"/>
        <logic:iterate id="imAccountLink" name="form" property="imAccounts">
            <c:if test="${imAccountLink.showInMain}">
                <c:set var="countShowIMAccounts"  value="${countShowIMAccounts + 1}"/>
                <div  class="sortableProductLinks">
                    <html:hidden property="sortedIMAccountIds" value="${imAccountLink.id}"/>
                    <div class="sortIcon opacitySort"></div>
                    <div class="tinyProductImg opacitySort">
                        <c:choose>
                            <c:when test="${imAccountLink.state == 'closed' || imAccountLink.state == 'lost_passbook'}">
                                <img src="${imagePath}/ima_type/imaccount32Blocked.jpg"/>
                            </c:when>
                            <c:otherwise>
                                <img src="${imagePath}/ima_type/imaccount32.jpg"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="left opacitySort">
                        <div class="titleBlock">
                            <span class="linkTitle">
                                <c:choose>
                                    <c:when test="${not empty imAccountLink.name}">
                                        <c:set var="linkName"><c:out value="${imAccountLink.name}"/></c:set>
                                        ${phiz:changeWhiteSpaces(linkName)}
                                    </c:when>
                                    <c:when test="${not empty imAccountLink.currency}">
                                        <c:out value="${imAccountLink.currency.name} (${phiz:normalizeCurrencyCode(imAccountLink.currency.code)})"/>
                                    </c:when>
                                </c:choose>
                            </span>
                            <div class="lightness"></div>
                        </div>
                        <span class="productNumber">${phiz:changeWhiteSpaces(imAccountLink.number)}</span>
                        <html:multibox name="form" property="selectedIMAccountIds"   value="${imAccountLink.id}"   styleId="IMAccount${imAccountLink.id}" styleClass="hideCheckbox" />
                    </div>
                    <div class="right opacitySort">
                        <span class="sortableProductCurrency">${phiz:getHtmlFormatAmount(imAccountLink.imAccount.balance)}</span>
                    </div>
                </div>

            </c:if>
        </logic:iterate>
        <div class="sortableMenuLinksShowDesc" <c:if test="${countShowIMAccounts > 0}">style="display:none"</c:if>>
            <span>Перетащите сюда, чтобы показать</span>
        </div>
    </div>
    <div class="sortableMenuLinksHide">
        <div class="sortableMenuLinksHideTitle" <c:if test="${countShowIMAccounts == IMAccountsCount}">style="display:none"</c:if>>СКРЫТЫ НА ГЛАВНОЙ</div>
        <div id="sortableIMAccountsHide"  class="connectedSortable">
            <logic:iterate id="imAccountLink" name="form" property="imAccounts">
                <c:if test="${!imAccountLink.showInMain}">
                    <div class="sortableProductLinks">
                        <html:hidden property="sortedIMAccountIds" value="${imAccountLink.id}"/>
                        <div class="sortIcon opacitySort"></div>
                        <div class="tinyProductImg opacitySort">
                            <c:choose>
                                <c:when test="${imAccountLink.state == 'closed' || imAccountLink.state == 'lost_passbook'}">
                                    <img src="${imagePath}/ima_type/imaccount32Blocked.jpg"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="${imagePath}/ima_type/imaccount32.jpg"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="left opacitySort">
                            <div class="titleBlock">
                                <span class="linkTitle">
                                    <c:choose>
                                        <c:when test="${not empty imAccountLink.name}">
                                            <c:set var="linkName"><c:out value="${imAccountLink.name}"/></c:set>
                                            ${phiz:changeWhiteSpaces(linkName)}
                                        </c:when>
                                        <c:when test="${not empty imAccountLink.currency}">
                                            <c:out value="${imAccountLink.currency.name} (${phiz:normalizeCurrencyCode(imAccountLink.currency.code)})"/>
                                        </c:when>
                                    </c:choose>
                                </span>
                                <div class="lightness"></div>
                            </div>
                            <span class="productNumber">${phiz:changeWhiteSpaces(imAccountLink.number)}</span>
                            <html:multibox name="form" property="selectedIMAccountIds"   value="${imAccountLink.id}"   styleId="IMAccount${imAccountLink.id}" styleClass="hideCheckbox" />
                        </div>
                        <div class="right opacitySort">
                            <span class="sortableProductCurrency">${phiz:getHtmlFormatAmount(imAccountLink.imAccount.balance)}</span>
                        </div>
                    </div>

                </c:if>
            </logic:iterate>
        </div>
        <div class="sortableMenuLinksHideDesc" <c:if test="${countShowIMAccounts != IMAccountsCount}">style="display:none"</c:if> >
            Перетащите сюда, чтобы скрыть
        </div>
    </div>

</div>




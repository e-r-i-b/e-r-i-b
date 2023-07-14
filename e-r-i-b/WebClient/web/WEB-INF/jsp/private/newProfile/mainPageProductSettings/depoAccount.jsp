<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<%--Информация по счетам депо--%>
<div id="sortableDepoAccounts">
    <div class="sortableHeader">
        <bean:message bundle="userprofileBundle" key="title.depoAccounts"/>
    </div>
    <div id="sortableDepoAccountsShow" class="connectedSortable">
        <c:set var="depoAccCount" value="${phiz:size(form.depoAccounts)}" />
        <c:set var="countShowDepoAcc" value="0" />
        <logic:iterate id="depoAccountLink" name="form" property="depoAccounts">
            <c:if test="${depoAccountLink.showInMain}">
                <c:set var="countShowDepoAcc" value="${countShowDepoAcc+1}"/>
                <div class="sortableProductLinks">
                    <html:hidden property="sortedDepoAccountIds" value="${depoAccountLink.id}"/>
                    <div class="sortIcon opacitySort"></div>
                    <div class="tinyProductImg opacitySort">
                        <c:set var="isLock" value="${depoAccountLink.depoAccount.state != 'open'}"/>
                        <c:choose>
                            <c:when test="${isLock}">
                                <img src="${imagePath}/products/icon_depositariy32Blocked.jpg"/>
                            </c:when>
                            <c:otherwise>
                                <img src="${imagePath}/products/icon_depositariy32.jpg"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="left opacitySort">
                        <div class="titleBlock">
                            <c:set var="linkName"><c:out value="${depoAccountLink.name}"/></c:set>
                            <span class="linkTitle">${phiz:changeWhiteSpaces(linkName)}
                            <c:choose>
                                <c:when test="${empty depoAccountLink.name}">
                                    ${phiz:changeWhiteSpaces(depoAccountLink.accountNumber)}
                                </c:when>
                                <c:otherwise>
                                    (${phiz:changeWhiteSpaces(depoAccountLink.accountNumber)})
                                </c:otherwise>
                            </c:choose>
                            </span>
                            <div class="lightness"></div>
                        </div>
                        <span class="productNumber">
                            ${depoAccountLink.depoAccount.agreementNumber}
                        </span>
                        <html:multibox name="form"  property="selectedDepoAccountIds" value="${depoAccountLink.id}" styleId="DepoAccount${depoAccountLink.id}" styleClass="hideCheckbox"/>
                    </div>
                    <div class="right opacitySort">
                    </div>
                </div>
            </c:if>
        </logic:iterate>
        <div class="sortableMenuLinksShowDesc" <c:if test="${countShowDepoAcc > 0}">style="display:none"</c:if>>
            <span>Перетащите сюда, чтобы показать</span>
        </div>
    </div>
    <div class="sortableMenuLinksHide">
        <div class="sortableMenuLinksHideTitle" <c:if test="${countShowDepoAcc == depoAccCount}">style="display:none"</c:if>>СКРЫТЫ НА ГЛАВНОЙ</div>
        <div id="sortableDepoAccountsHide"  class="connectedSortable">
            <logic:iterate id="depoAccountLink" name="form" property="depoAccounts">
                <c:if test="${!depoAccountLink.showInMain}">
                    <div class="sortableProductLinks">
                        <html:hidden property="sortedDepoAccountIds" value="${depoAccountLink.id}"/>
                        <div class="sortIcon opacitySort"></div>
                        <div class="tinyProductImg opacitySort">
                            <c:set var="isLock" value="${depoAccountLink.depoAccount.state != 'open'}"/>
                            <c:choose>
                                <c:when test="${isLock}">
                                    <img src="${imagePath}/products/icon_depositariy32Blocked.jpg"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="${imagePath}/products/icon_depositariy32.jpg"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="left opacitySort">
                            <div class="titleBlock">
                                <c:set var="linkName"><c:out value="${depoAccountLink.name}"/></c:set>
                            <span class="linkTitle">${phiz:changeWhiteSpaces(linkName)}
                            <c:choose>
                                <c:when test="${empty depoAccountLink.name}">
                                    ${phiz:changeWhiteSpaces(depoAccountLink.accountNumber)}
                                </c:when>
                                <c:otherwise>
                                    (${phiz:changeWhiteSpaces(depoAccountLink.accountNumber)})
                                </c:otherwise>
                            </c:choose>
                            </span>
                                <div class="lightness"></div>
                            </div>
                        <span class="productNumber">
                                ${depoAccountLink.depoAccount.agreementNumber}
                        </span>
                            <html:multibox name="form"  property="selectedDepoAccountIds" value="${depoAccountLink.id}" styleId="DepoAccount${depoAccountLink.id}" styleClass="hideCheckbox"/>
                        </div>
                        <div class="right opacitySort"></div>
                    </div>
                </c:if>
            </logic:iterate>
        </div>
        <div class="sortableMenuLinksHideDesc" <c:if test="${countShowDepoAcc != depoAccCount}">style="display:none"</c:if> >
            Перетащите сюда, чтобы скрыть
        </div>
    </div>
</div>

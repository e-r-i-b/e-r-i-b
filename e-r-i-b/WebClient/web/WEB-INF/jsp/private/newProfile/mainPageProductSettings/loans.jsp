<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<%--Информация по кредитам--%>
<c:set var="loansCount" value="${phiz:size(form.loans)}"/>
<div id="sortableLoans">
    <div class="sortableHeader">
        <bean:message bundle="userprofileBundle" key="title.loans"/>
    </div>
    <c:set var="countShowLoans" value="0"/>
    <div id="sortableLoansShow" class="connectedSortable">
            <logic:iterate id="listItem" name="form" property="loans">
                <c:set var="loanLink" value="${listItem}" scope="request"/>
                <c:if test="${loanLink.showInMain}">
                    <c:set var="loan" value="${loanLink.loan}"/>
                    <c:set var="countShowLoans" value="${countShowLoans+1}"/>
                    <div class="sortableProductLinks">
                        <html:hidden property="sortedLoanIds" value="${loanLink.id}"/>
                        <div class="sortIcon opacitySort"></div>
                        <div class="tinyProductImg opacitySort">
                            <c:choose>
                                <c:when test="${loan.isAnnuity}">
                                    <c:if test="${loan.state != 'closed'}">
                                        <img src="${imagePath}/credit_type/icon_creditAnuitet32.jpg"/>
                                    </c:if>
                                    <c:if test="${loan.state == 'closed'}">
                                        <img src="${imagePath}/credit_type/icon_creditAnuitet32_block.jpg"/>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${loan.state != 'closed'}">
                                        <img src="${imagePath}/credit_type/icon_creditDiffer32.jpg"/>
                                    </c:if>
                                    <c:if test="${loan.state == 'closed'}">
                                        <img src="${imagePath}/credit_type/icon_creditDiffer32_block.jpg"/>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="left opacitySort">
                            <div class="titleBlock">
                                <c:set var="linkName"><c:out value="${loanLink.name}"/></c:set>
                                <span class="linkTitle">${phiz:changeWhiteSpaces(linkName)}</span>
                                <div class="lightness"></div>
                            </div>
                            <span class="productNumber">${phiz:changeWhiteSpaces(loanLink.number)}</span>
                            <html:multibox name="form"   property="selectedLoanIds" value="${listItem.id}" styleId="Loan${listItem.id}"  styleClass="hideCheckbox"/>
                        </div>
                        <div class="right opacitySort">
                            <span class="sortableProductCurrency">${phiz:getHtmlFormatAmount(loan.loanAmount)}</span>
                        </div>
                    </div>
                </c:if>
            </logic:iterate>
            <div class="sortableMenuLinksShowDesc" <c:if test="${countShowLoans > 0}">style="display:none"</c:if>>
                <span>Перетащите сюда, чтобы показать</span>
            </div>
        </div>

    <div class="sortableMenuLinksHide">
        <div class="sortableMenuLinksHideTitle" <c:if test="${countShowLoans == loansCount}">style="display:none"</c:if>>СКРЫТЫ НА ГЛАВНОЙ</div>
        <div id="sortableLoansHide"  class="connectedSortable">
            <logic:iterate id="listItem" name="form" property="loans">
                <c:set var="loanLink" value="${listItem}" scope="request"/>
                <c:if test="${!loanLink.showInMain}">
                    <div class="sortableProductLinks">
                        <html:hidden property="sortedLoanIds" value="${loanLink.id}"/>
                        <div class="sortIcon opacitySort"></div>
                        <div class="tinyProductImg opacitySort">
                            <c:choose>
                                <c:when test="${loan.isAnnuity}">
                                    <c:if test="${loan.state != 'closed'}">
                                        <img src="${imagePath}/credit_type/icon_creditAnuitet32.jpg"/>
                                    </c:if>
                                    <c:if test="${loan.state == 'closed'}">
                                        <img src="${imagePath}/credit_type/icon_creditAnuitet32_block.jpg"/>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${loan.state != 'closed'}">
                                        <img src="${imagePath}/credit_type/icon_creditDiffer32.jpg"/>
                                    </c:if>
                                    <c:if test="${loan.state == 'closed'}">
                                        <img src="${imagePath}/credit_type/icon_creditDiffer32_block.jpg"/>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="left opacitySort">
                            <div class="titleBlock">
                                <c:set var="linkName"><c:out value="${loanLink.name}"/></c:set>
                                <span class="linkTitle">${phiz:changeWhiteSpaces(linkName)}</span>
                                <div class="lightness"></div>
                            </div>
                            <span class="productNumber">${phiz:changeWhiteSpaces(loanLink.number)}</span>
                            <html:multibox name="form"   property="selectedLoanIds" value="${listItem.id}" styleId="Loan${listItem.id}"  styleClass="hideCheckbox"/>
                        </div>
                        <div class="right opacitySort">
                            <span class="sortableProductCurrency">${phiz:getHtmlFormatAmount(loan.loanAmount)}</span>
                        </div>
                    </div>
                </c:if>
            </logic:iterate>
        </div>
        <div class="sortableMenuLinksHideDesc" <c:if test="${countShowLoans != loansCount}">style="display:none"</c:if> >
            Перетащите сюда, чтобы скрыть
        </div>
    </div>
</div>

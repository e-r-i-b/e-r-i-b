<%--
  Created by IntelliJ IDEA.
  User: EgorovaA
  Date: 06.11.13
  Time: 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--Информация по кредитам--%>
<c:if test="${loanLink != null}">
    <c:set var="imagePath" value="${globalUrl}/commonSkin/images" scope="request"/>
    <c:set var="loan" value="${loanLink.loan}" scope="request"/>

    <div id="sortable" class="sortableRows">
        <html:hidden property="sortedLoanIds" value="${loanLink.id}"/>
        <div class="tinyProductImg">
            <c:choose>
                <c:when test="${loan.isAnnuity}">
                    <c:if test="${loan.state != 'closed'}">
                        <img src="${imagePath}/credit_type/icon_creditAnuitet32.jpg">
                    </c:if>
                    <c:if test="${loan.state == 'closed'}">
                        <img src="${imagePath}/credit_type/icon_creditAnuitet32_block.jpg">
                    </c:if>
                </c:when>
                <c:otherwise>
                    <c:if test="${loan.state != 'closed'}">
                        <img src="${imagePath}/credit_type/icon_creditDiffer32.jpg">
                    </c:if>
                    <c:if test="${loan.state == 'closed'}">
                        <img src="${imagePath}/credit_type/icon_creditDiffer32_block.jpg">
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="left">
            <div class="titleBlock">
                <c:set var="linkName"><c:out value="${loanLink.name}"/></c:set>
                <span id="productTitle">${phiz:changeWhiteSpaces(linkName)}</span>
                <div class="lightness"></div>
            </div>
            <span class="productNumber">${phiz:changeWhiteSpaces(loanLink.number)}</span>
        </div>
        <div class="right">
            <span class="prodStatus bold">${phiz:formatAmount(loan.loanAmount)}</span>
        </div>
    </div>
</c:if>
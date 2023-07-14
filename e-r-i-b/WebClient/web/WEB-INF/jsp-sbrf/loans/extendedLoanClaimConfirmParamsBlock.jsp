<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<tiles:importAttribute/>

<%--@elvariable id="document" type="com.rssl.phizic.business.documents.payments.ExtendedLoanClaim"--%>
<c:set var="guestWithMobileBank" value="${guest && mobileBankExist}"/>
<div>
    <h2 class="size18">Детали кредита</h2>
    <div class="form-row">
        <div class="paymentLabel">
            <span class="paymentTextLabel">Кредитный продукт</span>
        </div>
        <div class="paymentValue">
            <div class="paymentInputDiv">
                <b>
                    <c:out value="${document.productName}"/>
                </b>
            </div>
        </div>
        <div class="clear"></div>
    </div>
    <div class="form-row">
        <div class="paymentLabel">
            <span class="paymentTextLabel">Сумма кредита</span>
        </div>
        <div class="paymentValue">
            <div class="paymentInputDiv">
                <b>
                    <span class="amount">${phiz:formatAmount(document.loanAmount)}</span>
                </b>
            </div>
        </div>
        <div class="clear"></div>
    </div>
    <div class="form-row">
        <div class="paymentLabel">
            <span class="paymentTextLabel">На срок</span>
        </div>
        <div class="paymentValue">
            <div class="paymentInputDiv">
                <b>
                    <span class="amount"><c:out value="${document.loanPeriod}"/></span>&nbsp;мес.
                </b>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</div>
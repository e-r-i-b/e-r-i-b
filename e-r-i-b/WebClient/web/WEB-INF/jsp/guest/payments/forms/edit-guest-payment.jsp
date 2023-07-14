<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags"                        prefix="phiz" %>

<html:form action="/guest/payments/payment" onsubmit="return setEmptyAction()">
    <c:set var="form"     value="${phiz:currentForm(pageContext)}"/>
    <c:set var="metadata" value="${form.metadata}"/>
    <c:set var="isLoanCardClaim" value="${metadata.form.name == 'LoanCardClaim'}"/>
    <c:set var="isLoanCardClaimAvailable" value="${phiz:isLoanCardClaimAvailable(false)}"/>
    <c:choose>
        <c:when test="${(isLoanCardClaim and isLoanCardClaimAvailable) or !isLoanCardClaim}">
            <tiles:insert definition="guestPage">
            <tiles:put name="mainMenuType" value="guestMainMenu"/>
            <tiles:put name="mainmenu"     value="Index"/>

            <tiles:put name="data" type="string">
                <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="guest"       value="${true}"/>
                <tiles:put name="id"          value="${metadata.form.name}"/>
                <tiles:put name="name"        value="${metadata.form.description}"/>
                <tiles:put name="description" value="${metadata.form.detailedDescription}"/>
                <c:if test="${isLoanCardClaim}">
                    <tiles:put name="showHeader" value="false"/>
                    <tiles:insert page="/WEB-INF/jsp-sbrf/payments/loanCardClaimHeader.jsp" flush="false">
                        <tiles:put name="view" value="false"/>
                        <tiles:put name="showTitle" value="true"/>
                    </tiles:insert>
                </c:if>
                    <tiles:put name="data" type="string">
                        <span onkeypress="onEnterKey(event);">
                            <c:set var="stateCode" value="${form.document.state.code}"/>
                            <c:if test="${stateCode eq 'INITIAL7'}">
                                <tiles:insert page="/WEB-INF/jsp-sbrf/loans/departmentSelection.jsp" flush="false"/>
                            </c:if>
                            ${form.html}
                        </span>
                    </tiles:put>

                    <tiles:put name="buttons">
                        <tiles:insert page="/WEB-INF/jsp-sbrf/payments/edit-payment-data-buttons.jsp" flush="false">
                            <tiles:put name="guest" value="true"/>
                            <tiles:put name="registeredGuest" value="true"/>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <tiles:insert page="/WEB-INF/jsp/guest/loanClaimUnavailablePage.jsp" flush="false"/>
        </c:otherwise>
    </c:choose>
</html:form>
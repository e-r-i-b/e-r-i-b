<%--
  User: Moshenko
  Date: 30.05.2011
  Time: 10:01:49
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/private/loan/loanoffer/show">

    <tiles:importAttribute/>
    <tiles:insert definition="accountInfo">
        <tiles:put name="mainmenu" value="Info"/>
        <tiles:put name="menu" type="string"/>
        <tiles:put name="data" type="string">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <c:set var="loanOffers" value="form.loanOffers"/>
            <c:set var="offersCount" value="${fn:length(form.loanOffers)-1}"/>
            <c:set var="loanCardOffers" value="form.loanCardOffers"/>
            <c:set var="cardOffersCount" value="${fn:length(form.loanCardOffers)-1}"/>
            <c:if test="${not empty form.loanOffers or not empty form.loanCardOffers}">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title"><div class="firstTitlePosition">Предложения банка</div></tiles:put>
                    <tiles:put name="data">
                        <%--Предложения на кредит--%>
                        <c:if test="${phiz:isExtendedLoanClaimAvailable() and phiz:isPreapprovedLoanClaimAvailable() and not empty form.loanOffers}">
                            <logic:iterate id="listElement" name="form" property="loanOffers" indexId="i">
                                <c:set var="loanOffer" value="${listElement}" scope="request"/>
                                <tiles:insert page="../loans/loanOfferTemplate.jsp" flush="false"/>
                                <c:if test="${offersCount != i or (offersCount == i and cardOffersCount+1 != 0)}">
                                    <div class="productDivider"></div>
                                </c:if>
                            </logic:iterate>
                        </c:if>
                        <%--Предложения на кредитную карту--%>
                        <c:if test="${not empty form.loanCardOffers}">
                            <logic:iterate id="listElement" name="form" property="loanCardOffers" indexId="i">
                                <c:set var="loanOffer" value="${listElement}" scope="request"/>
                                <c:set var="type" value="${loanOffer.offerType.value}" scope="request"/>
                                <c:set var="isCard" value="true" scope="request"/>
                                <c:choose>
                                    <c:when test="${type == '2' && phiz:impliesService('GetPreapprovedOffersService')}">
                                        <c:if test="${phiz:impliesService('ChangeCreditLimitClaim')}">
                                            <tiles:insert page="../loans/loanCardOfferBlock.jsp" flush="false"/>
                                        </c:if>
                                    </c:when>
                                    <c:when test="${type == '1' && phiz:isLoanCardClaimAvailable(false) || type != '1'}">
                                        <tiles:insert page="../loans/loanOfferTemplate.jsp" flush="false"/>
                                    </c:when>
                                </c:choose>
                                <c:if test="${cardOffersCount!= i}">
                                    <div class="productDivider"></div>
                                </c:if>
                            </logic:iterate>
                        </c:if>

                    </tiles:put>
                </tiles:insert>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
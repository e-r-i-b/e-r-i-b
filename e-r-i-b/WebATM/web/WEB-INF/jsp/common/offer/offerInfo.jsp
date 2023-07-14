<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="en-US"/>

<html:form action="/private/loan/loanOffer/show">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="loanOffers" value="${form.loanOffers}"/>
    <c:set var="loanOffersCount" value="${fn:length(form.loanOffers)}"/>

    <c:set var="loanCardOffers" value="${form.loanCardOffers}"/>
    <c:set var="loanCardOffersCount" value="${fn:length(form.loanCardOffers)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <c:if test="${loanOffersCount > 0 || loanCardOffersCount > 0}">
                <offerInfoData>
                    <c:if test="${loanOffersCount > 0}">
                    <loanOffers>
                        <logic:iterate id="listElement" name="form" property="loanOffers" indexId="i">
                            <c:set var="loanOffer" value="${listElement}" scope="request"/>
                            <tiles:insert page="loanOfferTemplate.jsp" flush="false"/>
                        </logic:iterate>
                    </loanOffers>
                    </c:if>
                    <c:if test="${loanCardOffersCount > 0}">
                     <loanCardOffers>
                         <logic:iterate id="listElement" name="form" property="loanCardOffers" indexId="i">
                            <c:set var="loanCardOffer" value="${listElement}" scope="request"/>
                            <tiles:insert page="loanCardOfferTemplate.jsp" flush="false"/>
                        </logic:iterate>
                     </loanCardOffers>
                    </c:if>
                </offerInfoData>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>

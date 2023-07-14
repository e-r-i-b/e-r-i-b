<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="document" value="${form.document}"/>

<tiles:insert page="/WEB-INF/jsp-sbrf/loans/shortLoanClaimHeaderBlock.jsp" flush="false">
    <tiles:put name="id" value="${document.id}"/>
    <tiles:put name="loanOfferId" value="${document.loanOfferId}"/>
    <tiles:put name="condId" value="${document.conditionId}"/>
    <tiles:put name="condCurrId" value="${document.conditionCurrencyId}"/>
    <tiles:put name="loanPeriod" value="${document.loanPeriod}"/>
    <tiles:put name="loanAmount" beanName="document" beanProperty="loanAmount"/>
    <tiles:put name="onlyShortClaim" value="${form.document.onlyShortClaim}"/>
</tiles:insert>
<tiles:insert page="/WEB-INF/jsp/common/loan/loanClaimParametersBlock.jsp" flush="false">
    <tiles:put name="document" beanName="form" beanProperty="document"/>
    <tiles:put name="description" value=""/>
</tiles:insert>
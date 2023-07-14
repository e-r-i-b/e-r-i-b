<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="form"      value="${phiz:currentForm(pageContext)}"/>
<c:set var="stateCode" value="${form.document.state.code}"/>
<c:set var="hasQuestionnaire" value="${form.document.questionCount != '0'}"/>

<c:if test="${!(stateCode eq 'INITIAL')}">
    <tiles:insert page="/WEB-INF/jsp-sbrf/loans/extendedLoanClaimHeaderBlock.jsp" flush="false">
        <tiles:put name="documentId" value="${form.document.id}"/>
    </tiles:insert>

    <tiles:insert page="/WEB-INF/jsp/common/loan/loanClaimParametersBlock.jsp" flush="false">
        <tiles:put name="document" beanName="form" beanProperty="document"/>
        <tiles:put name="description" value=""/>
    </tiles:insert>
</c:if>
<c:if test="${!form.document.inWaitTM || phiz:isAdminApplication()}">
    <tiles:insert page="/WEB-INF/jsp-sbrf/loans/extendedLoanClaimStripeBlock.jsp" flush="false">
        <tiles:put name="extendedLoanClaimState" value="${stateCode}"/>
        <tiles:put name="hasQuestionnaire" value="${hasQuestionnaire}"/>
    </tiles:insert>
</c:if>
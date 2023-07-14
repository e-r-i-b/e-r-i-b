<%@page contentType="text/html;charset=windows-1251" language="java"%>

<%@taglib uri="http://rssl.com/tags"                        prefix="phiz"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions"      prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>

<tiles:importAttribute/>

<script type="text/javascript">
    doOnLoad(function()
    {
        $("#openDetailClick").click(function() {
            var description = document.getElementById("additionalOfferDescription");
            hideOrShow(description, null);
            return false;
        });
    });
</script>

<div class="${floatClass}">
    <c:set var="loanTerms" value="${phiz:getLoanClaimTerms(conditionId)}" scope="page"/>

    <c:if test="${not empty loanTerms}">
        <c:set var="loanTermsSize" value="${fn:length(loanTerms)}"/>
        <div id="additionalOfferDescription" style="display: none;">
            <ul class="terms">
                <c:if test="${loanTermsSize > 0}">
                    <c:forEach varStatus="current" begin="0" end="${loanTermsSize-1}">
                        <li>
                            &mdash;&nbsp;<c:out value="${loanTerms[current.index]}"/>
                        </li>
                    </c:forEach>
                </c:if>
            </ul>
        </div>
    </c:if>
</div>
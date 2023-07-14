<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<tiles:importAttribute/>
<div id="offerBlock"></div>
<script type="text/javascript">
    function addOffer(data)
    {
        if (trim(data) != '')
            $("#offerBlock").append(data);
    }

    function getOffers()
    {
        var url = "${phiz:calculateActionURL(pageContext,'/private/async/offer')}";
        var params ="";
        ajaxQuery(params, url,  function(data) {addOffer(data);}, null, false);
    }

    doOnLoad(function()
    {
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <c:choose>
            <c:when test="${form.crmOffersEnabled}">
                 setTimeout(function ()
                {
                    getOffers();
                }, ${form.waitingTime});
            </c:when>
            <c:otherwise>
                getOffers();
            </c:otherwise>
        </c:choose>
    });
</script>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<html:form action="/private/payments/accountOpeningClaim/confirm" onsubmit="return setEmptyAction(event)">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

   <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="confirmAccountOpeningClaimWindow"/>
        <tiles:put name="data">
            <span class="title"><h1>ѕодтверждение открыти€ нового счета</h1> </span>
            <input type="hidden" name="transactionToken" value="${sessionScope['org.apache.struts.action.TOKEN']}"/>
            <input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="${sessionScope['org.apache.struts.action.TOKEN']}"/>
            <input type="hidden" id="accountOpeningClaimId" value="${frm.id}"/>
            <div id="paymentFormAccountOpeningClaim"></div>
            <div id="accountOpeningConfirmButtons" class="strategy">
                <tiles:insert page="/WEB-INF/jsp-sbrf/payments/confirm-payment-buttons.jsp" flush="false">
                    <tiles:put name="fromExtendedLoanClaim" value="${true}"/>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>

    <script type="text/javascript">
        $(document).ready(function()
        {
            $.getScript("${initParam.resourcesRealPath}/scripts/layout.js");

            win.init(document);
            $('#paymentFormAccountOpeningClaim').html('${phiz:escapeForJS(frm.html, true)}');
            win.open('confirmAccountOpeningClaimWindow');

            $(".chooseConfirmStrategy .clientButton").click(function () {
                win.close('confirmAccountOpeningClaimWindow');
            });

            $("#confirmAccountOpeningClaimWindowWin .closeImg").click(function() {
                var paymentInputs = $(":input");
                for (var index = 0; index < paymentInputs.length; index++)
                {
                    paymentInputs[index].disabled = false;
                }
            });
        });
    </script>
</html:form>
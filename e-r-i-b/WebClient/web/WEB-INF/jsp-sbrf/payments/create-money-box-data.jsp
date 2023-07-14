<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<c:set var="form" value="${EditPaymentForm}"/>
<c:set var="url">
    <c:choose>
        <c:when test="${form.type == 'create'}">
            <c:out value="/private/async/moneybox/claim/create"/>
        </c:when>
        <c:when test="${form.type == 'edit'}">
            <c:out value="/private/async/moneybox/edit"/>
        </c:when>
        <c:when test="${form.type == 'editDraftClaim'}">
            <c:out value="/private/async/moneybox/claim/edit"/>
        </c:when>
    </c:choose>
</c:set>

<html:form styleId="editMoneyBoxFormId" action="/private/async/moneybox/claim/create" onsubmit="return setEmptyAction();" show="true">
    <div id="paymentForm">
        <c:set var="winId" value="moneyBoxWinDiv"/>
        ${form.html}
        <div class="buttonsArea iebuttons">
            <c:choose>
                <c:when test="${form.type == 'create'}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.connect"/>
                        <tiles:put name="commandHelpKey" value="button.connect.help"/>
                        <tiles:put name="bundle" value="moneyboxBundle"/>
                        <tiles:put name="onclick">save();</tiles:put>
                    </tiles:insert>
                </c:when>
                <c:when test="${form.type == 'edit'}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="moneyboxBundle"/>
                        <tiles:put name="onclick">confirm();</tiles:put>
                    </tiles:insert>
                </c:when>
                <c:when test="${form.type == 'editDraftClaim'}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="moneyboxBundle"/>
                        <tiles:put name="onclick">saveDraftClaim();</tiles:put>
                    </tiles:insert>
                </c:when>
            </c:choose>
        </div>
        <div class="clear"></div>
    </div>
    <script type="text/javascript">
        doOnLoad ( function(){
        var el = ensureElement("${winId}");
        selectCore.init();
        if(el != null && !el.isApplyMask)
        {
            initDatePicker(el);
            el.isApplyMask = true;
        }
        });
    </script>
</html:form>
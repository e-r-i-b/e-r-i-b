<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags"  prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="ru-RU"/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="profileId" value="${form.id}"/>
<c:set var="portfolioType" value="${form.portfolio.type}"/>
<c:set var="portfolioId" value="${form.portfolio.id}"/>

<div class="confirmWindowTitle">
    <h2>
        <bean:message bundle="pfpBundle" key="changeStartAmountWindow.${portfolioType}.title"/>
    </h2>
</div>

<c:set var="errorMessageEmpty"><bean:message bundle="pfpBundle" key="changeStartAmountWindow.${portfolioType}.button.change.validation.empty"/></c:set>
<c:set var="errorMessageBad"><bean:message bundle="pfpBundle" key="changeStartAmountWindow.${portfolioType}.button.change.validation.bad"/></c:set>
<div class="warningMessage" id="warningMessages" style="display:none;">
    <tiles:insert definition="roundBorderLight" flush="false">
        <tiles:put name="color" value="red"/>
        <tiles:put name="data">
            <div id="messageContainer" class="messageContainer"></div>
            <div class="clear"></div>
        </tiles:put>
    </tiles:insert>
</div>

<div class="confirmWindowMessage">
    <bean:message bundle="pfpBundle" key="changeStartAmountWindow.${portfolioType}.message"/>
</div>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="changeStartAmountWindow.${portfolioType}.fieldName"/></tiles:put>
    <tiles:put name="data">
        <html:text property="field(changedStartAmount)" styleClass="moneyField" maxlength="13"/>
        <b>&nbsp;<bean:message bundle="pfpBundle" key="changeStartAmountWindow.${portfolioType}.fieldUnit"/></b>
    </tiles:put>
    <tiles:put name="description" value=""/>
    <tiles:put name="fieldName">changedStartAmount</tiles:put>
</tiles:insert>

<c:set var="changeUrl" value="${phiz:calculateActionURL(pageContext,'/async/changeStartAmount')}"/>
<script type="text/javascript">
    var startAmountField = $('[name=field(changedStartAmount)]');
    var errorMessageDiv = $('#warningMessages');

    function openChangeStartAmountWindow()
    {
        errorMessageDiv.hide();
        win.open('editStartAmountWindow');
    }

    function changeStartAmount()
    {
        errorMessageDiv.hide();

        var sum = getStringWithoutSpace(startAmountField.val());

        if (sum == null || sum.length <= 0)
        {
            addErrorMessage('${errorMessageEmpty}');
            return;
        }

        <%-- Количество нулей после запятой --%>
        var zeroCount = (sum.search(/\.|\,/)+3)-(sum.length);
        if(zeroCount > 0)
        {
            sum = sum + (zeroCount == 1 ? '0' : '00');
            startAmountField.val(startAmountField.val() + (zeroCount == 1 ? '0' : '00'));
        }

        if (!matchRegexp(sum, /^\d*((\.|\,)\d{2})?$/))
        {
            addErrorMessage('${errorMessageBad}');
            return;
        }

        var params = 'operation=changeStartAmount';
        params += '&profileId=' + ${profileId};
        params += '&portfolioId=' + ${portfolioId};
        params += '&field(changedStartAmount)=' + startAmountField.val();
        ajaxQuery(params,'${changeUrl}',function(data){addChangeStartAmountResult(data);});
    }

    function addErrorMessage(mess)
    {
        errorMessageDiv.show();
        $('#messageContainer').html(mess);
    }
</script>

<div class="tableArea pfpButtonsBlock">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="changeStartAmountWindow.button.cancel"/>
        <tiles:put name="commandHelpKey" value="changeStartAmountWindow.button.cancel.help"/>
        <tiles:put name="bundle"         value="pfpBundle"/>
        <tiles:put name="onclick"        value="win.close('editStartAmountWindow');"/>
        <tiles:put name="viewType"       value="buttonGrey"/>
    </tiles:insert>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="changeStartAmountWindow.button.change"/>
        <tiles:put name="commandHelpKey" value="changeStartAmountWindow.button.change.help"/>
        <tiles:put name="bundle"         value="pfpBundle"/>
        <tiles:put name="onclick"        value="changeStartAmount();"/>
        <tiles:put name="isDefault"        value="true"/>
    </tiles:insert>
</div>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
    function setExactAmount(text)
    {
        var exactAmount = document.getElementById('exactAmount');
        if (exactAmount != null)
            exactAmount.value = text;
    }
</script>

<c:set var="initialData" value="${form.response.initialData.internalPayment}"/>
<tiles:insert page="fields-table.jsp" flush="false">
    <tiles:put name="data">
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="initialData" beanProperty="fromResource"/>
        </tiles:insert>
        <tiles:insert page="field.jsp" flush="false">
            <tiles:put name="field" beanName="initialData" beanProperty="toResource"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
<tr><td>buyAmount</td><td><html:text onfocus="setExactAmount('destination-field-exact')" property="buyAmount" value="${initialData.buyAmount.moneyType.value}"/></td></tr>
<tr><td>sellAmount</td><td><html:text onfocus="setExactAmount('charge-off-field-exact')"property="sellAmount" value="${initialData.sellAmount.moneyType.value}"/></td></tr>
<tr><td>exactAmount</td><td><html:text styleId="exactAmount" property="exactAmount" readonly="true" value="${initialData.exactAmount.stringType.value}"/></td></tr>
<tr><td>operationCode</td><td><html:text styleId="operationCode" property="operationCode" value="${initialData.operationCode.stringType.value}"/></td></tr>
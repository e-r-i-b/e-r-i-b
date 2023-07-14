<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://rssl.com/tags"                        prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>

<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
<script type="text/javascript">
    $(document).ready(function()
    {
        var sumType = document.getElementsByName("longOfferSumType")[0];
        changeSumType(sumType);

        setSumModifyOnKeyDown();
    });

    // изменение типа суммы автоплатежа
    function changeSumType(item)
    {
        clearFields();

        var sumType = item.options[item.selectedIndex == undefined ? 0 : item.selectedIndex].value;
        $('.'+sumType).show();
        $('.'+sumType+' > td > input').removeAttr('disabled');
    }

    function clearFields()
    {
        $('.floatField').hide();
        $('.floatField > td > input').attr('disabled', 'disabled');
    }

    //установить событие: при вводе символов в поля с суммами взводить флаг isSumModify
    function setSumModifyOnKeyDown()
    {
        $('.changedBySumType > td > input').keydown(function() {
            $('input[name=isSumModify]').val('true');
        });
    }
</script>

<html:html>
    <head>
        <title>Создание автоплатежа погашения кредита</title>
    </head>

    <body>
    <h1>Подключение автоплатежа</h1>

    <html:form action="/atm/loanPaymentLongOffer" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address" value="/private/payments/payment.do"/>
            <tiles:put name="operation" value="save"/>

            <tiles:put name="data">
                <c:set var="initialData" value="${form.response.initialData.loanPaymentLongOffer}"/>
                <tiles:insert page="fields-table.jsp" flush="false">
                    <tiles:put name="data">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="initialData" beanProperty="loan"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="initialData" beanProperty="fromResource"/>
                        </tiles:insert>
                        <c:set var="longOfferDetails" value="${initialData.longOfferDetails}"/>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="longOfferDetails" beanProperty="startDate"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="longOfferDetails" beanProperty="endDate"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="longOfferDetails" beanProperty="eventType"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="longOfferDetails" beanProperty="payDay"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="longOfferDetails" beanProperty="firstPaymentDate"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="longOfferDetails" beanProperty="priority"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="longOfferDetails" beanProperty="isSumModify"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="longOfferDetails" beanProperty="sumType"/>
                            <tiles:put name="onChange" value="changeSumType(this);"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="longOfferDetails" beanProperty="fixedSumma.amount"/>
                            <tiles:put name="tdClass" value="floatField FIXED_SUMMA"/>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
    </html:form>

    </body>
</html:html>
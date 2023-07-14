<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/departments/currencyRates/print" onsubmit="return setEmptyAction(event);">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="currencyRates" value="${frm.rates}"/>
    <div style="padding: 0 10px 0 0">
    <tiles:insert definition="print">
        <%-- данные --%>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                doOnLoad(function()
                {
                    window.print();
                });
            </script>
            <%-- Последовательность тарифных планов на форме--%>
            <c:set var="tarifPlanCodeRatesOrder">0,1,3,2</c:set>
            <h4 class="ratesPrintTitle" style="">Курсы покупки/продажи иностранных валют</h4>
            <table class="borderTable ratesTable tblHeaderBig" cellpadding="0" cellspacing="0">
                <thead>
                    <th rowspan="2" width="170px">Валюта</th>
                    <th colspan="2">Тариф не задан</th>
                    <th colspan="2">Тариф «Премьер»</th>
                    <th colspan="2">Тариф «VIP»</th>
                    <th colspan="2">Тариф «Массовый/Золотой»</th>

                    <c:set var="buySale"><th>Покупка</th><th>Продажа</th></c:set>
                    <tr class="tblInfHeader">
                        ${buySale}${buySale}${buySale}${buySale}
                    </tr>
                </thead>
                <%@ include file="/WEB-INF/jsp/currency/showCurrencyRateItem.jsp"%>
            </table>
        </tiles:put>
    </tiles:insert>
    </div>
</html:form>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/departments/rates/print" onsubmit="return setEmptyAction(event);">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>


    <tiles:insert definition="print">
        <%-- данные --%>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                doOnLoad(function()
                {
                    window.print();
                });
            </script>
            <c:set var="rates" value="${frm.rates}"/>
            <%-- Последовательность кодов металов --%>
            <c:set var="ratesCodesOrder">A98,A99,A76,A33</c:set>
            <h4 class="ratesPrintTitle">Курсы покупки и продажи драгоценных металлов</h4>
            <table class="borderTable ratesTable" cellpadding="0" cellspacing="0">
                <thead>
                    <th width="140">&nbsp;</th>
                    <th>AUR</th>
                    <th>ARG</th>
                    <th>PTR</th>
                    <th>PDR</th>
                </thead>
                <tr><td colspan="5">
                    <div class="rateTarifPlan">&nbsp;Для клиентов с незаданным тарифным планом</div>
                </td></tr>
                <c:set var="ratesTarifPlan" value="0"/>
                <%@ include file="/WEB-INF/jsp/ima/showRateItem.jsp"%>

                <tr><td colspan="5">
                    <div class="rateTarifPlan">&nbsp;Для клиентов с тарифным планом «Премьер»</div>
                </td></tr>
                <c:set var="ratesTarifPlan" value="1"/>
                <%@ include file="/WEB-INF/jsp/ima/showRateItem.jsp"%>

                <tr><td colspan="5">
                    <div class="rateTarifPlan">&nbsp;Для клиентов с тарифным планом «VIP»</div>
                </td></tr>
                <c:set var="ratesTarifPlan" value="3"/>
                <%@ include file="/WEB-INF/jsp/ima/showRateItem.jsp"%>

                <tr><td colspan="5">
                    <div class="rateTarifPlan">&nbsp;Для клиентов с тарифным планом «Массовый/Золотой»</div>
                </td></tr>
                <c:set var="ratesTarifPlan" value="2"/>
                <%@ include file="/WEB-INF/jsp/ima/showRateItem.jsp"%>
            </table>
        </tiles:put>
    </tiles:insert>
</html:form>
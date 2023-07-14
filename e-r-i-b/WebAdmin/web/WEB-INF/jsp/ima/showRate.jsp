<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/departments/rates/show" onsubmit="return setEmptyAction(event);">
<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="departmentsEdit">
	<tiles:put name="submenu" type="string" value="ShowIMAccountRate"/>
    <tiles:put name="pageTitle"   type="string" value="Курсы покупки/продажи металлов"/>
	<tiles:put name="menu" type="string">

		<tiles:insert definition="clientButton" flush="false" operation="PrintIMAccountRateOperation">
			<tiles:put name="commandTextKey" value="button.rate.print"/>
			<tiles:put name="commandHelpKey" value="button.rate.print.help"/>
			<tiles:put name="bundle"         value="departmentsBundle"/>
            <tiles:put name="onclick">doPrint();</tiles:put>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>

	</tiles:put>

    <tiles:put name="filter" type="string">
        <c:set var="colCount" value="2" scope="request"/>
        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label" value="label.rateActualDate"/>
            <tiles:put name="labelTextAlign" value="right"/>
            <tiles:put name="bundle" value="departmentsBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="data">
                <span style="white-space:nowrap;">
                    <span style="font-weight:normal;overflow:visible;cursor:default;">
                        <input type="text"
                                size="10" name="filter(rateActualDate)" class="dot-date-pick"
                                value="<bean:write name="frm" property="filters.rateActualDate" format="dd.MM.yyyy"/>"/>
                        <input type="text"
                                size="8" name="filter(rateActualTime)" class="time-template"
                                value="<bean:write name="frm" property="filters.rateActualTime" format="HH:mm:ss"/>"/>
                    </span>
                </span>
            </tiles:put>
        </tiles:insert>
    </tiles:put>

	<tiles:put name="data" type="string">

    <script type="text/javascript">
        function doPrint()
        {
            <c:set var="params" value="?id=${frm.id}&rateActualDate=${frm.rateActualDate}&rateActualTime=${frm.rateActualTime}"/>
            <c:set var="printUrl">${phiz:calculateActionURL(pageContext,'/departments/rates/print.do')}${params}</c:set>
            window.open("${printUrl}", "", "width=900,height=500,resizable=0,menubar=1,toolbar=0,scrollbars=1");
        }
    </script>

    <c:set var="rates" value="${frm.rates}"/>
    <%-- Последовательность кодов металов --%>
    <c:set var="ratesCodesOrder">A98,A99,A76,A33</c:set>
    <tiles:insert definition="tableTemplate" flush="false">
        <tiles:put name="head">
            <th>&nbsp;</th>
            <th>AUR</th>
            <th>ARG</th>
            <th>PTR</th>
            <th>PDR</th>
        </tiles:put>
        <tiles:put name="data">
            <tr><td colspan="5">
                <div class="bold">&nbsp;Для клиентов с незаданным тарифным планом</div>
            </td></tr>
            <c:set var="ratesTarifPlan" value="0"/>
            <%@ include file="/WEB-INF/jsp/ima/showRateItem.jsp"%>

            <tr><td colspan="5">
                <div class="bold">&nbsp;Для клиентов с тарифным планом «Премьер»</div>
            </td></tr>
            <c:set var="ratesTarifPlan" value="1"/>
            <%@ include file="/WEB-INF/jsp/ima/showRateItem.jsp"%>

            <tr><td colspan="5">
                <div class="bold">&nbsp;Для клиентов с тарифным планом «VIP»</div>
            </td></tr>
            <c:set var="ratesTarifPlan" value="3"/>
            <%@ include file="/WEB-INF/jsp/ima/showRateItem.jsp"%>

            <tr><td colspan="5">
                <div class="bold">&nbsp;Для клиентов с тарифным планом «Массовый/Золотой»</div>
            </td></tr>
            <c:set var="ratesTarifPlan" value="2"/>
            <%@ include file="/WEB-INF/jsp/ima/showRateItem.jsp"%>
        </tiles:put>
        <tiles:put name="isEmpty" value="${empty rates}"/>
        <tiles:put name="emptyMessage">
            <bean:message bundle="departmentsBundle" key="rates.label.tableEmpty"/>
        </tiles:put>
	</tiles:insert>

	</tiles:put>
</tiles:insert>

</html:form>
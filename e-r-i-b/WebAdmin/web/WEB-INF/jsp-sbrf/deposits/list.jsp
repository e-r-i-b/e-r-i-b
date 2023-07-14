<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/deposits/list" onsubmit="return setEmptyAction(event);">

<tiles:insert definition="deposits">
    <tiles:put name="submenu" type="string" value="List"/>
	<tiles:put name="pageTitle" type="string">Список депозитных продуктов</tiles:put>

<tiles:put name="menu" type="string">

	<script type="text/javascript">
		var addUrl = "${phiz:calculateActionURL(pageContext,'/deposits/edit')}";

        function editDepositDetailProduct(productId, tariff, group)
        {
            var tariffParam = (tariff === '' || tariff == null || tariff == undefined) ? '' : "&tariff=" + tariff;
            var groupParam = (group === '' || group == null || group == undefined) ? '' : "&group=" + group;

            window.location = addUrl + "?id=" + productId + tariffParam + groupParam;
        }

        var addEntityDetailUrl = "${phiz:calculateActionURL(pageContext,'/depositEntity/edit')}";
        function editDepositEntityDetailProduct(productId, tariff, group)
        {
            var tariffParam = (tariff === '' || tariff == null || tariff == undefined) ? '' : "&tariff=" + tariff;
            var groupParam = (group === '' || group == null || group == undefined) ? '' : "&group=" + group;

            window.location = addEntityDetailUrl + "?id=" + productId + tariffParam + groupParam;
        }

	</script>

</tiles:put>

	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:put name="filter" type="string">
        <c:set var="colCount" value="3" scope="request"/>
        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="bundle" value="depositsBundle"/>
            <tiles:put name="label" value="label.name"/>
            <tiles:put name="data">
                <html:text property="filter(name)" styleClass="filterInput" size="50" maxlength="100"/>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label" value="label.tariff"/>
            <tiles:put name="bundle" value="depositsBundle"/>

            <c:set var="tariffPlans" value="${phiz:getDictionaryTariffPlans()}" scope="request"/>
            <tiles:put name="data">
                <html:select property="filter(tariff)" styleClass="select">
                    <html:option value="">Все тарифы</html:option>
                    <c:forEach var="tariff" items="${tariffPlans}">
                        <html:option value="${tariff.code}">
                            ${tariff.name}
                        </html:option>
                    </c:forEach>
                </html:select>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="bundle" value="depositsBundle"/>
            <tiles:put name="label" value="label.group"/>
            <tiles:put name="data">
                <html:text property="filter(group)" styleClass="numberField" size="10" maxlength="2"/>
            </tiles:put>
        </tiles:insert>
    </tiles:put>

	<tiles:put name="data" type="string">
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="depositsList"/>
            <tiles:put name="text" value="Вклады банка"/>

            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.allowAll"/>
                    <tiles:put name="commandHelpKey" value="button.allowAll.help"/>
                    <tiles:put name="bundle"         value="depositsBundle"/>
                    <tiles:put name="confirmText" value="Разрешить для открытия онлайн все виды вкладов?"/>
                </tiles:insert>
            </tiles:put>

            <c:choose>
                <c:when test="${phiz:isUseCasNsiDictionaries()}">
                    <tiles:put name="data">
                        <tr>
                            <td>
                                <c:if test="${not empty form.depositProducts}">
                                    <tiles:insert page="/WEB-INF/jsp-sbrf/deposits/depositProductList.jsp" flush="false"/>
                                </c:if>
                            </td>
                        </tr>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.depositProducts}"/>
                </c:when>
                <c:otherwise>
                    <tiles:put name="data">
                        <tr>
                            <td>
                                ${form.listHtml}
                            </td>
                        </tr>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.listHtml}"/>
                </c:otherwise>
            </c:choose>
		    <tiles:put name="emptyMessage" value="Не найдено ни одного вида депозитного продукта!"/>
        </tiles:insert>
	</tiles:put>

</tiles:insert>

</html:form>

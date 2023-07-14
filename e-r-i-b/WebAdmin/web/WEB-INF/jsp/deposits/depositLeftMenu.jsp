<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<c:set var="depositId" value="${form.id}"/>
<c:set var="tariff" value="${form.tariff}"/>
<c:set var="group" value="${form.group}"/>

<tiles:insert definition="leftMenuInsetGroup">
    <c:set var="enabledInfo" value="${submenu != 'depositInfo'}"/>
    <c:set var="enabledVisibleInfo" value="${submenu != 'depositVisibleInfo'}"/>

    <tiles:put name="enabled" value="${enabledInfo and enabledVisibleInfo}"/>
    <tiles:put name="action"  value="/deposits/list.do"/>
    <tiles:put name="text"    value="Депозитные продукты"/>
    <tiles:put name="title"   value="Список депозитных продуктов банка"/>
    <tiles:put name="name"    value="deposit_products"/>
    <tiles:put name="data">
        <c:choose>
            <c:when test="${phiz:isUseCasNsiDictionaries()}">
                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${enabledInfo}"/>
                    <tiles:put name="action"  value="/depositEntity/edit.do?id=${depositId}&tariff=${tariff}&group=${group}"/>
                    <tiles:put name="text"    value="Общие сведения"/>
                    <tiles:put name="title"   value="Общие сведения"/>
                    <tiles:put name="parentName" value="deposit_products"/>
                </tiles:insert>
                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${enabledVisibleInfo}"/>
                    <tiles:put name="action"  value="/depositEntity/editVisibility.do?id=${depositId}&tariff=${tariff}&group=${group}"/>
                    <tiles:put name="text"    value="Настройки видимости"/>
                    <tiles:put name="title"   value="Настройки видимости"/>
                    <tiles:put name="parentName" value="deposit_products"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${enabledInfo}"/>
                    <tiles:put name="action"  value="/deposits/edit.do?id=${depositId}&tariff=${tariff}&group=${group}"/>
                    <tiles:put name="text"    value="Общие сведения"/>
                    <tiles:put name="title"   value="Общие сведения"/>
                    <tiles:put name="parentName" value="deposit_products"/>
                </tiles:insert>
                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${enabledVisibleInfo}"/>
                    <tiles:put name="action"  value="/deposits/editVisibility.do?id=${depositId}&tariff=${tariff}&group=${group}"/>
                    <tiles:put name="text"    value="Настройки видимости"/>
                    <tiles:put name="title"   value="Настройки видимости"/>
                    <tiles:put name="parentName" value="deposit_products"/>
                </tiles:insert>
            </c:otherwise>
        </c:choose>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="leftMenuInset" operation="GetDepositProductsListForDeleteOperation">
	<tiles:put name="enabled" value="${submenu != 'ListForRemove'}"/>
    <tiles:put name="action" value="/deposits/list4remove.do"/>
    <tiles:put name="text" value="Удаление депозитных продуктов"/>
	<tiles:put name="title"   value="Удаление депозитных продуктов банка"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" operation="EditProlongationDateAlgorithmOperation">
    <tiles:put name="enabled" value="${submenu != 'ProlongationDate'}"/>
    <tiles:put name="action" value="/deposits/editProlongationDateAlgorithm.do"/>
    <tiles:put name="text" value="Определение даты пролонгации вкладов"/>
    <tiles:put name="title"   value="Определение даты пролонгации вкладов"/>
</tiles:insert>
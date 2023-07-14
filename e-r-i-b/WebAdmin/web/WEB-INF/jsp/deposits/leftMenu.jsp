<%--
  User: Zhuravleva
  Date: 23.04.2009 
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<tiles:insert definition="leftMenuInset" operation="GetDepositProductsListBankOperation">
    <tiles:put name="enabled" value="${submenu != 'List'}"/>
    <tiles:put name="action"  value="/deposits/list.do"/>
    <tiles:put name="text"    value="Депозитные продукты"/>
    <tiles:put name="title"   value="Список депозитных продуктов банка"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" operation="GetDepositProductsListForDeleteOperation">
	<tiles:put name="enabled" value="${submenu != 'ListForRemove'}"/>
    <tiles:put name="action" value="/deposits/list4remove.do"/>
    <tiles:put name="text" value="Удаление депозитных продуктов"/>
	<tiles:put name="title"   value="Удаление депозитных продуктов банка"/>
</tiles:insert>
<c:if test="${phiz:impliesService('EditProlongationDateAlgorithmManagement') or phiz:impliesService('DepositsManagement')}">
    <tiles:insert definition="leftMenuInset">
        <tiles:put name="enabled" value="${submenu != 'ProlongationDate'}"/>
        <tiles:put name="action"  value="/deposits/editProlongationDateAlgorithm.do"/>
        <tiles:put name="text"    value="Определение даты пролонгации вкладов"/>
        <tiles:put name="title"   value="Определение даты пролонгации вкладов"/>
    </tiles:insert>
</c:if>
<tiles:insert definition="leftMenuInset" operation="PromoCodeDepositOperation">
    <tiles:put name="enabled" value="${submenu != 'PromoCodeDepositSettings'}"/>
    <tiles:put name="action" value="/deposits/promoCodeDepositSettings.do"/>
    <tiles:put name="text" value="Настройка промо-кода"/>
    <tiles:put name="title"   value="Настройка промо-кода"/>
</tiles:insert>

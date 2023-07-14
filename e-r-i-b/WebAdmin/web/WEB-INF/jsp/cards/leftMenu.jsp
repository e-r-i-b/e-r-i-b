<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset" service="CreateCardsLimits">
    <tiles:put name="enabled" value="${submenu != 'CardsLimitsList'}"/>
    <tiles:put name="action"  value="/cards/limits/list.do"/>
    <tiles:put name="text"    value="Справочник лимитов"/>
    <tiles:put name="title"   value="Справочник лимитов"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ListCreditCardProductOperation">
    <tiles:put name="enabled" value="${submenu != 'CreditCardProducts'}"/>
    <tiles:put name="action"  value="/creditcards/products/list.do"/>
    <tiles:put name="text"    value="Карточные кредитные продукты"/>
    <tiles:put name="title"   value="Карточные кредитные продукты"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ListCreditCardProductOperation">
    <tiles:put name="enabled" value="${submenu != 'CreditCardProductsChannel'}"/>
    <tiles:put name="action"  value="/creditcards/products/channel.do"/>
    <tiles:put name="text"    value="Доступные продукты в зависимости от канала"/>
    <tiles:put name="title"   value="Доступные продукты в зависимости от канала"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ListIncomeLevelOperation">
    <tiles:put name="enabled" value="${submenu != 'IncomeLevels'}"/>
    <tiles:put name="action"  value="/creditcards/incomes/list.do"/>
    <tiles:put name="text"    value="Доступные продукты в зависимости от дохода"/>
    <tiles:put name="title"   value="Доступные продукты в зависимости от дохода"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ListCardProductOperation">
    <tiles:put name="enabled" value="${submenu != 'CardProducts'}"/>
    <tiles:put name="action"  value="/card/products/list.do"/>
    <tiles:put name="text"    value="Карточные продукты"/>
    <tiles:put name="title"   value="Карточные продукты"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ListGuestDebitCardClaimsOperation">
    <tiles:put name="enabled" value="${submenu != 'DebitCardClaims'}"/>
    <tiles:put name="action"  value="/debitcard/claims/list.do"/>
    <tiles:put name="text"    value="Заявки на дебетовую карту"/>
    <tiles:put name="title"   value="Заявки на дебетовую карту"/>
</tiles:insert>


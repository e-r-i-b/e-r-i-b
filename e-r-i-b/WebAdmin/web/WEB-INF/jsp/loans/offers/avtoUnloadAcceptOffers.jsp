<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<html:form action="/loans/offers/avtoUnloading" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="loansMain">
<tiles:put name="submenu" type="string" value="AutoUnloadAcceptOffers"/>
<tiles:put name="data" type="string">
<tiles:insert definition="paymentForm" flush="false">
    <tiles:put name="id" value="offerLoad"/>
    <tiles:put name="name" value="Настройка автоматической выгрузки заявок"/>
    <tiles:put name="description"
               value="Для того чтобы выгрузить заявки, укажите необходимые параметры и нажмите на кнопку «Выгрузить»."/>
    <tiles:put name="data">

        <tiles:insert definition="autoUnloadSettings" flush="false">
            <tiles:put name="id" value="loanProduct"/>
            <tiles:put name="name" value="Заявки на кредиты"/>
        </tiles:insert>

        <tiles:insert definition="autoUnloadSettings" flush="false">
            <tiles:put name="id" value="loanOffer"/>
            <tiles:put name="name" value="Заявки на предодобренные кредиты"/>
        </tiles:insert>

        <tiles:insert definition="autoUnloadSettings" flush="false">
            <tiles:put name="id" value="loanCardProduct"/>
            <tiles:put name="name" value="Заявки на кредитные карты"/>
        </tiles:insert>

        <tiles:insert definition="autoUnloadSettings" flush="false">
            <tiles:put name="id" value="loanCardOffer"/>
            <tiles:put name="name" value="Заявки на предодобренные кредитные карты"/>
        </tiles:insert>

        <tiles:insert definition="autoUnloadSettings" flush="false">
            <tiles:put name="id" value="virtualCard"/>
            <tiles:put name="name" value="Заявки на виртуальные карты"/>
        </tiles:insert>
        
    </tiles:put>
    <tiles:put name="buttons">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandHelpKey" value="button.cancel"/>
            <tiles:put name="commandTextKey" value="button.cancel"/>
            <tiles:put name="action" value="/loans/offers/avtoUnloading.do"/>
            <tiles:put name="bundle" value="loansBundle"/>
        </tiles:insert>
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey" value="button.save"/>
            <tiles:put name="commandHelpKey" value="button.save"/>
            <tiles:put name="bundle" value="loansBundle"/>
        </tiles:insert>
    </tiles:put>

</tiles:insert>
</tiles:put>
</tiles:insert>

</html:form>

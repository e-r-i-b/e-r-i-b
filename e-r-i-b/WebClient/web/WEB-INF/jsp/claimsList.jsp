<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="claimList">
  <!-- заголовок -->
  <tiles:put name="pageTitle" type="string">Заявки</tiles:put>
  <tiles:put name="data" type="string">

<div id="workspace" style="position:absolute; width:100%">
<div class="pmntListGroup">
<div class='pmntListGroupTitle'>Заявки</div>
<tiles:insert definition="ClaimsCards" service="DepositOpeningClaim" flush="false">
	<tiles:put name="serviceId" value="DepositOpeningClaim"/>
	<tiles:put name="listPayTitle" value="Открытие счета/вклада"/>
	<tiles:put name="description" value="Подача в банк заявки на открытие счета или срочного вклада."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="DepositClosingClaim" flush="false">
	<tiles:put name="serviceId" value="DepositClosingClaim"/>
	<tiles:put name="listPayTitle" value="Закрытие счета/вклада"/>
	<tiles:put name="description" value="Подача в банк заявки на закрытие счета или досрочное закрытие срочного вклада."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="BankcellLeasingClaim" flush="false">
	<tiles:put name="serviceId" value="BankcellLeasingClaim"/>
	<tiles:put name="listPayTitle" value="Аренда сейфовой ячейки"/>
	<tiles:put name="description" value="Подача в банк заявки на бронирование сейфовой ячейки в одном из офисов банка."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="DepositReplenishmentClaim" flush="false">
	<tiles:put name="serviceId" value="DepositReplenishmentClaim"/>
	<tiles:put name="listPayTitle" value="Пополнить вклад"/>
	<tiles:put name="description" value="Перевод денежных средств с вашего счета для пополнения срочного вклада."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="SecuritiesOperationsClaim" flush="false">
	<tiles:put name="serviceId" value="SecuritiesOperationsClaim"/>
	<tiles:put name="listPayTitle" value="Операции с ценными бумагами"/>
	<tiles:put name="description" value="Подача в банк заявки на совершение операций с ценными бумагами в одном из офисов банка."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="InternalTransferClaim" flush="false">
	<tiles:put name="serviceId" value="InternalTransferClaim"/>	
	<tiles:put name="listPayTitle" value="Списать средства со вклада"/>
	<tiles:put name="description" value="Перевод денежных средств со вклада на ваш счет."/>
</tiles:insert>
</div>
    <div style="clear:both;"></div>
<div class="pmntListGroup">
<div class='pmntListGroupTitle'>Пластиковые карты</div>

<tiles:insert definition="ClaimsCards" service="UnblockingCardClaim" flush="false">
	<tiles:put name="serviceId" value="UnblockingCardClaim"/>
	<tiles:put name="listPayTitle" value="Разблокировка карты"/>
	<tiles:put name="description" value="Подача в банк заявки на разблокировку пластиковой карты."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="SMSInformationClaim" flush="false">
	<tiles:put name="serviceId" value="SMSInformationClaim"/>
	<tiles:put name="listPayTitle" value="SMS - информирование"/>
	<tiles:put name="description" value="Подача в банк заявки на SMS-информирование по операциям с пластиковой карты."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="NotReIssueCardClaim" flush="false">
	<tiles:put name="serviceId" value="NotReIssueCardClaim"/>
	<tiles:put name="listPayTitle" value="Отказ от перевыпуска карты"/>
	<tiles:put name="description" value="Подача в банк заявки об отказе от перевыпуска пластиковой карты."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="ReIssueCardClaim" flush="false">
	<tiles:put name="serviceId" value="ReIssueCardClaim"/>
	<tiles:put name="listPayTitle" value="Перевыпуск карты"/>
	<tiles:put name="description" value="Подача в банк заявки на перевыпуск пластиковой карты."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="CardChargeLimitClaim" flush="false">
	<tiles:put name="serviceId" value="CardChargeLimitClaim"/>
	<tiles:put name="listPayTitle" value="Установка лимита для доп.карт"/>
	<tiles:put name="description" value="Заявка на установку лимита расходования средств для дополнительных карт клиента."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="IssueCardClaim" flush="false">
	<tiles:put name="serviceId" value="IssueCardClaim"/>
	<tiles:put name="listPayTitle" value="Выпуск пластиковой карты"/>
	<tiles:put name="description" value="Подача в банк заявки на выпуск пластиковой карты."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="BlockingCardClaim" flush="false">
	<tiles:put name="serviceId" value="BlockingCardClaim"/>
	<tiles:put name="listPayTitle" value="Блокировка карты"/>
	<tiles:put name="description" value="Подача в банк заявки на блокировка пластиковой карты."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="IssueAdditionalCardClaim" flush="false">
	<tiles:put name="serviceId" value="IssueAdditionalCardClaim"/>
	<tiles:put name="listPayTitle" value="Изготовление дополнительной карты"/>
	<tiles:put name="description" value="Подача в банк заявки на изготовление дополнительной пластиковой карты."/>
</tiles:insert>


<tiles:insert definition="ClaimsCards" service="StopListCardClaim" flush="false">
	 <tiles:put name="serviceId" value="StopListCardClaim"/>
	 <tiles:put name="listPayTitle" value="Постановка карты в СТОП-ЛИСТ"/>
	 <tiles:put name="description" value="Заявление на постановку карты в стоп-лист."/>
</tiles:insert>

<tiles:insert definition="ClaimsCards" service="CardMootTransClaim" flush="false">
	 <tiles:put name="serviceId" value="CardMootTransClaim"/>	 
	 <tiles:put name="listPayTitle" value="Расследование спорных транзакций"/>
	 <tiles:put name="description" value="Заявление на расследование спорных транзакций по пластиковой карте."/>
</tiles:insert>

 </div>
</div>
<script type="text/javascript">
    changeDivSize("workspace");
</script>

</tiles:put>
</tiles:insert>
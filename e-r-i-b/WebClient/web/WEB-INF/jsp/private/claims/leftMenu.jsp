<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>

<tiles:insert definition="leftMenuInset" service="DepositOpeningClaim">
	<tiles:put name="enabled" value="${submenu != 'DepositOpeningClaim'}"/>
	<tiles:put name="action" value="/private/claims/claim.do?form=DepositOpeningClaim"/>
	<tiles:put name="text" value="Открытие счета/вклада"/>
	<tiles:put name="title" value="Подача в банк заявки на открытие текущего счета или срочного вклада"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="DepositClosingClaim">
	<tiles:put name="enabled" value="${submenu != 'DepositClosingClaim'}"/>
	<tiles:put name="action" value="/private/claims/claim.do?form=DepositClosingClaim"/>
	<tiles:put name="text" value="Закрытие счета/вклада"/>
	<tiles:put name="title" value="Подача в банк заявки на закрытие текущего счета или досрочное закрытие срочного вклада"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="DepositReplenishmentClaim">
	<tiles:put name="enabled" value="${submenu != 'DepositReplenishmentClaim'}"/>
	<tiles:put name="action" value="/private/claims/claim.do?form=DepositReplenishmentClaim"/>
	<tiles:put name="text" value="Пополнить вклад"/>
	<tiles:put name="title" value="Перевод денежных средств с вашего счета для пополнения срочного вклада"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="InternalTransferClaim">
	<tiles:put name="enabled" value="${submenu != 'InternalTransferClaim'}"/>
	<tiles:put name="action" value="/private/claims/claim.do?form=InternalTransferClaim"/>
	<tiles:put name="text" value="Списать средства со вклада"/>
	<tiles:put name="title" value="Перевод денежных средств со вклада на ваш счет"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="BankcellLeasingClaim">
	<tiles:put name="enabled" value="${submenu != 'BankcellLeasingClaim'}"/>
	<tiles:put name="action" value="/private/claims/claim.do?form=BankcellLeasingClaim"/>
	<tiles:put name="text" value="Аренда сейфовой ячейки"/>
	<tiles:put name="title" value="Подача в банк заявки на бронирование сейфовой ячейки в одном из офисов банка"/>
</tiles:insert>
<tiles:insert definition="leftMenuInset" service="SecuritiesOperationsClaim">
	<tiles:put name="enabled" value="${submenu != 'SecuritiesOperationsClaim'}"/>
	<tiles:put name="action" value="/private/claims/claim.do?form=SecuritiesOperationsClaim"/>
	<tiles:put name="text" value="Операции с ценными бумагами"/>
	<tiles:put name="title" value="Подача в банк заявки на совершение операций с ценными бумагами в одном из офисов банка"/>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup">
	<tiles:put name="text"    value="Пластиковые карты"/>
	<tiles:put name="name"    value="CardClaim"/>
    <tiles:put name="enabled" value="${submenu != 'UnblockingCardClaim'  and submenu != 'SMSInformationClaim' and
                                       submenu != 'NotReIssueCardClaim'  and submenu != 'ReIssueCardClaim' and
                                       submenu != 'CardChargeLimitClaim' and submenu != 'IssueCardClaim' and
                                       submenu != 'BlockingCardClaim'    and submenu != 'IssueAdditionalCardClaim' and
                                       submenu != 'StopListCardClaim'    and submenu != 'CardMootTransClaim'}"/>
	<tiles:put name="data">
		<tiles:insert definition="leftMenuInset" service="UnblockingCardClaim" flush="false">
			<tiles:put name="enabled" value="${submenu != 'UnblockingCardClaim'}"/>
			<tiles:put name="action" value="/private/claims/claim.do?form=UnblockingCardClaim"/>
			<tiles:put name="text" value="Разблокировка карты"/>
			<tiles:put name="title" value="Подача в банк заявки на разблокировку пластиковой картой"/>
			<tiles:put name="parentName" value="CardClaim"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="SMSInformationClaim" flush="false">
			<tiles:put name="enabled" value="${submenu != 'SMSInformationClaim'}"/>
			<tiles:put name="action" value="/private/claims/claim.do?form=SMSInformationClaim"/>
			<tiles:put name="text" value="SMS - информирование"/>
			<tiles:put name="title" value="Подача в банк заявки на SMS-информирование по операциям с пластиковой карты"/>
			<tiles:put name="parentName" value="CardClaim"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="NotReIssueCardClaim" flush="false">
			<tiles:put name="enabled" value="${submenu != 'NotReIssueCardClaim'}"/>
			<tiles:put name="action" value="/private/claims/claim.do?form=NotReIssueCardClaim"/>
			<tiles:put name="text" value="Отказ от перевыпуска карты"/>
			<tiles:put name="title" value="Подача в банк заявки об отказе от перевыпуска пластиковой картой"/>
			<tiles:put name="parentName" value="CardClaim"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="ReIssueCardClaim" flush="false">
			<tiles:put name="enabled" value="${submenu != 'ReIssueCardClaim'}"/>
			<tiles:put name="action" value="/private/claims/claim.do?form=ReIssueCardClaim"/>
			<tiles:put name="text" value="Перевыпуск карты"/>
			<tiles:put name="title" value="Подача в банк заявки на перевыпуск пластиковой картой"/>
			<tiles:put name="parentName" value="CardClaim"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="CardChargeLimitClaim" flush="false">
			<tiles:put name="enabled" value="${submenu != 'CardChargeLimitClaim'}"/>
			<tiles:put name="action" value="/private/claims/claim.do?form=CardChargeLimitClaim"/>
			<tiles:put name="text" value="Установка лимита для доп.карт"/>
			<tiles:put name="title" value="Заявка на установку лимита расходования средств для дополнительных карт клиента"/>
			<tiles:put name="parentName" value="CardClaim"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="IssueCardClaim" flush="false">
			<tiles:put name="enabled" value="${submenu != 'IssueCardClaim'}"/>
			<tiles:put name="action" value="/private/claims/claim.do?form=IssueCardClaim"/>
			<tiles:put name="text" value="Выпуск пластиковой карты"/>
			<tiles:put name="title" value="Подача в банк заявки на выпуск пластиковой карты"/>
			<tiles:put name="parentName" value="CardClaim"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="BlockingCardClaim" flush="false">
			<tiles:put name="enabled" value="${submenu != 'BlockingCardClaim'}"/>
			<tiles:put name="action" value="/private/claims/claim.do?form=BlockingCardClaim"/>
			<tiles:put name="text" value="Блокировка карты"/>
			<tiles:put name="title" value="Подача в банк заявки на блокировку пластиковой карты"/>
			<tiles:put name="parentName" value="CardClaim"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="IssueAdditionalCardClaim" flush="false">
			<tiles:put name="enabled" value="${submenu != 'IssueAdditionalCardClaim'}"/>
			<tiles:put name="action" value="/private/claims/claim.do?form=IssueAdditionalCardClaim"/>
			<tiles:put name="text" value="Изготовление дополнительной карты"/>
			<tiles:put name="title" value="Подача в банк заявки на изготовление дополнительной пластиковой карты"/>
			<tiles:put name="parentName" value="CardClaim"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="StopListCardClaim" flush="false">
			<tiles:put name="enabled" value="${submenu != 'StopListCardClaim'}"/>
			<tiles:put name="action" value="/private/claims/claim.do?form=StopListCardClaim"/>
			<tiles:put name="text" value="Постановка карты в СТОП-ЛИСТ"/>
			<tiles:put name="title" value="Заявление на постановку карты в стоп-лист"/>
			<tiles:put name="parentName" value="CardClaim"/>
		</tiles:insert>
		<tiles:insert definition="leftMenuInset" service="CardMootTransClaim" flush="false">
			<tiles:put name="enabled" value="${submenu != 'CardMootTransClaim'}"/>
			<tiles:put name="action" value="/private/claims/claim.do?form=CardMootTransClaim"/>
			<tiles:put name="text" value="Расследование спорных транзакций"/>
			<tiles:put name="title" value="Заявление на расследование спорных транзакций по пластиковой карте"/>
			<tiles:put name="parentName" value="CardClaim"/>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
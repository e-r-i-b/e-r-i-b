package com.rssl.phizic.business.shop;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.kbk.KBK;
import com.rssl.phizic.business.dictionaries.kbk.KBKService;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;


/**
 * @author Mescheryakova
 * @ created 23.12.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Получает пришедшие по заказу данные из базы и заполняет ими поля для формы-платежа
 */

public class FnsDataServiceSources extends ExternalPaymentDataServiceSources
{
	private static final String GROUND_BY_KBK =  "groundByKBK";
	private static final String PAYER_LAST_NAME = "payerLastName";
	private static final String PAYER_FIRST_NAME = "payerFirstName";
	private static final String PAYER_MIDDLE_NAME = "payerMiddleName";
	private static final String PAYER_INN = "payerInn";
	private static final String STATUS_PAYER = "statusPayer";
	private static final String KBK = "kbk";
	private static final String OKATO = "okato";
	private static final String TAX_GROUND = "taxGround";
	private static final String TAX_PERIOD = "taxPeriod";
	private static final String TAX_TYPE = "taxType";

	/**
	 * Журнал для записи сообщений
	 */
	public FnsDataServiceSources(String orderId) throws BusinessException
	{
		Map<String, String> map = new HashMap<String, String>();
		OrderInfo orderInfo  = ExternalPaymentService.get().getOrderInfo(orderId);

		// узнаем все о поставщике
		InternetShopsServiceProvider provider = providerService.getRecipientActivityBySystemName(orderInfo.getOrder().getSystemName());
		if (provider == null)
			return;

		map = getSourceMap((FNS) orderInfo);

		// получатель
		map.put(RECIPIENT, provider.getId().toString());
		String receiverName = provider.getName();
		receiverName = provider.getName();

		map.put(PaymentFieldKeys.RECEIVER_NAME, receiverName);
		map.put(RECEIVER_ID,  provider.getSynchKey().toString());
		map.put(CODE_SERVICE, provider.getCodeService());
		map.put(NAME_SERVICE, provider.getServiceName());
		map.put(RECEIVER_PHONE_NUMBER, provider.getPhoneNumber());
		map.put(RECEIVER_NAME_ON_BILL, provider.getNameOnBill());

		map.put(RECEIVER_ACCOUNT, orderInfo.getOrder().getReceiverAccount());
		map.put(RECEIVER_NAME_ORDER, receiverName);
		map.put(INN, orderInfo.getOrder().getINN());
		map.put(KPP,  orderInfo.getOrder().getKPP());

		map.put(RECEIVER_BANK_CODE, orderInfo.getOrder().getBIC());
		map.put(PaymentFieldKeys.ORDER_ID_KEY, orderId);

		String corrAccount = orderInfo.getOrder().getCorrespondentAccount();
		ResidentBank bank =  bankService.findByBIC(orderInfo.getOrder().getBIC());;
		if (StringHelper.isEmpty(corrAccount) && bank != null)
			corrAccount = bank.getAccount();

		map.put(RECEIVER_COR_ACCOUNT, corrAccount);

		if (bank != null)
			map.put(RECEIVER_BANK_NAME, bank.getName());

		this.source = new MapValuesSource(map);
	}

	/**
	 * заполняем поля формы для оплаты заказа из ФНС
	 * @param fns -  заполненный из базы объект фнс
	 * @return - map вида: название_поля => значение из базы
	 */
	private Map<String, String> getSourceMap(FNS fns) throws BusinessException
	{
		Map<String, String> map =  new HashMap<String, String>();

		//Оплата
		map.put(AMOUNT,  getDecimalFromMoney(fns.getOrder().getAmount()));
		map.put(CURRENCY, getCurrencyFromMoney(fns.getOrder().getAmount()));
		if (fns.getKBK() != null)
		{
			KBKService kbkService = new KBKService();
			KBK kbk = (fns.getKBK() != null ? kbkService.findByCode(fns.getKBK()) : null);
			if (kbk != null)
				map.put(GROUND_BY_KBK, kbk.getDescription());
		}

		// плательщик
		map.put(PAYER_LAST_NAME, fns.getOrder().getPerson().getSurName());
		map.put(PAYER_FIRST_NAME, fns.getOrder().getPerson().getFirstName());
		map.put(PAYER_MIDDLE_NAME, fns.getOrder().getPerson().getPatrName());
		map.put(PAYER_INN, fns.getPayerINN());

		// налоговые поля
		map.put(STATUS_PAYER, fns.getTaxStatus());
		map.put(KBK, fns.getKBK());
		map.put(OKATO, fns.getOKATO());
		map.put(TAX_GROUND, fns.getPaymentGround());
		map.put(TAX_PERIOD, fns.getPeriod());
		map.put(REC_IDENTIFIER, fns.getIndexTaxationDocument());
		map.put(TAX_TYPE, fns.getPaymentType());

		return map;
	}
}

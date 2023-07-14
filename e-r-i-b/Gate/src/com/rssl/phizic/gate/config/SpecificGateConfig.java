package com.rssl.phizic.gate.config;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.payments.AccountJurIntraBankTransfer;
import com.rssl.phizic.gate.payments.AccountJurTransfer;
import com.rssl.phizic.gate.payments.AccountRUSTaxPayment;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;

/**
 * Конфиг со специфичными настройками гейта
 * Всякие магические цифры, типа номера Тер Банка, кот. обслуживает данный шлюз.
 *
 * @author egorova
 * @ created 13.07.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class SpecificGateConfig extends Config
{
	public static final String CREDITS_ROUTES_DICTIONARY_PATH = "com.rssl.gate.credits.routes.dictionary.path";
	public static final String DOCUMENT_UPDATE_WAITING_TIME = "com.rssl.gate.document.waiting.time";
	public static final String RECEIVERS_ROUTES_DICTIONARY_PATH = "com.rssl.gate.receivers.routes.dictionary.path";

	public static final String USE_PAYMENT_ORDER_ACCOUNT_JUR_TRANSFER = AccountJurTransfer.class.getName() + ".usePaymentOrder";
	public static final String USE_PAYMENT_ORDER_ACCOUNT_JUR_INTRA_BANK_TRANSFER = AccountJurIntraBankTransfer.class.getName() + ".usePaymentOrder";
	public static final String USE_PAYMENT_ORDER_RUS_TAX_PAYMENT = AccountRUSTaxPayment.class.getName() + ".usePaymentOrder";
	public static final String USE_PAYMENT_ORDER_ACCOUNT_PAYMENT = AccountPaymentSystemPayment.class.getName() + ".usePaymentOrder";

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 *
	 * @param reader ридер.
	 */
	protected SpecificGateConfig(PropertyReader reader)
	{
		super(reader);
	}

	//Путь к справочнику маршрутов кредитов
	public abstract String getCreditsRoutesDictionaryPath();

	/**
	 * @return путь к справочнику маршрутов получателей платежей
	 */
	public abstract String getReceiversRoutesDictionaryPath();

	/**
	 * @return Способ формирования платежного поручения для переводов юр лицу со счета на счет в другой банк.
	 */
	public abstract String getUsePaymentOrderForAccountJurTransfer();
	/**
	 * @return Способ формирования платежного поручения для переводов юр лицу со счета на счет внутри Сбербанка.
	 */
	public abstract String getUsePaymentOrderForAccountJurIntrabankTransfer();
	/**
	 * @return Способ формирования платежного поручения для оплаты налогов.
	 */
	public abstract String getUsePaymentOrderForRUSTaxPayment();
	/**
	 * @return Способ формирования платежного поручения для биллинговых платежей со счета.
	 */
	public abstract String getUsePaymentOrderForAccountPaymentSystemPayment();
	/**
	 * @return время ожидания, после которого начинаем уточнять статус документа (в секундах)
	 */
	public abstract Integer getDocumentUpdateWaitingTime();
}

package com.rssl.phizic.gate.payments;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Gainanov
 * @ created 14.03.2007
 * @ $Author$
 * @ $Revision$
 * @deprecated не соответствует модели гейта
 */
//TODO убрать из интерфейса гейта
//TODO убрать все коды ритейла отсюда
//todo перед чисток, определиться, что исползуеться и перенести в соотв. конфиги
//ENH009631: Убрать PaymentsConfig.	
@Deprecated
public abstract class PaymentsConfig extends Config
{
	public static final String NEED_ACCOUNT = "com.rssl.iccs.need.account";
	public static final String OPERATE_NUMBER = "com.rssl.iccs.operate.person.number";
	public static final String OPERATION_TRANSFER_OTHER_BANK = "com.rssl.iccs.operation.tranfer.other.bank";
	public static final String OPERATION_TRANSFER_OTHER_ACCOUNT = "com.rssl.iccs.operation.tranfer.other.account";
	public static final String OPERATION_TRANSFER_CONVERT = "com.rssl.iccs.operation.tranfer.convert";
	public static final String OPERATION_OPEN_ACCOUNT = "com.rssl.iccs.operation.open.account";
	public static final String OPERATION_ACCOUNT_PAY = "com.rssl.iccs.operation.account.pay";
	public static final String OPERATION_CLOSE_ACCOUNT = "com.rssl.iccs.operation.close.account";
	public static final String SYSTEM_APPLICATION_KIND = "com.rssl.iccs.system.aplication.kind";
	public static final String CONTACT_RETAIL_ID = "com.rssl.iccs.contact.reatail.id";
	public static final String OPERATION_MUNICIPAL_PAYMENT = "com.rssl.iccs.operation.municipal.payment";
	public static final String MUNICIPAL_PAYMENT_RECEIVER = "com.rssl.iccs.municipal.payment.receiver";
	public static final String BANKS_LIST = "com.rssl.iccs.banks.list";
	public static final String SUBOPERATION_TRANSFER_OTHER_BANK_JUR = "com.rssl.iccs.suboperation.transfer.other.bank.jur";
	public static final String SUBOPERATION_TRANSFER_OTHER_BANK_FIZ = "com.rssl.iccs.suboperation.transfer.other.bank.fiz";
	public static final String SUBOPERATION_TRANSFER_KORR_BANK = "com.rssl.iccs.suboperation.transfer.korr.bank";
	public static final String SUBOPERATION_CONTACT_TRANSFER_FIZ = "com.rssl.iccs.suboperation.contact.transfer.fiz";
	public static final String SUBOPERATION_CONTACT_TRANSFER_JUR = "com.rssl.iccs.suboperation.contact.transfer.jur";
	public static final String SUBOPERATION_TAX_PAYMENT = "com.rssl.iccs.suboperation.tax.payment";
	public static final String DATE_OF_STOP_EXTERNAL_ACCOUNT_PAYMENT = "com.rssl.phizic.payments.dateOfStopAccountPayment";
	protected static final String IQW_PROVIDER_CODE_ONLINE_PAYMENT_SETTING = "com.rssl.iccs.payment.online.iqw.provider.codes";
	public static final String INVOICE_ID = "com.rssl.iccs.payment.einvoicing.invoiceidkey";
	public static final String UID = "com.rssl.iccs.payment.einvoicing.eribuidkey";
	public static final String SUM = "com.rssl.iccs.payment.einvoicing.sumkey";

	//сделано временно, надо будет убрать
	public static final String OUR_BANK_BIC = "com.rssl.iccs.our.bank.bic";
	public static final String OUR_BANK_CORACC = "com.rssl.iccs.our.bank.coracc";

	protected PaymentsConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * идентификатор платежной системы контакт в retail
	 * @return
	 */
	public abstract Long getContactRetailId();

	/**
	 * Вид приложения
	 * @return
	 */
	public abstract Long getApplicationKind();
	/**
	 * true если необходимо указывать счет для списания платы услуг IKFL
	 * @return
	 */
	public abstract Boolean getNeedAccount();

	/**
	 * Номер операциониста, под которым ИКФЛ работает во внешними системах
	 * @return
	 */
	public abstract Long getOperateNumber();

	/**
	 * Номер операции перевода денег на счет в другой банк
 	 * @return
	 */
	public abstract Long getTransferOtherBankOperation();

	/**
	 * Номер операции перевода денег на другой счет
	 * @return
	 */
	public abstract Long getTransferOtherAccountOperation();

	/**
	 * Номер операции перевода денег на счет в другой валюте
	 * @return
	 */
	public abstract Long getTransferConvertOperation();

	/**
	 * Номер операции открытия счета
	 * @return
	 */
	public abstract Long getOpenAccountOperation();

	/**
	 * Номер операции закрытия счета
	 * @return
	 */
	public abstract Long getCloseAccountOperation();

	/**
	 * номер операции списания
	 * @return
	 */
	public abstract Long getPayAccountOperation();

	/**
	 * TODO это что за ересь?
	 * @return список непосредственных корреспондентов нашего банка
	 */
	public abstract ArrayList getBanksList();

	/**
	 *
	 * @return код подоперации перевода денег в др банк юр лицам
	 */
	public abstract String getTransferOtherBankSuboperationJur();

	/**
	 *
 	 * @return код подоперации перевода денег в др банк физ лицам
	 */
	public abstract String getTransferOtherBankSuboperationFiz();

	/**
	 *
	 * @return код подоперации перевода денег в банк-непосредственный корреспондент нашего
	 */
	public abstract String getTransferKorrBankSuboperation();

	/**
	 * 
	 * @return код подоперации перевод по контакту физ лицу
	 */
	public abstract String getContactTransferSuboperationFiz();

	/**
	 *
	 * @return код подоперации перевод по контакту юр лицу
	 */
	public abstract String getContactTransferSuboperationJur();

	/**
	 *
	 * @return  код подоперации налогового платежа
	 */
	public abstract String getTaxPaymentSuboperation();

	/**
	 * получить БИК нашего банка. Сделанно временно, до получения реквизитов нашего банка из бэк-офиса
	 * @return
	 */
	public abstract String getOurBankBic();
	
	/**
	 * получить коррсчет нашего банка. Сделанно временно, до получения реквизитов нашего банка из бэк-офиса
	 * @return
	 */
	public abstract String getOurBankCorAcc();

	/**
	 * Возвращает дату отключения внешних платежей по счету.
	 * @return дата в строке.
	 */
	public abstract String getDateOfStopExternalPayments();


	public abstract Set<String> getProviderCodes();

	public abstract String getEinvoicingUIDKey();

	public abstract String getEinvoicingSumKey();

	public abstract String getEinvoicingInvoiceIDKey();
}

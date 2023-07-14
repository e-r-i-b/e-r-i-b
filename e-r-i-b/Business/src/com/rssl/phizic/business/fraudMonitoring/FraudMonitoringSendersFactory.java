package com.rssl.phizic.business.fraudMonitoring;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.LoanCardClaim;
import com.rssl.phizic.business.documents.payments.LoanProductClaim;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.*;
import com.rssl.phizic.business.fraudMonitoring.senders.events.*;
import com.rssl.phizic.business.fraudMonitoring.senders.templates.*;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.claims.cards.LoanCardOfferClaim;
import com.rssl.phizic.gate.claims.cards.LoanCardProductClaim;
import com.rssl.phizic.gate.claims.cards.PreApprovedLoanCardClaim;
import com.rssl.phizic.gate.claims.cards.VirtualCardClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.autopayment.CreateAutoPayment;
import com.rssl.phizic.gate.payments.autopayment.EditAutoPayment;
import com.rssl.phizic.gate.payments.autosubscriptions.*;
import com.rssl.phizic.gate.payments.loan.ETSMLoanClaim;
import com.rssl.phizic.gate.payments.loan.EarlyLoanRepaymentClaim;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.CreateCardToAccountLongOffer;
import com.rssl.phizic.gate.payments.longoffer.EditCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.EditCardToAccountLongOffer;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.NullFraudMonitoringSender;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestSendingType;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.security.util.MobileApiUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Фабрика сендеров во Фрод-мониторинг
 *
 * @author khudyakov
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringSendersFactory
{
	private static final FraudMonitoringSendersFactory INSTANCE = new FraudMonitoringSendersFactory();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);


	//сендеры по документу
	private static final Map<Class<? extends GateDocument>, Class<? extends FraudMonitoringSender>> documentSenders
					= new HashMap<Class<? extends GateDocument>, Class<? extends FraudMonitoringSender>>();

	//сендеры по шаблону документа
	private static final Map<Class<? extends GateDocument>, Class<? extends FraudMonitoringSender>> templateSenders
					= new HashMap<Class<? extends GateDocument>, Class<? extends FraudMonitoringSender>>();

	//сендеры по событию
	private static final Map<EventsType, Class<? extends FraudMonitoringSender>> eventSenders
					= new HashMap<EventsType, Class<? extends FraudMonitoringSender>>();

	static
	{
		//сендеры по документу
		//переводы между моими счетами/картами/мет. счетами
		documentSenders.put(InternalCardsTransfer.class,                    AnalyzeCardsTransferDocumentSender.class);                      //перевод между моими картами
		documentSenders.put(CardToAccountTransfer.class,                    AnalyzeCardToAccountTransferDocumentSender.class);              //перевод с карты на счет
		documentSenders.put(AccountToCardTransfer.class,                    AnalyzeAccountToCardTransferDocumentSender.class);              //перевод со счета на карту
		documentSenders.put(ClientAccountsTransfer.class,                   AnalyzeClientAccountsTransferDocumentSender.class);             //перевод между моими счетами
		documentSenders.put(IMAToAccountTransfer.class,                     AnalyzeClientAccountsTransferDocumentSender.class);             //перевод с металического счета на счет
		documentSenders.put(AccountToIMATransfer.class,                     AnalyzeClientAccountsTransferDocumentSender.class);             //перевод со счета на мет. счет
		documentSenders.put(IMAToCardTransfer.class,                        AnalyzeAccountToCardTransferDocumentSender.class);              //перевод с металического счета на карту
		documentSenders.put(CardToIMATransfer.class,                        AnalyzeCardToAccountTransferDocumentSender.class);              //перево с карты на мет. счет
		//переводы частному лицу
		documentSenders.put(ExternalCardsTransferToOurBank.class,           AnalyzeExternalCardsTransferToOurBankDocumentSender.class);     //перевод внутрибанковский
		documentSenders.put(ExternalCardsTransferToOtherBank.class,         AnalyzeExternalCardsTransferToOtherBankDocumentSender.class);   //перевод внешнебанковский
		documentSenders.put(CardRUSPayment.class,                           AnalyzeToAccountRurPaymentDocumentSender.class);                //с карты на счет в другой банк через платежную систему России
		documentSenders.put(CardIntraBankPayment.class,                     AnalyzeToAccountRurPaymentDocumentSender.class);                //с карты на счет внутри банка
		documentSenders.put(AccountIntraBankPayment.class,                  AnalyzeToAccountRurPaymentDocumentSender.class);                //с вклада на счет внутри банка
		documentSenders.put(AccountRUSPayment.class,                        AnalyzeToAccountRurPaymentDocumentSender.class);                //с вклада на счет в другой банк через платежную систему России
		//биллинговые операции
		documentSenders.put(CardPaymentSystemPayment.class,                 AnalyzeCardPaymentSystemTransferDocumentSender.class);          //биллинговая оплата
		//закрыти счета
		documentSenders.put(AccountClosingPaymentToCard.class,              AnalyzeAccountClosingClaimSender.class);                        //закрытие вклада с переводом денежных средств на карту
		documentSenders.put(AccountClosingPaymentToAccount.class,           AnalyzeAccountClosingClaimSender.class);                        //закрытие вклада с переводом денежных средств на счёт

		//автоплатежи
		//создание автоплатежа
		documentSenders.put(InternalCardsTransferLongOffer.class,           AnalyzeCreateP2PAutoTransferClaimSender.class);                 //заявка на создание автоперевода между своими картами
		documentSenders.put(ExternalCardsTransferToOurBankLongOffer.class,  AnalyzeCreateP2PAutoTransferClaimSender.class);                 //заявка на создание автоперевода на внешнюю карту
		documentSenders.put(ExternalCardsTransferToOtherBankLongOffer.class,  AnalyzeCreateP2PAutoTransferClaimSender.class);               //заявка на создание автоперевода на внешнюю карту в другом ТБ
		documentSenders.put(CreateCardToAccountLongOffer.class,             AnalyzeCreateMoneyBoxPaymentSender.class);                      //заявка на создание копилки
		documentSenders.put(CardPaymentSystemPaymentLongOffer.class,        AnalyzeCreateCardPaymentSystemPaymentLongOfferSender.class);    //заявка на создание бил. автоплатежа (шина)
		documentSenders.put(CreateAutoPayment.class,                        AnalyzeCreateCardPaymentSystemPaymentLongOfferSender.class);    //заявка на создание бил. автоплатежа (iqwave)
		//редактирование автоплатежа
		documentSenders.put(EditInternalP2PAutoTransfer.class,              AnalyzeEditP2PAutoTransferClaimSender.class);                   //заявка на редактирование автоперевода между своими картами
		documentSenders.put(EditExternalP2PAutoTransfer.class,              AnalyzeEditP2PAutoTransferClaimSender.class);                   //заявка на редактирование автоперевода на внешнюю карту
		documentSenders.put(EditCardToAccountLongOffer.class,               AnalyzeEditMoneyBoxPaymentSender.class);                        //заявка на редактирование копилки
		documentSenders.put(EditCardPaymentSystemPaymentLongOffer.class,    AnalyzeEditCardPaymentSystemPaymentLongOfferSender.class);      //заявка на редактирование бил. автоплатежа (шина)
		documentSenders.put(EditAutoPayment.class,                          AnalyzeEditCardPaymentSystemPaymentLongOfferSender.class);      //заявка на редактирование бил. автоплатежа (iqwave)

		//кредиты
		documentSenders.put(ETSMLoanClaim.class,                            AnalyzeETSMLoanClaimSender.class);                              //заявка на создание кредита
		documentSenders.put(LoanTransfer.class,                             AnalyzeLoanPaymentSender.class);                                //заявка на оплату кредита
		documentSenders.put(EarlyLoanRepaymentClaim.class,                      AnalyzeLoanPaymentSender.class);                                //заявка на оплату кредита
		//карты
		documentSenders.put(LoanCardProductClaim.class,                     AnalyzeIssueCardClaimSender.class);                             //заявка на создание карты (кредитная)
		documentSenders.put(VirtualCardClaim.class,                         AnalyzeIssueCardClaimSender.class);                             //заявка на создание карты (виртуальная)
		documentSenders.put(PreApprovedLoanCardClaim.class,                 AnalyzeIssueCardClaimSender.class);                             //заявка на создание карты (пред одобренную кредитную карту)
		documentSenders.put(LoanCardOfferClaim.class,                       AnalyzeIssueCardClaimSender.class);
		documentSenders.put(LoanCardClaim.class,                            AnalyzeIssueCardClaimSender.class);
		documentSenders.put(LoanProductClaim.class,                         AnalyzeLoanProductClaimSender.class);
		documentSenders.put(RemoteConnectionUDBOClaim.class,                AnalyzeMakeUDBOClaimSender.class);                              //заявка на подключение УДБО

		//сендеры по шаблону документа
		//шаблонов переводов между моими счетами/картами/мет. счетами
		templateSenders.put(InternalCardsTransfer.class,                    AnalyzeCardsTransferTemplateSender.class);                      //перевод между моими картами
		templateSenders.put(CardToAccountTransfer.class,                    AnalyzeCardToAccountTransferTemplateSender.class);              //перевод с карты на счет
		templateSenders.put(AccountToCardTransfer.class,                    AnalyzeAccountToCardTransferTemplateSender.class);              //перевод со счета на карту
		templateSenders.put(ClientAccountsTransfer.class,                   AnalyzeClientAccountsTransferTemplateSender.class);             //перевод между моими счетами
		templateSenders.put(IMAToAccountTransfer.class,                     AnalyzeClientAccountsTransferTemplateSender.class);             //перевод с металического счета на счет
		templateSenders.put(AccountToIMATransfer.class,                     AnalyzeClientAccountsTransferTemplateSender.class);             //перевод со счета на мет. счет
		templateSenders.put(IMAToCardTransfer.class,                        AnalyzeAccountToCardTransferTemplateSender.class);              //перевод с металического счета на карту
		templateSenders.put(CardToIMATransfer.class,                        AnalyzeCardToAccountTransferTemplateSender.class);              //перево с карты на мет. счет
		//шаблонов на погашение кредита
		templateSenders.put(LoanTransfer.class,                             AnalyzeLoanTemplateSender.class);                               //заявка на оплату кредита
		//шаблонов частному лицу
		templateSenders.put(ExternalCardsTransferToOurBank.class,           AnalyzeExternalCardsTransferToOurBankTemplateSender.class);     //перевод внутрибанковский
		templateSenders.put(ExternalCardsTransferToOtherBank.class,         AnalyzeExternalCardsTransferToOtherBankTemplateSender.class);   //перевод внешнебанковский
		templateSenders.put(CardRUSPayment.class,                           AnalyzeToAccountRurPaymentTemplateSender.class);                //с карты на счет в другой банк через платежную систему России
		templateSenders.put(CardIntraBankPayment.class,                     AnalyzeToAccountRurPaymentTemplateSender.class);                //с карты на счет внутри банка
		templateSenders.put(AccountIntraBankPayment.class,                  AnalyzeToAccountRurPaymentTemplateSender.class);                //с вклада на счет внутри банка
		templateSenders.put(AccountRUSPayment.class,                        AnalyzeToAccountRurPaymentTemplateSender.class);                //с вклада на счет в другой банк через платежную систему России
		//шаблонов оплаты услуг
		templateSenders.put(CardPaymentSystemPayment.class,                 AnalyzeCardPaymentSystemTransferTemplateSender.class);          //биллинговая оплата

		//сендеры по событию
		eventSenders.put(EventsType.VIEW_STATEMENT,                         AnalyzeViewDocumentSender.class);
		eventSenders.put(EventsType.UPDATE_USER,                            null);
		eventSenders.put(EventsType.CHANGE_LOGIN_ID,                        AnalyzeChangeLoginSender.class);
		eventSenders.put(EventsType.CHANGE_PASSWORD,                        AnalyzeChangePasswordSender.class);
		eventSenders.put(EventsType.CHANGE_ALERT_SETTINGS,                  AnalyzeChangeNotificationSettingsSender.class);
		eventSenders.put(EventsType.SESSION_SIGNIN,                         AnalyzeClientLogInSender.class);
		eventSenders.put(EventsType.REQUEST_NEW_CARD,                       AnalyzeEventIssueCardClaimSender.class);
		eventSenders.put(EventsType.REQUEST_CREDIT,                             AnalyzeEventCreditRequestSender.class);
	}

	private FraudMonitoringSendersFactory()
	{}

	/**
	 * @return фабрика
	 */
	public static FraudMonitoringSendersFactory getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Сендер во Фрод-мониторинг по документу
	 * @param document документ
	 * @return сендер
	 */
	public FraudMonitoringSender getSender(BusinessDocument document)
	{
		if (!RSAConfig.getInstance().isSystemActive())
		{
			//проверка во ФМ выключена
			return new NullFraudMonitoringSender();
		}

		if (!isApplicationSupported())
		{
			//неподдерживаемый тип приложения
			return new NullFraudMonitoringSender();
		}

		try
		{
			Class<? extends FraudMonitoringSender> senderClass = documentSenders.get(document.getType());
			if (senderClass == null)
			{
				return new NullFraudMonitoringSender();
			}

			return senderClass.getConstructor(document.getClass()).newInstance(document);
		}
		catch (Exception e)
		{
			log.error(e);
			return new NullFraudMonitoringSender();
		}
	}

	/**
	 * Сендер во Фрод-мониторинг по шаблону документа
	 * @param template шаблон документа
	 * @return сендер
	 */
	public FraudMonitoringSender getSender(TemplateDocument template)
	{
		if (!RSAConfig.getInstance().isSystemActive())
		{
			//проверка во ФМ выключена
			return new NullFraudMonitoringSender();
		}

		if (!isApplicationSupported())
		{
			//неподдерживаемый тип приложения
			return new NullFraudMonitoringSender();
		}

		try
		{
			Class<? extends FraudMonitoringSender> senderClass = templateSenders.get(template.getType());
			if (senderClass == null)
			{
				return new NullFraudMonitoringSender();
			}

			return senderClass.getConstructor(template.getClass()).newInstance(template);
		}
		catch (Exception e)
		{
			log.error(e);
			return new NullFraudMonitoringSender();
		}
	}

	/**
	 * Сендер во Фрод-мониторинг по событию
	 * @param event событие
	 * @return сендер
	 */
	public FraudMonitoringSender getSender(EventsType event)
	{
		if (!RSAConfig.getInstance().isSystemActive())
		{
			//проверка во ФМ выключена
			return new NullFraudMonitoringSender();
		}

		if (!isApplicationSupported())
		{
			//неподдерживаемый тип приложения
			return new NullFraudMonitoringSender();
		}

		try
		{
			Class<? extends FraudMonitoringSender> senderClass = eventSenders.get(event);
			if (senderClass == null)
			{
				return new NullFraudMonitoringSender();
			}

			FraudMonitoringSender sender = senderClass.newInstance();
			return sender;
		}
		catch (Exception e)
		{
			log.error(e);
			return new NullFraudMonitoringSender();
		}
	}

	private boolean isApplicationSupported()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		if (applicationInfo.isMobileApi())
		{
			return MobileApiUtil.getApiVersionNumber().ge(new VersionNumber(9, 1));
		}

		return applicationInfo.isPhizIC() || applicationInfo.isCSA();
	}
}

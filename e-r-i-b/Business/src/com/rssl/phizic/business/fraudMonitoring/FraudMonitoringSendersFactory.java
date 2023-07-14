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
 * ������� �������� �� ����-����������
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


	//������� �� ���������
	private static final Map<Class<? extends GateDocument>, Class<? extends FraudMonitoringSender>> documentSenders
					= new HashMap<Class<? extends GateDocument>, Class<? extends FraudMonitoringSender>>();

	//������� �� ������� ���������
	private static final Map<Class<? extends GateDocument>, Class<? extends FraudMonitoringSender>> templateSenders
					= new HashMap<Class<? extends GateDocument>, Class<? extends FraudMonitoringSender>>();

	//������� �� �������
	private static final Map<EventsType, Class<? extends FraudMonitoringSender>> eventSenders
					= new HashMap<EventsType, Class<? extends FraudMonitoringSender>>();

	static
	{
		//������� �� ���������
		//�������� ����� ����� �������/�������/���. �������
		documentSenders.put(InternalCardsTransfer.class,                    AnalyzeCardsTransferDocumentSender.class);                      //������� ����� ����� �������
		documentSenders.put(CardToAccountTransfer.class,                    AnalyzeCardToAccountTransferDocumentSender.class);              //������� � ����� �� ����
		documentSenders.put(AccountToCardTransfer.class,                    AnalyzeAccountToCardTransferDocumentSender.class);              //������� �� ����� �� �����
		documentSenders.put(ClientAccountsTransfer.class,                   AnalyzeClientAccountsTransferDocumentSender.class);             //������� ����� ����� �������
		documentSenders.put(IMAToAccountTransfer.class,                     AnalyzeClientAccountsTransferDocumentSender.class);             //������� � ������������� ����� �� ����
		documentSenders.put(AccountToIMATransfer.class,                     AnalyzeClientAccountsTransferDocumentSender.class);             //������� �� ����� �� ���. ����
		documentSenders.put(IMAToCardTransfer.class,                        AnalyzeAccountToCardTransferDocumentSender.class);              //������� � ������������� ����� �� �����
		documentSenders.put(CardToIMATransfer.class,                        AnalyzeCardToAccountTransferDocumentSender.class);              //������ � ����� �� ���. ����
		//�������� �������� ����
		documentSenders.put(ExternalCardsTransferToOurBank.class,           AnalyzeExternalCardsTransferToOurBankDocumentSender.class);     //������� ����������������
		documentSenders.put(ExternalCardsTransferToOtherBank.class,         AnalyzeExternalCardsTransferToOtherBankDocumentSender.class);   //������� ����������������
		documentSenders.put(CardRUSPayment.class,                           AnalyzeToAccountRurPaymentDocumentSender.class);                //� ����� �� ���� � ������ ���� ����� ��������� ������� ������
		documentSenders.put(CardIntraBankPayment.class,                     AnalyzeToAccountRurPaymentDocumentSender.class);                //� ����� �� ���� ������ �����
		documentSenders.put(AccountIntraBankPayment.class,                  AnalyzeToAccountRurPaymentDocumentSender.class);                //� ������ �� ���� ������ �����
		documentSenders.put(AccountRUSPayment.class,                        AnalyzeToAccountRurPaymentDocumentSender.class);                //� ������ �� ���� � ������ ���� ����� ��������� ������� ������
		//����������� ��������
		documentSenders.put(CardPaymentSystemPayment.class,                 AnalyzeCardPaymentSystemTransferDocumentSender.class);          //����������� ������
		//������� �����
		documentSenders.put(AccountClosingPaymentToCard.class,              AnalyzeAccountClosingClaimSender.class);                        //�������� ������ � ��������� �������� ������� �� �����
		documentSenders.put(AccountClosingPaymentToAccount.class,           AnalyzeAccountClosingClaimSender.class);                        //�������� ������ � ��������� �������� ������� �� ����

		//�����������
		//�������� �����������
		documentSenders.put(InternalCardsTransferLongOffer.class,           AnalyzeCreateP2PAutoTransferClaimSender.class);                 //������ �� �������� ������������ ����� ������ �������
		documentSenders.put(ExternalCardsTransferToOurBankLongOffer.class,  AnalyzeCreateP2PAutoTransferClaimSender.class);                 //������ �� �������� ������������ �� ������� �����
		documentSenders.put(ExternalCardsTransferToOtherBankLongOffer.class,  AnalyzeCreateP2PAutoTransferClaimSender.class);               //������ �� �������� ������������ �� ������� ����� � ������ ��
		documentSenders.put(CreateCardToAccountLongOffer.class,             AnalyzeCreateMoneyBoxPaymentSender.class);                      //������ �� �������� �������
		documentSenders.put(CardPaymentSystemPaymentLongOffer.class,        AnalyzeCreateCardPaymentSystemPaymentLongOfferSender.class);    //������ �� �������� ���. ����������� (����)
		documentSenders.put(CreateAutoPayment.class,                        AnalyzeCreateCardPaymentSystemPaymentLongOfferSender.class);    //������ �� �������� ���. ����������� (iqwave)
		//�������������� �����������
		documentSenders.put(EditInternalP2PAutoTransfer.class,              AnalyzeEditP2PAutoTransferClaimSender.class);                   //������ �� �������������� ������������ ����� ������ �������
		documentSenders.put(EditExternalP2PAutoTransfer.class,              AnalyzeEditP2PAutoTransferClaimSender.class);                   //������ �� �������������� ������������ �� ������� �����
		documentSenders.put(EditCardToAccountLongOffer.class,               AnalyzeEditMoneyBoxPaymentSender.class);                        //������ �� �������������� �������
		documentSenders.put(EditCardPaymentSystemPaymentLongOffer.class,    AnalyzeEditCardPaymentSystemPaymentLongOfferSender.class);      //������ �� �������������� ���. ����������� (����)
		documentSenders.put(EditAutoPayment.class,                          AnalyzeEditCardPaymentSystemPaymentLongOfferSender.class);      //������ �� �������������� ���. ����������� (iqwave)

		//�������
		documentSenders.put(ETSMLoanClaim.class,                            AnalyzeETSMLoanClaimSender.class);                              //������ �� �������� �������
		documentSenders.put(LoanTransfer.class,                             AnalyzeLoanPaymentSender.class);                                //������ �� ������ �������
		documentSenders.put(EarlyLoanRepaymentClaim.class,                      AnalyzeLoanPaymentSender.class);                                //������ �� ������ �������
		//�����
		documentSenders.put(LoanCardProductClaim.class,                     AnalyzeIssueCardClaimSender.class);                             //������ �� �������� ����� (���������)
		documentSenders.put(VirtualCardClaim.class,                         AnalyzeIssueCardClaimSender.class);                             //������ �� �������� ����� (�����������)
		documentSenders.put(PreApprovedLoanCardClaim.class,                 AnalyzeIssueCardClaimSender.class);                             //������ �� �������� ����� (���� ���������� ��������� �����)
		documentSenders.put(LoanCardOfferClaim.class,                       AnalyzeIssueCardClaimSender.class);
		documentSenders.put(LoanCardClaim.class,                            AnalyzeIssueCardClaimSender.class);
		documentSenders.put(LoanProductClaim.class,                         AnalyzeLoanProductClaimSender.class);
		documentSenders.put(RemoteConnectionUDBOClaim.class,                AnalyzeMakeUDBOClaimSender.class);                              //������ �� ����������� ����

		//������� �� ������� ���������
		//�������� ��������� ����� ����� �������/�������/���. �������
		templateSenders.put(InternalCardsTransfer.class,                    AnalyzeCardsTransferTemplateSender.class);                      //������� ����� ����� �������
		templateSenders.put(CardToAccountTransfer.class,                    AnalyzeCardToAccountTransferTemplateSender.class);              //������� � ����� �� ����
		templateSenders.put(AccountToCardTransfer.class,                    AnalyzeAccountToCardTransferTemplateSender.class);              //������� �� ����� �� �����
		templateSenders.put(ClientAccountsTransfer.class,                   AnalyzeClientAccountsTransferTemplateSender.class);             //������� ����� ����� �������
		templateSenders.put(IMAToAccountTransfer.class,                     AnalyzeClientAccountsTransferTemplateSender.class);             //������� � ������������� ����� �� ����
		templateSenders.put(AccountToIMATransfer.class,                     AnalyzeClientAccountsTransferTemplateSender.class);             //������� �� ����� �� ���. ����
		templateSenders.put(IMAToCardTransfer.class,                        AnalyzeAccountToCardTransferTemplateSender.class);              //������� � ������������� ����� �� �����
		templateSenders.put(CardToIMATransfer.class,                        AnalyzeCardToAccountTransferTemplateSender.class);              //������ � ����� �� ���. ����
		//�������� �� ��������� �������
		templateSenders.put(LoanTransfer.class,                             AnalyzeLoanTemplateSender.class);                               //������ �� ������ �������
		//�������� �������� ����
		templateSenders.put(ExternalCardsTransferToOurBank.class,           AnalyzeExternalCardsTransferToOurBankTemplateSender.class);     //������� ����������������
		templateSenders.put(ExternalCardsTransferToOtherBank.class,         AnalyzeExternalCardsTransferToOtherBankTemplateSender.class);   //������� ����������������
		templateSenders.put(CardRUSPayment.class,                           AnalyzeToAccountRurPaymentTemplateSender.class);                //� ����� �� ���� � ������ ���� ����� ��������� ������� ������
		templateSenders.put(CardIntraBankPayment.class,                     AnalyzeToAccountRurPaymentTemplateSender.class);                //� ����� �� ���� ������ �����
		templateSenders.put(AccountIntraBankPayment.class,                  AnalyzeToAccountRurPaymentTemplateSender.class);                //� ������ �� ���� ������ �����
		templateSenders.put(AccountRUSPayment.class,                        AnalyzeToAccountRurPaymentTemplateSender.class);                //� ������ �� ���� � ������ ���� ����� ��������� ������� ������
		//�������� ������ �����
		templateSenders.put(CardPaymentSystemPayment.class,                 AnalyzeCardPaymentSystemTransferTemplateSender.class);          //����������� ������

		//������� �� �������
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
	 * @return �������
	 */
	public static FraudMonitoringSendersFactory getInstance()
	{
		return INSTANCE;
	}

	/**
	 * ������ �� ����-���������� �� ���������
	 * @param document ��������
	 * @return ������
	 */
	public FraudMonitoringSender getSender(BusinessDocument document)
	{
		if (!RSAConfig.getInstance().isSystemActive())
		{
			//�������� �� �� ���������
			return new NullFraudMonitoringSender();
		}

		if (!isApplicationSupported())
		{
			//���������������� ��� ����������
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
	 * ������ �� ����-���������� �� ������� ���������
	 * @param template ������ ���������
	 * @return ������
	 */
	public FraudMonitoringSender getSender(TemplateDocument template)
	{
		if (!RSAConfig.getInstance().isSystemActive())
		{
			//�������� �� �� ���������
			return new NullFraudMonitoringSender();
		}

		if (!isApplicationSupported())
		{
			//���������������� ��� ����������
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
	 * ������ �� ����-���������� �� �������
	 * @param event �������
	 * @return ������
	 */
	public FraudMonitoringSender getSender(EventsType event)
	{
		if (!RSAConfig.getInstance().isSystemActive())
		{
			//�������� �� �� ���������
			return new NullFraudMonitoringSender();
		}

		if (!isApplicationSupported())
		{
			//���������������� ��� ����������
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

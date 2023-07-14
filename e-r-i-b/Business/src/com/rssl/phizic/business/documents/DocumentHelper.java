package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.common.forms.types.SubType;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeService;
import com.rssl.phizic.business.clientPromoCodes.CloseReason;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.dictionaries.billing.TemplateState;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.dictionaries.providers.wrappers.ServiceProviderShortcut;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.payments.*;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.ext.sbrf.commissions.CommissionTBSettingService;
import com.rssl.phizic.business.ext.sbrf.commissions.CommissionsTBSetting;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.business.extendedattributes.AttributableBase;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.limits.GroupRiskRank;
import com.rssl.phizic.business.limits.GroupRiskService;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.business.shop.ExternalPaymentService;
import com.rssl.phizic.business.shop.Order;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.AdditionalCardType;
import com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.documents.AbstractCardTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.autopayment.CreateAutoPayment;
import com.rssl.phizic.gate.payments.autopayment.RefuseAutoPayment;
import com.rssl.phizic.gate.payments.autosubscriptions.*;
import com.rssl.phizic.gate.payments.basket.CloseInvoiceSubscription;
import com.rssl.phizic.gate.payments.basket.DelayInvoiceSubscription;
import com.rssl.phizic.gate.payments.basket.EditInvoiceSubscription;
import com.rssl.phizic.gate.payments.basket.RecoveryInvoiceSubscription;
import com.rssl.phizic.gate.payments.longoffer.CloseCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.DelayCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.EmployeeCloseCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.EmployeeDelayCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.permissions.ServicePermission;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.*;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;
import com.rssl.phizicgate.manager.services.IDHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rssl.phizic.business.ext.sbrf.payments.PaymentsSystemNameConstants.SYSTEM_NAME_UEC;

/**
 * ������ ��� ������ � �����������
 *
 * @author khudyakov
 * @ created 20.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class DocumentHelper
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public static final double TEMPLATE_FACTOR_DEFAULT_VALUE            = 1;

	private static final String DEFINE_RATE_ERROR_MESSAGE               = "������ ����������� ����� ������";

	private static final Pattern TIME_PATTERN = Pattern.compile("\\d{2}:\\d{2}"); // ��. DateHelper#TIME_PATTERN
	private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{2}:\\d{2} \\d{2}\\.\\d{2}"); // ��. DateHelper#DATE_TIME_PATTERN

	private static final GroupRiskService  groupRiskService  = new GroupRiskService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final ServiceProviderService  serviceProviderService  = new ServiceProviderService();
	private static final ServiceService serviceService  = new ServiceService();
	private static final BillingService billingService = new BillingService();

	//������� ��������, ��� ������� ��������� ������ ����
	private static final Set<String> allowedTemplatePaymentStates = new HashSet<String>();
	private static final Set<String> EXTERNAL_PAYMENTS_FORM_NAMES = new HashSet<String>();
	// �������� ����, ��� ������� �� ��������� �������� �����
	private static final Set<String>  disallowedCopyFormNames = new HashSet<String>();

	private static final Map<String, String> paymentStateDescriptions = new HashMap<String, String>();

	private static final MobilePlatformService mobilePlatformService = new MobilePlatformService();

	private static final ClientPromoCodeService clientPromoCodesService = new ClientPromoCodeService();

	//��� �� ������������ � iqwave
	private static final String CODE_AEROEXPRESS = "470";

	static
	{
		disallowedCopyFormNames.add(FormConstants.REISSUE_CARD_CLAIM);
		disallowedCopyFormNames.add(FormConstants.REMOTE_CONNECTION_UDBO_CLAIM_FORM);
		disallowedCopyFormNames.add(FormConstants.REFUND_GOODS_FORM);
		disallowedCopyFormNames.add(FormConstants.RECALL_ORDER_FORM);
		disallowedCopyFormNames.add(FormConstants.CREATE_AUTOPAYMENT_FORM);
		disallowedCopyFormNames.add(FormConstants.EDIT_AUTOPAYMENT_FORM);
		disallowedCopyFormNames.add(FormConstants.REFUSE_AUTOPAYMENT_FORM);
		disallowedCopyFormNames.add(FormConstants.EDIT_AUTOSUBSCRIPTION_PAYMENT);
		disallowedCopyFormNames.add(FormConstants.REFUSE_AUTOSUBSCRIPTION_PAYMENT_FORM);
		disallowedCopyFormNames.add(FormConstants.DELAY_AUTOSUBSCRIPTION_PAYMENT_FORM);
		disallowedCopyFormNames.add(FormConstants.CREATE_MONEY_BOX_FORM);
		disallowedCopyFormNames.add(FormConstants.EDIT_MONEY_BOX_FORM);
		disallowedCopyFormNames.add(FormConstants.RECOVERY_P2P_AUTO_TRANSFER_CLAIM_FORM);
		disallowedCopyFormNames.add(FormConstants.DELAY_P2P_AUTO_TRANSFER_CLAIM_FORM);
		disallowedCopyFormNames.add(FormConstants.CLOSE_P2P_AUTO_TRANSFER_CLAIM_FORM);
		disallowedCopyFormNames.add(FormConstants.CREATE_P2P_AUTO_TRANSFER_CLAIM_FORM);
		disallowedCopyFormNames.add(FormConstants.EDIT_P2P_AUTO_TRANSFER_CLAIM_FORM);
		disallowedCopyFormNames.add(FormConstants.RECOVERY_MONEY_BOX_FORM);
		disallowedCopyFormNames.add(FormConstants.CLOSE_MONEY_BOX_FORM);
		disallowedCopyFormNames.add(FormConstants.REFUSE_MONEY_BOX_FORM);
		disallowedCopyFormNames.add(FormConstants.CHANGE_CREDIT_LIMIT_CLAIM);
		disallowedCopyFormNames.add(FormConstants.IMA_OPENING_CLAIM);
		disallowedCopyFormNames.add(FormConstants.ACCOUNT_OPENING_CLAIM_FORM);
		disallowedCopyFormNames.add(FormConstants.ACCOUNT_OPENING_CLAIM_WITH_CLOSE_FORM);
		disallowedCopyFormNames.add(FormConstants.LOAN_CARD_OFFER_FORM);

		allowedTemplatePaymentStates.add("DELAYED_DISPATCH");
		allowedTemplatePaymentStates.add("DISPATCHED");
		allowedTemplatePaymentStates.add("EXECUTED");
		allowedTemplatePaymentStates.add("WAIT_CONFIRM");

		paymentStateDescriptions.put("SAVED",                           "��������");
		paymentStateDescriptions.put("INITIAL",                         "��������");
		paymentStateDescriptions.put("DELAYED_DISPATCH",                "��������� ���������");
		paymentStateDescriptions.put("DISPATCHED",                      "����������� ������");
		paymentStateDescriptions.put("SEND",                            "����������� ������");
		paymentStateDescriptions.put("EXECUTED",                        "��������");
		paymentStateDescriptions.put("TICKETS_WAITING",                 "��������");
		paymentStateDescriptions.put("REFUSED",                         "��������� ������");
		paymentStateDescriptions.put("RECALLED",                        "������ ���� ��������");
		paymentStateDescriptions.put("ERROR",                           "����������� ������");
		paymentStateDescriptions.put("PARTLY_EXECUTED",                 "����������� ������");
		paymentStateDescriptions.put("UNKNOW",                          "����������� ������");
		paymentStateDescriptions.put("SENT",                            "����������� ������");
		paymentStateDescriptions.put("WAIT_CONFIRM",                    "����������� � ���������� ������");
		paymentStateDescriptions.put("BILLING_CONFIRM_TIMEOUT",         "����������� ������");
		paymentStateDescriptions.put("BILLING_GATE_CONFIRM_TIMEOUT",    "����������� ������");
		paymentStateDescriptions.put("ABS_RECALL_TIMEOUT",              "����������� ������");
		paymentStateDescriptions.put("ABS_GATE_RECALL_TIMEOUT",         "����������� ������");
		paymentStateDescriptions.put("OFFLINE_DELAYED",                 "��������� ���������");
		paymentStateDescriptions.put("OFFLINE_SAVED",                   "��������");

		EXTERNAL_PAYMENTS_FORM_NAMES.add("ExternalProviderPayment");
		EXTERNAL_PAYMENTS_FORM_NAMES.add("AirlineReservationPayment");
	}

	/**
	 * �������� �������� ������� ���������
	 * @param stateCode ��� �������
	 * @return �������� ������� ���������
	 */
	public static String getStateDescription(String stateCode)
	{
		return paymentStateDescriptions.get(stateCode);
	}

	/**
	 * �������� �� ������ ������ ������������/���������� ����������
	 *
	 * @param document ��������
	 * @return true - ��������
	 */
	public static boolean isLongOffer(BusinessDocument document)
	{
		if (!(document instanceof GateExecutableDocument))
			return false;

		GateExecutableDocument gateDocument = (GateExecutableDocument) document;
		return LongOffer.class.isAssignableFrom(gateDocument.getType());
	}

	/**
	 * ���������, ��� ������ ��� ������������ - ��� �������� ��� ��������
	 * (�������� � ����������/��������������� �����������, �� �������� ��������, ���������������, �������������)
	 * @param document ��������
	 * @return true - �������� ��������
	 */
	public static boolean isActiveLongOffer(BusinessDocument document)
	{
		//�������� �� �� ���, ������ �������� ������������, ������ ���� ����

		if (!(document instanceof GateExecutableDocument))
			return false;

		GateExecutableDocument gateDocument = (GateExecutableDocument) document;
		Class<? extends GateDocument> type  = gateDocument.getType();

		//������ �������� �� �������� ���������
		if (RefuseAutoPayment.class == type || RefuseLongOffer.class == type
				|| CloseCardPaymentSystemPaymentLongOffer.class == type || EmployeeCloseCardPaymentSystemPaymentLongOffer.class == type
					|| DelayCardPaymentSystemPaymentLongOffer.class == type || EmployeeDelayCardPaymentSystemPaymentLongOffer.class == type
						|| CloseCardToCardLongOffer.class == type || DelayCardToCardLongOffer.class == type || RecoveryCardToCardLongOffer.class == type)
			return false;

		return true;
	}

	/**
	 * ����� �� ������������ ������ ��� ������������
	 * @param document ��������
	 * @return true - �����
	 */
	public static boolean needUseLongOffer(BusinessDocument document)
	{
		//�������� �� �� ���, ������ �������� ������������, ������ ���� ����
		//����������� �� ���������� ����� �� ���������
		if (TypeOfPayment.INTERNAL_PAYMENT_OPERATION == document.getTypeOfPayment())
		{
			return false;
		}

		//��������� ������ �������� �����������
		return DocumentHelper.isActiveLongOffer(document);
	}

	/**
	 * �������� �� �������� ������� ����� ������� ��������
	 * @param document - ��������
	 * @return true/false
	 */
	public static boolean isInvoicePayment(BusinessDocument document)
	{
		return document instanceof InvoicePayment;
	}

	/**
	 * �� �������� �������������� �������������
	 * ��� �������� ���� ��������� ������������� �������� �� �������� ���-�������
	 * @param office - ����
	 * @return true-���������� ������������/false - ������ ������� ������������� �������� �� ��������
	 */
	public static Boolean getTemplateConfirmSetting(Office office) throws BusinessException
	{
		try
		{
			String templateConfirmSetting = ConfigFactory.getConfig(DocumentConfig.class).getTemplateConfirmSetting(new SBRFOfficeCodeAdapter(office.getCode()).getRegion());
			if (templateConfirmSetting != null)
				return Boolean.valueOf(templateConfirmSetting);
			//�������� �� ���������
			return false;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}


	/**
	 * �� �������� �������������� �������������
	 * ��� �������� ���� ��������� ��������� ����� ��� ��������
	 * @param office - ������������� ������������� �� ���������
	 * @return ����������� ��������� ���������� ����� ������� �� ������� �������
	 */
	public static double getTemplateFactor(Office office) throws BusinessException
	{
		try
		{
			String templateFactor = ConfigFactory.getConfig(DocumentConfig.class).getTemplateFactor(new SBRFOfficeCodeAdapter(office.getCode()).getRegion());
			if (StringHelper.isNotEmpty(templateFactor))
			{
				return Double.valueOf(templateFactor);
			}
			//�������� �� ���������
			return DocumentHelper.TEMPLATE_FACTOR_DEFAULT_VALUE;
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �� �������� �������������� ������������� ��� �������� ���� ��������� ��������� ��������� ����� � PRO-����
	 * @param departmentId - ������������� ������������� �� ���������
	 * @return true - ���������� ���������
	 */
	public static Boolean getUseTemplateFactorInFullMAPI(Long departmentId) throws BusinessException
	{
		try
		{
			Department terbank = departmentService.getTB(departmentId);
			String useTemplateFactorInFullMAPI = ConfigFactory.getConfig(DocumentConfig.class).getTemplateFactorInFullMAPI(terbank.getId());
			if (useTemplateFactorInFullMAPI != null)
				return Boolean.valueOf(useTemplateFactorInFullMAPI);
			//�������� �� ���������
			return true;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� �������� �� ������ � ������ ���������� � ������� ����������
	 * (���������� �������� ����������� �� �������� ������� � ��������)
	 * @param document - ��������
	 * @return true - ��������
	 */
	public static boolean checkApplicationRestriction(BusinessDocument document)
	{
		return document.checkApplicationRestriction();
	}

	/**
	 * ��������, �������� �� �������� �������� �� ������ ����� ������������.
	 * ��������� �������� �� ����, ������������ � ������ �� ������ ���������� � �����.
	 * @param document - ������
	 * @return true - ������ ���������
	 */
	public static boolean isAirlineReservationPayment(AbstractPaymentDocument document) throws DocumentException
	{
		if (!(document instanceof AbstractPaymentSystemPayment))
			return false;

		try
		{
			AbstractPaymentSystemPayment payment = (AbstractPaymentSystemPayment)document;
			if (ESBHelper.isIQWavePayment(payment))
			{
				return (BillingPaymentHelper.getFieldById(payment.getExtendedFields(), "AirLineIdentifyField") != null);
			}
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		return false;
	}

	/**
	 * ������� ������� � ������ UNKNOW
	 * @param executor - StateMachineExecutor
	 * @param type - ��� �������
	 * @param e - ���������� �� ���-������� ����������, ���������� ������ ������ 
	 */
	public static void fireDounknowEvent(StateMachineExecutor executor, String type, BusinessTimeOutException e) throws BusinessException, BusinessLogicException
	{
		Throwable throwable = e.getCause();
		if (throwable.getCause() instanceof GateWrapperTimeOutException)
			executor.fireEvent(new ObjectEvent(DocumentEvent.DOUNKNOW, type, GateWrapperTimeOutException.TIMEOUT_WRAPPER_DOCUMENT_STATE_DESCRIPTION));
		else
			executor.fireEvent(new ObjectEvent(DocumentEvent.DOUNKNOW, type));
	}

	/**
	 * ��������� ������� ��������� �������� � ������� ������
	 * @param executor - ������� �������� ����������� ����� ������
	 * @param event - ������� ���������� �������� ���������� ���������
	 * @param document - ��������
	 * @param message - ��������� � ��������� ������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static void repeatOfflineDocument(StateMachineExecutor executor, ObjectEvent event, BusinessDocument document, String message) throws BusinessException, BusinessLogicException
	{
		Matcher timeMatcher = TIME_PATTERN.matcher(message);
		Matcher dateTimeMatcher = DATE_TIME_PATTERN.matcher(message);
		String timeString = null;
		boolean withDate = false;
		if (dateTimeMatcher.find())
		{
			timeString = dateTimeMatcher.group();
			withDate = true;
		}
		else if (timeMatcher.find())
			timeString = timeMatcher.group();

		if (StringHelper.isNotEmpty(timeString))
		{
			try
			{
				Calendar offlineToDate = Calendar.getInstance();
				Calendar toDate = Calendar.getInstance();
				toDate.setTime(withDate ? new SimpleDateFormat(DateHelper.DATE_TIME_PATTERN).parse(timeString) :
						new SimpleDateFormat(DateHelper.TIME_PATTERN).parse(timeString));
				offlineToDate.set(Calendar.HOUR_OF_DAY, toDate.get(Calendar.HOUR_OF_DAY));
				offlineToDate.set(Calendar.MINUTE, toDate.get(Calendar.MINUTE));
				if (withDate)
				{
					offlineToDate.set(Calendar.MONTH, toDate.get(Calendar.MONTH));
					offlineToDate.set(Calendar.DAY_OF_MONTH, toDate.get(Calendar.DAY_OF_MONTH));
				}
				if (offlineToDate.before(Calendar.getInstance()))
				{
					offlineToDate.add(Calendar.DAY_OF_MONTH, 1);
				}
				((AttributableBase) document).setAttributeValue(ExtendedAttribute.DATE_TIME_TYPE,
						BusinessDocumentBase.OFFLINE_DELAYED_TO_DATE_ATTRIBUTE_NAME, offlineToDate);
			}
			catch (ParseException ignored)
			{
			}
		}
		((AttributableBase) document).setAttributeValue(ExtendedAttribute.BOOLEAN_TYPE, BusinessDocumentBase.OFFLINE_DELAYED_ATTRIBUTE_NAME, true);
		executor.fireEvent(event);
	}

	/**
	 * @return ��������� �� ������ �� ����� ���. ����� 
	 */
	public static boolean isExternalPhizAccountPaymentsAllowed()
	{
		return AuthModule.getAuthModule().implies(new ServicePermission("ExternalPhizAccountPayment"));
	}

	/**
	 * @return ��������� �� ������ ����� ��. �����
	 */
	public static boolean isExternalJurAccountPaymentsAllowed()
	{
		return AuthModule.getAuthModule().implies(new ServicePermission("ExternalJurAccountPayment"));
	}

	public static Money moneyConvert(Money money, Currency currencyTo, Office office, String tarifPlanCode) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (money.getCurrency().compare(currencyTo))
			{
				return money;
			}

			CurrencyRateService currencyRateService = GateSingleton.getFactory().service(CurrencyRateService.class);

			//���� ���� �� �� ������� � ��������� �����
			CurrencyRate rate = currencyRateService.convert(money, currencyTo, office, tarifPlanCode);
			if (rate != null)
			{
				return new Money(rate.getToValue(), currencyTo);
			}

			CurrencyRate cbRate = getDefaultCurrencyRate(money.getCurrency(), currencyTo, tarifPlanCode);
			if (cbRate != null)
			{
				return new Money(CurrencyUtils.getFromCurrencyRate(money.getDecimal(), cbRate).getToValue(), currencyTo);
			}
			throw new BusinessLogicException("�� ������� �������� ����� �����. �������� �������� ����������.");
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(DEFINE_RATE_ERROR_MESSAGE, e);
		}
		catch (GateException e)
		{
			throw new BusinessException(DEFINE_RATE_ERROR_MESSAGE, e);
		}
	}


	/**
	 * ����������� ����� �� ����� ������ � ������ �� ����� �������
	 * @param money - ����� �����������
	 * @param currencyTo ������, � ������� ��������������
	 * @param document - ��������
	 * @return - ����������������� ��������
	 * @throws BusinessException
	 */
	public static Money moneyConvert(Money money, Currency currencyTo, BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		try
		{
			return moneyConvert(money, currencyTo, (Office) document.getDepartment(), document.getTarifPlanCodeType());
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����������� ����� �� ����� ������ � ������ �� ����� �������
	 * @param money - ����� �����������
	 * @param currencyTo ������, � ������� ��������������
	 * @param template ������
	 * @return - ����������������� ��������
	 * @throws BusinessException
	 */
	public static Money moneyConvert(Money money, Currency currencyTo, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		try
		{
			return moneyConvert(money, currencyTo, template.getOffice(), template.getTarifPlanCodeType());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ���� ��� �� �� ����������
	 * @param fromCurrency ������ from
	 * @param toCurrency ������ to
	 * @param tarifPlanCodeType ��� ��������� �����
	 * @return ����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static CurrencyRate getDefaultCurrencyRate(Currency fromCurrency, Currency toCurrency, String tarifPlanCodeType) throws BusinessException, BusinessLogicException
	{
		CurrencyRateService currencyRateService = GateSingleton.getFactory().service(CurrencyRateService.class);

		try
		{
			//���� ���� �� �� �� �. ������ � ��������� �����
			String TBMoscow = ConfigFactory.getConfig(DocumentConfig.class).getCbTbMoscow();
			return currencyRateService.getRate(fromCurrency, toCurrency, CurrencyRateType.CB, departmentService.getDepartmentTBByTBNumber(TBMoscow), tarifPlanCodeType);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * �������� ���������� ����� �������, ���������� �� �������, �� ��������� � ����� �������
	 * @param document ��������/�������
	 * @param template ������
	 * @return true - ����� ���������
	 */
	public static boolean checkPaymentByTemplateSum(BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		if (!(document instanceof AbstractPaymentDocument))
			throw new BusinessException("������������ ��� ���������, �������� AbstractPaymentDocument");

		if (document instanceof InternalTransfer)
			return false;

		AbstractPaymentDocument abstractDocument = (AbstractPaymentDocument) document;

		//� mAPI full-������ ������ "��������" ����� ��������� � ����������� �� ���������
		if (ApplicationUtil.isMobileApi() && MobileApiUtil.isFullScheme()
				&& template.getState().getCode().equals("WAIT_CONFIRM_TEMPLATE")
				&& !getUseTemplateFactorInFullMAPI(((Department) document.getDepartment()).getId()))
			return false;

		Money documentAmount = abstractDocument.getExactAmount();
		Money templateAmount = TemplateHelper.getExactAmount(template);
		if (templateAmount == null)
		{
			templateAmount = new Money(new BigDecimal("1.0"), documentAmount.getCurrency());
		}

		double templateFactor = getTemplateFactor(abstractDocument.getDepartment());
		templateAmount = templateAmount.multiply(templateFactor);
		templateAmount = moneyConvert(templateAmount, documentAmount.getCurrency(), abstractDocument);

		return abstractDocument.getExactAmount().compareTo(templateAmount) > 0;
	}

	/**
	 * @param document ��������
	 * @return true - ��������
	 */
	public static boolean isTemplateSupported(BusinessDocument document)
	{
		if (document == null)
		{
			return false;
		}

		if (document.isByTemplate())
		{
			return false;
		}

		if (!FormType.isTemplateSupported(document.getFormType()))
		{
			return false;
		}

		if (isLongOffer(document))
		{
			return false;
		}

		if (isPaymentDisallowedFromAccount(document))
		{
			return false;
		}

		if (document instanceof JurPayment)
		{
			JurPayment jurPayment = (JurPayment) document;
			try
			{
				ServiceProviderShort provider = getDocumentProvider(jurPayment);
				return !jurPayment.isBarCodeDocument(provider) && isBillingPaymentTemplateSupported(jurPayment, provider);
			}
			catch (BusinessException ignore)
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * @param document ��������
	 * @return true - ��������
	 */
	public static boolean isTemplateATMSupported(BusinessDocument document) throws BusinessException
	{
		boolean templateSupported = isTemplateSupported(document);
		boolean paymentStateSupportTemplate = allowedTemplatePaymentStates.contains(document.getState().getCode());
		boolean editTemplateSupported = PermissionUtil.impliesTemplateOperation(document.getFormName());

		return templateSupported && paymentStateSupportTemplate && editTemplateSupported;
	}

	/**
	 * �������������� �� �������� ������� ��� ���������� �������.
	 * @param document  ��������.
	 * @param provider  ���������.
	 * @return �������������� �� ��������.
	 */
	public static boolean isBillingPaymentTemplateSupported(JurPayment document, ServiceProviderShort provider)
	{
		if (document.getFormType() == FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER)
		{
			return isBillingExternalPaymentTemplateSupported(document);
		}

		if (provider == null)
			return false;

		if (provider != null && !(provider.getKind().equals("B")))
		{
			return false;
		}


		return checkAvailablePayments(provider) && provider.isTemplateSupported();
	}

	/**
	 * �������������� �� ������ � ��������� �� ��������� ����������.
	 * @param document ��������.
	 * @return �������������� �� ������.
	 */
	public static boolean isBillingExternalPaymentTemplateSupported(AbstractPaymentSystemPayment document)
	{
		try
		{
			if (document.getFormType() == FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER && StringHelper.isNotEmpty(document.getBillingCode()))
			{
				//��� ������������ ����������� ��������� ��������� ��������-��������� �� �������� �������.
				Billing billing = billingService.getByCode(document.getBillingCode());
				if (billing == null)
					return true;
				return billing.getTemplateState() != TemplateState.INACTIVE;
			}

			return true;
		}
		catch (BusinessException ignore)
		{
			return false;
		}
	}

	/**
	 * �������� �� ������ ������ �������� ������ �� �������� ��������� ��������
	 * �� �����.
	 * @param document - ������� ������
	 * @return - true/false - ��������/��������
	 */
	public static boolean isPaymentDisallowedFromAccount(BusinessDocument document)
	{
		try
		{
			if (!(document instanceof RurPayment))
				return false;

			RurPayment payment = (RurPayment) document;

			// �������� �� ������� �������� ��������� �� ������ �������� ������
			boolean availableExternalAccountPayment;
			if(payment.getFormName().equals(FormConstants.RUR_PAYMENT_FORM))
			{
				if(ApplicationConfig.getIt().getApplicationInfo().isAdminApplication())
					availableExternalAccountPayment = serviceService.isPersonServices(payment.getInternalOwnerId(), "ExternalPhizAccountPayment");
				else
					availableExternalAccountPayment = DocumentHelper.isExternalPhizAccountPaymentsAllowed();
			}
			else
			{
				if(ApplicationConfig.getIt().getApplicationInfo().isAdminApplication())
					availableExternalAccountPayment = serviceService.isPersonServices(payment.getInternalOwnerId(), "ExternalJurAccountPayment");
				else
					availableExternalAccountPayment = DocumentHelper.isExternalJurAccountPaymentsAllowed();
			}

			//���� ������ �������� �� ������, ������ ��� ����� ������. ��� �������� ������ �����������.
			if (payment.getChargeOffResourceLink() == null)
				return !availableExternalAccountPayment;
			return !availableExternalAccountPayment && (payment.getChargeOffResourceLink() instanceof AccountLink);
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������� �������� ������ �� �������� ��������� �������� ��� ��������� c id = " + document.getId(), e);
			return true;
		}
	}

	/**
	 * ��������� �� ��������� ������������ ������� ��� �������� �������� ������� �� �����, � ������ ���������� ����� ��������.
	 * @param document - ������
	 * @return ����� ��������� � ����������� �� ���� �������� �� �������� ��������, ��� ���������� �������� �� ������ �������.
	 */
	public static String getDisallowedExternalAccountMessage(BusinessDocument document) throws DocumentException
	{
		if (document.isByTemplate() && document.getChargeOffResourceLink() != null)
		{
			return Constants.EXTERNAL_ACCOUNT_PAYMENT_TEMPLATE_ERROR_MESSAGE;
		}

		return Constants.EXTERNAL_ACCOUNT_PAYMENT_ERROR_MESSAGE;
	}

	/**
	 * ���������� ������ �� �������� ������ �� ��������������
	 * @param id - �� ���������
	 * @return ������ �� �������� ������
	 * @throws BusinessException
	 */
	public static AccountOpeningClaim getAccountOpeningClaimById(Long id) throws BusinessException
	{
		return (AccountOpeningClaim)businessDocumentService.findById(id);
	}

	/**
	 * ����������� ��� ������ �� ������� ��� ��������� ����������,
	 * ������ �� ������������ ����������
	 *
	 * @param recipientId ���������� ������������� ���������� �����
	 * @return �����
	 */
	public static String getEditMode(long recipientId) throws BusinessException
	{
		GroupRisk groupRisk = getGroupRisk(recipientId);
		if (groupRisk == null)
		{
			return SubType.STATIC.getValue();
		}
		return GroupRiskRank.HIGH == groupRisk.getRank() ? SubType.STATIC.getValue() :  SubType.DINAMIC.getValue();
	}

	/**
	 * �������� ������ ����� �� ������������� ���������� �����.
	 * @param recipientId ������������ ���������� �����
	 * @return ������ ����� �� ������������ ���������� �����. ���� ��������� �� ����� - null. ���� ��� ��� - ��������� ������ �����.
	 * @throws BusinessException
	 */
	public static GroupRisk getGroupRisk(Long recipientId) throws BusinessException
	{
		if (recipientId == null)
		{
			return null;
		}
		GroupRisk groupRisk = serviceProviderService.findGroupRiskById(recipientId);
		if (groupRisk == null)
		{
			return groupRiskService.getDefaultGroupRisk();
		}
		return groupRisk;
	}
	/**
	 * ��������� �� ������ � ����
	 * (����������� - ��� ���������� ������������ �������� ��� ���������)
	 *
	 * @param document ��������
	 * @return true - ���������
	 */
	public static boolean isFromCardPaymentAllow(BusinessDocument document)
	{
		if (document == null)
		{
			return true;
		}

		GateExecutableDocument gateDocument = (GateExecutableDocument) document;
		if (gateDocument.getType() == null)
		{
			return true;
		}

		return AbstractCardTransfer.class.isAssignableFrom(gateDocument.getType());
	}

	/**
	 * ��������� �� ������ �� �����
	 * (����������� - ��� ���������� ������������ �������� ��� ���������)
	 *
	 * @param document ��������
	 * @return true - ���������
	 */
	public static boolean isFromAccountPaymentAllow(BusinessDocument document)
	{
		if (document == null)
		{
			return true;
		}

		GateExecutableDocument gateDocument = (GateExecutableDocument) document;
		if (gateDocument.getType() == null)
		{
			return true;
		}

		return AbstractAccountTransfer.class.isAssignableFrom(gateDocument.getType());
	}

	/**
	 * �������� ��� ���������� ����� ������� ��� ���� �� ���������
	 * @param document ��������
	 * @param channel �����
	 * @return ���������� ����� �������
	 */
	public static String getPaymentFormInfo(BusinessDocument document, CreationType channel)
	{
		try
		{
			Long providerId = getDocumentProviderId(document);
			if (providerId == null)
			{
				return null;
			}

			return serviceProviderService.getPaymentFormInfo(providerId, channel);
		}
		catch (Exception e)
		{
			log.error("�� ������� �������� ���������� ��� ����� ������� c id = " + document.getId(), e);
			return null;
		}
	}

	/**
	 * �������� ���������� ����� �� ���������
	 * @param document ��������
	 * @return ��������� �����
	 * @throws BusinessException
	 */
	public static ServiceProviderShort getDocumentProvider(BusinessDocument document) throws BusinessException
	{
		Long providerId  = getDocumentProviderId(document);
		if (providerId == null)
			return null;
		return ServiceProviderHelper.getServiceProvider(providerId);
	}

	/**
	 * �������� ������������ ���������� ����� �� ���������
	 * @param document ��������
	 * @return ������������ ���������� �����
	 * @throws BusinessException
	 */
	public static Long getDocumentProviderId(BusinessDocument document) throws BusinessException
	{
		if (document instanceof JurPayment)
		{
			return ((JurPayment) document).getReceiverInternalId();
		}

		if (document instanceof AutoPaymentBase)
		{
			return ((AutoPaymentBase) document).getReceiverInternalId();
		}
		return null;
	}

	/**
	 * ����� ��������/������������� ���������
	 * @return ����� ��������/�������������
	 */
	public static CreationType getChannelType()
	{
		ApplicationConfig config = ApplicationConfig.getIt();
		ApplicationInfo applicationInfo = config.getApplicationInfo();

		if (applicationInfo.isMobileApi())
		{
			return CreationType.mobile;
		}

		if (applicationInfo.isATM())
		{
			return CreationType.atm;
		}

		if (applicationInfo.isSMS())
		{
			return CreationType.sms;
		}

		if (applicationInfo.isWeb())
		{
			return CreationType.internet;
		}

        if (applicationInfo.isSocialApi())
        {
            return CreationType.social;
		}

		throw new IllegalArgumentException("������������ ��� ������ ������������� ��������.");
	}

	/**
	 * �������������� �� ��� ������� ������� ������� �������� ����� �������������.
	 * @param document - ������.
	 * @return true/false
	 * @throws BusinessException
	 */
	public static boolean postConfirmCommissionSupport(BusinessDocument document) throws BusinessException
	{
		if(!(document instanceof ConvertableToGateDocument))
			return false;

		BackRefCommissionTBSettingService service = GateSingleton.getFactory().service(BackRefCommissionTBSettingService.class);
		try
		{
			ConvertableToGateDocument convertable = (ConvertableToGateDocument) document;
			return service.isCalcCommissionSupport((Department) document.getDepartment(), convertable.asGateDocument());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������ �������������� ��� ������� �������� � ��� ����� ��������.
	 * @return ������ ���� ����������1|����������3
	 * @throws BusinessException
	 */
	public static String getSupportedCommissionPaymentTypes() throws BusinessException
	{
		if(!AuthModule.getAuthModule().implies(new ServicePermission("ClientCommissionRequestService")))
			return "";

		Department department = PersonContext.getPersonDataProvider().getPersonData().getDepartment();
		List<CommissionsTBSetting> list = CommissionTBSettingService.getDepartmentCommissionSettings(department);
		StringBuilder allowedSynonyms = new StringBuilder();
		for(CommissionsTBSetting setting: list)
		{
			if(setting.isShow())
			{
				if (allowedSynonyms.length() != 0)
					allowedSynonyms.append("|");
				allowedSynonyms.append(CommissionTBSettingService.getCommissionPaymentsSynonyms().get(setting.getPaymentType()).getFirst());
			}
			if (setting.isShowRub())
			{
				if (allowedSynonyms.length() != 0)
					allowedSynonyms.append("|");
				allowedSynonyms.append(CommissionTBSettingService.getCommissionPaymentsRurSynonyms().get(setting.getPaymentType()).getFirst());
			}
		}
		return allowedSynonyms.toString();
	}

	/**
	 * ��������� ��������� ��� ������� �� ��������� �� �����.
	 * @param messageKey - ����.
	 * @return �����.
	 */
	public static String getSettingMessage(String messageKey)
	{
		return StringHelper.getEmptyIfNull(ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(messageKey));
	}

	/**
	 * @param document ��������
	 * @return true - ������� ������ (����, EVENTIM, etc)
	 */
	public static boolean isExternalPayment(BusinessDocument document) throws BusinessException
	{
		if (document == null)
			return false;

		String formName = document.getFormName();
		return isInternetShopOrAeroflotPayment(document) || formName.equals("FNSPayment") || getUECOrder(document) != null;
	}

	/**
	 * @param document ��������.
	 * @return true ���� �������� ������� ������ �������� �������� ��� ����� ���������.
	 */
	public static boolean isInternetShopOrAeroflotPayment(BusinessDocument document)
	{
		if (document == null)
			return false;

		return EXTERNAL_PAYMENTS_FORM_NAMES.contains(document.getFormName());
	}

	/**
	 * ���������� ��� ����� �� ��������� ������
	 * @param document - ��������
	 * @return ��� �����
	 */
	public static Order getUECOrder(BusinessDocument document) throws BusinessException
	{
		if (!FormConstants.SERVICE_PAYMENT_FORM.equals(document.getFormName()))
			return null;

		String orderUuid = ((JurPayment) document).getOrderUuid();
		if  (StringHelper.isEmpty(orderUuid))
			return null;

		Order order = ExternalPaymentService.get().getOrder(orderUuid);
		if (order == null || !order.getSystemName().equals(SYSTEM_NAME_UEC))
			return null;

		return order;
	}

	private static boolean isInvoiceSubscriptionClaim(BusinessDocument document)
	{
		return  document instanceof EditInvoiceSubscription ||
				document instanceof CloseInvoiceSubscription ||
				document instanceof DelayInvoiceSubscription ||
				document instanceof RecoveryInvoiceSubscription;
	}

	/**
	 * ���������� ������ � ��������� ��������� �������
	 *
	 * @param externalId - ������������� ���������� �������� � ���
	 * @return ����� ��� null, ���� �� �������� �����
	 * @throws BusinessException ���� ���-�� ����� �� ���
	 */
	public static BusinessDocumentToOrder getOrderByExternalId(final String externalId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<BusinessDocumentToOrder>()
			{
				public BusinessDocumentToOrder run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(BusinessDocumentToOrder.class)
						.add(Expression.eq("orderUuid", externalId));

					return (BusinessDocumentToOrder) criteria.uniqueResult();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������ � ��������� ��������� �������
	 *
	 * @param externalId - ������������� ���������� �������� � ���
	 * @return ����� ��� null, ���� �� �������� �����
	 * @throws BusinessException ���� ���-�� ����� �� ���
	 */
	public static BusinessDocument getPaymentByExternalId(final String externalId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<BusinessDocument>()
			{
				public BusinessDocument run(Session session)
				{
					return (BusinessDocument) session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.findByOrderUUID")
							.setParameter("order_uuid", externalId)
							.setParameter("type", "ER")
							.uniqueResult();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� �����, ����������� � ������
	 *
	 * @param orderUuid - �����
	 * @return ����� ��� null, ���� � ������ ��� ���������� �������
	 * @throws BusinessException ���� ���-�� ����� �� ���
	 */
	public static BusinessDocument getPaymentByOrder(final String orderUuid) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<BusinessDocument>()
			{
				public BusinessDocument run(Session session)
				{
					return (BusinessDocument) session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.findByOrderUUID")
							.setParameter("order_uuid", orderUuid)
							.setParameter("type", "P")
							.uniqueResult();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ����������� ������� ��� ������
	 * @param login �����
	 * @param serviceKey ��� �������
	 * @return �������� ��� ���
	 */
	public static boolean isAvailableService(Login login, String serviceKey)
	{
		try
		{
			AuthModule authModule = AuthModule.getAuthModule();
			if (authModule == null || !authModule.getPrincipal().getLogin().equals(login))
				return serviceService.isPersonServices(login.getId(), serviceKey);
			return authModule.implies(new ServicePermission(serviceKey));
		}
		catch (Exception e)
		{
			log.error("������ ��������� ������� � �������", e);
			return false;
		}
	}

	/**
	 * �������� ������ �������������� ����� �������
	 * @param payment ������
	 * @return ����� �������������� �����
	 * @throws BusinessException
	 */
	public static List<Field> getDocumentExtendedFields(JurPayment payment) throws BusinessException
	{
		try
		{
			return payment.getExtendedFields();
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� �� ��������� iTunes
	 * @param businessDocument
	 * @return
	 * @throws
	 */
	public static boolean isITunesProvider(BusinessDocument businessDocument)
	{
		if(!(businessDocument instanceof JurPayment))
			return false;

		JurPayment payment = (JurPayment) businessDocument;

		try
		{
			return ESBHelper.isIQWavePayment(payment)
					&& ConfigFactory.getConfig(DocumentConfig.class).getiTunesProvider().equals(getBillingCodeRecipient(payment));
		}
		catch (GateException e)
		{
			log.error("������ ����������� ���������� ��� ���������  � id=" + businessDocument.getId(), e);
			return false;
		}
	}

	/**
	 * �������� ��� ����������
	 * @param payment ������
	 * @return ��� ����������
	 * @throws
	 */
	private static String getBillingCodeRecipient(AbstractBillingPayment payment)
	{
		return IDHelper.restoreOriginalIdWithAdditionalInfo(IDHelper.restoreOriginalId(payment.getReceiverPointCode()));
	}

	/**
	 * �������� ���������� ������������ ���������� �������
	 * @param payment ������� � ���������� ����������� �����
	 * @return ������������ ���������� �������
	 * @throws BusinessException
	 */
	public static String getReceiverActualName(JurPayment payment) throws BusinessException
	{
		try
		{
			if (ESBHelper.isIQWavePayment(payment))
			{
				return serviceProviderService.findNameById(payment.getReceiverInternalId(), null);
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		return payment.getReceiverName();
	}

	/**
	 * �������������� �� �� ������ ��������� �������� �������� �� �������
	 * @param document ��������
	 * @return true - ��������������
	 * @throws BusinessException
	 */
	public static boolean isSupportCreateInvoiceSubscription(BusinessDocument document) throws BusinessException
	{
		return isSupportCreateInvoiceSubscription(document, getDocumentProvider(document));
	}

	/**
	 * �������� �� ������ � ����� iqw(������� GateException � BusinessException)
	 * @param payment ������
	 * @return true - ��������
	 * @throws BusinessException
	 */
	private static boolean isIQWavePayment(AbstractPaymentSystemPayment payment) throws BusinessException
	{
		try
		{
			return ESBHelper.isIQWavePayment(payment);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ����� ������� ����� ��������� � ��������
	 * @param document - ������
	 * @return  isSupportCreateInvoiceSubscription - �������� �������� �� �������,
	 *  isCopyAllowed - ������ �������
	 *  isTemplateSupported - �������� �������
	 *  isAutoPaymentAllowed - �������� �����������
	 * @throws BusinessException
	 */
	public  static Map<String, Boolean> getSupportedActions(BusinessDocument document) throws BusinessException
	{
		Map<String, Boolean> actions = new HashMap<String, Boolean>();
		if(document == null)
			return actions;

		ServiceProviderShort provider = null;
		try
		{
			provider = DocumentHelper.getDocumentProvider(document);
		}
		catch (Exception ex)
		{
			log.error("������ ��������� ���������� ��� ��������� � id = " + document.getId(), ex);
		}

		try
		{
			//�������������� �� �� ������ ��������� �������� �������� �� �������
			actions.put("isSupportCreateInvoiceSubscription", isSupportCreateInvoiceSubscription (document, provider));
			//��������� �� ��������� ����� ��������� (������ �������)
			actions.put("isCopyAllowed", isCopyAllowed(document, provider));
			//��������� �� ��������� ������ �� ������ ���������
			actions.put("isTemplateSupported", isTemplateSupported(document, provider));
			//��������� �� ��������� ���������� �� ������ ���������
			actions.put("isAutoPaymentAllowed", isAutoPaymentAllowed(document, provider));
			//��������� �� ��������� ����������� �� ������ ���������
			actions.put("isAutoTransferAllowed", isAutoTransferAllowed(document));
		}
		catch (Exception e)
		{
			log.error("������ ����������� ��������� �������� ��� ��������� � id = " + document.getId(), e);
		}

		return actions;
	}

	public static boolean isMoneyBoxSupported(BusinessDocument document)
	{
		try
		{
			//����������� ������� �������� ������ ��� ���������� ��������.
			if(!document.getState().getCode().equals("EXECUTED"))
				return false;

			if((document instanceof AccountOpeningClaim))
			{
				AccountOpeningClaim claim = (AccountOpeningClaim) document;
				AccountLink toResource = PersonContext.getPersonDataProvider().getPersonData().findAccount(claim.getReceiverAccount());
				if (!isAccountForMoneyBoxMatched(toResource))
					return false;
				//��������� �����.
				if (!serviceService.isPersonServices(claim.getInternalOwnerId(), "CreateMoneyBoxPayment"))
					return false;
				return true;
			}

			if (!(document instanceof InternalTransfer))
				return false;

			InternalTransfer payment = (InternalTransfer) document;

			//��� ������� - ������� � ����� �� �����?
			if (payment.getType() != CardToAccountTransfer.class)
				return false;

			//��������� �����.
			if (!serviceService.isPersonServices(payment.getInternalOwnerId(), "CreateMoneyBoxPayment"))
				return false;

			PaymentAbilityERL fromResource = payment.getChargeOffResourceLink();
			ExternalResourceLink toResource = payment.getDestinationResourceLink();

			if (!isAccountForMoneyBoxMatched((AccountLink) toResource))
				return false;

			if (!isCardForMoneyBoxMatched((CardLink) fromResource))
				return false;

			//��� �������������� �������� ��������� ���������-��������� �� �������� ����� �������.
			if (!CurrencyUtils.isSameCurrency(fromResource.getCurrency().getCode(), toResource.getCurrency().getCode()))
			{
				PropertyConfig config = ConfigFactory.getConfig(PropertyConfig.class);
				if (config.getProperty("com.rssl.iccs.moneybox.disallowedConversion.enable").equals("true"))
					return false;
			}

			String cardTB = StringHelper.removeLeadingZeros(fromResource.getOffice().getCode().getFields().get("region"));
			String accountTB = StringHelper.removeLeadingZeros(((AccountLink)toResource).getOfficeTB());

			List<String> tbMoscow = ConfigFactory.getConfig(BusinessSettingsConfig.class).getMoscowTB();
			if (tbMoscow.contains(cardTB) && tbMoscow.contains(accountTB))
				return true;

			if (cardTB != null && accountTB != null)
				return cardTB.equals(accountTB);
			return false;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * �������� �� �������� ������� � ����� ������
	 * @param link- accountLink
	 * @return true/false
	 */
	public static boolean isAccountForMoneyBoxMatched(AccountLink link)
	{
		ActiveMoneyBoxAvailableAccountFilter accountFilter = new ActiveMoneyBoxAvailableAccountFilter();
		return accountFilter.accept(link);
	}

	/**
	 * �������� �� �������� ������� � ����� ������
	 * @param linkId- ���������� ������������� accountLink�
	 * @return true/false
	 */
	public static boolean isAccountForMoneyBoxMatched(String linkId) throws BusinessLogicException, BusinessException
	{
		return isAccountForMoneyBoxMatched(PersonContext.getPersonDataProvider().getPersonData().getAccount(Long.valueOf(linkId)));
	}

	/**
	 * �������� �� �������� ������� � ���� �����.
	 * @param link - cardLink
	 * @return true/false
	 */
	public static boolean isCardForMoneyBoxMatched(CardLink link)
	{
		ActiveNotCreditMainCardFilter cardFilter = new ActiveNotCreditMainCardFilter();
		return cardFilter.accept(link.getCard());
	}


	private static boolean isAutoPaymentAllowed(BusinessDocument document,  ServiceProviderShort provider)
	{
		if (document == null)
		{
			return false;
		}

		if (!(document instanceof GateExecutableDocument))
		{
			return false;
		}

		GateExecutableDocument executableDocument = (GateExecutableDocument) document;

		Class<? extends GateDocument> executableDocumentType = executableDocument.getType();

		// ��������!!! ������� ApplicationConfig.getIt().getApplicationInfo().isMobileApi() - �������! ��������� ������! ����� ���������� ������.
		if (PermissionUtil.impliesServiceRigid("CreateP2PAutoTransferClaim") && ApplicationConfig.getIt().getApplicationInfo().isMobileApi())
		{
			if (executableDocumentType == ExternalCardsTransferToOurBank.class || executableDocumentType == InternalCardsTransfer.class)
			{
				return true;
			}
		}

		try
		{
			return executableDocument.getType() == CardPaymentSystemPayment.class && AutoPaymentHelper.isAutoPaymentAllowed(provider);
		}
		catch (Exception e)
		{
			log.error("������ ��� ����������� ����������� �������� �����������", e);
			return false;
		}
	}

	private static boolean isAutoTransferAllowed(BusinessDocument document)
	{
		if (document == null)
		{
			return false;
		}

		if (!PermissionUtil.impliesServiceRigid("CreateP2PAutoTransferClaim"))
		{
			return false;
		}

		if (!(document instanceof GateExecutableDocument))
		{
			return false;
		}

		GateExecutableDocument executableDocument = (GateExecutableDocument) document;
		Class clazz = executableDocument.asGateDocument().getType();

		return clazz == ExternalCardsTransferToOurBank.class || clazz == InternalCardsTransfer.class;
	}

	/**
	 * ����� �� ������������ ��� ����� � �������� ����� ���������� ��� P2P �����������
	 * @param link - ���� �����
	 * @return true/false
	 */
	public static boolean isCardToResourceForP2PAutoTransfer (CardLink link)
	{
		if (link == null || link.getCard() == null)
			return false;
		ActiveCardWithArrestedAccountFilter activeCardWithArrestedAccountFilter = new ActiveCardWithArrestedAccountFilter();
		return activeCardWithArrestedAccountFilter.accept(link.getCard());
	}

	/**
	 * ����� �� ������������ ��� ����� � �������� ����� �������� ��� P2P �����������
	 * @param link - ���� �����
	 * @return true/false
	 */
	public static boolean isCardFromResourceForP2PAutoTransfer (CardLink link)
	{
		if (link == null || link.getCard() == null)
			return false;
		ActiveNotCreditCardFilter activeNotCreditCardFilter = new ActiveNotCreditCardFilter();
		return activeNotCreditCardFilter.accept(link.getCard())&& (link.getCard().isMain() || link.getCard().getAdditionalCardType() != AdditionalCardType.CLIENTTOOTHER);
	}

	/**
	 * @param document ��������
	 * @return true - ��������
	 */
	private static boolean isTemplateSupported(BusinessDocument document, ServiceProviderShort provider) throws BusinessException
	{
		if (document == null)
		{
			return false;
		}
		if (document.isByTemplate())
		{
			return false;
		}

		if (!FormType.isTemplateSupported(document.getFormType()))
		{
			return false;
		}

		if (isLongOffer(document))
		{
			return false;
		}

		if (isPaymentDisallowedFromAccount(document))
		{
			return false;
		}

		if(isInvoicePayment(document))
		{
			return false;
		}

		if (document instanceof JurPayment)
		{
			JurPayment jurPayment = (JurPayment) document;
			return !jurPayment.isBarCodeDocument(provider) && isBillingPaymentTemplateSupported(jurPayment, provider);
		}

		return true;
	}

	/**
	 * �������� ����������� ���������� � ����������� �� ����������
	 * @param billingProvider ��������� �����
	 * @return ������� ����������� ����������
	 */
	private static boolean checkAvailablePayments(final ServiceProviderShort billingProvider)
	{
		return checkAvailablePayments(billingProvider, null);
	}

	/**
	 * �������� ����������� ���������� � ����������� �� ����������
	 *
	 * @param provider ��������� �����
	 * @param versionNumber ������ ���������� ����������
	 * @return ������� ����������� ����������
	 */
	public static boolean checkAvailablePayments(final ServiceProviderShort provider, VersionNumber versionNumber)
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		if (applicationInfo.isATM())
		{
			return provider.isAvailablePaymentsForAtmApi();
		}

        if (applicationInfo.isSocialApi())
        {
            return provider.isAvailablePaymentsForSocialApi();
        }

		if (applicationInfo.isMobileApi())
		{
			boolean APISupported = versionNumber == null || provider.getVersionAPI() <= versionNumber.getSolid();
			return provider.isAvailablePaymentsForMApi() && APISupported;
		}

		if (applicationInfo.isSMS())
		{
			return provider.isAvailablePaymentsForErmb();
		}

		return provider.isAvailablePaymentsForInternetBank();
	}

	/**
	 * �������� ����������� ���������� � ����������� �� ����������
	 *
	 * @param shortcut ��������� �����
	 * @param versionNumber ������ ���������� ����������
	 * @return ������� ����������� ����������
	 */
	public static boolean checkAvailablePayments(final ServiceProviderShortcut shortcut, VersionNumber versionNumber)
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		if (applicationInfo.isATM())
		{
			return shortcut.isATMPaymentAvailability();
		}

		if (applicationInfo.isSocialApi())
		{
			return shortcut.isSocialPaymentAvailability();
		}

		if (applicationInfo.isMobileApi())
		{
			boolean APISupported = versionNumber == null || shortcut.getVersionAPI() <= versionNumber.getSolid();
			return shortcut.isMAPIPaymentAvailability() && APISupported;
		}

		if (applicationInfo.isSMS())
		{
			return shortcut.isERMBPaymentAvailability();
		}

		return shortcut.isWebPaymentAvailability();
	}

	/**
	 * ��������� �������� ������ ������ ��������, �� ���������.
	 * @param source - documentSource
	 * @return �������� ������.
	 */
	public static String getClientServiceName(DocumentSource source)
	{
		BusinessDocument document = source.getDocument();

		if(FormConstants.REFUSE_AUTOSUBSCRIPTION_PAYMENT_FORM.equals(document.getFormName()))
			return "CloseAutoSubscriptionPayment";

		if(FormConstants.DELAY_AUTOSUBSCRIPTION_PAYMENT_FORM.equals(document.getFormName()))
			return "DelayAutoSubscriptionPayment";

		if(FormConstants.RECOVERY_AUTOSUBSCRIPTION_PAYMENT_FORM.equals(document.getFormName()))
			return "RecoveryAutoSubscriptionPayment";

		if (FormConstants.EDIT_AUTOSUBSCRIPTION_PAYMENT.equals(document.getFormName()))
			return "EditAutoSubscriptionPayment";

		if (FormConstants.CREATE_INVOICE_SUBSCRIPTION_PAYMENT.equals(document.getFormName()))
			return "CreateInvoiceSubscriptionPayment";

		// ���� �������� ������� ����������� (������������)
		if ((document instanceof JurPayment) && document.isLongOffer())
		{
			if (((JurPayment) document).getReceiverInternalId() != null)
				return "ClientCreateAutoPayment";
			return "ClientFreeDetailAutoSubManagement";
		}
		return source.getMetadata().getName();
	}

	/**
	 * ��������� �������� ������ ������ �����������, �� ���������.
	 * @param source - documentSource
	 * @return �������� ������.
	 */
	public static String getEmployeeServiceName(DocumentSource source)
	{
		BusinessDocument document = source.getDocument();

		if(FormConstants.REFUSE_AUTOSUBSCRIPTION_PAYMENT_FORM.equals(document.getFormName()))
			return "CloseEmployeeAutoSubscriptionPayment";

		if(FormConstants.DELAY_AUTOSUBSCRIPTION_PAYMENT_FORM.equals(document.getFormName()))
			return "DelayEmployeeAutoSubscriptionPayment";

		if(FormConstants.RECOVERY_AUTOSUBSCRIPTION_PAYMENT_FORM.equals(document.getFormName()))
			return "RecoveryEmployeeAutoSubscriptionPayment";

		if(FormConstants.EDIT_AUTOSUBSCRIPTION_PAYMENT.equals(document.getFormName()))
			return "EditEmployeeAutoSubscriptionPayment";

		// ���� �������� ������� ����������� (������������)
		if((document instanceof JurPayment) && document.isLongOffer())
		{
			if (((JurPayment) document).getReceiverInternalId() != null)
				return "CreateEmployeeAutoPayment";
			return "EmployeeFreeDetailAutoSubManagement";
		}
		if (document instanceof ExtendedLoanClaim)
			return "LoanClaimEmployeeService";
		return "Employee" + document.getFormType().getName();
	}

	/**
	 * ��������� �� ��������� ����� ���������.
	 * @param document ��������
	 * @return ���������/���.
	 */
	private static boolean isCopyAllowed(BusinessDocument document, ServiceProviderShort provider)
	{
		if (document == null)
		{
			return false;
		}
		try
		{
			String formName = document.getFormName();

			if (disallowedCopyFormNames.contains(formName))
			{
				return false;
			}

			if(isCreateAutoSubscription(document))
			{
				return false;
			}

			if (isPaymentDisallowedFromAccount(document))
			{
				return false;
			}

			if (isInvoiceSubscriptionClaim(document))
			{
				return false;
			}

			if (document instanceof InvoicePayment)
			{
				return  false;
			}

			// ��� ���������� ������� ������ �� �����
			if (document instanceof ExtendedLoanClaim || document instanceof ShortLoanClaim)
				return false;

			if (document instanceof JurPayment)
			{
				JurPayment jurPayment = (JurPayment)document;
				//��� �������� �� �����-���� �������� ����� (������ �������) ���������!.
				if (provider != null && jurPayment.isBarCodeDocument(provider))
				{
					return false;
				}

				if (isExternalPayment(document))
					return false;

				Long receiverId = jurPayment.getReceiverInternalId();
				if (receiverId == null)
					return true;
				else if (provider == null)
					return false;
				return ServiceProviderState.MIGRATION != provider.getState();
			}
			return true;
		}
		catch (Exception e)
		{
			log.error("������ ������������ ����������� ����� �������",e);
			return false;
		}
	}

	/**
	 * �������� �� �������� ��������� ������ ������������
	 * @param document ��������
	 * @return true - ��������
	 */
	public static boolean isCreateAutoSubscription(BusinessDocument document)
	{
		return FormConstants.SERVICE_PAYMENT_FORM.equals(document.getFormName()) && document.isLongOffer();
	}

	/**
	 * �������������� �� �� ������ ��������� �������� �������� �� �������
	 * @param document ��������
	 * @return true - ��������������
	 * @throws BusinessException
	 */
	private static boolean isSupportCreateInvoiceSubscription(BusinessDocument document, ServiceProviderShort provider) throws BusinessException
	{
		if (document == null)
		{
			return false;
		}
		if(!(document instanceof JurPayment) || !document.getClass().isAssignableFrom(CreateInvoiceSubscriptionPayment.class))
			return false;

		JurPayment payment = (JurPayment) document;
		if(payment.isLongOffer())
			return false;

		if(isIQWavePayment(payment))
			return false;

		if(provider == null || !(provider.getKind().equals("B")))
			return false;

		return provider.isAutoPaymentSupported() && serviceProviderService.isSupportAutoPayType(provider.getId(), AutoSubType.INVOICE);
	}

	/**
	 * �������� �� ���������� ������ ����� iqwave
	 * @param document ��������
	 * @return true - ��������
	 * @throws GateException
	 */
	public static boolean isIQWaveDocument(BusinessDocument document) throws GateException
	{
		try
		{
			GateDocument gateDocument = new ConvertibleToGateDocumentAdapter(document).asGateDocument();
			Class<? extends GateDocument> type = gateDocument.getType();

			if (type != CardPaymentSystemPayment.class)
			{
				return false;
			}

			CardPaymentSystemPayment payment = (CardPaymentSystemPayment) gateDocument;
			return ESBHelper.isIQWavePayment(payment);
		}
		catch (NotConvertibleToGateBusinessException ignore)
		{
			return false;
		}
	}

	/**
	 * ��������� ���������� �������� �������� ��� ������ �� �������� �������.
	 * @return �� ��������� ����������� ���������, �������� � ����������
	 */
	public static String getMoneyBoxDefaultPercent()
	{
		PropertyConfig config = ConfigFactory.getConfig(PropertyConfig.class);
		return config.getProperty("com.rssl.iccs.moneybox.percent.default");
	}


	/**
	 * ��������� ������ ���������  ���������� � ���������� ���������� ��� ���������� ������ �� ������ ���������� � ������� � �������� ������ ��� ���� �� ����� �� ������ ��������
	 * @return ����� ���������
	 */
	public static String getPersonPaymentLimitMessage()
	{
		return ConfigFactory.getConfig(ClientConfig.class).getInfoPersonPaymentLimitMessage();
	}

	/**
	 * �������� �� ��������� �������� ����������� � 2 ����
	 * @param document ����������
	 * @return true - ��
	 */
	public static boolean isTwoStepLongOffer(BusinessDocument document)
	{
		if (document == null)
		{
			return false;
		}

		if (!document.isLongOffer())
		{
			return false;
		}

		if (!(document instanceof GateExecutableDocument))
		{
			return false;
		}

		Class<? extends GateDocument> type = ((GateExecutableDocument) document).asGateDocument().getType();
		if (type == null)
		{
			return false;
		}

		return InternalCardsTransferLongOffer.class == type || ExternalCardsTransferToOurBankLongOffer.class == type ||
					AbstractPaymentSystemPayment.class.isAssignableFrom(type) || CreateAutoPayment.class == type ||
				    ExternalCardsTransferToOtherBankLongOffer.class == type;
	}

	/**
	 * �������� �� ��������� �������� ����������� � 2 ����
	 * @param document ����������
	 * @return true - ��
	 */
	public static boolean isUseLongOfferValidationStrategy(BusinessDocument document)
	{
		if (document == null)
		{
			return false;
		}

		if (!document.isLongOffer())
		{
			return false;
		}

		return StateCode.INITIAL_LONG_OFFER.name().equals(document.getState().getCode()) || AutoPaymentBase.class.isAssignableFrom(document.getClass());
	}

	public static String getPlatformText(String deviceInfo) throws BusinessException
	{

		MobilePlatform mobilePlatform = mobilePlatformService.findByPlatformId(deviceInfo);
		if (mobilePlatform == null)
			return deviceInfo;
		String platformName = mobilePlatform.getPlatformName();
		if (StringHelper.isEmpty(platformName))
			return deviceInfo;
		else
			return platformName;
	}

	/**
	 * ����� ��������� �������� �� �������� ������� � ������ �������������
	 * @param document ������
	 * @return true, ���� �� ���� ������������, false � ��������� �������
	 * @throws DocumentException
	 */
	public static boolean isAeroexpressReservationPayment(AbstractPaymentDocument document) throws DocumentException
	{
		if (!(document instanceof AbstractPaymentSystemPayment))
		{
			return false;
		}
		AbstractPaymentSystemPayment payment = (AbstractPaymentSystemPayment) document;
		try
		{
			//���������, �������� �� �� �����������  IQWave
			if (ESBHelper.isIQWavePayment(payment))
			{
				//�������� id ����������
				String IdWithAdditionalInfo = IDHelper.restoreOriginalIdWithAdditionalInfo(payment.getReceiverPointCode());
				String id = IDHelper.restoreOriginalId(IdWithAdditionalInfo);
				if (id != null)
				{
					//�������� �� ���������� id �� � id �������������
					return id.equals(CODE_AEROEXPRESS);
				}
			}
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		return false;
	}

	/**
	 * ����� ��������� �������� �� �������� ������� �� ����������� ����
	 * @param document ��������
	 * @return �������� �� �������� ������� �� ����������� ����
	 */
	public static boolean isRemoteConnectionUDBOClaim(AbstractPaymentDocument document)
	{
		return document instanceof RemoteConnectionUDBOClaim;
	}

	public static String getCommissionMessage(String commissionValue, String tariffPlanESB) throws BusinessException
	{
		if (StringHelper.isEmpty(tariffPlanESB))
			return getSettingMessage("commission.info.message") + " " + commissionValue;
		TariffPlanConfig activePersonTarifPlanCode = TariffPlanHelper.getActualLocaledTariffPlanByCode(PersonHelper.getActivePersonTarifPlanCodeWithCheckPerms());
		if (activePersonTarifPlanCode != null && !TariffPlanHelper.isUnknownTariffPlan(activePersonTarifPlanCode.getCode()) && tariffPlanESB.equals(activePersonTarifPlanCode.getName()))
			return getSettingMessage("commission.discount.info.message") + " " + activePersonTarifPlanCode.getName();
		return getSettingMessage("commission.info.message") + " " + commissionValue;
	}

	/**
	 * �������� ����� �������� WAY �� ����� � ������ ���������
	 * ����� ������ � ������� ����������� ������ (���� ���� � ��, � ��)
	 * ������� ������ ������ � ����� �� ����������
	 * @param docSeries ����� ��������� (null ���� ���)
	 * @param docNumber ����� ��������� (null ���� ���)
	 * @return ����� �������� WAY
	 */
	public static String getPassportWayNumber(String docSeries, String docNumber)
	{
		String series = StringHelper.getEmptyIfNull(docSeries);
		String number = StringHelper.getEmptyIfNull(docNumber);
		String delimiter = StringUtils.isNotEmpty(series) && StringUtils.isNotEmpty(number) ? " " : "";
		return series + delimiter + number;
	}

    public static boolean isP2PAutoSubscription(Object link)
    {
        if (link == null)
            return false;
        if (!(link instanceof AutoSubscriptionLink))
            return false;
        Class<? extends GateDocument> type = ((AutoSubscriptionLink)link).getValue().getType();
        return type != null && (type.equals(InternalCardsTransferLongOffer.class) || type.equals(ExternalCardsTransferToOurBankLongOffer.class) ||
		        type.equals(ExternalCardsTransferToOtherBankLongOffer.class));
    }

	/**
	 * ������ ����� ��������, ��������� ��� ���������� �� ������ � ��
	 * @param document ��������
	 * @return �������� �� ���������� ��������� �� ������ � ��
	 */
	public static boolean isCallCentreExecutionAvailable(BusinessDocument document)
	{
		if (document == null)
			return false;
		String formName = document.getFormName();
		return FormConstants.BLOCKING_CARD_CLAIM.equals(formName)
				|| FormConstants.REFUSE_AUTOSUBSCRIPTION_PAYMENT_FORM.equals(formName)
				|| FormConstants.REFUSE_LONGOFFER_FORM.equals(formName)
				|| FormConstants.REFUSE_AUTOPAYMENT_FORM.equals(formName);
	}

	public static void incrementPromoCode(BusinessDocument document) throws BusinessException
	{
		if (!(document instanceof AccountOpeningClaim))
			return;

		AccountOpeningClaim claim = (AccountOpeningClaim) document;

		String promoCodeSegment = claim.getSegment();
		if (StringHelper.isEmpty(promoCodeSegment) || !claim.isUsePromoRate())
			return;

		Long loginId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId();
		List<ClientPromoCode> clientPromoCodes = clientPromoCodesService.getActiveClientPromoCodes(loginId);

		for (ClientPromoCode promoCode : clientPromoCodes)
		{
			PromoCodeDeposit promoCodeDeposit = promoCode.getPromoCodeDeposit();
			if (Long.valueOf(promoCodeSegment).equals(promoCodeDeposit.getCodeS()))
			{
				if (promoCodeDeposit.getNumUse() > 0)
				{
					Long used = promoCode.getUsed() + 1;

					promoCode.setUsed(used);
					if (used >= promoCodeDeposit.getNumUse())
					{
						promoCode.setActive(false);
						promoCode.setCloseReason(CloseReason.REACH_USE_LIMIT);
					}
					clientPromoCodesService.addOrUpdate(promoCode);
					ClientPromoCodeService.clearCache();
				}
			}
		}
	}

	public static void decrementPromoCode(BusinessDocument document) throws BusinessException
	{
		if (!(document instanceof AccountOpeningClaim))
			return;

		AccountOpeningClaim claim = (AccountOpeningClaim) document;

		String promoCodeSegment = claim.getSegment();
		if (StringHelper.isEmpty(promoCodeSegment) || !claim.isUsePromoRate() || !claim.isIncrementedPromoCode())
			return;

		Long loginId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId();
		List<ClientPromoCode> clientPromoCodes = clientPromoCodesService.getAllClientPromoCodes(loginId);

		for (ClientPromoCode promoCode : clientPromoCodes)
		{
			PromoCodeDeposit promoCodeDeposit = promoCode.getPromoCodeDeposit();
			if (Long.valueOf(promoCodeSegment).equals(promoCodeDeposit.getCodeS()))
			{
				if (promoCodeDeposit.getNumUse() > 0)
				{
					Long used = promoCode.getUsed() - 1;

					if (used < promoCodeDeposit.getNumUse())
					{
						promoCode.setActive(true);
						promoCode.setCloseReason(null);
					}
					promoCode.setUsed(used);
					clientPromoCodesService.addOrUpdate(promoCode);
					ClientPromoCodeService.clearCache();
				}
			}
		}
	}
}

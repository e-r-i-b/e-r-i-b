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
 * Хелпер для работы с документами
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

	private static final String DEFINE_RATE_ERROR_MESSAGE               = "Ошибка определения курса валюты";

	private static final Pattern TIME_PATTERN = Pattern.compile("\\d{2}:\\d{2}"); // см. DateHelper#TIME_PATTERN
	private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{2}:\\d{2} \\d{2}\\.\\d{2}"); // см. DateHelper#DATE_TIME_PATTERN

	private static final GroupRiskService  groupRiskService  = new GroupRiskService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final ServiceProviderService  serviceProviderService  = new ServiceProviderService();
	private static final ServiceService serviceService  = new ServiceService();
	private static final BillingService billingService = new BillingService();

	//Статусы платежей, для которых разрешена печать чека
	private static final Set<String> allowedTemplatePaymentStates = new HashSet<String>();
	private static final Set<String> EXTERNAL_PAYMENTS_FORM_NAMES = new HashSet<String>();
	// названия форм, для которых не разрешено создание копий
	private static final Set<String>  disallowedCopyFormNames = new HashSet<String>();

	private static final Map<String, String> paymentStateDescriptions = new HashMap<String, String>();

	private static final MobilePlatformService mobilePlatformService = new MobilePlatformService();

	private static final ClientPromoCodeService clientPromoCodesService = new ClientPromoCodeService();

	//Код ПУ Аэроэкспресс в iqwave
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

		paymentStateDescriptions.put("SAVED",                           "Черновик");
		paymentStateDescriptions.put("INITIAL",                         "Черновик");
		paymentStateDescriptions.put("DELAYED_DISPATCH",                "Ожидается обработка");
		paymentStateDescriptions.put("DISPATCHED",                      "Исполняется банком");
		paymentStateDescriptions.put("SEND",                            "Исполняется банком");
		paymentStateDescriptions.put("EXECUTED",                        "Исполнен");
		paymentStateDescriptions.put("TICKETS_WAITING",                 "Исполнен");
		paymentStateDescriptions.put("REFUSED",                         "Отклонено банком");
		paymentStateDescriptions.put("RECALLED",                        "Заявка была отменена");
		paymentStateDescriptions.put("ERROR",                           "Исполняется банком");
		paymentStateDescriptions.put("PARTLY_EXECUTED",                 "Исполняется банком");
		paymentStateDescriptions.put("UNKNOW",                          "Исполняется банком");
		paymentStateDescriptions.put("SENT",                            "Исполняется банком");
		paymentStateDescriptions.put("WAIT_CONFIRM",                    "Подтвердите в контактном центре");
		paymentStateDescriptions.put("BILLING_CONFIRM_TIMEOUT",         "Исполняется банком");
		paymentStateDescriptions.put("BILLING_GATE_CONFIRM_TIMEOUT",    "Исполняется банком");
		paymentStateDescriptions.put("ABS_RECALL_TIMEOUT",              "Исполняется банком");
		paymentStateDescriptions.put("ABS_GATE_RECALL_TIMEOUT",         "Исполняется банком");
		paymentStateDescriptions.put("OFFLINE_DELAYED",                 "Ожидается обработка");
		paymentStateDescriptions.put("OFFLINE_SAVED",                   "Черновик");

		EXTERNAL_PAYMENTS_FORM_NAMES.add("ExternalProviderPayment");
		EXTERNAL_PAYMENTS_FORM_NAMES.add("AirlineReservationPayment");
	}

	/**
	 * Получить описание статуса документа
	 * @param stateCode код статуса
	 * @return описание статуса документа
	 */
	public static String getStateDescription(String stateCode)
	{
		return paymentStateDescriptions.get(stateCode);
	}

	/**
	 * Является ли данный платеж автоплатежем/длительным поручением
	 *
	 * @param document документ
	 * @return true - является
	 */
	public static boolean isLongOffer(BusinessDocument document)
	{
		if (!(document instanceof GateExecutableDocument))
			return false;

		GateExecutableDocument gateDocument = (GateExecutableDocument) document;
		return LongOffer.class.isAssignableFrom(gateDocument.getType());
	}

	/**
	 * Проверяем, что данный вид автоплатежей - это актавный вид операции
	 * (операция с созданиенм/редактированием автоплатежа, не операция закрытия, приостановления, возобновления)
	 * @param document документ
	 * @return true - операция активная
	 */
	public static boolean isActiveLongOffer(BusinessDocument document)
	{
		//проверка на то что, платеж является автоплатежем, должна быть выше

		if (!(document instanceof GateExecutableDocument))
			return false;

		GateExecutableDocument gateDocument = (GateExecutableDocument) document;
		Class<? extends GateDocument> type  = gateDocument.getType();

		//данные операции не являются активными
		if (RefuseAutoPayment.class == type || RefuseLongOffer.class == type
				|| CloseCardPaymentSystemPaymentLongOffer.class == type || EmployeeCloseCardPaymentSystemPaymentLongOffer.class == type
					|| DelayCardPaymentSystemPaymentLongOffer.class == type || EmployeeDelayCardPaymentSystemPaymentLongOffer.class == type
						|| CloseCardToCardLongOffer.class == type || DelayCardToCardLongOffer.class == type || RecoveryCardToCardLongOffer.class == type)
			return false;

		return true;
	}

	/**
	 * Нужно ли использовать данный вид автоплатежей
	 * @param document документ
	 * @return true - нужно
	 */
	public static boolean needUseLongOffer(BusinessDocument document)
	{
		//проверка на то что, платеж является автоплатежем, должна быть выше
		//автоплатежи на внутренние счета не учитываем
		if (TypeOfPayment.INTERNAL_PAYMENT_OPERATION == document.getTypeOfPayment())
		{
			return false;
		}

		//учитываем только активные автоплатежи
		return DocumentHelper.isActiveLongOffer(document);
	}

	/**
	 * Является ли документ оплатой счета Корзины платежей
	 * @param document - документ
	 * @return true/false
	 */
	public static boolean isInvoicePayment(BusinessDocument document)
	{
		return document instanceof InvoicePayment;
	}

	/**
	 * По заданому идентификатору подразделения
	 * для тербанка ищем настройку подтверждения операций по шаблонам СМС-паролем
	 * @param office - офис
	 * @return true-необходимо подтверждать/false - старая система подтверждения операций по шаблонам
	 */
	public static Boolean getTemplateConfirmSetting(Office office) throws BusinessException
	{
		try
		{
			String templateConfirmSetting = ConfigFactory.getConfig(DocumentConfig.class).getTemplateConfirmSetting(new SBRFOfficeCodeAdapter(office.getCode()).getRegion());
			if (templateConfirmSetting != null)
				return Boolean.valueOf(templateConfirmSetting);
			//значение по умолчанию
			return false;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}


	/**
	 * По заданому идентификатору подразделения
	 * для тербанка ищем настройку кратности суммы для шаблонов
	 * @param office - идентификатор подразделения из документа
	 * @return разрешенная кратность увеличения суммы платежа по шаблону платежа
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
			//значение по умолчанию
			return DocumentHelper.TEMPLATE_FACTOR_DEFAULT_VALUE;
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * По заданому идентификатору подразделения для тербанка ищем настройку «Проверка кратности суммы в PRO-зоне»
	 * @param departmentId - идентификатор подразделения из документа
	 * @return true - необходимо проверять
	 */
	public static Boolean getUseTemplateFactorInFullMAPI(Long departmentId) throws BusinessException
	{
		try
		{
			Department terbank = departmentService.getTB(departmentId);
			String useTemplateFactorInFullMAPI = ConfigFactory.getConfig(DocumentConfig.class).getTemplateFactorInFullMAPI(terbank.getId());
			if (useTemplateFactorInFullMAPI != null)
				return Boolean.valueOf(useTemplateFactorInFullMAPI);
			//значение по умолчанию
			return true;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Проверка доступна ли работа с данным документом в текущем приложении
	 * (автопалтеж созданый сотрудником не доступен клиенту и наоборот)
	 * @param document - документ
	 * @return true - доступна
	 */
	public static boolean checkApplicationRestriction(BusinessDocument document)
	{
		return document.checkApplicationRestriction();
	}

	/**
	 * Проверка, является ли документ платежом по оплате брони авиакомпании.
	 * Пролверка завязана на поле, возвращается в ответе на запрос информации о брони.
	 * @param document - платеж
	 * @return true - оплата авиаброни
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
	 * Перевод платежа в статус UNKNOW
	 * @param executor - StateMachineExecutor
	 * @param type - тип события
	 * @param e - полученное от веб-сервиса исключение, содержащее детали ошибки 
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
	 * Повторить попытку выполнить действие в оффлайн режиме
	 * @param executor - инстанс текущего экзекьютера стейт машины
	 * @param event - событие выполнение которого необходимо повторить
	 * @param document - документ
	 * @param message - сообщение с описанием ошибки
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
	 * @return разрешена ли оплата со счета физ. лицам 
	 */
	public static boolean isExternalPhizAccountPaymentsAllowed()
	{
		return AuthModule.getAuthModule().implies(new ServicePermission("ExternalPhizAccountPayment"));
	}

	/**
	 * @return разрешена ли оплата услуг юр. лицам
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

			//ищем курс по ТБ клиента и тарифному плану
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
			throw new BusinessLogicException("Не удалось получить курсы валют. Операция временно недоступна.");
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
	 * Конвертация денег из одной валюты в другую по курсу продажи
	 * @param money - сумма конвертации
	 * @param currencyTo валюта, в которую конвертировать
	 * @param document - документ
	 * @return - сконвертированная величина
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
	 * Конвертация денег из одной валюты в другую по курсу продажи
	 * @param money - сумма конвертации
	 * @param currencyTo валюта, в которую конвертировать
	 * @param template шаблон
	 * @return - сконвертированная величина
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
	 * Получить курс для ТБ по умолчаниюл
	 * @param fromCurrency валюта from
	 * @param toCurrency валюта to
	 * @param tarifPlanCodeType код тарифного плана
	 * @return курс
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static CurrencyRate getDefaultCurrencyRate(Currency fromCurrency, Currency toCurrency, String tarifPlanCodeType) throws BusinessException, BusinessLogicException
	{
		CurrencyRateService currencyRateService = GateSingleton.getFactory().service(CurrencyRateService.class);

		try
		{
			//ищем курс ЦБ по ТБ г. Москва и тарифному плану
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
	 * Проверка увеличения суммы платежа, созданного по шаблону, по отношению к сумме шаблона
	 * @param document документ/оперция
	 * @param template шаблон
	 * @return true - сумма превышена
	 */
	public static boolean checkPaymentByTemplateSum(BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		if (!(document instanceof AbstractPaymentDocument))
			throw new BusinessException("некорректный тип документа, ожидался AbstractPaymentDocument");

		if (document instanceof InternalTransfer)
			return false;

		AbstractPaymentDocument abstractDocument = (AbstractPaymentDocument) document;

		//в mAPI full-версии шаблон "Активный" нужно проверять в зависимости от настройки
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
	 * @param document документ
	 * @return true - разрешен
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
	 * @param document документ
	 * @return true - разрешен
	 */
	public static boolean isTemplateATMSupported(BusinessDocument document) throws BusinessException
	{
		boolean templateSupported = isTemplateSupported(document);
		boolean paymentStateSupportTemplate = allowedTemplatePaymentStates.contains(document.getState().getCode());
		boolean editTemplateSupported = PermissionUtil.impliesTemplateOperation(document.getFormName());

		return templateSupported && paymentStateSupportTemplate && editTemplateSupported;
	}

	/**
	 * Поддерживается ли создание шаблона для билинговой системы.
	 * @param document  документ.
	 * @param provider  поставщик.
	 * @return поддерживается ли создание.
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
	 * Поддерживается ли работа с шаблонами по свободным реквизитам.
	 * @param document платежка.
	 * @return поддерживается ли работа.
	 */
	public static boolean isBillingExternalPaymentTemplateSupported(AbstractPaymentSystemPayment document)
	{
		try
		{
			if (document.getFormType() == FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER && StringHelper.isNotEmpty(document.getBillingCode()))
			{
				//для недоговорных поставщиков проверяем состояние биллинга-разрешено ли создание шаблона.
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
	 * Запрещен ли данный платеж согласно правам на создание внешеного перевода
	 * со счета.
	 * @param document - текущий платеж
	 * @return - true/false - запрещен/разрешен
	 */
	public static boolean isPaymentDisallowedFromAccount(BusinessDocument document)
	{
		try
		{
			if (!(document instanceof RurPayment))
				return false;

			RurPayment payment = (RurPayment) document;

			// Доступен ли перевод внешнему получталю со вклада согласно правам
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

			//Если ресурс списания не выбран, значит это новый платеж. При создании нового информируем.
			if (payment.getChargeOffResourceLink() == null)
				return !availableExternalAccountPayment;
			return !availableExternalAccountPayment && (payment.getChargeOffResourceLink() instanceof AccountLink);
		}
		catch (Exception e)
		{
			log.error("Ошибка определения запрета согласно правам на создание внешеного перевода для документа c id = " + document.getId(), e);
			return true;
		}
	}

	/**
	 * Сообщение по умолчанию отображаемое клиенту при создании внешнего перевда со счета, в случае запрещения такой операции.
	 * @param document - платеж
	 * @return текст сообщения в зависимости от того является ли документ шаблоном, или документом созданым на основе шаблона.
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
	 * Возвращает заявку на открытие вклада по идентификатору
	 * @param id - ид документа
	 * @return заявка на открытие вклада
	 * @throws BusinessException
	 */
	public static AccountOpeningClaim getAccountOpeningClaimById(Long id) throws BusinessException
	{
		return (AccountOpeningClaim)businessDocumentService.findById(id);
	}

	/**
	 * Ограничение при оплате по шаблону для переводов поставщику,
	 * оплате по произвольным реквизитам
	 *
	 * @param recipientId внутренний идентификатор поставщика услуг
	 * @return режим
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
	 * Получить группу риска по идентифкатору поставщика услуг.
	 * @param recipientId идентифкатор поставщика услуг
	 * @return Группа риска по идентифатору поставщика услуг. Если поставщик не задан - null. Если его нет - дефолтная группа риска.
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
	 * Разрешена ли оплата с карт
	 * (ограничение - для определния используется гейтовый тип документа)
	 *
	 * @param document документ
	 * @return true - разрешена
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
	 * Разрешена ли оплата со счета
	 * (ограничение - для определния используется гейтовый тип документа)
	 *
	 * @param document документ
	 * @return true - разрешена
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
	 * Получить доп информацию формы платежа для мапи по документу
	 * @param document документ
	 * @param channel канал
	 * @return информацию формы платежа
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
			log.error("Не удалось получить информацию для формы платежа c id = " + document.getId(), e);
			return null;
		}
	}

	/**
	 * Получить поставщика услуг по документу
	 * @param document документ
	 * @return поставщик услуг
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
	 * Получить идентифкатор поставщика услуг по документу
	 * @param document документ
	 * @return идентфикатор поставщика услуг
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
	 * Канал создания/подтверждения документа
	 * @return канал создания/подтверждения
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

		throw new IllegalArgumentException("Некорректный тип канала подтверждения операции.");
	}

	/**
	 * Поддерживается ли для данного платежа рассчет комиссии после подтверждения.
	 * @param document - платеж.
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
	 * Список поддреживаемых для рассчет комиссии в ЦОД типов платежей.
	 * @return строка вида типплатежа1|типплатежа3
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
	 * Получение текстовки для платежа из настройки по ключу.
	 * @param messageKey - ключ.
	 * @return текст.
	 */
	public static String getSettingMessage(String messageKey)
	{
		return StringHelper.getEmptyIfNull(ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(messageKey));
	}

	/**
	 * @param document документ
	 * @return true - внешняя оплата (ОЗОН, EVENTIM, etc)
	 */
	public static boolean isExternalPayment(BusinessDocument document) throws BusinessException
	{
		if (document == null)
			return false;

		String formName = document.getFormName();
		return isInternetShopOrAeroflotPayment(document) || formName.equals("FNSPayment") || getUECOrder(document) != null;
	}

	/**
	 * @param document документ.
	 * @return true если является оплатой заказа интернет магазина или брони аэрофлота.
	 */
	public static boolean isInternetShopOrAeroflotPayment(BusinessDocument document)
	{
		if (document == null)
			return false;

		return EXTERNAL_PAYMENTS_FORM_NAMES.contains(document.getFormName());
	}

	/**
	 * Возвращает УЭК заказ по документу оплаты
	 * @param document - документ
	 * @return УЭК заказ
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
	 * Возвращает заявку о досрочном погашении кредита
	 *
	 * @param externalId - Идентификатор кредитного договора в ЕКП
	 * @return заява или null, если не приходил ответ
	 * @throws BusinessException если что-то пошло не так
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
	 * Возвращает заявку о досрочном погашении кредита
	 *
	 * @param externalId - Идентификатор кредитного договора в ЕКП
	 * @return заява или null, если не приходил ответ
	 * @throws BusinessException если что-то пошло не так
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
	 * Возвращает платёж, привязанный к заказу
	 *
	 * @param orderUuid - заказ
	 * @return платёж или null, если у заказа нет связанного платежа
	 * @throws BusinessException если что-то пошло не так
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
	 * проверка доступности сервиса для логина
	 * @param login логин
	 * @param serviceKey имя сервиса
	 * @return доступен или нет
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
			log.error("Ошибка получения доступа к сервису", e);
			return false;
		}
	}

	/**
	 * Получить список дополнительных полей платежа
	 * @param payment платеж
	 * @return писок дополнительных полей
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
	 * Является ли поставщик iTunes
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
			log.error("Ошибка определения поставщика для документа  с id=" + businessDocument.getId(), e);
			return false;
		}
	}

	/**
	 * Получить код поставщика
	 * @param payment платеж
	 * @return код поставщика
	 * @throws
	 */
	private static String getBillingCodeRecipient(AbstractBillingPayment payment)
	{
		return IDHelper.restoreOriginalIdWithAdditionalInfo(IDHelper.restoreOriginalId(payment.getReceiverPointCode()));
	}

	/**
	 * Получить актуальное наименование получателя платежа
	 * @param payment перевод с переменным количеством полей
	 * @return наименование получателя платежа
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
	 * Поддерживается ли на основе документа создание подписки на инвойсы
	 * @param document документ
	 * @return true - поддерживается
	 * @throws BusinessException
	 */
	public static boolean isSupportCreateInvoiceSubscription(BusinessDocument document) throws BusinessException
	{
		return isSupportCreateInvoiceSubscription(document, getDocumentProvider(document));
	}

	/**
	 * Является ли платеж в адрес iqw(обертка GateException в BusinessException)
	 * @param payment платеж
	 * @return true - является
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
	 * Определяем какие оперции можно совершить с платежом
	 * @param document - платеж
	 * @return  isSupportCreateInvoiceSubscription - создание подписки на инвойсы,
	 *  isCopyAllowed - повтор платежа
	 *  isTemplateSupported - создание шаблона
	 *  isAutoPaymentAllowed - создание автоплатежа
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
			log.error("Ошибка получения поставщика для документа с id = " + document.getId(), ex);
		}

		try
		{
			//Поддерживается ли на основе документа создание подписки на инвойсы
			actions.put("isSupportCreateInvoiceSubscription", isSupportCreateInvoiceSubscription (document, provider));
			//Разрешено ли создавать копию документа (Повтор платежа)
			actions.put("isCopyAllowed", isCopyAllowed(document, provider));
			//Разрешено ли создавать шаблон на основе документа
			actions.put("isTemplateSupported", isTemplateSupported(document, provider));
			//Разрешено ли создавать автоплатеж на основе документа
			actions.put("isAutoPaymentAllowed", isAutoPaymentAllowed(document, provider));
			//Разрешено ли создавать автоперевод на основе документа
			actions.put("isAutoTransferAllowed", isAutoTransferAllowed(document));
		}
		catch (Exception e)
		{
			log.error("Ошибка определения доступных операций для документа с id = " + document.getId(), e);
		}

		return actions;
	}

	public static boolean isMoneyBoxSupported(BusinessDocument document)
	{
		try
		{
			//подключение копилок доступно только для исполненых платежей.
			if(!document.getState().getCode().equals("EXECUTED"))
				return false;

			if((document instanceof AccountOpeningClaim))
			{
				AccountOpeningClaim claim = (AccountOpeningClaim) document;
				AccountLink toResource = PersonContext.getPersonDataProvider().getPersonData().findAccount(claim.getReceiverAccount());
				if (!isAccountForMoneyBoxMatched(toResource))
					return false;
				//проверяем права.
				if (!serviceService.isPersonServices(claim.getInternalOwnerId(), "CreateMoneyBoxPayment"))
					return false;
				return true;
			}

			if (!(document instanceof InternalTransfer))
				return false;

			InternalTransfer payment = (InternalTransfer) document;

			//тип платежа - перевод с карты на вклад?
			if (payment.getType() != CardToAccountTransfer.class)
				return false;

			//проверяем права.
			if (!serviceService.isPersonServices(payment.getInternalOwnerId(), "CreateMoneyBoxPayment"))
				return false;

			PaymentAbilityERL fromResource = payment.getChargeOffResourceLink();
			ExternalResourceLink toResource = payment.getDestinationResourceLink();

			if (!isAccountForMoneyBoxMatched((AccountLink) toResource))
				return false;

			if (!isCardForMoneyBoxMatched((CardLink) fromResource))
				return false;

			//для конверсионного перевода проверяем настройку-разрешено ли создание таких копилок.
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
	 * Возможно ли создание копилки с этого вклада
	 * @param link- accountLink
	 * @return true/false
	 */
	public static boolean isAccountForMoneyBoxMatched(AccountLink link)
	{
		ActiveMoneyBoxAvailableAccountFilter accountFilter = new ActiveMoneyBoxAvailableAccountFilter();
		return accountFilter.accept(link);
	}

	/**
	 * Возможно ли создание копилки с этого вклада
	 * @param linkId- внутренний идентификатор accountLinkа
	 * @return true/false
	 */
	public static boolean isAccountForMoneyBoxMatched(String linkId) throws BusinessLogicException, BusinessException
	{
		return isAccountForMoneyBoxMatched(PersonContext.getPersonDataProvider().getPersonData().getAccount(Long.valueOf(linkId)));
	}

	/**
	 * Возможно ли создание копилки с этой карты.
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

		// Внимание!!! Условие ApplicationConfig.getIt().getApplicationInfo().isMobileApi() - костыль! Требуется убрать! Будет переоткрыт запрос.
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
			log.error("Ошибка при определении доступности создания автоплатежа", e);
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
	 * Можно ли использовать эту карту в качестве карты зачисления для P2P автоплатежа
	 * @param link - линк карты
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
	 * Можно ли использовать эту карту в качестве карты списания для P2P автоплатежа
	 * @param link - линк карты
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
	 * @param document документ
	 * @return true - разрешен
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
	 * Проверка доступности поставщика в зависимости от приложения
	 * @param billingProvider поставщик услуг
	 * @return признак доступности поставщика
	 */
	private static boolean checkAvailablePayments(final ServiceProviderShort billingProvider)
	{
		return checkAvailablePayments(billingProvider, null);
	}

	/**
	 * Проверка доступности поставщика в зависимости от приложения
	 *
	 * @param provider поставщик услуг
	 * @param versionNumber версия мобильного приложения
	 * @return признак доступности поставщика
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
	 * Проверка доступности поставщика в зависимости от приложения
	 *
	 * @param shortcut поставщик услуг
	 * @param versionNumber версия мобильного приложения
	 * @return признак доступности поставщика
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
	 * Получение названия услуги оплаты клиентом, по документу.
	 * @param source - documentSource
	 * @return название услуги.
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

		// если создание шинного автоплатежа (автоподписки)
		if ((document instanceof JurPayment) && document.isLongOffer())
		{
			if (((JurPayment) document).getReceiverInternalId() != null)
				return "ClientCreateAutoPayment";
			return "ClientFreeDetailAutoSubManagement";
		}
		return source.getMetadata().getName();
	}

	/**
	 * Получение названия услуги оплаты сотрудником, по документу.
	 * @param source - documentSource
	 * @return название услуги.
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

		// если создание шинного автоплатежа (автоподписки)
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
	 * Разрешено ли создавать копию документа.
	 * @param document документ
	 * @return разрешено/нет.
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

			// Для оформления кредита повтор не нужен
			if (document instanceof ExtendedLoanClaim || document instanceof ShortLoanClaim)
				return false;

			if (document instanceof JurPayment)
			{
				JurPayment jurPayment = (JurPayment)document;
				//Для платежей по штрих-коду создание копии (повтор платежа) запрещено!.
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
			log.error("Ошибка опеределения доступности копии платежа",e);
			return false;
		}
	}

	/**
	 * Является ли документ созданием шинной автоподписки
	 * @param document документ
	 * @return true - является
	 */
	public static boolean isCreateAutoSubscription(BusinessDocument document)
	{
		return FormConstants.SERVICE_PAYMENT_FORM.equals(document.getFormName()) && document.isLongOffer();
	}

	/**
	 * Поддерживается ли на основе документа создание подписки на инвойсы
	 * @param document документ
	 * @return true - поддерживается
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
	 * Проходит ли билинговый платеж через iqwave
	 * @param document документ
	 * @return true - проходит
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
	 * Получение дефолтного значения процента для заявки на создание копилки.
	 * @return По умолчанию заполняется значением, заданным в настройках
	 */
	public static String getMoneyBoxDefaultPercent()
	{
		PropertyConfig config = ConfigFactory.getConfig(PropertyConfig.class);
		return config.getProperty("com.rssl.iccs.moneybox.percent.default");
	}


	/**
	 * Получение текста сообщения  выводимого в клиентском приложении при превышении лимита на запрос информации о клиенте в операции оплата физ лицу на карту по номеру телефона
	 * @return текст сообщения
	 */
	public static String getPersonPaymentLimitMessage()
	{
		return ConfigFactory.getConfig(ClientConfig.class).getInfoPersonPaymentLimitMessage();
	}

	/**
	 * Проверка на поддержку создания автоплатежа в 2 шага
	 * @param document автоплатеж
	 * @return true - да
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
	 * Проверка на поддержку создания автоплатежа в 2 шага
	 * @param document автоплатеж
	 * @return true - да
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
	 * Метод проверяет является ли документ платёжом в пользу Аэроэкспресса
	 * @param document Платеж
	 * @return true, если ПУ есть Аэроэкспресс, false в остальных случаях
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
			//Проверяем, является ли ПУ поставщиком  IQWave
			if (ESBHelper.isIQWavePayment(payment))
			{
				//Получаем id поставщика
				String IdWithAdditionalInfo = IDHelper.restoreOriginalIdWithAdditionalInfo(payment.getReceiverPointCode());
				String id = IDHelper.restoreOriginalId(IdWithAdditionalInfo);
				if (id != null)
				{
					//Проверка на совпадение id ПУ с id Аэроэкспресса
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
	 * Метод проверяет является ли документ заявкой на подключение УДБО
	 * @param document документ
	 * @return является ли документ заявкой на подключение УДБО
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
	 * Получить номер паспорта WAY по серии и номеру документа
	 * Между серией и номеров вставляется пробел (если есть и то, и то)
	 * Пробелы внутри номера и серии не вырезаются
	 * @param docSeries серия документа (null если нет)
	 * @param docNumber номер документа (null если нет)
	 * @return номер паспорта WAY
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
	 * Список типов операций, доступных для проведения по звонку в КЦ
	 * @param document документ
	 * @return доступно ли исполнение документа по звонку в КЦ
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

package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.business.longoffer.autopayment.mock.MockAutoPayment;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MockHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author osminin
 * @ created 31.01.2011
 * @ $Author$
 * @ $Revision$
 * Линк автоплатежа
 */
public class AutoPaymentLink extends EditableExternalResourceLink
{
	public static final String CODE_PREFIX = "auto-payment";
	private static final String MOCK_STRING = "";

	private static ExternalResourceService resourceService = new ExternalResourceService();
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

    private String number; // номер автоплатежа

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	public AutoPayment getValue()
	{
		return getAutoPayment();
	}

	private AutoPayment getAutoPayment()
	{
		try
		{
			AutoPaymentService autoPaymentService = GateSingleton.getFactory().service(AutoPaymentService.class);
			return GroupResultHelper.getOneResult(autoPaymentService.getAutoPayment(getExternalId()));
		}
		catch (InactiveExternalSystemException e)
	    {
		    throw e;
	    }
		catch (Exception e)
		{
			log.error("Ошибка при получении автоплатежа по externalId " + getExternalId(), e);
			return new MockAutoPayment(getExternalId());
		}
	}

	public void reset() throws BusinessException, BusinessLogicException
	{
		AutoPayment payment = getAutoPayment();
		if (!MockHelper.isMockObject(payment))
		{
			reset(payment, getLoginId());
		}
	}

	/**
	 * Выполняет сброс КЕШа для автоплатежа.
	 * @param payment автоплатеж.
	 * @param login логин.
	 */
	public static void reset(AutoPayment payment, CommonLogin login) throws BusinessException, BusinessLogicException
	{
		reset(payment, login.getId());
	}

	/**
	 * Выполняет сброс КЕШа для автоплатежа.
	 * @param payment автоплатеж.
	 * @param loginId логин.
	 */
	public static void reset(AutoPayment payment, Long loginId) throws BusinessException, BusinessLogicException
	{
		try
		{
			List<CardLink> cardLinks = resourceService.getLinks(loginId, CardLink.class);
			List<Card> cards = new ArrayList<Card>(cardLinks.size());

			for (CardLink link : cardLinks)
			{
				cards.add(link.toLinkedObjectInDBView());
			}

			GateSingleton.getFactory().service(CacheService.class).clearAutoPaymentCache(payment, cards.toArray(new Card[0]));
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

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * Получить обощенную информацию об алгоритме исполнения платежа
	 * @return обощенная информация об алгоритме исполнения платежа
	 */
	public String getExecutionEventType()
	{
		AutoPayment payment = getAutoPayment();
		if (!MockHelper.isMockObject(payment))
		{
			StringBuilder algoritmInfo = new StringBuilder();
			// У пороговых платежей заполнен пороговый лимит
			ExecutionEventType eventType = payment.getExecutionEventType();
			if (ExecutionEventType.REDUSE_OF_BALANCE == eventType)
			{
				// по РО отображается "При снижении баланса (номер телефона/электронного кошелька) до (сумма порогового значения) р."
				algoritmInfo.append("При снижении баланса ").append(payment.getRequisite()).append(" до ").append(payment.getFloorLimit().getDecimal().toString()).append(" руб.");
			}
			else if (ExecutionEventType.ONCE_IN_MONTH == eventType || ExecutionEventType.ONCE_IN_QUARTER == eventType || ExecutionEventType.ONCE_IN_YEAR == eventType)
			{
				//Для периодически повторяющихся платежей – отображается название периода и дата исполнения
				algoritmInfo.append(eventType.getDescription()).append(" ").append(payment.getStartDate().get(Calendar.DATE)).append("-го числа");
			}
			else if(ExecutionEventType.BY_INVOICE == eventType)
			{
				algoritmInfo.append("При выставлении счета на сумму, указанную в счете");
			}
			return algoritmInfo.toString();
		}
		return MOCK_STRING;
	}

	/**
	 * @return линк карты на которую оформелен автоплатеж
	 */
	public CardLink getCardLink()
	{
		AutoPayment payment = getAutoPayment();
		if (MockHelper.isMockObject(payment))
			return null;

		try
		{
			return resourceService.findLinkByNumber(getLoginId(), ResourceType.CARD, payment.getCardNumber());
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при получении карты №" + payment.getCardNumber(), e);
			return null;
		}
	}

	public ResourceType getResourceType()
	{
		return ResourceType.NULL;
	}

	public String getPatternForFavouriteLink()
	{
		return "$$autoPaymentName:" + this.getId();
	}

	public BillingServiceProvider getServiceProvider() throws BusinessException
	{
		return (BillingServiceProvider) serviceProviderService.findBySynchKey(getAutoPayment().getCodeService());
	}

	/**
	 * @return название реквизита автоплатежа
	 */
	public String getRequisiteName() throws BusinessException
	{
		// находим соответствующего поставщика, поддерживающего автоплатеж
		BillingServiceProvider provider = getServiceProvider();
		// если поставщика нет, или он не поддреживает автоплатеж => значение по умолчанию
		if(provider == null || !AutoPaymentHelper.checkAutoPaymentSupport(provider))
			return ConfigFactory.getConfig(DocumentConfig.class).getDefualtAutoPaymentReqisiteName();

		// находим ключевое поле и возвращаем его название
		for(Field field : provider.getFieldDescriptions())
			if(field.isKey())
				return field.getName();

		return ConfigFactory.getConfig(DocumentConfig.class).getDefualtAutoPaymentReqisiteName();
	}

	/**
	 * @return  возвращает статус автоплатежа
	 */
	public AutoPaymentStatus getStatusReport()
	{
		AutoPayment autoPayment = getAutoPayment();
		if (autoPayment instanceof AutoPayment)
			return autoPayment.getReportStatus();

		return null;
	}

	public String toString()
    {
        return "Автоплатеж externalId=" + getExternalId();
    }

	/**
	 * @return возвращает реквизит автоплатежа
	 */
	public String getRequisite()
	{
		return getExternalId().split("\\^")[1];
	}

	/**
	 * @return возвращает номер карты
	 */
	public String getCardNumber()
	{
		return getExternalId().split("\\^")[0];
	}

	/**
	 * @return возвращает код поставщика
	 */
	public String getReceiverCode()
	{
		return (getExternalId().split("\\^")[2]).split("\\|")[0];
	}
}

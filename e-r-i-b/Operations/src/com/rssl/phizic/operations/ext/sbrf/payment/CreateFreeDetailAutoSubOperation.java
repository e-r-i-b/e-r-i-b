package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.providers.AccountType;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.documents.payments.validators.ChecksOwnersPaymentValidator;
import com.rssl.phizic.business.documents.payments.validators.DocumentValidator;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.ActiveNotVirtualNotCreditOwnCardFilter;
import com.rssl.phizic.business.resources.external.LoginedCardFilter;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.payment.EditJurPaymentOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author vagin
 * @ created 03.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class CreateFreeDetailAutoSubOperation extends EditJurPaymentOperation
{
	private static final SimpleService simpleService = new SimpleService();
	public static final String NOT_SUPPORTED_AUTO_PAYMENT_ERROR_MESSAGE = "Вы не можете оформить автоплатеж в адрес данного получателя. Пожалуйста, выберете другого получателя";
	/**
	 * Поиск поставщиков услуг предоставляющих автоплатеж по идентификаторам
	 * @param ids - массив идентификаторов ПУ
	 * @return список ПУ
	 */
	public List<BillingServiceProvider> findAllowesAutoSubProvidersByIds(List<Long> ids) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(BillingServiceProvider.class);
		criteria.add(Expression.in("id", ids));
		criteria.add(Expression.eq(getAutoPaymentSupportAppType(), true));
		return simpleService.find(criteria);
	}

	/**
	 * Получить список ПУ
	 * @param fields - поля формы
	 * @return список ПУ предоставляющих автоплатеж.
	 * @throws BusinessException
	 */
	public List<BillingServiceProvider> getProviders(Map<String,Object> fields) throws BusinessException
	{
		String recipientIdList = ((String)fields.get("recipientList"));
		if (StringHelper.isEmpty(recipientIdList))
		{
			throw new BusinessException("Некорреткный список аттрибутов для отображения окна выбора ПУ по свободным реквизитам.");
		}
		List<Long> longIds = parseIds(recipientIdList.split("\\|"));
		return findAllowesAutoSubProvidersByIds(longIds);
	}

	/**
	 * Найти поставщиков предотавляющих автоплатеж в справочнике поставщиков
	 * поиск производится по реквизитам ReceiverAccount, ReceiverINN, ReceiverBIC
	 * с учетои типа источника списания и доступных для него поставщиков.
	 * @return список ПУ для автоплатежа по заданным реквизитам
	 */
	public List<BillingServiceProvider> findRecipientByRequisites() throws BusinessException, BusinessLogicException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(BillingServiceProvider.class);
		criteria.add(Expression.eq("state", ServiceProviderState.ACTIVE));
		criteria.add(Expression.eq("account", getReceiverAccount()));
		criteria.add(Expression.eq("INN", getReceiverINN()));
		criteria.add(Expression.eq("BIC", getReceiverBIC()));
		criteria.add(Expression.in("accountType", new AccountType[]{AccountType.ALL, AccountType.CARD}));

		//для сотрудника запрещено создавать автоплатежи IQwave
		AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
		Adapter adapter = config.getCardTransfersAdapter();
		//После того как получили список поставщиков, необходимо отобрать только тех, кому разрешен автоплатеж.
		boolean isAutoPaymentSupported = false;
		List<BillingServiceProvider> providers = new ArrayList<BillingServiceProvider>();
		for (Object provider : simpleService.find(criteria))
		{
			BillingServiceProvider prov = (BillingServiceProvider) provider;
			if ((prov.isAutoPaymentSupported()||prov.isAutoPaymentSupportedInApi()||prov.isAutoPaymentSupportedInATM()) && (((String)prov.getSynchKey()).indexOf(adapter.getUUID()) < 0))
			{
				isAutoPaymentSupported = true;
				providers.add(prov);
			}
		}
		// если поставщиков нет в базе, то ищем их в биллинге
		if (CollectionUtils.isNotEmpty(providers) && !isAutoPaymentSupported)
			throw new BusinessLogicException(NOT_SUPPORTED_AUTO_PAYMENT_ERROR_MESSAGE);

		return providers;
	}

	/**
	 * Поиск ПУ поддерживающего АП по ключевым реквизитам
	 * @return список ПУ
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<Object[]> findRecipients() throws BusinessException, BusinessLogicException
	{
		boolean isAutoPaymentSupported = false;
		List<Object[]> result = new ArrayList<Object[]>();
		for(Object[] recipientRec: super.findRecipients())
		{
			if(recipientRec[12].equals("1")||recipientRec[13].equals("1")||recipientRec[14].equals("1"))
			{
				isAutoPaymentSupported = true;
				result.add(recipientRec);
			}
		}
		// если поставщиков нет в базе, то ищем их в биллинге
		if (CollectionUtils.isNotEmpty(result) && !isAutoPaymentSupported)
			throw new BusinessLogicException(NOT_SUPPORTED_AUTO_PAYMENT_ERROR_MESSAGE);

		return result;
	}

	protected DocumentValidator getDocumentValidator()
	{
		return new ChecksOwnersPaymentValidator();
	}

	public List<PaymentAbilityERL> getChargeOffResources() throws BusinessException, BusinessLogicException
	{
		List<PaymentAbilityERL> result = new ArrayList<PaymentAbilityERL>();
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

		if (PersonHelper.getLastClientLogin().getLastUserVisitingMode() == UserVisitingMode.EMPLOYEE_INPUT_BY_CARD)
			result.addAll(personData.getCards(new LoginedCardFilter(false)));
		else
			result.addAll(personData.getCards(new ActiveNotVirtualNotCreditOwnCardFilter()));

		return result;
	}

	private static List<Long> parseIds(String[] stringIds) throws BusinessException
	{
		try
		{
			List<Long> result = new ArrayList<Long>();
			for (String s : stringIds)
			{
				result.add(Long.valueOf(s));
			}
			return result;
		}
		catch(NumberFormatException e)
		{
			throw new BusinessException("Некорректный формат списка идентификаторов поставщиков услуг.",e);
		}
	}

	/**
	 * Возвращает поле для проверки доступности автоплатежа в зависимости от приложения
	 * @return поле для проверки доступности автоплатежа в зависимости от приложения
	 */
	private String getAutoPaymentSupportAppType()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		if (applicationInfo.isATM())
			return "autoPaymentSupportedInATM";
		else if (applicationInfo.isMobileApi())
			return "autoPaymentSupportedInApi";
		else
			return "autoPaymentSupported";
	}
}

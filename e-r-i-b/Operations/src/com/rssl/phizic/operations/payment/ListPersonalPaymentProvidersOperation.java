package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.payments.PersonalPaymentCard;
import com.rssl.phizic.business.payments.ServiceProviderShorcut;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.operations.dictionaries.PersonalPaymentCardOperationBase;
import com.rssl.phizic.operations.ext.sbrf.payment.ListServicesPaymentOperation;
import com.rssl.phizic.business.regions.RegionHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import java.util.*;

/**
 * @author Erkin
 * @ created 24.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция получения списка поставщиков,
 * принимающих персональные платежи (ПП) текущего пользователя
 */
public class ListPersonalPaymentProvidersOperation extends PersonalPaymentCardOperationBase
{
	private static final PaymentRecipientGateService recipientGateService
			= GateSingleton.getFactory().service(PaymentRecipientGateService.class);

	private List<ServiceProviderShorcut> providers
			 = Collections.emptyList();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Инициализация
	 */
	public void initialize() throws BusinessException, BusinessLogicException
	{
		// 1. Проверяем, есть ли карта ПП у клиента
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ActivePerson activePerson = personData.getPerson();
		if (activePerson.getTrustingPersonId() != null)
			super.initialize(activePerson.getTrustingPersonId());
		else super.initialize(activePerson.getId());

		// 1.1 Карты нет, заканчиваем работу пустым списком
		PersonalPaymentCard card = getCard();
		if (card == null) {
			providers = Collections.emptyList();
			log.trace("У пользователя нет карты ИСППН. " +
					"LOGIN_ID=" + activePerson.getLogin().getId());
			return;
		}

		// 2. Получаем список поставщиков из шлюза
		List<Recipient> gateRecipients = getGateRecipients();

		// 2.1 Поставщиков в шлюзе нет
		if (CollectionUtils.isEmpty(gateRecipients)) {
			providers = Collections.emptyList();
			if (log.isInfoEnabled())
				log.info("У пользователя есть карта ИСППН. " +
						"Но по ней Шлюз не вернул ни одного ИСППН-поставщика. " +
						"LOGIN_ID:" + activePerson.getLogin().getId() + ". " +
						"№ карты ИССПН: " + card.getCardNumber() + ". " +
						"Billing: " + getBilling());
			return;
		}

		// 3. Из справочника выбираем поставщиков,
		// которые по ключу совпадают со списком поставщиков п.2
		List<String> recipientIds = collectRecipientIdList(gateRecipients);
		providers = getServiceProviders(recipientIds);

		if (log.isWarnEnabled()) {
			if (CollectionUtils.isEmpty(providers))
				log.warn("У пользователя есть карта ИСППН. " +
						"Шлюз вернул по ней ИСППН-поставщиков, которых нет в базе ЕРИБ. " +
						"LOGIN_ID:" + activePerson.getLogin().getId() + ". " +
						"№ карты ИССПН: " + card.getCardNumber() + ". " +
						"Billing: " + getBilling());

			if (providers.size() != recipientIds.size())
				log.warn("Не удалось найти в базе ЕРИБ всех поставщиков, указанных Шлюзом! " +
						"Шлюз вернул поставщиков с такими EXTERNAL_ID: [" + StringUtils.join(recipientIds, "; ") + "]. " +
						"В базе ЕРИБ найдены такие поставщики: [" + StringUtils.join(providers, "; ") + "] ");
		}
	}

	private List<ServiceProviderShorcut> getServiceProviders(List<String> externalIds)
			throws BusinessException
	{
		try {
			final ListServicesPaymentOperation operation = new ListServicesPaymentOperation();
			operation.setServicelessExternalIdList(externalIds);
			operation.setProviderPerServiceLimit(null);

			final Region region = RegionHelper.getCurrentRegion();

			List<Object> result = HibernateExecutor.getInstance()
					.execute(new HibernateAction<List<Object>>()
			{
				public List<Object> run(Session session) throws Exception
				{
					Query query = operation.createQuery("listPersonalPayments");
					query.setParameter("region_id", (region == null) ? null : region.getId());
					Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
					query.setParameter("client_type", person.getCreationType().toString());
					return query.executeList();
				}
			});

			if (result.isEmpty())
				return Collections.emptyList();

			List<ServiceProviderShorcut> shorcuts
					= new ArrayList<ServiceProviderShorcut>(result.size());
			for (Object o : result) {
				Object[] fields = (Object[]) o;
				ServiceProviderShorcut shorcut = new ServiceProviderShorcut();
				shorcut.setId((Long) fields[0]);
				shorcut.setName((String) fields[1]);
				shorcuts.add(shorcut);
			}
			return shorcuts;
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	private List<Recipient> getGateRecipients()	throws BusinessLogicException, BusinessException
	{
		PersonalPaymentCard card = getCard();
		if (card == null)
			return Collections.emptyList();

		Billing billing = getBilling();
		if (billing == null)
			throw new BusinessException("Для карты персональных платежей не указан биллинг. " +
					"ID карты: " + card.getId());

		try {
			return recipientGateService
					.getPersonalRecipientList(card.getCardNumber(), billing);

		}
		catch (GateLogicException ex)
		{
			throw new BusinessLogicException(ex);
		}
		catch (GateException ex)
		{
			throw new BusinessException(ex);
		}
	}

	private List<String> collectRecipientIdList(List<Recipient> recipients)
	{
		List<String> providerKeyLikes = new ArrayList<String>(recipients.size());
		for (Recipient recipient : recipients)
			providerKeyLikes.add(recipient.getSynchKey().toString());
		return providerKeyLikes;
	}

	/**
	 * @return список поставщиков персональных платежей
	 * либо пустой список
	 */
	public List<ServiceProviderShorcut> getProviders()
	{
		return Collections.unmodifiableList(providers);
	}
}

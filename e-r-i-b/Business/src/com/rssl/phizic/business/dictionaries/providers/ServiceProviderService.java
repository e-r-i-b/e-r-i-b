package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.asyncSearch.AsynchSearchObjectState;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.providers.wrappers.ServiceProviderShortcut;
import com.rssl.phizic.business.documents.payments.AutoSubType;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.business.locale.MultiLocaleSimpleService;
import com.rssl.phizic.business.locale.MultiLocaleDetachedCriteria;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.*;

import java.util.*;

/**
 * @author akrenev
 * @ created 14.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class ServiceProviderService extends MultiInstanceServiceProviderService
{
	private static final String QUERY_PREFIX_PAYMENT_FORM_INFO = QUERY_PREFIX + "paymentFormInfo.";
	private static final String QUERY_PREFIX_SERVICE_PROVIDER_SERVICE = "com.rssl.phizic.business.dictionaries.providers.ServiceProviderService";
	private static final MultiLocaleSimpleService multiLocaleSimpleService = new MultiLocaleSimpleService();
	/**
	 * Получение справочника поставщиков услуг Мобильного Банка
	 * @return список поставщиков
	 * @throws BusinessException
	 */
	public List<BillingServiceProvider> getMBProviders() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<BillingServiceProvider>>()
			{
				public List<BillingServiceProvider> run(Session session)
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getMBProviders");
					return query.list();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение получателя по id
	 * @param id получателя
	 * @return получатель
	 * @throws BusinessException
	 */
	public ServiceProviderBase findById(Long id) throws BusinessException
	{
		return multiLocaleSimpleService.findById(ServiceProviderBase.class, id, null);
	}

	/**
	 * Возвращает название поставщика услуг по идентификатору
	 * @param id идентификатор
	 * @return название поставщика
	 * @throws BusinessException
	 */
	public Long findImageHelpById(Long id) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderBase.class)
				.add(Restrictions.eq("id", id))
				.setProjection(Projections.property("imageHelpId"));
		return simpleService.findSingle(criteria);
	}

	/**
	 * Подерживает ли поставщик услуг оплату с кредитных карт.
	 * @param id идентификатор
	 * @return isCreditCardSupported
	 * @throws BusinessException
	 */
	public Boolean findCreditCardSupportedById(Long id) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderBase.class)
				.add(Restrictions.eq("id", id))
				.setProjection(Projections.property("creditCardSupported"));
		return simpleService.findSingle(criteria);
	}
	/**
	 * Получение поставщика по id
	 * @param id поставщика
	 * @return поставщика для отображения
	 * @throws BusinessException
	 */
	public ServiceProviderShort findShortProviderById(final Long id) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ServiceProviderShort>()
			{
				public ServiceProviderShort run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "findShortProviderById").setParameter("id", id);
					return (ServiceProviderShort) query.uniqueResult();
				}
			});

		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * Сохранение получателя
	 * @param serviceProvider получатель
	 * @return добавленный получатель
	 * @throws BusinessException, DublicateServiceProviderException
	 */
	public ServiceProviderBase addOrUpdate(final ServiceProviderBase serviceProvider) throws BusinessException, DublicateServiceProviderException
	{
		AsynchSearchObjectState state = serviceProvider.getId() != null ? AsynchSearchObjectState.UPDATED : AsynchSearchObjectState.INSERTED;
		addOrUpdate(serviceProvider, null);
		asynchSearchDictionariesService.addObjectInfoForAsynchSearch(serviceProvider, state);
		return serviceProvider;
	}

	/**
	 * Удаление получателя
	 * @param serviceProvider удаляемый получатель
	 * @throws BusinessException
	 */
	public void remove (final ServiceProviderBase serviceProvider) throws BusinessLogicException, BusinessException
	{

		remove(serviceProvider, null);
		asynchSearchDictionariesService.addObjectInfoForAsynchSearch(serviceProvider, AsynchSearchObjectState.DELETED);
	}

	/**
	 * Получение получателя по ключу синхронизации
	 * @param key получателя
	 * @return получатель
	 * @throws BusinessException
	 */
	public ServiceProviderBase findBySynchKey(String key) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderBase.class).add(Expression.eq("synchKey", key));
		return simpleService.findSingle(criteria);
	}


	/**
	 * Получение получателя по ключу синхронизации
	 * @param key получателя
	 * @return получатель
	 * @throws BusinessException
	 */
	public ServiceProviderShort findShortProviderBySynchKey(final String key) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ServiceProviderShort>()
			{
				public ServiceProviderShort run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "findShortProviderBySynchKey").setParameter("synchKey", key);
					return (ServiceProviderShort) query.uniqueResult();
				}
			});

		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * Получить список доп. полей получателя
	 * @param recipient получатель
	 * @return список доп полей
	 * @throws BusinessException
	 */
	public List<? extends Field> getRecipientFields(Recipient recipient) throws BusinessException
	{
		if (!(recipient instanceof BillingServiceProvider))
			throw new BusinessException("Некорректный тип поставщика услуг");

		return ((BillingServiceProviderBase) recipient).getFieldDescriptions();
	}

	/**
	 * Получить список ключевых полей получателя
	 * @param recipient получатель
	 * @return список доп полей
	 * @throws BusinessException
	 */
	public List<? extends Field> getRecipientKeyFields(Recipient recipient) throws BusinessException
	{
		if (!(recipient instanceof BillingServiceProvider))
			throw new BusinessException("Некорректный тип поставщика услуг");

		ArrayList<Field> keyFields = new ArrayList<Field>();

		for (Field field: ((BillingServiceProviderBase) recipient).getFieldDescriptions())
		{
			if (field.isKey())
			{
				keyFields.add(field);
			}
		}
		return keyFields;
	}

	/**
	 * Удалить список доп. полей получателя
	 * @param recipient получатель
	 * @throws BusinessException
	 */
	public void removeRecipientFields(final Recipient recipient) throws BusinessException
	{
		if (!(recipient instanceof BillingServiceProviderBase))
			return;

		List<FieldDescription> fields = (List<FieldDescription>) getRecipientFields(recipient);
		if (fields.size() == 0)
			return;

		final List<Long> ids = new ArrayList<Long>();
		for (FieldDescription field : fields)
		{
			ids.add(field.getId());
		}

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "removeRecipientFields");
					query.setParameterList("ids", ids).executeUpdate();
					session.flush();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить налогового поставщика услуг по INN
	 * @param inn
	 * @return поставщик услуг
	 * @throws BusinessException
	 */

	public TaxationServiceProvider findTaxProviderByINN(final String inn) throws BusinessException
	{
		return findTaxProviderByINN(inn, null);
	}

	public List<FieldDescription> getKeyFieldDescriptionsByServiceId(final List<Long> providerIds) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<FieldDescription>>()
			{
				public List<FieldDescription> run(Session session)
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getKeyFieldDescriptionsByProviderList");
					query.setParameterList("providerIds", providerIds);
					return query.list();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить поставщика услуг по его коду в Мобильном Банке
	 * @param mobilebankCode - код поставщика в МБ
	 * @return поставщик
	 * @throws BusinessException
	 */
	public BillingServiceProvider findByMobileBankCode(final String mobilebankCode) throws BusinessException
	{
		return findByMobileBankCode(mobilebankCode, null);
	}

	/**
	 * Получить поставщиков услуг по их кодам в Мобильном Банке
	 * @param mobilebankCodes - коды поставщиков в МБ
	 * @return список поставщиков
	 * @throws BusinessException
	 */
	public List<BillingServiceProvider> getAllByMobilebankCodes(final Collection<String> mobilebankCodes)
			throws BusinessException
	{
		if (CollectionUtils.isEmpty(mobilebankCodes))
			return Collections.emptyList();

		try {
			HibernateExecutor executor = HibernateExecutor.getInstance();
			return executor.execute(new HibernateAction<List<BillingServiceProvider>>()
			{
				public List<BillingServiceProvider> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getAllByMobileBankCode")
							.setParameterList("mobilebankCodes", mobilebankCodes);
					return query.list();
				}
			});

		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	/**
	 * Возвращает коды подуслуги для поставщиков с указанными кодами в Мобильном Банке
	 * @param mobilebankCodes - коллекция кодов поставщиков в Мобильном Банке
	 * @return мап "код_поставщика_в_МБ -> код_подуслуги"
	 */
	public Map<String, String> getSubServiceCodes(final Collection<String> mobilebankCodes) throws BusinessException
	{
		if (CollectionUtils.isEmpty(mobilebankCodes))
			return Collections.emptyMap();

		// 1. Получение списка пар "код получателя в Мобильном банке, код подуслуги"
		List<Object[]> rs;
		try
		{
			HibernateExecutor executor = HibernateExecutor.getInstance();
			rs = executor.execute(new HibernateAction<List<Object[]>>()
			{
				public List<Object[]> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getSubServiceCodesByMobileBankCode")
							.setParameterList("mobilebankCodes", mobilebankCodes)
							.setString("subserviceFieldName", PaymentFieldKeys.SUBSERVICE_CODE);
					return query.list();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
		if (rs.isEmpty())
			return Collections.emptyMap();

		// 2. Составление мапа
		Map<String, String> map = new HashMap<String, String>(rs.size());
		for (Object[] row : rs)
			map.put((String)row[0], (String)row[1]);
		return map;
	}

	/**
	 * Получение имени и идентификатора иконки поставщиков по списку их кодов
	 * @param codes
	 * @return
	 * @throws BusinessException
	 */
	public List<Object[]> getNameAndImageByCodes(final Set<String> codes) throws BusinessException
	{
		if (CollectionUtils.isEmpty(codes))
			return Collections.emptyList();
		try
		{
			HibernateExecutor executor = HibernateExecutor.getInstance();
			return executor.execute(new HibernateAction<List<Object[]>>()
			{
				public List<Object[]> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getNameAndImageByCodes");
					query.setParameterList("codes", codes);
					return query.list();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}

	}

	/**
	 * Получение идентификатора иконки поставщика по идентификатору поставщика
	 * @param id идентификатор поставщика
	 * @return идентификатор иконки поставщика
	 * @throws BusinessException
	 */
	public Long getImageIdById(final Long id) throws BusinessException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор поставщика не может быть null.");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Long>()
			{
				public Long run(Session session) throws Exception
				{
					return (Long) session.getNamedQuery("com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase.getImageById")
							.setParameter("id", id)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Опорный объект: UN_CODE_S_CODE_R_SBOL
	 * Предикаты доступа:    access("THIS_"."CODE_RECIPIENT_SBOL"=:CODE AND
	                                "THIS_"."BILLING_ID"=TO_NUMBER(:BILLING_ID))
	 * Кардинальность: количество поставщиков с одинаковыми CODE_RECIPIENT_SBOL и BILLING_ID
	 *
	 * получить список "услуг" активного(подключенного) поставщика(не путать с группами услуг для организации иерархии во view)
	 * Услуги поставщика - это множество постащиков с одинаковыми CodeRecipientSBOL.
	 * На самом деле это 1 поставщик с точки природы(1 лицо), но предоставляющий разные услуги в биллинге(Service)
	 * например, поставщик мегафон  представляется 2 записями:
	 * 1) постащик мегафон с биллинговой услугой "сотовая связь"
	 * 2) постащик мегафон с биллинговой услугой "интернет"
	 * @param provider поставщик(услуга)
	 * @param onlyActive ищем только в статусе ACTIVE, или ACTIVE и MIGRATION
	 * @return список "услуг" (поставщиков).
	 */
	public List<ServiceProviderShort> getProviderAllServices(final BillingServiceProviderBase provider,final boolean onlyActive) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ServiceProviderShort>>()
			{
				public List<ServiceProviderShort> run(Session session)
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getProviderAllServices");
					query.setParameter("codeRecipientSBOL", provider.getCodeRecipientSBOL());
					query.setParameter("billingId", provider.getBilling().getId());
					if (onlyActive)
						query.setParameterList("state", new String[]{ServiceProviderState.ACTIVE.toString()});
					else
						query.setParameterList("state", new String[]{ServiceProviderState.ACTIVE.toString(), ServiceProviderState.MIGRATION.toString()});
					return query.list();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * получить список доступных из API "услуг" активного(подключенного) поставщика(не путать с группами услуг для организации иерархии во view)
	 * @param provider биллинговый поставщик(услуга)
	 * @return список "услуг" (поставщиков).
	 */
	public List<BillingServiceProvider> getProviderMobileServices(BillingServiceProvider provider) throws BusinessException
	{
		DetachedCriteria criteria = MultiLocaleDetachedCriteria.forClassInLocale(BillingServiceProvider.class, MultiLocaleContext.getLocaleId());
		criteria.add(Expression.eq("codeRecipientSBOL", provider.getCodeRecipientSBOL()));
		criteria.add(Expression.eq("state",ServiceProviderState.ACTIVE));
		criteria.add(Expression.eq("billing",provider.getBilling()));
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (applicationConfig.getApplicationInfo().getApplication() == Application.atm)
			//Для устройств самообслуживания - дополнительная проверка на доступность
			criteria.add(Expression.or(Expression.eq("availablePaymentsForAtmApi", true), Expression.eq("autoPaymentSupportedInATM", true)));
		else if (applicationConfig.getApplicationInfo().getApplication() == Application.socialApi)
        {
            criteria.add(Expression.or(Expression.eq("availablePaymentsForSocialApi", true), Expression.eq("autoPaymentSupportedInSocialApi", true)));
        }
        else
		{
			criteria.add(Expression.or(Expression.eq("availablePaymentsForMApi", true), Expression.eq("autoPaymentSupportedInApi", true)));
			criteria.add(Expression.le("versionAPI", MobileApiUtil.getApiVersionNumber().getSolid()));
		}
		return simpleService.find(criteria);
	}

	/**
	 * получить список всех полей постащика(ов) для всех его биллинговых услуг.
	 * @param provider поставщик(услуга)
	 * @return идентифкация принадлежности поля конкретному поставщику в разрезе услуги должна осуществляться по
	 * FieldDescriptio#getHolderId.
	 */
	public List<FieldDescription> getProviderAllServicesFields(final BillingServiceProviderBase provider) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<FieldDescription>>()
			{
				public List<FieldDescription> run(Session session)
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getFieldDescriptionsByCodeRecipientSBOL");
					query.setParameter("codeRecipientSBOL", provider.getCodeRecipientSBOL());
					query.setParameter("billingId", provider.getBilling().getId());
					return query.list();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * получает активного поставщика внешенй услуги (фнс или интернет-магазин) по названию системы
	 * @param systemName - название системы
	 * @return найденный интернет-поставщик или mull
	 * @throws BusinessException
	 */
	public InternetShopsServiceProvider getRecipientActivityBySystemName(String systemName) throws BusinessException
	{
		DetachedCriteria dc = DetachedCriteria.forClass(InternetShopsServiceProvider.class);
		dc.add(Expression.eq("codeRecipientSBOL", systemName));
		dc.add(Expression.eq("state", ServiceProviderState.ACTIVE));
		return simpleService.findSingle(dc);
	}

	/**
	 * Получение поставщиков с одним  codeRecipientSBOL поддерживающих создание шинного автоплатежа(автоподписки)
	 * @param provider поставщик, на которого оринтируемся
	 * @return список поставщиков
	 * @throws BusinessException
	 */
	public List<ServiceProviderShort> getProviderAllServicesSupportAutoPay(final BillingServiceProvider provider) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ServiceProviderShort>>()
			{
				public List<ServiceProviderShort> run(Session session)
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getProviderAllServiceSupportAutoPays");
					query.setParameter("codeRecipientSBOL", provider.getCodeRecipientSBOL());
					query.setParameter("billingId", provider.getBilling().getId());
					query.setParameter("state", ServiceProviderState.ACTIVE.toString());
					query.setParameterList("accountType", new String[]{AccountType.ALL.toString(), AccountType.CARD.toString()});
					ApplicationConfig applicationConfig = ApplicationConfig.getIt();
										//для сотрудника запрещено создавать автоплатежи через iqwave.
					if (applicationConfig.getLoginContextApplication() == Application.PhizIA)
					{
						Adapter cardTransferAdapter = ConfigFactory.getConfig(AdaptersConfig.class).getCardTransfersAdapter();
		                if (provider.getBilling().getAdapterUUID().equals(cardTransferAdapter.getUUID()))
			            	return new ArrayList<ServiceProviderShort>();
					}
					String autoPaymentSupportAppType = getAutoPaymentSupportAppType();
					query.setBoolean("autoPaymentSupported",      autoPaymentSupportAppType.equals("autoPaymentSupported"));
					query.setBoolean("autoPaymentSupportedInATM", autoPaymentSupportAppType.equals("autoPaymentSupportedInATM"));
					query.setBoolean("autoPaymentSupportedInApi", autoPaymentSupportAppType.equals("autoPaymentSupportedInApi"));

					return query.list();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поиск поставщика по коду коду во внешней системе, коду сервиса и билингу
	 * @param code код поставщика во внешней системе
	 * @param codeService код сервиса
	 * @param billing билинг
	 * @return билинговый поставщика
	 * @throws BusinessException
	 */
	public BillingServiceProvider find(String code, String codeService, Billing billing) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(BillingServiceProvider.class);
		criteria.add(Expression.eq("code", code));
		criteria.add(Expression.eq("codeService", codeService));
		criteria.add(Expression.eq("billing",billing));
		return simpleService.findSingle(criteria);
	}

	/**
	 * Поиск поставщика по коду постащика в билинге, коду услуги в билинге и uuid билинга
	 * @param code коду постащика в билинге
	 * @param codeService коду услуги в билинге
	 * @param uuid uuid билинга
	 * @return список идентификаторов
	 * @throws BusinessException
	 */
	public ServiceProviderBase find(final String code, final String codeService, final String uuid) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ServiceProviderBase>()
			{
				public ServiceProviderBase run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "findProvider");
					query.setParameter("code", code);
					query.setParameter("codeService", codeService);
					query.setParameter("uuid", uuid);

					//noinspection unchecked
					return (ServiceProviderBase) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Найти поставщиков по коду постащика в билинге и билингу
	 * @param code код поставщика во внешней системе
	 * @param billing билинг
	 * @return список поставщиков
	 * @throws BusinessException
	 */
	public List<BillingServiceProvider> find(String code, Billing billing) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(BillingServiceProvider.class);
		criteria.add(Expression.eq("code", code));
		criteria.add(Expression.eq("billing", billing));
		return simpleService.find(criteria);
	}

	/**
	 * Получить доп информацию формы платежа для по поставщику и каналу
	 * @param providerId идентификатор поставщика
	 * @param channel канал
	 * @return доп инфа для формы просмотра
	 * @throws BusinessException
	 */
	public String getPaymentFormInfo(final Long providerId, final CreationType channel) throws BusinessException
	{
		try
		{
			switch (channel)
			{
				case mobile:
                case social:
				case internet:
				{
					return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
					{
						public String run(Session session) throws Exception
						{
							Query query = session.getNamedQuery(QUERY_PREFIX_PAYMENT_FORM_INFO + channel);
							query.setParameter("providerId", providerId);
							return (String) query.uniqueResult();
						}
					});
				}
				default:
					return null;
			}
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
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

	/**
	 * Список поставщиков, по которым можно создать автоплатеж и для которых выставлена галка "показывать в промо-блоке Автоплатежей"
	 * @param regionId - регоин отображения
	 * @param providerCount  - количество пооставщиков
	 * @return список поставщиков
	 * @throws BusinessException
	 */
	public List<ServiceProviderShort> getProviderRegions(final Long regionId, final int providerCount) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ServiceProviderShort>>()
			{
				public List<ServiceProviderShort> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getAutoPayPromoBlock");
					query.setParameter("IQWaveUUID", ServiceProviderHelper.getIQWaveUUID());
					query.setParameter("regionId", regionId);
					query.setParameter("providerCount", providerCount);
					query.setBoolean("isIQWaveAutoPaymentPermit", ServiceProviderHelper.isESBAutoPaymentPermit());
					query.setBoolean("isESBAutoPaymentPermit", ServiceProviderHelper.isIQWaveAutoPaymentPermit());
					return (List<ServiceProviderShort>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Проверяет является ли поставщик мобильным оператором
	 * @param externalId - внешний идентификатор поставщика
	 * @return результат проверки
	 * @throws BusinessException
	 */
	public Boolean isMobileProvider(final String externalId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getCountOfMobileProvider")
							.setParameter("externalId", externalId);
					return ((Integer) query.uniqueResult()) > 0;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение списка поставщиков по реквизитам
	 * @param account - номер счета
	 * @param INN  - ИНН
	 * @param BIC  - БИК банка
	 * @param accountType  - тип источника списания
	 * @return   - список из записей
	 * @throws BusinessException
	 */
	public List<Object[]> getProviderByRequisites(final String account,final String INN,final String BIC, final String[] accountType) throws BusinessException
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(),"com.rssl.phizic.business.dictionaries.providers.queries.searchOrderByRegion");
			query.setParameter("account", account);
			query.setParameter("BIC", BIC);
			query.setParameter("INN", INN);
			query.setParameterList("accountType", accountType);
			return  query.executeList();
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение списка поставщиков по реквизитам с учетом "домашнего" региона
	 * @param account - номер счета
	 * @param INN  - ИНН
	 * @param BIC  - БИК банка
	 * @param accountType - тип источника списания
	 * @param parentRegionId - id родительского региона для "домашнего"
	 * @param region - id "домашнего" региона
	 * @return список из записей
	 * @throws BusinessException
	 */
	public List<Object[]> getProviderByRequisitesAndRegion(final String account,final String INN,final String BIC, final String[] accountType,final Long parentRegionId, final Long region) throws BusinessException
	{

		try
		{
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.dictionaries.providers.queries.searchInCurrentRegion");
			query.setParameter("account", account);
			query.setParameter("BIC", BIC);
			query.setParameter("INN", INN);
			query.setParameter("region_id", region);
			query.setParameter("isRegion", Boolean.toString(region != null));
			query.setParameter("isParentRegionId", parentRegionId == null ? "false" : "true");
			query.setParameter("parent_region_id", parentRegionId);
			query.setParameterList("accountType", accountType);
			return  query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение списка регоинов, в которых доступны указанные поставщики
	 * @param providers - список id поставщиков
	 * @return список из записей
	 * @throws BusinessException
	 */
	public List<Object[]> getRegionsForProviders(final List<String> providers) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Object[]>>()
			{
				public List<Object[]> run(Session session)
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "regionsForProviders");
					query.setParameterList("providers", providers);
					return  query.list();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}
	/**
	 * Возвращает статус поставщика услуг по идентификатору
	 * @param id идентификатор
	 * @return статус поставщика
	 * @throws BusinessException
	 */
	public ServiceProviderState findStateById(Long id) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderBase.class)
				.add(Restrictions.eq("id", id))
				.setProjection(Projections.property("state"));
		return simpleService.findSingle(criteria);
	}

	/**
	 * Возвращает группу риска поставщика услуг по идентификатору
	 * @param id идентификатор
	 * @return статус поставщика
	 * @throws BusinessException
	 */
	public GroupRisk findGroupRiskById(final Long id) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<GroupRisk>()
			{
				public GroupRisk run(Session session)
				{
					Query query = session.getNamedQuery(QUERY_PREFIX_SERVICE_PROVIDER_SERVICE + ".findGroupRiskById");
					query.setParameter("receiverId", id);
					return (GroupRisk) query.uniqueResult();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Разрешена ли для переданного приложения оплата поставщика услуг
	 * @param id идентифкатор поставщика услуг
	 * @param applicationInfo информация о приложении
	 * @return да /нет
	 */
	public boolean isPaymentAllowedByProviderId(Long id, ApplicationInfo applicationInfo) throws BusinessException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор поставщика должен быть задан");
		}
		if (applicationInfo == null)
		{
			throw new IllegalArgumentException("Информация о приложении должна быть задана");
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderBase.class).add(Restrictions.eq("id", id));

		if (applicationInfo.isWeb())
		{
			criteria.setProjection(Projections.property("availablePaymentsForInternetBank"));
		}
		else if (applicationInfo.isATM())
		{
			criteria.setProjection(Projections.property("availablePaymentsForAtmApi"));
		}
		else if (applicationInfo.isSocialApi())
		{
			criteria.setProjection(Projections.property("availablePaymentsForSocialApi"));
		}
		else if (applicationInfo.isMobileApi())
		{
			criteria.setProjection(Projections.property("availablePaymentsForMApi"));
		}
		else if (applicationInfo.isSMS())
		{
			criteria.setProjection(Projections.property("availablePaymentsForErmb"));
		}
		else
		{
			return false;
		}

		Boolean allowed = simpleService.findSingle(criteria);
		if (allowed == null)
		{
			return false;
		}
		return allowed;
	}

	/**
	 * Получить первую из услуг для заданного провайдера
	 * @param providerId поставщика
	 * @return список услуг провайдера
	 * @throws BusinessException
	 */
	public CardOperationCategory getFirstCardOperationCategoryForServiceProvider(final Long providerId) throws BusinessException
	{
		try
		{
			BeanQuery query = MultiLocaleQueryHelper.getQuery("com.rssl.phizic.business.dictionaries.providers.ServiceProvider.getCardOperationCategories");
			query.setParameter("providerId", providerId);
			query.setMaxResults(1);
			return query.executeUnique();
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Опорный объект: I_SERVICE_PROVIDERS_UUID
	 * Предикаты доступа: THIS_."UUID"=:UUID
	 * Кардинальность: 1
	 *
	 * Поиск поставщика услуг по кросблочному идентификатору
	 * @param uuid - кросблочный идентификатор
	 * @return ServiceProviderBase - поставщик услуг
	 * @throws BusinessException
	 */
	public ServiceProviderBase findByMultiBlockId(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderBase.class).add(Expression.eq("uuid", uuid));
		return simpleService.findSingle(criteria);
	}

	/**
	 * Найти мобильного оператора по номеру телефона.
	 * @param phoneNumber - номер телефона
	 * @return мобильный оператор
	 * @throws BusinessException
	 */
	public BillingServiceProvider getMobileOperatorByPhoneNumber(PhoneNumber phoneNumber) throws BusinessException
	{
		final String phone = PhoneNumberFormat.SIMPLE_NUMBER.format(phoneNumber);

		BillingService billingService = new BillingService();
		Adapter cardTransferAdapter = ConfigFactory.getConfig(AdaptersConfig.class).getCardTransfersAdapter();
		final Long iqwBillingId = billingService.getByAdapterUUID(cardTransferAdapter.getUUID()).getId();

		ErmbConfig ermbConfig = ConfigFactory.getConfig(ErmbConfig.class);

		if (ermbConfig.isUseMNPPhones())
		{
			BillingServiceProvider mobileOperator = findMobileOperatorByMNPPhones(phone, iqwBillingId);
			if (mobileOperator != null)
				return mobileOperator;
		}

		return findMobileOperatorByDefCodes(phone, iqwBillingId);
	}

	private BillingServiceProvider findMobileOperatorByMNPPhones(final String phone, final Long iqwBillingId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<BillingServiceProvider>()
			{
				public BillingServiceProvider run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "findMobileProviderByMNPPhones");
					query.setParameter("phone", phone);
					query.setParameter("iqwBillingId", iqwBillingId);
					List<BillingServiceProvider> providers = query.list();
					int providersCount = providers.size();
					if (providersCount > 1)
						throw new BusinessException("Для номера телефона " + phone + " найдено больше одного поставщика услуг");
					if (providersCount == 0)
						return null;
					return providers.get(0);
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private BillingServiceProvider findMobileOperatorByDefCodes(final String phone, final Long iqwBillingId) throws BusinessException
	{

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<BillingServiceProvider>()
			{
				public BillingServiceProvider run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "findMobileProviderByPhoneNumber");
					query.setParameter("phone", phone);
					query.setParameter("iqwBillingId", iqwBillingId);
					List<BillingServiceProvider> providers = query.list();
					int providersCount = providers.size();
					if (providersCount > 1)
						throw new BusinessException("Для номера телефона " + phone + " найдено больше одного поставщика услуг");
					if (providersCount == 0)
						return null;
					return providers.get(0);
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить информацию об активности поставщика услуг
	 * @param providerId id поставщика услуг
	 * @return информация
	 * @throws BusinessException
	 */
	public ServiceProviderShortcut getShortcutById(final long providerId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ServiceProviderShortcut>()
			{
				public ServiceProviderShortcut run(Session session) throws Exception
				{
					return (ServiceProviderShortcut) session.getNamedQuery(QUERY_PREFIX + "getShortcutById")
						.setParameter("id", providerId)
						.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить информацию об активности поставщика услуг
	 * @param uuid мультиблочный идентификатор поставщика услуг
	 * @return информация
	 * @throws BusinessException
	 */
	public ServiceProviderShortcut getShortcutByUUID(final String uuid) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ServiceProviderShortcut>()
			{
				public ServiceProviderShortcut run(Session session) throws Exception
				{
					return (ServiceProviderShortcut) session.getNamedQuery(QUERY_PREFIX + "getShortcutByUUID")
						.setParameter("uuid", uuid)
						.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поддерживает ли поставщик определенный тип автоплатежа
	 * @param recipientId id поставщика
	 * @param type тип автоплатежа
	 * @return true - поддерживает
	 * @throws BusinessException
	 */
	public boolean isSupportAutoPayType(final Long recipientId, final AutoSubType type) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "isSupportAutoPayType");
					query.setParameter("providerId", recipientId);
					query.setParameter("type", type.name());
					return query.list().size() > 0;
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить список поставщиков услуг, для которых созданы связки с идентификатором профиля
	 * @param identId идентификатор идентификатора профиля
	 * @return список ПУ
	 * @throws BusinessException
	 */
	public List<ServiceProviderForFieldFormulas> findShortProviderByFieldFormulas(final Long identId) throws BusinessException
	{
		if (identId == null)
		{
			throw new IllegalArgumentException("Идентификатор документа не может быть null.");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ServiceProviderForFieldFormulas>>()
			{
				public List<ServiceProviderForFieldFormulas> run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase.findShortProviderByFieldFormulas")
							.setParameter("ident_type_id", identId)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}

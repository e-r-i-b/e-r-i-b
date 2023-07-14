package com.rssl.phizic.operations.dictionaries.facilitator;

import com.rssl.phizgate.common.providers.ProviderPropertiesEntry;
import com.rssl.phizgate.common.providers.ProviderPropertiesService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.FacilitatorProvider;
import com.rssl.phizic.gate.einvoicing.ShopOrderService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Операция для редактирования настроек фасилитатора и списка КПУ
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ListEndpointProviderOperation extends OperationBase implements ListEntitiesOperation
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final ShopOrderService shopOrderService = GateSingleton.getFactory().service(ShopOrderService.class);
	private static final ProviderPropertiesService providerPropertiesService = new ProviderPropertiesService();
	private static final ServiceProviderService providerService = new ServiceProviderService();

	private static final String CSA_ADMIN_DB_NAME = "CSAAdmin";
	private static final String EINVOICING_PARAMETER_NAME = "einv";
	private static final String MOBILE_CHECKOUT_PARAMETER_NAME = "mco";
	private static final String MBCHECK_PARAMETER_NAME = "mbc";
	public static final String FACILITATOR_EINVOICING_PARAMETER_NAME = "feinv";
	public static final String FACILITATOR_MOBILE_CHECKOUT_PARAMETER_NAME = "fmco";
	public static final String FACILITATOR_MBCHECK_PARAMETER_NAME = "fmbc";

	private ServiceProviderBase provider;
	private ProviderPropertiesEntry providerProperties = null;
	private List<FacilitatorProvider> endpointProviders = new ArrayList<FacilitatorProvider>();

	private int maxResults;
	private int firstResult;

	/**
	 * @return поставщика-фасилитатора
	 */
	public ServiceProviderBase getEntity()
	{
		return provider;
	}

	/**
	 * @return свойства поставщика-фасилитатора
	 */
	public ProviderPropertiesEntry getProviderProperties()
	{
		return providerProperties;
	}

	/**
	 * Задать свойства поставщика-фасилитатора
	 * @param providerProperties - свойства
	 */
	public void setProviderProperties(ProviderPropertiesEntry providerProperties)
	{
		this.providerProperties = providerProperties;
	}

	/**
	 * @return Список конечных поставщиков данного фасилитатора
	 */
	public List<FacilitatorProvider> getEndpointProviders()
	{
		return endpointProviders;
	}

	/**
	 * Задать Список конечных поставщиков данного фасилитатора
	 * @param endpointProviders - список
	 */
	public void setEndpointProviders(List<FacilitatorProvider> endpointProviders)
	{
		this.endpointProviders = endpointProviders;
	}

	/**
	 * задать максимальное количество поставщиков
	 * @param maxResults - максимальное количество поставщиков
	 */
	public void setMaxResults(int maxResults)
	{
		this.maxResults = maxResults;
	}

	/**
	 * задать смещение выборки
	 * @param firstResult - смещение выборки
	 */
	public void setFirstResult(int firstResult)
	{
		this.firstResult = firstResult;
	}

	protected String getInstanceName()
	{
		return CSA_ADMIN_DB_NAME;
	}

	/**
	 * Инициализация операции
	 * @param id - идентификатор поставщика-фасилитатора
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		ServiceProviderBase temp = providerService.findById(id, getInstanceName());
		if (temp == null)
			throw new BusinessException("Поставщик услуг с id = " + id + " не найден");

		provider = temp;
		providerProperties = findProviderProperties();
	}

	/**
	 * Инициализация через конечного поставщика услуг
	 * @param id - идентификатор фасилитатора
	 * @param idEndpointProvider - идентификатор конечного поставщика
	 * @throws BusinessException
	 */
	public void initializeByEndpointProvider(Long id, Long idEndpointProvider) throws BusinessException
	{
		ServiceProviderBase temp = providerService.findById(id, getInstanceName());
		if (temp == null)
			throw new BusinessException("Поставщик услуг с id = " + id + " не найден");

		provider = temp;
		providerProperties = findProviderProperties();
		try
		{
			endpointProviders.add(shopOrderService.getEndPointProvider(idEndpointProvider));
		}
		catch (GateLogicException e)
		{
			log.error("Ошибка при получении конечного поставщика", e);
		}
		catch (GateException e)
		{
			log.error("Ошибка при получении конечного поставщика", e);
		}
	}

	/**
	 * @return свойства фасилитатора
	 */
	private ProviderPropertiesEntry findProviderProperties()
	{
		ProviderPropertiesEntry properties = null;
		try
		{
			properties = providerPropertiesService.findById(provider.getId());
		}
		catch (GateException e)
		{
			log.error("Ошибка при получении свойств фасилитатора", e);
		}

		if (properties == null)
		{
			properties = new ProviderPropertiesEntry();
			properties.setProviderId(provider.getId());
		}

		return properties;
	}

	/**
	 * Получить список конечных поставщиков для фасилитатора
	 */
	public void findEndpointProviders()
	{
		List<FacilitatorProvider> providers = new ArrayList<FacilitatorProvider>();
		try
		{
			providers = shopOrderService.findEndPointProviderByCode(((InternetShopsServiceProvider) provider).getCodeRecipientSBOL(), firstResult, maxResults);
		}
		catch (GateException e)
		{
			log.error("Ошибка при получении списка конечных поставщиков", e);
		}
		catch (GateLogicException e)
		{
			log.error("Ошибка при получении списка конечных поставщиков", e);
		}
		endpointProviders = providers;
	}

	/**
	 * Изменение свойств конечных поставщиков
	 * @param changedProperties - свойства
	 */
	public List<FacilitatorProvider> updateEndpointProviderProperties(List<List<String>> changedProperties)
	{
		List<FacilitatorProvider> changedProviders = new ArrayList<FacilitatorProvider>();

		for (List<String> row : changedProperties)
		{
			FacilitatorProvider changedProvider = null;
			boolean providerFounded = false; //признак: КПУ уже изменён ранее

			//ищем КПУ среди уже изменённых
			for (FacilitatorProvider element : changedProviders)
				if (element.getId().toString().equals(row.get(1)))
				{
					changedProvider = element;
					providerFounded = true;
					break;
				}

			//не нашли - ищем среди полученных при инициализации
			if (changedProvider == null)
				for (FacilitatorProvider element : endpointProviders)
					if (element.getId().toString().equals(row.get(1)))
					{
						changedProvider = element;
						break;
					}

			//не нашли - получаем из ВС по id
			if (changedProvider == null)
			{
				try
				{
					changedProvider = shopOrderService.getEndPointProvider(Long.parseLong(row.get(1)));
				}
				catch (GateLogicException e)
				{
					log.error("Ошибка при получении конечного поставщика", e);
				}
				catch (GateException e)
				{
					log.error("Ошибка при получении конечного поставщика", e);
				}
			}

			//меняем свойство
			if (changedProvider != null)
			{
				if (providerFounded)
					changedProviders.remove(changedProvider);
				if (row.get(0).equals(EINVOICING_PARAMETER_NAME))
					changedProvider.setEinvoiceEnabled(row.get(2).equals("1"));
				else if (row.get(0).equals(MOBILE_CHECKOUT_PARAMETER_NAME))
					changedProvider.setMobileCheckoutEnabled(row.get(2).equals("1"));
				else if (row.get(0).equals(MBCHECK_PARAMETER_NAME))
					changedProvider.setMbCheckEnabled(row.get(2).equals("1"));
				changedProviders.add(changedProvider);
			}
		}

		//сохраняем результаты
		try
		{
			if (CollectionUtils.isNotEmpty(changedProviders))
				for (FacilitatorProvider changedProvider : changedProviders)
					shopOrderService.updateEndPointProvider(changedProvider.getId(), changedProvider.isMobileCheckoutEnabled(), changedProvider.isEinvoiceEnabled(), changedProvider.isMbCheckEnabled());
			return changedProviders;
		}
		catch (GateLogicException e)
		{
			log.error("Ошибка при обновлении свойств конечного поставщика", e);
			return Collections.EMPTY_LIST;
		}
		catch (GateException e)
		{
			log.error("Ошибка при обновлении свойств конечного поставщика", e);
			return Collections.EMPTY_LIST;
		}
	}

	/**
	 * Созранить свойства фасилитатора
	 * @param property - изменённое свойство
	 */
	public void saveFacilitatorProperties(final String[] property)
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					boolean value = property[1].equals("1");
					boolean providerChanged = false;
					Boolean eInvoicingValue = null;
					Boolean mobileCheckoutValue = null;
					Boolean mbCheckValue = null;
					if (property[0].equals(FACILITATOR_EINVOICING_PARAMETER_NAME))
						eInvoicingValue = value ? true : null;
					else if (property[0].equals(FACILITATOR_MOBILE_CHECKOUT_PARAMETER_NAME))
					{
						((InternetShopsServiceProvider) provider).setAvailableMobileCheckout(value);
						providerChanged = true;
						mobileCheckoutValue = value ? true : null;
					}
					else if (property[0].equals(FACILITATOR_MBCHECK_PARAMETER_NAME))
					{
						providerProperties.setMbCheckEnabled(value);
						mbCheckValue = value;
					}

					//Соохраняем свойство фасилитатора
					//Если активируем для фасилитатора, то и для всех КПУ за ним
					try
					{
						if (providerChanged)
						{
							providerService.addOrUpdate(provider, getInstanceName());
							clearEntitiesListCache();
						}
						if (mbCheckValue != null)
							providerPropertiesService.addOrUpdate(providerProperties);
						if (value)
							shopOrderService.updateEndPointProviders(((InternetShopsServiceProvider) provider).getCodeRecipientSBOL(), mobileCheckoutValue, eInvoicingValue, mbCheckValue);
					}
					catch (GateLogicException e)
					{
						log.error("Ошибка при обновлении свойств фасилитатора и списка КПУ", e);
					}
					catch (GateException e)
					{
						log.error("Ошибка при обновлении свойств фасилитатора и списка КПУ", e);
					}
					return null;
				}
			});
		}
		catch (Exception e)
		{
			log.error("Ошибка при обновлении свойств фасилитатора и списка КПУ", e);
		}
	}

	/**
	 * при изменении поставщика неоходимо чистить кеш xml-справочника поставщиков
	 */
	private void clearEntitiesListCache()
	{
		//при изменении поставщика неоходимо чистить кеш xml-справочника поставщиков
		XmlEntityListCacheSingleton.getInstance().clearCache(provider, ServiceProviderBase.class);
	}
}

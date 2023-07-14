package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizgate.common.providers.ProviderPropertiesEntry;
import com.rssl.phizgate.common.providers.ProviderPropertiesService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAliasNotUniqException;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAliasService;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.limits.GroupRiskService;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.operations.restrictions.ServiceProvidersRestriction;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.ShopOrderService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.hibernate.Session;

import java.util.*;

/**
 * @author hudyakov
 * @ created 25.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class EditServiceProvidersOperation extends EditServiceProvidersOperationBase implements EditEntityOperation<ServiceProviderBase, ServiceProvidersRestriction>
{
	public static final String ICON_IMAGE_ID = "Icon";         //ключ для иконки поставщика
	public static final String HELP_IMAGE_ID = "Help";         //ключ для графической подсказки поставщика

	private static final String NOT_FOUND_GROUP_RISK                        = "Невозможно изменить поставщика потому, что группа риска с id: %d не найдена";

	private static final RegionDictionaryService regionService = new RegionDictionaryService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final BillingService billingService = new BillingService();
	private static final GroupRiskService groupRiskService = new GroupRiskService();
	private static final ServiceProviderSmsAliasService smsAliasService = new ServiceProviderSmsAliasService();
	private static final ProviderPropertiesService providerPropertiesService = new ProviderPropertiesService();
	private static final ShopOrderService shopOrderService = GateSingleton.getFactory().service(ShopOrderService.class);

	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private boolean isNew;
	private Department providerDepartment;
	private String[] aliasArr;
	private ProviderPropertiesEntry providerProperties = null;
	private boolean propertiesChanged = false;
	private boolean mbCheckChanged = false;
	private boolean mobileCheckoutChanged = false;

	public void initialize(Long id) throws BusinessException
	{
		ServiceProviderBase temp = providerService.findById(id, getInstanceName());
		if (temp == null)
			throw new BusinessException("Поставщик услуг с id = " + id + " не найден");

		if (!getRestriction().accept(temp))
		{
			throw new RestrictionViolationException("Поставщик ID= " + temp.getId());
		}

		provider = temp;
		isNew = false;

		providerDepartment = departmentService.findById(provider.getDepartmentId(), getInstanceName());

		addImage(ICON_IMAGE_ID, provider.getImageId());
		addImage(HELP_IMAGE_ID, provider.getImageHelpId());
	}

	public void initialize(Long id, ServiceProviderType type) throws BusinessException
	{
		initialize(id);

		//менять тип у сохраненного ПУ запрещено
		if (provider.getType() != type)
		{
			throw new BusinessException("Некорректный тип поставщика услуг");
		}
	}

	public void initializeNew(ServiceProviderType type) throws BusinessException
	{
		if (type == ServiceProviderType.TAXATION)
		{
			//если с формы пришел соответствующий тип
			provider = new TaxationServiceProvider();
		}
		else if (type == ServiceProviderType.EXTERNAL)
		{
			// внешняя услуга
			provider = new InternetShopsServiceProvider();
		}
		else if (type == ServiceProviderType.BILLING || type == null)
		{
			//если с формы пришел соответствующий тип, либо создание нового поставщика услуг
			provider = new BillingServiceProvider();
		}

        provider.setState(ServiceProviderState.NOT_ACTIVE);
		provider.setCreationDate(Calendar.getInstance());
		provider.setGroupRisk(groupRiskService.getDefaultGroupRisk(getInstanceName()));
		isNew = true;
		addImage(ICON_IMAGE_ID, provider.getImageId());
		addImage(HELP_IMAGE_ID, provider.getImageHelpId());
		provider.setAvailablePaymentsForInternetBank(true);
		provider.setAvailablePaymentsForMApi(true);
		provider.setAvailablePaymentsForAtmApi(true);
		provider.setAvailablePaymentsForSocialApi(true);
		provider.setAvailablePaymentsForErmb(true);
		provider.setVisiblePaymentsForInternetBank(false);
		provider.setVisiblePaymentsForMApi(false);
		provider.setVisiblePaymentsForAtmApi(false);
		provider.setVisiblePaymentsForSocialApi(false);
	}

	public ServiceProviderBase getEntity()
	{
		return provider;
	}

	/**
	 * @return подраздаление, к которому привязан поставщик
	 */
	public Department getDepartment()
	{
		return providerDepartment;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		executeTransactional(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				Image image = saveImage(ICON_IMAGE_ID);
				Long imageId = image != null ? image.getId() : null;
				provider.setImageId(imageId);
				image = saveImage(HELP_IMAGE_ID);
				imageId = image != null ? image.getId() : null;
				provider.setImageHelpId(imageId);
				providerService.addOrUpdate(provider, getInstanceName());
				saveProviderProperties();
				clearEntitiesListCache();
				return null;
			}
		});
	}

	/**
	 * Установить регионы обслуживания ПУ
	 * @param ids идентификаторы регионов
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void setRegions(List<Long> ids) throws BusinessException, BusinessLogicException
	{
		Set<Region> regions = provider.getRegions();
		regions.addAll(regionService.findByIds(ids, getInstanceName()));

		if (regions.size() == 0)
			throw new BusinessLogicException("Заполните регион обслуживания поставщиком услуг.");
	}

	/**
	 * Удаление регионов обслуживания ПУ
	 * @param ids идентификаторы регионов
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void removeRegions(List<Long> ids) throws BusinessException, BusinessLogicException
	{
		Set<Region> regions = provider.getRegions();
		List<Region> deletedRegions = regionService.findByIds(ids, getInstanceName());

		if (deletedRegions != null &&
				regions.size() - deletedRegions.size() < 1 && !isFederalProvider(provider))
			throw new BusinessLogicException("Удаление невозможно. Поставщик услуг должен обслуживать хотя бы один регион. Если Вы желаете удалить этот регион, то сначала добавьте другой, а затем повторите попытку его удаления.");

		regions.removeAll(deletedRegions);
	}

	private boolean isFederalProvider(ServiceProviderBase provider)
	{
		if(provider instanceof BillingServiceProviderBase)
			return ((BillingServiceProviderBase)provider).isFederal();
		return false;
	}

	/**
	 * Установить билинговую систему ПУ
	 * @param id идентификатор билинговой системы
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void setBilling(Long id) throws BusinessException, BusinessLogicException
	{
		if (!(provider instanceof BillingServiceProviderBase))
			throw new BusinessException("Некорректный тип поставщика услуг.");

		Billing billing = billingService.getById(id, getInstanceName());
		if (billing == null)
			throw new BusinessLogicException("Биллинговая система с идентификатором = " + id + " не найдена в базе данных ИКФЛ");

		BillingServiceProviderBase billingProvider = (BillingServiceProviderBase) provider;
		billingProvider.setSynchKey(provider.getCode());
		billingProvider.setBilling(billing);
		billingProvider.storeRouteInfo(billing.restoreRouteInfo());
		billingProvider.storePaymentSystemInfo(billingProvider.getCodeService());
	}

	public boolean isNew()
	{
		return isNew;
	}

	public void setGroupRisk(Long groupRiskId) throws BusinessException
	{
		if (groupRiskId == null)
		{
			provider.setGroupRisk(null);
			return;
		}

		GroupRisk groupRisk = groupRiskService.findById(groupRiskId, getInstanceName());
		if (groupRisk == null)
			throw new ResourceNotFoundBusinessException(String.format(NOT_FOUND_GROUP_RISK, groupRiskId), GroupRisk.class);

		provider.setGroupRisk(groupRisk);
	}

	public List<GroupRisk> getAllGroupsRisk() throws BusinessException
	{
		return groupRiskService.getAllGroupsRisk(getInstanceName());
	}

	/**
	 * @return смс псевдонимы поставщика
	 * @throws BusinessException
	 */
	public List<ServiceProviderSmsAlias> getAllSmsAlis() throws BusinessException
	{
		if (provider.getId() != null)
			return smsAliasService.getServiceProviderAlias(provider, getInstanceName());
		else
			return Collections.emptyList();

	}

	/**
	 * Добавляем алиас поставщику
	 * @param alias смс псевдоним
	 */
	public void addSmsAlias(String alias) throws BusinessLogicException, BusinessException
	{
		try
		{
			//могут быть указаны разделители: ';' или пробел.
			aliasArr = StringUtils.split(alias,"; ");
			smsAliasService.addOrUpdateAliasToServiceProvider(aliasArr, provider, getInstanceName());
			sendUpdateDictionaryEvent(ServiceProviderSmsAlias.class);
		}
		catch(ServiceProviderSmsAliasNotUniqException ex)
		{
			log.error(ex);
			String errTxt = null;
			if (aliasArr.length > 1)
				errTxt = "В строке " + alias + " содержится повторяющийся  алиас, или один из них уже добавлен.";
			else
				errTxt = "Алиас " + alias + " уже добавлен.";

			throw new BusinessLogicException(errTxt, ex);
		}
	}

	/**
	 * удалить псевдонимы поставщика
	 * @param ids id удаляемых псевдонимов
	 */
	public void removeSmsAlias(String[] ids) throws BusinessException
	{
		List<Long> idsList = new ArrayList<Long>(ids.length);
		for (String id:ids)
		{
			idsList.add(Long.valueOf(id));
		}
		smsAliasService.removeServiceProviderAliasByIds(idsList, getInstanceName());
		sendUpdateDictionaryEvent(ServiceProviderSmsAlias.class);
	}

	/**
	 * @return свойства фасилитатора
	 */
	public ProviderPropertiesEntry findProviderProperties()
	{
		if (provider instanceof InternetShopsServiceProvider)
		{
			if (providerProperties == null)
			{
				try
				{
					providerProperties = providerPropertiesService.findById(provider.getId());
				}
				catch (GateException e)
				{
					log.error("Ошибка при получении свойств фасилитатора", e);
				}

				if (providerProperties == null)
					providerProperties = new ProviderPropertiesEntry();
			}
			return providerProperties;
		}
		else
			return null;
	}

	/**
	 * Обновление свойств фасилитатора
	 * @param isEInvoicingPropertyEnabled - признак доступности EInvoicing
	 * @param isMobileCheckoutPropertyEnabled - признак доступности MobileCheckout
	 * @param isMBCheckPropertyEnabled - признак доступности MobileCheckout
	 * @param isMBCheckEnabled - признак доступности MBCheck для фасилитатора
	 * @param mbCheckChanged - признак изменения доступности MBCheck для фасилитатора
	 * @param useESB - признак "Работа через КСШ"
	 */
	public void updateProviderProperties(boolean isEInvoicingPropertyEnabled, boolean isMobileCheckoutPropertyEnabled, boolean isMBCheckPropertyEnabled,
	                                     boolean isMBCheckEnabled, boolean mbCheckChanged, boolean useESB)
	{
		if (providerProperties == null)
			findProviderProperties();
		providerProperties.setEinvoiceDefaultEnabled(isEInvoicingPropertyEnabled);
		providerProperties.setMcheckoutDefaultEnabled(isMobileCheckoutPropertyEnabled);
		providerProperties.setMbCheckDefaultEnabled(isMBCheckPropertyEnabled);
		providerProperties.setMbCheckEnabled(isMBCheckEnabled);
		providerProperties.setUseESB(useESB);
		this.mbCheckChanged = mbCheckChanged;
		propertiesChanged = true;
	}

	/**
	 * Установить признак изменения mobileCheckout фасилитатора
	 * @param mobileCheckoutChanged - признак
	 */
	public void setMobileCheckoutChanged(boolean mobileCheckoutChanged)
	{
		this.mobileCheckoutChanged = mobileCheckoutChanged;
	}

	/**
	 * Сохранение свойства фасилитатора
	 */
	private void saveProviderProperties() throws GateException, GateLogicException
	{
		if (provider instanceof InternetShopsServiceProvider && propertiesChanged)
		{
			InternetShopsServiceProvider facilitator = (InternetShopsServiceProvider)provider;
			if (providerProperties.getProviderId() == 0)
				providerProperties.setProviderId(provider.getId());
			if (propertiesChanged)
				providerPropertiesService.addOrUpdate(providerProperties);
			if (mbCheckChanged || mobileCheckoutChanged)
				shopOrderService.updateEndPointProviders(facilitator.getCodeRecipientSBOL(), mobileCheckoutChanged ? true : null, null, mbCheckChanged ? true : null);
		}
	}
}

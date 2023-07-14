package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceService;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.atm.AtmApiConfig;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.person.Person;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lukina
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class ListServicesPaymentOperation extends ListServiceProvidersOperationBase implements ListEntitiesOperation
{
	public static final long DEFAULT_PROVIDER_PER_SERVICE_LIMIT = 3L;

	private static final RegionDictionaryService regionService = new RegionDictionaryService();
	private static final PaymentServiceService paymentServiceService = new PaymentServiceService();

	private Long providerPerServiceLimit = DEFAULT_PROVIDER_PER_SERVICE_LIMIT;
	private List<String> servicelessExternalIdList = null;
	private Long groupId = null;
	private AtmApiConfig atmApiConfig;


	public void initialize(Long groupId) throws BusinessException
	{
		this.groupId = groupId;
		atmApiConfig = ConfigFactory.getConfig(AtmApiConfig.class);
	}

	public int getExternalIdCount()
	{
		return servicelessExternalIdList!=null ? servicelessExternalIdList.size() : 0;
	}

	/**
	 * @return список внешних ID, не содержащих услугу
	 */
	public List<String> getServicelessExternalIdList()
	{
		if (CollectionUtils.isEmpty(servicelessExternalIdList))
			return Collections.singletonList("");
		else return Collections.unmodifiableList(servicelessExternalIdList);
	}

	public void setServicelessExternalIdList(List<String> servicelessExternalIdList)
	{
		if (CollectionUtils.isEmpty(servicelessExternalIdList))
			this.servicelessExternalIdList = null;
		else
			this.servicelessExternalIdList = new ArrayList<String>(servicelessExternalIdList);
	}

	public String getClientType()
	{
		Person clientPerson = getClientPerson();
		if (clientPerson != null)
			return clientPerson.getCreationType().toString();
		else return null;
	}

	/**
	 * Возвращает ограничение на количество поставщиков по каждой услуге
	 * @return максимальное кол-во поставщиков на услугу
	 *  или null, если ограничения нет
	 */
	public Long getProviderPerServiceLimit()
	{
		return providerPerServiceLimit;
	}

	/**
	 * Задаёт ограничение на количество поставщиков по каждой услуге
	 * @param providerPerServiceLimit - максимальное кол-во поставщиков на услугу
	 *  или null, если ограничения нет
	 */
	public void setProviderPerServiceLimit(Long providerPerServiceLimit)
	{
		this.providerPerServiceLimit = providerPerServiceLimit;
	}

	public Long getGroupId()
	{
		return groupId;
	}

	/**
	 * Является ли группа листом
	 * @return true - лист
	 * @throws BusinessException
	 */
	public boolean isLeaf() throws BusinessException
	{
		if(groupId == null || groupId == 0)
			return false;
		return paymentServiceService.isLeaf(groupId);
	}

	/**
	 * Имеет ли услуга подчиненные услуги
	 * @return true - лист
	 * @throws BusinessException
	 */
	public boolean hasChildServices() throws BusinessException
	{
		if(groupId == null || groupId == 0)
			return false;
		return paymentServiceService.hasChild(groupId);
	}

	/**
	 * Возвращает внешний идентифкатор iqvave
	 * Используется в запросе для поиска поставщиков не IQW
	 * @return внешний идентифкатор iqvave
	 * @throws BusinessException
	 */
	public String getIQWaveUUID() throws BusinessException
	{
		AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
		Adapter adapter = config.getCardTransfersAdapter();
		if (adapter == null)
		{
			throw new BusinessException("Не задана внешняя система для карточных переводов");
		}

		return adapter.getUUID();
	}

	/**
	 * Возвращает идентификатор региона родителя.
	 * @param regionId идентификатор.
	 * @return идентификатор родителя.
	 * @throws BusinessException
	 */
	public Long getRegionParentId(Long regionId) throws BusinessException
	{
		if (RegionHelper.isAllRegionsSelected(regionId))
			return null;
		Region region = regionService.findById(regionId).getParent();
		if (region == null)
			return null;
		return region.getId();
	}

	/**
	 * @param id идентификатор категории услуг
	 * @return доступна ли категория услуг в mAPI
	 */
	public boolean isAvailableInMApi(Long id) throws BusinessException
	{
		PaymentService category = paymentServiceService.findById(id);
		return category != null && category.getCategory() && category.getShowInMApi();
	}

	/**
	 * @param id идентификатор категории или услуги
	 * @return доступна ли категория или услуга в atmAPI
	 */
	public boolean isAvailableInAtmApi(Long id) throws BusinessException
	{
		PaymentService service = paymentServiceService.findById(id);
		return service != null && (isShowServicesInAtmApi() || service.getCategory()) && service.getShowInAtmApi();
	}

	/**
	 * @return true - отображать в АТМ категории, услуги и поставщиков; false - отображать только категории и поставщиков
	 */
	public boolean isShowServicesInAtmApi()
	{
		return atmApiConfig.isShowServices();
	}
}

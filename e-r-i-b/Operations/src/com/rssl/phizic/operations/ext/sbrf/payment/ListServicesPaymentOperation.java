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
	 * @return ������ ������� ID, �� ���������� ������
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
	 * ���������� ����������� �� ���������� ����������� �� ������ ������
	 * @return ������������ ���-�� ����������� �� ������
	 *  ��� null, ���� ����������� ���
	 */
	public Long getProviderPerServiceLimit()
	{
		return providerPerServiceLimit;
	}

	/**
	 * ����� ����������� �� ���������� ����������� �� ������ ������
	 * @param providerPerServiceLimit - ������������ ���-�� ����������� �� ������
	 *  ��� null, ���� ����������� ���
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
	 * �������� �� ������ ������
	 * @return true - ����
	 * @throws BusinessException
	 */
	public boolean isLeaf() throws BusinessException
	{
		if(groupId == null || groupId == 0)
			return false;
		return paymentServiceService.isLeaf(groupId);
	}

	/**
	 * ����� �� ������ ����������� ������
	 * @return true - ����
	 * @throws BusinessException
	 */
	public boolean hasChildServices() throws BusinessException
	{
		if(groupId == null || groupId == 0)
			return false;
		return paymentServiceService.hasChild(groupId);
	}

	/**
	 * ���������� ������� ������������ iqvave
	 * ������������ � ������� ��� ������ ����������� �� IQW
	 * @return ������� ������������ iqvave
	 * @throws BusinessException
	 */
	public String getIQWaveUUID() throws BusinessException
	{
		AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
		Adapter adapter = config.getCardTransfersAdapter();
		if (adapter == null)
		{
			throw new BusinessException("�� ������ ������� ������� ��� ��������� ���������");
		}

		return adapter.getUUID();
	}

	/**
	 * ���������� ������������� ������� ��������.
	 * @param regionId �������������.
	 * @return ������������� ��������.
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
	 * @param id ������������� ��������� �����
	 * @return �������� �� ��������� ����� � mAPI
	 */
	public boolean isAvailableInMApi(Long id) throws BusinessException
	{
		PaymentService category = paymentServiceService.findById(id);
		return category != null && category.getCategory() && category.getShowInMApi();
	}

	/**
	 * @param id ������������� ��������� ��� ������
	 * @return �������� �� ��������� ��� ������ � atmAPI
	 */
	public boolean isAvailableInAtmApi(Long id) throws BusinessException
	{
		PaymentService service = paymentServiceService.findById(id);
		return service != null && (isShowServicesInAtmApi() || service.getCategory()) && service.getShowInAtmApi();
	}

	/**
	 * @return true - ���������� � ��� ���������, ������ � �����������; false - ���������� ������ ��������� � �����������
	 */
	public boolean isShowServicesInAtmApi()
	{
		return atmApiConfig.isShowServices();
	}
}

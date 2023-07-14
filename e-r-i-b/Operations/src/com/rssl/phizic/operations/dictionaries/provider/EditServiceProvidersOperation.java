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
	public static final String ICON_IMAGE_ID = "Icon";         //���� ��� ������ ����������
	public static final String HELP_IMAGE_ID = "Help";         //���� ��� ����������� ��������� ����������

	private static final String NOT_FOUND_GROUP_RISK                        = "���������� �������� ���������� ������, ��� ������ ����� � id: %d �� �������";

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
			throw new BusinessException("��������� ����� � id = " + id + " �� ������");

		if (!getRestriction().accept(temp))
		{
			throw new RestrictionViolationException("��������� ID= " + temp.getId());
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

		//������ ��� � ������������ �� ���������
		if (provider.getType() != type)
		{
			throw new BusinessException("������������ ��� ���������� �����");
		}
	}

	public void initializeNew(ServiceProviderType type) throws BusinessException
	{
		if (type == ServiceProviderType.TAXATION)
		{
			//���� � ����� ������ ��������������� ���
			provider = new TaxationServiceProvider();
		}
		else if (type == ServiceProviderType.EXTERNAL)
		{
			// ������� ������
			provider = new InternetShopsServiceProvider();
		}
		else if (type == ServiceProviderType.BILLING || type == null)
		{
			//���� � ����� ������ ��������������� ���, ���� �������� ������ ���������� �����
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
	 * @return �������������, � �������� �������� ���������
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
	 * ���������� ������� ������������ ��
	 * @param ids �������������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void setRegions(List<Long> ids) throws BusinessException, BusinessLogicException
	{
		Set<Region> regions = provider.getRegions();
		regions.addAll(regionService.findByIds(ids, getInstanceName()));

		if (regions.size() == 0)
			throw new BusinessLogicException("��������� ������ ������������ ����������� �����.");
	}

	/**
	 * �������� �������� ������������ ��
	 * @param ids �������������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void removeRegions(List<Long> ids) throws BusinessException, BusinessLogicException
	{
		Set<Region> regions = provider.getRegions();
		List<Region> deletedRegions = regionService.findByIds(ids, getInstanceName());

		if (deletedRegions != null &&
				regions.size() - deletedRegions.size() < 1 && !isFederalProvider(provider))
			throw new BusinessLogicException("�������� ����������. ��������� ����� ������ ����������� ���� �� ���� ������. ���� �� ������� ������� ���� ������, �� ������� �������� ������, � ����� ��������� ������� ��� ��������.");

		regions.removeAll(deletedRegions);
	}

	private boolean isFederalProvider(ServiceProviderBase provider)
	{
		if(provider instanceof BillingServiceProviderBase)
			return ((BillingServiceProviderBase)provider).isFederal();
		return false;
	}

	/**
	 * ���������� ���������� ������� ��
	 * @param id ������������� ���������� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void setBilling(Long id) throws BusinessException, BusinessLogicException
	{
		if (!(provider instanceof BillingServiceProviderBase))
			throw new BusinessException("������������ ��� ���������� �����.");

		Billing billing = billingService.getById(id, getInstanceName());
		if (billing == null)
			throw new BusinessLogicException("����������� ������� � ��������������� = " + id + " �� ������� � ���� ������ ����");

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
	 * @return ��� ���������� ����������
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
	 * ��������� ����� ����������
	 * @param alias ��� ���������
	 */
	public void addSmsAlias(String alias) throws BusinessLogicException, BusinessException
	{
		try
		{
			//����� ���� ������� �����������: ';' ��� ������.
			aliasArr = StringUtils.split(alias,"; ");
			smsAliasService.addOrUpdateAliasToServiceProvider(aliasArr, provider, getInstanceName());
			sendUpdateDictionaryEvent(ServiceProviderSmsAlias.class);
		}
		catch(ServiceProviderSmsAliasNotUniqException ex)
		{
			log.error(ex);
			String errTxt = null;
			if (aliasArr.length > 1)
				errTxt = "� ������ " + alias + " ���������� �������������  �����, ��� ���� �� ��� ��� ��������.";
			else
				errTxt = "����� " + alias + " ��� ��������.";

			throw new BusinessLogicException(errTxt, ex);
		}
	}

	/**
	 * ������� ���������� ����������
	 * @param ids id ��������� �����������
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
	 * @return �������� ������������
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
					log.error("������ ��� ��������� ������� ������������", e);
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
	 * ���������� ������� ������������
	 * @param isEInvoicingPropertyEnabled - ������� ����������� EInvoicing
	 * @param isMobileCheckoutPropertyEnabled - ������� ����������� MobileCheckout
	 * @param isMBCheckPropertyEnabled - ������� ����������� MobileCheckout
	 * @param isMBCheckEnabled - ������� ����������� MBCheck ��� ������������
	 * @param mbCheckChanged - ������� ��������� ����������� MBCheck ��� ������������
	 * @param useESB - ������� "������ ����� ���"
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
	 * ���������� ������� ��������� mobileCheckout ������������
	 * @param mobileCheckoutChanged - �������
	 */
	public void setMobileCheckoutChanged(boolean mobileCheckoutChanged)
	{
		this.mobileCheckoutChanged = mobileCheckoutChanged;
	}

	/**
	 * ���������� �������� ������������
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

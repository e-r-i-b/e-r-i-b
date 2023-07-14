package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.resources.external.ActiveDebitRurAccountFilter;
import com.rssl.phizic.business.resources.external.ActiveRurNotVirtualCardFilter;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.business.regions.RegionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lukina
 * @ created 28.04.2010
 * @ $Author$
 * @ $Revision$
 * �������� ��������� ���������� �� ����������� � �� ���� � ������� ������
 */

public class GetPaymentServiceInfoOperation extends ListServiceProvidersOperationBase
{
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private Long serviceId = null;
	private Boolean mobilebank = null;

	/**
	 * ������������������� �������� ��� ������ �� ���������� ����������� ������
	 * @param serviceId
	 */
	public void initialize(Long serviceId)
	{
	}

	protected void setMobilebank(Boolean mobilebank)
	{
		this.mobilebank = mobilebank;
	}

	public Boolean isMobilebank()
	{
		return mobilebank;
	}

	/**
	 *
	 * @return ������������ ������.
	 */
	public Long getServiceId()
	{
		return serviceId;
	}

	/**
	 * �������� ������ �������� ����� ��� ������ �����������
	 * @return ������ �������� �����
	 * @throws BusinessException
	 */
	public List<FieldDescription> getKeysFieldDescriptionsList(List<Long> providerIds) throws BusinessException
	{
		return providerService.getKeyFieldDescriptionsByServiceId(providerIds);
	}

	/**
	 * @return �������� ������� ������ �������
	 * @throws BusinessException
	 */
	public Region getCurrentRegion() throws BusinessException
	{
		return RegionHelper.getCurrentRegion();
	}

	/**
	 * @return ������ �������� ��������
	 */
	public List<PaymentAbilityERL> getChargeOffResources() throws BusinessException, BusinessLogicException
	{
		List<PaymentAbilityERL> result = new ArrayList<PaymentAbilityERL>();
		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		PersonData personData = provider.getPersonData();
		result.addAll(personData.getAccounts(new  ActiveDebitRurAccountFilter()));
		result.addAll(personData.getCards(new ActiveRurNotVirtualCardFilter()));

		return result;
	}
}

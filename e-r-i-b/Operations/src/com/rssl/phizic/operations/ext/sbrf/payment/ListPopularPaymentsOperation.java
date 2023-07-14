package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceService;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;

import java.util.List;

/**
 * @author lukina
 * @ created 05.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ��������� ������ ���������� �����, ����������� � ��������� �������� ������� ��� �������� "���������� �������"
 */
public class ListPopularPaymentsOperation  extends ListServiceProvidersOperationBase
{
	private static final PaymentServiceService paymentServiceService = new PaymentServiceService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final RegionDictionaryService regionService = new RegionDictionaryService();
	private static final int MAX_PAYMENT_COUNT = 5;

    /**
	 * �������� ��� �������
	 * @return ��� �������
	 */
	public String getClientType()
	{
		Person clientPerson = getClientPerson();
		if (clientPerson != null)
			return clientPerson.getCreationType().toString();
		else return null;
	}

	/**
	 * ���������� ������������� ������� ��������.
	 * @param regionId �������������.
	 * @return ������������� ��������.
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Long getRegionParentId(Long regionId) throws BusinessException
	{
		if (regionId == null)
			return null;
		Region selectedRegion = regionService.findById(regionId);
		if (selectedRegion == null)
			return null;
		Region region = selectedRegion.getParent();
		if (region == null)
			return null;
		return region.getId();
	}

	/**
	 * �������� ������ ��������� ����������� ��������� ���������� �������
	 * @return ������ ����������
	 * @throws BusinessException
	 */
	public List<BusinessDocumentBase> getUserPayments() throws BusinessException
	{
		return  getUserPayments(MAX_PAYMENT_COUNT);
	}

	/**
	 * �������� ������ ��������� ����������� ��������� ���������� �������
	 * @param count ���������� ��������� ����������
	 * @return ������ ����������
	 * @throws BusinessException
	 */
	public List<BusinessDocumentBase> getUserPayments(int count) throws BusinessException
	{
		try
		{
			Login login = getClientPerson().getLogin();
			Query query = createQuery("executedPayments");
			query.setParameter("loginId", login.getId());
			query.setMaxResults(count);
			return query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ ��������� ����������� ���-�������� �������
	 * @return ������ ����������
	 * @throws BusinessException
	 */
	public List<BusinessDocumentBase> getUserSMSPayments() throws BusinessException
	{
		return  businessDocumentService.getSmsPayments(getClientPerson(), MAX_PAYMENT_COUNT);
	}
}


package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.operations.OperationBase;

/**
 * �������� ��� ����������� ���� �����������, ������� ����� ������� � ����� ���������� ����������(iqw ��� ������)
 * @author niculichev
 * @ created 16.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class DefineTypeAutoPaymentOperation extends OperationBase
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	private ServiceProviderShort provider;

	public void initialize(Long providerId) throws BusinessException
	{
		if(providerId == null)
			throw new BusinessException("�� ������ ���������, � ����� �������� ����������� ����������");

		ServiceProviderShort serviceProvide = serviceProviderService.findShortProviderById(providerId);
		if(serviceProvide == null)
			throw new BusinessException("���������� � id = " + providerId + " �� ����������");

		if(!(serviceProvide.getKind().equals("B")) || !AutoPaymentHelper.checkAutoPaymentSupport(serviceProvide))
			throw new BusinessException("� ����� ���������� � id = " + providerId + " ���������� ������� ����������");

		provider =  serviceProvide;
	}

	public boolean isESBAutoPayProvider() throws BusinessException
	{
		//�� IQW � ��������� � �������� ����������
		return (provider.getAccountType() == AccountType.CARD || provider.getAccountType() == AccountType.ALL)
				&& !AutoPaymentHelper.isIQWProvider(provider.getSynchKey())
				&& AutoPaymentHelper.checkAutoPaymentSupport(provider);
	}
}

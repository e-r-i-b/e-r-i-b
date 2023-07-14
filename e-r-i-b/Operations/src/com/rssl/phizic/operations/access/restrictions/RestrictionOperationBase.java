package com.rssl.phizic.operations.access.restrictions;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.OperationDescriptorService;
import com.rssl.phizic.business.operations.restrictions.RestrictionData;
import com.rssl.phizic.business.operations.restrictions.RestrictionService;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.security.SecurityDbException;

/**
 * ������� ����� �������� ��� �����������, ������� ������ ������ ������ ����������� ���
 * ������ <�����, ������, ��������> ������� ������ initialize(Long loginId, Long serviceId, Long operationId)
 *
 * @author Roshka
 * @ created 19.04.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class RestrictionOperationBase<RD extends RestrictionData> extends OperationBase
{
	private static final RestrictionService restrictionService = new RestrictionService();
	protected static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final SecurityService securityService = new SecurityService();
	private static final ServiceService serviceService = new ServiceService();
	private static final OperationDescriptorService operationDescriptorService = new OperationDescriptorService();
	private RD restrictionData;
	private CommonLogin login;

	/**
	 * ���������������� �������� ���������� �������.
	 * @param loginId �����
	 * @param serviceId ������
	 * @param operationId ��������
	 * @throws BusinessException
	 */
	public void initialize(Long loginId, Long serviceId, Long operationId) throws BusinessException
	{
		//noinspection RedundantTypeArguments

		try
		{
			login = securityService.findById(loginId);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}

		Service service = null;

		if (serviceId != null)
			service = serviceService.findById(serviceId);

		OperationDescriptor operationDescriptor = null;
		if (operationId != null)
			operationDescriptor = operationDescriptorService.findById(operationId);

		RD temp = (RD) restrictionService.find(login, service, operationDescriptor);
		if (temp == null)
		{
			temp = createNew(loginId, serviceId, operationId);
		}

		this.restrictionData = temp;
	}

	/**
	 * �������� ������ ������ �����������.
	 * @return {@link RestrictionData}
	 */
	public RD getRestrictionData()
	{
		return restrictionData;
	}

	/**
	 * ��������� �����������.
	 * @throws BusinessException
	 */
	public void save() throws BusinessException
	{
		restrictionService.update(restrictionData);
	}

	/**
	 * ������� �����������.
	 * @throws BusinessException
	 */
	public void remove() throws BusinessException
	{
		if (restrictionData.getId() != null)
			restrictionService.remove(restrictionData);
	}

	/**
	 * �����
	 */
	protected CommonLogin getLogin()
	{
		return login;
	}

	/**
	 * ������� ������ ������ �����������.
	 * @param loginId �����
	 * @param serviceId ������
	 * @param operationId ��������
	 * @return ��������� {@link RestrictionData}
	 */
	protected abstract RD createNew(Long loginId, Long serviceId, Long operationId);
}
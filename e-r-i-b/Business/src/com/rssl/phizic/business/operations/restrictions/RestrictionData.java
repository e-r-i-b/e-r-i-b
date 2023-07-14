package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;

/**
 * ������� ����� ������ ����������� ��������. ����������: ������������� ������
 * <�����, ������, ��������>, � ������� ������������� ��� ����������
 * �����������, �������� �� �����, �� �����, ��. {@link ListAccountRestrictionData} �
 * {@link  ListCardRestrictionData} ��������������.
 *
 * @author Roshka
 * @ created 17.04.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class RestrictionData <T extends Restriction>
{
	private Long id;
	private Long loginId;
	private Long serviceId;
	private Long operationId;

	public Long getId()
	{
		return id;
	}

	@SuppressWarnings({"UNUSED_SYMBOL"})
	private void setId(Long id)
	{
		this.id = id;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public Long getOperationId()
	{
		return operationId;
	}

	public void setOperationId(Long operationId)
	{
		this.operationId = operationId;
	}

	public Long getServiceId()
	{
		return serviceId;
	}

	public void setServiceId(Long serviceId)
	{
		this.serviceId = serviceId;
	}

	/**
	 * ��������� ����������� - {@link Restriction} ������������ ������ - ��������.
	 * @return
	 * @throws BusinessException
	 */
	public abstract T buildRestriction() throws BusinessException;
}
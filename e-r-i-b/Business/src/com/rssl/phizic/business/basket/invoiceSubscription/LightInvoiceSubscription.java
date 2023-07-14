package com.rssl.phizic.business.basket.invoiceSubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.basket.Constants;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;

/**
 * @author osminin
 * @ created 16.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ���� ������ �������� "��������"
 * ������������, ���� ����� �������� ������ �� ��������, ����� ����������, �� ��������� �������� ��� CLOB
 */
public class LightInvoiceSubscription
{
	private long id;
	private String name;
	private Long recipientId;
	private String baseState;
	private String autoSubExternalId;

	/**
	 * @return ������������
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id ������������
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return ������������ ������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name ������������ ������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ������ ������
	 */
	public InvoiceSubscriptionState getState()
	{
		if (baseState.contains(Constants.STATE_DELIMITER))
		{
			return InvoiceSubscriptionState.WAIT;
		}
		return InvoiceSubscriptionState.valueOf(baseState);
	}

	/**
	 * @return ������������� ����������
	 */
	public Long getRecipientId()
	{
		return recipientId;
	}

	/**
	 * @param recipientId ������������� ����������
	 */
	public void setRecipientId(Long recipientId)
	{
		this.recipientId = recipientId;
	}

	/**
	 * @return ��������� ������
	 */
	public String getBaseState()
	{
		return baseState;
	}

	/**
	 * @param baseState ��������� ������
	 */
	public void setBaseState(String baseState)
	{
		this.baseState = baseState;
	}

	/**
	 * @return ������, � ������� ��������� ��������
	 * @throws BusinessException
	 */
	public InvoiceSubscriptionState getNextState() throws BusinessException
	{
		return InvoiceSubscription.getNextState(baseState);
	}

	/**
	 * @return ������� ������������� ���������� ������������
	 */
	public String getAutoSubExternalId()
	{
		return autoSubExternalId;
	}

	/**
	 * ���������� ������� ������������� ���������� ������������
	 * @param autoSubExternalId ������� �������������
	 */
	public void setAutoSubExternalId(String autoSubExternalId)
	{
		this.autoSubExternalId = autoSubExternalId;
	}
}

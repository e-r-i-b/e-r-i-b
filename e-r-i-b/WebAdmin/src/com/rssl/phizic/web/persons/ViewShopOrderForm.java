package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.FilterActionForm;

/**
 * ����� ��������� ��������-������ � �������
 * @author niculichev
 * @ created 21.08.15
 * @ $Author$
 * @ $Revision$
 */
public class ViewShopOrderForm extends EditFormBase
{
	private String html;
	private Long personId;
	private String orderId;
	private String formName;
	private String providerName;

	/**
	 * @return ������������� �������
	 */
	public Long getPersonId()
	{
		return personId;
	}

	/**
	 * ���������� ������������� �������
	 * @param personId id
	 */
	public void setPersonId(Long personId)
	{
		this.personId = personId;
	}

	/**
	 * @return ��������������� �� ������ html-���
	 */
	public String getHtml()
	{
		return html;
	}

	/**
	 * ���������� ��������������� �� ������ html-���
	 * @param html ���
	 */
	public void setHtml(String html)
	{
		this.html = html;
	}

	/**
	 * @return ������� ������������� ������
	 */
	public String getOrderId()
	{
		return orderId;
	}

	/**
	 * ���������� ������� ������������� ������
	 * @param orderId ������������� ������
	 */
	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	/**
	 * @return �������� ����������
	 */
	public String getProviderName()
	{
		return providerName;
	}

	/**
	 * ���������� �������� ����������
	 * @param providerName �������� ����������
	 */
	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	/**
	 * @return �������� �����
	 */
	public String getFormName()
	{
		return formName;
	}

	/**
	 * ���������� �������� �����
	 * @param formName ��� �����
	 */
	public void setFormName(String formName)
	{
		this.formName = formName;
	}
}

package com.rssl.phizic.web.actions.payments.catalog;

import com.rssl.phizic.web.actions.payments.IndexForm;

import java.util.List;

/**
 * @author krenev
 * @ created 02.10.14
 * @ $Author$
 * @ $Revision$
 * ������� ����� ����� �������� ����� ��� �������, �������� �� ����
 */
public class ApiCatalogFormBase extends IndexForm
{
	public static final String CATEGORY_TYPE = "category";
	public static final String PROVIDER_TYPE = "provider";
	public static final String SERVICE_TYPE = "service";

	//in
	private String id; //��������� �������: ���������� id
	private int paginationSize; //��������� �������
	private int paginationOffset; //��������� �������
	private boolean autoPaymentOnly; //������� ������� ����������� ��� ������������

	//out
	private List<Object[]> list; //������ ���������
	private String type;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public boolean isAutoPaymentOnly()
	{
		return autoPaymentOnly;
	}

	public void setAutoPaymentOnly(boolean autoPaymentOnly)
	{
		this.autoPaymentOnly = autoPaymentOnly;
	}

	public List<Object[]> getList()
	{
		return list;
	}

	public void setList(List<Object[]> list)
	{
		this.list = list;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getPaginationSize()
	{
		return paginationSize;
	}

	public void setPaginationSize(int paginationSize)
	{
		this.paginationSize = paginationSize;
	}

	public int getPaginationOffset()
	{
		return paginationOffset;
	}

	public void setPaginationOffset(int paginationOffset)
	{
		this.paginationOffset = paginationOffset;
	}
}

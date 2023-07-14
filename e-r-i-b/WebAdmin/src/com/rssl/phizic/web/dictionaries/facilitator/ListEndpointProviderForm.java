package com.rssl.phizic.web.dictionaries.facilitator;

import com.rssl.phizgate.common.providers.ProviderPropertiesEntry;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.gate.einvoicing.FacilitatorProvider;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * ����� ��� �������������� �������� ������������ � ������ ���
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ListEndpointProviderForm extends ListFormBase<FacilitatorProvider>
{
	public static final int DEFAULT_SIZE_VALUE = 50;
	public static final int DEFAULT_OFFSET_VALUE = 0;

	private long id;
	private long idEndpointProvider = 0;
	private InternetShopsServiceProvider facilitator;
	private ProviderPropertiesEntry facilitatorProperties;
	private int paginationOffset = DEFAULT_OFFSET_VALUE;
	private int paginationSize = DEFAULT_SIZE_VALUE;
	private String propertyString = null;
	private String facilitatorProperty = null;

	/**
	 * @return id ������������
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * ������ id ������������
	 * @param id - �������������
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return id ��������� ���������� �����
	 */
	public long getIdEndpointProvider()
	{
		return idEndpointProvider;
	}

	/**
	 * ������ id ��������� ���������� �����
	 * @param idEndpointProvider - id
	 */
	public void setIdEndpointProvider(long idEndpointProvider)
	{
		this.idEndpointProvider = idEndpointProvider;
	}

	/**
	 * @return ����������-������������
	 */
	public InternetShopsServiceProvider getFacilitator()
	{
		return facilitator;
	}

	/**
	 * ������ ����������-������������
	 * @param facilitator - �����������
	 */
	public void setFacilitator(InternetShopsServiceProvider facilitator)
	{
		this.facilitator = facilitator;
	}

	/**
	 * @return �������� ����������-������������
	 */
	public ProviderPropertiesEntry getFacilitatorProperties()
	{
		return facilitatorProperties;
	}

	/**
	 * ������ �������� ����������-������������
	 * @param facilitatorProperties - ��������
	 */
	public void setFacilitatorProperties(ProviderPropertiesEntry facilitatorProperties)
	{
		this.facilitatorProperties = facilitatorProperties;
	}

	/**
	 * @return �������� ���������
	 */
	public int getPaginationOffset()
	{
		return paginationOffset;
	}

	/**
	 * ������ �������� ���������
	 * @param paginationOffset - ��������
	 */
	public void setPaginationOffset(int paginationOffset)
	{
		this.paginationOffset = paginationOffset;
	}

	/**
	 * @return ������ ���������
	 */
	public int getPaginationSize()
	{
		return paginationSize;
	}

	/**
	 * ������ ������ ���������
	 * @param paginationSize - ������
	 */
	public void setPaginationSize(int paginationSize)
	{
		this.paginationSize = paginationSize;
	}

	/**
	 * @return ������ � ���������� ����������� ���
	 */
	public String getPropertyString()
	{
		return propertyString;
	}

	/**
	 * ������ ������ � ���������� ����������� ���
	 * @param propertyString - ������
	 */
	public void setPropertyString(String propertyString)
	{
		this.propertyString = propertyString;
	}

	/**
	 * @return ������ � ��������� ����������� ���
	 */
	public String getFacilitatorProperty()
	{
		return facilitatorProperty;
	}

	/**
	 * ������ ������ � ��������� ����������� ���
	 * @param facilitatorProperty - ������
	 */
	public void setFacilitatorProperty(String facilitatorProperty)
	{
		this.facilitatorProperty = facilitatorProperty;
	}
}

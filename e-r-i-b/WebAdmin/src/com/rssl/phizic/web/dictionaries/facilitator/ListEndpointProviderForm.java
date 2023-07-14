package com.rssl.phizic.web.dictionaries.facilitator;

import com.rssl.phizgate.common.providers.ProviderPropertiesEntry;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.gate.einvoicing.FacilitatorProvider;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * Форма для редактирования настроек фасилитатора и списка КПУ
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
	 * @return id фасилитатора
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Задать id фасилитатора
	 * @param id - идентификатор
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return id конечного поставщика услуг
	 */
	public long getIdEndpointProvider()
	{
		return idEndpointProvider;
	}

	/**
	 * Задать id конечного поставщика услуг
	 * @param idEndpointProvider - id
	 */
	public void setIdEndpointProvider(long idEndpointProvider)
	{
		this.idEndpointProvider = idEndpointProvider;
	}

	/**
	 * @return поставщика-фасилитатора
	 */
	public InternetShopsServiceProvider getFacilitator()
	{
		return facilitator;
	}

	/**
	 * Задать поставщика-фасилитатора
	 * @param facilitator - фасилитатор
	 */
	public void setFacilitator(InternetShopsServiceProvider facilitator)
	{
		this.facilitator = facilitator;
	}

	/**
	 * @return свойства поставщика-фасилитатора
	 */
	public ProviderPropertiesEntry getFacilitatorProperties()
	{
		return facilitatorProperties;
	}

	/**
	 * Задать свойства поставщика-фасилитатора
	 * @param facilitatorProperties - свойства
	 */
	public void setFacilitatorProperties(ProviderPropertiesEntry facilitatorProperties)
	{
		this.facilitatorProperties = facilitatorProperties;
	}

	/**
	 * @return смещение пагинации
	 */
	public int getPaginationOffset()
	{
		return paginationOffset;
	}

	/**
	 * Задать смещение пагинации
	 * @param paginationOffset - смещение
	 */
	public void setPaginationOffset(int paginationOffset)
	{
		this.paginationOffset = paginationOffset;
	}

	/**
	 * @return размер пагинации
	 */
	public int getPaginationSize()
	{
		return paginationSize;
	}

	/**
	 * Задать размер пагинацмм
	 * @param paginationSize - размер
	 */
	public void setPaginationSize(int paginationSize)
	{
		this.paginationSize = paginationSize;
	}

	/**
	 * @return строку с изменёнными параметрами КПУ
	 */
	public String getPropertyString()
	{
		return propertyString;
	}

	/**
	 * Задать строку с изменёнными параметрами КПУ
	 * @param propertyString - строка
	 */
	public void setPropertyString(String propertyString)
	{
		this.propertyString = propertyString;
	}

	/**
	 * @return строку с измнёнными параметрами КПУ
	 */
	public String getFacilitatorProperty()
	{
		return facilitatorProperty;
	}

	/**
	 * Задать строку с измнёнными параметрами КПУ
	 * @param facilitatorProperty - строка
	 */
	public void setFacilitatorProperty(String facilitatorProperty)
	{
		this.facilitatorProperty = facilitatorProperty;
	}
}

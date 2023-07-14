package com.rssl.phizic.business.dictionaries.pfp.products.types;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

/**
 * Сущность название столбца
 * @author komarov
 * @ created 15.07.2013 
 * @ $Author$
 * @ $Revision$
 */

public class TableColumn extends MultiBlockDictionaryRecordBase
{
	private Long id;
	private String value;
	private Long orderIndex; //Порядок отображения.

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return название столбца
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @param value название столбца
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * @return порядок отображения
	 */
	public Long getOrderIndex()
	{
		return orderIndex;
	}

	/**
	 * @param orderIndex порядок отображения
	 */
	public void setOrderIndex(Long orderIndex)
	{
		this.orderIndex = orderIndex;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		TableColumn that = (TableColumn) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
		{
			return false;
		}
		if (orderIndex != null ? !orderIndex.equals(that.orderIndex) : that.orderIndex != null)
		{
			return false;
		}
		if (value != null ? !value.equals(that.value) : that.value != null)
		{
			return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		result = 31 * result + (orderIndex != null ? orderIndex.hashCode() : 0);
		return result;
	}
}

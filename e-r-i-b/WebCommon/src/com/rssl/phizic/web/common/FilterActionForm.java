package com.rssl.phizic.web.common;

import com.rssl.phizic.web.actions.ActionFormBase;
import org.apache.commons.lang.BooleanUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Roshka
 * @ created 28.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class FilterActionForm extends ActionFormBase
{
	public static final String FIELD_FORMAT = "field(%s)";

    private Map<String,Object> filter = new HashMap<String, Object>();
    private Map<String,Object> fields = new HashMap<String, Object>();
	private Map<String,Boolean> filterState = new HashMap<String, Boolean>();//Состояние фильтра: свёрнут/развёрнут.
	private Map<String, Boolean> maskedFields = new HashMap<String, Boolean>(); // замаскировано ли поле

	/**
	 * @return значения полей фильтра
	 */
	public Map<String, Object> getFilters()
	{
		return filter;
	}

	/**
	 * Установить значения полей фильтра
	 * @param filter значения полей
	 */
	public void setFilters(Map<String, Object> filter)
	{
		this.filter = filter;
	}

	/**
	 * Вернуть значение поля фильтра
	 * @param key имя поля
	 * @return значения поля
	 */
	public Object getFilter(String key)
	{
		return filter.get(key);
	}

	/**
	 * Установить значение поля фильтра
	 * @param key имя поля
	 * @param obj значения поля
	 */
	public void setFilter(String key, Object obj)
	{
		filter.put(key, obj);
	}

	/**
	 * Добавить в фильтры не сколько значений.
	 * @param filters значения
	 */
	public void addFilters(Map<String, Object> filters)
	{
		this.filter.putAll(filters);
	}

    public Map<String,Object> getFields()
    {
        return fields;
    }

	public void setFields(Map<String, Object> fields)
	{
		this.fields = fields;
	}

    public Object getField(String key)
    {
        return fields.get(key);
    }

    public void setField(String key, Object obj)
    {
        fields.put(key, obj);
    }

	public void addFields(Map<String, Object> fields)
	{
		this.fields.putAll(fields);
	}

	public Map<String, Boolean> getFilterState()
	{
		return filterState;
	}

	public void setFilterState(Map<String, Boolean> filterState)
	{
		this.filterState = filterState;
	}

	public Map<String, Boolean> getMaskedFields()
	{
		return maskedFields;
	}

	public void setMaskedFields(Map<String, Boolean> maskedFields)
	{
		this.maskedFields = maskedFields;
	}

	public boolean getMaskedField(String key)
	{
		return BooleanUtils.isTrue(maskedFields.get(key));
	}

	public void setMaskedField(String key, Boolean obj)
	{
		maskedFields.put(key, obj);
	}
}

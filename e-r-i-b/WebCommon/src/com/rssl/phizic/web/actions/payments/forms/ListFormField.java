package com.rssl.phizic.web.actions.payments.forms;

import java.util.List;

/**
 * @author Erkin
 * @ created 26.11.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Поле-список
 * @param <T>
 */
public class ListFormField<T> extends FormFieldBase
{
	private List<T> items;

	private T selectedItem = null;

	///////////////////////////////////////////////////////////////////////////

	public FormFieldType getType()
	{
		return FormFieldType.LIST;
	}

	public List<T> getItems()
	{
		return items;
	}

	public void setItems(List<T> items)
	{
		this.items = items;
	}

	public T getSelectedItem()
	{
		return selectedItem;
	}

	public void setSelectedItem(T selectedItem)
	{
		this.selectedItem = selectedItem;
	}

}

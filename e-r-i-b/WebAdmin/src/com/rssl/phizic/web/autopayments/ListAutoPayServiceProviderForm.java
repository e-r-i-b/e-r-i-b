package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.web.actions.payments.ListPaymentServiceFormBase;
import com.rssl.phizic.business.persons.ActivePerson;

/**
 * ����� ��� ������ ����������� ��� �������� �����������
 * @author niculichev
 * @ created 26.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListAutoPayServiceProviderForm extends ListPaymentServiceFormBase
{
	private static final String SEARCH_ROW_FIELD_NAME = "searchRow";
	// �� ��������� 20 ������� �� ��������
	private int itemsPerPage = 20;
	private ActivePerson activePerson;
	private boolean modified;

	public int getItemsPerPage()
	{
		return itemsPerPage;
	}
	
	public void setItemsPerPage(int itemsPerPage)
	{
		this.itemsPerPage = itemsPerPage;
	}

	// �������������� ������, �.�. ������ ������ ������ � filters
	public String getSearchServices()
	{
		return (String) getFilter(SEARCH_ROW_FIELD_NAME);
	}

	public void setSearchServices(String searchServices)
	{
		setFilter(SEARCH_ROW_FIELD_NAME, searchServices);
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public boolean isModified()
	{
		return modified;
	}

	public void setModified(boolean modified)
	{
		this.modified = modified;
	}
}

package com.rssl.phizic.web.actions.payments;

import com.rssl.phizic.web.common.FilterActionForm;

import java.util.List;

/**
 * @author lukina
 * @ created 20.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class ListPaymentServiceFormBase extends FilterActionForm
{
	//����� ��������� �� ��������
	private static final int ITEMS_PER_PAGE=12;
	//��������� ������
	private String searchServices;
	//����� �������� ������
	private int searchPage;
	// ��������� ������
	private List<Object> searchResults;
	//���������
	private String categoryId;
	//id ������
	private Long serviceId;
	//����� ������� ��������
	private int currentPage;
	/**
	 * ����, �������� �� ����� ����� ��� �����������(��-��������� �� ��������).
	 */
	private boolean isAutoPaySearch = false;

	//������ �� �� �������� ����������� ���������
	private boolean fromFinanceCalendar = false;
	//������������� ������� ���� ���������
	private String extractId;

	public String getSearchServices()
	{
		return searchServices;
	}

	public void setSearchServices(String searchServices)
	{
		this.searchServices = searchServices;
	}

	public int getSearchPage()
	{
		return searchPage;
	}

	public void setSearchPage(int searchPage)
	{
		this.searchPage = searchPage;
	}

	public List<Object> getSearchResults()
	{
		return searchResults;
	}

	public void setSearchResults(List<Object> searchResults)
	{
		this.searchResults = searchResults;
	}

	public int getItemsPerPage()
	{
		return ITEMS_PER_PAGE;
	}

	public String getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(String categoryId)
	{
		this.categoryId = categoryId;
	}

	public Long getServiceId()
	{
		return serviceId;
	}

	public void setServiceId(Long serviceId)
	{
		this.serviceId = serviceId;
	}

	public int getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}

	public boolean getIsAutoPaySearch()
	{
		return isAutoPaySearch;
	}

	public void setIsAutoPaySearch(boolean autoPaySearch)
	{
		isAutoPaySearch = autoPaySearch;
	}

	public boolean isFromFinanceCalendar()
	{
		return fromFinanceCalendar;
	}

	public void setFromFinanceCalendar(boolean fromFinanceCalendar)
	{
		this.fromFinanceCalendar = fromFinanceCalendar;
	}

	public String getExtractId()
	{
		return extractId;
	}

	public void setExtractId(String extractId)
	{
		this.extractId = extractId;
	}
}

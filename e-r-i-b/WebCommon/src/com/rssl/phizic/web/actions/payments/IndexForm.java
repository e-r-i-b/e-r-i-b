package com.rssl.phizic.web.actions.payments;

import com.rssl.phizic.business.basket.invoice.FakeInvoice;
import com.rssl.phizic.business.dictionaries.payment.services.CategoryServiceType;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;

/**
 * @author Rydvanskiy
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class IndexForm extends ListPaymentServiceFormBase
{
	//Список услуг
	private List<Object[]> services;

	//Список поставщиков
	private List<Object[]> providers;

	/**
	 * Сет непустых категорий услуг (в которых есть поставщики)
	 */
	private Set<CategoryServiceType> fullProviderCategories;

	private List<Object[]> searchResult; //результаты поиска
	private String searchType; // тип сортировки результатов поиска по региону/по алфавиту/по услуге
	private int provCount = 0; //количество "уже отрисованных поставщиков"
	private int provCountInPage;  //количество поставщиков на текущей странице
	private String  paginationType;  //тип пагинации вперед/назад
	private static final int RESULT_COUNT = 8; //количество поставщиков на странице
	private Map<String, String[]> regions;//список регионов, для найденных поставщиков
	private String pageList = "";
	private String fromResource;
	private List<Object[]> categories;          //список категорий с подчиненными услугами
	private Boolean showInvoices;               //нужно ли отображать все инвойсы
	private String successMessage;            //сообщение успешного дейтствия при необходимое отобразить при редиректе на страницу.
	List<FakeInvoice> data = new ArrayList<FakeInvoice>();   //список отображаемых сущностей (инвойсов и интернет-платежей)
	private BigDecimal sumValue = BigDecimal.ZERO;              //итоговая сумма для всех отображённых счетов
	private String bannerText;                  //текст баннера
	private Long amountOfHiddenInvoices;        //количество скрытых инвойсов
	private Long accountingEntityId;
	private String pageType;   //тип станицы, на которой производтся поиск(платежи и переводы, корзина, автоплатежи)

	///////////////////////////////////////////////////////////////////////////

	public String getPageList()
	{
		return pageList;
	}

	public void setPageList(String pageList)
	{
		this.pageList = pageList;
	}

	public List<Object[]> getServices()
	{
		return services;
	}

	public void setServices(List<Object[]> services)
	{
		this.services = services;
	}

	public Set<CategoryServiceType> getFullProviderCategories()
	{
		return fullProviderCategories;
	}

	public void setFullProviderCategories(Set<CategoryServiceType> fullProviderCategories)
	{
		this.fullProviderCategories = fullProviderCategories;
	}

	public List<Object[]> getSearchResult()
	{
		return searchResult;
	}

	public void setSearchResult(List<Object[]> searchResult)
	{
		this.searchResult = searchResult;
	}

	public Map<String, String[]> getRegions()
	{
		return regions;
	}

	public void setRegions(Map<String, String[]> regions)
	{
		this.regions = regions;
	}

	public String getPaginationType()
	{
		return paginationType;
	}

	public void setPaginationType(String paginationType)
	{
		this.paginationType = paginationType;
	}

	public int getProvCount()
	{
		return provCount;
	}

	public void setProvCount(int provCount)
	{
		this.provCount = provCount;
	}

	public int getProvCountInPage()
	{
		return provCountInPage;
	}

	public void setProvCountInPage(int provCountInPage)
	{
		this.provCountInPage = provCountInPage;
	}

	public int getResultCount()
	{
		return RESULT_COUNT;
	}

	public String getSearchType()
	{
		return searchType;
	}

	public void setSearchType(String searchType)
	{
		this.searchType = searchType;
	}

	public String getFromResource()
	{
		return fromResource;
	}

	public void setFromResource(String fromResource)
	{
		this.fromResource = fromResource;
	}

	public List<Object[]> getCategories()
	{
		return categories;
	}

	public void setCategories(List<Object[]> categories)
	{
		this.categories = categories;
	}

	public Boolean isShowInvoices()
	{
		return showInvoices;
	}

	public void setShowInvoices(Boolean showInvoices)
	{
		this.showInvoices = showInvoices;
	}

	public Boolean getShowInvoices()
	{
		return showInvoices;
	}

	public List<FakeInvoice> getData()
	{
		return data;
	}

	public void setData(List<FakeInvoice> data)
	{
		this.data = data;
	}

	public BigDecimal getSumValue()
	{
		return sumValue;
	}

	public void setSumValue(BigDecimal sumValue)
	{
		this.sumValue = sumValue;
	}

	public String getBannerText()
	{
		return bannerText;
	}

	public void setBannerText(String bannerText)
	{
		this.bannerText = bannerText;
	}

	public Long getAmountOfHiddenInvoices()
	{
		return amountOfHiddenInvoices;
	}

	public void setAmountOfHiddenInvoices(Long amountOfHiddenInvoices)
	{
		this.amountOfHiddenInvoices = amountOfHiddenInvoices;
	}

	public String getSuccessMessage()
	{
		return successMessage;
	}

	public void setSuccessMessage(String successMessage)
	{
		this.successMessage = successMessage;
	}

	public Long getAccountingEntityId()
	{
		return accountingEntityId;
	}

	public void setAccountingEntityId(Long accountingEntityId)
	{
		this.accountingEntityId = accountingEntityId;
	}

	public void setProviders(List<Object[]> providers)
	{
		this.providers = providers;
	}

	public List<Object[]> getProviders()
	{
		return providers;
	}

	public String getPageType()
	{
		return pageType;
	}

	public void setPageType(String pageType)
	{
		this.pageType = pageType;
	}
}

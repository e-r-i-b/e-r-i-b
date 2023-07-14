package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.fields.FieldDescription;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author khudyakov
 * @ created 04.03.14
 * @ $Author$
 * @ $Revision$
 */
public class EditServicePaymentTemplateForm extends EditTemplateForm
{
	private static final int ITEMS_PER_PAGE=12; //Число элементов на страницу

	private Long service;
	private Long recipient;
	private String providerName;
	private List<ServiceProviderShort> providers;
	private boolean externalPayment;
	private boolean personal;
	private String orderId;
	private List<FieldDescription> fieldsDescription;
	private Integer searchPage;
	private String phoneFieldParam;
	private String socialNetUserId;
	private String socialNetProviderId;
	private String socialNetPaymentFieldName;
	private BusinessDocument document;
	private boolean needSelectProvider = true;

	public Long getService()
	{
		return service;
	}

	public void setService(Long service)
	{
		this.service = service;
	}

	public Long getRecipient()
	{
		return recipient;
	}

	public void setRecipient(Long recipient)
	{
		this.recipient = recipient;
	}

	public String getProviderName()
	{
		return providerName;
	}

	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	public void setProviders(List<ServiceProviderShort> providers)
	{
		this.providers = providers;
	}

	public List<ServiceProviderShort> getProviders()
	{
		return providers;
	}

	public void setExternalPayment(boolean externalPayment)
	{
		this.externalPayment = externalPayment;
	}

	public boolean isExternalPayment()
	{
		return externalPayment;
	}

	public boolean isPersonal()
	{
		return personal;
	}

	public void setPersonal(boolean personal)
	{
		this.personal = personal;
	}

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public void setFieldsDescription(List<FieldDescription> fieldsDescription)
	{
		this.fieldsDescription = fieldsDescription;
	}

	public List<FieldDescription> getFieldsDescription()
	{
		return fieldsDescription;
	}

	public Integer getSearchPage()
	{
		return searchPage;
	}

	public void setSearchPage(Integer searchPage)
	{
		this.searchPage = searchPage;
	}

	public String getPhoneFieldParam()
	{
		return phoneFieldParam;
	}

	public void setPhoneFieldParam(String phoneFieldParam)
	{
		this.phoneFieldParam = phoneFieldParam;
	}

	public int getItemsPerPage()
	{
		return ITEMS_PER_PAGE;
	}

	public Map<Long, ServiceProviderShort> getProvidersMap()
	{
		if (CollectionUtils.isEmpty(providers))
		{
			return Collections.emptyMap();
		}

		Map<Long, ServiceProviderShort> providerMap = new HashMap<Long, ServiceProviderShort>();
		for (ServiceProviderShort provider : providers)
		{
			providerMap.put(provider.getId(), provider);
		}
		return providerMap;
	}

	public String getSocialNetPaymentFieldName()
	{
		return socialNetPaymentFieldName;
	}

	public void setSocialNetPaymentFieldName(String socialNetPaymentFieldName)
	{
		this.socialNetPaymentFieldName = socialNetPaymentFieldName;
	}

	public String getSocialNetProviderId()
	{
		return socialNetProviderId;
	}

	public void setSocialNetProviderId(String socialNetProviderId)
	{
		this.socialNetProviderId = socialNetProviderId;
	}

	public String getSocialNetUserId()
	{
		return socialNetUserId;
	}

	public void setSocialNetUserId(String socialNetUserId)
	{
		this.socialNetUserId = socialNetUserId;
	}

	public BusinessDocument getDocument()
	{
		return document;
	}

	public void setDocument(BusinessDocument document)
	{
		this.document = document;
	}

	/**
	 * @return  нужно ли выбирать поставщика на первом шаге создания шаблона
	 */
	public boolean isNeedSelectProvider()
	{
		return needSelectProvider;
	}

	public void setNeedSelectProvider(boolean needSelectProvider)
	{
		this.needSelectProvider = needSelectProvider;
	}
}

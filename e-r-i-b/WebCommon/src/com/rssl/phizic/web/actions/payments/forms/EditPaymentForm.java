package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.ListField;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Erkin
 * @ created 09.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditPaymentForm extends CreatePaymentForm
{
	///////////////////////////////////////////////////////////////////////////
	// ����, ����� ��� ���� �����

	private Long service;

	private Long recipient;

	private boolean externalPayment = false;     // ������ � ������� ������

	private String providerName; // ��� ����������

	private Long providerImageId; // ����������� ������ ����������

	/**
	 * ������� ����, ��� �� ������ �� ����� �� ������ �����.
	 */
	private Boolean back;

	private boolean personal = false;

	/**
	 * ��� ��������� �������� (ExternalResourceLink#getCode)
	 */
	private String fromResource;

	private ConfirmStrategy confirmStrategy;

	private String socialNetUserId;
	private String socialNetProviderId;
	private String socialNetPaymentFieldName;

	///////////////////////////////////////////////////////////////////////////

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	public Long getService()
	{
		return service;
	}

	public void setService(Long service)
	{
		this.service = service;
	}

	public Boolean getBack()
	{
		return back != null ? back : false;
	}

	public void setBack(Boolean back)
	{
		this.back = back;
	}

	public boolean isPersonal()
	{
		return personal;
	}

	public void setPersonal(boolean personal)
	{
		this.personal = personal;
	}

	public String getFromResource()
	{
		return fromResource;
	}

	public void setFromResource(String fromResource)
	{
		this.fromResource = fromResource;
	}

	public boolean getExternalPayment()
	{
		return externalPayment;
	}

	public void setExternalPayment(boolean externalPayment)
	{
		this.externalPayment = externalPayment;
	}

	///////////////////////////////////////////////////////////////////////////
	// ���� ��� ������� ����

	private List<ServiceProviderShort> providers;
	private List<FieldDescription> fieldsDescription;
	private static final int ITEMS_PER_PAGE=12; //����� ��������� �� ��������
	private Integer searchPage; //����� �������� ������
	private List<Region> regionNavigation; // "������� ������" ������� �� �������� � ��������
	private String phoneFieldParam;

	private List<PaymentAbilityERL> chargeOffResources; // ������� ��������

	public List<Region> getRegionNavigation()
	{
		return regionNavigation;
	}

	public void setRegionNavigation(List<Region> regionNavigation)
	{
		this.regionNavigation = regionNavigation;
	}

	public Integer getSearchPage()
	{
		return searchPage;
	}

	public void setSearchPage(Integer searchPage)
	{
		this.searchPage = searchPage;
	}

	public List<ServiceProviderShort> getProviders()
	{
		return providers;
	}

	public void setProviders(List<ServiceProviderShort> providers)
	{
		this.providers = providers;
	}


	public Map<Long, ServiceProviderShort> getProvidersMap()
	{
		if(CollectionUtils.isEmpty(providers))
			return Collections.emptyMap();

		Map<Long, ServiceProviderShort> providerMap = new HashMap<Long, ServiceProviderShort>();
		for(ServiceProviderShort provider : providers)
			providerMap.put(provider.getId(), provider);

		return providerMap;
	}

	public List<FieldDescription> getFieldsDescription()
	{
		return fieldsDescription;
	}

	public void setFieldsDescription(List<FieldDescription> fieldsDescription)
	{
		this.fieldsDescription = fieldsDescription;
	}

	public int getItemsPerPage()
	{
		return ITEMS_PER_PAGE;
	}

	public List<PaymentAbilityERL> getChargeOffResources()
	{
		return chargeOffResources;
	}

	public void setChargeOffResources(List<PaymentAbilityERL> chargeOffResources)
	{
		this.chargeOffResources = chargeOffResources;
	}


	///////////////////////////////////////////////////////////////////////////
	// ���� ��� ������� ����
	// ListField ��� ����, ��� �� �� inputFields.jsp ����� ���� ������������ �������, ��� �������� ���� ����� ��� "������", � ������� ��� ��������
	private List<ListField> keyFields;

	public void setKeyFields(List<ListField> keyFields)
	{
		this.keyFields = keyFields;
	}

	public List<ListField> getKeyFields()
	{
		return keyFields;
	}


	///////////////////////////////////////////////////////////////////////////
	// ���� ��� �������� ����

	public String getDebtCode()
	{
		return (String) getFields().get(PaymentFieldKeys.DEBTCODE_KEY);
	}

	public void setDebtCode(String debtCode)
	{
		if (StringHelper.isEmpty(debtCode))
			getFields().put(PaymentFieldKeys.DEBTCODE_KEY, null);
		else getFields().put(PaymentFieldKeys.DEBTCODE_KEY, debtCode);
	}

	private List<Debt> debts;

	public void setDebts(List<Debt> debts)
	{
		this.debts = debts;
	}

	public List<Debt> getDebts()
	{
		return debts;
	}

	public Long getRecipient()
	{
		return recipient;
	}

	public void setRecipient(Long recipient)
	{
		this.recipient = recipient;
	}

	@Deprecated
	public String getFormName()
	{
		return getForm();
	}

	@Deprecated
	public void setFormName(String formName)
	{
		setForm(formName);
	}

	public String getProviderName()
	{
		return providerName;
	}

	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	public String getPhoneFieldParam()
	{
		return phoneFieldParam;
	}

	public void setPhoneFieldParam(String phoneFieldParam)
	{
		this.phoneFieldParam = phoneFieldParam;
	}

	public Long getProviderImageId()
	{
		return providerImageId;
	}

	public void setProviderImageId(Long providerImageId)
	{
		this.providerImageId = providerImageId;
	}

	/**
	 * @return ������������� ���������� ����� ���������� ����.
	 */
	public String getSocialNetProviderId()
	{
		return socialNetProviderId;
	}

	/**
	 * @param socialNetProviderId ������� ������������� ���������� ���������� ����.
	 */
	public void setSocialNetProviderId(String socialNetProviderId)
	{
		this.socialNetProviderId = socialNetProviderId;
	}

	/**
	 * @return ������������� ������������ � ���������� ����.
	 */
	public String getSocialNetUserId()
	{
		return socialNetUserId;
	}

	/**
	 * @param socialNetUserId ������������� ������������ � ���������� ����.
	 */
	public void setSocialNetUserId(String socialNetUserId)
	{
		this.socialNetUserId = socialNetUserId;
	}

	/**
	 * @return �������� ����, � ������� �������� ������������� ������������.
	 */
	public String getSocialNetPaymentFieldName()
	{
		return socialNetPaymentFieldName;
	}

	/**
	 * @param socialNetPaymentFieldName �������� ����, � ������� �������� ������������� ������������.
	 */
	public void setSocialNetPaymentFieldName(String socialNetPaymentFieldName)
	{
		this.socialNetPaymentFieldName = socialNetPaymentFieldName;
	}
}

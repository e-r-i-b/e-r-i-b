package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ext.sbrf.payment.GetPaymentServiceInfoOperation;
import com.rssl.phizic.business.regions.RegionHelper;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Erkin
 * @ created 09.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class ProviderFormEditor
{
	private static final String FORWARD_EDIT_PROVIDER_PAYMENT_FORM = "EditProviderPaymentForm";

	private final GetPaymentServiceInfoOperation operation;

	private Long providerId;

	private Map<String, String> keyFields;

	private Region regionFilter;

	public ProviderFormEditor(GetPaymentServiceInfoOperation operation)
	{
		this.operation = operation;
	}

	public void setProviderId(Long providerId)
	{
		this.providerId = providerId;
	}

	public void setRegionFilter(Region regionFilter)
	{
		this.regionFilter = regionFilter;
	}

	public void setKeyFields(Map<String, String> keyFields)
	{
		this.keyFields = keyFields;
	}

	///////////////////////////////////////////////////////////////////////////

	public ActionForward start(ActionMapping mapping, EditPaymentForm frm)
			throws BusinessException, BusinessLogicException
	{
		updateForm(frm);

		return mapping.findForward(FORWARD_EDIT_PROVIDER_PAYMENT_FORM);
	}

	public void updateForm(EditPaymentForm frm) throws BusinessException, BusinessLogicException
	{
		//иницииализируем форму заполенным поставщиком
		frm.setRecipient(providerId);
		//инициализируем форму значениями полей
		frm.addFields(new HashMap<String, Object>(keyFields));

		Region region = regionFilter;
		if (region != null)
			frm.setRegionNavigation(RegionHelper.arrayToList(region));

		//выбираем поставщиков с учетом региона и для нужной страницы
		List<ServiceProviderShort> serviceProviders =
				getRecipientsList(providerId);
		frm.setProviders(serviceProviders);
		List<Long> providerIds = new ArrayList<Long>();
		for (ServiceProviderShort provider : serviceProviders)
		{
			providerIds.add(provider.getId());
		}
		List<FieldDescription> keyFieldDescriptions = new ArrayList<FieldDescription>();
		if (!providerIds.isEmpty())
		{
			keyFieldDescriptions = operation.getKeysFieldDescriptionsList(providerIds);
		}
		frm.setFieldsDescription(keyFieldDescriptions);

		List<PaymentAbilityERL> resources = operation.getChargeOffResources();
		String selectAccountNumber = keyFields.get("fromAccountSelect");
		frm.setChargeOffResources(resources);
		for(PaymentAbilityERL res: resources)
		{
		   if(res.getNumber().equals(selectAccountNumber))
			   frm.setFromResource(res.getCode());
		}
	}

	private List<ServiceProviderShort> getRecipientsList(Long recepientId) throws BusinessException
	{
		Query query = operation.createQuery("listSPIdenticalCodeRecSBOL");
		query.setParameter("recepientId", recepientId);
		try
		{
			return query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}
}

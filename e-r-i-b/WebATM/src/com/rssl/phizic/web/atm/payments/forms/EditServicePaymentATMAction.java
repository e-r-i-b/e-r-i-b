package com.rssl.phizic.web.atm.payments.forms;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.web.actions.payments.forms.EditServicePaymentAction;
import com.rssl.phizic.web.actions.payments.forms.EditServicePaymentForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 21.10.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Инициализация + первый шаг оплаты поставщику
 */
public class EditServicePaymentATMAction extends EditServicePaymentAction
{
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private static final BillingService billingService = new BillingService();

	protected CreationType getNewDocumentCreationType()
	{
		return CreationType.atm;
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("init", "start");
		map.put("next", "next");
		map.put("makeLongOffer", "makeLongOffer");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		checkInputParameters(frm);
		return super.start(mapping, frm, request, response);
	}

	public ActionForward next(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		checkInputParameters(frm);
		return super.next(mapping, frm, request, response);
	}

	protected Map<String, String> prepareFieldInputValue(EditServicePaymentForm frm) throws BusinessException
	{
		Map<String, String> fieldInputValuesMap = super.prepareFieldInputValue(frm);

		EditServicePaymentATMForm form = (EditServicePaymentATMForm) frm;
		//Если указан кросблочный ID поставщика, то пытаемся найти по нему
		if (StringHelper.isNotEmpty(form.getProviderGuid()))
		{
			ServiceProviderBase provider = providerService.findByMultiBlockId(form.getProviderGuid());
			if (provider != null)
				frm.setRecipient(provider.getId());
			fieldInputValuesMap.put(PaymentFieldKeys.PROVIDER_KEY, StringHelper.getEmptyIfNull(frm.getRecipient()));
		}
		return fieldInputValuesMap;
	}

	/**
	 * Контролирует входные параметры: должен быть задан либо id, либо template, либо billing+serivce(serivceGuid)+provider(providerGuid)
	 * @param frm
	 * @throws BusinessException
	 */
	private void checkInputParameters(ActionForm frm) throws BusinessException
	{
		EditServicePaymentATMForm form = (EditServicePaymentATMForm) frm;
		if (NumericUtil.isNotEmpty(form.getId()))
		{
			form.setCopying(true);
			//чтобы не искать поставщика с id = 0
			form.setRecipient(null);
		}
		else if (NumericUtil.isNotEmpty(form.getTemplate()) || StringHelper.isNotEmpty(form.getBarCode()))
		{
			//чтобы не искать поставщика с id = 0
			form.setRecipient(null);
		}
		else
		{
			if (NumericUtil.isEmpty(form.getBilling()))
				throw new BusinessException("Не указан ID билинга \"billing\"");
			if (NumericUtil.isEmpty(form.getService()) && StringHelper.isEmpty(form.getServiceGuid()))
				throw new BusinessException("Не указан ID услуги \"service\" или кросблочный ID услуги \"serviceGuid\"");
			if (NumericUtil.isEmpty(form.getProvider()) && StringHelper.isEmpty(form.getProviderGuid()))
				throw new BusinessException("Не указан ID поставщика \"provider\" или кросблочный ID поставщика \"providerGuid\"");
		}
	}

	protected void updateFormAdditionalData(EditServicePaymentForm frm, EditServicePaymentOperation editServicePaymentOperation) throws BusinessException, BusinessLogicException
	{
		EditServicePaymentATMForm form = (EditServicePaymentATMForm) frm;
		BillingServiceProviderBase provider = editServicePaymentOperation.getProvider();

		form.setServiceProvider(provider);
		form.setChargeOffResources(editServicePaymentOperation.getProviderChargeOffResources());
		form.setDocument(editServicePaymentOperation.getDocument());
		if (StringHelper.isEmpty(form.getFromResource()))
			form.setFromResource(editServicePaymentOperation.getFromResourceLink());

		List<FieldDescription> fieldDescriptions = provider.getFieldDescriptions();
		if (fieldDescriptions != null)
		{
			for (FieldDescription fd : fieldDescriptions)
			{
				// Заполняем значение поля либо данными, введёнными ранее, либо значением по-умолчанию
				Object valueAsObject = form.getField(fd.getExternalId());
				String value = (valueAsObject != null) ? valueAsObject.toString() : fd.getDefaultValue();
				fd.setDescription(XmlHelper.escapeXML(fd.getDescription()));
				fd.setHint(XmlHelper.escapeXML(fd.getHint()));
				fd.setValue(value);
			}
		}
	}

	@Override protected void addBarcodeFields(Map<String, Object> data)
	{
		data.put(ConfigFactory.getConfig(PaymentsConfig.class).getTerminalidField(), LogThreadContext.getCodeATM());
		data.put(ConfigFactory.getConfig(PaymentsConfig.class).getSourceSystemCodeField(), ConfigFactory.getConfig(PaymentsConfig.class).getATMSourceSystemCode());
	}

	protected void addBillingFields(Map<String, Object> data) throws BusinessException
	{
		ServiceProviderShort provider = RegionHelper.getBarCodeProvider();
		if (provider == null || provider.getBilling() == null)
		{
			log.error("Оплата по штрих-коду. Не удалось определить билинговую систему по поставщику.");
			return;
		}
		//Выбор билинговой системы по поставщику
		data.put(PaymentFieldKeys.BILLING_CODE, billingService.getById(provider.getBilling()).getCode());
	}
}

package com.rssl.phizic.web.common.mobile.payments.forms;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.business.util.MaskPhoneNumberUtil;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
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
public class EditMobileServicePaymentAction extends EditServicePaymentAction
{
    private static final ServiceProviderService providerService = new ServiceProviderService();
    private static final BillingService billingService = new BillingService();
	private static final AddressBookService addressBookService = new AddressBookService();
	private static final String CONTACT_NOT_FOUND_ERROR_MSG = "Неудалось определить контакт из %s ID=%s";

	protected CreationType getNewDocumentCreationType()
	{
		return CreationType.mobile;
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

	/**
	 * Контролирует входные параметры: должен быть задан либо id, либо template, либо billing+serivce+provider
	 * @param frm
	 * @throws BusinessException
	 */
	private void checkInputParameters(ActionForm frm) throws BusinessException, MalformedVersionFormatException
	{
		EditMobileServicePaymentForm form = (EditMobileServicePaymentForm) frm;

		if (NumericUtil.isNotEmpty(form.getId())) {
			form.setCopying(true);
			//чтобы не искать поставщика с id = 0
			form.setRecipient(null);
		}
		else if (NumericUtil.isNotEmpty(form.getTemplate()) || StringHelper.isNotEmpty(form.getBarCode()))
		{
			//чтобы не искать поставщика с id = 0
			form.setRecipient(null);
		}
		else {
			if(NumericUtil.isEmpty(form.getTrustedRecipientId()))
            {
                if (NumericUtil.isEmpty(form.getBilling()))
                    throw new BusinessException("Не указан billing");
                if (NumericUtil.isEmpty(form.getService()))
                    throw new BusinessException("Не указан service");
                if (NumericUtil.isEmpty(form.getProvider()))
                    throw new BusinessException("Не указан provider");
            }
            else
            {
                String version = form.getMobileApiVersion();

                VersionNumber vNumber = VersionNumber.fromString(version);

                if (vNumber.ge(MobileAPIVersions.V8_00))
                {
                    if(!StringHelper.isEmpty(version))
                    {

                        String providerExternalId =  ConfigFactory.getConfig(PaymentsConfig.class).getDefaultServiceProviderExternalId();

                        if(StringHelper.isEmpty(providerExternalId))
                            throw new BusinessException("Не установлен поставщик по умолчанию.");

                        ServiceProviderShort provider = providerService.findShortProviderBySynchKey(providerExternalId);

                        if (provider == null)
                            throw new BusinessException("Не найден поставщик услуг.");

                        form.setProvider(provider.getId());
	                }
                }
                else
                {
                    //чтобы не искать поставщика с id = 0
                    form.setRecipient(null);
                }
            }
		}
	}

	protected void updateFormAdditionalData(EditServicePaymentForm frm, EditServicePaymentOperation editServicePaymentOperation) throws BusinessException, BusinessLogicException
	{
		EditMobileServicePaymentForm form = (EditMobileServicePaymentForm) frm;
		BillingServiceProviderBase provider = editServicePaymentOperation.getProvider();

		form.setServiceProvider(provider);
		form.setChargeOffResources(editServicePaymentOperation.getProviderChargeOffResources());
		form.setDocument(editServicePaymentOperation.getDocument());
		//если fromResource уже заполнен, то не меняем его
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

		updateMessages(editServicePaymentOperation);
	}

	@Override protected void addBarcodeFields(Map<String, Object> data) throws BusinessException
	{
		data.put(ConfigFactory.getConfig(PaymentsConfig.class).getSourceSystemCodeField(), ConfigFactory.getConfig(PaymentsConfig.class).getMobileSourceSystemCode());
	}

	protected void addBillingFields(Map<String, Object> data) throws BusinessException
	{
		if (!PersonContext.isAvailable())
			return;

		ServiceProviderShort provider = RegionHelper.getBarCodeProvider();
		if (provider == null || provider.getBilling() == null)
		{
			log.error("Оплата по штрих-коду. Не удалось определить билинговую систему по поставщику.");
			return;
		}
		//Выбор билинговой системы по поставщику
		data.put(PaymentFieldKeys.BILLING_CODE, billingService.getById(provider.getBilling()).getCode());
	}

	/**
	 * Добавление назначения платежа
	 *
	 * @param operation - операция
	 * @param document - платеж
	 * @param frm - форма платежа
	 * @throws BusinessException
	 */
	protected void addOperationDescription(EditServicePaymentOperation operation, BusinessDocument document, EditServicePaymentForm frm) throws BusinessException, BusinessLogicException
	{
		EditMobileServicePaymentForm form = (EditMobileServicePaymentForm) frm;

		Long trustedRecipientId = form.getTrustedRecipientId();
		if (document instanceof JurPayment && NumericUtil.isNotEmpty(trustedRecipientId))
		{
			String phoneNumber = null;
			String fio = null;
			VersionNumber version = MobileApiUtil.getApiVersionNumber();
			if (trustedRecipientId < 0)
			{
				phoneNumber = MaskPhoneNumberUtil.getCutPhone(operation.getSelfPhone(trustedRecipientId));
			}
			else
			{
				//Начиная с 9-й версии mAPI поиск вести по адресной книге (АК) ЕРИБ
				Contact contact = addressBookService.findContactByLoginAndId(trustedRecipientId, PersonContext.getPersonDataProvider().getPersonData().getLogin().getId());
				if (contact == null)
				{
					log.info(String.format(CONTACT_NOT_FOUND_ERROR_MSG, "адресной книги", trustedRecipientId));
				}
				else
				{
					phoneNumber = contact.getPhone();
					fio = contact.getFullName();
				}
			}

			//Если контат не найден то номер телефона подставляем из платежа
			if (StringHelper.isEmpty(phoneNumber))
			{
				String defaultPhoneFieldName = ConfigFactory.getConfig(PaymentsConfig.class).getDefaultServiceProviderPhoneFieldName();
				phoneNumber = (String) form.getField(defaultPhoneFieldName);
			}
			String contactInfo = StringHelper.isNotEmpty(fio) ? String.format(". Контакт – %s", fio.trim()) : "";
			String description = String.format("Оплата мобильного телефона %s%s", phoneNumber.trim(), contactInfo);
			JurPayment jurPayment = (JurPayment)document;
			jurPayment.setOperationDescription(description);
		}
	}

	protected void updateFormFields(EditServicePaymentForm frm, EditServicePaymentOperation operation, Map<String, String> documentFieldValues) throws BusinessException, BusinessLogicException
    {
        FieldValuesSource fieldValuesSource = new MapValuesSource(documentFieldValues);
        if(MaskPaymentFieldUtils.isRequireMasking())
            fieldValuesSource =	MaskPaymentFieldUtils.wrapMaskValuesSource(getMaskingInfo(operation, fieldValuesSource), fieldValuesSource);

        String defaultPhoneFieldName = ConfigFactory.getConfig(PaymentsConfig.class).getDefaultServiceProviderPhoneFieldName();
        String phoneNumber = null;
        Long trustedRecipientId = ((EditMobileServicePaymentForm)frm).getTrustedRecipientId();
        if(NumericUtil.isNotEmpty(trustedRecipientId)) {
            phoneNumber = trustedRecipientId < 0 ? operation.getSelfPhone(trustedRecipientId) : operation.getTrustedRecipientPhone(trustedRecipientId);
        }
        Map<String, String> fieldsMap = fieldValuesSource.getAllValues();
        for(String key : fieldsMap.keySet())
        {
            if(key.equals(defaultPhoneFieldName) && StringHelper.isNotEmpty(phoneNumber))
                frm.setField(key, phoneNumber);
            else
                frm.setField(key, fieldsMap.get(key));

            frm.setMaskedField(key, fieldValuesSource.isMasked(key));
        }

	}
}

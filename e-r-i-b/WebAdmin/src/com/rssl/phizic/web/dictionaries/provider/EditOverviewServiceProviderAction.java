package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizgate.common.providers.ProviderPropertiesEntry;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.kbk.PaymentType;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankUtils;
import com.rssl.phizic.business.ext.sbrf.mobilebank.SmsCommandComposer;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.common.types.documents.ServiceProvidersConstants;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.provider.EditServiceProvidersOperation;
import com.rssl.phizic.operations.dictionaries.provider.SetupGroupRiskServiceProvidersOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.image.ImageEditFormBase;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rssl.phizic.web.dictionaries.provider.EditOverviewServiceProviderForm.USE_ESB_FIELD_NAME;
/**
 * @author khudyakov
 * @ created 23.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class EditOverviewServiceProviderAction extends EditServiceProvidersActionBase
{
	private static final SmsCommandComposer smsCommandComposer = new SmsCommandComposer();

	protected Map<String, String> getAdditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAdditionalKeyMethodMap();
		map.put("button.setupGroupRisk", "setupGroupRisk");
		map.put("button.addSmsAlias", "addSmsAlias");
		map.put("button.removeSmsAlias", "removeSmsAlias");
		return map;
	}
	
	protected Form getEditForm(EditFormBase frm)
	{
		return EditOverviewServiceProviderForm.OVERVIEW_FORM;
	}

	protected EditServiceProvidersOperation createEditOperation(EditFormBase form) throws BusinessException
	{
		EditOverviewServiceProviderForm frm = (EditOverviewServiceProviderForm) form;
		EditServiceProvidersOperation operation = createOperation("EditServiceProvidersOperation");
		Long id = frm.getId();
		if (id != null && id != 0)
		{
			operation.initialize(id, getType(frm));
		}
		else
		{
			operation.initializeNew(getType(frm));
		}
		return operation;
	}

	protected EditEntityOperation createViewOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditOverviewServiceProviderForm frm = (EditOverviewServiceProviderForm) form;
		EditServiceProvidersOperation operation = createOperation("ViewServiceProvidersOperation");
		Long id = frm.getId();
		if (id != null && id != 0)
		{
			operation.initialize(id);
		}
		else
		{
			operation.initializeNew(getType(frm));
		}
		return operation;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		ServiceProviderBase provider = (ServiceProviderBase) entity;
		provider.setName((String) data.get("name"));
		provider.setSortPriority((Long) data.get("sortPriority"));
		provider.setINN((String) data.get("inn"));
		provider.setKPP((String) data.get("kpp"));
		provider.setAccount((String) data.get("account"));
		provider.setBIC((String) data.get("bankCode"));
		provider.setBankName((String) data.get("bankName"));
		provider.setCorrAccount((String) data.get("corAccount"));
		provider.setCode((String) data.get("providerCode"));
		provider.setNSICode((String) data.get("eng4000nsi"));
		provider.setDescription((String) data.get("comment"));
		provider.setTipOfProvider((String) data.get("tip"));
		provider.setCommissionMessage((String)data.get("commissionMessage"));
		provider.setTransitAccount((String) data.get("transitAccount"));
		provider.setBankDetails(data.get("bankDetails") == null ? false : (Boolean) data.get("bankDetails"));
		provider.setPhoneNumber((String) data.get("phoneNumber"));
		Boolean isBarSupported = data.get("isBarSupported") == null ? false : (Boolean) data.get("isBarSupported");
		provider.setBarSupported(isBarSupported);
		provider.setOfflineAvailable(data.get("isOfflineAvailable") == null ? false : (Boolean) data.get("isOfflineAvailable"));

		Boolean creditCardAccessible = (Boolean) data.get(ServiceProvidersConstants.PROVIDER_IS_CREDIT_CARD_ACCESSIBLE);
		provider.setCreditCardSupported(creditCardAccessible == null || !creditCardAccessible);

		provider.setAvailablePaymentsForInternetBank(data.get("availablePaymentsForInternetBank") == null ? false : (Boolean) data.get("availablePaymentsForInternetBank"));
		provider.setAvailablePaymentsForMApi(data.get("availablePaymentsForMApi") == null ? false : (Boolean) data.get("availablePaymentsForMApi"));
		provider.setAvailablePaymentsForAtmApi(data.get("availablePaymentsForAtmApi") == null ? false : (Boolean) data.get("availablePaymentsForAtmApi"));
		provider.setAvailablePaymentsForSocialApi(data.get("availablePaymentsForSocialApi") == null ? false : (Boolean) data.get("availablePaymentsForSocialApi"));
		provider.setAvailablePaymentsForErmb(data.get("availablePaymentsForErmb") == null ? false : (Boolean) data.get("availablePaymentsForErmb"));

		provider.setVisiblePaymentsForInternetBank(data.get("visiblePaymentsForInternetBank") == null ? false : (Boolean) data.get("visiblePaymentsForInternetBank"));
		provider.setVisiblePaymentsForMApi(data.get("visiblePaymentsForMApi") == null ? false : (Boolean) data.get("visiblePaymentsForMApi"));
		provider.setVisiblePaymentsForAtmApi(data.get("visiblePaymentsForAtmApi") == null ? false : (Boolean) data.get("visiblePaymentsForAtmApi"));
		provider.setVisiblePaymentsForSocialApi(data.get("visiblePaymentsForSocialApi") == null ? false : (Boolean) data.get("visiblePaymentsForSocialApi"));

		provider.setDepartmentId((Long) data.get("departmentId"));

		Boolean standartSmsTemplate =  data.get("standartSmsTemplate") == null ? false : (Boolean) data.get("standartSmsTemplate");
		provider.setStandartTemplate(standartSmsTemplate);
		String subType = (String)data.get("subType");
		provider.setSubType(StringHelper.isNotEmpty(subType) ? ServiceProviderSubType.valueOf(subType) : null);
		if(!standartSmsTemplate) //для нестандартных СМС-шаблонов
		{
			provider.setTemplateFormat((String)data.get("format"));
			provider.setTemplateExample((String)data.get("example"));
		}

		if (provider instanceof BillingServiceProviderBase)
		{
			BillingServiceProviderBase billingServiceProvider = (BillingServiceProviderBase) entity;
			billingServiceProvider.setLegalName((String) data.get("legalName"));
			billingServiceProvider.setAlias((String) data.get("alias"));
			billingServiceProvider.setNameOnBill((String) data.get("nameOnBill"));
			billingServiceProvider.setCodeRecipientSBOL((String) data.get("codeRecipientSBOL"));
			billingServiceProvider.setCodeService((String) data.get("serviceCode"));
			billingServiceProvider.setNameService((String) data.get("nameService"));
			billingServiceProvider.setGround(data.get("ground") == null ? false : (Boolean) data.get("ground"));
			billingServiceProvider.setAttrDelimiter((String) data.get("separator1"));
			billingServiceProvider.setAttrValuesDelimiter((String) data.get("separator2"));
			billingServiceProvider.setAccountType(AccountType.valueOf((String) data.get("accountType")));
		}

		if (provider instanceof  BillingServiceProvider)
		{
			BillingServiceProvider billingServiceProvider = (BillingServiceProvider) entity;
			billingServiceProvider.setDeptAvailable((Boolean) data.get("debtSupported"));
			billingServiceProvider.setPopular((Boolean) data.get("popular"));
			billingServiceProvider.setPropsOnline((Boolean) data.get("propsOnline"));
			billingServiceProvider.setMobilebank((Boolean) data.get("mobilebank"));
			billingServiceProvider.setFederal((Boolean) data.get("federal"));
			billingServiceProvider.setMobilebankCode((String) data.get("mobilebankCode"));
			if (data.get("versionAPI") != null)
				billingServiceProvider.setVersionAPI((Integer) data.get("versionAPI"));
			billingServiceProvider.setTemplateSupported((Boolean) data.get("templateSupported"));
			billingServiceProvider.setPlaningForDeactivate((Boolean) data.get("planingForDeactivate"));
			billingServiceProvider.setEditPaymentSupported((Boolean) data.get("editPaymentSupported"));
		}

		if (provider instanceof TaxationServiceProvider)
		{
			TaxationServiceProvider taxationServiceProvider = (TaxationServiceProvider) entity;
			taxationServiceProvider.setFullPayment((Boolean) data.get("fullPayment"));
			taxationServiceProvider.setPayType(PaymentType.valueOf((String) data.get("payType")));
			taxationServiceProvider.setSynchKey((String) data.get("providerCode"));
		}

		if (provider instanceof InternetShopsServiceProvider)
		{
			InternetShopsServiceProvider internetShopsServiceProvider = (InternetShopsServiceProvider) entity;
			internetShopsServiceProvider.setCodeRecipientSBOL((String) data.get("codeRecipientSBOL") );
			internetShopsServiceProvider.setFormName((String) data.get("formName"));
			if (!(Boolean)data.get(USE_ESB_FIELD_NAME))
				internetShopsServiceProvider.setUrl((String) data.get("url"));
			internetShopsServiceProvider.setBackUrl((String) data.get("providerBackUrl"));
			internetShopsServiceProvider.setAfterAction((Boolean)data.get("backUrlAction"));
			internetShopsServiceProvider.setCheckOrder((Boolean)data.get("checkOrder"));
			internetShopsServiceProvider.setSendChargeOffInfo((Boolean)data.get("sendChargeOffInfo"));
			internetShopsServiceProvider.setFacilitator((Boolean)data.get("isFasilitator"));
		}
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		ServiceProviderBase provider = (ServiceProviderBase) entity;

		frm.setField("id",  provider.getId()!=null ? provider.getId().toString() : "");
		frm.setField("state",   provider.getState());
		GroupRisk groupRisk = provider.getGroupRisk();
		frm.setField("groupRiskId", groupRisk != null ? groupRisk.getId() : "");
		frm.setField("name",    provider.getName());
		frm.setField("sortPriority", provider.getSortPriority());
		frm.setField("inn", provider.getINN());
		frm.setField("kpp", provider.getKPP());
		frm.setField("account", provider.getAccount());
		frm.setField("bankCode",    provider.getBIC());
		frm.setField("bankName",    provider.getBankName());
		frm.setField("corAccount",  provider.getCorrAccount());
		frm.setField("providerCode",    provider.getCode());
		frm.setField("comment", provider.getDescription());
		frm.setField("tip", provider.getTipOfProvider());
		frm.setField("commissionMessage", provider.getCommissionMessage());
		frm.setField("eng4000nsi",  provider.getNSICode());
		frm.setField("providerType",    provider.getId() != null ? provider.getType() : "BILLING"); //первоначально инициализируется биллинговый ПУ
		frm.setField("bankDetails",     provider.isBankDetails());
		frm.setField("phoneNumber", provider.getPhoneNumber());
		frm.setField("standartSmsTemplate",provider.isStandartTemplate());

		frm.setField("format",provider.getTemplateFormat());
		frm.setField("example",provider.getTemplateExample());
		frm.setField("isBarSupported", provider.isBarSupported());
		frm.setField("isOfflineAvailable", provider.isOfflineAvailable());
		frm.setField(ServiceProvidersConstants.PROVIDER_IS_CREDIT_CARD_ACCESSIBLE, !provider.isCreditCardSupported());

		frm.setField("visiblePaymentsForInternetBank", provider.isVisiblePaymentsForInternetBank());
		frm.setField("visiblePaymentsForMApi", provider.isVisiblePaymentsForMApi());
		frm.setField("visiblePaymentsForAtmApi", provider.isVisiblePaymentsForAtmApi());
		frm.setField("visiblePaymentsForSocialApi", provider.isVisiblePaymentsForSocialApi());
		frm.setField("availablePaymentsForInternetBank", provider.isAvailablePaymentsForInternetBank());
		frm.setField("availablePaymentsForMApi", provider.isAvailablePaymentsForMApi());
		frm.setField("availablePaymentsForAtmApi", provider.isAvailablePaymentsForAtmApi());
		frm.setField("availablePaymentsForSocialApi", provider.isAvailablePaymentsForSocialApi());
		frm.setField("availablePaymentsForErmb", provider.isAvailablePaymentsForErmb());
		frm.setField("subType", provider.getSubType());

		frm.setField("transitAccount", provider.getTransitAccount());

		if (provider instanceof BillingServiceProviderBase)
		{
			BillingServiceProviderBase billingServiceProvider = (BillingServiceProviderBase) entity;
			frm.setField("legalName", billingServiceProvider.getLegalName());
			frm.setField("alias", billingServiceProvider.getAlias());
			frm.setField("nameOnBill", billingServiceProvider.getNameOnBill());
			frm.setField("codeRecipientSBOL", billingServiceProvider.getCodeRecipientSBOL());
			frm.setField("accountType", billingServiceProvider.getAccountType());
			frm.setField("serviceCode", billingServiceProvider.getCodeService());
			frm.setField("nameService", billingServiceProvider.getNameService());
			frm.setField("ground",  billingServiceProvider.isGround());
			frm.setField("separator1",  billingServiceProvider.getAttrDelimiter());
			frm.setField("separator2",  billingServiceProvider.getAttrValuesDelimiter());

			frm = setFormBilling(frm, billingServiceProvider); // поля биллинга
			frm = setFormPaymentService(frm, billingServiceProvider);  // поля услуги
		}

		if (provider instanceof BillingServiceProvider)
		{
			BillingServiceProvider billingServiceProvider = (BillingServiceProvider) entity;

			frm.setField("debtSupported", billingServiceProvider.isDeptAvailable());
			frm.setField("popular", billingServiceProvider.isPopular());
			frm.setField("propsOnline", billingServiceProvider.isPropsOnline());
			frm.setField("mobilebank",  billingServiceProvider.isMobilebank());
			frm.setField("mobilebankCode",  billingServiceProvider.getMobilebankCode());
			frm.setField("versionAPI", billingServiceProvider.getVersionAPI());
			frm.setField("federal", billingServiceProvider.isFederal());
			frm.setField("fullPayment", "false");
			frm.setField("templateSupported", billingServiceProvider.isTemplateSupported());
			frm.setField("planingForDeactivate", billingServiceProvider.getPlaningForDeactivate());
			frm.setField("editPaymentSupported", billingServiceProvider.isEditPaymentSupported());

		}

		if (provider instanceof TaxationServiceProvider)
		{
			TaxationServiceProvider taxationServiceProvider = (TaxationServiceProvider) entity;
			frm.setField("fullPayment", taxationServiceProvider.isFullPayment());
			frm.setField("payType", taxationServiceProvider.getPayType());
		}

		if (provider instanceof InternetShopsServiceProvider)
		{
			InternetShopsServiceProvider internetShopsServiceProvider = (InternetShopsServiceProvider) entity;
			frm.setField("codeRecipientSBOL", internetShopsServiceProvider.getCodeRecipientSBOL());
			frm.setField("url", internetShopsServiceProvider.getUrl());
			frm.setField("formName", internetShopsServiceProvider.getFormName());
			frm.setField("providerBackUrl",internetShopsServiceProvider.getBackUrl());
			frm.setField("backUrlAction",internetShopsServiceProvider.isAfterAction());
			frm.setField("checkOrder",internetShopsServiceProvider.isCheckOrder());
			frm.setField("sendChargeOffInfo",internetShopsServiceProvider.isSendChargeOffInfo());
			frm.setField("availableMobileCheckout", internetShopsServiceProvider.isAvailableMobileCheckout());
			frm.setField("isFasilitator", internetShopsServiceProvider.isFacilitator());
		}
	}

	/**
	 * Устанавливает поля услуг на форме
	 * @param form
	 * @param providerBase
	 * @return   форму с добавленными полями
	 */
	private EditFormBase  setFormPaymentService(EditFormBase form, BillingServiceProviderBase providerBase)
	{
		PaymentService paymentService = providerBase.getPaymentService();
		if (paymentService != null)
		{
				form.setField("serviceId",   paymentService.getId());
				form.setField("serviceName", paymentService.getName());
		}
		return form;
	}

	/**
	 * Устанавливает поля биллинга на форме
	 * @param form
	 * @param providerBase
	 * @return   форму с добавленными полями
	 */
	private EditFormBase  setFormBilling(EditFormBase form, BillingServiceProviderBase providerBase)
	{
			Billing billing = providerBase.getBilling();
			if (billing != null)
			{
				form.setField("billingId",   billing.getId());
				form.setField("billingName", billing.getCode());
			}
			return form;
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		updateOperationImageData(editOperation, editForm, validationResult);
		EditServiceProvidersOperation op = (EditServiceProvidersOperation) editOperation;
		if (op.getEntity() instanceof BillingServiceProviderBase)
			op.setBilling(Long.parseLong((String) editForm.getField("billingId")));

		// если доступно редактирование группы риска
		if(checkAccess(SetupGroupRiskServiceProvidersOperation.class, "EditGroupRiskServiceProvider"))
			op.setGroupRisk((Long) validationResult.get("groupRiskId"));

		if (op.getEntity() instanceof InternetShopsServiceProvider)
		{
			ProviderPropertiesEntry providerProperties = op.findProviderProperties();
			InternetShopsServiceProvider provider = (InternetShopsServiceProvider)op.getEntity();
			boolean eInvoicingProperty = Boolean.parseBoolean((String)editForm.getField("eInvoicingProperty"));
			boolean mobileCheckoutProperty = Boolean.parseBoolean((String)editForm.getField("mobileCheckoutProperty"));
			boolean mbCheckProperty = Boolean.parseBoolean((String)editForm.getField("mbCheckProperty"));
			boolean mbCheckEnabled = Boolean.parseBoolean((String)editForm.getField("availableMBCheck"));
			boolean mbCheckChanged = providerProperties.isMbCheckEnabled() != mbCheckEnabled && mbCheckEnabled;
			boolean useESB = Boolean.parseBoolean((String)editForm.getField(USE_ESB_FIELD_NAME));

			boolean availableMobileCheckout = Boolean.parseBoolean((String)editForm.getField("availableMobileCheckout"));
			boolean mobileCheckoutChanged = provider.isAvailableMobileCheckout() != availableMobileCheckout && availableMobileCheckout;
			provider.setAvailableMobileCheckout(availableMobileCheckout);

			if (providerProperties.isEinvoiceDefaultEnabled() != eInvoicingProperty || providerProperties.isMcheckoutDefaultEnabled() != mobileCheckoutProperty ||
					providerProperties.isMbCheckDefaultEnabled() != mbCheckProperty || providerProperties.isMbCheckEnabled() != mbCheckEnabled
					|| providerProperties.isUseESB() != useESB)
				op.updateProviderProperties(eInvoicingProperty, mobileCheckoutProperty, mbCheckProperty, mbCheckEnabled, mbCheckChanged, useESB);
			if (mobileCheckoutChanged)
				op.setMobileCheckoutChanged(availableMobileCheckout);
		}
	}

	protected void updateFormAdditionalImageData(EditFormBase form, String imageId, Image image)
	{
		if (EditServiceProvidersOperation.ICON_IMAGE_ID.equals(imageId))
			form.setField("imageHelp", image.getImageHelp());
		else if (EditServiceProvidersOperation.HELP_IMAGE_ID.equals(imageId))
			form.setField("imageHelpTitle", image.getImageHelp());
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		updateFormImageData(frm, operation);
		EditServiceProvidersOperation op = (EditServiceProvidersOperation) operation;
		frm.setId(op.getEntity().getId());
		if (op.isNew())
		{
			currentRequest().setAttribute("$$newId", op.getEntity().getId());
		}

		ServiceProviderBase provider = op.getEntity();

		String mobileBankCode=new String();
		if(provider instanceof BillingServiceProvider)
			mobileBankCode=((BillingServiceProvider)provider).getMobilebankCode();

		String prefix = MobileBankUtils.getProviderSubserviceCode(provider);

		frm.setField("defaultFormat",smsCommandComposer.getPaymentSmsCommandFormat("#counter#", mobileBankCode, prefix, null));
		frm.setField("defaultExample",smsCommandComposer.getPaymentSmsCommandExample("#counter#", mobileBankCode, prefix, null));
		frm.setField("defaultHint","");

		EditOverviewServiceProviderForm form = (EditOverviewServiceProviderForm) frm;
		form.setGroupsRisk(op.getAllGroupsRisk());
		form.setSmsAliases(op.getAllSmsAlis());

		Department department = op.getDepartment();
		if (department != null)
		{
			frm.setField("departmentId",    department.getId());
			frm.setField("departmentName",  department.getName());
		}

		if (provider instanceof InternetShopsServiceProvider && op.findProviderProperties() != null)
		{
			ProviderPropertiesEntry providerProperties = op.findProviderProperties();
			frm.setField("eInvoicingProperty", providerProperties.isEinvoiceDefaultEnabled());
			frm.setField("mobileCheckoutProperty", providerProperties.isMcheckoutDefaultEnabled());
			frm.setField("mbCheckProperty", providerProperties.isMbCheckDefaultEnabled());
			frm.setField("availableMBCheck", providerProperties.isMbCheckEnabled());
			frm.setField(USE_ESB_FIELD_NAME, providerProperties.isUseESB());
		}
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		EditServiceProvidersOperation op = (EditServiceProvidersOperation) operation;
		ServiceProviderBase providerBase = op.getEntity();
		ActionForward forward = new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS));
		forward.setPath(forward.getPath() + "?id=" + providerBase.getId());

		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.dictionaries.provider.save.success"), null);
		return forward;
	}

	private ServiceProviderType getType(EditFormBase frm)
	{
		Object type = frm.getField("providerType");
		return (type != null) ? ServiceProviderType.valueOf(type.toString()) : null;
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return validateImageFormData(frm, operation);
	}

	protected void updateImageAdditionalData(Image image, String imageId, ImageEditFormBase form)
	{
		if (EditServiceProvidersOperation.ICON_IMAGE_ID.equals(imageId))
			image.setImageHelp((String) form.getField("imageHelp"));
		else if (EditServiceProvidersOperation.HELP_IMAGE_ID.equals(imageId))
			image.setImageHelp((String) form.getField("imageHelpTitle"));
	}

	public ActionForward setupGroupRisk(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditFormBase frm = (EditFormBase) form;

		SetupGroupRiskServiceProvidersOperation operation =
				createOperation("SetupGroupRiskServiceProvidersOperation", "EditGroupRiskServiceProvider");
		operation.initialize(frm.getId());

		FormProcessor<ActionMessages, ?> processor =
				createFormProcessor(getFormProcessorValueSource(frm, operation), EditOverviewServiceProviderForm.SETUP_GROUP_RISK_FORM);

		if(processor.process())
		{
			try
			{
				operation.setGroupRisk((Long) processor.getResult().get("groupRiskId"));
				operation.save();
				// сообщение об успешном сохранении данных
				saveMessage(request, StrutsUtils.getMessage("com.rssl.phizic.web.dictionaries.provider.save.success", "providerBundle"));
			}
			catch (BusinessLogicException e)
			{
				ActionMessages actionErrors = new ActionMessages();
				actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
				saveErrors(request, actionErrors);
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
		stopLogParameters();
		return start(mapping, form, request, response);
	}

	public ActionForward addSmsAlias(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditOverviewServiceProviderForm frm = (EditOverviewServiceProviderForm) form;

		EditServiceProvidersOperation op = createOperation("EditServiceProvidersOperation");
		op.initialize(frm.getId());
		FormProcessor<ActionMessages, ?> processor =
				createFormProcessor(getFormProcessorValueSource(frm, op), EditOverviewServiceProviderForm.SMS_ALIAS_FORM);

		if(processor.process())
		{
			try
			{
				op.addSmsAlias((String) processor.getResult().get("smsAlias"));
			}
			catch (BusinessLogicException e)
			{
				saveMessage(request, e.getMessage());
				ActionMessages actionErrors = new ActionMessages();
				actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
				saveErrors(request, actionErrors);
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}

		return start(mapping, form, request, response);
	}

	public ActionForward removeSmsAlias(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditOverviewServiceProviderForm frm = (EditOverviewServiceProviderForm) form;

		EditServiceProvidersOperation op = createOperation("EditServiceProvidersOperation");
		op.initialize(frm.getId());
		op.removeSmsAlias(frm.getSelectedIds());
		return start(mapping, form, request, response);
	}


}

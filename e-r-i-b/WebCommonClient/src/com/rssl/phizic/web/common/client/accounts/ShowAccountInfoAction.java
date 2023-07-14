package com.rssl.phizic.web.common.client.accounts;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.TargetType;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.account.AccountMessageConfig;
import com.rssl.phizic.operations.account.EditExternalLinkOperation;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.ermb.EditProductAliasOperation;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.finances.targets.EditAccountTargetOperation;
import com.rssl.phizic.operations.finances.targets.GetTargetOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.client.finances.targets.EditTargetForm;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountInfoAction extends OperationalActionBase
{
	private static final String ACCOUNT_SMS_ALIAS_FIELD = "clientSmsAlias";
	private static final String ACCOUNT_AUTO_SMS_ALIAS_FIELD = "autoSmsAlias";
	private static final String ACCOUNT_NAME_FIELD = "accountName";
	
	protected Map<String, String> getKeyMethodMap()
    {
	    Map<String, String> keyMap = new HashMap<String, String>();
	    keyMap.put("button.saveAccountName", "saveAccountName");
	    keyMap.put("button.saveClientSmsAlias", "saveClientSmsAlias");
	    keyMap.put("button.saveTargetParams", "saveTargetParams");
        return keyMap;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception
    {
	    ShowAccountInfoForm frm = (ShowAccountInfoForm) form;
	    Long linkId = frm.getId();

	    GetAccountAbstractExtendedOperation operation = createOperation(GetAccountAbstractExtendedOperation.class);
		operation.initialize(linkId);

	    updateFormData(frm, operation);

	    if (operation.isBackError())
	    {
		    ActionMessages mess = new ActionMessages();
		    mess.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(getResourceMessage("commonBundle", "error.errorHeader"),false));
			addMessages(request, mess);
	    }

	    if (operation.isUseStoredResource())
	    {
		    saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) operation.getAccount().getAccount()));
	    }

	    return mapping.findForward(FORWARD_SHOW);
    }

	private void updateFormData(ShowAccountInfoForm form, GetAccountAbstractExtendedOperation operation) throws BusinessException, BusinessLogicException
	{
		AccountLink accountLink = operation.getAccount();

		form.setAccountLink(accountLink);

		if (accountLink != null)
		{
			form.setField(ACCOUNT_NAME_FIELD,accountLink.getName());
			// если клиент имеет ЕРМБ-профиль, то надо установить значения смс-алиасов
			if (ErmbHelper.isERMBConnectedPerson())
			{
				String autoSmsAlias = accountLink.getAutoSmsAlias();
				form.setField(ACCOUNT_AUTO_SMS_ALIAS_FIELD, autoSmsAlias);
				form.setField(ACCOUNT_SMS_ALIAS_FIELD, StringUtils.defaultIfEmpty(accountLink.getErmbSmsAlias(), autoSmsAlias));
			}
		}

	    GetAccountsOperation operationAccounts = createOperation(GetAccountsOperation.class);
		List<AccountLink> otherAccountsLinks = operationAccounts.getActiveAccounts();
		if (otherAccountsLinks.contains(accountLink))
		{
			otherAccountsLinks.remove(accountLink);
		}
		form.setOtherAccounts(otherAccountsLinks);

		if (checkAccess(GetTargetOperation.class))
		{
			GetTargetOperation targetOperation = createOperation(GetTargetOperation.class);
			AccountTarget target = targetOperation.getTargetByAccountId(accountLink.getId());
			if (target != null)
			{
				form.setTarget(target);
				form.setField("dictionaryTarget", target.getDictionaryTarget());
				form.setField("targetName", target.getName());
				form.setField("targetNameComment", target.getNameComment());
				form.setField("targetPlanedDate", DateHelper.toDate(target.getPlannedDate()));
				form.setField("targetAmount", target.getAmount().getDecimal());
			}
		}

		form.setAccountMaxBalanceMessage(ConfigFactory.getConfig(AccountMessageConfig.class).getAccountMaxBallanceMessage());
	}

	public ActionForward saveAccountName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowAccountInfoForm frm = (ShowAccountInfoForm) form;
		Long linkId = frm.getId();
		EditExternalLinkOperation operation = createOperation(EditExternalLinkOperation.class);
		operation.initialize(linkId, AccountLink.class);

        FieldValuesSource valuesSource = getSaveAccountNameFieldValuesSource(frm);

		Form editForm = ShowAccountInfoForm.EDIT_ACCOUNT_NAME_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editForm);

		if(processor.process())
		{
            Map<String, Object> result = processor.getResult();
			operation.saveLinkName((String) result.get(ACCOUNT_NAME_FIELD));
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
        return forwardSaveAccountName(mapping, form, request, response);
	}

    protected MapValuesSource getSaveAccountNameFieldValuesSource(ShowAccountInfoForm form)
    {
        return new MapValuesSource(form.getFields());
    }

    protected ActionForward forwardSaveAccountName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return start(mapping, form, request, response);
    }

	public ActionForward saveClientSmsAlias(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowAccountInfoForm frm = (ShowAccountInfoForm) form;
		Long linkId = frm.getId();
		EditProductAliasOperation operation = createOperation(EditProductAliasOperation.class);
		operation.initialize(linkId, AccountLink.class);

		Map<String, Object> map = frm.getFields();
		FieldValuesSource valuesSource = new MapValuesSource(map);

		Form editForm = frm.editAccountAliasForm();
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editForm);

		if(processor.process())
		{
			operation.saveProductSmsAlias((String)frm.getField(ACCOUNT_SMS_ALIAS_FIELD));
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return start(mapping, form, request, response);
	}

	/**
	 * сохранение новых параметров цели, привязанной к вкладу
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveTargetParams(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowAccountInfoForm frm = (ShowAccountInfoForm) form;
		Long linkId = frm.getId();

		EditAccountTargetOperation targetOperation = createOperation(EditAccountTargetOperation.class);
		targetOperation.initializeByAccountLink(linkId);

		Map<String, Object> fields = frm.getFields();
		FieldValuesSource valuesSource = new MapValuesSource(fields);

		Form editForm = EditTargetForm.EDIT_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editForm);

		if(processor.process())
		{
			Map<String, Object> data = processor.getResult();
			AccountTarget target = targetOperation.getEntity();
			Currency nationalCurrency = MoneyUtil.getNationalCurrency();
			//Значение имени может изменяться только для не словарного типа цели
			if(TargetType.OTHER == target.getDictionaryTarget())
				target.setName((String) data.get("targetName"));
			target.setNameComment((String) data.get("targetNameComment"));
			target.setAmount(new Money((BigDecimal) data.get("targetAmount"), nationalCurrency));
			target.setPlannedDate(DateHelper.toCalendar((Date) data.get("targetPlanedDate")));

			targetOperation.save();
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return start(mapping, form, request, response);
	}

	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		ShowAccountInfoForm frm = (ShowAccountInfoForm) form;
		return mapping.getPath().concat((frm.getTarget()!=null?  "/target" : ""));
	}
}

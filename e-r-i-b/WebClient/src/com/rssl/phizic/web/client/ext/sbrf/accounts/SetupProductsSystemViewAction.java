package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.auth.modes.iPasSmsPasswordConfirmRequest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.account.*;
import com.rssl.phizic.operations.ext.sbrf.strategy.iPasCapConfirmRequest;
import com.rssl.phizic.operations.userprofile.ProductsViewSettingsOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.web.actions.NoActiveOperationException;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.client.userprofile.EditUserProfileActionBase;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.ext.sbrf.products.ProductsSetupForm;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.struts.action.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author potehin
 * @ created 21.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class SetupProductsSystemViewAction extends EditUserProfileActionBase
{
	private static final String PRECONFIRM_MESSAGE = ".settings.security.message";
	private static final String INFO_MESSAGE = "В настоящее время изменение настройки видимости по %s недоступно. Повторите попытку позднее";
	private static final String EMPTY_CHANGE_MESSAGE = "Вы не внесли никаких изменений в настройки видимости продуктов.";
	private static final String FORWARD_LIST = "List";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> keys = new HashMap<String,String>();
		keys.put("button.saveShowInSystem", "saveShowInSystem");
		keys.put("button.saveShowInES", "saveShowInES");
		keys.put("button.saveShowInMobile", "saveShowInMobile");
		keys.put("button.saveShowInSocial", "saveShowInSocial");
		keys.put("button.saveShowInErmb", "saveShowInErmb");

		keys.put("button.backToEdit", "backToEdit");
		keys.put("button.nextStage", "doNextStage"); //переход на след. шаг из ajax-action'а
		keys.put("button.preConfirmSMS", "preConfirmSMS"); //действие на получение смс-пароля для входа на страницу
		keys.put("button.preConfirmPush", "preConfirmPush"); //действие на получение push-пароля для входа на страницу
		keys.put("button.preConfirmCap", "preConfirmCAP"); //действие на получение cap-пароля для входа на страницу
		keys.put("button.confirmEnter", "confirmEnter"); //подтверждение входа на страницу

		keys.put("button.preConfirmSystemSMS", "preConfirmSystemSMS"); //действие на получение смс-пароля для изменения настроек видимости в системе
		keys.put("button.preConfirmSystemPush", "preConfirmSystemPush"); //действие на получение push-пароля для изменения настроек видимости в системе
		keys.put("button.preConfirmSystemCap", "preConfirmSystemCAP"); //действие на получение cap-пароля для изменения настроек видимости в системе
		keys.put("button.preConfirmESSMS", "preConfirmESSMS"); //действие на получение смс-пароля для изменения настроек видимости в УС
		keys.put("button.preConfirmESPush", "preConfirmESPush"); //действие на получение push-пароля для изменения настроек видимости в УС
		keys.put("button.preConfirmESCap", "preConfirmESCAP"); //действие на получение cap-пароля для изменения настроек видимости в УС
		keys.put("button.preConfirmMobileSMS", "preConfirmMobileSMS"); //действие на получение смс-пароля для изменения настроек видимости в iPhone/iPad
		keys.put("button.preConfirmMobilePush", "preConfirmMobilePush"); //действие на получение push-пароля для изменения настроек видимости в iPhone/iPad
		keys.put("button.preConfirmMobileCap", "preConfirmMobileCAP"); //действие на получение cap-пароля для изменения настроек видимости в iPhone/iPad
		keys.put("button.preConfirmSocialSMS", "preConfirmSocialSMS"); //действие на получение смс-пароля для изменения настроек видимости в iPhone/iPad
		keys.put("button.preConfirmSocialPush", "preConfirmSocialPush"); //действие на получение push-пароля для изменения настроек видимости в iPhone/iPad
		keys.put("button.preConfirmSocialCap", "preConfirmSocialCAP"); //действие на получение cap-пароля для изменения настроек видимости в iPhone/iPad
		keys.put("button.preConfirmErmbSMS", "preConfirmErmbSMS"); //действие на получение смс-пароля для изменения настроек видимости в смс-канале (ЕРМБ)
		keys.put("button.preConfirmErmbPush", "preConfirmErmbPush"); //действие на получение push-пароля для изменения настроек видимости в смс-канале (ЕРМБ)
		keys.put("button.preConfirmErmbCap", "preConfirmErmbCAP"); //действие на получение cap-пароля для изменения настроек видимости в смс-канале (ЕРМБ)

		keys.put("button.confirmSystem", "confirmSystem"); //подтверждение  изменения настроек видимости в системе
		keys.put("button.confirmES", "confirmES");      //подтверждение  изменения настроек видимости в УС
		keys.put("button.confirmMobile", "confirmMobile");  //подтверждение  изменения настроек видимости в в iPhone/iPad
		keys.put("button.confirmSocial", "confirmSocial");  //подтверждение  изменения настроек видимости в в iPhone/iPad
		keys.put("button.confirmErmb", "confirmErmb");  //подтверждение  изменения настроек видимости в смс-канале (ЕРМБ)

		return keys;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ProductsSetupForm frm = (ProductsSetupForm) form;
		if (isNotConfirmEnter())
		{
			ProductsViewSettingsOperation operation = createOperation(ProductsViewSettingsOperation.class);
			operation.initialize();
			operation.resetConfirmStrategy();
			ConfirmationManager.sendRequest(operation);
			frm.setConfirmStrategy(operation.getConfirmStrategy());
			frm.setConfirmableObject(operation.getConfirmableObject());
			saveOperation(request,operation);
			return mapping.findForward(FORWARD_START);
		}
		return doNextStage(mapping, form, request, response);
	}

	public ActionForward preConfirmSMS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return preConfirm(mapping, form, request, response, ConfirmStrategyType.sms);
	}

	public ActionForward preConfirmPush(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return preConfirm(mapping, form, request, response, ConfirmStrategyType.push);
	}

	public ActionForward preConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse respons, ConfirmStrategyType confirmStrategy) throws Exception
	{
		ProductsViewSettingsOperation operation = getOperation(request);
		operation.setUserStrategyType(confirmStrategy);
		ConfirmationManager.sendRequest(operation);
		ProductsSetupForm frm = (ProductsSetupForm) form;
		frm.setConfirmableObject(operation.getConfirmableObject());

		try
		{
			operation.getRequest().setPreConfirm(true);
			PreConfirmObject preConfirmObject = operation.preConfirm(createCallBackHandler(confirmStrategy, operation.getLogin(), operation.getConfirmableObject()));
			operation.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityLogicException e)
		{
            operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
		}
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward preConfirmCAP(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ProductsViewSettingsOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.cap);
		ConfirmationManager.sendRequest(operation);
		ProductsSetupForm frm = (ProductsSetupForm) form;
		frm.setConfirmableObject(operation.getConfirmableObject());
		operation.getRequest().setPreConfirm(true);
		operation.getRequest().addMessage(ConfirmHelper.getPreConfirmCapString());
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return mapping.findForward(FORWARD_START);
	}


	public ActionForward confirmEnter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ProductsSetupForm frm = (ProductsSetupForm)form;

		ProductsViewSettingsOperation operation = getOperation(request);

		List<String> errors = validateConfirmResponse(operation, request);
		if (!errors.isEmpty() )
		{
			operation.getRequest().setErrorMessage(errors.get(0));
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_START);
		}
		operation.getRequest().setPreConfirm(false);
		ConfirmRequest confirmRequest = operation.getRequest();

		if (confirmRequest instanceof iPasSmsPasswordConfirmRequest)
			((iPasSmsPasswordConfirmRequest)confirmRequest).setConfirmEnter(true);
		else
			((iPasCapConfirmRequest)confirmRequest).setConfirmEnter(true);

		return doNextStage(mapping, form, request, response);
	}


	//сохранить настройки видимости продуктов в системе
	public ActionForward saveShowInSystem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm) form;
		SetupProductsSystemViewOperation operation = createOperation(SetupProductsSystemViewOperation.class);
		operation.initialize();
		operation.updateResourcesSettings(frm.getSelectedAccountIds(),    frm.getSelectedCardIds(),
										  frm.getSelectedLoanIds(),    frm.getSelectedDepoAccountIds(), frm.getSelectedIMAccountIds(), frm.getSelectedSecurityAccountIds(),
										  frm.getPfrLinkSelected());
		operation.resetConfirmStrategy();
		updateFormData(operation, frm);
		frm.setField("pageType", "system");

		if (operation.noChanges())
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_CHANGE_MESSAGE, false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}

		ConfirmationManager.sendRequest(operation);
		setMessage(request);
		saveOperation(request,operation);
		return mapping.findForward(FORWARD_LIST);
	}

	//сохранить настройки видимости продуктов в устройствах самообслуживания
	public ActionForward saveShowInES(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm) form;
		SetupProductsRCSViewOperation operation = createOperation(SetupProductsRCSViewOperation.class);
		operation.initialize();
		if (PermissionUtil.impliesServiceRigid("ProductsSiriusView"))
			operation.updateResourcesSettings(frm.getSelectedAccountESIds(), frm.getSelectedCardESIds(), frm.getSelectedIMAccountESIds(), frm.getSelectedLoanESIds());
		else
			operation.updateAccountSettingsInES(frm.getSelectedAccountESIds());

		operation.resetConfirmStrategy();
		updateFormData(operation, frm);
		frm.setField("pageType", "ES");

		if (operation.noChanges())
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_CHANGE_MESSAGE, false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}

		ConfirmationManager.sendRequest(operation);
		setMessage(request);
		saveOperation(request,operation);

		return mapping.findForward(FORWARD_LIST);
	}
	//сохранить настройки видимости продуктов в iPhone/iPad
	public ActionForward saveShowInMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm) form;
		SetupProductsMobileViewOperation operation = createOperation(SetupProductsMobileViewOperation.class);
		operation.initialize();
		operation.updateResourcesSettingsInMobile(frm.getSelectedAccountMobileIds(), frm.getSelectedCardMobileIds(), frm.getSelectedLoanMobileIds(), frm.getSelectedIMAccountMobileIds());
		operation.resetConfirmStrategy();
		updateFormData(operation, frm);
		frm.setField("pageType", "mobile");

		if (operation.noChanges())
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_CHANGE_MESSAGE, false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}

		ConfirmationManager.sendRequest(operation);
		//если клиент только отключает видимость, то сообщение не отображаем
		if (operation.getStrategyType() != ConfirmStrategyType.none)
			setMessage(request);

		saveOperation(request,operation);
		return mapping.findForward(FORWARD_LIST);
	}

    public ActionForward saveShowInSocial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        if (isNotConfirmEnter())
            return  mapping.findForward(FORWARD_START);

        ProductsSetupForm frm = (ProductsSetupForm) form;
        SetupProductsSocialViewOperation operation = createOperation(SetupProductsSocialViewOperation.class);
        operation.initialize();
        operation.updateResourcesSettingsInSocial(frm.getSelectedAccountSocialIds(), frm.getSelectedCardSocialIds(), frm.getSelectedLoanSocialIds(), frm.getSelectedIMAccountSocialIds());
        operation.resetConfirmStrategy();
        updateFormData(operation, frm);
        frm.setField("pageType", "social");

        if (operation.noChanges())
        {
            ActionMessages message = new ActionMessages();
            message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_CHANGE_MESSAGE, false));
            saveErrors(request, message);
            return start(mapping, form, request, response);
        }

        ConfirmationManager.sendRequest(operation);
        //если клиент только отключает видимость, то сообщение не отображаем
        if (operation.getStrategyType() != ConfirmStrategyType.none)
            setMessage(request);

        saveOperation(request,operation);
        return mapping.findForward(FORWARD_LIST);
    }

	//сохранить настройки видимости продуктов в смс-канале
	public ActionForward saveShowInErmb(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm) form;
		SetupProductsSMSViewOperation operation = createOperation(SetupProductsSMSViewOperation.class);
		operation.initialize();
		operation.updateResourcesSettings(frm.getSelectedAccountSMSIds(), frm.getSelectedCardSMSIds(), frm.getSelectedLoanSMSIds(), frm.isNewProductsShowInSms());
		operation.resetConfirmStrategy();
		updateFormData(operation, frm);
		frm.setField("pageType", "SMS");

		if (operation.noChanges())
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_CHANGE_MESSAGE, false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}

		ConfirmationManager.sendRequest(operation);
		setMessage(request);
		saveOperation(request,operation);

		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward confirmSystem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm)form;
		SetupProductsSystemViewOperation operation = getOperation(request);
		operation.updateResourcesSettings(frm.getSelectedAccountIds(), frm.getSelectedCardIds(),
				frm.getSelectedLoanIds(), frm.getSelectedDepoAccountIds(), frm.getSelectedIMAccountIds(), frm.getSelectedSecurityAccountIds(),
				frm.getPfrLinkSelected());
		updateFormData(operation, frm);
		return confirmSettings(operation, mapping, frm, request, response);
	}

	public ActionForward confirmES(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm)form;
		SetupProductsRCSViewOperation operation = getOperation(request);
		if (PermissionUtil.impliesServiceRigid("ProductsSiriusView"))
			operation.updateResourcesSettings(frm.getSelectedAccountESIds(), frm.getSelectedCardESIds(), frm.getSelectedIMAccountESIds(), frm.getSelectedLoanESIds());
		else
			operation.updateAccountSettingsInES(frm.getSelectedAccountESIds());
		updateFormData(operation,frm);
		return confirmSettings(operation, mapping, frm, request, response);
	}

	public ActionForward confirmMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm)form;
		SetupProductsMobileViewOperation operation = getOperation(request);
		operation.updateResourcesSettingsInMobile(frm.getSelectedAccountMobileIds(), frm.getSelectedCardMobileIds(), frm.getSelectedLoanMobileIds(), frm.getSelectedIMAccountMobileIds());
		updatePersonInfoData(operation, frm);
		return confirmSettings(operation, mapping, frm, request, response);
	}

	public ActionForward confirmSocial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm)form;
		SetupProductsSocialViewOperation operation = getOperation(request);
		operation.updateResourcesSettingsInSocial(frm.getSelectedAccountSocialIds(), frm.getSelectedCardSocialIds(), frm.getSelectedLoanSocialIds(), frm.getSelectedIMAccountSocialIds());
		updatePersonInfoData(operation, frm);
		return confirmSettings(operation, mapping, frm, request, response);
	}

	public ActionForward confirmErmb(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm)form;
		SetupProductsSMSViewOperation operation = getOperation(request);
		updatePersonInfoData(operation, frm);
		return confirmSettings(operation, mapping, frm, request, response);
	}

	public ActionForward preConfirmSystemSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmSystem(mapping, form, request, ConfirmStrategyType.sms);
	}

	public ActionForward preConfirmSystemPush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmSystem(mapping, form, request, ConfirmStrategyType.push);
	}

	private ActionForward preConfirmSystem(ActionMapping mapping, ActionForm form, HttpServletRequest request, ConfirmStrategyType strategyType) throws BusinessLogicException, BusinessException, NoActiveOperationException
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm) form;
		SetupProductsSystemViewOperation operation = getOperation(request);
		operation.setUserStrategyType(strategyType);
		operation.updateResourcesSettings(frm.getSelectedAccountIds(),  frm.getSelectedCardIds(),
										  frm.getSelectedLoanIds(),    frm.getSelectedDepoAccountIds(), frm.getSelectedIMAccountIds(), frm.getSelectedSecurityAccountIds(),
										  frm.getPfrLinkSelected());
		updateFormData(operation,frm);
		frm.setField("pageType", "system");
		preConfirmAction(request, operation, strategyType + PRECONFIRM_MESSAGE, strategyType);
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmSystemCAP( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm) form;
		SetupProductsSystemViewOperation operation = getOperation(request);
		operation.updateResourcesSettings(frm.getSelectedAccountIds(),  frm.getSelectedCardIds(),
				frm.getSelectedLoanIds(),    frm.getSelectedDepoAccountIds(), frm.getSelectedIMAccountIds(),frm.getSelectedSecurityAccountIds(),
				frm.getPfrLinkSelected());
		updateFormData(operation,frm);
		frm.setField("pageType", "system");
		operation.setUserStrategyType(ConfirmStrategyType.cap);
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		operation.getRequest().addMessage(ConfirmHelper.getPreConfirmCapString());
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmESSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmES(mapping, form, request, ConfirmStrategyType.sms);
	}

	public ActionForward preConfirmESPush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmES(mapping, form, request, ConfirmStrategyType.push);
	}

	private ActionForward preConfirmES(ActionMapping mapping, ActionForm form, HttpServletRequest request, ConfirmStrategyType strategyType) throws BusinessLogicException, BusinessException, NoActiveOperationException
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm) form;
		SetupProductsRCSViewOperation operation = getOperation(request);
		operation.setUserStrategyType(strategyType);
		if (PermissionUtil.impliesServiceRigid("ProductsSiriusView"))
			operation.updateResourcesSettings(frm.getSelectedAccountESIds(), frm.getSelectedCardESIds(), frm.getSelectedIMAccountESIds(), frm.getSelectedLoanESIds());
		else
			operation.updateAccountSettingsInES(frm.getSelectedAccountESIds());
		updateFormData(operation,frm);
		frm.setField("pageType", "ES");
		preConfirmAction(request, operation, strategyType + PRECONFIRM_MESSAGE, strategyType);
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmESCAP( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm) form;
		SetupProductsRCSViewOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.cap);
		if (PermissionUtil.impliesServiceRigid("ProductsSiriusView"))
			operation.updateResourcesSettings(frm.getSelectedAccountESIds(), frm.getSelectedCardESIds(), frm.getSelectedIMAccountESIds(), frm.getSelectedLoanESIds());
		else
			operation.updateAccountSettingsInES(frm.getSelectedAccountESIds());
		updateFormData(operation,frm);
		frm.setField("pageType", "ES");
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		operation.getRequest().addMessage(ConfirmHelper.getPreConfirmCapString());
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmMobileSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmMobile(mapping, form, request, ConfirmStrategyType.sms);
	}

	public ActionForward preConfirmMobilePush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmMobile(mapping, form, request, ConfirmStrategyType.push);
	}

	private ActionForward preConfirmMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, ConfirmStrategyType strategyType) throws BusinessLogicException, BusinessException, NoActiveOperationException
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm) form;
		SetupProductsMobileViewOperation operation = getOperation(request);
		operation.setUserStrategyType(strategyType);
		operation.updateResourcesSettingsInMobile(frm.getSelectedAccountMobileIds(), frm.getSelectedCardMobileIds(), frm.getSelectedLoanMobileIds(), frm.getSelectedIMAccountMobileIds());
		updateFormData(operation,frm);
		frm.setField("pageType", "mobile");
		preConfirmAction(request, operation, strategyType + PRECONFIRM_MESSAGE, strategyType);
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmMobileCAP( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm) form;
		SetupProductsMobileViewOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.cap);
		operation.updateResourcesSettingsInMobile(frm.getSelectedAccountMobileIds(), frm.getSelectedCardMobileIds(), frm.getSelectedLoanMobileIds(), frm.getSelectedIMAccountMobileIds());
		updateFormData(operation,frm);
		frm.setField("pageType", "mobile");
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		operation.getRequest().addMessage(ConfirmHelper.getPreConfirmCapString());
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmSocialSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmSocial(mapping, form, request, ConfirmStrategyType.sms);
	}

	public ActionForward preConfirmSocialPush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmSocial(mapping, form, request, ConfirmStrategyType.push);
	}

	private ActionForward preConfirmSocial(ActionMapping mapping, ActionForm form, HttpServletRequest request, ConfirmStrategyType strategyType) throws BusinessLogicException, BusinessException, NoActiveOperationException
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm) form;
		SetupProductsSocialViewOperation operation = getOperation(request);
		operation.setUserStrategyType(strategyType);
		operation.updateResourcesSettingsInSocial(frm.getSelectedAccountSocialIds(), frm.getSelectedCardSocialIds(), frm.getSelectedLoanSocialIds(), frm.getSelectedIMAccountSocialIds());
		updateFormData(operation,frm);
		frm.setField("pageType", "social");
		preConfirmAction(request, operation, strategyType + PRECONFIRM_MESSAGE, strategyType);
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmSocialCAP( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm) form;
		SetupProductsSocialViewOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.cap);
		operation.updateResourcesSettingsInSocial(frm.getSelectedAccountSocialIds(), frm.getSelectedCardSocialIds(), frm.getSelectedLoanSocialIds(), frm.getSelectedIMAccountSocialIds());
		updateFormData(operation,frm);
		frm.setField("pageType", "social");
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		operation.getRequest().addMessage(ConfirmHelper.getPreConfirmCapString());
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmErmbSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmErmb(mapping, form, request, ConfirmStrategyType.sms);
	}

	public ActionForward preConfirmErmbPush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirmErmb(mapping, form, request, ConfirmStrategyType.push);
	}

	private ActionForward preConfirmErmb(ActionMapping mapping, ActionForm form, HttpServletRequest request, ConfirmStrategyType strategyType) throws BusinessLogicException, BusinessException, NoActiveOperationException
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm) form;
		SetupProductsSMSViewOperation operation = getOperation(request);
		operation.setUserStrategyType(strategyType);
		updateFormData(operation,frm);
		frm.setField("pageType", "SMS");
		preConfirmAction(request, operation, strategyType + PRECONFIRM_MESSAGE, strategyType);
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward preConfirmErmbCAP( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		ProductsSetupForm frm = (ProductsSetupForm) form;
		SetupProductsSMSViewOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.cap);
		updateFormData(operation,frm);
		frm.setField("pageType", "SMS");
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		operation.getRequest().addMessage(ConfirmHelper.getPreConfirmCapString());
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return mapping.findForward(FORWARD_LIST);
	}

	public ActionForward doNextStage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (isNotConfirmEnter())
			return mapping.findForward(FORWARD_START);

		SetupProductsSystemViewOperation operation = createOperation(SetupProductsSystemViewOperation.class);
		operation.initialize();
		return doNextStage(operation, mapping, form, request, response);
	}

	protected ActionForward doNextStage(SetupProductsSystemViewOperation operation, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ProductsSetupForm frm = (ProductsSetupForm)form;

		frm.setAccounts(operation.getClientAccounts());
		frm.setCards(operation.getClientCards());
		frm.setLoans(operation.getClientLoans());
		frm.setDepoAccounts(operation.getClientDepoAccounts());
		frm.setImAccounts(operation.getClientIMAccounts());
		frm.setSecurityAccounts(operation.getClientSecurityAccounts());
		frm.setPfrLink(operation.getClientPfrLink());
		if (operation.getClientPfrLink() != null)
			frm.setPfrLinkSelected(operation.getClientPfrLink().getShowInSystem());
		frm.setSNILS(PersonContext.getPersonDataProvider().getPersonData().getPerson().getSNILS());

		ArrayList<String> selectedIds = new ArrayList<String>();
		ArrayList<String> selectedMobileIds = new ArrayList<String>();
		ArrayList<String> selectedSocialIds = new ArrayList<String>();
		ArrayList<String> selectedATMIds = new ArrayList<String>();
		ArrayList<String> selectedSMSIds = new ArrayList<String>();
		for (AccountLink accountLink : frm.getAccounts())
		{
			if (accountLink.getShowInSystem())
				selectedIds.add(accountLink.getId().toString());
			if (accountLink.getShowInMobile())
				selectedMobileIds.add(accountLink.getId().toString());
			if (accountLink.getShowInSocial())
				selectedSocialIds.add(accountLink.getId().toString());
			if (accountLink.isShowInATM())
				selectedATMIds.add(accountLink.getId().toString());
			if (accountLink.getShowInSms())
				selectedSMSIds.add(accountLink.getId().toString());
		}
		frm.setSelectedAccountIds(selectedIds.toArray(new String[0]));
		frm.setSelectedAccountMobileIds(selectedMobileIds.toArray(new String[0]));
		frm.setSelectedAccountSocialIds(selectedSocialIds.toArray(new String[0]));
		frm.setSelectedAccountESIds(selectedATMIds.toArray(new String[0]));
		frm.setSelectedAccountSMSIds(selectedSMSIds.toArray(new String[0]));

		selectedIds.clear();
		selectedMobileIds.clear();
		selectedSocialIds.clear();
		selectedATMIds.clear();
		selectedSMSIds.clear();
		
		for (CardLink cardLink : frm.getCards())
		{
			String cardLinkId =  cardLink.getId().toString();
			if (cardLink.getShowInSystem())
				selectedIds.add(cardLinkId);
			if (cardLink.getShowInMobile())
				selectedMobileIds.add(cardLinkId);
			if (cardLink.getShowInSocial())
				selectedSocialIds.add(cardLinkId);
			if (cardLink.isShowInATM())
				selectedATMIds.add(cardLinkId);
			if (cardLink.getShowInSms())
				selectedSMSIds.add(cardLinkId);
		}
		frm.setSelectedCardIds(selectedIds.toArray(new String[0]));
		frm.setSelectedCardMobileIds(selectedMobileIds.toArray(new String[0]));
		frm.setSelectedCardSocialIds(selectedSocialIds.toArray(new String[0]));
		frm.setSelectedCardESIds(selectedATMIds.toArray(new String[0]));
		frm.setSelectedCardSMSIds(selectedSMSIds.toArray(new String[0]));

		selectedIds.clear();
		selectedMobileIds.clear();
		selectedSocialIds.clear();
		selectedATMIds.clear();
		selectedSMSIds.clear();

		for (LoanLink loanLink : frm.getLoans())
		{
			String loanLinkId = loanLink.getId().toString();
			if (loanLink.getShowInSystem())
				selectedIds.add(loanLinkId);
			if (loanLink.getShowInMobile())
				selectedMobileIds.add(loanLinkId);
			if (loanLink.getShowInSocial())
				selectedSocialIds.add(loanLinkId);
			if (loanLink.isShowInATM())
				selectedATMIds.add(loanLinkId);
			if (loanLink.getShowInSms())
				selectedSMSIds.add(loanLinkId);

		}
		frm.setSelectedLoanIds(selectedIds.toArray(new String[0]));
		frm.setSelectedLoanMobileIds(selectedMobileIds.toArray(new String[0]));
		frm.setSelectedLoanSocialIds(selectedSocialIds.toArray(new String[0]));
		frm.setSelectedLoanESIds(selectedATMIds.toArray(new String[0]));
		frm.setSelectedLoanSMSIds(selectedSMSIds.toArray(new String[0]));

		selectedIds.clear();
		for (DepoAccountLink depoAccountLink : frm.getDepoAccounts())
		{
			if (depoAccountLink.getShowInSystem())
				selectedIds.add(depoAccountLink.getId().toString());
		}
		frm.setSelectedDepoAccountIds(selectedIds.toArray(new String[0]));

		selectedIds.clear();
		for (SecurityAccountLink securityAccountLink : frm.getSecurityAccounts())
		{
			if (securityAccountLink.getShowInSystem())
				selectedIds.add(securityAccountLink.getId().toString());
		}
		frm.setSelectedSecurityAccountIds(selectedIds.toArray(new String[0]));

		selectedIds.clear();
		selectedMobileIds.clear();
		selectedSocialIds.clear();
		selectedATMIds.clear();
		selectedSMSIds.clear();

		for (IMAccountLink imAccountLink : frm.getImAccounts())
		{
			if (imAccountLink.getShowInSystem())
				selectedIds.add(imAccountLink.getId().toString());
			if (imAccountLink.getShowInMobile())
				selectedMobileIds.add(imAccountLink.getId().toString());
			if (imAccountLink.getShowInSocial())
				selectedSocialIds.add(imAccountLink.getId().toString());
			if (imAccountLink.isShowInATM())
				selectedATMIds.add(imAccountLink.getId().toString());
		}
		frm.setSelectedIMAccountIds(selectedIds.toArray(new String[0]));
		frm.setSelectedIMAccountMobileIds(selectedMobileIds.toArray(new String[0]));
		frm.setSelectedIMAccountSocialIds(selectedSocialIds.toArray(new String[0]));
		frm.setSelectedIMAccountESIds(selectedATMIds.toArray(new String[0]));

		frm.setNewProductsShowInSms(operation.isNewProductShowInSms());

		return mapping.findForward(FORWARD_LIST);
	}

	private ActionMessage getInfoMessage(List<AccountLink> links)
	{
		StringBuilder linkNames = new StringBuilder();
		for (AccountLink link : links)
		{
			linkNames.append("\"" + link.getName() + " " + link.getNumber() + "\"" + ", ");
		}
		linkNames = linkNames.delete(linkNames.length()-1, linkNames.length());
		return new ActionMessage(String.format(INFO_MESSAGE, linkNames), false);
	}

	public ActionForward backToEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (isNotConfirmEnter())
			return  mapping.findForward(FORWARD_START);

		SetupProductsSystemViewOperation operation = createOperation(SetupProductsSystemViewOperation.class);
		operation.initialize();
		ProductsSetupForm frm = (ProductsSetupForm) form;
		frm.setAccounts(operation.getClientAccounts());
		frm.setCards(operation.getClientCards());
		frm.setLoans(operation.getClientLoans());
		frm.setDepoAccounts(operation.getClientDepoAccounts());
		frm.setImAccounts(operation.getClientIMAccounts());
		frm.setSecurityAccounts(operation.getClientSecurityAccounts());
		frm.setPfrLink(operation.getClientPfrLink());
		frm.setSNILS(PersonContext.getPersonDataProvider().getPersonData().getPerson().getSNILS());
		frm.setField("unsavedData", true);

		operation.updateResourcesSettings(frm.getSelectedAccountIds(), frm.getSelectedCardIds(),
										  frm.getSelectedLoanIds(),    frm.getSelectedDepoAccountIds(), frm.getSelectedIMAccountIds(), frm.getSelectedSecurityAccountIds(),
										  frm.getPfrLinkSelected());

		return mapping.findForward(FORWARD_LIST);
	}

	//подтверждали или нет вход на страницу (true - не подтверждали)
	private boolean isNotConfirmEnter()  throws BusinessLogicException, BusinessException
	{
		ProductsViewSettingsOperation op = createOperation(ProductsViewSettingsOperation.class);
		if (ConfirmationManager.currentConfirmRequest(op.getConfirmableObject()) == null)
		{
			return  true;
		}

	    ConfirmRequest confirmRequest = ConfirmationManager.currentConfirmRequest(op.getConfirmableObject());

		if (confirmRequest instanceof iPasSmsPasswordConfirmRequest)
			return !((iPasSmsPasswordConfirmRequest)confirmRequest).isConfirmEnter();
		else
			return !((iPasCapConfirmRequest)confirmRequest).isConfirmEnter();
	}


	private void preConfirmAction(HttpServletRequest request, SetupProductsSystemViewOperation operation, String messageKey, ConfirmStrategyType strategyType) throws BusinessException
	{
		try
		{
			ConfirmationManager.sendRequest(operation);
			operation.getRequest().setPreConfirm(true);
			PreConfirmObject preConfirmObject = operation.preConfirm(createCallBackHandler(strategyType, operation.getLogin(), operation.getConfirmableObject()));
			operation.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityLogicException e)
		{
            operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
		}
		catch (BusinessLogicException e)
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
		}
	}

	private void updatePersonInfoData(SetupProductsSystemViewOperation operation, ProductsSetupForm frm)  throws BusinessException
	{
		frm.setAccounts(operation.getClientAccounts());
		frm.setCards(operation.getClientCards());
		frm.setLoans(operation.getClientLoans());
		frm.setDepoAccounts(operation.getClientDepoAccounts());
		frm.setImAccounts(operation.getClientIMAccounts());
		frm.setSecurityAccounts(operation.getClientSecurityAccounts());
		frm.setPfrLink(operation.getClientPfrLink());
		frm.setSNILS(PersonContext.getPersonDataProvider().getPersonData().getPerson().getSNILS());
	}

	private void updateFormData(SetupProductsSystemViewOperation operation, ProductsSetupForm frm)  throws BusinessException
	{
		updatePersonInfoData(operation, frm);
		frm.setConfirmableObject(operation.getConfirmableObject());
		frm.setConfirmStrategy(operation.getConfirmStrategy());
	}

	private void setMessage(HttpServletRequest request)
	{
		ActionMessages message = new ActionMessages();
		message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Обратите внимание, если Вы перейдете на другую страницу системы без " +
																						"подтверждения изменения настроек SMS-паролем, " +
																						"то Ваши изменения не будут сохранены в системе.", false));
		saveMessages(request, message);
	}

	private ActionForward confirmSettings(SetupProductsSystemViewOperation operation, ActionMapping mapping, ProductsSetupForm frm, HttpServletRequest request, HttpServletResponse response)  throws Exception
	{
		try
		{
			List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
			if (!errors.isEmpty() )
			{
				operation.getRequest().setErrorMessage(errors.get(0));
				frm.setConfirmableObject(operation.getConfirmableObject());
				return mapping.findForward(FORWARD_LIST);
			}
			else
			{
				// Сохранение делается при успешной валидации в методе saveConfirm
				operation.confirm();
				operation.sendSmsNotification(false);
				if(isAjax())
					if (operation.getNotUpdatedLinks() == null || operation.getNotUpdatedLinks().isEmpty())
						saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Данные успешно сохранены.", false), null);
					else
						saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, getInfoMessage(operation.getNotUpdatedLinks()), null);
				else
				{
					ActionMessages actionMessages = new ActionMessages();
					if (operation.getNotUpdatedLinks() == null || operation.getNotUpdatedLinks().isEmpty())
						actionMessages.add(ActionMessages.GLOBAL_MESSAGE, (new ActionMessage("Данные успешно сохранены.", false)));
					else
						actionMessages.add(ActionMessages.GLOBAL_MESSAGE, getInfoMessage(operation.getNotUpdatedLinks()));
					saveMessages(request, actionMessages);
				}
				return doNextStage(operation, mapping, frm, request, response);
			}
		}
		catch (BusinessLogicException ble)
		{
			saveError(request, SecurityMessages.translateException(ble));
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_LIST);
		}
		catch (SecurityLogicException e) // ошибка подтверждения
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_LIST);
		}
		catch (SecurityException e) //упал сервис
		{
			operation.getRequest().setErrorMessage("Сервис временно недоступен, попробуйте позже");
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_LIST);
		}
		catch (InactiveExternalSystemException e) // ошибка неактивна внешняя система
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_LIST);
		}
	}

	private List<String> validateConfirmResponse(ConfirmableOperationBase operation, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));

		if ((errors != null) && (!errors.isEmpty()))
		{
			return errors;
		}
		try
		{
			operation.validateConfirm();
		}
		catch (SecurityLogicException e)
		{
			List<String> err =  new ArrayList<String>();
			err.add(e.getMessage());
			return err;
		}
		catch (SecurityException e) //упал сервис
		{
			List<String> err =  new ArrayList<String>();
			err.add("Сервис временно недоступен, попробуйте позже");
			return err;
		}

		return new ArrayList<String>();
	}
}

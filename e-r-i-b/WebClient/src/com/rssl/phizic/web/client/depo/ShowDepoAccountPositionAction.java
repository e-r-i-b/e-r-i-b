package com.rssl.phizic.web.client.depo;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.gate.depo.DepoAccountState;
import com.rssl.phizic.operations.account.EditExternalLinkOperation;
import com.rssl.phizic.operations.depo.GetDepoAccountListOperation;
import com.rssl.phizic.operations.depo.GetDepoAccountPositionOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 31.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowDepoAccountPositionAction  extends OperationalActionBase
{
	private static final Pattern NOT_SPACE_LINE_VALIDATION_PATTERN = Pattern.compile("^.*[^ ].*$");
	private static final String FORWARD_SHOW_DOCUMENT = "ShowDocument";

	protected static final String BACK_ERROR_DEPO_ACCOUNT_MESSAGE  = "Операция временно недоступна. Повторите попытку позже.";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMap = new HashMap<String, String>();
	    keyMap.put("button.saveDepoAccountName", "saveDepoAccountName");
		keyMap.put("button.update", "updateDepoList");
        return keyMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return updateFormData(mapping, form, request, false);
	}

	public ActionForward updateDepoList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    return updateFormData(mapping, form, request, true);
	}

	private ActionForward updateFormData(ActionMapping mapping, ActionForm form, HttpServletRequest request, Boolean refresh) throws Exception
	{
		ShowDepoAccountPositionForm frm = (ShowDepoAccountPositionForm) form;
	    Long linkId = frm.getId();

	    GetDepoAccountPositionOperation operation = createOperation(GetDepoAccountPositionOperation.class);
	    operation.initialize(linkId);

	    DepoAccountLink link = operation.getDepoAccountLink();
		if (link.getDepoAccount().getState() == DepoAccountState.closed)
		{
			ActionRedirect redirect = new ActionRedirect(mapping.findForward(FORWARD_SHOW_DOCUMENT));
			redirect.addParameter("id", linkId);
			return redirect;
		}
		if(refresh)
			link.refresh();

		frm.setDepoAccountLink(link);
		frm.setField("depoAccountName",link.getName());
		setAnotherDepoAccounts(frm, link);
		ActionMessages msgs = new ActionMessages();

		try
		{
			frm.setDepoAccountPosition(operation.getDepoAccountPositionInfo());
			String message = getResourceMessage("depoBundle", "com.rssl.phizic.web.client.depo.position.info");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
		}
		catch (BusinessLogicException e)
		{
			log.error("Ошибка при получении информации по позиции для счета депо №" + link.getAccountNumber(), e);
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}

		if (operation.isBackError())
			saveMessage(currentRequest(), BACK_ERROR_DEPO_ACCOUNT_MESSAGE);
		else
			saveMessages(request, msgs);

		if (operation.isUseStoredResource())
		{
			saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) operation.getDepoAccountLink().getDepoAccount()));
		}
		return mapping.findForward(FORWARD_SHOW);
	}

	private ActionMessages validateAdditionalData(ShowDepoAccountPositionForm frm)
	{
		String depoAccountNameValue = (String) frm.getField("depoAccountName");
		if (StringHelper.isEmpty(depoAccountNameValue))
			return null;

		if (NOT_SPACE_LINE_VALIDATION_PATTERN.matcher(depoAccountNameValue).matches())
			return null;

		ActionMessages actionMessages = new ActionMessages();
		actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("depoAccountName", new ActionMessage("Введите значение в поле Название.", false)));
		return actionMessages;
	}

	public ActionForward saveDepoAccountName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowDepoAccountPositionForm frm = (ShowDepoAccountPositionForm) form;
		Long linkId = frm.getId();
		EditExternalLinkOperation operation = createOperation(EditExternalLinkOperation.class);
		operation.initialize(linkId, DepoAccountLink.class);

		Map<String, Object> fields = frm.getFields();
		FieldValuesSource valuesSource = new MapValuesSource(fields);

		Form editForm = ShowDepoAccountPositionForm.NAME_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editForm);

		ActionMessages additionalErrorMessages = validateAdditionalData(frm);
		if(processor.process() && additionalErrorMessages == null)
		{
			operation.saveLinkName(StringHelper.getNullIfEmpty((String) processor.getResult().get("depoAccountName")));
		}
		else
		{
			ActionMessages errors = processor.getErrors();
			errors.add(additionalErrorMessages);
			saveErrors(request, errors);
		}
		return start(mapping, form, request, response);
	}

	private void setAnotherDepoAccounts(ShowDepoAccountPositionForm frm, DepoAccountLink link) throws BusinessException, BusinessLogicException
	{
		GetDepoAccountListOperation listOperation = createOperation(GetDepoAccountListOperation.class);
	    List<DepoAccountLink> anotherDepoAccounts = listOperation.getDepoAccounts();
	    anotherDepoAccounts.remove(link);
	    frm.setAnotherDepoAccounts(anotherDepoAccounts);
	}
}

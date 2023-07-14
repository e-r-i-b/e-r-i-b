package com.rssl.phizic.web.common.client.ima;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.account.EditExternalLinkOperation;
import com.rssl.phizic.operations.ima.GetIMAccountAbstractOperation;
import com.rssl.phizic.operations.ima.GetIMAccountOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 * @see IMAccountGeneralAction
 */
public class IMAccountInfoAction extends IMAccountGeneralAction
{
    protected Map<String, String> getKeyMethodMap()
    {
	    Map<String, String> keyMap = super.getKeyMethodMap();
	    keyMap.put("button.save",           "saveIMAccountName");
		keyMap.put("button.filter",         "filter");
        return keyMap;
    }

	protected void updateFormData(ViewEntityOperation operation, EditFormBase formBase) throws BusinessException, BusinessLogicException
	{
		super.updateFormData(operation,  formBase);

		IMAccountInfoForm form = (IMAccountInfoForm) formBase;
		GetIMAccountAbstractOperation getIMAccountAbstractOperation = (GetIMAccountAbstractOperation)operation;

        Map<IMAccountLink, IMAccountAbstract> info = getAbstract(formBase, getIMAccountAbstractOperation, null);
		IMAccountLink imAccountlink = getIMAccountAbstractOperation.getEntity();
		form.setImAccountLink(imAccountlink);
		form.setImAccountAbstract(info.get(imAccountlink));
		form.setAbstractMsgError(getIMAccountAbstractOperation.getAccountAbstractMsgErrorMap().get(imAccountlink));
		if (form.getField("imAccountName") == null)
			form.setField("imAccountName",imAccountlink.getName());
		setAdditionalLinks(form, imAccountlink);

		if (getIMAccountAbstractOperation.isUseStoredResource())
		{
			form.setAbstractMsgError(StoredResourceMessages.getUnreachableStatement());
			saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) imAccountlink.getImAccount()));
		}
		if (getIMAccountAbstractOperation.isBackError())
		{
			saveMessage(currentRequest(), BACK_ERROR_IMACCOUNT_MESSAGE);
		}
	}

	/**
	 * Cохранение названия счёта пользователя
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveIMAccountName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		IMAccountInfoForm frm = (IMAccountInfoForm) form;
		EditExternalLinkOperation operation = createOperation(EditExternalLinkOperation.class);
		operation.initialize(frm.getId(), IMAccountLink.class);

        FieldValuesSource source = getSaveImaNameFieldValuesSource(frm);
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(source, IMAccountInfoForm.IMALINK_FORM);

		if(processor.process())
		{
            Map<String, Object> result = processor.getResult();
			operation.saveLinkName((String) result.get("imAccountName"));
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return forwardSaveImaName(mapping, form, request, response);
	}

    protected MapValuesSource getSaveImaNameFieldValuesSource(IMAccountInfoForm form)
    {
        return new MapValuesSource(form.getFields());
    }

    protected ActionForward forwardSaveImaName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return start(mapping, form, request, response);
    }

	/**
	 * Фильтр. Предназначен для указания периода за который необходимо формировать выписку.
	 * @throws Exception
	 */
	public ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		IMAccountInfoForm frm = (IMAccountInfoForm) form;
		
		GetIMAccountAbstractOperation operation = (GetIMAccountAbstractOperation) createViewEntityOperation((EditFormBase) form);

		IMAccountLink link = operation.getEntity();

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFilters()), IMAccountInfoForm.FILTER_FORM);
		Map<String, Object> source = null;
		if (processor.process())
		{
			source = processor.getResult();
		}
		else if(operation.isFullAbstractCreated())
		{
			saveMessages(request, processor.getErrors());
		}
		IMAccountAbstract imAbstract = getAbstract(frm, operation, source).get(link);

		frm.setImAccountLink(link);
		frm.setImAccountAbstract(imAbstract);
		setAdditionalLinks(frm, link);

		if (operation.isUseStoredResource())
		{
			saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) link.getImAccount()));
		}
		
        return mapping.findForward(ViewActionBase.FORWARD_START);
	}
	
	protected IMAbstractFilter getFilter(Map<String, Object> source)
	{
		return new IMAbstractFilter( source );
	}

	private void setAdditionalLinks(IMAccountInfoForm form, IMAccountLink activeLink) throws BusinessException, BusinessLogicException
	{
		GetIMAccountOperation accountOperation = createOperation("GetIMAccountOperation");
		List<IMAccountLink> additinal = accountOperation.getIMAccounts();
		additinal.remove(activeLink);
		form.setAdditionalIMAccountLink(additinal);
	}
}

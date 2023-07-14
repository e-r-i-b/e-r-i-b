package com.rssl.phizic.web.payments;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.business.claims.RefusingReasonDictionary;
import com.rssl.phizic.operations.payment.ProcessDocumentOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hudyakov
 * @ created 09.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class DocumentRefuseAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_LIST = "List";

	protected Map getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.refuse", "refuse");
        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws Exception
    {
	    DocumentRefuseForm frm = (DocumentRefuseForm) form;

	    ProcessDocumentOperation operation = initOperation(frm.getId());

	    if (operation.getEntity() != null)
	    {
			RefusingReasonDictionary refusingReasonDictionary = new RefusingReasonDictionary();
			List<String> refusingReasonsList = refusingReasonDictionary.getRefusingReasons();
			String[] refusingReasons = new String[refusingReasonsList.size()];
			refusingReasonsList.toArray(refusingReasons);
	
			frm.setField("refuseReason", refusingReasons);

		    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущнось", operation.getEntity()));
	    }

	    return mapping.findForward(FORWARD_START);
    }

	public ActionForward refuse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws Exception
	{
		DocumentRefuseForm frm = (DocumentRefuseForm) form;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), DocumentRefuseForm.REFUSE_FORM);
	    ProcessDocumentOperation operation = initOperation(frm.getId());
		ActionForward actionForward;

		if(processor.process())
		{
			//TODO убрать все в форму валидации!!!
			String refuseReason = (String) frm.getField("selectedRefuseReason");
			if(refuseReason.equals("Другое"))
				refuseReason = (String) frm.getField("otherReason");
			if(refuseReason.equals(""))
			{
				saveError(ActionMessages.GLOBAL_MESSAGE, "com.rssl.phizic.web.claims.noReason", request);
				return start(mapping, form, request, response);
			}
			else
			{
                operation.refuse(refuseReason);
			    actionForward = mapping.findForward(FORWARD_LIST);
			}
		}
		else
		{
		    saveErrors(request, processor.getErrors());
			actionForward = start(mapping, form, request, response);
		}
		
		return actionForward;
	}

	protected ProcessDocumentOperation initOperation(Long id) throws BusinessException, BusinessLogicException
	{
		ExistingSource source = new ExistingSource(id, new NullDocumentValidator());
		ProcessDocumentOperation operation = createOperation(ProcessDocumentOperation.class);
		operation.initialize(source);

		return operation;
	}
}

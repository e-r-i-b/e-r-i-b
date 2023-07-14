package com.rssl.phizic.web.client.insurance;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.insurance.InsuranceLink;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.account.EditExternalLinkOperation;
import com.rssl.phizic.operations.insurance.GetInsuranceDetailOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 21.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ShowInsuranceDetailAction extends ViewActionBase
{
	private static final String INSURANCE_NAME_FIELD = "insuranceName";

	protected Map<String, String> getKeyMethodMap()
    {
	    Map<String, String> keyMap = new HashMap<String, String>();
	    keyMap.put("button.saveInsuranceName", "saveInsuranceName");
        return keyMap;
    }

	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		GetInsuranceDetailOperation insuranceDetailOperation = createOperation(GetInsuranceDetailOperation.class);
		insuranceDetailOperation.initialize(frm.getId());
    	return insuranceDetailOperation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException, BusinessLogicException
	{
		ShowInsuranceDetailForm frm = (ShowInsuranceDetailForm) form;
		GetInsuranceDetailOperation detailOperation = (GetInsuranceDetailOperation) operation;
		InsuranceLink link = detailOperation.getEntity();
		frm.setLink(link);
		frm.setField(INSURANCE_NAME_FIELD, link.getName());
		frm.setInsuranceApp(detailOperation.getInsuranceApp());
	}


	public ActionForward saveInsuranceName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowInsuranceDetailForm frm = (ShowInsuranceDetailForm) form;
		Long linkId = frm.getId();
		EditExternalLinkOperation operation = createOperation(EditExternalLinkOperation.class);
		operation.initialize(linkId, InsuranceLink.class);

		Map<String, Object> fields = frm.getFields();
		FieldValuesSource valuesSource = new MapValuesSource(fields);

		Form editForm = ShowInsuranceDetailForm.EDIT_INSURANCE_NAME_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editForm);

		if(processor.process())
		{
			operation.saveLinkName((String)frm.getField(INSURANCE_NAME_FIELD));
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return start(mapping, form, request, response);
	}
}

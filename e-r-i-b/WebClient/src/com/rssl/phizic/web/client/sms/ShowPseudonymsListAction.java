package com.rssl.phizic.web.client.sms;

import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.operations.pseudonyms.EditPseudonymsListOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.business.smsbanking.pseudonyms.Pseudonym;
import com.rssl.phizic.business.smsbanking.pseudonyms.PseudonymService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

import java.util.*;

/**
 * @author eMakarov
 * @ created 20.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class ShowPseudonymsListAction extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
	    map.put("button.save",   "save");
		map.put("button.cancel", "start");
	    return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditPseudonymsListOperation operation = createOperation(EditPseudonymsListOperation.class, "SmsBanking");
		operation.initialize();

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		updateFormAdditionalData(frm, operation);
	}
	
	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		ShowPseudonymsListForm      frm = (ShowPseudonymsListForm) form;
        EditPseudonymsListOperation op  = (EditPseudonymsListOperation) operation;

		Map<String, Object> fields = new HashMap<String, Object>();

		PseudonymService pseudonymService = new PseudonymService();
		pseudonymService.synchronize(op.getLogin());
		
		List<Pseudonym> pseudonyms = op.getLinks();
	    frm.setPseudonyms(pseudonyms);
		for (Pseudonym pseudonym : pseudonyms)
		{
			fields.put(pseudonym.getId().toString(), pseudonym.getName());
		}
		frm.setFields(fields);
	}
}

package com.rssl.phizic.web.client.sms;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rssl.phizic.operations.pseudonyms.EditPseudonymsListOperation;
import com.rssl.phizic.business.smsbanking.pseudonyms.Pseudonym;
import com.rssl.phizic.business.smsbanking.pseudonyms.PseudonymService;
import com.rssl.phizic.web.log.CollectionLogParametersReader;

import java.util.*;

/**
 * @author eMakarov
 * @ created 22.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditPseudonymsListAction extends ShowPseudonymsListAction//TODO operationQuery??
{
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowPseudonymsListForm frm = (ShowPseudonymsListForm) form;
		EditPseudonymsListOperation operation = createOperation(EditPseudonymsListOperation.class, "SmsBanking");
		operation.initialize();

		addLogParameters(new CollectionLogParametersReader("Старое значение сущности", operation.getLinks()));

		Set<Long> checked = new TreeSet<Long>();
		for (String elem : frm.getSelectedIds())
		{
			checked.add(Long.parseLong(elem));
		}

		List<Pseudonym> pseudonyms = new ArrayList<Pseudonym>(operation.getLinks());
		Map<String, Object> fields = frm.getFields();
		for (Pseudonym pseudonym : pseudonyms)
		{
			pseudonym.setHasAccess(false);
			if (checked.contains(pseudonym.getId()))
			{
				pseudonym.setHasAccess(true);
			}
			pseudonym.setName((String) fields.get(pseudonym.getId().toString()));
		}

		addLogParameters(new CollectionLogParametersReader("Новое значение сущности", pseudonyms));

		PseudonymService pseudonymService = new PseudonymService();
		if (!pseudonymService.checkListOnUniq(pseudonyms))
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("В списке имен псевдонимов есть дубликаты", false));
			saveErrors(request, errors);
		}
		else
		{
			operation.saveChanges(pseudonyms);
		}
		frm.setPseudonyms(pseudonyms);

		return mapping.findForward(FORWARD_START);
	}
}

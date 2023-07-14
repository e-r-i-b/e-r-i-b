package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.dictionaries.contact.SetContactDictionariesSettingsOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kosyakova
 * @ created 24.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class DictionariesConfigureAction
		extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_SUCCESS = "Success";

	protected Map getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DictionariesConfigureForm frm = (DictionariesConfigureForm) form;
		SetContactDictionariesSettingsOperation operation = createOperation(SetContactDictionariesSettingsOperation.class);

		operation.read();
		updateForm(frm, operation);

		addLogParameters(new SimpleLogParametersReader("Просматриваемая сущность", operation.getContactDictionariesPath()));

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		DictionariesConfigureForm frm = (DictionariesConfigureForm) form;

		SetContactDictionariesSettingsOperation operation = createOperation(SetContactDictionariesSettingsOperation.class);

		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());
		Form editForm = DictionariesConfigureForm.FORM;
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(mapValuesSource, editForm);
		ActionForward actionForward;

		if (formProcessor.process())
		{
			upadateSettings(frm, operation);

			addLogParameters(new CompositeLogParametersReader(
					new SimpleLogParametersReader("Первоначальное значение", operation.getContactDictionariesPath()),
					new SimpleLogParametersReader("Текущее значение", frm.getField("contactDictionariesPath"))
				));

			operation.save();

			actionForward = mapping.findForward(FORWARD_SUCCESS);
		}
		else
		{
			saveErrors(request, formProcessor.getErrors());
			actionForward = mapping.findForward(FORWARD_START);
		}

		return actionForward;
	}

	private void updateForm(DictionariesConfigureForm frm, SetContactDictionariesSettingsOperation operation)
	{
		frm.setField("contactDictionariesPath", operation.getContactDictionariesPath());
	}

	private void upadateSettings(DictionariesConfigureForm frm, SetContactDictionariesSettingsOperation operation)
	{
		operation.setContactDictionariesPath((String) frm.getField(("contactDictionariesPath")));
	}
}

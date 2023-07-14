package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.dictionaries.SetDictionariesConfigsOperation;
import static com.rssl.phizic.utils.NumericUtil.toLong;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizicgate.manager.routing.Adapter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 18.05.2009
 * @ $Author$
 * @ $Revision$
 */

public class DictionariesExternalSystemConfigAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_SUCCESS = "Success";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		map.put("button.cancel", "start");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DictionariesExternalSystemConfigForm frm = (DictionariesExternalSystemConfigForm) form;

		SetDictionariesConfigsOperation operation = createOperation(SetDictionariesConfigsOperation.class);
		operation.initialize();

		updateForm(frm, operation);
		return mapping.findForward(FORWARD_START);
	}

	private void updateForm(DictionariesExternalSystemConfigForm frm, SetDictionariesConfigsOperation operation) throws GateException
	{
		Adapter adapter = operation.getDictionatyReplicationAdapter();
		if (adapter != null)
		{
			frm.setField("externalSystem", adapter.getName());
			frm.setField("externalSystemId", adapter.getId());
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DictionariesExternalSystemConfigForm frm = (DictionariesExternalSystemConfigForm) form;

		SetDictionariesConfigsOperation operation = createOperation(SetDictionariesConfigsOperation.class);
		operation.initialize();

		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());
		Form editForm = DictionariesExternalSystemConfigForm.FORM;
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(mapValuesSource, editForm);
		ActionForward actionForward;

		if (formProcessor.process())
		{
			updateSettings(formProcessor.getResult(), operation);

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

	private void updateSettings(Map<String, Object> data, SetDictionariesConfigsOperation operation) throws BusinessException, BusinessLogicException
	{
		BigInteger adapterId = (BigInteger) data.get("externalSystemId");
		operation.setDictionariesReplicationAdapter(toLong(adapterId));
	}
}

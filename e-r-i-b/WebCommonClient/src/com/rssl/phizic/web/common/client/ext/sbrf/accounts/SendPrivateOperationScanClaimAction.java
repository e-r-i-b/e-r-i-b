package com.rssl.phizic.web.common.client.ext.sbrf.accounts;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.operations.claims.operation.scan.claim.CreatePrivateScanClaimOperation;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Отправка заявки на получение электронного документа на электронную почту
 * @author komarov
 * @ created 15.04.2014
 * @ $Author$
 * @ $Revision$
 */
public class SendPrivateOperationScanClaimAction extends AsyncOperationalActionBase
{
	private static final String FORWARD_ERROR = "Error";
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = super.getKeyMethodMap();
		keyMethodMap.put("button.send", "send");
		return keyMethodMap;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return null;
	}

	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SendPrivateOperationScanClaimForm frm = (SendPrivateOperationScanClaimForm) form;
		CreatePrivateScanClaimOperation operation = createOperation(CreatePrivateScanClaimOperation.class);

		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());

		Form newCategoryForm = SendPrivateOperationScanClaimForm.OPERATION_SCAN_CLAIM_FORM;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, newCategoryForm);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			operation.initialize(result);
			operation.save();
			return mapping.findForward(FORWARD_START);
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return mapping.findForward(FORWARD_ERROR);
	}

	@Override
	protected boolean isAjax()
	{
		return true;
	}
}

package com.rssl.phizic.web.common.mobile.stash;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.stash.EditStashOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * "избранное" в мобильном приложении
 * @author Dorzhinov
 * @ created 06.09.13
 * @ $Author$
 * @ $Revision$
 */
public class EditStashAction extends OperationalActionBase
{
	private static final String FORWARD_SAVE = "Save";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("save", "save");
		return map;
	}

	protected EditStashOperation createEditOperation() throws BusinessException, BusinessLogicException
	{
		EditStashOperation operation = createOperation(EditStashOperation.class);
		operation.init();
		return operation;
	}

	//просмотр
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessLogicException, BusinessException
	{
		EditStashForm frm = (EditStashForm) form;
		try
		{
			EditStashOperation operation = createEditOperation();
			frm.setStash(operation.getStash());
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}
		return mapping.findForward(FORWARD_SHOW);
	}

	//сохранение
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessLogicException, BusinessException
	{
		EditStashForm frm = (EditStashForm) form;

		FormProcessor<ActionMessages, ?> formProcessor = FormHelper.newInstance(getValuesSource(frm), EditStashForm.FORM);
		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
		}
		else
		{
			try
			{
				EditStashOperation operation = createEditOperation();
				Map<String, Object> data = formProcessor.getResult();
				operation.setStash((String) data.get(EditStashForm.STASH));
			}
			catch (BusinessLogicException e)
			{
				saveError(request, e);
			}
		}
		return mapping.findForward(FORWARD_SAVE);
	}

	protected FieldValuesSource getValuesSource(EditStashForm form)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(EditStashForm.STASH, form.getStash());
		return new MapValuesSource(map);
	}
}

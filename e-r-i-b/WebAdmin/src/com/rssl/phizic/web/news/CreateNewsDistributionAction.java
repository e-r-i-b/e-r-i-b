package com.rssl.phizic.web.news;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.news.EditNewsDistributionsOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rssl.phizic.web.news.CreateNewsDistributionForm.MAIL_COUNT_FIELD;
import static com.rssl.phizic.web.news.CreateNewsDistributionForm.TIMEOUT_FIELD;

/**
 * @author gladishev
 * @ created 01.11.13
 * @ $Author$
 * @ $Revision$
 */
public class CreateNewsDistributionAction extends OperationalActionBase
{
	private static final String MAIN_PAGE_FORWARD_START = "MainPageNews";
	private static final String LOGIN_PAGE_FORWARD_START = "LoginPageNews";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateNewsDistributionForm frm = (CreateNewsDistributionForm) form;

	    FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(new MapValuesSource(frm.getFields()), CreateNewsDistributionForm.EDIT_FORM);

		if (formProcessor.process())
		{
			try
			{
				Map<String,Object> result = formProcessor.getResult();
				EditNewsDistributionsOperation operation = createOperation(EditNewsDistributionsOperation.class, "NewsDistributionsManagement");
				operation.initialize(frm.getId(), frm.isMainNews(), (Integer) result.get(MAIL_COUNT_FIELD), (Integer) result.get(TIMEOUT_FIELD));
				operation.save();
			}
			catch (BusinessLogicException e)
			{
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
				saveSessionErrors(request, msgs);
			}
		}
		else
			saveSessionErrors(request, formProcessor.getErrors());

		return mapping.findForward(frm.isMainNews() ? MAIN_PAGE_FORWARD_START : LOGIN_PAGE_FORWARD_START);
	}
}

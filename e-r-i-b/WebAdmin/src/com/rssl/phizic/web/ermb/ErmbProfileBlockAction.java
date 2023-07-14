package com.rssl.phizic.web.ermb;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.StringErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.ermb.person.ErmbProfileBlockOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 13.06.2013
 * Time: 15:39:02
 * Блокировка профиля ЕРМБ
 */
public class ErmbProfileBlockAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ErmbProfileBlockForm frm = (ErmbProfileBlockForm) form;
		ErmbProfileBlockOperation operation = createOperation(ErmbProfileBlockOperation.class);
		operation.initialize(frm.getId());
		
		FormProcessor<List<String>, ?> processor = new FormProcessor<List<String>, StringErrorCollector>(new MapValuesSource(frm.getFields()), ErmbProfileBlockForm.EDIT_FORM, new StringErrorCollector(), DefaultValidationStrategy.getInstance());;
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			String lockDescription = (String) result.get("lockDescription");
			Long profileVersion = operation.udateProfileStatus(lockDescription);
			response.getWriter().print(profileVersion);
		}
		else
		{
			//тут бывает только одна ошибка
			String error = processor.getErrors().get(0);
			try
			{
				response.getWriter().println(error);
			}
			catch (IOException e)
			{
				throw new BusinessException(e);
			}
		}
		return null;
	}

	protected boolean isAjax()
	{
		return true;
	}

	protected boolean getEmptyErrorPage()
	{
		return true;
	}
	
}

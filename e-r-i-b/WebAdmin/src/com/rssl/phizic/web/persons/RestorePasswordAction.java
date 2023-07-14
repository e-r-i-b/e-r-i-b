package com.rssl.phizic.web.persons;

import com.rssl.auth.csa.wsclient.exceptions.CheckIMSIException;
import com.rssl.auth.csa.wsclient.exceptions.FailureIdentificationException;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.ext.sbrf.person.RestoreClientPasswordOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rssl.phizic.operations.ext.sbrf.person.RestoreClientPasswordOperation.RestoreType;

/**
 * @author vagin
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 * Экшен формы восстановления пароля из анкеты клиента
 */
public class RestorePasswordAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String,String>();
		map.put("button.password.change", "checkIMSI");
		map.put("button.ignoreIMSICheck", "restorePassword");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RestorePasswordForm frm = (RestorePasswordForm) form;
		RestoreClientPasswordOperation operation = createOperation(frm);

		updateForm(form,operation);

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward checkIMSI(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RestorePasswordForm frm = (RestorePasswordForm) form;
		RestoreClientPasswordOperation operation = createOperation(frm);

		FormProcessor<ActionMessages, ?> processor = FormHelper.newInstance(new MapValuesSource(frm.getFields()), RestorePasswordForm.FORM);
		if (!processor.process())
		{
			updateForm(form, operation);
			saveErrors(request, processor.getErrors());

			return mapping.findForward(FORWARD_START);
		}

		if (operation.hasERMBProfile())
		{
			frm.setField("failureIMSICheck", "true");
			return restorePassword(mapping, form, request, response);
		}

		try
		{
			switch (RestoreType.fromValue((String) frm.getField("restoreType")))
			{
				case BY_CARD:
				{
					operation.checkIMSIByCardNumber((String) frm.getField("cardId"));
					break;
				}
				case BY_LOGIN:
				{
					operation.checkIMSIByProfile();
					break;
				}
			}
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			updateForm(form, operation);
			return mapping.findForward(FORWARD_START);
		}
		catch (CheckIMSIException e)
		{
			log.warn("Ошибка проверки IMSI", e);
			frm.setFailureIMSICheck(true);
			updateForm(form, operation);
			return mapping.findForward(FORWARD_START);
		}

		addLogParameters(new SimpleLogParametersReader(operation.getPerson()));

		return restorePassword(mapping, form, request, response);
	}

	/**
	 * Восстановить пароль
	 * @throws Exception
	 */
	public ActionForward restorePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RestorePasswordForm frm = (RestorePasswordForm) form;
		RestoreClientPasswordOperation operation = createOperation(frm);

		try
		{
			switch (RestoreType.fromValue((String)frm.getField("restoreType")))
			{
				case BY_CARD:
				{
					operation.restorePasswordByCardNumber((String) frm.getField("cardId"), Boolean.parseBoolean((String)frm.getField("failureIMSICheck")));
					break;
				}
				case BY_LOGIN:
				{
					operation.restorePasswordByProfile(Boolean.parseBoolean((String) frm.getField("failureIMSICheck")));
					break;
				}
			}
		}
		catch(BusinessLogicException e)
		{
			saveError(request,e);
			updateForm(form,operation);
			return mapping.findForward(FORWARD_START);
		}
		catch(FailureIdentificationException e)
		{
			log.warn("Ошибка идентификации пользователя", e);
			frm.setFailureIdentification(true);
			updateForm(form,operation);
			return mapping.findForward(FORWARD_START);
		}

		addLogParameters(new SimpleLogParametersReader(operation.getPerson()));

		updateForm(form,operation);
		saveMessage(request, "Новый пароль будет отправлен клиенту по SMS");

		return mapping.findForward(FORWARD_START);
	}

	private RestoreClientPasswordOperation createOperation(RestorePasswordForm form) throws BusinessException, BusinessLogicException
	{
		RestoreClientPasswordOperation operation = createOperation("RestoreClientPasswordOperation");
		operation.initialize(form.getId());
		return operation;
	}

	private void updateForm(ActionForm form, RestoreClientPasswordOperation operation) throws BusinessException, BusinessLogicException
	{
		RestorePasswordForm frm = (RestorePasswordForm) form;
		String restoreType = (String) frm.getField("restoreType");
		frm.setField("restoreType", StringHelper.isEmpty(restoreType) ? "card" : restoreType);
		frm.setCardLinks(operation.getMobileCardLinks());
		frm.setActivePerson(operation.getPerson());
	}
}


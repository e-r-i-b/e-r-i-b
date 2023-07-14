package com.rssl.phizic.web.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.ermb.person.ErmbControlPhoneOpertion;
import com.rssl.phizic.operations.ermb.person.ErmbResetIMSIOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 18.06.2013
 * Time: 16:34:03
 *  Управление телефонами ЕРМБ
 */
public class ErmbControlPhoneAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map =  new HashMap<String, String>();
		map.put("sendConfirmCode","sendConfirmCode");
		map.put("testSwapPhoneNumberCode","testSwapPhoneNumberCode");
		map.put("resetIMSI","resetIMSI");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ErmbControlPhoneForm frm = (ErmbControlPhoneForm) form;
		ErmbControlPhoneOpertion operation = createOperation(ErmbControlPhoneOpertion.class);
		writeToResponse(response, operation.isPhoneNumberAvailable(frm.getPhoneNumber(), frm.getPhonesToDelete()));
		return null;
	}

	public ActionForward sendConfirmCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ErmbControlPhoneForm frm = (ErmbControlPhoneForm) form;
		ErmbControlPhoneOpertion operation = createOperation(ErmbControlPhoneOpertion.class);
		writeToResponse(response,String.valueOf(operation.sendConfirmCode(frm.getPhoneNumber())));
		return null;
	}

	public ActionForward testSwapPhoneNumberCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ErmbControlPhoneForm frm = (ErmbControlPhoneForm) form;
		ErmbControlPhoneOpertion operation = createOperation(ErmbControlPhoneOpertion.class);
		writeToResponse(response,String.valueOf(operation.testSwapPhoneNumberCode(frm.getConfirmCode(),frm.getPhoneNumber())));
		return null;
	}

	public ActionForward resetIMSI(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ErmbControlPhoneForm frm = (ErmbControlPhoneForm) form;
		ErmbResetIMSIOperation operation = createOperation(ErmbResetIMSIOperation.class);
		writeToResponse(response,String.valueOf(operation.resetIMSI(Arrays.asList(frm.getPhoneCode()),frm.getProfileId())));
		return null;
	}

	private void writeToResponse(HttpServletResponse response,String text) throws BusinessException
	{
		ServletOutputStream ostream = null;
		try
		{
			ostream = response.getOutputStream();
			ostream.print(text);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
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

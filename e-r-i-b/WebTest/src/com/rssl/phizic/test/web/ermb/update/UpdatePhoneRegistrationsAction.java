package com.rssl.phizic.test.web.ermb.update;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */
public class UpdatePhoneRegistrationsAction extends LookupDispatchAction
{
	private static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("send", "send");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UpdatePhoneRegistrationsForm frm = (UpdatePhoneRegistrationsForm) form;

		UserInfo userInfo = getUserInfo(frm);

		if (userInfo != null)
		{
			try
			{
				if (frm.isDeleteDuplicates())
				{
					CSABackRequestHelper.sendUpdatePhoneRegRemoveDuplicateRq(frm.getPhoneNumber(), userInfo, getPhones(frm.getAddPhones()), getPhones(frm.getRemovePhones()));
				}
				else
				{
					CSABackRequestHelper.sendUpdatePhoneRegistrationsRq(frm.getPhoneNumber(), userInfo, getPhones(frm.getAddPhones()), getPhones(frm.getRemovePhones()));
				}
				frm.setErrors("Выполненно успешно");
			}
			catch (BackLogicException e)
			{
				frm.setErrors(e.getMessage());
			}
			catch (BackException ignore)
			{
				frm.setErrors("ошибки обработки сообщения в ЦСА БЭК");
			}
		}

		return mapping.findForward(FORWARD_START);
	}

	private List<String> getPhones(String stringPhones)
	{
		if (StringHelper.isEmpty(stringPhones))
		{
			return Collections.emptyList();
		}
		String[] phones = stringPhones.split(",");
		return Arrays.asList(phones);
	}

	private UserInfo getUserInfo(UpdatePhoneRegistrationsForm form)
	{
		try
		{
			Calendar birthDate = DateHelper.toCalendar(DateHelper.parseStringTime(form.getBirthDate()));
			String cbCode = StringHelper.addLeadingZeros("38".equals(form.getCbCode()) ? "99" : form.getCbCode(), 2);
			return new UserInfo(cbCode, form.getFirstName(), form.getSurName(), form.getPatrName(), form.getPassport(), birthDate);
		}
		catch (ParseException e)
		{
			log.error("ошибка при получении даты по строке: " + form.getBirthDate(), e);
		}
		return null;
	}
}

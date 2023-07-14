package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.connectUdbo.UDBOClaimRules;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.registration.EditUDBOClaimRulesOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Редактирование условий заявления для подключения УДБО
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class EditUDBOClaimRulesAction extends EditActionBase
{
	private static final String RESOURCE_BUNDLE = "configureBundle";
	private static final String HTML_STARTS_WITH_TEXT = "<html";
	private static final String HTML_ENDS_WITH_TEXT = "</html>";
	private static final String RESOURCE_KEY_PREFIX = "settings.connect.udbo.edit.rules.";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = super.getKeyMethodMap();
		map.put("ajax.deleteRules", "deleteRules");
		return map;
	}

	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditUDBOClaimRulesOperation operation = createOperation(EditUDBOClaimRulesOperation.class, "ConnectUDBOSettingsService");
		EditUDBOClaimRulesForm form = (EditUDBOClaimRulesForm) frm;
		operation.initialize(form.getId());
		return operation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return EditUDBOClaimRulesForm.EDIT_FORM;
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		UDBOClaimRules claimRules = (UDBOClaimRules)entity;
		claimRules.setStartDate(DateHelper.toCalendar((Date) data.get("startDate")));
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		UDBOClaimRules claimRules = (UDBOClaimRules)entity;
		EditUDBOClaimRulesForm form = (EditUDBOClaimRulesForm) frm;
		form.setField("startDate", DateHelper.toDate(claimRules.getStartDate()));
		form.setRulesText(claimRules.getRulesText());
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation operation, EditFormBase frm, Map<String, Object> data) throws Exception
	{
		EditUDBOClaimRulesForm form = (EditUDBOClaimRulesForm) frm;
		UDBOClaimRules claimRules = (UDBOClaimRules)operation.getEntity();
		String rulesText = convertToString(form.getRulesFile());
		if (StringHelper.isNotEmpty(rulesText))
			claimRules.setRulesText(rulesText);
	}

	private String convertToString(FormFile file) throws IOException
	{
		if (file == null)
			return null;
		InputStream streamIn = null;
		try
		{
			int fileSize = file.getFileSize();
			streamIn = file.getInputStream();
			byte[] b = new byte[fileSize];
			assert streamIn != null;
			streamIn.read(b);
			return new String(b);
		}
		catch (FileNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			assert streamIn != null;
			streamIn.close();
			file.destroy();
		}
	}

	/**
	 * Асинхронное удаление условий заявления для подключения УДБО
	 * @param mapping - маппинг
	 * @param form = форма
	 * @param request - реквест
	 * @param response - респонс
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward deleteRules(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditUDBOClaimRulesForm frm = (EditUDBOClaimRulesForm)form;
		EditUDBOClaimRulesOperation operation = (EditUDBOClaimRulesOperation)createEditOperation(frm);
		operation.remove();
		return getEmptyPage(response);
	}

	private ActionForward getEmptyPage(HttpServletResponse response) throws Exception
	{
		response.getOutputStream().println(" ");
		return null;
	}

	@Override
	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		EditUDBOClaimRulesOperation op = (EditUDBOClaimRulesOperation)operation;
		EditUDBOClaimRulesForm form = (EditUDBOClaimRulesForm)frm;
		String startDate = (String)frm.getField(EditUDBOClaimRulesForm.START_DATE_FIELD_NAME);
		String rulesText = convertToString(form.getRulesFile());

		if (StringHelper.isNotEmpty(startDate) && !op.checkStartDate(DateHelper.parseCalendar(startDate)))
			msgs.add(ActionMessages.GLOBAL_MESSAGE,	new ActionMessage(getResourceMessage(RESOURCE_BUNDLE, RESOURCE_KEY_PREFIX + "startDate.message.equalsPrevious"), false));
		if (StringHelper.isEmpty(form.getRulesText()) && StringHelper.isEmpty(rulesText))
			msgs.add(ActionMessages.GLOBAL_MESSAGE,	new ActionMessage(getResourceMessage(RESOURCE_BUNDLE, RESOURCE_KEY_PREFIX + "text.message.empty"), false));
		if (!rulesText.contains(HTML_STARTS_WITH_TEXT) || !rulesText.endsWith(HTML_ENDS_WITH_TEXT))
			msgs.add(ActionMessages.GLOBAL_MESSAGE,	new ActionMessage(getResourceMessage(RESOURCE_BUNDLE, RESOURCE_KEY_PREFIX + "text.message.html.incorrect"), false));
		return msgs;
	}
}

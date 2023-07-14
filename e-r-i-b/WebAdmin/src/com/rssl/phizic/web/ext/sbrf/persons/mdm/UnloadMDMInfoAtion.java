package com.rssl.phizic.web.ext.sbrf.persons.mdm;


import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.person.mdm.UnloadMDMCardInfoOperation;
import com.rssl.phizic.operations.person.mdm.UnloadMDMInfoOperation;
import com.rssl.phizic.operations.person.mdm.UnloadMDMPersonInfoOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.download.DownloadAction;
import com.rssl.phizic.web.common.exception.FormProcessorException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшен выгрузки анкет клиентов ЕРИБ для загрузки в МДМ
 * @author komarov
 * @ created 20.07.15
 * @ $Author$
 * @ $Revision$
 */
public class UnloadMDMInfoAtion extends OperationalActionBase
{
	private static final String UNLOAD_MDM_INFO_FILE_TYPE = "UNLOAD_MDM_INFO_FILE_TYPE";
	private static final String FILE_CARD_NAME = "file_card";
	private static final String FILE_PERSON_NAME = "file_person";
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.unload.mdm.card.info",   "unloadCard");
		map.put("button.unload.mdm.client.info", "unloadPerson");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	/**
	 * Выгружает данные паспорта клиента
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward unloadCard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UnloadMDMInfoOperation operation = createOperation(UnloadMDMCardInfoOperation.class);
		return unload(form, operation, FILE_CARD_NAME);
	}

	/**
	 * Выгружает анкетные данные клиента
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward unloadPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UnloadMDMInfoOperation operation = createOperation(UnloadMDMPersonInfoOperation.class);
		return unload(form, operation, FILE_PERSON_NAME);
	}

	private ActionForward unload(ActionForm form, UnloadMDMInfoOperation operation, String fileName) throws Exception
	{
		UnloadMDMInfoForm frm = (UnloadMDMInfoForm) form;
		FormProcessor<ActionMessages, ?> processor = getFormProcessor(frm);

		if(!processor.process())
		{
			saveErrors(currentRequest(), processor.getErrors());
			return getCurrentMapping().findForward(FORWARD_START);
		}

		Map<String, Object> data = processor.getResult();
		String loginIds = (String) data.get("loginIds");

		try
		{
			operation.initialize(loginIds.split(","));
			DownloadAction.unload(operation.getEntity(), UNLOAD_MDM_INFO_FILE_TYPE, fileName, frm, currentRequest());
		}
		catch (BusinessLogicException e)
		{
			saveError(currentRequest(), e.getMessage());
		}

		return getCurrentMapping().findForward(FORWARD_START);
	}

	protected FormProcessor<ActionMessages, ?> getFormProcessor(EditFormBase frm) throws Exception
	{
		return createFormProcessor(new MapValuesSource(frm.getFields()), UnloadMDMInfoForm.MDM_FORM);
	}
}
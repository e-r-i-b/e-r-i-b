package com.rssl.phizic.web.common.mobile.profile;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.operations.userprofile.IncognitoSettingOperation;
import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lepihina
 * @ created 16.06.14
 * $Author$
 * $Revision$
 * Установка/получение статуса «инкогнито» в мобильном приложении
 */
public class EditIncognitoSettingMobileAction extends OperationalActionBase
{
	private static final String FORWARD_SUCCESS = "Success";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("show", "start");
		map.put("save", "save");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditIncognitoSettingMobileForm frm = (EditIncognitoSettingMobileForm) form;
		IncognitoSettingOperation operation = createOperation(IncognitoSettingOperation.class);
		operation.initialize(AuthenticationContext.getContext().getCSA_SID());
		frm.setIncognito(operation.getClientIncognito());
		return mapping.findForward(FORWARD_SHOW);
	}

	/**
	 * Сохраняет введенный статус инкогнито
	 * @param mapping стратс-маппинг
	 * @param form стратс-форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард на успешное выполнение операции
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditIncognitoSettingMobileForm frm = (EditIncognitoSettingMobileForm) form;
		IncognitoSettingOperation operation = createOperation(IncognitoSettingOperation.class);

		FieldValuesSource valuesSource = getMapValueSource(frm);
		Form newCategoryForm = EditIncognitoSettingMobileForm.EDIT_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, newCategoryForm);
		if (processor.process())
		{
			operation.initialize(AuthenticationContext.getContext().getCSA_SID(), (Boolean) processor.getResult().get("incognitoSetting"));
			operation.saveWithoutConfirm();
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}

		return mapping.findForward(FORWARD_SUCCESS);
	}

	protected MapValuesSource getMapValueSource(ActionFormBase frm)
	{
		EditIncognitoSettingMobileForm form = (EditIncognitoSettingMobileForm) frm;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("incognitoSetting", form.getIncognito());
		return new MapValuesSource(map);
	}
}

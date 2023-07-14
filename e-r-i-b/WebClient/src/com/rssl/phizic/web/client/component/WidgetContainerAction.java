package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.web.Layout;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.operations.skins.ChangePersonalSkinOperation;
import com.rssl.phizic.operations.widget.WidgetContainerOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.struts.forms.ActionMessagesKeys;
import com.rssl.phizic.web.util.ExceptionLogHelper;
import com.rssl.phizic.web.util.HttpSessionUtils;
import com.rssl.phizic.web.util.SkinHelper;
import org.apache.struts.action.*;
import org.apache.struts.taglib.html.Constants;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 14.07.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Экшен контейнера виджетов
 */
public class WidgetContainerAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("save", "save");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		WidgetContainerForm frm = (WidgetContainerForm) form;

        WidgetContainerOperation operation = createOperation(WidgetContainerOperation.class);
		operation.initialize(frm.getCodename());

		updateForm(mapping, frm, operation);

		return mapping.findForward(FORWARD_START);
	}

	private void updateForm(ActionMapping mapping, WidgetContainerForm form, WidgetContainerOperation operation)
	{
		form.setContainer(operation.getContainer());
		form.setContainerActionPath(mapping.getPath());
		form.setLogin(operation.getLogin());
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		WidgetContainerForm frm = (WidgetContainerForm) form;

        WidgetContainerOperation operation = createOperation(WidgetContainerOperation.class);
		operation.initialize(frm.getCodename());

		updateOperation(frm, operation);

		operation.save();

		return null;
	}

	private void updateOperation(WidgetContainerForm form, WidgetContainerOperation operation) throws BusinessException
	{
		if (StringHelper.isNotEmpty(form.getLocation()))
			operation.setLocation(form.getLocation());
		if (StringHelper.isNotEmpty(form.getLayout()))
			operation.setLayout(Layout.valueOf(form.getLayout()));
	}

	protected String getSkinUrl(ActionForm form) throws BusinessException
	{
		String previewSkin = currentRequest().getParameter("previewSkin");
		if (StringHelper.isEmpty(previewSkin))
			return super.getSkinUrl(form);
		ChangePersonalSkinOperation operation = createOperation(ChangePersonalSkinOperation.class);
		operation.initialize(Long.valueOf(previewSkin));
		return SkinHelper.updateSkinPath(operation.getCurrentSkin().getUrl());
	}

	

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			// Делаем форм-бин виджет-контейнера текущим для использования на jsp-странице.
			// В InsertTag предыдущее значение будет возвращено
			request.setAttribute(Constants.BEAN_KEY, form);
			return super.execute(mapping, form, request, response);
		}
		catch (Exception e)
		{
			// TODO (виджеты): Скопировано и модифицировано из SystemLogFilter. Слить 2 копии в одну либо доделать под виджеты
			ExceptionLogHelper.writeLogMessage(e);

			//Не отображаем сообщение об ошибке в зависимости от атрибута isEmptyErrorPage. Например, когда
			//используется bean:include и есть ошибка в JSP этого бина.
			if (request.getAttribute("isEmptyErrorPage") != null && (Boolean) request.getAttribute("isEmptyErrorPage"))
				return null;

			Throwable throwable = ExceptionLogHelper.getRootCause(e);
			if (throwable instanceof InactiveExternalSystemException)
			{
				InactiveExternalSystemException externalSystemException = (InactiveExternalSystemException) throwable;

				ActionMessages messages = HttpSessionUtils.getSessionAttribute(request, ActionMessagesKeys.inactiveExternalSystem.getKey());
				if (messages == null)
					messages = new ActionMessages();

				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(externalSystemException.getMessage(), false));
				request.getSession().setAttribute(ActionMessagesKeys.inactiveExternalSystem.getKey(), messages);
			}
			// TODO (виджеты): форвард на отображение ошибки
			return null;
		}
	}

	@Override
	protected String getWebPageHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		return null;
	}

	protected boolean needSaveMapping()
	{
		return false;
	}
}

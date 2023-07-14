package com.rssl.phizic.web.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.struts.forms.ActionMessagesKeys;
import com.rssl.phizic.web.util.ExceptionLogHelper;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.struts.action.*;
import org.apache.struts.taglib.html.Constants;

import java.io.IOException;
import java.security.AccessControlException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 08.06.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Базовый класс для экшенов виджетов
 */
public abstract class WidgetActionBase extends OperationalActionBase
{
	private static final String FORWARD_FORBIDDEN = "WidgetForbidden";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("add", "add");
		map.put("save", "save");
		map.put("remove", "remove");
		return map;
	}

	protected abstract WidgetOperation createWidgetOperation() throws BusinessException, AccessControlException;

	protected void updateForm(WidgetForm form, WidgetOperation operation) throws BusinessException, BusinessLogicException
	{
		form.setWidget(operation.getWidget());
		form.setSettings(operation.getSerializedWidget());
	}

	protected void updateForbiddenForm(ActionForm form) throws BusinessException
	{
		WidgetForm frm = (WidgetForm) form;
		WidgetOperation operation = createWidgetOperation();
		operation.initForbiddenWidget(frm.getCodename());
		frm.setForbiddenMode(true);
		frm.setWidget(operation.getWidget());
		frm.setSettings(operation.getSerializedWidget());
	}

	protected void updateOperation(WidgetOperation operation, WidgetForm frm) throws BusinessException
	{
		if (StringHelper.isNotEmpty(frm.getSettings()))
			operation.setWidget(frm.getSettings());
		if (StringHelper.isNotEmpty(frm.getPage()))
			operation.setContainer(frm.getPage());
	}
	
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException, IOException
	{
		try
		{
			WidgetForm frm = (WidgetForm) form;
			WidgetOperation operation = createWidgetOperation();
			operation.initialize(frm.getCodename());

			try
			{
				operation.checkUseStoredResource();
			}
			catch (InactiveExternalSystemException e)
			{
				saveInactiveESMessage(request, e.getMessage());
			}

			updateForm(frm, operation);
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (AccessControlException e)
		{
			log.warn("Нет доступа к операции", e);
			updateForbiddenForm(form);
			return mapping.findForward(FORWARD_FORBIDDEN);
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
			return mapping.findForward(FORWARD_SHOW);
		}
	}

	/**
	 * Сохранение виджета
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		try
		{
			WidgetForm frm = (WidgetForm)form;
			WidgetOperation operation = createWidgetOperation();
			operation.initialize(frm.getCodename());
			updateOperation(operation, frm);
			operation.update();
			operation.save();
			operation.stopBlinking(false);
			if (operation.shouldReloadClient()) {
				updateForm(frm, operation);
				return mapping.findForward(FORWARD_SHOW);
			}
			else return null;
		}
		catch (AccessControlException e)
		{
			log.warn("Нет доступа к операции", e);
			return null;
		}
	}

	/**
	 * Добавление нового виджета.
	 * На вход принимается codename, генерируемый веб-браузером.
	 * На выходе html-фрагмент с виджетом.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		try
		{
			WidgetForm frm = (WidgetForm)form;
			WidgetOperation operation = createWidgetOperation();

			operation.initializeNew(frm.getCodename(), frm.getDefname(), frm.getPage());
			updateOperation(operation, frm);
			operation.update();
			operation.save();

			updateForm(frm, operation);
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (AccessControlException e)
		{
			log.warn("Нет доступа к операции", e);

			WidgetForm frm = (WidgetForm) form;
			WidgetOperation operation = createWidgetOperation();
			operation.initAddForbiddenWidget(frm.getCodename(), frm.getDefname(), frm.getPage());
			frm.setForbiddenMode(true);
			frm.setWidget(operation.getWidget());
			frm.setSettings(operation.getSerializedWidget());

			return mapping.findForward(FORWARD_FORBIDDEN);
		}
	}

	/**
	 * Удаление виджета
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		try
		{
			WidgetForm frm = (WidgetForm)form;
			WidgetOperation operation = createWidgetOperation();
			operation.initialize(frm.getCodename());
			operation.remove();
			return null;
		}
		catch (AccessControlException e)
		{
			log.warn("Нет доступа к операции", e);
			return null;
		}
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			// Делаем форм-бин виджета текущим для использования на jsp-странице виджета.
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

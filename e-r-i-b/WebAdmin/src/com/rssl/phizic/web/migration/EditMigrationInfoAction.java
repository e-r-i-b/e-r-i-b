package com.rssl.phizic.web.migration;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.migration.MigrationConfig;
import com.rssl.phizic.business.migration.MigrationState;
import com.rssl.phizic.business.migration.TotalMigrationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.migration.EditMigrationInfoOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 26.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Экшен редактирования состояния миграции
 */

public class EditMigrationInfoAction extends OperationalActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> methodMap = super.getKeyMethodMap();
		methodMap.put("button.initialize", "initializeMigration");
		methodMap.put("button.start",      "startMigration");
		methodMap.put("button.stop",       "stopMigration");
		return methodMap;
	}

	private EditMigrationInfoOperation getOperation(EditMigrationInfoForm form) throws BusinessException
	{
		EditMigrationInfoOperation operation = createOperation(EditMigrationInfoOperation.class);
		Long currentId = form.getCurrentId();
		if (currentId != null)
			operation.initialize(currentId);
		else
			operation.initialize();
		return operation;
	}

	private void updateFormData(EditMigrationInfoForm form, TotalMigrationInfo migrationInfo)
	{
		form.setDataTimeout(ConfigFactory.getConfig(MigrationConfig.class).getMigrationStatePageDelay() * DateUtils.MILLIS_PER_SECOND);
		if (migrationInfo == null)
			return;
		form.setCurrentId(migrationInfo.getId());
		form.setMigrationInfo(migrationInfo);
		form.setBatchSize(migrationInfo.getBatchSize());
	}

	private ActionForward goToStart(ActionMapping mapping, EditMigrationInfoOperation operation, EditMigrationInfoForm form) throws BusinessException
	{
		updateFormData(form, operation.getMigrationInfo());
		return mapping.findForward(FORWARD_START);
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMigrationInfoForm form = (EditMigrationInfoForm) actionForm;
		EditMigrationInfoOperation operation = getOperation(form);
		return goToStart(mapping, operation, form);
	}

	/**
	 * Запуск миграции
	 * @param mapping    стратс маппинг
	 * @param actionForm стратс форма
	 * @param request    запрос
	 * @param response   ответ
	 * @return следующий обработчик
	 * @throws Exception
	 */
	@SuppressWarnings({"UnusedParameters"/*response*/, "UnusedDeclaration"})
	public ActionForward initializeMigration(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMigrationInfoForm form = (EditMigrationInfoForm) actionForm;
		EditMigrationInfoOperation operation = getOperation(form);

		try
		{
			operation.initializeMigration();
			if (operation.getMigrationInfo().getState() != MigrationState.INITIALIZE)
				saveMessage(request, "Миграция уже инициализирована!");
		}
		catch (BusinessLogicException e)
		{
			operation.initialize();
			saveMessage(request, e.getMessage());
		}

		return goToStart(mapping, operation, form);
	}

	/**
	 * Запуск миграции
	 * @param mapping       стратс маппинг
	 * @param actionForm    стратс форма
	 * @param request       запрос
	 * @param response      ответ
	 * @return следующий обработчик
	 * @throws Exception
	 */
	@SuppressWarnings({"UnusedParameters"/*response*/, "UnusedDeclaration"})
	public ActionForward startMigration(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMigrationInfoForm form = (EditMigrationInfoForm) actionForm;
		EditMigrationInfoOperation operation = getOperation(form);
		Long batchSize = form.getBatchSize();
		if (batchSize == null || batchSize <= 0)
		{
			saveMessage(request, "Не указано количество клиентов в группе.");
			return goToStart(mapping, operation, form);
		}

		try
		{
			operation.setBatchSize(batchSize);
			operation.startMigration();
		}
		catch (BusinessLogicException e)
		{
			operation.initialize();
			saveMessage(request, e.getMessage());
		}
		return goToStart(mapping, operation, form);
	}

	/**
	 * Остановка миграции
	 * @param mapping       стратс маппинг
	 * @param actionForm    стратс форма
	 * @param request       запрос
	 * @param response      ответ
	 * @return следующий обработчик
	 * @throws Exception
	 */
	@SuppressWarnings({"UnusedParameters"/*response*/, "UnusedDeclaration"})
	public ActionForward stopMigration(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMigrationInfoForm form = (EditMigrationInfoForm) actionForm;
		EditMigrationInfoOperation operation = getOperation(form);

		try
		{
			operation.stopMigration();
		}
		catch (BusinessLogicException e)
		{
			saveMessage(request, e.getMessage());
		}
		return goToStart(mapping, operation, form);
	}
}

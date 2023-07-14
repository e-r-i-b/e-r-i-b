package com.rssl.phizic.web.common;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.exception.FormProcessorException;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.web.log.FormLogParametersReader;
import org.apache.commons.collections.MapUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Krenev
 * @ created 19.12.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class ListActionBase extends OperationalActionBase
{
	/**
	 * Коммент для любителей убирать файнал спецификаторы:
	 * final ставится не просто так, и, если есть желание его убрать при реализации задачи,
	 * стоит задуматься, почему он стоит и как работает весь остальной функционал с ним.
	 *
	 * Скорее всего, поставленная задача решается костылями другого рода.
	 */
	protected final Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = getAditionalKeyMethodMap();
		map.put("button.remove", "remove");
		map.put("button.filter", "filter");
		return map;
	}

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	protected abstract ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException;

	/**
	 * Вернуть форму фильтрации.
	 * В большинстве случаев(до исправления ENH00319) будет возвращаться frm.FILTER_FORM
	 * @param frm struts-форма
	 * @param operation операция. Некоторые формы(например платежей) определяются операций
	 * @return форма фильтрации
	 */
	protected abstract Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation);

	/**
	 * заполнить запрос параметрыми для получения списка.
	 * @param query запрос
	 * @param filterParams параметры запроса.
	 */
	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException {}

	/**
	 * Получение данных и обновление формы.
	 * @param filterParams параметры фильтрации.
	 * @param operation операция для получения данных
	 * @param frm форма для обновления.
	 */
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		Query query = operation.createQuery(getQueryName(frm));
		fillQuery(query, filterParams);
		frm.setData(query.executeList());
		frm.setFilters(filterParams);
		updateFormAdditionalData(frm, operation);
	}

	/**
	 * @param frm форма
	 * @return Возвращается имя запроса, привязанного к операции(по умолчанию "list").
	 */
	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "list";
	}

	/**
	 * Обновление формы дополнительными данными.
	 * Например: передача данных для левого меню в анкете (редактируемый клиент)
	 * Данный метод переопределять по минимуму!!!
	 * @param frm форма для обновления
	 * @param operation операция для получения данных.
	 */
	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception {}

	/**
	 * @param frm
	 * @param operation
	 * @return значения фильтрации по умолчанию.
	 */
	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
			throws BusinessException, BusinessLogicException
	{
		return new HashMap<String, Object>();
	}

	/** Получить значения фильтрации по умолчанию (для очистки фильтра)
	 * @param frm  - форма
	 * @return значения фильтрации по умолчанию.
	 */
	public Map<String, Object> getDefaultParameters(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListEntitiesOperation operation = createListOperation(frm);
		return getDefaultFilterParameters(frm, operation);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Удаление сущности
	 * @param operation операция удаления
	 * @param id идентификатор удаляемо сущности
	 * @return в случае логических ошибок сообщение для отображения пользователю. null при удачном удалении
	 */
	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();

		try
		{
			operation.initialize(id);
			operation.remove();
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(currentRequest(), e);
		}
		return msgs;
	}

	/**
	 * Во всех экшенах списка после форварда переходим на фильтр	 
	 */
	public ActionForward getFavouriteForward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return filter(mapping, form, request, response);
	}

	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		frm.setFromStart(true);

		try
		{
			doStart(createListOperation(frm), frm);
		}
		catch (FormProcessorException e)
		{
			saveErrors(request, e.getErrors());
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}

		return createActionForward(frm);
	}

	protected void doStart(ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		Map<String, Object> filterParameters = getFilterParams(frm, operation);

		//Фиксируем данные фильтрации по умолчанию
		addLogParameters(new FormLogParametersReader("Параметры фильтрации", getFilterForm(frm, operation), filterParameters));

		setDefaultOrderParameters(currentRequest());
		doFilter(filterParameters, operation, frm);
	}

	/**
	 * Устанавливает параметры сортировки столбцов поумолчанию.
	 * @param request
	 */
	protected void setDefaultOrderParameters(HttpServletRequest request){	}

	protected Map<String, Object> getFilterParams(ListFormBase frm, ListEntitiesOperation operation)
			throws BusinessException, BusinessLogicException
	{
		Form filterForm = getFilterForm(frm, operation);
		FieldValuesSource fieldValuesSource = getFilterValuesSource(frm);
		if (!fieldValuesSource.isEmpty())
		{
			//если пришли поля с формы, валидируем их и возвращаем в качестве фильтрующих значений
			FormProcessor<ActionMessages, ?> processor = createFormProcessor(fieldValuesSource, filterForm);
			if (processor.process())
				return processor.getResult();
			throw new FormProcessorException(processor.getErrors());
		}

		//иначе получаем сохраненные,
		Map<String, Object> savedFilterParams = loadFilterParameters(frm, operation);
		if (MapUtils.isNotEmpty(savedFilterParams))
			return savedFilterParams;

		// а если и их нет, используем параметры по умолчанию.
		return getDefaultFilterParameters(frm, operation);
	}

	/**
	 * Метод, возвращающий сохраненные параметры фильтрации
	 * Реализуется наследниками (на 22.09.2011 реализован в SaveFilterParameterAction).
	 * Если сохраненных параметров нет, метод ДОЛЖЕН вернуть null.
	 * Если они есть, но пустые, может вернуть пустую Map-у, но НЕ null.
	 * ATTENTION! У наследников, реализующих этот метод, должен быть переопределен и метод
	 * saveFilterParameters, сохраняющий параметры фильтрации.
	 * @return сохраненные параметры фильтрации
	 */
	protected Map<String,Object> loadFilterParameters(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		return null;
	}

	/**
	 * @param frm форма
	 * @return источник значений полей фильтрации
	 */
	protected FieldValuesSource getFilterValuesSource(ListFormBase frm)
	{
		return new MapValuesSource(frm.getFilters());
	}

	public final ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		ListEntitiesOperation operation = null;

		try
		{
			operation = createListOperation(frm);
			Map<String, Object> filterParameters = getFilterParams(frm, operation);
			//Фиксируем данные фильтрации
			addLogParameters(new FormLogParametersReader("Параметры фильтрации", getFilterForm(frm, operation), filterParameters));
			doFilter(filterParameters, operation, frm);
			saveFilterParameters(filterParameters);
		}
		catch (FormProcessorException e)
		{
			saveErrors(request, e.getErrors());
			frm.setFromStart(true);
			forwardFilterFail(frm, operation);
		}
		catch (InactiveExternalSystemException e)
		{
			//устанавливаем данный признак для того чтобы не выполнялся запрос поиска в базе в случае ошибки.
			frm.setFromStart(true);
			forwardFilterFail(frm, operation);
			saveInactiveESMessage(request, e);
		}

		return createActionForward(frm);
	}

	/**
	 * Метод для сохранения параметров фильтрации.
	 * Реализуется наследниками (на 22.09.2011 реализован в SaveFilterParameterAction).
	 * ATTENTION! У наследников, реализующих этот метод, должен быть переопределен и метод
	 * loadFilterParameters, который должен будет возвращать сохраненные параметры.
	 * @param filterParams
	 */
	protected void saveFilterParameters(Map<String,Object> filterParams) throws BusinessException {};

	/**
	 * Переход для варианта, когда фильтр не удался (например, из-за некорректных параметров фильтра)
	 * @param form
	 * @param operation
	 */
	protected void forwardFilterFail(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		// Никаких списков строить не нужно, если параметры фильтрации некорректны
		form.setData(Collections.emptyList());
		updateFormAdditionalData(form, operation);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		List<Long> ids = StrutsUtils.parseIds(frm.getSelectedIds());

		ActionMessages errors = new ActionMessages();

		try
		{
			RemoveEntityOperation operation = createRemoveOperation(frm);
			for (Long id : ids)
			{
				errors.add(doRemove(operation, id));
				//Фиксируем удаляемые сущности.
				addLogParameters(new BeanLogParemetersReader("Данные удаленной сущности", operation.getEntity()));
			}
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
		}

		stopLogParameters();
		saveErrors(request, errors);

		return filter(mapping, form, request, response);
	}

	protected ActionForward createActionForward(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}
}

package com.rssl.phizic.web.common;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.web.common.exception.FormProcessorException;
import com.rssl.phizic.web.log.FormLogParametersReader;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Krenev
 * @ created 31.12.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditActionBase extends OperationalActionBase
{
	protected static final String FORWARD_SUCCESS = "Success";
	protected static final String FORWARD_CLOSE   = "Close";
	protected static final String FORWARD_FAILURE = "Failure";


	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = getAdditionalKeyMethodMap();

		map.put("button.save", "save");
		return map;
	}

	protected Map<String, String>getAdditionalKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	/**
	 * —оздать и проинициализировать операцию (операци€ просмотра).
	 * @param frm форма
	 * @return созданна€ операци€.
	 */
	protected EditEntityOperation createViewOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createEditOperation(frm);
	}

	/**
	 * —оздать и проинициализировать операцию (операци€ редактировани€).
	 * @param frm форма
	 * @return созданна€ операци€.
	 */
	protected abstract EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException;

	/**
	 * ¬ернуть форму редактировани€.
	 * ¬ большинстве случаев(до исправлени€ ENH00319) будет возвращатьс€ frm.EDIT_FORM 
	 * @param frm struts-форма
	 * @return форма редактировани€
	 */
	protected abstract Form getEditForm(EditFormBase frm);

	/**
	 * ќбновить сужность данными.
	 * @param entity сужность
	 * @param data данные
	 */
	protected abstract void updateEntity(Object entity, Map<String, Object> data) throws Exception;

	/**
	 * ѕроинициализировать/обновить struts-форму
	 * @param frm форма дл€ обновлени€
	 * @param entity объект дл€ обновлени€.
	 */
	protected abstract void updateForm(EditFormBase frm, Object entity) throws Exception;

	/**
	 * ќбновление формы дополнительными данными.
	 * Ќапример: передача данных дл€ левого меню в анкете (редактируемый клиент)
	 * ƒанный метод переопредел€ть по минимуму!!!
	 * @param frm форма дл€ обновлени€
	 * @param operation операци€ дл€ получени€ данных.
	 */
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception {}

	/**
	 * ќбновить операцию дополнительными данными
	 * @param editOperation - операци€ редактировани€
	 * @param editForm - форма редактировани€
	 * @param validationResult - результаты валидации в форм-процессоре
	 */
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception {}

	/**
	 * ¬алидаци€ данных формы, проверка которых невозможна через FormProcessor
	 * ƒанный метод переопредел€ть по минимуму!!!
	 * @param frm форма дл€ обновлени€
	 */
	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return new ActionMessages();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditFormBase frm = (EditFormBase) form;
		frm.setFromStart(true);

		try
		{
			doStart(createViewOperation(frm), frm);
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
		}

		return createStartActionForward(form, mapping);
	}

	protected void doStart(EditEntityOperation operation, EditFormBase frm) throws Exception
	{
		Object entity = operation.getEntity();
		//фиксируем начальные данные редактируемой сущности
		addLogParameters(new BeanLogParemetersReader("ƒанные редактируемой сущности", entity));

		updateForm(frm, entity);
		updateFormAdditionalData(frm, operation);
	}

	/**
	 * ‘ункци€ дл€ получени€ процессора
	 * @param frm форма дл€ получени€ данных.
	 * @return процессор
	 */
	protected FormProcessor<ActionMessages, ?> getFormProcessor(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return createFormProcessor(getFormProcessorValueSource(frm, operation), getEditForm(frm));
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditFormBase frm = (EditFormBase) form;
		EditEntityOperation operation = null;

		try
		{
			operation = createEditOperation(frm);
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
			return start(mapping, form, request, response);
		}

		ActionMessages actionMessages = validateAdditionalFormData(frm, operation);
		if (!actionMessages.isEmpty())
		{
			saveErrors(request, actionMessages);
			updateFormAdditionalData(frm, operation);
			return createStartActionForward(frm, mapping);
		}

		ActionForward actionForward;

		try
		{
			Map<String, Object> result = checkFormData(frm, operation);
			Object entity = operation.getEntity();
			updateEntity(entity, result);
			updateOperationAdditionalData(operation, frm, result);
			actionForward = doSave(operation, mapping, frm);

			updateFormAdditionalData(frm, operation);
		}
		catch (FormProcessorException e)
		{
			saveErrors(request, e.getErrors());
			updateFormAdditionalData(frm, operation);
			return createStartActionForward(frm, mapping);
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
			updateFormAdditionalData(frm, operation);
			return createStartActionForward(frm, mapping);
		}
		catch (BusinessLogicException e)
		{
			try
			{
				saveError(request, e);
				updateFormAdditionalData(frm, createViewOperation(frm));
			}
			catch (InactiveExternalSystemException iese)
			{
				saveInactiveESMessage(request, iese);
			}
			
			return createStartActionForward(frm, mapping);
		}

		return actionForward;
	}

	protected Map<String, Object> checkFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		FormProcessor<ActionMessages, ?> processor = getFormProcessor(frm, operation);

		if(!processor.process())
			throw new FormProcessorException(processor.getErrors());

		return processor.getResult();
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		operation.save();
		addLogParameters(frm);
		return createSaveActionForward(operation, frm);
	}

	/**
	 * ‘иксируем данные, введенные пользователем
	 * @param frm форма
	 */
	protected void addLogParameters(EditFormBase frm)
	{
		Form form = getEditForm(frm);
		Map<String, Object> values = frm.getFields();
		addLogParameters(new FormLogParametersReader("ƒанные, введенные пользователем", form, values));
	}

	protected ActionForward createStartActionForward(ActionForm form, ActionMapping mapping) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_SUCCESS);
	}


	protected List<String> getChangedFields(HttpServletRequest request)
	{
		String changedFieldsParameter = request.getParameter("$$changedFields");
		if (StringHelper.isEmpty(changedFieldsParameter))
		{
			return Collections.emptyList();
		}

		return Arrays.asList(changedFieldsParameter.split(";"));
	}

	/**
	 * ѕолучени€ источника данных дл€ форм процессора
	 * @param frm форма дл€ получени€ данных
	 * @return источник данных (FieldValuesSource)
	 * @throws Exception
	 */
	protected FieldValuesSource getFormProcessorValueSource(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return new MapValuesSource(frm.getFields());
	}
}

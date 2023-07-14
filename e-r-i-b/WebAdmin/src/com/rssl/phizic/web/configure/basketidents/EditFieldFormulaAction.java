package com.rssl.phizic.web.configure.basketidents;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.basketident.FieldFormula;
import com.rssl.phizic.business.dictionaries.basketident.FieldFormulaAttribute;
import com.rssl.phizic.common.types.basket.fieldFormula.Constants;
import com.rssl.phizic.operations.basket.fieldFormula.EditFieldFormulaOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Экшен создания\редактирования связок ПУ с документами
 */
public class EditFieldFormulaAction extends OperationalActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.save", "save");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditFieldFormulaForm frm = (EditFieldFormulaForm) form;
		try
		{
			EditFieldFormulaOperation operation = createOperation(frm);
			updateForm(operation, frm);

			return mapping.findForward(FORWARD_START);
		}
		catch (BusinessLogicException e)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveSessionErrors(request, msgs);

			return createShowForward(frm.getIdentId());
		}
	}

	/**
	 * Сохранить изменений
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditFieldFormulaForm frm = (EditFieldFormulaForm) form;
		EditFieldFormulaOperation operation = createOperation(frm);
		try
		{
			FieldFormulaFormBuilder formBuilder = new FieldFormulaFormBuilder(frm.getFieldIds(), frm.getNewFieldIds(), operation.getFieldDescriptions(), operation.getAttributes());
			FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(new MapValuesSource(frm.getFields()), formBuilder.build());

			if (formProcessor.process())
			{
				Map<String, Object> result = formProcessor.getResult();
				updateFormulas(operation, result, frm);
				addFormulas(operation, result, frm);
			}
			else
			{
				saveErrors(request, formProcessor.getErrors());
				updateForm(operation, frm);

				return mapping.findForward(FORWARD_START);
			}
		}
		catch (BusinessLogicException e)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, msgs);

			updateForm(operation,frm);
			return mapping.findForward(FORWARD_START);
		}

		return createShowForward(frm.getIdentId());
	}

	private ActionForward createShowForward(Long identId)
	{
		ActionForward forward = new ActionForward(getCurrentMapping().findForward(FORWARD_SHOW));
		forward.setPath(forward.getPath() + "?id=" + identId);
		return forward;
	}

	private void addFormulas(EditFieldFormulaOperation operation, Map<String, Object> result, EditFieldFormulaForm frm) throws BusinessException, BusinessLogicException
	{
		String newFieldIds = frm.getNewFieldIds();
		if (StringHelper.isNotEmpty(newFieldIds))
		{
			String[] ids = newFieldIds.split(Constants.ID_SPLITTER);
			List<FieldFormula> newFormulas = new ArrayList<FieldFormula>(ids.length);

			for (String id : ids)
			{
				FieldFormula newFormula = new FieldFormula();
				int attributesSize = operation.getAttributes().size();

				updateFormula(newFormula, result, id, frm, Constants.NEW_FIELD_PREFIX);
				newFormula.setAttributes(createAttributes(result, id, attributesSize));
				newFormulas.add(newFormula);
			}

			operation.add(newFormulas);
		}
	}

	private void updateFormula(FieldFormula formula, Map<String, Object> result, Object id, EditFieldFormulaForm frm, String prefix)
	{
		formula.setIdentifierId(frm.getIdentId());
		formula.setProviderId(frm.getId());
		formula.setFieldExternalId((String) result.get(Constants.getProviderFieldName(id, prefix)));
	}

	private List<FieldFormulaAttribute> createAttributes(Map<String, Object> result, Object id, int attributesSize)
	{
		List<FieldFormulaAttribute> attributes = new ArrayList<FieldFormulaAttribute>(attributesSize + 1);

		for (int i = 0; i < attributesSize; i++)
		{
			FieldFormulaAttribute newAttribute = new FieldFormulaAttribute();

			updateAttribute(newAttribute, result, id, i, false, Constants.NEW_FIELD_PREFIX);
			attributes.add(newAttribute);
		}

		FieldFormulaAttribute newLastAttribute = new FieldFormulaAttribute();
		updateAttribute(newLastAttribute, result, id, 0, true, Constants.NEW_FIELD_PREFIX);
		attributes.add(newLastAttribute);

		return attributes;
	}

	private void updateAttribute(FieldFormulaAttribute attribute, Map<String, Object> result, Object id, int index, boolean isLast, String prefix)
	{
		String valueFieldName = isLast ?
				Constants.getLastFieldName(id, prefix) :
				Constants.getValueFieldName(id, index, prefix);

		attribute.setLast(isLast);
		attribute.setValue((String) result.get(valueFieldName));
		attribute.setSerial(Long.valueOf(index));

		if (!isLast)
		{
			attribute.setSystemId((String) result.get(Constants.getIdentTypeFieldName(id, index, prefix)));
		}
	}

	private void updateFormulas(EditFieldFormulaOperation operation, Map<String, Object> result, EditFieldFormulaForm frm) throws BusinessLogicException, BusinessException
	{
		String fieldIds = frm.getFieldIds();
		List<FieldFormula> formulasToRemove = new ArrayList<FieldFormula>(operation.getFormulas().size());

		if (StringHelper.isNotEmpty(fieldIds))
		{
			for (FieldFormula formula : operation.getFormulas())
			{
				if (result.containsKey(Constants.getProviderFieldName(formula.getId(), StringUtils.EMPTY)))
				{
					updateFormula(formula, result, formula.getId(), frm, StringUtils.EMPTY);
					updateAttributes(formula.getAttributes(), result, formula.getId(), operation.getAttributes().size());
				}
				else
				{
					formulasToRemove.add(formula);
				}
			}

			operation.save();
		}

		if (StringHelper.isEmpty(fieldIds) || CollectionUtils.isNotEmpty(formulasToRemove))
		{
			operation.remove(formulasToRemove);
		}
	}

	private void updateAttributes(List<FieldFormulaAttribute> attributes, Map<String, Object> result, Long id, int size)
	{
		for (FieldFormulaAttribute attribute : attributes)
		{
			updateAttribute(attribute, result, id, attribute.getSerial().intValue(), attribute.isLast(), StringUtils.EMPTY);
		}
		if (attributes.size() < size)
		{
			for (int i = attributes.size(); i < size; i++)
			{
				FieldFormulaAttribute newAttribute = new FieldFormulaAttribute();
				updateAttribute(newAttribute, result, id, i, false, StringUtils.EMPTY);

				attributes.add(newAttribute);
			}
		}
	}

	private EditFieldFormulaOperation createOperation(EditFieldFormulaForm frm) throws BusinessException, BusinessLogicException
	{
		EditFieldFormulaOperation operation = createOperation(EditFieldFormulaOperation.class);

		if (frm.getId() == null)
		{
			operation.initializeNew(frm.getExternalId(), frm.getIdentId());
		}
		else
		{
			operation.initialize(frm.getId(), frm.getIdentId());
		}

		return operation;
	}

	private void updateForm(EditFieldFormulaOperation operation, EditFieldFormulaForm frm)
	{
		List<FieldFormula> formulas = operation.getFormulas();
		for (FieldFormula formula: formulas)
		{
			frm.setField(Constants.getProviderFieldName(formula.getId(), StringUtils.EMPTY), formula.getFieldExternalId());

			updateAttributes(formula.getAttributes(), frm, formula.getId());
		}

		frm.setFormulas(formulas);
		frm.setFieldDescriptions(operation.getFieldDescriptions());
		frm.setAttributes(operation.getAttributes());
		frm.setServiceProviders(Collections.singletonList(operation.getServiceProvider()));

		if (frm.getId() == null)
		{
			frm.setId(operation.getServiceProvider().getId());
		}
	}

	private void updateAttributes(List<FieldFormulaAttribute> attributes, EditFieldFormulaForm frm, Long id)
	{
		for (FieldFormulaAttribute attribute : attributes)
		{
			if (attribute.isLast())
			{
				frm.setField(Constants.getLastFieldName(id, StringUtils.EMPTY), attribute.getValue());
			}
			else
			{
				frm.setField(Constants.getIdentTypeFieldName(id, attribute.getSerial().intValue(), StringUtils.EMPTY), attribute.getSystemId());
				frm.setField(Constants.getValueFieldName(id, attribute.getSerial().intValue(), StringUtils.EMPTY), attribute.getValue());
			}
		}
	}
}

package com.rssl.phizic.web.common.client.finances;

import com.rssl.phizic.operations.finances.EditCardOperationForm;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Erkin
 * @ created 19.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditCategoryAbstractForm extends ActionFormBase
{
	private Long operationId;

	private String operationTitle;

	private Long operationCategoryId;

	private String operationCountry;

	private Boolean resetCountry;

	// TreeMap позволяет сохранить сортировку по ключу (индексу в массиве операций)
	private final Map<Integer, EditCardOperationForm> newOperations = new TreeMap<Integer, EditCardOperationForm>();

	private Boolean autoRecategorization;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return ID редактируемой операции
	 */
	public Long getOperationId()
	{
		return operationId;
	}

	public void setOperationId(Long operationId)
	{
		this.operationId = operationId;
	}

	/**
	 * @return Название (описание) редактируемой операции
	 */
	public String getOperationTitle()
	{
		return operationTitle;
	}

	public void setOperationTitle(String operationTitle)
	{
		this.operationTitle = operationTitle;
	}

	/**
	 * @return ID категории редактируемой операции
	 */
	public Long getOperationCategoryId()
	{
		return operationCategoryId;
	}

	public void setOperationCategoryId(Long operationCategoryId)
	{
		this.operationCategoryId = operationCategoryId;
	}

	/**
	 * @return новые операции, полученные в результате разбиения старой
	 */
	public List<EditCardOperationForm> getNewOperations()
	{
		if (newOperations.isEmpty())
			return Collections.emptyList();
		else return new ArrayList<EditCardOperationForm>(newOperations.values());
	}

	private EditCardOperationForm ensureNewOperation(int index)
	{
		EditCardOperationForm form = newOperations.get(index);
		if (form == null) {
			form = new EditCardOperationForm();
			newOperations.put(index, form);
		}
		return form;
	}

	public String getNewOperationTitle(int index)
	{
		EditCardOperationForm form = newOperations.get(index);
		return form != null ? form.getDescription() : null;
	}

	public void setNewOperationTitle(int index, String value)
	{
		EditCardOperationForm form = ensureNewOperation(index);
		form.setDescription(value);
	}

	public Long getNewOperationCategoryId(int index)
	{
		EditCardOperationForm form = newOperations.get(index);
		return form != null ? form.getCategoryId() : null;
	}

	public void setNewOperationCategoryId(int index, Long value)
	{
		EditCardOperationForm form = ensureNewOperation(index);
		form.setCategoryId(value);
	}

	public BigDecimal getNewOperationSum(int index)
	{
		EditCardOperationForm form = newOperations.get(index);
		return form != null ? form.getAmount() : null;
	}

	public void setNewOperationSum(int index, BigDecimal value)
	{
		EditCardOperationForm form = ensureNewOperation(index);
		form.setAmount(value);
	}

	/**
	 * @return true - установить правило перекатегоризации
	 */
	public Boolean getAutoRecategorization()
	{
		return autoRecategorization;
	}

	/**
	 * @param autoRecategorization - true - установить правило перекатегоризации
	 */
	public void setAutoRecategorization(Boolean autoRecategorization)
	{
		this.autoRecategorization = autoRecategorization;
	}

	public String getOperationCountry()
	{
		return operationCountry;
	}

	public void setOperationCountry(String operationCountry)
	{
		this.operationCountry = operationCountry;
	}

	public Boolean getResetCountry()
	{
		return resetCountry;
	}

	public void setResetCountry(Boolean resetCountry)
	{
		this.resetCountry = resetCountry;
	}
}

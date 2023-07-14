package com.rssl.phizic.web.common.client.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.logging.finances.category.CardOperationCategoryChangingLogEntry;
import com.rssl.phizic.logging.finances.category.CardOperationCategoryChangingLogHelper;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.finances.EditCardOperationForm;
import com.rssl.phizic.operations.finances.EditCategoryAbstractOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import com.rssl.phizic.web.log.CollectionLogParametersReader;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 19.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditCategoryAbstractAction extends AsyncOperationalActionBase
{

	protected Map<String, String> getKeyMethodMap()
	{
		final Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		return map;
	}

	private EditCategoryAbstractOperation createEditCategoryAbstractOperation(EditCategoryAbstractForm form) throws BusinessLogicException, BusinessException
	{
		Long operationId = form.getOperationId();
		if (operationId == null)
			throw new BusinessException("Ќе указан operationId");

		EditCategoryAbstractOperation operation = createOperation("EditCategoryAbstractOperation", "EditOperationsService");
		operation.initialize(operationId);
		return operation;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * ѕользователь нажал "—охранить" на редактировании/разбиении карточной операции
	 * @param mapping
	 * @param frm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward save(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		EditCategoryAbstractForm form = (EditCategoryAbstractForm) frm;
		EditCategoryAbstractOperation editOperation = createEditCategoryAbstractOperation(form);

		try
		{
			List<EditCardOperationForm> newCardOperationForms = form.getNewOperations();

			addLogParameters(new BeanLogParemetersReader("–едактируема€ карт-операци€", editOperation.getOriginalOperation()));
			addLogParameters(new SimpleLogParametersReader("Ќовое название карт-операции", form.getOperationTitle()));
			addLogParameters(new SimpleLogParametersReader("Ќова€ категори€ карт-операции (ID)", form.getOperationCategoryId()));
			if (!CollectionUtils.isEmpty(newCardOperationForms))
				addLogParameters(new CollectionLogParametersReader("ƒобавленные операции", newCardOperationForms));

			editOperation.setCardOperationNewDescription(form.getOperationTitle());
			editOperation.setCardOperationNewCategoryId(form.getOperationCategoryId());
			editOperation.setNewCardOperations(newCardOperationForms);
			editOperation.setCountry(form.getOperationCountry());
			editOperation.setResetCountry(BooleanUtils.isTrue(form.getResetCountry()));
			editOperation.save(form.getAutoRecategorization());
			saveLogs(editOperation, newCardOperationForms.size());
		}
		catch (BusinessLogicException e)
		{
			return forwardError(e.getMessage(), request, response);
		}
		return null;
	}

	private void saveLogs(EditCategoryAbstractOperation operation, int newOperationsCount)
	{
		if (newOperationsCount == 0 && operation.getNewCategoriesCount() == 0)
			return;

		try
		{
			CardOperation parentOperation = operation.getOriginalOperation();
			Department department = operation.getPersonDepartment();
			int newCategoriesCount = operation.getNewCategoriesCount() != 0 ? operation.getNewCategoriesCount() : newOperationsCount;

			CardOperationCategoryChangingLogEntry logEntry = new CardOperationCategoryChangingLogEntry();
			logEntry.setDate(DateHelper.getCurrentDate());
			logEntry.setTB(department.getCode().getFields().get("region"));
			logEntry.setVSP(department.getCode().getFields().get("office"));
			logEntry.setFIO(operation.getPerson().getFullName());
			logEntry.setOperationName(parentOperation.getDescription());
			logEntry.setMccCode(parentOperation.getMccCode());
			logEntry.setAmount(parentOperation.getNationalAmount());
			logEntry.setParentCategory(parentOperation.getCategory().getName());
			logEntry.setNewCategories(operation.getNewCategories());
			logEntry.setNewCategoriesCount(newCategoriesCount);

			CardOperationCategoryChangingLogHelper.writeEntryToLog(logEntry);
		}
		catch (SystemException ignore)
		{
			log.error("Ќе удалось создать запись в журнале статистики по изменению клиентом категории карточной операции.");
		}
	}
}

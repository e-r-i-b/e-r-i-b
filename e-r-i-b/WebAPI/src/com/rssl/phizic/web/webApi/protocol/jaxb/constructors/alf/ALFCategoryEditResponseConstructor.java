package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.MapErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.finances.ExistOperationsInCardOperationCategoryException;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.business.finances.recategorization.ALFRecategorizationRule;
import com.rssl.phizic.business.finances.recategorization.ALFRecategorizationRuleService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.finances.CategoriesListOperation;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.webApi.exceptions.FormValidationException;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.StatusCode;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.ALFCategoryEditRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.ALFCategoryEditRequestBody;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.ALFCategoryEditResponse;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.CategoryToLinkOperationsTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.OperationType;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

import java.util.*;

/**
 * «аполн€ет ответ на запрос на работу с расходной категорией јЋ‘
 * @author Jatsky
 * @ created 15.05.14
 * @ $Author$
 * @ $Revision$
 */

public class ALFCategoryEditResponseConstructor extends ALFBaseResponseConstructor<ALFCategoryEditRequest, ALFCategoryEditResponse>
{
	private static final String EMPTY_OPERATION_MESSAGE = "не задан тип выполн€емой операции";
	private static final String EMPTY_ID_MESSAGE = "не задан идентификатор категории";
	private static final CardOperationService cardOperationService = new CardOperationService();
	private static final ALFRecategorizationRuleService recategorizationRuleService = new ALFRecategorizationRuleService();

	@Override protected ALFCategoryEditResponse makeResponse(ALFCategoryEditRequest request) throws Exception
	{
		ALFCategoryEditResponse response = new ALFCategoryEditResponse();
		ALFCategoryEditRequestBody requestBody = request.getBody();
		if (OperationType.ADD.equals(requestBody.getOperationType()))
			add(requestBody.getId(), requestBody.getName(), request, response, requestBody.getOperationType().equals(OperationType.ADD));
		else if (OperationType.EDIT.equals(requestBody.getOperationType()))
			if (requestBody.getId() == null)
				throw new BusinessException(EMPTY_ID_MESSAGE);
			else
				add(requestBody.getId(), requestBody.getName(), request, response, requestBody.getOperationType().equals(OperationType.ADD));
		else if (OperationType.REMOVE.equals(requestBody.getOperationType()))
			if (requestBody.getId() == null)
				throw new BusinessException(EMPTY_ID_MESSAGE);
			else
				delete(requestBody.getId(), response);
		else
			throw new BusinessException(EMPTY_OPERATION_MESSAGE);
		return response;
	}

	/**
	 * ƒобавление и редактирование категории клиента
	 * @param id идентификатор категории
	 * @param name название категории
	 * @param response ответ на запрос
	 * @param isAdding тип операции(true - добавление)
	 * @throws Exception
	 */
	public void add(Long id, String name, ALFCategoryEditRequest request, ALFCategoryEditResponse response, boolean isAdding) throws Exception
	{
		CategoriesListOperation operation = createOperation("CategoriesEditOperation", "EditCategoriesService");
		operation.initialize(id);

		MapErrorCollector mapErrorCollector = new MapErrorCollector();
		FormProcessor<Map<String, String>, MapErrorCollector> processor = new FormProcessor<Map<String, String>, MapErrorCollector>(getMapValueSource(request), ALFCategoryEditRequestBody.NEW_CATEGORY_FORM, mapErrorCollector, DefaultValidationStrategy.getInstance());
		if (processor.process())
		{
			CardOperationCategory category = operation.getEntity();
			category.setName(name);
			category.setIncome(false);
			category.setCash(true);
			category.setCashless(true);
			category.setColor(operation.getFreeColor());
			operation.save();
			if (isAdding)
				response.setId(category.getId());
		}
		else
		{
			Map<String, String> errors = processor.getErrors();
			throw new FormValidationException(errors);
		}
	}

	/**
	 * ”даление категории клиента
	 * @param id идентификатор категории
	 * @throws Exception
	 */
	public void delete(Long id, ALFCategoryEditResponse response) throws Exception
	{
		try
		{
			CategoriesListOperation operation = createOperation("CategoriesEditOperation", "EditCategoriesService");
			operation.delete(id);
		}
		catch (ExistOperationsInCardOperationCategoryException e)
		{
			Login login = PersonContext.getPersonDataProvider().getPersonData().getLogin();
			List<CardOperation> cardOperations = cardOperationService.getCardOperations(login, id, Integer.MAX_VALUE, 0);
			List<CategoryToLinkOperationsTag> categories = new ArrayList<CategoryToLinkOperationsTag>();
			for (CardOperation cardOperation : cardOperations)
			{
				ALFRecategorizationRule recategorizationRuleByOperation = recategorizationRuleService.findRecategorizationRuleByOperation(cardOperation);
				if (recategorizationRuleByOperation == null)
					continue;
				CategoryToLinkOperationsTag categoryToLinkOperationsTag = new CategoryToLinkOperationsTag();
				categoryToLinkOperationsTag.setId(recategorizationRuleByOperation.getNewCategory().getId());
				categories.add(categoryToLinkOperationsTag);
			}
			response.setStatus(new Status(StatusCode.CATEGORY_HAVE_LINKED_OPERATION, e.getMessage()));
			response.setCategoriesToLinkOperations(categories);
		}
	}

	protected MapValuesSource getMapValueSource(ALFCategoryEditRequest request)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", request.getBody().getName());
		return new MapValuesSource(map);
	}

	private static String getResourcesMessage(String bundle, String key, Object[] values)
	{
		MessageResources resources = (MessageResources) WebContext.getCurrentRequest().getAttribute(bundle);
		Locale userLocale = (Locale) WebContext.getCurrentRequest().getAttribute(Globals.LOCALE_KEY);
		return resources.getMessage(userLocale, key, values);
	}
}

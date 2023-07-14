package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.finances.OperationType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.finances.FinancesConfig;
import com.rssl.phizic.operations.finances.CardOperationDescription;
import com.rssl.phizic.operations.finances.EditDeletedCategoryAbstractOperation;
import com.rssl.phizic.operations.finances.GetListOfOperationsOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.web.component.DatePeriodFilter;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.AbstractFinanceConstructor;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.utils.LinkUtils;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.AlfOperation;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.Category;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Money;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.AlfHistoryRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.AlfHistoryRequestBody;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.AlfHistoryResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Формирует ответ для запроса истории операций АЛФ
 *
 * @author Balovtsev
 * @since 12.05.2014
 */
public class AlfHistoryConstructor extends AbstractFinanceConstructor<AlfHistoryRequest, AlfHistoryResponse>
{
	protected static final String FIELD_INCOME              = "income";
	protected static final String FIELD_SHOW_CASH           = "showCash";
	protected static final String FIELD_PAGINATION_SIZE     = "paginationSize";
	protected static final String FIELD_PAGINATION_OFFSET   = "paginationOffset";
	protected static final String FIELD_SHOW_OTHER_ACCOUNTS = "showOtherAccounts";
	private static final String VIEW_PAYMENT_URL = "/private/payments/view.do?id=%d";

	/**
	 * @see FinanceMobileFormBase#createMobileFilterForm()
	 * @return форма идентичная той что создается в FinanceMobileFormBase, но из-за необходимости
	 * добавлять зависимость был просто скопирован код
	 */
	@Override
	protected Form getForm()
	{
		return AlfHistoryForm.FORM;
	}

	@Override
	protected MapValuesSource getMapValueSource(AlfHistoryRequest request)
	{
		Map<String, Object> sources = new HashMap<String, Object>();

		AlfHistoryRequestBody body  = request.getBody();
		sources.put("showCreditCards",         body.getShowCreditCards());
		sources.put("openPageDate",            Calendar.getInstance().getTime());
		sources.put("categoryId",              body.getCategoryId());
		sources.put("showCashPayments",        body.getShowCashPayments());

		sources.put(FIELD_INCOME,              body.getIncome());
		sources.put(FIELD_SHOW_CASH,           body.getShowCash());
		sources.put(FIELD_PAGINATION_SIZE,     body.getPaginationSize());
		sources.put(FIELD_PAGINATION_OFFSET,   body.getPaginationOffset());
		sources.put(FIELD_SHOW_OTHER_ACCOUNTS, body.getShowOtherAccounts());

		Boolean showTransfers = request.getBody().getShowTransfers();
		if (Boolean.FALSE.equals(showTransfers))
		{
			sources.put("excludeCategories", ConfigFactory.getConfig(FinancesConfig.class).getAlfTransfersCategories());
		}

		List<Integer> selectedCardIds = body.getSelectedCardId();
		if (CollectionUtils.isNotEmpty(selectedCardIds))
		{
			String ids = StringUtils.join(selectedCardIds.toArray(new Integer[selectedCardIds.size()]), ";");
			sources.put("selectedCardIds", ids);
		}

		sources.put(DatePeriodFilter.TYPE_PERIOD, DatePeriodFilter.TYPE_PERIOD_PERIOD);
		if (body.getFrom() != null)
		{
			sources.put(DatePeriodFilter.FROM_DATE, body.getFrom().getTime());
		}

		if (body.getTo() != null)
		{
			sources.put(DatePeriodFilter.TO_DATE, DateHelper.endOfDay(body.getTo()).getTime());
		}

		return new MapValuesSource(sources);
	}

	protected AlfHistoryResponse doMakeResponse(Map<String, Object> sources) throws Exception
	{
		AlfHistoryResponse response = new AlfHistoryResponse();
		int paginationOffset = (Integer) sources.get(FIELD_PAGINATION_OFFSET);
		Integer paginationSize = (Integer) sources.get(FIELD_PAGINATION_SIZE);

		List<CardOperationDescription> cardOperations = null;
		int maxResults = (paginationSize == null || paginationSize == 0) ? Integer.MAX_VALUE : paginationSize + 1;
		if (sources.get(DatePeriodFilter.FROM_DATE) == null && sources.get(DatePeriodFilter.TO_DATE) == null)
		{
			Object categoryId = sources.get("categoryId");
			if (categoryId == null)
				throw new BusinessException("не задан идентификатор категории");
			EditDeletedCategoryAbstractOperation operation = createOperation(EditDeletedCategoryAbstractOperation.class);
			operation.initialize((Long) categoryId);
			cardOperations = operation.getCardOperationsDescription(maxResults, paginationOffset);
		}
		else
		{
			GetListOfOperationsOperation operation = createFinanceOperation(GetListOfOperationsOperation.class);
			cardOperations = operation.getCardOperationsToCategories(sources, maxResults, paginationOffset, true);
		}

		if (CollectionUtils.isNotEmpty(cardOperations))
		{
			List<AlfOperation> alfOperations = new ArrayList<AlfOperation>();
			for (CardOperationDescription cardOperation : cardOperations.subList(0, (paginationSize == null || paginationSize == 0 || paginationSize > cardOperations.size()) ? cardOperations.size() : paginationSize))
			{
				AlfOperation alfOperation = new AlfOperation();
				alfOperation.setId(cardOperation.getId());
				alfOperation.setDate(cardOperation.getDateAsCalendar());
				alfOperation.setComment(cardOperation.getTitle());

				if(OperationType.OTHER.equals(cardOperation.getOperationType()))
					alfOperation.setCardNumber("Наличные");
				else
					alfOperation.setCardNumber(MaskUtil.getCutCardNumber(cardOperation.getCard().getNumber()));
				alfOperation.setCardAmount(new Money(cardOperation.getCardAmount()));
				alfOperation.setNationalAmount(new Money(cardOperation.getNationalAmount()));

				List<Category> categories = new ArrayList<Category>();
				for (CardOperationCategory category : cardOperation.getAvailableCategories())
				{
					categories.add(new Category(category.getId(), category.getName()));
				}

				alfOperation.setCategories(categories);
				alfOperation.setCategoryId(cardOperation.getCategoryId());
				alfOperation.setCategoryName(cardOperation.getCategory().getName());
				if (NumericUtil.isNotEmpty(cardOperation.getBusinessDocumentId()))
					alfOperation.setDetailsLink(LinkUtils.createRedirectUrl(VIEW_PAYMENT_URL, null, cardOperation.getBusinessDocumentId()));

				alfOperations.add(alfOperation);
			}

			response.setNumberOfOperations(paginationOffset + cardOperations.size());
			response.setOperations(alfOperations);
		}

		return response;
	}

	private static class AlfHistoryForm
	{
		public  static final Form FORM       = createForm();
		private static final long MAX_PERIOD = 366 * 24 * 60 * 60 * 1000L;

		private static Form createForm()
		{
			List<Field> fields = new ArrayList<Field>();

			DateInYearBeforeCurrentDateValidator dateFieldValidator = new DateInYearBeforeCurrentDateValidator();
			dateFieldValidator.setMessage("Вы можете просмотреть движение средств только за последний год");

			Expression periodDatesExpression = new RhinoExpression("form." + DatePeriodFilter.TYPE_PERIOD + " == '" + DatePeriodFilter.TYPE_PERIOD_PERIOD + "' ");
			DateParser dataParser = new DateParser();

			DateFieldValidator dataValidator = new DateFieldValidator();
			dataValidator.setEnabledExpression(periodDatesExpression);
			dataValidator.setMessage("Введите дату в формате ДД.ММ.ГГГГ");

			RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
			requiredValidator.setEnabledExpression(periodDatesExpression);
			// Период
			FieldBuilder fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("Тип периода");
			fieldBuilder.setName(DatePeriodFilter.TYPE_PERIOD);
			fieldBuilder.setType("string");
			fieldBuilder.addValidators(
					new RequiredFieldValidator(),
					new ChooseValueValidator(ListUtil.fromArray(new String[]{DatePeriodFilter.TYPE_PERIOD_MONTH, DatePeriodFilter.TYPE_PERIOD_PERIOD}))
			);
			fields.add(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(DatePeriodFilter.FROM_DATE);
			fieldBuilder.setDescription("Дата с");
			fieldBuilder.setType(DateType.INSTANCE.getName());
			fieldBuilder.setParser(dataParser);
			fieldBuilder.clearValidators();
			fieldBuilder.addValidators(dataValidator, dateFieldValidator);
			fields.add(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(DatePeriodFilter.TO_DATE);
			fieldBuilder.setDescription("Дата по");
			fieldBuilder.setType(DateType.INSTANCE.getName());
			fieldBuilder.setParser(dataParser);
			fieldBuilder.clearValidators();
			fieldBuilder.addValidators(dataValidator, dateFieldValidator);
			fields.add(fieldBuilder.build());

			// показать операции совещённые по доп картам к чужим счетам
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(FIELD_SHOW_OTHER_ACCOUNTS);
			fieldBuilder.setDescription("показать операции совещённые по дополнительным картам к чужим счетам");
			fieldBuilder.setType(BooleanType.INSTANCE.getName());
			fieldBuilder.clearValidators();
			fieldBuilder.addValidators(requiredValidator);
			fields.add(fieldBuilder.build());

			// включать операции с наличными
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(FIELD_SHOW_CASH);
			fieldBuilder.setDescription("показать операции совершенные за наличные");
			fieldBuilder.setType(BooleanType.INSTANCE.getName());
			fieldBuilder.clearValidators();
			fieldBuilder.addValidators(requiredValidator);
			fields.add(fieldBuilder.build());

			// доход / расход
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("Приходная операция");
			fieldBuilder.setName(FIELD_INCOME);
			fieldBuilder.setType(BooleanType.INSTANCE.getName());
			fieldBuilder.addValidators(requiredValidator);
			fields.add(fieldBuilder.build());

			FormBuilder formBuilder = new FormBuilder();
			formBuilder.setFields(fields);

			CompareValidator compareDateValidator = new CompareValidator();
			compareDateValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
			compareDateValidator.setBinding(CompareValidator.FIELD_O1, DatePeriodFilter.FROM_DATE);
			compareDateValidator.setBinding(CompareValidator.FIELD_O2, DatePeriodFilter.TO_DATE);
			compareDateValidator.setMessage("Конечная дата должна быть больше начальной");

			DatePeriodMultiFieldValidator dateYearMultiFieldValidator = new DatePeriodMultiFieldValidator();
			dateYearMultiFieldValidator.setMaxTimeSpan(MAX_PERIOD);
			dateYearMultiFieldValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_FROM, DatePeriodFilter.FROM_DATE);
			dateYearMultiFieldValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_TO, DatePeriodFilter.TO_DATE);
			dateYearMultiFieldValidator.setMessage("Пожалуйста, укажите период не более 1 года");

			formBuilder.setFormValidators(compareDateValidator, dateYearMultiFieldValidator);
			return formBuilder.build();
		}
	}
}

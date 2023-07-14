package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.alf;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.MapErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategoryGraphAbstract;
import com.rssl.phizic.business.tree.TreeElement;
import com.rssl.phizic.business.tree.TreeLeaf;
import com.rssl.phizic.business.tree.TreeNode;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.finances.FinancesConfig;
import com.rssl.phizic.operations.finances.CategoriesGraphOperation;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesStatus;
import com.rssl.phizic.utils.BooleanHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.component.DatePeriodFilter;
import com.rssl.phizic.web.component.PeriodFilter;
import com.rssl.phizic.web.util.MoneyFunctions;
import com.rssl.phizic.web.webApi.exceptions.FormValidationException;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.CurrencyTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MoneyTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.ALFCategoryFilterRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.ALFCategoryFilterRequestBody;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.ALFCategoryFilterResponse;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.BudgetTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.CategoryFilterTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.TreeNodeTag;
import org.apache.commons.lang.StringEscapeUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Зполняет ответ на запрос структуры расходов по категориям
 * @author Jatsky
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */

public class ALFCategoryFilterResponseConstructor extends ALFBaseResponseConstructor<ALFCategoryFilterRequest, ALFCategoryFilterResponse>
{
	public static final char DECIMAL_SEPARATOR = '.';

	@Override protected ALFCategoryFilterResponse makeResponse(ALFCategoryFilterRequest request) throws Exception
	{
		ALFCategoryFilterResponse response = new ALFCategoryFilterResponse();
		CategoriesGraphOperation operation = createOperation(CategoriesGraphOperation.class);
		operation.initialize(request.getBody().getSelectedId());

		fillALFStatus(response, operation);
		if (operation.getStatus() != FinancesStatus.allOk)
			return response;

		MapErrorCollector mapErrorCollector = new MapErrorCollector();
		FormProcessor<Map<String, String>, MapErrorCollector> processor = new FormProcessor<Map<String, String>, MapErrorCollector>(getMapValueSource(request), ALFCategoryFilterRequestBody.FILTER_FORM, mapErrorCollector, DefaultValidationStrategy.getInstance());
		Map<String, Object> filterParams;
		if (!processor.process())
		{
			throw new FormValidationException(processor.getErrors());
		}
		filterParams = processor.getResult();
		PeriodFilter dateParams = getDefaultPeriodFilter(filterParams);
		filterParams = dateParams.normalize().getParameters();

		FinanceFilterData filterData = new FinanceFilterData();
		filterData.setFromDate(DateHelper.toCalendar((Date) filterParams.get(DatePeriodFilter.FROM_DATE)));
		filterData.setToDate(DateHelper.toCalendar((Date) filterParams.get(DatePeriodFilter.TO_DATE)));
		Object showCash = filterParams.get("showCash");
		filterData.setCash(showCash instanceof Boolean ? (Boolean) showCash : Boolean.parseBoolean((String) showCash));
		filterData.setIncome(false);
		Boolean showTransfers = request.getBody().getShowTransfers();
		filterData.setTransfer(Boolean.TRUE.equals(showTransfers));
		Object showCashPayments = filterParams.get("showCashPayments");
		filterData.setShowCashPayments(showCashPayments != null ? BooleanHelper.asBoolean(showCashPayments) : null);
		String[] excludeCategories = Boolean.FALSE.equals(showTransfers) ? ConfigFactory.getConfig(FinancesConfig.class).getAlfTransfersCategories() : null;
		List<CardOperationCategoryGraphAbstract> outcomeOperations = operation.getCategoriesGraphData(filterData, operation.getRoot().getSelectedIds(), excludeCategories);

		response.setCardFilter(buildTreeNodeTag(operation.getRoot().getList().get(0)));

		response.setCategories(buildCategoryFilterTag(outcomeOperations, filterParams.get("showCents") == null ? true : Boolean.parseBoolean((String) filterParams.get("showCents"))));
		return response;
	}

	private List<CategoryFilterTag> buildCategoryFilterTag(List<CardOperationCategoryGraphAbstract> outcomeOperations, boolean showCents)
	{
		List<CategoryFilterTag> categories = new ArrayList<CategoryFilterTag>();
		for (CardOperationCategoryGraphAbstract outcomeOperation : outcomeOperations)
		{
			CategoryFilterTag categoryFilterTag = new CategoryFilterTag();
			categoryFilterTag.setId(outcomeOperation.getId());
			categoryFilterTag.setName(outcomeOperation.getName());
			CurrencyTag currencyTag = new CurrencyTag("RUB", MoneyFunctions.getCurrencySign("RUB"));
			String nationalSum = getFormattedSum(outcomeOperation.getCategorySum(), showCents);
			categoryFilterTag.setNationalSum(new MoneyTag(nationalSum, currencyTag));
			if (outcomeOperation.getBudgetSum() != null && (!outcomeOperation.getAvgBudget() || outcomeOperation.getBudgetSum() != 0))
			{
				BudgetTag budgetTag = new BudgetTag();
				String budgetSum = getFormattedSum(outcomeOperation.getBudgetSum(), showCents);
				budgetTag.setBudgetSum(new MoneyTag(budgetSum, currencyTag));
				budgetTag.setAvg(outcomeOperation.getAvgBudget());
				categoryFilterTag.setBudget(budgetTag);
			}
			categories.add(categoryFilterTag);
		}
		return categories.isEmpty() ? null : categories;
	}

	protected MapValuesSource getMapValueSource(ALFCategoryFilterRequest request)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		ALFCategoryFilterRequestBody alfCategoryFilterRequestBody = request.getBody();
		map.put("from", alfCategoryFilterRequestBody.getFrom());
		map.put("to", alfCategoryFilterRequestBody.getTo());
		map.put("showCash", alfCategoryFilterRequestBody.getShowCash());
		map.put("showCashPayments", alfCategoryFilterRequestBody.getShowCashPayments());
		map.put("selectedId", alfCategoryFilterRequestBody.getSelectedId());
		map.put("showTransfers", alfCategoryFilterRequestBody.getShowTransfers());
		map.put("showCents", alfCategoryFilterRequestBody.isShowCents());
		return new MapValuesSource(map);
	}

	private PeriodFilter getDefaultPeriodFilter(Map<String, Object> filterParams)
	{
		filterParams.put(DatePeriodFilter.FROM_DATE, filterParams.remove("from"));
		filterParams.put(DatePeriodFilter.TO_DATE, filterParams.remove("to"));
		filterParams.put(DatePeriodFilter.TYPE_PERIOD, DatePeriodFilter.TYPE_PERIOD_PERIOD);
		return new DatePeriodFilter(filterParams);
	}

	private TreeNodeTag buildTreeNodeTag(TreeElement element)
	{
		TreeNodeTag treeNodeTag = new TreeNodeTag();
		treeNodeTag.setLabel(StringEscapeUtils.unescapeHtml(element.getName()));
		if (element.isLeaf())
		{
			treeNodeTag.setValue(((TreeLeaf) element).getId().toString());
		}
		else
		{
			TreeNode treeNode = (TreeNode) element;
			treeNodeTag.setValue(treeNode.getId());
			if (!treeNode.getList().isEmpty())
			{
				List<TreeNodeTag> treeNodeTagList = new ArrayList<TreeNodeTag>();
				for (TreeElement treeElement : treeNode.getList())
				{
					treeNodeTagList.add(buildTreeNodeTag(treeElement));
				}
				treeNodeTag.setNodeList(treeNodeTagList);
			}
		}
		return treeNodeTag;
	}

	private String getFormattedSum(final Double sum, boolean showCent)
	{
		if (showCent)
		{
			return MoneyFunctions.formatAmount(new BigDecimal(sum), 2, DECIMAL_SEPARATOR, false);
		}
		else
		{
			return MoneyFunctions.formatAmount(new BigDecimal(sum), 0, DECIMAL_SEPARATOR, false);
		}
	}
}

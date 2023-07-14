package com.rssl.phizic.web.ext.sbrf.deposits;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.deposits.DepositHtmlConverter;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.account.DepositGroupOrderConfig;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.deposits.EditDepositDetailsOperation;
import com.rssl.phizic.operations.deposits.GetDepositProductsListBankOperation;
import com.rssl.phizic.operations.deposits.GetDepositProductsListOperation;
import com.rssl.phizic.operations.deposits.GetDepositProductsListOperationBase;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.StaticResolver;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.deposits.ListDepositProductsBankAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

/**
 * @author filimonova
 * @ created 15.03.2011
 * @ $Author$
 * @ $Revision$
 *
 * Список депозитных продуктов
 */
public class ListDepositProductsAction extends ListDepositProductsBankAction
{
	protected static final String FORWARD_EMPTY = "Empty";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("button.allowAll", "allowAll");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		if (checkAccess(GetDepositProductsListBankOperation.class))
		{
			return createOperation(GetDepositProductsListBankOperation.class);
		}
		else
		{
			((ListDepositProductsForm) frm).setNoDepositListAccess(true);
			return null;
		}
	}

	@Override
	protected void doStart(ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		if (((ListDepositProductsForm) frm).isNoDepositListAccess())
		{
			return;
		}

		super.doStart(operation, frm);
	}

	@Override
	protected ActionForward createActionForward(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		if (((ListDepositProductsForm) frm).isNoDepositListAccess())
		{
			return getCurrentMapping().findForward(FORWARD_EMPTY);
		}

		return super.createActionForward(frm);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListDepositProductsForm.FILTER_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws BusinessException
	{
		frm.setFilters(filterParams);
		updateFormAdditionalData(filterParams, frm, operation);
	}

	protected void forwardFilterFail(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		ListDepositProductsForm frm = (ListDepositProductsForm) form;
		frm.setData(Collections.emptyList());
		frm.setListHtml("");
	}

	protected void updateFormAdditionalData(Map<String, Object> filterParams, ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		GetDepositProductsListOperationBase op = (GetDepositProductsListOperationBase) operation;
		ListDepositProductsForm frm = (ListDepositProductsForm) form;

		String tariffToFind = StringHelper.getEmptyIfNull(filterParams.get("tariff"));
		String group = StringHelper.getEmptyIfNull(filterParams.get("group"));
		String name = StringHelper.getEmptyIfNull(filterParams.get("name"));

		DepositConfig depositConfig = ConfigFactory.getConfig(DepositConfig.class);
		if (depositConfig.isUseCasNsiDictionaries())
		{
			frm.setDepositProducts(getDepositProductEntities(tariffToFind, group, name));
		}
		else
		{
			Source source = op.getListSource(false, null);

			Source templateSource = op.getTemplateSource();
			TransformerFactory fact = TransformerFactory.newInstance();
			fact.setURIResolver(new StaticResolver());
			Templates templates = null;
			try
			{
				templates = fact.newTemplates(templateSource);
			}
			catch (TransformerConfigurationException e)
			{
				throw new BusinessException(e);
			}

			XmlConverter converter = new DepositHtmlConverter(templates);

			// Т.к. продуктов может не быть - не падаем.
			if (source != null)
			{
				converter.setData(source);
				DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
				converter.setParameter("curDate", df.format((new GregorianCalendar()).getTime()));
				converter.setParameter("admin", true);
				converter.setParameter("isPension", false);
				converter.setParameter("tariff", tariffToFind);
				converter.setParameter("group", group);
				converter.setParameter("sortList", (Serializable) ConfigFactory.getConfig(DepositGroupOrderConfig.class).getDepositGroupOrder());
				converter.setParameter("name", name);
				frm.setListHtml(converter.convert().toString());
			}
		}
	}

	/**
	 * Получить вкладные продукты в зависимости от значения тарифного плана в фильтре.
	 * Если ТП не указан, то получить для всех ТП, которые есть в справочнике
	 * @param tariffCode код ТП
	 * @param group код группы (или его часть, из фильтра)
	 * @param name полное или частичное название вкладного продукта (из фильтра)
	 * @return
	 * @throws BusinessException
	 */
	private Map<String, List<DepositProductEntity>> getDepositProductEntities(String tariffCode, String group, String name) throws BusinessException
	{
		GetDepositProductsListOperation op = createOperation(GetDepositProductsListOperation.class, "DepositsManagement");
		Map<String, List<DepositProductEntity>> depositEntitiesMap = new HashMap<String, List<DepositProductEntity>>();

		if (StringHelper.isNotEmpty(tariffCode))
		{
			List<DepositProductEntity> entities =  filterDeposits(op, Long.valueOf(tariffCode), group, name);
			depositEntitiesMap.put(tariffCode, entities);
		}
		else
		{
			List<Long> tariffPlans = TariffPlanHelper.getDictionaryTariffCodes();
			for (Long tariffPlanCode : tariffPlans)
			{
				List<DepositProductEntity> entities =  filterDeposits(op, tariffPlanCode, group, name);
				depositEntitiesMap.put(tariffPlanCode.toString(), entities);
			}
		}
		return depositEntitiesMap;
	}

	// todo вынести фильтрацию. Исполнитель Егорова А.В.
	private List<DepositProductEntity> filterDeposits(GetDepositProductsListOperation op, Long tariffPlanCode, String group, String name) throws BusinessException
	{
		op.initialize(false, tariffPlanCode);
		List<DepositProductEntity> entities = op.getDepositEntities(null, false);

		List<DepositProductEntity> filteredEntities = new ArrayList<DepositProductEntity>(entities);

		if (StringHelper.isNotEmpty(name))
		{
			String preparedName = name.toUpperCase();
			for (DepositProductEntity entity : entities)
			{
				if (!entity.getGroupName().toUpperCase().contains(preparedName))
					filteredEntities.remove(entity);
			}
		}
		if (StringHelper.isNotEmpty(group))
		{
			for (DepositProductEntity entity : entities)
			{
				if (!entity.getGroupCode().toString().contains(group))
					filteredEntities.remove(entity);
			}
		}
		return filteredEntities;
	}

	/**
	 * По умолчанию должны отображаться подвиды вкладов с тарифным планом 0.
	 * @param form
	 * @param operation
	 * @return
	 */
	protected Map<String, Object> getDefaultFilterParameters(ListFormBase form, ListEntitiesOperation operation)
	{
		Map<String, Object> filterParams = new HashMap<String, Object>();
		filterParams.put("tariff", 0);
		return filterParams;
	}

	public ActionForward allowAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditDepositDetailsOperation operation = createOperation(EditDepositDetailsOperation.class);
		operation.allowAll();
		return start(mapping, form, request, response);
	}
}

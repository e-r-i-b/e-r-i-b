package com.rssl.phizic.web.dictionaries.facilitator;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.einvoicing.FacilitatorProvider;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.dictionaries.facilitator.ListFacilitatorsOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Экшен для поиска по справочнику фасилитаторов
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ListFacilitatorsAction extends SaveFilterParameterAction
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListFacilitatorsOperation.class, "FacilitatorsDictionaryManagement");
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListFacilitatorsForm.FILTER_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws DataAccessException, BusinessException, BusinessLogicException
	{
		ListFacilitatorsForm form = (ListFacilitatorsForm)frm;
		ListFacilitatorsOperation op = (ListFacilitatorsOperation) operation;

		if (filterParams.size() == 0 || (StringHelper.isEmpty((String)filterParams.get("name")) && StringHelper.isEmpty((String)filterParams.get("inn"))))
		{
			frm.setFilters(filterParams);
			return;
		}

		if (form.isSearchByFacilitator())
		{
			Query query = op.createQuery(getQueryName(frm));
			fillQuery(query, filterParams);
			query.setFirstResult(form.getPaginationOffset());
			query.setMaxResults(form.getPaginationSize() + 1);
			frm.setData(query.executeList()); // результат: {id фасилитатора, название фасилитатора, инн фасилитатора}
		}
		else
		{
			List<FacilitatorProvider> providerList = op.getEndpointProviderList((String)filterParams.get("name"), (String)filterParams.get("inn"), form.getPaginationOffset(), form.getPaginationSize() + 1);
			if (CollectionUtils.isNotEmpty(providerList))
			{
				Map<String,Pair<String,String>> facilitators = new HashMap<String, Pair<String, String>>();
				List<Object[]> tableData = new ArrayList<Object[]>();

				for (FacilitatorProvider provider : providerList)
				{
					String facilitatorCode = provider.getFacilitatorCode();
					if (!facilitators.containsKey(facilitatorCode))
					{
						Object[] idAndName = op.getFacilitatorIdAndNameByCode(facilitatorCode);
						if (idAndName != null)
							facilitators.put(facilitatorCode, new Pair<String, String>((String)idAndName[0], (String)idAndName[1]));
					}

					//{id фасилитатора, название КПУ, инн КПУ, id КПУ, код фасилитатора}
					tableData.add(new Object[]{facilitators.get(facilitatorCode).getFirst(), provider.getName(), provider.getInn(), provider.getId(), facilitators.get(facilitatorCode).getSecond()});
				}
				if (CollectionUtils.isNotEmpty(tableData))
					frm.setData(tableData);
			}
		}
		frm.setFilters(filterParams);
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("name", filterParams.get("name"));
		query.setParameter("inn", filterParams.get("inn"));
	}
}

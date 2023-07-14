package com.rssl.phizic.web.creditcards.products;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.creditcards.conditions.CreditCardCondition;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.creditcards.products.EditCreditCardProductOperation;
import com.rssl.phizic.operations.creditcards.products.ListCreditCardProductOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Balovtsev
 * @since 20.05.2015.
 */
public class ChannelCreditCardProductAction extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("button.saveChannel", "saveChannel");
		return map;
	}

	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListCreditCardProductOperation.class);
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListCreditCardProductForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("product",   filterParams.get("product"));
		query.setParameter("publicity", StringHelper.getNullIfEmpty((String) filterParams.get("publicity")));
	}

	public ActionForward saveChannel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditCreditCardProductOperation operation = createOperation(EditCreditCardProductOperation.class);
		Long selectedId = ((ListFormBase) form).getSelectedId();
		operation.initialize(selectedId);

		ListFormBase frm = (ListFormBase) form;
		CreditCardProduct product = operation.getEntity();
		String channelGuestLead = StringHelper.getEmptyIfNull((frm.getField(ListCreditCardProductForm.CHANNEL_GUEST_LEAD + selectedId)));
		String channelCommonLead = StringHelper.getEmptyIfNull((frm.getField(ListCreditCardProductForm.CHANNEL_COMMON_LEAD + selectedId)));
		String channelGuestPreapproved= StringHelper.getEmptyIfNull((frm.getField(ListCreditCardProductForm.CHANNEL_GUEST_PREAPPROVED + selectedId)));
		String channelCommonPreapproved = StringHelper.getEmptyIfNull((frm.getField(ListCreditCardProductForm.CHANNEL_COMMON_PREAPPROVED + selectedId)));
		product.setGuestLead ((BooleanUtils.toBoolean(channelGuestLead)));
		product.setCommonLead((BooleanUtils.toBoolean(channelCommonLead)));
		product.setGuestPreapproved ((BooleanUtils.toBoolean(channelGuestPreapproved)));
		product.setUseForPreapprovedOffers((BooleanUtils.toBoolean(channelCommonPreapproved)));
		operation.save();

		return filter(mapping, form, request, response);
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		super.doFilter(filterParams, operation, frm);

		Iterator<CreditCardProduct> products = ((ListCreditCardProductForm) frm).getData().listIterator();

		while (products.hasNext())
		{
			List<CreditCardCondition> conditions = products.next().getConditions();

			CollectionUtils.filter(conditions, new Predicate()
			{
				public boolean evaluate(Object object)
				{
					return ((CreditCardCondition) object).getCurrency().getCode().equals("RUB");
				}
			});

			if (conditions.isEmpty())
			{
				products.remove();
			}
		}
	}
}

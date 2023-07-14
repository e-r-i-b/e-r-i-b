package com.rssl.phizic.web.client.contacts;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.contacts.ListMobileContactOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * ѕросмотр списка мобильных контактов
 * @author Dorzhinov
 * @ created 28.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListMobileContactAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListMobileContactOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListMobileContactForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("loginId", PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId());
		query.setParameter("name", filterParams.get("name"));
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		Query query = operation.createQuery(getQueryName(frm));
		fillQuery(query, filterParams);
		List list = query.executeList();
		Collections.sort(list, new Comparator()
		{
			public int compare(Object o1, Object o2)
			{
				if (!(o1 instanceof Contact))
					return -1;
				if (!(o2 instanceof Contact))
					return 1;

				Contact c1 = (Contact) o1;
				Contact c2 = (Contact) o2;

				return c1.getFullName().compareTo(c2.getFullName());
			}
		});
		frm.setData(list);
		frm.setFilters(filterParams);
		updateFormAdditionalData(frm, operation);
	}
}

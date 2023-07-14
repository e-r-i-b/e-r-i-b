package com.rssl.phizic.operations.link;

import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;
import com.rssl.phizic.business.resources.external.ShowOperationLinkFilter;
import com.rssl.phizic.business.resources.external.ShowInMainLinkFilter;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/** Операция для получения списка линков
 * @author akrenev
 * @ created 20.09.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract class GetExternalResourceLinkOperation<R extends Restriction> extends OperationBase<R>
{
	private boolean isUseStoredResource;
	
	/**
	 * @param resource список фильтруемых линков
	 * @return  список линков с признаком "отображать на главной"
	 */
	public <L extends EditableExternalResourceLink> List<L> getShowInMainLinks(List<L> resource)
	{
		List<L> filteredResource = new ArrayList<L>(resource);
		ShowInMainLinkFilter filter = new ShowInMainLinkFilter();
		CollectionUtils.filter(filteredResource, filter);
		return filteredResource;
	}

	/**
	 * @param resource список фильтруемых линков
	 * @return  список линков с признаком "отображать операции"
	 */
	public <L extends EditableExternalResourceLink> List<L> getShowOperationLinks(List<L> resource)
	{
		List<L> filteredResource = new ArrayList<L>(resource);
		ShowOperationLinkFilter filter = new ShowOperationLinkFilter();
		CollectionUtils.filter(filteredResource, filter);
		return filteredResource;
	}

	protected void setUseStoredResource(boolean useStoredResource)
	{
		isUseStoredResource = useStoredResource;
	}

	public boolean isUseStoredResource()
	{
		return isUseStoredResource;
	}

	public PersonData getPersonData()
	{
		return PersonContext.getPersonDataProvider().getPersonData();
	}
}

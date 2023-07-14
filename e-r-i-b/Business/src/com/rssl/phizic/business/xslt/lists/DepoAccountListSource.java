package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.DepoAccountFilter;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.business.resources.external.NullDepoAccountFilter;
import com.rssl.phizic.business.xslt.lists.builder.Entity;
import com.rssl.phizic.business.xslt.lists.builder.EntityList;
import com.rssl.phizic.business.xslt.lists.builder.Field;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.utils.MockHelper;

import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 03.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountListSource extends CachedEntityListSourceBase
{
	private DepoAccountFilter filter;

	public DepoAccountListSource(EntityListDefinition definition)
	{
		this(definition, new NullDepoAccountFilter() );
	}

	public DepoAccountListSource(EntityListDefinition definition, DepoAccountFilter depoAccountFilter)
	{
		super(definition);
		this.filter = depoAccountFilter;
	}

	public DepoAccountListSource(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition);
		try
		{
			String filterClassName = (String) parameters.get(FILTER_CLASS_NAME_PARAMETER);
			Class filterClass = Thread.currentThread().getContextClassLoader().loadClass(filterClassName);

			filter = (DepoAccountFilter) filterClass.newInstance();
		}
		catch (ClassNotFoundException e)
		{
			throw new BusinessException(e);
		}
		catch (InstantiationException e)
		{
			throw new BusinessException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	public Pair<String, List<Object>> buildEntityList(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		EntityList builder = new EntityList();

		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		if (provider == null){
			return convertToReturnValue(builder.toString());
		}

		List<DepoAccountLink> depoAccountLinks = provider.getPersonData().getDepoAccounts(filter);

		for(DepoAccountLink link : depoAccountLinks)
		{
			appendDepoAccount(builder, link);
		}
	    return convertToReturnValue(builder.toString());
	}

	protected void appendDepoAccount(EntityList builder, DepoAccountLink link) throws BusinessException
	{
		DepoAccount depoAccount = link.getDepoAccount();
		//если для линка не смогли получить счет депо, то не добавляем данный элемент в лист
		if (MockHelper.isMockObject(depoAccount))
			return;

		String key = depoAccount.getAccountNumber();
		Entity entity = new Entity(key, null);
		entity.addField(new Field("depoId",link.getId().toString()));
		entity.addField(new Field("externalId",link.getExternalId()));
		entity.addField(new Field("accountNumber",key));
		entity.addField(new Field("depoAccountName",link.getName()));

		Client depoAccountOwner = link.getOwner();
		if(depoAccountOwner != null)
		{
			String formattedName = PersonHelper.getFormattedPersonName(depoAccountOwner.getFirstName(), depoAccountOwner.getSurName(), depoAccountOwner.getPatrName());
			entity.addField(new Field("owner",formattedName));
		}
		builder.addEntity(entity);
	}

}

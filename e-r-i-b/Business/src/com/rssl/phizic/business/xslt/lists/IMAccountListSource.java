package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.IMAccountFilter;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.NullIMAccountFilter;
import com.rssl.phizic.business.resources.external.comparator.IMACurrencyComparator;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MockHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Формирует список ОМС
 * @author Pankin
 * @ created 28.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class IMAccountListSource extends CachedEntityListSourceBase
{
	private static final String FILTER_CLASS_NAME_PARAMETER = "filter";

	private IMAccountFilter imAccountFilter;

    public IMAccountListSource(EntityListDefinition definition)
    {
        this(definition, new NullIMAccountFilter() );
    }

    public IMAccountListSource(EntityListDefinition definition, IMAccountFilter imaccountFilter)
    {
	    super(definition);
        this.imAccountFilter = imaccountFilter;
    }

    public IMAccountListSource(EntityListDefinition definition, Map parameters) throws BusinessException
    {
	    super(definition);
        try
        {
            String filterClassName = (String) parameters.get(FILTER_CLASS_NAME_PARAMETER);
            Class  filterClass     = Thread.currentThread().getContextClassLoader().loadClass(filterClassName);

            imAccountFilter = (IMAccountFilter) filterClass.newInstance();
        }
        catch (ClassNotFoundException e)
        {
            throw new BusinessException(e);
        }
        catch (IllegalAccessException e)
        {
            throw new BusinessException(e);
        }
        catch (InstantiationException e)
        {
            throw new BusinessException(e);
        }
    }

	public Pair<String, List<Object>> buildEntityList(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		List<IMAccountLink> imAccountLinks = getIMAccountsList();

		Collections.sort(imAccountLinks, new IMACurrencyComparator());

		EntityListBuilder builder = new EntityListBuilder();
		builder.openEntityListTag();

		for(IMAccountLink imAccountLink: imAccountLinks)
		{
			IMAccount imAccount = imAccountLink.getImAccount();
			if (!MockHelper.isMockObject(imAccount) && skipStoredResource(imAccount))
			{
				String key = imAccount.getNumber();

				builder.openEntityTag(key);
				builder.appentField("currencyCode", imAccount.getCurrency().getCode());
				builder.appentField("linkId", imAccountLink.getId().toString());
				builder.appentField("code", imAccountLink.getCode());
				builder.appentField("name", imAccountLink.getName());
				builder.appentField("number", imAccountLink.getNumber());
				builder.appentField("openDate", String.format("%1$te.%1$tm.%1$tY", DateHelper.toDate(imAccount.getOpenDate())));
				builder.appentField("closeDate", String.format("%1$te.%1$tm.%1$tY", DateHelper.toDate(imAccount.getClosingDate())));
				builder.appentField("currency", imAccount.getCurrency().getCode());
				builder.appentField("agreementNumber", imAccount.getAgreementNumber());
				String ammountStr = imAccount.getBalance() == null ? null : String.valueOf(imAccount.getBalance().getDecimal());
				builder.appentField("amountDecimal", ammountStr);

				builder.closeEntityTag();
			}

		}

		builder.closeEntityListTag();
		return convertToReturnValue(builder.toString());
	}

	private List<IMAccountLink> getIMAccountsList() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		return personData == null ? new ArrayList<IMAccountLink>() : personData.getIMAccountLinks(imAccountFilter);
    }

	protected boolean skipStoredResource(IMAccount imAccount)
	{
		return !(imAccount instanceof AbstractStoredResource);
	}
}

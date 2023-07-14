package com.rssl.phizic.operations.descriptions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.descriptions.ExtendedDescriptionData;
import com.rssl.phizic.business.descriptions.ExtendedDescriptionDataService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.StringHelper;

/**
 * ќпераци€ получени€ расширенного описани€ правил, договоров и т.д.
 * @author niculichev
 * @ created 08.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class GetExtendedDescriptionDataOperation extends OperationBase implements ViewEntityOperation<ExtendedDescriptionData>
{
	private static final ExtendedDescriptionDataService extendedDescriptionDataService = new ExtendedDescriptionDataService();

	private ExtendedDescriptionData data;

	public void initialize(String name) throws BusinessException
	{
		if(StringHelper.isEmpty(name))
			throw new IllegalArgumentException("name не может быть пустым");

		data = extendedDescriptionDataService.getDataByName(name);
		if(data == null)
			throw new BusinessException("Ќе найдены данные с именем " + name);
	}

	public ExtendedDescriptionData getEntity() throws BusinessException, BusinessLogicException
	{
		return data;
	}
}

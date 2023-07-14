package com.rssl.phizic.business.ips;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationTypeService;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.ips.CardOperationInfoService;
import com.rssl.phizic.gate.ips.CardOperationType;
import com.rssl.phizic.utils.GroupResultHelper;

import java.util.Arrays;
import java.util.List;

/**
 * @author Erkin
 * @ created 28.07.2011
 * @ $Author$
 * @ $Revision$
 */
class CardOperationInfoServiceImpl extends AbstractService implements CardOperationInfoService
{
	private static final CardOperationTypeService operationTypeService = new CardOperationTypeService();

	/**
	 * стандартный конструктор шлюзового сервиса
	 * @param factory - фабрика шлюзов
	 */
	CardOperationInfoServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public GroupResult<Long, CardOperationType> getOperationTypes(final Long... operationTypeCodes)
	{
		List<CardOperationType> types;
		try
		{
			types = operationTypeService.findByCode(Arrays.asList(operationTypeCodes));
		}
		catch (BusinessException e)
		{
			return GroupResultHelper.getOneErrorResult(operationTypeCodes, e);
		}
		
		GroupResult<Long, CardOperationType> result = new GroupResult<Long, CardOperationType>();
		for (CardOperationType type : types)
			result.putResult(type.getCode(), type);
		return result;
	}
}

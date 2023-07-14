package com.rssl.phizic.csaadmin.listeners.mail;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.listeners.generated.*;
import com.rssl.phizic.csaadmin.listeners.mail.converters.IncomeMailEntityConverter;
import com.rssl.phizic.csaadmin.listeners.mail.converters.OutcomeMailEntityConverter;
import com.rssl.phizic.csaadmin.listeners.mail.converters.RemovedMailEntityConverter;
import com.rssl.phizic.csaadmin.listeners.mail.processors.SortUnionListProcessor;
import com.rssl.phizic.gate.mail.IncomeMailListEntity;
import com.rssl.phizic.gate.mail.OutcomeMailListEntity;
import com.rssl.phizic.gate.mail.RemovedMailListEntity;

import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для получения списков писем.
 * Получает параметры писем и опрашивает блоки на получение писем с теми же параметрами.
 * Затем агрегирует полученные списки.
 */
public class MailListService
{
	private static final IncomeMailEntityConverter incomeMailConverter = new IncomeMailEntityConverter();
	private static final OutcomeMailEntityConverter outcomeMailConverter = new OutcomeMailEntityConverter();
	private static final RemovedMailEntityConverter removedMailConverter = new RemovedMailEntityConverter();
	private static final MultiNodeDataService multiNodeService = new MultiNodeDataService();


	/**
	 * @param parameters - параметры запроса
	 * @return список входящих писем для сотрудника
	 */
	public GetIncomeMailListResultType getIncomeMailList(GetIncomeMailListParametersType parameters) throws AdminException
	{
		GetIncomeMailListResultType result = new GetIncomeMailListResultType();
		String request = multiNodeService.buildListRequest(parameters, MultiNodeRequestType.INCOME_MAIL);
		Map<Long,List<IncomeMailListEntity>> multiNodeMap = multiNodeService.getMultiNodeData(request, MultiNodeRequestType.INCOME_MAIL);
		List<IncomeMailListEntity> resultList = SortUnionListProcessor.process(multiNodeMap, parameters, MultiNodeRequestType.INCOME_MAIL);
		IncomeMailListDataType[] gateResult = new IncomeMailListDataType[resultList.size()];
		for(int i=0; i < resultList.size(); i++)
			gateResult[i] = incomeMailConverter.convertToGate(resultList.get(i));
		result.setMailList(gateResult);
		return result;
	}

	/**
	 * @param parameters - параметры запроса
	 * @return список исходящих писем для сотрудника
	 */
	public GetOutcomeMailListResultType getOutcomeMailList(GetOutcomeMailListParametersType parameters) throws AdminException
	{
		GetOutcomeMailListResultType result = new GetOutcomeMailListResultType();
		String request = multiNodeService.buildListRequest(parameters, MultiNodeRequestType.OUTCOME_MAIL);
		Map<Long,List<OutcomeMailListEntity>> multiNodeMap = multiNodeService.getMultiNodeData(request, MultiNodeRequestType.OUTCOME_MAIL);
		List<OutcomeMailListEntity> resultList = SortUnionListProcessor.process(multiNodeMap, parameters, MultiNodeRequestType.OUTCOME_MAIL);
		OutcomeMailListDataType[] gateResult = new OutcomeMailListDataType[resultList.size()];
		for(int i=0; i < resultList.size(); i++)
			gateResult[i] = outcomeMailConverter.convertToGate(resultList.get(i));
		result.setMailList(gateResult);
		return result;
	}

	/**
	 * @param parameters - параметры запроса
	 * @return список удаленных писем для сотрудника
	 */
	public GetRemovedMailListResultType getRemovedMailList(GetRemovedMailListParametersType parameters) throws AdminException
	{
		GetRemovedMailListResultType result = new GetRemovedMailListResultType();
		String request = multiNodeService.buildListRequest(parameters, MultiNodeRequestType.REMOVED_MAIL);
		Map<Long,List<RemovedMailListEntity>> multiNodeMap = multiNodeService.getMultiNodeData(request, MultiNodeRequestType.REMOVED_MAIL);
		List<RemovedMailListEntity> resultList = SortUnionListProcessor.process(multiNodeMap, parameters, MultiNodeRequestType.REMOVED_MAIL);
		RemovedMailListDataType[] gateResult = new RemovedMailListDataType[resultList.size()];
		for(int i=0; i < resultList.size(); i++)
			gateResult[i] = removedMailConverter.convertToGate(resultList.get(i));
		result.setMailList(gateResult);
		return result;
	}

}

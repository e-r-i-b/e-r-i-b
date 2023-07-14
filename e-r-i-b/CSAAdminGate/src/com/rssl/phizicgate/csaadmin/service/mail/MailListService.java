package com.rssl.phizicgate.csaadmin.service.mail;

import com.rssl.phizic.dataaccess.query.OrderParameter;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mail.IncomeMailListEntity;
import com.rssl.phizic.gate.mail.OutcomeMailListEntity;
import com.rssl.phizic.gate.mail.RemovedMailListEntity;
import com.rssl.phizic.utils.xml.xstream.XStreamSerializer;
import com.rssl.phizicgate.csaadmin.service.CSAAdminServiceBase;
import com.rssl.phizicgate.csaadmin.service.generated.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 16.05.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��������� ������ ����� �� ��� �����
 */
public class MailListService extends CSAAdminServiceBase
{

	/**
	 * �����������
	 */
	public MailListService()
	{
		super(null);
	}

	/**
	 * �������� ������ �������� �����
	 * @param parameters - ��������� �������
	 * @param size - ������ �������
	 * @param offset - �������� �������
	 * @param orderParameters - ��������� ����������
	 * @return ������ �������� �����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<IncomeMailListEntity> getIncomeList(Map<String,Object> parameters, int size, int offset, List<OrderParameter> orderParameters) throws GateException, GateLogicException
	{
		RequestData data = new RequestData();
		GetIncomeMailListParametersType mailListParameters = createRequest(new GetIncomeMailListParametersType(),parameters,size,offset,orderParameters);
		data.setGetIncomeMailListRq(mailListParameters);
		ResponseData response = process(data);
		return parseIncomeList(response.getGetIncomeMailListRs().getMailList());
	}

	/**
	 * �������� ������ ������������ �����
	 * @param parameters - ��������� �������
	 * @param size - ������ �������
	 * @param offset - �������� �������
	 * @param orderParameters - ��������� ����������
	 * @return ������ ������������ �����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<OutcomeMailListEntity> getOutcomeList(Map<String,Object> parameters, int size, int offset, List<OrderParameter> orderParameters) throws GateException, GateLogicException
	{
		RequestData data = new RequestData();
		GetOutcomeMailListParametersType mailListParameters = createRequest(new GetOutcomeMailListParametersType(),parameters,size,offset,orderParameters);
		data.setGetOutcomeMailListRq(mailListParameters);
		ResponseData response = process(data);
		return parseOutcomeList(response.getGetOutcomeMailListRs().getMailList());
	}

	/**
	 * �������� ������ ��������� �����
	 * @param parameters - ��������� �������
	 * @param size - ������ �������
	 * @param offset - �������� �������
	 * @param orderParameters - ��������� ����������
	 * @return ������ ��������� �����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<RemovedMailListEntity> getRemovedList(Map<String,Object> parameters, int size, int offset, List<OrderParameter> orderParameters) throws GateException, GateLogicException
	{
		RequestData data = new RequestData();
		GetRemovedMailListParametersType mailListParameters = createRequest(new GetRemovedMailListParametersType(),parameters,size,offset,orderParameters);
		data.setGetRemovedMailListRq(mailListParameters);
		ResponseData response = process(data);
		return parseRemovedList(response.getGetRemovedMailListRs().getMailList());
	}

	private <T extends GetMultiNodeListParametersType> T createRequest(T request, Map<String,Object> parameters, int size, int offset, List<OrderParameter> orderParameters)
	{
		request.setParameters(XStreamSerializer.serialize(parameters));
		request.setMaxResults(size);
		request.setFirstResult(offset);
		request.setOrderParameters(XStreamSerializer.serialize(orderParameters));
		return request;
	}

	private List<IncomeMailListEntity> parseIncomeList(IncomeMailListDataType[] mailListData)
	{
		ArrayList<IncomeMailListEntity> result = new ArrayList<IncomeMailListEntity>(mailListData.length);
		for(IncomeMailListDataType mail: mailListData)
		{
			IncomeMailListEntity entity = new IncomeMailListEntity();
			entity.setId(mail.getId());
			entity.setNodeId(mail.getNodeId());
			entity.setNumber(mail.getNumber());
			entity.setSubject(decodeData(mail.getSubject()));
			entity.setState(mail.getState());
			entity.setStateDescription(mail.getStateDescription());
			entity.setType(mail.getType());
			entity.setTypeDescription(mail.getTypeDescription());
			entity.setCreationDate(parseCalendar(mail.getCreationDate()));
			entity.setResponseMethod(mail.getResponseMethod());
			entity.setTheme(mail.getTheme());
			entity.setSenderFIO(mail.getSenderFIO());
			entity.setSenderId(mail.getSenderId());
			entity.setTb(mail.getTb());
			entity.setArea(mail.getArea());
			entity.setEmployeeFIO(mail.getEmployeeFIO());
			entity.setEmployeeUserId(mail.getEmployeeUserId());
			result.add(entity);
		}
		return result;
	}

	private List<OutcomeMailListEntity> parseOutcomeList(OutcomeMailListDataType[] mailListData)
	{
		ArrayList<OutcomeMailListEntity> result = new ArrayList<OutcomeMailListEntity>(mailListData.length);
		for(OutcomeMailListDataType mail: mailListData)
		{
			OutcomeMailListEntity entity = new OutcomeMailListEntity();
			entity.setId(mail.getId());
			entity.setNodeId(mail.getNodeId());
			entity.setNumber(mail.getNumber());
			entity.setSubject(decodeData(mail.getSubject()));
			entity.setState(mail.getState());
			entity.setStateDescription(mail.getStateDescription());
			entity.setType(mail.getType());
			entity.setTypeDescription(mail.getTypeDescription());
			entity.setCreationDate(parseCalendar(mail.getCreationDate()));
			entity.setResponseMethod(mail.getResponseMethod());
			entity.setTheme(mail.getTheme());
			entity.setRecipientFIO(mail.getRecipientFIO());
			entity.setRecipientId(mail.getRecipientId());
			entity.setTb(mail.getTb());
			entity.setArea(mail.getArea());
			entity.setEmployeeFIO(mail.getEmployeeFIO());
			entity.setEmployeeUserId(mail.getEmployeeUserId());
			result.add(entity);
		}
		return result;
	}

	private List<RemovedMailListEntity> parseRemovedList(RemovedMailListDataType[] mailListData)
	{
		ArrayList<RemovedMailListEntity> result = new ArrayList<RemovedMailListEntity>(mailListData.length);
		for(RemovedMailListDataType mail: mailListData)
		{
			RemovedMailListEntity entity = new RemovedMailListEntity();
			entity.setId(mail.getId());
			entity.setNodeId(mail.getNodeId());
			entity.setNumber(mail.getNumber());
			entity.setSubject(decodeData(mail.getSubject()));
			entity.setState(mail.getState());
			entity.setStateDescription(mail.getStateDescription());
			entity.setType(mail.getType());
			entity.setTypeDescription(mail.getTypeDescription());
			entity.setCreationDate(parseCalendar(mail.getCreationDate()));
			entity.setResponseMethod(mail.getResponseMethod());
			entity.setTheme(mail.getTheme());
			entity.setRecipientName(mail.getRecipientName());
			entity.setDirectionDescription(mail.getDirectionDescription());
			entity.setTb(mail.getTb());
			entity.setArea(mail.getArea());
			entity.setEmployeeFIO(mail.getEmployeeFIO());
			entity.setEmployeeUserId(mail.getEmployeeUserId());
			result.add(entity);
		}
		return result;
	}

}

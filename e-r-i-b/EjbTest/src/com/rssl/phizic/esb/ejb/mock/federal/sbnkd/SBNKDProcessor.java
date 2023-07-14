package com.rssl.phizic.esb.ejb.mock.federal.sbnkd;

import com.rssl.phizic.ejbtest.service.SBNKDService;
import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * @author bogdanov
 * @ created 10.02.15
 * @ $Author$
 * @ $Revision$
 */

public class SBNKDProcessor extends MessageProcessorBase<ESBMessage>
{
	private static SBNKDService sbnkdService = new SBNKDService();
	/**
	 * ctor
	 * @param module - модуль
	 */
	public SBNKDProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected void doProcessMessage(ESBMessage xmlRequest)
	{
		Class requestClass = xmlRequest.getRequestClass();

		if (requestClass == GetPrivateClientRq.class)
		{
			processGetPrivateClient((GetPrivateClientRq) xmlRequest.getObject());
		}
		else if (requestClass == CreateCardContractRq.class)
		{
			processCreateCardContract((CreateCardContractRq) xmlRequest.getObject());
		}
		else if (requestClass == ConcludeEDBORq.class)
		{
			processConcludeEDBO((ConcludeEDBORq) xmlRequest.getObject());
		}
		else if (requestClass == IssueCardRq.class)
		{
			processIssueCard((IssueCardRq) xmlRequest.getObject());
		}
		else if (requestClass == CustAddRq.class)
		{
			processCustAdd((CustAddRq) xmlRequest.getObject());
		}
		else
		{
			throw new IllegalArgumentException("сообщение не соответствует ни одному из: getPrivateClientRq, createCardContractRq, concludeEDBORq, issueCardRq или  custAddRq");
		}
	}

	private void processCustAdd(CustAddRq custAddRq)
	{
		CustAddRs rs = new CustAddRs();
		rs.setRqUID(custAddRq.getRqUID());
		rs.setRqTm(custAddRq.getRqTm());

		if (Math.random() > 0.99) {
			rs.setStatus(new StatusType());
			rs.getStatus().setStatusCode(-1L);
			rs.getStatus().setStatusDesc("Какая-то случайная ошибка");
		}
		else
		{
			rs.setStatus(new StatusType());
			rs.getStatus().setStatusCode(0L);
		}

		try
		{
			send(custAddRq, rs, rs.getRqUID());
		}
		catch (GateException e)
		{
			System.out.println(e);
		}
	}

	private void processIssueCard(IssueCardRq issueCardRq)
	{
		IssueCardRs rs = new IssueCardRs();
		rs.setRqTm(issueCardRq.getRqTm());
		rs.setRqUID(issueCardRq.getRqUID());
		rs.setSPName(issueCardRq.getSPName().value());
		rs.setSystemId(issueCardRq.getSystemId());

		if (Math.random() > 0.99) {
			rs.setStatus(new StatusWayType());
			rs.getStatus().setStatusCode(-1L);
			rs.getStatus().setSeverity("Error");
			rs.getStatus().setStatusDesc("Какая-то случайная ошибка");
		}
		else
		{
			rs.setStatus(new StatusWayType());
			rs.getStatus().setStatusCode(0L);

			rs.setCardAcctId(new CardAcctIdType());
			rs.getCardAcctId().setCardNum("1234567890123456");
			rs.getCardAcctId().setAcctId("00000810000000000001");
			rs.getCardAcctId().setContract(new ContractType());
			rs.getCardAcctId().getContract().setContractNumber("2142623623");

			rs.setCardAcctInfo(new CardAcctInfoType());
			rs.getCardAcctInfo().setEndDtForWay("2012");
		}

		try
		{
			send(issueCardRq, rs, rs.getRqUID());
		}
		catch (GateException e)
		{
			System.out.println(e);
		}
	}

	private void processConcludeEDBO(ConcludeEDBORq concludeEDBORq)
	{
		ConcludeEDBORs rs = new ConcludeEDBORs();
		rs.setRqUID(concludeEDBORq.getRqUID());
		rs.setRqTm(concludeEDBORq.getRqTm());
		rs.setOperUID(concludeEDBORq.getOperUID());

		if (Math.random() > 0.99) {
			rs.setStatus(new StatusType());
			rs.getStatus().setStatusCode(-1L);
			rs.getStatus().setStatusDesc("Какая-то случайная ошибка");
		}
		else
		{
			rs.setStatus(new StatusType());
			rs.getStatus().setStatusCode(0L);

			rs.setSPDefField(new SPDefFieldShortType());
			rs.getSPDefField().setFieldData1(Long.parseLong(concludeEDBORq.getBankInfo().getRbTbBrchId().substring(0, 2)));
			rs.getSPDefField().setFieldNum(356364L);
		}

		try
		{
			send(concludeEDBORq, rs, rs.getRqUID());
		}
		catch (GateException e)
		{
			System.out.println(e);
		}
	}

	private void processCreateCardContract(CreateCardContractRq createCardContractRq)
	{
		CreateCardContractRs rs = new CreateCardContractRs();
		rs.setRqTm(createCardContractRq.getRqTm());
		rs.setRqUID(createCardContractRq.getRqUID());
		rs.setOperUID(createCardContractRq.getOperUID());
		rs.setSPName(createCardContractRq.getSPName().value());

		if (Math.random() > 0.99) {
			rs.setStatus(new StatusType());
			rs.getStatus().setStatusCode(-1L);
			rs.getStatus().setStatusDesc("Какая-то случайная ошибка");
		}
		else
		{
			rs.setStatus(new StatusType());
			rs.getStatus().setStatusCode(0L);

			rs.setCardContractNumber(5346747L);
		}

		try
		{
			send(createCardContractRq, rs, rs.getRqUID());
		}
		catch (GateException e)
		{
			System.out.println(e);
		}
	}

	private void processGetPrivateClient(GetPrivateClientRq getPrivateClientRq)
	{
		GetPrivateClientRs rs = new GetPrivateClientRs();
		rs.setRqUID(getPrivateClientRq.getRqUID());
		rs.setRqTm(getPrivateClientRq.getRqTm());
		rs.setOperUID(getPrivateClientRq.getOperUID());
		rs.setSPName(getPrivateClientRq.getSPName().value());

		if (Math.random() > 0.99) {
			rs.setStatus(new StatusType());
			rs.getStatus().setStatusCode(-1L);
			rs.getStatus().setStatusDesc("Какая-то случайная ошибка");
		}
		else
		{
			rs.setStatus(new StatusType());
			rs.getStatus().setStatusCode(0L);

			if (Math.random() > 0.3)
			{
				rs.setCustRec(getPrivateClientRq.getCustRec());
				rs.setVerified(true);
				TarifPlanInfoType tarifPlanInfoType = new TarifPlanInfoType();
				tarifPlanInfoType.setSegmentCode((int) (Math.random() * 6) + "");
				rs.getCustRec().setTarifPlanInfo(tarifPlanInfoType);
			}
			else
			{
				rs.setVerified(false);
			}
		}

		try
		{
			send(getPrivateClientRq, rs, rs.getRqUID());
		}
		catch (GateException e)
		{
			System.out.println(e);
		}
	}

	private String xml(Object request) throws JAXBException, UnsupportedEncodingException
	{
		StringWriter writer = new StringWriter();
		JAXBContext jaxbContext = JAXBContext.newInstance(request.getClass());
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(request, writer);
		return new String(writer.toString().getBytes("UTF-8"), "UTF-8");
	}

	private void send(Object income, Object answer, String uuid) throws GateException
	{
		try
		{
			String incomeXml = xml(income);
			String answerXml = xml(answer);

			sbnkdService.addMessage(uuid, incomeXml, answerXml);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}

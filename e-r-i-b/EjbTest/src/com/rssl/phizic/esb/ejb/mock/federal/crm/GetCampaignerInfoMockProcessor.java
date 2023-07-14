package com.rssl.phizic.esb.ejb.mock.federal.crm;

import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.federal.FederalESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

import java.math.BigInteger;
import java.util.Random;

/**
 * Заглушка: Ответ CRM с предодобренными предложениями по кредиту / кредитной карте (CRM -> ЕРИБ)
 * @author Puzikov
 * @ created 03.07.15
 * @ $Author$
 * @ $Revision$
 */

public class GetCampaignerInfoMockProcessor extends FederalESBMockProcessorBase<GetCampaignerInfoRq, GetCampaignerInfoRs>
{
	//редактировать значение, для определения режима получения предложений
	private Mode mockMode = Mode.RANDOM;

	private enum Mode
	{
		/**
		 * Ответ, что предложений для клиента нет.
		 */
		NONE,

		/**
		 * Список только предложений по кредиту.
		 */
		CREDIT,

		/**
		 * Список только предложений по карте.
		 */
		CARD,

		/**
		 * Список, в котором есть предложения и по картам и по кредитам.
		 */
		CARD_AND_CREDIT,

		/**
		 * Случайное значение из предыдущих
		 */
		RANDOM,

		/**
		 * Отсутствие ответа (высылать вручную через тестилку)
		 */
		NULL,
		;
	}

	private Mode getMode()
	{
		if (mockMode != Mode.RANDOM)
		{
			return mockMode;
		}
		else
		{
			int i = new Random().nextInt(4);
			return Mode.values()[i];
		}
	}

	/**
	 * ctor
	 * @param module модуль
	 */
	public GetCampaignerInfoMockProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected void process(ESBMessage<GetCampaignerInfoRq> xmlRequest)
	{
		Mode mode = getMode();
		if (mode == Mode.NULL)
			return;

		GetCampaignerInfoRq rq = xmlRequest.getObject();
		GetCampaignerInfoRs rs = new GetCampaignerInfoRs();

		rs.setStatus(getStatusInstance(0, "", "", ""));
		rs.setRqUID(rq.getRqUID());
		rs.setRqTm(rq.getRqTm());
		rs.setSPName(SPNameType.fromValue(rq.getSystemId()));
		rs.setSystemId(rq.getSPName().name());

		for (int i = 0; i < 2; i++)
		{
			CampaignMember campaignMember = new CampaignMember();
			rs.getCampaignMembers().add(campaignMember);
			campaignMember.setPersonInfo(rq.getCampaignMember().getPersonInfo());
			campaignMember.setCampaignMemberId("113570");
			campaignMember.setCampaignName("Компания");
			campaignMember.setCampaignId("112");
			campaignMember.setSourceCode("593");
			campaignMember.setSourceName("Кредиты и кредитные карты");
			campaignMember.setProductASName("Кредиты и кредитные карты");
			campaignMember.setPersonalText("Текст персонализированного сообщения");
			campaignMember.setClientId("902323002");

			if (mode == Mode.CARD || mode == Mode.CARD_AND_CREDIT)
			{
				for (int j = 0; j < 2; j++)
				{
					campaignMember.getInternalProducts().add(makeOffer("CreditCard"));
				}
			}
			if (mode == Mode.CREDIT || mode == Mode.CARD_AND_CREDIT)
			{
				for (int j = 0; j < 2; j++)
				{
					campaignMember.getInternalProducts().add(makeOffer("Consumer Credit"));
				}
			}
		}

		send(xmlRequest, rs, "GetCampaignerInfo");
	}

	private InternalProduct makeOffer(String offerType)
	{
		InternalProduct offer = new InternalProduct();
		offer.setProductId("string");
		offer.setProductId("string");
		offer.setProductType(offerType);
		offer.setTargetProductType("1");
		offer.setTargetProduct("2");
		offer.setTargetProductSub("87");
		offer.setPriority("7");
		offer.setExpDate("2016-10-10T12:00:00");
		offer.setCurCode("RUR");

		ProposalParameters proposalParameters = new ProposalParameters();
		offer.setProposalParameters(proposalParameters);

		TableType column1 = new TableType();
		proposalParameters.getColumns().add(column1);
		column1.setName("6 мес.");
		column1.setNumber(BigInteger.valueOf(1));

		TableType column2 = new TableType();
		proposalParameters.getColumns().add(column2);
		column2.setName("12 мес.");
		column2.setNumber(BigInteger.valueOf(2));

		TableType row1 = new TableType();
		proposalParameters.getRows().add(row1);
		row1.setName("6%");
		row1.setNumber(BigInteger.valueOf(1));
		TableType row2 = new TableType();
		proposalParameters.getRows().add(row2);
		row2.setName("7%");
		row2.setNumber(BigInteger.valueOf(2));

		//Для тетсирования матрицы предложений по TOP-UP
		TableType row3 = new TableType();
		proposalParameters.getRows().add(row3);
		row3.setName("Сумма наличными");
		row3.setNumber(BigInteger.valueOf(3));

		Elements element1 = new Elements();
		proposalParameters.getElements().add(element1);
		element1.setColumnIndex(BigInteger.valueOf(1));
		element1.setRowIndex(BigInteger.valueOf(1));
		element1.setValue("90000");

		Elements element2 = new Elements();
		proposalParameters.getElements().add(element2);
		element2.setColumnIndex(BigInteger.valueOf(1));
		element2.setRowIndex(BigInteger.valueOf(2));
		element2.setValue("120000");
		//Для тетсирования матрицы предложений по TOP-UP
		Elements element3 = new Elements();
		proposalParameters.getElements().add(element3);
		element3.setColumnIndex(BigInteger.valueOf(1));
		element3.setRowIndex(BigInteger.valueOf(3));
		element3.setValue("50000");

		Elements element4 = new Elements();
		proposalParameters.getElements().add(element4);
		element4.setColumnIndex(BigInteger.valueOf(2));
		element4.setRowIndex(BigInteger.valueOf(1));
		element4.setValue("50000");

		Elements element5 = new Elements();
		proposalParameters.getElements().add(element5);
		element5.setColumnIndex(BigInteger.valueOf(2));
		element5.setRowIndex(BigInteger.valueOf(2));
		element5.setValue("60000");
		//Для тетсирования матрицы предложений по TOP-UP
		Elements element6 = new Elements();
		proposalParameters.getElements().add(element6);
		element6.setColumnIndex(BigInteger.valueOf(2));
		element6.setRowIndex(BigInteger.valueOf(3));
		element6.setValue("60000");

		proposalParameters.setRateMin("1");
		proposalParameters.setRateMax("12");
		proposalParameters.setLimitMin("65000");
		proposalParameters.setLimitMax("1000000");
		proposalParameters.setPeriodMin("6");
		proposalParameters.setPeriodMax("360");

		return offer;
	}

	@Override
	protected boolean needSendResult(ESBMessage<GetCampaignerInfoRq> xmlRequest, GetCampaignerInfoRs message)
	{
		return true;
	}

	@Override
	protected boolean needSendOnline(ESBMessage<GetCampaignerInfoRq> xmlRequest, GetCampaignerInfoRs message)
	{
		return false;
	}
}

package com.rssl.phizic.test.esberibmock;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.test.webgate.esberib.utils.CardResponseHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Egorovaa
 * Date: 22.11.2011
 * Time: 16:43:04
 */
public class MockCardResponseHelper extends MockBaseResponseHelper
{
	private static final MockProductService mockProductService = new MockProductService();

	private static final CardResponseHelper cardResponseHelper = new CardResponseHelper();

	/**
	 * ������������ ������ ��� GFL, ����������� ���������� �� ������
	 * @param bankAcctInqRs ����� ��� GFL
	 * @param gflId ������������� ������� GFL, �� �������� �������� �����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void buildMockCards(BankAcctInqRs_Type bankAcctInqRs, Long gflId) throws BusinessException, BusinessLogicException
	{
		List<MockCard> cards = mockProductService.getCards(gflId);
		if (CollectionUtils.isEmpty(cards))
		{
			bankAcctInqRs.setCardAcctRec(null);
		}
		else
		{
			CardAcctRec_Type[] cardAcctRecs = new CardAcctRec_Type[cards.size()];
			for (int i = 0; i < cards.size(); i++)
			{
				MockCard card = cards.get(i);
				cardAcctRecs[i] = fillCardByGFL(card);
			}
			bankAcctInqRs.setCardAcctRec(cardAcctRecs);
		}
	}

	/**
	 * ��������� ����� ����������. ��� GFL
	 * @param card �����, �� ���������� ������� ����� ����������� ����� ��� GFL
	 * @return ����� ��� GFL
	 */
	private CardAcctRec_Type fillCardByGFL(MockCard card)
	{
		DateFormat mockDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CardAcctRec_Type cardAcctRec = new CardAcctRec_Type();

		// ��������� ���������� � �����
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setAgencyId(card.getAgencyId());
		bankInfo.setBranchId(card.getBranchId());
		bankInfo.setRbBrchId(card.getRbBrchId());
		bankInfo.setRegionId(card.getRegionId());
		cardAcctRec.setBankInfo(bankInfo);

		// ��������� ������ �����
		BankAcctStatus_Type bankAcctStatus = new BankAcctStatus_Type();
		bankAcctStatus.setBankAcctStatusCode(card.getBankacctstatuscode());
		bankAcctStatus.setStatusDesc(card.getStatusdesc());
		cardAcctRec.setBankAcctStatus(bankAcctStatus);

		// ��������� ���������� �� ����������������� ���������� �����
		CardAcctId_Type cardAcctId = new CardAcctId_Type();
		cardAcctId.setSystemId(card.getSystemId());
		cardAcctId.setCardNum(card.getCardNum());
		cardAcctId.setAcctId(card.getAcctId());
		cardAcctId.setAcctCode(card.getAcctCode());
		cardAcctId.setAcctSubCode(card.getAcctSubCode());
		cardAcctId.setCardType(card.getCardType());
		cardAcctId.setCardName(card.getCardName());
		cardAcctId.setAcctCur(card.getAcctCur());
		if (card.getIssDt() != null)
			cardAcctId.setIssDt(mockDateFormat.format(card.getIssDt().getTime()));
		if (card.getEndDt() != null)
			cardAcctId.setEndDt(mockDateFormat.format(card.getEndDt().getTime()));
		if (card.getPmtdt() != null)
			cardAcctId.setPmtDt(mockDateFormat.format(card.getPmtdt().getTime()));
		cardAcctId.setCardHolder(card.getCardHolder());
		cardAcctId.setBankInfo(bankInfo);
		cardAcctRec.setCardAcctId(cardAcctId);

		// ��������� �������������� ���������� �� ����� (����� �������� �����, ��� ���. �����, ���� �������� �����)
		CardAcctInfo_Type cardAcctInfo = new CardAcctInfo_Type();
		if (!StringHelper.isEmpty(card.getMainCard()))
		{
			cardAcctInfo.setMainCard(card.getMainCard());
			cardAcctInfo.setAdditionalCard(card.getAdditionalCard());
            if (card.getNextReportDate() != null)
                cardAcctInfo.setNextReportDate(mockDateFormat.format(card.getNextReportDate().getTime()));
		}
		cardAcctInfo.setEndDtForWay(card.getEndDtForWay());
		cardAcctRec.setCardAcctInfo(cardAcctInfo);

		cardAcctRec.setAcctBal(buildBalance(card.getAcctBal()));

		return cardAcctRec;
	}

	/**
	 * ��������� ��������� ���������� �� ����� (��� CRDWI)
	 * @param parameters - ������� ���������
	 * @return
	 */
	public IFXRs_Type createCardAcctDInqRs(IFXRq_Type parameters) throws BusinessException
	{
		CardAcctDInqRq_Type cardAcctDInqRq = parameters.getCardAcctDInqRq();
		CardAcctDInqRs_Type cardAcctDInqRs = new CardAcctDInqRs_Type();

		cardAcctDInqRs.setRqUID(cardAcctDInqRq.getRqUID());
		cardAcctDInqRs.setRqTm(cardAcctDInqRq.getRqTm());
		cardAcctDInqRs.setOperUID(cardAcctDInqRq.getOperUID());

		CardAcctRec_Type[] cardAcctRecs = new CardAcctRec_Type[cardAcctDInqRq.getCardInfo().length];
		for (int i = 0; i < cardAcctDInqRq.getCardInfo().length; i++)
		{
			CardInfo_Type cardInfo = parameters.getCardAcctDInqRq().getCardInfo(i);
			String cardNum = cardInfo.getCardAcctId().getCardNum();
			RequestCRDWI requestCRDWI = mockProductService.getCardByCRDWI(parameters, cardInfo);
			if (requestCRDWI == null)   // ���� ������ ������(������ �� ����� �� �������), �� �������� �����
			{
				return cardResponseHelper.createCardAcctDInqRs(parameters);
			}
			else if (requestCRDWI.getMockCard()==null) // ��������� ����, ����� �� ��������
			{
				Status_Type responseStatus = new Status_Type(-10L, "������: �� ������ ���������� ������� CRDWI ����� �� ��������.",
						null, "������ ��������� ��������� ���������� �� ����� " + cardNum);
				CardAcctRec_Type cardAcctRec = new CardAcctRec_Type();
				CardAcctId_Type cardAcctId = new CardAcctId_Type();
				cardAcctId.setCardNum(cardNum);
				cardAcctRec.setCardAcctId(cardAcctId);
				cardAcctRec.setStatus(responseStatus);
				cardAcctRecs[i] = cardAcctRec;
			}
			else
			{
				CardAcctRec_Type cardAcctRec = fillCardByCRDWI(requestCRDWI.getMockCard());
				cardAcctRecs[i] = cardAcctRec;
			}
		}
		cardAcctDInqRs.setBankAcctRec(cardAcctRecs);

		IFXRs_Type response = new IFXRs_Type();
		response.setCardAcctDInqRs(cardAcctDInqRs);

		return response;
	}

	/**
	 * ��������� ����� ����������. ��� CRDWI
	 * @param card
	 * @return
	 */
	private CardAcctRec_Type fillCardByCRDWI(MockCard card) throws BusinessException
	{
		DateFormat mockDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// �������� ��������� ����� ��� CRDWI
		CardAcctRec_Type cardAcctRec = new CardAcctRec_Type();

		// ������ �����
		BankAcctStatus_Type bankAcctStatus = new BankAcctStatus_Type();
		fillBankAcctStatus(bankAcctStatus, card);
		cardAcctRec.setBankAcctStatus(bankAcctStatus);

		CardAcctId_Type cardAcctId = new CardAcctId_Type();

		CustInfo_Type custInfo = null;
		if (card.getPersonInfo() != null)
		{
			custInfo = new CustInfo_Type();
			//���������� � ���������
			custInfo.setCustId(card.getPersonInfo().getCustId());
			PersonInfo_Type personInfo = new PersonInfo_Type();
			if (card.getPersonInfo().getBirthday() != null)
				personInfo.setBirthday(mockDateFormat.format(card.getPersonInfo().getBirthday().getTime()));
			personInfo.setBirthPlace(card.getPersonInfo().getBirthPlace());
			personInfo.setTaxId(card.getPersonInfo().getTaxId());
			personInfo.setCitizenship(card.getPersonInfo().getCitizenship());
			personInfo.setAdditionalInfo(card.getPersonInfo().getAdditionalInfo());
			PersonName_Type personName = new PersonName_Type();
			personName.setLastName(card.getPersonInfo().getLastName());
			personName.setFirstName(card.getPersonInfo().getFirstName());
			personName.setMiddleName(card.getPersonInfo().getMiddleName());
			personInfo.setPersonName(personName);

			IdentityCard_Type identityCard = new IdentityCard_Type();
			identityCard.setIdType(card.getPersonInfo().getIdType());
			identityCard.setIdSeries(card.getPersonInfo().getIdSeries());
			identityCard.setIdNum(card.getPersonInfo().getIdNum());
			personInfo.setIdentityCard(identityCard);

			ContactInfo_Type contactInfo = new ContactInfo_Type();
			if (card.getPersonInfo().getMessageDeliveryType() != null)
			{
				contactInfo.setMessageDeliveryType(MessageDeliveryType_Type.fromString(card.getPersonInfo().getMessageDeliveryType()));
				contactInfo.setEmailAddr(card.getPersonInfo().getEmailAddr());
			}
			personInfo.setContactInfo(contactInfo);
			custInfo.setPersonInfo(personInfo);
			cardAcctRec.setStatus(new Status_Type(0L, null, null, null));
		}
		else
			cardAcctRec.setStatus(new Status_Type(-10L, "������: ��� ����� � "+ card.getCardNum()+ " �� �������� ���������� � ���������.",
					null, "�� ������� ���������� � ��������� ����� " + card.getCardNum()));

		cardAcctId.setCustInfo(custInfo);

		BankInfo_Type bankInfo = new BankInfo_Type();
		fillMockBankInfo(bankInfo, card);
		cardAcctRec.setBankInfo(bankInfo);

		cardAcctId.setCardNum(card.getCardNum());
		cardAcctId.setCardType(card.getCardType());
		cardAcctId.setCardName(card.getCardName());
		cardAcctId.setAcctCur(card.getAcctCur());
		if (card.getIssDt() != null)
			cardAcctId.setIssDt(mockDateFormat.format(card.getIssDt().getTime()));
		if (card.getEndDt() != null)
			cardAcctId.setEndDt(mockDateFormat.format(card.getEndDt().getTime()));
		if (card.getPmtdt() != null)
			cardAcctId.setPmtDt(mockDateFormat.format(card.getPmtdt().getTime()));
		cardAcctId.setCardHolder(card.getCardHolder());
		cardAcctId.setSystemId(card.getSystemId());
		cardAcctId.setAcctId(card.getAcctId());

		cardAcctId.setBankInfo(bankInfo);

		cardAcctRec.setCardAcctId(cardAcctId);

		CardAcctInfo_Type cardAcctInfo = new CardAcctInfo_Type();
		cardAcctInfo.setAdditionalCard(card.getAdditionalCard());
		cardAcctInfo.setMainCard(card.getMainCard());
		cardAcctInfo.setEndDtForWay(card.getEndDtForWay());
        if (card.getNextReportDate() != null)
            cardAcctInfo.setNextReportDate(mockDateFormat.format(card.getNextReportDate().getTime()));
		cardAcctRec.setCardAcctId(cardAcctId);
		cardAcctRec.setCardAcctInfo(cardAcctInfo);
		cardAcctRec.setAcctBal(buildBalance(card.getAcctBal()));

		return cardAcctRec;
	}

	/**
	 * ��������� ������ �����
	 * @param bankAcctStatus
	 * @param card
	 */
	private void fillBankAcctStatus(BankAcctStatus_Type bankAcctStatus, MockCard card)
	{
		bankAcctStatus.setBankAcctStatusCode(card.getBankacctstatuscode());
		bankAcctStatus.setStatusDesc(card.getStatusdesc());
	}

	/**
	 * ��������� ���������� � �����
	 * @param bankInfo
	 * @param card
	 */
	private void fillMockBankInfo(BankInfo_Type bankInfo, MockCard card)
	{
		bankInfo.setAgencyId(card.getAgencyId());
		bankInfo.setBranchId(card.getBranchId());
		bankInfo.setRbBrchId(card.getRbBrchId());
		bankInfo.setRegionId(card.getRegionId());
	}
}

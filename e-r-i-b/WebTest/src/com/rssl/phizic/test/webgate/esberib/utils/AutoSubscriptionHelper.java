package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * ������ ��� ������ � �������������
 *
 * @author khudyakov
 * @ created 02.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubscriptionHelper extends BaseResponseHelper
{
	private static final int MAX_NUMBER_OF_AUTO_SUBSCRIPTIONS = 10; //������������ ����� ������������ ��������.
	private static final long AUTO_SUBSCRIPTION_START_ID = 5000L;   //��������� ����� �������������� ��������.
	private static final long AUTO_SUBSCRIPTION_PAYMENT_START_ID = 1000000L; //��������� ����� ��� �������������� �������.
	private static final long AUTO_SUBSCRIPTION_ID_MULTIPLIER = 1000; //��������� (� id ������� ������������ ����� ��������).
	private static final double PERCENT_OF_AUTOTRANSFERS = 0.5; //����������� ��������� ������������ (��������� �� ����������� �������� �����)

	private static Map<Long, AutoSubscriptionRec_Type> autoSubscriptions = new HashMap<Long, AutoSubscriptionRec_Type>();
	private static long lastSubscriptionId = AUTO_SUBSCRIPTION_START_ID;
	private static Map<String, List<Long>> cardToSubscription = new HashMap<String, List<Long>>();

	private static Random rand = new Random();

	/**
	 * ��������� ������� ���������� ��� �����������
	 * @return
	 */
	private String generateAutoPaymentType()
	{
		if (rand.nextDouble() < PERCENT_OF_AUTOTRANSFERS)
		{
			return "autoTransfer";
		}
		else if(new Random().nextBoolean())
		{
			return "moneyBox";
		}
		else
		{
			return "autoPayment";
		}
	}
    /**
	 * ��������� GASL ��������� ������ �������� �� ��������� ������������.
	 * @param ifxRq ������
	 * @return ����� (��������� AutoSubscriptionListRs)
	 */
	public IFXRs_Type createAutoSubscriptionListRs(IFXRq_Type ifxRq)
	{
		GetAutoSubscriptionListRs_Type responce = new GetAutoSubscriptionListRs_Type();
		responce.setOperUID(ifxRq.getGetAutoSubscriptionListRq().getOperUID());
		responce.setRqTm(PaymentsRequestHelper.generateRqTm());
		responce.setRqUID(PaymentsRequestHelper.generateUUID());
		responce.setStatus(getStatus());

		int randInt = (int)(Math.random()*500);
		if (randInt >= 23 && randInt <= 26)
			responce.setStatus(new Status_Type(-10L, "�������� ������, ������� ��� ����������� ������������.", null, null));
		else if(randInt >27 && randInt <= 30)
			responce.setStatus(new  Status_Type(-105L, "�������� ������, ��������� �� ������� �������", null, "������ �� ��������"));
		else
			responce.setStatus(new Status_Type(0L, null, null, null));

		String cardNumber = ifxRq.getGetAutoSubscriptionListRq().getBankAcctRec()[0].getCardAcctId().getCardNum();
		if (cardToSubscription.containsKey(cardNumber) && ifxRq.getGetAutoSubscriptionListRq().getBankAcctRec().length <= 1)
		{
			List<Long> ids = cardToSubscription.get(cardNumber);
			AutoSubscriptionRec_Type[] autoSubscriptionRecs = new AutoSubscriptionRec_Type[ids.size()];
			for (int i = 0; i < ids.size(); i++) {
				autoSubscriptionRecs[i] = autoSubscriptions.get(ids.get(i));
				autoSubscriptionRecs[i].getAutoPaymentTemplate().setRecipientRec(fillRecipientRecByTemplate(autoSubscriptionRecs[i].getAutoPaymentTemplate()));
			}
			responce.setAutoSubscriptionRec(autoSubscriptionRecs);
		}
		else
		{
			AutoSubscriptionRec_Type[] autoSubscriptionRecs = new AutoSubscriptionRec_Type[rand.nextInt(MAX_NUMBER_OF_AUTO_SUBSCRIPTIONS - 2) + 2];
			for (int i = 0; i < autoSubscriptionRecs.length; i++)
			{
				Long autopayId = lastSubscriptionId + i;
				String autoSubscriptionType = generateAutoPaymentType();

				autoSubscriptionRecs[i] = generateNewAutoSubscriptionRec(ifxRq.getGetAutoSubscriptionListRq().getBankAcctRec(), autopayId, autoSubscriptionType);
				autoSubscriptionRecs[i].getAutoPaymentTemplate().setRecipientRec(fillRecipientRecSimple(autoSubscriptionType));
			}
			lastSubscriptionId += autoSubscriptionRecs.length;
			responce.setAutoSubscriptionRec(autoSubscriptionRecs);
		}

		checkAutoSubscriptionStates(responce, ifxRq.getGetAutoSubscriptionListRq().getAutopayStatusList());
		IFXRs_Type ifxRs = new IFXRs_Type();
		ifxRs.setGetAutoSubscriptionListRs(responce);
	    return ifxRs;
	}

	/**
	 * ��������� ASM c�������/��������� ��������
	 * @param ifxRq ������
	 * @return ����� (��������� AutoSubscriptionModRs)
	 */
	public IFXRs_Type createAutoSubscriptionModRs(IFXRq_Type ifxRq)
	{
		AutoSubscriptionModRq_Type request = ifxRq.getAutoSubscriptionModRq();
		AutoSubscriptionModRs_Type autoSubModRs = new AutoSubscriptionModRs_Type();

		autoSubModRs.setRqUID(request.getRqUID());
		autoSubModRs.setRqTm(getRqTm());
		autoSubModRs.setOperUID(request.getOperUID());

		int randInt = (int)(Math.random()*500);
		if (randInt >= 23 && randInt <= 26)
			autoSubModRs.setStatus(new Status_Type(-10L, "�������� ������, ������� ��� ����������� ������������.", null, null));
		else if(randInt >27 && randInt <= 30)
			autoSubModRs.setStatus(new  Status_Type(-105L, null, null, "������ �� ��������"));
		else
			autoSubModRs.setStatus(new Status_Type(0L, null, null, null));

		IFXRs_Type result = new IFXRs_Type();
		result.setAutoSubscriptionModRs(autoSubModRs);
		return result;
	}

	/**
	 * ��������� GASDI ��������� ��������� ���������� �� ��������
	 * @param ifxRq ������
	 * @return ����� (��������� GetAutoSubscriptionDetailInfoRs)
	 */
	public IFXRs_Type createAutoSubscriptionDetailInfoRs(IFXRq_Type ifxRq)
	{
		GetAutoSubscriptionDetailInfoRq_Type request = ifxRq.getGetAutoSubscriptionDetailInfoRq();
		GetAutoSubscriptionDetailInfoRs_Type responce = new GetAutoSubscriptionDetailInfoRs_Type();

		responce.setRqUID(PaymentsRequestHelper.generateUUID());
		responce.setRqTm(PaymentsRequestHelper.generateRqTm());
		responce.setOperUID(request.getOperUID());

		int randInt = (int)(Math.random()*500);
		if (randInt >= 23 && randInt <= 26)
			responce.setStatus(new Status_Type(-10L, "�������� ������, ������� ��� ����������� ������������.", null, null));
		else if(randInt >27 && randInt <= 30)
			responce.setStatus(new  Status_Type(-105L, null, null, "������ �� ��������"));
		else
			responce.setStatus(new Status_Type(0L, null, null, null));

		AutoSubscriptionRec_Type[] autoSubscriptionRecs = new AutoSubscriptionRec_Type[request.getAutoSubscriptionId().length];

		int i = 0;
		for(; i < autoSubscriptionRecs.length; i++)
		{
			if (autoSubscriptions.containsKey(request.getAutoSubscriptionId(i).getAutopayId()))
			{
				autoSubscriptionRecs[i] = fillDetailedInfo(autoSubscriptions.get(request.getAutoSubscriptionId(i).getAutopayId()));
				changeToValidData(autoSubscriptionRecs[i].getAutoPaymentTemplate().getRecipientRec(), true);
				continue;
			}
		}

		AutoSubscriptionRec_Type[] tmp = new AutoSubscriptionRec_Type[i];
		System.arraycopy(autoSubscriptionRecs, 0, tmp, 0, tmp.length);

		responce.setAutoSubscriptionRec(tmp);

		IFXRs_Type ifxRs = new IFXRs_Type();
		ifxRs.setGetAutoSubscriptionDetailInfoRs(responce);
	    return ifxRs;
	}

	/**
	 * �������� ������ � ���������� �� ��������. ����� ���� �������� ������ ����������,
	 * � ����� �������� ���� ������� ������������
	 * @param recipientRec_type
	 */
	public void changeToValidData(RecipientRec_Type recipientRec_type, boolean subscription)
	{
		//���� ������ � ���������� ����� == null, ������ - ��������� �� �� ������, ������ �� ������
		if (recipientRec_type == null)
			return;
		// ����. ���� ��
		recipientRec_type.setCorrAccount("30101810100000000402");
		// ��� ��
		recipientRec_type.setKPP("0123456789");
		// ��� ��
		recipientRec_type.setBIC("044525402");
		//��������� ���� ��
		recipientRec_type.setAcctId("12234810001234567890");
		//��� ��
		recipientRec_type.setTaxId("12311111111");

		if (subscription)
		{
			// ��� ���������� � ���������� �������
			recipientRec_type.setCodeRecipientBS("98897");
			// ��� ������ ������
			recipientRec_type.setGroupService("1");
			// ��� ������� � ���������� �������
			recipientRec_type.setCodeService("123");
			// ������������ ������, � ������� ��������� ��
			recipientRec_type.setNameService("������");

			//���������� �������� ������ �������������
			BankInfo_Type bankInfo_type = new BankInfo_Type();
			bankInfo_type.setRegionId("77");
			bankInfo_type.setAgencyId("17");

			recipientRec_type.setBankInfo(bankInfo_type);

			List<Requisite_Type> requisites = new ArrayList<Requisite_Type>();
			List<FieldDataType> fieldDataTypes = getFieldDataTypesExceptInternalType();
			requisites.add(createRequisite(fieldDataTypes.get(rand.nextInt(fieldDataTypes.size()))));
			requisites.add(createRequisite(fieldDataTypes.get(rand.nextInt(fieldDataTypes.size()))));
			requisites.add(createRequisite(fieldDataTypes.get(rand.nextInt(fieldDataTypes.size()))));
			recipientRec_type.setRequisites(requisites.toArray(new Requisite_Type[requisites.size()]));
		}
		else
		{
			//������� "�� ���������� ���������� ���������"
			recipientRec_type.setNotVisibleBankDetails(rand.nextBoolean());
			if (rand.nextDouble() < 0.7)
				//����� �������� ��
				recipientRec_type.setPhoneToClient("+0 123 456 7890");
			recipientRec_type.setNameOnBill("����������� �������� �� ������ �����");
			recipientRec_type.setBankInfo(null);
			//��������� �������
			List<Requisite_Type> requisites = new ArrayList<Requisite_Type>();
			List<FieldDataType> fieldDataTypes = getFieldDataTypesExceptInternalType();
			requisites.add(createRequisite(fieldDataTypes.get(rand.nextInt(fieldDataTypes.size()))));
			requisites.add(createRequisite(fieldDataTypes.get(rand.nextInt(fieldDataTypes.size()))));
			requisites.add(createRequisite(fieldDataTypes.get(rand.nextInt(fieldDataTypes.size()))));
			Requisite_Type requisite = createRequisite(FieldDataType.money);
			requisite.setIsSum(true);
			requisites.add(requisite);
			recipientRec_type.setRequisites(requisites.toArray(new Requisite_Type[requisites.size()]));
		}
	}

	/**
	 * ��������� GAPL ��������� ������ �������� �� ��������
	 * @param ifxRq ������.
	 * @return ����� (��������� GetAutoPaymentListRs)
	 */
	public IFXRs_Type createAutoPaymentListRs(IFXRq_Type ifxRq)
	{
		GetAutoPaymentListRq_Type request = ifxRq.getGetAutoPaymentListRq();
		GetAutoPaymentListRs_Type responce = new GetAutoPaymentListRs_Type();

		responce.setRqUID(PaymentsRequestHelper.generateUUID());
		responce.setRqTm(PaymentsRequestHelper.generateRqTm());
		responce.setOperUID(request.getOperUID());

		int randInt = (int)(Math.random()*500);
		if (randInt >= 23 && randInt <= 26)
			responce.setStatus(new Status_Type(-10L, "�������� ������, ������� ��� ����������� ������������.", null, null));
		else if(randInt >27 && randInt <= 30)
			responce.setStatus(new  Status_Type(-105L, null, null, "������ �� ��������"));
		else
			responce.setStatus(new Status_Type(0L, null, null, null));

		//������ ��������� ������������.
		AutoPaymentRec_Type autoPaymentRec[] = new AutoPaymentRec_Type[rand.nextInt(10)];
		for (int i = 0; i < autoPaymentRec.length; i++)
		{
			long paymentId = AUTO_SUBSCRIPTION_PAYMENT_START_ID + i + (request.getAutoSubscriptionId().getAutopayId() - AUTO_SUBSCRIPTION_START_ID)*AUTO_SUBSCRIPTION_ID_MULTIPLIER;

			AutoPaymentRec_Type autoPaymentRec_type =  generateAutoPaymentRec(request, paymentId);
			AutoSubscriptionRec_Type autoSubscriptionRec_type = autoSubscriptions.get(request.getAutoSubscriptionId().getAutopayId());
			if (autoSubscriptionRec_type != null)
			{
				AutoSubscriptionInfo_Type autoSubscriptionInfo = new AutoSubscriptionInfo_Type();
				autoSubscriptionInfo.setSummaKindCode(autoSubscriptionRec_type.getAutoSubscriptionInfo().getSummaKindCode());
				autoSubscriptionInfo.setTransDirection(autoSubscriptionRec_type.getAutoSubscriptionInfo().getTransDirection());
				autoPaymentRec_type.setAutoSubscriptionInfo(autoSubscriptionInfo);
			}
			autoPaymentRec[i] = autoPaymentRec_type;
		}
		responce.setAutoPaymentRec(autoPaymentRec);

		IFXRs_Type ifxRs = new IFXRs_Type();
		ifxRs.setGetAutoPaymentListRs(responce);
	    return ifxRs;
	}

	/**
	 * ���������� ������ ��� �����������.
	 * @param request ������.
	 * @return ������.
	 */
	private AutoPaymentRec_Type generateAutoPaymentRec(GetAutoPaymentListRq_Type request, long num)
	{
		AutoPaymentRec_Type autoPaymentRec = new AutoPaymentRec_Type();
		//����������������� ���������� � �������.
		autoPaymentRec.setAutoPaymentId(new AutoPaymentId_Type("urn:sbrfsystems:99-ap", num+""));

		//���������� � �������.
		AutoPaymentInfo_Type autoPaymentInfo = new AutoPaymentInfo_Type();
		{
			//��� ��������� �������.
			String[] paymentStatuses = {"New", "Canceled", "Done"};
			int stNum = rand.nextInt(paymentStatuses.length);
			autoPaymentInfo.setPaymentStatus(PaymentStatusASAP_Type.fromValue(paymentStatuses[stNum]));

			//��������� �������� �������
			String[] statusDesc = {"������ ��������� �� ����������", "�� ������� ��������� ������", "������ �������� �������"};
			autoPaymentInfo.setPaymentStatusDesc(statusDesc[stNum]);

			//����� ������� � ������.
			autoPaymentInfo.setCurAmt(new BigDecimal(rand.nextInt(200)));

			//��� ������ ������� (��� P2P ���������)
			autoPaymentInfo.setAcctCur("RUB");

			if (autoPaymentInfo.getPaymentStatus() != PaymentStatusASAP_Type.Done)
			{
				//�������� ������� � ������.
				autoPaymentInfo.setCommission(new BigDecimal(rand.nextDouble()*5));
			}

			//���������� �� ���������� �����������.
			ExecStatus_Type execStatus = new ExecStatus_Type();
			{
				Calendar calen = Calendar.getInstance();
				if (request == null || request.getSelRangeDtTm() == null)
				{
					calen.setTimeInMillis((long) (calen.getTimeInMillis() - rand.nextDouble() * 1000 * 60 * 60 * 24 * 365));
				}
				else
				{
					long startDate = XMLDatatypeHelper.parseDateTime(request.getSelRangeDtTm().getStartDtTm()).getTimeInMillis();
					long endDate = XMLDatatypeHelper.parseDateTime(request.getSelRangeDtTm().getEndDtTm()).getTimeInMillis();
					calen.setTimeInMillis((long) (startDate + (endDate - startDate) * rand.nextDouble()));
				}
				execStatus.setExecPaymentDate(getStringDateTime(calen));
				if (autoPaymentInfo.getPaymentStatus() == PaymentStatusASAP_Type.Canceled)
				{
					execStatus.setNonExecReasonCode("-12");
					execStatus.setNonExecReasonDesc("������������ ������� �� �����");
				}
			}
			autoPaymentInfo.setExecStatus(execStatus);
		}
		autoPaymentRec.setAutoPaymentInfo(autoPaymentInfo);

		//���������� � ���������� �����.
		if (request != null)
		{
			Long autoPayNumber = request.getAutoSubscriptionId().getAutopayId();
			if (autoSubscriptions.get(autoPayNumber) == null || (autoSubscriptions.get(autoPayNumber) != null && autoSubscriptions.get(autoPayNumber).getAutoPaymentTemplate().getRecipientRec() != null))
			{
				RecipientRec_Type recipientRec = new RecipientRec_Type();
				{
					 recipientRec.setName("������ ����������� �������� �� ������ �����");
				}
				autoPaymentRec.setRecipientRec(recipientRec);
			}
		}else{
			RecipientRec_Type recipientRec = new RecipientRec_Type();
			recipientRec.setBIC("044525401");
			recipientRec.setCorrAccount("30101810800000000401");
			recipientRec.setName("�� \"������\" (���)");
			autoPaymentRec.setRecipientRec(recipientRec);
		}

		return autoPaymentRec;
	}

	/**
	 * ��������� GAPDI ��������� ��������� ���������� �� ��������
	 * @param ifxRq ������
	 * @return ����� (��������� GetAutoPaymentDetailInfoRs)
	 */
	public IFXRs_Type createGetAutoPaymentDetailInfoRs(IFXRq_Type ifxRq)
	{
		GetAutoPaymentDetailInfoRq_Type request =  ifxRq.getGetAutoPaymentDetailInfoRq();
		GetAutoPaymentDetailInfoRs_Type responce = new GetAutoPaymentDetailInfoRs_Type();

		responce.setRqUID(PaymentsRequestHelper.generateUUID());
		responce.setRqTm(PaymentsRequestHelper.generateRqTm());
		responce.setOperUID(request.getOperUID());

		int randInt = (int)(Math.random()*500);
		if (randInt >= 23 && randInt <= 26)
			responce.setStatus(new Status_Type(-10L, "�������� ������, ������� ��� ����������� ������������.", null, null));
		else if(randInt >27 && randInt <= 30)
			responce.setStatus(new  Status_Type(-105L, null, null, "������ �� ��������"));
		else
			responce.setStatus(new Status_Type(0L, null, null, null));

		ArrayList<AutoPaymentRec_Type> list = new ArrayList<AutoPaymentRec_Type>();
		for(int i = 0; i < request.getAutoPaymentId().length; i++)
		{
			if (rand.nextDouble() > 0.9)//� 10% ������� ������������� ������ �� ������.
			{
				continue;
			}
			int payId = Integer.parseInt(request.getAutoPaymentId()[i].getPaymentId());
			AutoPaymentRec_Type autoPaymentRec = generateAutoPaymentRec(null, payId);
			//��� �������� � �������
			autoPaymentRec.getAutoPaymentInfo().setMadeOperationId("bil-00"+autoPaymentRec.getAutoPaymentId().getPaymentId());

			RecipientRec_Type recipientRec = autoPaymentRec.getRecipientRec();
			{
				changeToValidData(recipientRec, false);
			}
			autoPaymentRec.setRecipientRec(recipientRec);

			//����������������� ���������� � ��������
			AutoSubscriptionId_Type autoSubscriptionId = new AutoSubscriptionId_Type();
			{
				autoSubscriptionId.setSystemId("urn:sbrfsystems:99-ap");
				long autoSubId = (payId - AUTO_SUBSCRIPTION_PAYMENT_START_ID)/AUTO_SUBSCRIPTION_ID_MULTIPLIER + AUTO_SUBSCRIPTION_START_ID;
				autoSubscriptionId.setAutopayId(autoSubId);
			}
			autoPaymentRec.setAutoSubscriptionId(autoSubscriptionId);

			//���������� � ��������� ��������, � �������� ������������� ������.
			BankAcctRec_Type bankAcctRec = new BankAcctRec_Type();
			//���������� � �����.
			bankAcctRec.setCardAcctId(createCardAcctId_Type(autoPaymentRec));

			autoPaymentRec.setBankAcctRec(new BankAcctRec_Type[]{bankAcctRec});

			autoPaymentRec.setCardAcctTo(createCardAcctId_Type(autoPaymentRec));

			//���������� �� ����������� ����� � Way4, ���� �������� ������ �������.
			if (rand.nextDouble() < 0.8)
			{
				CardAuthorization_Type cardAuthorization = new CardAuthorization_Type();
				cardAuthorization.setAuthorizationCode(rand.nextLong());
				cardAuthorization.setAuthorizationDtTm("2012-04-16T15:23:00");
				autoPaymentRec.setCardAuthorization(cardAuthorization);
			}

			AutoSubscriptionInfo_Type autoSubscriptionInfo = new AutoSubscriptionInfo_Type();
			autoSubscriptionInfo.setAutopayNumber(generateAutopayNumber(autoSubscriptionId.getAutopayId()));
			AutoSubscriptionRec_Type autoSubscriptionRec_type = autoSubscriptions.get(autoSubscriptionId.getAutopayId());
			if (autoSubscriptionRec_type != null)
			{
				autoSubscriptionInfo.setSummaKindCode(autoSubscriptionRec_type.getAutoSubscriptionInfo().getSummaKindCode());
				autoSubscriptionInfo.setTransDirection(autoSubscriptionRec_type.getAutoSubscriptionInfo().getTransDirection());
			}

			autoPaymentRec.setAutoSubscriptionInfo(autoSubscriptionInfo);
			autoPaymentRec.setDepAcctIdTo(generateDepAcctId());
			list.add(autoPaymentRec);
		}
		responce.setAutoPaymentRec(list.toArray(new AutoPaymentRec_Type[0]));

		IFXRs_Type ifxRs = new IFXRs_Type();
		ifxRs.setGetAutoPaymentDetailInfoRs(responce);
	    return ifxRs;
	}

	/**
	 * 3.2 ��������� ASSM ������������/�������������/�������� ��������
	 * @param ifxRq ������
	 * @return ����� (��������� AutoSubscriptionStatusModRq)
	 */
	public IFXRs_Type createAutoSubscriptionStatusModRs(IFXRq_Type ifxRq)
	{
		AutoSubscriptionStatusModRq_Type request =  ifxRq.getAutoSubscriptionStatusModRq();
		AutoSubscriptionStatusModRs_Type responce = new AutoSubscriptionStatusModRs_Type();

		responce.setRqUID(request.getRqUID());
		responce.setRqTm(getRqTm());
		responce.setOperUID(request.getOperUID());
		responce.setStatus(getStatus());

		IFXRs_Type ifxRs = new IFXRs_Type();
		ifxRs.setAutoSubscriptionStatusModRs(responce);

	    return ifxRs;
	}

	protected CustInfo_Type getMockCustInfo()
	{
		CustInfo_Type custInfo = new CustInfo_Type();

		PersonInfo_Type personInfo = new PersonInfo_Type();
		custInfo.setPersonInfo(personInfo);
		PersonName_Type personName = new PersonName_Type("�����", "������", "����������", null);
		personInfo.setPersonName(personName);
		ContactInfo_Type contactInfo = new ContactInfo_Type();
		FullAddress_Type[] posAddr = {new FullAddress_Type()};
		posAddr[0].setAddrType("2");
		posAddr[0].setAddr3("�. �����, ��. �����, �. 32, ��. 12");
		contactInfo.setPostAddr(posAddr);
		personInfo.setContactInfo(contactInfo);

		return custInfo;
	}

	private CardAcctId_Type generateDepAcctId()
	{
		CardAcctId_Type depAcctId = new CardAcctId_Type();

		depAcctId.setSystemId("systemX");
		//����� �����.
		depAcctId.setAcctId("42301840146754266088");
		//��� ������
		depAcctId.setAcctCur("RUB");
		//�������� �����
		depAcctId.setAcctName("��������������");
		//��� ������
		depAcctId.setAcctCode(1L);
		//rbTbBrch
		depAcctId.setBankInfo(new BankInfo_Type(null, null, null, null, "770017"));

		return depAcctId;
	}

	private CardAcctId_Type generateCardAcctId()
	{
		//������������� �������, ������ �������� �����
		CardAcctId_Type cardAcctId = new CardAcctId_Type();

		cardAcctId.setSystemId("urn:sbrfsystems:99-ap");
		//����� �����.
		cardAcctId.setCardNum("4012356789012345");
		//����� ���������� �����.
		cardAcctId.setAcctId("40817810012345456576");
		//��� �����
		cardAcctId.setCardType("Debet");
		//��� �����
		cardAcctId.setCardLevel("MB");
		//�������������� � �������� ���������
		cardAcctId.setCardBonusSign("Y");
		//���� ��������� ����� ��������
		cardAcctId.setEndDt("22.12.2112");
		//���������� � ��������� �����.
		cardAcctId.setCustInfo(getMockCustInfo());
		//rbTbBrch
		cardAcctId.setBankInfo(new BankInfo_Type(null, null, null, null, "770017"));

		return cardAcctId;
	}
	private CardAcctId_Type createCardAcctId_Type(AutoPaymentRec_Type autoPaymentRec)
	{
		//������������� �������, ������ �������� �����
		CardAcctId_Type cardAcctId = new CardAcctId_Type();

		cardAcctId.setSystemId("urn:sbrfsystems:99-ap");
		//����� �����.
		cardAcctId.setCardNum(autoSubscriptions.get(autoPaymentRec.getAutoSubscriptionId().getAutopayId()).getAutoPaymentTemplate().getBankAcctRec(0).getCardAcctId().getCardNum());
		//����� ���������� �����.
		cardAcctId.setAcctId("40817810012345456576");
		//��� �����
		cardAcctId.setCardType("Debet");
		//��� �����
		cardAcctId.setCardLevel("MB");
		//�������������� � �������� ���������
		cardAcctId.setCardBonusSign("Y");
		//���� ��������� ����� ��������
		cardAcctId.setEndDt("22.12.2112");
		//���������� � ��������� �����.
		cardAcctId.setCustInfo(getMockCustInfo());
		//rbTbBrch
		cardAcctId.setBankInfo(new BankInfo_Type(null, null, null, null, "770017"));

		return cardAcctId;
	}

	/**
	 * ���������� ������ �� ���������� �� ��������� ���� �����������
	 * @param autoSubscriptionType
	 * @return
	 */
	private RecipientRec_Type fillRecipientRecSimple(String autoSubscriptionType)
	{
		if (autoSubscriptionType.equals("autoPayment"))
		{
			RecipientRec_Type recipientRec = new RecipientRec_Type();
			//������������ ��.
			recipientRec.setName("���");

			//��� ��
			recipientRec.setTaxId("12311111111");

			return recipientRec;
		}
		else
		{
			return null;
		}
	}

	/**
	 * ���������� ������ �� ����������, � ����������� �� ������ �������
	 * @param template
	 * @return
	 */
	private RecipientRec_Type fillRecipientRecByTemplate(AutoPaymentTemplate_Type template)
	{
		if (template.getCardAcctTo() == null && template.getDepAcctIdTo() == null)
		{
			RecipientRec_Type recipientRec = new RecipientRec_Type();
			//������������ ��.
			recipientRec.setName("���");

			//��� ��
			recipientRec.setTaxId("12311111111");

			return recipientRec;
		}
		else
		{
			return null;
		}
	}

	/**
	 * ��������� ��� ������
	 * @return
	 */
	private PayDay_Type generatePayDate()
	{
		PayDay_Type payday = new PayDay_Type();
		Calendar calendar = Calendar.getInstance();
		payday.setDay(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
		TreeMap<Integer, String> dayOfWeekMap = new TreeMap<Integer, String>();
		dayOfWeekMap.put(1, "Monday");
		dayOfWeekMap.put(2, "Tuesday");
		dayOfWeekMap.put(3, "Wednesday");
		dayOfWeekMap.put(4, "Thursday");
		dayOfWeekMap.put(5, "Friday");
		dayOfWeekMap.put(6, "Saturday");
		dayOfWeekMap.put(7, "Sunday");
		payday.setWeekDay(dayOfWeekMap.get(calendar.get(Calendar.DAY_OF_WEEK)));
		payday.setMonthInYear(String.valueOf(calendar.get(Calendar.MONTH)));
		payday.setMonthInQuarter(String.valueOf(calendar.get(Calendar.MONTH) % 3 + 1));
		return payday;
	}

	private void fillInfoForAutoTransfer(AutoSubscriptionInfo_Type info, AutoPaymentTemplate_Type template)
	{
		info.setPercent(new BigDecimal(Math.random() * 100));

		info.setPayDay(generatePayDate());

		TransDirection_Type transDirection;

		double rand = Math.random() * 3;
		if (rand < 1)
		{
			transDirection = TransDirection_Type.TCC_OWN;
		}
		else if (rand < 2)
		{
			transDirection = TransDirection_Type.TCC_P2P;
		}
		else
		{
			transDirection = TransDirection_Type.TCC_P2P_FOREIGN;
		}

		info.setTransDirection(transDirection);
		info.setMessage("this is the test message");
		template.setCardAcctTo(generateCardAcctId());
	}

	private void fillInfoForMoneyBox(AutoSubscriptionInfo_Type info, AutoPaymentTemplate_Type template)
	{
		ExeEventCodeASAP_Type execEventType = info.getExeEventCode();
		SummaKindCodeASAP_Type summaKindCode = info.getSummaKindCode();
		//������� �� ����������
		if (summaKindCode.getValue().equals("PERCENT_BY_ANY_RECEIPT"))
		{
			info.setPercent(new BigDecimal(new Random().nextInt(100)));
			info.setCurAmt(null);
			info.setMaxSumWritePerMonth(BigDecimal.valueOf(rand.nextInt(1000)));
		}
		//������� �� �������
		else if (summaKindCode.getValue().equals("PERCENT_BY_DEBIT"))
		{
			info.setPercent(new BigDecimal(new Random().nextInt(100)));
			info.setCurAmt(null);
			info.setMaxSumWritePerMonth(BigDecimal.valueOf(rand.nextInt(1000)));
		}
		//������������� �����
		else if (summaKindCode.getValue().equals("FIXED_SUMMA"))
		{
			PayDay_Type payDay_type = new PayDay_Type();
			String eventType = execEventType.getValue();
			if(eventType.equals("ONCE_IN_WEEK"))
			{
				TreeMap<Integer, String> dayOfWeekMap = new TreeMap<Integer, String>();
				dayOfWeekMap.put(1, "Monday");
				dayOfWeekMap.put(2, "Tuesday");
				dayOfWeekMap.put(3, "Wednesday");
				dayOfWeekMap.put(4, "Thursday");
				dayOfWeekMap.put(5, "Friday");
				dayOfWeekMap.put(6, "Saturday");
				dayOfWeekMap.put(7, "Sunday");
				payDay_type.setWeekDay(dayOfWeekMap.get(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)));
			}
			else if(eventType.equals("ONCE_IN_MONTH"))
			{
				payDay_type.setDay(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
			}
			else if(eventType.equals("ONCE_IN_QUARTER"))
			{
				payDay_type.setDay(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
				payDay_type.setMonthInQuarter(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) % 3 + 1));
			}
			else if(eventType.equals("ONCE_IN_YEAR"))
			{
				payDay_type.setMonthInYear(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
			}
			info.setPayDay(payDay_type);
		}

		info.setMessage("this is the test message for MoneyBox");
		template.setDepAcctIdTo(generateDepAcctId());
	}

	/**
	 * ���������� ��������.
	 * @param bankAcctRec
	 * @param autopayId
	 * @return
	 */
	private AutoSubscriptionRec_Type generateNewAutoSubscriptionRec(BankAcctRec_Type[] bankAcctRec, Long autopayId, String autoSubscriptionType)
	{
		AutoSubscriptionRec_Type tmp = new AutoSubscriptionRec_Type();
		AutoSubscriptionId_Type autoSubscriptionId = new AutoSubscriptionId_Type();
		autoSubscriptionId.setAutopayId(autopayId);
		autoSubscriptionId.setSystemId("urn:sbrfsystems:99-ap");

		AutoSubscriptionInfo_Type autoSubscriptionInfo = new AutoSubscriptionInfo_Type();
		autoSubscriptionInfo.setAutopayNumber(generateAutopayNumber(autoSubscriptionId.getAutopayId()));
		autoSubscriptionInfo.setAutopayName("���������� ����� " + autoSubscriptionId.getAutopayId());
		String[] statusType = {"New","OnRegistration","Confirmed","Active","WaitForAccept","ErrorRegistration","Paused","Closed"};
		autoSubscriptionInfo.setAutopayStatus(AutopayStatus_Type.fromValue(statusType[rand.nextInt(statusType.length)]));
		autoSubscriptionInfo.setAutopayStatusDesc("����� ��������");
		autoSubscriptionInfo.setStartDate("2012-04-15T13:31:00");
		autoSubscriptionInfo.setCurAmt(BigDecimal.valueOf(rand.nextInt(1000)));
		autoSubscriptionInfo.setNextPayDate("2012-05-10T12:34:00");

        String summaKind = "";
		if (autoSubscriptionType.equals("moneyBox"))
		{
			String[] summaKinds = {"PERCENT_BY_ANY_RECEIPT","PERCENT_BY_DEBIT","FIXED_SUMMA"};
			summaKind = summaKinds[rand.nextInt(summaKinds.length)];
			autoSubscriptionInfo.setSummaKindCode(SummaKindCodeASAP_Type.fromValue(summaKind));
		}
		else if(autoSubscriptionType.equals("autoTransfer"))
		{
			autoSubscriptionInfo.setSummaKindCode(SummaKindCodeASAP_Type.RUR_SUMMA);
		}
		else
		{
			String[] summaKinds = {"FIXED_SUMMA_IN_RECIP_CURR","BY_BILLING"};
	        summaKind = summaKinds[rand.nextInt(summaKinds.length)];
			autoSubscriptionInfo.setSummaKindCode(SummaKindCodeASAP_Type.fromValue(summaKind));
		}

        String[] exeEventCode = {"ONCE_IN_WEEK","ONCE_IN_MONTH","ONCE_IN_QUARTER","ONCE_IN_YEAR"/*,"ON_REMAIND"*/};
		if(summaKind.equals("PERCENT_BY_ANY_RECEIPT"))
		{
			autoSubscriptionInfo.setExeEventCode(ExeEventCodeASAP_Type.fromValue("BY_ANY_RECEIPT"));
		}
		else if(summaKind.equals("PERCENT_BY_DEBIT"))
		{
			autoSubscriptionInfo.setExeEventCode(ExeEventCodeASAP_Type.fromValue("BY_DEBIT"));
		}
		else
		{
            autoSubscriptionInfo.setExeEventCode(ExeEventCodeASAP_Type.fromValue(
                exeEventCode[rand.nextInt(summaKind.equals("BY_BILLING") ? (exeEventCode.length - 1) : exeEventCode.length)]));
		}

        AutoPaymentTemplate_Type autoPaymentTemplate = new AutoPaymentTemplate_Type();

		//�� ������ ���������� ������ ���� ��������, ������� �� ��� �������� �� ������
		BankAcctRec_Type[] newBankAcctRec = new BankAcctRec_Type[1];
		newBankAcctRec[0] = bankAcctRec[rand.nextInt(bankAcctRec.length)];
		autoPaymentTemplate.setBankAcctRec(newBankAcctRec);

		if (autoSubscriptionType.equals("autoTransfer"))
		{
			fillInfoForAutoTransfer(autoSubscriptionInfo, autoPaymentTemplate);
		}
		if (autoSubscriptionType.equals("moneyBox"))
		{
			fillInfoForMoneyBox(autoSubscriptionInfo, autoPaymentTemplate);
			String currency = autoPaymentTemplate.getBankAcctRec()[0].getCardAcctId().getAcctCur();
			if (currency == null)
				currency = "RUB";
			autoPaymentTemplate.getBankAcctRec()[0].getCardAcctId().setAcctCur(currency);
		}
		tmp.setAutoSubscriptionId(autoSubscriptionId);
		tmp.setAutoSubscriptionInfo(autoSubscriptionInfo);
		tmp.setAutoPaymentTemplate(autoPaymentTemplate);

		autoSubscriptions.put(tmp.getAutoSubscriptionId().getAutopayId(), tmp);
		String cardNumber = newBankAcctRec[0].getCardAcctId().getCardNum();
		if (!cardToSubscription.containsKey(cardNumber))
		{
			cardToSubscription.put(cardNumber, new ArrayList<Long>());
		}
		cardToSubscription.get(cardNumber).add(tmp.getAutoSubscriptionId().getAutopayId());

		return tmp;
	}

	/**
	 * ��������� ��������� ����������
	 * @param subscriptionRec ��������� AutoSubscriptionRec_Type 
	 * @return
	 */
	private AutoSubscriptionRec_Type fillDetailedInfo(AutoSubscriptionRec_Type subscriptionRec)
	{
		if (subscriptionRec.getAutoSubscriptionInfo().getUpdateDate() != null)
			return subscriptionRec;

		AutoSubscriptionInfo_Type autoSubscriptionInfo = subscriptionRec.getAutoSubscriptionInfo();
		autoSubscriptionInfo.setUpdateDate("2012-04-20T15:23:00");
		autoSubscriptionInfo.setMaxSumWritePerMonth(new BigDecimal(100));

		ChangeStatus_Type changeStatus = new ChangeStatus_Type("123", "�������� ������� �����������. :)", "2012-04-20T14:23:12");
		autoSubscriptionInfo.setChangeStatus(changeStatus);

		String channelType[] = {"IB", "VSP", "US", "SMSSender"};
		autoSubscriptionInfo.setChannelType(Channel_Type.fromValue(channelType[rand.nextInt(channelType.length)]));
		if (autoSubscriptionInfo.getChannelType() == Channel_Type.US)
			autoSubscriptionInfo.setSPNum("23124");

		autoSubscriptionInfo.setAutopayNumber(generateAutopayNumber(subscriptionRec.getAutoSubscriptionId().getAutopayId()));
		autoSubscriptionInfo.setSummaKindCode(subscriptionRec.getAutoSubscriptionInfo().getSummaKindCode());
		autoSubscriptionInfo.setTransDirection(subscriptionRec.getAutoSubscriptionInfo().getTransDirection());

		AutoPaymentTemplate_Type autoPaymentTemplate = subscriptionRec.getAutoPaymentTemplate();
		autoPaymentTemplate.setSystemId("1251");

		for (BankAcctRec_Type bankAcct: autoPaymentTemplate.getBankAcctRec())
		{
			CardAcctId_Type cardAcctID = bankAcct.getCardAcctId();

			CustInfo_Type custInfo = new CustInfo_Type();
			PersonInfo_Type personInfo = new PersonInfo_Type();
			custInfo.setPersonInfo(personInfo);
			PersonName_Type personName = new PersonName_Type("�����", "������", "����������", null);
			personInfo.setPersonName(personName);
			ContactInfo_Type contactInfo = new ContactInfo_Type();
			FullAddress_Type[] posAddr = {new FullAddress_Type()};
			posAddr[0].setAddrType("2");
			posAddr[0].setAddr3("�. �����, ��. �����, �. 32, ��. 12");
			contactInfo.setPostAddr(posAddr);
			personInfo.setContactInfo(contactInfo);

			cardAcctID.setCustInfo(custInfo);
			cardAcctID.setCardBonusSign("MB");
			cardAcctID.setCardLevel("Y");
		}
		//���� �����������
		if (autoPaymentTemplate.getCardAcctTo() != null)
		{
			fillInfoForAutoTransfer(autoSubscriptionInfo, autoPaymentTemplate);
		}
		//���� �������
		else if (autoPaymentTemplate.getDepAcctIdTo() != null)
		{
			fillInfoForMoneyBox(autoSubscriptionInfo, autoPaymentTemplate);
		}
		return subscriptionRec;
	}

	private Requisite_Type createRequisite(FieldDataType type)
	{
		if (type == FieldDataType.calendar)
		{
			// ���� �� �������� � ����� ���������. ��� ��������� �����
			type = FieldDataType.date;
		}
		if (type == FieldDataType.integer)
		{
			// ���� �� �������� � ����� integer.
			type = FieldDataType.number;
		}
		Requisite_Type result = new Requisite_Type();
		result.setNameVisible("���� " + type);
		result.setNameBS("extend-" + type + "-");
		result.setDescription("�������� ���� " + type);
		result.setComment("������ � ���� " + type);
		result.setType(type.name());
		if (type == FieldDataType.number)
		{
			result.setNumberPrecision(BigInteger.valueOf(4));
		}
		result.setIsRequired(true);
		result.setIsSum(false);
		result.setIsKey(false);
		result.setIsEditable(true);
		result.setIsVisible(true);
		result.setIsForBill(true);
		result.setIncludeInSMS(true);
		result.setSaveInTemplate(true);
		result.setHideInConfirmation(false);
		result.setRequisiteTypes(createRandomRequisiteTypes());
		if (type == FieldDataType.list)
		{
			String[] meny = {"�������� 1", "�������� 2", "�������� 3"};
			result.setMenu(meny);
			result.setEnteredData(new String[]{meny[rand.nextInt(meny.length)]});
		}
		else if(type == FieldDataType.set)
		{
			String[] meny = {"�������� set 1", "�������� set 2", "�������� set 3"};
			result.setMenu(meny);
			int size = rand.nextInt(3) + 1;
			String[] values = new String[size];
			int i = 0;
			while(i < size)
			{
				values[i++] = meny[rand.nextInt(meny.length)];
			}
			result.setEnteredData(values);
		}
		else if (type == FieldDataType.date)
		{
			result.setEnteredData(new String[]{"2010-12-13T00:00:00"});
		}
		else
		{
			result.setEnteredData(new String[]{(long)(Math.random()*1000) + ""});
		}
		return result;
	}

	/**
	 * �������� ������ ���� ����� ����� FieldDataType, ����� ���������� �����
	 * @return
	 */
	private List<FieldDataType> getFieldDataTypesExceptInternalType()
	{
		List<FieldDataType> fieldDataTypes  = new ArrayList<FieldDataType>();
		for (FieldDataType fieldDataType : FieldDataType.values())
		{
			if(fieldDataType != FieldDataType.link
					&& fieldDataType != FieldDataType.choice
					&& fieldDataType != FieldDataType.graphicset
					&& fieldDataType != FieldDataType.email)
				fieldDataTypes.add(fieldDataType);
		}
		return fieldDataTypes;
	}

	/**
	 * ������������� ����������� �� ��������� ����������
	 * @param responce �������� ������ ������������
	 * @param types ����������� ���������
	 */
	private void checkAutoSubscriptionStates(GetAutoSubscriptionListRs_Type responce, AutopayStatus_Type[] types)
	{
		if(types == null)
			return;
		List<AutoSubscriptionRec_Type> autoSubscriptionRecs = new ArrayList<AutoSubscriptionRec_Type>();
		for(AutoSubscriptionRec_Type autoSubscriptionRec_type : responce.getAutoSubscriptionRec())
		{
			for(AutopayStatus_Type type : types)
			{
				if(autoSubscriptionRec_type.getAutoSubscriptionInfo().getAutopayStatus().equals(type))
				{
					autoSubscriptionRecs.add(autoSubscriptionRec_type);
				}
			}
		}
		responce.setAutoSubscriptionRec(autoSubscriptionRecs.toArray(new AutoSubscriptionRec_Type[autoSubscriptionRecs.size()]));
	}

	/**
	 * ������������ �������������� �����������.
	 * �������� ������������ �������������� �����������.
	   � ����  ���� �������� �����  ��� �������� �������������� �����������:
	     <����� �������><����� �����><�������>
	     ���:
	     �	����� ������� � ����� ���� = 1
	     �	����� ����� � ����� ����� ����, ���������� 2 �������
	     �	������� � ������� ���������� �������� �������� ������������ 9 ��������. ������ � ����� 1.
	 */
	private String generateAutopayNumber(Long autoPayId)
	{
		if(autoPayId == null)
			return null;
		return "1011"+ StringHelper.addLeadingZeros(autoPayId.toString(), 8);
	}
}

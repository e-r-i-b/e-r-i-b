package com.rssl.phizicgate.esberibgate.autopayments;

import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.esberibgate.messaging.ClientRequestHelperBase;
import com.rssl.phizicgate.esberibgate.types.CardTypeWrapper;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * ������ �������� �������� ������������
 *
 * @author: vagin
 * @ created: 19.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubscriptionsRequestHelper extends ClientRequestHelperBase
{
	public static final String SYSTEM_ID = "urn:sbrfsystems:99-ap";        //systemId �� "�����������"

	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	public AutoSubscriptionsRequestHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * @param cards ������ ���� �������
	 * @param types ������������� ������� ��������
	 * @return ������ �� ������ �������� �� ���������� �������� �� �������� ������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public IFXRq_Type createAutoSubscriptionListRq(List<Card> cards, AutoPayStatusType... types) throws GateException, GateLogicException
	{
		if (CollectionUtils.isEmpty(cards))
		{
			throw new GateLogicException("��������� �������� ������ ������������. ������ ���������� ���� ����.");
		}

		GetAutoSubscriptionListRq_Type autoSubscriptionListRq_Type = new GetAutoSubscriptionListRq_Type();

		autoSubscriptionListRq_Type.setRqUID(generateUUID());
		autoSubscriptionListRq_Type.setRqTm(generateRqTm());
		autoSubscriptionListRq_Type.setOperUID(generateOUUID());
		autoSubscriptionListRq_Type.setSPName(getSPName());
		autoSubscriptionListRq_Type.setBankInfo(createAuthBankInfo(EntityIdHelper.getCommonCompositeId(cards.get(0).getId())));

		autoSubscriptionListRq_Type.setBankAcctRec(createBankAcctRec_Types(cards));

		if (ArrayUtils.isNotEmpty(types))
		{
			autoSubscriptionListRq_Type.setAutopayStatusList(createAutoPayStatus_Types(types));
		}

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setGetAutoSubscriptionListRq(autoSubscriptionListRq_Type);

		return ifxRq;
	}

	/**
	 * ������������ ��������� BankAcctRec_Type
	 * @param cards �������� ������ ���� ������ �������
	 * @return BankAcctRec_Type
	 */
	private BankAcctRec_Type[] createBankAcctRec_Types(List<Card> cards) throws GateLogicException, GateException
	{
		try
		{
			List<BankAcctRec_Type> bankAcctRec_types = new ArrayList<BankAcctRec_Type>(cards.size());
			for (Card card : cards)
			{
				  bankAcctRec_types.add(createBankAcctRec_Type(card));
			}
			return bankAcctRec_types.toArray(new BankAcctRec_Type[bankAcctRec_types.size()]);
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e);
		}
	}

	/**
	 * ������� ������ �� ��������� ��������� ����������.
	 *
	 * @param autoSubscriptions ������ �������� �� �����������.
	 * @return ������.
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public IFXRq_Type createAutoSubscriptionDetailInfoRq(AutoSubscription... autoSubscriptions) throws GateException, GateLogicException
	{
		if (ArrayUtils.isEmpty(autoSubscriptions))
			throw new GateLogicException("�� ������� �������� ��������� ���������� �� ������������. ������ �������� ����.");

		GetAutoSubscriptionDetailInfoRq_Type autoPaymentDetailInfoRq = new GetAutoSubscriptionDetailInfoRq_Type();

		autoPaymentDetailInfoRq.setRqUID(generateUUID());
		autoPaymentDetailInfoRq.setRqTm(generateRqTm());
		autoPaymentDetailInfoRq.setOperUID(generateOUUID());
		autoPaymentDetailInfoRq.setSPName(getSPName());
		EntityCompositeId compositeId = EntityIdHelper.getLongOfferCompositeId(autoSubscriptions[0].getExternalId());
		autoPaymentDetailInfoRq.setBankInfo(createAuthBankInfo(compositeId));

		AutoSubscriptionId_Type[] ids = new AutoSubscriptionId_Type[autoSubscriptions.length];

		int i = 0;
		for (AutoSubscription autoSubscription : autoSubscriptions)
		{
			ids[i++] = new AutoSubscriptionId_Type(ExternalSystemHelper.getCode(SYSTEM_ID), Long.parseLong(autoSubscription.getNumber()));
		}
		autoPaymentDetailInfoRq.setAutoSubscriptionId(ids);

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setGetAutoSubscriptionDetailInfoRq(autoPaymentDetailInfoRq);
		return ifxRq;
	}

	/**
	 * ������� ������ �� ��������� ��������� ���������� �� ������������
	 * @param externalId ������� ������������� ��������
	 * @return �������������� ������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public IFXRq_Type createAutoSubscriptionDetailInfoRq(String... externalId) throws GateException, GateLogicException
	{
		GetAutoSubscriptionDetailInfoRq_Type autoPaymentDetailInfoRq = new GetAutoSubscriptionDetailInfoRq_Type();

		autoPaymentDetailInfoRq.setRqUID(generateUUID());
		autoPaymentDetailInfoRq.setRqTm(generateRqTm());
		autoPaymentDetailInfoRq.setOperUID(generateOUUID());
		autoPaymentDetailInfoRq.setSPName(getSPName());
		EntityCompositeId compositeId = EntityIdHelper.getLongOfferCompositeId(externalId[0]);
		autoPaymentDetailInfoRq.setBankInfo(createAuthBankInfo(compositeId));

		AutoSubscriptionId_Type[] ids = new AutoSubscriptionId_Type[]{
				new AutoSubscriptionId_Type(ExternalSystemHelper.getCode(SYSTEM_ID), Long.parseLong(compositeId.getEntityId()))};

		autoPaymentDetailInfoRq.setAutoSubscriptionId(ids);

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setGetAutoSubscriptionDetailInfoRq(autoPaymentDetailInfoRq);
		return ifxRq;
	}

	/**
	 * ������������ ��������� BankAcctRec_Type
	 *
	 * @param card �����
	 * @return BankAcctRec_Type
	 * @throws GateException
	 * @throws GateLogicException
	 */
	private BankAcctRec_Type createBankAcctRec_Type(Card card) throws GateException, GateLogicException
	{
		CardAcctId_Type cardAcctId_Type = new CardAcctId_Type();

		EntityCompositeId compositeId = EntityIdHelper.getCardCompositeId(card);
		cardAcctId_Type.setSystemId(compositeId.getSystemId());
		cardAcctId_Type.setAcctId(card.getPrimaryAccountNumber());
		cardAcctId_Type.setCardNum(card.getNumber());
		cardAcctId_Type.setCardType(card.getCardType() != null ? CardTypeWrapper.getCardTypeForRequest(card.getCardType()) : "");
		cardAcctId_Type.setEndDt(getStringDate(card.getExpireDate()));

		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId(compositeId.getRbBrchId());
		cardAcctId_Type.setBankInfo(bankInfo);

		BankAcctRec_Type bankAcctRec_type = new BankAcctRec_Type();
		bankAcctRec_type.setCardAcctId(cardAcctId_Type);

		return bankAcctRec_type;
	}

	/**
	 * ������������ ��������� AutoPayStatus_Types
	 * @param types ������������� ������� ��������
	 * @return AutoPayStatus_Types
	 */
	private AutopayStatus_Type[] createAutoPayStatus_Types(AutoPayStatusType[] types)
	{
		AutopayStatus_Type[] autoPayStatus_Types = new AutopayStatus_Type[types.length];

		int i = 0;
		for (AutoPayStatusType type : types)
		{
			autoPayStatus_Types[i++] = AutopayStatus_Type.fromValue((type.name()));
		}

		return autoPayStatus_Types;
	}

	/**
	 * ������� ������ �� ��������� ������� ������������.
	 * @param autoSubscription �������� �� ����������.
	 * @param begin ���� ������.
	 * @param end ���� ���������.
	 * @return ������.
	 */
	public IFXRq_Type createAutoPaymentListRq(AutoSubscription autoSubscription, Calendar begin, Calendar end) throws GateLogicException, GateException
	{
		GetAutoPaymentListRq_Type autoPaymentListRq = new GetAutoPaymentListRq_Type();

		autoPaymentListRq.setRqUID(generateUUID());
		autoPaymentListRq.setRqTm(generateRqTm());
		autoPaymentListRq.setOperUID(generateOUUID());
		autoPaymentListRq.setSPName(getSPName());
		autoPaymentListRq.setBankInfo(createAuthBankInfo(EntityIdHelper.getLongOfferCompositeId(autoSubscription.getExternalId())));

		//����������������� ���������� � ��������.
		autoPaymentListRq.setAutoSubscriptionId(new AutoSubscriptionId_Type(ExternalSystemHelper.getCode(SYSTEM_ID), Long.parseLong(autoSubscription.getNumber())));

		//������ ��������� ��������� �����������.
		//�� �� �������� ���
		//autoPaymentListRq.setPaymentStatusList(new PaymentStatusASAP_Type[0]);

		//������ �������, �� ������� ���� ��������� �������.
		if (begin != null && end != null)
			autoPaymentListRq.setSelRangeDtTm(new SelRangeDtTm_Type(getStringDateTime(begin), getStringDateTime(end)));

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setGetAutoPaymentListRq(autoPaymentListRq);
		return ifxRq;
	}

	/**
	 * ������� ������ ��������� ��������� ���������� �� ������� �� ��������.
	 * @param ids �������������� �������.
	 * @param autoSub ��������.
	 * @return ������.
	 */
	public IFXRq_Type createGetAutoPaymentDetailInfoRq(AutoSubscription autoSub, Long... ids) throws GateLogicException, GateException
	{
		if (ArrayUtils.isEmpty(ids))
		{
			throw new IllegalArgumentException("�� ������� �������� ��������� ���������� �� ������������ �������. ������ �������� ����.");
		}

		GetAutoPaymentDetailInfoRq_Type autoPaymentDetailInfoRq = new GetAutoPaymentDetailInfoRq_Type();

		autoPaymentDetailInfoRq.setRqUID(generateUUID());
		autoPaymentDetailInfoRq.setRqTm(generateRqTm());
		autoPaymentDetailInfoRq.setOperUID(generateOUUID());
		autoPaymentDetailInfoRq.setSPName(getSPName());
		autoPaymentDetailInfoRq.setBankInfo(createAuthBankInfo(EntityIdHelper.getLongOfferCompositeId(autoSub.getExternalId())));

		AutoPaymentId_Type list[] = new AutoPaymentId_Type[ids.length];
		for (int i = 0; i < list.length; i++)
		{
			AutoPaymentId_Type autoPaymentId_type = new AutoPaymentId_Type(ExternalSystemHelper.getCode(SYSTEM_ID), Long.toString(ids[i]));
			list[i] = autoPaymentId_type;
		}
		autoPaymentDetailInfoRq.setAutoPaymentId(list);

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setGetAutoPaymentDetailInfoRq(autoPaymentDetailInfoRq);
		return ifxRq;
	}


	private BankInfo_Type createAuthBankInfo(EntityCompositeId compositeId) throws GateLogicException, GateException
	{
		return createAuthBankInfo(compositeId.getLoginId());
	}
}

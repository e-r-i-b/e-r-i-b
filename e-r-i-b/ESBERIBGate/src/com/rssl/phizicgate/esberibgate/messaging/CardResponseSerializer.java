package com.rssl.phizicgate.esberibgate.messaging;

import com.rssl.phizgate.mobilebank.GateCardHelper;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.AdditionalProductData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.OTPRestriction;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.utils.StoredResourcesService;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.bankroll.*;
import com.rssl.phizicgate.esberibgate.statistics.exception.ESBERIBExceptionStatisticHelper;
import com.rssl.phizicgate.esberibgate.types.*;
import com.rssl.phizicgate.esberibgate.types.wrappers.MessageDeliveryTypeWrapper;
import com.rssl.phizicgate.esberibgate.types.wrappers.ReportDeliveryLanguageWrapper;
import com.rssl.phizicgate.esberibgate.types.wrappers.ReportDeliveryTypeWrapper;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.util.*;

/**
 @author Pankin
 @ created 24.12.2010
 @ $Author$
 @ $Revision$
 */
@SuppressWarnings({"ThrowableInstanceNeverThrown"})
public class CardResponseSerializer extends BaseResponseSerializer
{
	private static final List<String> NOT_ACCESS_TB_CODES = Arrays.asList("91", "92");

	private static final String CURRENCY_NOT_EQUAL = "������ ����� �� ��������� � ���������� �������";

	private static final String RBBRCHID_REGEXP = "[0-9]{6}";

	private static final List<String> virtualBINs = new ArrayList<String>();

	private static final List<String> filterBlockedCards = new ArrayList<String>();

	static
	{
		filterBlockedCards.add("N - ������� ������".toUpperCase().replace(" ", ""));
		filterBlockedCards.add("Y - ����� / ����������".toUpperCase().replace(" ", ""));
		filterBlockedCards.add("y - NO PBT - ����� / ����������".toUpperCase().replace(" ", ""));
		filterBlockedCards.add("Q - ���� ������".toUpperCase().replace(" ", ""));
		virtualBINs.add("427432");
		virtualBINs.add("547932");
	}

	/**
	 * ���������� �������� ������� �� ������
	 * @param ifxRs - ���������� �����
	 * @param cards - ������ �������� �� ������� ����������� �������
	 * @return ��������������� ��������� <������, �������>.
	 */
	public GroupResult<Card, AbstractBase> fillGroupCardAbstract(IFXRs_Type ifxRs, Card... cards) throws GateException
	{
		if (cards == null)
			return null;
		CCAcctExtStmtInqRs_Type cCAcctExtStmtInqRs = ifxRs.getCCAcctExtStmtInqRs();
		CCAcctExtStmtInqRs_TypeCardAcctRec[] recs = cCAcctExtStmtInqRs.getCardAcctRec();

		IKFLException exception = checkResponse(recs);
		if (exception != null)
		{
			log.error(exception);
			return GroupResultHelper.getOneErrorResult(cards, exception);
		}
		GroupResult<Card, AbstractBase> res = new GroupResult<Card, AbstractBase>();
		for (CCAcctExtStmtInqRs_TypeCardAcctRec rec : recs)
		{
			Card obj = null;
			try
			{
				obj = findObject(rec.getCardAcctId().getCardNum(), cards);
			}
			catch (GateException e)
			{
				log.error(e.getMessage(), e);
				continue;
			}

			Status_Type status = rec.getStatus();
			long statusCode = status.getStatusCode();
			CardAbstractImpl cardAbstract = new CardAbstractImpl();
			if (statusCode != CORRECT_MESSAGE_STATUS)
			{
				ESBERIBExceptionStatisticHelper.addError(res, obj, status, CCAcctExtStmtInqRs_TypeCardAcctRec.class, EntityIdHelper.getCardCompositeId(obj));
				continue;
			}

			// ��� ��������� ���������� �� �������� ����  AcctBal = "��� �������, ��� �������"
			if (statusCode == CORRECT_MESSAGE_STATUS)
			{
				AcctBal_Type[] acctBals = rec.getAcctBal();
				for (AcctBal_Type acctBal : acctBals)
				{
					if (acctBal.getBalType().equals(LimitType.Avail.toString()))
					{
						Currency currency = getCurrencyByString(acctBal.getAcctCur());
						if (currency != null)
						{
							Money limit = safeCreateMoney(acctBal.getCurAmt(),currency);
							cardAbstract.setClosingBalance(limit);
						}
					}
				}
			}
			List<TransactionBase> transactions = fillTransactions(rec.getCCAcctStmtRec(), obj);
			if (transactions != null && !transactions.isEmpty())
				cardAbstract.setTransactions(transactions);
			res.putResult(obj, cardAbstract);
		}
		return res;
	}

	/**
	 * ��������� ������� ��������������� ���� �������. ������������ ��������� GFL
	 * @param ifxRq ������
	 * @param ifxRs - ����� ����
	 * @param clientId - ������������� ������ �������
	 * @return ������ ������� ��������������� ����
	 * @throws GateLogicException
	 */
	public String[] getCardIds(IFXRq_Type ifxRq, IFXRs_Type ifxRs, Long clientId) throws GateLogicException
	{
		Status_Type status = ifxRs.getBankAcctInqRs().getStatus();
		if (status.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			log.error("��������� ��������� � �������. ������ ����� " + status.getStatusCode() + ". ID ������� "
					+ clientId + ". " + status.getStatusDesc());
			ESBERIBExceptionStatisticHelper.throwErrorResponse(status, BankAcctInqRs_Type.class, ifxRq);
		}
		CardAcctRec_Type[] cardAcctRecs = ifxRs.getBankAcctInqRs().getCardAcctRec();
		List<String> cardIds = new ArrayList<String>(cardAcctRecs.length);
		if (cardAcctRecs != null)
		{
			for (CardAcctRec_Type cardAcctRec : cardAcctRecs)
			{
				BankAcctStatus_Type bankAcctStatus = cardAcctRec.getBankAcctStatus();
				CardState cardState = (bankAcctStatus == null) ? null :
						CardStateWrapper.getCardType(bankAcctStatus.getBankAcctStatusCode());
				Calendar expireDate = (StringHelper.isEmpty(cardAcctRec.getCardAcctId().getEndDt())) ? null :
						parseCalendar(cardAcctRec.getCardAcctId().getEndDt());
				if (!checkOldBlockedCards(cardState, expireDate, (bankAcctStatus == null) ? null : bankAcctStatus.getStatusDesc()))
				{
					cardIds.add(EntityIdHelper.createCardCompositeId(cardAcctRec, clientId));
				}
			}
		}
		return cardIds.toArray(new String[cardIds.size()]);
	}

	/**
	 * ���������� ������ ����, ����������� �� GFL �������
	 * @param ifxRs - ���������� �����
	 * @param clientId - Id ������� �������� ����������� �����
	 * @return ������ ����
	 */
	public List<Pair<Card, AdditionalProductData>>  fillCards(IFXRs_Type ifxRs, Long clientId)
	{
		if (ifxRs == null)
			return Collections.emptyList();

		Status_Type status = ifxRs.getBankAcctInqRs().getStatus();
		if (status.getStatusCode() != CORRECT_MESSAGE_STATUS)
			return Collections.emptyList();
		CardAcctRec_Type[] cardAcctRecs = ifxRs.getBankAcctInqRs().getCardAcctRec();
		if (cardAcctRecs == null || cardAcctRecs.length == 0)
			return Collections.emptyList();

		List<Pair<Card, AdditionalProductData>>  cards = new ArrayList<Pair<Card, AdditionalProductData>> (cardAcctRecs.length);
		for (CardAcctRec_Type cardAcctRec : cardAcctRecs)
		{
			try
			{
				CardImpl card = fillCardFromGFL(cardAcctRec,clientId);

				//�������� bankInfo �� "������������" : � ������� ENH031378
				// + ��� ����������� �� ����������� �� ��� �����
				BankAcctStatus_Type bankAcctStatus = cardAcctRec.getBankAcctStatus();
				if (checkBankInfo(cardAcctRec) && isAccessCardByBankInfo(cardAcctRec.getBankInfo(), cardAcctRec.getCardAcctId().getCardNum()) )
				{
					//���� ����� �� �������������, � � ���� �������� �� ����� ����� ���� �����
					if (!checkOldBlockedCards(
							// ������ ������ ���� � ������ ����
							card.getCardState(), card.getExpireDate(), bankAcctStatus != null ? bankAcctStatus.getStatusDesc() : null))
					{

						card.setId(EntityIdHelper.createCardCompositeId(cardAcctRec, clientId));
						cards.add(new Pair<Card, AdditionalProductData>(card, fillProductOTPRestriction(cardAcctRec.getOTPRestriction())));
					}
				}
			}
			catch (Exception e)
			{
				if (cardAcctRec != null && cardAcctRec.getCardAcctId() != null)
					log.error("������ ��� ���������� ����� �" +
							maskCardNumber(cardAcctRec.getCardAcctId().getCardNum()), e);
				else
					log.error("������ ��� ���������� �����", e);
			}
		}

		checkAdditionalCards(cards);
		return cards;
	}


	/**
	 * ��������� ������ OTPRestriction
	 * @param restriction
	 * @return
	 */
	protected OTPRestriction fillProductOTPRestriction(OTPRestriction_Type restriction)
	{
		if (restriction == null)
			return null;

		OTPRestrictionImpl result = new OTPRestrictionImpl();
		result.setOTPGet(restriction.isOTPGet());
		result.setOTPUse(restriction.getOTPUse());
		return result;
	}

	/**
	 * ���������� ��������� ���������� �� ������ (�� ������ CRDWI)
	 * @param ifxRs - ����� ���� (��������� CRDWI ��������� ��������� ���������� � �����)
	 * @param cardIds - ������ ��������������� ����, �� ������� ����������� ����������
	 * @return ��������� ���������� �� ������
	 */
	public GroupResult<String, Card> fillCardsByIds(IFXRs_Type ifxRs, String... cardIds)
	{
		GroupResult<String, Card> res = new GroupResult<String, Card>();
		CardAcctDInqRs_Type cardAcctDInqRs = ifxRs.getCardAcctDInqRs();
		CardAcctRec_Type[] cardAcctRecs = cardAcctDInqRs.getBankAcctRec();
		IKFLException exception = checkResponse(cardAcctRecs);
		if (exception != null)
		{
			log.error(exception);
			return GroupResultHelper.getOneErrorResult(cardIds, exception);
		}

		for (CardAcctRec_Type cardAcctRec : cardAcctRecs)
		{
			CardAcctId_Type cardAcctId = cardAcctRec.getCardAcctId();
			String cardNum = cardAcctId.getCardNum();
			String cardId = findId(cardNum, cardIds);

			try
			{
				Status_Type status = cardAcctRec.getStatus();
				// ���������� ������, �� ��� �� �������� �� �������
				if (status.getStatusCode() == CORRECT_MESSAGE_STATUS && !isAccessCardByBankInfo(cardAcctId.getBankInfo(), cardNum))
				{
					// ��������� ������ "�� ������� ���������� �� �����".
					ESBERIBExceptionStatisticHelper.addError(
							res, cardId, getMockNotFoundCardInfoStatus(cardNum), CardAcctRec_Type.class, EntityIdHelper.getCardOrAccountCompositeId(cardId));
				}
				else if(status.getStatusCode() == CORRECT_MESSAGE_STATUS)
				{
					CardImpl card = fillCardFromCRDWI(cardAcctRec, EntityIdHelper.getCommonCompositeId(cardId).getLoginId());

					BankInfo_Type bankInfo = cardAcctId.getBankInfo();
					EntityCompositeId compositeId = EntityIdHelper.getCommonCompositeId(cardId);
					String systemId = compositeId.getSystemId();
					String rbBrchId = compositeId.getRbBrchId();
					String cardCompositeId = EntityIdHelper.createCardOrAccountCompositeId(cardAcctId.getCardNum(),
							systemId == null ? "" : systemId,
							bankInfo == null ? "" : rbBrchId,
							compositeId.getLoginId(),
							bankInfo == null ? "" : bankInfo.getRegionId(),
							bankInfo == null ? "" : bankInfo.getBranchId(),
							bankInfo == null ? "" : bankInfo.getAgencyId());
					card.setId(cardCompositeId);
					card.setUNIAccountType(cardAcctId.getUNIAcctType());
					card.setUNICardType(cardAcctId.getUNICardType());

					res.putResult(cardId, card);
					StoredResourcesService storedResourcesService = GateSingleton.getFactory().service(StoredResourcesService.class);
					storedResourcesService.updateStoredCard(compositeId.getLoginId(), card);
				}
				else
				{
					//�������, ��� ��� ������������ ����� ����� (�� ������������ ��� CardAcctId ������������ � ������ ������)
					if (OFFLINE_SYSTEM_STATUSES.contains(status.getStatusCode()))
						ESBERIBExceptionStatisticHelper.addOfflineError(res, cardId, status, CardAcctRec_Type.class, EntityIdHelper.getCardOrAccountCompositeId(cardId));
					else
						ESBERIBExceptionStatisticHelper.addError(res, cardId, status, CardAcctRec_Type.class, EntityIdHelper.getCardOrAccountCompositeId(cardId));
					log.error("��������� ��������� � �������. ������ ����� " + status.getStatusCode() + ". ����� "
							+ cardId + ". " + status.getStatusDesc());
				}
			}
			catch (GateLogicException e)
			{
				res.putException(cardId, e);
			}
			catch (GateException e)
			{
				res.putException(cardId, e);
			}
            catch (ParseException e)
            {
                res.putException(cardId, new GateException(e));
		    }
		}
		return res;
	}

	/**
	 * ���������� ���������� �� ������� - ��������� �����
	 * @param ifxRs - ���������� ����� (��������� CRDWI ��������� ��������� ���������� � �����)
	 * @param cards �����
	 * @return ��������� ���������� �� ��������.
	 */
	public GroupResult<Card, Client> fillClient(IFXRs_Type ifxRs, Card... cards)
	{
		GroupResult<Card, Client> res = new GroupResult<Card, Client>();
		CardAcctRec_Type[] cardAcctRecs = ifxRs.getCardAcctDInqRs().getBankAcctRec();

		for (CardAcctRec_Type cardAcctRec : cardAcctRecs)
		{
			CardAcctId_Type cardAcctId = cardAcctRec.getCardAcctId();
			Card card = null;
			try
			{
				card = findObject(cardAcctId.getCardNum(), cards);
			}
			catch (GateException e)
			{
				log.error(e.getMessage(), e);
				continue;
			}

			Status_Type status = cardAcctRec.getStatus();
			if (status.getStatusCode() == CORRECT_MESSAGE_STATUS && !isAccessCardByBankInfo(cardAcctId.getBankInfo(), card.getNumber()))
			{
				ESBERIBExceptionStatisticHelper.addError(res, card, getMockNotFoundCardInfoStatus(card.getNumber()), CardAcctRec_Type.class, EntityIdHelper.getCardCompositeId(card));
			}
			else if (status.getStatusCode() == CORRECT_MESSAGE_STATUS)
			{
				ClientImpl client = fillClient(cardAcctId.getCustInfo());
				res.putResult(card, client);
			}
			else
			{
				//�������, ��� ��� ������������ ����� ����� (�� ������������ ��� CardAcctId ������������ � ������ ������)
				ESBERIBExceptionStatisticHelper.addError(res, card, status, CardAcctRec_Type.class, EntityIdHelper.getCardCompositeId(card));
				log.error("��������� ��������� � �������. ������ ����� " + status.getStatusCode() + ". ����� � "
						+ maskCardNumber(card.getNumber()) + ". " + status.getStatusDesc());
			}
		}

		return res;
	}

	/**
	 * ���������� ���������� �� ������� - ��������� �����
	 * @param ifxRs - ���������� ����� (��������� CRDWI ��������� ��������� ���������� � �����)
	 * @param cardDetails ���������� �� ������
	 * @return ��������� ���������� �� ��������.
	 */
	public GroupResult<Pair<String, Office>, Client> fillClient(IFXRs_Type ifxRs, Pair<String, Office> ... cardDetails)
	{
		GroupResult<Pair<String, Office>, Client> responce = new GroupResult<Pair<String, Office>, Client>();
		CardAcctRec_Type[] cardAcctRecs = ifxRs.getCardAcctDInqRs().getBankAcctRec();

		for (CardAcctRec_Type cardAcctRec : cardAcctRecs)
		{
			try
			{
				CardAcctId_Type cardAcctId = cardAcctRec.getCardAcctId();
				String cardNum = cardAcctId.getCardNum();
				Pair<String, Office> cardInfo = findObjectInfo(cardNum, cardDetails);

				Status_Type status = cardAcctRec.getStatus();
				if (cardAcctRec.getStatus().getStatusCode() != CORRECT_MESSAGE_STATUS)
				{
					//�������, ��� ��� ������������ ����� ����� (�� ������������ ��� CardAcctId ������������ � ������ ������)
					ESBERIBExceptionStatisticHelper.addError(responce, cardInfo,status, CardAcctRec_Type.class);
					log.error("��������� ��������� � �������. ������ ����� " + status.getStatusCode() + ". ����� � " + maskCardNumber(cardInfo.getFirst()) + ". " + status.getStatusDesc());
					continue;
				}

				if(!isAccessCardByBankInfo(cardAcctId.getBankInfo(), cardNum))
				{
					Status_Type mockStatus = getMockNotFoundCardInfoStatus(cardNum);
					ESBERIBExceptionStatisticHelper.addError(responce, cardInfo, mockStatus, CardAcctRec_Type.class);
					log.error("�������� ��������� � �������. ������ ����� " + mockStatus.getStatusCode() + ". ����� � " + maskCardNumber(cardInfo.getFirst()) + ". " + mockStatus.getStatusDesc());
					continue;
				}

				ClientImpl client = fillClient(cardAcctId.getCustInfo());
				responce.putResult(cardInfo, client);
			}
			catch (GateException e)
			{
				log.error(e.getMessage(), e);
			}
		}

		return responce;
	}

	/**
	 * ���������� ��������� ���������� �� ������ ��� ������ getCardByNumber
	 * @param ifxRs - ����� (��������� CRDWI ��������� ��������� ���������� � �����)
	 * @param client - ������ ������������ ���� � �����
	 * @param numberAndOffice ����� �����, ����
	 * @return ��������� ����������
	 */
	public GroupResult<Pair<String, Office>, Card> fillCardsByNum(IFXRs_Type ifxRs, Client client, Pair<String, Office> numberAndOffice) throws GateException
	{
		GroupResult<Pair<String, Office>, Card> res = new GroupResult<Pair<String, Office>, Card>();

		CardAcctDInqRs_Type cardAcctDInqRs = ifxRs.getCardAcctDInqRs();
		CardAcctRec_Type[] cardAcctRecs = cardAcctDInqRs.getBankAcctRec();

		Set<Long> cardNotFoundCodes = ConfigFactory.getConfig(ESBEribConfig.class).getCardNotFoundCodes();
		for (CardAcctRec_Type cardAcctRec : cardAcctRecs)
		{
			try
			{
				Status_Type status = cardAcctRec.getStatus();
				CardAcctId_Type cardAcctId = cardAcctRec.getCardAcctId();
				// ���������� ������, �� ��� �� �������� �� �������
				if (status.getStatusCode() == CORRECT_MESSAGE_STATUS && !isAccessCardByBankInfo(cardAcctId.getBankInfo(), numberAndOffice.getFirst()))
				{
					String cardNum = numberAndOffice.getFirst();
					// ��������� ������ "�� ������� ���������� �� �����".
					ESBERIBExceptionStatisticHelper.addError(
							res, new Pair<String, Office>(cardNum, null), getMockNotFoundCardInfoStatus(cardNum), CardAcctRec_Type.class, cardAcctId.getSystemId());
				}
				else if(status.getStatusCode() == CORRECT_MESSAGE_STATUS)
				{
					CardImpl card = fillCardFromCRDWI(cardAcctRec, client.getInternalOwnerId());
					card.setId(EntityIdHelper.createCardCompositeId(cardAcctId, client.getInternalOwnerId()));
					Pair<String, Office> pair = new Pair<String, Office>(card.getNumber(), card.getOffice());
					res.putResult(pair, card);
				}
				else if (cardNotFoundCodes.contains(status.getStatusCode()))
				{
					Pair<String, Office> pair = new Pair<String, Office>(numberAndOffice.getFirst(), null);
					res.putResult(pair, null);
				}
				else
				{
					//�������, ��� ��� ������������ ����� ����� (�� ������������ ��� CardAcctId ������������ � ������ ������)
					Pair<String, Office> pair = new Pair<String, Office>(numberAndOffice.getFirst(), null);
					String systemId = cardAcctId == null ? null : cardAcctId.getSystemId();
					ESBERIBExceptionStatisticHelper.addError(res, pair, status, CardAcctRec_Type.class, systemId);
					log.error("��������� ��������� � �������. ������ ����� " + status.getStatusCode() + ". ����� � "
							+ maskCardNumber(numberAndOffice.getFirst()) + ". " + status.getStatusDesc());
				}
			}
			catch (GateLogicException e)
			{
				Pair<String, Office> pair = new Pair<String, Office>(cardAcctRec.getCardAcctId().getCardNum(), null);
				res.putException(pair, e);
			}
			catch (GateException e)
			{
				Pair<String, Office> pair = new Pair<String, Office>(cardAcctRec.getCardAcctId().getCardNum(), null);
				res.putException(pair, e);
			}
            catch (ParseException e)
			{
				Pair<String, Office> pair = new Pair<String, Office>(cardAcctRec.getCardAcctId().getCardNum(), null);
				res.putException(pair, new GateException(e));
			}
		}
		return res;
	}

	/**
	 *
	 * @param cardAcctRec - ���������� �� �����
	 * @return Account - ����
	 * @throws GateLogicException
	 * @throws GateException
	 */
	private AccountImpl getAccount(CardAcctRec_Type cardAcctRec, Long loginId, Card card) throws GateLogicException, GateException
	{
		AccountImpl account = new AccountImpl();
		AccountResponseSerializer accountResponseSerializer = new AccountResponseSerializer();
		account.setOffice(accountResponseSerializer.getOffice(cardAcctRec.getBankInfo()));
		CardAcctId_Type cardAcctId = cardAcctRec.getCardAcctId();
		account.setId(EntityIdHelper.createCardOrAccountCompositeId(cardAcctRec,loginId,false));
		account.setNumber(cardAcctId.getAcctId());
		account.setKind(cardAcctId.getAcctCode());
		account.setSubKind(cardAcctId.getAcctSubCode());
		account.setDescription(cardAcctId.getCardName());
		account.setCurrency(getCurrencyByString(cardAcctId.getAcctCur()));
		account.setOpenDate(card.getIssueDate());
		return account;
	}


	/**
	 * ���������� ��������� ���������� �� ���
	 * @param ifxRs - ����� ���� (��������� GFL)
	 * @param cards - ������ ����
	 * @param loginId - ������������� ������
	 * @return ��������������� ��������� �� ���
	 */
	public GroupResult<Card, Account> fillGroupCardPrimaryAccount(IFXRs_Type ifxRs, Card[] cards, Long loginId)
	{
		GroupResult<Card, Account> res = new GroupResult<Card, Account>();
		CardAcctRec_Type[] cardAcctRecs = ifxRs.getBankAcctInqRs().getCardAcctRec();
		if (cardAcctRecs == null || cardAcctRecs.length == 0)
			return null;
		for (Card card : cards)
		{
			try
			{
				CardAcctRec_Type cardAcctRec = findCardAcctRec(card.getNumber(), cardAcctRecs);
				if (cardAcctRec == null)
					res.putException(card, new GateLogicException("�� ��������� ���������� �� ����� �" +
							maskCardNumber(card.getNumber())));
				else if (doesCardHasPrimaryAccount(cardAcctRec))
				{
					AccountImpl account = getAccount(cardAcctRec,loginId,card);
					res.putResult(card, account);
				}
				else
					res.putResult(card, null);
			}
			catch (GateLogicException e)
			{
				res.putException(card, e);
			}
			catch (GateException e)
			{
				res.putException(card, e);
			}
		}
		return res;
	}

	/**
	 * ���������� ������ ���. ����
	 * @param ifxRs ����� ����
	 * @param mainCards ������ �������� ����
	 * @return ��������� ����������
	 */
	public GroupResult<Card, List<Card>> fillAdditionalCards(IFXRs_Type ifxRs, Card... mainCards)
	{
		AdditionalCardInfo_Type[] additionalCardInfos = ifxRs.getCardAdditionalInfoRs().getAdditionalCardInfo();
		if (ArrayUtils.isEmpty(additionalCardInfos))
			return null;

		GroupResult<Card, List<Card>> res = new GroupResult<Card, List<Card>>();

		for (AdditionalCardInfo_Type additionalCardInfo : additionalCardInfos)
		{
			Card mainCard = null;
			try
			{
				mainCard = findObject(additionalCardInfo.getCardNum(), mainCards);
			}
			catch (GateException e)
			{
				log.error(e.getMessage(), e);
				continue;
			}

			Status_Type status = additionalCardInfo.getStatus();
			if (status.getStatusCode() == CORRECT_MESSAGE_STATUS)
			{
				AdditionalCardInfo_TypeCards[] additionalCardInfoCards = additionalCardInfo.getCards();

				if (ArrayUtils.isEmpty(additionalCardInfoCards))
					continue;

				List<Card> additionalCards = new ArrayList<Card>(0);
				for (AdditionalCardInfo_TypeCards additionalCardInfoCard : additionalCardInfo.getCards())
				{
					CardImpl card = new CardImpl();
					card.setNumber(additionalCardInfoCard.getCardNum());
					additionalCards.add(card);
				}
				res.putResult(mainCard, additionalCards);
			}
			else
			{
				ESBERIBExceptionStatisticHelper.addError(res, mainCard, status, AdditionalCardInfo_Type.class, EntityIdHelper.getCardCompositeId(mainCard));
				log.error("��������� ��������� � �������. ������ ����� " + status.getStatusCode() + ". ����� � "
						+ maskCardNumber(additionalCardInfo.getCardNum()) + ". " + status.getStatusDesc());
			}
		}

		return res;
	}

	/**
	 * @param cardAcctRec
	 * @return true, ���� � ����� ���� ���
	 */
	private boolean doesCardHasPrimaryAccount(CardAcctRec_Type cardAcctRec)
	{
		CardAcctId_Type cardAcctId = cardAcctRec.getCardAcctId();
		return !StringHelper.isEmpty(cardAcctId.getAcctId());
	}

	/**
	 * ���������� �������������� �������� ��������
	 * @param stmtSummType - ��� ��������. Authorization - ���������� �������, Financial - ���������� �������� ��� ����������
	 * @param trnDesc - �������� ����������
	 * @return �������� ��������
	 */
	private static String getDescription(String stmtSummType, String trnDesc)
	{
		//�� ������������ � ������� ���������, �������� Authorization (����� ����� ������ � ������� ��������) �������� "���������� ������� �� �����".
		return ((stmtSummType.equalsIgnoreCase("Authorization") || stmtSummType.equalsIgnoreCase("�����������")) && StringHelper.isEmpty(trnDesc)) ? "���������� ������� �� �����" : trnDesc;
	}

	/*
	���������� ���������� ����������
	 */
	private List<TransactionBase> fillTransactions(CCAcctStmtRec_Type[] cCAcctStmtRecs, Object obj) throws GateException
	{
		if (cCAcctStmtRecs == null || cCAcctStmtRecs.length == 0)
		{
			return null;
		}
		List<TransactionBase> transactions = new ArrayList<TransactionBase>();
		for (CCAcctStmtRec_Type cCAcctStmtRec : cCAcctStmtRecs)
		{
			CardOperationImpl cardOperation = new CardOperationImpl();
			//��� ��� ����� ������ �� ��������? �� ����� �������, ����� ��� ����� ������ ����������.
			cardOperation.setOperationCard((Card) obj);

			Calendar opDate = parseCalendar(cCAcctStmtRec.getEffDate());
			cardOperation.setDate(opDate);
			cardOperation.setOperationDate(opDate);

			StmtSummAmt_Type stmtSummAmt = cCAcctStmtRec.getStmtSummAmt();
			if (stmtSummAmt == null || stmtSummAmt.getCurAmt() == null)
				throw new GateException("�� ������ ����� ��������.");
			Currency stmtSummCurrency = getCurrencyByString(stmtSummAmt.getAcctCur());
			//����� ��������
			Money stmtSumm = safeCreateMoney(stmtSummAmt.getCurAmt().abs(),stmtSummCurrency);

			//����� �������� � ������ �����
			CCAcctStmtRec_TypeOrigCurAmt origCurAmt = cCAcctStmtRec.getOrigCurAmt();
			if (origCurAmt == null || origCurAmt.getCurAmt() == null)
				throw new GateException("�� ������ ����� �������� � ������ ����� �����.");
			Currency origCurCurrency = getCurrencyByString(origCurAmt.getAcctCur());
			//����� �������� � ������ �����
			Money origAmt = safeCreateMoney(origCurAmt.getCurAmt().abs(),origCurCurrency);

			//True- �������� ����������, False � �������� ��������. �� ������������ ���.
			//������ ������� ��������. false - ����������, true - ��������.
			boolean isDebitOperation = !cCAcctStmtRec.getIsDebit();

			if (isDebitOperation)
			{
				cardOperation.setCreditSum(stmtSumm);
				cardOperation.setAccountCreditSum(origAmt);
			}
			else
			{
				cardOperation.setDebitSum(stmtSumm);
				cardOperation.setAccountDebitSum(origAmt);
			}
			cardOperation.setShopInfo(cCAcctStmtRec.getTrnSrc());

			cardOperation.setDescription(getDescription(stmtSummAmt.getStmtSummType(), cCAcctStmtRec.getTrnDesc()));

			transactions.add(cardOperation);
		}
		return transactions;
	}

	/**
	 * ���������� ���������� �� ����� ����� (��� CRDWI)
	 */
	private CardImpl fillCardFromCRDWI(CardAcctRec_Type cardAcctRec, Long loginId) throws GateLogicException, GateException, ParseException
	{
		CardImpl card = new CardImpl();
		// ��� CRDWI ���� ����� - ��� CardAcctRec->CardAcctId->BankInfo
		setCardMainInfo(card, cardAcctRec.getCardAcctId(), cardAcctRec.getCardAcctId().getBankInfo());
		fillCard(card, cardAcctRec, false, loginId);
		return card;
	}

	/**
	 * ���������� ���������� �� ����� ����� (��� GFL)
	 */
	private CardImpl fillCardFromGFL(CardAcctRec_Type cardAcctRec, Long loginId) throws GateLogicException, GateException, ParseException
	{
		CardImpl card = new GFLCard();
		// ��� GFL ���� ����� - ��� CardAcctRec->BankInfo 
		setCardMainInfo(card, cardAcctRec.getCardAcctId(), cardAcctRec.getBankInfo());
		fillCard(card, cardAcctRec, true, loginId);
		return card;
	}

	private void fillCard(CardImpl card, CardAcctRec_Type cardAcctRec, boolean fillFromGFL, Long loginId) throws GateLogicException, GateException, ParseException
	{
		setStatus(card, cardAcctRec);

		//�������������� ���������� �� �����
		setAdditionalCardInfo(card, cardAcctRec);

		//������. ������ ������� ����� ������ ������ �����
		setLimits(card, cardAcctRec);

		CardAcctId_Type cardAcctId = cardAcctRec.getCardAcctId();
		//����������� �����. ������������� �������� �� ����� �����������.
		setVirtual(card, cardAcctId);

		//���������� ��� �� �����
		setPrimaryAccount(card, cardAcctRec, fillFromGFL, loginId);

		setCardInfo(card, cardAcctRec.getCardAcctId());

		if (cardAcctId != null && StringHelper.isNotEmpty(cardAcctId.getPmtDt()))
			card.setOverdraftMinimalPaymentDate(parseCalendar(cardAcctRec.getCardAcctId().getPmtDt()));

		if (cardAcctId != null && cardAcctId.getCustInfo() != null)
			card.setCardClient(fillClient(cardAcctId.getCustInfo()));
	}

	/**
	 * ���������� ��������� ���������� �� �����
	 * @param card
	 * @param cardAcctId
	 */
	private void setCardInfo(CardImpl card, CardAcctId_Type cardAcctId)
	{
		card.setUNIAccountType(cardAcctId.getUNIAcctType());
		card.setUNICardType(cardAcctId.getUNICardType());
	}

	private void setVirtual(CardImpl card, CardAcctId_Type cardAcctId)
	{
		String bin = cardAcctId.getCardNum().substring(0, 6);
		card.setVirtual( virtualBINs.contains(bin) );
	}
	/*
	���������� �������� ���������� �� ������
	 */
	private void setCardMainInfo(CardImpl card, CardAcctId_Type cardAcctId, BankInfo_Type bankInfo) throws GateLogicException, GateException
	{
		card.setId(cardAcctId.getCardNum());
		card.setDescription(cardAcctId.getCardName());
		card.setNumber(cardAcctId.getCardNum());

		if (!StringHelper.isEmpty(cardAcctId.getIssDt()))
			card.setIssueDate(parseCalendar(cardAcctId.getIssDt()));
		if (!StringHelper.isEmpty(cardAcctId.getEndDt()))
			card.setExpireDate(parseCalendar(cardAcctId.getEndDt()));

		card.setCardType(CardTypeWrapper.getCardType(cardAcctId.getCardType()));
		card.setCardLevel(CardLevelWrapper.getCardLevel(cardAcctId.getCardLevel()));
		card.setCardBonusSign(CardBonusSignWrapper.getCardBonusSign(cardAcctId.getCardBonusSign()));

		card.setOffice(getOffice(bankInfo));
		card.setNotCorrectedTbCode(getOriginalTbCode(bankInfo));

		card.setCurrency(getCurrencyByString(cardAcctId.getAcctCur()));
		setContactInfo(card, cardAcctId);
		card.setContractNumber(cardAcctId.getContractNumber());
	}

	private void setContactInfo(CardImpl card, CardAcctId_Type cardAcctId)
	{
		if (cardAcctId.getCustInfo() != null && cardAcctId.getCustInfo().getPersonInfo() != null)
		{
			String holderName = getPersonFullName(cardAcctId.getCustInfo().getPersonInfo().getPersonName());
			card.setHolderName(holderName);

			ContactInfo_Type contactInfo = cardAcctId.getCustInfo().getPersonInfo().getContactInfo();
			if (contactInfo == null)
				return;

			card.setUseReportDelivery(MessageDeliveryTypeWrapper.fromGate(contactInfo.getMessageDeliveryType()));
			card.setEmailAddress(StringUtils.trimToNull(contactInfo.getEmailAddr()));
			card.setReportDeliveryType(ReportDeliveryTypeWrapper.fromGate(contactInfo.getReportDeliveryType()));
			card.setReportDeliveryLanguage(ReportDeliveryLanguageWrapper.fromGate(contactInfo.getReportLangType()));
		}
		else
			card.setHolderName(cardAcctId.getCardHolder());
	}

	/*
	���������� ������� �����
	 */
	private void setStatus(CardImpl card, CardAcctRec_Type bankAcctRec)
	{
		BankAcctStatus_Type bankAcctStatus = bankAcctRec.getBankAcctStatus();
		CardStatusEnum_Type cardAcctStatus = bankAcctRec.getCardAcctId().getStatus();
		if (cardAcctStatus == CardStatusEnum_Type.Arrested)
			card.setCardAccountState(AccountState.ARRESTED);

		if (bankAcctStatus != null)
		{
			card.setCardState(CardStateWrapper.getCardType(bankAcctStatus.getBankAcctStatusCode()));

			Pair<StatusDescExternalCode, String> pairDesc =
					CardExternalCodeWrapper.parseDescription(bankAcctStatus.getStatusDesc());

			card.setStatusDescExternalCode(pairDesc.getFirst());
			card.setStatusDescription(pairDesc.getSecond());
		}
	}

	/*
	���������� �������� ��������/�������������� ��� �����
	 */
	private void setAdditionalCardInfo(CardImpl card, CardAcctRec_Type cardAcctRec) throws ParseException
	{
		CardAcctInfo_Type cardAcctInfo = cardAcctRec.getCardAcctInfo();
		if (cardAcctInfo == null || cardAcctInfo.getAdditionalCard() == null)
		{
			card.setMain(true);
		}
		else
		{
			card.setMain(false);
			String mainCard = cardAcctInfo.getMainCard();
			if (!StringHelper.isEmpty(mainCard) && mainCard.matches("\\d+"))
				card.setMainCardNumber(cardAcctInfo.getMainCard());
			else
				log.error("��� �������������� ����� " + maskCardNumber(card.getNumber()) +
						" ������� ������������ ����� �������� �����: " + maskCardNumber(mainCard));
			card.setAdditionalCardType(AdditionalCardTypeWrapper.getAdditionalCardType(cardAcctInfo.getAdditionalCard()));
		}

		if (cardAcctInfo != null)
		{
			if (ConfigFactory.getConfig(ESBEribConfig.class).isUseOldMethodsCHG032182())
			{
				card.setDisplayedExpireDate(DateHelper.toDisplayedExpiredate(card.getExpireDate()));
			}
			else
			{
				String endDateForWay = cardAcctInfo.getEndDtForWay();
				if ( StringHelper.isEmpty(endDateForWay) )
				{
					log.warn("���� endDtForWay(���� �������� �����) �� ������.");
					card.setDisplayedExpireDate(null);
				}
				else
				{
					card.setDisplayedExpireDate(endDateForWay);
				}
			}
            if (cardAcctInfo.getNextReportDate() != null)
            {
                card.setNextReportDate(parseCalendar(cardAcctInfo.getNextReportDate()));
			}
		}
	}

	/*
	������� ����������� ������� �����
	 */
	private void setLimits(CardImpl card, CardAcctRec_Type bankAcctRec)
	{
		AcctBal_Type[] acctBals = bankAcctRec.getAcctBal();
		if (!(acctBals != null && acctBals.length > 0))
			return;

		boolean availPmtToAvail = !card.isMain() && ConfigFactory.getConfig(ESBEribConfig.class).isRewriteAvailWithAvailPmnt();

		for (AcctBal_Type acctBal : acctBals)
		{
			if (acctBal.getBalType().equals(LimitType.Avail.name()))
			{
				if (!availPmtToAvail)
				{
					Money limit = safeCreateMoney(acctBal.getCurAmt(), card.getCurrency());
					card.setAvailableLimit(limit);
				}
			}
			if (acctBal.getBalType().equals(LimitType.AvailPmt.toString()))
			{
				Money purchaseLimit = safeCreateMoney(acctBal.getCurAmt(), card.getCurrency());
				card.setPurchaseLimit(purchaseLimit);
				if (availPmtToAvail)
					card.setAvailableLimit(purchaseLimit);
			}
			if (acctBal.getBalType().equals(LimitType.AvailCash.toString()))
			{
				Money availableCashLimit = safeCreateMoney(acctBal.getCurAmt(), card.getCurrency());
				card.setAvailableCashLimit(availableCashLimit);
			}
			if (acctBal.getBalType().equals(LimitType.CR_LIMIT.toString()))
			{
				Money limit = safeCreateMoney(acctBal.getCurAmt(), card.getCurrency());
				card.setOverdraftLimit(limit);
			}
			if (acctBal.getBalType().equals(LimitType.Debt.toString()))
			{
				Money totalDebtSum = safeCreateMoney(acctBal.getCurAmt(), card.getCurrency());
				card.setOverdraftTotalDebtSum(totalDebtSum);
			}
			if (acctBal.getBalType().equals(LimitType.MinPmt.toString()))
			{
				Money minimalPayment = safeCreateMoney(acctBal.getCurAmt(), card.getCurrency());
				card.setOverdraftMinimalPayment(minimalPayment);
			}
			if (acctBal.getBalType().equals(LimitType.OWN_BALANCE.toString()))
			{
				Money ownSum = safeCreateMoney(acctBal.getCurAmt(), card.getCurrency());
				card.setOverdraftOwnSum(ownSum);
			}
		}
	}

	private CardAcctRec_Type findCardAcctRec(String cardNumber, CardAcctRec_Type[] cardAcctRecs)
	{
		for (CardAcctRec_Type cardAcctRec : cardAcctRecs)
		{
			if (cardAcctRec.getCardAcctId().getCardNum().equals(cardNumber))
				return cardAcctRec;
		}
		return null;
	}

	/**
	 * ���������, ����� �� ��������� ��� ����� �� ����������� ������� GFL
	 * @param cardState ��������� �����
	 * @param expireDate ���� ���������
	 * @param statusDescription �������� �������
	 * @return true - ����� ������, ����� �������������, false - ����� ����������� �� �����
	 */
	private boolean checkOldBlockedCards(CardState cardState, Calendar expireDate, String statusDescription)
	{
		if (cardState != null && cardState == CardState.blocked)
		{
			if (expireDate != null && expireDate.before(DateHelper.getPreviousYear(DateHelper.getCurrentDate())))
				return true;
			if (statusDescription != null && filterBlockedCards.contains(statusDescription.toUpperCase().replace(" ", "")))
				return true;
		}
		return false;
	}


	/**
	 * ��������� branchId, agrencyId, regionId,rbBrchId �� �����
     * + rbBrchId �� ������������  [0-9]{6}
	 * @param cardAcctRec
	 * @return boolean
	 */
	private boolean checkBankInfo(CardAcctRec_Type cardAcctRec)
	{
		String cardNumber = cardAcctRec.getCardAcctId().getCardNum();
		BankInfo_Type bankInfo = cardAcctRec.getBankInfo();

		String branchId  = bankInfo.getBranchId();
		String agrencyId = bankInfo.getAgencyId();
		String regionId  = bankInfo.getRegionId();
		String rbBrchId  = bankInfo.getRbBrchId();

		if (StringHelper.isEmpty(branchId))
		{
			printToLog("branchId", cardNumber);
			return false;
		}
		if (StringHelper.isEmpty(agrencyId))
		{
			printToLog("agrencyId", cardNumber);
			return false;
		}
		if (StringHelper.isEmpty(regionId))
		{
			printToLog("regionId", cardNumber);
			return false;
		}
		if (StringHelper.isEmpty(rbBrchId))
		{
			printToLog("rbBrchId", cardNumber);
			return false;
		}
		if (!rbBrchId.matches(RBBRCHID_REGEXP))
		{
			printToLog(RBBRCHID_REGEXP, cardNumber);
			return false;
		}
		return true;
	}

	private void printToLog(String tag, String cardNumber)
	{
		log.debug("����� " + maskCardNumber(cardNumber) + " ������������ ������, ��� �� ��� ������ ��� " + tag + " ��� ��������.");
	}

	private String maskCardNumber(String cardNumber)
	{
		if (StringHelper.isEmpty(cardNumber))
			return "";
		return GateCardHelper.hideCardNumber(cardNumber);
	}

	/**
	 * �������� ��������, ����� �������� �������������� ����� ���� Client2Client, �� �������� ��� ��� ��
	 * ��������
	 * @param cards ���������� �� GFL �����
	 */
	private void checkAdditionalCards(List<Pair<Card, AdditionalProductData>> cards)
	{
		List<String> mainCardNumbers = new ArrayList<String>();
		List<Card> additionalCards = new ArrayList<Card>();

		for (Pair<Card, AdditionalProductData> pair : cards)
		{
			Card card = pair.getFirst();
			if (card.isMain())
				mainCardNumbers.add(card.getNumber());
			else
				additionalCards.add(card);
		}

		for (Card card : additionalCards)
		{
			if (card.getAdditionalCardType() == AdditionalCardType.CLIENTTOCLIENT &&
					card.getMainCardNumber() != null && !mainCardNumbers.contains(card.getMainCardNumber()))
			{
				log.warn("��� �������������� ����� � " + maskCardNumber(card.getNumber()) + " �� ��������" +
						" �������� �����. ��� �������������� ����� Client2Client (������ ���� �� �������).");
			}
		}
	}

	/**
	 * ���������� ��� �� �����
	 * @param card - �����
	 * @param cardAcctRec - ������ �� ����� �� �����
	 * @param fillFromGFL - ���� ������ ������ �����
	 * @throws GateLogicException
	 * @throws GateException
	 */
	private void setPrimaryAccount(CardImpl card, CardAcctRec_Type cardAcctRec, boolean fillFromGFL, Long loginId) throws GateLogicException, GateException
	{
		String primaryAccountNumber = null;
		String primaryAccountExternalId = null;
		//�������� �� GFL
		if (cardAcctRec.getCardAcctId().getCardNum().equals(card.getNumber()) && fillFromGFL)	{
			primaryAccountExternalId = EntityIdHelper.createCardOrAccountCompositeId(cardAcctRec,loginId,false);
			primaryAccountNumber = cardAcctRec.getCardAcctId().getAcctId();
		}
		//���� �������� �� CRDWI, �� ���� �� �����������, �.�. � �������� ������ ���-� �� ����� �� ������������
		card.setPrimaryAccountExternalId(primaryAccountExternalId);
		card.setPrimaryAccountNumber(primaryAccountNumber);

		// ���������� ���� � ������� �����
		card.setKind(cardAcctRec.getCardAcctId().getAcctCode());
		card.setSubkind(cardAcctRec.getCardAcctId().getAcctSubCode());
	}

	/**
	 * ����������� ���������� �� �����, ������������ ����������� �� ��
	 * @param bankInfo ���������� � �����
	 * @return true - ��������(����������� �� �������������)
	 */
	private static boolean isAccessCardByBankInfo(BankInfo_Type bankInfo, String logCardNum)
	{
		String regionId  = bankInfo.getRegionId();

		// ����� �� ����������, ��������� �� ���������(�������� ���� �� ������)
		if(StringHelper.isEmpty(regionId))
		{
			return false;
		}

		// ��� 91 � 92 �� ������ �� ������
		String tb = StringHelper.removeLeadingZeros(regionId);
		if(NOT_ACCESS_TB_CODES.contains(tb))
		{
			log.debug("����� ������������� �������������, ����������� �� ��. ����� ����� : " + logCardNum);
			return false;
		}

		return true;
	}

	/**
	 * ������������ �������-�������� "�� ������� ���������� �� �����"
	 * @param cardNumber ����� �����
	 * @return ������
	 */
	private static Status_Type getMockNotFoundCardInfoStatus(String cardNumber)
	{
		return new Status_Type(-10L, "������ ��������� ��������� ���������� �� �����.", null, "������ ��������� ��������� ���������� �� ����� " + cardNumber);
	}
}

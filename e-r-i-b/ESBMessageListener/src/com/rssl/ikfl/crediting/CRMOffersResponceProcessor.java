package com.rssl.ikfl.crediting;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.common.types.external.systems.AutoStopSystemType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.mbuesi.UESIMessagesService;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.Priority;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;
import org.apache.commons.collections.Predicate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.*;

/**
 * @author Erkin
 * @ created 02.01.2015
 * @ $Author$
 * @ $Revision$
 */

/**
 * Обработка ответов CRM по предодобренным предложениям (CRM -> ЕРИБ)
 */
public class CRMOffersResponceProcessor
{
	private final Log syslog = PhizICLogFactory.getLog(LogModule.Core);

	private final OfferStorage offerStorage = new OfferStorage();

	private static final String ERROR_VALIDATE_TEMPLATE = "Ошибка в атрибуте: %s";

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Обработать ответ на отклик
	 * @param response - ответ CRM на отклик
	 * @param externalId - идентификатор сообщения JMS
	 */
	public void processFeedbackResponse(RegisterRespondToMarketingProposeRs response, String externalId) throws Exception
	{
		String rqUID = response.getRqUID();
		StatusType status = response.getStatus();

		if (status.getStatusCode() != 0)
		{
			syslog.error("Отказ по отклику rqUID=" + rqUID + ". StatusCode=" + status.getStatusCode() + " StatusDesc=" + status.getStatusDesc());
		}

		GateSingleton.getFactory().service(UESIMessagesService.class).processMessage(externalId, status.getStatusCode() == 0L);
	}

	/**
	 * Обработать ответ с предложениями
	 * @param response - ответ, в котором содеражаться предодобренные предложения
	 * @throws Exception
	 */
	public void processOfferResponse(GetCampaignerInfoRs response) throws Exception
	{
		String rqUID = response.getRqUID();

		// 1. Ищем запись в таблице входов
		OfferLogin offerLogin = offerStorage.loadOfferLoginByUID(rqUID);
		if (offerLogin == null)
			throw new RuntimeException("Не найден OfferLogin по UID=" + rqUID);

		// 2. Проверяем актуальность ответа
		if (!rqUID.equals(offerLogin.lastRqUID))
			throw new RuntimeException("Ответ с предложениями просрочен. rqUID = " + rqUID);

		// 3.A CRM прислал положительный ответ => сохраняем предложения
		if (response.getStatus().getStatusCode() == 0)
		{
			offerLogin.lastRqStatus = OfferRequestStatus.SUCCEEDED;
			offerStorage.saveOfferLogin(offerLogin);
			offerStorage.markPersonCrmOffersAsNonActive(offerLogin);
			processOffers(offerLogin, response);
		}

		// 4.B CRM прислал отрицательный ответ => фиксируем отказ
		else
		{
			offerLogin.lastRqStatus = OfferRequestStatus.FAILED;
			offerStorage.saveOfferLogin(offerLogin);
		}
	}

	@Transactional
	private void processOffers(OfferLogin offerLogin, GetCampaignerInfoRs response) throws BusinessException
	{
		Calendar responseDate = Calendar.getInstance();

		// 1. Получаем набор предложений, отсортированых по возрастанию приоритета
		// (Наивысший приоритет – минимальный. Меньше значение – выше приоритет.)
		int offerIndex = 1;
		Map<Priority, Offer> offersMap  = new TreeMap<Priority, Offer>();
		List<OfferCondition> conditions = new LinkedList<OfferCondition>();
		List<OfferTopUp>     topUps     = new ArrayList<OfferTopUp>();

		for (CampaignMember campaignMember : response.getCampaignMembers())
		{
			TopUpType topUpType = campaignMember.getTopUp();
			if (topUpType != null)
			{
				int        topUpLoanBlockCount = topUpType.getTopUpLoanListCount();
				BigDecimal totalRepaymentSum   = topUpType.getTotalRepaymentSum();

				for (RepayLoanType repayLoanType : topUpType.getRepayLoen())
				{
					try
					{
						topUps.add(makeTopUp(repayLoanType, topUpLoanBlockCount, totalRepaymentSum));
					}
					catch (BusinessException e)
					{
						syslog.error("Сбой при обработке погашаемого договора TOP UP кредита" +
								" RqUID = " + offerLogin.lastRqUID +
								" FIO = "   + offerLogin.surName + " " + offerLogin.firstName + " " + offerLogin.patrName +
								" CampaignMemberID =" + campaignMember.getCampaignMemberId() +
								" IdSource = "   + repayLoanType.getIdSource() +
								" IdContract = " + repayLoanType.getIdContract(), e);
					}
				}
			}

			for (InternalProduct internalProduct : campaignMember.getInternalProducts())
			{
				try
				{
					validateOffer(internalProduct);
					Offer offer = makeOffer(offerLogin, responseDate, campaignMember, internalProduct);
					List<OfferCondition> offerConditions = makeOfferConditions(offer.id, internalProduct);

					Priority offerPriority = new Priority();
					offerPriority.p1 = parseProductASPriority(campaignMember);
					offerPriority.p2 = parseInternalProductPriority(internalProduct);
					offerPriority.p3 = offerIndex;

					bindWithTopUps(offer, topUps);
					offersMap.put(offerPriority, offer);
					conditions.addAll(offerConditions);

					offerIndex++;
				}
				catch (BusinessLogicException ex)
				{
					syslog.error("Сбой на обработке предложения " +
							"RqUID=" + offerLogin.lastRqUID + " " +
							"FIO=" + offerLogin.surName + " " + offerLogin.firstName + " " + offerLogin.patrName + " " +
							"CampaignMemberID=" + campaignMember.getCampaignMemberId() + " " +
							"InternalProductID=" + internalProduct.getProductId(), ex);
				}
				catch (Exception e)
				{
					syslog.error("Сбой на обработке предложения " +
							"RqUID=" + offerLogin.lastRqUID + " " +
							"FIO=" + offerLogin.surName + " " + offerLogin.firstName + " " + offerLogin.patrName + " " +
							"CampaignMemberID=" + campaignMember.getCampaignMemberId() + " " +
							"InternalProductID=" + internalProduct.getProductId(), e);
				}
			}
		}

		List<Offer> offers = new ArrayList<Offer>(offersMap.values());

		// 2. Выставляем сквозной приоритет предложениям
		int offerPriority = 1;
		for (Offer offer : offers)
			offer.priority = offerPriority++;

		// 3. Сохраняем предложения, условия и отклики
		offerStorage.saveOffers(offers);
		offerStorage.saveTopUps(topUps);
		offerStorage.saveOfferConditions(conditions);
	}

	private OfferTopUp makeTopUp(RepayLoanType repayLoanType, int topUpLoanBlockCount, BigDecimal totalRepaymentSum) throws BusinessException
	{
		try
		{
			OfferTopUp offerTopUp = new OfferTopUp();
			offerTopUp.setTopUpLoanBlockCount(topUpLoanBlockCount);
			offerTopUp.setTotalRepaymentSum(totalRepaymentSum);
			offerTopUp.setCurrency(repayLoanType.getCurrency());
			offerTopUp.setIdSource(repayLoanType.getIdSource());
			offerTopUp.setIdContract(repayLoanType.getIdContract());
			offerTopUp.setTotalAmount(repayLoanType.getTotalAmount());
			offerTopUp.setAgreementNumber(repayLoanType.getAgreementNumber());
			offerTopUp.setLoanAccountNumber(repayLoanType.getLoanAccountNumber());

			Date startDate    = DateHelper.fromXMlDateToDate(repayLoanType.getStartDate());
			Date maturityDate = DateHelper.fromXMlDateToDate(repayLoanType.getMaturityDate());

			offerTopUp.setStartDate(DateHelper.toCalendar(startDate));
			offerTopUp.setMaturityDate(DateHelper.toCalendar(maturityDate));
			return offerTopUp;
		}
		catch (ParseException e)
		{
			throw new BusinessException(e);
		}
	}

	private void validateOffer(InternalProduct internalProduct) throws BusinessLogicException
	{
		String productType = internalProduct.getProductType();
		boolean isCardType = productType.equals("CreditCard");
		boolean isChangeCardLimitType = productType.equals("Change Credit Limit");
		boolean isCreditType =productType.equals("Consumer Credit");
		if (StringHelper.isEmpty(productType) || (!isCreditType && !isCardType && !isChangeCardLimitType))
			throw new BusinessLogicException(String.format(ERROR_VALIDATE_TEMPLATE, "ProductType"));
		ProposalParameters proposalParameters = internalProduct.getProposalParameters();
		if (!isNumOrNull(proposalParameters.getRateMax()))
			throw new BusinessLogicException(String.format(ERROR_VALIDATE_TEMPLATE, "RateMax"));
		if (!isNumOrNull(proposalParameters.getRateMin()))
			throw new BusinessLogicException(String.format(ERROR_VALIDATE_TEMPLATE, "RateMin"));
		if (!isNumOrNull(proposalParameters.getLimitMax()))
			throw new BusinessLogicException(String.format(ERROR_VALIDATE_TEMPLATE, "LimitMax"));
		if (!isNumOrNull(proposalParameters.getLimitMin()))
			throw new BusinessLogicException(String.format(ERROR_VALIDATE_TEMPLATE, "LimitMin"));
		if (!isNumOrNull(proposalParameters.getPeriodMax()))
			throw new BusinessLogicException(String.format(ERROR_VALIDATE_TEMPLATE, "PeriodMax"));
		if (!isNumOrNull(proposalParameters.getPeriodMin()))
			throw new BusinessLogicException(String.format(ERROR_VALIDATE_TEMPLATE, "PeriodMin"));
		for (Elements value : proposalParameters.getElements())
			if (!isNumOrNull(value.getValue()))
				throw new BusinessLogicException("Атрибут CampaignMember.InternalProduct.ProposalParameters.Elements.Value является либо числовым, либо пустым");
		if (StringHelper.isEmpty(internalProduct.getExpDate()))
			throw new BusinessLogicException(String.format(ERROR_VALIDATE_TEMPLATE, "ExpDate"));

		if (isCardType || isChangeCardLimitType)
		{
			if (proposalParameters.getLimitMax() == null)
				throw new BusinessLogicException(String.format(ERROR_VALIDATE_TEMPLATE, "LimitMax"));
		}
		if (isCreditType)
		{
			for (TableType table : proposalParameters.getRows())
				if (table.getNumber().intValue() ==1)
					if (StringHelper.isEmpty(table.getName()))
						throw new BusinessLogicException("Не заполнена процентная ставка в первой строке (CampaignMember.InternalProduct.ProposalParameters.Rows.Name)");

			if (StringHelper.isEmpty(internalProduct.getTargetProduct()))
				throw new BusinessLogicException(String.format(ERROR_VALIDATE_TEMPLATE, "TargetProduct"));
			if (StringHelper.isEmpty(internalProduct.getTargetProductType()))
				throw new BusinessLogicException(String.format(ERROR_VALIDATE_TEMPLATE, "TargetProductType"));
			if (StringHelper.isEmpty(internalProduct.getTargetProductSub()))
				throw new BusinessLogicException(String.format(ERROR_VALIDATE_TEMPLATE, "TargetProductSub"));
			boolean flag=false;
			for (TableType table : proposalParameters.getColumns())
			{
				if (StringHelper.isNotEmpty(table.getName()))
				{
					flag = true;
					break;
				}
			}
			if (!flag)
				throw new BusinessLogicException("Не заполенен ни один период (CampaignMember.InternalProduct. ProposalParameters.Columns.Name)");
		}
	}
	private boolean isNumOrNull(String num)
	{
		if (num==null)
			return true;
		try
		{
			NumericUtil.parseBigDecimal(num);
			return true;
		} catch (ParseException e)
		{
			return false;
		}
	}

	private Offer makeOffer(OfferLogin offerLogin, Calendar responseDate, CampaignMember campaignMember, InternalProduct internalProduct) throws Exception
	{
		Offer offer = new Offer();
		offer.id                = offerStorage.getNextOfferID();
		offer.surName           = offerLogin.surName;
		offer.firstName         = offerLogin.firstName;
		offer.patrName          = offerLogin.patrName;
		offer.birthDay          = offerLogin.birthDay;
		offer.docSeries         = offerLogin.docSeries;
		offer.docNumber         = offerLogin.docNumber;
		offer.tb                = offerLogin.tb;
		offer.campaignId        = campaignMember.getCampaignId();
		offer.campaignName      = campaignMember.getCampaignName();
		offer.productType       = OfferProductType.fromCRMCode(internalProduct.getProductType());
		offer.sourceCode        = campaignMember.getSourceCode();
		offer.sourceName        = campaignMember.getSourceName();
		offer.productASName     = campaignMember.getProductASName();
		offer.productTypeCode   = internalProduct.getTargetProductType();
		offer.productCode       = internalProduct.getTargetProduct();
		offer.productSubCode    = internalProduct.getTargetProductSub();
		offer.campaignMemberId  = campaignMember.getCampaignMemberId();
		offer.clientId          = campaignMember.getClientId();
		offer.personalText      = campaignMember.getPersonalText();
		offer.expDate           = XMLDatatypeHelper.parseDateTime(internalProduct.getExpDate());
		offer.currencyCode      = internalProduct.getCurCode();

		ProposalParameters proposalParams = internalProduct.getProposalParameters();
		if (proposalParams != null)
		{
			offer.rateMin       = NumericUtil.parseBigDecimal(proposalParams.getRateMin());
			offer.rateMax       = NumericUtil.parseBigDecimal(proposalParams.getRateMax());
			offer.limitMin      = NumericUtil.parseBigDecimal(proposalParams.getLimitMin());
			offer.limitMax      = NumericUtil.parseBigDecimal(proposalParams.getLimitMax());
			offer.periodMin     = NumericUtil.parseInteger(proposalParams.getPeriodMin());
			offer.periodMax     = NumericUtil.parseInteger(proposalParams.getPeriodMax());
		}

		offer.loadDate = responseDate;
		offer.isActive = true;
		return offer;
	}

	private List<OfferCondition> makeOfferConditions(long offerID, InternalProduct internalProduct) throws Exception
	{
		ProposalParameters proposalParams = internalProduct.getProposalParameters();
		if (proposalParams == null)
			return Collections.emptyList();

		// Мапа "номер_столбца -> период (мес)"
		Map<BigInteger, Integer> periods = new HashMap<BigInteger, Integer>();
		for (TableType column : proposalParams.getColumns())
		{
			Integer period = parsePeriod(column);
			if (period != null)
				periods.put(column.getNumber(), period);
		}

		// Мапа "номер_строки -> ставка"
		Map<BigInteger, BigDecimal> rates = new HashMap<BigInteger, BigDecimal>();
		for (TableType row : proposalParams.getRows())
		{
			BigDecimal rate = parseRate(row);
			if (rate != null)
				rates.put(row.getNumber(), rate);
		}

		List<OfferCondition> offerConditions = new LinkedList<OfferCondition>();
		for (Elements element : proposalParams.getElements())
		{
			OfferCondition condition = makeOfferCondition(offerID, element, periods, rates);
			if (condition != null)
				offerConditions.add(condition);
		}
		return offerConditions;
	}

	private OfferCondition makeOfferCondition(long offerID, Elements element, Map<BigInteger, Integer> periods, Map<BigInteger, BigDecimal> rates)
	{
		if (StringHelper.isEmpty(element.getValue()))
			return null;

		Integer period = periods.get(element.getColumnIndex());
		if (period == null)
			throw new RuntimeException("Не найден период ColumnIndex=" + element.getColumnIndex());

		BigDecimal rate = rates.get(element.getRowIndex());
		if (rate == null)
		{
			//Костыль для TOP-UP: нужно игнорировать каждую четную строку
			if ((element.getRowIndex().longValue() % 2) == 0)
				return null;
			else
				throw new RuntimeException("Не найдена ставка RowIndex=" + element.getRowIndex());
		}
		BigDecimal amount;
		try
		{
			amount = NumericUtil.parseBigDecimal(element.getValue());
		}
		catch (ParseException e)
		{
			throw new RuntimeException("Не удалось прочитать сумму " + element.getValue() + ". " +
					"ColumnIndex=" + element.getColumnIndex() + " " +
					"RowIndex=" + element.getRowIndex(), e);
		}

		OfferCondition condition = new OfferCondition(offerID, rate, period, amount);
		return condition;
	}

	private int parseProductASPriority(CampaignMember campaignMember)
	{
		BigInteger productASPriority = campaignMember.getProductASPriority();
		return (productASPriority != null) ? productASPriority.intValue() : Integer.MAX_VALUE;
	}

	private int parseInternalProductPriority(InternalProduct internalProduct)
	{
		String productPriority = internalProduct.getPriority();
		return StringHelper.isNotEmpty(productPriority) ? Integer.parseInt(productPriority) : Integer.MAX_VALUE;
	}

	/**
	 * Прочитать период: исключить все цифры кроме цифровых, и вернуть целое
	 * @param column - столбец, в котором период содержится в виде текста, напр. "6 мес."
	 * @return период в виде кол-ва месяцев или null, если столбец не содержит текст
	 */
	private Integer parsePeriod(TableType column)
	{
		if (StringHelper.isEmpty(column.getName()))
			return null;

		try
		{
			String periodString = StringHelper.removeChars(column.getName(), new Predicate()
			{
				public boolean evaluate(Object object)
				{
					return Character.isDigit((Character) object);
				}
			});
			return Integer.parseInt(periodString);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Не удалось прочитать период " + column.getName() + " в столбце " + column.getNumber(), e);
		}
	}

	/**
	 * Прочитать ставку: исключить все цифры кроме цифровых и десятичного разделителя, и вернуть дробное
	 * @param row - строка, в которой ставка содержится в виде текста, напр. "6%"
	 * @return ставка в долях процента или null, если строка не содержит текст
	 */
	private BigDecimal parseRate(TableType row)
	{
		if (StringHelper.isEmpty(row.getName()))
			return null;

		try
		{
			String rateString = StringHelper.removeChars(row.getName(), new Predicate()
			{
				public boolean evaluate(Object object)
				{
					Character c = (Character) object;
					return Character.isDigit(c) || c == ',' || c == '.';
				}
			});
			return NumericUtil.parseBigDecimal(rateString);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Не удалось прочитать ставку " + row.getName() + " в строке " + row.getNumber(), e);
		}
	}

	private void bindWithTopUps(Offer offer, List<OfferTopUp> topUps) throws BusinessLogicException
	{
		if (topUps.size() == 0)
		{
			return;
		}

		if (offer.getProductType() == OfferProductType.CONSUMER_CREDIT)
		{
			for (OfferTopUp topUp : topUps)
			{
				topUp.setOfferId(offer.getId());
			}
		}
	}
}

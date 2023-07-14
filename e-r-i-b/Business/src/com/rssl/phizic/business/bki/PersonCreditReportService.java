package com.rssl.phizic.business.bki;

/**
 * @author Gulov
 * @ created 02.10.14
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.bki.CreditResponseParser;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryResponseERIB;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import javax.xml.bind.JAXBException;

/**
 * Сервис для работы с кредитным отчётом
 */
public class PersonCreditReportService
{
	private static final int PART_COUNT = 5;
	private static final double THRESHOLD_AMOUNT = 1000000;

	/**
	 * Построение детальной информации по кредиту, или карте
	 * @param profile профиль
	 * @param creditId идентификатор кредита(карты) - порядковый номер в списке
	 * @return детальная информация по кредиту
	 * @throws BusinessException
	 */
	public CreditDetail makeCreditDetail(PersonCreditProfile profile, int creditId) throws BusinessException
	{
		// парсим отчет
		EnquiryResponseERIB bean = parseResponse(profile);
		// по идентификатору получаем кредит (карту)
		List<EnquiryResponseERIB.Consumers.S.CAIS> caises = bean.getConsumers().getSS().get(0).getCAISES();
		EnquiryResponseERIB.Consumers.S.CAIS record = caises.get(creditId);
		if (record == null)
			return null;
		// получаем объект кредита (карты) в удобоваримом виде
		CreditBuilder builder = new CreditBuilder();
		CreditProduct credit = builder.build(record, 0);
		// получим объект историй выплат
		RepaymentHistory repaymentHistory = new RepaymentHistoryBuilder().build(record);
		// сделаем информацию о договоре на кредит (карту)
		CreditContract creditContract = makeCreditContractInfo(record);
		return new CreditDetail(profile.getLastReport(), credit, repaymentHistory, creditContract);
	}

	/**
	 * Сформировать кредитный отчет клиента по кредитному профилю
	 * @param profile кредитный профиль клиента
	 * @return кредитный отчет
	 */
	public PersonCreditReport makePersonCreditReport(PersonCreditProfile profile) throws BusinessException
	{
		EnquiryResponseERIB bean = parseResponse(profile);

		List<Credit> credits = new LinkedList<Credit>();
		List<Card> cards = new LinkedList<Card>();
		List<Credit> closedCredits = new LinkedList<Credit>();
		List<Card> closedCards = new LinkedList<Card>();
		CreditBuilder builder = new CreditBuilder();
		CreditProduct product;
		int i = 0;
		Money maxAmount = null;
		BigDecimal maxValue = BigDecimal.ZERO;
		if (bean.getConsumers() == null)
		{
			PersonCreditReport report = new PersonCreditReport();
			report.setCredits(credits);
			report.setCards(cards);
			report.setClosedCredits(closedCredits);
			report.setClosedCards(closedCards);
			report.setCreditHistoryRequests(new ArrayList<CreditHistoryRequest>());
			report.setCreditHistoryRequestsHalfYear(new ArrayList<CreditHistoryRequest>());
			return report;
		}
		for (EnquiryResponseERIB.Consumers.S.CAIS record : bean.getConsumers().getSS().get(0).getCAISES())
		{
			product = builder.build(record, i++);
			if (StringHelper.isEmpty(record.getAccountClosedDate()))
			{
				if (product instanceof Credit)
				{
					if (product.getAmount().getValue() == null)
						continue;
					BigDecimal temp = product.getAmount().getValue();
					if (temp.compareTo(maxValue) > 0)
					{
						maxAmount = product.getAmount();
						maxValue = temp;
					}
					credits.add((Credit) product);
				}
				else
					cards.add((Card) product);
			}
			else
			{
				if (product instanceof Credit)
					closedCredits.add((Credit) product);
				else
					closedCards.add((Card) product);
			}
		}
		if (CollectionUtils.isNotEmpty(credits) && maxAmount != null)
			setWidth(credits, maxAmount);
		PersonCreditReport report = new PersonCreditReport();
		report.setUpdated(profile.getLastReport());
		if (CollectionUtils.isNotEmpty(bean.getConsumers().getSS().get(0).getBureauScores()))
			report.setRating(bean.getConsumers().getSS().get(0).getBureauScores().get(0).getScoreInterval());
		if (CollectionUtils.isNotEmpty(credits) || CollectionUtils.isNotEmpty(cards))
		{
			Collections.sort(credits, new CreditComparator());
			List<CreditProduct> totalPaymentAndArrears = new LinkedList<CreditProduct>();
			totalPaymentAndArrears.addAll(credits);
			totalPaymentAndArrears.addAll(cards);
			report.setTotalPayment(calculateTotalPayment(totalPaymentAndArrears));
			report.setTotalArrears(calculateTotalArrears(totalPaymentAndArrears));
		}
		if (CollectionUtils.isNotEmpty(closedCredits) || CollectionUtils.isNotEmpty(closedCards))
			report.setTotalClosedCredits(calculateTotalClosedCredits(closedCredits, closedCards));

		Collections.sort(cards, new CreditComparator());
		Collections.sort(closedCards, new CreditComparator());
		Collections.sort(closedCredits, new CreditComparator());

		report.setCredits(credits);
		report.setCards(cards);
		report.setClosedCredits(closedCredits);
		report.setClosedCards(closedCards);

		//Заполнение запросов кредитной истории клиента
		fillHistoryRequests(bean.getConsumers().getSS().get(0).getCAPS(), report, builder);

		return report;
	}

	/**
	 * Расчет ширины блоков.
	 * Определяется максимальная сумма - если максимальная сумма кредита превышает 1000000,
	 * то сумма принимается равной 1000000, если меньше, то маскимальная сумма кредита.
	 * Определяется сумма одной части - максимальная сумма делится на 5 частей.
	 * Определяются количество блоков каждого кредита - суммы всех кредитов делятся на сумму одной части.
	 * Если остаток от деления больше 0, то к количеству блоков добавляется 1.
	 * Если количество блока кредита получается меньше 2, то она принимается равной 2.
	 * @param credits список кредитов
	 * @param maxAmount максимальная сумма кредита
	 */
	private void setWidth(List<Credit> credits, Money maxAmount)
	{
		if (credits.size() == 1)
			credits.get(0).setWidth(PART_COUNT);
		double onePartValue;
		if (maxAmount.getValue().intValue() >= THRESHOLD_AMOUNT)
			onePartValue = THRESHOLD_AMOUNT / PART_COUNT;
		else
			onePartValue = maxAmount.getValue().intValue() / PART_COUNT;
		for (Credit credit : credits)
		{
			int creditValue = credit.getAmount().getValue().intValue();
			int partCount = creditValue / ((int) onePartValue);
			if (creditValue - (onePartValue * partCount) > 0)
				++partCount;
			if (partCount < 2)
				partCount = 2;
			else if (partCount > 5)
				partCount = 5;
			credit.setWidth(partCount);
		}
	}

	private List<Money> calculateTotalClosedCredits(List<Credit> credits, List<Card> cards)
	{
		Map<String, BigDecimal> payments = new HashMap<String, BigDecimal>();
		for (Credit credit : credits)
		{
			String currency = credit.getAmount().getCurrency();
			BigDecimal value = credit.getAmount().getValue();
			BigDecimal amount = payments.get(currency);
			payments.put(currency, amount == null ? value : amount.add(value));
		}
		for (Card card : cards)
		{
			String currency = card.getCreditLimit().getCurrency();
			BigDecimal value = card.getCreditLimit().getValue();
			BigDecimal amount = payments.get(currency);
			payments.put(currency, amount == null ? value : amount.add(value));
		}
		List<Money> moneys = new ArrayList<Money>(payments.size());
		for (Map.Entry<String, BigDecimal> entry : payments.entrySet())
			moneys.add(new Money(entry.getValue(), entry.getKey()));
		return moneys;
	}

	private EnquiryResponseERIB parseResponse(PersonCreditProfile profile) throws BusinessException
	{
		try
		{
			CreditResponseParser parser = new CreditResponseParser(false, true);
			return (EnquiryResponseERIB) parser.parse(profile.getReport());
		}
		catch (JAXBException e)
		{
			throw new BusinessException("Ошибка парсинга кредитного отчета", e);
		}
	}

	private List<Money> calculateTotalPayment(List<CreditProduct> products)
	{
		Map<String, BigDecimal> payments = new HashMap<String, BigDecimal>();
		for (CreditProduct product : products)
		{
			String currency = product.getInstalment().getCurrency();
			BigDecimal value = product.getInstalment().getValue();
			BigDecimal amount = payments.get(currency);
			payments.put(currency, amount == null ? value : amount.add(value));
		}
		List<Money> moneys = new ArrayList<Money>(payments.size());
		for (Map.Entry<String, BigDecimal> entry : payments.entrySet())
			moneys.add(new Money(entry.getValue(), entry.getKey()));
		return moneys;
	}

	private List<TotalArrears> calculateTotalArrears(List<CreditProduct> credits)
	{
		Map<String, Pair<String, Map<String, BigDecimal>>> arrears = new HashMap<String, Pair<String, Map<String, BigDecimal>>>();
		for (CreditProduct credit : credits)
		{
			if (credit.getArrears().getValue()!=null)
			{
				String bankName = credit.getBankName();
				String currency = credit.getArrears().getCurrency();
				BigDecimal value = credit.getArrears().getValue();
				Pair<String, Map<String, BigDecimal>> moneys = arrears.get(bankName);
				if (moneys == null)
				{
					moneys = new Pair<String, Map<String, BigDecimal>>();
					moneys.setSecond(new HashMap<String, BigDecimal>());
					moneys.getSecond().put(currency, value);
					moneys.setFirst(credit.getMonthRequest());
					arrears.put(bankName, moneys);
				}
				else
				{
					BigDecimal tempValue = moneys.getSecond().get(currency);
					moneys.getSecond().put(currency, tempValue == null ? value : tempValue.add(value));
					moneys.setFirst(credit.getMonthRequest());
				}
			}
		}
		List<TotalArrears> moneys = new ArrayList<TotalArrears>();
		for (Map.Entry<String, Pair<String, Map<String, BigDecimal>>> entry : arrears.entrySet())
		{
			String bankName = entry.getKey();
			for (Map.Entry<String, BigDecimal> money : entry.getValue().getSecond().entrySet())
				moneys.add(new TotalArrears(entry.getValue().getFirst(), bankName, new Money(money.getValue(), money.getKey())));
		}
		return moneys;
	}

	private boolean isHistoryRequestLastHalfYear(CreditHistoryRequest request)
	{
		Calendar newDate = Calendar.getInstance();
		newDate.add(Calendar.DATE, -183);
		return request.getDateRequest().after(newDate);
	}

	private static class CreditComparator implements Comparator<CreditProduct>
	{
		public int compare(CreditProduct o1, CreditProduct o2)
		{
			return DateHelper.nullSafeCompare(o2.getOpenDate(), o1.getOpenDate());
		}
	}

	private CreditContract makeCreditContractInfo(EnquiryResponseERIB.Consumers.S.CAIS credit) throws BusinessException
	{
		CreditBuilder builder = new CreditBuilder();
		return builder.buildCreditContract(credit);
	}

	private static class CreditHistoryRequestComparator implements Comparator<CreditHistoryRequest>
	{

		public int compare(CreditHistoryRequest o1, CreditHistoryRequest o2)
		{
			return o2.getDateRequest().compareTo(o1.getDateRequest());
		}
	}

	private void fillHistoryRequests(List<EnquiryResponseERIB.Consumers.S.CAPS> capses, PersonCreditReport report, CreditBuilder builder) throws BusinessException
	{
		List<CreditHistoryRequest> historyRequests = new ArrayList<CreditHistoryRequest>(capses.size());
		List<CreditHistoryRequest> historyRequestsLastHalfYear = new ArrayList<CreditHistoryRequest>(capses.size());
		for (EnquiryResponseERIB.Consumers.S.CAPS record : capses)
		{
			CreditHistoryRequest request = builder.buildCreditHistoryRequest(record);
			if (request.getDateRequest() != null)
				if (isHistoryRequestLastHalfYear(request))
					historyRequestsLastHalfYear.add(request);
				else
					historyRequests.add(request);

		}
		Collections.sort(historyRequests, new CreditHistoryRequestComparator());
		Collections.sort(historyRequestsLastHalfYear, new CreditHistoryRequestComparator());
		report.setCreditHistoryRequests(historyRequests);
		report.setCreditHistoryRequestsHalfYear(historyRequestsLastHalfYear);
	}
}

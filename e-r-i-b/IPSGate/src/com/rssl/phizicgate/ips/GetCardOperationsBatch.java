package com.rssl.phizicgate.ips;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.dataaccess.jdbc.JDBCAction;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.ips.*;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.source.LogableCallableStatement;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import oracle.jdbc.OracleTypes;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static com.rssl.phizgate.mobilebank.GateCardHelper.hideCardNumber;

/**
 * @author Erkin
 * @ created 29.07.2011
 * @ $Author$
 * @ $Revision$
 */
class GetCardOperationsBatch implements JDBCAction<Void>
{
	private static final String CARD_SEPARATOR = ",";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private final GateFactory gateFactory;

	private final IPSReceiverService receiverService;

	private final CardOperationInfoService cardOperationInfoService;

	private final ClaimPack pack;

	private static final String GET_TRACSACTIONS_BY_CARDS = "getTransactionsByCards";

	/**
	 * Мап "заявка -> атрибуты_карт"
	 */
	private final Map<IPSCardOperationClaim, List<CardOperationInfo>> claimOperationInfos
			= new HashMap<IPSCardOperationClaim, List<CardOperationInfo>>();

	/**
	 * Сет заявок с проблемами
	 */
	private final Map<IPSCardOperationClaim, IKFLException> claimExceptions
			= new HashMap<IPSCardOperationClaim, IKFLException>();

	private final Set<IPSCardOperationClaim> failedClaims = new HashSet<IPSCardOperationClaim>();

	///////////////////////////////////////////////////////////////////////////

	GetCardOperationsBatch(GateFactory gateFactory, ClaimPack pack)
	{
		this.pack = pack;
		this.gateFactory = gateFactory;
		this.receiverService = gateFactory.service(IPSReceiverService.class);
		this.cardOperationInfoService = gateFactory.service(CardOperationInfoService.class);
	}

	public Void execute(Connection con) throws SQLException, GateException
	{
		log.info("Приступаю к обработке пачки заявок: " + StringUtils.join(pack.getClaims(), ", "));

		Timestamp startDate = new Timestamp(pack.getStartDate().getTimeInMillis());
		String cardsString = StringUtils.join(pack.getCardNumbers(), CARD_SEPARATOR);

		ResultSet cursor = null;
		LogableCallableStatement cstmt =
				(LogableCallableStatement) con.prepareCall("{call " + GET_TRACSACTIONS_BY_CARDS +"( " +
						"?, " + // pSeparator
						"?, " + // pCards
						"?, " + // pFromDate
						"?)}"   // pCursor
				);

		LogThreadContext.setProcName(GET_TRACSACTIONS_BY_CARDS);
		try
		{
			cstmt.setString(1, CARD_SEPARATOR);
			cstmt.setString(2, cardsString);
			cstmt.setTimestamp(3, startDate);
			cstmt.registerOutParameter(4, OracleTypes.CURSOR);
			IPSConfig config = ConfigFactory.getConfig(IPSConfig.class);
			CardOperationsParser parser = new CardOperationsParser(this, cstmt, gateFactory, config.getOperationsBatchSize(), pack.getStartDate());
			cstmt.addObjectParser(parser, 4);
			cstmt.setQueryTimeout(config.getConnectionTimeout());
			cstmt.execute();
			cursor = parser.getCursor();
		}
		finally
		{
			if (cursor != null)
				try { cursor.close(); } catch (SQLException ignored) {}

			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}

		flush();
		finish();

		return null;
	}

	void save(List<CardOperationInfo> result) throws SQLException
	{
		for (CardOperationInfo record : result)
		{
			// Если по всем заявкам есть ошибки, получение оставшихся операций бессмысленно
			if (failedClaims.size() >= pack.size())
				break;

			// 1.1 Определяем заявку по номеру карты
			String cardNumber = record.getCardId();

			IPSCardOperationClaim claim = pack.getClaimByCard(cardNumber);
			if (claim == null)
			{
				log.error("Не найдена заявка для карты " + hideCardNumber(cardNumber) + ". Останов загрузки операций.");
				// break, потому что не понятно, какой заявки касается исключение => можем пропустить важную транзакцию => останавливаем процесс
				break;
			}

			// (1.2) По заявке уже есть ошибка => операцию пропускаем
			if (failedClaims.contains(claim))
				continue;

			if (record.isBadOperation())
			{
				failClaim(claim, new GateException(record.getErrorMessage()));
				continue;
			}

			record.setCard(claim.getCard());

			// Если валюта карты не совпадает с валютой счёта, это плохо
			Currency operationCurrency = record.getAccountMoney().getCurrency();
			if (!operationCurrency.compare(claim.getCard().getCurrency())) {
				// заявку бракуем, сконвертированные операции сохраняем
				// noinspection ThrowableInstanceNeverThrown
				failClaim(claim, new GateException("Валюта счёта операции не совпадает с валютой карты " + record));
				continue;
			}

			// 1.4 Запоминаем атрибуты карточной операции
			List<CardOperationInfo> claimOperations = claimOperationInfos.get(claim);
			if (claimOperations == null)
			{
				claimOperations = new LinkedList<CardOperationInfo>();
				claimOperationInfos.put(claim, claimOperations);
			}
			claimOperations.add(record);

			// (2) Сбрасываем запомненные карточные операции на получателя
		}
		try
		{
			flush();
		}
		catch (GateException e)
		{
			//noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
			throw new SQLException(e.getMessage());
		}
	}

	private void flush() throws GateException
	{
		Map<IPSCardOperationClaim, List<IPSCardOperation>> operations = createCardOperations();

		if (!operations.isEmpty())
		{
			GroupResult<IPSCardOperationClaim, List<IPSCardOperation>> result
					= new GroupResult<IPSCardOperationClaim, List<IPSCardOperation>>();
			result.setResults(operations);

			List<IPSCardOperationClaim> receiveResult = receiverService.receiveCardOperationClaimResult(result);

			// Непринятые заявки - это те из отправленных, которые не принял получатель
			Set<IPSCardOperationClaim> sendedClaims = operations.keySet();
			Set<IPSCardOperationClaim> nonsendedClaims = new HashSet<IPSCardOperationClaim>(receiveResult);
			nonsendedClaims.retainAll(sendedClaims);
			// Добавляем непринятые заявки в сет забракованных
			failedClaims.addAll(nonsendedClaims);
		}

		claimOperationInfos.clear();
	}

	/**
	 * Создаёт операции по списку <operationInfos>
	 * @return мап "заявка -> список_операций"
	 * список_операций не должен быть пустым!
	 */
	private Map<IPSCardOperationClaim, List<IPSCardOperation>> createCardOperations() throws GateException
	{
		// 1. Получаем справочную информацию по операциям: тип операции и её категорию
		List<CardOperationInfo> operationInfoList = buildCardOperationInfosList();
		if (operationInfoList.isEmpty())
			return Collections.emptyMap();

		GroupResult<Long, CardOperationType> types = getCardOperationTypes(operationInfoList);		

		// 2. Конвертация CardOperationInfo -> CardOperation
		Map<IPSCardOperationClaim, List<IPSCardOperation>> operations = new HashMap<IPSCardOperationClaim, List<IPSCardOperation>>();
		for (Map.Entry<IPSCardOperationClaim, List<CardOperationInfo>> entry : claimOperationInfos.entrySet())
		{
			IPSCardOperationClaim claim = entry.getKey();
			List<CardOperationInfo> cardOperationInfos = entry.getValue();

			for (CardOperationInfo operationInfo : cardOperationInfos)
			{
				try
				{
					CardOperationType type = selectCardOperationType(operationInfo, types);

					IPSCardOperation operation = createCardOperation(claim.getClient(), operationInfo, type);

					List<IPSCardOperation> claimOperations = operations.get(claim);
					if (claimOperations == null) {
						claimOperations = new LinkedList<IPSCardOperation>();
						operations.put(claim, claimOperations);
					}
					claimOperations.add(operation);
				}
				catch (IllegalOperationCategoryException e)
				{
					// noinspection ThrowableInstanceNeverThrown
					failClaim(claim, new GateException("Сбой при определении категории карточной операции", e));
					// дальше не конвертим операции по данной заявке
					break;
				}
			}
		}

		return operations;
	}

	private List<CardOperationInfo> buildCardOperationInfosList()
	{
		if (claimOperationInfos.isEmpty())
			return Collections.emptyList();
		List<CardOperationInfo> wholelist = new LinkedList<CardOperationInfo>();
		for (List<CardOperationInfo> list : claimOperationInfos.values())
			wholelist.addAll(list);
		return wholelist;
	}

	private GroupResult<Long, CardOperationType> getCardOperationTypes(List<CardOperationInfo> operationInfos) throws GateException
	{
		Set<Long> typeCodesSet = new HashSet<Long>();
		for (CardOperationInfo operationInfo : operationInfos)
			typeCodesSet.add(operationInfo.getOperationType());

		Long[] typeCodesArray = typeCodesSet.toArray(new Long[typeCodesSet.size()]);
		GroupResult<Long, CardOperationType> types = cardOperationInfoService.getOperationTypes(typeCodesArray);
		// Если произошёл общий (глобальный) сбой, передаём исключение "наружу"
		if (types.isOneError())
			//noinspection ThrowableResultOfMethodCallIgnored
			throw new GateException(types.getOneErrorException());
		return types;
	}

	private CardOperationType selectCardOperationType(CardOperationInfo operationInfo, GroupResult<Long, CardOperationType> types) throws IllegalOperationCategoryException
	{
		long typeCode = operationInfo.getOperationType();

		IKFLException exception = types.getException(typeCode);
		if (exception != null)
			throw new IllegalOperationCategoryException(exception);

		CardOperationType type = types.getResult(typeCode);
		if (type == null)
			throw new IllegalOperationCategoryException("Не найден тип карточной операции " + operationInfo);

		return type;
	}

	private IPSCardOperation createCardOperation(Client owner, CardOperationInfo info, CardOperationType type)
	{
		Long loginId = owner.getInternalOwnerId();

		IPSCardOperationImpl operation = new IPSCardOperationImpl();
		operation.setDocumentNumber(String.format("%s^%d", info.getOperationId(), loginId));
		operation.setOperationCard(info.getCard());
		operation.setDate(info.getOperationDate());
		operation.setOperationDate(info.getOperationDate());
		operation.setDescription(info.getDescription());

		Money accountMoney = info.getAccountMoney();
		Money operationMoney = info.getOperationMoney();
		Money accountNoMoney = new Money(BigDecimal.ZERO, accountMoney.getCurrency());
		Money operationNoMoney = new Money(BigDecimal.ZERO, operationMoney.getCurrency());
		if (accountMoney.getDecimal().compareTo(BigDecimal.ZERO) < 0)
		{
			operation.setAccountCreditSum(accountNoMoney);
			operation.setAccountDebitSum(accountMoney);
			operation.setCreditSum(operationNoMoney);
			operation.setDebitSum(operationMoney);
		}
		else
		{
			operation.setAccountCreditSum(accountMoney);
			operation.setAccountDebitSum(accountNoMoney);
			operation.setCreditSum(operationMoney);
			operation.setDebitSum(operationNoMoney);
		}

		operation.setShopInfo(info.getDeviceId());
		operation.setOwner(owner);
		operation.setMccCode(info.getMccCode());
		operation.setCash(type.isCash());
		operation.setAuthCode(info.getAuthCode());

		return operation;
	}

	/**
	 * Послать "закругляющий" ответ по выполненным заявкам
	 * Для нормально выполненных заявок указывается пустой список карточных операций.
	 */
	private void finish() throws GateException
	{
		GroupResult<IPSCardOperationClaim, List<IPSCardOperation>> result
				= new GroupResult<IPSCardOperationClaim, List<IPSCardOperation>>();

		for (IPSCardOperationClaim claim : pack.getClaims())
		{
			IKFLException exception = claimExceptions.get(claim);
			if (exception != null)
				result.putException(claim, exception);

			else if (!failedClaims.contains(claim))
				result.putResult(claim, Collections.<IPSCardOperation>emptyList());
		}

		receiverService.receiveCardOperationClaimResult(result);

		claimOperationInfos.clear();
		claimExceptions.clear();
		failedClaims.clear();
	}

	///////////////////////////////////////////////////////////////////////////

	private void failClaim(IPSCardOperationClaim claim, GateException e)
	{
		log.error(e.getMessage(), e);
		// noinspection ThrowableResultOfMethodCallIgnored
		claimExceptions.put(claim, e);
		failedClaims.add(claim);
	}

	public boolean isConnectionLogEnabled()
	{
		return true;
	}
}

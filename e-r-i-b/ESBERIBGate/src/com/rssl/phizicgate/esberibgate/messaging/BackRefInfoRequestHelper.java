package com.rssl.phizicgate.esberibgate.messaging;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountService;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.ArrayUtils;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author akrenev
 * @ created 02.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер работы с уточнением инфы в шлюзе
 */

public final class BackRefInfoRequestHelper
{
	private static final String SRB_SYSTEM_ID = "urn:sbrfsystems:40-cod";
	private static final String SRB_TB_CODE = "40";

	private static final String GROUP_PREFIX = "com.rssl.phizic.gate.TB.group";
	private static final String SPLITER = ",";
	private static final String SEPARATOR = "\\|";

	private static Properties groups;                                               //группы тербанков

	static
	{
		groups = ConfigFactory.getReaderByFileName("gate.properties").getProperties(GROUP_PREFIX);
	}

	private final GateFactory factory;

	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public BackRefInfoRequestHelper(GateFactory factory)
	{
		this.factory = factory;
	}

	private GateFactory getFactory()
	{
		return factory;
	}

	/**
	 * Поддерживается ли отображение для данного платежа.
	 * @param document - платеж.
	 * @return true/false - пддерживается/не поддерживаетя
	 * @throws GateException
	 */
	public boolean isCalcCommissionSupport(GateDocument document) throws GateException
	{
		return getFactory().service(BackRefCommissionTBSettingService.class).isCalcCommissionSupport(document);
	}

	/**
	 * Получить владельца документа из анкеты клиента в бизнесе(через BackRefClientService)
	 * @param document документ
	 * @return владелец
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public Client getBusinessOwner(GateDocument document) throws GateLogicException, GateException
	{
		return getFactory().service(BackRefClientService.class).getClientById(document.getInternalOwnerId());
	}

	/**
	 * Получение RbTbBrch через loginId клиента
	 * @param loginId клиента
	 * @return Сложный тип данных для Номера территориального банка в формате RbTbBrch – Код ТБ-Головное отделение ТБ–Номер ОСБ ТБ.
	 */
	public String getRbTbBrch(Long loginId) throws GateLogicException, GateException
	{
		BackRefClientService service = getFactory().service(BackRefClientService.class);
		String departmentCode = service.getClientDepartmentCode(loginId);
		if (departmentCode == null)
			return null;
		String[] codeArray = departmentCode.split(SEPARATOR);
		return ArrayUtils.isEmpty(codeArray) ? null : codeArray[0];
	}

	/**
	 * Получение RbTbBrch через документ
	 * @param document документ
	 * @return Сложный тип данных для Номера территориального банка в формате RbTbBrch – Код ТБ-Головное отделение ТБ–Номер ОСБ ТБ.
	 */
	public String getRbTbBrch(GateDocument document) throws GateLogicException, GateException
	{
		return getRbTbBrch(document.getInternalOwnerId());
	}

	/**
	 * получить идентификатор карты
	 * @param owner владелец
	 * @param cardNumber номер карты
	 * @return идентификатор карты
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public String getCardExternalId(Client owner, String cardNumber) throws GateException, GateLogicException
	{
		return getFactory().service(BackRefBankrollService.class).findCardExternalId(owner.getInternalOwnerId(), cardNumber);
	}

	/**
	 * получить карту по номеру
	 * @param client владелец или инициатор запроса
	 * @param number номер счета
	 * @param office офис
	 * @return карта
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public Card getCard(Client client, String number, Office office) throws GateException, GateLogicException
	{
		try
		{
			BankrollService bankrollService = getFactory().service(BankrollService.class);
			//noinspection unchecked
			return GroupResultHelper.getOneResult(bankrollService.getCardByNumber(client, new Pair<String, Office>(number, office)));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
	}

	/**
	 * получить счет по номеру
	 * @param number номер счета
	 * @param office офис
	 * @return счет
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public Account getAccount(String number, Office office) throws GateException, GateLogicException
	{
		try
		{
			BankrollService bankrollService = getFactory().service(BankrollService.class);
			//noinspection unchecked
			return GroupResultHelper.getOneResult(bankrollService.getAccountByNumber(new Pair<String, Office>(number, office)));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
	}

	/**
	 * получить ОМС по номеру
	 * @param client владелец или инициатор запроса
	 * @param number номер счета
	 * @return счет
	 */
	public IMAccount getIMAccount(Client client, String number) throws GateException, GateLogicException
	{
		IMAccountService imAccountService = getFactory().service(IMAccountService.class);

		try
		{
			return GroupResultHelper.getOneResult(imAccountService.getIMAccountByNumber(client, number));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
	}

	private List<String> getSrbTBCodes()
	{
		return ConfigFactory.getConfig(DocumentConfig.class).getAllTbCodesSrb();
	}

	/**
	 * Нужно ли использовать значения для СРБ
	 * @param compositeId композитный ключ
	 * @param client клиент
	 * @return нужно ли использовать значения для СРБ
	 */
	public boolean isUseSRBValues(EntityCompositeId compositeId, Client client)
	{
		String tb = client.getOffice().getCode().getFields().get("region");
		String rbBrchId = compositeId.getRbBrchId();
		String systemId = compositeId.getSystemId();
		return StringHelper.isEmpty(systemId) && StringHelper.isEmpty(rbBrchId) && getSrbTBCodes().contains(tb);
	}

	/**
	 * @return код внешней системы для СРБ
	 * @throws GateException
	 */
	public String getSRBExternalSystemCode() throws GateException
	{
		return ExternalSystemHelper.getCode(SRB_SYSTEM_ID);
	}

	/**
	 * получить RbBrchId для СРБ
	 * @param office офис
	 * @return RbBrchId для СРБ
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public String getSRBRbBrchId(Office office) throws GateLogicException, GateException
	{
		//правила формирования:
		//В качестве номера террбанка для СРБ всегда передается 40.
		//В качестве номер ОСБ передается значение, найденное по следующему алгоритму:
		//1. Из АС Шлюз ЦОД запрашивается информация по счету, в том числе информация о подразделение ведущем счет.
		//2. По этой информации происходит поиск в справочнике НСИ, который загружен в систему СБОЛ.
		//3. Из найденного подразделения берется номер осб и обрезается спереди до 4 знаков.
		// Значение поля с типом RbBrchId формируется из числа 40 и полученного номера ОСБ.
		// Например, если номер ОСБ 1234, то значение поля будет 401234.
		ExtendedCodeGateImpl code = new ExtendedCodeGateImpl(office.getCode());
		String branch = code.getBranch();
		if (branch.length() > 4)
		{
			branch = branch.substring(branch.length() - 4);//вырезаем
		}
		branch = StringHelper.appendLeadingZeros(branch, 4);
		return SRB_TB_CODE + branch;
	}

	/**
	 * Проверяем принадлжедат ли офис источника списания и счет получателя 1 ТБ.
	 * @param office офис источника списания
	 * @param receiverAccount счет получателя. Важно: передаваться должны только сберовские счета
	 * те перед вызовом обязательно проверять является ли банк получаетеля отделением сбера
	 * @return true - принадлежат
	 */
	public static boolean isSameTB(Office office, String receiverAccount) throws GateException
	{
		String payerTB = office.getCode().getFields().get("region");
		if (payerTB.length() == 1)
			payerTB = StringHelper.appendLeadingZeros(payerTB, 2);

		String receiverTB = receiverAccount.substring(9, 11);
		if (receiverTB.equals(payerTB))
			return true;

/*
		TODO проверка по правоприемникам временно отключена, до согласования алгоритма
		//если правоприемник найден - значит мы в одном ТБ
		if (!StringHelper.isEmpty(assignee.get(receiverTB)))
			return true;
*/

		for (Map.Entry entry : groups.entrySet())
		{
			StringBuilder group = new StringBuilder();
			//добавляем лидирующий ноль на случай, если он упущен
			for (String tb : ((String) entry.getValue()).split(SPLITER))
			{
				if (tb.length() == 1)
					tb = StringHelper.appendLeadingZeros(tb, 2);
				group.append(tb).append(SPLITER);
			}

			//если оба тб из одной группы, то платеж в одном тб
			if (group.indexOf(payerTB) > -1 && group.indexOf(receiverTB) > -1)
				return true;
		}

		return false;
	}
}
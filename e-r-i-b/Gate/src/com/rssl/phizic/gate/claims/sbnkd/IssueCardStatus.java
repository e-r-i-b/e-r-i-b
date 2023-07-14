package com.rssl.phizic.gate.claims.sbnkd;

import java.util.*;

/**
 * Состояние документа на выпуск карты или подключение УДБО
 *
 * @author bogdanov
 * @ created 26.12.14
 * @ $Author$
 * @ $Revision$
 */

public enum IssueCardStatus
{
	/**
	 * Инициализация, устанавливается для только что созданных заявок.
	 */
	INIT,
	/**
	 * Клиент VIP, устаналивается в случае, если после запроса GetPrivateClient вернулся VIP-клиент.(из  статуса INIT или )
	 */
	VIP_CLIENT,
	/**
	 * Клиент не VIP, ответ на  GetPrivateClient получен до подтверждения
	 */
	INIT_NO_VIP,
	/**
	 * Подтверждение, устанавливает перед отправкой документа в КСШ, являетс яли клиент VIP не знаем, ответ на GetPrivateClient не пришел.
	 */
	CONFIRM,
	/**
	 * Создан, устанавливается для заявок, которые прошли проверку на ВИП, или эта проверка не нужна.
	 */
	CONFIRM_NO_VIP,

	/**
	 * Получение информации о клиенте. устанавлиавется до GetPrivateClient.
	 */
	CLIENT_GETTING,

	/**
	 * Клиент получен, устанавливается после выполнения запроса GetPrivateClient.
	 */
	CLIENT_GET,

	/**
	 * Договор на обслуживание первой карты создан, устанавливается после успешного выполнения первого запроса CreateCardContract.
	 */
	FIRST_CARD_CONTRACT_CREATED,
	/**
	 * УДБО подключен, устанавливает в случае успешного выполения запроса ConcludeEDBO.
	 */
	EDBO_CONCLUDED,

	/**
	 * Первая карта выпущена, устанавливается в случае успешного выполенния запроса IssueCard для первой карты.
	 */
	FIRST_CARD_CREATED,

	/**
	 * Исполнена, устанавливается после исполнения заявки на выпуск всех карт.
	 */
	EXECUTED,

	/**
	 * Ошибка, Устаналивается в случае ошибки на любом шаге. Дальнейшая работа с документом невозможна.
	 */
	ERROR;

	private static final Map<IssueCardStatus, Set<IssueCardStatus>> nextState = new HashMap<IssueCardStatus, Set<IssueCardStatus>>();

	private static Set<IssueCardStatus> statuses(IssueCardStatus ... statuses)
	{
		Set<IssueCardStatus> s = new HashSet<IssueCardStatus>();
		s.addAll(Arrays.asList(statuses));
		return s;
	}

	static
	{
		nextState.put(INIT, statuses(INIT_NO_VIP, CONFIRM, VIP_CLIENT, ERROR));
		nextState.put(INIT_NO_VIP, statuses(CONFIRM_NO_VIP, ERROR));
		nextState.put(CONFIRM, statuses(CONFIRM_NO_VIP, VIP_CLIENT, ERROR));
		nextState.put(CONFIRM_NO_VIP, statuses(CLIENT_GETTING, CLIENT_GET, ERROR, EDBO_CONCLUDED));
		nextState.put(CLIENT_GETTING, statuses(CLIENT_GET, VIP_CLIENT, ERROR));
		nextState.put(CLIENT_GET, statuses(FIRST_CARD_CONTRACT_CREATED, FIRST_CARD_CREATED, ERROR));
		nextState.put(VIP_CLIENT, statuses());
		nextState.put(FIRST_CARD_CONTRACT_CREATED, statuses(FIRST_CARD_CREATED, EDBO_CONCLUDED, ERROR));
		nextState.put(EDBO_CONCLUDED, statuses(FIRST_CARD_CREATED, EXECUTED, ERROR));
		nextState.put(FIRST_CARD_CREATED, statuses(EXECUTED, FIRST_CARD_CREATED, ERROR));
		nextState.put(EXECUTED, statuses());
		nextState.put(ERROR, statuses());
	}

	/**
	 * Возможность перехода в следующее состояние.
	 * @param nextState следующие состояние.
	 * @return  возможен ли переход.
	 */
	public boolean canGoToState(IssueCardStatus nextState)
	{
		return this.nextState.get(this).contains(nextState);
	}
}

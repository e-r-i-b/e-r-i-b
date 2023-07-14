package com.rssl.phizic.gate.payments.autosubscriptions;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.AbstractCardTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.longoffer.ChannelType;
import com.rssl.phizic.logging.operations.context.PossibleAddingOperationUIDObject;

import java.util.Calendar;

/**
 * Заявка на автоплатеж с карты в АС Автоплатежи
 *
 * @author khudyakov
 * @ created 09.09.14
 * @ $Author$
 * @ $Revision$
 */
public interface AutoSubscriptionClaim extends AutoSubscription, AbstractCardTransfer, PossibleAddingOperationUIDObject
{
	/**
	 * Подключен ли ресурс списания к мобильному банку
	 * @return true - подключен
	 */
	boolean isConnectChargeOffResourceToMobileBank();

	/**
	 * @return выполнить сразу?(true - да)
	 */
	public Boolean isExecutionNow();

	/**
	 * @return дата и время изменения подписки
	 */
	public Calendar getUpdateDate();

	/**
	 * @return Дата и время создания подписки
	 */
	public Calendar getCreateDate();

	/**
	 * @return Необходимость подтверждения операции
	 */
	public boolean isNeedConfirmation();

	/**
	 * @return Канал создания подписки(IB - интернет банк, VSP - ВСП, US - устройство самообслуживания)
	 */
	public ChannelType getChannelType();

	/**
	 * Установить канал создания подписки
	 * (IB - интернет банк, VSP - ВСП, US - устройство самообслуживания)
	 * @param channelType тип канала
	 */
	public void setChannelType(ChannelType channelType);

	/**
	 * Максимальная сумма платежей в месяц
	 * @return максимальную сумму платежей в месяц
	 */
	Money getMaxSumWritePerMonth();

	/**
	 * Причина нахождения автоплатежа в данном состоянии
	 * @return причина нахождения автоплатежа в данном состоянии
	 */
	String getReasonDescription();

	/**
	 * @return  номер УС, с которого вошел клиент
	 */
	String getCodeATM();

	/**
	 * @return сообщение получателю платежа
	 */
	String getMessageToRecipient();

	/**
	 * Проверить нахождение карты списания и зачисления в одном ТБ
	 * @return true - да
	 */
	boolean isSameTB() throws GateException, GateLogicException;
}

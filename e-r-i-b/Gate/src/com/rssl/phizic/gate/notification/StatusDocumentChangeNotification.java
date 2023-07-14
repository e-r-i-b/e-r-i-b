package com.rssl.phizic.gate.notification;

import com.rssl.phizic.notifications.Notification;

/**
 * Изменение статуса документа
 */
public interface StatusDocumentChangeNotification extends Notification
{
	public static String PAYMENT_STATUS_REJECT   = "R";// отказан
	public static String PAYMENT_STATUS_ACCEPT   = "C";// проведён
	public static String PAYMENT_STATUS_DELETED   = "D";// удалён

    @Deprecated
	long getApplicationKind();

	@Deprecated
	String getApplicationKey();


   /**
    * Внешний идентификатор документа
    *
    * @return Внешний ID Документа
    */
   String getDocumentId();
   /**
    * Новый статус документа.
    *
    * @return Новый статус документа.
    */
   String getStatus();
   /**
    * Текст ошибки, если статус ошибочен.
    *
    * @return Текст ошибки, если статус ошибочен.
    */
   String getError();
}

package com.rssl.phizic.person;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

/**
 * @author Erkin
 * @ created 02.08.2014
 * @ $Author$
 * @ $Revision$
 */
@PlainOldJavaObject
@SuppressWarnings("PublicField")
public class UpdateErmbProfileOptions
{
	/**
	 * Флажок: отправить изменения ЕРМБ-профиля в СОС (UpdateProfile)
	 */
	public boolean notifyMSS;

	/**
	 * Флажок: отправить изменения ЕРМБ-профиля в МБК (телефоны)
	 */
	public boolean notifyMBK;

	/**
	 * Флажок: отправить изменения ЕРМБ-профиля в ЦСА (телефоны)
	 */
	public boolean notifyCSA;

	/**
	 * Флажок: отправить изменения ЕРМБ-профиля в ЦОД (переключатель МБК/ЕРМБ)
	 */
	public boolean notifyCOD;

	/**
	 * Флажок: отправить изменения ЕРМБ-профиля в WAY (переключатель МБК/ЕРМБ)
	 */
	public boolean notifyWAY;

	/**
	 * Флажок: отправить изменения ЕРМБ-профиля клиенту (СМС с уведомлением)
	 */
	public boolean notifyClient;
}

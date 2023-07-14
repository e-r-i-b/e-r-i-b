package com.rssl.phizic.security.crypto;

import java.util.Date;
import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 20.12.2006
 * @ $Author: gladishev $
 * @ $Revision: 5701 $
 */
public interface Certificate extends Serializable
{
	/**
	 * @return ID сертификата
	 */
	String getId();

	/**
	 * @return Наименование сертификата
	 */
	String getName();

	/**
	 * @return Организация
	 */
	String getOrganization();

	/**
	 * @return годен до (GMT)
	 */
	public Date getExpiration();

	/**
	 * @return выдан (GMT)
	 */
	public Date getIssue();

	/**
	 * @return Описание
	 */
	public String getDescription();

	/**
	 * @return Номер ключевой пары
	 */
	public String getKeyPairId();

	/**
	 * Проверка сертификатов на равенство
	 */
	public boolean equals(Object obj);

	/**
	 * @return хэш код сертификата
	 */
	public int hashCode();

}


package com.rssl.phizic.gate.payments.systems.recipients;

/**
 * Услуга
 * @author mihaylov
 * @ created 07.04.2010
 * @ $Author$
 * @ $Revision$
 */

public interface Service
{
	/**
    * Наименование услуги в биллинговой системе
    * @return Наименование услуги.
    */
   String getName();
	/**
    * Идентификатор услуги в биллинговой системе
    * @return идентификатор услуги.
    */
   String getCode();
}

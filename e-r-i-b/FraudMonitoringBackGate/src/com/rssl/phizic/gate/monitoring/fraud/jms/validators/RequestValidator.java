package com.rssl.phizic.gate.monitoring.fraud.jms.validators;

/**
 * Валидатор запроса от ВС ФМ
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public interface RequestValidator
{
	/**
	 * @return true - проверку прошел
	 */
	boolean validate();

	/**
	 * @return сообщение об ощибке
	 */
	String getMessage();
}

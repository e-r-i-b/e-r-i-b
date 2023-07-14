package com.rssl.phizic.gate.depo;

/** Операции над ценными бумагами
 *
 * @author akrenev
 * @ created 15.12.2011
 * @ $Author$
 * @ $Revision$
 */
public enum TransferOperation
{
	/**
	 * перевод
	 */
	TRANFER("231"),
	/**
	 * залоговые операции
	 */
	RECEPTION("240"),
	/**
	 * Перевод между разделами счета депо
	 */
	INTERNAL_TRANFER("220");

	private String value;

	/**
	 * @return код операции
	 */
	public String getValue()
	{
		return value;
	}

	private TransferOperation(String value)
	{
		this.value = value;
	}
}
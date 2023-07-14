package com.rssl.phizic.logging.operations.context;

/** Объект гетером и сетером operUID'а
 * @author akrenev
 * @ created 10.10.2011
 * @ $Author$
 * @ $Revision$
 */
public interface PossibleAddingOperationUIDObject
{
	/**
	 * задать operUID для объекта
	 * @param operUID задаваемый operUID
	 */
	void setOperationUID(String operUID);

	/**
	 * @return operUID объекта
	 */
	String getOperationUID();
}

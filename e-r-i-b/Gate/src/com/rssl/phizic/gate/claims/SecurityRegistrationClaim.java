package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.depo.SecurityOperationType;

import java.util.List;

/**
 * @author lukina
 * @ created 29.09.2010
 * @ $Author$
 * @ $Revision$
 */

public interface SecurityRegistrationClaim extends DepoAccountClaimBase
{
	/**
	 * Наименование депозитария получателя, указывается только для операций
	 * 231 – EXTERNAL_TRANSFER и 240 – EXTERNAL_RECEPTION
	 * @return corrDepositary
	 */
	String getCorrDepositary();
	
	/**
	 * Депозитарный  код выпуска ценной бумаги
	 * @return insideCode
	 */
	String getInsideCode();

	/**
	 * Наименование ценной бумаги
	 * @return securityName
	 */
	String getSecurityName();

	/**
	 * Регистрационный номер ценной бумаги для эмиссионных,
	 * для неэмиссионных серия(при наличии) номинал и номер
	 * @return securityNumber
	 */
	String getSecurityNumber();

	/**
	 * Эмитент ценной бумаги
	 * @return issu
	 */
	String getIssuer();

	/**
	 * Планируемые операции над ценной бумагой
	 * @return operations
	 */
	List<SecurityOperationType> getOperations();

	/**
	 * Наименования операций клиента одной строкой
	 * @return clientOperationDescription
	 */
	String getClientOperationDescription();

	/**
	 * Наименования операций клиента
	 * @return список clientOperationDescription'ов
	 */
	List<String> getClientOperationDescriptionsList();
}


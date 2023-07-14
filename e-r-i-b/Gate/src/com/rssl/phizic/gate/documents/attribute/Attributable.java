package com.rssl.phizic.gate.documents.attribute;

import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Map;

/**
 * Интерфейс гейтового документа, позволяющего передавать дополнительные поля
 *
 * @author khudyakov
 * @ created 28.04.14
 * @ $Author$
 * @ $Revision$
 */
public interface Attributable
{
	/**
	 * @return список дополнительных атрибутов
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	Map<String, ExtendedAttribute> getExtendedAttributes() throws GateException;
}

package com.rssl.common.forms.doc;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.utils.annotation.PublicDefaultCreatable;

import java.io.Serializable;

/**
 * @author Roshka
 * @ created 13.10.2006
 * @ $Author$
 * @ $Revision$
 */
@PublicDefaultCreatable
public interface BusinessDocumentHandler<SO extends StateObject> extends Serializable
{
	/**
	 * ќбработать документ
	 * @param document документ
	 * @param stateMachineEvent
	 * @throws DocumentLogicException неправильно заполнен документ, нужно исправить ошибки
	 */
	void process(SO document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException;

	/**
	 * ”становить значение параметра
	 * @param name им€
	 * @param value значение
	 */
	void setParameter(String name, String value);

	/**
	 * ѕолучить значение
	 * @param name им€ параметра
	 * @return значение
	 */
	String getParameter(String name);
}

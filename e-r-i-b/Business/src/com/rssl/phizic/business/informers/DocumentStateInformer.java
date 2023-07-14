package com.rssl.phizic.business.informers;

import com.rssl.phizic.business.BusinessException;

import java.util.List;

/**
 * Информатор клиента о каком-либо событии
 *
 * @author khudyakov
 * @ created 14.08.14
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentStateInformer
{
	/**
	 * Информировать
	 * @return список сообщений
	 */
	List<String> inform() throws BusinessException;

	/**
	 * @return true - информатор активен
	 */
	boolean isActive();
}

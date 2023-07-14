package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

import javax.xml.transform.Source;
import java.util.Map;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * @author Evgrafov
 * @ created 03.11.2005
 * @ $Author: osminin $
 * @ $Revision$
 */
//TODO Имеет смысл, если это не скажется на быстродействие,
//оставить только 1 метод, возвращающий DOM
public interface EntityListSource
{
	/**
	 * построения из мапа xml формата EntityList в виде Source.
	 * @param params параметры создания EntityList (критерии отбора etc)
	 * @return источник XML
	 * @throws BusinessException
	 */
    Source getSource( Map<String,String> params ) throws BusinessException, BusinessLogicException;

	/**
	 * построения из мапа xml формата EntityList в виде Document
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	Document getDocument ( Map<String,String> params ) throws BusinessException, BusinessLogicException;
}

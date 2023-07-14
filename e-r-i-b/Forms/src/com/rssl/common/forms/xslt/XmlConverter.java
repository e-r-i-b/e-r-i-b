package com.rssl.common.forms.xslt;

import com.rssl.common.forms.FormException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

/**
 * @author Evgrafov
 * @ created 20.02.2006
 * @ $Author: erkin $
 * @ $Revision: 69392 $
 */

public interface XmlConverter
{
	/**
	 * Данные для преобразования
	 */
	Source getData();

	/**
	 * Данные для преобразования
	 */
	void setData(Source dataSource);

	/**
	 * Установить параметр трансформации, будет передан в Transformer.setParameter()
	 * @param name
	 * @param value
	 */
	void setParameter(String name, Object value);

	void setDictionary(String dictionaryName, Source dictionarySource);

	StringBuffer convert() throws FormException;

	void convert(Result result) throws FormException;
}

package com.rssl.phizic.business.ant.pfp.dictionary;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Оболочка для записи справочника с несколькими картинками
 * @author koptyaev
 * @ created 25.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class PFPDictionaryRecordWrapperMultiImage<R extends PFPDictionaryRecord> extends PFPDictionaryRecordWrapper<R>
{
	private Map<String,Image> images = new HashMap<String, Image>(); //картинки

	/**
	 * инициализация записью и картинкой
	 * @param record запись
	 * @param images мапа ключ - картинка
	 */
	public PFPDictionaryRecordWrapperMultiImage(R record, Map<String,Image> images)
	{
		super(record);
		this.images.putAll(images);
	}

	/**
	 * @param key ключ картинки
	 * @return картинка
	 */
	public Image getImage(String key)
	{
		return images.get(key);
	}
}

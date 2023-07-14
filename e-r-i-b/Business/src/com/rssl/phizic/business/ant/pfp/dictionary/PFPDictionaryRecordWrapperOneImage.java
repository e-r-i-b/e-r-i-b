package com.rssl.phizic.business.ant.pfp.dictionary;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.image.Image;

/**
 * Оболочка для записи справочника с одной картинкой
 * @author koptyaev
 * @ created 24.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class PFPDictionaryRecordWrapperOneImage<R extends PFPDictionaryRecord> extends PFPDictionaryRecordWrapper<R>
{
	private Image image; //картинка

	/**
	 * инициализация записью и картинкой
	 * @param record запись
	 * @param image картинка
	 */
	public PFPDictionaryRecordWrapperOneImage(R record, Image image)
	{
		super(record);
		this.image = image;
	}

	/**
	 * @return картинка
	 */
	public Image getImage()
	{
		return image;
	}

}

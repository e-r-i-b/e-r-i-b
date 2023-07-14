package com.rssl.phizic.business.ant.pfp.dictionary;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.image.Image;

/**
 * �������� ��� ������ ����������� � ����� ���������
 * @author koptyaev
 * @ created 24.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class PFPDictionaryRecordWrapperOneImage<R extends PFPDictionaryRecord> extends PFPDictionaryRecordWrapper<R>
{
	private Image image; //��������

	/**
	 * ������������� ������� � ���������
	 * @param record ������
	 * @param image ��������
	 */
	public PFPDictionaryRecordWrapperOneImage(R record, Image image)
	{
		super(record);
		this.image = image;
	}

	/**
	 * @return ��������
	 */
	public Image getImage()
	{
		return image;
	}

}

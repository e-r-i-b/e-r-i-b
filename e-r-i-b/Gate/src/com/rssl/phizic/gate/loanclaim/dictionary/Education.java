package com.rssl.phizic.gate.loanclaim.dictionary;

/**
 * @author Nady
 * @ created 25.12.2013
 * @ $Author$
 * @ $Revision$
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ������ ����������� "�����������"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "education")
public class Education extends AbstractDictionaryEntry
{
	/**
	 * ��� ���������� ������ �� ������ ��������� ������� ���� ������������� ������� �����������
	 */
	@XmlElement(name = "highEducationCourseRequired", required = true)
	private boolean highEducationCourseRequired;

	public boolean isHighEducationCourseRequired()
	{
		return highEducationCourseRequired;
	}

	public void setHighEducationCourseRequired(boolean highEducationCourseRequired)
	{
		this.highEducationCourseRequired = highEducationCourseRequired;
	}
}

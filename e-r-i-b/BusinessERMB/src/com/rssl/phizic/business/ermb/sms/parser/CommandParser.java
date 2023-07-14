package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.annotation.NonThreadSafe;
import com.rssl.phizic.common.types.annotation.Statefull;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.person.Person;

/**
 * @author Erkin
 * @ created 26.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ���-�������
 */
@Statefull
@NonThreadSafe
public interface CommandParser
{
	/**
	 * @return ��� �������
	 * ������������ � �����������
	 */
	String getParserName();

	/**
	 * @param module - ������
	 */
	@MandatoryParameter
	void setModule(Module module);

	/**
	 * @param person - ������
	 */
	@MandatoryParameter
	void setPerson(Person person);

	/*
	 * @param phone - �������, � �������� ������ �������
	 */
	@MandatoryParameter
	void setPhone(String phone);

	/**
	 * @param keywords - ������� � ��������� ������� ���-�������
	 */
	@MandatoryParameter
	void setKeywords(Dictionary keywords);

	@MandatoryParameter
	void setScanner(Scanner scanner);

	/**
	 * @param text - ����� ��� (�� ������ ���������)
	 */
	@MandatoryParameter
	void setText(String text);

	@MandatoryParameter
	void setErrorCollector(ParseErrorCollector parseErrorCollector);

	/**
	 * @return ���-������� ��� null, ���� ������ �� ���� ��������� ���-�������
	 */
	Command parseCommand();
}

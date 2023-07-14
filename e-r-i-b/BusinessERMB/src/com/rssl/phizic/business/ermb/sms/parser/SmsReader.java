package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.annotation.Statefull;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.person.Person;

import static com.rssl.phizic.business.ermb.sms.parser.SmsPreprocessor.preprocess;

/**
 * @author Erkin
 * @ created 07.11.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ������ ��� � ���-������ ����
 */
@Statefull
public class SmsReader
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private Module module;

	private Person person;

	private String phone;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @param module - ������
	 */
	@MandatoryParameter
	public void setModule(Module module)
	{
		this.module = module;
	}

	/**
	 * @param person - ������
	 */
	@MandatoryParameter
	public void setPerson(Person person)
	{
		this.person = person;
	}

	/**
	 * @param phone - �������
	 */
	@MandatoryParameter
	public void setPhone(String phone)
	{
		this.phone = phone ;
	}

	/**
	 * ��������� ����� ���-�������
	 * @param text - ����� ���-�������
	 * @return ���-������� ��� null, ���� ����� �� ������� ���������
	 */
	public Command read(String text)
	{
		if (module == null)
			throw new IllegalStateException("�� ����� �������� module");
		if (person == null)
			throw new IllegalStateException("�� ����� �������� person");

		String preprocessedText = preprocess(text);
		if (log.isDebugEnabled())
			log.debug("��������� �������������� ���: " + preprocessedText);

		Scanner scanner = new Scanner(preprocessedText);
		SmsParser smsParser = new SmsParser();
		smsParser.setModule(module);
		smsParser.setPerson(person);
		smsParser.setPhone(phone);
		smsParser.setScanner(scanner);
		smsParser.setText(text);
		smsParser.init();

		return smsParser.parse();
	}
}

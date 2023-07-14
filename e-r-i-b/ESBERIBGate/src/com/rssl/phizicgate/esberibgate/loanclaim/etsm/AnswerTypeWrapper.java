package com.rssl.phizicgate.esberibgate.loanclaim.etsm;


/**
 * @author komarov
 * @ created 30.06.15
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������
 */
public abstract class AnswerTypeWrapper
{
	private static final AnswerTypeWrapper YES_NO_ANSWER_TYPE_WRAPPER = new AnswerTypeWrapper()
	{
		@Override
		public String toGate(String value)
		{
			return "1".equals(value)? "Y": "N";
		}
	};

	private static final AnswerTypeWrapper INPUT_FIELD_ANSWER_TYPE_WRAPPER = new AnswerTypeWrapper()
	{
		@Override
		public String toGate(String value)
		{
			return value;
		}
	};

	/**
	 * �������� ������ ������� �� ���� ������
	 * @param type ��� ������
	 * @return �������
	 */
	public static AnswerTypeWrapper getInstance(String type)
	{
		return "0".equals(type)? YES_NO_ANSWER_TYPE_WRAPPER: INPUT_FIELD_ANSWER_TYPE_WRAPPER;
	}

	/**
	 * ����������� ����� � ��������� �������������
	 * @param value ��������
	 * @return �������� ��������
	 */
	public abstract String toGate(String value);
}

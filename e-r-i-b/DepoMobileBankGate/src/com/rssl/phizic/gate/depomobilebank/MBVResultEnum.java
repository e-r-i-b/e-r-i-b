package com.rssl.phizic.gate.depomobilebank;

/**
 * User: Moshenko
 * Date: 12.09.13
 * Time: 14:17
 * ���� ������ MBV
 */
public enum MBVResultEnum
{
    SUCCESS("000_Success"),                 //�����
    PARAMETER_NOT_FIND("001_No_Parameter"), //�� ������ ������������� ��������
    NO_CONNECTION("002_No_Connect"),        //��� ����� � ����� �� ����� �������
    OBJECT_READY("003_Already_Done"),       //������������� � ��������� ������ ��� ��������� � ������ �������;
    PROGRAM_ERROR("004_Exception");         //��� ����������� ������;

    private String returnCode;

    MBVResultEnum(String returnCode)
    {
        this.returnCode = returnCode;
    }

    public String toValue()
    {
        return returnCode;
    }

    public String getValue()
    {
        return returnCode;
    }

	public static MBVResultEnum fromValue(String value)
	{
		for(MBVResultEnum code : values())
			if(code.getValue().equals(value))
				return code;

		throw new IllegalArgumentException("����������� ��� ������ ���[" + value + "]");
	}
}

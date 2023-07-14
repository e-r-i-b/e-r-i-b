package com.rssl.phizic.gate.dictionaries;

/**
 * @author Kosyakova
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class Country  extends DictionaryRecordBase implements DictionaryRecord
{
	//������� �������������
	private Long id;
	//��� ������. 2� ��� 3� ����������
	private String code;
	//��������
    private String name;
	//������ ��������
    private String fullName;
	//�������� ��� ������
	private String intCode;

	public String getIntCode()
	{
		return intCode;
	}

	public void setIntCode(String intCode)
	{
		this.intCode = intCode;
	}

	public Comparable getSynchKey ()
    {
        return id;
    }

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

    public String getName ()
    {
        return name;
    }

    public void setName ( String name )
    {
        this.name=name;
    }

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}
}

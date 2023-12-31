package com.rssl.phizic.test.mbvmock;


import java.util.Calendar;
import java.util.Set;

/**
 * User: Moshenko
 * Date: 10.09.13
 * Time: 9:54
 * ������ �� ������� MBV ������� ClientAccPhRq + ��� ��������
 */
public class ClientAccPh
{
	private Long id;
	/**
	 * ���� ��������
	 */
	private Calendar birthday;
	/**
	 * ���
	 */
	private String firstName;
	/**
	 * �������
	 */
	private String lastName;

	/**
	 * ��������
	 */
	private String middleName;
	/**
	 * ��� ���������
	 */
	private String idType;
	/**
	 * ����� ���������
	 */
	private String idSeries;
	/**
	 * ����� ���������.
	 */
	private String idNum;

    /**
     * �������� �������
     */
    private Set<ClinetPhone> phones;

    /**
     * ����� �������
     */
    private Set<ClinetAcc> accNumbers;

    /**
     * @return ��� ��������
     */
    private String retCode;
    /**
     * �������������� ���������� � ���������� ��������.
     */
    private String resultMessage;


	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public String getIdType()
	{
		return idType;
	}

	public void setIdType(String idType)
	{
		this.idType = idType;
	}

	public String getIdSeries()
	{
		return idSeries;
	}

	public void setIdSeries(String idSeries)
	{
		this.idSeries = idSeries;
	}

	public String getIdNum()
	{
		return idNum;
	}

	public void setIdNum(String idNum)
	{
		this.idNum = idNum;
	}

    public Set<ClinetPhone> getPhones() {
        return phones;
    }

    public void setPhones(Set<ClinetPhone> phones) {
        this.phones = phones;
    }

    public Set<ClinetAcc> getAccNumbers() {
        return accNumbers;
    }

    public void setAccNumbers(Set<ClinetAcc> accNumbers) {
        this.accNumbers = accNumbers;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}

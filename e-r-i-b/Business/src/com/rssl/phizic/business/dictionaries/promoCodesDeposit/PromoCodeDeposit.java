package com.rssl.phizic.business.dictionaries.promoCodesDeposit;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.utils.BeanHelper;

import java.util.Calendar;

/**
 * ����� - ��� ��� �������� �������.
 *
 * @ author: Gololobov
 * @ created: 10.12.14
 * @ $Author$
 * @ $Revision$
 */
public class PromoCodeDeposit extends MultiBlockDictionaryRecordBase implements DictionaryRecord
{
	private Long id;            //�� ���������
	private Long code;          //���������� ��� ������
	private Long codeG;         //��� �����-�����
	private String mask;        //����� �����-����
	private Long codeS;         //��� �������� �������
	private Calendar dateBegin; //���� ������ ������� ��������� �����-����, �YYYY.MM.DD�
	private Calendar dateEnd;   //���� ��������� ������� ��������� �����-����, �YYYY.MM.DD�
	private String srokBegin;   //���� �������� �����-����, ������� �� ��� ����� ��������.
								//������ YYMMDDD, ��� YY-���������� ���; MM����������� �������;	 DDD����������� ����
	private String srokEnd;     //���� �������� �����-����, ������� �� ��� ��������� ������� ��������� �����-����.
								//������ YYMMDDD, ��� YY-���������� ���; MM����������� �������; DDD����������� ����
	private Long numUse;        //��������� ���������� ��� ������������� ��������
	private int prior;          //��������� ������� � �����-�����: �0�-������������ ������ ��� �����-����; �1� - ������������ ��� ��������� �����
	private Boolean abRemove;   //����������� �������� ���������� �����-����
	private Long activeCount;   //���������� ����������� ������-����� � �������
	private Long histCount;     //���������� ��������� ������-����� ��������
	private String nameAct;     //�������� ����� �����
	private String nameS;       //������� �������� �����-����
	private String nameF;       //��������� �������� �����-����

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getCode()
	{
		return code;
	}

	public void setCode(Long code)
	{
		this.code = code;
	}

	public Long getCodeG()
	{
		return codeG;
	}

	public void setCodeG(Long codeG)
	{
		this.codeG = codeG;
	}

	public String getMask()
	{
		return mask;
	}

	public void setMask(String mask)
	{
		this.mask = mask;
	}

	public Long getCodeS()
	{
		return codeS;
	}

	public void setCodeS(Long codeS)
	{
		this.codeS = codeS;
	}

	public Calendar getDateBegin()
	{
		return dateBegin;
	}

	public void setDateBegin(Calendar dateBegin)
	{
		this.dateBegin = dateBegin;
	}

	public Calendar getDateEnd()
	{
		return dateEnd;
	}

	public void setDateEnd(Calendar dateEnd)
	{
		this.dateEnd = dateEnd;
	}

	public String getSrokBegin()
	{
		return srokBegin;
	}

	public void setSrokBegin(String srokBegin)
	{
		this.srokBegin = srokBegin;
	}

	public String getSrokEnd()
	{
		return srokEnd;
	}

	public void setSrokEnd(String srokEnd)
	{
		this.srokEnd = srokEnd;
	}

	public Long getNumUse()
	{
		return numUse;
	}

	public void setNumUse(Long numUse)
	{
		this.numUse = numUse;
	}

	public int getPrior()
	{
		return prior;
	}

	public void setPrior(int prior)
	{
		this.prior = prior;
	}

	public Boolean getAbRemove()
	{
		return abRemove;
	}

	public void setAbRemove(Boolean abRemove)
	{
		this.abRemove = abRemove;
	}

	public Long getActiveCount()
	{
		return activeCount;
	}

	public void setActiveCount(Long activeCount)
	{
		this.activeCount = activeCount;
	}

	public Long getHistCount()
	{
		return histCount;
	}

	public void setHistCount(Long histCount)
	{
		this.histCount = histCount;
	}

	public String getNameAct()
	{
		return nameAct;
	}

	public void setNameAct(String nameAct)
	{
		this.nameAct = nameAct;
	}

	public String getNameS()
	{
		return nameS;
	}

	public void setNameS(String nameS)
	{
		this.nameS = nameS;
	}

	public String getNameF()
	{
		return nameF;
	}

	public void setNameF(String nameF)
	{
		this.nameF = nameF;
	}

	public Comparable getSynchKey()
	{
		return code;
	}

	public void updateFrom(DictionaryRecord that)
	{
		Long oldId = id;
        String oldUuid = getUuid();
		BeanHelper.copyProperties(this, that);
		id = oldId;
        setUuid(oldUuid);
	}
}

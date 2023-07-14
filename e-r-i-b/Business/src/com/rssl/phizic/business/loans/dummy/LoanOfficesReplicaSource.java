package com.rssl.phizic.business.loans.dummy;

import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateFactory;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

/**
 * @author gladishev
 * @ created 04.09.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoanOfficesReplicaSource implements ReplicaSource
{
	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator<LoanOffice> iterator() throws GateException
	{
		List<LoanOffice> offices = new ArrayList<LoanOffice>();

		offices.add(createOffice(new BigDecimal(1), "�������� ���� �����", null, null, null, true));
		offices.add(createOffice(new BigDecimal(2), "�� ����������", null, null, null, false));
		offices.add(createOffice(new BigDecimal(3), "�� ��������� \"������������\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(4), "�� \"�����������\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(5), "�� ��������", null, null, null, false));
		offices.add(createOffice(new BigDecimal(6), "��� \"�������\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(7), "�� \"��������\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(8), "�� \"���������\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(9), "�� \"��������\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(500), "�������� ����", null, null, null, false));
		offices.add(createOffice(new BigDecimal(700), "�� ���������", null, null, null, false));
		offices.add(createOffice(new BigDecimal(800), "�� \"�����������\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(900), "����������� ���", null, null, null, false));
		offices.add(createOffice(new BigDecimal(999), "�� �� ������� \"��������-�������\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1001), "�� \"������������\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1002), "�� \"����������-���������\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1003), "�� \"���������������-�����\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1004), "�� \"���-���������������\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1005), "�� \"�����\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1006), "�� \"����������-����\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1007), "�� \"��������-������\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1008), "�� \"������������\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1009), "�� \"�������\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1010), "�� \"���������\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1011), "�� \"�����������\"", null, null, null, false));

		return offices.iterator();
	}

    public void close()
    {}

	private LoanOffice createOffice(BigDecimal key, String name, String address, String telephone, String info, boolean main)
	{
		LoanOffice office = new LoanOffice();
		office.setSynchKey(key);
		office.setName(name);
		if (address != null) office.setAddress(address);
		if (telephone != null) office.setTelephone(telephone);
		if (info != null) office.setInfo(info);
		office.setMain(main);

		return office;
	}
}

package com.rssl.phizic.gate.mbv;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Moshenko
 * Date: 12.09.13
 * Time: 17:12
 *  �������� � ������� ������� � ��� (��������� ������� ClientAccPhRq)
 */
public interface ClientAccPh
{
    //������ ������ ������� ������������ � ���
    public List<String> getAccList();

    public void setAccList(List<String> accList);

    //��������, ������������������ � ���
    public List<ClentPhone> getPhoneList();

    public void setPhoneList(List<ClentPhone> phoneList) ;
}

package com.rssl.phizic.gate.mobilebank;


/**
 * User: Moshenko
 * Date: 17.09.13
 * Time: 11:11
 * ���������� �� �������������� ������ � ���
 */
public class InfoCardImpl
{
    private String pan;  //����� �����
    private boolean block;  //������� ����������
    public String getPan()
    {
        return pan;
    }

    public void setPan(String pan)
    {
        this.pan = pan;
    }

    public boolean isBlock()
    {
        return block;
    }

    public void setBlcok(boolean block)
    {
        this.block = block;
    }
}

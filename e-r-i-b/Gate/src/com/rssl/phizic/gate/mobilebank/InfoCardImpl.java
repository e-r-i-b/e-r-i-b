package com.rssl.phizic.gate.mobilebank;


/**
 * User: Moshenko
 * Date: 17.09.13
 * Time: 11:11
 * Информация об информационных картах в МБК
 */
public class InfoCardImpl
{
    private String pan;  //номер карты
    private boolean block;  //признак блокировки
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

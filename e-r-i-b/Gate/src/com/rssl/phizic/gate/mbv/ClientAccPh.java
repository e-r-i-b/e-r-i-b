package com.rssl.phizic.gate.mbv;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Moshenko
 * Date: 12.09.13
 * Time: 17:12
 *  Телефоны и счетами клиента в МБВ (результат запроса ClientAccPhRq)
 */
public interface ClientAccPh
{
    //Номера счетов вкладов подключённых к МБВ
    public List<String> getAccList();

    public void setAccList(List<String> accList);

    //Телефоны, зарегистрированные в МБВ
    public List<ClentPhone> getPhoneList();

    public void setPhoneList(List<ClentPhone> phoneList) ;
}

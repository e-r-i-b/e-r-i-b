package com.rssl.phizic.gate.depomobilebank;

import com.rssl.phizic.gate.mbv.ClentPhone;
import com.rssl.phizic.gate.mbv.ClientAccPh;

import java.util.List;
import java.util.Set;

/**
 * User: Moshenko
 * Date: 12.09.13
 * Time: 17:06
 *
 */
public class MBVClientAccPh implements ClientAccPh
{
    private List<String> accList;

    private List<ClentPhone> phoneList;

    public List<String> getAccList() {
        return accList;
    }

    public void setAccList(List<String> accList) {
        this.accList = accList;
    }

    public List<ClentPhone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<ClentPhone> phoneList) {
        this.phoneList = phoneList;
    }
}

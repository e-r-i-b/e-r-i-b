/**
 * MBVMigratorSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.depomobilebank.generated;

import com.rssl.phizic.test.webgate.depomobilebank.MBVMigratorSoapWrapper;
import org.apache.commons.lang.StringUtils;

public class MBVMigratorSoapBindingImpl implements com.rssl.phizic.test.webgate.depomobilebank.generated.MBVMigrator
{
    private MBVMigratorSoapWrapper wrapper = new MBVMigratorSoapWrapper();

    public com.rssl.phizic.test.webgate.depomobilebank.generated.SendMessageResponse sendMessage(com.rssl.phizic.test.webgate.depomobilebank.generated.SendMessage parameters) throws java.rmi.RemoteException
    {
        String strMessage = parameters.getMessage();

        if (StringUtils.contains(strMessage,"ClientAccPhRq"))
            return wrapper.clientAccPh(strMessage);
        else if (StringUtils.contains(strMessage,"GetClientByPhoneRq"))
            return wrapper.getClientByPhone(strMessage);
        else if ((StringUtils.contains(strMessage,"BeginRq")))
            return wrapper.beginMigration(strMessage);
        else if ((StringUtils.contains(strMessage,"CommitRq")))
            return wrapper.commitMigration(strMessage);
        else if ((StringUtils.contains(strMessage,"RollbackRq")))
            return wrapper.rollbackMigration(strMessage);
        else if ((StringUtils.contains(strMessage,"ReverseRq")))
	        return wrapper.reverseMigration(strMessage);
        else if ((StringUtils.contains(strMessage,"DiscByPhoneRq")))
            return wrapper.discByPhone(strMessage);
        return  null;
    }

}

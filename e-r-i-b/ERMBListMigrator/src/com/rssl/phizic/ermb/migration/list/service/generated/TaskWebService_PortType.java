/**
 * TaskWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ermb.migration.list.service.generated;

public interface TaskWebService_PortType extends java.rmi.Remote {
    public com.rssl.phizic.ermb.migration.list.service.generated.StatusRs loadClients(com.rssl.phizic.ermb.migration.list.service.generated.LoadClientsActionRq loadClientsActionRq) throws java.rmi.RemoteException;
    public com.rssl.phizic.ermb.migration.list.service.generated.StatusRs smsDelivery(com.rssl.phizic.ermb.migration.list.service.generated.SmsDeliveryActionRq smsSendsSegRq) throws java.rmi.RemoteException;
}

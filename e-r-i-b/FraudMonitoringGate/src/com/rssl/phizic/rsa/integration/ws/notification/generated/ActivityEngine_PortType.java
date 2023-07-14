/**
 * ActivityEngine_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.notification.generated;

public interface ActivityEngine_PortType extends java.rmi.Remote {

    /**
     * Обновление резолюции события
     */
    public com.rssl.phizic.rsa.integration.ws.notification.generated.UpdateActivityResponseType updateActivity(com.rssl.phizic.rsa.integration.ws.notification.generated.UpdateActivityRequestType request) throws java.rmi.RemoteException;

    /**
     * Получить резолюцию события
     */
    public com.rssl.phizic.rsa.integration.ws.notification.generated.GetResolutionResponseType getResolution(com.rssl.phizic.rsa.integration.ws.notification.generated.GetResolutionRequestType request) throws java.rmi.RemoteException;
}

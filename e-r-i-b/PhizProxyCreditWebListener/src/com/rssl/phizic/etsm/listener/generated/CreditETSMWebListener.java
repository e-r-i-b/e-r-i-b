/**
 * CreditETSMWebListener.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.etsm.listener.generated;

public interface CreditETSMWebListener extends java.rmi.Remote {
    public com.rssl.phizic.etsm.listener.generated.QueryOfferRsType queryOffer(com.rssl.phizic.etsm.listener.generated.QueryOfferRqType parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.etsm.listener.generated.DeleteOfferRsType deleteOffer(com.rssl.phizic.etsm.listener.generated.DeleteOfferRqType parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.etsm.listener.generated.AddOfficeLoanRsType addOfficeLoan(com.rssl.phizic.etsm.listener.generated.AddOfficeLoanRqType parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.etsm.listener.generated.GetOfficeLoansByFIODulBDRs getOfficeLoansByFIODulBD(com.rssl.phizic.etsm.listener.generated.GetOfficeLoansByFIODulBDRq parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.etsm.listener.generated.GetOfferOfficePriorByFIODulBDRs getOfferOfficePriorByFIODulBD(com.rssl.phizic.etsm.listener.generated.GetOfferOfficePriorByFIODulBDRq parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.etsm.listener.generated.UpdateOfferOfficePriorVisibleCounterRsType updateOfferOfficePriorVisibleCounter(com.rssl.phizic.etsm.listener.generated.UpdateOfferOfficePriorVisibleCounterRqType parameters) throws java.rmi.RemoteException;
}

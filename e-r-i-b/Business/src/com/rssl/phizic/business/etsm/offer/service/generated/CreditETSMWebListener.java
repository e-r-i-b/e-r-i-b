/**
 * CreditETSMWebListener.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.business.etsm.offer.service.generated;

public interface CreditETSMWebListener extends java.rmi.Remote {
    public com.rssl.phizic.business.etsm.offer.service.generated.QueryOfferRsType queryOffer(com.rssl.phizic.business.etsm.offer.service.generated.QueryOfferRqType parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.business.etsm.offer.service.generated.DeleteOfferRsType deleteOffer(com.rssl.phizic.business.etsm.offer.service.generated.DeleteOfferRqType parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.business.etsm.offer.service.generated.AddOfficeLoanRsType addOfficeLoan(com.rssl.phizic.business.etsm.offer.service.generated.AddOfficeLoanRqType parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.business.etsm.offer.service.generated.GetOfficeLoansByFIODulBDRs getOfficeLoansByFIODulBD(com.rssl.phizic.business.etsm.offer.service.generated.GetOfficeLoansByFIODulBDRq parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.business.etsm.offer.service.generated.GetOfferOfficePriorByFIODulBDRs getOfferOfficePriorByFIODulBD(com.rssl.phizic.business.etsm.offer.service.generated.GetOfferOfficePriorByFIODulBDRq parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.business.etsm.offer.service.generated.UpdateOfferOfficePriorVisibleCounterRsType updateOfferOfficePriorVisibleCounter(com.rssl.phizic.business.etsm.offer.service.generated.UpdateOfferOfficePriorVisibleCounterRqType parameters) throws java.rmi.RemoteException;
}

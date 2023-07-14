/**
 * DepoCODPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.sbrf.ws.generated.depocod;

public interface DepoCODPortType extends java.rmi.Remote {

    /**
     * Service definition of function ns__reserveForCredit
     */
    public byte[] reserveForCredit(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__cancelReserveForCredit
     */
    public byte[] cancelReserveForCredit(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__payForCredit
     */
    public byte[] payForCredit(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__accountRemainder
     */
    public byte[] accountRemainder(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__operationResult
     */
    public byte[] operationResult(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getAccountInfo
     */
    public byte[] getAccountInfo(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__createNewAccountNumber
     */
    public byte[] createNewAccountNumber(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__createForm190ForCredit
     */
    public byte[] createForm190ForCredit(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__deleteForm190ForCredit
     */
    public byte[] deleteForm190ForCredit(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getClientAccounts
     */
    public byte[] getClientAccounts(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__createNewAccount
     */
    public byte[] createNewAccount(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__paySumma
     */
    public byte[] paySumma(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__addSumma
     */
    public byte[] addSumma(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getAccHistory
     */
    public byte[] getAccHistory(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getClients
     */
    public byte[] getClients(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getOperStatictics
     */
    public byte[] getOperStatictics(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__payForCreditByItems
     */
    public byte[] payForCreditByItems(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getClientsWithAccounts
     */
    public byte[] getClientsWithAccounts(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__findAccNumByCardNum
     */
    public byte[] findAccNumByCardNum(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getSuccessCreditOperations
     */
    public byte[] getSuccessCreditOperations(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getAccCreditsByDates
     */
    public byte[] getAccCreditsByDates(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__acc2acc
     */
    public byte[] acc2Acc(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__acc2card
     */
    public byte[] acc2Card(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__card2acc
     */
    public byte[] card2Acc(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__card2card
     */
    public byte[] card2Card(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__acc2accClose
     */
    public byte[] acc2AccClose(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__acc2cardClose
     */
    public byte[] acc2CardClose(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__acc2accOpen
     */
    public byte[] acc2AccOpen(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__card2accOpen
     */
    public byte[] card2AccOpen(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__acc2accCloseOpen
     */
    public byte[] acc2AccCloseOpen(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__acc2communal
     */
    public byte[] acc2Communal(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__card2communal
     */
    public byte[] card2Communal(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__acc2acc143
     */
    public byte[] acc2Acc143(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__acc2acc365
     */
    public byte[] acc2Acc365(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__acc2acc364internal
     */
    public byte[] acc2Acc364Internal(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__acc2acc364external
     */
    public byte[] acc2Acc364External(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__card2acc143
     */
    public byte[] card2Acc143(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__card2acc365
     */
    public byte[] card2Acc365(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__card2acc364internal
     */
    public byte[] card2Acc364Internal(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__card2acc364external
     */
    public byte[] card2Acc364External(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getEDBOContract
     */
    public byte[] getEDBOContract(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getEDBOContractByCard
     */
    public byte[] getEDBOContractByCard(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getAccHistoryShortExtract
     */
    public byte[] getAccHistoryShortExtract(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getForms190ForClient
     */
    public byte[] getForms190ForClient(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getForm190Info
     */
    public byte[] getForm190Info(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getForm190PayExtract
     */
    public byte[] getForm190PayExtract(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__chargeCommission
     */
    public byte[] chargeCommission(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__payingBack
     */
    public byte[] payingBack(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__createForm190ForCommunal
     */
    public byte[] createForm190ForCommunal(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__createForm190ForCharge
     */
    public byte[] createForm190ForCharge(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__createForm190For143
     */
    public byte[] createForm190For143(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__createForm190For365
     */
    public byte[] createForm190For365(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__createForm190ForLoan
     */
    public byte[] createForm190ForLoan(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__deleteForm190
     */
    public byte[] deleteForm190(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__redrawForms190ForEDBO
     */
    public byte[] redrawForms190ForEDBO(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__setSubscriber
     */
    public byte[] setSubscriber(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__setAccountState
     */
    public byte[] setAccountState(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__acc2payment
     */
    public byte[] acc2Payment(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__card2payment
     */
    public byte[] card2Payment(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__card2createNewAccountNumber
     */
    public byte[] card2CreateNewAccountNumber(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getPaymentInfo
     */
    public byte[] getPaymentInfo(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__operationFullResult
     */
    public byte[] operationFullResult(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__accClose
     */
    public byte[] accClose(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__accOpen
     */
    public byte[] accOpen(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__sendPaymentDraft
     */
    public byte[] sendPaymentDraft(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__reverseAuth
     */
    public byte[] reverseAuth(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__loadCourses
     */
    public byte[] loadCourses(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__accMaintenance
     */
    public byte[] accMaintenance(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__acc2accClose143
     */
    public byte[] acc2AccClose143(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__acc2accClose365
     */
    public byte[] acc2AccClose365(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getAccHistoryFullExtract
     */
    public byte[] getAccHistoryFullExtract(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__credit2acc
     */
    public byte[] credit2Acc(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__acc2credit
     */
    public byte[] acc2Credit(byte[] req) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__getOperDay
     */
    public byte[] getOperDay(byte[] req) throws java.rmi.RemoteException;
}

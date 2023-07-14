/**
 * RequestData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;

public class RequestData  implements java.io.Serializable {
    private com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeListParametersType getEmployeeListRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeMailManagerListParametersType getEmployeeMailManagerListRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeManagerInfoParametersType getEmployeeManagerInfoRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeByIdParametersType getEmployeeByIdRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetCurrentEmployeeParametersType getCurrentEmployeeRq;

    private com.rssl.phizicgate.csaadmin.service.generated.SaveEmployeeParametersType saveEmployeeRq;

    private com.rssl.phizicgate.csaadmin.service.generated.AssignAccessSchemeEmployeeParametersType assignAccessSchemeEmployeeRq;

    private com.rssl.phizicgate.csaadmin.service.generated.DeleteEmployeeParametersType deleteEmployeeRq;

    private com.rssl.phizicgate.csaadmin.service.generated.LockEmployeeParametersType lockEmployeeRq;

    private com.rssl.phizicgate.csaadmin.service.generated.UnlockEmployeeParametersType unlockEmployeeRq;

    private com.rssl.phizicgate.csaadmin.service.generated.ChangeEmployeePasswordParametersType changeEmployeePasswordRq;

    private com.rssl.phizicgate.csaadmin.service.generated.SelfChangePasswordParametersType selfChangePasswordRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetAllowedDepartmentsParametersType getAllowedDepartmentsRq;

    private com.rssl.phizicgate.csaadmin.service.generated.SaveAllowedDepartmentsParametersType saveAllowedDepartmentsRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetAccessSchemeListParametersType getAccessSchemeListRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetAccessSchemeByIdParametersType getAccessSchemeByIdRq;

    private com.rssl.phizicgate.csaadmin.service.generated.SaveAccessSchemeParametersType saveAccessSchemeRq;

    private com.rssl.phizicgate.csaadmin.service.generated.DeleteAccessSchemeParametersType deleteAccessSchemeRq;

    private com.rssl.phizicgate.csaadmin.service.generated.AuthenticationParametersRequestType getAuthenticationParametersRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeSynchronizationDataRequestType getEmployeeSynchronizationDataRq;

    private com.rssl.phizicgate.csaadmin.service.generated.CloseSessionParametersRequestType closeSessionRq;

    private com.rssl.phizicgate.csaadmin.service.generated.ChangeNodeRequestType changeNodeRq;

    private com.rssl.phizicgate.csaadmin.service.generated.SaveOperationContextRequestType saveOperationContextRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetOperationContextRequestType getOperationContextRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetCurrentEmployeeContextParametersType getCurrentEmployeeContextRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetIncomeMailListParametersType getIncomeMailListRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetOutcomeMailListParametersType getOutcomeMailListRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetRemovedMailListParametersType getRemovedMailListRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetMailStatisticsParametersType getMailStatisticsRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetMailEmployeeStatisticsParametersType getMailEmployeeStatisticsRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetMailAverageTimeParametersType getMailAverageTimeRq;

    private com.rssl.phizicgate.csaadmin.service.generated.GetFirstMailDateParametersType getFirstMailDateRq;

    public RequestData() {
    }

    public RequestData(
           com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeListParametersType getEmployeeListRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeMailManagerListParametersType getEmployeeMailManagerListRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeManagerInfoParametersType getEmployeeManagerInfoRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeByIdParametersType getEmployeeByIdRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetCurrentEmployeeParametersType getCurrentEmployeeRq,
           com.rssl.phizicgate.csaadmin.service.generated.SaveEmployeeParametersType saveEmployeeRq,
           com.rssl.phizicgate.csaadmin.service.generated.AssignAccessSchemeEmployeeParametersType assignAccessSchemeEmployeeRq,
           com.rssl.phizicgate.csaadmin.service.generated.DeleteEmployeeParametersType deleteEmployeeRq,
           com.rssl.phizicgate.csaadmin.service.generated.LockEmployeeParametersType lockEmployeeRq,
           com.rssl.phizicgate.csaadmin.service.generated.UnlockEmployeeParametersType unlockEmployeeRq,
           com.rssl.phizicgate.csaadmin.service.generated.ChangeEmployeePasswordParametersType changeEmployeePasswordRq,
           com.rssl.phizicgate.csaadmin.service.generated.SelfChangePasswordParametersType selfChangePasswordRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetAllowedDepartmentsParametersType getAllowedDepartmentsRq,
           com.rssl.phizicgate.csaadmin.service.generated.SaveAllowedDepartmentsParametersType saveAllowedDepartmentsRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetAccessSchemeListParametersType getAccessSchemeListRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetAccessSchemeByIdParametersType getAccessSchemeByIdRq,
           com.rssl.phizicgate.csaadmin.service.generated.SaveAccessSchemeParametersType saveAccessSchemeRq,
           com.rssl.phizicgate.csaadmin.service.generated.DeleteAccessSchemeParametersType deleteAccessSchemeRq,
           com.rssl.phizicgate.csaadmin.service.generated.AuthenticationParametersRequestType getAuthenticationParametersRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeSynchronizationDataRequestType getEmployeeSynchronizationDataRq,
           com.rssl.phizicgate.csaadmin.service.generated.CloseSessionParametersRequestType closeSessionRq,
           com.rssl.phizicgate.csaadmin.service.generated.ChangeNodeRequestType changeNodeRq,
           com.rssl.phizicgate.csaadmin.service.generated.SaveOperationContextRequestType saveOperationContextRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetOperationContextRequestType getOperationContextRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetCurrentEmployeeContextParametersType getCurrentEmployeeContextRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetIncomeMailListParametersType getIncomeMailListRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetOutcomeMailListParametersType getOutcomeMailListRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetRemovedMailListParametersType getRemovedMailListRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetMailStatisticsParametersType getMailStatisticsRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetMailEmployeeStatisticsParametersType getMailEmployeeStatisticsRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetMailAverageTimeParametersType getMailAverageTimeRq,
           com.rssl.phizicgate.csaadmin.service.generated.GetFirstMailDateParametersType getFirstMailDateRq) {
           this.getEmployeeListRq = getEmployeeListRq;
           this.getEmployeeMailManagerListRq = getEmployeeMailManagerListRq;
           this.getEmployeeManagerInfoRq = getEmployeeManagerInfoRq;
           this.getEmployeeByIdRq = getEmployeeByIdRq;
           this.getCurrentEmployeeRq = getCurrentEmployeeRq;
           this.saveEmployeeRq = saveEmployeeRq;
           this.assignAccessSchemeEmployeeRq = assignAccessSchemeEmployeeRq;
           this.deleteEmployeeRq = deleteEmployeeRq;
           this.lockEmployeeRq = lockEmployeeRq;
           this.unlockEmployeeRq = unlockEmployeeRq;
           this.changeEmployeePasswordRq = changeEmployeePasswordRq;
           this.selfChangePasswordRq = selfChangePasswordRq;
           this.getAllowedDepartmentsRq = getAllowedDepartmentsRq;
           this.saveAllowedDepartmentsRq = saveAllowedDepartmentsRq;
           this.getAccessSchemeListRq = getAccessSchemeListRq;
           this.getAccessSchemeByIdRq = getAccessSchemeByIdRq;
           this.saveAccessSchemeRq = saveAccessSchemeRq;
           this.deleteAccessSchemeRq = deleteAccessSchemeRq;
           this.getAuthenticationParametersRq = getAuthenticationParametersRq;
           this.getEmployeeSynchronizationDataRq = getEmployeeSynchronizationDataRq;
           this.closeSessionRq = closeSessionRq;
           this.changeNodeRq = changeNodeRq;
           this.saveOperationContextRq = saveOperationContextRq;
           this.getOperationContextRq = getOperationContextRq;
           this.getCurrentEmployeeContextRq = getCurrentEmployeeContextRq;
           this.getIncomeMailListRq = getIncomeMailListRq;
           this.getOutcomeMailListRq = getOutcomeMailListRq;
           this.getRemovedMailListRq = getRemovedMailListRq;
           this.getMailStatisticsRq = getMailStatisticsRq;
           this.getMailEmployeeStatisticsRq = getMailEmployeeStatisticsRq;
           this.getMailAverageTimeRq = getMailAverageTimeRq;
           this.getFirstMailDateRq = getFirstMailDateRq;
    }


    /**
     * Gets the getEmployeeListRq value for this RequestData.
     * 
     * @return getEmployeeListRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeListParametersType getGetEmployeeListRq() {
        return getEmployeeListRq;
    }


    /**
     * Sets the getEmployeeListRq value for this RequestData.
     * 
     * @param getEmployeeListRq
     */
    public void setGetEmployeeListRq(com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeListParametersType getEmployeeListRq) {
        this.getEmployeeListRq = getEmployeeListRq;
    }


    /**
     * Gets the getEmployeeMailManagerListRq value for this RequestData.
     * 
     * @return getEmployeeMailManagerListRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeMailManagerListParametersType getGetEmployeeMailManagerListRq() {
        return getEmployeeMailManagerListRq;
    }


    /**
     * Sets the getEmployeeMailManagerListRq value for this RequestData.
     * 
     * @param getEmployeeMailManagerListRq
     */
    public void setGetEmployeeMailManagerListRq(com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeMailManagerListParametersType getEmployeeMailManagerListRq) {
        this.getEmployeeMailManagerListRq = getEmployeeMailManagerListRq;
    }


    /**
     * Gets the getEmployeeManagerInfoRq value for this RequestData.
     * 
     * @return getEmployeeManagerInfoRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeManagerInfoParametersType getGetEmployeeManagerInfoRq() {
        return getEmployeeManagerInfoRq;
    }


    /**
     * Sets the getEmployeeManagerInfoRq value for this RequestData.
     * 
     * @param getEmployeeManagerInfoRq
     */
    public void setGetEmployeeManagerInfoRq(com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeManagerInfoParametersType getEmployeeManagerInfoRq) {
        this.getEmployeeManagerInfoRq = getEmployeeManagerInfoRq;
    }


    /**
     * Gets the getEmployeeByIdRq value for this RequestData.
     * 
     * @return getEmployeeByIdRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeByIdParametersType getGetEmployeeByIdRq() {
        return getEmployeeByIdRq;
    }


    /**
     * Sets the getEmployeeByIdRq value for this RequestData.
     * 
     * @param getEmployeeByIdRq
     */
    public void setGetEmployeeByIdRq(com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeByIdParametersType getEmployeeByIdRq) {
        this.getEmployeeByIdRq = getEmployeeByIdRq;
    }


    /**
     * Gets the getCurrentEmployeeRq value for this RequestData.
     * 
     * @return getCurrentEmployeeRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetCurrentEmployeeParametersType getGetCurrentEmployeeRq() {
        return getCurrentEmployeeRq;
    }


    /**
     * Sets the getCurrentEmployeeRq value for this RequestData.
     * 
     * @param getCurrentEmployeeRq
     */
    public void setGetCurrentEmployeeRq(com.rssl.phizicgate.csaadmin.service.generated.GetCurrentEmployeeParametersType getCurrentEmployeeRq) {
        this.getCurrentEmployeeRq = getCurrentEmployeeRq;
    }


    /**
     * Gets the saveEmployeeRq value for this RequestData.
     * 
     * @return saveEmployeeRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.SaveEmployeeParametersType getSaveEmployeeRq() {
        return saveEmployeeRq;
    }


    /**
     * Sets the saveEmployeeRq value for this RequestData.
     * 
     * @param saveEmployeeRq
     */
    public void setSaveEmployeeRq(com.rssl.phizicgate.csaadmin.service.generated.SaveEmployeeParametersType saveEmployeeRq) {
        this.saveEmployeeRq = saveEmployeeRq;
    }


    /**
     * Gets the assignAccessSchemeEmployeeRq value for this RequestData.
     * 
     * @return assignAccessSchemeEmployeeRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.AssignAccessSchemeEmployeeParametersType getAssignAccessSchemeEmployeeRq() {
        return assignAccessSchemeEmployeeRq;
    }


    /**
     * Sets the assignAccessSchemeEmployeeRq value for this RequestData.
     * 
     * @param assignAccessSchemeEmployeeRq
     */
    public void setAssignAccessSchemeEmployeeRq(com.rssl.phizicgate.csaadmin.service.generated.AssignAccessSchemeEmployeeParametersType assignAccessSchemeEmployeeRq) {
        this.assignAccessSchemeEmployeeRq = assignAccessSchemeEmployeeRq;
    }


    /**
     * Gets the deleteEmployeeRq value for this RequestData.
     * 
     * @return deleteEmployeeRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.DeleteEmployeeParametersType getDeleteEmployeeRq() {
        return deleteEmployeeRq;
    }


    /**
     * Sets the deleteEmployeeRq value for this RequestData.
     * 
     * @param deleteEmployeeRq
     */
    public void setDeleteEmployeeRq(com.rssl.phizicgate.csaadmin.service.generated.DeleteEmployeeParametersType deleteEmployeeRq) {
        this.deleteEmployeeRq = deleteEmployeeRq;
    }


    /**
     * Gets the lockEmployeeRq value for this RequestData.
     * 
     * @return lockEmployeeRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.LockEmployeeParametersType getLockEmployeeRq() {
        return lockEmployeeRq;
    }


    /**
     * Sets the lockEmployeeRq value for this RequestData.
     * 
     * @param lockEmployeeRq
     */
    public void setLockEmployeeRq(com.rssl.phizicgate.csaadmin.service.generated.LockEmployeeParametersType lockEmployeeRq) {
        this.lockEmployeeRq = lockEmployeeRq;
    }


    /**
     * Gets the unlockEmployeeRq value for this RequestData.
     * 
     * @return unlockEmployeeRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.UnlockEmployeeParametersType getUnlockEmployeeRq() {
        return unlockEmployeeRq;
    }


    /**
     * Sets the unlockEmployeeRq value for this RequestData.
     * 
     * @param unlockEmployeeRq
     */
    public void setUnlockEmployeeRq(com.rssl.phizicgate.csaadmin.service.generated.UnlockEmployeeParametersType unlockEmployeeRq) {
        this.unlockEmployeeRq = unlockEmployeeRq;
    }


    /**
     * Gets the changeEmployeePasswordRq value for this RequestData.
     * 
     * @return changeEmployeePasswordRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.ChangeEmployeePasswordParametersType getChangeEmployeePasswordRq() {
        return changeEmployeePasswordRq;
    }


    /**
     * Sets the changeEmployeePasswordRq value for this RequestData.
     * 
     * @param changeEmployeePasswordRq
     */
    public void setChangeEmployeePasswordRq(com.rssl.phizicgate.csaadmin.service.generated.ChangeEmployeePasswordParametersType changeEmployeePasswordRq) {
        this.changeEmployeePasswordRq = changeEmployeePasswordRq;
    }


    /**
     * Gets the selfChangePasswordRq value for this RequestData.
     * 
     * @return selfChangePasswordRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.SelfChangePasswordParametersType getSelfChangePasswordRq() {
        return selfChangePasswordRq;
    }


    /**
     * Sets the selfChangePasswordRq value for this RequestData.
     * 
     * @param selfChangePasswordRq
     */
    public void setSelfChangePasswordRq(com.rssl.phizicgate.csaadmin.service.generated.SelfChangePasswordParametersType selfChangePasswordRq) {
        this.selfChangePasswordRq = selfChangePasswordRq;
    }


    /**
     * Gets the getAllowedDepartmentsRq value for this RequestData.
     * 
     * @return getAllowedDepartmentsRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetAllowedDepartmentsParametersType getGetAllowedDepartmentsRq() {
        return getAllowedDepartmentsRq;
    }


    /**
     * Sets the getAllowedDepartmentsRq value for this RequestData.
     * 
     * @param getAllowedDepartmentsRq
     */
    public void setGetAllowedDepartmentsRq(com.rssl.phizicgate.csaadmin.service.generated.GetAllowedDepartmentsParametersType getAllowedDepartmentsRq) {
        this.getAllowedDepartmentsRq = getAllowedDepartmentsRq;
    }


    /**
     * Gets the saveAllowedDepartmentsRq value for this RequestData.
     * 
     * @return saveAllowedDepartmentsRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.SaveAllowedDepartmentsParametersType getSaveAllowedDepartmentsRq() {
        return saveAllowedDepartmentsRq;
    }


    /**
     * Sets the saveAllowedDepartmentsRq value for this RequestData.
     * 
     * @param saveAllowedDepartmentsRq
     */
    public void setSaveAllowedDepartmentsRq(com.rssl.phizicgate.csaadmin.service.generated.SaveAllowedDepartmentsParametersType saveAllowedDepartmentsRq) {
        this.saveAllowedDepartmentsRq = saveAllowedDepartmentsRq;
    }


    /**
     * Gets the getAccessSchemeListRq value for this RequestData.
     * 
     * @return getAccessSchemeListRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetAccessSchemeListParametersType getGetAccessSchemeListRq() {
        return getAccessSchemeListRq;
    }


    /**
     * Sets the getAccessSchemeListRq value for this RequestData.
     * 
     * @param getAccessSchemeListRq
     */
    public void setGetAccessSchemeListRq(com.rssl.phizicgate.csaadmin.service.generated.GetAccessSchemeListParametersType getAccessSchemeListRq) {
        this.getAccessSchemeListRq = getAccessSchemeListRq;
    }


    /**
     * Gets the getAccessSchemeByIdRq value for this RequestData.
     * 
     * @return getAccessSchemeByIdRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetAccessSchemeByIdParametersType getGetAccessSchemeByIdRq() {
        return getAccessSchemeByIdRq;
    }


    /**
     * Sets the getAccessSchemeByIdRq value for this RequestData.
     * 
     * @param getAccessSchemeByIdRq
     */
    public void setGetAccessSchemeByIdRq(com.rssl.phizicgate.csaadmin.service.generated.GetAccessSchemeByIdParametersType getAccessSchemeByIdRq) {
        this.getAccessSchemeByIdRq = getAccessSchemeByIdRq;
    }


    /**
     * Gets the saveAccessSchemeRq value for this RequestData.
     * 
     * @return saveAccessSchemeRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.SaveAccessSchemeParametersType getSaveAccessSchemeRq() {
        return saveAccessSchemeRq;
    }


    /**
     * Sets the saveAccessSchemeRq value for this RequestData.
     * 
     * @param saveAccessSchemeRq
     */
    public void setSaveAccessSchemeRq(com.rssl.phizicgate.csaadmin.service.generated.SaveAccessSchemeParametersType saveAccessSchemeRq) {
        this.saveAccessSchemeRq = saveAccessSchemeRq;
    }


    /**
     * Gets the deleteAccessSchemeRq value for this RequestData.
     * 
     * @return deleteAccessSchemeRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.DeleteAccessSchemeParametersType getDeleteAccessSchemeRq() {
        return deleteAccessSchemeRq;
    }


    /**
     * Sets the deleteAccessSchemeRq value for this RequestData.
     * 
     * @param deleteAccessSchemeRq
     */
    public void setDeleteAccessSchemeRq(com.rssl.phizicgate.csaadmin.service.generated.DeleteAccessSchemeParametersType deleteAccessSchemeRq) {
        this.deleteAccessSchemeRq = deleteAccessSchemeRq;
    }


    /**
     * Gets the getAuthenticationParametersRq value for this RequestData.
     * 
     * @return getAuthenticationParametersRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.AuthenticationParametersRequestType getGetAuthenticationParametersRq() {
        return getAuthenticationParametersRq;
    }


    /**
     * Sets the getAuthenticationParametersRq value for this RequestData.
     * 
     * @param getAuthenticationParametersRq
     */
    public void setGetAuthenticationParametersRq(com.rssl.phizicgate.csaadmin.service.generated.AuthenticationParametersRequestType getAuthenticationParametersRq) {
        this.getAuthenticationParametersRq = getAuthenticationParametersRq;
    }


    /**
     * Gets the getEmployeeSynchronizationDataRq value for this RequestData.
     * 
     * @return getEmployeeSynchronizationDataRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeSynchronizationDataRequestType getGetEmployeeSynchronizationDataRq() {
        return getEmployeeSynchronizationDataRq;
    }


    /**
     * Sets the getEmployeeSynchronizationDataRq value for this RequestData.
     * 
     * @param getEmployeeSynchronizationDataRq
     */
    public void setGetEmployeeSynchronizationDataRq(com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeSynchronizationDataRequestType getEmployeeSynchronizationDataRq) {
        this.getEmployeeSynchronizationDataRq = getEmployeeSynchronizationDataRq;
    }


    /**
     * Gets the closeSessionRq value for this RequestData.
     * 
     * @return closeSessionRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.CloseSessionParametersRequestType getCloseSessionRq() {
        return closeSessionRq;
    }


    /**
     * Sets the closeSessionRq value for this RequestData.
     * 
     * @param closeSessionRq
     */
    public void setCloseSessionRq(com.rssl.phizicgate.csaadmin.service.generated.CloseSessionParametersRequestType closeSessionRq) {
        this.closeSessionRq = closeSessionRq;
    }


    /**
     * Gets the changeNodeRq value for this RequestData.
     * 
     * @return changeNodeRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.ChangeNodeRequestType getChangeNodeRq() {
        return changeNodeRq;
    }


    /**
     * Sets the changeNodeRq value for this RequestData.
     * 
     * @param changeNodeRq
     */
    public void setChangeNodeRq(com.rssl.phizicgate.csaadmin.service.generated.ChangeNodeRequestType changeNodeRq) {
        this.changeNodeRq = changeNodeRq;
    }


    /**
     * Gets the saveOperationContextRq value for this RequestData.
     * 
     * @return saveOperationContextRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.SaveOperationContextRequestType getSaveOperationContextRq() {
        return saveOperationContextRq;
    }


    /**
     * Sets the saveOperationContextRq value for this RequestData.
     * 
     * @param saveOperationContextRq
     */
    public void setSaveOperationContextRq(com.rssl.phizicgate.csaadmin.service.generated.SaveOperationContextRequestType saveOperationContextRq) {
        this.saveOperationContextRq = saveOperationContextRq;
    }


    /**
     * Gets the getOperationContextRq value for this RequestData.
     * 
     * @return getOperationContextRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetOperationContextRequestType getGetOperationContextRq() {
        return getOperationContextRq;
    }


    /**
     * Sets the getOperationContextRq value for this RequestData.
     * 
     * @param getOperationContextRq
     */
    public void setGetOperationContextRq(com.rssl.phizicgate.csaadmin.service.generated.GetOperationContextRequestType getOperationContextRq) {
        this.getOperationContextRq = getOperationContextRq;
    }


    /**
     * Gets the getCurrentEmployeeContextRq value for this RequestData.
     * 
     * @return getCurrentEmployeeContextRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetCurrentEmployeeContextParametersType getGetCurrentEmployeeContextRq() {
        return getCurrentEmployeeContextRq;
    }


    /**
     * Sets the getCurrentEmployeeContextRq value for this RequestData.
     * 
     * @param getCurrentEmployeeContextRq
     */
    public void setGetCurrentEmployeeContextRq(com.rssl.phizicgate.csaadmin.service.generated.GetCurrentEmployeeContextParametersType getCurrentEmployeeContextRq) {
        this.getCurrentEmployeeContextRq = getCurrentEmployeeContextRq;
    }


    /**
     * Gets the getIncomeMailListRq value for this RequestData.
     * 
     * @return getIncomeMailListRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetIncomeMailListParametersType getGetIncomeMailListRq() {
        return getIncomeMailListRq;
    }


    /**
     * Sets the getIncomeMailListRq value for this RequestData.
     * 
     * @param getIncomeMailListRq
     */
    public void setGetIncomeMailListRq(com.rssl.phizicgate.csaadmin.service.generated.GetIncomeMailListParametersType getIncomeMailListRq) {
        this.getIncomeMailListRq = getIncomeMailListRq;
    }


    /**
     * Gets the getOutcomeMailListRq value for this RequestData.
     * 
     * @return getOutcomeMailListRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetOutcomeMailListParametersType getGetOutcomeMailListRq() {
        return getOutcomeMailListRq;
    }


    /**
     * Sets the getOutcomeMailListRq value for this RequestData.
     * 
     * @param getOutcomeMailListRq
     */
    public void setGetOutcomeMailListRq(com.rssl.phizicgate.csaadmin.service.generated.GetOutcomeMailListParametersType getOutcomeMailListRq) {
        this.getOutcomeMailListRq = getOutcomeMailListRq;
    }


    /**
     * Gets the getRemovedMailListRq value for this RequestData.
     * 
     * @return getRemovedMailListRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetRemovedMailListParametersType getGetRemovedMailListRq() {
        return getRemovedMailListRq;
    }


    /**
     * Sets the getRemovedMailListRq value for this RequestData.
     * 
     * @param getRemovedMailListRq
     */
    public void setGetRemovedMailListRq(com.rssl.phizicgate.csaadmin.service.generated.GetRemovedMailListParametersType getRemovedMailListRq) {
        this.getRemovedMailListRq = getRemovedMailListRq;
    }


    /**
     * Gets the getMailStatisticsRq value for this RequestData.
     * 
     * @return getMailStatisticsRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetMailStatisticsParametersType getGetMailStatisticsRq() {
        return getMailStatisticsRq;
    }


    /**
     * Sets the getMailStatisticsRq value for this RequestData.
     * 
     * @param getMailStatisticsRq
     */
    public void setGetMailStatisticsRq(com.rssl.phizicgate.csaadmin.service.generated.GetMailStatisticsParametersType getMailStatisticsRq) {
        this.getMailStatisticsRq = getMailStatisticsRq;
    }


    /**
     * Gets the getMailEmployeeStatisticsRq value for this RequestData.
     * 
     * @return getMailEmployeeStatisticsRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetMailEmployeeStatisticsParametersType getGetMailEmployeeStatisticsRq() {
        return getMailEmployeeStatisticsRq;
    }


    /**
     * Sets the getMailEmployeeStatisticsRq value for this RequestData.
     * 
     * @param getMailEmployeeStatisticsRq
     */
    public void setGetMailEmployeeStatisticsRq(com.rssl.phizicgate.csaadmin.service.generated.GetMailEmployeeStatisticsParametersType getMailEmployeeStatisticsRq) {
        this.getMailEmployeeStatisticsRq = getMailEmployeeStatisticsRq;
    }


    /**
     * Gets the getMailAverageTimeRq value for this RequestData.
     * 
     * @return getMailAverageTimeRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetMailAverageTimeParametersType getGetMailAverageTimeRq() {
        return getMailAverageTimeRq;
    }


    /**
     * Sets the getMailAverageTimeRq value for this RequestData.
     * 
     * @param getMailAverageTimeRq
     */
    public void setGetMailAverageTimeRq(com.rssl.phizicgate.csaadmin.service.generated.GetMailAverageTimeParametersType getMailAverageTimeRq) {
        this.getMailAverageTimeRq = getMailAverageTimeRq;
    }


    /**
     * Gets the getFirstMailDateRq value for this RequestData.
     * 
     * @return getFirstMailDateRq
     */
    public com.rssl.phizicgate.csaadmin.service.generated.GetFirstMailDateParametersType getGetFirstMailDateRq() {
        return getFirstMailDateRq;
    }


    /**
     * Sets the getFirstMailDateRq value for this RequestData.
     * 
     * @param getFirstMailDateRq
     */
    public void setGetFirstMailDateRq(com.rssl.phizicgate.csaadmin.service.generated.GetFirstMailDateParametersType getFirstMailDateRq) {
        this.getFirstMailDateRq = getFirstMailDateRq;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RequestData)) return false;
        RequestData other = (RequestData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getEmployeeListRq==null && other.getGetEmployeeListRq()==null) || 
             (this.getEmployeeListRq!=null &&
              this.getEmployeeListRq.equals(other.getGetEmployeeListRq()))) &&
            ((this.getEmployeeMailManagerListRq==null && other.getGetEmployeeMailManagerListRq()==null) || 
             (this.getEmployeeMailManagerListRq!=null &&
              this.getEmployeeMailManagerListRq.equals(other.getGetEmployeeMailManagerListRq()))) &&
            ((this.getEmployeeManagerInfoRq==null && other.getGetEmployeeManagerInfoRq()==null) || 
             (this.getEmployeeManagerInfoRq!=null &&
              this.getEmployeeManagerInfoRq.equals(other.getGetEmployeeManagerInfoRq()))) &&
            ((this.getEmployeeByIdRq==null && other.getGetEmployeeByIdRq()==null) || 
             (this.getEmployeeByIdRq!=null &&
              this.getEmployeeByIdRq.equals(other.getGetEmployeeByIdRq()))) &&
            ((this.getCurrentEmployeeRq==null && other.getGetCurrentEmployeeRq()==null) || 
             (this.getCurrentEmployeeRq!=null &&
              this.getCurrentEmployeeRq.equals(other.getGetCurrentEmployeeRq()))) &&
            ((this.saveEmployeeRq==null && other.getSaveEmployeeRq()==null) || 
             (this.saveEmployeeRq!=null &&
              this.saveEmployeeRq.equals(other.getSaveEmployeeRq()))) &&
            ((this.assignAccessSchemeEmployeeRq==null && other.getAssignAccessSchemeEmployeeRq()==null) || 
             (this.assignAccessSchemeEmployeeRq!=null &&
              this.assignAccessSchemeEmployeeRq.equals(other.getAssignAccessSchemeEmployeeRq()))) &&
            ((this.deleteEmployeeRq==null && other.getDeleteEmployeeRq()==null) || 
             (this.deleteEmployeeRq!=null &&
              this.deleteEmployeeRq.equals(other.getDeleteEmployeeRq()))) &&
            ((this.lockEmployeeRq==null && other.getLockEmployeeRq()==null) || 
             (this.lockEmployeeRq!=null &&
              this.lockEmployeeRq.equals(other.getLockEmployeeRq()))) &&
            ((this.unlockEmployeeRq==null && other.getUnlockEmployeeRq()==null) || 
             (this.unlockEmployeeRq!=null &&
              this.unlockEmployeeRq.equals(other.getUnlockEmployeeRq()))) &&
            ((this.changeEmployeePasswordRq==null && other.getChangeEmployeePasswordRq()==null) || 
             (this.changeEmployeePasswordRq!=null &&
              this.changeEmployeePasswordRq.equals(other.getChangeEmployeePasswordRq()))) &&
            ((this.selfChangePasswordRq==null && other.getSelfChangePasswordRq()==null) || 
             (this.selfChangePasswordRq!=null &&
              this.selfChangePasswordRq.equals(other.getSelfChangePasswordRq()))) &&
            ((this.getAllowedDepartmentsRq==null && other.getGetAllowedDepartmentsRq()==null) || 
             (this.getAllowedDepartmentsRq!=null &&
              this.getAllowedDepartmentsRq.equals(other.getGetAllowedDepartmentsRq()))) &&
            ((this.saveAllowedDepartmentsRq==null && other.getSaveAllowedDepartmentsRq()==null) || 
             (this.saveAllowedDepartmentsRq!=null &&
              this.saveAllowedDepartmentsRq.equals(other.getSaveAllowedDepartmentsRq()))) &&
            ((this.getAccessSchemeListRq==null && other.getGetAccessSchemeListRq()==null) || 
             (this.getAccessSchemeListRq!=null &&
              this.getAccessSchemeListRq.equals(other.getGetAccessSchemeListRq()))) &&
            ((this.getAccessSchemeByIdRq==null && other.getGetAccessSchemeByIdRq()==null) || 
             (this.getAccessSchemeByIdRq!=null &&
              this.getAccessSchemeByIdRq.equals(other.getGetAccessSchemeByIdRq()))) &&
            ((this.saveAccessSchemeRq==null && other.getSaveAccessSchemeRq()==null) || 
             (this.saveAccessSchemeRq!=null &&
              this.saveAccessSchemeRq.equals(other.getSaveAccessSchemeRq()))) &&
            ((this.deleteAccessSchemeRq==null && other.getDeleteAccessSchemeRq()==null) || 
             (this.deleteAccessSchemeRq!=null &&
              this.deleteAccessSchemeRq.equals(other.getDeleteAccessSchemeRq()))) &&
            ((this.getAuthenticationParametersRq==null && other.getGetAuthenticationParametersRq()==null) || 
             (this.getAuthenticationParametersRq!=null &&
              this.getAuthenticationParametersRq.equals(other.getGetAuthenticationParametersRq()))) &&
            ((this.getEmployeeSynchronizationDataRq==null && other.getGetEmployeeSynchronizationDataRq()==null) || 
             (this.getEmployeeSynchronizationDataRq!=null &&
              this.getEmployeeSynchronizationDataRq.equals(other.getGetEmployeeSynchronizationDataRq()))) &&
            ((this.closeSessionRq==null && other.getCloseSessionRq()==null) || 
             (this.closeSessionRq!=null &&
              this.closeSessionRq.equals(other.getCloseSessionRq()))) &&
            ((this.changeNodeRq==null && other.getChangeNodeRq()==null) || 
             (this.changeNodeRq!=null &&
              this.changeNodeRq.equals(other.getChangeNodeRq()))) &&
            ((this.saveOperationContextRq==null && other.getSaveOperationContextRq()==null) || 
             (this.saveOperationContextRq!=null &&
              this.saveOperationContextRq.equals(other.getSaveOperationContextRq()))) &&
            ((this.getOperationContextRq==null && other.getGetOperationContextRq()==null) || 
             (this.getOperationContextRq!=null &&
              this.getOperationContextRq.equals(other.getGetOperationContextRq()))) &&
            ((this.getCurrentEmployeeContextRq==null && other.getGetCurrentEmployeeContextRq()==null) || 
             (this.getCurrentEmployeeContextRq!=null &&
              this.getCurrentEmployeeContextRq.equals(other.getGetCurrentEmployeeContextRq()))) &&
            ((this.getIncomeMailListRq==null && other.getGetIncomeMailListRq()==null) || 
             (this.getIncomeMailListRq!=null &&
              this.getIncomeMailListRq.equals(other.getGetIncomeMailListRq()))) &&
            ((this.getOutcomeMailListRq==null && other.getGetOutcomeMailListRq()==null) || 
             (this.getOutcomeMailListRq!=null &&
              this.getOutcomeMailListRq.equals(other.getGetOutcomeMailListRq()))) &&
            ((this.getRemovedMailListRq==null && other.getGetRemovedMailListRq()==null) || 
             (this.getRemovedMailListRq!=null &&
              this.getRemovedMailListRq.equals(other.getGetRemovedMailListRq()))) &&
            ((this.getMailStatisticsRq==null && other.getGetMailStatisticsRq()==null) || 
             (this.getMailStatisticsRq!=null &&
              this.getMailStatisticsRq.equals(other.getGetMailStatisticsRq()))) &&
            ((this.getMailEmployeeStatisticsRq==null && other.getGetMailEmployeeStatisticsRq()==null) || 
             (this.getMailEmployeeStatisticsRq!=null &&
              this.getMailEmployeeStatisticsRq.equals(other.getGetMailEmployeeStatisticsRq()))) &&
            ((this.getMailAverageTimeRq==null && other.getGetMailAverageTimeRq()==null) || 
             (this.getMailAverageTimeRq!=null &&
              this.getMailAverageTimeRq.equals(other.getGetMailAverageTimeRq()))) &&
            ((this.getFirstMailDateRq==null && other.getGetFirstMailDateRq()==null) || 
             (this.getFirstMailDateRq!=null &&
              this.getFirstMailDateRq.equals(other.getGetFirstMailDateRq())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getGetEmployeeListRq() != null) {
            _hashCode += getGetEmployeeListRq().hashCode();
        }
        if (getGetEmployeeMailManagerListRq() != null) {
            _hashCode += getGetEmployeeMailManagerListRq().hashCode();
        }
        if (getGetEmployeeManagerInfoRq() != null) {
            _hashCode += getGetEmployeeManagerInfoRq().hashCode();
        }
        if (getGetEmployeeByIdRq() != null) {
            _hashCode += getGetEmployeeByIdRq().hashCode();
        }
        if (getGetCurrentEmployeeRq() != null) {
            _hashCode += getGetCurrentEmployeeRq().hashCode();
        }
        if (getSaveEmployeeRq() != null) {
            _hashCode += getSaveEmployeeRq().hashCode();
        }
        if (getAssignAccessSchemeEmployeeRq() != null) {
            _hashCode += getAssignAccessSchemeEmployeeRq().hashCode();
        }
        if (getDeleteEmployeeRq() != null) {
            _hashCode += getDeleteEmployeeRq().hashCode();
        }
        if (getLockEmployeeRq() != null) {
            _hashCode += getLockEmployeeRq().hashCode();
        }
        if (getUnlockEmployeeRq() != null) {
            _hashCode += getUnlockEmployeeRq().hashCode();
        }
        if (getChangeEmployeePasswordRq() != null) {
            _hashCode += getChangeEmployeePasswordRq().hashCode();
        }
        if (getSelfChangePasswordRq() != null) {
            _hashCode += getSelfChangePasswordRq().hashCode();
        }
        if (getGetAllowedDepartmentsRq() != null) {
            _hashCode += getGetAllowedDepartmentsRq().hashCode();
        }
        if (getSaveAllowedDepartmentsRq() != null) {
            _hashCode += getSaveAllowedDepartmentsRq().hashCode();
        }
        if (getGetAccessSchemeListRq() != null) {
            _hashCode += getGetAccessSchemeListRq().hashCode();
        }
        if (getGetAccessSchemeByIdRq() != null) {
            _hashCode += getGetAccessSchemeByIdRq().hashCode();
        }
        if (getSaveAccessSchemeRq() != null) {
            _hashCode += getSaveAccessSchemeRq().hashCode();
        }
        if (getDeleteAccessSchemeRq() != null) {
            _hashCode += getDeleteAccessSchemeRq().hashCode();
        }
        if (getGetAuthenticationParametersRq() != null) {
            _hashCode += getGetAuthenticationParametersRq().hashCode();
        }
        if (getGetEmployeeSynchronizationDataRq() != null) {
            _hashCode += getGetEmployeeSynchronizationDataRq().hashCode();
        }
        if (getCloseSessionRq() != null) {
            _hashCode += getCloseSessionRq().hashCode();
        }
        if (getChangeNodeRq() != null) {
            _hashCode += getChangeNodeRq().hashCode();
        }
        if (getSaveOperationContextRq() != null) {
            _hashCode += getSaveOperationContextRq().hashCode();
        }
        if (getGetOperationContextRq() != null) {
            _hashCode += getGetOperationContextRq().hashCode();
        }
        if (getGetCurrentEmployeeContextRq() != null) {
            _hashCode += getGetCurrentEmployeeContextRq().hashCode();
        }
        if (getGetIncomeMailListRq() != null) {
            _hashCode += getGetIncomeMailListRq().hashCode();
        }
        if (getGetOutcomeMailListRq() != null) {
            _hashCode += getGetOutcomeMailListRq().hashCode();
        }
        if (getGetRemovedMailListRq() != null) {
            _hashCode += getGetRemovedMailListRq().hashCode();
        }
        if (getGetMailStatisticsRq() != null) {
            _hashCode += getGetMailStatisticsRq().hashCode();
        }
        if (getGetMailEmployeeStatisticsRq() != null) {
            _hashCode += getGetMailEmployeeStatisticsRq().hashCode();
        }
        if (getGetMailAverageTimeRq() != null) {
            _hashCode += getGetMailAverageTimeRq().hashCode();
        }
        if (getGetFirstMailDateRq() != null) {
            _hashCode += getGetFirstMailDateRq().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RequestData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "RequestData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getEmployeeListRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeListRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeListParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getEmployeeMailManagerListRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeMailManagerListRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeMailManagerListParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getEmployeeManagerInfoRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeManagerInfoRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeManagerInfoParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getEmployeeByIdRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeByIdRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeByIdParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getCurrentEmployeeRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetCurrentEmployeeRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetCurrentEmployeeParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saveEmployeeRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveEmployeeRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveEmployeeParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("assignAccessSchemeEmployeeRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AssignAccessSchemeEmployeeRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AssignAccessSchemeEmployeeParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deleteEmployeeRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DeleteEmployeeRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DeleteEmployeeParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lockEmployeeRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LockEmployeeRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LockEmployeeParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unlockEmployeeRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "UnlockEmployeeRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "UnlockEmployeeParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeEmployeePasswordRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ChangeEmployeePasswordRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ChangeEmployeePasswordParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("selfChangePasswordRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SelfChangePasswordRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SelfChangePasswordParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getAllowedDepartmentsRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetAllowedDepartmentsRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetAllowedDepartmentsParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saveAllowedDepartmentsRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveAllowedDepartmentsRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveAllowedDepartmentsParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getAccessSchemeListRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetAccessSchemeListRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetAccessSchemeListParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getAccessSchemeByIdRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetAccessSchemeByIdRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetAccessSchemeByIdParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saveAccessSchemeRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveAccessSchemeRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveAccessSchemeParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deleteAccessSchemeRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DeleteAccessSchemeRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DeleteAccessSchemeParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getAuthenticationParametersRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetAuthenticationParametersRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AuthenticationParametersRequestType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getEmployeeSynchronizationDataRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeSynchronizationDataRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeSynchronizationDataRequestType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("closeSessionRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "CloseSessionRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "CloseSessionParametersRequestType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeNodeRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ChangeNodeRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ChangeNodeRequestType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saveOperationContextRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveOperationContextRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveOperationContextRequestType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getOperationContextRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetOperationContextRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetOperationContextRequestType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getCurrentEmployeeContextRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetCurrentEmployeeContextRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetCurrentEmployeeContextParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getIncomeMailListRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetIncomeMailListRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetIncomeMailListParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getOutcomeMailListRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetOutcomeMailListRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetOutcomeMailListParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getRemovedMailListRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetRemovedMailListRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetRemovedMailListParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getMailStatisticsRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMailStatisticsRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMailStatisticsParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getMailEmployeeStatisticsRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMailEmployeeStatisticsRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMailEmployeeStatisticsParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getMailAverageTimeRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMailAverageTimeRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMailAverageTimeParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getFirstMailDateRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetFirstMailDateRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetFirstMailDateParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}

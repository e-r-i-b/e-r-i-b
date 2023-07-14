
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Тип - дополнительная информация о заемщике
 * 
 * <p>Java class for ApplicantAddData_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantAddData_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LoanIssue" type="{}LoanIssue_Type"/>
 *         &lt;element name="InsuranceFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="InsuranceNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="14"/>
 *               &lt;pattern value="\d\d\d-\d\d\d-\d\d\d \d\d"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CBReqApprovalFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CBSendApprovalFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PFRSendApprovalFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CBCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="15"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CCardGetApprovalFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SpecialAttentionFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SBSalaryCardFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SalaryCardList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SalaryCard" type="{}SalaryCard_Type" maxOccurs="4"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="SBSalaryDepFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SalaryDepList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SalaryDep" type="{}SalaryDep_Type" maxOccurs="4"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="SBShareHolderFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ShareHolder" type="{}ShareHolder_Type" minOccurs="0"/>
 *         &lt;element name="PersonVerify" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="VerifyQuestionInfo" type="{}VerifyQuestionInfo_Type" maxOccurs="20" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="SigningDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantAddData_Type", propOrder = {
    "loanIssue",
    "insuranceFlag",
    "insuranceNumber",
    "cbReqApprovalFlag",
    "cbSendApprovalFlag",
    "pfrSendApprovalFlag",
    "cbCode",
    "cCardGetApprovalFlag",
    "specialAttentionFlag",
    "sbSalaryCardFlag",
    "salaryCardList",
    "sbSalaryDepFlag",
    "salaryDepList",
    "sbShareHolderFlag",
    "shareHolder",
    "personVerify",
    "signingDate"
})
public class ApplicantAddDataType {

    @XmlElement(name = "LoanIssue", required = true)
    protected LoanIssueType loanIssue;
    @XmlElement(name = "InsuranceFlag")
    protected boolean insuranceFlag;
    @XmlElement(name = "InsuranceNumber")
    protected String insuranceNumber;
    @XmlElement(name = "CBReqApprovalFlag")
    protected boolean cbReqApprovalFlag;
    @XmlElement(name = "CBSendApprovalFlag")
    protected boolean cbSendApprovalFlag;
    @XmlElement(name = "PFRSendApprovalFlag")
    protected boolean pfrSendApprovalFlag;
    @XmlElement(name = "CBCode")
    protected String cbCode;
    @XmlElement(name = "CCardGetApprovalFlag")
    protected Boolean cCardGetApprovalFlag;
    @XmlElement(name = "SpecialAttentionFlag")
    protected boolean specialAttentionFlag;
    @XmlElement(name = "SBSalaryCardFlag")
    protected boolean sbSalaryCardFlag;
    @XmlElement(name = "SalaryCardList")
    protected ApplicantAddDataType.SalaryCardList salaryCardList;
    @XmlElement(name = "SBSalaryDepFlag")
    protected boolean sbSalaryDepFlag;
    @XmlElement(name = "SalaryDepList")
    protected ApplicantAddDataType.SalaryDepList salaryDepList;
    @XmlElement(name = "SBShareHolderFlag")
    protected Boolean sbShareHolderFlag;
    @XmlElement(name = "ShareHolder")
    protected ShareHolderType shareHolder;
    @XmlElement(name = "PersonVerify")
    protected ApplicantAddDataType.PersonVerify personVerify;
    @XmlElement(name = "SigningDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Calendar signingDate;

    /**
     * Gets the value of the loanIssue property.
     * 
     * @return
     *     possible object is
     *     {@link LoanIssueType }
     *     
     */
    public LoanIssueType getLoanIssue() {
        return loanIssue;
    }

    /**
     * Sets the value of the loanIssue property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoanIssueType }
     *     
     */
    public void setLoanIssue(LoanIssueType value) {
        this.loanIssue = value;
    }

    /**
     * Gets the value of the insuranceFlag property.
     * 
     */
    public boolean isInsuranceFlag() {
        return insuranceFlag;
    }

    /**
     * Sets the value of the insuranceFlag property.
     * 
     */
    public void setInsuranceFlag(boolean value) {
        this.insuranceFlag = value;
    }

    /**
     * Gets the value of the insuranceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    /**
     * Sets the value of the insuranceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuranceNumber(String value) {
        this.insuranceNumber = value;
    }

    /**
     * Gets the value of the cbReqApprovalFlag property.
     * 
     */
    public boolean isCBReqApprovalFlag() {
        return cbReqApprovalFlag;
    }

    /**
     * Sets the value of the cbReqApprovalFlag property.
     * 
     */
    public void setCBReqApprovalFlag(boolean value) {
        this.cbReqApprovalFlag = value;
    }

    /**
     * Gets the value of the cbSendApprovalFlag property.
     * 
     */
    public boolean isCBSendApprovalFlag() {
        return cbSendApprovalFlag;
    }

    /**
     * Sets the value of the cbSendApprovalFlag property.
     * 
     */
    public void setCBSendApprovalFlag(boolean value) {
        this.cbSendApprovalFlag = value;
    }

    /**
     * Gets the value of the pfrSendApprovalFlag property.
     * 
     */
    public boolean isPFRSendApprovalFlag() {
        return pfrSendApprovalFlag;
    }

    /**
     * Sets the value of the pfrSendApprovalFlag property.
     * 
     */
    public void setPFRSendApprovalFlag(boolean value) {
        this.pfrSendApprovalFlag = value;
    }

    /**
     * Gets the value of the cbCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCBCode() {
        return cbCode;
    }

    /**
     * Sets the value of the cbCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCBCode(String value) {
        this.cbCode = value;
    }

    /**
     * Gets the value of the cCardGetApprovalFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getCCardGetApprovalFlag() {
        return cCardGetApprovalFlag;
    }

    /**
     * Sets the value of the cCardGetApprovalFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCCardGetApprovalFlag(Boolean value) {
        this.cCardGetApprovalFlag = value;
    }

    /**
     * Gets the value of the specialAttentionFlag property.
     * 
     */
    public boolean isSpecialAttentionFlag() {
        return specialAttentionFlag;
    }

    /**
     * Sets the value of the specialAttentionFlag property.
     * 
     */
    public void setSpecialAttentionFlag(boolean value) {
        this.specialAttentionFlag = value;
    }

    /**
     * Gets the value of the sbSalaryCardFlag property.
     * 
     */
    public boolean isSBSalaryCardFlag() {
        return sbSalaryCardFlag;
    }

    /**
     * Sets the value of the sbSalaryCardFlag property.
     * 
     */
    public void setSBSalaryCardFlag(boolean value) {
        this.sbSalaryCardFlag = value;
    }

    /**
     * Gets the value of the salaryCardList property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantAddDataType.SalaryCardList }
     *     
     */
    public ApplicantAddDataType.SalaryCardList getSalaryCardList() {
        return salaryCardList;
    }

    /**
     * Sets the value of the salaryCardList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantAddDataType.SalaryCardList }
     *     
     */
    public void setSalaryCardList(ApplicantAddDataType.SalaryCardList value) {
        this.salaryCardList = value;
    }

    /**
     * Gets the value of the sbSalaryDepFlag property.
     * 
     */
    public boolean isSBSalaryDepFlag() {
        return sbSalaryDepFlag;
    }

    /**
     * Sets the value of the sbSalaryDepFlag property.
     * 
     */
    public void setSBSalaryDepFlag(boolean value) {
        this.sbSalaryDepFlag = value;
    }

    /**
     * Gets the value of the salaryDepList property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantAddDataType.SalaryDepList }
     *     
     */
    public ApplicantAddDataType.SalaryDepList getSalaryDepList() {
        return salaryDepList;
    }

    /**
     * Sets the value of the salaryDepList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantAddDataType.SalaryDepList }
     *     
     */
    public void setSalaryDepList(ApplicantAddDataType.SalaryDepList value) {
        this.salaryDepList = value;
    }

    /**
     * Gets the value of the sbShareHolderFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getSBShareHolderFlag() {
        return sbShareHolderFlag;
    }

    /**
     * Sets the value of the sbShareHolderFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSBShareHolderFlag(Boolean value) {
        this.sbShareHolderFlag = value;
    }

    /**
     * Gets the value of the shareHolder property.
     * 
     * @return
     *     possible object is
     *     {@link ShareHolderType }
     *     
     */
    public ShareHolderType getShareHolder() {
        return shareHolder;
    }

    /**
     * Sets the value of the shareHolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShareHolderType }
     *     
     */
    public void setShareHolder(ShareHolderType value) {
        this.shareHolder = value;
    }

    /**
     * Gets the value of the personVerify property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantAddDataType.PersonVerify }
     *     
     */
    public ApplicantAddDataType.PersonVerify getPersonVerify() {
        return personVerify;
    }

    /**
     * Sets the value of the personVerify property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantAddDataType.PersonVerify }
     *     
     */
    public void setPersonVerify(ApplicantAddDataType.PersonVerify value) {
        this.personVerify = value;
    }

    /**
     * Gets the value of the signingDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getSigningDate() {
        return signingDate;
    }

    /**
     * Sets the value of the signingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigningDate(Calendar value) {
        this.signingDate = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="VerifyQuestionInfo" type="{}VerifyQuestionInfo_Type" maxOccurs="20" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "verifyQuestionInfos"
    })
    public static class PersonVerify {

        @XmlElement(name = "VerifyQuestionInfo")
        protected List<VerifyQuestionInfoType> verifyQuestionInfos;

        /**
         * Gets the value of the verifyQuestionInfos property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the verifyQuestionInfos property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVerifyQuestionInfos().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link VerifyQuestionInfoType }
         * 
         * 
         */
        public List<VerifyQuestionInfoType> getVerifyQuestionInfos() {
            if (verifyQuestionInfos == null) {
                verifyQuestionInfos = new ArrayList<VerifyQuestionInfoType>();
            }
            return this.verifyQuestionInfos;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="SalaryCard" type="{}SalaryCard_Type" maxOccurs="4"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "salaryCards"
    })
    public static class SalaryCardList {

        @XmlElement(name = "SalaryCard", required = true)
        protected List<SalaryCardType> salaryCards;

        /**
         * Gets the value of the salaryCards property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the salaryCards property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSalaryCards().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SalaryCardType }
         * 
         * 
         */
        public List<SalaryCardType> getSalaryCards() {
            if (salaryCards == null) {
                salaryCards = new ArrayList<SalaryCardType>();
            }
            return this.salaryCards;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="SalaryDep" type="{}SalaryDep_Type" maxOccurs="4"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "salaryDeps"
    })
    public static class SalaryDepList {

        @XmlElement(name = "SalaryDep", required = true)
        protected List<SalaryDepType> salaryDeps;

        /**
         * Gets the value of the salaryDeps property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the salaryDeps property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSalaryDeps().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SalaryDepType }
         * 
         * 
         */
        public List<SalaryDepType> getSalaryDeps() {
            if (salaryDeps == null) {
                salaryDeps = new ArrayList<SalaryDepType>();
            }
            return this.salaryDeps;
        }

    }

}

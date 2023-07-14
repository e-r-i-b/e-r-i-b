
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicantData_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantData_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Type" type="{}NPPartyType_Type"/>
 *         &lt;element name="PersonInfo" type="{}ApplicantPersonInfo_Type"/>
 *         &lt;element name="MaritalCondition" type="{}MaritalCondition_Type" minOccurs="0"/>
 *         &lt;element name="RelativeList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Relative" type="{}RelativeInfo_Type" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="EmploymentHistory" type="{}EmploymentHistory_Type" minOccurs="0"/>
 *         &lt;element name="Income" type="{}Income_Type" minOccurs="0"/>
 *         &lt;element name="RealEstateFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="RealEstateList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RealEstate" type="{}RealEstate_Type" maxOccurs="7"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="VehicleFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="VehicleList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Vehicle" type="{}Vehicle_Type" maxOccurs="7"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="LoanFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="LoanList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Loan" type="{}Loan_Type" maxOccurs="10"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AddData" type="{}ApplicantAddData_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantData_Type", propOrder = {
    "type",
    "personInfo",
    "maritalCondition",
    "relativeList",
    "employmentHistory",
    "income",
    "realEstateFlag",
    "realEstateList",
    "vehicleFlag",
    "vehicleList",
    "loanFlag",
    "loanList",
    "addData"
})
public class ApplicantDataType {

    @XmlElement(name = "Type", required = true)
    protected String type;
    @XmlElement(name = "PersonInfo", required = true)
    protected ApplicantPersonInfoType personInfo;
    @XmlElement(name = "MaritalCondition")
    protected MaritalConditionType maritalCondition;
    @XmlElement(name = "RelativeList")
    protected ApplicantDataType.RelativeList relativeList;
    @XmlElement(name = "EmploymentHistory")
    protected EmploymentHistoryType2 employmentHistory;
    @XmlElement(name = "Income")
    protected IncomeType income;
    @XmlElement(name = "RealEstateFlag", defaultValue = "false")
    protected boolean realEstateFlag;
    @XmlElement(name = "RealEstateList")
    protected ApplicantDataType.RealEstateList realEstateList;
    @XmlElement(name = "VehicleFlag", defaultValue = "false")
    protected boolean vehicleFlag;
    @XmlElement(name = "VehicleList")
    protected ApplicantDataType.VehicleList vehicleList;
    @XmlElement(name = "LoanFlag")
    protected boolean loanFlag;
    @XmlElement(name = "LoanList")
    protected ApplicantDataType.LoanList loanList;
    @XmlElement(name = "AddData")
    protected ApplicantAddDataType addData;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the personInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantPersonInfoType }
     *     
     */
    public ApplicantPersonInfoType getPersonInfo() {
        return personInfo;
    }

    /**
     * Sets the value of the personInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantPersonInfoType }
     *     
     */
    public void setPersonInfo(ApplicantPersonInfoType value) {
        this.personInfo = value;
    }

    /**
     * Gets the value of the maritalCondition property.
     * 
     * @return
     *     possible object is
     *     {@link MaritalConditionType }
     *     
     */
    public MaritalConditionType getMaritalCondition() {
        return maritalCondition;
    }

    /**
     * Sets the value of the maritalCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaritalConditionType }
     *     
     */
    public void setMaritalCondition(MaritalConditionType value) {
        this.maritalCondition = value;
    }

    /**
     * Gets the value of the relativeList property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantDataType.RelativeList }
     *     
     */
    public ApplicantDataType.RelativeList getRelativeList() {
        return relativeList;
    }

    /**
     * Sets the value of the relativeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantDataType.RelativeList }
     *     
     */
    public void setRelativeList(ApplicantDataType.RelativeList value) {
        this.relativeList = value;
    }

    /**
     * Gets the value of the employmentHistory property.
     * 
     * @return
     *     possible object is
     *     {@link EmploymentHistoryType2 }
     *     
     */
    public EmploymentHistoryType2 getEmploymentHistory() {
        return employmentHistory;
    }

    /**
     * Sets the value of the employmentHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmploymentHistoryType2 }
     *     
     */
    public void setEmploymentHistory(EmploymentHistoryType2 value) {
        this.employmentHistory = value;
    }

    /**
     * Gets the value of the income property.
     * 
     * @return
     *     possible object is
     *     {@link IncomeType }
     *     
     */
    public IncomeType getIncome() {
        return income;
    }

    /**
     * Sets the value of the income property.
     * 
     * @param value
     *     allowed object is
     *     {@link IncomeType }
     *     
     */
    public void setIncome(IncomeType value) {
        this.income = value;
    }

    /**
     * Gets the value of the realEstateFlag property.
     * 
     */
    public boolean isRealEstateFlag() {
        return realEstateFlag;
    }

    /**
     * Sets the value of the realEstateFlag property.
     * 
     */
    public void setRealEstateFlag(boolean value) {
        this.realEstateFlag = value;
    }

    /**
     * Gets the value of the realEstateList property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantDataType.RealEstateList }
     *     
     */
    public ApplicantDataType.RealEstateList getRealEstateList() {
        return realEstateList;
    }

    /**
     * Sets the value of the realEstateList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantDataType.RealEstateList }
     *     
     */
    public void setRealEstateList(ApplicantDataType.RealEstateList value) {
        this.realEstateList = value;
    }

    /**
     * Gets the value of the vehicleFlag property.
     * 
     */
    public boolean isVehicleFlag() {
        return vehicleFlag;
    }

    /**
     * Sets the value of the vehicleFlag property.
     * 
     */
    public void setVehicleFlag(boolean value) {
        this.vehicleFlag = value;
    }

    /**
     * Gets the value of the vehicleList property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantDataType.VehicleList }
     *     
     */
    public ApplicantDataType.VehicleList getVehicleList() {
        return vehicleList;
    }

    /**
     * Sets the value of the vehicleList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantDataType.VehicleList }
     *     
     */
    public void setVehicleList(ApplicantDataType.VehicleList value) {
        this.vehicleList = value;
    }

    /**
     * Gets the value of the loanFlag property.
     * 
     */
    public boolean isLoanFlag() {
        return loanFlag;
    }

    /**
     * Sets the value of the loanFlag property.
     * 
     */
    public void setLoanFlag(boolean value) {
        this.loanFlag = value;
    }

    /**
     * Gets the value of the loanList property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantDataType.LoanList }
     *     
     */
    public ApplicantDataType.LoanList getLoanList() {
        return loanList;
    }

    /**
     * Sets the value of the loanList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantDataType.LoanList }
     *     
     */
    public void setLoanList(ApplicantDataType.LoanList value) {
        this.loanList = value;
    }

    /**
     * Gets the value of the addData property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantAddDataType }
     *     
     */
    public ApplicantAddDataType getAddData() {
        return addData;
    }

    /**
     * Sets the value of the addData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantAddDataType }
     *     
     */
    public void setAddData(ApplicantAddDataType value) {
        this.addData = value;
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
     *         &lt;element name="Loan" type="{}Loan_Type" maxOccurs="10"/>
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
        "loen"
    })
    public static class LoanList {

        @XmlElement(name = "Loan", required = true)
        protected List<LoanType> loen;

        /**
         * Gets the value of the loen property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the loen property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLoen().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link LoanType }
         * 
         * 
         */
        public List<LoanType> getLoen() {
            if (loen == null) {
                loen = new ArrayList<LoanType>();
            }
            return this.loen;
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
     *         &lt;element name="RealEstate" type="{}RealEstate_Type" maxOccurs="7"/>
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
        "realEstates"
    })
    public static class RealEstateList {

        @XmlElement(name = "RealEstate", required = true)
        protected List<RealEstateType> realEstates;

        /**
         * Gets the value of the realEstates property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the realEstates property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRealEstates().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RealEstateType }
         * 
         * 
         */
        public List<RealEstateType> getRealEstates() {
            if (realEstates == null) {
                realEstates = new ArrayList<RealEstateType>();
            }
            return this.realEstates;
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
     *         &lt;element name="Relative" type="{}RelativeInfo_Type" maxOccurs="unbounded"/>
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
        "relatives"
    })
    public static class RelativeList {

        @XmlElement(name = "Relative", required = true)
        protected List<RelativeInfoType> relatives;

        /**
         * Gets the value of the relatives property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the relatives property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRelatives().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RelativeInfoType }
         * 
         * 
         */
        public List<RelativeInfoType> getRelatives() {
            if (relatives == null) {
                relatives = new ArrayList<RelativeInfoType>();
            }
            return this.relatives;
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
     *         &lt;element name="Vehicle" type="{}Vehicle_Type" maxOccurs="7"/>
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
        "vehicles"
    })
    public static class VehicleList {

        @XmlElement(name = "Vehicle", required = true)
        protected List<VehicleType> vehicles;

        /**
         * Gets the value of the vehicles property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the vehicles property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVehicles().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link VehicleType }
         * 
         * 
         */
        public List<VehicleType> getVehicles() {
            if (vehicles == null) {
                vehicles = new ArrayList<VehicleType>();
            }
            return this.vehicles;
        }

    }

}

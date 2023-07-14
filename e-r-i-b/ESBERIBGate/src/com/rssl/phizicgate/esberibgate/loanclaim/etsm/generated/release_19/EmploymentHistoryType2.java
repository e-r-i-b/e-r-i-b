
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Блок данных о занятости
 * 
 * <p>Java class for EmploymentHistory_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmploymentHistory_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status">
 *           &lt;simpleType>
 *             &lt;restriction base="{}EmploymentStatus_Type">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="OrgInfo" type="{}OrgInfo_Type" minOccurs="0"/>
 *         &lt;element name="OrgInfo_Ext" type="{}OrgInfoExt_Type" minOccurs="0"/>
 *         &lt;element ref="{}SBEmployeeFlag"/>
 *         &lt;element name="SBEmployee" type="{}SBEmployee_Type" minOccurs="0"/>
 *         &lt;element name="EmployeeInfo" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="JobType">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{}JobType_Type">
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="JobTitle">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="60"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="ExperienceCode" type="{}ExperienceCode_Type"/>
 *                   &lt;element name="WorkPlacesNum">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="OwnBusinessDesc" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmploymentHistory_Type", propOrder = {
    "status",
    "orgInfo",
    "orgInfoExt",
    "sbEmployeeFlag",
    "sbEmployee",
    "employeeInfo",
    "ownBusinessDesc"
})
public class EmploymentHistoryType2 {

    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "OrgInfo")
    protected OrgInfo orgInfo;
    @XmlElement(name = "OrgInfo_Ext")
    protected OrgInfoExtType orgInfoExt;
    @XmlElement(name = "SBEmployeeFlag")
    protected boolean sbEmployeeFlag;
    @XmlElement(name = "SBEmployee")
    protected SBEmployeeType sbEmployee;
    @XmlElement(name = "EmployeeInfo")
    protected EmploymentHistoryType2 .EmployeeInfo employeeInfo;
    @XmlElement(name = "OwnBusinessDesc")
    protected String ownBusinessDesc;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the orgInfo property.
     * 
     * @return
     *     possible object is
     *     {@link OrgInfo }
     *     
     */
    public OrgInfo getOrgInfo() {
        return orgInfo;
    }

    /**
     * Sets the value of the orgInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrgInfo }
     *     
     */
    public void setOrgInfo(OrgInfo value) {
        this.orgInfo = value;
    }

    /**
     * Gets the value of the orgInfoExt property.
     * 
     * @return
     *     possible object is
     *     {@link OrgInfoExtType }
     *     
     */
    public OrgInfoExtType getOrgInfoExt() {
        return orgInfoExt;
    }

    /**
     * Sets the value of the orgInfoExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrgInfoExtType }
     *     
     */
    public void setOrgInfoExt(OrgInfoExtType value) {
        this.orgInfoExt = value;
    }

    /**
     * Gets the value of the sbEmployeeFlag property.
     * 
     */
    public boolean isSBEmployeeFlag() {
        return sbEmployeeFlag;
    }

    /**
     * Sets the value of the sbEmployeeFlag property.
     * 
     */
    public void setSBEmployeeFlag(boolean value) {
        this.sbEmployeeFlag = value;
    }

    /**
     * Gets the value of the sbEmployee property.
     * 
     * @return
     *     possible object is
     *     {@link SBEmployeeType }
     *     
     */
    public SBEmployeeType getSBEmployee() {
        return sbEmployee;
    }

    /**
     * Sets the value of the sbEmployee property.
     * 
     * @param value
     *     allowed object is
     *     {@link SBEmployeeType }
     *     
     */
    public void setSBEmployee(SBEmployeeType value) {
        this.sbEmployee = value;
    }

    /**
     * Gets the value of the employeeInfo property.
     * 
     * @return
     *     possible object is
     *     {@link EmploymentHistoryType2 .EmployeeInfo }
     *     
     */
    public EmploymentHistoryType2 .EmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    /**
     * Sets the value of the employeeInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmploymentHistoryType2 .EmployeeInfo }
     *     
     */
    public void setEmployeeInfo(EmploymentHistoryType2 .EmployeeInfo value) {
        this.employeeInfo = value;
    }

    /**
     * Gets the value of the ownBusinessDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnBusinessDesc() {
        return ownBusinessDesc;
    }

    /**
     * Sets the value of the ownBusinessDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnBusinessDesc(String value) {
        this.ownBusinessDesc = value;
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
     *         &lt;element name="JobType">
     *           &lt;simpleType>
     *             &lt;restriction base="{}JobType_Type">
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="JobTitle">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="60"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="ExperienceCode" type="{}ExperienceCode_Type"/>
     *         &lt;element name="WorkPlacesNum">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
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
        "jobType",
        "jobTitle",
        "experienceCode",
        "workPlacesNum"
    })
    public static class EmployeeInfo {

        @XmlElement(name = "JobType", required = true)
        protected String jobType;
        @XmlElement(name = "JobTitle", required = true)
        protected String jobTitle;
        @XmlElement(name = "ExperienceCode", required = true)
        protected String experienceCode;
        @XmlElement(name = "WorkPlacesNum", required = true)
        protected BigInteger workPlacesNum;

        /**
         * Gets the value of the jobType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getJobType() {
            return jobType;
        }

        /**
         * Sets the value of the jobType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setJobType(String value) {
            this.jobType = value;
        }

        /**
         * Gets the value of the jobTitle property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getJobTitle() {
            return jobTitle;
        }

        /**
         * Sets the value of the jobTitle property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setJobTitle(String value) {
            this.jobTitle = value;
        }

        /**
         * Gets the value of the experienceCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getExperienceCode() {
            return experienceCode;
        }

        /**
         * Sets the value of the experienceCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setExperienceCode(String value) {
            this.experienceCode = value;
        }

        /**
         * Gets the value of the workPlacesNum property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getWorkPlacesNum() {
            return workPlacesNum;
        }

        /**
         * Sets the value of the workPlacesNum property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setWorkPlacesNum(BigInteger value) {
            this.workPlacesNum = value;
        }

    }

}

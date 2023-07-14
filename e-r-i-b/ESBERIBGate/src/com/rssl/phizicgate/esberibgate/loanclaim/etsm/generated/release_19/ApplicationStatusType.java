
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Тип - Статус кредитной заявки
 * 
 * <p>Java class for ApplicationStatus_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicationStatus_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="StatusCode">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="ApplicationNumber" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="20"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Error" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ErrorCode">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                   &lt;enumeration value="-1"/>
 *                                   &lt;enumeration value="1"/>
 *                                   &lt;enumeration value="2"/>
 *                                   &lt;enumeration value="4"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Message">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="255"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Approval" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PeriodM">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
 *                         &lt;totalDigits value="4"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Amount">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;totalDigits value="17"/>
 *                         &lt;fractionDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="InterestRate">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;totalDigits value="6"/>
 *                         &lt;fractionDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="PrePeriodM" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
 *                         &lt;totalDigits value="4"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="PreAmount" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;totalDigits value="17"/>
 *                         &lt;fractionDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="PreInterestRate" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;totalDigits value="6"/>
 *                         &lt;fractionDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="CreditCardLimit" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;totalDigits value="17"/>
 *                         &lt;fractionDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Application" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="FullNameKI">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="150"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="LoginKI">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="50"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="FullNameTM" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="150"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="LoginTM" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="50"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Unit">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{}String">
 *                         &lt;length value="11"/>
 *                         &lt;pattern value="\d{11}"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Channel" type="{}Chanel_Type"/>
 *                   &lt;element name="appSigningDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="Product">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Type">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{}String">
 *                                   &lt;length value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Code">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{}String">
 *                                   &lt;maxLength value="10"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="SubProductCode">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{}String">
 *                                   &lt;maxLength value="10"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Amount">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;totalDigits value="17"/>
 *                                   &lt;fractionDigits value="2"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Period" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *                             &lt;element name="Currency">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;enumeration value="RUB"/>
 *                                   &lt;enumeration value="USD"/>
 *                                   &lt;enumeration value="EUR"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="PaymentType">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{}String">
 *                                   &lt;enumeration value="Annuity"/>
 *                                   &lt;enumeration value="Diff"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Applicant" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="LastName" type="{}LastName_Type"/>
 *                             &lt;element name="FirstName" type="{}FirstName_Type"/>
 *                             &lt;element name="MiddleName" type="{}MiddleName_Type" minOccurs="0"/>
 *                             &lt;element name="Birthday" type="{}Date"/>
 *                             &lt;element name="Citizenship">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{}String">
 *                                   &lt;enumeration value="RUSSIA"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="IdSeries">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{}String">
 *                                   &lt;length value="4"/>
 *                                   &lt;pattern value="\d\d\d\d"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="IdNum">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{}String">
 *                                   &lt;length value="6"/>
 *                                   &lt;pattern value="\d\d\d\d\d\d"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="IssueDt" type="{}Date"/>
 *                             &lt;element name="IssueCode">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{}String">
 *                                   &lt;maxLength value="20"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="IssuedBy">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{}String">
 *                                   &lt;maxLength value="100"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="PrevPassportInfoFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                             &lt;element name="PrevIdSeries" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{}String">
 *                                   &lt;length value="4"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="PrevIdNum" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{}String">
 *                                   &lt;length value="6"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="PrevIssueDt" type="{}Date" minOccurs="0"/>
 *                             &lt;element name="PrevIssuedBy" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{}String">
 *                                   &lt;maxLength value="100"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="InsuranceInfo" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="businessProcess" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="insuranceProgram" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="includeInsuranceFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                             &lt;element name="insurancePremium" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Type">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;length value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="AcctId" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="25"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="CardNum" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="13"/>
 *                         &lt;maxLength value="19"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ReasonCode" minOccurs="0">
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
@XmlType(name = "ApplicationStatus_Type", propOrder = {
    "status",
    "approval",
    "application",
    "reasonCode"
})
public class ApplicationStatusType {

    @XmlElement(name = "Status", required = true)
    protected ApplicationStatusType.Status status;
    @XmlElement(name = "Approval")
    protected ApplicationStatusType.Approval approval;
    @XmlElement(name = "Application")
    protected ApplicationStatusType.Application application;
    @XmlElement(name = "ReasonCode")
    protected String reasonCode;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationStatusType.Status }
     *     
     */
    public ApplicationStatusType.Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationStatusType.Status }
     *     
     */
    public void setStatus(ApplicationStatusType.Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the approval property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationStatusType.Approval }
     *     
     */
    public ApplicationStatusType.Approval getApproval() {
        return approval;
    }

    /**
     * Sets the value of the approval property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationStatusType.Approval }
     *     
     */
    public void setApproval(ApplicationStatusType.Approval value) {
        this.approval = value;
    }

    /**
     * Gets the value of the application property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationStatusType.Application }
     *     
     */
    public ApplicationStatusType.Application getApplication() {
        return application;
    }

    /**
     * Sets the value of the application property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationStatusType.Application }
     *     
     */
    public void setApplication(ApplicationStatusType.Application value) {
        this.application = value;
    }

    /**
     * Gets the value of the reasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonCode(String value) {
        this.reasonCode = value;
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
     *         &lt;element name="FullNameKI">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="150"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="LoginKI">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="50"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="FullNameTM" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="150"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="LoginTM" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="50"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Unit">
     *           &lt;simpleType>
     *             &lt;restriction base="{}String">
     *               &lt;length value="11"/>
     *               &lt;pattern value="\d{11}"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Channel" type="{}Chanel_Type"/>
     *         &lt;element name="appSigningDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="Product">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Type">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{}String">
     *                         &lt;length value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Code">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{}String">
     *                         &lt;maxLength value="10"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="SubProductCode">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{}String">
     *                         &lt;maxLength value="10"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Amount">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;totalDigits value="17"/>
     *                         &lt;fractionDigits value="2"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Period" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
     *                   &lt;element name="Currency">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;enumeration value="RUB"/>
     *                         &lt;enumeration value="USD"/>
     *                         &lt;enumeration value="EUR"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="PaymentType">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{}String">
     *                         &lt;enumeration value="Annuity"/>
     *                         &lt;enumeration value="Diff"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Applicant" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="LastName" type="{}LastName_Type"/>
     *                   &lt;element name="FirstName" type="{}FirstName_Type"/>
     *                   &lt;element name="MiddleName" type="{}MiddleName_Type" minOccurs="0"/>
     *                   &lt;element name="Birthday" type="{}Date"/>
     *                   &lt;element name="Citizenship">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{}String">
     *                         &lt;enumeration value="RUSSIA"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="IdSeries">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{}String">
     *                         &lt;length value="4"/>
     *                         &lt;pattern value="\d\d\d\d"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="IdNum">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{}String">
     *                         &lt;length value="6"/>
     *                         &lt;pattern value="\d\d\d\d\d\d"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="IssueDt" type="{}Date"/>
     *                   &lt;element name="IssueCode">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{}String">
     *                         &lt;maxLength value="20"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="IssuedBy">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{}String">
     *                         &lt;maxLength value="100"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="PrevPassportInfoFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                   &lt;element name="PrevIdSeries" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{}String">
     *                         &lt;length value="4"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="PrevIdNum" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{}String">
     *                         &lt;length value="6"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="PrevIssueDt" type="{}Date" minOccurs="0"/>
     *                   &lt;element name="PrevIssuedBy" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{}String">
     *                         &lt;maxLength value="100"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Address" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="InsuranceInfo" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="businessProcess" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="insuranceProgram" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="includeInsuranceFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                   &lt;element name="insurancePremium" type="{http://www.w3.org/2001/XMLSchema}double"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Type">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;length value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="AcctId" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="25"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="CardNum" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="13"/>
     *               &lt;maxLength value="19"/>
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
        "fullNameKI",
        "loginKI",
        "fullNameTM",
        "loginTM",
        "unit",
        "channel",
        "appSigningDate",
        "product",
        "applicant",
        "insuranceInfo",
        "type",
        "acctId",
        "cardNum"
    })
    public static class Application {

        @XmlElement(name = "FullNameKI", required = true)
        protected String fullNameKI;
        @XmlElement(name = "LoginKI", required = true)
        protected String loginKI;
        @XmlElement(name = "FullNameTM")
        protected String fullNameTM;
        @XmlElement(name = "LoginTM")
        protected String loginTM;
        @XmlElement(name = "Unit", required = true)
        protected String unit;
        @XmlElement(name = "Channel", required = true)
        protected String channel;
        @XmlElement(type = String.class)
        @XmlJavaTypeAdapter(CalendarDateAdapter.class)
        @XmlSchemaType(name = "date")
        protected Calendar appSigningDate;
        @XmlElement(name = "Product", required = true)
        protected ApplicationStatusType.Application.Product product;
        @XmlElement(name = "Applicant")
        protected ApplicationStatusType.Application.Applicant applicant;
        @XmlElement(name = "InsuranceInfo")
        protected ApplicationStatusType.Application.InsuranceInfo insuranceInfo;
        @XmlElement(name = "Type", required = true)
        protected String type;
        @XmlElement(name = "AcctId")
        protected String acctId;
        @XmlElement(name = "CardNum")
        protected String cardNum;

        /**
         * Gets the value of the fullNameKI property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFullNameKI() {
            return fullNameKI;
        }

        /**
         * Sets the value of the fullNameKI property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFullNameKI(String value) {
            this.fullNameKI = value;
        }

        /**
         * Gets the value of the loginKI property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLoginKI() {
            return loginKI;
        }

        /**
         * Sets the value of the loginKI property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLoginKI(String value) {
            this.loginKI = value;
        }

        /**
         * Gets the value of the fullNameTM property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFullNameTM() {
            return fullNameTM;
        }

        /**
         * Sets the value of the fullNameTM property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFullNameTM(String value) {
            this.fullNameTM = value;
        }

        /**
         * Gets the value of the loginTM property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLoginTM() {
            return loginTM;
        }

        /**
         * Sets the value of the loginTM property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLoginTM(String value) {
            this.loginTM = value;
        }

        /**
         * Gets the value of the unit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUnit() {
            return unit;
        }

        /**
         * Sets the value of the unit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUnit(String value) {
            this.unit = value;
        }

        /**
         * Gets the value of the channel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChannel() {
            return channel;
        }

        /**
         * Sets the value of the channel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChannel(String value) {
            this.channel = value;
        }

        /**
         * Gets the value of the appSigningDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public Calendar getAppSigningDate() {
            return appSigningDate;
        }

        /**
         * Sets the value of the appSigningDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAppSigningDate(Calendar value) {
            this.appSigningDate = value;
        }

        /**
         * Gets the value of the product property.
         * 
         * @return
         *     possible object is
         *     {@link ApplicationStatusType.Application.Product }
         *     
         */
        public ApplicationStatusType.Application.Product getProduct() {
            return product;
        }

        /**
         * Sets the value of the product property.
         * 
         * @param value
         *     allowed object is
         *     {@link ApplicationStatusType.Application.Product }
         *     
         */
        public void setProduct(ApplicationStatusType.Application.Product value) {
            this.product = value;
        }

        /**
         * Gets the value of the applicant property.
         * 
         * @return
         *     possible object is
         *     {@link ApplicationStatusType.Application.Applicant }
         *     
         */
        public ApplicationStatusType.Application.Applicant getApplicant() {
            return applicant;
        }

        /**
         * Sets the value of the applicant property.
         * 
         * @param value
         *     allowed object is
         *     {@link ApplicationStatusType.Application.Applicant }
         *     
         */
        public void setApplicant(ApplicationStatusType.Application.Applicant value) {
            this.applicant = value;
        }

        /**
         * Gets the value of the insuranceInfo property.
         * 
         * @return
         *     possible object is
         *     {@link ApplicationStatusType.Application.InsuranceInfo }
         *     
         */
        public ApplicationStatusType.Application.InsuranceInfo getInsuranceInfo() {
            return insuranceInfo;
        }

        /**
         * Sets the value of the insuranceInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link ApplicationStatusType.Application.InsuranceInfo }
         *     
         */
        public void setInsuranceInfo(ApplicationStatusType.Application.InsuranceInfo value) {
            this.insuranceInfo = value;
        }

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
         * Gets the value of the acctId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAcctId() {
            return acctId;
        }

        /**
         * Sets the value of the acctId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAcctId(String value) {
            this.acctId = value;
        }

        /**
         * Gets the value of the cardNum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCardNum() {
            return cardNum;
        }

        /**
         * Sets the value of the cardNum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCardNum(String value) {
            this.cardNum = value;
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
         *         &lt;element name="LastName" type="{}LastName_Type"/>
         *         &lt;element name="FirstName" type="{}FirstName_Type"/>
         *         &lt;element name="MiddleName" type="{}MiddleName_Type" minOccurs="0"/>
         *         &lt;element name="Birthday" type="{}Date"/>
         *         &lt;element name="Citizenship">
         *           &lt;simpleType>
         *             &lt;restriction base="{}String">
         *               &lt;enumeration value="RUSSIA"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="IdSeries">
         *           &lt;simpleType>
         *             &lt;restriction base="{}String">
         *               &lt;length value="4"/>
         *               &lt;pattern value="\d\d\d\d"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="IdNum">
         *           &lt;simpleType>
         *             &lt;restriction base="{}String">
         *               &lt;length value="6"/>
         *               &lt;pattern value="\d\d\d\d\d\d"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="IssueDt" type="{}Date"/>
         *         &lt;element name="IssueCode">
         *           &lt;simpleType>
         *             &lt;restriction base="{}String">
         *               &lt;maxLength value="20"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="IssuedBy">
         *           &lt;simpleType>
         *             &lt;restriction base="{}String">
         *               &lt;maxLength value="100"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="PrevPassportInfoFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
         *         &lt;element name="PrevIdSeries" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{}String">
         *               &lt;length value="4"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="PrevIdNum" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{}String">
         *               &lt;length value="6"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="PrevIssueDt" type="{}Date" minOccurs="0"/>
         *         &lt;element name="PrevIssuedBy" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{}String">
         *               &lt;maxLength value="100"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Address" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "lastName",
            "firstName",
            "middleName",
            "birthday",
            "citizenship",
            "idSeries",
            "idNum",
            "issueDt",
            "issueCode",
            "issuedBy",
            "prevPassportInfoFlag",
            "prevIdSeries",
            "prevIdNum",
            "prevIssueDt",
            "prevIssuedBy",
            "address"
        })
        public static class Applicant {

            @XmlElement(name = "LastName", required = true)
            protected String lastName;
            @XmlElement(name = "FirstName", required = true)
            protected String firstName;
            @XmlElement(name = "MiddleName")
            protected String middleName;
            @XmlElement(name = "Birthday", required = true, type = String.class)
            @XmlJavaTypeAdapter(CalendarDateAdapter.class)
            protected Calendar birthday;
            @XmlElement(name = "Citizenship", required = true)
            protected String citizenship;
            @XmlElement(name = "IdSeries", required = true)
            protected String idSeries;
            @XmlElement(name = "IdNum", required = true)
            protected String idNum;
            @XmlElement(name = "IssueDt", required = true, type = String.class)
            @XmlJavaTypeAdapter(CalendarDateAdapter.class)
            protected Calendar issueDt;
            @XmlElement(name = "IssueCode", required = true)
            protected String issueCode;
            @XmlElement(name = "IssuedBy", required = true)
            protected String issuedBy;
            @XmlElement(name = "PrevPassportInfoFlag")
            protected boolean prevPassportInfoFlag;
            @XmlElement(name = "PrevIdSeries")
            protected String prevIdSeries;
            @XmlElement(name = "PrevIdNum")
            protected String prevIdNum;
            @XmlElement(name = "PrevIssueDt", type = String.class)
            @XmlJavaTypeAdapter(CalendarDateAdapter.class)
            protected Calendar prevIssueDt;
            @XmlElement(name = "PrevIssuedBy")
            protected String prevIssuedBy;
            @XmlElement(name = "Address", required = true)
            protected String address;

            /**
             * Gets the value of the lastName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLastName() {
                return lastName;
            }

            /**
             * Sets the value of the lastName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLastName(String value) {
                this.lastName = value;
            }

            /**
             * Gets the value of the firstName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFirstName() {
                return firstName;
            }

            /**
             * Sets the value of the firstName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFirstName(String value) {
                this.firstName = value;
            }

            /**
             * Gets the value of the middleName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMiddleName() {
                return middleName;
            }

            /**
             * Sets the value of the middleName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMiddleName(String value) {
                this.middleName = value;
            }

            /**
             * Gets the value of the birthday property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public Calendar getBirthday() {
                return birthday;
            }

            /**
             * Sets the value of the birthday property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBirthday(Calendar value) {
                this.birthday = value;
            }

            /**
             * Gets the value of the citizenship property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCitizenship() {
                return citizenship;
            }

            /**
             * Sets the value of the citizenship property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCitizenship(String value) {
                this.citizenship = value;
            }

            /**
             * Gets the value of the idSeries property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIdSeries() {
                return idSeries;
            }

            /**
             * Sets the value of the idSeries property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIdSeries(String value) {
                this.idSeries = value;
            }

            /**
             * Gets the value of the idNum property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIdNum() {
                return idNum;
            }

            /**
             * Sets the value of the idNum property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIdNum(String value) {
                this.idNum = value;
            }

            /**
             * Gets the value of the issueDt property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public Calendar getIssueDt() {
                return issueDt;
            }

            /**
             * Sets the value of the issueDt property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIssueDt(Calendar value) {
                this.issueDt = value;
            }

            /**
             * Gets the value of the issueCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIssueCode() {
                return issueCode;
            }

            /**
             * Sets the value of the issueCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIssueCode(String value) {
                this.issueCode = value;
            }

            /**
             * Gets the value of the issuedBy property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIssuedBy() {
                return issuedBy;
            }

            /**
             * Sets the value of the issuedBy property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIssuedBy(String value) {
                this.issuedBy = value;
            }

            /**
             * Gets the value of the prevPassportInfoFlag property.
             * 
             */
            public boolean isPrevPassportInfoFlag() {
                return prevPassportInfoFlag;
            }

            /**
             * Sets the value of the prevPassportInfoFlag property.
             * 
             */
            public void setPrevPassportInfoFlag(boolean value) {
                this.prevPassportInfoFlag = value;
            }

            /**
             * Gets the value of the prevIdSeries property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPrevIdSeries() {
                return prevIdSeries;
            }

            /**
             * Sets the value of the prevIdSeries property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPrevIdSeries(String value) {
                this.prevIdSeries = value;
            }

            /**
             * Gets the value of the prevIdNum property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPrevIdNum() {
                return prevIdNum;
            }

            /**
             * Sets the value of the prevIdNum property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPrevIdNum(String value) {
                this.prevIdNum = value;
            }

            /**
             * Gets the value of the prevIssueDt property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public Calendar getPrevIssueDt() {
                return prevIssueDt;
            }

            /**
             * Sets the value of the prevIssueDt property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPrevIssueDt(Calendar value) {
                this.prevIssueDt = value;
            }

            /**
             * Gets the value of the prevIssuedBy property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPrevIssuedBy() {
                return prevIssuedBy;
            }

            /**
             * Sets the value of the prevIssuedBy property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPrevIssuedBy(String value) {
                this.prevIssuedBy = value;
            }

            /**
             * Gets the value of the address property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAddress() {
                return address;
            }

            /**
             * Sets the value of the address property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAddress(String value) {
                this.address = value;
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
         *         &lt;element name="businessProcess" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="insuranceProgram" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="includeInsuranceFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
         *         &lt;element name="insurancePremium" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
            "businessProcess",
            "insuranceProgram",
            "includeInsuranceFlag",
            "insurancePremium"
        })
        public static class InsuranceInfo {

            @XmlElement(required = true)
            protected String businessProcess;
            @XmlElement(required = true)
            protected String insuranceProgram;
            protected boolean includeInsuranceFlag;
            protected double insurancePremium;

            /**
             * Gets the value of the businessProcess property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBusinessProcess() {
                return businessProcess;
            }

            /**
             * Sets the value of the businessProcess property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBusinessProcess(String value) {
                this.businessProcess = value;
            }

            /**
             * Gets the value of the insuranceProgram property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInsuranceProgram() {
                return insuranceProgram;
            }

            /**
             * Sets the value of the insuranceProgram property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInsuranceProgram(String value) {
                this.insuranceProgram = value;
            }

            /**
             * Gets the value of the includeInsuranceFlag property.
             * 
             */
            public boolean isIncludeInsuranceFlag() {
                return includeInsuranceFlag;
            }

            /**
             * Sets the value of the includeInsuranceFlag property.
             * 
             */
            public void setIncludeInsuranceFlag(boolean value) {
                this.includeInsuranceFlag = value;
            }

            /**
             * Gets the value of the insurancePremium property.
             * 
             */
            public double getInsurancePremium() {
                return insurancePremium;
            }

            /**
             * Sets the value of the insurancePremium property.
             * 
             */
            public void setInsurancePremium(double value) {
                this.insurancePremium = value;
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
         *         &lt;element name="Type">
         *           &lt;simpleType>
         *             &lt;restriction base="{}String">
         *               &lt;length value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Code">
         *           &lt;simpleType>
         *             &lt;restriction base="{}String">
         *               &lt;maxLength value="10"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="SubProductCode">
         *           &lt;simpleType>
         *             &lt;restriction base="{}String">
         *               &lt;maxLength value="10"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Amount">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;totalDigits value="17"/>
         *               &lt;fractionDigits value="2"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Period" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
         *         &lt;element name="Currency">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;enumeration value="RUB"/>
         *               &lt;enumeration value="USD"/>
         *               &lt;enumeration value="EUR"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="PaymentType">
         *           &lt;simpleType>
         *             &lt;restriction base="{}String">
         *               &lt;enumeration value="Annuity"/>
         *               &lt;enumeration value="Diff"/>
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
            "type",
            "code",
            "subProductCode",
            "amount",
            "period",
            "currency",
            "paymentType"
        })
        public static class Product {

            @XmlElement(name = "Type", required = true)
            protected String type;
            @XmlElement(name = "Code", required = true)
            protected String code;
            @XmlElement(name = "SubProductCode", required = true)
            protected String subProductCode;
            @XmlElement(name = "Amount", required = true)
            protected BigDecimal amount;
            @XmlElement(name = "Period")
            @XmlSchemaType(name = "unsignedInt")
            protected long period;
            @XmlElement(name = "Currency", required = true)
            protected String currency;
            @XmlElement(name = "PaymentType", required = true)
            protected String paymentType;

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
             * Gets the value of the code property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCode() {
                return code;
            }

            /**
             * Sets the value of the code property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCode(String value) {
                this.code = value;
            }

            /**
             * Gets the value of the subProductCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSubProductCode() {
                return subProductCode;
            }

            /**
             * Sets the value of the subProductCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSubProductCode(String value) {
                this.subProductCode = value;
            }

            /**
             * Gets the value of the amount property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getAmount() {
                return amount;
            }

            /**
             * Sets the value of the amount property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setAmount(BigDecimal value) {
                this.amount = value;
            }

            /**
             * Gets the value of the period property.
             * 
             */
            public long getPeriod() {
                return period;
            }

            /**
             * Sets the value of the period property.
             * 
             */
            public void setPeriod(long value) {
                this.period = value;
            }

            /**
             * Gets the value of the currency property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCurrency() {
                return currency;
            }

            /**
             * Sets the value of the currency property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCurrency(String value) {
                this.currency = value;
            }

            /**
             * Gets the value of the paymentType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPaymentType() {
                return paymentType;
            }

            /**
             * Sets the value of the paymentType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPaymentType(String value) {
                this.paymentType = value;
            }

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
     *         &lt;element name="PeriodM">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
     *               &lt;totalDigits value="4"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Amount">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;totalDigits value="17"/>
     *               &lt;fractionDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="InterestRate">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;totalDigits value="6"/>
     *               &lt;fractionDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="PrePeriodM" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
     *               &lt;totalDigits value="4"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="PreAmount" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;totalDigits value="17"/>
     *               &lt;fractionDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="PreInterestRate" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;totalDigits value="6"/>
     *               &lt;fractionDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="CreditCardLimit" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;totalDigits value="17"/>
     *               &lt;fractionDigits value="2"/>
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
        "periodM",
        "amount",
        "interestRate",
        "prePeriodM",
        "preAmount",
        "preInterestRate",
        "creditCardLimit"
    })
    public static class Approval {

        @XmlElement(name = "PeriodM")
        protected long periodM;
        @XmlElement(name = "Amount", required = true)
        protected BigDecimal amount;
        @XmlElement(name = "InterestRate", required = true)
        protected BigDecimal interestRate;
        @XmlElement(name = "PrePeriodM")
        protected Long prePeriodM;
        @XmlElement(name = "PreAmount")
        protected BigDecimal preAmount;
        @XmlElement(name = "PreInterestRate")
        protected BigDecimal preInterestRate;
        @XmlElement(name = "CreditCardLimit")
        protected BigDecimal creditCardLimit;

        /**
         * Gets the value of the periodM property.
         * 
         */
        public long getPeriodM() {
            return periodM;
        }

        /**
         * Sets the value of the periodM property.
         * 
         */
        public void setPeriodM(long value) {
            this.periodM = value;
        }

        /**
         * Gets the value of the amount property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getAmount() {
            return amount;
        }

        /**
         * Sets the value of the amount property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setAmount(BigDecimal value) {
            this.amount = value;
        }

        /**
         * Gets the value of the interestRate property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getInterestRate() {
            return interestRate;
        }

        /**
         * Sets the value of the interestRate property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setInterestRate(BigDecimal value) {
            this.interestRate = value;
        }

        /**
         * Gets the value of the prePeriodM property.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getPrePeriodM() {
            return prePeriodM;
        }

        /**
         * Sets the value of the prePeriodM property.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setPrePeriodM(Long value) {
            this.prePeriodM = value;
        }

        /**
         * Gets the value of the preAmount property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getPreAmount() {
            return preAmount;
        }

        /**
         * Sets the value of the preAmount property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setPreAmount(BigDecimal value) {
            this.preAmount = value;
        }

        /**
         * Gets the value of the preInterestRate property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getPreInterestRate() {
            return preInterestRate;
        }

        /**
         * Sets the value of the preInterestRate property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setPreInterestRate(BigDecimal value) {
            this.preInterestRate = value;
        }

        /**
         * Gets the value of the creditCardLimit property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getCreditCardLimit() {
            return creditCardLimit;
        }

        /**
         * Sets the value of the creditCardLimit property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setCreditCardLimit(BigDecimal value) {
            this.creditCardLimit = value;
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
     *         &lt;element name="StatusCode">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="ApplicationNumber" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="20"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Error" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ErrorCode">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                         &lt;enumeration value="-1"/>
     *                         &lt;enumeration value="1"/>
     *                         &lt;enumeration value="2"/>
     *                         &lt;enumeration value="4"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Message">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="255"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
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
        "statusCode",
        "applicationNumber",
        "error"
    })
    public static class Status {

        @XmlElement(name = "StatusCode")
        protected int statusCode;
        @XmlElement(name = "ApplicationNumber")
        protected String applicationNumber;
        @XmlElement(name = "Error")
        protected ApplicationStatusType.Status.Error error;

        /**
         * Gets the value of the statusCode property.
         * 
         */
        public int getStatusCode() {
            return statusCode;
        }

        /**
         * Sets the value of the statusCode property.
         * 
         */
        public void setStatusCode(int value) {
            this.statusCode = value;
        }

        /**
         * Gets the value of the applicationNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getApplicationNumber() {
            return applicationNumber;
        }

        /**
         * Sets the value of the applicationNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setApplicationNumber(String value) {
            this.applicationNumber = value;
        }

        /**
         * Gets the value of the error property.
         * 
         * @return
         *     possible object is
         *     {@link ApplicationStatusType.Status.Error }
         *     
         */
        public ApplicationStatusType.Status.Error getError() {
            return error;
        }

        /**
         * Sets the value of the error property.
         * 
         * @param value
         *     allowed object is
         *     {@link ApplicationStatusType.Status.Error }
         *     
         */
        public void setError(ApplicationStatusType.Status.Error value) {
            this.error = value;
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
         *         &lt;element name="ErrorCode">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *               &lt;enumeration value="-1"/>
         *               &lt;enumeration value="1"/>
         *               &lt;enumeration value="2"/>
         *               &lt;enumeration value="4"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Message">
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
        @XmlType(name = "", propOrder = {
            "errorCode",
            "message"
        })
        public static class Error {

            @XmlElement(name = "ErrorCode")
            protected int errorCode;
            @XmlElement(name = "Message", required = true)
            protected String message;

            /**
             * Gets the value of the errorCode property.
             * 
             */
            public int getErrorCode() {
                return errorCode;
            }

            /**
             * Sets the value of the errorCode property.
             * 
             */
            public void setErrorCode(int value) {
                this.errorCode = value;
            }

            /**
             * Gets the value of the message property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMessage() {
                return message;
            }

            /**
             * Sets the value of the message property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMessage(String value) {
                this.message = value;
            }

        }

    }

}

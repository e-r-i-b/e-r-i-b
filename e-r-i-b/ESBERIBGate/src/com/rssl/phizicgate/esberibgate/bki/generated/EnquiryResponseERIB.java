
package com.rssl.phizicgate.esberibgate.bki.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * <p>Java class for EnquiryResponseERIBType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EnquiryResponseERIBType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}RqUID"/>
 *         &lt;element ref="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}RqTm"/>
 *         &lt;element ref="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}OperUID"/>
 *         &lt;element name="Consumers">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="s" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BureauScore" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="confidenceFlag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                                       &lt;element name="scoreCardType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                                       &lt;element name="scoreInterval" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                                       &lt;element name="scoreNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="CAIS" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Consumer" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="Address" maxOccurs="unbounded">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="addressCurrPrev" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                           &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                           &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                           &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="homeTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                           &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                           &lt;element name="type" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="Employer" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                           &lt;element name="currentPreviousIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                           &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                           &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="idNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                           &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                           &lt;element name="telephoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="accountHolderType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                 &lt;element name="aliasName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="consentFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="dateConsentGiven" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="dateOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="mobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="name1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="name2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="name3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="nationality" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                 &lt;element name="pensionNbr" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                                 &lt;element name="placeOfBirth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="previousCompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="previousPassportNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="primaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="primaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="primaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="primaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="primaryIDPlaceOfIssue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="primaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                 &lt;element name="privateEntrepreneurEGRN" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                                 &lt;element name="privateEntrepreneurNbr" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                                 &lt;element name="privateEntrepreneurNbrIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="secondaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="secondaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="secondaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="secondaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="secondaryIDPlaceOfIssue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="secondaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                 &lt;element name="sex" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="title" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="MontlyHistory" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="accountBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                                 &lt;element name="accountPaymentHistory" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
 *                                                 &lt;element name="arrearsBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                                 &lt;element name="creditLimit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                                 &lt;element name="historyDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="instalment" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="accountClass" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *                                       &lt;element name="accountClosedDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                       &lt;element name="accountHolderComments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="accountPaymentStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                       &lt;element name="accountSpecialStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                       &lt;element name="amountInsuredLoan" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                       &lt;element name="amountOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                       &lt;element name="arrearsBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                       &lt;element name="consumerAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="creditFacilityStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                       &lt;element name="creditLimit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                       &lt;element name="currency">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             &lt;enumeration value="RUB"/>
 *                                             &lt;enumeration value="USD"/>
 *                                             &lt;enumeration value="EUR"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="dateAccountAdded" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                       &lt;element name="defaultDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                       &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="durationUnit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                       &lt;element name="financeType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                       &lt;element name="fulfilmentDueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                       &lt;element name="instalment" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                       &lt;element name="insuredLoan" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                       &lt;element name="interestRate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                       &lt;element name="lastMissedPaymentDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                       &lt;element name="lastPaymentDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                       &lt;element name="lastUpdateTS" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                       &lt;element name="litigationDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                       &lt;element name="monthOfLastUpdate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                       &lt;element name="numOfApplicants" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *                                       &lt;element name="openDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                       &lt;element name="outstandingBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                       &lt;element name="paymentFrequency" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                       &lt;element name="purposeOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                       &lt;element name="reasonForClosure" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                       &lt;element name="recordBlockDisputeIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                       &lt;element name="subscriberComments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="subscriberName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="typeOfSecurity" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                       &lt;element name="worstPaymentStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
 *                                       &lt;element name="writeOffDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="CAPS" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Consumer" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="Address" maxOccurs="unbounded">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="addressFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                           &lt;element name="addressType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                           &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                           &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="homeTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                           &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                           &lt;element name="timeAtAddress" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="Employer" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                           &lt;element name="currentPreviousIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                           &lt;element name="employerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                           &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="idNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                           &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                           &lt;element name="timeWithEmployer" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                                           &lt;element name="workMobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="workTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="Verification" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="status" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}status" minOccurs="0"/>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="aliasName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="applicantType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                 &lt;element name="consentFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="dateConsentGiven" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="dateOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="drivingLicenceNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="education" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="maritalStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                 &lt;element name="medicalNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="mobileRegistration" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="mobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="name1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="name2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="nationality" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                 &lt;element name="numberDependants" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                                 &lt;element name="occupation" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                 &lt;element name="otherIncome" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                                 &lt;element name="pensionNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="placeOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="previousCompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="previousPassportNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="primaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="primaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="primaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="primaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="primaryIDIssuePlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="primaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                 &lt;element name="primaryIncome" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                                 &lt;element name="primaryIncomeFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="primaryIncomeFreq" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="privateEntrepreneurEGRN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="privateEntrepreneurNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="privateEntrepreneurNbrIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="residentialStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="secondaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="secondaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="secondaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="secondaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                                 &lt;element name="secondaryIDIssuePlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="secondaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                 &lt;element name="sex" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *                                                 &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="title" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                 &lt;element name="occupationStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                                 &lt;element name="mastercardHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="retailHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="amExpressHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="chqGteeHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="debitHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="dinersHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="jcbHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="otherHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                                 &lt;element name="visaHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="accountClass" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                       &lt;element name="amountOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                       &lt;element name="applicationDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                       &lt;element name="applicationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="creditLimit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                       &lt;element name="currency">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             &lt;enumeration value="RUB"/>
 *                                             &lt;enumeration value="USD"/>
 *                                             &lt;enumeration value="EUR"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="disputeIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                       &lt;element name="duration" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                       &lt;element name="durationUnits" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                       &lt;element name="enquiryDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *                                       &lt;element name="finalPaymentAmount" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                       &lt;element name="financeType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                       &lt;element name="initialDepositAmount" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                       &lt;element name="instalmentAmount" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
 *                                       &lt;element name="nbrOfApplicants" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *                                       &lt;element name="paymentFrequency" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                       &lt;element name="purposeOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                       &lt;element name="reason" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
 *                                       &lt;element name="streamID" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0"/>
 *                                       &lt;element name="subscriberName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="typeOfSecurity" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="PVS" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="status" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}status" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Summary">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="CAISDistribution1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAISDistribution2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAISDistribution3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAISDistribution4" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAISDistribution5" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAISDistribution5Plus" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAISRecordsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAISRecordsGuarantorRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAISRecordsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAISRecordsJointRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAISRecordsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAISRecordsOwnerRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAISRecordsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAISRecordsRefereeRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSDistribution1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSDistribution2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSDistribution3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSDistribution4" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSDistribution5" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSDistribution5Plus" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSLast12MonthsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSLast12MonthsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSLast12MonthsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSLast12MonthsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSLast3MonthsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSLast3MonthsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSLast3MonthsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSLast3MonthsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSLast6MonthsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSLast6MonthsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSLast6MonthsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSLast6MonthsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSRecordsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSRecordsGuarantorBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSRecordsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSRecordsJointBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSRecordsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSRecordsOwnerBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSRecordsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="CAPSRecordsRefereeBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="PotentialMonthlyInstalmentsAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="PotentialMonthlyInstalmentsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="PotentialOutstandingBalanceAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="PotentialOutstandingBalanceOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="TotalMonthlyInstalmentsAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="TotalMonthlyInstalmentsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="TotalOutstandingBalanceAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="TotalOutstandingBalanceOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                                       &lt;element name="WorstCurrentPayStatusGuarantor" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
 *                                       &lt;element name="WorstCurrentPayStatusJoint" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
 *                                       &lt;element name="WorstCurrentPayStatusOwner" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
 *                                       &lt;element name="WorstCurrentPayStatusReferee" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
 *                                       &lt;element name="WorstEverPayStatusGuarantor" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
 *                                       &lt;element name="WorstEverPayStatusJoint" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
 *                                       &lt;element name="WorstEverPayStatusOwner" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
 *                                       &lt;element name="WorstEverPayStatusReferee" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
 *                                       &lt;element name="checkOther1" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther10" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther11" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther12" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther13" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther14" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther15" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther16" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther17" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther18" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther19" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther2" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther20" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther3" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther4" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther5" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther6" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther7" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther8" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="checkOther9" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Warning" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="a" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
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
 *         &lt;element name="ValidationErrors" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="s" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="extra1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="extra2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="field" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                             &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *         &lt;element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *         &lt;element name="errorDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="responseDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
 *         &lt;element name="streamID" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0"/>
 *         &lt;element name="nativeCBMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="messageDigest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnquiryResponseERIBType", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "consumers",
    "validationErrors",
    "errorCode",
    "errorDescription",
    "responseDate",
    "streamID",
    "nativeCBMessage",
    "messageDigest"
})
@XmlRootElement(name = "enquiryResponseERIB")
public class EnquiryResponseERIB {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "Consumers", required = true)
    protected EnquiryResponseERIB.Consumers consumers;
    @XmlElement(name = "ValidationErrors")
    protected EnquiryResponseERIB.ValidationErrors validationErrors;
    @XmlSchemaType(name = "unsignedInt")
    protected Long errorCode;
    protected String errorDescription;
    protected String responseDate;
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger streamID;
    protected String nativeCBMessage;
    protected String messageDigest;

    /**
     * Gets the value of the rqUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqUID() {
        return rqUID;
    }

    /**
     * Sets the value of the rqUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqUID(String value) {
        this.rqUID = value;
    }

    /**
     * Gets the value of the rqTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getRqTm() {
        return rqTm;
    }

    /**
     * Sets the value of the rqTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqTm(Calendar value) {
        this.rqTm = value;
    }

    /**
     * Gets the value of the operUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperUID() {
        return operUID;
    }

    /**
     * Sets the value of the operUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperUID(String value) {
        this.operUID = value;
    }

    /**
     * Gets the value of the consumers property.
     * 
     * @return
     *     possible object is
     *     {@link EnquiryResponseERIB.Consumers }
     *     
     */
    public EnquiryResponseERIB.Consumers getConsumers() {
        return consumers;
    }

    /**
     * Sets the value of the consumers property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnquiryResponseERIB.Consumers }
     *     
     */
    public void setConsumers(EnquiryResponseERIB.Consumers value) {
        this.consumers = value;
    }

    /**
     * Gets the value of the validationErrors property.
     * 
     * @return
     *     possible object is
     *     {@link EnquiryResponseERIB.ValidationErrors }
     *     
     */
    public EnquiryResponseERIB.ValidationErrors getValidationErrors() {
        return validationErrors;
    }

    /**
     * Sets the value of the validationErrors property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnquiryResponseERIB.ValidationErrors }
     *     
     */
    public void setValidationErrors(EnquiryResponseERIB.ValidationErrors value) {
        this.validationErrors = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setErrorCode(Long value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the errorDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * Sets the value of the errorDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorDescription(String value) {
        this.errorDescription = value;
    }

    /**
     * Gets the value of the responseDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseDate() {
        return responseDate;
    }

    /**
     * Sets the value of the responseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseDate(String value) {
        this.responseDate = value;
    }

    /**
     * Gets the value of the streamID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStreamID() {
        return streamID;
    }

    /**
     * Sets the value of the streamID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStreamID(BigInteger value) {
        this.streamID = value;
    }

    /**
     * Gets the value of the nativeCBMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNativeCBMessage() {
        return nativeCBMessage;
    }

    /**
     * Sets the value of the nativeCBMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNativeCBMessage(String value) {
        this.nativeCBMessage = value;
    }

    /**
     * Gets the value of the messageDigest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageDigest() {
        return messageDigest;
    }

    /**
     * Sets the value of the messageDigest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageDigest(String value) {
        this.messageDigest = value;
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
     *         &lt;element name="s" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BureauScore" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="confidenceFlag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                             &lt;element name="scoreCardType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                             &lt;element name="scoreInterval" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                             &lt;element name="scoreNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="CAIS" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Consumer" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="Address" maxOccurs="unbounded">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="addressCurrPrev" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                                 &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                                 &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                                 &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="homeTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                                 &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                                 &lt;element name="type" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="Employer" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                                 &lt;element name="currentPreviousIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                                 &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                                 &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="idNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                                 &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                                 &lt;element name="telephoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="accountHolderType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                       &lt;element name="aliasName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="consentFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="dateConsentGiven" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="dateOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="mobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="name1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="name2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="name3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="nationality" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                       &lt;element name="pensionNbr" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                                       &lt;element name="placeOfBirth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="previousCompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="previousPassportNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="primaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="primaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="primaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="primaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="primaryIDPlaceOfIssue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="primaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                       &lt;element name="privateEntrepreneurEGRN" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                                       &lt;element name="privateEntrepreneurNbr" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                                       &lt;element name="privateEntrepreneurNbrIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="secondaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="secondaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="secondaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="secondaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="secondaryIDPlaceOfIssue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="secondaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                       &lt;element name="sex" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="title" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="MontlyHistory" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="accountBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                                       &lt;element name="accountPaymentHistory" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
     *                                       &lt;element name="arrearsBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                                       &lt;element name="creditLimit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                                       &lt;element name="historyDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="instalment" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="accountClass" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
     *                             &lt;element name="accountClosedDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                             &lt;element name="accountHolderComments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="accountPaymentStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                             &lt;element name="accountSpecialStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                             &lt;element name="amountInsuredLoan" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                             &lt;element name="amountOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                             &lt;element name="arrearsBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                             &lt;element name="consumerAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="creditFacilityStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                             &lt;element name="creditLimit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                             &lt;element name="currency">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;enumeration value="RUB"/>
     *                                   &lt;enumeration value="USD"/>
     *                                   &lt;enumeration value="EUR"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="dateAccountAdded" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                             &lt;element name="defaultDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                             &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="durationUnit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                             &lt;element name="financeType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                             &lt;element name="fulfilmentDueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                             &lt;element name="instalment" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                             &lt;element name="insuredLoan" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                             &lt;element name="interestRate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                             &lt;element name="lastMissedPaymentDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                             &lt;element name="lastPaymentDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                             &lt;element name="lastUpdateTS" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                             &lt;element name="litigationDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                             &lt;element name="monthOfLastUpdate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                             &lt;element name="numOfApplicants" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
     *                             &lt;element name="openDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                             &lt;element name="outstandingBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                             &lt;element name="paymentFrequency" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                             &lt;element name="purposeOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                             &lt;element name="reasonForClosure" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                             &lt;element name="recordBlockDisputeIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                             &lt;element name="subscriberComments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="subscriberName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="typeOfSecurity" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                             &lt;element name="worstPaymentStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
     *                             &lt;element name="writeOffDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="CAPS" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Consumer" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="Address" maxOccurs="unbounded">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="addressFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                                 &lt;element name="addressType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                                 &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                                 &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="homeTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                                 &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                                 &lt;element name="timeAtAddress" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="Employer" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                                 &lt;element name="currentPreviousIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                                 &lt;element name="employerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                                 &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="idNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                                 &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                                 &lt;element name="timeWithEmployer" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                                                 &lt;element name="workMobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="workTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="Verification" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="status" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}status" minOccurs="0"/>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="aliasName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="applicantType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                       &lt;element name="consentFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="dateConsentGiven" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="dateOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="drivingLicenceNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="education" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="maritalStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                       &lt;element name="medicalNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="mobileRegistration" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="mobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="name1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="name2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="nationality" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                       &lt;element name="numberDependants" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                                       &lt;element name="occupation" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                       &lt;element name="otherIncome" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                                       &lt;element name="pensionNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="placeOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="previousCompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="previousPassportNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="primaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="primaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="primaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="primaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="primaryIDIssuePlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="primaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                       &lt;element name="primaryIncome" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                                       &lt;element name="primaryIncomeFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="primaryIncomeFreq" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="privateEntrepreneurEGRN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="privateEntrepreneurNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="privateEntrepreneurNbrIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="residentialStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="secondaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="secondaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="secondaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="secondaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                                       &lt;element name="secondaryIDIssuePlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="secondaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                       &lt;element name="sex" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
     *                                       &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="title" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                       &lt;element name="occupationStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                                       &lt;element name="mastercardHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="retailHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="amExpressHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="chqGteeHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="debitHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="dinersHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="jcbHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="otherHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                       &lt;element name="visaHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="accountClass" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                             &lt;element name="amountOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                             &lt;element name="applicationDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                             &lt;element name="applicationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="creditLimit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                             &lt;element name="currency">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;enumeration value="RUB"/>
     *                                   &lt;enumeration value="USD"/>
     *                                   &lt;enumeration value="EUR"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="disputeIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                             &lt;element name="duration" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                             &lt;element name="durationUnits" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                             &lt;element name="enquiryDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
     *                             &lt;element name="finalPaymentAmount" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                             &lt;element name="financeType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                             &lt;element name="initialDepositAmount" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                             &lt;element name="instalmentAmount" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
     *                             &lt;element name="nbrOfApplicants" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
     *                             &lt;element name="paymentFrequency" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                             &lt;element name="purposeOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                             &lt;element name="reason" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
     *                             &lt;element name="streamID" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0"/>
     *                             &lt;element name="subscriberName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="typeOfSecurity" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="PVS" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="status" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}status" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Summary">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="CAISDistribution1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAISDistribution2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAISDistribution3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAISDistribution4" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAISDistribution5" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAISDistribution5Plus" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAISRecordsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAISRecordsGuarantorRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAISRecordsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAISRecordsJointRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAISRecordsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAISRecordsOwnerRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAISRecordsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAISRecordsRefereeRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSDistribution1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSDistribution2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSDistribution3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSDistribution4" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSDistribution5" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSDistribution5Plus" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSLast12MonthsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSLast12MonthsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSLast12MonthsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSLast12MonthsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSLast3MonthsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSLast3MonthsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSLast3MonthsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSLast3MonthsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSLast6MonthsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSLast6MonthsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSLast6MonthsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSLast6MonthsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSRecordsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSRecordsGuarantorBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSRecordsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSRecordsJointBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSRecordsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSRecordsOwnerBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSRecordsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="CAPSRecordsRefereeBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="PotentialMonthlyInstalmentsAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="PotentialMonthlyInstalmentsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="PotentialOutstandingBalanceAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="PotentialOutstandingBalanceOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="TotalMonthlyInstalmentsAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="TotalMonthlyInstalmentsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="TotalOutstandingBalanceAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="TotalOutstandingBalanceOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                             &lt;element name="WorstCurrentPayStatusGuarantor" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
     *                             &lt;element name="WorstCurrentPayStatusJoint" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
     *                             &lt;element name="WorstCurrentPayStatusOwner" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
     *                             &lt;element name="WorstCurrentPayStatusReferee" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
     *                             &lt;element name="WorstEverPayStatusGuarantor" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
     *                             &lt;element name="WorstEverPayStatusJoint" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
     *                             &lt;element name="WorstEverPayStatusOwner" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
     *                             &lt;element name="WorstEverPayStatusReferee" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
     *                             &lt;element name="checkOther1" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther10" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther11" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther12" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther13" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther14" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther15" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther16" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther17" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther18" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther19" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther2" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther20" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther3" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther4" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther5" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther6" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther7" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther8" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="checkOther9" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Warning" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="a" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
        "ss"
    })
    public static class Consumers {

        @XmlElement(name = "s", required = true)
        protected List<EnquiryResponseERIB.Consumers.S> ss;

        /**
         * Gets the value of the ss property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ss property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSS().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EnquiryResponseERIB.Consumers.S }
         * 
         * 
         */
        public List<EnquiryResponseERIB.Consumers.S> getSS() {
            if (ss == null) {
                ss = new ArrayList<EnquiryResponseERIB.Consumers.S>();
            }
            return this.ss;
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
         *         &lt;element name="BureauScore" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="confidenceFlag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *                   &lt;element name="scoreCardType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *                   &lt;element name="scoreInterval" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *                   &lt;element name="scoreNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="CAIS" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Consumer" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="Address" maxOccurs="unbounded">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="addressCurrPrev" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                                       &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                                       &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                                       &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="homeTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                                       &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                                       &lt;element name="type" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="Employer" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                                       &lt;element name="currentPreviousIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                                       &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                                       &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="idNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                                       &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                                       &lt;element name="telephoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="accountHolderType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                             &lt;element name="aliasName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="consentFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="dateConsentGiven" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="dateOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="mobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="name1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="name2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="name3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="nationality" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                             &lt;element name="pensionNbr" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                             &lt;element name="placeOfBirth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="previousCompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="previousPassportNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="primaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="primaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="primaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="primaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="primaryIDPlaceOfIssue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="primaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                             &lt;element name="privateEntrepreneurEGRN" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                             &lt;element name="privateEntrepreneurNbr" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                             &lt;element name="privateEntrepreneurNbrIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="secondaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="secondaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="secondaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="secondaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="secondaryIDPlaceOfIssue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="secondaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                             &lt;element name="sex" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="title" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="MontlyHistory" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="accountBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                             &lt;element name="accountPaymentHistory" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
         *                             &lt;element name="arrearsBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                             &lt;element name="creditLimit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                             &lt;element name="historyDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="instalment" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="accountClass" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
         *                   &lt;element name="accountClosedDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                   &lt;element name="accountHolderComments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="accountPaymentStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                   &lt;element name="accountSpecialStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                   &lt;element name="amountInsuredLoan" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                   &lt;element name="amountOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                   &lt;element name="arrearsBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                   &lt;element name="consumerAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="creditFacilityStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                   &lt;element name="creditLimit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                   &lt;element name="currency">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;enumeration value="RUB"/>
         *                         &lt;enumeration value="USD"/>
         *                         &lt;enumeration value="EUR"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="dateAccountAdded" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                   &lt;element name="defaultDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                   &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="durationUnit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                   &lt;element name="financeType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                   &lt;element name="fulfilmentDueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                   &lt;element name="instalment" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                   &lt;element name="insuredLoan" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                   &lt;element name="interestRate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                   &lt;element name="lastMissedPaymentDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                   &lt;element name="lastPaymentDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                   &lt;element name="lastUpdateTS" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                   &lt;element name="litigationDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                   &lt;element name="monthOfLastUpdate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                   &lt;element name="numOfApplicants" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
         *                   &lt;element name="openDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                   &lt;element name="outstandingBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                   &lt;element name="paymentFrequency" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                   &lt;element name="purposeOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                   &lt;element name="reasonForClosure" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                   &lt;element name="recordBlockDisputeIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                   &lt;element name="subscriberComments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="subscriberName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="typeOfSecurity" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                   &lt;element name="worstPaymentStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
         *                   &lt;element name="writeOffDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="CAPS" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Consumer" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="Address" maxOccurs="unbounded">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="addressFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                                       &lt;element name="addressType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                                       &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                                       &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="homeTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                                       &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                                       &lt;element name="timeAtAddress" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="Employer" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                                       &lt;element name="currentPreviousIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                                       &lt;element name="employerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                                       &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="idNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                                       &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                                       &lt;element name="timeWithEmployer" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                                       &lt;element name="workMobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="workTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="Verification" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="status" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}status" minOccurs="0"/>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="aliasName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="applicantType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                             &lt;element name="consentFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="dateConsentGiven" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="dateOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="drivingLicenceNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="education" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="maritalStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                             &lt;element name="medicalNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="mobileRegistration" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="mobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="name1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="name2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="nationality" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                             &lt;element name="numberDependants" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                             &lt;element name="occupation" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                             &lt;element name="otherIncome" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                             &lt;element name="pensionNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="placeOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="previousCompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="previousPassportNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="primaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="primaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="primaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="primaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="primaryIDIssuePlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="primaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                             &lt;element name="primaryIncome" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                             &lt;element name="primaryIncomeFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="primaryIncomeFreq" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="privateEntrepreneurEGRN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="privateEntrepreneurNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="privateEntrepreneurNbrIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="residentialStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="secondaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="secondaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="secondaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="secondaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                             &lt;element name="secondaryIDIssuePlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="secondaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                             &lt;element name="sex" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
         *                             &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="title" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                             &lt;element name="occupationStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                             &lt;element name="mastercardHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="retailHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="amExpressHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="chqGteeHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="debitHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="dinersHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="jcbHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="otherHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                             &lt;element name="visaHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="accountClass" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                   &lt;element name="amountOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                   &lt;element name="applicationDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                   &lt;element name="applicationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="creditLimit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                   &lt;element name="currency">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;enumeration value="RUB"/>
         *                         &lt;enumeration value="USD"/>
         *                         &lt;enumeration value="EUR"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="disputeIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                   &lt;element name="duration" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                   &lt;element name="durationUnits" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                   &lt;element name="enquiryDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
         *                   &lt;element name="finalPaymentAmount" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                   &lt;element name="financeType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                   &lt;element name="initialDepositAmount" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                   &lt;element name="instalmentAmount" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
         *                   &lt;element name="nbrOfApplicants" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
         *                   &lt;element name="paymentFrequency" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                   &lt;element name="purposeOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                   &lt;element name="reason" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
         *                   &lt;element name="streamID" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0"/>
         *                   &lt;element name="subscriberName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="typeOfSecurity" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="PVS" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="status" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}status" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Summary">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="CAISDistribution1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAISDistribution2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAISDistribution3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAISDistribution4" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAISDistribution5" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAISDistribution5Plus" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAISRecordsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAISRecordsGuarantorRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAISRecordsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAISRecordsJointRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAISRecordsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAISRecordsOwnerRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAISRecordsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAISRecordsRefereeRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSDistribution1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSDistribution2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSDistribution3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSDistribution4" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSDistribution5" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSDistribution5Plus" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSLast12MonthsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSLast12MonthsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSLast12MonthsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSLast12MonthsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSLast3MonthsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSLast3MonthsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSLast3MonthsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSLast3MonthsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSLast6MonthsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSLast6MonthsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSLast6MonthsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSLast6MonthsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSRecordsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSRecordsGuarantorBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSRecordsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSRecordsJointBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSRecordsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSRecordsOwnerBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSRecordsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="CAPSRecordsRefereeBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="PotentialMonthlyInstalmentsAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="PotentialMonthlyInstalmentsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="PotentialOutstandingBalanceAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="PotentialOutstandingBalanceOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="TotalMonthlyInstalmentsAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="TotalMonthlyInstalmentsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="TotalOutstandingBalanceAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="TotalOutstandingBalanceOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *                   &lt;element name="WorstCurrentPayStatusGuarantor" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
         *                   &lt;element name="WorstCurrentPayStatusJoint" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
         *                   &lt;element name="WorstCurrentPayStatusOwner" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
         *                   &lt;element name="WorstCurrentPayStatusReferee" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
         *                   &lt;element name="WorstEverPayStatusGuarantor" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
         *                   &lt;element name="WorstEverPayStatusJoint" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
         *                   &lt;element name="WorstEverPayStatusOwner" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
         *                   &lt;element name="WorstEverPayStatusReferee" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
         *                   &lt;element name="checkOther1" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther10" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther11" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther12" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther13" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther14" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther15" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther16" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther17" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther18" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther19" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther2" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther20" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther3" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther4" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther5" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther6" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther7" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther8" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="checkOther9" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Warning" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="a" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
            "bureauScores",
            "caises",
            "caps",
            "pvs",
            "summary",
            "warning"
        })
        public static class S {

            @XmlElement(name = "BureauScore")
            protected List<EnquiryResponseERIB.Consumers.S.BureauScore> bureauScores;
            @XmlElement(name = "CAIS")
            protected List<EnquiryResponseERIB.Consumers.S.CAIS> caises;
            @XmlElement(name = "CAPS")
            protected List<EnquiryResponseERIB.Consumers.S.CAPS> caps;
            @XmlElement(name = "PVS")
            protected EnquiryResponseERIB.Consumers.S.PVS pvs;
            @XmlElement(name = "Summary", required = true)
            protected EnquiryResponseERIB.Consumers.S.Summary summary;
            @XmlElement(name = "Warning")
            protected EnquiryResponseERIB.Consumers.S.Warning warning;

            /**
             * Gets the value of the bureauScores property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the bureauScores property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getBureauScores().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link EnquiryResponseERIB.Consumers.S.BureauScore }
             * 
             * 
             */
            public List<EnquiryResponseERIB.Consumers.S.BureauScore> getBureauScores() {
                if (bureauScores == null) {
                    bureauScores = new ArrayList<EnquiryResponseERIB.Consumers.S.BureauScore>();
                }
                return this.bureauScores;
            }

            /**
             * Gets the value of the caises property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the caises property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCAISES().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link EnquiryResponseERIB.Consumers.S.CAIS }
             * 
             * 
             */
            public List<EnquiryResponseERIB.Consumers.S.CAIS> getCAISES() {
                if (caises == null) {
                    caises = new ArrayList<EnquiryResponseERIB.Consumers.S.CAIS>();
                }
                return this.caises;
            }

            /**
             * Gets the value of the caps property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the caps property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCAPS().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link EnquiryResponseERIB.Consumers.S.CAPS }
             * 
             * 
             */
            public List<EnquiryResponseERIB.Consumers.S.CAPS> getCAPS() {
                if (caps == null) {
                    caps = new ArrayList<EnquiryResponseERIB.Consumers.S.CAPS>();
                }
                return this.caps;
            }

            /**
             * Gets the value of the pvs property.
             * 
             * @return
             *     possible object is
             *     {@link EnquiryResponseERIB.Consumers.S.PVS }
             *     
             */
            public EnquiryResponseERIB.Consumers.S.PVS getPVS() {
                return pvs;
            }

            /**
             * Sets the value of the pvs property.
             * 
             * @param value
             *     allowed object is
             *     {@link EnquiryResponseERIB.Consumers.S.PVS }
             *     
             */
            public void setPVS(EnquiryResponseERIB.Consumers.S.PVS value) {
                this.pvs = value;
            }

            /**
             * Gets the value of the summary property.
             * 
             * @return
             *     possible object is
             *     {@link EnquiryResponseERIB.Consumers.S.Summary }
             *     
             */
            public EnquiryResponseERIB.Consumers.S.Summary getSummary() {
                return summary;
            }

            /**
             * Sets the value of the summary property.
             * 
             * @param value
             *     allowed object is
             *     {@link EnquiryResponseERIB.Consumers.S.Summary }
             *     
             */
            public void setSummary(EnquiryResponseERIB.Consumers.S.Summary value) {
                this.summary = value;
            }

            /**
             * Gets the value of the warning property.
             * 
             * @return
             *     possible object is
             *     {@link EnquiryResponseERIB.Consumers.S.Warning }
             *     
             */
            public EnquiryResponseERIB.Consumers.S.Warning getWarning() {
                return warning;
            }

            /**
             * Sets the value of the warning property.
             * 
             * @param value
             *     allowed object is
             *     {@link EnquiryResponseERIB.Consumers.S.Warning }
             *     
             */
            public void setWarning(EnquiryResponseERIB.Consumers.S.Warning value) {
                this.warning = value;
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
             *         &lt;element name="confidenceFlag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
             *         &lt;element name="scoreCardType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
             *         &lt;element name="scoreInterval" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
             *         &lt;element name="scoreNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
                "confidenceFlag",
                "scoreCardType",
                "scoreInterval",
                "scoreNumber"
            })
            public static class BureauScore {

                protected Integer confidenceFlag;
                protected Integer scoreCardType;
                protected Integer scoreInterval;
                protected Integer scoreNumber;

                /**
                 * Gets the value of the confidenceFlag property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getConfidenceFlag() {
                    return confidenceFlag;
                }

                /**
                 * Sets the value of the confidenceFlag property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setConfidenceFlag(Integer value) {
                    this.confidenceFlag = value;
                }

                /**
                 * Gets the value of the scoreCardType property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getScoreCardType() {
                    return scoreCardType;
                }

                /**
                 * Sets the value of the scoreCardType property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setScoreCardType(Integer value) {
                    this.scoreCardType = value;
                }

                /**
                 * Gets the value of the scoreInterval property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getScoreInterval() {
                    return scoreInterval;
                }

                /**
                 * Sets the value of the scoreInterval property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setScoreInterval(Integer value) {
                    this.scoreInterval = value;
                }

                /**
                 * Gets the value of the scoreNumber property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getScoreNumber() {
                    return scoreNumber;
                }

                /**
                 * Sets the value of the scoreNumber property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setScoreNumber(Integer value) {
                    this.scoreNumber = value;
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
             *         &lt;element name="Consumer" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="Address" maxOccurs="unbounded">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="addressCurrPrev" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                             &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                             &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                             &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="homeTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                             &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                             &lt;element name="type" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="Employer" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                             &lt;element name="currentPreviousIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                             &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                             &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="idNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                             &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                             &lt;element name="telephoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="accountHolderType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                   &lt;element name="aliasName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="consentFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="dateConsentGiven" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="dateOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="mobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="name1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="name2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="name3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="nationality" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                   &lt;element name="pensionNbr" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *                   &lt;element name="placeOfBirth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="previousCompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="previousPassportNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="primaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="primaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="primaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="primaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="primaryIDPlaceOfIssue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="primaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                   &lt;element name="privateEntrepreneurEGRN" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *                   &lt;element name="privateEntrepreneurNbr" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *                   &lt;element name="privateEntrepreneurNbrIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="secondaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="secondaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="secondaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="secondaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="secondaryIDPlaceOfIssue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="secondaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                   &lt;element name="sex" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="title" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="MontlyHistory" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="accountBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *                   &lt;element name="accountPaymentHistory" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
             *                   &lt;element name="arrearsBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *                   &lt;element name="creditLimit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *                   &lt;element name="historyDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="instalment" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="accountClass" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
             *         &lt;element name="accountClosedDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *         &lt;element name="accountHolderComments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="accountPaymentStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *         &lt;element name="accountSpecialStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *         &lt;element name="amountInsuredLoan" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *         &lt;element name="amountOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *         &lt;element name="arrearsBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *         &lt;element name="consumerAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="creditFacilityStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *         &lt;element name="creditLimit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *         &lt;element name="currency">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;enumeration value="RUB"/>
             *               &lt;enumeration value="USD"/>
             *               &lt;enumeration value="EUR"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="dateAccountAdded" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *         &lt;element name="defaultDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="durationUnit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *         &lt;element name="financeType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *         &lt;element name="fulfilmentDueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *         &lt;element name="instalment" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *         &lt;element name="insuredLoan" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *         &lt;element name="interestRate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *         &lt;element name="lastMissedPaymentDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *         &lt;element name="lastPaymentDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *         &lt;element name="lastUpdateTS" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *         &lt;element name="litigationDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *         &lt;element name="monthOfLastUpdate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *         &lt;element name="numOfApplicants" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
             *         &lt;element name="openDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *         &lt;element name="outstandingBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *         &lt;element name="paymentFrequency" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *         &lt;element name="purposeOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *         &lt;element name="reasonForClosure" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *         &lt;element name="recordBlockDisputeIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *         &lt;element name="subscriberComments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="subscriberName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="typeOfSecurity" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *         &lt;element name="worstPaymentStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
             *         &lt;element name="writeOffDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
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
                "consumers",
                "montlyHistories",
                "accountClass",
                "accountClosedDate",
                "accountHolderComments",
                "accountPaymentStatus",
                "accountSpecialStatus",
                "amountInsuredLoan",
                "amountOfFinance",
                "arrearsBalance",
                "consumerAccountNumber",
                "creditFacilityStatus",
                "creditLimit",
                "currency",
                "dateAccountAdded",
                "defaultDate",
                "duration",
                "durationUnit",
                "financeType",
                "fulfilmentDueDate",
                "instalment",
                "insuredLoan",
                "interestRate",
                "lastMissedPaymentDate",
                "lastPaymentDate",
                "lastUpdateTS",
                "litigationDate",
                "monthOfLastUpdate",
                "numOfApplicants",
                "openDate",
                "outstandingBalance",
                "paymentFrequency",
                "purposeOfFinance",
                "reasonForClosure",
                "recordBlockDisputeIndicator",
                "subscriberComments",
                "subscriberName",
                "typeOfSecurity",
                "worstPaymentStatus",
                "writeOffDate"
            })
            public static class CAIS {

                @XmlElement(name = "Consumer", required = true)
                protected List<EnquiryResponseERIB.Consumers.S.CAIS.Consumer> consumers;
                @XmlElement(name = "MontlyHistory")
                protected List<EnquiryResponseERIB.Consumers.S.CAIS.MontlyHistory> montlyHistories;
                @XmlSchemaType(name = "unsignedByte")
                protected Short accountClass;
                protected String accountClosedDate;
                protected String accountHolderComments;
                protected String accountPaymentStatus;
                protected String accountSpecialStatus;
                protected String amountInsuredLoan;
                protected String amountOfFinance;
                protected String arrearsBalance;
                protected String consumerAccountNumber;
                protected String creditFacilityStatus;
                protected String creditLimit;
                @XmlElement(required = true)
                protected String currency;
                protected String dateAccountAdded;
                protected String defaultDate;
                @XmlSchemaType(name = "unsignedInt")
                protected Long duration;
                protected String durationUnit;
                protected String financeType;
                protected String fulfilmentDueDate;
                protected String instalment;
                protected String insuredLoan;
                protected String interestRate;
                protected String lastMissedPaymentDate;
                protected String lastPaymentDate;
                protected String lastUpdateTS;
                protected String litigationDate;
                protected String monthOfLastUpdate;
                @XmlSchemaType(name = "positiveInteger")
                protected BigInteger numOfApplicants;
                protected String openDate;
                protected String outstandingBalance;
                protected String paymentFrequency;
                protected String purposeOfFinance;
                protected String reasonForClosure;
                protected String recordBlockDisputeIndicator;
                protected String subscriberComments;
                protected String subscriberName;
                protected String typeOfSecurity;
                protected String worstPaymentStatus;
                protected String writeOffDate;

                /**
                 * Gets the value of the consumers property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the consumers property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getConsumers().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link EnquiryResponseERIB.Consumers.S.CAIS.Consumer }
                 * 
                 * 
                 */
                public List<EnquiryResponseERIB.Consumers.S.CAIS.Consumer> getConsumers() {
                    if (consumers == null) {
                        consumers = new ArrayList<EnquiryResponseERIB.Consumers.S.CAIS.Consumer>();
                    }
                    return this.consumers;
                }

                /**
                 * Gets the value of the montlyHistories property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the montlyHistories property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getMontlyHistories().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link EnquiryResponseERIB.Consumers.S.CAIS.MontlyHistory }
                 * 
                 * 
                 */
                public List<EnquiryResponseERIB.Consumers.S.CAIS.MontlyHistory> getMontlyHistories() {
                    if (montlyHistories == null) {
                        montlyHistories = new ArrayList<EnquiryResponseERIB.Consumers.S.CAIS.MontlyHistory>();
                    }
                    return this.montlyHistories;
                }

                /**
                 * Gets the value of the accountClass property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Short }
                 *     
                 */
                public Short getAccountClass() {
                    return accountClass;
                }

                /**
                 * Sets the value of the accountClass property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Short }
                 *     
                 */
                public void setAccountClass(Short value) {
                    this.accountClass = value;
                }

                /**
                 * Gets the value of the accountClosedDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAccountClosedDate() {
                    return accountClosedDate;
                }

                /**
                 * Sets the value of the accountClosedDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAccountClosedDate(String value) {
                    this.accountClosedDate = value;
                }

                /**
                 * Gets the value of the accountHolderComments property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAccountHolderComments() {
                    return accountHolderComments;
                }

                /**
                 * Sets the value of the accountHolderComments property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAccountHolderComments(String value) {
                    this.accountHolderComments = value;
                }

                /**
                 * Gets the value of the accountPaymentStatus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAccountPaymentStatus() {
                    return accountPaymentStatus;
                }

                /**
                 * Sets the value of the accountPaymentStatus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAccountPaymentStatus(String value) {
                    this.accountPaymentStatus = value;
                }

                /**
                 * Gets the value of the accountSpecialStatus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAccountSpecialStatus() {
                    return accountSpecialStatus;
                }

                /**
                 * Sets the value of the accountSpecialStatus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAccountSpecialStatus(String value) {
                    this.accountSpecialStatus = value;
                }

                /**
                 * Gets the value of the amountInsuredLoan property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAmountInsuredLoan() {
                    return amountInsuredLoan;
                }

                /**
                 * Sets the value of the amountInsuredLoan property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAmountInsuredLoan(String value) {
                    this.amountInsuredLoan = value;
                }

                /**
                 * Gets the value of the amountOfFinance property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAmountOfFinance() {
                    return amountOfFinance;
                }

                /**
                 * Sets the value of the amountOfFinance property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAmountOfFinance(String value) {
                    this.amountOfFinance = value;
                }

                /**
                 * Gets the value of the arrearsBalance property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getArrearsBalance() {
                    return arrearsBalance;
                }

                /**
                 * Sets the value of the arrearsBalance property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setArrearsBalance(String value) {
                    this.arrearsBalance = value;
                }

                /**
                 * Gets the value of the consumerAccountNumber property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getConsumerAccountNumber() {
                    return consumerAccountNumber;
                }

                /**
                 * Sets the value of the consumerAccountNumber property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setConsumerAccountNumber(String value) {
                    this.consumerAccountNumber = value;
                }

                /**
                 * Gets the value of the creditFacilityStatus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCreditFacilityStatus() {
                    return creditFacilityStatus;
                }

                /**
                 * Sets the value of the creditFacilityStatus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCreditFacilityStatus(String value) {
                    this.creditFacilityStatus = value;
                }

                /**
                 * Gets the value of the creditLimit property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCreditLimit() {
                    return creditLimit;
                }

                /**
                 * Sets the value of the creditLimit property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCreditLimit(String value) {
                    this.creditLimit = value;
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
                 * Gets the value of the dateAccountAdded property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDateAccountAdded() {
                    return dateAccountAdded;
                }

                /**
                 * Sets the value of the dateAccountAdded property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDateAccountAdded(String value) {
                    this.dateAccountAdded = value;
                }

                /**
                 * Gets the value of the defaultDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDefaultDate() {
                    return defaultDate;
                }

                /**
                 * Sets the value of the defaultDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDefaultDate(String value) {
                    this.defaultDate = value;
                }

                /**
                 * Gets the value of the duration property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getDuration() {
                    return duration;
                }

                /**
                 * Sets the value of the duration property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setDuration(Long value) {
                    this.duration = value;
                }

                /**
                 * Gets the value of the durationUnit property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDurationUnit() {
                    return durationUnit;
                }

                /**
                 * Sets the value of the durationUnit property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDurationUnit(String value) {
                    this.durationUnit = value;
                }

                /**
                 * Gets the value of the financeType property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getFinanceType() {
                    return financeType;
                }

                /**
                 * Sets the value of the financeType property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setFinanceType(String value) {
                    this.financeType = value;
                }

                /**
                 * Gets the value of the fulfilmentDueDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getFulfilmentDueDate() {
                    return fulfilmentDueDate;
                }

                /**
                 * Sets the value of the fulfilmentDueDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setFulfilmentDueDate(String value) {
                    this.fulfilmentDueDate = value;
                }

                /**
                 * Gets the value of the instalment property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getInstalment() {
                    return instalment;
                }

                /**
                 * Sets the value of the instalment property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setInstalment(String value) {
                    this.instalment = value;
                }

                /**
                 * Gets the value of the insuredLoan property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getInsuredLoan() {
                    return insuredLoan;
                }

                /**
                 * Sets the value of the insuredLoan property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setInsuredLoan(String value) {
                    this.insuredLoan = value;
                }

                /**
                 * Gets the value of the interestRate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getInterestRate() {
                    return interestRate;
                }

                /**
                 * Sets the value of the interestRate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setInterestRate(String value) {
                    this.interestRate = value;
                }

                /**
                 * Gets the value of the lastMissedPaymentDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLastMissedPaymentDate() {
                    return lastMissedPaymentDate;
                }

                /**
                 * Sets the value of the lastMissedPaymentDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLastMissedPaymentDate(String value) {
                    this.lastMissedPaymentDate = value;
                }

                /**
                 * Gets the value of the lastPaymentDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLastPaymentDate() {
                    return lastPaymentDate;
                }

                /**
                 * Sets the value of the lastPaymentDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLastPaymentDate(String value) {
                    this.lastPaymentDate = value;
                }

                /**
                 * Gets the value of the lastUpdateTS property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLastUpdateTS() {
                    return lastUpdateTS;
                }

                /**
                 * Sets the value of the lastUpdateTS property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLastUpdateTS(String value) {
                    this.lastUpdateTS = value;
                }

                /**
                 * Gets the value of the litigationDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLitigationDate() {
                    return litigationDate;
                }

                /**
                 * Sets the value of the litigationDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLitigationDate(String value) {
                    this.litigationDate = value;
                }

                /**
                 * Gets the value of the monthOfLastUpdate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getMonthOfLastUpdate() {
                    return monthOfLastUpdate;
                }

                /**
                 * Sets the value of the monthOfLastUpdate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setMonthOfLastUpdate(String value) {
                    this.monthOfLastUpdate = value;
                }

                /**
                 * Gets the value of the numOfApplicants property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getNumOfApplicants() {
                    return numOfApplicants;
                }

                /**
                 * Sets the value of the numOfApplicants property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setNumOfApplicants(BigInteger value) {
                    this.numOfApplicants = value;
                }

                /**
                 * Gets the value of the openDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOpenDate() {
                    return openDate;
                }

                /**
                 * Sets the value of the openDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOpenDate(String value) {
                    this.openDate = value;
                }

                /**
                 * Gets the value of the outstandingBalance property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOutstandingBalance() {
                    return outstandingBalance;
                }

                /**
                 * Sets the value of the outstandingBalance property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOutstandingBalance(String value) {
                    this.outstandingBalance = value;
                }

                /**
                 * Gets the value of the paymentFrequency property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPaymentFrequency() {
                    return paymentFrequency;
                }

                /**
                 * Sets the value of the paymentFrequency property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPaymentFrequency(String value) {
                    this.paymentFrequency = value;
                }

                /**
                 * Gets the value of the purposeOfFinance property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPurposeOfFinance() {
                    return purposeOfFinance;
                }

                /**
                 * Sets the value of the purposeOfFinance property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPurposeOfFinance(String value) {
                    this.purposeOfFinance = value;
                }

                /**
                 * Gets the value of the reasonForClosure property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getReasonForClosure() {
                    return reasonForClosure;
                }

                /**
                 * Sets the value of the reasonForClosure property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setReasonForClosure(String value) {
                    this.reasonForClosure = value;
                }

                /**
                 * Gets the value of the recordBlockDisputeIndicator property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getRecordBlockDisputeIndicator() {
                    return recordBlockDisputeIndicator;
                }

                /**
                 * Sets the value of the recordBlockDisputeIndicator property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setRecordBlockDisputeIndicator(String value) {
                    this.recordBlockDisputeIndicator = value;
                }

                /**
                 * Gets the value of the subscriberComments property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSubscriberComments() {
                    return subscriberComments;
                }

                /**
                 * Sets the value of the subscriberComments property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSubscriberComments(String value) {
                    this.subscriberComments = value;
                }

                /**
                 * Gets the value of the subscriberName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSubscriberName() {
                    return subscriberName;
                }

                /**
                 * Sets the value of the subscriberName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSubscriberName(String value) {
                    this.subscriberName = value;
                }

                /**
                 * Gets the value of the typeOfSecurity property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTypeOfSecurity() {
                    return typeOfSecurity;
                }

                /**
                 * Sets the value of the typeOfSecurity property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTypeOfSecurity(String value) {
                    this.typeOfSecurity = value;
                }

                /**
                 * Gets the value of the worstPaymentStatus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getWorstPaymentStatus() {
                    return worstPaymentStatus;
                }

                /**
                 * Sets the value of the worstPaymentStatus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setWorstPaymentStatus(String value) {
                    this.worstPaymentStatus = value;
                }

                /**
                 * Gets the value of the writeOffDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getWriteOffDate() {
                    return writeOffDate;
                }

                /**
                 * Sets the value of the writeOffDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setWriteOffDate(String value) {
                    this.writeOffDate = value;
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
                 *         &lt;element name="Address" maxOccurs="unbounded">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="addressCurrPrev" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *                   &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *                   &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *                   &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="homeTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *                   &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *                   &lt;element name="type" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="Employer" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *                   &lt;element name="currentPreviousIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *                   &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *                   &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="idNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *                   &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *                   &lt;element name="telephoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="accountHolderType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *         &lt;element name="aliasName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="consentFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="dateConsentGiven" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="dateOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="mobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="name1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="name2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="name3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="nationality" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *         &lt;element name="pensionNbr" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
                 *         &lt;element name="placeOfBirth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="previousCompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="previousPassportNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="primaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="primaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="primaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="primaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="primaryIDPlaceOfIssue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="primaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *         &lt;element name="privateEntrepreneurEGRN" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
                 *         &lt;element name="privateEntrepreneurNbr" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
                 *         &lt;element name="privateEntrepreneurNbrIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="secondaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="secondaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="secondaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="secondaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="secondaryIDPlaceOfIssue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="secondaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *         &lt;element name="sex" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="title" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
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
                    "addresses",
                    "employer",
                    "accountHolderType",
                    "aliasName",
                    "consentFlag",
                    "dateConsentGiven",
                    "dateOfBirth",
                    "mobileTelNbr",
                    "name1",
                    "name2",
                    "name3",
                    "nationality",
                    "pensionNbr",
                    "placeOfBirth",
                    "previousCompanyName",
                    "previousPassportNbr",
                    "primaryID",
                    "primaryIDAuthority",
                    "primaryIDExpiry",
                    "primaryIDIssueDate",
                    "primaryIDPlaceOfIssue",
                    "primaryIDType",
                    "privateEntrepreneurEGRN",
                    "privateEntrepreneurNbr",
                    "privateEntrepreneurNbrIssueDate",
                    "secondaryID",
                    "secondaryIDAuthority",
                    "secondaryIDExpiry",
                    "secondaryIDIssueDate",
                    "secondaryIDPlaceOfIssue",
                    "secondaryIDType",
                    "sex",
                    "surname",
                    "title"
                })
                public static class Consumer {

                    @XmlElement(name = "Address", required = true)
                    protected List<EnquiryResponseERIB.Consumers.S.CAIS.Consumer.Address> addresses;
                    @XmlElement(name = "Employer")
                    protected EnquiryResponseERIB.Consumers.S.CAIS.Consumer.Employer employer;
                    protected String accountHolderType;
                    protected String aliasName;
                    protected String consentFlag;
                    protected String dateConsentGiven;
                    protected String dateOfBirth;
                    protected String mobileTelNbr;
                    protected String name1;
                    protected String name2;
                    protected String name3;
                    protected String nationality;
                    protected String pensionNbr;
                    protected String placeOfBirth;
                    protected String previousCompanyName;
                    protected String previousPassportNbr;
                    protected String primaryID;
                    protected String primaryIDAuthority;
                    protected String primaryIDExpiry;
                    protected String primaryIDIssueDate;
                    protected String primaryIDPlaceOfIssue;
                    protected String primaryIDType;
                    protected String privateEntrepreneurEGRN;
                    protected String privateEntrepreneurNbr;
                    protected String privateEntrepreneurNbrIssueDate;
                    protected String secondaryID;
                    protected String secondaryIDAuthority;
                    protected String secondaryIDExpiry;
                    protected String secondaryIDIssueDate;
                    protected String secondaryIDPlaceOfIssue;
                    protected String secondaryIDType;
                    protected String sex;
                    protected String surname;
                    protected String title;

                    /**
                     * Gets the value of the addresses property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the addresses property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getAddresses().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link EnquiryResponseERIB.Consumers.S.CAIS.Consumer.Address }
                     * 
                     * 
                     */
                    public List<EnquiryResponseERIB.Consumers.S.CAIS.Consumer.Address> getAddresses() {
                        if (addresses == null) {
                            addresses = new ArrayList<EnquiryResponseERIB.Consumers.S.CAIS.Consumer.Address>();
                        }
                        return this.addresses;
                    }

                    /**
                     * Gets the value of the employer property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link EnquiryResponseERIB.Consumers.S.CAIS.Consumer.Employer }
                     *     
                     */
                    public EnquiryResponseERIB.Consumers.S.CAIS.Consumer.Employer getEmployer() {
                        return employer;
                    }

                    /**
                     * Sets the value of the employer property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link EnquiryResponseERIB.Consumers.S.CAIS.Consumer.Employer }
                     *     
                     */
                    public void setEmployer(EnquiryResponseERIB.Consumers.S.CAIS.Consumer.Employer value) {
                        this.employer = value;
                    }

                    /**
                     * Gets the value of the accountHolderType property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getAccountHolderType() {
                        return accountHolderType;
                    }

                    /**
                     * Sets the value of the accountHolderType property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setAccountHolderType(String value) {
                        this.accountHolderType = value;
                    }

                    /**
                     * Gets the value of the aliasName property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getAliasName() {
                        return aliasName;
                    }

                    /**
                     * Sets the value of the aliasName property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setAliasName(String value) {
                        this.aliasName = value;
                    }

                    /**
                     * Gets the value of the consentFlag property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getConsentFlag() {
                        return consentFlag;
                    }

                    /**
                     * Sets the value of the consentFlag property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setConsentFlag(String value) {
                        this.consentFlag = value;
                    }

                    /**
                     * Gets the value of the dateConsentGiven property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDateConsentGiven() {
                        return dateConsentGiven;
                    }

                    /**
                     * Sets the value of the dateConsentGiven property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDateConsentGiven(String value) {
                        this.dateConsentGiven = value;
                    }

                    /**
                     * Gets the value of the dateOfBirth property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDateOfBirth() {
                        return dateOfBirth;
                    }

                    /**
                     * Sets the value of the dateOfBirth property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDateOfBirth(String value) {
                        this.dateOfBirth = value;
                    }

                    /**
                     * Gets the value of the mobileTelNbr property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getMobileTelNbr() {
                        return mobileTelNbr;
                    }

                    /**
                     * Sets the value of the mobileTelNbr property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setMobileTelNbr(String value) {
                        this.mobileTelNbr = value;
                    }

                    /**
                     * Gets the value of the name1 property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getName1() {
                        return name1;
                    }

                    /**
                     * Sets the value of the name1 property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setName1(String value) {
                        this.name1 = value;
                    }

                    /**
                     * Gets the value of the name2 property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getName2() {
                        return name2;
                    }

                    /**
                     * Sets the value of the name2 property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setName2(String value) {
                        this.name2 = value;
                    }

                    /**
                     * Gets the value of the name3 property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getName3() {
                        return name3;
                    }

                    /**
                     * Sets the value of the name3 property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setName3(String value) {
                        this.name3 = value;
                    }

                    /**
                     * Gets the value of the nationality property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNationality() {
                        return nationality;
                    }

                    /**
                     * Sets the value of the nationality property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNationality(String value) {
                        this.nationality = value;
                    }

                    /**
                     * Gets the value of the pensionNbr property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPensionNbr() {
                        return pensionNbr;
                    }

                    /**
                     * Sets the value of the pensionNbr property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPensionNbr(String value) {
                        this.pensionNbr = value;
                    }

                    /**
                     * Gets the value of the placeOfBirth property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPlaceOfBirth() {
                        return placeOfBirth;
                    }

                    /**
                     * Sets the value of the placeOfBirth property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPlaceOfBirth(String value) {
                        this.placeOfBirth = value;
                    }

                    /**
                     * Gets the value of the previousCompanyName property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPreviousCompanyName() {
                        return previousCompanyName;
                    }

                    /**
                     * Sets the value of the previousCompanyName property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPreviousCompanyName(String value) {
                        this.previousCompanyName = value;
                    }

                    /**
                     * Gets the value of the previousPassportNbr property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPreviousPassportNbr() {
                        return previousPassportNbr;
                    }

                    /**
                     * Sets the value of the previousPassportNbr property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPreviousPassportNbr(String value) {
                        this.previousPassportNbr = value;
                    }

                    /**
                     * Gets the value of the primaryID property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrimaryID() {
                        return primaryID;
                    }

                    /**
                     * Sets the value of the primaryID property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrimaryID(String value) {
                        this.primaryID = value;
                    }

                    /**
                     * Gets the value of the primaryIDAuthority property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrimaryIDAuthority() {
                        return primaryIDAuthority;
                    }

                    /**
                     * Sets the value of the primaryIDAuthority property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrimaryIDAuthority(String value) {
                        this.primaryIDAuthority = value;
                    }

                    /**
                     * Gets the value of the primaryIDExpiry property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrimaryIDExpiry() {
                        return primaryIDExpiry;
                    }

                    /**
                     * Sets the value of the primaryIDExpiry property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrimaryIDExpiry(String value) {
                        this.primaryIDExpiry = value;
                    }

                    /**
                     * Gets the value of the primaryIDIssueDate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrimaryIDIssueDate() {
                        return primaryIDIssueDate;
                    }

                    /**
                     * Sets the value of the primaryIDIssueDate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrimaryIDIssueDate(String value) {
                        this.primaryIDIssueDate = value;
                    }

                    /**
                     * Gets the value of the primaryIDPlaceOfIssue property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrimaryIDPlaceOfIssue() {
                        return primaryIDPlaceOfIssue;
                    }

                    /**
                     * Sets the value of the primaryIDPlaceOfIssue property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrimaryIDPlaceOfIssue(String value) {
                        this.primaryIDPlaceOfIssue = value;
                    }

                    /**
                     * Gets the value of the primaryIDType property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrimaryIDType() {
                        return primaryIDType;
                    }

                    /**
                     * Sets the value of the primaryIDType property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrimaryIDType(String value) {
                        this.primaryIDType = value;
                    }

                    /**
                     * Gets the value of the privateEntrepreneurEGRN property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrivateEntrepreneurEGRN() {
                        return privateEntrepreneurEGRN;
                    }

                    /**
                     * Sets the value of the privateEntrepreneurEGRN property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrivateEntrepreneurEGRN(String value) {
                        this.privateEntrepreneurEGRN = value;
                    }

                    /**
                     * Gets the value of the privateEntrepreneurNbr property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrivateEntrepreneurNbr() {
                        return privateEntrepreneurNbr;
                    }

                    /**
                     * Sets the value of the privateEntrepreneurNbr property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrivateEntrepreneurNbr(String value) {
                        this.privateEntrepreneurNbr = value;
                    }

                    /**
                     * Gets the value of the privateEntrepreneurNbrIssueDate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrivateEntrepreneurNbrIssueDate() {
                        return privateEntrepreneurNbrIssueDate;
                    }

                    /**
                     * Sets the value of the privateEntrepreneurNbrIssueDate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrivateEntrepreneurNbrIssueDate(String value) {
                        this.privateEntrepreneurNbrIssueDate = value;
                    }

                    /**
                     * Gets the value of the secondaryID property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSecondaryID() {
                        return secondaryID;
                    }

                    /**
                     * Sets the value of the secondaryID property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSecondaryID(String value) {
                        this.secondaryID = value;
                    }

                    /**
                     * Gets the value of the secondaryIDAuthority property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSecondaryIDAuthority() {
                        return secondaryIDAuthority;
                    }

                    /**
                     * Sets the value of the secondaryIDAuthority property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSecondaryIDAuthority(String value) {
                        this.secondaryIDAuthority = value;
                    }

                    /**
                     * Gets the value of the secondaryIDExpiry property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSecondaryIDExpiry() {
                        return secondaryIDExpiry;
                    }

                    /**
                     * Sets the value of the secondaryIDExpiry property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSecondaryIDExpiry(String value) {
                        this.secondaryIDExpiry = value;
                    }

                    /**
                     * Gets the value of the secondaryIDIssueDate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSecondaryIDIssueDate() {
                        return secondaryIDIssueDate;
                    }

                    /**
                     * Sets the value of the secondaryIDIssueDate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSecondaryIDIssueDate(String value) {
                        this.secondaryIDIssueDate = value;
                    }

                    /**
                     * Gets the value of the secondaryIDPlaceOfIssue property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSecondaryIDPlaceOfIssue() {
                        return secondaryIDPlaceOfIssue;
                    }

                    /**
                     * Sets the value of the secondaryIDPlaceOfIssue property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSecondaryIDPlaceOfIssue(String value) {
                        this.secondaryIDPlaceOfIssue = value;
                    }

                    /**
                     * Gets the value of the secondaryIDType property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSecondaryIDType() {
                        return secondaryIDType;
                    }

                    /**
                     * Sets the value of the secondaryIDType property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSecondaryIDType(String value) {
                        this.secondaryIDType = value;
                    }

                    /**
                     * Gets the value of the sex property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSex() {
                        return sex;
                    }

                    /**
                     * Sets the value of the sex property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSex(String value) {
                        this.sex = value;
                    }

                    /**
                     * Gets the value of the surname property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSurname() {
                        return surname;
                    }

                    /**
                     * Sets the value of the surname property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSurname(String value) {
                        this.surname = value;
                    }

                    /**
                     * Gets the value of the title property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getTitle() {
                        return title;
                    }

                    /**
                     * Sets the value of the title property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setTitle(String value) {
                        this.title = value;
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
                     *         &lt;element name="addressCurrPrev" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                     *         &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                     *         &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                     *         &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="homeTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                     *         &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                     *         &lt;element name="type" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
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
                        "addressCurrPrev",
                        "buildingNbr",
                        "country",
                        "endDate",
                        "flatNbr",
                        "homeTelNbr",
                        "houseNbr",
                        "line1",
                        "line2",
                        "line3",
                        "line4",
                        "postcode",
                        "regionCode",
                        "startDate",
                        "type"
                    })
                    public static class Address {

                        protected String addressCurrPrev;
                        protected String buildingNbr;
                        protected String country;
                        protected String endDate;
                        protected String flatNbr;
                        protected String homeTelNbr;
                        protected String houseNbr;
                        protected String line1;
                        protected String line2;
                        protected String line3;
                        protected String line4;
                        protected String postcode;
                        protected String regionCode;
                        protected String startDate;
                        protected String type;

                        /**
                         * Gets the value of the addressCurrPrev property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getAddressCurrPrev() {
                            return addressCurrPrev;
                        }

                        /**
                         * Sets the value of the addressCurrPrev property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setAddressCurrPrev(String value) {
                            this.addressCurrPrev = value;
                        }

                        /**
                         * Gets the value of the buildingNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getBuildingNbr() {
                            return buildingNbr;
                        }

                        /**
                         * Sets the value of the buildingNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setBuildingNbr(String value) {
                            this.buildingNbr = value;
                        }

                        /**
                         * Gets the value of the country property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getCountry() {
                            return country;
                        }

                        /**
                         * Sets the value of the country property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setCountry(String value) {
                            this.country = value;
                        }

                        /**
                         * Gets the value of the endDate property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getEndDate() {
                            return endDate;
                        }

                        /**
                         * Sets the value of the endDate property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setEndDate(String value) {
                            this.endDate = value;
                        }

                        /**
                         * Gets the value of the flatNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getFlatNbr() {
                            return flatNbr;
                        }

                        /**
                         * Sets the value of the flatNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setFlatNbr(String value) {
                            this.flatNbr = value;
                        }

                        /**
                         * Gets the value of the homeTelNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getHomeTelNbr() {
                            return homeTelNbr;
                        }

                        /**
                         * Sets the value of the homeTelNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setHomeTelNbr(String value) {
                            this.homeTelNbr = value;
                        }

                        /**
                         * Gets the value of the houseNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getHouseNbr() {
                            return houseNbr;
                        }

                        /**
                         * Sets the value of the houseNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setHouseNbr(String value) {
                            this.houseNbr = value;
                        }

                        /**
                         * Gets the value of the line1 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine1() {
                            return line1;
                        }

                        /**
                         * Sets the value of the line1 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine1(String value) {
                            this.line1 = value;
                        }

                        /**
                         * Gets the value of the line2 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine2() {
                            return line2;
                        }

                        /**
                         * Sets the value of the line2 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine2(String value) {
                            this.line2 = value;
                        }

                        /**
                         * Gets the value of the line3 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine3() {
                            return line3;
                        }

                        /**
                         * Sets the value of the line3 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine3(String value) {
                            this.line3 = value;
                        }

                        /**
                         * Gets the value of the line4 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine4() {
                            return line4;
                        }

                        /**
                         * Sets the value of the line4 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine4(String value) {
                            this.line4 = value;
                        }

                        /**
                         * Gets the value of the postcode property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getPostcode() {
                            return postcode;
                        }

                        /**
                         * Sets the value of the postcode property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setPostcode(String value) {
                            this.postcode = value;
                        }

                        /**
                         * Gets the value of the regionCode property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getRegionCode() {
                            return regionCode;
                        }

                        /**
                         * Sets the value of the regionCode property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setRegionCode(String value) {
                            this.regionCode = value;
                        }

                        /**
                         * Gets the value of the startDate property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getStartDate() {
                            return startDate;
                        }

                        /**
                         * Sets the value of the startDate property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setStartDate(String value) {
                            this.startDate = value;
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
                     *         &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                     *         &lt;element name="currentPreviousIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                     *         &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                     *         &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="idNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                     *         &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                     *         &lt;element name="telephoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                        "buildingNbr",
                        "country",
                        "currentPreviousIndicator",
                        "endDate",
                        "flatNbr",
                        "houseNbr",
                        "idNumber",
                        "line1",
                        "line2",
                        "line3",
                        "line4",
                        "name",
                        "postcode",
                        "regionCode",
                        "startDate",
                        "telephoneNumber"
                    })
                    public static class Employer {

                        protected String buildingNbr;
                        protected String country;
                        protected String currentPreviousIndicator;
                        protected String endDate;
                        protected String flatNbr;
                        protected String houseNbr;
                        protected String idNumber;
                        protected String line1;
                        protected String line2;
                        protected String line3;
                        protected String line4;
                        protected String name;
                        protected String postcode;
                        protected String regionCode;
                        protected String startDate;
                        protected String telephoneNumber;

                        /**
                         * Gets the value of the buildingNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getBuildingNbr() {
                            return buildingNbr;
                        }

                        /**
                         * Sets the value of the buildingNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setBuildingNbr(String value) {
                            this.buildingNbr = value;
                        }

                        /**
                         * Gets the value of the country property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getCountry() {
                            return country;
                        }

                        /**
                         * Sets the value of the country property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setCountry(String value) {
                            this.country = value;
                        }

                        /**
                         * Gets the value of the currentPreviousIndicator property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getCurrentPreviousIndicator() {
                            return currentPreviousIndicator;
                        }

                        /**
                         * Sets the value of the currentPreviousIndicator property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setCurrentPreviousIndicator(String value) {
                            this.currentPreviousIndicator = value;
                        }

                        /**
                         * Gets the value of the endDate property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getEndDate() {
                            return endDate;
                        }

                        /**
                         * Sets the value of the endDate property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setEndDate(String value) {
                            this.endDate = value;
                        }

                        /**
                         * Gets the value of the flatNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getFlatNbr() {
                            return flatNbr;
                        }

                        /**
                         * Sets the value of the flatNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setFlatNbr(String value) {
                            this.flatNbr = value;
                        }

                        /**
                         * Gets the value of the houseNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getHouseNbr() {
                            return houseNbr;
                        }

                        /**
                         * Sets the value of the houseNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setHouseNbr(String value) {
                            this.houseNbr = value;
                        }

                        /**
                         * Gets the value of the idNumber property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getIdNumber() {
                            return idNumber;
                        }

                        /**
                         * Sets the value of the idNumber property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setIdNumber(String value) {
                            this.idNumber = value;
                        }

                        /**
                         * Gets the value of the line1 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine1() {
                            return line1;
                        }

                        /**
                         * Sets the value of the line1 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine1(String value) {
                            this.line1 = value;
                        }

                        /**
                         * Gets the value of the line2 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine2() {
                            return line2;
                        }

                        /**
                         * Sets the value of the line2 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine2(String value) {
                            this.line2 = value;
                        }

                        /**
                         * Gets the value of the line3 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine3() {
                            return line3;
                        }

                        /**
                         * Sets the value of the line3 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine3(String value) {
                            this.line3 = value;
                        }

                        /**
                         * Gets the value of the line4 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine4() {
                            return line4;
                        }

                        /**
                         * Sets the value of the line4 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine4(String value) {
                            this.line4 = value;
                        }

                        /**
                         * Gets the value of the name property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getName() {
                            return name;
                        }

                        /**
                         * Sets the value of the name property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setName(String value) {
                            this.name = value;
                        }

                        /**
                         * Gets the value of the postcode property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getPostcode() {
                            return postcode;
                        }

                        /**
                         * Sets the value of the postcode property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setPostcode(String value) {
                            this.postcode = value;
                        }

                        /**
                         * Gets the value of the regionCode property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getRegionCode() {
                            return regionCode;
                        }

                        /**
                         * Sets the value of the regionCode property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setRegionCode(String value) {
                            this.regionCode = value;
                        }

                        /**
                         * Gets the value of the startDate property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getStartDate() {
                            return startDate;
                        }

                        /**
                         * Sets the value of the startDate property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setStartDate(String value) {
                            this.startDate = value;
                        }

                        /**
                         * Gets the value of the telephoneNumber property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getTelephoneNumber() {
                            return telephoneNumber;
                        }

                        /**
                         * Sets the value of the telephoneNumber property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setTelephoneNumber(String value) {
                            this.telephoneNumber = value;
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
                 *         &lt;element name="accountBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
                 *         &lt;element name="accountPaymentHistory" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
                 *         &lt;element name="arrearsBalance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
                 *         &lt;element name="creditLimit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
                 *         &lt;element name="historyDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="instalment" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
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
                    "accountBalance",
                    "accountPaymentHistory",
                    "arrearsBalance",
                    "creditLimit",
                    "historyDate",
                    "instalment"
                })
                public static class MontlyHistory {

                    protected String accountBalance;
                    protected String accountPaymentHistory;
                    protected String arrearsBalance;
                    protected String creditLimit;
                    protected String historyDate;
                    protected String instalment;

                    /**
                     * Gets the value of the accountBalance property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getAccountBalance() {
                        return accountBalance;
                    }

                    /**
                     * Sets the value of the accountBalance property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setAccountBalance(String value) {
                        this.accountBalance = value;
                    }

                    /**
                     * Gets the value of the accountPaymentHistory property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getAccountPaymentHistory() {
                        return accountPaymentHistory;
                    }

                    /**
                     * Sets the value of the accountPaymentHistory property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setAccountPaymentHistory(String value) {
                        this.accountPaymentHistory = value;
                    }

                    /**
                     * Gets the value of the arrearsBalance property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getArrearsBalance() {
                        return arrearsBalance;
                    }

                    /**
                     * Sets the value of the arrearsBalance property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setArrearsBalance(String value) {
                        this.arrearsBalance = value;
                    }

                    /**
                     * Gets the value of the creditLimit property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCreditLimit() {
                        return creditLimit;
                    }

                    /**
                     * Sets the value of the creditLimit property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCreditLimit(String value) {
                        this.creditLimit = value;
                    }

                    /**
                     * Gets the value of the historyDate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getHistoryDate() {
                        return historyDate;
                    }

                    /**
                     * Sets the value of the historyDate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setHistoryDate(String value) {
                        this.historyDate = value;
                    }

                    /**
                     * Gets the value of the instalment property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getInstalment() {
                        return instalment;
                    }

                    /**
                     * Sets the value of the instalment property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setInstalment(String value) {
                        this.instalment = value;
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
             *         &lt;element name="Consumer" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="Address" maxOccurs="unbounded">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="addressFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                             &lt;element name="addressType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                             &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                             &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="homeTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                             &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                             &lt;element name="timeAtAddress" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="Employer" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                             &lt;element name="currentPreviousIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                             &lt;element name="employerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                             &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="idNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                             &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                             &lt;element name="timeWithEmployer" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *                             &lt;element name="workMobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="workTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="Verification" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="status" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}status" minOccurs="0"/>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="aliasName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="applicantType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                   &lt;element name="consentFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="dateConsentGiven" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="dateOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="drivingLicenceNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="education" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="maritalStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                   &lt;element name="medicalNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="mobileRegistration" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="mobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="name1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="name2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="nationality" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                   &lt;element name="numberDependants" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *                   &lt;element name="occupation" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                   &lt;element name="otherIncome" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *                   &lt;element name="pensionNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="placeOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="previousCompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="previousPassportNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="primaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="primaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="primaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="primaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="primaryIDIssuePlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="primaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                   &lt;element name="primaryIncome" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *                   &lt;element name="primaryIncomeFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="primaryIncomeFreq" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="privateEntrepreneurEGRN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="privateEntrepreneurNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="privateEntrepreneurNbrIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="residentialStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="secondaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="secondaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="secondaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="secondaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *                   &lt;element name="secondaryIDIssuePlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="secondaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                   &lt;element name="sex" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
             *                   &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="title" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                   &lt;element name="occupationStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *                   &lt;element name="mastercardHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="retailHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="amExpressHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="chqGteeHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="debitHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="dinersHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="jcbHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="otherHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                   &lt;element name="visaHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="accountClass" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *         &lt;element name="amountOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *         &lt;element name="applicationDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *         &lt;element name="applicationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="creditLimit" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *         &lt;element name="currency">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;enumeration value="RUB"/>
             *               &lt;enumeration value="USD"/>
             *               &lt;enumeration value="EUR"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="disputeIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
             *         &lt;element name="duration" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *         &lt;element name="durationUnits" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *         &lt;element name="enquiryDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
             *         &lt;element name="finalPaymentAmount" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *         &lt;element name="financeType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *         &lt;element name="initialDepositAmount" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *         &lt;element name="instalmentAmount" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
             *         &lt;element name="nbrOfApplicants" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
             *         &lt;element name="paymentFrequency" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *         &lt;element name="purposeOfFinance" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *         &lt;element name="reason" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
             *         &lt;element name="streamID" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0"/>
             *         &lt;element name="subscriberName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="typeOfSecurity" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
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
                "consumers",
                "accountClass",
                "amountOfFinance",
                "applicationDate",
                "applicationNumber",
                "comments",
                "creditLimit",
                "currency",
                "disputeIndicator",
                "duration",
                "durationUnits",
                "enquiryDate",
                "finalPaymentAmount",
                "financeType",
                "initialDepositAmount",
                "instalmentAmount",
                "nbrOfApplicants",
                "paymentFrequency",
                "purposeOfFinance",
                "reason",
                "streamID",
                "subscriberName",
                "typeOfSecurity"
            })
            public static class CAPS {

                @XmlElement(name = "Consumer", required = true)
                protected List<EnquiryResponseERIB.Consumers.S.CAPS.Consumer> consumers;
                protected String accountClass;
                protected String amountOfFinance;
                protected String applicationDate;
                protected String applicationNumber;
                protected String comments;
                protected String creditLimit;
                @XmlElement(required = true)
                protected String currency;
                protected String disputeIndicator;
                protected String duration;
                protected String durationUnits;
                protected String enquiryDate;
                protected String finalPaymentAmount;
                protected String financeType;
                protected String initialDepositAmount;
                protected String instalmentAmount;
                @XmlSchemaType(name = "positiveInteger")
                protected BigInteger nbrOfApplicants;
                protected String paymentFrequency;
                protected String purposeOfFinance;
                protected String reason;
                @XmlSchemaType(name = "unsignedLong")
                protected BigInteger streamID;
                protected String subscriberName;
                protected String typeOfSecurity;

                /**
                 * Gets the value of the consumers property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the consumers property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getConsumers().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link EnquiryResponseERIB.Consumers.S.CAPS.Consumer }
                 * 
                 * 
                 */
                public List<EnquiryResponseERIB.Consumers.S.CAPS.Consumer> getConsumers() {
                    if (consumers == null) {
                        consumers = new ArrayList<EnquiryResponseERIB.Consumers.S.CAPS.Consumer>();
                    }
                    return this.consumers;
                }

                /**
                 * Gets the value of the accountClass property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAccountClass() {
                    return accountClass;
                }

                /**
                 * Sets the value of the accountClass property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAccountClass(String value) {
                    this.accountClass = value;
                }

                /**
                 * Gets the value of the amountOfFinance property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAmountOfFinance() {
                    return amountOfFinance;
                }

                /**
                 * Sets the value of the amountOfFinance property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAmountOfFinance(String value) {
                    this.amountOfFinance = value;
                }

                /**
                 * Gets the value of the applicationDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getApplicationDate() {
                    return applicationDate;
                }

                /**
                 * Sets the value of the applicationDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setApplicationDate(String value) {
                    this.applicationDate = value;
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
                 * Gets the value of the comments property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getComments() {
                    return comments;
                }

                /**
                 * Sets the value of the comments property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setComments(String value) {
                    this.comments = value;
                }

                /**
                 * Gets the value of the creditLimit property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCreditLimit() {
                    return creditLimit;
                }

                /**
                 * Sets the value of the creditLimit property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCreditLimit(String value) {
                    this.creditLimit = value;
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
                 * Gets the value of the disputeIndicator property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDisputeIndicator() {
                    return disputeIndicator;
                }

                /**
                 * Sets the value of the disputeIndicator property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDisputeIndicator(String value) {
                    this.disputeIndicator = value;
                }

                /**
                 * Gets the value of the duration property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDuration() {
                    return duration;
                }

                /**
                 * Sets the value of the duration property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDuration(String value) {
                    this.duration = value;
                }

                /**
                 * Gets the value of the durationUnits property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDurationUnits() {
                    return durationUnits;
                }

                /**
                 * Sets the value of the durationUnits property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDurationUnits(String value) {
                    this.durationUnits = value;
                }

                /**
                 * Gets the value of the enquiryDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getEnquiryDate() {
                    return enquiryDate;
                }

                /**
                 * Sets the value of the enquiryDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setEnquiryDate(String value) {
                    this.enquiryDate = value;
                }

                /**
                 * Gets the value of the finalPaymentAmount property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getFinalPaymentAmount() {
                    return finalPaymentAmount;
                }

                /**
                 * Sets the value of the finalPaymentAmount property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setFinalPaymentAmount(String value) {
                    this.finalPaymentAmount = value;
                }

                /**
                 * Gets the value of the financeType property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getFinanceType() {
                    return financeType;
                }

                /**
                 * Sets the value of the financeType property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setFinanceType(String value) {
                    this.financeType = value;
                }

                /**
                 * Gets the value of the initialDepositAmount property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getInitialDepositAmount() {
                    return initialDepositAmount;
                }

                /**
                 * Sets the value of the initialDepositAmount property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setInitialDepositAmount(String value) {
                    this.initialDepositAmount = value;
                }

                /**
                 * Gets the value of the instalmentAmount property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getInstalmentAmount() {
                    return instalmentAmount;
                }

                /**
                 * Sets the value of the instalmentAmount property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setInstalmentAmount(String value) {
                    this.instalmentAmount = value;
                }

                /**
                 * Gets the value of the nbrOfApplicants property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getNbrOfApplicants() {
                    return nbrOfApplicants;
                }

                /**
                 * Sets the value of the nbrOfApplicants property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setNbrOfApplicants(BigInteger value) {
                    this.nbrOfApplicants = value;
                }

                /**
                 * Gets the value of the paymentFrequency property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPaymentFrequency() {
                    return paymentFrequency;
                }

                /**
                 * Sets the value of the paymentFrequency property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPaymentFrequency(String value) {
                    this.paymentFrequency = value;
                }

                /**
                 * Gets the value of the purposeOfFinance property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPurposeOfFinance() {
                    return purposeOfFinance;
                }

                /**
                 * Sets the value of the purposeOfFinance property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPurposeOfFinance(String value) {
                    this.purposeOfFinance = value;
                }

                /**
                 * Gets the value of the reason property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getReason() {
                    return reason;
                }

                /**
                 * Sets the value of the reason property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setReason(String value) {
                    this.reason = value;
                }

                /**
                 * Gets the value of the streamID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getStreamID() {
                    return streamID;
                }

                /**
                 * Sets the value of the streamID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setStreamID(BigInteger value) {
                    this.streamID = value;
                }

                /**
                 * Gets the value of the subscriberName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSubscriberName() {
                    return subscriberName;
                }

                /**
                 * Sets the value of the subscriberName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSubscriberName(String value) {
                    this.subscriberName = value;
                }

                /**
                 * Gets the value of the typeOfSecurity property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTypeOfSecurity() {
                    return typeOfSecurity;
                }

                /**
                 * Sets the value of the typeOfSecurity property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTypeOfSecurity(String value) {
                    this.typeOfSecurity = value;
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
                 *         &lt;element name="Address" maxOccurs="unbounded">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="addressFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *                   &lt;element name="addressType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *                   &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *                   &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="homeTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *                   &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *                   &lt;element name="timeAtAddress" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="Employer" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *                   &lt;element name="currentPreviousIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *                   &lt;element name="employerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *                   &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="idNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *                   &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *                   &lt;element name="timeWithEmployer" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
                 *                   &lt;element name="workMobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="workTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="Verification" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="status" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}status" minOccurs="0"/>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="aliasName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="applicantType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *         &lt;element name="consentFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="dateConsentGiven" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="dateOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="drivingLicenceNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="education" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="maritalStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *         &lt;element name="medicalNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="mobileRegistration" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="mobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="name1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="name2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="nationality" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *         &lt;element name="numberDependants" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
                 *         &lt;element name="occupation" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *         &lt;element name="otherIncome" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
                 *         &lt;element name="pensionNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="placeOfBirth" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="previousCompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="previousPassportNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="primaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="primaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="primaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="primaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="primaryIDIssuePlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="primaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *         &lt;element name="primaryIncome" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
                 *         &lt;element name="primaryIncomeFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="primaryIncomeFreq" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="privateEntrepreneurEGRN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="privateEntrepreneurNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="privateEntrepreneurNbrIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="residentialStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="secondaryID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="secondaryIDAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="secondaryIDExpiry" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="secondaryIDIssueDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                 *         &lt;element name="secondaryIDIssuePlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="secondaryIDType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *         &lt;element name="sex" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
                 *         &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="title" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *         &lt;element name="occupationStatus" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                 *         &lt;element name="mastercardHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="retailHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="amExpressHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="chqGteeHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="debitHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="dinersHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="jcbHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="otherHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                 *         &lt;element name="visaHeld" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
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
                    "addresses",
                    "employer",
                    "verification",
                    "aliasName",
                    "applicantType",
                    "consentFlag",
                    "dateConsentGiven",
                    "dateOfBirth",
                    "drivingLicenceNbr",
                    "education",
                    "maritalStatus",
                    "medicalNbr",
                    "mobileRegistration",
                    "mobileTelNbr",
                    "name1",
                    "name2",
                    "nationality",
                    "numberDependants",
                    "occupation",
                    "otherIncome",
                    "pensionNbr",
                    "placeOfBirth",
                    "previousCompanyName",
                    "previousPassportNbr",
                    "primaryID",
                    "primaryIDAuthority",
                    "primaryIDExpiry",
                    "primaryIDIssueDate",
                    "primaryIDIssuePlace",
                    "primaryIDType",
                    "primaryIncome",
                    "primaryIncomeFlag",
                    "primaryIncomeFreq",
                    "privateEntrepreneurEGRN",
                    "privateEntrepreneurNbr",
                    "privateEntrepreneurNbrIssueDate",
                    "residentialStatus",
                    "secondaryID",
                    "secondaryIDAuthority",
                    "secondaryIDExpiry",
                    "secondaryIDIssueDate",
                    "secondaryIDIssuePlace",
                    "secondaryIDType",
                    "sex",
                    "surname",
                    "title",
                    "occupationStatus",
                    "mastercardHeld",
                    "retailHeld",
                    "amExpressHeld",
                    "chqGteeHeld",
                    "debitHeld",
                    "dinersHeld",
                    "jcbHeld",
                    "otherHeld",
                    "visaHeld"
                })
                public static class Consumer {

                    @XmlElement(name = "Address", required = true)
                    protected List<EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Address> addresses;
                    @XmlElement(name = "Employer")
                    protected EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Employer employer;
                    @XmlElement(name = "Verification")
                    protected EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Verification verification;
                    protected String aliasName;
                    protected String applicantType;
                    protected String consentFlag;
                    protected String dateConsentGiven;
                    protected String dateOfBirth;
                    protected String drivingLicenceNbr;
                    protected String education;
                    protected String maritalStatus;
                    protected String medicalNbr;
                    protected String mobileRegistration;
                    protected String mobileTelNbr;
                    protected String name1;
                    protected String name2;
                    protected String nationality;
                    protected String numberDependants;
                    protected String occupation;
                    protected String otherIncome;
                    protected String pensionNbr;
                    protected String placeOfBirth;
                    protected String previousCompanyName;
                    protected String previousPassportNbr;
                    protected String primaryID;
                    protected String primaryIDAuthority;
                    protected String primaryIDExpiry;
                    protected String primaryIDIssueDate;
                    protected String primaryIDIssuePlace;
                    protected String primaryIDType;
                    protected String primaryIncome;
                    protected String primaryIncomeFlag;
                    protected String primaryIncomeFreq;
                    protected String privateEntrepreneurEGRN;
                    protected String privateEntrepreneurNbr;
                    protected String privateEntrepreneurNbrIssueDate;
                    protected String residentialStatus;
                    protected String secondaryID;
                    protected String secondaryIDAuthority;
                    protected String secondaryIDExpiry;
                    protected String secondaryIDIssueDate;
                    protected String secondaryIDIssuePlace;
                    protected String secondaryIDType;
                    @XmlSchemaType(name = "unsignedByte")
                    protected Short sex;
                    protected String surname;
                    protected String title;
                    protected String occupationStatus;
                    protected String mastercardHeld;
                    protected String retailHeld;
                    protected String amExpressHeld;
                    protected String chqGteeHeld;
                    protected String debitHeld;
                    protected String dinersHeld;
                    protected String jcbHeld;
                    protected String otherHeld;
                    protected String visaHeld;

                    /**
                     * Gets the value of the addresses property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the addresses property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getAddresses().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Address }
                     * 
                     * 
                     */
                    public List<EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Address> getAddresses() {
                        if (addresses == null) {
                            addresses = new ArrayList<EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Address>();
                        }
                        return this.addresses;
                    }

                    /**
                     * Gets the value of the employer property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Employer }
                     *     
                     */
                    public EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Employer getEmployer() {
                        return employer;
                    }

                    /**
                     * Sets the value of the employer property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Employer }
                     *     
                     */
                    public void setEmployer(EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Employer value) {
                        this.employer = value;
                    }

                    /**
                     * Gets the value of the verification property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Verification }
                     *     
                     */
                    public EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Verification getVerification() {
                        return verification;
                    }

                    /**
                     * Sets the value of the verification property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Verification }
                     *     
                     */
                    public void setVerification(EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Verification value) {
                        this.verification = value;
                    }

                    /**
                     * Gets the value of the aliasName property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getAliasName() {
                        return aliasName;
                    }

                    /**
                     * Sets the value of the aliasName property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setAliasName(String value) {
                        this.aliasName = value;
                    }

                    /**
                     * Gets the value of the applicantType property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getApplicantType() {
                        return applicantType;
                    }

                    /**
                     * Sets the value of the applicantType property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setApplicantType(String value) {
                        this.applicantType = value;
                    }

                    /**
                     * Gets the value of the consentFlag property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getConsentFlag() {
                        return consentFlag;
                    }

                    /**
                     * Sets the value of the consentFlag property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setConsentFlag(String value) {
                        this.consentFlag = value;
                    }

                    /**
                     * Gets the value of the dateConsentGiven property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDateConsentGiven() {
                        return dateConsentGiven;
                    }

                    /**
                     * Sets the value of the dateConsentGiven property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDateConsentGiven(String value) {
                        this.dateConsentGiven = value;
                    }

                    /**
                     * Gets the value of the dateOfBirth property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDateOfBirth() {
                        return dateOfBirth;
                    }

                    /**
                     * Sets the value of the dateOfBirth property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDateOfBirth(String value) {
                        this.dateOfBirth = value;
                    }

                    /**
                     * Gets the value of the drivingLicenceNbr property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDrivingLicenceNbr() {
                        return drivingLicenceNbr;
                    }

                    /**
                     * Sets the value of the drivingLicenceNbr property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDrivingLicenceNbr(String value) {
                        this.drivingLicenceNbr = value;
                    }

                    /**
                     * Gets the value of the education property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getEducation() {
                        return education;
                    }

                    /**
                     * Sets the value of the education property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setEducation(String value) {
                        this.education = value;
                    }

                    /**
                     * Gets the value of the maritalStatus property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getMaritalStatus() {
                        return maritalStatus;
                    }

                    /**
                     * Sets the value of the maritalStatus property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setMaritalStatus(String value) {
                        this.maritalStatus = value;
                    }

                    /**
                     * Gets the value of the medicalNbr property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getMedicalNbr() {
                        return medicalNbr;
                    }

                    /**
                     * Sets the value of the medicalNbr property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setMedicalNbr(String value) {
                        this.medicalNbr = value;
                    }

                    /**
                     * Gets the value of the mobileRegistration property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getMobileRegistration() {
                        return mobileRegistration;
                    }

                    /**
                     * Sets the value of the mobileRegistration property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setMobileRegistration(String value) {
                        this.mobileRegistration = value;
                    }

                    /**
                     * Gets the value of the mobileTelNbr property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getMobileTelNbr() {
                        return mobileTelNbr;
                    }

                    /**
                     * Sets the value of the mobileTelNbr property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setMobileTelNbr(String value) {
                        this.mobileTelNbr = value;
                    }

                    /**
                     * Gets the value of the name1 property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getName1() {
                        return name1;
                    }

                    /**
                     * Sets the value of the name1 property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setName1(String value) {
                        this.name1 = value;
                    }

                    /**
                     * Gets the value of the name2 property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getName2() {
                        return name2;
                    }

                    /**
                     * Sets the value of the name2 property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setName2(String value) {
                        this.name2 = value;
                    }

                    /**
                     * Gets the value of the nationality property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNationality() {
                        return nationality;
                    }

                    /**
                     * Sets the value of the nationality property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNationality(String value) {
                        this.nationality = value;
                    }

                    /**
                     * Gets the value of the numberDependants property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNumberDependants() {
                        return numberDependants;
                    }

                    /**
                     * Sets the value of the numberDependants property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNumberDependants(String value) {
                        this.numberDependants = value;
                    }

                    /**
                     * Gets the value of the occupation property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getOccupation() {
                        return occupation;
                    }

                    /**
                     * Sets the value of the occupation property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setOccupation(String value) {
                        this.occupation = value;
                    }

                    /**
                     * Gets the value of the otherIncome property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getOtherIncome() {
                        return otherIncome;
                    }

                    /**
                     * Sets the value of the otherIncome property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setOtherIncome(String value) {
                        this.otherIncome = value;
                    }

                    /**
                     * Gets the value of the pensionNbr property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPensionNbr() {
                        return pensionNbr;
                    }

                    /**
                     * Sets the value of the pensionNbr property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPensionNbr(String value) {
                        this.pensionNbr = value;
                    }

                    /**
                     * Gets the value of the placeOfBirth property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPlaceOfBirth() {
                        return placeOfBirth;
                    }

                    /**
                     * Sets the value of the placeOfBirth property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPlaceOfBirth(String value) {
                        this.placeOfBirth = value;
                    }

                    /**
                     * Gets the value of the previousCompanyName property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPreviousCompanyName() {
                        return previousCompanyName;
                    }

                    /**
                     * Sets the value of the previousCompanyName property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPreviousCompanyName(String value) {
                        this.previousCompanyName = value;
                    }

                    /**
                     * Gets the value of the previousPassportNbr property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPreviousPassportNbr() {
                        return previousPassportNbr;
                    }

                    /**
                     * Sets the value of the previousPassportNbr property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPreviousPassportNbr(String value) {
                        this.previousPassportNbr = value;
                    }

                    /**
                     * Gets the value of the primaryID property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrimaryID() {
                        return primaryID;
                    }

                    /**
                     * Sets the value of the primaryID property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrimaryID(String value) {
                        this.primaryID = value;
                    }

                    /**
                     * Gets the value of the primaryIDAuthority property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrimaryIDAuthority() {
                        return primaryIDAuthority;
                    }

                    /**
                     * Sets the value of the primaryIDAuthority property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrimaryIDAuthority(String value) {
                        this.primaryIDAuthority = value;
                    }

                    /**
                     * Gets the value of the primaryIDExpiry property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrimaryIDExpiry() {
                        return primaryIDExpiry;
                    }

                    /**
                     * Sets the value of the primaryIDExpiry property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrimaryIDExpiry(String value) {
                        this.primaryIDExpiry = value;
                    }

                    /**
                     * Gets the value of the primaryIDIssueDate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrimaryIDIssueDate() {
                        return primaryIDIssueDate;
                    }

                    /**
                     * Sets the value of the primaryIDIssueDate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrimaryIDIssueDate(String value) {
                        this.primaryIDIssueDate = value;
                    }

                    /**
                     * Gets the value of the primaryIDIssuePlace property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrimaryIDIssuePlace() {
                        return primaryIDIssuePlace;
                    }

                    /**
                     * Sets the value of the primaryIDIssuePlace property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrimaryIDIssuePlace(String value) {
                        this.primaryIDIssuePlace = value;
                    }

                    /**
                     * Gets the value of the primaryIDType property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrimaryIDType() {
                        return primaryIDType;
                    }

                    /**
                     * Sets the value of the primaryIDType property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrimaryIDType(String value) {
                        this.primaryIDType = value;
                    }

                    /**
                     * Gets the value of the primaryIncome property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrimaryIncome() {
                        return primaryIncome;
                    }

                    /**
                     * Sets the value of the primaryIncome property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrimaryIncome(String value) {
                        this.primaryIncome = value;
                    }

                    /**
                     * Gets the value of the primaryIncomeFlag property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrimaryIncomeFlag() {
                        return primaryIncomeFlag;
                    }

                    /**
                     * Sets the value of the primaryIncomeFlag property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrimaryIncomeFlag(String value) {
                        this.primaryIncomeFlag = value;
                    }

                    /**
                     * Gets the value of the primaryIncomeFreq property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrimaryIncomeFreq() {
                        return primaryIncomeFreq;
                    }

                    /**
                     * Sets the value of the primaryIncomeFreq property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrimaryIncomeFreq(String value) {
                        this.primaryIncomeFreq = value;
                    }

                    /**
                     * Gets the value of the privateEntrepreneurEGRN property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrivateEntrepreneurEGRN() {
                        return privateEntrepreneurEGRN;
                    }

                    /**
                     * Sets the value of the privateEntrepreneurEGRN property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrivateEntrepreneurEGRN(String value) {
                        this.privateEntrepreneurEGRN = value;
                    }

                    /**
                     * Gets the value of the privateEntrepreneurNbr property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrivateEntrepreneurNbr() {
                        return privateEntrepreneurNbr;
                    }

                    /**
                     * Sets the value of the privateEntrepreneurNbr property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrivateEntrepreneurNbr(String value) {
                        this.privateEntrepreneurNbr = value;
                    }

                    /**
                     * Gets the value of the privateEntrepreneurNbrIssueDate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPrivateEntrepreneurNbrIssueDate() {
                        return privateEntrepreneurNbrIssueDate;
                    }

                    /**
                     * Sets the value of the privateEntrepreneurNbrIssueDate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPrivateEntrepreneurNbrIssueDate(String value) {
                        this.privateEntrepreneurNbrIssueDate = value;
                    }

                    /**
                     * Gets the value of the residentialStatus property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getResidentialStatus() {
                        return residentialStatus;
                    }

                    /**
                     * Sets the value of the residentialStatus property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setResidentialStatus(String value) {
                        this.residentialStatus = value;
                    }

                    /**
                     * Gets the value of the secondaryID property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSecondaryID() {
                        return secondaryID;
                    }

                    /**
                     * Sets the value of the secondaryID property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSecondaryID(String value) {
                        this.secondaryID = value;
                    }

                    /**
                     * Gets the value of the secondaryIDAuthority property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSecondaryIDAuthority() {
                        return secondaryIDAuthority;
                    }

                    /**
                     * Sets the value of the secondaryIDAuthority property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSecondaryIDAuthority(String value) {
                        this.secondaryIDAuthority = value;
                    }

                    /**
                     * Gets the value of the secondaryIDExpiry property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSecondaryIDExpiry() {
                        return secondaryIDExpiry;
                    }

                    /**
                     * Sets the value of the secondaryIDExpiry property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSecondaryIDExpiry(String value) {
                        this.secondaryIDExpiry = value;
                    }

                    /**
                     * Gets the value of the secondaryIDIssueDate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSecondaryIDIssueDate() {
                        return secondaryIDIssueDate;
                    }

                    /**
                     * Sets the value of the secondaryIDIssueDate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSecondaryIDIssueDate(String value) {
                        this.secondaryIDIssueDate = value;
                    }

                    /**
                     * Gets the value of the secondaryIDIssuePlace property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSecondaryIDIssuePlace() {
                        return secondaryIDIssuePlace;
                    }

                    /**
                     * Sets the value of the secondaryIDIssuePlace property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSecondaryIDIssuePlace(String value) {
                        this.secondaryIDIssuePlace = value;
                    }

                    /**
                     * Gets the value of the secondaryIDType property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSecondaryIDType() {
                        return secondaryIDType;
                    }

                    /**
                     * Sets the value of the secondaryIDType property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSecondaryIDType(String value) {
                        this.secondaryIDType = value;
                    }

                    /**
                     * Gets the value of the sex property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Short }
                     *     
                     */
                    public Short getSex() {
                        return sex;
                    }

                    /**
                     * Sets the value of the sex property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Short }
                     *     
                     */
                    public void setSex(Short value) {
                        this.sex = value;
                    }

                    /**
                     * Gets the value of the surname property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSurname() {
                        return surname;
                    }

                    /**
                     * Sets the value of the surname property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSurname(String value) {
                        this.surname = value;
                    }

                    /**
                     * Gets the value of the title property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getTitle() {
                        return title;
                    }

                    /**
                     * Sets the value of the title property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setTitle(String value) {
                        this.title = value;
                    }

                    /**
                     * Gets the value of the occupationStatus property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getOccupationStatus() {
                        return occupationStatus;
                    }

                    /**
                     * Sets the value of the occupationStatus property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setOccupationStatus(String value) {
                        this.occupationStatus = value;
                    }

                    /**
                     * Gets the value of the mastercardHeld property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getMastercardHeld() {
                        return mastercardHeld;
                    }

                    /**
                     * Sets the value of the mastercardHeld property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setMastercardHeld(String value) {
                        this.mastercardHeld = value;
                    }

                    /**
                     * Gets the value of the retailHeld property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getRetailHeld() {
                        return retailHeld;
                    }

                    /**
                     * Sets the value of the retailHeld property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setRetailHeld(String value) {
                        this.retailHeld = value;
                    }

                    /**
                     * Gets the value of the amExpressHeld property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getAmExpressHeld() {
                        return amExpressHeld;
                    }

                    /**
                     * Sets the value of the amExpressHeld property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setAmExpressHeld(String value) {
                        this.amExpressHeld = value;
                    }

                    /**
                     * Gets the value of the chqGteeHeld property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getChqGteeHeld() {
                        return chqGteeHeld;
                    }

                    /**
                     * Sets the value of the chqGteeHeld property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setChqGteeHeld(String value) {
                        this.chqGteeHeld = value;
                    }

                    /**
                     * Gets the value of the debitHeld property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDebitHeld() {
                        return debitHeld;
                    }

                    /**
                     * Sets the value of the debitHeld property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDebitHeld(String value) {
                        this.debitHeld = value;
                    }

                    /**
                     * Gets the value of the dinersHeld property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDinersHeld() {
                        return dinersHeld;
                    }

                    /**
                     * Sets the value of the dinersHeld property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDinersHeld(String value) {
                        this.dinersHeld = value;
                    }

                    /**
                     * Gets the value of the jcbHeld property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getJcbHeld() {
                        return jcbHeld;
                    }

                    /**
                     * Sets the value of the jcbHeld property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setJcbHeld(String value) {
                        this.jcbHeld = value;
                    }

                    /**
                     * Gets the value of the otherHeld property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getOtherHeld() {
                        return otherHeld;
                    }

                    /**
                     * Sets the value of the otherHeld property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setOtherHeld(String value) {
                        this.otherHeld = value;
                    }

                    /**
                     * Gets the value of the visaHeld property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getVisaHeld() {
                        return visaHeld;
                    }

                    /**
                     * Sets the value of the visaHeld property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setVisaHeld(String value) {
                        this.visaHeld = value;
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
                     *         &lt;element name="addressFlag" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                     *         &lt;element name="addressType" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                     *         &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                     *         &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="homeTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                     *         &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                     *         &lt;element name="timeAtAddress" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
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
                        "addressFlag",
                        "addressType",
                        "buildingNbr",
                        "country",
                        "endDate",
                        "flatNbr",
                        "homeTelNbr",
                        "houseNbr",
                        "line1",
                        "line2",
                        "line3",
                        "line4",
                        "postcode",
                        "regionCode",
                        "startDate",
                        "timeAtAddress"
                    })
                    public static class Address {

                        protected String addressFlag;
                        protected String addressType;
                        protected String buildingNbr;
                        protected String country;
                        protected String endDate;
                        protected String flatNbr;
                        protected String homeTelNbr;
                        protected String houseNbr;
                        protected String line1;
                        protected String line2;
                        protected String line3;
                        protected String line4;
                        protected String postcode;
                        protected String regionCode;
                        protected String startDate;
                        protected String timeAtAddress;

                        /**
                         * Gets the value of the addressFlag property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getAddressFlag() {
                            return addressFlag;
                        }

                        /**
                         * Sets the value of the addressFlag property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setAddressFlag(String value) {
                            this.addressFlag = value;
                        }

                        /**
                         * Gets the value of the addressType property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getAddressType() {
                            return addressType;
                        }

                        /**
                         * Sets the value of the addressType property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setAddressType(String value) {
                            this.addressType = value;
                        }

                        /**
                         * Gets the value of the buildingNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getBuildingNbr() {
                            return buildingNbr;
                        }

                        /**
                         * Sets the value of the buildingNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setBuildingNbr(String value) {
                            this.buildingNbr = value;
                        }

                        /**
                         * Gets the value of the country property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getCountry() {
                            return country;
                        }

                        /**
                         * Sets the value of the country property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setCountry(String value) {
                            this.country = value;
                        }

                        /**
                         * Gets the value of the endDate property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getEndDate() {
                            return endDate;
                        }

                        /**
                         * Sets the value of the endDate property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setEndDate(String value) {
                            this.endDate = value;
                        }

                        /**
                         * Gets the value of the flatNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getFlatNbr() {
                            return flatNbr;
                        }

                        /**
                         * Sets the value of the flatNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setFlatNbr(String value) {
                            this.flatNbr = value;
                        }

                        /**
                         * Gets the value of the homeTelNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getHomeTelNbr() {
                            return homeTelNbr;
                        }

                        /**
                         * Sets the value of the homeTelNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setHomeTelNbr(String value) {
                            this.homeTelNbr = value;
                        }

                        /**
                         * Gets the value of the houseNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getHouseNbr() {
                            return houseNbr;
                        }

                        /**
                         * Sets the value of the houseNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setHouseNbr(String value) {
                            this.houseNbr = value;
                        }

                        /**
                         * Gets the value of the line1 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine1() {
                            return line1;
                        }

                        /**
                         * Sets the value of the line1 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine1(String value) {
                            this.line1 = value;
                        }

                        /**
                         * Gets the value of the line2 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine2() {
                            return line2;
                        }

                        /**
                         * Sets the value of the line2 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine2(String value) {
                            this.line2 = value;
                        }

                        /**
                         * Gets the value of the line3 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine3() {
                            return line3;
                        }

                        /**
                         * Sets the value of the line3 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine3(String value) {
                            this.line3 = value;
                        }

                        /**
                         * Gets the value of the line4 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine4() {
                            return line4;
                        }

                        /**
                         * Sets the value of the line4 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine4(String value) {
                            this.line4 = value;
                        }

                        /**
                         * Gets the value of the postcode property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getPostcode() {
                            return postcode;
                        }

                        /**
                         * Sets the value of the postcode property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setPostcode(String value) {
                            this.postcode = value;
                        }

                        /**
                         * Gets the value of the regionCode property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getRegionCode() {
                            return regionCode;
                        }

                        /**
                         * Sets the value of the regionCode property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setRegionCode(String value) {
                            this.regionCode = value;
                        }

                        /**
                         * Gets the value of the startDate property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getStartDate() {
                            return startDate;
                        }

                        /**
                         * Sets the value of the startDate property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setStartDate(String value) {
                            this.startDate = value;
                        }

                        /**
                         * Gets the value of the timeAtAddress property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getTimeAtAddress() {
                            return timeAtAddress;
                        }

                        /**
                         * Sets the value of the timeAtAddress property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setTimeAtAddress(String value) {
                            this.timeAtAddress = value;
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
                     *         &lt;element name="buildingNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="country" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                     *         &lt;element name="currentPreviousIndicator" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue1" minOccurs="0"/>
                     *         &lt;element name="employerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="endDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                     *         &lt;element name="flatNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="houseNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="idNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="postcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="regionCode" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}codeValue2" minOccurs="0"/>
                     *         &lt;element name="startDate" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}timeStamp" minOccurs="0"/>
                     *         &lt;element name="timeWithEmployer" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}bureauNumeric" minOccurs="0"/>
                     *         &lt;element name="workMobileTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="workTelNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                        "buildingNbr",
                        "country",
                        "currentPreviousIndicator",
                        "employerName",
                        "endDate",
                        "flatNbr",
                        "houseNbr",
                        "idNumber",
                        "line1",
                        "line2",
                        "line3",
                        "line4",
                        "postcode",
                        "regionCode",
                        "startDate",
                        "timeWithEmployer",
                        "workMobileTelNbr",
                        "workTelNbr"
                    })
                    public static class Employer {

                        protected String buildingNbr;
                        protected String country;
                        protected String currentPreviousIndicator;
                        protected String employerName;
                        protected String endDate;
                        protected String flatNbr;
                        protected String houseNbr;
                        protected String idNumber;
                        protected String line1;
                        protected String line2;
                        protected String line3;
                        protected String line4;
                        protected String postcode;
                        protected String regionCode;
                        protected String startDate;
                        protected String timeWithEmployer;
                        protected String workMobileTelNbr;
                        protected String workTelNbr;

                        /**
                         * Gets the value of the buildingNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getBuildingNbr() {
                            return buildingNbr;
                        }

                        /**
                         * Sets the value of the buildingNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setBuildingNbr(String value) {
                            this.buildingNbr = value;
                        }

                        /**
                         * Gets the value of the country property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getCountry() {
                            return country;
                        }

                        /**
                         * Sets the value of the country property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setCountry(String value) {
                            this.country = value;
                        }

                        /**
                         * Gets the value of the currentPreviousIndicator property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getCurrentPreviousIndicator() {
                            return currentPreviousIndicator;
                        }

                        /**
                         * Sets the value of the currentPreviousIndicator property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setCurrentPreviousIndicator(String value) {
                            this.currentPreviousIndicator = value;
                        }

                        /**
                         * Gets the value of the employerName property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getEmployerName() {
                            return employerName;
                        }

                        /**
                         * Sets the value of the employerName property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setEmployerName(String value) {
                            this.employerName = value;
                        }

                        /**
                         * Gets the value of the endDate property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getEndDate() {
                            return endDate;
                        }

                        /**
                         * Sets the value of the endDate property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setEndDate(String value) {
                            this.endDate = value;
                        }

                        /**
                         * Gets the value of the flatNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getFlatNbr() {
                            return flatNbr;
                        }

                        /**
                         * Sets the value of the flatNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setFlatNbr(String value) {
                            this.flatNbr = value;
                        }

                        /**
                         * Gets the value of the houseNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getHouseNbr() {
                            return houseNbr;
                        }

                        /**
                         * Sets the value of the houseNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setHouseNbr(String value) {
                            this.houseNbr = value;
                        }

                        /**
                         * Gets the value of the idNumber property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getIdNumber() {
                            return idNumber;
                        }

                        /**
                         * Sets the value of the idNumber property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setIdNumber(String value) {
                            this.idNumber = value;
                        }

                        /**
                         * Gets the value of the line1 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine1() {
                            return line1;
                        }

                        /**
                         * Sets the value of the line1 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine1(String value) {
                            this.line1 = value;
                        }

                        /**
                         * Gets the value of the line2 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine2() {
                            return line2;
                        }

                        /**
                         * Sets the value of the line2 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine2(String value) {
                            this.line2 = value;
                        }

                        /**
                         * Gets the value of the line3 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine3() {
                            return line3;
                        }

                        /**
                         * Sets the value of the line3 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine3(String value) {
                            this.line3 = value;
                        }

                        /**
                         * Gets the value of the line4 property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLine4() {
                            return line4;
                        }

                        /**
                         * Sets the value of the line4 property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLine4(String value) {
                            this.line4 = value;
                        }

                        /**
                         * Gets the value of the postcode property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getPostcode() {
                            return postcode;
                        }

                        /**
                         * Sets the value of the postcode property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setPostcode(String value) {
                            this.postcode = value;
                        }

                        /**
                         * Gets the value of the regionCode property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getRegionCode() {
                            return regionCode;
                        }

                        /**
                         * Sets the value of the regionCode property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setRegionCode(String value) {
                            this.regionCode = value;
                        }

                        /**
                         * Gets the value of the startDate property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getStartDate() {
                            return startDate;
                        }

                        /**
                         * Sets the value of the startDate property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setStartDate(String value) {
                            this.startDate = value;
                        }

                        /**
                         * Gets the value of the timeWithEmployer property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getTimeWithEmployer() {
                            return timeWithEmployer;
                        }

                        /**
                         * Sets the value of the timeWithEmployer property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setTimeWithEmployer(String value) {
                            this.timeWithEmployer = value;
                        }

                        /**
                         * Gets the value of the workMobileTelNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getWorkMobileTelNbr() {
                            return workMobileTelNbr;
                        }

                        /**
                         * Sets the value of the workMobileTelNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setWorkMobileTelNbr(String value) {
                            this.workMobileTelNbr = value;
                        }

                        /**
                         * Gets the value of the workTelNbr property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getWorkTelNbr() {
                            return workTelNbr;
                        }

                        /**
                         * Sets the value of the workTelNbr property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setWorkTelNbr(String value) {
                            this.workTelNbr = value;
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
                     *         &lt;element name="status" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}status" minOccurs="0"/>
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
                        "status"
                    })
                    public static class Verification {

                        protected Status status;

                        /**
                         * Gets the value of the status property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link Status }
                         *     
                         */
                        public Status getStatus() {
                            return status;
                        }

                        /**
                         * Sets the value of the status property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link Status }
                         *     
                         */
                        public void setStatus(Status value) {
                            this.status = value;
                        }

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
             *         &lt;element name="status" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}status" minOccurs="0"/>
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
                "status"
            })
            public static class PVS {

                protected Status status;

                /**
                 * Gets the value of the status property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Status }
                 *     
                 */
                public Status getStatus() {
                    return status;
                }

                /**
                 * Sets the value of the status property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Status }
                 *     
                 */
                public void setStatus(Status value) {
                    this.status = value;
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
             *         &lt;element name="CAISDistribution1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAISDistribution2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAISDistribution3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAISDistribution4" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAISDistribution5" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAISDistribution5Plus" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAISRecordsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAISRecordsGuarantorRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAISRecordsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAISRecordsJointRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAISRecordsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAISRecordsOwnerRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAISRecordsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAISRecordsRefereeRecip" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSDistribution1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSDistribution2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSDistribution3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSDistribution4" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSDistribution5" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSDistribution5Plus" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSLast12MonthsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSLast12MonthsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSLast12MonthsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSLast12MonthsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSLast3MonthsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSLast3MonthsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSLast3MonthsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSLast3MonthsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSLast6MonthsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSLast6MonthsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSLast6MonthsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSLast6MonthsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSRecordsGuarantor" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSRecordsGuarantorBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSRecordsJoint" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSRecordsJointBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSRecordsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSRecordsOwnerBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSRecordsReferee" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="CAPSRecordsRefereeBeforeFilter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="PotentialMonthlyInstalmentsAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="PotentialMonthlyInstalmentsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="PotentialOutstandingBalanceAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="PotentialOutstandingBalanceOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="TotalMonthlyInstalmentsAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="TotalMonthlyInstalmentsOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="TotalOutstandingBalanceAllButOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="TotalOutstandingBalanceOwner" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
             *         &lt;element name="WorstCurrentPayStatusGuarantor" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
             *         &lt;element name="WorstCurrentPayStatusJoint" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
             *         &lt;element name="WorstCurrentPayStatusOwner" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
             *         &lt;element name="WorstCurrentPayStatusReferee" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
             *         &lt;element name="WorstEverPayStatusGuarantor" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
             *         &lt;element name="WorstEverPayStatusJoint" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
             *         &lt;element name="WorstEverPayStatusOwner" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
             *         &lt;element name="WorstEverPayStatusReferee" type="{http://CreditBureauLibrary/ru/neoflex/sbrf/ei/bo}accountPaymentStatus" minOccurs="0"/>
             *         &lt;element name="checkOther1" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther10" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther11" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther12" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther13" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther14" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther15" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther16" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther17" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther18" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther19" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther2" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther20" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther3" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther4" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther5" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther6" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther7" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther8" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="checkOther9" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
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
                "caisDistribution1",
                "caisDistribution2",
                "caisDistribution3",
                "caisDistribution4",
                "caisDistribution5",
                "caisDistribution5Plus",
                "caisRecordsGuarantor",
                "caisRecordsGuarantorRecip",
                "caisRecordsJoint",
                "caisRecordsJointRecip",
                "caisRecordsOwner",
                "caisRecordsOwnerRecip",
                "caisRecordsReferee",
                "caisRecordsRefereeRecip",
                "capsDistribution1",
                "capsDistribution2",
                "capsDistribution3",
                "capsDistribution4",
                "capsDistribution5",
                "capsDistribution5Plus",
                "capsLast12MonthsGuarantor",
                "capsLast12MonthsJoint",
                "capsLast12MonthsOwner",
                "capsLast12MonthsReferee",
                "capsLast3MonthsGuarantor",
                "capsLast3MonthsJoint",
                "capsLast3MonthsOwner",
                "capsLast3MonthsReferee",
                "capsLast6MonthsGuarantor",
                "capsLast6MonthsJoint",
                "capsLast6MonthsOwner",
                "capsLast6MonthsReferee",
                "capsRecordsGuarantor",
                "capsRecordsGuarantorBeforeFilter",
                "capsRecordsJoint",
                "capsRecordsJointBeforeFilter",
                "capsRecordsOwner",
                "capsRecordsOwnerBeforeFilter",
                "capsRecordsReferee",
                "capsRecordsRefereeBeforeFilter",
                "potentialMonthlyInstalmentsAllButOwner",
                "potentialMonthlyInstalmentsOwner",
                "potentialOutstandingBalanceAllButOwner",
                "potentialOutstandingBalanceOwner",
                "totalMonthlyInstalmentsAllButOwner",
                "totalMonthlyInstalmentsOwner",
                "totalOutstandingBalanceAllButOwner",
                "totalOutstandingBalanceOwner",
                "worstCurrentPayStatusGuarantor",
                "worstCurrentPayStatusJoint",
                "worstCurrentPayStatusOwner",
                "worstCurrentPayStatusReferee",
                "worstEverPayStatusGuarantor",
                "worstEverPayStatusJoint",
                "worstEverPayStatusOwner",
                "worstEverPayStatusReferee",
                "checkOther1",
                "checkOther10",
                "checkOther11",
                "checkOther12",
                "checkOther13",
                "checkOther14",
                "checkOther15",
                "checkOther16",
                "checkOther17",
                "checkOther18",
                "checkOther19",
                "checkOther2",
                "checkOther20",
                "checkOther3",
                "checkOther4",
                "checkOther5",
                "checkOther6",
                "checkOther7",
                "checkOther8",
                "checkOther9"
            })
            public static class Summary {

                @XmlElement(name = "CAISDistribution1")
                @XmlSchemaType(name = "unsignedInt")
                protected Long caisDistribution1;
                @XmlElement(name = "CAISDistribution2")
                @XmlSchemaType(name = "unsignedInt")
                protected Long caisDistribution2;
                @XmlElement(name = "CAISDistribution3")
                @XmlSchemaType(name = "unsignedInt")
                protected Long caisDistribution3;
                @XmlElement(name = "CAISDistribution4")
                @XmlSchemaType(name = "unsignedInt")
                protected Long caisDistribution4;
                @XmlElement(name = "CAISDistribution5")
                @XmlSchemaType(name = "unsignedInt")
                protected Long caisDistribution5;
                @XmlElement(name = "CAISDistribution5Plus")
                @XmlSchemaType(name = "unsignedInt")
                protected Long caisDistribution5Plus;
                @XmlElement(name = "CAISRecordsGuarantor")
                @XmlSchemaType(name = "unsignedInt")
                protected Long caisRecordsGuarantor;
                @XmlElement(name = "CAISRecordsGuarantorRecip")
                @XmlSchemaType(name = "unsignedInt")
                protected Long caisRecordsGuarantorRecip;
                @XmlElement(name = "CAISRecordsJoint")
                @XmlSchemaType(name = "unsignedInt")
                protected Long caisRecordsJoint;
                @XmlElement(name = "CAISRecordsJointRecip")
                @XmlSchemaType(name = "unsignedInt")
                protected Long caisRecordsJointRecip;
                @XmlElement(name = "CAISRecordsOwner")
                @XmlSchemaType(name = "unsignedInt")
                protected Long caisRecordsOwner;
                @XmlElement(name = "CAISRecordsOwnerRecip")
                @XmlSchemaType(name = "unsignedInt")
                protected Long caisRecordsOwnerRecip;
                @XmlElement(name = "CAISRecordsReferee")
                @XmlSchemaType(name = "unsignedInt")
                protected Long caisRecordsReferee;
                @XmlElement(name = "CAISRecordsRefereeRecip")
                @XmlSchemaType(name = "unsignedInt")
                protected Long caisRecordsRefereeRecip;
                @XmlElement(name = "CAPSDistribution1")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsDistribution1;
                @XmlElement(name = "CAPSDistribution2")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsDistribution2;
                @XmlElement(name = "CAPSDistribution3")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsDistribution3;
                @XmlElement(name = "CAPSDistribution4")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsDistribution4;
                @XmlElement(name = "CAPSDistribution5")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsDistribution5;
                @XmlElement(name = "CAPSDistribution5Plus")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsDistribution5Plus;
                @XmlElement(name = "CAPSLast12MonthsGuarantor")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsLast12MonthsGuarantor;
                @XmlElement(name = "CAPSLast12MonthsJoint")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsLast12MonthsJoint;
                @XmlElement(name = "CAPSLast12MonthsOwner")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsLast12MonthsOwner;
                @XmlElement(name = "CAPSLast12MonthsReferee")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsLast12MonthsReferee;
                @XmlElement(name = "CAPSLast3MonthsGuarantor")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsLast3MonthsGuarantor;
                @XmlElement(name = "CAPSLast3MonthsJoint")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsLast3MonthsJoint;
                @XmlElement(name = "CAPSLast3MonthsOwner")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsLast3MonthsOwner;
                @XmlElement(name = "CAPSLast3MonthsReferee")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsLast3MonthsReferee;
                @XmlElement(name = "CAPSLast6MonthsGuarantor")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsLast6MonthsGuarantor;
                @XmlElement(name = "CAPSLast6MonthsJoint")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsLast6MonthsJoint;
                @XmlElement(name = "CAPSLast6MonthsOwner")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsLast6MonthsOwner;
                @XmlElement(name = "CAPSLast6MonthsReferee")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsLast6MonthsReferee;
                @XmlElement(name = "CAPSRecordsGuarantor")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsRecordsGuarantor;
                @XmlElement(name = "CAPSRecordsGuarantorBeforeFilter")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsRecordsGuarantorBeforeFilter;
                @XmlElement(name = "CAPSRecordsJoint")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsRecordsJoint;
                @XmlElement(name = "CAPSRecordsJointBeforeFilter")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsRecordsJointBeforeFilter;
                @XmlElement(name = "CAPSRecordsOwner")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsRecordsOwner;
                @XmlElement(name = "CAPSRecordsOwnerBeforeFilter")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsRecordsOwnerBeforeFilter;
                @XmlElement(name = "CAPSRecordsReferee")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsRecordsReferee;
                @XmlElement(name = "CAPSRecordsRefereeBeforeFilter")
                @XmlSchemaType(name = "unsignedInt")
                protected Long capsRecordsRefereeBeforeFilter;
                @XmlElement(name = "PotentialMonthlyInstalmentsAllButOwner")
                @XmlSchemaType(name = "unsignedInt")
                protected Long potentialMonthlyInstalmentsAllButOwner;
                @XmlElement(name = "PotentialMonthlyInstalmentsOwner")
                @XmlSchemaType(name = "unsignedInt")
                protected Long potentialMonthlyInstalmentsOwner;
                @XmlElement(name = "PotentialOutstandingBalanceAllButOwner")
                @XmlSchemaType(name = "unsignedInt")
                protected Long potentialOutstandingBalanceAllButOwner;
                @XmlElement(name = "PotentialOutstandingBalanceOwner")
                @XmlSchemaType(name = "unsignedInt")
                protected Long potentialOutstandingBalanceOwner;
                @XmlElement(name = "TotalMonthlyInstalmentsAllButOwner")
                @XmlSchemaType(name = "unsignedInt")
                protected Long totalMonthlyInstalmentsAllButOwner;
                @XmlElement(name = "TotalMonthlyInstalmentsOwner")
                @XmlSchemaType(name = "unsignedInt")
                protected Long totalMonthlyInstalmentsOwner;
                @XmlElement(name = "TotalOutstandingBalanceAllButOwner")
                @XmlSchemaType(name = "unsignedInt")
                protected Long totalOutstandingBalanceAllButOwner;
                @XmlElement(name = "TotalOutstandingBalanceOwner")
                @XmlSchemaType(name = "unsignedInt")
                protected Long totalOutstandingBalanceOwner;
                @XmlElement(name = "WorstCurrentPayStatusGuarantor")
                protected String worstCurrentPayStatusGuarantor;
                @XmlElement(name = "WorstCurrentPayStatusJoint")
                protected String worstCurrentPayStatusJoint;
                @XmlElement(name = "WorstCurrentPayStatusOwner")
                protected String worstCurrentPayStatusOwner;
                @XmlElement(name = "WorstCurrentPayStatusReferee")
                protected String worstCurrentPayStatusReferee;
                @XmlElement(name = "WorstEverPayStatusGuarantor")
                protected String worstEverPayStatusGuarantor;
                @XmlElement(name = "WorstEverPayStatusJoint")
                protected String worstEverPayStatusJoint;
                @XmlElement(name = "WorstEverPayStatusOwner")
                protected String worstEverPayStatusOwner;
                @XmlElement(name = "WorstEverPayStatusReferee")
                protected String worstEverPayStatusReferee;
                protected Object checkOther1;
                protected Object checkOther10;
                protected Object checkOther11;
                protected Object checkOther12;
                protected Object checkOther13;
                protected Object checkOther14;
                protected Object checkOther15;
                protected Object checkOther16;
                protected Object checkOther17;
                protected Object checkOther18;
                protected Object checkOther19;
                protected Object checkOther2;
                protected Object checkOther20;
                protected Object checkOther3;
                protected Object checkOther4;
                protected Object checkOther5;
                protected Object checkOther6;
                protected Object checkOther7;
                protected Object checkOther8;
                protected Object checkOther9;

                /**
                 * Gets the value of the caisDistribution1 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAISDistribution1() {
                    return caisDistribution1;
                }

                /**
                 * Sets the value of the caisDistribution1 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAISDistribution1(Long value) {
                    this.caisDistribution1 = value;
                }

                /**
                 * Gets the value of the caisDistribution2 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAISDistribution2() {
                    return caisDistribution2;
                }

                /**
                 * Sets the value of the caisDistribution2 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAISDistribution2(Long value) {
                    this.caisDistribution2 = value;
                }

                /**
                 * Gets the value of the caisDistribution3 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAISDistribution3() {
                    return caisDistribution3;
                }

                /**
                 * Sets the value of the caisDistribution3 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAISDistribution3(Long value) {
                    this.caisDistribution3 = value;
                }

                /**
                 * Gets the value of the caisDistribution4 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAISDistribution4() {
                    return caisDistribution4;
                }

                /**
                 * Sets the value of the caisDistribution4 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAISDistribution4(Long value) {
                    this.caisDistribution4 = value;
                }

                /**
                 * Gets the value of the caisDistribution5 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAISDistribution5() {
                    return caisDistribution5;
                }

                /**
                 * Sets the value of the caisDistribution5 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAISDistribution5(Long value) {
                    this.caisDistribution5 = value;
                }

                /**
                 * Gets the value of the caisDistribution5Plus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAISDistribution5Plus() {
                    return caisDistribution5Plus;
                }

                /**
                 * Sets the value of the caisDistribution5Plus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAISDistribution5Plus(Long value) {
                    this.caisDistribution5Plus = value;
                }

                /**
                 * Gets the value of the caisRecordsGuarantor property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAISRecordsGuarantor() {
                    return caisRecordsGuarantor;
                }

                /**
                 * Sets the value of the caisRecordsGuarantor property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAISRecordsGuarantor(Long value) {
                    this.caisRecordsGuarantor = value;
                }

                /**
                 * Gets the value of the caisRecordsGuarantorRecip property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAISRecordsGuarantorRecip() {
                    return caisRecordsGuarantorRecip;
                }

                /**
                 * Sets the value of the caisRecordsGuarantorRecip property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAISRecordsGuarantorRecip(Long value) {
                    this.caisRecordsGuarantorRecip = value;
                }

                /**
                 * Gets the value of the caisRecordsJoint property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAISRecordsJoint() {
                    return caisRecordsJoint;
                }

                /**
                 * Sets the value of the caisRecordsJoint property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAISRecordsJoint(Long value) {
                    this.caisRecordsJoint = value;
                }

                /**
                 * Gets the value of the caisRecordsJointRecip property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAISRecordsJointRecip() {
                    return caisRecordsJointRecip;
                }

                /**
                 * Sets the value of the caisRecordsJointRecip property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAISRecordsJointRecip(Long value) {
                    this.caisRecordsJointRecip = value;
                }

                /**
                 * Gets the value of the caisRecordsOwner property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAISRecordsOwner() {
                    return caisRecordsOwner;
                }

                /**
                 * Sets the value of the caisRecordsOwner property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAISRecordsOwner(Long value) {
                    this.caisRecordsOwner = value;
                }

                /**
                 * Gets the value of the caisRecordsOwnerRecip property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAISRecordsOwnerRecip() {
                    return caisRecordsOwnerRecip;
                }

                /**
                 * Sets the value of the caisRecordsOwnerRecip property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAISRecordsOwnerRecip(Long value) {
                    this.caisRecordsOwnerRecip = value;
                }

                /**
                 * Gets the value of the caisRecordsReferee property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAISRecordsReferee() {
                    return caisRecordsReferee;
                }

                /**
                 * Sets the value of the caisRecordsReferee property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAISRecordsReferee(Long value) {
                    this.caisRecordsReferee = value;
                }

                /**
                 * Gets the value of the caisRecordsRefereeRecip property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAISRecordsRefereeRecip() {
                    return caisRecordsRefereeRecip;
                }

                /**
                 * Sets the value of the caisRecordsRefereeRecip property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAISRecordsRefereeRecip(Long value) {
                    this.caisRecordsRefereeRecip = value;
                }

                /**
                 * Gets the value of the capsDistribution1 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSDistribution1() {
                    return capsDistribution1;
                }

                /**
                 * Sets the value of the capsDistribution1 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSDistribution1(Long value) {
                    this.capsDistribution1 = value;
                }

                /**
                 * Gets the value of the capsDistribution2 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSDistribution2() {
                    return capsDistribution2;
                }

                /**
                 * Sets the value of the capsDistribution2 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSDistribution2(Long value) {
                    this.capsDistribution2 = value;
                }

                /**
                 * Gets the value of the capsDistribution3 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSDistribution3() {
                    return capsDistribution3;
                }

                /**
                 * Sets the value of the capsDistribution3 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSDistribution3(Long value) {
                    this.capsDistribution3 = value;
                }

                /**
                 * Gets the value of the capsDistribution4 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSDistribution4() {
                    return capsDistribution4;
                }

                /**
                 * Sets the value of the capsDistribution4 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSDistribution4(Long value) {
                    this.capsDistribution4 = value;
                }

                /**
                 * Gets the value of the capsDistribution5 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSDistribution5() {
                    return capsDistribution5;
                }

                /**
                 * Sets the value of the capsDistribution5 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSDistribution5(Long value) {
                    this.capsDistribution5 = value;
                }

                /**
                 * Gets the value of the capsDistribution5Plus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSDistribution5Plus() {
                    return capsDistribution5Plus;
                }

                /**
                 * Sets the value of the capsDistribution5Plus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSDistribution5Plus(Long value) {
                    this.capsDistribution5Plus = value;
                }

                /**
                 * Gets the value of the capsLast12MonthsGuarantor property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSLast12MonthsGuarantor() {
                    return capsLast12MonthsGuarantor;
                }

                /**
                 * Sets the value of the capsLast12MonthsGuarantor property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSLast12MonthsGuarantor(Long value) {
                    this.capsLast12MonthsGuarantor = value;
                }

                /**
                 * Gets the value of the capsLast12MonthsJoint property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSLast12MonthsJoint() {
                    return capsLast12MonthsJoint;
                }

                /**
                 * Sets the value of the capsLast12MonthsJoint property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSLast12MonthsJoint(Long value) {
                    this.capsLast12MonthsJoint = value;
                }

                /**
                 * Gets the value of the capsLast12MonthsOwner property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSLast12MonthsOwner() {
                    return capsLast12MonthsOwner;
                }

                /**
                 * Sets the value of the capsLast12MonthsOwner property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSLast12MonthsOwner(Long value) {
                    this.capsLast12MonthsOwner = value;
                }

                /**
                 * Gets the value of the capsLast12MonthsReferee property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSLast12MonthsReferee() {
                    return capsLast12MonthsReferee;
                }

                /**
                 * Sets the value of the capsLast12MonthsReferee property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSLast12MonthsReferee(Long value) {
                    this.capsLast12MonthsReferee = value;
                }

                /**
                 * Gets the value of the capsLast3MonthsGuarantor property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSLast3MonthsGuarantor() {
                    return capsLast3MonthsGuarantor;
                }

                /**
                 * Sets the value of the capsLast3MonthsGuarantor property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSLast3MonthsGuarantor(Long value) {
                    this.capsLast3MonthsGuarantor = value;
                }

                /**
                 * Gets the value of the capsLast3MonthsJoint property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSLast3MonthsJoint() {
                    return capsLast3MonthsJoint;
                }

                /**
                 * Sets the value of the capsLast3MonthsJoint property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSLast3MonthsJoint(Long value) {
                    this.capsLast3MonthsJoint = value;
                }

                /**
                 * Gets the value of the capsLast3MonthsOwner property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSLast3MonthsOwner() {
                    return capsLast3MonthsOwner;
                }

                /**
                 * Sets the value of the capsLast3MonthsOwner property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSLast3MonthsOwner(Long value) {
                    this.capsLast3MonthsOwner = value;
                }

                /**
                 * Gets the value of the capsLast3MonthsReferee property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSLast3MonthsReferee() {
                    return capsLast3MonthsReferee;
                }

                /**
                 * Sets the value of the capsLast3MonthsReferee property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSLast3MonthsReferee(Long value) {
                    this.capsLast3MonthsReferee = value;
                }

                /**
                 * Gets the value of the capsLast6MonthsGuarantor property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSLast6MonthsGuarantor() {
                    return capsLast6MonthsGuarantor;
                }

                /**
                 * Sets the value of the capsLast6MonthsGuarantor property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSLast6MonthsGuarantor(Long value) {
                    this.capsLast6MonthsGuarantor = value;
                }

                /**
                 * Gets the value of the capsLast6MonthsJoint property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSLast6MonthsJoint() {
                    return capsLast6MonthsJoint;
                }

                /**
                 * Sets the value of the capsLast6MonthsJoint property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSLast6MonthsJoint(Long value) {
                    this.capsLast6MonthsJoint = value;
                }

                /**
                 * Gets the value of the capsLast6MonthsOwner property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSLast6MonthsOwner() {
                    return capsLast6MonthsOwner;
                }

                /**
                 * Sets the value of the capsLast6MonthsOwner property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSLast6MonthsOwner(Long value) {
                    this.capsLast6MonthsOwner = value;
                }

                /**
                 * Gets the value of the capsLast6MonthsReferee property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSLast6MonthsReferee() {
                    return capsLast6MonthsReferee;
                }

                /**
                 * Sets the value of the capsLast6MonthsReferee property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSLast6MonthsReferee(Long value) {
                    this.capsLast6MonthsReferee = value;
                }

                /**
                 * Gets the value of the capsRecordsGuarantor property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSRecordsGuarantor() {
                    return capsRecordsGuarantor;
                }

                /**
                 * Sets the value of the capsRecordsGuarantor property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSRecordsGuarantor(Long value) {
                    this.capsRecordsGuarantor = value;
                }

                /**
                 * Gets the value of the capsRecordsGuarantorBeforeFilter property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSRecordsGuarantorBeforeFilter() {
                    return capsRecordsGuarantorBeforeFilter;
                }

                /**
                 * Sets the value of the capsRecordsGuarantorBeforeFilter property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSRecordsGuarantorBeforeFilter(Long value) {
                    this.capsRecordsGuarantorBeforeFilter = value;
                }

                /**
                 * Gets the value of the capsRecordsJoint property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSRecordsJoint() {
                    return capsRecordsJoint;
                }

                /**
                 * Sets the value of the capsRecordsJoint property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSRecordsJoint(Long value) {
                    this.capsRecordsJoint = value;
                }

                /**
                 * Gets the value of the capsRecordsJointBeforeFilter property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSRecordsJointBeforeFilter() {
                    return capsRecordsJointBeforeFilter;
                }

                /**
                 * Sets the value of the capsRecordsJointBeforeFilter property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSRecordsJointBeforeFilter(Long value) {
                    this.capsRecordsJointBeforeFilter = value;
                }

                /**
                 * Gets the value of the capsRecordsOwner property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSRecordsOwner() {
                    return capsRecordsOwner;
                }

                /**
                 * Sets the value of the capsRecordsOwner property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSRecordsOwner(Long value) {
                    this.capsRecordsOwner = value;
                }

                /**
                 * Gets the value of the capsRecordsOwnerBeforeFilter property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSRecordsOwnerBeforeFilter() {
                    return capsRecordsOwnerBeforeFilter;
                }

                /**
                 * Sets the value of the capsRecordsOwnerBeforeFilter property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSRecordsOwnerBeforeFilter(Long value) {
                    this.capsRecordsOwnerBeforeFilter = value;
                }

                /**
                 * Gets the value of the capsRecordsReferee property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSRecordsReferee() {
                    return capsRecordsReferee;
                }

                /**
                 * Sets the value of the capsRecordsReferee property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSRecordsReferee(Long value) {
                    this.capsRecordsReferee = value;
                }

                /**
                 * Gets the value of the capsRecordsRefereeBeforeFilter property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getCAPSRecordsRefereeBeforeFilter() {
                    return capsRecordsRefereeBeforeFilter;
                }

                /**
                 * Sets the value of the capsRecordsRefereeBeforeFilter property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setCAPSRecordsRefereeBeforeFilter(Long value) {
                    this.capsRecordsRefereeBeforeFilter = value;
                }

                /**
                 * Gets the value of the potentialMonthlyInstalmentsAllButOwner property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getPotentialMonthlyInstalmentsAllButOwner() {
                    return potentialMonthlyInstalmentsAllButOwner;
                }

                /**
                 * Sets the value of the potentialMonthlyInstalmentsAllButOwner property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setPotentialMonthlyInstalmentsAllButOwner(Long value) {
                    this.potentialMonthlyInstalmentsAllButOwner = value;
                }

                /**
                 * Gets the value of the potentialMonthlyInstalmentsOwner property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getPotentialMonthlyInstalmentsOwner() {
                    return potentialMonthlyInstalmentsOwner;
                }

                /**
                 * Sets the value of the potentialMonthlyInstalmentsOwner property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setPotentialMonthlyInstalmentsOwner(Long value) {
                    this.potentialMonthlyInstalmentsOwner = value;
                }

                /**
                 * Gets the value of the potentialOutstandingBalanceAllButOwner property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getPotentialOutstandingBalanceAllButOwner() {
                    return potentialOutstandingBalanceAllButOwner;
                }

                /**
                 * Sets the value of the potentialOutstandingBalanceAllButOwner property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setPotentialOutstandingBalanceAllButOwner(Long value) {
                    this.potentialOutstandingBalanceAllButOwner = value;
                }

                /**
                 * Gets the value of the potentialOutstandingBalanceOwner property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getPotentialOutstandingBalanceOwner() {
                    return potentialOutstandingBalanceOwner;
                }

                /**
                 * Sets the value of the potentialOutstandingBalanceOwner property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setPotentialOutstandingBalanceOwner(Long value) {
                    this.potentialOutstandingBalanceOwner = value;
                }

                /**
                 * Gets the value of the totalMonthlyInstalmentsAllButOwner property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getTotalMonthlyInstalmentsAllButOwner() {
                    return totalMonthlyInstalmentsAllButOwner;
                }

                /**
                 * Sets the value of the totalMonthlyInstalmentsAllButOwner property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setTotalMonthlyInstalmentsAllButOwner(Long value) {
                    this.totalMonthlyInstalmentsAllButOwner = value;
                }

                /**
                 * Gets the value of the totalMonthlyInstalmentsOwner property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getTotalMonthlyInstalmentsOwner() {
                    return totalMonthlyInstalmentsOwner;
                }

                /**
                 * Sets the value of the totalMonthlyInstalmentsOwner property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setTotalMonthlyInstalmentsOwner(Long value) {
                    this.totalMonthlyInstalmentsOwner = value;
                }

                /**
                 * Gets the value of the totalOutstandingBalanceAllButOwner property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getTotalOutstandingBalanceAllButOwner() {
                    return totalOutstandingBalanceAllButOwner;
                }

                /**
                 * Sets the value of the totalOutstandingBalanceAllButOwner property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setTotalOutstandingBalanceAllButOwner(Long value) {
                    this.totalOutstandingBalanceAllButOwner = value;
                }

                /**
                 * Gets the value of the totalOutstandingBalanceOwner property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getTotalOutstandingBalanceOwner() {
                    return totalOutstandingBalanceOwner;
                }

                /**
                 * Sets the value of the totalOutstandingBalanceOwner property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setTotalOutstandingBalanceOwner(Long value) {
                    this.totalOutstandingBalanceOwner = value;
                }

                /**
                 * Gets the value of the worstCurrentPayStatusGuarantor property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getWorstCurrentPayStatusGuarantor() {
                    return worstCurrentPayStatusGuarantor;
                }

                /**
                 * Sets the value of the worstCurrentPayStatusGuarantor property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setWorstCurrentPayStatusGuarantor(String value) {
                    this.worstCurrentPayStatusGuarantor = value;
                }

                /**
                 * Gets the value of the worstCurrentPayStatusJoint property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getWorstCurrentPayStatusJoint() {
                    return worstCurrentPayStatusJoint;
                }

                /**
                 * Sets the value of the worstCurrentPayStatusJoint property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setWorstCurrentPayStatusJoint(String value) {
                    this.worstCurrentPayStatusJoint = value;
                }

                /**
                 * Gets the value of the worstCurrentPayStatusOwner property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getWorstCurrentPayStatusOwner() {
                    return worstCurrentPayStatusOwner;
                }

                /**
                 * Sets the value of the worstCurrentPayStatusOwner property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setWorstCurrentPayStatusOwner(String value) {
                    this.worstCurrentPayStatusOwner = value;
                }

                /**
                 * Gets the value of the worstCurrentPayStatusReferee property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getWorstCurrentPayStatusReferee() {
                    return worstCurrentPayStatusReferee;
                }

                /**
                 * Sets the value of the worstCurrentPayStatusReferee property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setWorstCurrentPayStatusReferee(String value) {
                    this.worstCurrentPayStatusReferee = value;
                }

                /**
                 * Gets the value of the worstEverPayStatusGuarantor property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getWorstEverPayStatusGuarantor() {
                    return worstEverPayStatusGuarantor;
                }

                /**
                 * Sets the value of the worstEverPayStatusGuarantor property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setWorstEverPayStatusGuarantor(String value) {
                    this.worstEverPayStatusGuarantor = value;
                }

                /**
                 * Gets the value of the worstEverPayStatusJoint property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getWorstEverPayStatusJoint() {
                    return worstEverPayStatusJoint;
                }

                /**
                 * Sets the value of the worstEverPayStatusJoint property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setWorstEverPayStatusJoint(String value) {
                    this.worstEverPayStatusJoint = value;
                }

                /**
                 * Gets the value of the worstEverPayStatusOwner property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getWorstEverPayStatusOwner() {
                    return worstEverPayStatusOwner;
                }

                /**
                 * Sets the value of the worstEverPayStatusOwner property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setWorstEverPayStatusOwner(String value) {
                    this.worstEverPayStatusOwner = value;
                }

                /**
                 * Gets the value of the worstEverPayStatusReferee property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getWorstEverPayStatusReferee() {
                    return worstEverPayStatusReferee;
                }

                /**
                 * Sets the value of the worstEverPayStatusReferee property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setWorstEverPayStatusReferee(String value) {
                    this.worstEverPayStatusReferee = value;
                }

                /**
                 * Gets the value of the checkOther1 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther1() {
                    return checkOther1;
                }

                /**
                 * Sets the value of the checkOther1 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther1(Object value) {
                    this.checkOther1 = value;
                }

                /**
                 * Gets the value of the checkOther10 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther10() {
                    return checkOther10;
                }

                /**
                 * Sets the value of the checkOther10 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther10(Object value) {
                    this.checkOther10 = value;
                }

                /**
                 * Gets the value of the checkOther11 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther11() {
                    return checkOther11;
                }

                /**
                 * Sets the value of the checkOther11 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther11(Object value) {
                    this.checkOther11 = value;
                }

                /**
                 * Gets the value of the checkOther12 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther12() {
                    return checkOther12;
                }

                /**
                 * Sets the value of the checkOther12 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther12(Object value) {
                    this.checkOther12 = value;
                }

                /**
                 * Gets the value of the checkOther13 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther13() {
                    return checkOther13;
                }

                /**
                 * Sets the value of the checkOther13 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther13(Object value) {
                    this.checkOther13 = value;
                }

                /**
                 * Gets the value of the checkOther14 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther14() {
                    return checkOther14;
                }

                /**
                 * Sets the value of the checkOther14 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther14(Object value) {
                    this.checkOther14 = value;
                }

                /**
                 * Gets the value of the checkOther15 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther15() {
                    return checkOther15;
                }

                /**
                 * Sets the value of the checkOther15 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther15(Object value) {
                    this.checkOther15 = value;
                }

                /**
                 * Gets the value of the checkOther16 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther16() {
                    return checkOther16;
                }

                /**
                 * Sets the value of the checkOther16 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther16(Object value) {
                    this.checkOther16 = value;
                }

                /**
                 * Gets the value of the checkOther17 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther17() {
                    return checkOther17;
                }

                /**
                 * Sets the value of the checkOther17 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther17(Object value) {
                    this.checkOther17 = value;
                }

                /**
                 * Gets the value of the checkOther18 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther18() {
                    return checkOther18;
                }

                /**
                 * Sets the value of the checkOther18 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther18(Object value) {
                    this.checkOther18 = value;
                }

                /**
                 * Gets the value of the checkOther19 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther19() {
                    return checkOther19;
                }

                /**
                 * Sets the value of the checkOther19 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther19(Object value) {
                    this.checkOther19 = value;
                }

                /**
                 * Gets the value of the checkOther2 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther2() {
                    return checkOther2;
                }

                /**
                 * Sets the value of the checkOther2 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther2(Object value) {
                    this.checkOther2 = value;
                }

                /**
                 * Gets the value of the checkOther20 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther20() {
                    return checkOther20;
                }

                /**
                 * Sets the value of the checkOther20 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther20(Object value) {
                    this.checkOther20 = value;
                }

                /**
                 * Gets the value of the checkOther3 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther3() {
                    return checkOther3;
                }

                /**
                 * Sets the value of the checkOther3 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther3(Object value) {
                    this.checkOther3 = value;
                }

                /**
                 * Gets the value of the checkOther4 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther4() {
                    return checkOther4;
                }

                /**
                 * Sets the value of the checkOther4 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther4(Object value) {
                    this.checkOther4 = value;
                }

                /**
                 * Gets the value of the checkOther5 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther5() {
                    return checkOther5;
                }

                /**
                 * Sets the value of the checkOther5 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther5(Object value) {
                    this.checkOther5 = value;
                }

                /**
                 * Gets the value of the checkOther6 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther6() {
                    return checkOther6;
                }

                /**
                 * Sets the value of the checkOther6 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther6(Object value) {
                    this.checkOther6 = value;
                }

                /**
                 * Gets the value of the checkOther7 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther7() {
                    return checkOther7;
                }

                /**
                 * Sets the value of the checkOther7 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther7(Object value) {
                    this.checkOther7 = value;
                }

                /**
                 * Gets the value of the checkOther8 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther8() {
                    return checkOther8;
                }

                /**
                 * Sets the value of the checkOther8 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther8(Object value) {
                    this.checkOther8 = value;
                }

                /**
                 * Gets the value of the checkOther9 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCheckOther9() {
                    return checkOther9;
                }

                /**
                 * Sets the value of the checkOther9 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCheckOther9(Object value) {
                    this.checkOther9 = value;
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
             *         &lt;element name="a" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
                "as"
            })
            public static class Warning {

                @XmlElement(name = "a")
                protected List<String> as;

                /**
                 * Gets the value of the as property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the as property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getAS().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getAS() {
                    if (as == null) {
                        as = new ArrayList<String>();
                    }
                    return this.as;
                }

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
     *         &lt;element name="s" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="extra1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="extra2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="field" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *                   &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "ss"
    })
    public static class ValidationErrors {

        @XmlElement(name = "s")
        protected List<EnquiryResponseERIB.ValidationErrors.S> ss;

        /**
         * Gets the value of the ss property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ss property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSS().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EnquiryResponseERIB.ValidationErrors.S }
         * 
         * 
         */
        public List<EnquiryResponseERIB.ValidationErrors.S> getSS() {
            if (ss == null) {
                ss = new ArrayList<EnquiryResponseERIB.ValidationErrors.S>();
            }
            return this.ss;
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
         *         &lt;element name="extra1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="extra2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="field" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
         *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "extra1",
            "extra2",
            "field",
            "number",
            "path",
            "value"
        })
        public static class S {

            protected String extra1;
            protected String extra2;
            protected String field;
            @XmlSchemaType(name = "unsignedInt")
            protected Long number;
            protected String path;
            protected String value;

            /**
             * Gets the value of the extra1 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getExtra1() {
                return extra1;
            }

            /**
             * Sets the value of the extra1 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setExtra1(String value) {
                this.extra1 = value;
            }

            /**
             * Gets the value of the extra2 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getExtra2() {
                return extra2;
            }

            /**
             * Sets the value of the extra2 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setExtra2(String value) {
                this.extra2 = value;
            }

            /**
             * Gets the value of the field property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getField() {
                return field;
            }

            /**
             * Sets the value of the field property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setField(String value) {
                this.field = value;
            }

            /**
             * Gets the value of the number property.
             * 
             * @return
             *     possible object is
             *     {@link Long }
             *     
             */
            public Long getNumber() {
                return number;
            }

            /**
             * Sets the value of the number property.
             * 
             * @param value
             *     allowed object is
             *     {@link Long }
             *     
             */
            public void setNumber(Long value) {
                this.number = value;
            }

            /**
             * Gets the value of the path property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPath() {
                return path;
            }

            /**
             * Sets the value of the path property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPath(String value) {
                this.path = value;
            }

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

        }

    }

}

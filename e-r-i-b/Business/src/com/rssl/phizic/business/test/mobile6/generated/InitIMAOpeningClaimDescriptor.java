//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.09.13 at 03:37:43 PM MSD 
//


package com.rssl.phizic.business.test.mobile6.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ������ �� �������� ���
 * 
 * <p>Java class for InitIMAOpeningClaim complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InitIMAOpeningClaim">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentNumber" type="{}Field"/>
 *         &lt;element name="documentDate" type="{}Field"/>
 *         &lt;element name="imaId" type="{}Field"/>
 *         &lt;element name="imaName" type="{}Field"/>
 *         &lt;element name="imaType" type="{}Field"/>
 *         &lt;element name="imaSubType" type="{}Field"/>
 *         &lt;element name="openingDate" type="{}Field"/>
 *         &lt;element name="buyCurrency" type="{}Field"/>
 *         &lt;element name="buyAmount" type="{}Field"/>
 *         &lt;element name="course" type="{}Field"/>
 *         &lt;element name="fromResource" type="{}Field"/>
 *         &lt;element name="sellCurrency" type="{}Field"/>
 *         &lt;element name="sellAmount" type="{}Field"/>
 *         &lt;element name="exactAmount" type="{}Field"/>
 *         &lt;element name="operationCode" type="{}Field"/>
 *         &lt;element name="offices" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="office" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="code" type="{}Resource"/>
 *                             &lt;element name="name" type="{}string"/>
 *                             &lt;element name="address" type="{}string"/>
 *                             &lt;element name="tb" type="{}string"/>
 *                             &lt;element name="osb" type="{}string"/>
 *                             &lt;element name="vsp" type="{}string"/>
 *                             &lt;element name="isIma" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                             &lt;element name="parentSynchKey" type="{}string"/>
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
 *         &lt;element name="officeName" type="{}Field"/>
 *         &lt;element name="officeAddress" type="{}Field"/>
 *         &lt;element name="tb" type="{}Field"/>
 *         &lt;element name="osb" type="{}Field"/>
 *         &lt;element name="vsp" type="{}Field"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InitIMAOpeningClaim", propOrder = {
    "documentNumber",
    "documentDate",
    "imaId",
    "imaName",
    "imaType",
    "imaSubType",
    "openingDate",
    "buyCurrency",
    "buyAmount",
    "course",
    "fromResource",
    "sellCurrency",
    "sellAmount",
    "exactAmount",
    "operationCode",
    "offices",
    "officeName",
    "officeAddress",
    "tb",
    "osb",
    "vsp"
})
public class InitIMAOpeningClaimDescriptor {

    @XmlElement(required = true)
    protected FieldDescriptor documentNumber;
    @XmlElement(required = true)
    protected FieldDescriptor documentDate;
    @XmlElement(required = true)
    protected FieldDescriptor imaId;
    @XmlElement(required = true)
    protected FieldDescriptor imaName;
    @XmlElement(required = true)
    protected FieldDescriptor imaType;
    @XmlElement(required = true)
    protected FieldDescriptor imaSubType;
    @XmlElement(required = true)
    protected FieldDescriptor openingDate;
    @XmlElement(required = true)
    protected FieldDescriptor buyCurrency;
    @XmlElement(required = true)
    protected FieldDescriptor buyAmount;
    @XmlElement(required = true)
    protected FieldDescriptor course;
    @XmlElement(required = true)
    protected FieldDescriptor fromResource;
    @XmlElement(required = true)
    protected FieldDescriptor sellCurrency;
    @XmlElement(required = true)
    protected FieldDescriptor sellAmount;
    @XmlElement(required = true)
    protected FieldDescriptor exactAmount;
    @XmlElement(required = true)
    protected FieldDescriptor operationCode;
    protected InitIMAOpeningClaimDescriptor.Offices offices;
    @XmlElement(required = true)
    protected FieldDescriptor officeName;
    @XmlElement(required = true)
    protected FieldDescriptor officeAddress;
    @XmlElement(required = true)
    protected FieldDescriptor tb;
    @XmlElement(required = true)
    protected FieldDescriptor osb;
    @XmlElement(required = true)
    protected FieldDescriptor vsp;

    /**
     * Gets the value of the documentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setDocumentNumber(FieldDescriptor value) {
        this.documentNumber = value;
    }

    /**
     * Gets the value of the documentDate property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getDocumentDate() {
        return documentDate;
    }

    /**
     * Sets the value of the documentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setDocumentDate(FieldDescriptor value) {
        this.documentDate = value;
    }

    /**
     * Gets the value of the imaId property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getImaId() {
        return imaId;
    }

    /**
     * Sets the value of the imaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setImaId(FieldDescriptor value) {
        this.imaId = value;
    }

    /**
     * Gets the value of the imaName property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getImaName() {
        return imaName;
    }

    /**
     * Sets the value of the imaName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setImaName(FieldDescriptor value) {
        this.imaName = value;
    }

    /**
     * Gets the value of the imaType property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getImaType() {
        return imaType;
    }

    /**
     * Sets the value of the imaType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setImaType(FieldDescriptor value) {
        this.imaType = value;
    }

    /**
     * Gets the value of the imaSubType property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getImaSubType() {
        return imaSubType;
    }

    /**
     * Sets the value of the imaSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setImaSubType(FieldDescriptor value) {
        this.imaSubType = value;
    }

    /**
     * Gets the value of the openingDate property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getOpeningDate() {
        return openingDate;
    }

    /**
     * Sets the value of the openingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setOpeningDate(FieldDescriptor value) {
        this.openingDate = value;
    }

    /**
     * Gets the value of the buyCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getBuyCurrency() {
        return buyCurrency;
    }

    /**
     * Sets the value of the buyCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setBuyCurrency(FieldDescriptor value) {
        this.buyCurrency = value;
    }

    /**
     * Gets the value of the buyAmount property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getBuyAmount() {
        return buyAmount;
    }

    /**
     * Sets the value of the buyAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setBuyAmount(FieldDescriptor value) {
        this.buyAmount = value;
    }

    /**
     * Gets the value of the course property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getCourse() {
        return course;
    }

    /**
     * Sets the value of the course property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setCourse(FieldDescriptor value) {
        this.course = value;
    }

    /**
     * Gets the value of the fromResource property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getFromResource() {
        return fromResource;
    }

    /**
     * Sets the value of the fromResource property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setFromResource(FieldDescriptor value) {
        this.fromResource = value;
    }

    /**
     * Gets the value of the sellCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getSellCurrency() {
        return sellCurrency;
    }

    /**
     * Sets the value of the sellCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setSellCurrency(FieldDescriptor value) {
        this.sellCurrency = value;
    }

    /**
     * Gets the value of the sellAmount property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getSellAmount() {
        return sellAmount;
    }

    /**
     * Sets the value of the sellAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setSellAmount(FieldDescriptor value) {
        this.sellAmount = value;
    }

    /**
     * Gets the value of the exactAmount property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getExactAmount() {
        return exactAmount;
    }

    /**
     * Sets the value of the exactAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setExactAmount(FieldDescriptor value) {
        this.exactAmount = value;
    }

    /**
     * Gets the value of the operationCode property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getOperationCode() {
        return operationCode;
    }

    /**
     * Sets the value of the operationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setOperationCode(FieldDescriptor value) {
        this.operationCode = value;
    }

    /**
     * Gets the value of the offices property.
     * 
     * @return
     *     possible object is
     *     {@link InitIMAOpeningClaimDescriptor.Offices }
     *     
     */
    public InitIMAOpeningClaimDescriptor.Offices getOffices() {
        return offices;
    }

    /**
     * Sets the value of the offices property.
     * 
     * @param value
     *     allowed object is
     *     {@link InitIMAOpeningClaimDescriptor.Offices }
     *     
     */
    public void setOffices(InitIMAOpeningClaimDescriptor.Offices value) {
        this.offices = value;
    }

    /**
     * Gets the value of the officeName property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getOfficeName() {
        return officeName;
    }

    /**
     * Sets the value of the officeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setOfficeName(FieldDescriptor value) {
        this.officeName = value;
    }

    /**
     * Gets the value of the officeAddress property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getOfficeAddress() {
        return officeAddress;
    }

    /**
     * Sets the value of the officeAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setOfficeAddress(FieldDescriptor value) {
        this.officeAddress = value;
    }

    /**
     * Gets the value of the tb property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getTb() {
        return tb;
    }

    /**
     * Sets the value of the tb property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setTb(FieldDescriptor value) {
        this.tb = value;
    }

    /**
     * Gets the value of the osb property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getOsb() {
        return osb;
    }

    /**
     * Sets the value of the osb property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setOsb(FieldDescriptor value) {
        this.osb = value;
    }

    /**
     * Gets the value of the vsp property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getVsp() {
        return vsp;
    }

    /**
     * Sets the value of the vsp property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setVsp(FieldDescriptor value) {
        this.vsp = value;
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
     *         &lt;element name="office" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="code" type="{}Resource"/>
     *                   &lt;element name="name" type="{}string"/>
     *                   &lt;element name="address" type="{}string"/>
     *                   &lt;element name="tb" type="{}string"/>
     *                   &lt;element name="osb" type="{}string"/>
     *                   &lt;element name="vsp" type="{}string"/>
     *                   &lt;element name="isIma" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                   &lt;element name="parentSynchKey" type="{}string"/>
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
        "office"
    })
    public static class Offices {

        @XmlElement(required = true)
        protected List<InitIMAOpeningClaimDescriptor.Offices.Office> office;

        /**
         * Gets the value of the office property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the office property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOffice().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InitIMAOpeningClaimDescriptor.Offices.Office }
         * 
         * 
         */
        public List<InitIMAOpeningClaimDescriptor.Offices.Office> getOffice() {
            if (office == null) {
                office = new ArrayList<InitIMAOpeningClaimDescriptor.Offices.Office>();
            }
            return this.office;
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
         *         &lt;element name="code" type="{}Resource"/>
         *         &lt;element name="name" type="{}string"/>
         *         &lt;element name="address" type="{}string"/>
         *         &lt;element name="tb" type="{}string"/>
         *         &lt;element name="osb" type="{}string"/>
         *         &lt;element name="vsp" type="{}string"/>
         *         &lt;element name="isIma" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
         *         &lt;element name="parentSynchKey" type="{}string"/>
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
            "code",
            "name",
            "address",
            "tb",
            "osb",
            "vsp",
            "isIma",
            "parentSynchKey"
        })
        public static class Office {

            @XmlElement(required = true)
            protected String code;
            @XmlElement(required = true)
            protected String name;
            @XmlElement(required = true)
            protected String address;
            @XmlElement(required = true)
            protected String tb;
            @XmlElement(required = true)
            protected String osb;
            @XmlElement(required = true)
            protected String vsp;
            protected boolean isIma;
            @XmlElement(required = true)
            protected String parentSynchKey;

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

            /**
             * Gets the value of the tb property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTb() {
                return tb;
            }

            /**
             * Sets the value of the tb property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTb(String value) {
                this.tb = value;
            }

            /**
             * Gets the value of the osb property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOsb() {
                return osb;
            }

            /**
             * Sets the value of the osb property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOsb(String value) {
                this.osb = value;
            }

            /**
             * Gets the value of the vsp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVsp() {
                return vsp;
            }

            /**
             * Sets the value of the vsp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVsp(String value) {
                this.vsp = value;
            }

            /**
             * Gets the value of the isIma property.
             * 
             */
            public boolean isIsIma() {
                return isIma;
            }

            /**
             * Sets the value of the isIma property.
             * 
             */
            public void setIsIma(boolean value) {
                this.isIma = value;
            }

            /**
             * Gets the value of the parentSynchKey property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParentSynchKey() {
                return parentSynchKey;
            }

            /**
             * Sets the value of the parentSynchKey property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParentSynchKey(String value) {
                this.parentSynchKey = value;
            }

        }

    }

}

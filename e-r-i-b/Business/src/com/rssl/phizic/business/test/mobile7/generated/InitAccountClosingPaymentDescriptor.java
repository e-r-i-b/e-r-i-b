//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.02.20 at 04:13:07 PM MSK 
//


package com.rssl.phizic.business.test.mobile7.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * �������� �������
 * 
 * <p>Java class for InitAccountClosingPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InitAccountClosingPayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="closingDate" type="{}Field"/>
 *         &lt;element name="documentNumber" type="{}Field"/>
 *         &lt;element name="documentDate" type="{}Field"/>
 *         &lt;element name="fromResource" type="{}Field"/>
 *         &lt;element name="toResource" type="{}Field"/>
 *         &lt;element name="chargeOffAmount" type="{}Field"/>
 *         &lt;element name="destinationAmount" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="course" type="{}Field" minOccurs="0"/>
 *         &lt;element name="standartCourse" type="{}Field" minOccurs="0"/>
 *         &lt;element name="gain" type="{}Field" minOccurs="0"/>
 *         &lt;element name="operationCode" type="{}Field" minOccurs="0"/>
 *         &lt;element name="targetMap" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded">
 *                   &lt;element name="target">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="name" type="{}Field"/>
 *                             &lt;element name="comment" type="{}Field" minOccurs="0"/>
 *                             &lt;element name="date" type="{}Field"/>
 *                             &lt;element name="amount" type="{}Field"/>
 *                             &lt;element name="fromResource" type="{}Resource"/>
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
@XmlType(name = "InitAccountClosingPayment", propOrder = {
    "closingDate",
    "documentNumber",
    "documentDate",
    "fromResource",
    "toResource",
    "chargeOffAmount",
    "destinationAmount",
    "course",
    "standartCourse",
    "gain",
    "operationCode",
    "targetMap"
})
public class InitAccountClosingPaymentDescriptor {

    @XmlElement(required = true)
    protected FieldDescriptor closingDate;
    @XmlElement(required = true)
    protected FieldDescriptor documentNumber;
    @XmlElement(required = true)
    protected FieldDescriptor documentDate;
    @XmlElement(required = true)
    protected FieldDescriptor fromResource;
    @XmlElement(required = true)
    protected FieldDescriptor toResource;
    @XmlElement(required = true)
    protected FieldDescriptor chargeOffAmount;
    protected Object destinationAmount;
    protected FieldDescriptor course;
    protected FieldDescriptor standartCourse;
    protected FieldDescriptor gain;
    protected FieldDescriptor operationCode;
    protected InitAccountClosingPaymentDescriptor.TargetMap targetMap;

    /**
     * Gets the value of the closingDate property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getClosingDate() {
        return closingDate;
    }

    /**
     * Sets the value of the closingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setClosingDate(FieldDescriptor value) {
        this.closingDate = value;
    }

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
     * Gets the value of the toResource property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getToResource() {
        return toResource;
    }

    /**
     * Sets the value of the toResource property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setToResource(FieldDescriptor value) {
        this.toResource = value;
    }

    /**
     * Gets the value of the chargeOffAmount property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getChargeOffAmount() {
        return chargeOffAmount;
    }

    /**
     * Sets the value of the chargeOffAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setChargeOffAmount(FieldDescriptor value) {
        this.chargeOffAmount = value;
    }

    /**
     * Gets the value of the destinationAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDestinationAmount() {
        return destinationAmount;
    }

    /**
     * Sets the value of the destinationAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDestinationAmount(Object value) {
        this.destinationAmount = value;
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
     * Gets the value of the standartCourse property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getStandartCourse() {
        return standartCourse;
    }

    /**
     * Sets the value of the standartCourse property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setStandartCourse(FieldDescriptor value) {
        this.standartCourse = value;
    }

    /**
     * Gets the value of the gain property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getGain() {
        return gain;
    }

    /**
     * Sets the value of the gain property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setGain(FieldDescriptor value) {
        this.gain = value;
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
     * Gets the value of the targetMap property.
     * 
     * @return
     *     possible object is
     *     {@link InitAccountClosingPaymentDescriptor.TargetMap }
     *     
     */
    public InitAccountClosingPaymentDescriptor.TargetMap getTargetMap() {
        return targetMap;
    }

    /**
     * Sets the value of the targetMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link InitAccountClosingPaymentDescriptor.TargetMap }
     *     
     */
    public void setTargetMap(InitAccountClosingPaymentDescriptor.TargetMap value) {
        this.targetMap = value;
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
     *       &lt;sequence maxOccurs="unbounded">
     *         &lt;element name="target">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="name" type="{}Field"/>
     *                   &lt;element name="comment" type="{}Field" minOccurs="0"/>
     *                   &lt;element name="date" type="{}Field"/>
     *                   &lt;element name="amount" type="{}Field"/>
     *                   &lt;element name="fromResource" type="{}Resource"/>
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
        "target"
    })
    public static class TargetMap {

        @XmlElement(required = true)
        protected List<InitAccountClosingPaymentDescriptor.TargetMap.Target> target;

        /**
         * Gets the value of the target property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the target property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTarget().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InitAccountClosingPaymentDescriptor.TargetMap.Target }
         * 
         * 
         */
        public List<InitAccountClosingPaymentDescriptor.TargetMap.Target> getTarget() {
            if (target == null) {
                target = new ArrayList<InitAccountClosingPaymentDescriptor.TargetMap.Target>();
            }
            return this.target;
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
         *         &lt;element name="name" type="{}Field"/>
         *         &lt;element name="comment" type="{}Field" minOccurs="0"/>
         *         &lt;element name="date" type="{}Field"/>
         *         &lt;element name="amount" type="{}Field"/>
         *         &lt;element name="fromResource" type="{}Resource"/>
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
            "name",
            "comment",
            "date",
            "amount",
            "fromResource"
        })
        public static class Target {

            @XmlElement(required = true)
            protected FieldDescriptor name;
            protected FieldDescriptor comment;
            @XmlElement(required = true)
            protected FieldDescriptor date;
            @XmlElement(required = true)
            protected FieldDescriptor amount;
            @XmlElement(required = true)
            protected String fromResource;

            /**
             * Gets the value of the name property.
             * 
             * @return
             *     possible object is
             *     {@link FieldDescriptor }
             *     
             */
            public FieldDescriptor getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             * 
             * @param value
             *     allowed object is
             *     {@link FieldDescriptor }
             *     
             */
            public void setName(FieldDescriptor value) {
                this.name = value;
            }

            /**
             * Gets the value of the comment property.
             * 
             * @return
             *     possible object is
             *     {@link FieldDescriptor }
             *     
             */
            public FieldDescriptor getComment() {
                return comment;
            }

            /**
             * Sets the value of the comment property.
             * 
             * @param value
             *     allowed object is
             *     {@link FieldDescriptor }
             *     
             */
            public void setComment(FieldDescriptor value) {
                this.comment = value;
            }

            /**
             * Gets the value of the date property.
             * 
             * @return
             *     possible object is
             *     {@link FieldDescriptor }
             *     
             */
            public FieldDescriptor getDate() {
                return date;
            }

            /**
             * Sets the value of the date property.
             * 
             * @param value
             *     allowed object is
             *     {@link FieldDescriptor }
             *     
             */
            public void setDate(FieldDescriptor value) {
                this.date = value;
            }

            /**
             * Gets the value of the amount property.
             * 
             * @return
             *     possible object is
             *     {@link FieldDescriptor }
             *     
             */
            public FieldDescriptor getAmount() {
                return amount;
            }

            /**
             * Sets the value of the amount property.
             * 
             * @param value
             *     allowed object is
             *     {@link FieldDescriptor }
             *     
             */
            public void setAmount(FieldDescriptor value) {
                this.amount = value;
            }

            /**
             * Gets the value of the fromResource property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFromResource() {
                return fromResource;
            }

            /**
             * Sets the value of the fromResource property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFromResource(String value) {
                this.fromResource = value;
            }

        }

    }

}

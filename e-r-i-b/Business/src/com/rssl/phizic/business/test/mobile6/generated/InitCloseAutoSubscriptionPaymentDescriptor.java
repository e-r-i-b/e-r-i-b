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
 * ������ ����������� (����)
 * 
 * <p>Java class for InitCloseAutoSubscriptionPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InitCloseAutoSubscriptionPayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="receiver">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="name" type="{}Field" minOccurs="0"/>
 *                   &lt;element name="service" type="{}Field" minOccurs="0"/>
 *                   &lt;element name="inn" type="{}Field" minOccurs="0"/>
 *                   &lt;element name="account" type="{}Field" minOccurs="0"/>
 *                   &lt;element name="bank" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="name" type="{}Field"/>
 *                             &lt;element name="bic" type="{}Field"/>
 *                             &lt;element name="corAccount" type="{}Field" minOccurs="0"/>
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
 *         &lt;element name="fromResource" type="{}Field"/>
 *         &lt;element name="paymentDetails">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="externalFields">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="field" type="{}Field" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="operationCode" type="{}Field" minOccurs="0"/>
 *                   &lt;element name="promoCode" type="{}Field" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="autoSubDetails">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="startDate" type="{}Field"/>
 *                   &lt;element name="updateDate" type="{}Field" minOccurs="0"/>
 *                   &lt;element name="name" type="{}Field"/>
 *                   &lt;element name="type" type="{}Field"/>
 *                   &lt;element name="always" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="eventType" type="{}Field"/>
 *                             &lt;element name="nextPayDate" type="{}Field"/>
 *                             &lt;element name="amount" type="{}Field"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="invoice" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="eventType" type="{}Field"/>
 *                             &lt;element name="startDate" type="{}Field"/>
 *                             &lt;element name="amount" type="{}Field"/>
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
@XmlType(name = "InitCloseAutoSubscriptionPayment", propOrder = {
    "receiver",
    "fromResource",
    "paymentDetails",
    "autoSubDetails"
})
public class InitCloseAutoSubscriptionPaymentDescriptor {

    @XmlElement(required = true)
    protected InitCloseAutoSubscriptionPaymentDescriptor.Receiver receiver;
    @XmlElement(required = true)
    protected FieldDescriptor fromResource;
    @XmlElement(required = true)
    protected InitCloseAutoSubscriptionPaymentDescriptor.PaymentDetails paymentDetails;
    @XmlElement(required = true)
    protected InitCloseAutoSubscriptionPaymentDescriptor.AutoSubDetails autoSubDetails;

    /**
     * Gets the value of the receiver property.
     * 
     * @return
     *     possible object is
     *     {@link InitCloseAutoSubscriptionPaymentDescriptor.Receiver }
     *     
     */
    public InitCloseAutoSubscriptionPaymentDescriptor.Receiver getReceiver() {
        return receiver;
    }

    /**
     * Sets the value of the receiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link InitCloseAutoSubscriptionPaymentDescriptor.Receiver }
     *     
     */
    public void setReceiver(InitCloseAutoSubscriptionPaymentDescriptor.Receiver value) {
        this.receiver = value;
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
     * Gets the value of the paymentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link InitCloseAutoSubscriptionPaymentDescriptor.PaymentDetails }
     *     
     */
    public InitCloseAutoSubscriptionPaymentDescriptor.PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    /**
     * Sets the value of the paymentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link InitCloseAutoSubscriptionPaymentDescriptor.PaymentDetails }
     *     
     */
    public void setPaymentDetails(InitCloseAutoSubscriptionPaymentDescriptor.PaymentDetails value) {
        this.paymentDetails = value;
    }

    /**
     * Gets the value of the autoSubDetails property.
     * 
     * @return
     *     possible object is
     *     {@link InitCloseAutoSubscriptionPaymentDescriptor.AutoSubDetails }
     *     
     */
    public InitCloseAutoSubscriptionPaymentDescriptor.AutoSubDetails getAutoSubDetails() {
        return autoSubDetails;
    }

    /**
     * Sets the value of the autoSubDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link InitCloseAutoSubscriptionPaymentDescriptor.AutoSubDetails }
     *     
     */
    public void setAutoSubDetails(InitCloseAutoSubscriptionPaymentDescriptor.AutoSubDetails value) {
        this.autoSubDetails = value;
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
     *         &lt;element name="startDate" type="{}Field"/>
     *         &lt;element name="updateDate" type="{}Field" minOccurs="0"/>
     *         &lt;element name="name" type="{}Field"/>
     *         &lt;element name="type" type="{}Field"/>
     *         &lt;element name="always" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="eventType" type="{}Field"/>
     *                   &lt;element name="nextPayDate" type="{}Field"/>
     *                   &lt;element name="amount" type="{}Field"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="invoice" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="eventType" type="{}Field"/>
     *                   &lt;element name="startDate" type="{}Field"/>
     *                   &lt;element name="amount" type="{}Field"/>
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
        "startDate",
        "updateDate",
        "name",
        "type",
        "always",
        "invoice"
    })
    public static class AutoSubDetails {

        @XmlElement(required = true)
        protected FieldDescriptor startDate;
        protected FieldDescriptor updateDate;
        @XmlElement(required = true)
        protected FieldDescriptor name;
        @XmlElement(required = true)
        protected FieldDescriptor type;
        protected InitCloseAutoSubscriptionPaymentDescriptor.AutoSubDetails.Always always;
        protected InitCloseAutoSubscriptionPaymentDescriptor.AutoSubDetails.Invoice invoice;

        /**
         * Gets the value of the startDate property.
         * 
         * @return
         *     possible object is
         *     {@link FieldDescriptor }
         *     
         */
        public FieldDescriptor getStartDate() {
            return startDate;
        }

        /**
         * Sets the value of the startDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link FieldDescriptor }
         *     
         */
        public void setStartDate(FieldDescriptor value) {
            this.startDate = value;
        }

        /**
         * Gets the value of the updateDate property.
         * 
         * @return
         *     possible object is
         *     {@link FieldDescriptor }
         *     
         */
        public FieldDescriptor getUpdateDate() {
            return updateDate;
        }

        /**
         * Sets the value of the updateDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link FieldDescriptor }
         *     
         */
        public void setUpdateDate(FieldDescriptor value) {
            this.updateDate = value;
        }

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
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link FieldDescriptor }
         *     
         */
        public FieldDescriptor getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link FieldDescriptor }
         *     
         */
        public void setType(FieldDescriptor value) {
            this.type = value;
        }

        /**
         * Gets the value of the always property.
         * 
         * @return
         *     possible object is
         *     {@link InitCloseAutoSubscriptionPaymentDescriptor.AutoSubDetails.Always }
         *     
         */
        public InitCloseAutoSubscriptionPaymentDescriptor.AutoSubDetails.Always getAlways() {
            return always;
        }

        /**
         * Sets the value of the always property.
         * 
         * @param value
         *     allowed object is
         *     {@link InitCloseAutoSubscriptionPaymentDescriptor.AutoSubDetails.Always }
         *     
         */
        public void setAlways(InitCloseAutoSubscriptionPaymentDescriptor.AutoSubDetails.Always value) {
            this.always = value;
        }

        /**
         * Gets the value of the invoice property.
         * 
         * @return
         *     possible object is
         *     {@link InitCloseAutoSubscriptionPaymentDescriptor.AutoSubDetails.Invoice }
         *     
         */
        public InitCloseAutoSubscriptionPaymentDescriptor.AutoSubDetails.Invoice getInvoice() {
            return invoice;
        }

        /**
         * Sets the value of the invoice property.
         * 
         * @param value
         *     allowed object is
         *     {@link InitCloseAutoSubscriptionPaymentDescriptor.AutoSubDetails.Invoice }
         *     
         */
        public void setInvoice(InitCloseAutoSubscriptionPaymentDescriptor.AutoSubDetails.Invoice value) {
            this.invoice = value;
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
         *         &lt;element name="eventType" type="{}Field"/>
         *         &lt;element name="nextPayDate" type="{}Field"/>
         *         &lt;element name="amount" type="{}Field"/>
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
            "eventType",
            "nextPayDate",
            "amount"
        })
        public static class Always {

            @XmlElement(required = true)
            protected FieldDescriptor eventType;
            @XmlElement(required = true)
            protected FieldDescriptor nextPayDate;
            @XmlElement(required = true)
            protected FieldDescriptor amount;

            /**
             * Gets the value of the eventType property.
             * 
             * @return
             *     possible object is
             *     {@link FieldDescriptor }
             *     
             */
            public FieldDescriptor getEventType() {
                return eventType;
            }

            /**
             * Sets the value of the eventType property.
             * 
             * @param value
             *     allowed object is
             *     {@link FieldDescriptor }
             *     
             */
            public void setEventType(FieldDescriptor value) {
                this.eventType = value;
            }

            /**
             * Gets the value of the nextPayDate property.
             * 
             * @return
             *     possible object is
             *     {@link FieldDescriptor }
             *     
             */
            public FieldDescriptor getNextPayDate() {
                return nextPayDate;
            }

            /**
             * Sets the value of the nextPayDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link FieldDescriptor }
             *     
             */
            public void setNextPayDate(FieldDescriptor value) {
                this.nextPayDate = value;
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
         *         &lt;element name="eventType" type="{}Field"/>
         *         &lt;element name="startDate" type="{}Field"/>
         *         &lt;element name="amount" type="{}Field"/>
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
            "eventType",
            "startDate",
            "amount"
        })
        public static class Invoice {

            @XmlElement(required = true)
            protected FieldDescriptor eventType;
            @XmlElement(required = true)
            protected FieldDescriptor startDate;
            @XmlElement(required = true)
            protected FieldDescriptor amount;

            /**
             * Gets the value of the eventType property.
             * 
             * @return
             *     possible object is
             *     {@link FieldDescriptor }
             *     
             */
            public FieldDescriptor getEventType() {
                return eventType;
            }

            /**
             * Sets the value of the eventType property.
             * 
             * @param value
             *     allowed object is
             *     {@link FieldDescriptor }
             *     
             */
            public void setEventType(FieldDescriptor value) {
                this.eventType = value;
            }

            /**
             * Gets the value of the startDate property.
             * 
             * @return
             *     possible object is
             *     {@link FieldDescriptor }
             *     
             */
            public FieldDescriptor getStartDate() {
                return startDate;
            }

            /**
             * Sets the value of the startDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link FieldDescriptor }
             *     
             */
            public void setStartDate(FieldDescriptor value) {
                this.startDate = value;
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
     *         &lt;element name="externalFields">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="field" type="{}Field" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="operationCode" type="{}Field" minOccurs="0"/>
     *         &lt;element name="promoCode" type="{}Field" minOccurs="0"/>
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
        "externalFields",
        "operationCode",
        "promoCode"
    })
    public static class PaymentDetails {

        @XmlElement(required = true)
        protected InitCloseAutoSubscriptionPaymentDescriptor.PaymentDetails.ExternalFields externalFields;
        protected FieldDescriptor operationCode;
        protected FieldDescriptor promoCode;

        /**
         * Gets the value of the externalFields property.
         * 
         * @return
         *     possible object is
         *     {@link InitCloseAutoSubscriptionPaymentDescriptor.PaymentDetails.ExternalFields }
         *     
         */
        public InitCloseAutoSubscriptionPaymentDescriptor.PaymentDetails.ExternalFields getExternalFields() {
            return externalFields;
        }

        /**
         * Sets the value of the externalFields property.
         * 
         * @param value
         *     allowed object is
         *     {@link InitCloseAutoSubscriptionPaymentDescriptor.PaymentDetails.ExternalFields }
         *     
         */
        public void setExternalFields(InitCloseAutoSubscriptionPaymentDescriptor.PaymentDetails.ExternalFields value) {
            this.externalFields = value;
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
         * Gets the value of the promoCode property.
         * 
         * @return
         *     possible object is
         *     {@link FieldDescriptor }
         *     
         */
        public FieldDescriptor getPromoCode() {
            return promoCode;
        }

        /**
         * Sets the value of the promoCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link FieldDescriptor }
         *     
         */
        public void setPromoCode(FieldDescriptor value) {
            this.promoCode = value;
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
         *         &lt;element name="field" type="{}Field" maxOccurs="unbounded"/>
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
            "field"
        })
        public static class ExternalFields {

            @XmlElement(required = true)
            protected List<FieldDescriptor> field;

            /**
             * Gets the value of the field property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the field property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getField().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link FieldDescriptor }
             * 
             * 
             */
            public List<FieldDescriptor> getField() {
                if (field == null) {
                    field = new ArrayList<FieldDescriptor>();
                }
                return this.field;
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
     *         &lt;element name="name" type="{}Field" minOccurs="0"/>
     *         &lt;element name="service" type="{}Field" minOccurs="0"/>
     *         &lt;element name="inn" type="{}Field" minOccurs="0"/>
     *         &lt;element name="account" type="{}Field" minOccurs="0"/>
     *         &lt;element name="bank" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="name" type="{}Field"/>
     *                   &lt;element name="bic" type="{}Field"/>
     *                   &lt;element name="corAccount" type="{}Field" minOccurs="0"/>
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
        "name",
        "service",
        "inn",
        "account",
        "bank"
    })
    public static class Receiver {

        protected FieldDescriptor name;
        protected FieldDescriptor service;
        protected FieldDescriptor inn;
        protected FieldDescriptor account;
        protected InitCloseAutoSubscriptionPaymentDescriptor.Receiver.Bank bank;

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
         * Gets the value of the service property.
         * 
         * @return
         *     possible object is
         *     {@link FieldDescriptor }
         *     
         */
        public FieldDescriptor getService() {
            return service;
        }

        /**
         * Sets the value of the service property.
         * 
         * @param value
         *     allowed object is
         *     {@link FieldDescriptor }
         *     
         */
        public void setService(FieldDescriptor value) {
            this.service = value;
        }

        /**
         * Gets the value of the inn property.
         * 
         * @return
         *     possible object is
         *     {@link FieldDescriptor }
         *     
         */
        public FieldDescriptor getInn() {
            return inn;
        }

        /**
         * Sets the value of the inn property.
         * 
         * @param value
         *     allowed object is
         *     {@link FieldDescriptor }
         *     
         */
        public void setInn(FieldDescriptor value) {
            this.inn = value;
        }

        /**
         * Gets the value of the account property.
         * 
         * @return
         *     possible object is
         *     {@link FieldDescriptor }
         *     
         */
        public FieldDescriptor getAccount() {
            return account;
        }

        /**
         * Sets the value of the account property.
         * 
         * @param value
         *     allowed object is
         *     {@link FieldDescriptor }
         *     
         */
        public void setAccount(FieldDescriptor value) {
            this.account = value;
        }

        /**
         * Gets the value of the bank property.
         * 
         * @return
         *     possible object is
         *     {@link InitCloseAutoSubscriptionPaymentDescriptor.Receiver.Bank }
         *     
         */
        public InitCloseAutoSubscriptionPaymentDescriptor.Receiver.Bank getBank() {
            return bank;
        }

        /**
         * Sets the value of the bank property.
         * 
         * @param value
         *     allowed object is
         *     {@link InitCloseAutoSubscriptionPaymentDescriptor.Receiver.Bank }
         *     
         */
        public void setBank(InitCloseAutoSubscriptionPaymentDescriptor.Receiver.Bank value) {
            this.bank = value;
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
         *         &lt;element name="bic" type="{}Field"/>
         *         &lt;element name="corAccount" type="{}Field" minOccurs="0"/>
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
            "bic",
            "corAccount"
        })
        public static class Bank {

            @XmlElement(required = true)
            protected FieldDescriptor name;
            @XmlElement(required = true)
            protected FieldDescriptor bic;
            protected FieldDescriptor corAccount;

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
             * Gets the value of the bic property.
             * 
             * @return
             *     possible object is
             *     {@link FieldDescriptor }
             *     
             */
            public FieldDescriptor getBic() {
                return bic;
            }

            /**
             * Sets the value of the bic property.
             * 
             * @param value
             *     allowed object is
             *     {@link FieldDescriptor }
             *     
             */
            public void setBic(FieldDescriptor value) {
                this.bic = value;
            }

            /**
             * Gets the value of the corAccount property.
             * 
             * @return
             *     possible object is
             *     {@link FieldDescriptor }
             *     
             */
            public FieldDescriptor getCorAccount() {
                return corAccount;
            }

            /**
             * Sets the value of the corAccount property.
             * 
             * @param value
             *     allowed object is
             *     {@link FieldDescriptor }
             *     
             */
            public void setCorAccount(FieldDescriptor value) {
                this.corAccount = value;
            }

        }

    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.09.13 at 03:37:37 PM MSD 
//


package com.rssl.phizic.business.test.mobile5.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * �������������� ����������� iqwave
 * 
 * <p>Java class for DocEditAutoPaymentPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocEditAutoPaymentPayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="receiverName" type="{}Field"/>
 *         &lt;element name="fromResource" type="{}Field"/>
 *         &lt;element name="requisiteName" type="{}Field"/>
 *         &lt;element name="requisite" type="{}Field"/>
 *         &lt;element name="amount" type="{}Field" minOccurs="0"/>
 *         &lt;element name="autoPaymentParameters">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="executionEventType" type="{}Field"/>
 *                   &lt;element name="periodic" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="autoPaymentStartDate" type="{}Field"/>
 *                             &lt;element name="firstPaymentDate" type="{}Field"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="reduseOfBalance" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="autoPaymentFloorLimit" type="{}Field"/>
 *                             &lt;element name="autoPaymentTotalAmountLimit" type="{}Field" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="byInvoice" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="autoPaymentFloorLimit" type="{}Field"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="autoPaymentName" type="{}Field"/>
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
@XmlType(name = "DocEditAutoPaymentPayment", propOrder = {
    "receiverName",
    "fromResource",
    "requisiteName",
    "requisite",
    "amount",
    "autoPaymentParameters"
})
public class DocEditAutoPaymentPaymentDescriptor {

    @XmlElement(required = true)
    protected FieldDescriptor receiverName;
    @XmlElement(required = true)
    protected FieldDescriptor fromResource;
    @XmlElement(required = true)
    protected FieldDescriptor requisiteName;
    @XmlElement(required = true)
    protected FieldDescriptor requisite;
    protected FieldDescriptor amount;
    @XmlElement(required = true)
    protected DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters autoPaymentParameters;

    /**
     * Gets the value of the receiverName property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getReceiverName() {
        return receiverName;
    }

    /**
     * Sets the value of the receiverName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setReceiverName(FieldDescriptor value) {
        this.receiverName = value;
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
     * Gets the value of the requisiteName property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getRequisiteName() {
        return requisiteName;
    }

    /**
     * Sets the value of the requisiteName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setRequisiteName(FieldDescriptor value) {
        this.requisiteName = value;
    }

    /**
     * Gets the value of the requisite property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getRequisite() {
        return requisite;
    }

    /**
     * Sets the value of the requisite property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setRequisite(FieldDescriptor value) {
        this.requisite = value;
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
     * Gets the value of the autoPaymentParameters property.
     * 
     * @return
     *     possible object is
     *     {@link DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters }
     *     
     */
    public DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters getAutoPaymentParameters() {
        return autoPaymentParameters;
    }

    /**
     * Sets the value of the autoPaymentParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters }
     *     
     */
    public void setAutoPaymentParameters(DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters value) {
        this.autoPaymentParameters = value;
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
     *         &lt;element name="executionEventType" type="{}Field"/>
     *         &lt;element name="periodic" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="autoPaymentStartDate" type="{}Field"/>
     *                   &lt;element name="firstPaymentDate" type="{}Field"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="reduseOfBalance" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="autoPaymentFloorLimit" type="{}Field"/>
     *                   &lt;element name="autoPaymentTotalAmountLimit" type="{}Field" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="byInvoice" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="autoPaymentFloorLimit" type="{}Field"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="autoPaymentName" type="{}Field"/>
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
        "executionEventType",
        "periodic",
        "reduseOfBalance",
        "byInvoice",
        "autoPaymentName"
    })
    public static class AutoPaymentParameters {

        @XmlElement(required = true)
        protected FieldDescriptor executionEventType;
        protected DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters.Periodic periodic;
        protected DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters.ReduseOfBalance reduseOfBalance;
        protected DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters.ByInvoice byInvoice;
        @XmlElement(required = true)
        protected FieldDescriptor autoPaymentName;

        /**
         * Gets the value of the executionEventType property.
         * 
         * @return
         *     possible object is
         *     {@link FieldDescriptor }
         *     
         */
        public FieldDescriptor getExecutionEventType() {
            return executionEventType;
        }

        /**
         * Sets the value of the executionEventType property.
         * 
         * @param value
         *     allowed object is
         *     {@link FieldDescriptor }
         *     
         */
        public void setExecutionEventType(FieldDescriptor value) {
            this.executionEventType = value;
        }

        /**
         * Gets the value of the periodic property.
         * 
         * @return
         *     possible object is
         *     {@link DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters.Periodic }
         *     
         */
        public DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters.Periodic getPeriodic() {
            return periodic;
        }

        /**
         * Sets the value of the periodic property.
         * 
         * @param value
         *     allowed object is
         *     {@link DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters.Periodic }
         *     
         */
        public void setPeriodic(DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters.Periodic value) {
            this.periodic = value;
        }

        /**
         * Gets the value of the reduseOfBalance property.
         * 
         * @return
         *     possible object is
         *     {@link DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters.ReduseOfBalance }
         *     
         */
        public DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters.ReduseOfBalance getReduseOfBalance() {
            return reduseOfBalance;
        }

        /**
         * Sets the value of the reduseOfBalance property.
         * 
         * @param value
         *     allowed object is
         *     {@link DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters.ReduseOfBalance }
         *     
         */
        public void setReduseOfBalance(DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters.ReduseOfBalance value) {
            this.reduseOfBalance = value;
        }

        /**
         * Gets the value of the byInvoice property.
         * 
         * @return
         *     possible object is
         *     {@link DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters.ByInvoice }
         *     
         */
        public DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters.ByInvoice getByInvoice() {
            return byInvoice;
        }

        /**
         * Sets the value of the byInvoice property.
         * 
         * @param value
         *     allowed object is
         *     {@link DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters.ByInvoice }
         *     
         */
        public void setByInvoice(DocEditAutoPaymentPaymentDescriptor.AutoPaymentParameters.ByInvoice value) {
            this.byInvoice = value;
        }

        /**
         * Gets the value of the autoPaymentName property.
         * 
         * @return
         *     possible object is
         *     {@link FieldDescriptor }
         *     
         */
        public FieldDescriptor getAutoPaymentName() {
            return autoPaymentName;
        }

        /**
         * Sets the value of the autoPaymentName property.
         * 
         * @param value
         *     allowed object is
         *     {@link FieldDescriptor }
         *     
         */
        public void setAutoPaymentName(FieldDescriptor value) {
            this.autoPaymentName = value;
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
         *         &lt;element name="autoPaymentFloorLimit" type="{}Field"/>
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
            "autoPaymentFloorLimit"
        })
        public static class ByInvoice {

            @XmlElement(required = true)
            protected FieldDescriptor autoPaymentFloorLimit;

            /**
             * Gets the value of the autoPaymentFloorLimit property.
             * 
             * @return
             *     possible object is
             *     {@link FieldDescriptor }
             *     
             */
            public FieldDescriptor getAutoPaymentFloorLimit() {
                return autoPaymentFloorLimit;
            }

            /**
             * Sets the value of the autoPaymentFloorLimit property.
             * 
             * @param value
             *     allowed object is
             *     {@link FieldDescriptor }
             *     
             */
            public void setAutoPaymentFloorLimit(FieldDescriptor value) {
                this.autoPaymentFloorLimit = value;
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
         *         &lt;element name="autoPaymentStartDate" type="{}Field"/>
         *         &lt;element name="firstPaymentDate" type="{}Field"/>
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
            "autoPaymentStartDate",
            "firstPaymentDate"
        })
        public static class Periodic {

            @XmlElement(required = true)
            protected FieldDescriptor autoPaymentStartDate;
            @XmlElement(required = true)
            protected FieldDescriptor firstPaymentDate;

            /**
             * Gets the value of the autoPaymentStartDate property.
             * 
             * @return
             *     possible object is
             *     {@link FieldDescriptor }
             *     
             */
            public FieldDescriptor getAutoPaymentStartDate() {
                return autoPaymentStartDate;
            }

            /**
             * Sets the value of the autoPaymentStartDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link FieldDescriptor }
             *     
             */
            public void setAutoPaymentStartDate(FieldDescriptor value) {
                this.autoPaymentStartDate = value;
            }

            /**
             * Gets the value of the firstPaymentDate property.
             * 
             * @return
             *     possible object is
             *     {@link FieldDescriptor }
             *     
             */
            public FieldDescriptor getFirstPaymentDate() {
                return firstPaymentDate;
            }

            /**
             * Sets the value of the firstPaymentDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link FieldDescriptor }
             *     
             */
            public void setFirstPaymentDate(FieldDescriptor value) {
                this.firstPaymentDate = value;
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
         *         &lt;element name="autoPaymentFloorLimit" type="{}Field"/>
         *         &lt;element name="autoPaymentTotalAmountLimit" type="{}Field" minOccurs="0"/>
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
            "autoPaymentFloorLimit",
            "autoPaymentTotalAmountLimit"
        })
        public static class ReduseOfBalance {

            @XmlElement(required = true)
            protected FieldDescriptor autoPaymentFloorLimit;
            protected FieldDescriptor autoPaymentTotalAmountLimit;

            /**
             * Gets the value of the autoPaymentFloorLimit property.
             * 
             * @return
             *     possible object is
             *     {@link FieldDescriptor }
             *     
             */
            public FieldDescriptor getAutoPaymentFloorLimit() {
                return autoPaymentFloorLimit;
            }

            /**
             * Sets the value of the autoPaymentFloorLimit property.
             * 
             * @param value
             *     allowed object is
             *     {@link FieldDescriptor }
             *     
             */
            public void setAutoPaymentFloorLimit(FieldDescriptor value) {
                this.autoPaymentFloorLimit = value;
            }

            /**
             * Gets the value of the autoPaymentTotalAmountLimit property.
             * 
             * @return
             *     possible object is
             *     {@link FieldDescriptor }
             *     
             */
            public FieldDescriptor getAutoPaymentTotalAmountLimit() {
                return autoPaymentTotalAmountLimit;
            }

            /**
             * Sets the value of the autoPaymentTotalAmountLimit property.
             * 
             * @param value
             *     allowed object is
             *     {@link FieldDescriptor }
             *     
             */
            public void setAutoPaymentTotalAmountLimit(FieldDescriptor value) {
                this.autoPaymentTotalAmountLimit = value;
            }

        }

    }

}

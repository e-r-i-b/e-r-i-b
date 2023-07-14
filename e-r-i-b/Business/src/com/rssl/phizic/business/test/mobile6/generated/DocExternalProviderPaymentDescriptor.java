//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.09.13 at 03:37:43 PM MSD 
//


package com.rssl.phizic.business.test.mobile6.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ������ ������ ��������-��������
 * 
 * <p>Java class for DocExternalProviderPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocExternalProviderPayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentNumber" type="{}Field"/>
 *         &lt;element name="receiverName" type="{}Field"/>
 *         &lt;element name="fromResource" type="{}Field"/>
 *         &lt;element name="amount" type="{}Field"/>
 *         &lt;element name="currency" type="{}Field"/>
 *         &lt;element name="recIdentifier" type="{}Field"/>
 *         &lt;element name="itemList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="item" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="name" type="{}string" minOccurs="0"/>
 *                             &lt;element name="description" type="{}string" minOccurs="0"/>
 *                             &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                             &lt;element name="price" type="{}Money" minOccurs="0"/>
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
 *         &lt;element name="refundList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="refund" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="itemList" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="item" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="name" type="{}string" minOccurs="0"/>
 *                                                 &lt;element name="description" type="{}string" minOccurs="0"/>
 *                                                 &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                                                 &lt;element name="price" type="{}Money" minOccurs="0"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="total" type="{}Money"/>
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
@XmlType(name = "DocExternalProviderPayment", propOrder = {
    "documentNumber",
    "receiverName",
    "fromResource",
    "amount",
    "currency",
    "recIdentifier",
    "itemList",
    "refundList",
    "promoCode"
})
public class DocExternalProviderPaymentDescriptor {

    @XmlElement(required = true)
    protected FieldDescriptor documentNumber;
    @XmlElement(required = true)
    protected FieldDescriptor receiverName;
    @XmlElement(required = true)
    protected FieldDescriptor fromResource;
    @XmlElement(required = true)
    protected FieldDescriptor amount;
    @XmlElement(required = true)
    protected FieldDescriptor currency;
    @XmlElement(required = true)
    protected FieldDescriptor recIdentifier;
    protected DocExternalProviderPaymentDescriptor.ItemList itemList;
    protected DocExternalProviderPaymentDescriptor.RefundList refundList;
    protected FieldDescriptor promoCode;

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
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setCurrency(FieldDescriptor value) {
        this.currency = value;
    }

    /**
     * Gets the value of the recIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getRecIdentifier() {
        return recIdentifier;
    }

    /**
     * Sets the value of the recIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setRecIdentifier(FieldDescriptor value) {
        this.recIdentifier = value;
    }

    /**
     * Gets the value of the itemList property.
     * 
     * @return
     *     possible object is
     *     {@link DocExternalProviderPaymentDescriptor.ItemList }
     *     
     */
    public DocExternalProviderPaymentDescriptor.ItemList getItemList() {
        return itemList;
    }

    /**
     * Sets the value of the itemList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocExternalProviderPaymentDescriptor.ItemList }
     *     
     */
    public void setItemList(DocExternalProviderPaymentDescriptor.ItemList value) {
        this.itemList = value;
    }

    /**
     * Gets the value of the refundList property.
     * 
     * @return
     *     possible object is
     *     {@link DocExternalProviderPaymentDescriptor.RefundList }
     *     
     */
    public DocExternalProviderPaymentDescriptor.RefundList getRefundList() {
        return refundList;
    }

    /**
     * Sets the value of the refundList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocExternalProviderPaymentDescriptor.RefundList }
     *     
     */
    public void setRefundList(DocExternalProviderPaymentDescriptor.RefundList value) {
        this.refundList = value;
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
     *         &lt;element name="item" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="name" type="{}string" minOccurs="0"/>
     *                   &lt;element name="description" type="{}string" minOccurs="0"/>
     *                   &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                   &lt;element name="price" type="{}Money" minOccurs="0"/>
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
        "item"
    })
    public static class ItemList {

        @XmlElement(required = true)
        protected List<DocExternalProviderPaymentDescriptor.ItemList.Item> item;

        /**
         * Gets the value of the item property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the item property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DocExternalProviderPaymentDescriptor.ItemList.Item }
         * 
         * 
         */
        public List<DocExternalProviderPaymentDescriptor.ItemList.Item> getItem() {
            if (item == null) {
                item = new ArrayList<DocExternalProviderPaymentDescriptor.ItemList.Item>();
            }
            return this.item;
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
         *         &lt;element name="name" type="{}string" minOccurs="0"/>
         *         &lt;element name="description" type="{}string" minOccurs="0"/>
         *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
         *         &lt;element name="price" type="{}Money" minOccurs="0"/>
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
            "description",
            "count",
            "price"
        })
        public static class Item {

            protected String name;
            protected String description;
            protected BigInteger count;
            protected MoneyDescriptor price;

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
             * Gets the value of the description property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescription() {
                return description;
            }

            /**
             * Sets the value of the description property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescription(String value) {
                this.description = value;
            }

            /**
             * Gets the value of the count property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getCount() {
                return count;
            }

            /**
             * Sets the value of the count property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setCount(BigInteger value) {
                this.count = value;
            }

            /**
             * Gets the value of the price property.
             * 
             * @return
             *     possible object is
             *     {@link MoneyDescriptor }
             *     
             */
            public MoneyDescriptor getPrice() {
                return price;
            }

            /**
             * Sets the value of the price property.
             * 
             * @param value
             *     allowed object is
             *     {@link MoneyDescriptor }
             *     
             */
            public void setPrice(MoneyDescriptor value) {
                this.price = value;
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
     *         &lt;element name="refund" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="itemList" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="item" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="name" type="{}string" minOccurs="0"/>
     *                                       &lt;element name="description" type="{}string" minOccurs="0"/>
     *                                       &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                                       &lt;element name="price" type="{}Money" minOccurs="0"/>
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
     *                   &lt;element name="total" type="{}Money"/>
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
        "refund"
    })
    public static class RefundList {

        @XmlElement(required = true)
        protected List<DocExternalProviderPaymentDescriptor.RefundList.Refund> refund;

        /**
         * Gets the value of the refund property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the refund property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRefund().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DocExternalProviderPaymentDescriptor.RefundList.Refund }
         * 
         * 
         */
        public List<DocExternalProviderPaymentDescriptor.RefundList.Refund> getRefund() {
            if (refund == null) {
                refund = new ArrayList<DocExternalProviderPaymentDescriptor.RefundList.Refund>();
            }
            return this.refund;
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
         *         &lt;element name="itemList" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="item" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="name" type="{}string" minOccurs="0"/>
         *                             &lt;element name="description" type="{}string" minOccurs="0"/>
         *                             &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
         *                             &lt;element name="price" type="{}Money" minOccurs="0"/>
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
         *         &lt;element name="total" type="{}Money"/>
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
            "itemList",
            "total"
        })
        public static class Refund {

            protected DocExternalProviderPaymentDescriptor.RefundList.Refund.ItemList itemList;
            @XmlElement(required = true)
            protected MoneyDescriptor total;

            /**
             * Gets the value of the itemList property.
             * 
             * @return
             *     possible object is
             *     {@link DocExternalProviderPaymentDescriptor.RefundList.Refund.ItemList }
             *     
             */
            public DocExternalProviderPaymentDescriptor.RefundList.Refund.ItemList getItemList() {
                return itemList;
            }

            /**
             * Sets the value of the itemList property.
             * 
             * @param value
             *     allowed object is
             *     {@link DocExternalProviderPaymentDescriptor.RefundList.Refund.ItemList }
             *     
             */
            public void setItemList(DocExternalProviderPaymentDescriptor.RefundList.Refund.ItemList value) {
                this.itemList = value;
            }

            /**
             * Gets the value of the total property.
             * 
             * @return
             *     possible object is
             *     {@link MoneyDescriptor }
             *     
             */
            public MoneyDescriptor getTotal() {
                return total;
            }

            /**
             * Sets the value of the total property.
             * 
             * @param value
             *     allowed object is
             *     {@link MoneyDescriptor }
             *     
             */
            public void setTotal(MoneyDescriptor value) {
                this.total = value;
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
             *         &lt;element name="item" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="name" type="{}string" minOccurs="0"/>
             *                   &lt;element name="description" type="{}string" minOccurs="0"/>
             *                   &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
             *                   &lt;element name="price" type="{}Money" minOccurs="0"/>
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
                "item"
            })
            public static class ItemList {

                @XmlElement(required = true)
                protected List<DocExternalProviderPaymentDescriptor.RefundList.Refund.ItemList.Item> item;

                /**
                 * Gets the value of the item property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the item property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getItem().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link DocExternalProviderPaymentDescriptor.RefundList.Refund.ItemList.Item }
                 * 
                 * 
                 */
                public List<DocExternalProviderPaymentDescriptor.RefundList.Refund.ItemList.Item> getItem() {
                    if (item == null) {
                        item = new ArrayList<DocExternalProviderPaymentDescriptor.RefundList.Refund.ItemList.Item>();
                    }
                    return this.item;
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
                 *         &lt;element name="name" type="{}string" minOccurs="0"/>
                 *         &lt;element name="description" type="{}string" minOccurs="0"/>
                 *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
                 *         &lt;element name="price" type="{}Money" minOccurs="0"/>
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
                    "description",
                    "count",
                    "price"
                })
                public static class Item {

                    protected String name;
                    protected String description;
                    protected BigInteger count;
                    protected MoneyDescriptor price;

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
                     * Gets the value of the description property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDescription() {
                        return description;
                    }

                    /**
                     * Sets the value of the description property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDescription(String value) {
                        this.description = value;
                    }

                    /**
                     * Gets the value of the count property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigInteger }
                     *     
                     */
                    public BigInteger getCount() {
                        return count;
                    }

                    /**
                     * Sets the value of the count property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigInteger }
                     *     
                     */
                    public void setCount(BigInteger value) {
                        this.count = value;
                    }

                    /**
                     * Gets the value of the price property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link MoneyDescriptor }
                     *     
                     */
                    public MoneyDescriptor getPrice() {
                        return price;
                    }

                    /**
                     * Sets the value of the price property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link MoneyDescriptor }
                     *     
                     */
                    public void setPrice(MoneyDescriptor value) {
                        this.price = value;
                    }

                }

            }

        }

    }

}

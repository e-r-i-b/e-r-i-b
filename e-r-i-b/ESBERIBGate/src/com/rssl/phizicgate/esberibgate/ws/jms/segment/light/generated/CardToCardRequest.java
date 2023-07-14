
package com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CardToCardRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardToCardRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Head" type="{}HeadRequestType"/>
 *         &lt;element name="Body">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DebitCard">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="CardNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="CurrCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CreditCard" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Summa" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Properties" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Sign" type="{}SignType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardToCardRequestType", propOrder = {
    "head",
    "body",
    "sign"
})
@XmlRootElement(name = "CardToCardRequest")
public class CardToCardRequest {

    @XmlElement(name = "Head", required = true)
    protected HeadRequestType head;
    @XmlElement(name = "Body", required = true)
    protected CardToCardRequest.Body body;
    @XmlElement(name = "Sign")
    protected SignType sign;

    /**
     * Gets the value of the head property.
     * 
     * @return
     *     possible object is
     *     {@link HeadRequestType }
     *     
     */
    public HeadRequestType getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeadRequestType }
     *     
     */
    public void setHead(HeadRequestType value) {
        this.head = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link CardToCardRequest.Body }
     *     
     */
    public CardToCardRequest.Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardToCardRequest.Body }
     *     
     */
    public void setBody(CardToCardRequest.Body value) {
        this.body = value;
    }

    /**
     * Gets the value of the sign property.
     * 
     * @return
     *     possible object is
     *     {@link SignType }
     *     
     */
    public SignType getSign() {
        return sign;
    }

    /**
     * Sets the value of the sign property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignType }
     *     
     */
    public void setSign(SignType value) {
        this.sign = value;
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
     *         &lt;element name="DebitCard">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="CardNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="CurrCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="CreditCard" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Summa" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Properties" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "debitCard",
        "currCode",
        "creditCard",
        "summa",
        "properties"
    })
    public static class Body {

        @XmlElement(name = "DebitCard", required = true)
        protected CardToCardRequest.Body.DebitCard debitCard;
        @XmlElement(name = "CurrCode", required = true)
        protected String currCode;
        @XmlElement(name = "CreditCard", required = true)
        protected String creditCard;
        @XmlElement(name = "Summa", required = true)
        protected String summa;
        @XmlElement(name = "Properties")
        protected String properties;

        /**
         * Gets the value of the debitCard property.
         * 
         * @return
         *     possible object is
         *     {@link CardToCardRequest.Body.DebitCard }
         *     
         */
        public CardToCardRequest.Body.DebitCard getDebitCard() {
            return debitCard;
        }

        /**
         * Sets the value of the debitCard property.
         * 
         * @param value
         *     allowed object is
         *     {@link CardToCardRequest.Body.DebitCard }
         *     
         */
        public void setDebitCard(CardToCardRequest.Body.DebitCard value) {
            this.debitCard = value;
        }

        /**
         * Gets the value of the currCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCurrCode() {
            return currCode;
        }

        /**
         * Sets the value of the currCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCurrCode(String value) {
            this.currCode = value;
        }

        /**
         * Gets the value of the creditCard property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCreditCard() {
            return creditCard;
        }

        /**
         * Sets the value of the creditCard property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCreditCard(String value) {
            this.creditCard = value;
        }

        /**
         * Gets the value of the summa property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSumma() {
            return summa;
        }

        /**
         * Sets the value of the summa property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSumma(String value) {
            this.summa = value;
        }

        /**
         * Gets the value of the properties property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProperties() {
            return properties;
        }

        /**
         * Sets the value of the properties property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProperties(String value) {
            this.properties = value;
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
         *         &lt;element name="CardNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "cardNumber",
            "endDate"
        })
        public static class DebitCard {

            @XmlElement(name = "CardNumber", required = true)
            protected String cardNumber;
            @XmlElement(name = "EndDate")
            protected String endDate;

            /**
             * Gets the value of the cardNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCardNumber() {
                return cardNumber;
            }

            /**
             * Sets the value of the cardNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCardNumber(String value) {
                this.cardNumber = value;
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

        }

    }

}

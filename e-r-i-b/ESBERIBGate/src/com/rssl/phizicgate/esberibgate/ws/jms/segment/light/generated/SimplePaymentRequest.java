
package com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SimplePaymentRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SimplePaymentRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Head" type="{}HeadRequestType"/>
 *         &lt;element name="Body">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Route">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="DigCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
 *                   &lt;element name="RecIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Summa" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "SimplePaymentRequestType", propOrder = {
    "head",
    "body",
    "sign"
})
@XmlRootElement(name = "SimplePaymentRequest")
public class SimplePaymentRequest {

    @XmlElement(name = "Head", required = true)
    protected HeadRequestType head;
    @XmlElement(name = "Body", required = true)
    protected SimplePaymentRequest.Body body;
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
     *     {@link SimplePaymentRequest.Body }
     *     
     */
    public SimplePaymentRequest.Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimplePaymentRequest.Body }
     *     
     */
    public void setBody(SimplePaymentRequest.Body value) {
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
     *         &lt;element name="Route">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="DigCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
     *         &lt;element name="RecIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Summa" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "route",
        "debitCard",
        "currCode",
        "recIdentifier",
        "summa"
    })
    public static class Body {

        @XmlElement(name = "Route", required = true)
        protected SimplePaymentRequest.Body.Route route;
        @XmlElement(name = "DebitCard", required = true)
        protected SimplePaymentRequest.Body.DebitCard debitCard;
        @XmlElement(name = "CurrCode", required = true)
        protected String currCode;
        @XmlElement(name = "RecIdentifier", required = true)
        protected String recIdentifier;
        @XmlElement(name = "Summa", required = true)
        protected String summa;

        /**
         * Gets the value of the route property.
         * 
         * @return
         *     possible object is
         *     {@link SimplePaymentRequest.Body.Route }
         *     
         */
        public SimplePaymentRequest.Body.Route getRoute() {
            return route;
        }

        /**
         * Sets the value of the route property.
         * 
         * @param value
         *     allowed object is
         *     {@link SimplePaymentRequest.Body.Route }
         *     
         */
        public void setRoute(SimplePaymentRequest.Body.Route value) {
            this.route = value;
        }

        /**
         * Gets the value of the debitCard property.
         * 
         * @return
         *     possible object is
         *     {@link SimplePaymentRequest.Body.DebitCard }
         *     
         */
        public SimplePaymentRequest.Body.DebitCard getDebitCard() {
            return debitCard;
        }

        /**
         * Sets the value of the debitCard property.
         * 
         * @param value
         *     allowed object is
         *     {@link SimplePaymentRequest.Body.DebitCard }
         *     
         */
        public void setDebitCard(SimplePaymentRequest.Body.DebitCard value) {
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
         * Gets the value of the recIdentifier property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRecIdentifier() {
            return recIdentifier;
        }

        /**
         * Sets the value of the recIdentifier property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRecIdentifier(String value) {
            this.recIdentifier = value;
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
         *         &lt;element name="DigCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "digCode"
        })
        public static class Route {

            @XmlElement(name = "DigCode", required = true)
            protected String digCode;

            /**
             * Gets the value of the digCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDigCode() {
                return digCode;
            }

            /**
             * Sets the value of the digCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDigCode(String value) {
                this.digCode = value;
            }

        }

    }

}

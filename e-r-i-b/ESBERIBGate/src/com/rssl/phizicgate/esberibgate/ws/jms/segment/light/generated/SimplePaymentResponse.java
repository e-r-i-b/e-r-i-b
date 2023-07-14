
package com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SimplePaymentResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SimplePaymentResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Head" type="{}HeadResponseType"/>
 *         &lt;element name="Body" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AuthorizeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="OperationIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="RecAcc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="RecBic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="RecCorrAcc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="RecInn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="RecCompName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "SimplePaymentResponseType", propOrder = {
    "head",
    "body",
    "sign"
})
@XmlRootElement(name = "SimplePaymentResponse")
public class SimplePaymentResponse {

    @XmlElement(name = "Head", required = true)
    protected HeadResponseType head;
    @XmlElement(name = "Body")
    protected SimplePaymentResponse.Body body;
    @XmlElement(name = "Sign")
    protected SignType sign;

    /**
     * Gets the value of the head property.
     * 
     * @return
     *     possible object is
     *     {@link HeadResponseType }
     *     
     */
    public HeadResponseType getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeadResponseType }
     *     
     */
    public void setHead(HeadResponseType value) {
        this.head = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link SimplePaymentResponse.Body }
     *     
     */
    public SimplePaymentResponse.Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimplePaymentResponse.Body }
     *     
     */
    public void setBody(SimplePaymentResponse.Body value) {
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
     *         &lt;element name="AuthorizeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="OperationIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="RecAcc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="RecBic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="RecCorrAcc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="RecInn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="RecCompName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "authorizeCode",
        "operationIdentifier",
        "recAcc",
        "recBic",
        "recCorrAcc",
        "recInn",
        "recCompName"
    })
    public static class Body {

        @XmlElement(name = "AuthorizeCode", required = true)
        protected String authorizeCode;
        @XmlElement(name = "OperationIdentifier")
        protected String operationIdentifier;
        @XmlElement(name = "RecAcc")
        protected String recAcc;
        @XmlElement(name = "RecBic")
        protected String recBic;
        @XmlElement(name = "RecCorrAcc")
        protected String recCorrAcc;
        @XmlElement(name = "RecInn")
        protected String recInn;
        @XmlElement(name = "RecCompName")
        protected String recCompName;

        /**
         * Gets the value of the authorizeCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAuthorizeCode() {
            return authorizeCode;
        }

        /**
         * Sets the value of the authorizeCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAuthorizeCode(String value) {
            this.authorizeCode = value;
        }

        /**
         * Gets the value of the operationIdentifier property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOperationIdentifier() {
            return operationIdentifier;
        }

        /**
         * Sets the value of the operationIdentifier property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOperationIdentifier(String value) {
            this.operationIdentifier = value;
        }

        /**
         * Gets the value of the recAcc property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRecAcc() {
            return recAcc;
        }

        /**
         * Sets the value of the recAcc property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRecAcc(String value) {
            this.recAcc = value;
        }

        /**
         * Gets the value of the recBic property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRecBic() {
            return recBic;
        }

        /**
         * Sets the value of the recBic property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRecBic(String value) {
            this.recBic = value;
        }

        /**
         * Gets the value of the recCorrAcc property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRecCorrAcc() {
            return recCorrAcc;
        }

        /**
         * Sets the value of the recCorrAcc property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRecCorrAcc(String value) {
            this.recCorrAcc = value;
        }

        /**
         * Gets the value of the recInn property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRecInn() {
            return recInn;
        }

        /**
         * Sets the value of the recInn property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRecInn(String value) {
            this.recInn = value;
        }

        /**
         * Gets the value of the recCompName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRecCompName() {
            return recCompName;
        }

        /**
         * Sets the value of the recCompName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRecCompName(String value) {
            this.recCompName = value;
        }

    }

}

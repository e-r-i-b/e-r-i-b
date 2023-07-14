
package com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CardToCardResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardToCardResponseType">
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
@XmlType(name = "CardToCardResponseType", propOrder = {
    "head",
    "body",
    "sign"
})
@XmlRootElement(name = "CardToCardResponse")
public class CardToCardResponse {

    @XmlElement(name = "Head", required = true)
    protected HeadResponseType head;
    @XmlElement(name = "Body")
    protected CardToCardResponse.Body body;
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
     *     {@link CardToCardResponse.Body }
     *     
     */
    public CardToCardResponse.Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardToCardResponse.Body }
     *     
     */
    public void setBody(CardToCardResponse.Body value) {
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
        "authorizeCode"
    })
    public static class Body {

        @XmlElement(name = "AuthorizeCode", required = true)
        protected String authorizeCode;

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

    }

}

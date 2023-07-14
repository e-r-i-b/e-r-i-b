
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация о вопросе
 * 
 * <p>Java class for VerifyQuestionInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VerifyQuestionInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VerifyQuestionNumber">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
 *               &lt;totalDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="VerifyQuestionText">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="500"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="VerifyQuestionAnswer">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="25"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
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
@XmlType(name = "VerifyQuestionInfo_Type", propOrder = {
    "verifyQuestionNumber",
    "verifyQuestionText",
    "verifyQuestionAnswer"
})
public class VerifyQuestionInfoType {

    @XmlElement(name = "VerifyQuestionNumber")
    protected long verifyQuestionNumber;
    @XmlElement(name = "VerifyQuestionText", required = true)
    protected String verifyQuestionText;
    @XmlElement(name = "VerifyQuestionAnswer", required = true)
    protected String verifyQuestionAnswer;

    /**
     * Gets the value of the verifyQuestionNumber property.
     * 
     */
    public long getVerifyQuestionNumber() {
        return verifyQuestionNumber;
    }

    /**
     * Sets the value of the verifyQuestionNumber property.
     * 
     */
    public void setVerifyQuestionNumber(long value) {
        this.verifyQuestionNumber = value;
    }

    /**
     * Gets the value of the verifyQuestionText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerifyQuestionText() {
        return verifyQuestionText;
    }

    /**
     * Sets the value of the verifyQuestionText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerifyQuestionText(String value) {
        this.verifyQuestionText = value;
    }

    /**
     * Gets the value of the verifyQuestionAnswer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerifyQuestionAnswer() {
        return verifyQuestionAnswer;
    }

    /**
     * Sets the value of the verifyQuestionAnswer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerifyQuestionAnswer(String value) {
        this.verifyQuestionAnswer = value;
    }

}

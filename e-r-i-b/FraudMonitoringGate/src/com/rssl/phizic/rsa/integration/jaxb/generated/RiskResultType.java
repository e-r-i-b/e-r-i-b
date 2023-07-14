
package com.rssl.phizic.rsa.integration.jaxb.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * This defines the risk result information
 * 
 * <p>Java class for RiskResult_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RiskResult_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="riskScore" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="riskScoreBand" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="triggeredRule" type="{}TriggeredRule_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiskResult_Type", propOrder = {
    "riskScore",
    "riskScoreBand",
    "triggeredRule"
})
public class RiskResultType {

    protected Integer riskScore;
    protected String riskScoreBand;
    protected TriggeredRuleType triggeredRule;

    /**
     * Gets the value of the riskScore property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRiskScore() {
        return riskScore;
    }

    /**
     * Sets the value of the riskScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRiskScore(Integer value) {
        this.riskScore = value;
    }

    /**
     * Gets the value of the riskScoreBand property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskScoreBand() {
        return riskScoreBand;
    }

    /**
     * Sets the value of the riskScoreBand property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskScoreBand(String value) {
        this.riskScoreBand = value;
    }

    /**
     * Gets the value of the triggeredRule property.
     * 
     * @return
     *     possible object is
     *     {@link TriggeredRuleType }
     *     
     */
    public TriggeredRuleType getTriggeredRule() {
        return triggeredRule;
    }

    /**
     * Sets the value of the triggeredRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link TriggeredRuleType }
     *     
     */
    public void setTriggeredRule(TriggeredRuleType value) {
        this.triggeredRule = value;
    }

}

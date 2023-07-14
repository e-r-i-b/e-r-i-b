<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loanOffer/settings" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="data" type="string">
            <tiles:put name="tilesDefinition" type="string" value="LoanOffersSettingsEdit"/>
            <tiles:put name="submenu" type="string" value="LoanOffersSettings"/>
            <tiles:put name="pageTitle" type="string">
                <bean:message key="label.loan.settings" bundle="loanclaimBundle"/>
            </tiles:put>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="additionalStyle" value="propertiesForm"/>
                <tiles:put name="description">
                    <bean:message key="label.loan.settings.description" bundle="loanclaimBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <fieldset>
                        <bean:message key="label.loan.offer.title.uko" bundle="loanclaimBundle"/>
                        <fieldset>
                            <span><bean:message key="label.loan.offer.title.commonConditions" bundle="loanclaimBundle"/></span>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.employees"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.uko.commonConditions.employees"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.salaries"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.uko.commonConditions.salaries"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.pensioners"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.uko.commonConditions.pensioners"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.accredits"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.uko.commonConditions.accredits"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.street"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.uko.commonConditions.street"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </fieldset>
                        <fieldset>
                            <span><bean:message key="label.loan.offer.title.preapproved" bundle="loanclaimBundle"/> </span>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.employees"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.uko.preapproved.employees"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.salaries"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.uko.preapproved.salaries"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.pensioners"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.uko.preapproved.pensioners"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.accredits"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.uko.preapproved.accredits"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.street"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.uko.preapproved.street"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </fieldset>
                        <bean:message key="label.loan.offer.title.vsp" bundle="loanclaimBundle"/>
                        <fieldset>
                            <span><bean:message key="label.loan.offer.title.commonConditions" bundle="loanclaimBundle"/></span>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.employees"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.vsp.commonConditions.employees"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.salaries"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.vsp.commonConditions.salaries"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.pensioners"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.vsp.commonConditions.pensioners"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.accredits"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.vsp.commonConditions.accredits"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.street"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.vsp.commonConditions.street"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </fieldset>
                        <fieldset>
                            <span><bean:message key="label.loan.offer.title.preapproved" bundle="loanclaimBundle"/></span>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.employees"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.vsp.preapproved.employees"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.salaries"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.vsp.preapproved.salaries"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.pensioners"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.vsp.preapproved.pensioners"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.accredits"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.vsp.preapproved.accredits"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="loanclaimBundle" key="label.loan.offer.street"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.vsp.preapproved.street"/>
                                        <tiles:put name="textSize" value="32"/>
                                        <tiles:put name="textMaxLength" value="32"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </fieldset>
                    </fieldset>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="loanclaimBundle" key="label.loan.offer.offerDisplayCountOnMainPage"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="propertyInput" flush="false">
                                <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.offerDisplayCountOnMainPage"/>
                                <tiles:put name="textSize" value="10"/>
                                <tiles:put name="textMaxLength" value="3"/>
                                <tiles:put name="fieldType" value="text"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="loanclaimBundle" key="label.loan.offer.sendAcceptFactToFraudMonitoring"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="propertyInput" flush="false">
                                <tiles:put name="fieldName" value="com.rssl.iccs.crediting.offer.sendAcceptFactToFraudMonitoring"/>
                                <tiles:put name="textSize" value="32"/>
                                <tiles:put name="textMaxLength" value="32"/>
                                <tiles:put name="fieldType" value="checkbox"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false" operation="LoanOffersSettingsEditOperation">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
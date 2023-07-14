<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:form action="/loanreport/bki/timeSettings" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>

    <tiles:insert definition="propertiesForm">
        <tiles:put name="data" type="string">
            <tiles:put name="tilesDefinition" type="string" value="editCreditBureauTime"/>
            <tiles:put name="submenu" type="string" value="EditCreditBureauTime"/>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="additionalStyle" value="propertiesForm"/>
                <tiles:put name="name"><bean:message bundle="creditBureauBundle" key="bki.settings.time.title"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="creditBureauBundle" key="bki.settings.time.description"/></tiles:put>
                <tiles:put name="data">

                    <h4><bean:message bundle="creditBureauBundle" key="bki.settings.time.period.title"/></h4>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <tiles:insert definition="propertyInput" flush="false">
                                <tiles:put name="fieldName" value="com.rssl.iccs.loanreport.bureau.job.request.day"/>
                                <tiles:put name="textSize" value="5"/>
                                <tiles:put name="textMaxLength" value="2"/>
                            </tiles:insert>
                            <bean:message bundle="creditBureauBundle" key="bki.settings.time.period.day"/>

                            <bean:message bundle="creditBureauBundle" key="bki.settings.time.period.from"/>
                            <tiles:insert definition="propertyInput" flush="false">
                                <tiles:put name="fieldName" value="com.rssl.iccs.loanreport.bureau.job.request.startTime"/>
                                <tiles:put name="textSize" value="8"/>
                                <tiles:put name="textMaxLength" value="8"/>
                                <tiles:put name="inputClass" value="time-template"/>
                                <tiles:put name="format" value="HH:mm:ss"/>
                            </tiles:insert>

                            <bean:message bundle="creditBureauBundle" key="bki.settings.time.period.to"/>
                            <tiles:insert definition="propertyInput" flush="false">
                                <tiles:put name="fieldName" value="com.rssl.iccs.loanreport.bureau.job.request.endTime"/>
                                <tiles:put name="textSize" value="8"/>
                                <tiles:put name="textMaxLength" value="8"/>
                                <tiles:put name="inputClass" value="time-template"/>
                                <tiles:put name="format" value="HH:mm:ss"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <tiles:insert definition="propertyInput" flush="false">
                                <tiles:put name="fieldType" value="checkbox"/>
                                <tiles:put name="fieldName" value="com.rssl.iccs.loanreport.bureau.job.enabled"/>
                            </tiles:insert>
                            <bean:message bundle="creditBureauBundle" key="bki.settings.enabled"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <tiles:insert definition="propertyInput" flush="false">
                                <tiles:put name="fieldType" value="checkbox"/>
                                <tiles:put name="fieldName" value="com.rssl.iccs.loanreport.bureau.job.suspended"/>
                            </tiles:insert>
                            <bean:message bundle="creditBureauBundle" key="bki.settings.suspended"/>
                        </tiles:put>
                    </tiles:insert>

                    <a class="float" href='javascript:hideOrShow("jobReport")'>
                        <bean:message bundle="creditBureauBundle" key="bki.settings.report.view"/>
                    </a>
                    <br/>
                    <div id="jobReport" style="display: none">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="creditBureauBundle" key="bki.settings.report.from"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <tiles:insert definition="propertyInput" flush="false">
                                    <tiles:put name="fieldName" value="com.rssl.iccs.loanreport.bureau.job.start.time"/>
                                    <tiles:put name="readonly" value="true"/>
                                    <tiles:put name="textSize" value="21"/>
                                    <tiles:put name="textMaxLength" value="21"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="creditBureauBundle" key="bki.settings.report.to"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <bean:message bundle="creditBureauBundle" key="bki.settings.time.period.from"/>
                                <tiles:insert definition="propertyInput" flush="false">
                                    <tiles:put name="fieldName" value="com.rssl.iccs.loanreport.bureau.job.lastTry.period.from"/>
                                    <tiles:put name="readonly" value="true"/>
                                    <tiles:put name="textSize" value="21"/>
                                    <tiles:put name="textMaxLength" value="21"/>
                                </tiles:insert>

                                <bean:message bundle="creditBureauBundle" key="bki.settings.time.period.to"/>
                                <tiles:insert definition="propertyInput" flush="false">
                                    <tiles:put name="fieldName" value="com.rssl.iccs.loanreport.bureau.job.lastTry.period.to"/>
                                    <tiles:put name="readonly" value="true"/>
                                    <tiles:put name="textSize" value="21"/>
                                    <tiles:put name="textMaxLength" value="21"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                    </div>

                </tiles:put>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false" operation="EditBKITimeOperation">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>
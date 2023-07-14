<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>


<html:form action="/ermb/person/search" onsubmit="return setAction();">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>
    <tiles:insert definition="ermbMain">
        <tiles:put name="submenu" type="string" value="ErmbPersonSearch"/>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                $(document).ready(function ()
                {

                    showDocumentNameField($('select[name$="field(documentType)"]'));
                });

                function showDocumentNameField(select)
                {
                    if (select!= null)
                    {
                        switch (select.value)
                        {
                            case "OTHER" :
                            {
                                hideOrShow("documentName", false);
                                return;
                            }
                            default:
                            {
                                hideOrShow("documentName", true);
                                return;
                            }
                        }
                    }
                }
            </script>

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message bundle="ermbBundle" key="ermb.person.search"/>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.clear"/>
                        <tiles:put name="commandHelpKey" value="button.clear.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="image" value=""/>
                        <tiles:put name="action" value="/ermb/person/search.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.search"/>
                        <tiles:put name="commandTextKey" value="button.find"/>
                        <tiles:put name="commandHelpKey" value="button.find"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="image" value=""/>
                        <tiles:put name="action" value="/ermb/person/search"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="data" type="string">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="ermb.person.search.mode" bundle="ermbBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:radio property="searchByPhone" styleId="searchByPhone" value="true" onclick="$('.searchByPhone').show();$('.searchByFIO').hide();"/><label for="searchByPhone"><bean:message key="ermb.person.search.mode.phone" bundle="ermbBundle"/></label>
                            <br/>
                            <html:radio property="searchByPhone" styleId="searchByFIO" value="false" onclick="$('.searchByPhone').hide();$('.searchByFIO').show();"/><label for="searchByFIO"><bean:message key="ermb.person.search.mode.fio" bundle="ermbBundle"/></label>
                        </tiles:put>
                    </tiles:insert>

                    <div class="searchByPhone"<c:if test="${not form.searchByPhone}"> style="display:none"</c:if>>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.phone.number" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(phone)" size="17" styleClass="phone-template-new contactInput"/>
                            </tiles:put>
                        </tiles:insert>
                    </div>

                    <div class="searchByFIO"<c:if test="${form.searchByPhone}"> style="display:none"</c:if>>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.surName" bundle="personsBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(surName)" size="40" styleClass="contactInput"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.firstName" bundle="personsBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(firstName)" size="40" styleClass="contactInput"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.patrName" bundle="personsBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(patrName)" size="40" styleClass="contactInput"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.birthDay" bundle="personsBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <input type="text"
                                       name="field(birthDay)" class="dot-date-pick"
                                       size="10" class="contactInput"
                                       value="<bean:write name="form" property="field(birthDay)" format="dd.MM.yyyy"/>"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.documentType" bundle="personsBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:select property="field(documentType)" styleClass="select" style="width:500px;" onchange="showDocumentNameField(this);">
                                    <c:forEach var="documentType" items="${form.documentTypes}">
                                        <html:option value="${documentType}" styleClass="text-grey">
                                            <bean:message key="document.type.${documentType}" bundle="personsBundle"/>
                                        </html:option>
                                    </c:forEach>
                                </html:select>
                            </tiles:put>
                        </tiles:insert>

                        <div id="documentName">
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.documentName" bundle="personsBundle"/>
                                </tiles:put>
                                <tiles:put name="isNecessary" value="true"/>
                                <tiles:put name="data">
                                    <html:text property="field(documentName)"  styleId="field(documentName)" size="58" styleClass="contactInput"/>
                                </tiles:put>
                            </tiles:insert>
                        </div>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.documentSeries" bundle="personsBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(documentSeries)"
                                           size="20"
                                           styleClass="contactInput"
                                           styleId="field(documentSeries)"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.documentNumber" bundle="personsBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(documentNumber)"
                                           size="20"
                                           styleClass="contactInput"
                                           styleId="field(documentNumber)"/>
                            </tiles:put>
                        </tiles:insert>

                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

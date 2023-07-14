<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/technobreak/edit" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="technobreakEdit">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>
        <tiles:put name="submenu" type="string" value="ListTechnoBreak"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="technobreaksBundle"/>
                <tiles:put name="action" value="/technobreak/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function updateMessageDate()
                {
                    var templateMessage = "<bean:message key="label.message.default" bundle="technobreaksBundle" arg0="{0}"/>";
                    var defaultDate = "הה.לל.דדדד קק:לל:סס";

                    var strDate = ensureElement("toDate").value + " " + ensureElement("toTime").value;
                    var resultMessage =  templateMessage.replace("{0}", templateObj.DATE_FULLTIME.validate(strDate) ? strDate : defaultDate);

                    ensureElement("defaultMessage").innerHTML = resultMessage;
                }

                function setAdapterInfo(adapterInfo)
                {
                    setElement('field(adapterName)', adapterInfo["name"]);
                    setElement('field(adapterUUID)', adapterInfo["UUID"]);
                }

                $(document).ready(function(){updateMessageDate();});
                addClearMasks(null,
                        function(event)
                        {
                            clearInputTemplate('filter(fromDate)', '__.__.____');
                            clearInputTemplate('filter(toDate)', '__.__.____');
                            clearInputTemplate('filter(fromTime)', '__:__:__');
                            clearInputTemplate('filter(toTime)', '__:__:__');
                        });

            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value=""/>
                <tiles:put name="name">
                    <bean:message key="technobreak.edit.main.header" bundle="technobreaksBundle"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="technobreak.edit.text" bundle="technobreaksBundle"/>
                </tiles:put>
                <tiles:put name="data" type="string">

                    <%-- גםורםÿÿ סטסעולא --%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.external.system" bundle="technobreaksBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(adapterName)" readonly="true" size="50"/>
                            <html:hidden property="field(adapterUUID)"/>
                            <input type="button" class="buttWhite smButt"
                                   onclick="openAdaptersDictionary(setAdapterInfo)"
                                   value="..."/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.period" bundle="technobreaksBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <span style="white-space:nowrap;">
                                ס&nbsp;
                                <span style="font-weight:normal;overflow:visible;cursor:default;">
                                    <c:set var="fromDate"><bean:write name="form" property="field(fromDate)" format="dd.MM.yyyy"/></c:set>
                                    <c:set var="fromTime"><bean:write name="form" property="field(fromTime)" format="HH:mm:ss"/></c:set>
                                    <c:if test="${form.id != null}">
                                        <input type="hidden" name="field(fromDate)" value="${fromDate}"/>
                                        <input type="hidden" name="field(fromTime)" value="${fromTime}"/>
                                        <input type="hidden" name="field(id)" value="${form.id}"/>
                                    </c:if>
                                    <input type="text" ${form.id == null ? "" : "disabled"}
                                           size="10" name="field${form.id == null ? '' : '_'}(fromDate)" class="dot-date-pick"
                                           maxsize="10"
                                           value="${fromDate}"/>
                                    <input type="text" ${form.id == null ? "" : "disabled"}
                                           size="8" name="field${form.id == null ? '' : '_'}(fromTime)"
                                           maxsize="8" class="time-template"
                                           value="${fromTime}"
                                           onkeydown="onTabClick(event,'field(toDate)');"/>
                                </span>
                                &nbsp;ןמ&nbsp;
                                <span style="font-weight:normal;cursor:default;">
                                    <input type="text"
                                           size="10" name="field(toDate)" class="dot-date-pick" id="toDate"
                                           maxsize="10"
                                           value="<bean:write name="form" property="field(toDate)" format="dd.MM.yyyy"/>"
                                           onkeydown="updateMessageDate();" onchange="updateMessageDate();"/>

                                    <input type="text" id="toTime"
                                           size="8" name="field(toTime)"
                                           maxsize="8" class="time-template"
                                           value="<bean:write name="form" property="field(toTime)" format="HH:mm:ss"/>"
                                           onkeydown="updateMessageDate();" onchange="updateMessageDate();"/>
                                </span>
                           </span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.periodic" bundle="technobreaksBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(periodic)">
                                <html:option value="SINGLE">
                                    <bean:message key="label.periodic.SINGLE" bundle="technobreaksBundle"/>
                                </html:option>
                                <html:option value="EVERYDAY">
                                    <bean:message key="label.periodic.EVERYDAY" bundle="technobreaksBundle"/>
                                </html:option>
                                <html:option value="WORKDAY">
                                    <bean:message key="label.periodic.WORKDAY" bundle="technobreaksBundle"/>
                                </html:option>
                                <html:option value="WEEKEND">
                                    <bean:message key="label.periodic.WEEKEND" bundle="technobreaksBundle"/>
                                </html:option>
                                <html:option value="BEFOREWEEKEND">
                                    <bean:message key="label.periodic.BEFOREWEEKEND" bundle="technobreaksBundle"/>
                                </html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.message" bundle="technobreaksBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:radio property="field(isDefaultMessage)" value="true"/>
                            <span id="defaultMessage"><bean:message key="label.message.default" bundle="technobreaksBundle" arg0="הה.לל.דדדד קק:לל:סס"/></span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <html:radio property="field(isDefaultMessage)" value="false" styleClass="float"/>
                            <html:textarea styleId="messageText" property="field(message)" cols="40" />
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <div class="iconsMargin">
                                <div id="textCotor" class="mediumImportanceText pointer float " onmousedown="changeSelText('c', ['messageText'], $('#textCotor').css('color'));">&nbsp;
                                    <img src="${imagePath}/bbCodeButtons/iconSm_Colored.gif" alt="" border="0"/>
                                </div>
                                <div class="pointer float" onmousedown="addHyperlink(['messageText']);">
                                    <img src="${imagePath}/bbCodeButtons/iconSm_Link.gif" alt="Ãטןונססûכךא" border="0"/>
                                </div>
                            </div>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <html:checkbox property="field(allowOfflinePayments)" styleId="sovlePayment"/>
                            <label for="sovlePayment">Ðאחנורטעü מפפכאים-ןכאעוזט</label>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="back.to.list"/>
                        <tiles:put name="commandHelpKey" value="back.to.list"/>
                        <tiles:put name="bundle" value="localeBundle"/>
                        <tiles:put name="image" value=""/>
                        <tiles:put name="action" value="/technobreak/list.do"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.close"/>
                        <tiles:put name="commandHelpKey" value="button.close.help"/>
                        <tiles:put name="bundle" value="technobreaksBundle"/>
                        <tiles:put name="action" value="/technobreak/list.do"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false" operation="EditTechnoBreakOperation">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="technobreaksBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/technobreak/edit')}"/>
                    <tiles:insert definition="languageSelectForEdit" flush="false">
                        <tiles:put name="selectId" value="chooseLocale"/>
                        <tiles:put name="entityId" value="${form.id}"/>
                        <tiles:put name="styleClass" value="float"/>
                        <tiles:put name="selectTitleOnclick" value="openEditLocaleWin(this);"/>
                        <tiles:put name="editLanguageURL" value="${editLanguageURL}"/>
                    </tiles:insert>

                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html:form action="/blockingrules/edit" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="blockingRulesEdit">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:put name="submenu" type="string" value="BlockingRules"/>
        <tiles:put name="data" type="string">
        <c:set var="currentDate" ><fmt:formatDate value="${phiz:currentDate().time}" pattern="dd.MM.yyyy"/></c:set>
        <script type="text/javascript">
            function enablePublish(param)
            {
                var date = getElement("field(" + param + "PublishDate)");
                var time = getElement("field(" + param + "PublishTime)");

                var disabled = (document.getElementsByName("field(" + param + "Publish)")[0].checked) ? false : true;

                date.disabled = disabled;
                time.disabled = disabled;
            }

            function enableRestriction()
            {
                var disabled = (document.getElementsByName("field(configureNotification)")[0].checked) ? false : true;
                var dateFrom = getElement("field(fromRestrictionDate)");
                var timeFrom = getElement("field(fromRestrictionTime)");
                var dateTo = getElement("field(toRestrictionDate)");
                var timeTo = getElement("field(toRestrictionTime)");

                dateFrom.disabled = disabled;
                dateTo.disabled = disabled;
                timeFrom.disabled = disabled;
                timeTo.disabled = disabled;
            }

            function setCurrentDate(param)
            {
                var date = getElementValue("field("+param+"PublishDate)");
                if (date == '')
                {
                    setElement("field("+param+"PublishDate)", '${currentDate}');
                }
            }

            function resetChannelsSettings()
            {
                var applyToERIB = document.getElementById('applyToERIB').checked;
                document.getElementById('messageERIB').disabled = !applyToERIB;

                disableApiSettings(true, true);
            }

            function disableApiSettings(disableEribField, disableApiFields)
            {
                var applyToERIB = document.getElementById('applyToERIB').checked;
                var eribMessage = document.getElementById('messageERIB').value ;
                var disableERIB = !(applyToERIB && eribMessage != null && eribMessage.length > 0);

                var condition = 'useAnotherMessageIn';
                var channels = $('input[type=radio][id^=' + condition + ']');
                for (var i = 0; i < channels.length; i++)
                {
                    var channel = channels[i];
                    var channelName = channel.id.substr(condition.length, channel.id.length);
                    var disabled = !document.getElementById('applyTo' + channelName).checked;

                    if (disableEribField)
                    {
                        document.getElementById('useERIBMessageIn' + channelName).disabled = disableERIB || disabled;

                        if (disableERIB || disabled)
                        {
                            $('input[type=radio][id^=useERIBMessageIn' + channelName + ']').removeAttr('checked');
                            $('input[type=radio][id^=useAnotherMessage' + channelName + ']').attr('checked', 'checked');
                        }
                    }
                    if (disableApiFields)
                    {
                        document.getElementById('message' + channelName).disabled = disabled;
                        document.getElementById('useAnotherMessageIn' + channelName).disabled = disabled;
                    }
                }
            }

            function updateUseERIBButons()
            {
                disableApiSettings(true, false);
            }

            function updateRulesActive(channel)
            {
                var rule = document.getElementById('applyTo' + channel);
                var disabled = !rule.checked;

                if (channel == 'ERIB')
                {
                    disableApiSettings(true, false);
                }
                else
                {
                    var applyToErib = document.getElementById('applyToERIB').checked;
                    var eribMessage = document.getElementById('messageERIB');

                    document.getElementById('useAnotherMessageIn' + channel).disabled = disabled;
                    document.getElementById('useERIBMessageIn' + channel).disabled = !(rule.checked && applyToErib && eribMessage.value != null && eribMessage.value.length > 0);
                }

                document.getElementById('message' + channel).disabled = disabled;
            }

            function resetAnotherMessage(channel)
            {
                document.getElementById('useAnotherMessageIn' + channel).checked = false;
            }

            function resetERIBMessage(channel)
            {
                document.getElementById('useERIBMessageIn' + channel).checked = false;
            }

            function setTime(param)
            {
                var publishTime = getElementValue("field(" + param + "PublishTime)");
                if (publishTime == '')
                {
                    setElement("field(" + param + "PublishTime)", '00:00');
                }
            }

            $(document).ready(function()
            {
                enablePublish("from");
                enablePublish("to");
                enableRestriction()
                resetChannelsSettings();
            });

        </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value=""/>
                <tiles:put name="name">
                    <bean:message key="blockingrules.page.edit.header" bundle="blockingRulesBundle"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="blockingrules.page.edit.description" bundle="blockingRulesBundle"/>
                </tiles:put>
                <tiles:put name="data" type="string">
                    <fieldset>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.name" bundle="blockingRulesBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(name)" size="100"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.departments" bundle="blockingRulesBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="description">
                            Укажите коды подразделений в формате RbTbBrchId или маски соответствующие подразделениям, для которых будет запрещаться вход в систему. Разделителем кодов является запятая.
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(departments)" size="100"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.resuming.time" bundle="blockingRulesBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <span style="font-weight:normal;overflow:visible;cursor:default;">
                            <input type="text"
                                   size="10" name="field(resumingDate)" class="dot-date-pick"
                                   value="<bean:write name="form" property="fields.resumingDate" format="dd.MM.yyyy"/>"
                                    />
                            &nbsp;в
                            <c:set var="resumingTime"><bean:write name="form" property="field(resumingTime)" format="HH:mm"/></c:set>
                            <html:text property="field(resumingTime)" value="${resumingTime}" styleClass="pmntDate short-time-template" maxlength="8"/>
                        </span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.state" bundle="blockingRulesBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(state)">
                                <html:option value="blocked">
                                    <bean:message key="item.state.blocked" bundle="blockingRulesBundle"/>
                                </html:option>
                                <html:option value="unblocked">
                                    <bean:message key="item.state.unblocked" bundle="blockingRulesBundle"/>
                                </html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    </fieldset>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="description">
                            <bean:message key="label.configure.notification.help" bundle="blockingRulesBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:checkbox property="field(configureNotification)" onclick="enableRestriction()"/>
                            <bean:message key="label.configure.notification" bundle="blockingRulesBundle"/>
                        </tiles:put>
                    </tiles:insert>

                    <fieldset>
                        <legend><bean:message key="label.automatical" bundle="blockingRulesBundle"/></legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <div style="float:left">
                                    <html:checkbox property="field(fromPublish)"  onclick="enablePublish('from');setCurrentDate('from');setTime('from');"/>
                                    <bean:message key="label.publish.from.date" bundle="blockingRulesBundle"/>
                                </div>
                            </tiles:put>
                            <tiles:put name="data">
                                <span style="font-weight:normal;overflow:visible;cursor:default;">
                                    <c:set var="fromPublishDateTime"><bean:write name="form" property="field(fromPublishDate)" format="dd.MM.yyyy"/></c:set>
                                    <html:text property="field(fromPublishDate)" value="${fromPublishDateTime}" styleClass="dot-date-pick" onkeydown="setTime('from');" disabled="true" maxlength="10"/>
                                    &nbsp;в
                                    <c:set var="publishTime"><bean:write name="form" property="field(fromPublishTime)" format="HH:mm"/></c:set>
                                    <html:text property="field(fromPublishTime)" value="${publishTime}" styleClass="pmntDate short-time-template" disabled="true" maxlength="8"/>
                                </span>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <div style="float:left">
                                    <html:checkbox property="field(toPublish)" onclick="enablePublish('to');setCurrentDate('to');setTime('to');"/>
                                    <bean:message key="label.publish.to.date" bundle="blockingRulesBundle"/>
                                </div>
                            </tiles:put>
                            <tiles:put name="data">
                                <span style="font-weight:normal;overflow:visible;cursor:default;">
                                    <c:set var="toPublishDateTime"><bean:write name="form" property="field(toPublishDate)" format="dd.MM.yyyy"/></c:set>
                                    <html:text property="field(toPublishDate)" value="${toPublishDateTime}" styleClass="dot-date-pick" onkeydown="setTime('from');" disabled="true" maxlength="10"/>
                                    &nbsp;в
                                    <c:set var="toTime"><bean:write name="form" property="field(toPublishTime)" format="HH:mm"/></c:set>
                                    <html:text property="field(toPublishTime)" value="${toTime}" styleClass="pmntDate short-time-template" disabled="true" maxlength="8"/>
                                </span>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="description">
                                <bean:message key="label.publish.help" bundle="blockingRulesBundle"/>
                            </tiles:put>
                        </tiles:insert>

                    </fieldset>

                    <fieldset>
                        <legend><bean:message key="label.period.restriction" bundle="blockingRulesBundle"/></legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.access.restriction.from" bundle="blockingRulesBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <span style="font-weight:normal;overflow:visible;cursor:default;">
                                    <input type="text"
                                           size="10" name="field(fromRestrictionDate)" class="dot-date-pick"
                                           value="<bean:write name="form" property="fields.fromRestrictionDate" format="dd.MM.yyyy"/>"
                                            />
                                    &nbsp;в
                                    <c:set var="fromTimeRest"><bean:write name="form" property="field(fromRestrictionTime)" format="HH:mm"/></c:set>
                                    <html:text property="field(fromRestrictionTime)" value="${fromTimeRest}" styleClass="pmntDate short-time-template" maxlength="8"/>
                                </span>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.access.restriction.to" bundle="blockingRulesBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <span style="font-weight:normal;overflow:visible;cursor:default;">
                                    <input type="text"
                                           size="10" name="field(toRestrictionDate)" class="dot-date-pick"
                                           value="<bean:write name="form" property="fields.toRestrictionDate" format="dd.MM.yyyy"/>"
                                            />
                                    &nbsp;в
                                    <c:set var="toTimeRest"><bean:write name="form" property="field(toRestrictionTime)" format="HH:mm"/></c:set>
                                    <html:text property="field(toRestrictionTime)" value="${toTimeRest}" styleClass="pmntDate short-time-template" maxlength="8"/>
                                </span>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="description">
                                <bean:message key="label.access.restriction.help" bundle="blockingRulesBundle"/>
                            </tiles:put>
                        </tiles:insert>

                    </fieldset>

                    <fieldset>
                        <legend><bean:message key="label.channels.settings" bundle="blockingRulesBundle"/></legend>

                        <!-- ERIB -->
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.channels.ERIB" bundle="blockingRulesBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:checkbox property="field(applyToERIB)" styleId="applyToERIB" onclick="updateRulesActive('ERIB')"/>&nbsp;
                                <bean:message key="label.blockingRule.active" bundle="blockingRulesBundle"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.blocking.reason" bundle="blockingRulesBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:textarea styleId="messageERIB" property="field(eribMessage)" cols="55" rows="3" onchange="updateUseERIBButons()"/>
                            </tiles:put>
                        </tiles:insert>

                        <!-- MAPI -->
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.channels.MAPI" bundle="blockingRulesBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:checkbox property="field(applyToMAPI)" styleId="applyToMAPI" onclick="updateRulesActive('MAPI')"/>&nbsp;
                                <bean:message key="label.blockingRule.active" bundle="blockingRulesBundle"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.blocking.reason"  bundle="blockingRulesBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:radio property="field(useMapiMessage)" styleId="useERIBMessageInMAPI" onclick="resetAnotherMessage('MAPI')" value="false"/>&nbsp;
                                <bean:message key="label.useERIBMessage" bundle="blockingRulesBundle"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:radio property="field(useMapiMessage)" styleId="useAnotherMessageInMAPI" onclick="resetERIBMessage('MAPI')" value="true"/>&nbsp;
                                <bean:message key="label.useAnotherMessage" bundle="blockingRulesBundle"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:textarea styleId="messageMAPI" property="field(mapiMessage)" cols="55" rows="3"/>
                            </tiles:put>
                        </tiles:insert>

                        <!-- ATM -->
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.channels.ATM" bundle="blockingRulesBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:checkbox property="field(applyToATM)" styleId="applyToATM" onclick="updateRulesActive('ATM')"/>&nbsp;
                                <bean:message key="label.blockingRule.active" bundle="blockingRulesBundle"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.blocking.reason"  bundle="blockingRulesBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:radio property="field(useAtmMessage)" styleId="useERIBMessageInATM" onclick="resetAnotherMessage('ATM')" value="false"/>&nbsp;
                                <bean:message key="label.useERIBMessage" bundle="blockingRulesBundle"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:radio property="field(useAtmMessage)" styleId="useAnotherMessageInATM" onclick="resetERIBMessage('ATM')" value="true"/>&nbsp;
                                <bean:message key="label.useAnotherMessage" bundle="blockingRulesBundle"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:textarea styleId="messageATM" property="field(atmMessage)" cols="55" rows="3"/>
                            </tiles:put>
                        </tiles:insert>

                        <!-- ERMB -->
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.channels.ERMB" bundle="blockingRulesBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:checkbox property="field(applyToERMB)" styleId="applyToERMB" onclick="updateRulesActive('ERMB')"/>&nbsp;
                                <bean:message key="label.blockingRule.active" bundle="blockingRulesBundle"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.blocking.reason"  bundle="blockingRulesBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:radio property="field(useErmbMessage)" styleId="useERIBMessageInERMB" onclick="resetAnotherMessage('ERMB')" value="false"/>&nbsp;
                                <bean:message key="label.useERIBMessage" bundle="blockingRulesBundle"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:radio property="field(useErmbMessage)" styleId="useAnotherMessageInERMB" onclick="resetERIBMessage('ERMB')" value="true"/>&nbsp;
                                <bean:message key="label.useAnotherMessage" bundle="blockingRulesBundle"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:textarea styleId="messageERMB" property="field(ermbMessage)" cols="55" rows="3"/>
                            </tiles:put>
                        </tiles:insert>

                    </fieldset>

                </tiles:put>

                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="blockingRulesBundle"/>
                        <tiles:put name="action" value="/blockingrules/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="blockingRulesBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/blockingrules/language/save')}"/>
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
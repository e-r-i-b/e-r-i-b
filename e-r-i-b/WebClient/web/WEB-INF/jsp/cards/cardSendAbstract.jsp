<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 07.06.2010
  Time: 13:49:47
  To change this template use File | Settings | File Templates.
--%>
<tiles:importAttribute/>

<c:set var="bundle" value="${messagesBundle}"/>
<c:if test="${empty bundle || bundle==''}">
    <c:set var="bundle" value="commonBundle"/>
</c:if>

<c:set var="errors">
    <phiz:messages id="error" bundle="${bundle}" field="field" message="error">
        <c:choose>
            <c:when test="${field != null}">
                errorHash ['<bean:write name="field" filter="false"/>'] = '<bean:write name="error"
                                                                                       filter="false"/>';
            </c:when>
            <c:otherwise>
                globalErrors.push('<bean:write name="error" filter="false"/>');
            </c:otherwise>
        </c:choose>
    </phiz:messages>
</c:set>

<c:choose>
    <c:when test="${fn:length(errors) gt 0}">
        var errorHash = {};
        var globalErrors = [];
        ${errors}
    </c:when>
    <c:otherwise>
        <html:form action="/private/async/cards/sendAbstract" onsubmit="return setEmptyAction(event)">
            <c:set var="imagePath" value="${skinUrl}/images"/>
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <c:set var="cardLink" value="${form.cardLink}"/>
            <c:set var="card" value="${cardLink.card}"/>
            <h1>Заказать отчет на e-mail</h1>

            <tiles:insert page="/WEB-INF/jsp/common/layout/emailValidation.jsp"/>

            <script type="text/javascript">

                function removeResolvedErrors(errorHash)
                {
                    var errDivs = $('.errorDiv');

                    $.each(errDivs, function(errDivIndex, errDiv)
                    {
                        var elemFound = false;

                        $.each(errorHash, function(hashIndex, hash)
                        {
                            if (errDiv.innerText == hash)
                            {
                                elemFound = true;
                            }
                        });

                        if (!elemFound)
                        {
                            $(errDiv).css('display', 'none');
                            $(errDiv).closest('div.error').removeClass('error');
                        }
                    });
                }

                function errorHandler(message, url, line)
                {
                    return true;
                }

                if (navigator.userAgent.indexOf("MSIE") != -1)
                    window.onerror = errorHandler;

                <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/async/cards/sendAbstract')}"/>
                <c:set var="cardLinkId" value="${param['id']}"/>
                function sendedFilterAjaxResult(msg)
                {
                    if (trim(msg) == '')
                    {
                        window.location.reload();
                        return;
                    }
                    try
                    {
                        eval(msg);
                        if (globalErrors.length > 0)
                        {
                            for (var i = 0; i < globalErrors.length; i++)
                            {
                                addError(globalErrors[i], 'winErrors', true);
                            }
                            win.open('sendAbstract');
                            return;
                        }
                        removeResolvedErrors(errorHash);
                        getFieldError(errorHash, 'winErrors', true);
                    }
                    catch (err)
                    {
                        var sendAbstractData = document.getElementById('sendAbstract');
                        sendAbstractData.innerHTML = msg;
                    <%--  активируем инпуты с подсказками --%>
                        payInput.setEvents(payInput);
                    <%--  Устанавливаем фильтры --%>
                        <c:if test="${form.asVypiskaActive == 'false'}">
                        filterDateChange('Email');
                        fillDateTimeEmail();
                        </c:if>
                        win.close('sendAbstract');
                        <c:if test="${form.asVypiskaActive == 'true'}">
                            win.open('sendEmailResult');
                            var indexOf = msg.indexOf("---error"+"[---");
                            var meslength = ("---error"+"[---").length;
                            if (indexOf >= 0)
                            {
                                $("#sendEmailResultTitle").html(msg.substr(indexOf + meslength, msg.indexOf("---]"+"error---") - meslength - indexOf));
                                $(".sendEmailHideBlock").hide();
                            }
                        </c:if>
                    }

                }

                function sendAbstract()
                {
                    var msg = $('#fillReport') ? "" : validatePeriod("filter(fromEmail)", "filter(toEmail)", "Ошибка в формате начальной даты.", "Ошибка в формате конечной даты.", templateObj.ENGLISH_DATE);
                    removeAllErrors("winErrors");
                    payInput.fieldClearError("fromEmail");
                    payInput.fieldClearError("toEmail");
                    payInput.fieldClearError("email");

                    if (msg)
                    {
                        getFieldError({fromEmail : msg}, "winErrors", true);
                        return false;
                    }
                    var msgEmail = validateEmail($('[name="filter(email)"]'), 'Выслать отчет на E-mail');
                    if (isNotEmpty(msgEmail))
                    {
                        getFieldError({email : msgEmail}, "winErrors", true);
                        return false;
                    }
                    var params = 'id=${cardLinkId}&filter(email)=' + encodeURIComponent(getElementValue("filter(email)")) + "&operation=button.send";

                    <c:choose>
                        <c:when test="${not form.asVypiskaActive}">
                            var fParams = getDateFilterParams("Email");
                            if (fParams.type)
                                params += "&filter(typeEmail)=" + fParams.type;
                            if (fParams.type == 'period')
                                params += "&filter(fromEmail)=" + fParams.from + "&filter(toEmail)=" + fParams.to;
                        </c:when>
                        <c:otherwise>
                            params += "&filter(fromEmail)=" + $('#fromEmail').val() + "&filter(toEmail)=" + $('#toEmail').val();
                        </c:otherwise>
                    </c:choose>
                    if ($('#format').val())
                        params += "&filter(format)=" + $('#format').val();
                    if ($('#lang').val())
                        params += "&filter(lang)=" + $('#lang').val();
                    if ($('#fillReport').val())
                        params += "&filter(fillReport)=" + $('#fillReport').val();
                    var errorDivMessage = findChildByClassName(ensureElement("winErrors"), "messageContainer");
                    ajaxQuery(params, '${url}', sendedFilterAjaxResult);
                    return true;
                }

                function showOrHideHistory(button)
                {
                     <c:if test="${form.asVypiskaActive}">
                        if ($('#sendedHistory')[0].style.display == 'none' && $("#historyBlock").html()=="")
                        {
                            var params = 'id=${cardLinkId}&operation=button.history';
                            ajaxQuery(params, '${url}', function (data){
                                $("#historyBlock").html(data);
                            });
                        }
                    </c:if>
                    var obj = button.getElementsByTagName("span")[0];

                    if (obj == null)
                        return false;

                    if (obj.origValue == undefined)
                    {
                        obj.origValue = obj.innerHTML;
                        obj.innerHTML = '<bean:message key="button.report.hide.history" bundle="cardInfoBundle"/>';
                    }
                    else
                    {
                        obj.innerHTML = obj.origValue;
                        obj.origValue = undefined;
                    }

                    hideOrShow("sendedHistory");
                    return false;

                }
                <%-- todo извращение переделать, как только определимся, что делать с ajax окнами --%>
                function paginationAjaxResult(msg)
                {
                    if (trim(msg) == '')
                    {
                        window.location.reload();
                        return;
                    }

                    var sendAbstractData = document.getElementById('sendAbstract');
                    sendAbstractData.innerHTML = msg;
                    showOrHideHistory(ensureElement("historyButton"));
                }

                function ajaxTibleList(sizeFieldName,paginationSize,offsetFieldName, offset)
                {
                    var params = 'id=${cardLinkId}&' + offsetFieldName + '=' + offset + '&' + sizeFieldName + '=' + paginationSize;
                    ajaxQuery(params, '${url}', paginationAjaxResult);
                }
                <%-- / --%>
            </script>



            <div class="clear"></div>

<c:choose>
    <c:when test="${form.timeoutError || form.otherError || form.inactiveESError != null}">
        ---error[---
                <c:choose>
                    <c:when test="${form.inactiveESError != null}"><c:out value="${form.inactiveESError}"/></c:when>
                    <c:when test="${form.timeoutError}"><bean:message bundle="cardInfoBundle" key="email.report.result.error.timeout"/></c:when>
                    <c:otherwise><bean:message bundle="cardInfoBundle" key="email.report.result.error"/></c:otherwise>
                </c:choose>
        ---]error---
    </c:when>
    <c:when test="${form.asVypiskaActive}">
            <%-- Ошибки --%>
            <tiles:insert definition="errorBlock" flush="false">
                <tiles:put name="regionSelector" value="winErrors"/>
            </tiles:insert>
            <%-- /Ошибки --%>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="E-mail"/>
                <tiles:put name="isNecessary" value="false"/>
                <tiles:put name="data">
                    <input type="text" name="filter(email)" size="30" maxlength="69" value='<bean:write name="form" property="filter(email)"/>'/>
                </tiles:put>
            </tiles:insert>

            <input type="hidden" name="filter(fillReport)" id="fillReport" value="<bean:write name="form" property="filter(fillReport)"/>"/>

            <div class="reportCardEmail">
                <div id='receiverSubTypeControl' class="bigTypecontrol">
                    <div id="ER" class="inner firstButton" onclick="clickReportType('ER');">
                        Полная выписка
                    </div>
                    <div id="IR" class="inner lastButton" onclick="clickReportType('IR');">
                        Отчет только по операциям
                    </div>
                </div>
                <div class="clear"></div>

                <div id="ERInfo">
                    <p class="subtitle paragraphBottom firstItemPosition">Содержит полную информацию по карте за отчетный период, заканчивающийся в выбранном месяце: детализацию операций, суммы процентов, комиссий и иных платежей, подлежащих уплате Банку</p>
                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title" value="Отчетный период"/>
                        <tiles:put name="isNecessary" value="false"/>
                        <tiles:put name="data">
                            <select name="filter(month)" id="month" class="selectSbtS" style="width:115px;" onchange="changeDate();">
                                <option value="1" id="month1">Январь</option>
                                <option value="2" id="month2">Февраль</option>
                                <option value="3" id="month3">Март</option>
                                <option value="4" id="month4">Апрель</option>
                                <option value="5" id="month5">Май</option>
                                <option value="6" id="month6">Июнь</option>
                                <option value="7" id="month7">Июль</option>
                                <option value="8" id="month8">Август</option>
                                <option value="9" id="month9">Сентябрь</option>
                                <option value="10" id="month10">Октябрь</option>
                                <option value="11" id="month11">Ноябрь</option>
                                <option value="12" id="month12">Декабрь</option>
                            </select>

                            <input name="filter(year)" id="year" type="text" onchange="changeDate();" onkeyup="changeDate();" size="4"/>
                        </tiles:put>
                    </tiles:insert>
                </div>

                <div id="IRInfo">
                    <p class="subtitle paragraphBottom firstItemPosition">Содержит детализацию операций за указанный период и краткую информацию по карте</p>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title" value="Период"/>
                        <tiles:put name="isNecessary" value="false"/>
                        <tiles:put name="data">
                            <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(fromEmail)" format="dd/MM/yyyy"/>'
                                   size="10" name="filter(fromEmail)" id="fromEmail" class="Emaildate-pick" />
                            &nbsp;&mdash;&nbsp;
                            <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(toEmail)" format="dd/MM/yyyy"/>'
                                   size="10" name="filter(toEmail)" id="toEmail" class="Emaildate-pick endOfPeriod"/>
                        </tiles:put>
                    </tiles:insert>
                </div>

                <div class="format">
                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title" value="Язык отчета"/>
                        <tiles:put name="isNecessary" value="false"/>
                        <tiles:put name="data">
                            <select name="filter(lang)" id="lang" class="selectSbtS" style="width:125px;" onchange="changeSelectFormat();">
                                <option value="RUS" id="RUS" <c:if test="${form.filters['lang'] == 'RUS'}">selected</c:if>>Русский</option>
                                <option value="ENG" id="ENG" <c:if test="${form.filters['lang'] == 'ENG'}">selected</c:if>>Английский</option>
                            </select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title" value="Формат"/>
                        <tiles:put name="isNecessary" value="false"/>
                        <tiles:put name="data">
                            <select name="filter(format)" id="format" class="selectSbtS" style="width:90px;" onchange="changeSelectLang();">
                                <option value="PDF" id="PDF" <c:if test="${form.filters['format'] == 'PDF'}">selected</c:if>>PDF</option>
                                <option value="HTML" id="HTML" <c:if test="${form.filters['format'] == 'HTML'}">selected</c:if>>HTML</option>
                                <option value="CSV" id="CSV" <c:if test="${form.filters['format'] == 'CSV'}">selected</c:if>>CSV</option>
                            </select>
                        </tiles:put>
                    </tiles:insert>
                </div>

                <script type="text/javascript">
                    var formatLang = [];
                    formatLang["ER"] = [];
                    formatLang["IR"] = [];
                    formatLang["ER"]["PDF"] = [];
                    formatLang["ER"]["HTML"] = [];
                    formatLang["ER"]["CSV"] = [];
                    formatLang["IR"]["PDF"] = [];
                    formatLang["IR"]["HTML"] = [];
                    formatLang["IR"]["CSV"] = [];

                    <%-- boolean formatLang[ER][PDF][RUS] ...  --%>
                    formatLang["ER"]["PDF"]["RUS"] = ${form.activePDF_RUS};
                    formatLang["ER"]["PDF"]["ENG"] = ${form.activePDF_ENG};
                    formatLang["ER"]["HTML"]["RUS"] = ${form.activeHTML_RUS};
                    formatLang["ER"]["HTML"]["ENG"] = ${form.activeHTML_ENG};
                    formatLang["ER"]["CSV"]["RUS"] = false;
                    formatLang["ER"]["CSV"]["ENG"] = false;
                    formatLang["IR"]["PDF"] = formatLang["ER"]["PDF"];
                    formatLang["IR"]["HTML"] = formatLang["ER"]["HTML"];
                    formatLang["IR"]["CSV"]["RUS"] = true;
                    formatLang["IR"]["CSV"]["ENG"] = true;

                    function clickReportType(newReportType)
                    {
                        $('#receiverSubTypeControl').find('div').removeClass('activeButton');
                        $('#' + newReportType).addClass('activeButton');
                        $('#fillReport').val(newReportType);

                        $("#IRInfo").hide();
                        $("#ERInfo").hide();
                        $('#' + newReportType + 'Info').show();

                        if (newReportType === 'IR')
                        {
                            dt = new Date();
                            day = dt.getDate() + "";
                            if (day.length == 1)
                                day = "0" + day;
                            month = dt.getMonth() + 1 + "";
                            if (month.length == 1)
                                month = "0" + month;
                            $('#toEmail').val(day + "/" + month + "/" + dt.getFullYear());
                            $('#fromEmail').val(day + "/" + (dt.getMonth() == 0 ? "12" : (dt.getMonth() < 10 ? ("0" + dt.getMonth()) : dt.getMonth())) + "/" + (dt.getMonth() == 0 ? dt.getFullYear() - 1: dt.getFullYear()));
                        }
                        else
                        {
                            changeDate();
                        }

                        changeSelectFormat();
                        changeSelectLang();
                        changeSelectFormat();
                    }

                    function changeSelectFormat()
                    {
                        var firstActive = "";
                        for (var format in formatLang[$('#fillReport').val()])
                        {
                            var active = formatLang[$('#fillReport').val()][format][$('#lang').val()];
                            if (active && !firstActive)
                                firstActive = format;
                            $('#' + format).attr('disabled', !active);
                        }

                        if (!formatLang[$('#fillReport').val()][$('#format').val()][$('#lang').val()])
                        {
                            $('#format option').removeAttr("selected");
                            $('#' + firstActive).attr("selected", "true");
                            $('#format').change();
                        }

                        $('.selectSbtS').jSelect({ clNames: '_size-s'});
                    }
                    function changeSelectLang()
                    {
                        var firstActive = "";
                        for (var lang in formatLang[$('#fillReport').val()][$('#format').val()])
                        {
                            var active = formatLang[$('#fillReport').val()][$('#format').val()][lang];
                            if (active && !firstActive)
                                firstActive = lang;
                            $('#' + lang).attr('disabled', !active);
                        }
                        if (!formatLang[$('#fillReport').val()][$('#format').val()][$('#lang').val()])
                        {
                            $('#lang option').removeAttr("selected");
                            $('#' + firstActive).attr("selected", "true");
                            $('#lang').change();
                        }

                        $('.selectSbtS').jSelect({ clNames: '_size-s'});
                    }

                    function changeDate()
                    {
                        var month = $('#month').val();
                        if (month.length == 1)
                            month = "0" + month;
                        $('#fromEmail').val("01/" + month + "/" + $('#year').val());
                    }

                    doOnLoad(function()
                    {
                        clickReportType($('#fillReport').val());
                        $('#month' + (new Date().getMonth() + 1)).attr("selected", "true");
                        $('#month').change();
                        $("#year").val(new Date().getFullYear());
                        changeDate();
                    });

                    doOnLoad(function()
                    {
                        $(function()
                        {
                            var dpDateFormat = 'dd/MM/yyyy'.toLowerCase();
                            var dP;
                            var elements = $('.Emaildate-pick:not(.dp-applied)');
                            if (elements.datePicker)
                            {
                                dP = elements.datePicker({displayClose: true, chooseImg: globalUrl + '/commonSkin/images/datePickerCalendar.gif', dateFormat:dpDateFormat});
                                dP.dpApplyMask();
                            }
                        });
                    });
                </script>

                <div class="buttonsArea">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.report.send"/>
                        <tiles:put name="commandHelpKey" value="button.report.send.help"/>
                        <tiles:put name="bundle" value="cardInfoBundle"/>
                        <tiles:put name="onclick">sendAbstract();</tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.report.history"/>
                        <tiles:put name="commandHelpKey" value="button.report.history.help"/>
                        <tiles:put name="viewType" value="blueGrayLinkDotted"/>
                        <tiles:put name="bundle" value="cardInfoBundle"/>
                        <tiles:put name="onclick">  showOrHideHistory (this);  </tiles:put>
                    </tiles:insert>
                </div>
        </div>

        <div id="sendedHistory" style="display: none;">
             <div id="historyBlock"></div>
        </div>
    </c:when>
    <c:otherwise>
            <p class="subtitle paragraphBottom">Для получения отчета по выбранной пластиковой карте Вам необходимо заполнить данную форму. Отчет в ближайшее время будет выслан на указанный Вами e-mail.</p>

            <%-- Ошибки --%>
            <tiles:insert definition="errorBlock" flush="false">
                <tiles:put name="regionSelector" value="winErrors"/>
            </tiles:insert>
            <%-- /Ошибки --%>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="Выслать отчет на E-mail"/>
                <tiles:put name="isNecessary" value="true"/>
                <tiles:put name="data">
                    <input type="text" name="filter(email)" size="30" maxlength="69"
                           value='<bean:write name="form" property="filter(email)"/>'/>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="filterDataPeriod">
                <tiles:put name="name" value="Email"/>
                <tiles:put name="buttonKey" value="button.report.send"/>
                <tiles:put name="buttonBundle" value="cardInfoBundle"/>
                <tiles:put name="windowId" value="sendAbstract"/>
                <tiles:put name="onclick" value="sendAbstract();"/>
                <tiles:put name="buttons">
                    <span id="historyButton">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.report.history"/>
                            <tiles:put name="commandHelpKey" value="button.report.history.help"/>
                            <tiles:put name="viewType" value="blueGrayLinkDotted"/>
                            <tiles:put name="bundle" value="cardInfoBundle"/>
                            <tiles:put name="onclick">  showOrHideHistory (this);  </tiles:put>
                        </tiles:insert>
                    </span>
                </tiles:put>
            </tiles:insert>

            <div id="sendedHistory" style="display: none;">
                <span class="title">История заказанных отчетов</span>
                <tiles:insert definition="simpleTableTemplate" flush="false">
                    <tiles:put name="grid">
                        <sl:collection id="sendedAbstract" property="sendedAbstract" model="simple-pagination"
                                       onClick="ajaxTibleList('%1$s', %2$d, '%3$s', %4$d); return false;">
                            <sl:collectionItem title="Дата запроса" width="33%">
                                ${phiz:formatDateWithStringMonth(sendedAbstract.sendedDate)}
                            </sl:collectionItem>
                            <sl:collectionItem styleClass="reportTblCell" title="Начало периода" width="33%">
                                ${phiz:formatDateWithStringMonth(sendedAbstract.fromDate)}
                            </sl:collectionItem>
                            <sl:collectionItem styleClass="reportTblCell" title="Окончание периода" width="33%">
                                ${phiz:formatDateWithStringMonth(sendedAbstract.toDate)}
                            </sl:collectionItem>
                        </sl:collection>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${form.size == 0}"/>
                    <tiles:put name="emptyMessage">Вы не заказывали ни один отчет.</tiles:put>
                </tiles:insert>
            </div>

            <%-- /История заказанных отчетов --%>
    </c:otherwise>
</c:choose>
        </html:form>
    </c:otherwise>
</c:choose>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<html:form action="/ermb/profile/connection" onsubmit="return setEmptyAction(event);">

    <tiles:insert definition="personEdit">
        <tiles:importAttribute/>
        <tiles:put name="submenu" type="string" value="ErmbProfileConnect"/>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="profile" value="${form.profile}"/>
        <c:set var="serviceStatus" value="${profile.serviceStatus}"/>
        <c:set var="clientBlocked" value="${profile.clientBlocked}"/>
        <c:set var="paymentBlocked" value="${profile.paymentBlocked}"/>
        <c:set var="suppressAdv" value="${!profile.suppressAdv}"/>
        <c:set var="persistent" value="${profile.id != null}"/>
        <c:set var="gracePeriodEnd" value="${phiz:сalendarToString(profile.gracePeriodEnd)}"/>

        <c:set var="phoneControlUrl" value="${phiz:calculateActionURL(pageContext,'/ermb/profile/phones/control')}"/>
        <c:set var="phoneDeleteUrl" value="${phiz:calculateActionURL(pageContext, '/ermb/async/profile/connection/phone/delete.do')}"/>
        <tiles:put name="menu" type="string">
            <c:if test="${serviceStatus and not (profile.codActivated and profile.wayActivated)}">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.ermb.activate"/>
                    <tiles:put name="commandTextKey" value="button.ermb.activate"/>
                    <tiles:put name="commandHelpKey" value="button.ermb.activate"/>
                    <tiles:put name="bundle" value="ermbBundle"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:if>
            <tiles:insert definition="clientButton" flush="false" service="ErmbSmsHistoryService">
                <tiles:put name="commandTextKey" value="button.ermb.log.sms.title"/>
                <tiles:put name="commandHelpKey" value="button.ermb.log.sms.title"/>
                <tiles:put name="bundle" value="ermbBundle"/>
                <tiles:put name="action" value="/log/ermb/sms.do?personId=${profile.person.id}"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <input id="serviceStatus"  type="hidden" value="${serviceStatus}">
            <input id="clientBlocked"  type="hidden" value="${clientBlocked}">
            <input id="paymentBlocked"  type="hidden" value="${paymentBlocked}">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name" value="ЕРМБ. Настройка подключения"/>
                <tiles:put name="description"
                           value="Используйте форму для подключения или изменения параметров услуги
                           «Мобильный банк»"/>
                <tiles:put name="data">
                    <script type="text/javascript">

                        $(document).ready(function ()
                        {
                            initData();
                            <!--Льготный период. первый в списке всегда тариф профиля -->
                            loadTarifData($('select[name$="selectedTarif"] option:selected').val(), false);
                            <!--Продукты. checked -->
                            var products = $('input[name^="product"]');
                            $.each(products,function(index,product)
                            {
                                if ($(product).val() == "true")
                                {
                                    $(product).attr('checked','checked');
                                }
                            });

                            setStatusDiv($('#lockTime').val());
                            initTimeZone();
                        });

                        function calculateGraceBySelectedTariff(tarifId)
                        {
                            var gracePeriod = $("#tarif" + tarifId).val();
                            <!--Не отображается если тарифный план не предполагает льготный период.-->
                            if (gracePeriod == '0')
                                return '';

                            <!--Дата окончания льготного периода -->
                            var graceDate;
                            if (${persistent})
                            {
                                graceDate = moment('${gracePeriodEnd}', "DD.MM.YYYY");
                            }
                            else
                            {
                                var connectionDate = $("#connectionDate").val();
                                var cd = moment(connectionDate, "DD.MM.YYYY");
                                graceDate = cd.add('months', gracePeriod);
                            }
                            var currentDate = moment();
                            <!--Не отображается если эта дата уже прошла.-->
                            if (graceDate.isBefore(currentDate))
                                return '';

                            return graceDate.format("DD.MM.YYYY");
                        }

                        <!-- рассчитывает дату бизнесового (ненулевого) списания -->
                        function calculateActualNextPaymentDate(tarifCost, graceCost, currentGrace)
                        {
                            <!-- для бесплатного тарифа нет даты списания-->
                            if (tarifCost == '0.00' || tarifCost == '0')
                                return '';
                            <!-- если бесплатный грейс, то следующее списание - его окончание -->
                            if (currentGrace && (graceCost == '0.00' || graceCost == '0'))
                                return currentGrace;

                            var nextPaymentDate = $("#nextPaymentDate").text();
                            if (isEmpty(nextPaymentDate))
                                nextPaymentDate = getCurrentDate('.');

                            return nextPaymentDate;
                        }

                        function loadTarifData(tarifId, onTariffChange)
                        {
                            var currentGrace = calculateGraceBySelectedTariff(tarifId);
                            $("#endGraceDate").html(currentGrace);
                            var tarifCost = $("#tarifCost" + tarifId).val();
                            var tarifProductClass = $("#tarifProductClass" + tarifId).val();
                            var graceCost = $("#tarifGracePeriodCost" + tarifId).val();
                            var nextPaymentDate = calculateActualNextPaymentDate(tarifCost, graceCost, currentGrace);
                            $("#nextPaymentDate").html(nextPaymentDate);
                            $("#tarifCost").html(tarifCost);
                            $("#tarifProductClass").html(tarifProductClass);
                            var tarifAllowsCardSmsNotification = $("#tarifAllowsCardSmsNotification" + tarifId).val();
                            var allCardsNtf = $("#allCardsNtf");
                            if (allCardsNtf.length != 0)
                            {
                                if (tarifAllowsCardSmsNotification == "false")
                                {
                                    allCardsNtf[0].disabled = true;
                                    if (onTariffChange)
                                        allCardsNtf[0].checked = false;
                                    $("[name=cardsNtf]").each(
                                        function()
                                        {
                                            this.disabled = true;
                                            if(onTariffChange)
                                                this.checked = false;
                                        }
                                    )
                                }
                                else
                                {
                                    allCardsNtf[0].disabled = false;
                                    if(onTariffChange)
                                        allCardsNtf[0].checked = true;
                                    $("[name=cardsNtf]").each(
                                        function()
                                        {
                                            this.disabled = false;
                                            if(onTariffChange)
                                                this.checked = true;
                                        }
                                    )
                                }
                            }

                            var allAccountsNtf = $("#allAccountsNtf");
                            if (allAccountsNtf.length != 0)
                            {
                                var tarifAllowsAccountSmsNotification = $("#tarifAllowsAccountSmsNotification" + tarifId).val();
                                if (tarifAllowsAccountSmsNotification == "false")
                                {
                                    allAccountsNtf[0].disabled = true;
                                    if(onTariffChange)
                                        allAccountsNtf[0].checked = false;
                                    $("[name=accountsNtf]").each(
                                        function()
                                        {
                                            this.disabled = true;
                                            if(onTariffChange)
                                                this.checked = false;
                                        }
                                    )
                                }
                                else
                                {
                                    allAccountsNtf[0].disabled = false;
                                    if(onTariffChange)
                                        allAccountsNtf[0].checked = true;
                                    $("[name=accountsNtf]").each(
                                        function()
                                        {
                                            this.disabled = false;
                                            if(onTariffChange)
                                                this.checked = true;
                                        }
                                    )
                                }
                            }
                        }

                        function validateRemovePhone()
                        {
                            if (checkPhoneSelection('Удалять телефоны можно только по одному!'))
                            {
                                var phoneCode = $('input[name^="codesToPhoneNumber"]:checked')[0];
                                if (phoneCode.className == "newPhoneNumber")
                                {
                                    var phoneRow = phoneCode.parentNode.parentNode;
                                    phoneRow.parentNode.removeChild(phoneRow);
                                    return false;
                                }
                                return true;
                            }
                            return false;
                        }

                        function setRemovedPhonesToForm()
                        {
                            validateRemovePhone();
                            var phoneCode = $('input[name^="codesToPhoneNumber"]:checked')[0];
                            var codeToOfert = $('input[name^="phoneCodeToCheck"]');

                            var phoneCodeValue = $(phoneCode).val();
                            var name = "codePhonesToDelete(" + phoneCodeValue + ")";
                            if (codeToOfert.length == 0 || !codeToOfert[0].checked)
                            {
                                phoneCode.parentNode.parentNode.style.display = "none";
                                $('#mainPhoneNumberCode').find('option[value="' + phoneCodeValue + '"]').remove();
                                phoneCode.name = phoneCode.name.replace("codesToPhoneNumber", "codePhonesToDelete");
                                phoneCode.disabled = true;
                                document.getElementById('isSendOfert(' + phoneCodeValue + ')').innerHTML = '<input type="checkbox" value="'+phoneCodeValue+'" checked="false" name="' + name + '">';
                            }
                            else
                            {
                                alert('Телефон будет удален после подтверждения клиентом!');
                            }
                        }

                        /*с помощью js москировать номер и добавлять его в phonTd, a полный номер в  hdnInput */
                        function addPhone(phoneNumber)
                        {
                            <!-- добавляем в список Телефоны -->
                            var phons =  $('#phonTd .standartTable tbody');
                            var maskedPhoneNumber =  getMaskedPhoneNumber(templateObj.PHONE_NUMBER_INTERNATIONAL,phoneNumber);
                            phons.append(
                                    '<tr>' +
                                            '<td class="ListLine">' +
                                            '<input type="checkbox" value="'+ phoneNumber +'" name="codesToPhoneNumber('+phoneNumber+')"  class="newPhoneNumber">'+
                                            '</td>' +
                                            '<td class="ListLine">' +
                                                '<label id="maskedNumber">' + maskedPhoneNumber +'</label>' +
                                            '</td>' +
                                            '<td style="display: none" id="isSendOfert(' + phoneNumber + ')"> </td>' +
                                     '</tr>'
                            );
                            <!-- добавляем в выпадающий список Основной номер -->
                            var mainPhoneNumber = $('#mainPhoneNumberCode select') ;
                            var option = document.createElement('option');
                            $(option).val(phoneNumber);
                            $(option).text(maskedPhoneNumber);
                            mainPhoneNumber.append(option);
                            updatePhoneIndex();
                        }

                        function doAdd(phoneNumber, ermbProfileId)
                        {

                            if (phoneTest(phoneNumber))
                            {
                                var PHON_CTRL_URL = '${phoneControlUrl}';
                                var params = 'phoneNumber='+phoneNumber+addRemovedPhoneToRequest();
                                <!-- Проверяем не привязан  ли такой телефон к другому клиенту -->
                                preloader(true);
                                ajaxQuery (params, PHON_CTRL_URL, function(data)
                                {
                                    if (data == 'free')
                                    {
                                        addPhone(phoneNumber);
                                    }
                                    else if(data.indexOf('phone-to-restore:')>=0)
                                    {
                                        //получаем код телефона, отмеченного на удаление и восстанавливаем номер телефона
                                        restoreRemovedNumber(data.substr('phone-to-restore:'.length, data.length), phoneNumber);
                                    }
                                    else if (data == ermbProfileId)
                                    {
                                        alert("Телефон с таким номером уже добавлен");
                                    }
                                    else if (data == 'error')
                                    {
                                        alert("Ошибка проверки принадлежности телефона");
                                    }
                                    <!-- привязан к другому профилю (в этом или другом блоке) или к мбк-->
                                    else
                                    {
                                        if (confirm('Такой номер уже используется в системе. Подтвердить владение телефоном?'))
                                        {
                                            <!-- отсылаем код подтверждения на указанный номер -->
                                            params += '&operation=sendConfirmCode';
                                            ajaxQuery(params, PHON_CTRL_URL, function (confirmData)
                                            {
                                                if (confirmData == 'false')
                                                {
                                                    alert('Не удалось отправить сообщения c кодом подтверждения.');
                                                }
                                                else
                                                {
                                                    <!-- отображаем окно для ввод кода подтверждения -->
                                                    showLayer('confirmHoldeParentDiv', 'confirmHolderDiv', 'default');
                                                    $('#confirmPhone').val(phoneNumber);
                                                    $('#isMbk').val(data.indexOf('used-in-mbk')>=0);
                                                }
                                            });
                                        }
                                    }
                                    preloader(false);
                                });
                            }
                        }

                        <!-- todo нормальный прелоудер в арм-->
                        function preloader(show)
                        {
                            if (show == true)
                            {
                                $('#centerLoadDiv').show();
                                setTintedDiv(true);
                            }
                            else
                            {
                                $('#centerLoadDiv').hide();
                                setTintedDiv(false);
                            }
                        }

                        function phoneTest(phoneNumber)
                        {
                            if (!templateObj.PHONE_NUMBER_INTERNATIONAL.validate(phoneNumber))
                            {
                                alert("Неверный формат номера телефона");
                                return false;
                            } else {
                                //Проверяем,что добавляемый номер телефона есть на форме
                                var phons =  $('.newPhoneNumber');
                                var isNewPhone = true;
                                $.each(phons,function(index,phone)
                                {
                                    if ($(phone).val()==phoneNumber)
                                    {
                                        alert("Телефон с таким номером уже добавлен");
                                        isNewPhone = false;
                                    }
                                });
                                return isNewPhone;
                            }
                            return true;
                        }

                        function addRemovedPhoneToRequest()
                        {
                            var phoneRemovedToRequest = $('input[name^="codePhonesToDelete"]');
                            var request='';
                            $.each(phoneRemovedToRequest,function(index,phoneToRequest)
                            {
                                if (!$(phoneToRequest)[0].disabled)
                                    request += '&phonesToDelete[]='+$(phoneToRequest).val();
                            });
                            return request;
                        }

                        function restoreRemovedNumber(removedNumberCode, phoneNumber)
                        {
                            var phoneCode = $('input[name^="codePhonesToDelete('+removedNumberCode+')"]')[0];

                            phoneCode.parentNode.parentNode.style="";
                            phoneCode.name = phoneCode.name.replace("codePhonesToDelete", "codesToPhoneNumber");
                            phoneCode.disabled = false;
                            document.getElementById('isSendOfert(' + removedNumberCode + ')').innerHTML = '';
                            //восстанавливаем выбор главного номера
                            var mainPhoneNumber = $('#mainPhoneNumberCode select') ;
                            var maskedPhoneNumber =  getMaskedPhoneNumber(templateObj.PHONE_NUMBER_INTERNATIONAL,phoneNumber);
                            var option = document.createElement('option');
                            $(option).val(removedNumberCode);
                            $(option).text(maskedPhoneNumber);
                            mainPhoneNumber.append(option);
                            updatePhoneIndex();
                        }

                        function clickPhones()
                        {  <!--необходимо зачекать что бы передалось в bean-->
                            var phones = $('input[name^="codesToPhoneNumber"]');
                             $.each(phones,function(index,phone)
                             {
                                 $(phone).attr('checked','checked');
                             });
                        }

                        function updatePhoneIndex()
                        {
                            var phones = $('input[name^="phoneNumber"]');
                            $.each(phones,function(index,phone)
                            {
                                $(phone).attr("name","phoneNumber["+index+"]");
                            });
                        }

                        function checkPhoneSelection(msg)
                        {
                            var el = $('input[name^=codesToPhoneNumber]:checked');
                            var el2 = $('input[name^=isSendOfertCode]:checked');
                            if (el.length+el2.length!=1)
                            {
                                alert(msg);
                                return false;
                            }
                            return true;
                        }

                        function checkPhone()
                        {
                            var el = $('input[name^=codesToPhoneNumber]');
                            if (el.length==0)
                            {
                                alert("Необходимо добавить телефон");
                                return false;
                            }
                            clickPhones();
                            return true;
                        }

                        function checkAndUpdate()
                        {
                            var fromTime = $('input[name$="ntfStartTimeString"]').val();
                            var toTime = $('input[name$="ntfEndTimeString"]').val();
                            var isPhoneValid = validateTimePeriod(fromTime, toTime);

                            if (!(isPhoneValid))
                                return false;
                            clickPhones();
                            return true;
                        }

                        <%--
                        name = имя поля по которому определяем в кокой див менять
                        msgType = виды сообщений (0 - разрешены/запрещены 1 или null - подключены/не подключены)
                        --%>

                        function changeAvailable(name,msgType)
                        {
                            var el = $("input[name$='"+ name +"']");
                            var divEl= $('#'+name+'Div');

                            var trueMsg = null;
                            var falseMsg = null;

                            if (msgType){

                                trueMsg = 'подключены';
                                falseMsg = 'не подключены';
                            }
                            else{
                                trueMsg = 'разрешены';
                                falseMsg = 'запрещены';
                            }

                            if  ($(el).val()=='true')
                            {
                                $(divEl).html(falseMsg);
                                $(el).val('false');
                            }
                            else
                            {
                                $(divEl).html(trueMsg);
                                $(el).val('true');
                            }
                        }

                        function setTimeZone()
                        {
                           var selectVal = $('#timeZoneSelect option:selected').val();
                           $('input[name$="timeZone"]').val(selectVal);
                        }

                        function initTimeZone()
                        {
                            var timeZoneVal = $('input[name$="timeZone"]').val();
                            var select = getElement('timeZoneSelect');
                            $('#timeZoneSelect').val(timeZoneVal).attr('select',true);
                        }

                        function setStatusDiv(lockTime)
                        {
                            var ermbStatus = $('#clientBlocked').val();
                            if (ermbStatus == 'false')
                            {
                                 hideLayer('unlockDiv');
                                $('#blockedDiv').css("display", "none");
                                $('#activeDiv').css("display", "block");
                            }
                            else
                            {
                                hideLayer('lockDiv');
                                $('#blockedDiv').css("display", "block");
                                $('#activeDiv').css("display", "none");
                                if (lockTime != null)
                                    $('#lockTimeDiv').text('(' + lockTime + ')');
                                else
                                   $('#lockTimeDiv').text('(' + moment().format("DD.MM.YYYY") + ')');
                            }
                        }

                        function blockAjaxResult(msg)
                        {
                            if (msg.length != 0)
                            {
                               var regexp = new RegExp('^\\d');
                               if (regexp.test(msg))
                               {
                                   var el = $('#clientBlocked');
                                   if ($(el).val() == 'true')
                                       el.val('false');
                                   else
                                       el.val('true');
                                   setStatusDiv();
                                   $('input[name="profileOldVersion"]')[0].value = msg;
                               }
                               else
                               {
                                   alert(msg);
                               }
                            }
                        }



                        function ajaxBlock()
                        {
                            <c:set var="lockUrl" value="${phiz:calculateActionURL(pageContext,'/ermb/profile/block')}"/>
                            var LOCK_URL = '${lockUrl}';
                            var lockDesk = $('textarea[name$="field(lockDescription)"]').val();
                            var newStatus = $('#clientBlocked').val() == 'true' ? 'false' : 'true';

                            var params = 'id='+${profile.id} + '&field(status)='+newStatus+'&field(lockDescription)='+decodeURItoWin(lockDesk);
                            preloader(true);
                            ajaxQuery(params, LOCK_URL, function (data) {
                                blockAjaxResult(data);
                                preloader(false);
                            });
                        }

                        function ajaxConfirmPhoneNumberByCode()
                        {
                            var URL = '${phoneControlUrl}';
                            var confirmCode = $('#confirmHolderCodeInput').val();
                            var phoneNumber = $('#confirmPhone').val();
                            var params = 'confirmCode='+confirmCode +'&phoneNumber='+phoneNumber+'&operation=testSwapPhoneNumberCode';
                            preloader(true);
                            ajaxQuery (params, URL, function(data){
                                if (data=='true')
                                {
                                    addPhone(phoneNumber);
                                    hideLayer('confirmHolderDiv');
                                    if ('true' == $('#isMbk').val())
                                    {
                                        addMbkPhone(phoneNumber);
                                    }
                                }
                                else
                                {
                                    $('#confirmHolderCodeInput').val('');
                                    alert('Код не принят');
                                }
                                preloader(false);
                            });
                        }

                        <!-- телефоны из мбк, по которым получено подтверждение-->
                        function addMbkPhone(phoneNumber)
                        {
                            $('#mbkPhonesDiv').append(
                                    '<input type="hidden" name="mbkPhones" value="' + phoneNumber + '"/>'
                            );
                        }

                        function ajaxResetIMSI()
                        {
                            if (confirm("Сбросить сохраненное значение IMSI для введенного номера?"))
                            {
                                var URL = '${phoneControlUrl}';
                                if (checkPhoneSelection('Укажите один телефон'))
                                {
                                    var phoneCodes = $('input[name^="codesToPhoneNumber"]:checked');
                                    var params = 'operation=resetIMSI&profileId=${profile.id}';
                                    $.each(phoneCodes,function(index,phoneCode){
                                        params = params + '&phoneCode='+$(phoneCode).val()

                                    }) ;
                                    preloader(true);
                                    ajaxQuery (params, URL, function(data){
                                        if (data=='false')
                                        {
                                            alert('Не удалось отправить сообщения на сброс IMSI.');
                                        }
                                        else
                                        {
                                            alert('Сообщение на сброс IMSI для выбранных номеров отправлено.');
                                        }
                                        preloader(false);
                                    });
                                }
                            }
                        }

                        function ajaxDeletePhone()
                        {
                            // получаем закодированный номер телефона, выбранный для удаления
                            var phoneCode = $('input[name^="codesToPhoneNumber"]:checked')[0];
                            //Проверяем стоит ли галка "Отправить код подтверждения"
                            var phoneCodeToCheck = $('input[name^="phoneCodeToCheck"]');
                            if(phoneCodeToCheck.length != 0 && phoneCodeToCheck[0].checked)
                            {
                                // если установлена, то отправляем ajax запрос на подтверждение удаления номера
                                var params = 'profileId=${profile.id}&phoneCode='+$(phoneCode).val();
                                var URL = '${phoneDeleteUrl}';
                                preloader(true);
                                ajaxQuery(params, URL, function (){
                                    setRemovedPhonesToForm();
                                    preloader(false);
                                });
                            } else
                            {
                                //если нет - скрываем номер с формы и удаляем телефон из селекта для выбора главного номера
                                setRemovedPhonesToForm();
                            }
                        }

                        function showBlockReason()
                        {
                            alert('Причина блокировки: ' + $('textarea[name$="field(lockDescription)"]').val());
                        }
                    </script>

                    <c:if test="${not empty form.profileOldVersion}">
                        <html:hidden property="profileOldVersion"/>
                    </c:if>
                    <div id="mbkPhonesDiv" style="display:none;">
                    </div>
                    <fieldset>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.connection.date" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <input type="hidden" id="connectionDate" value="${phiz:сalendarToString(profile.connectionDate)}"/>
                                <c:out value="${phiz:сalendarToString(profile.connectionDate)}"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.service.status" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <c:choose>
                                    <c:when test='${serviceStatus and not paymentBlocked}'>
                                        <div id="activeDiv">
                                            <span class="float" style="color:green"><bean:message key="ermb.service.status.active" bundle="ermbBundle"/></span>
                                            <div style="display:inline-block;" class="float">
                                                <tiles:insert definition="clientButton" flush="false">
                                                    <tiles:put name="commandTextKey" value="button.lock"/>
                                                    <tiles:put name="commandHelpKey" value="button.lock"/>
                                                    <tiles:put name="bundle" value="ermbBundle"/>
                                                    <tiles:put name="onclick" value="showLayer('lockParentDiv','lockDiv','default');"/>
                                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                                </tiles:insert>
                                            </div>
                                            <div class="clear"></div>
                                            <div id="lockParentDiv" style="display:inline-block;">
                                                <div id="lockDiv"
                                                     class="layerFon"
                                                     style="display:none;position:absolute; width:492px; overflow:auto;z-index: 1;">

                                                    <html:textarea property="field(lockDescription)"  cols="55"
                                                                   rows="4"
                                                                   style="text-align:justify;"
                                                                   value="${form.lockDescription}"/>

                                                    <tiles:insert definition="clientButton" flush="false">
                                                        <tiles:put name="commandTextKey" value="button.block"/>
                                                        <tiles:put name="commandHelpKey" value="button.block"/>
                                                        <tiles:put name="bundle" value="ermbBundle"/>
                                                        <tiles:put name="viewType" value="buttonGrayNew"/>
                                                        <tiles:put name="onclick">
                                                            ajaxBlock();
                                                        </tiles:put>
                                                    </tiles:insert>
                                                    <tiles:insert definition="clientButton" flush="false">
                                                        <tiles:put name="commandTextKey" value="button.cancel"/>
                                                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                                        <tiles:put name="bundle" value="commonBundle"/>
                                                        <tiles:put name="onclick" value="hideLayer('lockDiv');"/>
                                                        <tiles:put name="viewType" value="buttonGrayNew"/>
                                                    </tiles:insert>
                                                </div>
                                            </div>
                                        </div>

                                        <div id="blockedDiv">
                                            <div class="float" style="color:grey"><bean:message key="ermb.service.status.block" bundle="ermbBundle"/>&nbsp;</div>
                                            <input type="hidden" value="${phiz:сalendarToString(profile.lockTime)}" id="lockTime"/>
                                            <div id="lockTimeDiv" class="float" style="display:inline-block"/>
                                                <c:out value="(${phiz:сalendarToString(profile.lockTime)})"/>
                                            </div>
                                            <a class="float" href='javascript:showBlockReason()'>
                                                <bean:message key="ermb.block.description" bundle="ermbBundle"/>
                                            </a>
                                            <div class="clear"></div>
                                            <div class="float" style="display:inline-block;">
                                                <tiles:insert definition="clientButton" flush="false">
                                                    <tiles:put name="commandTextKey" value="button.unlock"/>
                                                    <tiles:put name="commandHelpKey" value="button.unlock"/>
                                                    <tiles:put name="bundle" value="ermbBundle"/>
                                                    <tiles:put name="onclick" value="showLayer('unlockParentDiv','unlockDiv','default');"/>
                                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                                </tiles:insert>
                                            </div>
                                            <div id="unlockParentDiv" style="display:inline-block;">
                                                <div id="unlockDiv"
                                                     class="layerFon"
                                                     style="display:none;position:absolute; width:392px; height:55;overflow:auto;">
                                                    <bean:message key="ermb.unlock.description" bundle="ermbBundle"/>
                                                    <div>
                                                        <tiles:insert definition="clientButton" flush="false">
                                                            <tiles:put name="commandTextKey" value="button.unlock"/>
                                                            <tiles:put name="commandHelpKey" value="button.unlock"/>
                                                            <tiles:put name="bundle" value="ermbBundle"/>
                                                            <tiles:put name="viewType" value="buttonGrayNew"/>
                                                            <tiles:put name="onclick">
                                                                ajaxBlock();
                                                            </tiles:put>
                                                        </tiles:insert>
                                                        <tiles:insert definition="clientButton" flush="false">
                                                            <tiles:put name="commandTextKey" value="button.cancel"/>
                                                            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                                            <tiles:put name="bundle" value="commonBundle"/>
                                                            <tiles:put name="onclick" value="hideLayer('unlockDiv');"/>
                                                            <tiles:put name="viewType" value="buttonGrayNew"/>
                                                        </tiles:insert>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:when test='${serviceStatus and paymentBlocked}'>
                                       <span style="color:peru"><bean:message key="ermb.service.status.not.paid" bundle="ermbBundle"/></span>
                                    </c:when>
                                    <c:when test='${not serviceStatus}'>
                                        <div class="buttonState float" style="color:grey">
                                            <bean:message key="ermb.service.status.not.connected" bundle="ermbBundle"/>
                                        </div>
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="commandKey" value="button.connect"/>
                                            <tiles:put name="commandTextKey" value="button.connect"/>
                                            <tiles:put name="commandHelpKey" value="button.connect"/>
                                            <tiles:put name="bundle" value="ermbBundle"/>
                                            <tiles:put name="postbackNavigation" value="true"/>
                                            <tiles:put name="validationFunction">checkPhone();</tiles:put>
                                        </tiles:insert>
                                    </c:when>
                                </c:choose>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.client.category" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <c:out value="${phiz:cleintSegmentCode(profile)}"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.connection.department" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <c:set var="connectedDepartment" value="${phiz:getDepartmentById(profile.connectedDepartment)}"/>
                                <c:if test="${employeeDepartment!=null}">
                                    <c:out value="${employeeDepartment.code.office}"/>
                                </c:if>
                                <c:out value="${connectedDepartment.name}"/>
                            </tiles:put>
                        </tiles:insert>

                    </fieldset>

                    <fieldset>
                        <legend>
                            <bean:message key="ermb.tarif.info" bundle="ermbBundle"/>
                        </legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.tariff.plane" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <c:forEach items="${form.tarifs}" var="tarif">
                                    <input type="hidden" id="tarif${tarif.id}" value="${tarif.gracePeriod}"/>
                                    <input type="hidden" id="tarifChargePeriod${tarif.id}" value="${tarif.chargePeriod}"/>
                                    <input type="hidden" id="tarifGracePeriodCost${tarif.id}" value="${tarif.gracePeriodCost.decimal}"/>
                                    <input type="hidden" id="tarifAllowsCardSmsNotification${tarif.id}" value="${phiz:isTariffAllowCardSmsNotification(profile, tarif)}"/>
                                    <input type="hidden" id="tarifAllowsAccountSmsNotification${tarif.id}" value="${phiz:isTariffAllowAccountSmsNotification(profile, tarif)}"/>
                                    <input type="hidden" id="tarifCost${tarif.id}" value="${phiz:getErmbCostNoGrace(profile, tarif).decimal}"/>
                                    <input type="hidden" id="tarifProductClass${tarif.id}" value="${phiz:getClientProductClassName(profile, tarif)}"/>
                                </c:forEach>
                                <html:select property="selectedTarif"
                                             onchange="loadTarifData(this.value, true);">
                                    <html:optionsCollection name="form" property="tarifs"
                                                            value="id"
                                                            label="name"/>
                                </html:select>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.product.class" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <div id="tarifProductClass"></div>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.subscription.fee" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <div id="tarifCost"></div>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.grace.period.expiry.date" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <div id="endGraceDate"></div>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.next.payment.date" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <div id="nextPaymentDate">
                                    <c:out value="${phiz:сalendarToString(profile.chargeNextDate)}"/>
                                </div>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                    <fieldset>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.main.card" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <html:select property="mainCardId">
                                    <html:option value="0">по умолчанию</html:option>
                                    <c:forEach var="cardLink" items="${profile.ermbAvailablePaymentCards}" varStatus="li">
                                        <html:option value="${cardLink.id}"><c:out value="${phiz:getCutCardNumber(cardLink.number)}"/></html:option>
                                    </c:forEach>
                                </html:select>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                    <fieldset>
                        <legend>
                            <bean:message key="ermb.phones" bundle="ermbBundle"/>
                        </legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.add"/>
                                    <tiles:put name="commandHelpKey" value="button.add.help"/>
                                    <tiles:put name="bundle" value="ermbBundle"/>
                                    <tiles:put name="onclick">doAdd($('#addPhoneNumber').val(), '${profile.id}');</tiles:put>
                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.phones.number" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <input id="addPhoneNumber" maxlength="11"/>
                                <bean:message key="ermb.phone.hint" bundle="ermbBundle"/>
                                <div id="confirmHoldeParentDiv" style="display:inline-block;">
                                    <div id="confirmHolderDiv"
                                         class="layerFon"
                                         style="display:none;position:absolute; width:330px; height:60;overflow:auto;">
                                        <bean:message key="ermb.confirm.holder.code" bundle="ermbBundle"/>
                                        <input id="confirmHolderCodeInput" size="10" maxlength="10">
                                        <input id="confirmPhone" type="hidden">
                                        <input id="isMbk" type="hidden">
                                        <div>

                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey" value="button.confirmCode"/>
                                                <tiles:put name="commandHelpKey" value="button.confirmCode"/>
                                                <tiles:put name="bundle" value="ermbBundle"/>
                                                <tiles:put name="onclick">
                                                    ajaxConfirmPhoneNumberByCode();
                                                </tiles:put>
                                            </tiles:insert>
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                                <tiles:put name="bundle" value="commonBundle"/>
                                                <tiles:put name="onclick">
                                                    hideLayer('confirmHolderDiv');
                                                </tiles:put>
                                            </tiles:insert>
                                        </div>
                                    </div>
                                </div>
                            </tiles:put>
                        </tiles:insert>

                        <div id="phonTd">
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    <table width="100%" cellspacing="0" cellpadding="0" align="CENTER" class="standartTable">
                                    <tr>
                                        <th width="20">&nbsp;</th>
                                        <th>
                                            <bean:message key="ermb.phone.number" bundle="ermbBundle"/>
                                        </th>
                                    </tr>
                                    <c:forEach var="phone" items="${form.codesToPhoneNumber}">
                                        <tr>
                                            <td class="ListLine">
                                                <input type="checkbox" value="${phone.key}" name="codesToPhoneNumber(${phone.key})"/>
                                            </td>
                                            <td class="ListLine">
                                                <label id="maskedNumber"><c:out value="${phone.value}"/></label>
                                            </td>
                                            <td style="display: none" id="isSendOfert(${phone.key})">
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    </table>
                                </tiles:put>
                            </tiles:insert>
                        </div>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <tiles:insert definition="confirmationButton" flush="false">
                                    <tiles:put name="winId"                 value="confirmBack"/>
                                    <tiles:put name="title">Удалить выбранный номер?</tiles:put>
                                    <tiles:put name="message"><c:if test="${serviceStatus == true}"><div><input type='checkbox' name = 'phoneCodeToCheck' checked='true'/>Отправить подтверждение?</div></c:if></tiles:put>
                                    <tiles:put name="buttonViewType"        value="buttonGrayNew"/>
                                    <tiles:put name="confirmCommandKey"     value="button.remove"/>
                                    <tiles:put name="confirmCommandTextKey" value="button.remove"/>
                                    <tiles:put name="confirmKey"            value="button.remove"/>
                                    <tiles:put name="currentBundle"         value="ermbBundle"/>
                                    <tiles:put name="afterConfirmFunction">ajaxDeletePhone()</tiles:put>
                                    <tiles:put name="validationFunction">validateRemovePhone()</tiles:put>
                                </tiles:insert>
                                <tiles:insert definition="clientButton" flush="false" service="IMSIResetService">
                                    <tiles:put name="commandTextKey" value="button.resetIMSI"/>
                                    <tiles:put name="commandHelpKey" value="button.resetIMSI"/>
                                    <tiles:put name="bundle" value="ermbBundle"/>
                                    <tiles:put name="viewType"       value="buttonGrayNew"/>
                                    <tiles:put name="onclick">
                                        ajaxResetIMSI();
                                    </tiles:put>
                                </tiles:insert>
                            <div>
                                <div class="clear"></div>
                            </div>
                            </tiles:put>
                        </tiles:insert>


                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.main.phone.number" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <div id="mainPhoneNumberCode">
                                    <html:select property="mainPhoneNumberCode">
                                        <c:forEach var="phone" items="${form.codesToPhoneNumber}">
                                            <html:option value="${phone.key}">
                                                <c:out value="${phone.value}"/>
                                            </html:option>
                                        </c:forEach>
                                    </html:select>
                                </div>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                    <fieldset>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.fast.service" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:hidden property="fastServiceAvailable"/>
                                <div id="fastServiceAvailableDiv" class="buttonState float">
                                    <c:choose>
                                        <c:when test="${form.fastServiceAvailable}">
                                            <bean:message key="ermb.connect" bundle="ermbBundle"/>
                                        </c:when>
                                        <c:otherwise>
                                            <bean:message key="ermb.not.connect" bundle="ermbBundle"/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.connect.disconnect"/>
                                    <tiles:put name="commandHelpKey" value="button.connect.disconnect"/>
                                    <tiles:put name="bundle" value="ermbBundle"/>
                                    <tiles:put name="onclick">changeAvailable('fastServiceAvailable',true);</tiles:put>
                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.notification.transfert.pedosits" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:hidden property="depositsTransferAvailable"/>
                                <div id="depositsTransferAvailableDiv" class="buttonState float">
                                    <c:choose>
                                        <c:when test="${form.depositsTransferAvailable}">
                                            <bean:message key="ermb.allowed" bundle="ermbBundle"/>
                                        </c:when>
                                        <c:otherwise>
                                            <bean:message key="ermb.denied" bundle="ermbBundle"/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.connect.disconnect"/>
                                    <tiles:put name="commandHelpKey" value="button.connect.disconnect"/>
                                    <tiles:put name="bundle" value="ermbBundle"/>
                                    <tiles:put name="onclick">changeAvailable('depositsTransferAvailable',false);</tiles:put>
                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.promotional.mailing" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:hidden property="promotionalMailingAvailable"/>
                                <div id="promotionalMailingAvailableDiv" class="buttonState float">
                                    <c:choose>
                                        <c:when test="${form.promotionalMailingAvailable}">
                                            <bean:message key="ermb.allowed" bundle="ermbBundle"/>
                                        </c:when>
                                        <c:otherwise>
                                            <bean:message key="ermb.denied" bundle="ermbBundle"/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.connect.disconnect"/>
                                    <tiles:put name="commandHelpKey" value="button.connect.disconnect"/>
                                    <tiles:put name="bundle" value="ermbBundle"/>
                                    <tiles:put name="onclick">changeAvailable('promotionalMailingAvailable',false);</tiles:put>
                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.schedule.notification" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <bean:message key="ermb.from" bundle="ermbBundle"/>
                                <input type="text" name="ntfStartTimeString" size="5" maxlength="5"
                                       value="<bean:write name="form" property="ntfStartTimeString" format="HH:MM"/>"/>
                                <bean:message key="ermb.to" bundle="ermbBundle"/>
                                <input type="text" name="ntfEndTimeString" size="5"  maxlength="5"
                                       value="<bean:write name="form" property="ntfEndTimeString" format="HH:MM"/>"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.time.zone" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:hidden property="timeZone"/>
                                <select id="timeZoneSelect" onchange="setTimeZone();">
                                    <c:forEach var="timeZone" items="${form.timeZoneList}">
                                        <option value="${timeZone.code}">
                                            ${timeZone.text}
                                        </option>
                                    </c:forEach>
                                </select>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                    <div>
                        <html:multibox property="ntfDays" value="MON"/>
                        <bean:message key="ermb.btn.monday" bundle="ermbBundle"/>
                        <html:multibox property="ntfDays" value="TUE"/>
                        <bean:message key="ermb.btn.tuesday" bundle="ermbBundle"/>
                        <html:multibox property="ntfDays" value="WED"/>
                        <bean:message key="ermb.btn.wednesday" bundle="ermbBundle"/>
                        <html:multibox property="ntfDays" value="THU"/>
                        <bean:message key="ermb.btn.thursday" bundle="ermbBundle"/>
                        <html:multibox property="ntfDays" value="FRI"/>
                        <bean:message key="ermb.btn.friday" bundle="ermbBundle"/>
                        <html:multibox property="ntfDays" value="SAT"/>
                        <bean:message key="ermb.btn.saturday" bundle="ermbBundle"/>
                        <html:multibox property="ntfDays" value="SUN"/>
                        <bean:message key="ermb.btn.sunday" bundle="ermbBundle"/>
                    </div>
                    <br/>

                    <c:choose>
                        <c:when test="${phiz:isCommonAttributeUseInProductsAvailable()}">
                            <c:set var="commonAttribute" value="true"/>
                            <c:set var="useInSmsBankTitle"><bean:message key="ermb.sms.useInSmsBank" bundle="ermbBundle"/></c:set>
                        </c:when>
                        <c:otherwise>
                            <c:set var="commonAttribute" value="false"/>
                            <c:set var="tableClass" value="notCommonAttribute"/>
                            <c:set var="ermbSmsOperationsTitle"> <bean:message key="ermb.sms.operations" bundle="ermbBundle"/> </c:set>
                            <c:set var="useSmsCommandsTitle"> <bean:message key="ermb.sms.useSmsCommands" bundle="ermbBundle"/> </c:set>
                            <c:set var="ermbSmsNotificationTitle"><bean:message key="ermb.sms.notification" bundle="ermbBundle"/></c:set>
                            <c:set var="ermbSmsNotifyTitle"><bean:message key="ermb.sms.notify" bundle="ermbBundle"/></c:set>
                        </c:otherwise>
                    </c:choose>
                    <div class="tableCaption"><bean:message key="ermb.notification.settings" bundle="ermbBundle"/></div>

                    <table width="100%" cellspacing="0" cellpadding="0" align="CENTER" class="standartTable ermbNotificationTable ${tableClass}">
                        <tbody>
                        <tr>
                            <th>
                                <bean:message key="ermb.client.card" bundle="ermbBundle"/>
                            </th>
                            <c:choose>
                                <c:when test="${commonAttribute}">
                                    <th width="160px">
                                        <input name="isSelectedAllCardsAvailableInSmsBankItems" type="checkbox" style="border:none" onclick="switchSelection('isSelectedAllCardsAvailableInSmsBankItems','cardsAvailableInSmsBank')"/>
                                        ${useInSmsBankTitle}
                                    </th>
                                </c:when>
                                <c:otherwise>
                                    <th class="smsOperationstitle">
                                        <input name="isSelectedAllCardsShowInSmsItems" type="checkbox" style="border:none" onclick="switchSelection('isSelectedAllCardsShowInSmsItems','cardsShowInSms')"/>
                                        ${ermbSmsOperationsTitle}
                                    </th>
                                    <th class="smsNotificationsTitle">
                                        <input  id="allCardsNtf" name="isSelectedAllCardsNtfItems" type="checkbox" style="border:none" onclick="switchSelection('isSelectedAllCardsNtfItems','cardsNtf')"/>
                                        ${ermbSmsNotificationTitle}
                                    </th>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                        <c:forEach var="cardLink" items="${profile.cardLinks}" varStatus="li">
                            <c:set var="cardNumber" value="${cardLink.number}"/>
                            <c:set var="cardlinkId"  value="${cardLink.id}"/>
                            <tr class="ermbNotificationProduct">
                                <td>
                                    <label><c:out value="${cardLink.name}"/> <c:out value="${phiz:getCutCardNumber(cardLink.number)}"/></label>
                                </td>
                                <c:choose>
                                   <c:when test="${commonAttribute}">
                                       <td>
                                          <html:multibox property="cardsAvailableInSmsBank" value="${cardlinkId}"/> ${useInSmsBankTitle}
                                       </td>
                                   </c:when>
                                   <c:otherwise>
                                       <td>
                                           <html:multibox property="cardsShowInSms" value="${cardlinkId}"/>
                                           ${useSmsCommandsTitle}
                                       </td>
                                       <td>
                                           <html:multibox property="cardsNtf" value="${cardlinkId}"/>
                                           ${ermbSmsNotifyTitle}
                                       </td>
                                   </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <table width="100%" cellspacing="0" cellpadding="0" align="CENTER" class="standartTable ermbNotificationTable ${tableClass}">
                        <tbody>
                        <tr>
                            <th>
                                <bean:message key="ermb.accounts.and.deposits" bundle="ermbBundle"/>
                            </th>
                            <c:choose>
                                <c:when test="${commonAttribute}">
                                    <th width="160px">
                                        <input name="isSelectedAllAccountsAvailableInSmsBankItems" type="checkbox" style="border:none" onclick="switchSelection('isSelectedAllAccountsAvailableInSmsBankItems','accountsAvailableInSmsBank')"/>
                                        ${useInSmsBankTitle}
                                    </th>
                                </c:when>
                                <c:otherwise>
                                    <th class="smsOperationstitle">
                                        <input name="isSelectedAllAccountsShowInSmsItems" type="checkbox" style="border:none" onclick="switchSelection('isSelectedAllAccountsShowInSmsItems','accountsShowInSms')"/>
                                        ${ermbSmsOperationsTitle}
                                    </th>
                                    <th class="smsNotificationsTitle">
                                        <input id="allAccountsNtf" name="isSelectedAllAccountsNtfItems" type="checkbox" style="border:none" onclick="switchSelection('isSelectedAllAccountsNtfItems','accountsNtf')"/>
                                        ${ermbSmsNotificationTitle}
                                    </th>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                        <c:forEach var="accountLink" items="${profile.accountLinks}" varStatus="li">
                            <c:set var="accountNumber" value="${accountLink.number}"/>
                            <c:set var="accountLinkId"  value="${accountLink.id}"/>
                            <tr class="ermbNotificationProduct">
                                <td>
                                    <label><c:out value="${accountLink.name}"/> <c:out value="${accountNumber}"/></label>
                                </td>
                                <c:choose>
                                   <c:when test="${commonAttribute}">
                                       <td>
                                          <html:multibox property="accountsAvailableInSmsBank" value="${accountLinkId}"/> ${useInSmsBankTitle}
                                       </td>
                                   </c:when>
                                   <c:otherwise>
                                       <td>
                                           <html:multibox property="accountsShowInSms" value="${accountLinkId}"/> ${useSmsCommandsTitle}
                                       </td>
                                       <td>
                                           <html:multibox property="accountsNtf" styleId="accountsNtf" value="${accountLinkId}"/> ${ermbSmsNotifyTitle}
                                       </td>
                                   </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <c:set var="loanAvailable" value="${phiz:isLoanSmsNotificationAvailable()}"/>
                    <table width="100%" cellspacing="0" cellpadding="0" align="CENTER" class="standartTable ermbNotificationTable ${tableClass}">
                        <tbody>
                        <tr>
                            <th>
                                <bean:message key="ermb.loans" bundle="ermbBundle"/>
                            </th>
                            <c:choose>
                                <c:when test="${commonAttribute}">
                                    <th width="160px">
                                        <input name="isSelectedAllLoansAvailableInSmsBankItems" type="checkbox" style="border:none" onclick="switchSelection('isSelectedAllLoansAvailableInSmsBankItems','loansAvailableInSmsBank')"/>
                                        ${useInSmsBankTitle}
                                    </th>
                                </c:when>
                                <c:otherwise>
                                    <th class="smsOperationstitle">
                                        <input name="isSelectedAllLoansShowInSmsItems" type="checkbox" style="border:none" onclick="switchSelection('isSelectedAllLoansShowInSmsItems','loansShowInSms')"/>
                                        ${ermbSmsOperationsTitle}
                                    </th>
                                    <th class="smsNotificationsTitle">
                                        <input name="isSelectedAllLoansNtfItems" type="checkbox" style="border:none" onclick="switchSelection('isSelectedAllLoansNtfItems','loansNtf')" disabled="${not loanAvailable}"/>
                                        ${ermbSmsNotificationTitle}
                                    </th>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                        <c:forEach var="loanLink" items="${profile.loanLinks}" varStatus="li">
                            <c:set var="loanNumber" value="${loanLink.number}"/>
                            <c:set var="loanLinkId"  value="${loanLink.id}"/>
                            <tr class="ermbNotificationProduct">
                                <td>
                                    <label><c:out value="${loanLink.name}"/> <c:out value="${loanNumber}"/></label>
                                </td>
                                <c:choose>
                                   <c:when test="${commonAttribute}">
                                       <td>
                                          <html:multibox property="loansAvailableInSmsBank" value="${loanLinkId}"/> ${useInSmsBankTitle}
                                       </td>
                                   </c:when>
                                   <c:otherwise>
                                       <td>
                                           <html:multibox property="loansShowInSms" value="${loanLinkId}"/> ${useSmsCommandsTitle}
                                       </td>
                                       <td>
                                           <html:multibox property="loansNtf" value="${loanLinkId}" disabled="${not loanAvailable}"/> ${ermbSmsNotifyTitle}
                                       </td>
                                   </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <table width="100%" cellspacing="0" cellpadding="0" align="CENTER" class="standartTable ermbNotificationTable ${tableClass}">
                        <tbody>
                            <tr>
                                <th>
                                    <bean:message key="ermb.newProducts" bundle="ermbBundle"/>
                                </th>
                                <c:choose>
                                    <c:when test="${commonAttribute}">
                                        <th width="160px">
                                            ${useInSmsBankTitle}
                                        </th>
                                    </c:when>
                                    <c:otherwise>
                                        <th class="smsOperationstitle">
                                            ${ermbSmsOperationsTitle}
                                        </th>
                                        <th class="smsNotificationsTitle">
                                            ${ermbSmsNotificationTitle}
                                        </th>
                                    </c:otherwise>
                                </c:choose>
                            </tr>

                            <tr class="ermbNotificationProduct">
                                <td>
                                    <label><bean:message key="ermb.newProducts" bundle="ermbBundle"/></label>
                                </td>
                                <c:choose>
                                   <c:when test="${commonAttribute}">
                                       <td>
                                          <html:checkbox property="newProductsAvailableInSmsBank" name="form"/> ${useInSmsBankTitle}
                                       </td>
                                   </c:when>
                                   <c:otherwise>
                                       <td>
                                           <html:checkbox property="newProductsShowInSms" name="form"/> ${useSmsCommandsTitle}
                                       </td>
                                       <td>
                                           <html:checkbox property="newProductsNtf" name="form"/> ${ermbSmsNotifyTitle}
                                       </td>
                                   </c:otherwise>
                                </c:choose>
                            </tr>
                        </tbody>
                    </table>

                    <fieldset>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.last.sms.time" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">${phiz:formatDateToStringOnPattern(form.lastSmsRequestTime, 'dd-MM-yyyy HH:mm:ss')}</tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="ermb.last.mb.time" bundle="ermbBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">${phiz:formatDateToStringOnPattern(form.lastRequestTime, 'dd-MM-yyyy HH:mm:ss')}</tiles:put>
                        </tiles:insert>

                    </fieldset>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="validationFunction">checkAndUpdate();</tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="onclick" value="window.location.reload();"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
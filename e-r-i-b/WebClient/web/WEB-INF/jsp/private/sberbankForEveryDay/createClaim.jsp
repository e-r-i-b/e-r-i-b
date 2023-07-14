<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <c:if test="${not empty form.id}">
        <html:hidden property="id"/>
    </c:if>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/sbnkd.js" charset="UTF-8"></script>
    <div class="page_content">
       <input id="stageNumber" type="hidden" name="stageNumber" value="${form.stageNumber}"/>
       <input id="cardCount" type="hidden" name="cardCount" value="${form.cardCount}"/>
       <%--Заказ дебетовых карт--%>
       <c:if test="${!form.guest || hasGuestAnyAccount}">
            <div class="title_common head_title"><bean:message key="label.title" bundle="sbnkdBundle"/></div>
       </c:if>
       <div class="description_text"><bean:message key="label.description" bundle="sbnkdBundle"/></div>

       <c:set var="thisStage" value="1"/>
       <div id="myRegion">
           <c:if test="${form.guest}">
               <span class="regionTtl">Мой регион:</span>
               <span id="regionNameSpan" onclick="win.open('personRegionsDiv'); return false" class="regionUserSelect userRegionLink">
                   <bean:write name="form" property="field(regionName)"/>
               </span>
               <img alt="" class="stickpin" src="${imagePath}/stickpin-s.png">
               <input id="field(regionId)" type="hidden" name="field(regionId)" value="${form.fields['regionId']}"/>
               <input id="field(regionName)" type="hidden" name="field(regionName)" value="${form.fields['regionName']}"/>
               <input id="field(personTB)" type="hidden" name="field(personTB)" value="${form.fields['personTB']}"/>
               <div class="dividerArea"> </div>
           </c:if>
       </div>
       <c:set var="regionUrl" value="${phiz:calculateActionURL(pageContext,'/dictionaries/sbnkd-regions/list')}"/>
       <tiles:insert definition="window" flush="false">
           <tiles:put name="id" value="personRegionsDiv"/>
           <tiles:put name="loadAjaxUrl" value="${regionUrl}?isOpening=true&id=0&oldId=${form.fields['regionId']}"/>
           <tiles:put name="styleClass" value="regionsDiv"/>
       </tiles:insert>
       <%--Выбор карт--%>
       <div id="stage_${thisStage}" class="activeStage">
            <div class="stage <c:if test="${form.stageNumber == thisStage}">stageIndent</c:if>">
                <div class="steps">
                    <c:choose>
                        <c:when test="${form.stageNumber > thisStage || form.preparedForConfirm}">
                            <div class="stage_tick"></div>
                        </c:when>
                        <c:otherwise>
                            <div class="stage_number">${thisStage}</div>
                        </c:otherwise>
                    </c:choose>
                    <div id="cardSelect" class="title_common subtitle_1_level float"><bean:message key="label.cardsSelection.title" bundle="sbnkdBundle"/></div>
                </div>
                <div class="clear"></div>
                <%@ include file="cardSelect.jsp" %>
                <div class="clear"> </div>
            </div>
           <c:if test="${form.stageNumber == thisStage}">
               <div class="orderseparate"></div>

               <div id="btn_${thisStage}" class="next_stage_button">
                   <tiles:insert definition="clientButton" flush="false">
                       <c:choose>
                           <c:when test="${form.guest}">
                               <tiles:put name="commandTextKey" value="button.continue"/>
                               <tiles:put name="commandHelpKey" value="button.continue"/>
                           </c:when>
                           <c:otherwise>
                               <tiles:put name="commandTextKey" value="button.next"/>
                               <tiles:put name="commandHelpKey" value="button.next"/>
                           </c:otherwise>
                       </c:choose>
                       <tiles:put name="bundle" value="sbnkdBundle"/>
                       <tiles:put name="onclick" value="nextStage('0')"/>
                   </tiles:insert>
               </div>
           </c:if>
       </div>

       <c:if test="${form.guest}">
           <%--Заполнение персональных данных--%>
           <c:set var="thisStage" value="2"/>
           <c:set var="editThis" value="${form.editStageNumber <= thisStage && form.editStageNumber ne 1 && form.editStageNumber ne '1'}"/>
           <c:if test="${form.stageNumber >= thisStage}">
               <div id="stage_${thisStage}" class="activeStage">
                   <div class="stage">
                       <div class="steps">
                           <c:choose>
                               <c:when test="${form.stageNumber > thisStage && !editThis || form.preparedForConfirm}">
                                   <div class="stage_tick"></div>
                               </c:when>
                               <c:otherwise>
                                   <div class="stage_number">${thisStage}</div>
                               </c:otherwise>
                           </c:choose>
                           <div class="tableBlock fixWidthTitle">
                               <div id="fillPersonalInfo" class="title_common subtitle_1_level tableCellBlock">
                                   <bean:message key="label.fillPersonalInfo.title" bundle="sbnkdBundle"/>
                               </div>
                               <c:if test="${form.stageNumber > thisStage && (form.editStageNumber == 1 || form.editStageNumber > thisStage) || form.preparedForConfirm}">
                                    <div class="relative">
                                        <div class="btn_edit personalInfo tableCellBlock css3" onclick="editStage('2')">Изменить данные</div>
                                    </div>
                               </c:if>
                           </div>
                       </div>
                       <div class="clear"></div>
                       <%@ include file="fillPersonalInfo.jsp" %>
                       <div class="clear"></div>
                   </div>

                   <c:if test="${form.stageNumber == thisStage}">
                       <div class="orderseparate"></div>
                       <div id="btn_${thisStage}" class="next_stage_button">
                           <tiles:insert definition="commandButton" flush="false">
                               <tiles:put name="commandKey" value="button.continue"/>
                               <tiles:put name="commandHelpKey" value="button.continue"/>
                               <tiles:put name="bundle" value="sbnkdBundle"/>
                               </tiles:insert>
                       </div>
                   </c:if>
               </div>
           </c:if>

           <c:if test="${form.stageNumber < thisStage}">
               <div id="disabled_stage_${thisStage}" class="unactiveStage">
                   <div class="steps">
                       <div class="disabled_stage_number">${thisStage}</div>
                       <div class="title_common subtitle_1_level color_gray float">
                           <bean:message key="label.fillPersonalInfo.title" bundle="sbnkdBundle"/>
                       </div>
                   </div>
               </div>
           </c:if>
       </c:if>

       <%--Добавление услуг--%>
       <c:set var="thisStage" value="3"/>
       <c:set var="editThis" value="${form.editStageNumber <= thisStage && form.editStageNumber ne 1 && form.editStageNumber ne '1'}"/>
       <c:if test="${form.stageNumber >= thisStage}">
           <div id="stage_${thisStage}" class="activeStage">
               <div class="stage">
                   <div class="steps">
                       <c:choose>
                           <c:when test="${form.stageNumber > thisStage && !editThis || form.preparedForConfirm}">
                               <div class="stage_tick"></div>
                           </c:when>
                           <c:otherwise>
                               <div class="stage_number">
                                   <c:choose>
                                       <c:when test="${form.guest}">
                                           ${thisStage}
                                       </c:when>
                                       <c:otherwise>
                                           2
                                       </c:otherwise>
                                   </c:choose>
                               </div>
                           </c:otherwise>
                       </c:choose>
                       <div class="tableBlock fixWidthTitle">
                           <div id="addServices" class="title_common subtitle_1_level tableCellBlock">
                               <bean:message key="label.addServices.title" bundle="sbnkdBundle"/>
                           </div>
                           <c:if test="${form.stageNumber > thisStage && (form.editStageNumber == 1 || form.editStageNumber > thisStage) || form.preparedForConfirm}">
                               <div class="relative">
                                   <div class="btn_edit tableCellBlock css3" onclick="editStage('3')">Изменить услуги</div>
                               </div>
                           </c:if>
                       </div>
                       <div class="clear"> </div>
                       <%@ include file="addServices.jsp" %>
                   </div>
               </div>

               <c:if test="${form.stageNumber == thisStage}">
                   <div class="orderseparate orderseparate-s"></div>
                   <div id="btn_${thisStage}" class="next_stage_button">
                       <tiles:insert definition="commandButton" flush="false">
                           <tiles:put name="commandKey" value="button.continue"/>
                           <tiles:put name="commandHelpKey" value="button.continue"/>
                           <tiles:put name="bundle" value="sbnkdBundle"/>
                          </tiles:insert>
                   </div>
               </c:if>
           </div>
       </c:if>

       <c:if test="${form.stageNumber < thisStage}">
           <div id="disabled_stage_${thisStage}" class="unactiveStage">
               <div class="steps">
                   <div class="disabled_stage_number">
                       <c:choose>
                           <c:when test="${form.guest}">
                               ${thisStage}
                           </c:when>
                           <c:otherwise>
                               2
                           </c:otherwise>
                       </c:choose>
                   </div>
                   <div class="title_common subtitle_1_level color_gray float">
                       <bean:message key="label.addServices.title" bundle="sbnkdBundle"/>
                   </div>
               </div>
           </div>
       </c:if>

       <%--Выбор отделения для получения карты--%>
       <c:set var="thisStage" value="4"/>
       <c:set var="editThis" value="${form.editStageNumber <= thisStage && form.editStageNumber ne 1 && form.editStageNumber ne '1'}"/>
       <c:if test="${form.stageNumber >= thisStage }">
           <div id="stage_${thisStage}" class="activeStage">
               <div class="stage">
                   <div class="steps">
                       <c:choose>
                           <c:when test="${form.stageNumber > thisStage && !editThis || form.preparedForConfirm}">
                               <div class="stage_tick"></div>
                           </c:when>
                           <c:otherwise>
                               <div class="stage_number">
                                   <c:choose>
                                       <c:when test="${form.guest}">
                                           ${thisStage}
                                       </c:when>
                                       <c:otherwise>
                                           3
                                       </c:otherwise>
                                   </c:choose>
                               </div>
                           </c:otherwise>
                       </c:choose>
                       <div class="tableBlock fixWidthTitle">
                           <div id="selectBranch" class="title_common subtitle_1_level tableCellBlock">
                               <bean:message key="label.selectBranchToGetCard.title" bundle="sbnkdBundle"/>
                           </div>
                           <c:if test="${form.stageNumber > thisStage && (form.editStageNumber == 1 || form.editStageNumber > thisStage) || form.preparedForConfirm}">
                            <div class="relative">
                                <div class="btn_edit tableCellBlock css3" onclick="editStage('4')">Изменить отделение</div>
                            </div>
                           </c:if>
                       </div>
                       <div class="clear"> </div>
                       <%@ include file="selectBranch.jsp" %>
                   </div>
               </div>

               <c:if test="${form.stageNumber == thisStage}">
                   <div class="orderseparate margin_top_60 orderseparate_view"></div>
                   <div id="btn_${thisStage}" class="next_stage_button">
                       <tiles:insert definition="commandButton" flush="false">
                           <tiles:put name="commandKey" value="button.continue"/>
                           <tiles:put name="commandHelpKey" value="button.continue"/>
                           <tiles:put name="bundle" value="sbnkdBundle"/>
                       </tiles:insert>
                   </div>
               </c:if>
           </div>
       </c:if>

       <c:if test="${form.stageNumber < thisStage}">
           <div id="disabled_stage_${thisStage}" class="unactiveStage">
               <div class="steps">
                   <div class="disabled_stage_number">
                       <c:choose>
                           <c:when test="${form.guest}">
                               ${thisStage}
                           </c:when>
                           <c:otherwise>
                               3
                           </c:otherwise>
                       </c:choose>
                   </div>
                   <div class="title_common subtitle_1_level color_gray float">
                       <bean:message key="label.selectBranchToGetCard.title" bundle="sbnkdBundle"/>
                   </div>
               </div>
           </div>
       </c:if>

       <c:if test="${form.stageNumber == 5 && (!form.preparedForConfirm || form.error)}">
           <div class="orderseparate orderseparate_view"></div>
           <div class="next_stage_button">
               <tiles:insert definition="commandButton" flush="false">
                   <tiles:put name="commandKey" value="button.continue"/>
                   <tiles:put name="commandHelpKey" value="button.continue"/>
                   <tiles:put name="bundle" value="sbnkdBundle"/>
               </tiles:insert>
           </div>
       </c:if>

       <c:if test="${!form.error && (form.stageNumber == 5 && (form.editStageNumber == 1 || form.editStageNumber != 1 && form.preparedForConfirm) || form.stageNumber == 6 && form.preparedForConfirm)}">
           <div class="btmTick confirmConditions">
               <input type="checkbox" class="float" id="confirmConditions"/>
               <label for="confirmConditions" class="tickDesc">
                   <bean:message key="confirm.text" bundle="sbnkdBundle"/>
               </label>
           </div>
           <div class="clear"></div>

           <div class="orderseparate orderseparate_view"></div>

           <div class="next_stage_button">
               <tiles:insert definition="commandButton" flush="false">
                   <tiles:put name="commandKey" value="button.orderCard"/>
                   <tiles:put name="commandHelpKey" value="button.orderCard"/>
                   <tiles:put name="bundle" value="sbnkdBundle"/>
                   <tiles:put name="validationFunction" value="checkClientAgreesCondition()"/>
               </tiles:insert>
           </div>
       </c:if>
       <c:if test="${form.preparedForConfirm}">
           <html:hidden property="preparedForConfirm" value="true"/>
       </c:if>

       <div class="invisibleBlock">
           <tiles:insert definition="commandButton" flush="false">
               <tiles:put name="commandKey" value="button.edit"/>
               <tiles:put name="commandHelpKey" value="button.edit"/>
               <tiles:put name="bundle" value="paymentsBundle"/>
           </tiles:insert>
       </div>
       <html:hidden property="editStageNumber" styleId="editStageNumber"/>
       <c:if test="${form.vipClient}">
           <c:set var="cardPageUrl" value="${phiz:calculateActionURL(pageContext, '/private/cards/list.do')}"/>
           <c:set var="guestIndexUrl" value="${phiz:calculateActionURL(pageContext, '/guest/index.do')}"/>
           <script type="text/javascript">
               function goToCardPage()
               {
                    <c:choose>
                        <c:when test="${form.guest}">
                            window.location = "${guestIndexUrl}";
                        </c:when>
                        <c:otherwise>
                            window.location = "${cardPageUrl}";
                        </c:otherwise>
                    </c:choose>

               }
           </script>
           <tiles:insert definition="window" flush="false">
               <tiles:put name="id" value="VIPClientError"/>
               <tiles:put name="closeCallback" value="goToCardPage" />
               <tiles:put name="data">
                   <h2>Для заказа дебетовой карты обратитесь <br/> к вашему персональному менеджеру</h2>
                   <p class="messSize14">${form.vipClientMessage}</p>
                   <div class="buttonsArea">
                       <tiles:insert definition="clientButton" flush="false">
                           <tiles:put name="commandTextKey" value="button.closeClaim"/>
                           <tiles:put name="commandHelpKey" value="button.closeClaim"/>
                           <tiles:put name="bundle" value="sbnkdBundle"/>
                           <tiles:put name="onclick" value="goToCardPage()"/>
                       </tiles:insert>
                    </div>
               </tiles:put>
           </tiles:insert>
            <script type="text/javascript">
                doOnLoad(function() {
                    win.open('VIPClientError');
                });
            </script>
       </c:if>
   </div>
   <script type="text/javascript">


    function onLoad()
    {
        initSigns();
        <c:if test="${form.stageNumber == 1}">
            initCardList(0);
            changeCardInfo(0, "${form.fields['cardTypeSelect0']}");
        </c:if>
        var stickpin = $(".stickpin");
        stickpin.animate({
            marginTop: 2,
            opacity: 1
        }, 800);
    }

       function initSigns()
       {
           //заполнение персональных данных
           $('#RFCitizen').attr('checked', ${form.RFCitizen});
           var displayNationalityVal = ${form.RFCitizen} ? 'none' : 'block';
           $('#nationality').parent().parent().parent().css('display', displayNationalityVal);
           $('#livingByRegistrationPlace').attr('checked', ${form.livingByRegistrationPlace});
           var displayResidentialBlockVal = ${form.livingByRegistrationPlace} ? 'none' : 'block';
           $('#residentialBlock').css('display', displayResidentialBlockVal);
           var autopaymentIsAvailable = ${form.fields['autopaymentIsAvailable']};
           var needAutopayment = ${form.needAutopayment};
           var displayAutopaymentBlockVal = needAutopayment && autopaymentIsAvailable ? 'on' : '';
           $('#needAutopayment').val(displayAutopaymentBlockVal);

           if(displayAutopaymentBlockVal == '')
           {
               $('.switched_content').hide();
           }
           else
           {
                $('.switched_content').show();
           }

           <c:set var="fieldVal" value="${form.fields['seriesAndNumber']}"/>
           var fieldVal = '${fieldVal}';
           if(fieldVal != '')
           {
               if(${form.fields['docType'] == 'MILITARY_IDCARD' || form.fields['docType'] == 'OFFICER_IDCARD'})
                  $('#seriesAndNumber2').val('<c:out value="${fieldVal}"/>');
               $('#seriesAndNumber').val('<c:out value="${fieldVal}"/>');
           }
           if(${not empty form.cardError})
           {
               setTimeout("cardErrorCheck()", 100);
           }
       }

       $(document).ready(onLoad());

    function cardErrorCheck()
    {
        var errText = '${form.cardError}';
        var winType = errText.substring(0, errText.indexOf(" "));
        var cardCount = '${form.cardCount}';
        var editCardNumber = '${form.editCardNumber}';
        var msg = errText;

        if(errText.indexOf('EmailFieldValidator.format') > 0)
        {
            msg = '\"E-mail для связи\" может содержать буквы латинского алфавита, а также должен включать точку и символ @. Например, ivanova@list.ru.';
        }

        if(errText.indexOf('RequiredFieldValidator.message') > 0)
        {
            msg = 'Введите значение в поле ' + errText.substring(errText.lastIndexOf('[') + 1, errText.indexOf(']'));
        }

        if(errText.indexOf('[]') > 0)
        {
            msg = errText.substring(errText.indexOf('[') + 1, errText.lastIndexOf('['));
        }

        if(winType == "addCardWin")
        {
            addNewCard(cardCount, true);
            $('#editCardWarningMessages' + cardCount).removeClass('displayNone');
            addError(msg, 'editCardWarningMessages' + cardCount, false);
        }
        else
        {
            editCard(editCardNumber);
            $('#editCardWarningMessages' + editCardNumber).removeClass('displayNone');
            addError(msg, 'editCardWarningMessages' + editCardNumber, false);
        }
    }

    function checkClientAgreesCondition()
    {
        var checked = document.getElementById('confirmConditions').checked;

        var AGREEMENT_MESSAGE = 'Если вы согласны с условиями, установите флажок в поле «Согласен(на) на обработку ОАО «Сбербанк России» моих персональных данных в ' +
                'соответствии с требованиями ФЗ от 27.07.2006 № 152-ФЗ «О персональных данных» и нажмите на кнопку «Заказать карту»';

        if (!checked)
        {
            addMessage(AGREEMENT_MESSAGE);
            window.scrollTo(0,0);
        }
        else
            removeMessage(AGREEMENT_MESSAGE);
        return checked;
    }

   function editStage(num)
   {
       $('#editStageNumber').val(num);
       findCommandButton('button.edit').click();
   }

    function updateRegionsFieldForCard(regionSelector)
    {
        $('[name="field(regionId)"]').val(regionSelector.currentRegionId);
        $('[name="field(regionName)"]').val(regionSelector.currentRegionName);
        $('[name="field(personTB)"]').val(regionSelector.currentRegionCodeTB);
        $('#regionNameSpan').html(regionSelector.currentRegionName);
        $('#regionChangeWindowNameSpan').html(regionSelector.currentRegionName);
        $('[name="field(osb)"]').val("");
        $('[name="field(vsp)"]').val("");
        win.close(regionSelectorWindowId);
        <c:if test="${form.stageNumber > 4 && (form.editStageNumber == 1 || form.editStageNumber > 4)}">
            editStage('4');
       </c:if>
    }
        var regionSelectorWindowId = 'personRegionsDiv';

        var parameters =
        {
            customDictionaryUrl: '/dictionaries/sbnkd-regions/list.do',
            windowId: regionSelectorWindowId,
            click:
            {
                getParametersCallback: function(id){return id > 0? 'isOpening=false&setCnt=true&id=' + id: '';},
                updateRegionsFieldForCard: updateRegionsFieldForCard
            },
            choose:
            {
                useAjax: false,
                afterActionCallback: function(myself)
                {
                    setCurrentRegion(myself);
                }
            }
        };
        initializeRegionSelector(parameters);

   </script>





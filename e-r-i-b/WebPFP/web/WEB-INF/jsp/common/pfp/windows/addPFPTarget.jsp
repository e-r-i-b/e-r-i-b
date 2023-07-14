<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags"  prefix="phiz"%>
<%@ taglib uri="http://rssl.com/pfptags" prefix="pfptags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="ru-RU"/>

<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="profileId" value="${form.id}"/>
<c:set var="dictionaryTargetList" value="${pfptags:getDictionaryTargetList()}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="currentDate" value="${phiz:currentDate()}"/>

<h1>Параметры цели</h1>

<div class="warningMessage displayNone" id="warningMessages">
    <tiles:insert definition="roundBorderLight" flush="false">
        <tiles:put name="color" value="red"/>
        <tiles:put name="data">
            <div id="messageContainer" class="messageContainer">
            </div>
            <div class="clear"></div>
        </tiles:put>
    </tiles:insert>
</div>

<div class="winDescription">
    <div class="targetIcon">
        <img id="targetImgDefault" src="${globalImagePath}/pfp/otherIcon.jpg" class="displayNone" width="64px" height="64px"/>
        <c:forEach items="${dictionaryTargetList}" var="dictionaryTarget">
            <c:if test="${!empty dictionaryTarget.imageId}">
                <c:set var="imageData" value="${phiz:getImageById(dictionaryTarget.imageId)}"/>
                <c:set var="targetImg" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                <img id="targetImg${dictionaryTarget.id}" src="${targetImg}" class="displayNone" name="${dictionaryTarget.imageId}" width="64px" height="64px"/>
            </c:if>
        </c:forEach>
    </div>

    <div class="addTargetDescr"><bean:message bundle="pfpBundle" key="addTarget.description"/></div>
    <div class="clear"></div>
</div>

<div  onkeydown="onEnterKeyInTargetWindow(event);">
    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addTarget.target"/></tiles:put>
        <tiles:put name="isNecessary" value="true"/>
        <tiles:put name="data">
            <div class="relative">
                <select id="targetSelect" class="select" style="width:170px" onchange="changeTargetImg();">
                    <c:forEach items="${dictionaryTargetList}" var="dictionaryTarget">
                        <option value="${dictionaryTarget.id}"><c:out value="${dictionaryTarget.name}"/></option>
                    </c:forEach>
                </select>
                <div id="targetComment">
                    – <input type="text" class="customPlaceholder" id="targetNameComment" name="targetNameComment" maxlength="100" title="введите комментарий"/>
                </div>
            </div>
        </tiles:put>
        <tiles:put name="clazz" value="pfpFormRow"/>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addTarget.targetAmount"/></tiles:put>
        <tiles:put name="isNecessary" value="true"/>
        <tiles:put name="data">
            <input id="targetAmount" type="text" name="targetAmount" size="10" maxlength="15" class="moneyField"/> руб
        </tiles:put>
        <tiles:put name="fieldName">targetAmount</tiles:put>
        <tiles:put name="clazz" value="pfpFormRow"/>
    </tiles:insert>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.addTarget.targetPlanedYear"/></tiles:put>
        <tiles:put name="isNecessary" value="true"/>
        <tiles:put name="data">

            <c:set var="currentYear" value="${phiz:currentYear()}"/>
            <c:set var="currentQuarter" value="${phiz:getQuarter(currentDate)}"/>
            <c:set var="getDefaultPeriodValue" value="${currentYear + pfptags:getDefaultPeriodValue()}"/>
            <c:set var="minQuarter" value="${currentQuarter + 1}"/>
            <c:set var="minYear" value="${currentYear}"/>
            <c:if test="${minQuarter > 4}">
                <c:set var="minQuarter" value="1"/>
                <c:set var="minYear" value="${currentYear+1}"/>
            </c:if>

            <tiles:insert definition="scrollTemplate2D" flush="false">
                <tiles:put name="id" value="targetPlanedDate"/>
                <tiles:put name="xMinValue"  value="${minYear}"/>
                <tiles:put name="xMaxValue"  value="${currentYear + pfptags:getMaxPlanningYear()}"/>
                <tiles:put name="xCurrValue" value="${getDefaultPeriodValue}"/>
                <tiles:put name="xUnit" value="год"/>
                <tiles:put name="xFieldName" value="field(targetPlanedYear)"/>
                <tiles:put name="yScrollType" value="quarter"/>
                <tiles:put name="yCurrValue" value="${currentQuarter}"/>
                <tiles:put name="yMinPossibleVal" value="${minQuarter}"/>
                <tiles:put name="yMaxPossibleVal" value="${currentQuarter}"/>
                <tiles:put name="yFieldName" value="field(targetPlanedQuarter)"/>
                <tiles:put name="totalFieldName" value="field(planedDate)"/>
                <tiles:put name="yUnit" value="квартал"/>
                <tiles:put name="inputHint"><div class="italic"><bean:message key="label.dateScroll.hint" bundle="pfpBundle"/></div></tiles:put>
            </tiles:insert>

        </tiles:put>
        <tiles:put name="clazz" value="pfpFormRow"/>
    </tiles:insert>
</div>

<div class="tableArea pfpButtonsBlock">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.cancelAddTarget"/>
        <tiles:put name="commandHelpKey" value="button.cancelAddTarget.help"/>
        <tiles:put name="bundle" value="pfpBundle"/>
        <tiles:put name="onclick" value="win.close('addPFPTarget');"/>
        <tiles:put name="viewType" value="buttonGrey"/>
    </tiles:insert>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.saveAddTarget"/>
        <tiles:put name="commandHelpKey" value="button.saveAddTarget.help"/>
        <tiles:put name="bundle" value="pfpBundle"/>
        <tiles:put name="onclick" value="selectPersonTarget();"/>
    </tiles:insert>
    <div class="clear"></div>
</div>

<script type="text/javascript">
    var targetId = null;
    var showImg;

    function clearOldTarget()
    {
        targetId = null;
    }

    function changeTargetImg()
    {
        var image = $("#targetImg"+showImg);
        if (image.length == 0)
            $('#targetImgDefault').addClass("displayNone");
        else
            image.addClass("displayNone");
        var id = $('#targetSelect :selected').val();
        showTargetImg(id);
    }

    function showTargetImg(imgId)
    {
        var image = $("#targetImg"+imgId);
        if (image.length == 0)
            $('#targetImgDefault').removeClass("displayNone");
        else
            image.removeClass("displayNone");
        showImg = imgId;
    }

    function validateTargetDate(plannedDate)
    {
        <c:set var="curDate"><fmt:formatDate value="${currentDate.time}" pattern="MM/dd/yyyy"/></c:set>
        var diffQuarters = pfpMath.getDiffInQuarters(new Date("${curDate}"), plannedDate);
        return (diffQuarters>=1) && (diffQuarters<=(${pfptags:getMaxPlanningYear()}*4));
    }

    function selectPersonTarget()
    {
        var target = new Object();
        target.dictionaryTarget = $('#targetSelect :selected').val();
        target.name = $('#targetSelect :selected').text();
        target.nameComment = customPlaceholder.getCurrentVal($('#targetNameComment'));
        target.amount = parseFloatVal($('#targetAmount').val());
        target.plannedDateStr = $('input[name=field(planedDate)]').val();
        target.plannedDate = Str2Date($('input[name=field(planedDate)]').val());

        if(isEmpty(target.amount) || isNaN(target.amount))
        {
            addErrorMessage("Пожалуйста, заполните все параметры цели.");
            return;
        }

        if (target.nameComment.length > 100)
        {
            addErrorMessage("Поле Комментарий должно содержать не более 100 символов.");
            return;
        }
        if (!matchRegexp(target.amount, /^(\d ?){1,15}((\.|,)\d{1,2})?$/))
        {
            addErrorMessage("Вы неправильно указали сумму. Пожалуйста, введите только цифры.");
            return;
        }

        if (!validateTargetDate(target.plannedDate))
        {
             addErrorMessage("Неверно введена планируемая дата достижения цели.");
             return;
         }

        target.id = targetId;
        pfpTarget.addPersonTarget(target);
    }

    function addErrorMessage(mess){
        $('#warningMessages').removeClass("displayNone");
        $('#messageContainer').html(mess);
    }

    function openTargetsWindow(target)
    {
        /*производим первоначальную инициализацию данных окна*/
        var currYear;
        var currQuarter;
        if($.isEmptyObject(target))
        {
            $('#targetSelect :first').attr("selected","true");
            $('#targetNameComment').val("").blur();
            $('#targetAmount').val("");
            currYear = ${getDefaultPeriodValue};
            currQuarter = ${phiz:getQuarter(currentDate)};
        }
        else
        {
            $('#targetSelect option[value='+target.dictionaryTarget+']').attr("selected","true");
            $('#targetNameComment').val(target.nameComment);
            if (target.nameComment == '')
                $('#targetNameComment').blur();
            $('#targetAmount').setMoneyValue(target.amount);
            currYear = target.planedDate.year;
            currQuarter = target.planedDate.quarter;
            targetId = target.id;
        }

        initScrolls('targetPlanedDate', horizDragtargetPlanedDate, vertDragtargetPlanedDate, currYear, currQuarter);
        changeTargetImg();
        $('#warningMessages').addClass("displayNone");// убираем старые ошибки валидации
        win.open('addPFPTarget');
    }

        $('.dot-month-pick').createMask(ENGLISH_MONTH_TEMPLATE);


/* При нажатии Enter кликает по дефолтной кнопке формы */
    function onEnterKeyInTargetWindow(e)
    {
        var kk = navigator.appName == 'Netscape' ? e.which : e.keyCode;

        if(kk == 13)
        {
            selectPersonTarget();
            saveOffset();
        }
    }
</script>
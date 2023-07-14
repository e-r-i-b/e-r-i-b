var url;
var skinUrl;
var SLIDER_WITHOUT_CONTROLS = {controls:false};

function initCSA(path, skinPath)
{
    url = "/" + path;
    skinUrl = skinPath;

    if (document.getElementById('MainPageNews') != null)
        ajaxQuery(null, url + "/async/news.do", setNewsOnMainPage, "json", false);

    if (document.getElementById('frontInfo') != null)
        ajaxQuery(null, url + "/async/tabs.do", function(){}, null, false);
}

jQuery(document).ready(function(){
    var regexp = /[dmy]/gi;
    if ($('.Datedate-pick').datePicker)
    {
        var dP = $('.Datedate-pick').datePicker({displayClose: true, chooseImg: skinUrl + '/skins/commonSkin/images/datePickerCalendar.gif', dateFormat: 'dd/mm/yyyy'});
        dP.dpApplyMask();
    }

    customPlaceholder.init();

    // ��������� ������������ � ���������� � ��6
    if (isIE(6))
        try {

            var el = document.getElementById("loading");
            if (el != null)
            {
                plateSrc = skinUrl+'/images/plate.png';
                el.style.filter ="progid:DXImageTransform.Microsoft.AlphaImageLoader(src="+plateSrc+",sizingMethod='scale')";
            }

            document.execCommand("BackgroundImageCache",false,true);
        }
        catch (e) { }

    buttonAddEventClick('butGreen', 'butGreenClick');

});

function buttonAddEventClick(buttonClass, clickClassName)
{
    if ($('.' + buttonClass)[0] != undefined)
    {
        $('.' + buttonClass).live("mousedown",
            function(){
                $(this).addClass(clickClassName);
            });

        $('.' + buttonClass).live("mouseup",
            function(){
                $(this).removeClass(clickClassName);
            });

        $('.' + buttonClass).live("mouseleave",
            function(){
                $(this).removeClass(clickClassName);
            });
    }
}

function initFirstTab()
{
    var tabsNameSelector = "#frontInfo .tabs-names ul";
    // �� ��������� ������ ��� ��������
    var firstIdSelector = $(tabsNameSelector + ">li:first a").get(0);
    // ������������� ������� �� ������ ���, ���� ������ ��� ������� ���� ��������
    initSliders(firstIdSelector.hash + '_sliders');
}

function initSliders(sliderSelector)
{
    var sliderElementCount = $(sliderSelector).children().length;
    if (sliderElementCount > 1)
        $(sliderSelector).bxSlider({});
    else if (sliderElementCount == 1)
        $(sliderSelector).bxSlider(SLIDER_WITHOUT_CONTROLS);
}

function setNewsOnMainPage(data)
{
    var news = "";
    if (data == undefined || data.news == undefined)
        return;
    for(var key in data.news)
    {
        if(data.news.hasOwnProperty(key))
        {
            var importantNews = '';
            if (data.news[key].important == 'HIGH')
                importantNews = 'news-main';
            news = news + "<li class='news_item " + importantNews + "'><div class='news_cap'><a href='"+ url + "/news/view.do?id=" + data.news[key].id +"' class='aBlack'><span>";
            news = news + data.news[key].title  + "</span></a></div><small class='news_date'>"+data.news[key].date+"</small></li>";
        }
    }
    $('#MainPageNews').html(news);
    $('.b-news').jNews();
}

function sendedAjaxResultForNews(data)
{
    if (trim(data) == '')
    {
        window.location.reload();
        return;
    }

    if (data.search("news-list") == -1)
    {
        window.location.reload();
        return;
    }
    $('#news').html(data);
}

function ajaxTableList(sizeFieldName,paginationSize,offsetFieldName, offset)
{
    var typeDate = getElement('filter(typeDate)').value;
    var fromDate = getElement('filter(fromDate)').value;
    var toDate  = getElement('filter(toDate)').value;
    var search = customPlaceholder.getCurrentVal(getElement('filter(filterSearch)'));
    var important =  getElement('filter(filterImportant)').checked;
    var params = offsetFieldName + '=' + offset + '&' + sizeFieldName + '=' + paginationSize;

    if (typeDate != '')
        params = params + '&typeDate=' + typeDate;
    if (fromDate != '')
        params = params + '&fromDate=' + fromDate;
    if (toDate != '')
        params = params + '&toDate=' + toDate;
    if (search != '')
        params = params + '&search=' + search;
    if (important != '')
        params = params + '&important=' + important;

    ajaxQuery(params, url + "/async/news/list.do", sendedAjaxResultForNews, null);
}

function sendAjaxList(url)
{
    var typeDate = getElement('filter(typeDate)').value;
    var fromDate = getElement('filter(fromDate)').value;
    var toDate  = getElement('filter(toDate)').value;
    var search = customPlaceholder.getCurrentVal(getElement('filter(filterSearch)'));
    var important =  getElement('filter(filterImportant)').checked;
    var params = 'filter(typeDate)=' + typeDate;

    if (fromDate != '')
        params = params + '&filter(fromDate)=' + fromDate;
    if (toDate != '')
        params = params + '&filter(toDate)=' + toDate;
    if (search != '')
        params = params + '&filter(search)=' + search;
    if (important != '')
        params = params + '&filter(important)=' + important;

    ajaxQuery(params, url, sendedAjaxResultForNews, null);
}

//������������� ����, tabBlockId - ������������� ����
function initTabs(tabBlockId, callbackTabs)
{
    var tabsNameSelector = "#" + tabBlockId + " .tabs-names ul";
    var tabsContentSelector = "#" + tabBlockId + " div.tabs-contents";

    // �� ��������� ������ ��������
    var firstIdSelector = $(tabsNameSelector + ">li:first a").get(0);
    $(tabsContentSelector + ">div").hide().filter(firstIdSelector.hash).show();
    $(tabsNameSelector + ">li:first").addClass("activeSecondMenu greenFirst");

    // �������������� ���� �� �������
    $(tabsNameSelector + ">li a").click(function () {
        // ������ ��� ���� ����� ����, �� ������� ������� �����
        $(tabsContentSelector + ">div").hide().filter(this.hash).show();
        // ���� ������ �� ������������, �� ������ �� ������
        if ($(this.hash + "_sliders>li").length > 0)
            callbackTabs(this.hash);
        // ������������� ��� ���������� ������� ���������
        $(tabsNameSelector + ">li:first").removeClass("greenFirst");
        $(tabsNameSelector + ">li:last").removeClass("greenLast");
        $(tabsNameSelector + ">li").removeClass("activeSecondMenu");

        // ������ ����� � �������, �� ������� ����
        var li = $(this).closest("li");
        $(li).addClass("activeSecondMenu");
        if ($(li).is(":first-child"))
            $(li).addClass("greenFirst");
        else if ($(li).is(":last-child"))
            $(li).addClass("greenLast");

        return false;
    });
}

function clickIfEnterKeyPress(button, event)
{
    var code = navigator.appName == 'Netscape' ? event.which : event.keyCode;
    if(code != 13)
        return;
    button.onclick();
}

function goTo(url)
{
    showOrHideWaitDiv();
    window.location.href = url;
}

/* ������ ��������� � ������ ��������������, ����������� � �������������� ������ */
function AuthForm(isShowCaptcha)
{
    this.isShowCaptcha = isShowCaptcha;

    this.showForm = function(type, isFocus)
    {
        this.reset();
        $("#authBlock .enterBlock").hide().filter("#" + type).show();

        if(this.isShowCaptcha)
            this.showCaptcha(type);
        else
            this.hideCaptcha(type);

        if(isFocus)
            this.setFocus();
         setCurrentHelpId(type);
    };

    this.updateForm = function(isSetCaptcha, isPasswordReset)
    {
        var currentBlock = this.getCurrentBlock();

        // ���������� ���� � ��������
        if(isPasswordReset)
            $(currentBlock).find("input[type='password']").filter(":not(.inputPlaceholder)").val("").blur();

        // ������������� � ���������� ����� ���� �����
        this.isShowCaptcha = isSetCaptcha;
        if (currentBlock != undefined)
        {
            if(isSetCaptcha)
                this.showCaptcha(currentBlock.id);
            else
                this.hideCaptcha(currentBlock.id);
        }
    };

    this.setFocus = function()
    {
        // ������ ������ � ������ ������� ���� ���������� �����
        var visibleInputs = $(this.getCurrentBlock()).find(":input").filter(":visible");
        if(visibleInputs.length > 0)
        {
            var firstInput = visibleInputs.get(0);
            $(firstInput).val(" " + $.trim($(firstInput).val()));
            firstInput.focus();
        }
    };

    /**
     * ��������� ������ �� ������
     */
    this.submit = function(source, operation)
    {
        var form = $(source).closest("form").get(0);
        FormSubmitter.submit(form, operation);
    };

    /**
     * ���������� ������.
     */
    this.showCaptcha = function(formName)
    {
        var captchaBlocks = $("#authBlock .enterBlock")
                .filter("#" + (formName == null ? this.getCurrentBlock().id : formName))
                .find(".captcha-block");
        $(captchaBlocks).find(":input").filter(":not(.inputPlaceholder)").val("").blur();
        captchaBlocks.find("#captcha").html("<img src=\"captcha.png?=" + Math.random() + "\" width=\"100%\" height=\"100%\">");
        captchaBlocks.show();
    };

    /**
     * �������� ��� �����
     */
    this.updateCaptcha = function()
    {
        // �� ����� ����������� ����� � CaptchaServlet, � ����� � ������ ����������� ����� ���,
        // � �� �������� ����� �������
        this.showCaptcha();
    };

    /**
     * ������ ������.
     */
    this.hideCaptcha = function()
    {
        var captchaBlock = $("#authBlock .enterBlock").find(".captcha-block");
        $(captchaBlock).find(":input").filter(":not(.inputPlaceholder)").val("").blur();
        captchaBlock.find("#captcha").empty();
        captchaBlock.hide();
    };

    /**
     * ��������� ������� ������� ������� Enter
     * @param event ������������ �������
     * @param operation �������� � ������ �������
     * @param isSubmit true - ����� ������
     */
    this.onEnterKeyPress = function(event, operation, isSubmit)
    {
        event = event || window.event;
        var code = navigator.appName == 'Netscape' ? event.which : event.keyCode;
        // ���� �� enter - ����������
        if(code != 13)
            return;

        preventDefault(event);
        if(isSubmit != null && isSubmit) {
            this.hidePassword();
            this.submit(event.target || event.srcElement, operation);
        }
    };

    /**
     * ����� �����
     * @param enterBlockId ���� � ������� ��������� �������� ����
     * @param fieldId ����, ��� �������� ����� �������� ��������
     */
    this.reset = function(enterBlockId, fieldId)
    {
        var enterBlocks = $("#authBlock .enterBlock");
        if(enterBlockId)
            enterBlocks.filter("#" + enterBlockId);

        var fields = enterBlocks.find("form").filter(":not(.hidden-form)")
                                .find(":input").filter(":not(.inputPlaceholder)");
        if(fieldId)
            fields.filter("#" + fieldId);
        // ���� ��������� ��������, ������� ����� �� ����� reset
        fields.val("").blur();
    };

    /**
     * ������� ������� ����
     */
    this.getCurrentBlock = function()
    {
        return  $("#authBlock .enterBlock").filter(":visible").get(0);
    };

    this.hidePassword = function()
    {
        var passwordField = document.getElementById("password");
        var hiddenPasswordField = document.getElementById("hiddenPasswordField");
        hiddenPasswordField.value = passwordField.value;
        passwordField.value = "";
    }
}

var stageForm = {

    send : function(operation)
    {
        FormSubmitter.submit($("#stageForm form").get(0), operation);
    },

    load : function(data)
    {
        $("#stageForm").html(data);
        if ($("#stageForm")[0] != undefined)
            win.open("stageForm");
    },

    close : function()
    {
        $("#stageForm").empty();
        if ($("#stageForm")[0] != undefined)
            win.close("stageForm");
    }
};

var FormSubmitter =
{
    isWait : false,

    submit : function(form, operation)
    {
        if(this.isWait)
            return;
        // �������� ��������
        this.isWait = true;
        // ������ ����������� ��������� ��������
        this.isWait = ajaxQuery(
                $(form).find(":input").filter(":not(.inputPlaceholder)").serializeToWin() + "&operation=" + operation,
                $(form).attr("action"),
                this.processResponce);
    },

    processResponce : function(data)
    {
        // ������ �� ������� ���������� ����� �������(��� ����������� ������ ����������� � div, ����� ������ �� ������ ������ �� ��������)
        var findedByData = "<div>" + data + "</div>";
        //���� ���� ������������� � ����� ��������.
        if ($(findedByData).find("input[name='$$turingTestFlag']").size() > 0)
        {
            displayError("��� ����������� ������ � �������� �������� ��� ���� � ������� ���, ������������ �� ��������.");
            authForm.updateForm(true);
        }
        // ���� ���� ���� �� ������
        else if($(findedByData).find("input[name='$$errorFlag']").size() > 0)
        {
            stageForm.close();
            displayError(data);
            authForm.updateForm(false, true);
        }
        // ���� ���� ���� � ������
        else if($(findedByData).find("input[name='$$reset']").size() > 0)
        {
            stageForm.close();
            if($(findedByData).find("div#reset-messages").size() > 0)
                displayError($(findedByData).find("div#reset-messages").html());
            authForm.showForm("login-form");
            authForm.updateForm(false);
        }
        else if($(findedByData).find("input[name='$$POSTRedirect']").size() > 0)
        {
            var redirectUrl = $(findedByData).find("input[name='$$POSTRedirect']").get(0);
            $("#redirectPayOrderForm").attr("action", redirectUrl.value).submit();
        }
        else if($(findedByData).find("input[name='$$redirect']").size() > 0)
        {
            var redirectToUrl = $(findedByData).find("input[name='$$redirect']").get(0);
            goTo(redirectToUrl.value);
            return;
        }
        // � ��������� ������� ������ ��������� ����������
        else
        {
            stageForm.load(data);
            authForm.updateForm(false);
        }

        FormSubmitter.isWait = false;
    }
};

function reloadNews()
{
    if (document.getElementById('MainPageNews') != null)
        ajaxQuery(null, url + "/async/news.do", setNewsOnMainPage, "json", false);
}

function reloadBlockingMessage()
{
    ajaxQuery(null, url + "/async/blockingMessage.do", setBlockingMessage, null, false);
}

function setBlockingMessage(data)
{
    if (trim(data) == '')
        return;
    var blockingMessage = $(data).find('#blockingMessage');
    if(blockingMessage.length == 0)
        $('#blocking-message-restriction-block').empty();
    else
        $('#blocking-message-restriction-block').html(blockingMessage.html());
}

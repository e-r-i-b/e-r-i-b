// инициализация календаря
function initDatePicker (node)
{
    var imageCalendar;
    if (document.webRoot == '/PhizIC')
        imageCalendar = globalUrl + '/commonSkin/images/datePickerCalendar.gif';
    else
        imageCalendar = skinUrl + '/images/calendar.gif';
    $(function()
     {
        var elems = node == null ? $('.date-pick') : $(node).find('.date-pick');
	    if ((elems.length > 0) && elems.datePicker)
        {
            var dP = elems.datePicker({displayClose: true, chooseImg: imageCalendar, dateFormat: ENGLISH_DATE_TEMPLATE});
            dP.dpApplyMask();
        }
     });
    // календарь формата 'dd.mm.yyyy'
    $(function()
     {
         var elems = node == null ? $('.dot-date-pick') : $(node).find('.dot-date-pick');
	    if ((elems.length > 0) && elems.datePicker)
        {
            var dP = elems.datePicker({displayClose: true, chooseImg: imageCalendar, dateFormat: 'dd.mm.yyyy' });
            dP.dpApplyMask();
        }
     });
     $(function()
     {
        var elems = node == null ? $('.async-date-pick') : $(node).find('.async-date-pick');
	    if ((elems.length > 0) && elems.datePicker)
        {
            var dP = elems.datePicker({displayClose: false, chooseImg: imageCalendar, dateFormat: 'dd.mm.yyyy',
            showYearMonth: true, startDate: asyncDatePickSettings.startDate, endDate: asyncDatePickSettings.endDate,
            needAjaxUpdateDisabledDates: true, disabledWeekends: true, ajaxUpdateUrl: asyncDatePickSettings.url,
            tb: asyncDatePickSettings.tb, verticalOffset: 43, horizontalOffset: -105, plannedPayments: asyncDatePickSettings.plannedPayments,
            showPlannedPaymentsText: asyncDatePickSettings.showPlannedPaymentsText, daySelectedHandler: asyncDatePickSettings.daySelectedHandler});
            dP.dpApplyMask();
        }
     });

    $(function()
    {
        var elems = node == null ? $('.dot-date-pick-after-current-date') : $(node).find('.dot-date-pick-after-current-date');
        if (( elems.length > 0) && elems.datePicker)
        {
            var tomorrow = new Date();
            tomorrow.setDate(tomorrow.getDate() + 1);

            var dP = elems.datePicker({displayClose: true, chooseImg: imageCalendar, startDate: tomorrow.asString('dd.mm.yyyy'), dateFormat: 'dd.mm.yyyy' });
            dP.dpApplyMask();
        }
    });
}

// инициализация масок ввода
function  initMaskedInput ()
{
    if (($('.time-template').length > 0) && $('.time-template').createMask)
        $('.time-template').createMask(TIME_TEMPLATE);

    if (($('.short-time-template').length > 0) && $('.short-time-template').createMask)
        $('.short-time-template').createMask(SHORT_TIME_TEMPLATE);

    if (($('.phone-template-p2p').length > 0) && $('.phone-template-p2p').createMask)
        $('.phone-template-p2p').createMask(PHONE_TEMPLATE_P2P);

    if (($('.phone-template-new').length > 0) && $('.phone-template-new').createMask)
        $('.phone-template-new').createMask(PHONE_NUMBER_NEW);

    if (($('.passport-number-and-series-template').length > 0) && $('.passport-number-and-series-template').createMask)
        $('.passport-number-and-series-template').createMask(PASSPORT_NUMBER_AND_SERIES_TEMPLATE);

    if (($('.passport-issue_by_code-template').length > 0) && $('.passport-issue_by_code-template').createMask)
        $('.passport-issue_by_code-template').createMask(PASSPORT_ISSUE_BY_CODE_TEMPLATE);
}

// Инициализация полей, допускающих ввод только чисел
function initNumberInput ()
{
    $('.numberField').bind("change keyup input click", function() {
        if (this.value.match(/[^0-9]/g)) {
            this.value = this.value.replace(/[^0-9]/g, '');
        }
    });
}

// Установка размера окна для справочников
function initReferenceSize(){
    if (!$.isEmptyObject(window.document.forms[0]))
    {
        var action = window.document.forms[0].action;
        action+=action.indexOf("?") >= 0?"&":"?";
        window.document.forms[0].action = action+"noResize=1";
    }
    if (window.location.href.indexOf("noResize") >= 0)
        return;

    var clientScreenHeight = screen.height - 30; // высота всего экрана минус меню с кнопкой "Пуск"

    var windowWidth = $('#reference').width()+30;
    var windowHeight = $('#reference').height();

    if($.browser.msie){
        windowHeight += 70;
    } else if($.browser.mozilla){
        windowHeight += 130;
    } else if($.browser.webkit){
        windowHeight += 90;
    }
    window.resizeTo(windowWidth, windowHeight);
    var moveHeight = screen.height/2 - windowHeight/2;
    if (moveHeight < 0)
        moveHeight = 0;
    window.moveTo(screen.width/2 - windowWidth/2, moveHeight);
}

// Ждем, пока загрузится все дерево DOM, что бы можно было с ним работать

$(document).ready(function(){

    if (window.getCookie)
    {
        // При загрузке страницы проверяем куки на наличие в них данных по смещению
        //отрезание параметров по необходимости
        var urlStr = document.location;
        var paramIdx = urlStr.href.indexOf('\?');
        var path = (paramIdx == -1 ? urlStr : urlStr.href.slice(0,paramIdx));
        if(getCookie("isFormSubmit")=="true" && path==getCookie("currentUrl")){
            // Смещаем форму, только если на странице не появились warnings или errors
            if($('#errors').css("display")=="none" && $('#warnings').css("display")=="none"){
                window.scrollTo(0, getCookie("offset"));
            }
        }else{
            deleteCookie("isFormSubmit");
            deleteCookie("offset");
            deleteCookie("currentUrl");
        }

        // Убивает признак перехода с формы, для идентификации повтроного захода
        // на страницу с формой после того как ушли с нее

        deleteCookie("isFormSubmit");
        setCookie("isFormSubmit", "false");
    }
	// Фиксим проблему с :hover для элементов отличных от <a> в ie6

    $('#menu td').mousedown(function() {
        $(this).addClass('clickMenu');
    });
    $('#menu td').mouseup(function() {
        $(this).removeClass('clickMenu');
    });

	$('#menu td').hover(function(){
	   $(this).addClass('hover');
	   }, function() {
	   $(this).removeClass('hover');
        $(this).removeClass('clickMenu');
	});
    $('#menu td:last').hover(function(){
	   $(this).addClass('hoverLast');
	   }, function() {
	   $(this).removeClass('hoverLast');
	});

    $('.imgHintBlock').hover(function(){
	   $(this).addClass('imgHintBlockHover');
	   }, function() {
	   $(this).removeClass('imgHintBlockHover');
	});

    $('.chooseConfirmStrategy').parent('.buttonsArea').addClass('strategy');

    $('.commission-block .paymentValue .black_link').parents('.form-row').hover(function(){
        $(this).attr('id','hoverLightRow');
    }, function() {
        $(this).removeAttr('id');
    });

    $('.btn_edit').mousedown(function() {
        $(this).addClass('btn_edit_click');
    });
    $('.btn_edit').mouseup(function() {
        $(this).removeClass('btn_edit_click');
    });

    //создаем подчеркивание при наведении для элементов, отличных от а
    $('.blueGrayLink, .paymentInputDiv span.text-green, .mainProductTitleLight, .subMTitle i').hover(function(){
       $(this).addClass('linkHover');
       }, function() {
       $(this).removeClass('linkHover');
    });

    $('.blueGrayLinkDotted, .simpleLink span.text-green, span.link, .productBottom a, span.reigonSearch, .selectRegion span, .blockedProducts .titleControl span, .archivePayments .titleControl span').hover(function(){
       $(this).addClass('linkHoverDotted');
       }, function() {
       $(this).removeClass('linkHoverDotted');
    });

    $('.imageAndButton, .immitate .clientButton').hover(function(){
      $(this).addClass('buttonHover');
      }, function() {
      $(this).removeClass('buttonHover');
    });

    $('.headHelpLink').hover(function(){
       $(this).addClass('linkHover');
       }, function() {
       $(this).removeClass('linkHover');
    });

    $('.lightGreenRound').hover(function(){
        $(this).addClass('lightGreenHover');
    }, function() {
        $(this).removeClass('lightGreenHover');
    });

    $('.paymentOnReceipt').hover(function(){
       $(this).addClass('receiptHover');
       }, function() {
       $(this).removeClass('receiptHover');
    });
    $('.blueBorder').hover(function(){
       $(this).addClass('addHover');
       }, function() {
       $(this).removeClass('addHover');
    });

    //определяем последний элемент списка меню
    $('.personalMenuWithTitleItem:last').addClass('lastTitleItem');
    $('#menu #dropDownMenu td:last').addClass('lastItem');
    $('.subMInsetGroup:last').addClass('lastGroupItem');
    $('.subMInactiveInset:last').addClass('lastItem');

    // Подсветка ссылок личного меню

    $('.personalMenuWithTitleItem .greenTitle').live("mouseenter",
        function(){
            $(this).addClass('hover');
    });

    $('.personalMenuWithTitleItem .greenTitle').live("mouseleave",
        function(){
            $(this).removeClass('hover');
    });



    $('.mobileBannerBlock a, #favouriteLinks li a').live("mouseenter",
           function(){
               $(this).addClass('hover');
     });

    $('.mobileBannerBlock a, #favouriteLinks li a').live("mouseleave",
          function(){
               $(this).removeClass('hover');
    });

    $('.mobileBannerBlock .greenTitle').live("mouseenter",
           function(){
               $(this).addClass('hover');
     });

    $('.mobileBannerBlock .greenTitle').live("mouseleave",
          function(){
               $(this).removeClass('hover');
    });
    $('.conditionsBlock').live("mouseenter",
           function(){
               $(this).addClass('conditionHover');
     });

    $('.conditionsBlock').live("mouseleave",
          function(){
               $(this).removeClass('conditionHover');
    });


   //подсветка кнопки Помощь в правой колонке
   $('.helpAppeal a').hover(function(){
       $(this).addClass('greenLightText');
       }, function() {
       $(this).removeClass('greenLightText');
   });


    //подсветка кнопок типа greenButton
    buttonAddEventHover('buttonGreen', 'buttonGreenHover');
    buttonAddEventClick('buttonGreen', 'buttonGreenClick');
    buttonAddEventClick('btnGreenSmall', 'GreenSmallClick');

    //подсветка кнопок типа buttonLightGray
    buttonAddEventHover('buttonLightGray', 'buttonLightGrayHover');
    buttonAddEventClick('buttonLightGray', 'buttonLightGrayClick');
    buttonAddEventClick('grayBtn', 'grayBtnClick');

    //подсветка кнопок типа buttonRoundGray
    buttonAddEventHover('buttonRoundGray', 'buttonRoundGrayHover');
    buttonAddEventClick('buttonRoundGray', 'buttonRoundGrayClick');

    //подсветка кнопок типа buttonOrange
    buttonAddEventHover('buttonOrange', 'buttonOrangeHover');
    buttonAddEventClick('buttonOrange', 'buttonOrangeClick');

    //подсветка кнопок типа buttonGrey, simpleLink, buttonGray
    buttonAddEventHover('buttonGrey', 'buttonGreyHover');
    buttonAddEventClick('buttonGrey', 'buttonGreyClick');
    buttonAddEventHover('simpleLink', 'simpleLinkHover');
    buttonAddEventClick('simpleLink', 'simpleLinkClick');
    buttonAddEventHover('buttonGray', 'buttonGrayHover');
    buttonAddEventClick('buttonGray', 'buttonGrayClick');
    buttonAddEventClick('grayProfileButton', 'grayProfileButtonClick');
    buttonAddEventClick('whiteProfileButton', 'whiteProfileButtonClick');
    buttonAddEventClick('orangePromo', 'orangePromoClick');
    //подсветка пагинации
    buttonAddEventHover('activePaginRightArrow', 'pageIconHover');
    buttonAddEventClick('activePaginRightArrow', 'pageIconClick');
    buttonAddEventHover('activePaginLeftArrow', 'pageIconHover');
    buttonAddEventClick('activePaginLeftArrow', 'pageIconClick');

    //подсветка иконки при клике
    buttonAddEventClick('closeImg', 'closeImgClick');
    buttonAddEventClick('removeIdentButton', 'removeClick');
    buttonAddEventClick('lightGrayProfileButton', 'profileButtonClick');
    buttonAddEventClick('.newSecondMenu li', 'tabClick');


    $('.showLight').live("mouseenter",
        function(){
            if (!$(this).parents('#mobilebank').size())
                $(this).addClass('productTitleHover');
    });

    $('.showLight').live("mouseleave",
        function(){
            $(this).removeClass('productTitleHover');
            window.remProductObj = this;
    });

    // Определяем последний элемент списка
    $(".help-menu li:last").addClass("lastHelpPosition");
    $(".securityOptions:last").addClass("securityOptionsLast");
    $(".addedCard:last").addClass("lastAddedCard");

    $.each($(".viewCardsClaim"), function(){
        $(this).find('tr:last').addClass('lastTblData');
    });

    // Определяем min-height контента
    var leftMenu = $("#help-left-section").height();
    $("#help-workspace").css("min-height", leftMenu);

    $("table.productOperationList").find("tr:first").addClass("opHoverFirst");
    $("table.productOperationList").find("tr:last").addClass("opHoverLast");
    $("tr.opHoverFirst.opHoverLast").addClass("FirstLastOperation");

    //Задаем ширину для заголовка первого столбца в списке сортировки
    $(".sort li.listInfHeader span:first-child, .no-sort li.listInfHeader span:first-child").addClass("firstColumnWidth");
    //Задаем ширину для заголовка третьего столбца в списке сортировки
    $(".sort li.listInfHeader span:first-child + span + span, .no-sort li.listInfHeader span:first-child +span +span").addClass("thirdColumnWidth");

    $(".securityOptionsArea").find(".securityOptions" + ":last").find(".productDivider").remove();

    //для IE открываем выпадающий блок с информацией о предыдущем входе клиента в шапке
    if (isIE(6)){
        $('.lastPositionActive').hover(function(){
           $(this).addClass('activeHoverLast');
           }, function() {
           $(this).removeClass('activeHoverLast');
        });

        $('.topHint').hover(function(){
           $(this).find('.topHintsBlock ').show();
           }, function() {
            $(this).find('.topHintsBlock ').hide();
        });


    }

    $('.changeStrategy').click(function(){
        var el = $(this);
        if (el.hasClass('showStrategy'))
            $(this).removeClass('showStrategy');
        else
            $(this).addClass('showStrategy');
    });

    //обрабатываем клик вне  changeStrategy
    $(document).click(function(e) {
        var target = $(e.target);
        if(target.closest(".changeStrategy").length==0 && target.closest("anotherStrategy").length==0)
        {
            $(".changeStrategy").removeClass('showStrategy');
        }
    });

    // Вертикальная подсказка
    $('.imgHint').each(function()
    {
        // Блок информации позиционируем по центру относительно
        var hintInfo = $(this).find('.hintBlock');
        var imgHint = $(this);
        hintInfo.css("top",$(this).height()/2 - hintInfo.height()/2);

        // Выравниваем треугольник относительно родительской иконки
        var tBlock = $(this).find('.tHint');
        var tHeight = 22;
        tBlock.css("top",hintInfo.height()/2 - tHeight/2);

        $(this).hover(function(){
            hintInfo.show();
        }, function() {
            hintInfo.hide();
        });
    });

    // Блок информации позиционируем по центру относительно ФИО клиента
    var clientInfo = $('.clientInfo');
    clientInfo.css("margin-left",$('#previousEnterInfo span span').width()/2 - clientInfo.width()/2 + $('.fixAvatarBlock').width());

    // Находим последний элемент Блок anotherStrategy
    $('.anotherStrategy ul li:last').addClass('lastElement');

    var parentText = $('#state span, .text-highlight, .simpleHintLabel');
    var descriptionHint = $('#stateDescription');
    showMainHint(parentText, descriptionHint);

    // показать простую подсказку
    $('.simpleHintBlock').hover(function(){
        $(this).find('.simpleHint').css('display', 'block');
    }, function() {
        $(this).find('.simpleHint').css('display', 'none');
    });

    $(".p2pState").click(function () {
        $(".bankDetailData").toggle(500);
    });

    promoDataWidth();
    promoOldProfileDataWidth();

    // Навешиваем событие на клик ля всех commandButton, при котором в куки пишем текущие
    // координаты смещиния страницы, URL и признак сабмита с формы

    $('.commandButton').click(function(){

        saveOffset();

    });

	// Прячем тизер warnings с отчетами об ошибках раскоментировать, что бы прятать

	/*$('#warnings').hide();
    $('#errors').hide();*/


	// Сбрасывание текста "Введите услугу для поиска" в форме пойска

	$('input[name=search]').focus(function(){
		$(this).val('');
	});

    //перерасчет ширины блока выбора региона
    var regionTextWidth = $('.paymentGoods .regionSelect span').width();
    $('.paymentGoods .regionSelect span').parent().css('width', regionTextWidth);

    // Скрипты для истории операций

    if($.browser.opera){
		$('#history-filter span, #history-filter label').css('top','0');
	};

	$('input[value=week], input[value=month]').click(function(){
		$('input[name=filter(toDate)], input[name=filter(fromDate)]').attr('disabled','disabled');
	});

	$('input[value=period]').click(function(){
		$('input[name=filter(toDate)], input[name=filter(fromDate)]').removeAttr('disabled');
	});

	$('#submit-button').click(function() {
		$('#history-filter').submit();
	});

    if($('input[value=period]').attr('checked')){
        $('input[name=filter(toDate)], input[name=filter(fromDate)]').removeAttr('disabled');
    };

//	$('tr').not(':first').hover(function(){
//		$(this).addClass('over').prev().addClass('overprev');
//	},function(){
//		$(this).removeClass('over').prev().removeClass('overprev');
//	});

    //Определяем тип браузера для переопределения стилей у вкладок
    if(isBrowser('opera') || isBrowser('safari')){
		$('ul.newSecondMenu3 li.grayLast').css("width","246px");
	};


    // Вкладки в профиле пользователя

    //профиль пользователя, настройки шаблонов, избранных операций и главного меню
    // + АРМ Управление каталогом виджетов
    $(".linkUp,.linkDown").mousedown(function() {

        linkMove(this);

    });

    var lastLoginInfoHeight=$('.header').height()-68;
    $('.header-info').css('margin-top',lastLoginInfoHeight);

   // подключение календаря
    initDatePicker();

    initMaskedInput();

    initNumberInput();

//список новостей
	var clearAction = $(".search-box .search-clear-action");
	var newsFilter = $(".news-filter");

	$(".search").click(function(){
		if(clearAction.css("display")=="none" && newsFilter.css("display")=="none"){
			clearAction.show();
			newsFilter.show();
		}else{
			clearAction.hide();
			newsFilter.hide();
		}
	});
	$(".news-filter .close").click(function(){
		clearAction.hide();
		newsFilter.hide();
	});

    $('.clientButton.hoverBtn .simpleLink').parent().hover(
        function(){
                var simpleLink = findChildByClassName(this, "simpleLink");
                var beforeW = $(this).width();
                if (this.style.height == '') this.style.height = $(this).height()+"px";
                $(simpleLink).removeClass('simpleLink').addClass('buttonGrey');
                this.style.width = beforeW + "px";
            },
		function(){
                var simpleLink = findChildByClassName(this, "buttonGrey");
                this.style.width = "auto";
                $(simpleLink).removeClass('buttonGrey').addClass('simpleLink');
			}
    );

    //персональное меню
    // так как Opera не понимает
    if (window.opera)
        //профиль
        $.each($("#profile .favourites-operations td div span"), function() { this.innerHTML = wordBreak(this.innerHTML, 21); } );

    // включение кэша фоновых картинок в ие 6, не каждая сборка 6 ие знает это свойство, так что для безопастности глушим ошибку
    if (isIE(6))
        try {

            var el = document.getElementById("loading");
            if (el != null)
            {
                plateSrc = skinUrl+'/images/plate.png';
                el.style.filter ="progid:DXImageTransform.Microsoft.AlphaImageLoader(src="+plateSrc+",sizingMethod='scale')";

            }
            var bgFilter = "filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + skinUrl + "/images/selectArrow.png', sizingMethod='scale')";
            document.styleSheets[0].addRule("div.selectArrow", bgFilter);
            document.styleSheets[0].addRule("div.selectArrow", "background-image: none;");
            document.styleSheets[0].addRule(".disabled .selectArrow", bgFilter+ " alpha(opacity = 50)");

            IEPNGFix.fixTransparentBackground(globalUrl + "/commonSkin/images/customSelectHideShadow.png", "div.customSelectHideShadow");
            IEPNGFix.fixTransparentBackground(globalUrl + "/commonSkin/images/buttonGray_grad1.png", "div.buttonRoundGray.buttonWithImg div.buttonGrad");
            IEPNGFix.fixTransparentBackground(globalUrl + "/commonSkin/images/buttonGray_grad2.png", "div.buttonRoundGray.buttonWithImg.buttonRoundGrayHover div.buttonGrad");
            IEPNGFix.fixTransparentBackground(globalUrl + "/commonSkin/images/buttonGray_grad3.png", "div.buttonRoundGray.buttonWithImg.buttonRoundGrayClick div.buttonGrad");
            IEPNGFix.fixTransparentBackground(globalUrl + "/commonSkin/images/lightness.png", ".lightness");
            IEPNGFix.fixTransparentBackground(globalUrl + "/commonSkin/images/orangeHorizScroll.png", ".orangeScrollInner");
            IEPNGFix.fixTransparentBackground(skinlUrl + "/images/subMAciveVertGradient.png", ".shadowInsetLeft");

            document.execCommand("BackgroundImageCache",false,true);
        }
        catch (e) { }

    if ($('.word-wrap').breakWords)
        $('.word-wrap').breakWords();

    $('.rowOver .ListLine0').live('mouseenter',
        function(event) {
            $(this).addClass("over");
        }
    );

     $('.rowOver .ListLine0').live('mouseleave',
        function(event) {
            $(this).removeClass("over");
        }
    );

    $('.rowOver .ListLine1').live('mouseenter',
        function(event) {
            $(this).addClass("over");
        }
    );

    $('.rowOver .ListLine1').live('mouseleave',
        function(event) {
            $(this).removeClass("over");
        }
    );

    textareaAutoHeight ();

    initPhoneNumber();

    if (window.customPlaceholder != undefined)
        customPlaceholder.init();

    //расчет отображения заголовков

    $('.Title span').each(function(index,element)
       {
           var titleHeight = $(element);
           if (titleHeight.text().length > 37)
           {
               $(this).addClass('middleTitle');
           }
        });

    $('.RTC').each(function()
       {
           var allTitleWidth = $(this).width();
           var controlWidth = $(this).find('.CBT').width();
           var titleWidth = $(this).find('.Title').width();
           var titleInnerWidth = $(this).find('.Title span').width();

           if (controlWidth > 0)
             {
                 titleWidth = allTitleWidth - controlWidth;
                 $(this).find('.Title').css('width', titleWidth + 20);
                 $(this).find('.CBT').css('width', controlWidth);

                 var controlTitleWidth = titleWidth + controlWidth;
                 if (controlTitleWidth >= allTitleWidth)
                    {
                        $(this).find('.Title').css('width', titleWidth - 20);
                    }
             }

           if (titleWidth > titleInnerWidth)
           {
               $(this).find('.Title').css('width', titleInnerWidth + 20);
           }
        });

    // Всплывающая подсказка при наведении на иконку
    $('.detailHint').each(function()   // Бежим по каждой компоненте для корректного отображения последующих подсказок
    {
        // Блок информации позиционируем по центру относительно иконки
        var hintInfo = $(this).find('.hintBlockBtm');
        var imgHint = $(this);
        hintInfo.css("left",$(this).width()/2 - hintInfo.width()/2);

        // Выравниваем треугольник относительно родительской иконки
        var tBlock = $(this).find('.tHintTop');
        var tWidth = 20;
        tBlock.css("left",hintInfo.width()/2 - tWidth/2);

        $(this).hover(function(){
            hintInfo.show();
        }, function() {
            hintInfo.hide();
        });
    });

    initProductTitle();

    adaptiveWidth();
    adaptiveVerticalPosition();
    $('.showHideCreditOffers').click(hideAddCondition);
    calcCellsWidth();

    //Отображение блока Операции
    $('.listOfOperation .buttonSelect, .buttonGrayNew, .buttonSelect.productListOperation').hover(function(){
       $(this).addClass('hoverOperation');
       }, function() {
       $(this).removeClass('hoverOperation');
    });

    $(".listOfOperation").click(function(){
        showOrHideOperationBlock(this);
    });

    // IE6 и IE7 не могут сами корректно расчитать ширину div, в котором кнопка "Операции" у продуктов. Поможем им.
    if (isIE(6) || isIE(7))
    {
        $(".productRight .listOfOperation").each(function(){
            var newWidth = $(this).find('.buttonSelect').width();
            $(this).width(newWidth);
        });
    }

//    обрабатываем клик вне  listOfOperation
    $("body").click(function(e) {
        var target = $(e.target);
        if(target.closest(".listOfOperation").length==0 && target.closest(".linkListOfOperation").length==0)
        {
            hideListOfOperation();
        }
    });
});
$(function() {
    $('.imgHintBlock').click(function(event) {

        var message = $(this).parent().find('.hintsBlock');
        if (message.css('display') == 'none') {
            message.show();
        }
        else {
            message.hide();
        }
        return false;
    });

    $('.hintsBlock, .hintsBlock *').click(function (event) {
        return false;
    });

    $('body :not(.showHintBlock *)').click(function (event) {
        $('.hintsBlock').hide();
    });
});
function showOrHideOperationBlock(element)
{
    var el = $(element);
//      отображаем блок, если он еще не отображается
    if(!el.hasClass("relative"))
    {
//               скрываем все остальные открытые элементы
        hideListOfOperation();
        el.addClass("relative");
        el.css("z-index",4);
        el.find(".opHoverFirst td").addClass("opHover");
        var itemId = el.attr("id");
        var childId = itemId.replace("_parent",'');
        if (!childId)
            return;
        var obj = el.find("#" + childId);
        var timeoutId = setTimeout(function(){
            obj.show();
            obj.css("z-index",4);
            el.parents(".forProductBorder.showLight").addClass("productTitleHoverList");
        }, 150);
        el.data('timeoutId', timeoutId);
    }
    else
    {
        hideThisListOfOpertaion(element);
    }
}

//функция, скрывающая блок "операции"
function hideListOfOperation()
{
    $(".listOfOperation").each(function(){hideThisListOfOpertaion(this)});
}

function hideThisListOfOpertaion(element)
{
    clearTimeout($(element).data('timeoutId'));
    $(element).removeClass("relative");
    $(element).css("z-index",0);
    var itemId = $(element).attr("id");
    var childId = itemId.replace("_parent",'');
    if (!childId)
        return;
    var obj = $(element).find("#" + childId);
    obj.hide();
    obj.css("z-index",0);
    $(element).parents(".forProductBorder.showLight").removeClass("productTitleHoverList");
}

// Блок с подсказкой позиционируем по центру относительно блока, требующего подсказки
function showMainHint (element, descBlk)
{
    var descHintWidth = descBlk.width();
    var whiteDescriptionHint = $('.stateDescription.whiteHint');
    var descriptionHintSimple = $('.simpleHint');
    var triangle = $('.floatMessageHeader');

    descBlk.css("margin-left",element.innerWidth()/2 - descBlk.width()/2);
    whiteDescriptionHint.css("margin-left",element.innerWidth()/2 - descBlk.width()/2 + 27);
    $(triangle).css('width', descBlk.width());

    descriptionHintSimple.css("margin-left",element.innerWidth()/2 - descriptionHintSimple.width()/2);
    $(triangle).css('width', descriptionHintSimple.width());
    var p2pHint = $('.payData #state');
    if(p2pHint.height() > 20)
    {
        $('#stateDescription').addClass('secondLine');
    }
}
// расчет ширины заголовков
function initProductTitle()
{
    $('.productTitle').each(function()
    {
        var titleBlock = $(this).find('.titleBlock').width();
        var amountAndOperations = $(this).find(".productButtonsAndOperations").width();
        var productStatus = $(this).find(".prodStatus.status").width();
        var maxWidth = $(this).parent(".all-about-product").width();
        var descriptionRight = $(this).find(".descriptionRight").width();

        // Изменение общей ширины для доп.продукта
        var additionalBlockWidth = $(this).parent().parent().find(".all-about-product-with-arrow");
        if (additionalBlockWidth.length > 0){
            var maxWidth = additionalBlockWidth.width();
        }
        var maxTitleWidth = maxWidth - productStatus - amountAndOperations - descriptionRight;

        if (titleBlock > maxTitleWidth)
        {
            if (maxTitleWidth < 200)
            {
                var changed = false;
                var detailTitle = $(this).find('.mainProductDetailTitle');
                if (detailTitle.length > 0)
                {
                    detailTitle.addClass('size20');
                    changed = true;
                }

                var detailAmount = $(this).find('.detailAmount');
                if (detailAmount.length > 0)
                {
                    detailAmount.addClass('size20');
                    changed = true;
                }

                if (changed)
                {
                    //пересчитываем длину блоков
                    titleBlock = $(this).find('.titleBlock').width();
                    amountAndOperations = $(this).find(".productButtonsAndOperations").width();
                    maxTitleWidth = maxWidth - productStatus - amountAndOperations;
                }
            }

            if (titleBlock > maxTitleWidth){
                titleBlock = maxTitleWidth - 10;
                $(this).find('.titleBlock').addClass('showLightness');
            }
        }

        var mainProductDetailTitle = $(this).find('.mainProductDetailTitle');
        if (mainProductDetailTitle.length > 0)
        {
            var textWidth = mainProductDetailTitle.width();
            if (titleBlock < textWidth)
            {
                mainProductDetailTitle.addClass('size21');
                $(this).find('.detailAmount').addClass('size21');
                $(this).find('.productAmount').addClass('topText');
                $(this).find('.descriptionRight').addClass('smallTextDesc');
                amountAndOperations = $(this).find('.productButtonsAndOperations').width();
                maxTitleWidth = maxWidth - amountAndOperations - descriptionRight;
                titleBlock = maxTitleWidth - 10;
            }
        }
        var titleBlockInner = $(this).find('.mainProductTitle').width();
        if (titleBlock >= titleBlockInner)
        {
            if (titleBlockInner + 20 + productStatus + amountAndOperations <= maxWidth)
                titleBlock = titleBlockInner + 20;

            $(this).find('.descriptionRight').css('left', titleBlockInner); // определяем позицию отображения description
        }
        else{
            $(this).find('.descriptionRight').css('left', titleBlock);
        }

        $(this).find('.titleBlock').css('width', titleBlock);
        $(this).find('.titleBlock').parent('td').css('width', titleBlock);
        $(this).find('.description').each(function()
        {
            if(!$(this).hasClass("descriptionRight"))
            {
                $(this).css('width', titleBlock);
            }
        });
        $(this).find('.titleBlock').parent('.titleName').css('width', titleBlock);
        if (titleBlock >= titleBlockInner)
        {
            $(this).find('.titleBlock').parent('.titleName').css('width', titleBlockInner + 20);
        }
    });
}

// Автовысота для textarea
function textareaAutoHeight(){
    // Проверка на заполненность поля при загрузке страницы
    var textareaBlock  = $("#groundRow .paymentValueNew textarea");
    var hiddenBlock  = $('.newAreaHeight');
    var areaVal = textareaBlock.val();
    hiddenBlock.text(areaVal);
    var newHeight = hiddenBlock.innerHeight();
    if(newHeight > 0){
        hiddenBlock.css('height','');
        textareaBlock.css('height',newHeight);
    }
    if(newHeight < 40){
        textareaBlock.css('height', '25px');
    }
    // Ресайз по событию
    textareaBlock.focusin(function() {
        $(this).css('height', '110px');
    });
    textareaBlock.focusout(function() {
        // Делаем задержку, чтобы отработал onclick на других элементах
        setTimeout(function(){
            var areaVal = textareaBlock.val();
            if(areaVal == 0){
                $('textarea#ground').css('height', '25px');
            }
            hiddenBlock.text(areaVal);
            var newHeight = hiddenBlock.innerHeight();
            textareaBlock.css('height',newHeight);
            if(newHeight < 40){
                $('textarea#ground').css('height', '25px');
            }
        }, 50);
    });
}

// расчет ширины заголовков при загрузке страницы
function initInvoiceTitle(){
    $('.il-main').each(function()
    {
        initInvoiceTitleOne(this);
    });
}

// расчет ширины описания промо-кода
function promoDataWidth(){

    $('.promoCodeList tr').each(function()
    {
        var PromoTbl = 656;
        var PromoCode = $(".promoName").width();
        var promoDate = $(".endDate .nowrapWhiteSpace").width();
        var promoDesc = $('.promoCodeList .shortDescription').width();

        promoDesc =  PromoTbl - PromoCode -  promoDate;

        $(this).find('.shortDescription span').css('width', promoDesc);
    });
    var promoTitle = $("th.align-left").width();
    $(".promoCodeList tr").find('td.promoTitle').css('width', promoTitle);
}
// расчет ширины описания промо-кода для старого профиля
function promoOldProfileDataWidth(){
    //тут
    $('.promoOldProfileCodeList tr').each(function()
    {
        var PromoTbl = 589;
        var PromoCode = $(".promoName").width();
        var promoDate = $(".endDate .nowrapWhiteSpace").width();
        var promoDesc = $('.promoOldProfileCodeList .shortDescription').width();

        promoDesc =  PromoTbl - PromoCode -  promoDate;

        $(this).find('.shortDescription span').css('width', promoDesc);
    });
    var promoTitle = $("th.align-left").width();
    $(".promoOldProfileCodeList tr").find('td.promoTitle').css('width', promoTitle);
}
// расчет ширины конкретного заголовка при hover
function  initInvoiceTitleOne(invoiceItem)
{
    var indent = 12;
    var parentBlock = $(invoiceItem).find('.il-invoice').width();

    var icon = 35;

    var invoiceName = $(invoiceItem).find('.il-invoiceName').width();
    var originalWidth = $(invoiceItem).find('.il-invoiceName span').width();
    var stateIcon = $(invoiceItem).find('.il-stateIcon').width();
    var payBtn = $(invoiceItem).find('.il-right').width();
    var entityName = $(invoiceItem).find('.entityName').width();

    // расчет ширины имени счета
    maxNameBlock = parentBlock - icon - payBtn - indent;
    $(invoiceItem).find('.il-center').css('width', maxNameBlock);

    invoiceNameWidth = maxNameBlock - entityName;
    $(invoiceItem).find('.il-invoiceName').css('width', invoiceNameWidth - 2);
    invoiceName = invoiceNameWidth;

    if(invoiceName < originalWidth){
        $(invoiceItem).find('.il-invoiceName .hideText').css('display', 'block');  // если рассчитаная ширина меньше начальной добавляем затенение
    } else if(invoiceName >= originalWidth){
        $(invoiceItem).find('.il-invoiceName').css('width', originalWidth + 2);    // иначе возвращаем исходную ширину
    }

    // Наименование счета и наименование объекта учета. расчет ширины
    var providerName = $(invoiceItem).find('.il-providerName').width();
    var keyName = $(invoiceItem).find('.il-keyName').width();
    centerBottom = providerName + keyName + 7;
    $(invoiceItem).find('.il-centerBottom').css('width', centerBottom);

    if(centerBottom > maxNameBlock){
        $(invoiceItem).find('.il-centerBottom .hideText').css('display', 'block');
    }
}

function adaptiveVerticalPosition()
{
    // Позиционируем баббл по середине области предложений независимо от количества элементов
    var tblHeight = $('.fixHeightTbl').height();
    var bubble = $('.offerBubble').height();
    var indentbubble = 38; // Отступ, компенсирующий реальный размер баббла
    $('.offerBubble').css('top', tblHeight/2 - (bubble/2 - indentbubble));
}
// Скрываем или показываем условия и дополнительные предложения
function hideAddCondition()
{
    var condition = $('.creditOfferConditions');
    if(condition.css("display")=="block"){
        condition.hide();
        $(".showHideCreditOffers").text("Показать другие условия");
        $('.alternativeOffers tr:not(.mainCreditOffer)').css('display', 'none');
        $('.mainCreditOffer').addClass('onlyMainOffer');
        $('.offerBubble').hide();
    }else{
        condition.show();
        $(".showHideCreditOffers").text("Скрыть другие условия");
        $('.alternativeOffers tr').css('display', '');
        $('.mainCreditOffer').removeClass('onlyMainOffer');
    }
}
// Выравниваем ширину ячеек шапки и тела таблицы
function calcCellsWidth()
{
    var firstThIndent = 52;
    var tblWidth = $('.alternativeOffers').width();
    var summCell = $('.mainOfferTick').innerWidth() + $('.alternativeOffers .offerSum').innerWidth() - firstThIndent;
    var offerPeriodCol = $('.offerPeriodCol').width();
    var offerRate = $('.offerRate').width();

    $('th.offerSum').css('width', summCell);
    $('.alternativeOffersStyle table').css('width', tblWidth);

}
function calcCellsTblWidth()
{
    var tblWidth = $('#chooseDepOnList').width();
    var depName = $('.depName').width();

    $('.depsOnList table').css('width', tblWidth);
    $('.depNameHdr').css('width', depName);
}

function mouseEnter(element)
{
    $(element).parents(".listOfOperation").find(".opHover").removeClass("opHover");
    $(element).addClass("opHover");
    return false;
}

function adaptiveWidth()
{
    var minWidthBlock = 0;
    $('.commission-block .paymentValue').each(function(){
        var innerText= $(this).find('.paymentInputDiv');
        var thisWidthBlock = $(innerText).outerWidth();
        if (thisWidthBlock > minWidthBlock) minWidthBlock = thisWidthBlock;
    });
    $('.commission-block .paymentValue').css('width', minWidthBlock + 10 + 'px');
    $('.commission-block .paymentValueNew').wrap("<div class='fullValue'></div>");

}

function mouseLeave(element)
{
    $(element).removeClass("opHover");
    return false;
}

function initPhoneNumber()
{ $('.masked-phone-number').live("click", function(event){
        if(!$(this).attr('disabled') && !$(this).attr('readonly')) {
            phoneInputClick(this);
        }
    });
}

function phoneInputBlur()
{
    if (trim(this.value) == "")
    {
        this.value = this.origValue;
        this.style.color = this.origColor;
        this.origValue = undefined;
        this.origColor = undefined;
    }
}
function phoneInputClick(obj)
{
    if (obj.origValue == undefined)
    {
        obj.origValue = obj.value;
        obj.origColor = obj.style.color;
        obj.onblur = phoneInputBlur;
        obj.value = '';
        obj.style.color = "black";
    }
}

function buttonAddEventHover(buttonClass, hoverClassName)
{
    $('.' + buttonClass).live("mouseenter",
        function(){
            $(this).addClass(hoverClassName);
    });

    $('.' + buttonClass).live("mouseleave",
        function(){
            $(this).removeClass(hoverClassName);
    });

     $('.' + buttonClass).live("focus",
        function(){
            $(this).addClass(hoverClassName);
    });

    $('.' + buttonClass).live("blur",
        function(){
            $(this).removeClass(hoverClassName);
    });
}

function buttonAddEventClick(buttonClass, clickClassName)
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

// Перенос длинного слова
(function($)
{
    $.fn.breakWords = function()
    {
        this.each(function()
        {
            if(this.nodeType !== 1)
            {
                return;
            }
            //Нужно ли переносить целым словом не разрывая его, если это возможно.
            var isWholeWords = ($(this).attr("class").indexOf("whole-words") != -1);
            var width = 0;
            if(isWholeWords)
            {
                var inner = $(this).html();
                $(this).html('');
                width = $(this).parents("div:first").width();
                $(this).html(inner);
            }

            // символ - zero width space
            var c = String.fromCharCode(8203);
            // получение всех текстовых узлов
            var elements = $(this).find(":not(iframe)").andSelf().contents().filter(function()
            {
                return this.nodeType == 3;
            });
            elements.each(function () {
                if (trim(this.nodeValue) != "")
                {
                    var s = "";
                    // разбиваем строку на слова
                    var wordAr = this.nodeValue.split(" ");
                    for (var i=0; i<wordAr.length; i++ )
                    {
                        if (i!=0) s += ' ';

                        // каждое слово, если оно больше 8 символов разбиваем на части:
                        // первые и последние два символа не разбиваем, остальные, после каждого
                        // добавляем перенос строки.
                        // число 8 является статистической величиной и взята в результате исследования
                        var word = wordAr[i];
                        //Вычисление размера слова в пикселях.
                        var wordWidth = 0;
                        if(isWholeWords)
                        {
                            var objq = document.createElement('span');
                            var str = document.createTextNode( word );
                            objq.appendChild(str);
                            this.parentNode.appendChild( objq );
                            wordWidth = objq.offsetWidth;
                            this.parentNode.removeChild( objq );
                        }
                                               //Влезает ли слово в нашу область
                        if (word.length > 8 && wordWidth >= width)
                        {
                            var tmp = word.substr(2, word.length-4);
                            s += word.substr(0, 2) + tmp.split('').join(c) + word.substr(word.length-2, 2);
                        }
                        else
                            s += word;
                    }
                    this.nodeValue = s;
                }
            });
            if (isIE(6) || ($.browser.opera && $.browser.version >= 10))
            {
                var operaWBR = "<wbr style='display: inline-block'/>";
                this.innerHTML = this.innerHTML.split(c).join(operaWBR);
            }
            $(this).css("overflow", "visible");
        });
        return this;
    };
})(jQuery);

doOnLoad(function(){
    // Определяем последний элемент в меню виджетов
    $(".dijitTab:last").addClass("noBorder");

    //создаем подсветку раскрытого списка личного меню
    $('.personalMenuWithTitleItem .linksList li.hide .greenTitle').addClass('active');
});

function linkMove(obj){
    var urlStr =  window.location.href;
        var isArm = false;
        if (urlStr.indexOf("PhizIA") >= 0)
            isArm = true;

        var row = $(obj).parents("tr:first");
        var table = $(obj).parents("table:first");

        if ($(obj).is(".linkUp"))
        {
            row.insertBefore(row.prev());
        }
        else
        {
            row.insertAfter(row.next());
        }
        if (isArm)
        {// для PhizIA
            table.find('tr .linkUp').css("display", "inline");
            table.find('tr .linkDown').css("display", "inline");
            $(table.find('tr .linkUp')[0]).css("display", "none");
            var trlink = table.find('tr .linkDown');
            $(trlink[trlink.size() - 1]).css("display", "none");

        }
        else
        {// для PhizIC
            table.find('tr .linkUp').css("display", "block");
            table.find('tr .linkDown').css("display", "block");
            table.find('tr:first .linkUp').css("display", "none");
            table.find('tr:last .linkDown').css("display", "none");
        }
}


function changeShowHideLinksListInPersonalMenu(obj)
{
    if (obj != undefined)
    {
        var url = document.webRoot + "/private/async/info/changePersonalMenuFade.do";
        var params ="";
        $(obj).each(function(){
            var object = $('#'+$(this).attr("name"));
            params = (params =="" ? params : (params+"&")) + $(object).attr('id')+"="+$(object).attr('stepShow');
        });

        ajaxQuery(params, url, function(msg){}, null, false);
    }
}

$(document).ready(function() {

    $('.personalMenuWithTitleItem .linksListTitle .greenTitle').live('click', function(event) {
        var accordion_head = $('.personalMenuWithTitleItem .linksListTitle .greenTitle');
        var accordion_body = $('.personalMenuWithTitleItem .linksList .linksListHidden');

        preventDefault(event);
        var object = $('#'+$(this).attr("name"));
        if ($(this).attr('class').indexOf('active') == -1){
            accordion_head.each(function(){
                var obj = $('#'+$(this).attr("name"));
                obj.attr('stepShow', '0');
            });

            object.attr('stepShow', '1');
            changeShowHideLinksListInPersonalMenu(accordion_head);

            accordion_body.slideUp('normal');
            $(this).next().stop(true,true).slideToggle('normal');
            accordion_head.removeClass('active');
            accordion_head.parent().removeClass('hide');
            $(this).addClass('active');
            $(this).parent().addClass('hide');
        }
        else
        {
            object.attr('stepShow', '0');
            changeShowHideLinksListInPersonalMenu(accordion_head);
            accordion_body.slideUp('normal');
            $(this).parent().removeClass('hide');
            accordion_head.removeClass('active');
        }

    })

})

/**
 * Убираем подчёркивание и подсказку для статуса во всплывающем окне подтверждения по смс
 */
function removeStateAttr()
{
    $('#oneTimePasswordWindowWin').find('#state span').removeAttr('onmouseover').removeAttr('onmouseout').removeClass('link').addClass('bold');
}

//Менеджер собатий
var eventManager = {


    //функции, которая вызывается при событии
    eventActions : {},

    //привязываем функцию к событию
    subscribe : function(type, fn) {
        var listeners = this.eventActions[type];
        if(listeners == null) {
           listeners = [];
           this.eventActions[type] = listeners;
        }
        listeners.push(fn);
    },

    //выполнение события
    fire : function(type, eventArgs) {
        var listeners = this.eventActions[type];
        if(listeners != null)
        {
            for(var i = 0; i < listeners.length; i++)
            {
                listeners[i](eventArgs);
            }
        }
    }
};
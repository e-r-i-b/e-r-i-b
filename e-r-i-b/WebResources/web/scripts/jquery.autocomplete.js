jQuery.autocomplete = function(input, options) {
	// Create a link to self
	var me = this;

	// Create jQuery object for input element
	var $input = $(input).attr("autocomplete", "off");

	// Apply inputClass if necessary
	if (options.inputClass) $input.addClass("").addClass(options.inputClass);

    // скрытый серый текст-подсказки во вложенных дивах
    if (options.greyStyle)
    {
        var parent = $input.parent();
        var greyStyleElementIdDiv = options.greyStyleElementId + "Div";
        parent.prepend("<div style='position:relative;float: left;'>"
            + "<div style='position: relative;float: left; background: none repeat scroll 0% 0% transparent;'>"
            +   "<input type='text' class='" + options.greyStyleElementClass + "' id='" + options.greyStyleElementId + "' autocomplete='off'>"
            + "</div>"
            + "<div id='" +  greyStyleElementIdDiv + "' style='position:relative;display:inline;background:transparent'>"
            + "</div>"
            + "</div>");

        $input.appendTo("#" + greyStyleElementIdDiv);    // перемещаем нашу текстовую область в созданный див, чтобы накладывать его поверх серого текста
        getGreyStyleElementId().attr("size", $input.attr("size"));
    }

	// Create results
	var results = document.createElement("div");
	// Create jQuery object for results
	var $results = $(results);

    // Добавляем туда, куда нам необходимо (например, в контейнер с кастом скроллами)
    if (options.append2CustomPlace)
    {
        $results.addClass(options.resultsClass);
        options.append2CustomPlace(results);
    }
	// Add to body element
    else
    {
	    $("body").append(results);
	    $results.hide().addClass(options.resultsClass).css("position", "absolute");
	    if( options.width > 0 ) $results.css("width", options.width);
    }

	input.autocomplete = me;

	var timeout = null;
	var prev = "";
	var active = -1;
	var cache = {};
	var keyb = false;
	var hasFocus = false;
	var lastKeyPressCode = null;

	// flush cache
	function flushCache()
    {
		cache = {};
		cache.data = {};
		cache.length = 0;
	}

	// flush cache
	flushCache();

	// if there is a data array supplied
	if( options.data != null ){
		var sFirstChar = "", stMatchSets = {}, row = [];

		// no url was specified, we need to adjust the cache length to make sure it fits the local data store
		if( typeof options.url != "string" ) options.cacheLength = 1;

		// loop through the array and create a lookup structure
		for( var i=0; i < options.data.length; i++ ){
			// if row is a string, make an array otherwise just reference the array
			row = ((typeof options.data[i] == "string") ? [options.data[i]] : options.data[i]);

			// if the length is zero, don't add to list
			if( row[0].length > 0 ){
				// get the first character
				sFirstChar = row[0].substring(0, 1).toLowerCase();
				// if no lookup array for this character exists, look it up now
				if( !stMatchSets[sFirstChar] ) stMatchSets[sFirstChar] = [];
				// if the match is a string
				stMatchSets[sFirstChar].push(row);
			}
		}

		// add the data items to the cache
		for( var k in stMatchSets ){
			// increase the cache size
			options.cacheLength++;
			// add to the cache
			addToCache(k, stMatchSets[k]);
		}
	}

	$input
	.keydown(function(e) {
		// track last key pressed
		lastKeyPressCode = e.keyCode;
		switch(e.keyCode) {
			case 38: // up
				e.preventDefault();
				moveSelect(-1);
				break;
			case 40: // down
				e.preventDefault();
				//при нажатии на стрелку вниз, отображать список из кеша если есть
                if (!$results.is(":visible"))
                {
                    var q = $input.val();
                    if (!options.matchCase) q = q.toLowerCase();
                    var data = options.cacheLength ? loadFromCache(q) : null;
                    if (data) showResults();
                }
                else moveSelect(1);
				break;
            case 39: // стрелка вправо
                    leftRightKey(1);
                    break;
            case 37:   // стрелка влево
                    leftRightKey(-1);
                    break;
            case 9:  // tab
			case 13: // return
                requestData($input.val(), true);
                e.preventDefault();
				break;
			default:
                startBuildResultsList();
				break;
		}
        hiddenGreyText();
	})
    .keypress(function(e){

            switch(e.keyCode) {
			    case 13: // return, для того, чтобы при выборе списка в опере сразу же не запускалось выполнение фильтра
                     if (navigator.appName == 'Opera' && !options.operaIgnore)
                        return false;
                     break;
                case 39: // стрелка вправо
                    if (navigator.appName == 'Opera')
                    {
                        leftRightKey(1);
                    }
                    break;
                case 37:   // стрелка влево
                    if (navigator.appName == 'Opera')
                    {
                        leftRightKey(-1);
                    }
                    break;
            }
            hiddenGreyText();

    })
  	.focus(function(){
        if (!$results.is(":visible"))
        {
            var q = $input.val();
            if (!options.matchCase) q = q.toLowerCase();
            requestData(q);
            var data = options.cacheLength ? loadFromCache(q) : null;
            if (data) {
                showResults();
            }
        }
		// track whether the field has focus, we shouldn't process any results if the field no longer has focus
		hasFocus = true;
	})
	.blur(function() {
		// track whether the field has focus
		hasFocus = false;
		hideResults();
	});

	hideResultsNow();

	function onChange() {
		// ignore if the following keys are pressed: [del] [shift] [capslock]
		if( lastKeyPressCode == 46 || (lastKeyPressCode > 8 && lastKeyPressCode < 32) ) {
            clearCrayText();

            var hide = $results.hide();
            if (options.customAutoSize)
            {
                options.customAutoSize();
            }

            return hide;
        }
		var v = $input.val();
		if (v == prev) return;
		prev = v;
		if (v.length >= options.minChars) {
			$input.addClass(options.loadingClass);
			requestData(v);
		} else {
			$input.removeClass(options.loadingClass);
            clearCrayText();
            $results.hide();

            if (options.customAutoSize)
            {
                options.customAutoSize();
            }
		}
	}

 	function moveSelect(step) {

		var lis = $("li", results);
		if (!lis) return;

		active += step;

		if (active < 0) {
			active = 0;
		} else if (active >= lis.size()) {
			active = lis.size() - 1;
		}

		lis.removeClass("ac_over");

		$(lis[active]).addClass("ac_over");

        if (options.greyStyle && $results.is(":visible"))   // берем выбранное значение из списка и подставляем его в область с серым текстом
            greyText($input.val(), $(lis[active]).text());
        if (options.moveScroll)
        {
            var lisActive = $(lis[active]);
            if (!lisActive.get(0))
                return;
            var customScrollBox = $results.closest(".customScrollBox");
            var condition;
            if (step > 0)
                condition = lisActive.offset().top + 26 >= customScrollBox.offset().top + customScrollBox.height();
            else
                condition = lisActive.offset().top < customScrollBox.offset().top;
            if (condition)
                options.moveScroll(step);
        }
		// Weird behaviour in IE
		// if (lis[active] && lis[active].scrollIntoView) {
		// 	lis[active].scrollIntoView(false);
		// }

	}

	function selectCurrent() {
		var li = $("li.ac_over", results)[0];
		if (!li) {
			var $li = $("li", results);
			if (options.selectOnly) {
				if ($li.length == 1) li = $li[0];
			} else if (options.selectFirst) {
				li = $li[0];
			}
		}
		if (li) {
			selectItem(li);
			return true;
		} else {
			return false;
		}
	}

	function selectItem(li) {
		if (!li) {
			li = document.createElement("li");
			li.extra = [];
			li.selectValue = "";
		}
		var v = $.trim(li.selectValue ? li.selectValue : li.innerHTML);
		input.lastSelected = v;
		prev = v;
		$results.html("");
		$input.val($("<div/>").html(v).text());
		hideResultsNow();
		if (options.onItemSelect) setTimeout(function() { options.onItemSelect(li) }, 1);
	}

	// selects a portion of the input string
	function createSelection(start, end){
		// get a reference to the input element
		var field = $input.get(0);
		if( field.createTextRange ){
			var selRange = field.createTextRange();
			selRange.collapse(true);
			selRange.moveStart("character", start);
			selRange.moveEnd("character", end);
			selRange.select();
		} else if( field.setSelectionRange ){
			field.setSelectionRange(start, end);
		} else {
			if( field.selectionStart ){
				field.selectionStart = start;
				field.selectionEnd = end;
			}
		}
		field.focus();
	}

	// fills in the input box w/the first match (assumed to be the best match)
	function autoFill(sValue){
		// if the last user key pressed was backspace, don't autofill
		if( lastKeyPressCode != 8 ){
            if (options.greyStyle)
               greyText($input.val(), sValue);
            else
			    // fill in the value (keep the case the user has typed)
			    $input.val($input.val() + sValue.substring(prev.length));
			// select the portion of the value not typed by the user (so the next character will erase)
			if(options.selectFill)   createSelection(prev.length, sValue.length);
		}
	}

    function showResults()
    {
        if (options.customAutoSize)
        {
            $results.show();
            return;
        }

        // get the position of the input field right now (in case the DOM is shifted)
        var pos = findPos(input);
        // either use the specified width, or autocalculate based on form element
        var iWidth = (options.width > 0) ? options.width : $input.width();
        // reposition
        $results.css({
            width: parseInt(iWidth) + "px",
            top: (pos.y + input.offsetHeight) + "px",
            left: pos.x + "px"
        }).show();
    }

	function hideResults()
    {
        if (timeout)
        {
            clearTimeout(timeout);
        }

        timeout = setTimeout(hideResultsNow, 200);
	}

	function hideResultsNow() {
		if (timeout)
        {
            clearTimeout(timeout);
        }

		$input.removeClass(options.loadingClass);

        if ($results.is(":visible"))
        {
			$results.hide();
            clearCrayText();

            if (options.customAutoSize)
            {
                options.customAutoSize();
            }
		}
		if (options.mustMatch)
        {
			var v = $input.val();
			if (v != input.lastSelected)
            {
				selectItem(null);
			}
		}
	}

	function receiveData(q, data) {
		if (data) {
			$input.removeClass(options.loadingClass);
			results.innerHTML = "";

			// if the field no longer has focus or if there are no matches, do not display the drop down
			if( data.length == 0 )
            {
                return hideResultsNow();
            }

			// we put a styled iframe behind the calendar so HTML SELECT elements don't show through
			$results.bgIframe();

			results.appendChild(dataToDom(data));
			// autofill in the complete box w/the first match as long as the user hasn't entered in more data
			if( options.autoFill && ($input.val().toLowerCase() == q.toLowerCase()) )
            {
                autoFill(data[0][0]);
            }

			showResults();

            if (options.customAutoSize)
            {
                options.customAutoSize();
            }
		}
        else
        {
			hideResultsNow();
		}
	}

	function parseData(data, query) {
		if (!data)
        {
            return null;
        }

		var parsed = [];
		var rows = data.split(options.lineSeparator);

		for (var i=0; i < rows.length; i++)
        {
			var row = $.trim(rows[i]);
			if (row)
            {
				parsed[parsed.length] = row.split(options.cellSeparator);
			}
		}

        if (options.parseData)
        {
            return options.parseData(parsed, query);
        }

		return parsed;
	}

	function dataToDom(data) {
		var ul = document.createElement("ul");
		var num = data.length;

		// limited results to a max number
		if( (options.maxItemsToShow > 0) && (options.maxItemsToShow < num) ) num = options.maxItemsToShow;

		for (var i=0; i < num; i++) {
			var row = data[i];
			if (!row) continue;
			var li = document.createElement("li");
			if (options.formatItem) {
				li.innerHTML = options.formatItem(row, i, num);
				li.selectValue = row[0];
			} else {
				li.innerHTML = row[0];
				li.selectValue = row[0];
			}
			var extra = null;
			if (row.length > 1) {
				extra = [];
				for (var j=1; j < row.length; j++) {
					extra[extra.length] = row[j];
				}
			}
			li.extra = extra;
			ul.appendChild(li);
			$(li).hover(
				function() { $("li", ul).removeClass("ac_over"); $(this).addClass("ac_over"); active = $("li", ul).indexOf($(this).get(0)); },
				function() { $(this).removeClass("ac_over"); }
			).click(function(e) { e.preventDefault(); e.stopPropagation(); selectItem(this) });
		}
		return ul;
	}

	function requestData(q, hasSelectedItem) {
		if (!options.matchCase) q = q.toLowerCase();
		var data = options.cacheLength ? loadFromCache(q) : null;
		// recieve the cached data
		if (data) {
            if (hasSelectedItem)
            {
                if( selectCurrent() ){
                    // make sure to blur off the current field
                    $input.blur();
                }
            }
            receiveData(q, data);
		// if an AJAX url has been supplied, try loading the data now
		} else if( (typeof options.url == "string") && (options.url.length > 0) ){
			ajaxQuery(options.extraParams, makeUrl(q), function(data) {
				data = parseData(data, q);
				addToCache(q, data);
                if (hasSelectedItem)
                {
                    if( selectCurrent() ){
                        // make sure to blur off the current field
                        $input.blur();
                    }
                }
				receiveData(q, data);
			}, null, false, null, true);//не показывать прелоудер при ajax-запросе
		// if there's been no data found, remove the loading class
		} else {
			$input.removeClass(options.loadingClass);
		}
	}

	function makeUrl(q)
    {
		return options.url + "?field(q)=" + encodeURI(q);
	}

	function loadFromCache(q) {
		if (!q) return null;


		if (cache.data[q])
        {
            return cache.data[q];
        }

        /*
         * Если есть переопределенный метод поиска в кэше вызываем его.
         * Поиск по-умолчанию очень простой - лучше определить свой.
         */
        if (options.loadFromCache)
        {
            return options.loadFromCache(q);
        }

		if (options.matchSubset)
        {
			for (var i = q.length - 1; i >= options.minChars; i--)
            {
				var qs = q.substr(0, i);
				var c = cache.data[qs];
				if (c) {
					var csub = [];
					for (var j = 0; j < c.length; j++) {
						var x = c[j];
						if (matchSubset(x.join(), q)) {
							csub[csub.length] = x;
						}
					}
					return csub.length > 0 ? csub : null;
				}
			}
		}
		return null;
	}

	function matchSubset(s, sub) {
		if (!options.matchCase) s = s.toLowerCase();
		var i = s.indexOf(sub);
		if (i == -1) return false;
		return i == 0 || options.matchContains;
	}

	this.flushCache = function() {
		flushCache();
	};

	this.setExtraParams = function(p) {
		options.extraParams = p;
	};

	this.findValue = function(){
		var q = $input.val();

		if (!options.matchCase) q = q.toLowerCase();
		var data = options.cacheLength ? loadFromCache(q) : null;
		if (data) {
			findValueCallback(q, data);
		} else if( (typeof options.url == "string") && (options.url.length > 0) ){
			ajaxQuery(options.extraParams, makeUrl(q), function(data) {
				data = parseData(data, q);
				addToCache(q, data);
				findValueCallback(q, data);
			}, null, false, null, true);//не показывать прелоудер при ajax-запросе
		} else {
			// no matches
			findValueCallback(q, null);
		}
	};

	function findValueCallback(q, data){
		if (data) $input.removeClass(options.loadingClass);

		var num = (data) ? data.length : 0;
		var li = null;

		for (var i=0; i < num; i++) {
			var row = data[i];

			if( row[0].toLowerCase() == q.toLowerCase() ){
				li = document.createElement("li");
				if (options.formatItem) {
					li.innerHTML = options.formatItem(row, i, num);
					li.selectValue = row[0];
				} else {
					li.innerHTML = row[0];
					li.selectValue = row[0];
				}
				var extra = null;
				if( row.length > 1 ){
					extra = [];
					for (var j=1; j < row.length; j++) {
						extra[extra.length] = row[j];
					}
				}
				li.extra = extra;
			}
		}

		if( options.onFindValue ) setTimeout(function() { options.onFindValue(li) }, 1);
	}

	function addToCache(q, data) {
		if (!data || !q || !options.cacheLength) return;

        if (options.addToCache)
        {
            return options.addToCache(q, data);
        }

		if (!cache.length || cache.length > options.cacheLength) {
			flushCache();
			cache.length++;
		} else if (!cache[q]) {
			cache.length++;
		}
		cache.data[q] = data;
	}

	function findPos(obj) {
		var curleft = obj.offsetLeft || 0;
		var curtop = obj.offsetTop || 0;
		while (obj = obj.offsetParent) {
			curleft += obj.offsetLeft;
			curtop += obj.offsetTop;
		}
		return {x:curleft,y:curtop};
	}

    // очистка текста-подсказки в другом цвете
    function clearCrayText() {
        if (options.greyStyle)
            getGreyStyleElementId().val("");
    }

    // вывод серого текста-подсказки в соответствии введенного черного (с учетом его регистра),
    // без этого будет видно  несоответсвие наложения букв в разном регистре
    function greyText(blackText, greyText) {
        getGreyStyleElementId().val(blackText + greyText.substring(blackText.length));
    }

    // проверка соответствия черного и серого текстов
    function compareBlackGreyTexts()
    {
        if (options.greyStyle && getGreyStyleElementId().val().toLowerCase().match("^" + escape($input.val().toLowerCase())))
            return true;
        return false

    }

    // добавляем countSymbols элементов серого цвета к черному
    function stepShiftBlackText(countSymbols)
    {
        if (!options.greyStyle || getGreyStyleElementId().val().length == 0)
            return;

        $results.hide();
        if (options.customAutoSize)
        {
            options.customAutoSize();
        }

        var length = $input.val().length;
        var chars = getGreyStyleElementId().val().substring(0, length + countSymbols);
        $input.val(chars);

        if (chars.length == 0)
            clearCrayText();
    }

    // обработка клавиш влево вправо для серой подложки
    function leftRightKey(count)
    {
        if (options.greyStyle)
        {
            stepShiftBlackText(count);
            startBuildResultsList();
        }
    }

    function startBuildResultsList()
    {
		active = -1;
        if (!compareBlackGreyTexts())
            clearCrayText();
		if (timeout) clearTimeout(timeout);
		timeout = setTimeout(function(){onChange();}, options.delay);
        if (options.greyStyle && $results.is(":visible"))
            greyText($input.val(), getGreyStyleElementId().val());
    }

    function getGreyStyleElementId()
    {
        return $("#" + options.greyStyleElementId + '');
    }

    function escape(inputString )
    {
        var specials = new RegExp("[.*+?|()\\[\\]{}\\\\]", "g");
        return inputString.replace(specials, "\\$&");

    }

    // скрываем подложку, если строка ввода не умещается на экране
    function hiddenGreyText()
    {
        if (!options.greyStyle)
            return;

        if ($input.val().length >= ($input.attr("size")))
        {
            $input.css("background-color", getGreyStyleElementId().css("background-color"));
            getGreyStyleElementId().hide();

        }
        else
        {
            getGreyStyleElementId().show();
            $input.css("background-color", "transparent");
        }
    }

	// make it so that no error is thrown if bgIframe plugin isn't included (allows you to use conditional
	// comments to only include bgIframe where it is needed in IE without breaking this plugin).
	if ($.fn.bgIframe == undefined) {
		$.fn.bgIframe = function() {return this; };
	}
};

jQuery.fn.autocomplete = function(url, options, data) {
	// Make sure options exists
	options = options || {};
	// Set url as option
	options.url = url;
	// set some bulk local data
	options.data = ((typeof data == "object") && (data.constructor == Array)) ? data : null;

	// Set default values for required options
	options.inputClass = options.inputClass || "ac_input";
	options.resultsClass = options.resultsClass || "ac_results";
	options.lineSeparator = options.lineSeparator || "\n";
	options.cellSeparator = options.cellSeparator || "|";
	options.minChars = options.minChars || 1;
	options.delay = options.delay || 400;
	options.matchCase = options.matchCase || 0;
	options.matchSubset = options.matchSubset || 1;
	options.matchContains = options.matchContains || 0;
	options.cacheLength = options.cacheLength || 1;
	options.mustMatch = options.mustMatch || 0;
	options.extraParams = options.extraParams || "";
	options.loadingClass = options.loadingClass || "ac_loading";
	options.selectFirst = options.selectFirst || false;
	options.selectOnly = options.selectOnly || false;
	options.maxItemsToShow = options.maxItemsToShow || -1;
	options.autoFill = options.autoFill || false;
	options.width = parseInt(options.width, 10) || 0;
    options.selectFill = options.selectFill || false;  // выделять подставляемый текст
    options.greyStyle = options.greyStyle || false; // автозаполнение серым цветом
    options.greyStyleElementId = options.greyStyleElementId || "grey"; // автозаполнение серым цветом
    options.greyStyleElementClass = options.greyStyleElementClass || "ac_grey"; // автозаполнение серым цветом
    options.operaIgnore = options.operaIgnore || false;

    options.customAutoSize     = options.customAutoSize     || false;
    options.append2CustomPlace = options.append2CustomPlace || false;
    options.addToCache         = options.addToCache         || false;
    options.parseData          = options.parseData          || false;
    options.loadFromCache      = options.loadFromCache      || false;

	this.each(function() {
		var input = this;
		new jQuery.autocomplete(input, options);
	});

	// Don't break the chain
	return this;
};

jQuery.fn.autocompleteArray = function(data, options) {
	return this.autocomplete(null, options, data);
};

jQuery.fn.indexOf = function(e){
	for( var i=0; i<this.length; i++ ){
		if( this[i] == e ) return i;
	}
	return -1;
};

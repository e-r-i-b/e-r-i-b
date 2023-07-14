/*
 * Date prototype extensions. Doesn't depend on any
 * other code. Doens't overwrite existing methods.
 *
 * Adds dayNames, abbrDayNames, monthNames and abbrMonthNames static properties and isLeapYear,
 * isWeekend, isWeekDay, getDaysInMonth, getDayName, getMonthName, getDayOfYear, getWeekOfYear,
 * setDayOfYear, addYears, addMonths, addDays, addHours, addMinutes, addSeconds methods
 *
 * Copyright (c) 2006 J?rn Zaefferer and Brandon Aaron (brandon.aaron@gmail.com || http://brandonaaron.net)
 *
 * Additional methods and properties added by Kelvin Luck: firstDayOfWeek, dateFormat, zeroTime, asString, fromString -
 * I've added my name to these methods so you know who to blame if they are broken!
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 */

/**
 * An Array of day names starting with Sunday.
 *
 * @example dayNames[0]
 * @result 'Sunday'
 *
 * @name dayNames
 * @type Array
 * @cat Plugins/Methods/Date
 */
Date.dayNames = ['Воскресенье', 'Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота'];

/**
 * An Array of abbreviated day names starting with Sun.
 *
 * @example abbrDayNames[0]
 * @result 'Sun'
 *
 * @name abbrDayNames
 * @type Array
 * @cat Plugins/Methods/Date
 */
Date.abbrDayNames = ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'];

/**
 * An Array of month names starting with Janurary.
 *
 * @example monthNames[0]
 * @result 'January'
 *
 * @name monthNames
 * @type Array
 * @cat Plugins/Methods/Date
 */
Date.monthNames = ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'];

/**
 * An Array of abbreviated month names starting with Jan.
 *
 * @example abbrMonthNames[0]
 * @result 'Jan'
 *
 * @name monthNames
 * @type Array
 * @cat Plugins/Methods/Date
 */
Date.abbrMonthNames = ['Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек'];

/**
 * Массив месяцев, содержащихся в квартале.
 *
 * @example monthsOfQuarters[1]
 * @result [0, 1, 2]
 *
 * @name monthsOfQuarters
 * @type Array
 * @cat Plugins/Methods/Date
 */
Date.monthsOfQuarters = [[-1],
                         [0, 1, 2],
                         [3, 4, 5],
                         [6, 7, 8],
                         [9,10,11]];

/**
 * The first day of the week for this locale.
 *
 * @name firstDayOfWeek
 * @type Number
 * @cat Plugins/Methods/Date
 * @author Kelvin Luck
 */
Date.firstDayOfWeek = 1;

/**
 * The format that string dates should be represented as (e.g. 'dd/mm/yyyy' for UK, 'mm/dd/yyyy' for US, 'yyyy-mm-dd' for Unicode etc).
 *
 * @name format
 * @type String
 * @cat Plugins/Methods/Date
 * @author Kelvin Luck
 */
Date.format = 'dd/mm/yyyy';
//Date.format = 'mm/dd/yyyy';
//Date.format = 'yyyy-mm-dd';
//Date.format = 'dd mmm yy';

/**
 * The first two numbers in the century to be used when decoding a two digit year. Since a two digit year is ambiguous (and date.setYear
 * only works with numbers < 99 and so doesn't allow you to set years after 2000) we need to use this to disambiguate the two digit year codes.
 *
 * @name format
 * @type String
 * @cat Plugins/Methods/Date
 * @author Kelvin Luck
 */
Date.fullYearStart = '20';

/**
 * на скольо месяцев заправшивать данные за один ajax запрос
 * @type {number}
 */
Date.numUpdateMonth = 3;

(function() {

	/**
	 * Adds a given method under the given name
	 * to the Date prototype if it doesn't
	 * currently exist.
	 *
	 * @private
	 */
	function add(name, method) {
		if( !Date.prototype[name] ) {
			Date.prototype[name] = method;
		}
	};

	/**
	 * Checks if the year is a leap year.
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.isLeapYear();
	 * @result true
	 *
	 * @name isLeapYear
	 * @type Boolean
	 * @cat Plugins/Methods/Date
	 */
	add("isLeapYear", function() {
		var y = this.getFullYear();
		return (y%4==0 && y%100!=0) || y%400==0;
	});

	/**
	 * Checks if the day is a weekend day (Sat or Sun).
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.isWeekend();
	 * @result false
	 *
	 * @name isWeekend
	 * @type Boolean
	 * @cat Plugins/Methods/Date
	 */
	add("isWeekend", function() {
		return this.getDay()==0 || this.getDay()==6;
	});

	/**
	 * Check if the day is a day of the week (Mon-Fri)
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.isWeekDay();
	 * @result false
	 *
	 * @name isWeekDay
	 * @type Boolean
	 * @cat Plugins/Methods/Date
	 */
	add("isWeekDay", function() {
		return !this.isWeekend();
	});

	/**
	 * Gets the number of days in the month.
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.getDaysInMonth();
	 * @result 31
	 *
	 * @name getDaysInMonth
	 * @type Number
	 * @cat Plugins/Methods/Date
	 */
	add("getDaysInMonth", function() {
		return [31,(this.isLeapYear() ? 29:28),31,30,31,30,31,31,30,31,30,31][this.getMonth()];
	});

	/**
	 * Gets the name of the day.
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.getDayName();
	 * @result 'Saturday'
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.getDayName(true);
	 * @result 'Sat'
	 *
	 * @param abbreviated Boolean When set to true the name will be abbreviated.
	 * @name getDayName
	 * @type String
	 * @cat Plugins/Methods/Date
	 */
	add("getDayName", function(abbreviated) {
		return abbreviated ? Date.abbrDayNames[this.getDay()] : Date.dayNames[this.getDay()];
	});

	/**
	 * Gets the name of the month.
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.getMonthName();
	 * @result 'Janurary'
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.getMonthName(true);
	 * @result 'Jan'
	 *
	 * @param abbreviated Boolean When set to true the name will be abbreviated.
	 * @name getDayName
	 * @type String
	 * @cat Plugins/Methods/Date
	 */
	add("getMonthName", function(abbreviated) {
		return abbreviated ? Date.abbrMonthNames[this.getMonth()] : Date.monthNames[this.getMonth()];
	});

    /**
     * Возврашает номер квартала в году (1..4).
     *
     * @example var dtm = new Date("01/12/2008");
     * dtm.getQuarter();
     * @result 1
     *
     * @name getQuarter
     * @type Number
     * @cat Plugins/Methods/Date
     */
    add("getQuarter", function() {
        return Math.floor(this.getMonth() / 3) + 1;
    });

    /**
     * Возврашает номер первого месяца квартала(1..4).
     *
     * @example var dtm = new Date("12/12/2008");
     * dtm.getFirstMonthOfQuarter();
     * @result 9
     *
     * @name getFirstMonthOfQuarter
     * @type Number
     * @cat Plugins/Methods/Date
     */
    add("getFirstMonthOfQuarter", function(q) {
        var q = q || this.getQuarter();
        return Number(Date.monthsOfQuarters[q][0]);
    });

	/**
	 * Get the number of the day of the year.
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.getDayOfYear();
	 * @result 11
	 *
	 * @name getDayOfYear
	 * @type Number
	 * @cat Plugins/Methods/Date
	 */
	add("getDayOfYear", function() {
		var tmpdtm = new Date("1/1/" + this.getFullYear());
		return Math.floor((this.getTime() - tmpdtm.getTime()) / 86400000);
	});

	/**
	 * Get the number of the week of the year.
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.getWeekOfYear();
	 * @result 2
	 *
	 * @name getWeekOfYear
	 * @type Number
	 * @cat Plugins/Methods/Date
	 */
	add("getWeekOfYear", function() {
		return Math.ceil(this.getDayOfYear() / 7);
	});

	/**
	 * Set the day of the year.
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.setDayOfYear(1);
	 * dtm.toString();
	 * @result 'Tue Jan 01 2008 00:00:00'
	 *
	 * @name setDayOfYear
	 * @type Date
	 * @cat Plugins/Methods/Date
	 */
	add("setDayOfYear", function(day) {
		this.setMonth(0);
		this.setDate(day);
		return this;
	});

	/**
	 * Add a number of years to the date object.
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.addYears(1);
	 * dtm.toString();
	 * @result 'Mon Jan 12 2009 00:00:00'
	 *
	 * @name addYears
	 * @type Date
	 * @cat Plugins/Methods/Date
	 */
	add("addYears", function(num) {
		this.setFullYear(this.getFullYear() + num);
		return this;
	});

	/**
	 * Add a number of months to the date object.
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.addMonths(1);
	 * dtm.toString();
	 * @result 'Tue Feb 12 2008 00:00:00'
	 *
	 * @name addMonths
	 * @type Date
	 * @cat Plugins/Methods/Date
	 */
	add("addMonths", function(num) {
		var tmpdtm = this.getDate();

		this.setMonth(this.getMonth() + num);

		if (tmpdtm > this.getDate())
			this.addDays(-this.getDate());

		return this;
	});

	/**
	 * Add a number of days to the date object.
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.addDays(1);
	 * dtm.toString();
	 * @result 'Sun Jan 13 2008 00:00:00'
	 *
	 * @name addDays
	 * @type Date
	 * @cat Plugins/Methods/Date
	 */
	add("addDays", function(num) {
		//this.setDate(this.getDate() + num);
		this.setTime(this.getTime() + (num*86400000) );
		return this;
	});

	/**
	 * Add a number of hours to the date object.
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.addHours(24);
	 * dtm.toString();
	 * @result 'Sun Jan 13 2008 00:00:00'
	 *
	 * @name addHours
	 * @type Date
	 * @cat Plugins/Methods/Date
	 */
	add("addHours", function(num) {
		this.setHours(this.getHours() + num);
		return this;
	});

	/**
	 * Add a number of minutes to the date object.
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.addMinutes(60);
	 * dtm.toString();
	 * @result 'Sat Jan 12 2008 01:00:00'
	 *
	 * @name addMinutes
	 * @type Date
	 * @cat Plugins/Methods/Date
	 */
	add("addMinutes", function(num) {
		this.setMinutes(this.getMinutes() + num);
		return this;
	});

	/**
	 * Add a number of seconds to the date object.
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.addSeconds(60);
	 * dtm.toString();
	 * @result 'Sat Jan 12 2008 00:01:00'
	 *
	 * @name addSeconds
	 * @type Date
	 * @cat Plugins/Methods/Date
	 */
	add("addSeconds", function(num) {
		this.setSeconds(this.getSeconds() + num);
		return this;
	});

	/**
	 * Sets the time component of this Date to zero for cleaner, easier comparison of dates where time is not relevant.
	 *
	 * @example var dtm = new Date();
	 * dtm.zeroTime();
	 * dtm.toString();
	 * @result 'Sat Jan 12 2008 00:01:00'
	 *
	 * @name zeroTime
	 * @type Date
	 * @cat Plugins/Methods/Date
	 * @author Kelvin Luck
	 */
	add("zeroTime", function() {
		this.setMilliseconds(0);
		this.setSeconds(0);
		this.setMinutes(0);
		this.setHours(0);
		return this;
	});

	/**
	 * Returns a string representation of the date object according to Date.format.
	 * (Date.toString may be used in other places so I purposefully didn't overwrite it)
	 *
	 * @example var dtm = new Date("01/12/2008");
	 * dtm.asString();
	 * @result '12/01/2008' // (where Date.format == 'dd/mm/yyyy'
	 *
	 * @name asString
	 * @type Date
	 * @cat Plugins/Methods/Date
	 * @author Kelvin Luck
	 */
	add("asString", function(format) {
		var r = format || Date.format;
		return r
			.split('yyyy').join(this.getFullYear())
			.split('yy').join((this.getFullYear() + '').substring(2))
            .split('q').join(this.getQuarter())
			.split('mmmm').join(this.getMonthName(false))
			.split('mmm').join(this.getMonthName(true))
			.split('mm').join(_zeroPad(this.getMonth()+1))
			.split('dd').join(_zeroPad(this.getDate()))
			.split('hh').join(_zeroPad(this.getHours()))
			.split('min').join(_zeroPad(this.getMinutes()))
			.split('ss').join(_zeroPad(this.getSeconds()));
	});

	/**
	 * Returns a new date object created from the passed String according to Date.format or false if the attempt to do this results in an invalid date object
	 * (We can't simple use Date.parse as it's not aware of locale and I chose not to overwrite it incase it's functionality is being relied on elsewhere)
	 *
	 * @example var dtm = Date.fromString("12/01/2008");
	 * dtm.toString();
	 * @result 'Sat Jan 12 2008 00:00:00' // (where Date.format == 'dd/mm/yyyy'
	 *
	 * @name fromString
	 * @type Date
	 * @cat Plugins/Methods/Date
	 * @author Kelvin Luck
	 */
	Date.fromString = function(s, format)
	{
        if(s==null) return;
		var f = format || Date.format;
		var d = new Date('01/01/1977');
        var dayValue = 1;
        var monthValue = 0;
        var yearValue = 1997;

		var mLength = 0;

		var iM = f.indexOf('mmmm');
		if (iM > -1) {
			for (var i=0; i<Date.monthNames.length; i++) {
				var mStr = s.substr(iM, Date.monthNames[i].length);
				if (Date.monthNames[i] == mStr) {
					mLength = Date.monthNames[i].length - 4;
					break;
				}
			}
            monthValue = i;
		} else {
			iM = f.indexOf('mmm');
			if (iM > -1) {
				var mStr = s.substr(iM, 3);
				for (var i=0; i<Date.abbrMonthNames.length; i++) {
					if (Date.abbrMonthNames[i] == mStr) break;
				}
                monthValue = i;
			} else {
                var monthNum = Number(s.substr(f.indexOf('mm'), 2));
                if(monthNum < 1) monthNum = 1;
                else if (monthNum > 12) monthNum = 12;

                monthValue = monthNum - 1;
			}
		}

		var iY = f.indexOf('yyyy');

		if (iY > -1) {
			if (iM < iY)
			{
				iY += mLength;
			}
            var year = Number(s.substr(iY, 4));
            if(year < 1) year = 1;
            yearValue = year;
        } else {
			if (iM < iY)
			{
				iY += mLength;
			}
			// TODO - this doesn't work very well - are there any rules for what is meant by a two digit year?
            yearValue = Number(Date.fullYearStart + s.substr(f.indexOf('yy'), 2));
		}
		var iD = f.indexOf('dd');
		if (iM < iD)
		{
			iD += mLength;
		}
        var date = Number(s.substr(iD, 2));
        if (date < 1) date = 1;
        var maxDayInMonth = d.getDaysInMonth();
        dayValue = (date > maxDayInMonth) ? maxDayInMonth : date;
		if (isNaN(d.getTime())) {
			return false;
		}
		return new Date(yearValue, monthValue, dayValue, 12, 0, 0, 0);
	};

	// utility method
	var _zeroPad = function(num) {
		var s = '0'+num;
		return s.substring(s.length-2)
		//return ('0'+num).substring(-2); // doesn't work on IE :(
	};

})();

/**
 * Copyright (c) 2008 Kelvin Luck (http://www.kelvinluck.com/)
 * Dual licensed under the MIT (http://www.opensource.org/licenses/mit-license.php) 
 * and GPL (http://www.opensource.org/licenses/gpl-license.php) licenses.
 * .
 *
 *
 * $Id: jquery.datePicker.js 100 2010-08-09 10:45:28Z softlab adapted version $
 **/

(function($){
    
	$.fn.extend({
/**
 * Render a calendar table into any matched elements.
 * 
 * @param Object s (optional) Customize your calendars.
 * @option Number month The month to render (NOTE that months are zero based). Default is today's month.
 * @option Number year The year to render. Default is today's year.
 * @option Function renderCallback A reference to a function that is called as each cell is rendered and which can add classes and event listeners to the created nodes. Default is no callback.
 * @option Number showHeader Whether or not to show the header row, possible values are: $.dpConst.SHOW_HEADER_NONE (no header), $.dpConst.SHOW_HEADER_SHORT (first letter of each day) and $.dpConst.SHOW_HEADER_LONG (full name of each day). Default is $.dpConst.SHOW_HEADER_SHORT.
 * @option String hoverClass The class to attach to each cell when you hover over it (to allow you to use hover effects in IE6 which doesn't support the :hover pseudo-class on elements other than links). Default is dp-hover. Pass false if you don't want a hover class.
 * @type jQuery
 * @name renderCalendar
 * @cat plugins/datePicker
 * @author Kelvin Luck (http://www.kelvinluck.com/)
 *
 * @example $('#calendar-me').renderCalendar({month:0, year:2007});
 * @desc Renders a calendar displaying January 2007 into the element with an id of calendar-me.
 *
 * @example
 * var testCallback = function($td, thisDate, month, year)
 * {
 * if ($td.is('.current-month') && thisDate.getDay() == 4) {
 *		var d = thisDate.getDate();
 *		$td.bind(
 *			'click',
 *			function()
 *			{
 *				alert('You clicked on ' + d + '/' + (Number(month)+1) + '/' + year);
 *			}
 *		).addClass('thursday');
 *	} else if (thisDate.getDay() == 5) {
 *		$td.html('Friday the ' + $td.html() + 'th');
 *	}
 * }
 * $('#calendar-me').renderCalendar({month:0, year:2007, renderCallback:testCallback});
 * 
 * @desc Renders a calendar displaying January 2007 into the element with an id of calendar-me. Every Thursday in the current month has a class of "thursday" applied to it, is clickable and shows an alert when clicked. Every Friday on the calendar has the number inside replaced with text.
 **/
		renderCalendar  :   function(s)
		{
			var dc = function(a)
			{
				return document.createElement(a);
			};

			s = $.extend({}, $.fn.datePicker.defaults, s);

            var calendarTable = function(year, month, today)
            {
                if (s.showHeader != $.dpConst.SHOW_HEADER_NONE) {
                    var headRow = $(dc('tr'));
                    for (var i=Date.firstDayOfWeek; i<Date.firstDayOfWeek+7; i++) {
                        var weekday = i%7;
                        var day = Date.dayNames[weekday];
                        var shortDay = Date.abbrDayNames[weekday];
                        headRow.append(
                                jQuery(dc('th')).attr({'scope':'col', 'abbr':day, 'title':day, 'class':(weekday == 0 || weekday == 6 ? 'weekend' : 'weekday')}).html(s.showHeader == $.dpConst.SHOW_HEADER_SHORT ? shortDay : day)
                        );
                    }
                };

                var cTable = $(dc('table'))
                        .attr(
                            {
                                'cellspacing':0,
                                'cellpadding':0
                            }
                        )
                        .addClass('jCalendar')
                        .append(
                                (s.showHeader != $.dpConst.SHOW_HEADER_NONE ?
                                        $(dc('thead'))
                                                .append(headRow)
                                        :
                                        dc('thead')
                                        )
                        );
                var tbody = $(dc('tbody'));

                var currentDate = (new Date(year, month, 1, 12, 0, 0));


                var firstDayOffset = Date.firstDayOfWeek - currentDate.getDay() + 1;
                if (firstDayOffset > 1) firstDayOffset -= 7;
                var weeksToDraw = Math.ceil(( (-1*firstDayOffset+1) + currentDate.getDaysInMonth() ) /7);
                currentDate.addDays(firstDayOffset-1);

                var doHover = function(firstDayInBounds)
                {
                    return function()
                    {
                        if (s.hoverClass) {
                            var $this = $(this);
                            if (!s.selectWeek) {
                                $this.addClass(s.hoverClass);
                            } else if (firstDayInBounds && !$this.is('.disabled')) {
                                $this.parent().addClass('activeWeekHover');
                            }
                        }
                    }
                };
                var unHover = function()
                {
                    if (s.hoverClass) {
                        var $this = $(this);
                        $this.removeClass(s.hoverClass);
                        $this.parent().removeClass('activeWeekHover');
                    }
                };

                var w = 0;
                while (w++<weeksToDraw) {
                    var r = jQuery(dc('tr'));
                    var firstDayInBounds = s.dpController ? currentDate > s.dpController.startDate : false;
                    for (var i=0; i<7; i++) {
                        var thisMonth = currentDate.getMonth() == month;

                        var d = $(dc('td'));
                        if (w == weeksToDraw ) d.addClass("last-week");
                        if (thisMonth || s.showOtherMonth) d
                                .text(currentDate.getDate() + '')
                                .addClass((thisMonth ? 'current-month ' : 'other-month ') +
                                        (currentDate.isWeekend() ? 'weekend ' : 'weekday ') +
                                        (thisMonth && currentDate.getTime() == today.getTime() ? 'today ' : '')
                                ).wrapInner('<span></span>')
                                .data('datePickerDate', currentDate.asString(s.dateFormat == undefined ? null : s.dateFormat))
                                .hover(doHover(firstDayInBounds), unHover)
                        ;
                        r.append(d);
                        if (s.renderCallback) {
                            s.renderCallback(d, currentDate, month, year);
                        }
                        // addDays(1) fails in some locales due to daylight savings. See issue 39.
                        //currentDate.addDays(1);
                        // set the time to midday to avoid any weird timezone issues??
                        currentDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate()+1, 12, 0, 0);
                    }
                    tbody.append(r);
                }
                cTable.append(tbody);
                return cTable;
            }
            if (s.showQuarter)
            {
                var today = (new Date()).zeroTime();
                today.setHours(12);
                var quarter = s.quarter == undefined ? today.getQuarter() : s.quarter;
                var months = Date.monthsOfQuarters[quarter];
                var year = s.year || today.getFullYear();

                //строка с названиями месяцев квартала
                var headRowQuarter = $(dc('tr'));
                for (var i=0; i<months.length; i++) {
                    var mon = months[i];
                    var nameMonth = Date.monthNames[mon];
                    headRowQuarter.append(jQuery(dc('th')).attr({'scope':'col', 'class': 'nameMonth'}).html(nameMonth));
                }
                //таблица с тремя месяцами
                var quarterTable = $(dc('table'))
                        .attr(
                            {
                                'cellspacing':0,
                                'cellpadding':0
                            }
                        )
                        .addClass('jQuarter')
                        .append($(dc('thead')).append(headRowQuarter)
                        );
                var quarterBody = $(dc('tbody'));
                var threeMonths = jQuery(dc('tr'));
                for (var i=0; i<months.length; i++) {
                    var mon = months[i];
                    var tdMonth = jQuery(dc('td')).attr({'class': 'quarterRow', 'month' : mon, 'year' : year});   //один месяц
                    tdMonth.append(calendarTable(year, mon, today));
                    threeMonths.append(tdMonth);
                }
                quarterBody.append(threeMonths);
                quarterTable.append(quarterBody);

                return this.each(
                        function()
                        {
                            $(this).empty().append(quarterTable);
                        }
                );
            }
            else
            {
                var today = (new Date()).zeroTime();
                today.setHours(12);
                var month = s.month == undefined ? today.getMonth() : s.month;
                var year = s.year || today.getFullYear();

                return this.each(
                    function()
                    {
                        $(this).empty().append(calendarTable(year, month, today));
                    }
                );
            }
		},
/**
 * Create a datePicker associated with each of the matched elements.
 *
 * The matched element will receive a few custom events with the following signatures:
 *
 * dateSelected(event, date, $td, status)
 * Triggered when a date is selected. event is a reference to the event, date is the Date selected, $td is a jquery object wrapped around the TD that was clicked on and status is whether the date was selected (true) or deselected (false)
 * 
 * dpClosed(event, selected)
 * Triggered when the date picker is closed. event is a reference to the event and selected is an Array containing Date objects.
 *
 * dpMonthChanged(event, displayedMonth, displayedYear)
 * Triggered when the month of the popped up calendar is changed. event is a reference to the event, displayedMonth is the number of the month now displayed (zero based) and displayedYear is the year of the month.
 *
 * dpDisplayed(event, $datePickerDiv)
 * Triggered when the date picker is created. $datePickerDiv is the div containing the date picker. Use this event to add custom content/ listeners to the popped up date picker.
 *
 * @param Object s (optional) Customize your date pickers.
 * @option Number month The month to render when the date picker is opened (NOTE that months are zero based). Default is today's month.
 * @option Number year The year to render when the date picker is opened. Default is today's year.
 * @option String startDate The first date date can be selected.
 * @option String endDate The last date that can be selected.
 * @option Boolean inline Whether to create the datePicker as inline (e.g. always on the page) or as a model popup. Default is false (== modal popup)
 * @option Boolean createButton Whether to create a .dp-choose-date anchor directly after the matched element which when clicked will trigger the showing of the date picker. Default is true.
 * @option Boolean showYearNavigation Whether to display buttons which allow the user to navigate through the months a year at a time. Default is true.
 * @option Boolean showQuarter Show calendar by month (the default) or by quarter. Default is false.
 * @option Boolean closeOnSelect Whether to close the date picker when a date is selected. Default is true.
 * @option Boolean displayClose Whether to create a "Close" button within the date picker popup. Default is false.
 * @option Boolean selectMultiple Whether a user should be able to select multiple dates with this date picker. Default is false.
 * @option Number numSelectable The maximum number of dates that can be selected where selectMultiple is true. Default is a very high number.
 * @option Boolean clickInput If the matched element is an input type="text" and this option is true then clicking on the input will cause the date picker to appear.
 * @option Boolean rememberViewedMonth Whether the datePicker should remember the last viewed month and open on it. If false then the date picker will always open with the month for the first selected date visible.
 * @option Boolean selectWeek Whether to select a complete week at a time...
 * @option Number verticalPosition The vertical alignment of the popped up date picker to the matched element. One of $.dpConst.POS_TOP and $.dpConst.POS_BOTTOM. Default is $.dpConst.POS_TOP.
 * @option Number horizontalPosition The horizontal alignment of the popped up date picker to the matched element. One of $.dpConst.POS_LEFT and $.dpConst.POS_RIGHT.
 * @option Number verticalOffset The number of pixels offset from the defined verticalPosition of this date picker that it should pop up in. Default in 0.
 * @option Number horizontalOffset The number of pixels offset from the defined horizontalPosition of this date picker that it should pop up in. Default in 0.
 * @option (Function|Array) renderCallback A reference to a function (or an array of seperate functions) that is called as each cell is rendered and which can add classes and event listeners to the created nodes. Each callback function will receive four arguments; a jquery object wrapping the created TD, a Date object containing the date this TD represents, a number giving the currently rendered month and a number giving the currently rendered year. Default is no callback.
 * @option String hoverClass The class to attach to each cell when you hover over it (to allow you to use hover effects in IE6 which doesn't support the :hover pseudo-class on elements other than links). Default is dp-hover. Pass false if you don't want a hover class.
 * @option String autoFocusNextInput Whether focus should be passed onto the next input in the form (true) or remain on this input (false) when a date is selected and the calendar closes
 * @option String chooseImg dont print text in choose link print img if chooseImg != '' default ''
 * @option String dateFormat set date format
 * @option Boolean showOtherMonth show other month days default false
 * @type jQuery
 * @name datePicker
 * @cat plugins/datePicker
 * @author Kelvin Luck (http://www.kelvinluck.com/)
 *
 * @example $('input.date-picker').datePicker();
 * @desc Creates a date picker button next to all matched input elements. When the button is clicked on the value of the selected date will be placed in the corresponding input (formatted according to Date.format).
 *
 * @example demo/index.html
 * @desc See the projects homepage for many more complex examples...
 **/
		datePicker : function(s)
		{
			if (!$.event._dpCache) $.event._dpCache = [];
			
			// initialise the date picker controller with the relevant settings...
			s = $.extend({}, $.fn.datePicker.defaults, s);

			return this.each(
				function()
				{
					var $this = $(this);
					var alreadyExists = true;
					
					if (!this._dpId) {
						this._dpId = $.event.guid++;
						$.event._dpCache[this._dpId] = new DatePicker(this);
						alreadyExists = false;
					}
					
					if (s.inline) {
						s.createButton = false;
						s.displayClose = false;
						s.closeOnSelect = false;
						$this.empty();
					}
					
					var controller = $.event._dpCache[this._dpId];
					
					controller.init(s);
					
					if (!alreadyExists && s.createButton) {
						// create it!
                        var chooseElement;
                        if(s.altField != null)
                            chooseElement = $(s.altField);
                        else
                        {
                            var chooseView = s.chooseImg != '' ? '<img src="' + s.chooseImg + '" title="' + $.dpText.TEXT_CHOOSE_DATE + '" alt="' + chooseView + '" border="0" style="position:absolute; z-index:1;"/>' : $.dpText.TEXT_CHOOSE_DATE;
                            chooseElement = $('<a style="position:absolute; z-index:1;" href="#" class="dp-choose-date" title="' + $.dpText.TEXT_CHOOSE_DATE + '">' + chooseView + '</a>');
                        }

						controller.button = chooseElement
								.bind(
									'click',
									function()
									{
                                        var context = $this.context;
                                        var value = $(context).val();
                                        if (value.trim && value.trim() != '')
                                        {
                                            var picker = _getController(context);
                                            var d = Date.fromString(value, picker.settings.dateFormat == undefined ? null : picker.settings.dateFormat);
                                            if (d)
                                                picker.setSelected(d, true, true, true);
                                        }
                                        if ($this.attr("disabled")) return false;
										$this.dpDisplay(this);
										this.blur();
										return false;
									}
								);
						$this.after(controller.button);
					}
					
					if (!alreadyExists && ($this.is(':text') || $this.is("input:hidden"))) {
						$this
							.bind(
								'dateSelected',
								function(e, selectedDate, $td)
								{
									this.value = selectedDate.asString(s.dateFormat == undefined ? null : s.dateFormat);
								}
							).bind(
								'change',
								function()
								{
									if (this.value == '') {
										controller.clearSelected();
									} else {
										var d = Date.fromString(this.value, s.dateFormat == undefined ? null : s.dateFormat);
										if (d) {
                                            _w.call($(this), 'validVal');
											controller.setSelected(d, true, true);
										}
									}
								}
							);
						if (s.clickInput) {
							$this.bind(
								'click',
								function()
								{
									// The change event doesn't happen until the input loses focus so we need to manually trigger it...
									$this.trigger('change');
									$this.dpDisplay();
								}
							);
						}
						var d = Date.fromString(this.value, s.dateFormat == undefined ? null : s.dateFormat);
						if (this.value != '' && d) {
							controller.setSelected(d, true, true);
						}
					}
					
					$this.addClass('dp-applied');
					
				}
			)
		},
/**
 * Disables or enables this date picker
 *
 * @param Boolean s Whether to disable (true) or enable (false) this datePicker
 * @type jQuery
 * @name dpSetDisabled
 * @cat plugins/datePicker
 * @author Kelvin Luck (http://www.kelvinluck.com/)
 *
 * @example $('.date-picker').datePicker();
 * $('.date-picker').dpSetDisabled(true);
 * @desc Prevents this date picker from displaying and adds a class of dp-disabled to it (and it's associated button if it has one) for styling purposes. If the matched element is an input field then it will also set the disabled attribute to stop people directly editing the field.
 **/
		dpSetDisabled : function(s)
		{
			return _w.call(this, 'setDisabled', s);
		},
/**
 * Updates the first selectable date for any date pickers on any matched elements.
 *
 * @param String d A string representing the first selectable date (formatted according to Date.format).
 * @type jQuery
 * @name dpSetStartDate
 * @cat plugins/datePicker
 * @author Kelvin Luck (http://www.kelvinluck.com/)
 *
 * @example $('.date-picker').datePicker();
 * $('.date-picker').dpSetStartDate('01/01/2000');
 * @desc Creates a date picker associated with all elements with a class of "date-picker" then sets the first selectable date for each of these to the first day of the millenium.
 **/
		dpSetStartDate : function(d)
		{
			return _w.call(this, 'setStartDate', d);
		},

        /**
         * Установка признака показа квартала
         * @param d
         */
        dpSetShowQuarter : function(flag)
        {
            return _w.call(this, 'setShowQuarter', flag);
        },
/**
 * Updates the last selectable date for any date pickers on any matched elements.
 *
 * @param String d A string representing the last selectable date (formatted according to Date.format).
 * @type jQuery
 * @name dpSetEndDate
 * @cat plugins/datePicker
 * @author Kelvin Luck (http://www.kelvinluck.com/)
 *
 * @example $('.date-picker').datePicker();
 * $('.date-picker').dpSetEndDate('01/01/2010');
 * @desc Creates a date picker associated with all elements with a class of "date-picker" then sets the last selectable date for each of these to the first Janurary 2010.
 **/
		dpSetEndDate : function(d)
		{
			return _w.call(this, 'setEndDate', d);
		},
/**
 * Gets a list of Dates currently selected by this datePicker. This will be an empty array if no dates are currently selected or NULL if there is no datePicker associated with the matched element.
 *
 * @type Array
 * @name dpGetSelected
 * @cat plugins/datePicker
 * @author Kelvin Luck (http://www.kelvinluck.com/)
 *
 * @example $('.date-picker').datePicker();
 * alert($('.date-picker').dpGetSelected());
 * @desc Will alert an empty array (as nothing is selected yet)
 **/
		dpGetSelected : function()
		{
			var c = _getController(this[0]);
			if (c) {
				return c.getSelected();
			}
			return null;
		},
/**
 * Selects or deselects a date on any matched element's date pickers. Deselcting is only useful on date pickers where selectMultiple==true. Selecting will only work if the passed date is within the startDate and endDate boundries for a given date picker.
 *
 * @param String d A string representing the date you want to select (formatted according to Date.format).
 * @param Boolean v Whether you want to select (true) or deselect (false) this date. Optional - default = true.
 * @param Boolean m Whether you want the date picker to open up on the month of this date when it is next opened. Optional - default = true.
 * @param Boolean e Whether you want the date picker to dispatch events related to this change of selection. Optional - default = true.
 * @type jQuery
 * @name dpSetSelected
 * @cat plugins/datePicker
 * @author Kelvin Luck (http://www.kelvinluck.com/)
 *
 * @example $('.date-picker').datePicker();
 * $('.date-picker').dpSetSelected('01/01/2010');
 * @desc Creates a date picker associated with all elements with a class of "date-picker" then sets the selected date on these date pickers to the first Janurary 2010. When the date picker is next opened it will display Janurary 2010.
 **/
		dpSetSelected : function(d, v, m, e)
		{
			if (v == undefined) v=true;
			if (m == undefined) m=true;
			if (e == undefined) e=true;
			return _w.call(this, 'setSelected', Date.fromString(d), v, m, e);
		},
/**
 * Sets the month that will be displayed when the date picker is next opened. If the passed month is before startDate then the month containing startDate will be displayed instead. If the passed month is after endDate then the month containing the endDate will be displayed instead.
 *
 * @param Number m The month you want the date picker to display. Optional - defaults to the currently displayed month.
 * @param Number y The year you want the date picker to display. Optional - defaults to the currently displayed year.
 * @type jQuery
 * @name dpSetDisplayedMonth
 * @cat plugins/datePicker
 * @author Kelvin Luck (http://www.kelvinluck.com/)
 *
 * @example $('.date-picker').datePicker();
 * $('.date-picker').dpSetDisplayedMonth(10, 2008);
 * @desc Creates a date picker associated with all elements with a class of "date-picker" then sets the selected date on these date pickers to the first Janurary 2010. When the date picker is next opened it will display Janurary 2010.
 **/
		dpSetDisplayedMonth : function(m, y)
		{
			return _w.call(this, 'setDisplayedMonth', Number(m), Number(y), true);
		},
/**
 * Displays the date picker associated with the matched elements. Since only one date picker can be displayed at once then the date picker associated with the last matched element will be the one that is displayed.
 *
 * @param HTMLElement e An element that you want the date picker to pop up relative in position to. Optional - default behaviour is to pop up next to the element associated with this date picker.
 * @type jQuery
 * @name dpDisplay
 * @cat plugins/datePicker
 * @author Kelvin Luck (http://www.kelvinluck.com/)
 *
 * @example $('#date-picker').datePicker();
 * $('#date-picker').dpDisplay();
 * @desc Creates a date picker associated with the element with an id of date-picker and then causes it to pop up.
 **/
		dpDisplay : function(e)
		{
			return _w.call(this, 'display', e);
		},
/**
 * Sets a function or array of functions that is called when each TD of the date picker popup is rendered to the page
 *
 * @param (Function|Array) a A function or an array of functions that are called when each td is rendered. Each function will receive four arguments; a jquery object wrapping the created TD, a Date object containing the date this TD represents, a number giving the currently rendered month and a number giving the currently rendered year.
 * @type jQuery
 * @name dpSetRenderCallback
 * @cat plugins/datePicker
 * @author Kelvin Luck (http://www.kelvinluck.com/)
 *
 * @example $('#date-picker').datePicker();
 * $('#date-picker').dpSetRenderCallback(function($td, thisDate, month, year)
 * {
 * 	// do stuff as each td is rendered dependant on the date in the td and the displayed month and year
 * });
 * @desc Creates a date picker associated with the element with an id of date-picker and then creates a function which is called as each td is rendered when this date picker is displayed.
 **/
		dpSetRenderCallback : function(a)
		{
			return _w.call(this, 'setRenderCallback', a);
		},
/**
 * Sets the position that the datePicker will pop up (relative to it's associated element)
 *
 * @param Number v The vertical alignment of the created date picker to it's associated element. Possible values are $.dpConst.POS_TOP and $.dpConst.POS_BOTTOM
 * @param Number h The horizontal alignment of the created date picker to it's associated element. Possible values are $.dpConst.POS_LEFT and $.dpConst.POS_RIGHT
 * @type jQuery
 * @name dpSetPosition
 * @cat plugins/datePicker
 * @author Kelvin Luck (http://www.kelvinluck.com/)
 *
 * @example $('#date-picker').datePicker();
 * $('#date-picker').dpSetPosition($.dpConst.POS_BOTTOM, $.dpConst.POS_RIGHT);
 * @desc Creates a date picker associated with the element with an id of date-picker and makes it so that when this date picker pops up it will be bottom and right aligned to the #date-picker element.
 **/
		dpSetPosition : function(v, h)
		{
			return _w.call(this, 'setPosition', v, h);
		},
/**
 * Sets the offset that the popped up date picker will have from it's default position relative to it's associated element (as set by dpSetPosition)
 *
 * @param Number v The vertical offset of the created date picker.
 * @param Number h The horizontal offset of the created date picker.
 * @type jQuery
 * @name dpSetOffset
 * @cat plugins/datePicker
 * @author Kelvin Luck (http://www.kelvinluck.com/)
 *
 * @example $('#date-picker').datePicker();
 * $('#date-picker').dpSetOffset(-20, 200);
 * @desc Creates a date picker associated with the element with an id of date-picker and makes it so that when this date picker pops up it will be 20 pixels above and 200 pixels to the right of it's default position.
 **/
		dpSetOffset : function(v, h)
		{
			return _w.call(this, 'setOffset', v, h);
		},

        /**
         * Вызов функции валидации
         */
        dpValidVal : function()
		{    
			return _w.call(this, 'validVal');
		},
        /**
         * Применение маски к связанному input'у
         */
        dpApplyMask : function()
        {
            return _w.call(this, 'applyMask');
        },
/**
 * Closes the open date picker associated with this element.
 *
 * @type jQuery
 * @name dpClose
 * @cat plugins/datePicker
 * @author Kelvin Luck (http://www.kelvinluck.com/)
 *
 * @example $('.date-pick')
 *		.datePicker()
 *		.bind(
 *			'focus',
 *			function()
 *			{
 *				$(this).dpDisplay();
 *			}
 *		).bind(
 *			'blur',
 *			function()
 *			{
 *				$(this).dpClose();
 *			}
 *		);
 **/
		dpClose : function()
		{
			return _w.call(this, '_closeCalendar', false, this[0]);
		},
/**
 * Rerenders the date picker's current month (for use with inline calendars and renderCallbacks).
 *
 * @type jQuery
 * @name dpRerenderCalendar
 * @cat plugins/datePicker
 * @author Kelvin Luck (http://www.kelvinluck.com/)
 *
 **/
		dpRerenderCalendar : function()
		{
			return _w.call(this, '_rerenderCalendar');
		},
		// private function called on unload to clean up any expandos etc and prevent memory links...
		_dpDestroy : function()
		{
			// TODO - implement this?
		}
	});
	
	// private internal function to cut down on the amount of code needed where we forward
	// dp* methods on the jQuery object on to the relevant DatePicker controllers...
	var _w = function(f, a1, a2, a3, a4)
	{
		return this.each(
			function()
			{
				var c = _getController(this);
				if (c) {
					c[f](a1, a2, a3, a4);
				}
			}
		);
	};
	
	function DatePicker(ele)
	{
		this.ele = ele;
		
		// initial values...
		this.displayedMonth		=	null;
        this.displayedQuarter   =   null;
		this.displayedYear		=	null;
		this.startDate			=	null;
		this.endDate			=	null;
		this.showYearNavigation	=	null;
        this.showQuarter        =   null;
        this.showYearMonth      =   null;
		this.closeOnSelect		=	null;
		this.displayClose		=	null;
		this.rememberViewedMonth=	null;
		this.selectMultiple		=	null;
		this.numSelectable		=	null;
		this.numSelected		=	null;
		this.verticalPosition	=	null;
		this.horizontalPosition	=	null;
		this.verticalOffset		=	null;
		this.horizontalOffset	=	null;
		this.button				=	null;
		this.renderCallback		=	[];
		this.selectedDates		=	{};
		this.inline				=	null;
		this.context			=	'#dp-popup';
		this.settings			=	{};
        this.needAjaxUpdateDisabledDates = false;
        this.disabledWeekends = false;
        this.ajaxUpdateUrl = null;
        this.tb = null;
        this.workDays = [];
        this.plannedPayments = [];
        this.showPlannedPaymentsText = false;
	};
	$.extend(
		DatePicker.prototype,
		{	
			init : function(s)
			{
                this.showQuarter = s.showQuarter;
                this.showYearMonth = s.showYearMonth;
				this.setStartDate(s.startDate);
				this.setEndDate(s.endDate);
				this.setDisplayedMonth(Number(s.month), Number(s.year));
				this.setRenderCallback(s.renderCallback);
				this.showYearNavigation = s.showYearNavigation;
				this.closeOnSelect = s.closeOnSelect;
				this.displayClose = s.displayClose;
				this.rememberViewedMonth =	s.rememberViewedMonth;
				this.selectMultiple = s.selectMultiple;
				this.numSelectable = s.selectMultiple ? s.numSelectable : 1;
				this.numSelected = 0;
				this.verticalPosition = s.verticalPosition;
				this.horizontalPosition = s.horizontalPosition;
				this.hoverClass = s.hoverClass;
				this.setOffset(s.verticalOffset, s.horizontalOffset);
				this.inline = s.inline;
				this.settings = s;
                if (s.needAjaxUpdateDisabledDates != undefined)
                    this.needAjaxUpdateDisabledDates = s.needAjaxUpdateDisabledDates;
                if (s.disabledWeekends != undefined)
                    this.disabledWeekends = s.disabledWeekends;
                if (s.ajaxUpdateUrl != undefined)
                    this.ajaxUpdateUrl = s.ajaxUpdateUrl;
                if (s.tb != undefined)
                    this.tb = s.tb;
                if (s.plannedPayments != undefined)
                    this.plannedPayments = s.plannedPayments;
                if (s.showPlannedPaymentsText != undefined)
                    this.showPlannedPaymentsText = s.showPlannedPaymentsText;
                this.daySelectedHandler = s.daySelectedHandler;
				if (this.inline) {
					this.context = this.ele;
					this.display();
				}
			},
            setShowQuarter : function(flag)
            {
                this.showQuarter = flag != null && flag;
            },
			setStartDate : function(d)
			{
				if (d) {
					this.startDate = Date.fromString(d);
				}
				if (!this.startDate) {
					this.startDate = (new Date()).zeroTime();
                    this.startDate.setFullYear(1900);
				}
				this.setDisplayedMonth(this.displayedMonth, this.displayedYear);
			},
			setEndDate : function(d)
			{
				if (d) {
					this.endDate = Date.fromString(d);
				}
				if (!this.endDate) {
					this.endDate = (new Date('12/31/2999')); // using the JS Date.parse function which expects mm/dd/yyyy
				}
				if (this.endDate.getTime() < this.startDate.getTime()) {
					this.endDate = this.startDate;
				}
				this.setDisplayedMonth(this.displayedMonth, this.displayedYear);
			},
			setPosition : function(v, h)
			{
				this.verticalPosition = v;
				this.horizontalPosition = h;
			},
			setOffset : function(v, h)
			{
				this.verticalOffset = parseInt(v) || 0;
				this.horizontalOffset = parseInt(h) || 0;
			},
			setDisabled : function(s)
			{
				$e = $(this.ele);
				$e[s ? 'addClass' : 'removeClass']('dp-disabled');
				if (this.button) {
					$but = $(this.button);
					$but[s ? 'addClass' : 'removeClass']('dp-disabled');
					$but.attr('title', s ? '' : $.dpText.TEXT_CHOOSE_DATE);
				}
				if ($e.is(':text')) {
					$e.attr('disabled', s ? 'disabled' : '');
				}
			},
			setDisplayedMonth : function(m, y, rerender)
			{
				if (this.startDate == undefined || this.endDate == undefined) {
					return;
				}
				var s = new Date(this.startDate.getTime());
				s.setDate(1);
				var e = new Date(this.endDate.getTime());
				e.setDate(1);
				if (this.showQuarter)
                {
                    s.setMonth(s.getFirstMonthOfQuarter());
                    e.setMonth(e.getFirstMonthOfQuarter());
                }

				var t;
				if ((!m && !y) || (isNaN(m) && isNaN(y))) {
					// no month or year passed - default to current month
					t = new Date().zeroTime();
					t.setDate(1);
				} else if (isNaN(m)) {
					// just year passed in - presume we want the displayedMonth
					t = new Date(y, this.displayedMonth, 1, 12);
				} else if (isNaN(y)) {
					// just month passed in - presume we want the displayedYear
					t = new Date(this.displayedYear, m, 1, 12);
				} else {
					// year and month passed in - that's the date we want!
					t = new Date(y, m, 1, 12);
				}
                if (this.showQuarter)
                    t.setMonth(t.getFirstMonthOfQuarter());
				// check if the desired date is within the range of our defined startDate and endDate
				if (t.getTime() < s.getTime()) {
					t = s;
				} else if (t.getTime() > e.getTime()) {
					t = e;
				}
                if (this.showQuarter)
                {
                    var oldQuarter = this.displayedQuarter;
                    var oldYear = this.displayedYear;
                    this.displayedMonth = t.getMonth();
                    this.displayedQuarter = t.getQuarter();
                    this.displayedYear = t.getFullYear();

                    if (rerender && (this.displayedQuarter != oldQuarter || this.displayedYear != oldYear))
                    {
                        this._rerenderCalendar();
                        $(this.ele).trigger('dpMonthChanged', [this.displayedMonth, this.displayedYear]);
                    }
                }
                else
                {
                    var oldMonth = this.displayedMonth;
                    var oldYear = this.displayedYear;
                    this.displayedMonth = t.getMonth();
                    this.displayedYear = t.getFullYear();

                    if (rerender && (this.displayedMonth != oldMonth || this.displayedYear != oldYear))
                    {
                        this._rerenderCalendar();
                        $(this.ele).trigger('dpMonthChanged', [this.displayedMonth, this.displayedYear]);
                    }
                }

			},
            validVal:function()
            {
                var d = Date.fromString($(this.ele).val(), this.settings.dateFormat == undefined ? null : this.settings.dateFormat);
                if (d)
                {
                    var newValue = d.asString(this.settings.dateFormat == undefined ? null : this.settings.dateFormat);
                    $(this.ele).val(newValue);
                    var c = _getController(this.ele);
                    c.setSelected(Date.fromString(newValue, this.settings.dateFormat == undefined ? null : this.settings.dateFormat), true, true, true);
                }
            },
			setSelected : function(d, v, moveToMonth, dispatchEvents)
			{
                var temp = new Date(d);
				if (d < this.startDate || temp.zeroTime() > this.endDate.zeroTime()) {
					// Don't allow people to select dates outside range...
					//return;
                    //Не позволяем выбирать невалидные даты, но позволяем одуматься и все исправить(BUG055080)
                    d = d < this.startDate ? this.startDate : this.endDate;
                    var newValue = d.asString(this.settings.dateFormat == undefined ? null : this.settings.dateFormat);
                    $(this.ele).val(newValue);
				}
				var s = this.settings;
				if (s.selectWeek)
				{
					d = d.addDays(- (d.getDay() - Date.firstDayOfWeek + 7) % 7);
					if (d < this.startDate) // The first day of this week is before the start date so is unselectable...
					{
						return;
					}
				}
				if (v == this.isSelected(d)) // this date is already un/selected
				{
					return;
				}
				if (this.selectMultiple == false) {
					this.clearSelected();
				} else if (v && this.numSelected == this.numSelectable) {
					// can't select any more dates...
					return;
				}
                if (this.showQuarter)
                {
                    if (moveToMonth && (this.displayedMonth != d.getFirstMonthOfQuarter() || this.displayedYear != d.getFullYear())) {
                        this.setDisplayedMonth(d.getFirstMonthOfQuarter(), d.getFullYear(), true);
                    }
                }
                else
                {
                    if (moveToMonth && (this.displayedMonth != d.getMonth() || this.displayedYear != d.getFullYear())) {
                        this.setDisplayedMonth(d.getMonth(), d.getFullYear(), true);
                    }
                }
				this.selectedDates[d.asString()] = v;
				this.numSelected += v ? 1 : -1;
				var selectorString = 'td.' + (d.getMonth() == this.displayedMonth ? 'current-month' : 'other-month');
				var $td;
				$(selectorString, this.context).each(
					function()
					{
						if ($(this).data('datePickerDate') == d.asString()) {
							$td = $(this);
							if (s.selectWeek)
							{
								$td.parent()[v ? 'addClass' : 'removeClass']('selectedWeek');
							}
							$td[v ? 'addClass' : 'removeClass']('selected'); 
						}
					}
				);
				$('td', this.context).not('.selected')[this.selectMultiple &&  this.numSelected == this.numSelectable ? 'addClass' : 'removeClass']('unselectable');
				
				if (dispatchEvents)
				{
					var s = this.isSelected(d);
					$e = $(this.ele);
					var dClone = Date.fromString(d.asString());
					$e.trigger('dateSelected', [dClone, $td, s]);
					$e.trigger('change');
				}
			},
			isSelected : function(d)
			{
				return this.selectedDates[d.asString()];
			},
			getSelected : function()
			{
				var r = [];
				for(var s in this.selectedDates) {
					if (this.selectedDates[s] == true) {
						r.push(Date.fromString(s));
					}
				}
				return r;
			},
			clearSelected : function()
			{
				this.selectedDates = {};
				this.numSelected = 0;
				$('td.selected', this.context).removeClass('selected').parent().removeClass('selectedWeek');
			},
            /**
             * требуется ли дополнительно аяксом запрашивать дни которые можно нельзя выбрать
             * @param flag
             */
            setNeedAjaxUpdateDisabledDates : function(flag)
            {
                this.needAjaxUpdateDisabledDates = flag;
            },
            /**
             * требуется ли делать невозможными для выбора все выходные
             * @param flag
             */
            setDisabledWeekends : function(flag)
            {
                this.disabledWeekends = flag;
            },
            /**
             * адрес для обновления данных
             * @param flag
             */
            setAjaxUpdateUrl : function(url)
            {
                this.ajaxUpdateUrl = url;
            },
            getGoodLeftPosition : function(pop, ele)
            {
                var screenWidth = screenSize().w;
                var left = parseInt(pop.css("left").replace(/[^0-9\.]+/ig, ""));
                var width = pop.width();
                return {
                    left : left < screenWidth - width ? left : screenWidth - width - ele.children().width(),
                    changed : left >= screenWidth - width
                };
            },
            getGoodTopPosition : function(pop, ele, leftChanged)
            {
                var screenHeight = screenSize().h;
                var top = parseInt(pop.css("top").replace(/[^0-9\.]+/ig, ""));
                if (leftChanged) //если изменилась левая координата, сдвинем чуть вниз.
                    top += ele.children().height();
                var height = pop.height();
                var topX = getScrollTop() + screenHeight;
                return top < topX - height ? top : topX - height;
            },
			display : function(eleAlignTo)
			{
				if ($(this.ele).is('.dp-disabled')) return;
				
				eleAlignTo = eleAlignTo || this.ele;
				var c = this;
				var $ele = $(eleAlignTo);
				var eleOffset = $ele.offset();
				
				var $createIn;
				var attrs;
				var attrsCalendarHolder;
				var cssRules;
				
				if (c.inline) {
					$createIn = $(this.ele);
					attrs = {
						'id'		:	'calendar-' + this.ele._dpId,
						'class'	:	'dp-popup dp-popup-inline'
					};

					$('.dp-popup', $createIn).remove();
					cssRules = {
					};
				} else {
					$createIn = $('body');
                    if (c.showQuarter)
                    {
                        attrs = {
                            'id'		:	'dp-popup',
                            'class'	:	'dp-popup datePicker dp-quarter'
                        };
                    }
                    else if(c.needAjaxUpdateDisabledDates)
                    {
                        attrs = {
                            'id'		:	'dp-popup',
                            'class'	:	'dp-popup datePicker date-pick-newType'
                        };
                    }
                    else
                    {
                        attrs = {
                            'id'		:	'dp-popup',
                            'class'	:	'dp-popup datePicker'
                        };
                    }

					cssRules = {
						'top'	:	eleOffset.top + c.verticalOffset,
						'left'	:	eleOffset.left + c.horizontalOffset,
                        'display': 'none',
                        'z-index': getZindex(this.ele)+19
					};

                    if(!$.browser.msie){
                        cssRules.top = cssRules.top - 15; 
                    }
					
					var _checkMouse = function(e)
					{
						var el = e.target;
						var cal = $('#dp-popup')[0];
						
						while (true){
							if (el == cal) {
								return true;
							} else if (el == document) {
								c._closeCalendar();
								return false;
							} else {
								el = $(el).parent()[0];
							}
						}
					};
					this._checkMouse = _checkMouse;
					
					c._closeCalendar(true);
					$(document).bind(
						'keydown.datepicker', 
						function(event)
						{
							if (event.keyCode == 27) {
								c._closeCalendar();
							}
						}
					);
				}
				
				if (!c.rememberViewedMonth)
				{
					var selectedDate = this.getSelected()[0];
					if (selectedDate) {
						selectedDate = new Date(selectedDate);
						this.setDisplayedMonth(selectedDate.getMonth(), selectedDate.getFullYear(), false);
					}
                    else{
                        selectedDate = (new Date()).zeroTime();
						this.setDisplayedMonth(selectedDate.getMonth(), selectedDate.getFullYear(), false);
				}
				}

               if (!c.inline)
               {
                var $roundCorner = $createIn;

                    $roundCorner.append( $("<div></div>").attr(attrs)
                                .css(cssRules).append
                        (
                        $("<div class='datePickerRT r-top'></div>").append(
                             $("<div class='datePickerRTL r-top-left'></div>").append(
                                    $("<div class='datePickerRTR r-top-right'></div>").append(
                                        $("<div class='datePickerRTC r-top-center'></div>")
                                            )
                                     )),
                        $("<div class='datePickerRCL r-center-left'></div>").append(
                                 $("<div class='datePickerRCR r-center-right'></div>").append(
                                         $("<div class='datePickerRC r-content'></div>")
                                         )
                                ),
                        $("<div class='datePickerRBL r-bottom-left'></div>").append(
                                $("<div class='datePickerRBR r-bottom-right'></div>").append(
                                    $("<div class='datePickerRBC r-bottom-center'></div>")
                                        )
                                )
                        )
                    );
                 $createIn = $('#'+attrs.id+" .r-content");
               }
                if (c.showQuarter)
                {
                    $createIn
                            .append(
                                    $('<div></div>')
                                            .append($("<div class='dp-header'></div>").append(
                                                    $('<div class="cell-center-quarter"></div>').append(
                                                            $('<div class="dp-nav-prev"></div>')
                                                                    .append(
                                                                    $('<a class="dp-nav-prev-quarter" href="#" title="' + $.dpText.TEXT_PREV_QUARTER + '"> </a>')
                                                                            .bind(
                                                                            'click',
                                                                            function()
                                                                            {
                                                                                return c._displayNewMonth.call(c, this, -3, 0);
                                                                            }
                                                                    )
                                                                    ),
                                                            $('<div class="dp-nav-next"></div>')
                                                                    .append(
                                                                            $('<a class="dp-nav-next-quarter" href="#" title="' + $.dpText.TEXT_NEXT_QUARTER + '"> </a>')
                                                                                    .bind(
                                                                                    'click',
                                                                                    function()
                                                                                    {
                                                                                        return c._displayNewMonth.call(c, this, 3, 0);
                                                                                    }
                                                                            )
                                                                    ),
                                                            $('<div class="quarter"></div>')
                                                    ),
                                                    $("<div class='clear'></div>")
                                            ),
                                                    $('<div class="dp-calendar"></div>')
                                            )
                            ).bgIframe();
                }
                else if (c.showYearMonth)
                {
                    $createIn
                            .append(
                                    $('<div></div>')
                                            .append($("<div class='dp-header'></div>").append(
                                                    $('<div class="cell-center-year-month"></div>').append(
                                                    $('<div class="dp-nav-prev"></div>')
                                                            .append(
                                                                    $('<a class="dp-nav-prev-month" href="#" title="' + $.dpText.TEXT_PREV_MONTH + '"> </a>')
                                                                            .bind(
                                                                            'click',
                                                                            function()
                                                                            {
                                                                                return c._displayNewMonth.call(c, this, -1, 0);
                                                                            }
                                                                    )
                                                            ),

                                                    $('<div class="dp-nav-next"></div>')
                                                            .append(
                                                                    $('<a class="dp-nav-next-month" href="#" title="' + $.dpText.TEXT_NEXT_MONTH + '"> </a>')
                                                                            .bind(
                                                                            'click',
                                                                            function()
                                                                            {
                                                                                return c._displayNewMonth.call(c, this, 1, 0);
                                                                            }
                                                                    )
                                                            ),
                                                    $('<div class="yearMonth"></div>')
                                                    ),
                                                    $("<div class='clear'></div>")
                                                    ),
                                                    $('<div class="dp-calendar"></div>')
                                            )
                            ).bgIframe();
                }
                else
                {
                    $createIn
                        .append(
                        $('<div></div>')
                                .append($("<div class='dp-header'></div>").append(
                                $('<div class="cell-left"></div>').append(
                                        $('<div class="dp-nav-prev"></div>')
                                                .append(
                                                $('<a class="dp-nav-prev-month" href="#" title="' + $.dpText.TEXT_PREV_MONTH + '"> </a>')
                                                        .bind(
                                                        'click',
                                                        function()
                                                        {
                                                            return c._displayNewMonth.call(c, this, -1, 0);
                                                        }
                                                        )
                                                ),
                                        $('<div class="dp-nav-next"></div>')
                                                .append(
                                                $('<a class="dp-nav-next-month" href="#" title="' + $.dpText.TEXT_NEXT_MONTH + '"> </a>')
                                                        .bind(
                                                        'click',
                                                        function()
                                                        {
                                                            return c._displayNewMonth.call(c, this, 1, 0);
                                                        }
                                                        )
                                                ),
                                        $('<div class="month"></div>')
                                        ),
                                /*
                                * Классы cell-right и cell-left позволяют отобразить закругленную рамку вокруг
                                * названия года и месяца соответственно. В IE рамки будут без закругленных
                                * углов. Это допущение, согласованное с дизайнером.
                                */
                                $('<div class="cell-right"></div>').append(
                                        $('<div class="dp-nav-prev"></div>')
                                                .append(
                                                $('<a class="dp-nav-prev-year" href="#" title="' + $.dpText.TEXT_PREV_YEAR + '"> </a>')
                                                        .bind(
                                                        'click',
                                                        function()
                                                        {
                                                            return c._displayNewMonth.call(c, this, 0, -1);
                                                        }
                                                        )
                                                ),
                                        $('<div class="dp-nav-next"></div>')
                                                .append(
                                                $('<a class="dp-nav-next-year" href="#" title="' + $.dpText.TEXT_NEXT_YEAR + '"> </a>')
                                                        .bind(
                                                        'click',
                                                        function()
                                                        {
                                                            return c._displayNewMonth.call(c, this, 0, 1);
                                                        }
                                                        )
                                                ),
                                        $('<div class="year"></div>')
                                        ),
                                $("<div class='clear'></div>")
                                ),
                                $('<div class="dp-calendar"></div>')
                                )
                        ).bgIframe();
                }

                var $pop = this.inline ? $('.dp-popup', this.context) : $('#dp-popup');

				if (this.showYearNavigation == false)
					$('.dp-nav-prev-year, .dp-nav-next-year', c.context).css('display', 'none');

				if (this.displayClose) {
					$createIn.append(
						$('<a href="#" id="dp-close">' + $.dpText.TEXT_CLOSE + '</a>')
							.bind(
								'click',
								function()
								{
									c._closeCalendar();
									return false;
								}
							)
					);
				}

                if (this.showPlannedPaymentsText) {
                    $createIn.append(
                        $('<div class="date-picker-planned-payments-text">' + $.dpText.TEXT_PLANNED_PAYMENTS + '</div>')
                    );
                }
				c._renderCalendar();

				$(this.ele).trigger('dpDisplayed', $pop);
				
				if (!c.inline) {
					if (this.verticalPosition == $.dpConst.POS_BOTTOM) {
						$pop.css('top', eleOffset.top + $ele.height() - $pop.height() + c.verticalOffset);
					}
					if (this.horizontalPosition == $.dpConst.POS_RIGHT) {
						$pop.css('left', eleOffset.left + $ele.width() - $pop.width() + c.horizontalOffset);
					}
                    var leftPos = this.getGoodLeftPosition($pop, $ele);
                    $pop.css('left', leftPos.left);
                    $pop.css('top', this.getGoodTopPosition($pop, $ele, leftPos.changed));
					$(document).bind('mousedown.datepicker', this._checkMouse);
					if (!($.browser.msie && $.browser.version == '6.0'))
                        $(window).bind('resize', function() { c._closeCalendar();});
                    /*$(window).bind('scroll', function() { c._closeCalendar(); });*/
				}

                //
                if (!this.inline) $("#dp-popup").show();
				
			},
			setRenderCallback : function(a)
			{
				if (a == null) return;
				if (a && typeof(a) == 'function') {
					a = [a];
				}
				this.renderCallback = this.renderCallback.concat(a);
			},
			cellRender : function ($td, thisDate, month, year) {
				var c = this.dpController;
				var d = new Date(thisDate.getTime());

                if (!c.settings.showOtherMonth && d.getMonth() != month) return;
				// add our click handlers to deal with it when the days are clicked...

				$td.bind(
					'click',
					function()
					{
						var $this = $(this);
						if (!$this.is('.disabled')) {
							c.setSelected(d, !$this.is('.selected') || !c.selectMultiple, false, true);
                            if(c.daySelectedHandler!==undefined)
                            {
                                c.daySelectedHandler(d);
                            }
							if (c.closeOnSelect) {
								// Focus the next input in the form…
								if (c.settings.autoFocusNextInput) {
									var ele = c.ele;
									var found = false;
									$(':input', ele.form).each(
										function()
										{
											if (found) {
												$(this).focus();
												return false;
											}
											if (this == ele) {
												found = true;
											}
										}
									);
								} else {
                                    if ($(c.ele).is('not(:hidden)'))
									    c.ele.focus();
								}
								c._closeCalendar();
							}
						}
					}
				);
				if (c.isSelected(d)) {
					$td.addClass('selected');
					if (c.settings.selectWeek)
					{
						$td.parent().addClass('selectedWeek');
					}
				} else  if (c.selectMultiple && c.numSelected == c.numSelectable) {
					$td.addClass('unselectable');
				}
				
			},
            applyMask:function()
            {
                $(this.ele).createMask(this.settings.dateFormat == undefined ? Date.format : this.settings.dateFormat);
                $(this.ele).addCompleted(function(){_w.call($(this), 'validVal');});
                $(this.ele).addYearPeriod(this.startDate.getFullYear(), this.endDate.getFullYear());
                $(this.ele).addValueChanged(function(){
                    var datePicker = _getController(this);
                    var dpSettings = datePicker != undefined ? datePicker.settings : null;
                    var dpSettingsDateFormat = dpSettings != null ? dpSettings.dateFormat : null;
                    if (dpSettingsDateFormat == null || dpSettingsDateFormat == undefined)
                        return;

                    var d = Date.fromString(this.value, dpSettingsDateFormat);
                    if (d.valueOf())
                    {
                        var newValue = d.asString(dpSettingsDateFormat);
                        $(this.ele).val(newValue);
                        datePicker.setSelected(Date.fromString(newValue, dpSettingsDateFormat), true, true, true);
                        //Вызов 'change' при успешной валидации значения
                        $(this.ele).trigger('change');
                    }
                });
			},
			_applyRenderCallbacks : function()
			{
				var c = this;
				$('td', this.context).each(
					function()
					{
						for (var i=0; i<c.renderCallback.length; i++) {
							$td = $(this);
							c.renderCallback[i].apply(this, [$td, Date.fromString($td.data('datePickerDate')), c.displayedMonth, c.displayedYear]);
						}
					}
				);
				return;
			},
			// ele is the clicked button - only proceed if it doesn't have the class disabled...
			// m and y are -1, 0 or 1 depending which direction we want to go in...
			_displayNewMonth : function(ele, m, y) 
			{
				if (!$(ele).is('.disabled')) {
					this.setDisplayedMonth(this.displayedMonth + m, this.displayedYear + y, true);
				}
				ele.blur();
				return false;
			},
			_rerenderCalendar : function()
			{
				this._clearCalendar();
				this._renderCalendar();
			},

            _renderCalendar : function()
            {
                if (this.needAjaxUpdateDisabledDates)
                {
                    if (this.workDays[this.displayedYear] == undefined || this.workDays[this.displayedYear][this.displayedMonth] == undefined)
                    {
                        var fromDate = new Date(this.displayedYear, this.displayedMonth, 1);
                        var toDate = new Date(this.displayedYear, this.displayedMonth + Date.numUpdateMonth - 1, 1);
                        for (var year = fromDate.getFullYear(); year <= toDate.getFullYear(); year++)
                        {
                            for (var month = fromDate.getMonth(); month <= toDate.getMonth(); month++)
                            {
                                if (this.workDays[year] == undefined)
                                    this.workDays[year] = [];

                                if (this.workDays[year][month] == undefined)
                                    this.workDays[year][month] = [];
                            }
                        }
                        toDate.setMonth(toDate.getMonth() + 1);
                        var params = "field(tb)=" + this.tb + "&field(fromDate)=" + fromDate.asString('dd.mm.yyyy') +  "&field(toDate)=" + toDate.asString('dd.mm.yyyy');
                        var self = this;
                        ajaxQuery(params, this.ajaxUpdateUrl, function(resp)
                        {
                            if (resp != null)
                            {
                                var weekendDays = resp.weekendDays;
                                var workDays = resp.workDays;
                                var day;
                                var i;
                                for (i = 0; i < workDays.length; i++)
                                {
                                    day = parseDate(workDays[i]);

                                    if (self.workDays[day.getFullYear()] != undefined && self.workDays[day.getFullYear()][day.getMonth()] != undefined)
                                        self.workDays[day.getFullYear()][day.getMonth()][day.getDate()] = true;
                                }
                                for (i = 0; i < weekendDays.length; i++)
                                {
                                    day = parseDate((weekendDays[i]));

                                    if (self.workDays[day.getFullYear()] != undefined && self.workDays[day.getFullYear()][day.getMonth()] != undefined)
                                        self.workDays[day.getFullYear()][day.getMonth()][day.getDate()] = false;
                                }
                            }
                            self._doRenderCalendar()
                        }, "json", true);
                    }
                    else
                    {
                        this._doRenderCalendar();
                    }
                }
                else
                {
                    this._doRenderCalendar();
                }
            },

			_doRenderCalendar : function()
			{
				// set the title...
                if (this.showQuarter)
                {
                    $('.quarter', this.context).html((new Date(this.displayedYear, this.displayedMonth, 1, 12)).asString($.dpText.HEADER_QUARTER_FORMAT));
                }
                else if (this.showYearMonth)
                {
                    $('.yearMonth', this.context).html((new Date(this.displayedYear, this.displayedMonth, 1)).asString($.dpText.HEADER_FORMAT));
                }
                else
                {
                    $('.month', this.context).html((new Date(this.displayedYear, this.displayedMonth, 1, 12)).asString($.dpText.HEADER_MONTH_FORMAT));
                    $('.year', this.context).html((new Date(this.displayedYear, this.displayedMonth, 1, 12)).asString($.dpText.HEADER_YEAR_FORMAT));
                }
				// render the calendar...
				$('.dp-calendar', this.context).renderCalendar(
					$.extend(
						{},
						this.settings, 
						{
                            showQuarter     : this.showQuarter,
							month			: this.displayedMonth,
                            quarter         : this.displayedQuarter,
							year			: this.displayedYear,
							renderCallback	: this.cellRender,
							dpController	: this,
							hoverClass		: this.hoverClass
						})
				);

                if (this.disabledWeekends)
                {

                    $('.dp-calendar td.current-month.weekend', this.context).each(
                        function()
                        {
                            var $this = $(this);
                            $this.addClass('disabled');
                        }
                    );
                }
                var workDays = this.workDays;
                var plannedPayments = this.plannedPayments;
                var month = this.displayedMonth;
                var year = this.displayedYear;
                if (this.needAjaxUpdateDisabledDates)
                {
                    $('.dp-calendar td.current-month', this.context).each(
                        function()
                        {
                            var $this = $(this);
                            var day = Number($this.text());
                            if (workDays[year] != undefined && workDays[year][month] != undefined && workDays[year][month][day] != undefined)
                            {
                                if (workDays[year][month][day] == true)
                                    $this.removeClass('disabled');
                                else if (workDays[year][month][day] == false)
                                    $this.addClass('disabled');
                            }
                        }
                    );
                }

                if (this.needAjaxUpdateDisabledDates)
                {
                    $('.dp-calendar td.current-month', this.context).each(
                        function()
                        {
                            var $this = $(this);
                            var day = Number($this.text());
                            for (var i = 0; i < plannedPayments.length; i++)
                                if (plannedPayments[i].getFullYear() == year && plannedPayments[i].getMonth() == month && plannedPayments[i].getDate() == day)
                                    $this.addClass('star');
                        }
                    );
                }
				
				// update the status of the control buttons and disable dates before startDate or after endDate...
				// TODO: When should the year buttons be disabled? When you can't go forward a whole year from where you are or is that annoying?
				if (this.displayedYear == this.startDate.getFullYear() && this.displayedMonth == this.startDate.getMonth()) {
					if (this.showQuarter)
                    {
                        $('.dp-nav-prev-quarter', this.context).addClass('disabled');
                    }
                    else
                    {
                        $('.dp-nav-prev-year', this.context).addClass('disabled');
                        $('.dp-nav-prev-month', this.context).addClass('disabled');
                    }

					$('.dp-calendar td.other-month', this.context).each(
						function()
						{
							var $this = $(this);
							if (Number($this.text()) > 20) {
								$this.addClass('disabled');
							}
						}
					);
					var d = this.startDate.getDate();
					$('.dp-calendar td.current-month', this.context).each(
						function()
						{
							var $this = $(this);
							if (Number($this.text()) < d) {
								$this.addClass('disabled');
							}
						}
					);
				} else {
                    if (this.showQuarter)
                    {
                        $('.dp-nav-prev-quarter', this.context).removeClass('disabled');
                    }
                    else
                    {
                        $('.dp-nav-prev-year', this.context).removeClass('disabled');
                        $('.dp-nav-prev-month', this.context).removeClass('disabled');
                    }

					var d = this.startDate.getDate();
					if (d > 20) {
						// check if the startDate is last month as we might need to add some disabled classes...
						var st = this.startDate.getTime();
						var sd = new Date(st);
						sd.addMonths(1);
						if (this.displayedYear == sd.getFullYear() && this.displayedMonth == sd.getMonth()) {
							$('.dp-calendar td.other-month', this.context).each(
								function()
								{
									var $this = $(this);
									if (Date.fromString($this.data('datePickerDate')).getTime() < st) {
										$this.addClass('disabled');
									}
								}
							);
						}
					}
				}
				if (this.displayedYear == this.endDate.getFullYear() && this.displayedMonth == this.endDate.getMonth()) {
                    if (this.showQuarter)
                    {
                        $('.dp-nav-next-quarter', this.context).addClass('disabled');
                    }
                    else
                    {
                        $('.dp-nav-next-year', this.context).addClass('disabled');
                        $('.dp-nav-next-month', this.context).addClass('disabled');
                    }

					$('.dp-calendar td.other-month', this.context).each(
						function()
						{
							var $this = $(this);
							if (Number($this.text()) < 14) {
								$this.addClass('disabled');
							}
						}
					);
					var d = this.endDate.getDate();
					$('.dp-calendar td.current-month', this.context).each(
						function()
						{
							var $this = $(this);
							if (Number($this.text()) > d) {
								$this.addClass('disabled');
							}
						}
					);
				} else {
                    if (this.showQuarter)
                    {
                        $('.dp-nav-next-quarter', this.context).removeClass('disabled');
                    }
                    else
                    {
                        $('.dp-nav-next-year', this.context).removeClass('disabled');
                        $('.dp-nav-next-month', this.context).removeClass('disabled');
                    }


					var d = this.endDate.getDate();
					if (d < 13) {
						// check if the endDate is next month as we might need to add some disabled classes...
						var ed = new Date(this.endDate.getTime());
						ed.addMonths(-1);
						if (this.displayedYear == ed.getFullYear() && this.displayedMonth == ed.getMonth()) {
							$('.dp-calendar td.other-month', this.context).each(
								function()
								{
									var $this = $(this);
									var cellDay = Number($this.text());
									if (cellDay < 13 && cellDay > d) {
										$this.addClass('disabled');
									}
								}
							);
						}
					}
				}
				this._applyRenderCallbacks();

                var self = this;
                // startDate - меньше этой даты выбрать невозможно
                // showQuarter - отображать квартал (показывать сразу 3 месяца)
                // отображает 3 месяца календаря с недоступностью выбора определенных дат
                if (this.startDate && this.showQuarter)
                    $("td.quarterRow").each(
                        // функция, которая заускается на каждый показанный месяц
                        function()
                        {
                            // определяем месяц отображения и год
                            var quarterRow = $(this);
                            var month = quarterRow.attr("month");
                            var year = quarterRow.attr("year");
                            // цикл по всем датам месяца
                            quarterRow.find("td.current-month").each(
                                function()
                                {
                                    // определяем день
                                    var $this = $(this);
                                    var day = Number($this.text());
                                    if (day && month && year)
                                    {
                                        // формируем дату
                                        var temp = new Date(year, month, day, 12);
                                        // если дата меньше той, которую можно выбрать, тогда не позволяем ее выбрать
                                        if (temp.getTime() < self.startDate.getTime())
                                            $this.addClass('disabled');
                                    }
                                }
                            )
                        }
                    );
			},
			_closeCalendar : function(programatic, ele)
			{
				if (!ele || ele == this.ele)
				{
					$(document).unbind('mousedown.datepicker');
					$(document).unbind('keydown.datepicker');
                    $(window).unbind('resize');
                    $(window).unbind('scroll');
					this._clearCalendar();
					$('#dp-popup a').unbind();
					$('#dp-popup').empty().remove();
					if (!programatic) {
						$(this.ele).trigger('dpClosed', [this.getSelected()]);
					}
				}
			},
			// empties the current dp-calendar div and makes sure that all events are unbound
			// and expandos removed to avoid memory leaks...
			_clearCalendar : function()
			{
				// TODO.
				$('.dp-calendar td', this.context).unbind();
				$('.dp-calendar', this.context).empty();
			}
		}
	);
	
	// static constants
	$.dpConst = {
		SHOW_HEADER_NONE	:	1,
		SHOW_HEADER_SHORT	:	1,
		SHOW_HEADER_LONG	:	2,
		POS_TOP				:	0,
		POS_BOTTOM			:	1,
		POS_LEFT			:	0,
		POS_RIGHT			:	1,
		DP_INTERNAL_FOCUS	:	'dpInternalFocusTrigger'
	};
	// localisable text
	$.dpText = {
		TEXT_PREV_YEAR		:	'Предыдущий год',
        TEXT_PREV_QUARTER	:	'Предыдущий квартал',
		TEXT_PREV_MONTH		:	'Предыдущий месяц',
		TEXT_NEXT_YEAR		:	'Следующий год',
        TEXT_NEXT_QUARTER	:	'Следующий квартал',
		TEXT_NEXT_MONTH		:	'Следующий месяц',
		TEXT_CLOSE			:	'отмена',
		TEXT_PLANNED_PAYMENTS:	'дата ежемесячного платежа',
		TEXT_CHOOSE_DATE	:	'Выбрать дату',
		HEADER_MONTH_FORMAT	:	'mmmm',
        HEADER_QUARTER_FORMAT : 'q КВАРТАЛ, yyyy',
        HEADER_YEAR_FORMAT	:	'yyyy',
        HEADER_FORMAT		:	'mmmm, yyyy'
	};
	// version
	$.dpVersion = '$Id: jquery.datePicker.js 100 2010-08-09 10:45:28Z softlab adapted version $';

	$.fn.datePicker.defaults = {
		month				: undefined,
        quarter             : undefined,
		year				: undefined,
		showHeader			: $.dpConst.SHOW_HEADER_SHORT,
		startDate			: undefined,
		endDate				: undefined,
		inline				: false,
		renderCallback		: null,
		createButton		: true,
		showYearNavigation	: true,
        showQuarter	        : false,
        showYearMonth       : false,
		closeOnSelect		: true,
		displayClose		: false,
		selectMultiple		: false,
		numSelectable		: Number.MAX_VALUE,
		clickInput			: false,
		rememberViewedMonth	: false,
		selectWeek			: false,
		verticalPosition	: $.dpConst.POS_TOP,
		horizontalPosition	: $.dpConst.POS_LEFT,
		verticalOffset		: 0,
		horizontalOffset	: 0,
		hoverClass			: 'dp-hover',
        chooseImg			: '',
        altField		    : undefined,
        dateFormat          : undefined,
        showOtherMonth		: false,
		autoFocusNextInput  : false
	};

	function _getController(ele)
	{
		if (ele._dpId) return $.event._dpCache[ele._dpId];
		return false;
	};
	
	// make it so that no error is thrown if bgIframe plugin isn't included (allows you to use conditional
	// comments to only include bgIframe where it is needed in IE without breaking this plugin).
	if ($.fn.bgIframe == undefined) {
		$.fn.bgIframe = function() {return this; };
	};


	// clean-up
	$(window)
		.bind('unload', function() {
			var els = $.event._dpCache || [];
			for (var i in els) {
				$(els[i].ele)._dpDestroy();
			}
		});
		
	
})(jQuery);

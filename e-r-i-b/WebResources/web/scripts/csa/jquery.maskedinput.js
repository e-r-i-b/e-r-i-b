/**
 * Использованы исходники digitalbush.com Version: 1.3, jQuery UI Mask и добавлено от себя.
 * Основная добавленная плюшка - свои функции валидации для разных частей маски и разбор маски по подстрокам(ранее было посимвольно).
 * Использование:
 * - если используем с календарем(date-picker), то юзаем метод dpApplyMask(), описанный в jquery.datePicker.js, который все делает сам для масок дат(по умолчанию даипазон дат берется из настроек календаря):
 *      dP.dpApplyMask();
 * - если юзаем отдельно маску, то юзаем createMask(inputMask), описаный ниже:
 *      input.createMask(dd/mm/yyyy);
 * - для масок дат есть смысл задавать диапазон валидных годов методом addYearPeriod(startYear, endYear), описанным ниже;
 * - собственные подстроки масок задаются в методе addDefinition(key, value), описанном ниже;
 * - обработчик конца ввода(например, для валидации всего значения input'а) задается в addCompleted, описанном ниже.
 * - для установки курсора в нужную позицию использовать caretSelect.
 * - Для того чтобы маска показывалать при получении фокуса элементом, нужно 1 раз вызвать, функцию  showMaskOnFocus
 * */

(function($) {

    var keyCode = {
        BACKSPACE: 8,
        DELETE: 46,
        DOWN: 40,
        END: 35,
        ENTER: 13,
        ESCAPE: 27,
        HOME: 36,
        LEFT: 37,
        NUMPAD_ADD: 107,
        NUMPAD_DECIMAL: 110,
        NUMPAD_DIVIDE: 111,
        NUMPAD_ENTER: 108,
        NUMPAD_MULTIPLY: 106,
        NUMPAD_SUBTRACT: 109,
        PAGE_DOWN: 34,
        PAGE_UP: 33,
        PERIOD: 190,
        RIGHT: 39,
        SPACE: 32,
        TAB: 9,
        UP: 38
    };

    $.fn.extend({
        createMask: function(inputMask, customPlaceholder)
        {
            if (!$.event._miCache) $.event._miCache = [];

            return this.each(
                function()
                {
                    if (!this._miId) {
                        this._miId = $.event.guid++;
                        $.event._miCache[this._miId] = new MaskedInput(this, customPlaceholder);
                    }

                    var controller = $.event._miCache[this._miId];

                    controller._createMask(inputMask);
                }
            )
        },
        addCompleted: function(completed){_w.call(this, '_addCompleted', completed);},
        caretSelect: function(position){_w.call(this, '_caretSelect', position);},
        addYearPeriod: function(startYear, endYear){_w.call(this, '_addYearPeriod', startYear, endYear);},
        showMaskOnFocus: function () {
            $(this).focus(function(){
                _w.call($(this), 'showMask');
            })
        }
    });
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
    function MaskedInput(ele, customPlaceholder){
        this.ele = ele;

        this.clearEmpty = true;
        this.startYear =  1900;
        this.endYear =  2999;
        this.definitions = {
            "9": /[0-9]/,
            "a": /[A-Za-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]/,
            "*": /[A-Za-z0-9\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]/,
            "dd": function( value ) {
                value = parseInt( value, 10 );
                if(value > 31)
                {
                    return 31;
                }
                if ( value >= 0 && value <= 31 ) {
                    if(value == 0) value = 1;
                    var res = ( value < 10 ? "0" : "" ) + value;
                    return (res == "00") ? "01" : res;
                }
            },
            "MM": function( value ) {
                value = parseInt( value, 10 );
                if(value > 12)
                {
                    return 12;
                }
                if ( value >= 0 && value <= 12 ) {
                    var res = ( value < 10 ? "0" : "" ) + value;
                    return (res == "00") ? "01" : res;
                }
            },
            "yyyy":function( value, input ) {
                value = parseInt( value, 10 );
                var endYear = parseInt(input.endYear, 10 );
                var startYear = parseInt(input.startYear, 10);
                if(value > endYear)
                {
                    return endYear;
                }
                if ( value >= 1 && value <= endYear ) {
                    return value < startYear ? startYear : value;
                }
            },
            hh: function( value ) {
                value = parseInt( value, 10 );
                if(value > 23)
                {
                    return 23;
                }
                if ( value >= 0 && value <= 23 ) {
                    var res = ( value < 10 ? "0" : "" ) + value;
                    return res;
                }
            },
            mm: sixty,
            ss: sixty,
            "p": /[0-9•]/
        };

        this.placeholder = customPlaceholder != undefined ? customPlaceholder : "_";
        this.completed = null;

        function sixty( value ) {
            value = parseInt( value, 10 );
            if(value > 59)
            {
                return 59;
            }
            if ( value >= 0 && value <= 59 ) {
                var res = ( value < 10 ? "0" : "" ) + value;
                return res;
            }
        }

        $.extend(
                MaskedInput.prototype,
                {

                    /**
                     * Показывает текщее значение поля вместе с маской
                     */
                    showMask:function(){
                        $(this.ele).val(this._getValue(false,true));
                    },

                    /**
                     * Добавление собственной подстроки для разбора маски
                     * @param key ключ(например: "dd")
                     * @param value валидатор подстроки
                     */
                    addDefinition: function( key, value ) {
                        this.definitions[key] = value;
                        this._parseMask();
                        this._parseValue();
                    },
                    /**
                     * Добавление обработчика конца ввода
                     * @param completed обработчик конца ввода
                     */
                    _addCompleted: function(completed){
                        this.completed = completed;
                    },
                    /**
                     * Добавление диапазона валидных годов для масок с датой
                     * @param startYear начало диапазона
                     * @param endYear конец диапазона
                     */
                    _addYearPeriod: function(startYear, endYear){
                        this.startYear = startYear;
                        this.endYear = endYear;
                    },

                    _createMask: function(inputMask) {
                        this._parseMask(inputMask);
                        this.refresh();
                        var c = this;
                        $(this.ele).addClass( "ui-mask" );

                        $(this.ele).focus(function() {
                            if(this.readOnly) return;
                            this.lastUnsavedValue = $(this.ele).val();
                            c._caretSelect( c._seekRight( c._parseValue() ) );

                            this._justFocused = true;
                            $(this).delay(100);
                            this._justFocused = false;
                        });
                        $(this.ele).click(function() {
                            if(this.readOnly) return;
                            // Normally, the call to handle this in the focus event handler would be
                            // sufficient, but Chrome fires the focus events before positioning the
                            // cursor based on where the user clicked (and then fires the click event).

                            // We only want to move the caret on clicks that resulted in focus
                            if ( this._justFocused ) {
                                c._caretSelect( c._seekRight( c._parseValue() ) );
                                this._justFocused = false;
                            }
                        });
                        $(this.ele).blur(function() {
                            if(this.readOnly) return;
                            this._justFocused = false;
                            // because we are constantly setting the value of the input, the change event
                            // never fires - we re-introduce the change event here
                            c._parseValue();
                            c._paint( false );
                            if ( $(this.ele).val() !== this.lastUnsavedValue ) {
                                this.change();
                            }
                        });
                        $(this.ele).keydown(function( event ) {
                            if(this.readOnly) return;
                            var bufferObject,
                                    key = event.keyCode,
                                    position = c._caret();

                            if ( event.shiftKey || event.metaKey || event.altKey || event.ctrlKey ) {
                                return;
                            }


                            switch ( key ) {
                                case keyCode.ESCAPE:
                                    $(this.ele).val( this.lastUnsavedValue );
                                    c._caretSelect( 0, c._parseValue() );
                                    event.preventDefault();
                                    return;

                                case keyCode.BACKSPACE:
                                case keyCode.DELETE:
                                    event.preventDefault();

                                    // if the caret is not "selecting" values, we need to find the proper
                                    // character in the buffer to delete/backspace over.
                                    if ( position.begin === position.end || c._isEmpty( position.begin, position.end ) ) {
                                        if ( key === keyCode.DELETE ) {
                                            position.begin = position.end = c._seekRight( position.begin - 1 );
                                        } else {
                                            position.begin = position.end = c._seekLeft( position.begin );
                                        }

                                        // nothing to backspace
                                        if ( position.begin < 0 ) {
                                            c._caret( c._seekRight( -1 ) );
                                            return;
                                        }
                                    }
                                    c._removeValues( position.begin, position.end );
                                    c._paint();
                                    c._caretSelect( position.begin );
                                    return;

                                case keyCode.LEFT:
                                case keyCode.RIGHT:
                                    bufferObject = c[ position.begin ];
                                    if ( bufferObject && bufferObject.length > 1 ) {
                                        bufferObject.value = c._validValue( bufferObject, bufferObject.value );
                                        c._paint();
                                        event.preventDefault();
                                    }
                                    if ( key === keyCode.LEFT ) {
                                        position = c._seekLeft( bufferObject ? bufferObject.start : position.begin );
                                    } else {
                                        position = c._seekRight( bufferObject ?
                                                bufferObject.start + bufferObject.length - 1 :
                                                position.end );
                                    }
                                    c._caretSelect( position );
                                    event.preventDefault();
                                    return;
                            }
                        });
                        $(this.ele).keypress(function( event ) {
                            if(this.readOnly) return;
                            var tempValue, valid,
                                    key = event.which,
                                    position = c._caret(),
                                    bufferPosition = c._seekRight( position.begin - 1 ),
                                    bufferObject = this.buffer[ bufferPosition ];
                            // ignore keypresses with special keys, or control characters
                            if ( event.metaKey || event.altKey || event.ctrlKey || key < 32 ) {
                                return;
                            }
                            var p= c.ele;
                            var g = this;
                            var t = $(this);
                            if ( position.begin !== position.end ) {
                                c._removeValues( position.begin, position.end );
                            }
                            if ( bufferObject ) {
                                tempValue = String.fromCharCode( key );
                                if ( bufferObject.length > 1 && bufferObject.value ) {
                                    tempValue = bufferObject.value.substr( 0, bufferPosition - bufferObject.start ) +
                                            tempValue +
                                            bufferObject.value.substr( bufferPosition - bufferObject.start + 1 );
                                    tempValue = tempValue.substr( 0, bufferObject.length );
                                }
                                valid = c._validValue( bufferObject, tempValue );
                                if ( valid ) {
                                    c._shiftRight( position.begin );
                                    bufferObject.value = tempValue;
                                    position = c._seekRight( bufferPosition );
                                    if ( position < bufferObject.start + bufferObject.length ) {
                                        c._paint();
                                        c._caret( position );
                                    } else {
                                        bufferObject.value = valid;
                                        c._paint();
                                        c._caretSelect( position );
                                    }
                                }
                                else
                                {
                                    var val = parseInt(bufferObject.value);
                                    if ((val > 0) && (val < 10))
                                    {
                                        c._shiftRight( position.begin );
                                        bufferObject.value = '0'+val;
                                        position = this._seekRight( ++bufferPosition );
                                        c._paint();
                                        c._caret( position );
                                    }
                                }
                            }
                            if(!this.buffer[ bufferPosition +1]){
                                if(this.readOnly) return;
                                if(c.completed)
                                    c.completed.call(this);
                            }
                            event.preventDefault();
                        });

                    },

                    _destroy: function() {
                        $(this.ele).removeClass( "ui-mask" );
                    },

                    refresh: function() {
                        this._parseValue();
                        this._paint();
                    },

                    valid: function() {
                        return this.isValid;
                    },

                    // returns (or sets) the value without the mask
                    value: function( value ) {
                        if ( value !== undefined ) {
                            $(this.ele).val( value );
                            this.refresh();
                        } else {
                            return this._getValue( true );
                        }
                    },

                    _setOption: function( key, value ) {
                        this._super( key, value );
                        if ( key === "mask" ) {
                            this._parseMask();
                            this._parseValue();
                        }
                    },

                    // helper function to get or set position of text cursor (caret)
                    _caret: function( begin, end ) {
                        var range;
                        // if begin is defined, we are setting a range
                        if ( begin !== undefined ) {
                            end = ( end === undefined ) ? begin : end;
                            return $(this.ele).each(function() {
                                if ( this.setSelectionRange ) {
                                    this.setSelectionRange( begin, end );
                                } else if ( this.createTextRange ) {
                                    range = this.createTextRange();
                                    range.collapse( true );
                                    range.moveEnd( "character", end );
                                    range.moveStart( "character", begin );
                                    range.select();
                                }});
                        } else {
                            // begin is undefined, we are reading the range
                            if ( $(this.ele)[0].setSelectionRange ) {
                                begin = $(this.ele)[0].selectionStart;
                                end = $(this.ele)[0].selectionEnd;
                            } else if ( document.selection && document.selection.createRange ) {
                                range = document.selection.createRange();
                                // the moveStart returns the number of characters it moved as a negative number
                                begin = 0 - range.duplicate().moveStart( "character", -100000 );
                                end = begin + range.text.length;
                            }
                            return {
                                begin: begin,
                                end: end
                            };
                        }
                    },
                    _caretSelect: function( bufferPosition ) {
                        var bufferObject = this.ele.buffer[ bufferPosition ];
                        if ( bufferObject && bufferObject.length > 1 ) {
                            this._caret( bufferObject.start, bufferObject.start + bufferObject.length );
                        } else {
                            this._caret( bufferPosition );
                        }
                    },
                    _getValue: function( raw, focused ) {
                        var bufferPosition, bufferObject, counter,
                                bufferLength = this.ele.buffer.length,
                                value = "",
                                lastValue = 0;

                        this.isEmpty = this.isValid = true;
                        for ( bufferPosition = 0; bufferPosition < bufferLength; bufferPosition += bufferObject.length ) {
                            bufferObject = this.ele.buffer[ bufferPosition ];
                            if ( bufferObject.literal ) {
                                if ( !raw && ( bufferPosition < this.optionalPosition || this.isValid ) ) {
                                    value += bufferObject.literal;
                                }
                            } else if ( bufferObject.value ) {
                                lastValue = bufferObject.start + bufferObject.length;
                                this.isEmpty = false;
                                value += bufferObject.value;
                                for ( counter = bufferObject.value.length; counter < bufferObject.length; counter++ ) {
                                    value += this.placeholder;
                                }
                            } else {
                                if ( !raw ) {
                                    for ( counter = bufferObject.length ; counter; counter-- ) {
                                        value += this.placeholder;
                                    }
                                }
                                if ( bufferPosition < this.optionalPosition ) {
                                    this.isValid = false;
                                }
                            }
                        }

                        // don't display the "optional" portion until the input is "valid" or there are
                        // values past the optional position
                        if ( this.clearEmpty && this.isEmpty && focused === false ) {
                            return "";
                        }

                        // strip the optional parts off if we haven't gotten there yet, or there are no values past it
                        // and we aren't focused
                        if ( lastValue <= this.optionalPosition && !( this.isValid && focused ) ) {
                            value = value.substr( 0, this.optionalPosition );
                        }
                        return value;
                    },

                    _isEmpty: function( begin, end ) {
                        var index;
                        if ( begin === undefined ) {
                            begin = 0;
                            end = this.ele.buffer.length - 1;
                        } else if ( end === undefined ) {
                            end = begin;
                        }
                        for ( index = begin; index <= end; index++ ) {
                            if ( this.ele.buffer[ index ] && this.ele.buffer[ index ].value ) {
                                return false;
                            }
                        }
                        return true;
                    },
                    _paste: function() {
                        var position = this._parseValue();
                        this._paint();
                        this._caret( this._seekRight( position ) );
                    },
                    _paint: function( focused ) {
                        if ( focused === undefined ) {
                            focused = this === document.activeElement;
                        }
                        $(this.ele).val( this._getValue( false, focused ) );
                    },
                    _addBuffer: function( bufferObject ) {
                        var x,
                                begin = bufferObject.start,
                                end = bufferObject.start + bufferObject.length;

                        for ( x = begin; x < end; x++ ) {
                            if ( this.ele.buffer[ x ] !== undefined ) {
                                return false;
                            }
                        }

                        for ( x = begin; x < end; x++ ) {
                            this.ele.buffer[ x ] = bufferObject;
                        }

                        return true;
                    },
                    _removeCharacter: function( mask, index ) {
                        var x, bufferObject;

                        for ( x = index ; x < mask.length - 1 ; x++ ) {
                            bufferObject = this.ele.buffer[ x + 1 ];
                            this.ele.buffer[ x ] = bufferObject;
                            if ( bufferObject !== undefined ) {
                                bufferObject.start = bufferObject.start - 1;
                                x += bufferObject.length - 1;
                            }
                        }
                        this.ele.buffer.splice( x, 1 );

                        if ( this.optionalPosition > index ) {
                            this.optionalPosition--;
                        }

                        return mask.substring( 0, index ) + mask.substring( index + 1 );
                    },
                    _parseMask: function(inputMask) {
                        var key, x, optionalPosition,
                                index = -1,
                                mask = inputMask,
                                reservedChars = [ "a", "9", "*", "?", "<", ">", "\\" ];
                        if (mask.indexOf("mm") != -1 && (mask.indexOf("dd") != -1 || mask.indexOf("yy") != -1) && mask.indexOf("MM") == -1)
                            mask = mask.replace("mm", "MM");
                        this.ele.buffer = [];
                        if ( !mask ) {
                            return;
                        }
                        // search for escaped reserved characters
                        for ( index = 0 ; index < mask.length - 1 ; index++ ) {
                            if ( mask.charAt( index ) === "\\" &&
                                    $.inArray( mask.charAt( index + 1 ), reservedChars ) !== -1 ) {
                                // remove escape character
                                mask = mask.substring( 0, index ) + mask.substring( index + 1 );
                                this._addBuffer({
                                    start: index,
                                    literal: mask.charAt( index ),
                                    length: 1
                                });
                            }
                        }
                        // locate unescaped optional markers ; use attention to the first, remove all others
                        optionalPosition = -1;
                        this.optionalPosition = undefined;
                        while ( ( optionalPosition = mask.indexOf( "?", optionalPosition + 1 ) ) > -1 ) {
                            if ( this.ele.buffer[ optionalPosition ] === undefined ) {
                                if ( this.optionalPosition === undefined ) {
                                    this.optionalPosition = optionalPosition;
                                }

                                // remove the ? from the mask
                                mask = this._removeCharacter( mask, optionalPosition );
                            }
                        }
                        if ( this.optionalPosition === undefined ) {
                            this.optionalPosition = mask.length;
                        }

                        // search for strictly definied "masks"
                        for ( x = 0 ; x < mask.length ; x++ ) {
                            if ( mask.charAt(x) === "<" ) {
                                index = x;
                                for ( ; x < mask.length ; x++ ) {
                                    if ( mask.charAt(x) === ">" ) {
                                        key = mask.substring( index + 1 , x );
                                        if ( this.definitions[key] !== undefined ) {
                                            if (this._addBuffer({
                                                start: index + 1,
                                                length: key.length,
                                                valid: this.definitions[ key ]
                                            })) {
                                                mask = this._removeCharacter( mask, x );
                                                mask = this._removeCharacter( mask, index );
                                                for ( x = index ; x < index + key.length ; x++ ) {
                                                    mask[x] = " ";
                                                }
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        }

                        // search for definied "masks"
                        index = -1;
                        for ( key in this.definitions ) {
                            while ( ( index = mask.indexOf( key, index + 1 ) ) > -1 ) {
                                this._addBuffer({
                                    start: index,
                                    length: key.length,
                                    valid: this.definitions[ key ]
                                });
                            }
                        }

                        // anything we didn't find is a literal
                        for ( index = 0 ; index < mask.length ; index++ ) {
                            this._addBuffer({
                                start: index,
                                literal: mask.charAt( index ),
                                length: 1
                            });
                        }
                    },

                    // parses the .val() and places it into the buffer
                    // returns the last filled in value position
                    _parseValue: function() {
                        var bufferPosition, bufferObject, character,
                                valuePosition = 0,
                                lastFilledPosition = -1,
                                value = $(this.ele).val(),
                                bufferLength = this.ele.buffer.length;
                        if (value === undefined) value = '';
                        // remove all current values from the buffer
                        this._removeValues( 0, bufferLength );
                        // seek through the buffer pulling characters from the value
                        for ( bufferPosition = 0; bufferPosition < bufferLength; bufferPosition += bufferObject.length ) {
                            bufferObject = this.ele.buffer[ bufferPosition ];

                            while ( valuePosition < value.length ) {
                                character = value.substr( valuePosition, bufferObject.length );
                                if ( bufferObject.literal ) {
                                    if ( this._validValue( bufferObject, character ) ) {
                                        valuePosition++;
                                    }
                                    // when parsing a literal from a raw .val() if it doesn't match,
                                    // assume that the literal is missing from the val()
                                    break;
                                }
                                valuePosition++;
                                character = this._validValue( bufferObject, character );
                                if ( character ) {
                                    bufferObject.value = character;
                                    lastFilledPosition = bufferPosition + bufferObject.length - 1;
                                    valuePosition += bufferObject.length - 1;
                                    break;
                                }
                            }
                            // allow "default values" to be passed back from the buffer functions
                            if ( !bufferObject.value && (character = this._validValue( bufferObject, "" )) ) {
                                bufferObject.value = character;
                            }
                        }
                        return lastFilledPosition;
                    },
                    _removeValues: function( begin, end ) {

                        var position, bufferObject;
                        for ( position = begin; position <= end; position++ ) {
                            bufferObject = this.ele.buffer[ position ];
                            if ( bufferObject && bufferObject.value ) {
                                delete bufferObject.value;
                            }
                        }
                        this._shiftLeft( begin, end + 1 );
                        return this;
                    },

                    // _seekLeft and _seekRight will tell the next non-literal position in the buffer
                    _seekLeft: function( position ) {
                        while ( --position >= 0 ) {
                            if ( this.ele.buffer[ position ] && !this.ele.buffer[ position ].literal ) {
                                return position;
                            }
                        }
                        return -1;
                    },
                    _seekRight: function( position ) {
                        var length = this.ele.buffer.length;
                        while ( ++position < length ) {
                            if ( this.ele.buffer[ position ] && !this.ele.buffer[ position ].literal ) {
                                return position;
                            }
                        }
                        return length;
                    },

                    // _shiftLeft and _shiftRight will move values in the buffer over to the left/right
                    _shiftLeft: function( begin, end ) {
                        var bufferPosition,
                                bufferObject,
                                destPosition,
                                destObject,
                                bufferLength = this.ele.buffer.length;

                        for ( destPosition = begin, bufferPosition = this._seekRight( end - 1 );
                                destPosition < bufferLength;
                                destPosition += destObject.length ) {
                            destObject = this.ele.buffer[ destPosition ];
                            bufferObject = this.ele.buffer[ bufferPosition ];

                            // we don't want to shift values into multi character fields
                            if ( destObject.valid && destObject.length === 1 ) {
                                if ( bufferPosition < bufferLength ) {
                                    if ( this._validValue( destObject, bufferObject.value ) ) {
                                        destObject.value = bufferObject.value;
                                        delete bufferObject.value;
                                        bufferPosition = this._seekRight( bufferPosition );
                                    } else {

                                        // once we find a value that doesn't fit anymore, we stop this shift
                                        break;
                                    }
                                }
                            }
                        }
                    },
                    _shiftRight: function ( bufferPosition ) {
                        var bufferObject,
                                temp,
                                shiftingValue = false,
                                bufferLength = this.ele.buffer.length;

                        bufferPosition--;
                        while ( ( bufferPosition = this._seekRight( bufferPosition ) ) < bufferLength )
                        {
                            bufferObject = this.ele.buffer[ bufferPosition ];
                            if ( shiftingValue === false ) {
                                shiftingValue = bufferObject.value;
                            } else {

                                // we don't want to shift values into multi character fields
                                if ( bufferObject.length === 1 && this._validValue( bufferObject, shiftingValue ) ) {
                                    temp = bufferObject.value;
                                    bufferObject.value = shiftingValue;
                                    shiftingValue = temp;
                                } else {
                                    return;
                                }
                            }
                        }
                    },

                    // returns the value if valid, otherwise returns false
                    _validValue: function( bufferObject, value ) {
                        if ( bufferObject.valid ) {
                            if ( $.isFunction( bufferObject.valid ) ) {
                                return bufferObject.valid( value || "" , this) || false;
                            }
                            return bufferObject.valid.test( value ) && value;
                        }
                        return ( bufferObject.literal === value ) && value;
                    }
                });
    };


    function _getController(ele)
    {
        if (ele._miId) return $.event._miCache[ele._miId];
        return false;
    };

})(jQuery);

/* таблица соответствия кодов юникод с кодами windows-1251 в десятичном формате */
var TABLE_UNICODE_TO_WIN_BY_DECIMAL = new Array();
/* добавляем элементы которые совпадают */
for(var i = 0; i < 128; i++)
   TABLE_UNICODE_TO_WIN_BY_DECIMAL[i] = i;
/* русские символы отличаются ровно на 848 */
for(var j = 192; j < 256; j++)
   TABLE_UNICODE_TO_WIN_BY_DECIMAL[j+848] = j;
/* остальные символы */
TABLE_UNICODE_TO_WIN_BY_DECIMAL[1026] = 128;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[8226] = 149;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[171]  = 171;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[1027] = 129;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[8211] = 150;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[172]  = 172;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[8218] = 130;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[8212] = 151;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[173]  = 173;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[1107] = 131;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[8482] = 153;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[174]  = 174;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[8222] = 132;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1113] = 154;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1031] = 175;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[8230] = 133;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[8250] = 155;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[176]  = 176;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[8224] = 134;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1114] = 156;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[177]  = 177;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[8225] = 135;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1116] = 157;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1030] = 178;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[8364] = 136;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1115] = 158;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1110] = 179;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[8240] = 137;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1119] = 159;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1169] = 180;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[1033] = 138;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[160]  = 160;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[181]  = 181;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[8249] = 139;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1038] = 161;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[182]  = 182;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[1034] = 140;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1118] = 162;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[183]  = 183;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[1036] = 141;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1032] = 163;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1105] = 184;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[1035] = 142;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[164]  = 164;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[8470] = 185;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[1039] = 143;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1168] = 165;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1108] = 186;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[1106] = 144;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[166]  = 166;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[187]  = 187;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[8216] = 145;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[167]  = 167;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1112] = 188;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[8217] = 146;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1025] = 168;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1029] = 189;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[8220] = 147;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[169]  = 169;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1109] = 190;
TABLE_UNICODE_TO_WIN_BY_DECIMAL[8221] = 148;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1028] = 170;   TABLE_UNICODE_TO_WIN_BY_DECIMAL[1111] = 191;

function decodeURItoWin(str)
{
    var result = "";
    if(str == null)
        return result;

    for(var i = 0; i < str.length; i++)
    {
        var charCode = str.charCodeAt(i);
        var winCharCode = TABLE_UNICODE_TO_WIN_BY_DECIMAL[charCode];

        /* если символа нет в таблице, посылаем символ, которого нет в таблице */
        if(winCharCode == null)
        {
            result += '%' + (152).toString(16);
            continue;
        }


        /* добавляем ноль если требуется */
        if(charCode < 16)
        {
            result += '%0' + winCharCode.toString(16);
            continue;
        }

        result += '%' + winCharCode.toString(16);
    }

    return result;
};

/* простенький плагинчик для работы jquery */
(function($) {
    $.extend($.fn, {
        serializeToWin : function(){
            return $.paramToWin(this.serializeArray());
        }
    });

    $.paramToWin = function( a ) {
        var s = [];
        if ( a.constructor == Array || a.jquery )
        {
            jQuery.each( a, function(){
                s.push(decodeURItoWin(this.name) + "=" + decodeURItoWin( this.value ) );
            });
        }
        else
        {
            for ( var j in a )
            {
                if ( a[j] && a[j].constructor == Array )
                    jQuery.each( a[j], function(){
                        s.push( decodeURItoWin(j) + "=" + decodeURItoWin( this ) );
                    });
                else
                    s.push( decodeURItoWin(j) + "=" + decodeURItoWin( jQuery.isFunction(a[j]) ? a[j]() : a[j] ) );
            }
        }

        return s.join("&").replace(/%20/g, "+");
    };

}(jQuery));



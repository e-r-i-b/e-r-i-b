/*
 * JavaScript реализация построения хеша буфера данных
 * с использованием варианта алгоритма ГОСТ 34.11-94 а
 * также реализация на его основе HMAC(Keyed - Hashing
 * for Message Authentication, RFC2104).
 *
 *
 * Версия 1.3 Copyright (C) 2007 R-Style Softlab
 *
 * Автор: Иванов Александр
 *
 *   Функция: fnHashLikeGOST(data)
 *  Параметр: шестнадцатеричное представление данных для которых необходимо построить хеш
 * Результат: строка содержащая шестнадцатеричное представление хеша
 *
 *   Функция: fnHmacLikeGOST(key, data)
 *  Параметр: шестнадцатеричное представление ключа для HMAC
 *  Параметр: шестнадцатеричное представление сообщения
 * Результат: строка содержащая шестнадцатеричное представление HMAC
 *
 */

var CNA = new Array(
						new Array(0x8,0x7,0x3,0xC,0xE,0xD,0x2,0x0,0xB,0xA,0x4,0x1,0x5,0xF,0x9,0x6),
						new Array(0xC,0x8,0x9,0xD,0x3,0x4,0x1,0x5,0x7,0x6,0x2,0xF,0xA,0x0,0xB,0xE),
						new Array(0xA,0x5,0xC,0x8,0xD,0x2,0x1,0x9,0xB,0x7,0xE,0xF,0x0,0x3,0x6,0x4),
						new Array(0xA,0xC,0xB,0x0,0x6,0x2,0xE,0x8,0xF,0x5,0x7,0xD,0x3,0x9,0x4,0x1),
						new Array(0x5,0x3,0xF,0xE,0xA,0x0,0xB,0x8,0x7,0x1,0xD,0x9,0x2,0x4,0xC,0x6),
						new Array(0x3,0x9,0x4,0x0,0xE,0x7,0x8,0xF,0x5,0xD,0x6,0xA,0xB,0x2,0x1,0xC),
						new Array(0x9,0x4,0x0,0xF,0x7,0xD,0xA,0xB,0x2,0x3,0x5,0x6,0xE,0x1,0xC,0x8),
						new Array(0x3,0x6,0xA,0xE,0x2,0xB,0x1,0x9,0xD,0xC,0x8,0xF,0x4,0x5,0x0,0x7)
					);

var CP = new Array(0xFF00FF00, 0xFF00FF00, 0x00FF00FF, 0x00FF00FF, 0x00FFFF00, 0xFF0000FF, 0x000000FF, 0xFF00FFFF);

var HEXTAB = "0123456789ABCDEF";

function numb_to_hex(val)
{
    var buff = "";

	buff += HEXTAB.charAt((val >> 4) & 15) + HEXTAB.charAt(val & 15);
	val >>>= 8;
	buff += HEXTAB.charAt((val >> 4) & 15) + HEXTAB.charAt(val & 15);
	val >>>= 8;
	buff += HEXTAB.charAt((val >> 4) & 15) + HEXTAB.charAt(val & 15);
	val >>>= 8;
	buff += HEXTAB.charAt((val >> 4) & 15) + HEXTAB.charAt(val & 15);

    return buff;
}

function	hash_to_hex(data)
{
    var buff = "";

	for(var i = 0, l = data.length; i < l; ++i)
	{
		buff += numb_to_hex(data[i]);
	}

	return buff;
}

function Loop32_Encode(B, X)
{
	for(var i = 0, j, n, v;;)
	{
		n = B[0] + X[(i < 24) ? (i & 7) : (31 - i)];
		v = 0;

		for(j = 0; j < 8; ++j, n >>>= 4)
		{
			v |= CNA[j][n & 15] << (j << 2);
		}

		v = ((v << 11) | (v >>> 21)) ^ B[1];

		if(++i < 32)
		{
			B[1] = B[0];
			B[0] = v;

            continue;
		}

		B[1] = v;

		return B;
	}
}

function    fnShift64(BUFF, count)
{
    var B = new Array(2);

	while(count--)
	{
		B[0] = BUFF[0] ^ BUFF[2];
		B[1] = BUFF[1] ^ BUFF[3];

		BUFF[0] = BUFF[2];
		BUFF[1] = BUFF[3];
		BUFF[2] = BUFF[4];
		BUFF[3] = BUFF[5];
		BUFF[4] = BUFF[6];
		BUFF[5] = BUFF[7];
		BUFF[6] = B[0];
		BUFF[7] = B[1];
	}

	return BUFF;
}

function    fnShift16(BUFF, count)
{
    var v;

	while(count--)
	{
        v = BUFF[0] ^ (BUFF[0] >>> 16) ^ BUFF[1] ^ (BUFF[1] >>> 16) ^ BUFF[6] ^ (BUFF[7] >>> 16);

		BUFF[0] = (BUFF[0] >>> 16) | (BUFF[1] << 16);
		BUFF[1] = (BUFF[1] >>> 16) | (BUFF[2] << 16);
		BUFF[2] = (BUFF[2] >>> 16) | (BUFF[3] << 16);
		BUFF[3] = (BUFF[3] >>> 16) | (BUFF[4] << 16);
		BUFF[4] = (BUFF[4] >>> 16) | (BUFF[5] << 16);
		BUFF[5] = (BUFF[5] >>> 16) | (BUFF[6] << 16);
		BUFF[6] = (BUFF[6] >>> 16) | (BUFF[7] << 16);
		BUFF[7] = (BUFF[7] >>> 16) | (v << 16);
	}

	return BUFF;
}

function    fnSum(A1, A2, S)
{
	for(var i = 0; i < 8; ++i)
    {
		S[i] = A1[i] ^ A2[i];
    }

    return S;
}

function hash_step(BUFF, HASH)
{
	var V = new Array(BUFF[0], BUFF[1], BUFF[2], BUFF[3], BUFF[4], BUFF[5], BUFF[6], BUFF[7]);
	var U = new Array(HASH[0], HASH[1], HASH[2], HASH[3], HASH[4], HASH[5], HASH[6], HASH[7]);

	var X = new Array(8);
	var E = new Array(8);
	var W = new Array(8);
	var B = new Array(2);

	for(var i = 0, j, k; i < 4; ++i)
	{
		if(i)
		{
            fnShift64(V, 2);
			fnShift64(U, 1);

            if(i == 2)
			{
				fnSum(U, CP, U);
			}
		}

		fnSum(U, V, W);

		for(k = 0; k < 8; ++k)
		{
		    for(j = 0, l = (k & 3) << 3, X[k] = 0; j < 4; ++j)
			{
                X[k] |= ((W[(j << 1) + (k >> 2)] >>> l) & 0xff) << (j << 3);
			}
		}

        B[0]   = HASH[j = i << 1];
        B[1]   = HASH[j + 1];
        E[j++] = Loop32_Encode(B, X)[0];
        E[j]   = B[1];
	}

    return fnShift16(fnSum(fnShift16(fnSum(fnShift16(E, 12), BUFF, E), 1), HASH, HASH), 61);
}

function fnHashLikeGOST(data)
{
	var ACCM = new Array(0, 0, 0, 0, 0, 0, 0, 0);
	var HASH = new Array(0, 0, 0, 0, 0, 0, 0, 0);
	var SIZE = new Array(0, 0, 0, 0, 0, 0, 0, 0);

	for(var i = 0, size = data.length >> 1, j, v, n, ii, iv, code; size > 0;)
	{
		n = (size < 32) ? (size << 3) : 256;

	    var BUFF = new Array(0, 0, 0, 0, 0, 0, 0, 0);

		for(j = 0, v = 0; j < 32; ++j)
		{
			ii = (j & 3) << 3;
			iv = j >> 2;

			if(n)
			{
				n += (SIZE[iv] >>> ii) & 0xff;

				SIZE[iv] &= ~(0xff << ii);
				SIZE[iv] |= (n & 0xff) << ii;

				n >>>= 8;
			}

			if(size)
			{
                --size;

                code = new Number("0x" + data.charAt(i++) + data.charAt(i++));
				BUFF[iv] |= code << ii;
				v += code;
			}
			else if(v == 0)
			{
				continue;
			}

			v += (ACCM[iv] >>> ii) & 0xff;

			ACCM[iv] &= ~(0xff << ii);
			ACCM[iv] |= (v & 0xff) << ii;

			v >>>= 8;
		}

		hash_step(BUFF, HASH);
	}

	hash_step(SIZE, HASH);

    return hash_to_hex(hash_step(ACCM, HASH));
}

function fnHmacXor(data, v)
{
    var buff = "";

    for(var i = 0, j = 0, n; i < 64; ++i) if(i < data.length)
    {
        n = new Number("0x" + data.charAt(j++) + data.charAt(j++)) ^ v;

	    buff += HEXTAB.charAt((n >> 4) & 15) + HEXTAB.charAt(n & 15);
    }
    else
    {
	    buff += HEXTAB.charAt((v >> 4) & 15) + HEXTAB.charAt(v & 15);
    }

    return buff;
}

function fnHmacLikeGOST(key, data)
{
    if(key.length > 64)
    {
        key = fnHashLikeGOST(key);
    }

    return fnHashLikeGOST(fnHmacXor(key, 0x5c) + fnHashLikeGOST(fnHmacXor(key, 0x36) + data));
}

function fnStr2Hex(data)
{
    var buff = "";

    for(var i = 0, n = data.length, v; i < n; ++i)
    {
        v = data.charCodeAt(i);
	    buff += HEXTAB.charAt((v >> 4) & 15) + HEXTAB.charAt(v & 15);
        v >>>= 8;
	    buff += HEXTAB.charAt((v >> 4) & 15) + HEXTAB.charAt(v & 15);
    }

    return buff;
}

function fnStrHashLikeGOST(data)
{
    return fnHashLikeGOST(fnStr2Hex(data));
}

function fnStrHmacLikeGOST(key, data)
{
    return fnHmacLikeGOST(fnStr2Hex(key), fnStr2Hex(data));
}
/**
 * Created by Dendy on 2015/4/27.
 */
var Encrypter = {
    /**
     * Base64算法
     */
    Base64: {
        _BASE64_ENCODE_STR: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",
        encode: function (str) {
            var out, i, len;
            var c1, c2, c3;

            len = str.length;
            i = 0;
            out = "";
            while (i < len) {
                c1 = str.charCodeAt(i++) & 0xff;
                if (i == len) {
                    out += this._BASE64_ENCODE_STR.charAt(c1 >> 2);
                    out += this._BASE64_ENCODE_STR.charAt((c1 & 0x3) << 4);
                    out += "==";
                    break;
                }
                c2 = str.charCodeAt(i++);
                if (i == len) {
                    out += this._BASE64_ENCODE_STR.charAt(c1 >> 2);
                    out += this._BASE64_ENCODE_STR.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
                    out += this._BASE64_ENCODE_STR.charAt((c2 & 0xF) << 2);
                    out += "=";
                    break;
                }
                c3 = str.charCodeAt(i++);
                out += this._BASE64_ENCODE_STR.charAt(c1 >> 2);
                out += this._BASE64_ENCODE_STR.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
                out += this._BASE64_ENCODE_STR.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
                out += this._BASE64_ENCODE_STR.charAt(c3 & 0x3F);
            }
            return out;
        },
    },

    /**
     * SHA算法
     */
    SHA1: {
        _hexcase: 0,
        _chrsz: 8,
        /**
         * sha加密。
         * @param s 被加密的字符串.
         * @return {*} 密文
         */
        encode: function (s) {
            return this._binb2hex(this._core_sha1(this._AlignSHA1(s)));
        },

        /*
         * Calculate the SHA-1 of an array of big-endian words, and a bit length
         */
        _core_sha1: function (blockArray) {
            var x = blockArray;  //append padding
            var w = Array(80);
            var a = 1732584193;
            var b = -271733879;
            var c = -1732584194;
            var d = 271733878;
            var e = -1009589776;

            //每次处理512位 16*32
            for (var i = 0; i < x.length; i += 16) {
                var olda = a;
                var oldb = b;
                var oldc = c;
                var oldd = d;
                var olde = e;

                // 对每个512位进行80步操作
                for (var j = 0; j < 80; j++) {
                    if (j < 16) w[j] = x[i + j];
                    else w[j] = this._rol(w[j - 3] ^ w[j - 8] ^ w[j - 14] ^ w[j - 16], 1);
                    var t = this._safe_add(this._safe_add(this._rol(a, 5), this._sha1_ft(j, b, c, d)),
                        this._safe_add(this._safe_add(e, w[j]), this._sha1_kt(j)));
                    e = d;
                    d = c;
                    c = this._rol(b, 30);
                    b = a;
                    a = t;
                }
                a = this._safe_add(a, olda);
                b = this._safe_add(b, oldb);
                c = this._safe_add(c, oldc);
                d = this._safe_add(d, oldd);
                e = this._safe_add(e, olde);
            }
            return new Array(a, b, c, d, e);
        },

        /*
         * Perform the appropriate triplet combination function for the current iteration
         * 返回对应F函数的值
         */
        _sha1_ft: function (t, b, c, d) {
            if (t < 20) return (b & c) | ((~b) & d);
            if (t < 40) return b ^ c ^ d;
            if (t < 60) return (b & c) | (b & d) | (c & d);
            return b ^ c ^ d;  //t<80
        },

        /*
         * Determine the appropriate additive constant for the current iteration
         * 返回对应的Kt值
         */
        _sha1_kt: function (t) {
            return (t < 20) ? 1518500249 : (t < 40) ? 1859775393 : (t < 60) ? -1894007588 : -899497514;
        },

        /*
         * Add integers, wrapping at 2^32. This uses 16-bit operations internally
         * to work around bugs in some JS interpreters.
         * 将32位数拆成高16位和低16位分别进行相加，从而实现 MOD 2^32 的加法
         */
        _safe_add: function (x, y) {
            var lsw = (x & 0xFFFF) + (y & 0xFFFF);
            var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
            return (msw << 16) | (lsw & 0xFFFF);
        },

        /*
         * Bitwise rotate a 32-bit number to the left.
         * 32位二进制数循环左移
         */
        _rol: function (num, cnt) {
            return (num << cnt) | (num >>> (32 - cnt));
        },

        /*
         * The standard SHA1 needs the input string to fit into a block
         * This function align the input string to meet the requirement
         */
        _AlignSHA1: function (str) {
            var nblk = ((str.length + 8) >> 6) + 1, blks = new Array(nblk * 16);
            for (var i = 0; i < nblk * 16; i++)blks[i] = 0;
            for (i = 0; i < str.length; i++)
                blks[i >> 2] |= str.charCodeAt(i) << (24 - (i & 3) * 8);
            blks[i >> 2] |= 0x80 << (24 - (i & 3) * 8);
            blks[nblk * 16 - 1] = str.length * 8;
            return blks;
        },

        /*
         * Convert an array of big-endian words to a hex string.
         */
        _binb2hex: function (binarray) {
            var hex_tab = this._hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
            var str = "";
            for (var i = 0; i < binarray.length * 4; i++) {
                str += hex_tab.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8 + 4)) & 0xF) +
                    hex_tab.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8  )) & 0xF);
            }
            return str;
        }
    }
};
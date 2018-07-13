package com.woyou.util;

import java.math.BigInteger;
import java.util.UUID;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2018 UCWeb Inc. All Rights Reserved
 * Description  :  cn.uc.gamesdk.background.util.UuidUtil.java
 * <p>
 * Creation     : 2018/6/22
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2018 lizx, Create the file
 * *************************************************************
 */
public class UuidUtil {
    /**
     * 产生1个uuid
     * 由于原生uuid是32位 16进制数的 字符串，太浪费空间，所以把它转化为62进制的字符串，变短到22位（不改变唯一性）
     * @return
     */
    public static String generaUuid(){
        return MultiNumberationUtil.zipUuidto62Str(UUID.randomUUID());
    }

    /**
     * 大数进制转换
     * Created by guosen.lgs@alibaba-inc.com on 6/20/18.
     */
    private static class MultiNumberationUtil {

        //符号集，最大可转换进制为表的大小
        private final static char[] SYMBOLS_MAP = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        //            '!', '#', '%', '&', '$', '*', '(', ')', '[', ']', '{', '}', '^', '~', '?', '@', '>',
        //            '<', '=' };

        private static String radixConvert(BigInteger decimal, int deep) {
            if (deep < 1 || deep > SYMBOLS_MAP.length || deep == 10) {
                return "";
            }

            // 余数
            BigInteger d = BigInteger.valueOf(deep);
            BigInteger[] bigDivide = decimal.divideAndRemainder(d);
            int remainder = bigDivide[1].intValue();
            String result = "" + SYMBOLS_MAP[remainder];

            // 商
            BigInteger quotient = bigDivide[0];
            if (quotient.compareTo(d) >= 0) {
                result = radixConvert(quotient, deep) + result;
            } else {
                result = "" + SYMBOLS_MAP[quotient.intValue()] + result;
            }

            return result;
        }

        /**
         * 把一个严格16进制数字符串，转化为严格62进制数字符串(A-Z,a-z,0-9，共62进制)
         */
        static String zipUuidto62Str(UUID uuid) {
            try {
                String str = uuid.toString();
                if(str.length() == 32){
                    //没有分割线的版本
                    return str;
                }
                String sb = str.substring(0, 8) +
                        str.substring(9, 13) +
                        str.substring(14, 18) +
                        str.substring(19, 23) +
                        str.substring(24);
                BigInteger big = new BigInteger(sb, 16);
                return radixConvert(big, 62);
            }catch (Exception e){
                ZxLog.warn(e);
                return "";
            }
        }
    }
}

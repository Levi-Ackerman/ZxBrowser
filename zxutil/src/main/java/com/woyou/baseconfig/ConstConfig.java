package com.woyou.baseconfig;

/**
 * Created by lee on 18-2-24.
 */

public class ConstConfig {
    public static final String HOME_PAGE_URL = "file:///android_asset/search.html";
//    public static final String URL_REG = "(((^https?:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)$";
    public static final String URL_REG = "((https?|ftp|file)://)?[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
    public static final String BAIDU_SEARCH_PREFIX = "https://www.baidu.com/s?wd=";
}

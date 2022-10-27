package com.luo.utils;

import com.overzealous.remark.Remark;

public class Html2MarkdwonUtil {
    private static final Remark remark = new Remark();

    public static Remark getRemark() {
        return remark;
    }

    /** 将html转markdown */
    public static String covert(String content){
        return remark.convert(content);
    }
}

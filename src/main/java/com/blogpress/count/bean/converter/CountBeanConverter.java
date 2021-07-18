package com.blogpress.count.bean.converter;

import com.blogpress.common.util.bean.BeanCopyUtils;
import com.blogpress.count.bean.entity.Count;
import com.blogpress.count.bean.response.CountVO;

/**
 * 统计对象转换器
 *
 * @author JY
 */
public class CountBeanConverter {

    public static CountVO toCountVO(Count count) {
        if (count == null) {
            return null;
        }
        CountVO countVO = new CountVO();
        BeanCopyUtils.copy(count, countVO);
        return countVO;
    }

}

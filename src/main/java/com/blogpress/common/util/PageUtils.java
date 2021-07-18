package com.blogpress.common.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * 分页工具
 * @author JY
 */
public class PageUtils {

    public static <T, VO> PageInfo<VO> of(Page<T> list, Class<VO> voClass) {
        PageInfo<VO> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(list.getPageNum());
        pageInfo.setPageSize(list.getPageSize());
        pageInfo.setTotal(list.getTotal());
        pageInfo.setPages(list.getPages());
        return pageInfo;
    }

}

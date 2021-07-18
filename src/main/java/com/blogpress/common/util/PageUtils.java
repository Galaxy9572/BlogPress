package com.blogpress.common.util;

import com.blogpress.common.bean.response.PageVO;
import com.github.pagehelper.Page;

/**
 * 分页工具
 * @author JY
 */
public class PageUtils {

    public static <T, VO> PageVO<VO> of(Page<T> list, Class<VO> voClas) {
        PageVO<VO> pageInfo = new PageVO<>();
        pageInfo.setPageNo(list.getPageNum());
        pageInfo.setPageSize(list.getPageSize());
        pageInfo.setTotal(list.getTotal());
        pageInfo.setPages(list.getPages());
        return pageInfo;
    }

    public static <T> PageVO<T> of(org.springframework.data.domain.Page<T> page){
        PageVO<T> vo = new PageVO<>();
        vo.setPageNo(page.getNumber());
        vo.setPageSize(page.getSize());
        vo.setPages(page.getTotalPages());
        vo.setTotal(page.getTotalElements());
        vo.setList(page.getContent());
        return vo;
    }

}

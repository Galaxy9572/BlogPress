package com.blogpress.common.bean.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页结果VO
 * @author JY
 */
@Data
public class PageVO<T> {

    /** 总页数 */
    private Integer pages;

    /** 当前页 */
    private Integer pageNo;

    /** 页大小 */
    private Integer pageSize;

    /** 总数 */
    private Long total;

    /** 数据 */
    private List<T> list;

    public static <T> PageVO<T> of(Page<T> page){
        PageVO<T> vo = new PageVO<>();
        vo.setPageNo(page.getNumber());
        vo.setPageSize(page.getSize());
        vo.setPages(page.getTotalPages());
        vo.setTotal(page.getTotalElements());
        vo.setList(page.getContent());
        return vo;
    }

}

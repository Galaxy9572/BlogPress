package com.blogpress.search.dao;


import com.blogpress.search.bean.entity.SearchUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author JY
 */
public interface UserRepository extends ElasticsearchRepository<SearchUser, Long> {

    /**
     * 根据昵称分页查询用户
     * @param pageable Pageable
     * @param nick 昵称
     * @return Page<SearchUser>
     */
    @Highlight(fields = {@HighlightField(name = "nick")})
    Page<SearchUser> findByNickContaining(Pageable pageable, String nick);

    /**
     * 根据用户ID删除用户
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);
}

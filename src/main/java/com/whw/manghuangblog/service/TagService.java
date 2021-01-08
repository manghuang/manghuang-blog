package com.whw.manghuangblog.service;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import com.whw.manghuangblog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    Tag saveTag(Tag tag);

    Tag getTagById(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Long id, Tag tag);

    void deleteTagById(Long id);


}

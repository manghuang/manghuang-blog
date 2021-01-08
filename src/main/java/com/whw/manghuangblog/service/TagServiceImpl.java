package com.whw.manghuangblog.service;

import com.whw.manghuangblog.Exception.NotFoundException;
import com.whw.manghuangblog.dao.TagReponsitory;
import com.whw.manghuangblog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.Addressing;


@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagReponsitory tagReponsitory;

    @Override
    public Tag saveTag(Tag tag) {
        return tagReponsitory.save(tag);
    }

    @Override
    public Tag getTagById(Long id) {
        return tagReponsitory.getOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagReponsitory.findByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagReponsitory.findAll(pageable);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag one = tagReponsitory.getOne(id);
        BeanUtils.copyProperties(tag, one);
        return tagReponsitory.save(one);
    }

    @Override
    public void deleteTagById(Long id) {
        tagReponsitory.deleteById(id);
    }
}

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
import java.util.ArrayList;
import java.util.List;


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
    public List<Tag> listTag() {
        return tagReponsitory.findAll();
    }

    @Override
    public List<Tag> listTag(String tagIds) {
        return tagReponsitory.findAllById(convertToList(tagIds));
    }

    /**
     * 把字符串转换成类
     * @param ids
     * @return
     */
    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
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

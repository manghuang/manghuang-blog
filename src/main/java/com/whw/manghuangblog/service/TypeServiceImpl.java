package com.whw.manghuangblog.service;

import com.whw.manghuangblog.Exception.NotFoundException;
import com.whw.manghuangblog.dao.TypeReponsitory;
import com.whw.manghuangblog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeReponsitory typeReponsitory;

    @Transactional  // 事务
    @Override
    public Type saveType(Type type) {
        return typeReponsitory.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeReponsitory.getOne(id);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeReponsitory.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeReponsitory.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0,size, sort);
        return typeReponsitory.findTop(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type one = typeReponsitory.getOne(id);
        BeanUtils.copyProperties(type, one);
        return typeReponsitory.save(one);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeReponsitory.deleteById(id);
    }


    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeReponsitory.findByName(name);
    }
}

package com.kinglead.demo.service.impl;

import com.kinglead.demo.dao.UserRepository;
import com.kinglead.demo.entity.User;
import com.kinglead.demo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * 新增用户
     */
    @Override
    public User insert(User user) {
        return userRepository.save(user);
    }

    /**
     * 查询用户列表
     */
    @Override
    public Page<User> queryAll(String name,String age,Pageable pageable) {
            Specification<User> specification =  (Specification<User>)(root, query, criteriaBuilder) ->{
                List<Predicate> list = new ArrayList<>();
                // 第一个name为User实体对象中的字段，第二个name为参数
                Predicate p1 = criteriaBuilder.equal(root.get("name"),name);
                list.add(p1);
//                if (!age.equals(null)) {
//                    // 此处为查询serverName中含有age的数据
//                    Predicate p2 = criteriaBuilder.like(root.get("age"),"%"+age+"%" );
//                    list.add(p2);
//                }
                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            };
            return userRepository.findAll(specification,pageable);
    }

}

package com.example.productcategoryservice.repository;

import com.example.productcategoryservice.dto.UserFilterDto;
import com.example.productcategoryservice.entity.QUser;
import com.example.productcategoryservice.entity.User;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CustomUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    public List<User> users(UserFilterDto userFilterDto) {
        QUser qUser = QUser.user;
        var query = new JPAQuery(entityManager);
        JPAQueryBase from = query.from(qUser);
        if (userFilterDto.getName() != null &&
                !userFilterDto.getName().equals("")) {
            from.where(qUser.name.contains(userFilterDto.getName()));
        }
        if (userFilterDto.getSurname() != null &&
                !userFilterDto.getSurname().equals("")) {
            from.where(qUser.surname.contains(userFilterDto.getSurname()));
        }
        if (userFilterDto.getEmail() != null &&
                !userFilterDto.getEmail().equals("")) {
            from.where(qUser.email.contains(userFilterDto.getEmail()));
        }
        if (userFilterDto.getRole() != null) {
            from.where(qUser.role.eq(userFilterDto.getRole()));
        }
        return from.fetch();
    }
}

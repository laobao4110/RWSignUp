package com.example.rwsignup.contrller;
import com.example.rwsignup.dto.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private EntityManager entityManager;


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @Transactional
    @PostMapping("/register")
    public String registerUser(User user) {
        // Check if the email is already registered
        String email = user.getEmail() + "@laobao.line.pm";
        String sqlQuery = "SELECT 1 FROM `tbl_Users` where `Email` = :condition";
        NativeQuery query = (NativeQuery) entityManager.createNativeQuery(sqlQuery);
        query.setParameter("condition", email);

        // 使用 AliasToEntityMapResultTransformer 替代 setResultTransformer
        List<Map<String, Object>> result = query.unwrap(org.hibernate.query.NativeQuery.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                .getResultList();
        if (result != null && result.size()!= 0) {
            return "redirect:/register?error";
        }


        // 生成随机的盐值
        String salt = BCrypt.gensalt();

        // 使用 SHA-512-CRYPT 加密密码
        String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);

        String sqlInsert = "INSERT INTO `tbl_Users` ( `DomainID`, `Email`, `Password`, `Department`, `FulName`, `Active`) VALUES ( '1','"+ email +" ', '"+hashedPassword+"', 'IT', '"+user.getUsername()+"', '1');";
        entityManager.createNativeQuery(sqlInsert).executeUpdate();
        return "redirect:/success";
    }
    @GetMapping("/success")
    public String success() {
        return "success";
    }
}

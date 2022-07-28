package note.备用;


import com.example.admin.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Component
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Override
    /*
        登录的时候，将username传递过来；
        通过username查询admin表：
                存在，将密码提交给security
                不存在，认证失败
     */


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin=adminService.findAdminByUserName(username);

        if(admin==null){
            return null;
        }

//        admin信息不为空，将其信息装到security框架的UserDetails类中
        UserDetails userDetails=new User(username,admin.getPassword(),new ArrayList<>());

        return userDetails;


    }


}

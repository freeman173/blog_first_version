package note.备用;


import com.example.admin.pojo.Admin;
import com.example.admin.pojo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


//权限认证功能
@Service
public class AuthService {

    @Autowired
    private AdminService adminService;

//    public boolean auth(HttpServletRequest request, Authentication authentication){
//
//
//        String requestURI= request.getRequestURI();
//        Object principal=authentication.getPrincipal();
//
////        用户未登录
//        if(principal==null||"anonymousUser".equals(principal)){
//
//            return false;
//        }
//
////        根据用户名从数据库中拿到用户数据
//        UserDetails userDetails=(UserDetails) principal;
//        String userName=userDetails.getUsername();
//        Admin admin=adminService.findAdminByUserName(userName);
//        if(admin==null){
//            return false;
//        }
//
////        admin.id=1,超级管理员，直接放行
//        if(admin.getId()==1){
//            return true;
//        }
//
////        根据用户id拿到权限列表
//        Long adminId=admin.getId();
//        List<Permission> permissionList=adminService.findPermissionsByAdminId(adminId);
////        得到的uri里面可能携带参数，以？为分隔，去前面的uri即可！！
//        requestURI= StringUtils.split(requestURI,'?')[0];
//        for(Permission permission:permissionList){
//            if(requestURI.equals(permission.getPath())){
//
//                return true;
//            }
//
//        }
//
//        return true;
//    }




}

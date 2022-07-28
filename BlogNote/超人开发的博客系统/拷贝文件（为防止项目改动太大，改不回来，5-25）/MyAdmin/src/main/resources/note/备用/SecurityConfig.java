package note.备用;


import com.example.admin.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private AuthService authService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){return new BCryptPasswordEncoder();}

//    public static void main(String[] args){
//        String mszlu=new BCryptPasswordEncoder().encode("mszlu");
//        System.out.println(mszlu);
//
//    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        开启登录认证
//        http.authorizeRequests()
//                .antMatchers("/css/**").permitAll()
//                .antMatchers("/img/**").permitAll()
//                .antMatchers("/js/**").permitAll()
//                .antMatchers("/plugins/**").permitAll()
//                .antMatchers("/admin/**").access("@authService.auth(request,authentication)")
//                .antMatchers("/pages/**").authenticated()
//                .and().formLogin()
//                .loginPage("/login.html")//自定义登录页面
//                .loginProcessingUrl("/login")//登录页面的接口
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/pages/main.html")
//                .failureUrl("/login.html")
//                .permitAll()//
//                .and().logout()//退出登录配置
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login.html")
//                .permitAll()
//                .and()
//                .httpBasic()
//                .and()
//                .csrf().disable()//自定义登录时，需要关掉csrf
//                .headers().frameOptions().sameOrigin();
//    }
}

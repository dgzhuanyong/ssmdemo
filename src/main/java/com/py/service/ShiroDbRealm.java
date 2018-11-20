package com.py.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.py.entity.SysUser;


/**
 * 
 * @author Administrator <br>
 * 
 * 在认证、授权内部实现机制中都有提到，最终处理都将交给Real进行处理。因为在Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的。<br>
 * 通常情况下，在Realm中会直接从我们的数据源中获取Shiro需要的验证信息。可以说，Realm是专用于安全框架的DAO<br>
 * <br>
 * Shiro的认证过程最终会交由Realm执行，这时会调用Realm的getAuthenticationInfo(token)方法:<br>
 * 该方法主要执行以下操作:<br>
 * 	1、检查提交的进行认证的令牌信息 <br>
 *  2、根据令牌信息从数据源(通常为数据库)中获取用户信息 <br>
 *  3、对用户信息进行匹配验证。<br>
 *  4、验证通过将返回一个封装了用户信息的AuthenticationInfo实例<br>
 *  5、验证失败则抛出AuthenticationException异常信息<br>
 *  <br>
 *  而在我们的应用程序中要做的就是自定义一个Realm类，继承AuthorizingRealm抽象类，重载doGetAuthenticationInfo ()，重写获取用户信息的方法。<br>
 *  而授权实现则与认证实现非常相似，在我们自定义的Realm中，重载doGetAuthorizationInfo()方法，重写获取用户权限的方法即可。 <br>
 *  <br>
 */
@Component
public class ShiroDbRealm extends AuthorizingRealm {

	protected SysUserService sysUserService;
	
	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		SysUser sysUser = sysUserService.selectByLoginName(token.getUsername());
		if(null == sysUser) {
			return null;
		}
		if(sysUser.getState() > 0) {
			throw new DisabledAccountException();
		}
		//设置session超时时间，设置的时间单位是:ms，但是Shiro会把这个时间转成:s，而且是会舍掉小数部分，设置的最大时间，正负都可以，为负数时表示永不超时
		Session session = SecurityUtils.getSubject().getSession();
		//60分钟过期
		session.setTimeout(3600000);
		//将当前用户的菜单，放入shiro框架session中
		//session.setAttribute(Constants.MENU, godService.getMenuByUserId(god.getLoginName(),god.getId()));
		ShiroUser shiroUser = new ShiroUser(sysUser.getLoginName(), sysUser.getName());
		return new SimpleAuthenticationInfo(shiroUser, sysUser.getPassword(),ByteSource.Util.bytes(sysUser.getSalt()), getName());
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		//List<Role> roles = godService.selectRoleListByLoginName(shiroUser.loginName);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		/*for (Role role : roles) {
			// 基于Role的权限信息
			info.addRole(role.getName());
			// 基于Permission的权限信息
			info.addStringPermissions(roleService.selectPrivilegeListById(role.getId()));
		}*/
		return info;
	}
	
	
	@Autowired
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	/**
	 * 自定义Authentication对象
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public String loginName;
		public String name;

		public ShiroUser(String loginName, String name) {
			this.loginName = loginName;
			this.name = name;
		}

		public String getName() {
			return name;
		}
		
		public String getLoginName() {
			return loginName;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return name;
		}
	}
	
	
	/**
	 * 重写jsp shior:hasPermission 标签 支持复杂表达式（使用逆波兰表达式计算）
	 * 其中操作符不限大小写，支持and、or、not、&&、||、！
	 * demo: organization:create OR organization:update OR organization:delete
	 *	 ( organization:create Or organization:update ) OR  NOT organization:delete
	 *	 ( organization:create && organization:update ) OR  ! organization:delete
	 * 唯一缺点就是为了解析方便，所有内容必须用空格隔开
	 */
	
	//支持的运算符和运算符优先级
    @SuppressWarnings("serial")
	public static final Map<String, Integer> expMap = new HashMap<String, Integer>(){{
        put("not",0);
        put("!"  ,0);

        put("and",0);
        put("&&" ,0);

        put("or" ,0);
        put("||" ,0);

        put("("  ,1);
        put(")"  ,1);
    }};
    
    public static final Set<String> expList = expMap.keySet();
    
    /**
     * 	重写用户授权过程
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
    	//获得逆波兰表达式
        Stack<String> exp = getExp(expList, permission);
        if (exp.size() == 1){
            return super.isPermitted(principals, exp.pop());
        }
        List<String> expTemp = new ArrayList<>();
        //将其中的权限字符串解析成true , false
        for(String temp : exp){
            if (expList.contains(temp)){
                expTemp.add(temp);
            }else{
                expTemp.add(Boolean.toString(super.isPermitted(principals, temp)) );
            }
        }
        //计算逆波兰
        return computeRpn(expList, expTemp);
    }

    
    /**
     * 计算逆波兰
     * @param expList
     * @param exp
     * @return
     */
    private static boolean computeRpn(Collection<String> expList,Collection<String> exp){
        Stack<Boolean> stack = new Stack<>();
        for(String temp : exp){
            if (expList.contains(temp)){
                if ("!".equals(temp) || "not".equals(temp)){
                    stack.push( !stack.pop() );
                }else if ("and".equals(temp) || "&&".equals(temp)){
                    Boolean s1 = stack.pop();
                    Boolean s2 = stack.pop();
                    stack.push(s1 && s2);
                }else{
                    Boolean s1 = stack.pop();
                    Boolean s2 = stack.pop();
                    stack.push(s1 || s2);
                }
            }else{
                stack.push(Boolean.parseBoolean(temp));
            }
        }
        if (stack.size() > 1){
            throw new RuntimeException("compute error！ stack: "+ exp.toString());
        }else{
            return stack.pop();
        }
    }

    /**
     * 获得逆波兰表达式
     * @param expList
     * @param exp
     * @return
     */
    private static Stack<String> getExp(Collection<String> expList, String exp) {
        Stack<String> s1 = new Stack<>();
        Stack<String> s2 = new Stack<>();
        for (String str : exp.split(" ")){
            str = str.trim();
            String strL = str.toLowerCase();
            if ("".equals(str)){
                continue;
            }
            if ("(".equals(str)){
                //左括号
                s1.push(str);
            }else if (")".equals(str)){
                //右括号
                while(!s1.empty()){
                    String temp = s1.pop();
                    if ("(".equals(temp)){
                        break;
                    }else{
                        s2.push(temp);
                    }
                }
            }else if(expList.contains(strL)){
                //操作符
                if (s1.empty()){
                    s1.push(strL);
                }else {
                    String temp = s1.peek();
                    if ("(".equals(temp) || ")".equals(temp)){
                        s1.push(strL);
                    }else if(expMap.get(strL) >= expMap.get(temp)){
                        s1.push(strL);
                    }else{
                        s2.push(s1.pop());
                        s1.push(strL);
                    }
                }
            }else{
                //运算数
                s2.push(str);
            }
        }
        while(!s1.empty()){
            s2.push(s1.pop());
        }
        return s2;
    }
	
}

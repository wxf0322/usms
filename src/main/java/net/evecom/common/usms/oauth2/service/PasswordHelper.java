package net.evecom.common.usms.oauth2.service;

import net.evecom.common.usms.entity.UserEntity;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 描述 密码加密工具类
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/25 8:51
 */
@Service
public class PasswordHelper {

    /**
     * 随机数生成策略
     */
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    /**
     * 加密算法
     */
    @Value("${password.algorithmName}")
    private String algorithmName = "md5";

    /**
     * 散列次数
     */
    @Value("${password.hashIterations}")
    private int hashIterations = 2;

    /**
     * 加密用户信息
     *
     * @param user
     */
    public void encryptPassword(UserEntity user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();
        user.setPassword(newPassword);
    }

    /**
     * 根据用户名和盐值加密
     *
     * @param username
     * @param password
     * @param salt
     */
    public String encryptPassword(String username, String password, String salt) {
        String pwd = new SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(username + salt),
                hashIterations).toHex();
        return pwd;
    }

}

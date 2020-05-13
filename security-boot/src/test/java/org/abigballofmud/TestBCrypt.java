package org.abigballofmud;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/12/17 16:25
 * @since 1.0
 */
@SpringBootTest
public class TestBCrypt {

    @Test
   public void testBCrypt(){
        // 对密码加密
        String hashpw = BCrypt.hashpw("456", BCrypt.gensalt());
        System.out.println(hashpw);
        // 对密码校验
        boolean checkpw1 = BCrypt.checkpw("123", "$2a$10$hMtxppujq6W..Mft5gLG2OtRr9FcRShxN2.nwzg.wiNW6Yc4YsAii");
        boolean checkpw2 = BCrypt.checkpw("123", "$2a$10$GPhyb8ybIu.6gXMJt5ycH.yr6IT9YvOGvPcWYHniCMNouIeTt1GcC");
        Assertions.assertTrue(checkpw1);
        Assertions.assertTrue(checkpw2);
    }
}

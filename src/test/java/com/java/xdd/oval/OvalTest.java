package com.java.xdd.oval;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.junit.Test;

import java.util.List;

/**
 * Created by qw on 2017/9/4.
 */
public class OvalTest {
    @Test
    public void test() {
        User user = new User();
        user.setName("1");
        Validator validator = new Validator();
        List<ConstraintViolation> validate = validator.validate(user);

        validate.stream().forEach(c -> {
            System.out.println(c.getMessage());
//            System.out.println(c.getCheckName());
//            System.out.println(c.getSeverity());
        });

    }
}

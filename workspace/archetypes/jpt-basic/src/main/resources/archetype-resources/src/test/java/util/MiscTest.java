package ${package}.util;

import static org.junit.Assert.assertEquals;
import ognl.Ognl;

import org.junit.Test;

import ${package}.model.User;

public class MiscTest {

	@Test
	public void testOgnl() throws Exception {
		User user = new User();
		user.setPassword("111111");
		user.setConfirmPassword("123456");
		assertEquals(Ognl.getValue("password==confirmPassword", user), false);
		assertEquals(Ognl.getValue("password.substring(1)", user), "11111");
		assertEquals(
				Ognl.getValue(
								"confirmPassword.charAt(0)=='1'&&confirmPassword.charAt(1)=='2'&&confirmPassword.charAt(2)=='3'",
								user), true);
		Ognl.setValue("ctx.v1", user, 1);
		assertEquals(user.getCtx().get("v1"), 1);
	}

}

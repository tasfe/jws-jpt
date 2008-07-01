package ${package}.dao.ibatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import ${package}.dao.Dao;
import ${package}.model.User;
import ${package}.service.ModelStatics;

public class BasicDaoiBatisTestCase extends
		AbstractTransactionalDataSourceSpringContextTests {

	protected final Log logger = LogFactory.getLog(getClass());

	protected Dao dao;

	protected String[] getConfigLocations() {
		setAutowireMode(AUTOWIRE_BY_NAME);
		return new String[] { "classpath:/applicationContext-resources.xml",
				"classpath:/applicationContext-dao.xml" };
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	@Test
	public void testGetUserByName() {
		User user = (User) dao
				.get(ModelStatics.M_USER_BY_NAME, "administrator");
		assertEquals(user.getName(), "administrator");
	}

}

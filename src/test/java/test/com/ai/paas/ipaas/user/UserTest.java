package test.com.ai.paas.ipaas.user;


import org.apache.jmeter.protocol.java.sampler.JUnitSampler;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ai.paas.ipaas.PaasException;
import com.ai.paas.ipaas.uac.service.IUserClient;
import com.ai.paas.ipaas.uac.service.UserClientFactory;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;
import com.ai.paas.ipaas.uac.vo.AuthResult;

public class UserTest {
	private static transient final Logger log = LogManager
			.getLogger(UserTest.class);
	private IUserClient userClient = null;
	private AuthDescriptor ap = null;

	public UserTest() {

	}

	@Before
	public void setUp() throws Exception {
		try {
			String srvId, authAddr, pId, authPasswd;
			JUnitSampler sampler = new JUnitSampler();
			JMeterVariables vars = sampler.getThreadContext().getVariables();
			if (null != vars) {
				srvId = sampler.getThreadContext().getVariables()
						.get("serviceId");
				authAddr = sampler.getThreadContext().getVariables()
						.get("authAddr");
				pId = sampler.getThreadContext().getVariables()
						.get("pid");
				authPasswd = sampler.getThreadContext().getVariables()
						.get("authPasswd");
			} else {
				authAddr   = "http://10.1.228.198:14821/iPaas-Auth/service/auth";
				srvId      = "CCS001";
				pId        = "4bd58aa309df8c0d";
				authPasswd = "mvne";
				
			}
			ap = new AuthDescriptor(authAddr, pId,
					authPasswd, srvId);
			userClient = UserClientFactory.getUserClient();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	@After
	public void tearDown() throws Exception {
		userClient = null;
		ap = null;
	}

	@Test
	public void authUser() throws Exception {
		if (null == userClient) {
			log.error("userClient is null!");
			throw new PaasException("90000", "userClient is null!");
		}
		AuthResult ar = userClient.auth(ap);
		log.info(ar.toString());
	}




}

package intro.openl.myrules.util.deploy;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.openl.rules.ruleservice.deployer.RulesDeployerService;
import org.openl.util.FileUtils;

public class DeployUtils {
	static final String DB_URI = "jdbc:h2:~/openl-intro/db/openl-rules;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE";

	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();
		properties.put("production-repository.factory", "org.openl.rules.repository.db.JdbcDBRepositoryFactory");
		properties.put("production-repository.uri", DB_URI);
//		properties.put("production-repository.login", "myUsername");
//		properties.put("production-repository.password", "myPassword");
		properties.put("production-repository.base.path", "deploy/");

		File zipFile = new File("./../openl-intro-p3-myRules-rules/target/openl-intro-myRules-rules.zip");
		System.out.println(zipFile.getCanonicalPath());
		System.out.println(zipFile.exists());
		try (RulesDeployerService deployerService = new RulesDeployerService(properties)) {
			deployerService.deploy(FileUtils.getBaseName(zipFile.getName()), new FileInputStream(zipFile), true);
		}
	}

}

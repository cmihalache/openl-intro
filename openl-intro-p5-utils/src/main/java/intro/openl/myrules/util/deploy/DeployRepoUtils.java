package intro.openl.myrules.util.deploy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openl.rules.repository.RepositoryInstatiator;
import org.openl.rules.repository.api.FileData;
import org.openl.rules.repository.api.Repository;

public class DeployRepoUtils {

	public static void main(String[] args) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("uri", DeployUtils.DB_URI);

		Repository deployRepo = RepositoryInstatiator
				.newRepository("org.openl.rules.repository.db.JdbcDBRepositoryFactory", params);
//		List<FileData> list = deployRepo.list("");
		List<FileData> list = deployRepo.list("deploy/");
		System.out.println(list.size());
		FileData fileData = list.get(0);
		System.out.println("\tAuthor: " + fileData.getAuthor());
		System.out.println("\tBranch: "+fileData.getBranch());
		System.out.println("\tComment: " + fileData.getComment());
		System.out.println("\tName: " + fileData.getName());
		System.out.println("\tSize: " + fileData.getSize());
		System.out.println("\tUniqueId: " + fileData.getUniqueId());
		System.out.println("\tVersion: " + fileData.getVersion());
	}

}

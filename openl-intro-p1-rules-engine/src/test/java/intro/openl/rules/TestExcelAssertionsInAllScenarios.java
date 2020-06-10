package intro.openl.rules;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openl.rules.runtime.RulesEngineFactory;
import org.openl.rules.testmethod.ITestUnit;
import org.openl.rules.testmethod.TestStatus;
import org.openl.rules.testmethod.TestUnitsResults;
import org.openl.rules.testmethod.result.ComparedResult;
import org.openl.rules.vm.SimpleRulesRuntimeEnv;
import org.openl.rules.vm.SimpleRulesVM;
import org.openl.types.IOpenClass;
import org.openl.types.IOpenMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestExcelAssertionsInAllScenarios {
	private static final Logger LOG = LoggerFactory.getLogger(TestExcelAssertionsInAllScenarios.class);

	@ParameterizedTest
	@MethodSource("excelFilesProvider")
	void runAllTestRules(File excelFile) throws Exception {
		RulesEngineFactory<?> rulesFactory = new RulesEngineFactory<>(excelFile.getAbsolutePath());
		IOpenClass openClass = rulesFactory.getCompiledOpenClass().getOpenClass();
		SimpleRulesRuntimeEnv env = new SimpleRulesVM().getRuntimeEnv();
		for (IOpenMethod testMethod : getTestMethods(openClass)) {
			executeTestMethod(env, openClass, testMethod);
		}
	}

	private List<IOpenMethod> getTestMethods(IOpenClass openClass) {
		List<IOpenMethod> out = new ArrayList<IOpenMethod>();
		for (IOpenMethod method : openClass.getMethods()) {
			String methodName = method.getName().toLowerCase();
			if (methodName.startsWith("test") || methodName.endsWith("test")) { // our convention
				out.add(method);
			}
		}
		return out;
	}

	private void executeTestMethod(SimpleRulesRuntimeEnv env, IOpenClass openClass, IOpenMethod testMethod) {
		LOG.info("Executing tests in table: {}", testMethod.getName());
		@SuppressWarnings("unchecked")
		TestUnitsResults results = (TestUnitsResults) testMethod.invoke(openClass.newInstance(env), new Object[0], env);
		if (results.getNumberOfAssertionFailures() > 0) {
			for (ITestUnit testUnit : results.getTestUnits()) {
				for (ComparedResult cRes : testUnit.getComparisonResults()) {
					if (cRes.getStatus() != TestStatus.TR_OK) {
						LOG.info("Expected {} but was {}", cRes.getExpectedValue(), cRes.getActualValue());
					}
				}
			}
			fail("There are test failures in table '" + testMethod.getName() + "'");
		}
	}

	static final Stream<File> excelFilesProvider() throws IOException {
		return Files.list(Paths.get("src/test/resources")).filter(Files::isRegularFile)
				.filter(path -> path.toString().endsWith(".xlsx")).map(path -> path.toFile());
	}

}

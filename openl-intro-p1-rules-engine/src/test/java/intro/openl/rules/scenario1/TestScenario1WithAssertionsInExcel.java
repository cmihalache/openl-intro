package intro.openl.rules.scenario1;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
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

public class TestScenario1WithAssertionsInExcel {
	private static final String EXCEL_FILE = "src/test/resources/Scenario1.xlsx";
	private static final Logger LOG = LoggerFactory.getLogger(TestScenario1WithAssertionsInExcel.class);

	@Test
	void testPremiumAllowedRule() throws Exception {
		RulesEngineFactory<?> rulesFactory = new RulesEngineFactory<>(EXCEL_FILE);
		IOpenClass openClass = rulesFactory.getCompiledOpenClass().getOpenClass();
		SimpleRulesRuntimeEnv env = new SimpleRulesVM().getRuntimeEnv();
		Object target = openClass.newInstance(env);
//		IOpenMethod testMethod = openClass.getMethod("isPremiumAllowedTest", new IOpenClass[0]);
		for (IOpenMethod testMethod : getTestMethods(openClass)) {
			LOG.info("Executing tests in table: {}", testMethod.getName());
			@SuppressWarnings("unchecked")
			TestUnitsResults results = (TestUnitsResults) testMethod.invoke(target, new Object[0], env);
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

}

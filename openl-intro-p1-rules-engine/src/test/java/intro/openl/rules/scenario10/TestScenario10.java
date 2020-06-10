package intro.openl.rules.scenario10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.openl.rules.context.IRulesRuntimeContext;
import org.openl.rules.context.RulesRuntimeContextFactory;
import org.openl.rules.runtime.RulesEngineFactory;
import org.openl.runtime.IEngineWrapper;
import org.openl.vm.IRuntimeEnv;

public class TestScenario10 {
	private static final String EXCEL_FILE = "src/test/resources/Scenario10.xlsx";
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	@Test
	void testBaseDiscount() throws Exception {
		RulesEngineFactory<MyRules> rulesFactory = new RulesEngineFactory<>(EXCEL_FILE, MyRules.class);
		MyRules rules = rulesFactory.newEngineInstance();

		IRuntimeEnv env = ((IEngineWrapper) rules).getRuntimeEnv();
		IRulesRuntimeContext context = RulesRuntimeContextFactory.buildRulesRuntimeContext();
		env.setContext(context);

		context.setRequestDate(formatter.parse("2020-06-10T23:59:59"));
		assertEquals(0, rules.getBaseDiscount(), 0);

		context.setRequestDate(formatter.parse("2020-06-11T00:00:00"));
		assertEquals(0.1, rules.getBaseDiscount(), 0);

		context.setRequestDate(formatter.parse("2020-06-11T23:59:59"));
		assertEquals(0.1, rules.getBaseDiscount(), 0);

		context.setRequestDate(formatter.parse("2020-06-12T00:00:00"));
		assertEquals(0.0, rules.getBaseDiscount(), 0);
	}

	private static interface MyRules {
		double getBaseDiscount();
	}

}

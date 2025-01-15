import com.admiral.app.analyze.AnalyzatorClass;
import com.admiral.app.conf.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

public class TestApp {

    @Test
    void testAnalyzatorClass() throws IOException {
        String[] classNames = {
                "ru.lois.web.actions.GetGurnalAvtorizaciiAction",
                "ru.lois.setprodag.actions.VibratBankiAction",
                "ru.lois.nastroikitarifa.actions.GetNastroikaTarifaCfgAction"
        };
        AnalyzatorClass analyzatorClass1 = new AnalyzatorClass(
                Config.PATHS_FOR_ANALYZE_CLASSES[0],
                new String[] {"RPDSessionGetter.INST.получитьСессиюРПД"}
        );

        analyzatorClass1.analyze(Arrays.stream(classNames).toList());
        Assertions.assertEquals(2, analyzatorClass1.getClassList().size());
    }
}

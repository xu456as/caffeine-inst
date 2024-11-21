package me.fang.javaagent.instrumentation.caffeine;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;

import java.util.Collections;
import java.util.List;

@AutoService(InstrumentationModule.class)
public class CaffeineInstrumentationModule extends InstrumentationModule {


    public CaffeineInstrumentationModule() {
        super("caffeine", "caffeine-4");
    }

    @Override
    public List<TypeInstrumentation> typeInstrumentations() {
        return Collections.singletonList(new CaffeineInstrumentation());
    }
}

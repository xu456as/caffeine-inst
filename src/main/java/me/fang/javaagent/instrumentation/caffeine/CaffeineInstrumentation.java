package me.fang.javaagent.instrumentation.caffeine;

import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.checkerframework.checker.index.qual.NonNegative;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import static net.bytebuddy.matcher.ElementMatchers.*;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class CaffeineInstrumentation implements TypeInstrumentation {

    private static class OtelStatCounter implements StatsCounter{
        private StatsCounter originalStatCounter;
        private OtelStatCounter(StatsCounter sta) {
            System.out.println("OtelStatCounter");
            this.originalStatCounter = sta;
        }
        @Override
        public void recordHits(@NonNegative int i) {
            System.out.println("recordHits");
            originalStatCounter.recordHits(i);
        }

        @Override
        public void recordMisses(@NonNegative int i) {
            System.out.println("recordMisses");
            originalStatCounter.recordMisses(i);
        }

        @Override
        public void recordLoadSuccess(@NonNegative long l) {
            System.out.println("recordLoadSuccess");
            originalStatCounter.recordLoadSuccess(l);
        }

        @Override
        public void recordLoadFailure(@NonNegative long l) {
            System.out.println("recordLoadFailure");
            originalStatCounter.recordLoadFailure(l);
        }

        @Override
        public void recordEviction(@NonNegative int i, RemovalCause removalCause) {
            System.out.println("recordEviction");
            originalStatCounter.recordEviction(i, removalCause);
        }

        @Override
        public CacheStats snapshot() {
            System.out.println("snapshot");
            return originalStatCounter.snapshot();
        }
    };

    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {
        return ElementMatchers.named("org.me.demo.controller.HealthCheckController");
    }

    @Override
    public void transform(TypeTransformer transformer) {
        transformer.applyAdviceToMethod(
                named("ping").and(takesNoArguments()).and(isPublic()),
                this.getClass().getName() + "$MetricsAdvice");
    }

    public static class MetricsAdvice {
        private static OtelStatCounter otelStatCounter;


        @Advice.OnMethodExit
        public static void printPingResult(@Advice.Return String value,
                                           @Advice.FieldValue String field) throws Exception {
            System.out.println(value);
//            wes
        }
    }
}

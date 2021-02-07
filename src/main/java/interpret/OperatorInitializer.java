package interpret;

import function.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OperatorInitializer {
    Computable create(List<Computable> operands);
    int arity();

    class CosInitializer implements OperatorInitializer {

        @Override
        public Computable create(List<Computable> operands) {
            return new Cos(operands.get(0));
        }

        @Override
        public int arity() {
            return 1;
        }
    }

    class ExpInitializer implements OperatorInitializer {

        @Override
        public Computable create(List<Computable> operands) {
            return new Exp(operands.get(0));
        }

        @Override
        public int arity() {
            return 1;
        }
    }

    class LogInitializer implements OperatorInitializer {

        @Override
        public Computable create(List<Computable> operands) {
            return new Log(operands.get(0));
        }

        @Override
        public int arity() {
            return 1;
        }
    }

    class SinInitializer implements OperatorInitializer {

        @Override
        public Computable create(List<Computable> operands) {
            return new Sin(operands.get(0));
        }

        @Override
        public int arity() {
            return 1;
        }
    }

    class TanInitializer implements OperatorInitializer {

        @Override
        public Computable create(List<Computable> operands) {
            return new Tan(operands.get(0));
        }

        @Override
        public int arity() {
            return 1;
        }
    }

    class CustomInitializer implements OperatorInitializer {
        public static Map<String, Computable> defines = new HashMap<>();

        @Override
        public Computable create(List<Computable> operands) {
            return null;
        }

        @Override
        public int arity() {
            return 0;
        }
    }
}

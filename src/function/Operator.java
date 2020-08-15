package function;

import java.util.List;

public interface Operator extends Computable {
    public void bind(List<Computable> operands);
}

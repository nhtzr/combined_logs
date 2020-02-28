package mx.nhtzr.combinedlogs;

import java.util.Collection;

public class MultiAutoClosable<T extends AutoCloseable> implements AutoCloseable {

  private Collection<T> inner;

  public MultiAutoClosable(Collection<T> inner) {
    this.inner = inner;
  }

  public Collection<T> getInner() {
    return inner;
  }

  @Override
  public void close() {
    for (T i : this.inner) {
      try {
        i.close();
      }
      catch (Exception ignored) {
      }
    }
  }
}

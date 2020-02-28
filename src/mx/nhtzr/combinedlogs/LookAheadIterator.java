package mx.nhtzr.combinedlogs;

import java.util.Iterator;

public class LookAheadIterator<E> implements Iterator<E> {
  private final Iterator<E> iterator;
  private E peek;

  public LookAheadIterator(Iterator<E> iterator) {
    this.iterator = iterator;
  }

  public E peek() {
    final boolean hasPeekedBefore = this.peek != null;
    if (hasPeekedBefore) {
      return this.peek;
    }
    final boolean isAbleToPeek = iterator.hasNext();
    if (!isAbleToPeek) {
      return null;
    }

    return this.peek = iterator.next();
  }

  @Override
  public boolean hasNext() {
    final boolean hasPeekedBefore = this.peek != null;
    return hasPeekedBefore || iterator.hasNext();
  }

  @Override
  public E next() {
    final boolean hasPeekedBefore = this.peek != null;
    E next = hasPeekedBefore
      ? this.peek
      : iterator.next();
    this.peek = null;
    return next;
  }

}

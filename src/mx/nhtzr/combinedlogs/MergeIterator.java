package mx.nhtzr.combinedlogs;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class MergeIterator<E> implements Iterator<E> {
  private final PriorityQueue<LookAheadIterator<E>> queue;
  public MergeIterator(PriorityQueue<LookAheadIterator<E>> queue) {
    this.queue = queue;
  }
  @Override
  public boolean hasNext() {
    return !queue.isEmpty();
  }
  private static <T> Comparator<LookAheadIterator<T>> lookAheadComparatorOf(Comparator<? super T> comparator) {
    return (l, r) -> comparator.compare(l.peek(), r.peek());
  }

  public static <T> MergeIterator<T> mergeStreams(
    Stream<Stream<T>> streams,
    Comparator<? super T> comparator
  ) {
    PriorityQueue<LookAheadIterator<T>> queue = new PriorityQueue<>(
      lookAheadComparatorOf(comparator));

    streams
      .map(Stream::iterator)
      .map(LookAheadIterator::new)
      .filter(Iterator::hasNext)
      .forEach(queue::add);

    return new MergeIterator<>(queue);
  }

  @Override
  public E next() {
    while (!queue.isEmpty()) {
      final LookAheadIterator<E> nextIt = queue.poll();
      if (!nextIt.hasNext()) {
        continue;  // Somehow we got an empty iterator, lets try next it.
      }

      final E next = nextIt.next();
      if (nextIt.hasNext()) {
        queue.add(nextIt); // Return to queue it there are more values
      }
      return next;
    }
    throw new NoSuchElementException();
  }

}

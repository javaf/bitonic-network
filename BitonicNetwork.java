// Fine Set is a collection of unique elements
// maintained as a linked list. The list of nodes
// are arranged in ascending order by their key,
// which is obtained using `hashCode()`. This
// facilitates the search of a item within the
// list. When the list is empty, it contains two
// sentinel nodes `head` and `tail` with minimum
// and maximum key values respectively. These
// sentinel nodes are not part of the set.
// 
// Each node has an associated lock (fine-grained)
// that enables locking specific nodes, instead of
// locking down the whole list for all method
// calls. Traversing the list (find) is done in
// a hand-holding manner, as children do with an
// overhead ladder. Initially two nodes are locked.
// While moving to the next node, we unlock the
// first node, and lock the third node, and so on.
// This prevents any thread from adding or
// removing threads in between, allowing them to
// execute in pipelined fashion.
// 
// As this set uses fine-grained locks (per node),
// it performs well when contention is medium. Due
// to acquiring of locks in hand-holding fashion,
// threads traversing the list concurrently will
// be stacked behind each other. This forced
// pipelining occurs even if they want to modify
// completely different parts of the list.

class BitonicNetwork implements CountingNetwork {
  CountingNetwork[] halves;
  CountingNetwork merger;
  final int width;
  // size: number of items in set
  // head: points to begin of nodes in set

  public BitonicNetwork(int w) {
    if (w > 2) halves = new BitonicNetwork[] {
      new BitonicNetwork(w/2),
      new BitonicNetwork(w/2)
    };
    merger = new MergerNetwork(w);
    width = w;
  }

  // 1. Create new node beforehand.
  // 2. Find node after which to insert.
  // 3. Add node, only if key is unique.
  // 4. Increment size if node was added.
  // 5. Unlock node pairs locked by find.
  @Override
  public int traverse(int x) {
    int w = width;
    int h = x / (w/2);
    if (w > 2) {
      x = halves[h].traverse(x % (w/2));
      x = x + h * (w/2);
    }
    return merger.traverse(x);
  }
}

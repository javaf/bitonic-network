// Each Bitonic[2K] consists of 2 Bitonic[K]
// networks followed by a Merger[2K]. The top
// Bitonic[K] is connected to top-half inputs, and
// bottom Bitonic[K] is connected to bottom-half
// inputs. Outputs of both Bitonic[K] are connected
// directly to Merger[2K]. Bitonic[2] networks
// consists of a single Balancer.
class BitonicNetwork implements CountingNetwork {
  CountingNetwork[] halves;
  CountingNetwork merger;
  final int width;
  // halves: top, bottom Bitonic[K]
  // merger: Merger[2K] connected to both Bitonic[K]
  // width: number of inputs/outputs

  public BitonicNetwork(int w) {
    if (w > 2) halves = new BitonicNetwork[] {
      new BitonicNetwork(w/2),
      new BitonicNetwork(w/2)
    };
    merger = new MergerNetwork(w);
    width = w;
  }

  // 1. Find connected Bitonic[K].
  // 2. Traverse connected Bitonic[K].
  // 3. Traverse connected Merger[2K].
  @Override
  public int traverse(int x) {
    int w = width;
    int h = x / (w/2); // 1
    if (w > 2) {                         // 2
      x = halves[h].traverse(x % (w/2)); // 2
      x = x + h * (w/2);                 // 2
    }
    return merger.traverse(x); // 3
  }
}

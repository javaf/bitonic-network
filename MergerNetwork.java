// Each Merger[2K] consists of 2 Merger[K] networks
// followed by a layer of Balancers. The top
// Merger[K] takes even inputs from top-half and
// odd inputs from bottom-half. The bottom
// Merger[K] takes odd inputs from top-half and
// even inputs form bottom half. The respective
// output of each Merger[K] is then combined with
// a Balancer. When K is 1, Merger[2K] is a single
// Balancer.
class MergerNetwork implements CountingNetwork {
  CountingNetwork[] halves;
  CountingNetwork[] balancers;
  final int width;
  // halves: top, bottom Merger[K]
  // balancers: layers of Balancers
  // width: number of inputs/outputs

  public MergerNetwork(int w) {
    if (w > 2) halves = new MergerNetwork[] {
      new MergerNetwork(w/2),
      new MergerNetwork(w/2)
    };
    balancers = new Balancer[w/2];
    for (int i=0; i<w/2; i++)
      balancers[i] = new Balancer();
    width = w;
  }

  // 1. Find connected Merger[K].
  // 2. Traverse connected Merger[K].
  // 3. Traverse connected balancer.
  @Override
  public int traverse(int x) {
    int w = width;
    int h = x < w/2? x%2 : 1 - x%2; // 1
    if (w <= 2) x = x/2;            // 1
    else x = halves[h].traverse(x/2);      // 2
    return x*2 + balancers[x].traverse(0); // 3
  }
}

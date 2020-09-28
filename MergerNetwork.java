class MergerNetwork implements CountingNetwork {
  CountingNetwork[] halves;
  CountingNetwork[] balancers;
  final int width;

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

  @Override
  public int traverse(int x) {
    int w = width;
    int h = x < w/2? x%2 : 1 - x%2;
    if (w <= 2) x = x/2;
    else x = halves[h].traverse(x/2);
    return x*2 + balancers[x].traverse(0);
  }
}

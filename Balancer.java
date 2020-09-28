// A balancer is a simple switch with two input
// wires and two output wires, called the top and
// bottom wires (or sometimes the north and south
// wires). Tokens arrive on the balancer’s input
// wires at arbitrary times, and emerge on their
// output wires, at some later time. A balancer
// can be viewed as a toggle: given a stream of
// input tokens, it sends one token to the top
// output wire, and the next to the bottom, and so
// on, effectively balancing the number of tokens
// between the two wires.
class Balancer implements CountingNetwork {
  int state = 1;
  // state: previous state (0=up, 1=down)

  
  // Each time a balancer is traversed it switches
  // its state.
  @Override
  public synchronized int traverse(int x) {
    state = 1-state;
    return state;
  }
}

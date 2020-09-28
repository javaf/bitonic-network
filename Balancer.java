// A Balancer is a simple switch with two input
// and two output wires. Tokens arrive on the
// Balancer’s input wires at arbitrary times,
// and emerge on their output wires, at some later
// Given a stream of input tokens, it sends one
// token to the top output wire, and the next to
// the bottom, and so on, effectively balancing the
// number of tokens between the two wires.
class Balancer implements CountingNetwork {
  int state = 1;
  // state: previous state (0=up, 1=down)
  
  // 1. Flip balancer state.
  // 2. Return the updated state.
  @Override
  public synchronized int traverse(int x) {
    state = 1-state; // 1
    return state;    // 2
  }
}

package sequencing.computations.genericfolds.exercises

/**
  * A binary tree can be defined as follows:
  *   A Tree of type A is a Node with a left and right Tree or a Leaf with an element of type A
  */

sealed trait Tree[A] {
  def fold[B](node: (B, B) => B, leaf: A => B): B
}
// using final here means we cannot extend the class
// it is good to make case classes final as extending them is not a good idea
// you will get unexpected behaviour from equals, hashcode etc ...
final case class Leaf[A](elem: A) extends Tree[A] {
  def fold[B](node: (B, B) => B, leaf: A => B): B =
    leaf(elem)
}

final case class Node[A](left: Tree[A], right: Tree[A]) extends Tree[A] {
  def fold[B](node: (B, B) => B, leaf: A => B): B =
    node(left.fold(node, leaf), right.fold(node, leaf))
}
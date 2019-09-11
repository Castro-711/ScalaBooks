package recursion.exercises

/**
  * a binary tree of integers can be defined as follows
  *
  * a Tree is a Node with a left and right Tree or a Leaf with an element type Int
  */

sealed trait Tree {

  def sum(tree: Tree): Int =
    this match {
      case Leaf(v) => v
      case Node(l, r) => sum(l) + sum(r)
    }

  def double(tree: Tree): Tree =
    this match {
      case Leaf(v) => Leaf(v * 2)
      case Node(l, r) => Node(double(l), double(r))
    }
}

final case class Node(left: Tree, right: Tree) extends Tree
final case class Leaf(value: Int) extends Tree

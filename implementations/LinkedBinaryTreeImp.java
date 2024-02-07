package implementations;

/**
 *
 * @author Dinis
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedBinaryTreeImp<T> extends LinkedBinaryTree<T> {

    // A classe BinaryTreeNode já deve estar definida em algum lugar do projeto.
    protected int count;
    protected BinaryTreeNode<T> root;

    public LinkedBinaryTreeImp() {
        count = 0;
        root = null;
    }

    public LinkedBinaryTreeImp(T element) {
        count = 1;
        root = new BinaryTreeNode<>(element);
    }

    @Override
    public T getRoot() {
        if (root == null) {
            throw new NoSuchElementException("The tree is empty.");
        }
        return root.element;
    }

    @Override
    public boolean isEmpty() {
        return (count == 0);
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean contains(T targetElement) {
        return findAgain(targetElement, root) != null;
    }

    @Override
    public T find(T targetElement) {
        BinaryTreeNode<T> current = findAgain(targetElement, root);
        if (current == null) {
            throw new NoSuchElementException("Element not found in tree.");
        }
        return current.element;
    }

    private BinaryTreeNode<T> findAgain(T targetElement, BinaryTreeNode<T> next) {
        if (next == null) {
            return null;
        }
        if (next.element.equals(targetElement)) {
            return next;
        }
        BinaryTreeNode<T> temp = findAgain(targetElement, next.left);
        if (temp == null) {
            temp = findAgain(targetElement, next.right);
        }
        return temp;
    }

    @Override
    public String toString() {
        // Implementação simplificada. Deverá ser implementada conforme a necessidade.
        return "BinaryTree containing " + count + " elements";
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        return new Iterator<T>() {
            ArrayStack<BinaryTreeNode<T>> stack = new ArrayStack<>();

            {
                pushLeft(root);
            }

            private void pushLeft(BinaryTreeNode<T> node) {
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                BinaryTreeNode<T> node = stack.pop();
                T result = node.element;
                if (node.right != null) {
                    pushLeft(node.right);
                }
                return result;
            }
        };
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        return new Iterator<T>() {
            ArrayStack<BinaryTreeNode<T>> stack = new ArrayStack<>();

            {
                if (root != null) {
                    stack.push(root);
                }
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                BinaryTreeNode<T> node = stack.pop();
                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }
                return node.element;
            }
        };
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        DoublyLinkedList<T> postOrderList = new DoublyLinkedList<>();
        fillPostOrder(root, postOrderList);
        return postOrderList.iterator();
    }

    // Método auxiliar para preencher a lista em ordem post-order
    private void fillPostOrder(BinaryTreeNode<T> node, DoublyLinkedList<T> list) {
        if (node != null) {
            fillPostOrder(node.left, list);
            fillPostOrder(node.right, list);
            list.addLast(node.element);
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        return new Iterator<T>() {
            LinkedQueue<BinaryTreeNode<T>> queue = new LinkedQueue<>();

            {
                // Inicializa a fila com a raiz, se a árvore não estiver vazia
                if (root != null) {
                    queue.enqueue(root);
                }
            }

            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements in level order traversal");
                }

                // Remove o próximo nó da fila e enfileira seus filhos (se houver)
                BinaryTreeNode<T> currentNode = queue.dequeue();
                if (currentNode.left != null) {
                    queue.enqueue(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.enqueue(currentNode.right);
                }

                return currentNode.element;
            }
        };
    }
}

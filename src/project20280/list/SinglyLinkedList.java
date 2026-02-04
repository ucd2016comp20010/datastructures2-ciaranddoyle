package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class SinglyLinkedList<E> implements List<E>, Iterable<E>{

    private static class Node<E> {

        private E element;            // reference to the element stored at this node

        /**
         * A reference to the subsequent node in the list
         */
        private Node<E> next;         // reference to the subsequent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param e the element to be stored
         * @param n reference to a node that should follow the new node
         */
        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        // Accessor methods

        /**
         * Returns the element stored at the node.
         *
         * @return the element stored at the node
         */
        public E getElement() {
            return element;
        }

        /**
         * Returns the node that follows this one (or null if no such node).
         *
         * @return the following node
         */
        public Node<E> getNext() {
            if (next == null) {
                return null;
            }
            return next;
        }

        // Modifier methods

        /**
         * Sets the node's next reference to point to Node n.
         *
         * @param n the node that should follow this one
         */
        public void setNext(Node<E> n) {
            next = n;
        }
    } //----------- end of nested Node class -----------

    /**
     * The head node of the list
     */
    private Node<E> head = null;               // head node of the list (or null if empty)


    /**
     * Number of nodes in the list
     */
    private int size = 0;                      // number of nodes in the list

    public SinglyLinkedList() {
    }              // constructs an initially empty list

    //@Override
    public int size() {
        return size;
    }

    //@Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + position);
        }
        Node<E> current = head;
        for (int i = 0; i < position; i++) {
            current = current.getNext();
        }
        return current.getElement();
    }

    @Override
    public void add(int position, E e) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + position);
        }
        if (position == 0) {
            addFirst(e);
        } else {
            Node<E> prev = head;
            for (int i = 0; i < position - 1; i++) {
                prev = prev.getNext();
            }
            Node<E> newest = new Node<E>(e, prev.getNext());
            prev.setNext(newest);
            size++;
        }
    }


    @Override
    public void addFirst(E e) {
        Node<E> newest = new Node<E>(e, head); // create and link a new node
        head = newest;                         // update head to point to the new node
        size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> newest = new Node<E>(e, null); // node will eventually be the tail
        Node<E> last = head;
        if(last == null) {
            head = newest;
        }
        else {
            while (last.getNext() != null) { // advance to the last node
                last = last.getNext();
            }
            last.setNext(newest);
        }
        size++;
    }

    @Override
    public E remove(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + position);
        }
        if (position == 0) {
            return removeFirst();
        }
        Node<E> prev = head;
        for (int i = 0; i < position - 1; i++) {
            prev = prev.getNext();
        }
        E answer = prev.getNext().getElement();
        prev.setNext(prev.getNext().getNext());
        size--;
        return answer;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) return null;
        E answer = head.getElement();
        head = head.getNext();
        size--;
        return answer;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) return null;
        if (size == 1) {
            return removeFirst();
        }
        Node<E> prev = head;
        for (int i = 0; i < size - 2; i++) {
            prev = prev.getNext();
        }
        E answer = prev.getNext().getElement();
        prev.setNext(null);
        size--;
        return answer;

    }

    //@Override
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<E>();
    }

    private class SinglyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            E res = curr.getElement();
            curr = curr.next;
            return res;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        while (curr != null) {
            sb.append(curr.getElement());
            if (curr.getNext() != null)
                sb.append(", ");
            curr = curr.getNext();
        }
        sb.append("]");
        return sb.toString();

        //Q9
    }
    public SinglyLinkedList<E> sortedMerge(SinglyLinkedList<E> list) {
        Node<E> l2 = list.head;
        Node<E> l1 = this.head;
        SinglyLinkedList<E> mergedList = new SinglyLinkedList<E>();
        while (l1 != null && l2 != null) {
            Comparable<E> comp1 = (Comparable<E>) l1.getElement();
            E elem2 = l2.getElement();
            if (comp1.compareTo(elem2) <= 0) {
                mergedList.addLast(l1.getElement());
                l1 = l1.getNext();
            } else {
                mergedList.addLast(l2.getElement());
                l2 = l2.getNext();
            }
        }
        if(l1 != null) {
            while (l1 != null) {
                mergedList.addLast(l1.getElement());
                l1 = l1.getNext();
            }
        }
        if(l2 != null) {
            while (l2 != null) {
                mergedList.addLast(l2.getElement());
                l2 = l2.getNext();
            }
        }
        return mergedList;
    }

    //Q10

    public void reverse() {
        Node<E> prev = null;
        Node<E> curr = head;
        Node<E> next;

        while (curr != null) {
            next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        }
        head = prev;
    }

    //Q11
    public SinglyLinkedList<E> clone() {
        SinglyLinkedList<E> twin = new SinglyLinkedList<E>();
        Node<E> tmp = head;
        while (tmp != null) {
            twin.addLast(tmp.getElement());
            tmp = tmp.getNext();
        }
        return twin;
    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
        System.out.println("ll " + ll + " isEmpty: " + ll.isEmpty());
        //LinkedList<Integer> ll = new LinkedList<Integer>();

        // Q9

        SinglyLinkedList<Integer> l4 = new SinglyLinkedList<Integer>();
        l4.addFirst(24);
        l4.addFirst(20);
        l4.addFirst(6);
        l4.addFirst(2);

        SinglyLinkedList<Integer> l3 = new SinglyLinkedList<Integer>();
        l3.addFirst(25);
        l3.addFirst(19);
        l3.addFirst(12);
        l3.addFirst(8);
        l3.addFirst(5);
        l3.addFirst(3);
        l3.addFirst(1);

        System.out.println(l3);
        System.out.println(l4);

       System.out.println(  l4.sortedMerge(l3));

       //q10
       System.out.println(l4);
       l4.reverse();
       System.out.println(l4);

       //Q11
       System.out.println(l4.clone());



        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addFirst(3);
        ll.addFirst(4);
        ll.addLast(-1);
        //ll.removeLast();
        //ll.removeFirst();
        //System.out.println("I accept your apology");
        //ll.add(3, 2);
        System.out.println(ll);
        ll.remove(5);
        System.out.println(ll);

    }
}




/*
Q6. A singly linked list consists of nodes where each node points to the next node, and the last node points to null, indicating the end of the list.
In a circularly linked list, the last node does not point to null; instead, it points back to the first node, forming a loop. This means there is no natural “end” to the list.

Q7. A linked list is preferred when the size of the data structure needs to change frequently.

Q8. A circularly linked list works well when people take turns, such as in a board game or group activity.
After the last person takes their turn, it moves back to the first person without restarting the list.

A circularly linked list can be used for a playlist where songs play one after another.
When the last song finishes, the playlist automatically goes back to the first song and starts again.


 */

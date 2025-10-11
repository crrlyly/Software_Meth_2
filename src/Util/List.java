package Util;
import java.util.Iterator;
/**
 * This class creates a circularly linked list of trips.
 * Additionally, it inserts the trips in order of earliest end date, and can print the list/find the number of trips in the list.
 *
 * @author Alec Cotler
 */
public class List<E> implements Iterable<E> {
    public static final int CAPACITY = 4;
    public static final int NOT_FOUND = -1;

    private E[] objects; //E is the name for the generic type
    private int size;

    public List() {
        objects = (E[]) new Object[CAPACITY];
        size = 0;
    } //new an array type-casted to E with a capacity of 4.

    private int find(E e) {
        if (e == null){
            return NOT_FOUND;
        }
        for (int i = 0; i < size; i++){
            if (e.equals(objects[i])){
                return i;
            }
        }
        return NOT_FOUND;
    } //return -1 if not found

    private void grow() {
        E[] newObjects = (E[]) new Object[this.objects.length + CAPACITY];
        for(int i = 0; i < size; i++) {
            newObjects[i] = this.objects[i]; // copy over each element
        }
        this.objects = newObjects; // replace old array
    }

    public boolean contains(E e) {
        if (e == null){
            return false;
        }
        for (int i=0; i<size; i++){
            if (objects[i].equals(e)){
                return true;
            }
        }
        return false;
    }

    public void add(E e) {
        if (e == null) {
            return;
        }
        if (contains(e)) {
            return;
        }
        if (this.size == this.objects.length) {
            this.grow();
        }
        this.objects[size++] = e;
    }

    public void remove(E e) {
        int index = NOT_FOUND;
        if (contains(e)) {
            index = find(e);
        } else return;

        this.objects[index] = objects[size - 1];
        this.objects[size - 1] = null;
        size--;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int Size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    } //traversing the list using for each

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new RuntimeException("Index out of bounds");
        }
        return (E) objects[index];
    } //return the object at the index

    public void set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new RuntimeException("Index out of bounds");
        }
        objects[index] = e;
    } //put object e at the index

    public int indexOf(E e) {
        if (e == null){
            return NOT_FOUND;
        }
        for (int i = 0; i < size; i++) {
            if (objects[i].equals(e)) {
                return i;
            }
        }
        return NOT_FOUND;
    } //return index of object e, or return -1
    //private inner class for the iterator to work properly

    private class ListIterator<E> implements Iterator<E> {

        int current = 0; //current index when traversing the list (array)

        @Override
        public boolean hasNext(){
            return current <size;
        } //if it’s empty or at the end of the array

        @Override
        public E next(){
            if (!hasNext()) {
                throw new RuntimeException("No more elements");
            }
            return (E) objects[current++];
        } //return the next object in the list
    }

}
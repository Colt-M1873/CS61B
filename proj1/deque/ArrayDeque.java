package deque;

/** 使用率低于25%的时候重新安排数组，重置左右指针，arraycopy
 *
 */

public class ArrayDeque<T> {
    private T[] arr;
    private int left,right;
    private int size=right-left;
    public ArrayDeque(){
        arr=(T[])new Object[100]; // typecast is a must
        left = 50;
        right = 50;
    }

    /**enlarge array and relocate left right pointer*/
    private void enlarge(){
        System.out.println("enlarge");
        T[] newArr=(T[])new Object[2* arr.length];
        int newLeft=arr.length-(right-left)/2;
        int newRight=newLeft + (right-left);
        System.arraycopy(arr,left,newArr,newLeft,right-left);
        left= newLeft;
        right = newRight;
        arr=newArr;
    }
    private void shrink(){
        System.out.println("shrink");
    }

    public void addFirst(T item){
        System.out.println("addFirst");
        if(left>0){
            left--;
            arr[left]=item;
        }
        else{
            enlarge();
            addFirst(item);
        }
        System.out.println(right-left);
    }

    public void addLast(T item){
//        System.out.println("addLast");
        if(right<arr.length){
            arr[right]=item;
            right++;
        }
        else{
            enlarge();
            addLast(item);
        }
    }

    public boolean isEmpty(){
        return right-left==0;
    }

    public int size(){
        return right-left;
    }

    public void printDeque(){
        System.out.println("Dummy Deque");
    }

    public T removeFirst(){
        if(right-left<=0){return null;}
        T tmp=arr[left];
        arr[left]=null;
        left++;
        if((right-left)*4<arr.length && arr.length>100){
            shrink();
        }
        return tmp;
    }

    public T removeLast(){
        if((right-left)<=0){return null;}
        T tmp=arr[right-1];
        arr[right-1]=null;
        right--;

        if((right-left)*4<arr.length && arr.length>100){
            shrink();
        }

        return tmp;

    }

    public T getIndex(int index){
        if(index>=(right-left) || index<0){return null;}
        return arr[left+index];
    }


}

package deque;

public class LinkedListDeque<T> {
    private class DListNode{//double linked list node
        public T item;
        public DListNode next;
        public DListNode prev;


        public DListNode(T i, DListNode n,DListNode p){
            item=i;
            next=n;
            prev=p;
        }
    }

    private DListNode sentinel;
    private int size;

    public LinkedListDeque(){
        sentinel = new DListNode(null,null,null);
        sentinel.next=sentinel;
        sentinel.prev=sentinel;
        size=0;
    }

    public LinkedListDeque(T x){

        sentinel = new DListNode(null,null,null);
        sentinel.next=new DListNode(x,sentinel,sentinel);
        sentinel.prev=sentinel.next;
        size=0;
    }

    public void addFirst(T item){
//        System.out.println("addFirst");
        DListNode itemNode=new DListNode(item, null,null);
        DListNode tmpNext=sentinel.next;

        sentinel.next=itemNode;
        itemNode.prev=sentinel;
        tmpNext.prev=itemNode;
        itemNode.next=tmpNext;

        size++;
    }

    public void addLast(T item){
        System.out.println("addLast");
        DListNode itemNode=new DListNode(item, null,null);
        DListNode tmpPrev=sentinel.prev;

        sentinel.prev=itemNode;
        itemNode.next=sentinel;
        tmpPrev.next=itemNode;
        itemNode.prev=tmpPrev;

        size++;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        System.out.println("Dummy Deque");
    }

    public T removeFirst(){
//        System.out.println("removeFirst");
        if(size==0){return null;}
        DListNode firstTmp=sentinel.next;

        sentinel.next=firstTmp.next;
        firstTmp.next.prev=sentinel;
        firstTmp.next=null;
        firstTmp.prev=null;

        size--;
        return firstTmp.item;
    }
    public T removeLast(){
//        System.out.println("removeLast");
        if(size==0){return null;}
        DListNode lastTmp=sentinel.prev;

        sentinel.prev=lastTmp.prev;
        lastTmp.prev.next=sentinel;
        lastTmp.next=null;
        lastTmp.prev=null;

        size--;
        return lastTmp.item;
    }

    public T getIndex(int index){
        if(index>=size || index <0 ){return null;}
        DListNode ret=sentinel.next;
        while(index>0){
            ret=ret.next;
            index--;
        }
        return ret.item;
    }



}

import java.util.*;

public class TestQueue {

    public static void main(String[] args) {

//        testQueue();
//        testDeque();
        testPriorityQueue();

    }

    @SuppressWarnings("Since15")
    private static void testPriorityQueue() {
        Queue<Integer> queue = new LinkedList<Integer>();
        PriorityQueue<Integer> pqueue = new PriorityQueue<Integer>();
        PriorityQueue<Integer> pqueue2 = new PriorityQueue<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 % 2 == 0 && o2 % 2 == 0)
                    return o1.compareTo(o2);
                else if(o1 % 2 != 0 && o2 % 2 != 0)
                    return o1.compareTo(o2);
                else if(o1 % 2 == 0)
                    return -1;
                else if(o2 % 2 == 0)
                    return 1;
                else
                    return 0;
            }
        });
        for (int i = 5; i > 0; i--) {
            queue.add(i);
            pqueue.add(i);
            pqueue2.add(i);
        }


        while (!queue.isEmpty()){
            System.out.printf("q %d pq %d pq2 %d\n",queue.poll(),pqueue.poll(),pqueue2.poll());
        }

        System.out.println("queue   " + queue);
        System.out.println("pqueue  " + pqueue);
        System.out.println("pqueue2 " + pqueue2);

    }

    private static void testDeque() {

        Deque<Integer> deque = new LinkedList();
        deque.add(1);
        deque.add(2);
        deque.add(3);

        System.out.println(deque);

        System.out.println("remove(): "+deque.remove());
        System.out.println("poll(): "+ deque.poll());

        System.out.println(deque);

        System.out.println("element(): "+ deque.element());
        System.out.println("peek(): "+ deque.peek());

        System.out.println("poll(): "+ deque.poll());
        System.out.println("poll(): "+ deque.poll());

        try {
            System.out.println("remove(): "+ deque.remove());
        } catch (Exception e) {
            e.printStackTrace();
        }

        deque.add(1);
        deque.add(2);

        System.out.println("getFirst(): "+ deque.getFirst());
        System.out.println("getLast(): "+ deque.getLast());
        deque.addFirst(0);
        deque.addLast(3);
        System.out.println(deque);





    }

    private static void testQueue() {
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(1);
        queue.add(2);
        queue.add(3);

        System.out.println(queue);

        System.out.println("remove(): "+queue.remove());
        System.out.println("poll(): "+ queue.poll());

        System.out.println(queue);

        System.out.println("element(): "+ queue.element());
        System.out.println("peek(): "+ queue.peek());

        System.out.println("poll(): "+ queue.poll());
        System.out.println("poll(): "+ queue.poll());

        try {
            System.out.println("remove(): "+ queue.remove());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
